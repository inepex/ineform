package com.inepex.ineForm.client.form.widgets.datetime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.ui.FlowPanel;
import com.inepex.ineForm.client.form.widgets.DenyingFormWidget;
import com.inepex.ineForm.client.form.widgets.datetime.IneDateGWT.Precision;
import com.inepex.ineForm.client.resources.ResourceHelper;
import com.inepex.ineFrame.shared.util.DateProvider;
import com.inepex.ineom.shared.descriptor.DescriptorBase;
import com.inepex.ineom.shared.descriptor.FDesc;

/**
 * available properties:
 * 
 * fields:
 * 	year
 * 	yearmonth
 * 	date
 * 	hourminsec
 * 	hourmin
 *  datehourmin
 *  hourlist
 *  minlist
 * 	monthlist
 * 
 * other:
 *  now
 *  nowroundtosec:10
 *  nowroundtomin:5
 *  
 *  prop:
 *  	s - step
 *  	s(10)
 *  	t - textbox
 *  	m - enable select manager
 *  	c - calendar
 */
public class DateTimeFW extends DenyingFormWidget implements DateTimeFieldParentInterface {

	private final DateProvider dateProv;
	private final FlowPanel panel_main = new FlowPanel();
	private final IneDateGWT inedate = new IneDateGWT();
	private final List<DateTimeFieldInterface> fields = new ArrayList<DateTimeFieldInterface>();
	private final Map<String, String> props;

	/**
	 * standalone constructor...
	 * 
	 * example:
	 * DateTimeFW field = new DateTimeFW("year:st","monthlist");
	 * 
	 * @param stringprops
	 */
	public DateTimeFW(DateProvider dateProv, String... stringprops) {
		super(null);
		this.dateProv=dateProv;
		props=new HashMap<String, String>();
		
		if(stringprops!=null) {
			for(String prop : stringprops) {
				String[] parts = DescriptorBase.parseProp(prop);
				props.put(parts[0], parts[1]);
			}
		}
			
		init();
	}
	
	/**
	 * constructor for form generator
	 * 
	 * @param props
	 */
	public DateTimeFW(DateProvider dateProv, FDesc fielddescriptor, Map<String, String> props) {
		super(fielddescriptor);
		this.props=props;
		this.dateProv=dateProv;
		init();
	}
	
	private void init() {
		initWidget(panel_main);
		panel_main.addStyleName(ResourceHelper.ineformRes().style().datetimeFW());
		
		if(props.containsKey("now")) {
			inedate.setToNow();
		}
		
		if(props.containsKey("nowroundtosec")) {
			int rt=Integer.parseInt(props.get("nowroundtosec"));
			inedate.setToNowRoundToSec(rt);
		}
		
		if(props.containsKey("nowroundtomin")) {
			int rt=Integer.parseInt(props.get("nowroundtomin"));
			inedate.setToNowRoundToSec(rt*60);
		}
		
		//define fields
		if(props.containsKey("year")) {
			String val = props.get("year");
			fields.add(new YOO_OOField(inedate, val.contains("s"), getStep(val),val.contains("t"), this, val.contains("m")));
		}
		
		if(props.containsKey("yearmonth")) {
			String val = props.get("yearmonth");
			fields.add(new YMO_OOField(inedate, val.contains("s"), getStep(val), val.contains("t"), this, val.contains("m")));
		}
		
		if(props.containsKey("monthlist")) {
			fields.add(new OMO_OOField(inedate, this));
		}
		
		if(props.containsKey("date")) {
			String val = props.get("date");
			fields.add(new YMD_OOField(dateProv, inedate, val.contains("s"), getStep(val),val.contains("c"), val.contains("t"), this, val.contains("m")));
		}
		
		if(props.containsKey("datehourmin")) {
			String val = props.get("datehourmin");
			fields.add(new YMD_HMField(inedate, val.contains("s"), getStep(val), val.contains("t"), this, val.contains("m"))); 
		}
		
		if(props.containsKey("hourminsec")) {
			String val = props.get("hourminsec");
			fields.add(new OOO_HMSField(inedate, val.contains("s"), getStep(val), val.contains("t"), this, val.contains("m")));
		}
		
		if(props.containsKey("hourmin")) {
			String val = props.get("hourmin");
			fields.add(new OOO_HMField(inedate, val.contains("s"), getStep(val), val.contains("t"), this, val.contains("m"))); 
		}
		
		if(props.containsKey("hourlist")) {
			fields.add(new OOO_HOField(inedate, this)); 
		}
		
		if(props.containsKey("minlist")) {
			fields.add(new OOO_OMField(inedate, this)); 
		}

		//attach fields
		for ( DateTimeFieldInterface w : fields) {
			panel_main.add(w.asWidget());
			w.asWidget().addStyleName("field");
		}
		
		childValueChanged(false, true);

	}

	private int getStep(String val) {
		try {
			if(val.contains("s(")) {
				int b=val.indexOf('s')+2;
				int e=b;
				while(val.charAt(e)!=')') e++;
				
				return Integer.parseInt(val.substring(b, e));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 1;
	}

	@Override
	public Long getLongValue() {
		boolean isnull=false;

		for ( DateTimeFieldInterface w : fields) {
			isnull=isnull || w.isNull();
		}
		
		if(!isnull) 
			return dateProv.whatMeansTyped(inedate.getLongValue()).getTime();
		else
			return null;
	}
	
	@Override
	public void setLongValue(Long value) {
		if(value==null) {
			if(props.containsKey("now")) {
				inedate.setToNow();
				childValueChanged(false, true);
			} else if(props.containsKey("nowroundtosec")) {
				int rt=Integer.parseInt(props.get("nowroundtosec"));
				inedate.setToNowRoundToSec(rt);
				childValueChanged(false, true);
			} else if(props.containsKey("nowroundtomin")) {
				int rt=Integer.parseInt(props.get("nowroundtomin"));
				inedate.setToNowRoundToSec(rt*60);
				childValueChanged(false, true);
			} else {
				inedate.setDateNull(Precision.YMD_HM);
				childValueChanged(false, true);
			}
			
		} else {
			inedate.initLongValue(dateProv.getDate(value).getTime());
			childValueChanged(false, true);
		}		
	}

	/**
	 * 
	 * @param event - fire this event too, if not null
	 */
	@Override
	public void childValueChanged(boolean fireevent, boolean initialValue) {
		boolean isempty=false;

		for ( DateTimeFieldInterface w : fields) {
			isempty=isempty || w.isEmpty();
		}

		for ( DateTimeFieldInterface w : fields) {
			w.refresh(isempty, initialValue);
		}

		if(fireevent) {
			fireFormWidgetChanged();
		}
	}
	
	@Override
	public boolean handlesLong() {
		return true;
	}

	@Override
	public boolean isFocusable() {
		for ( DateTimeFieldInterface w : fields) {
			if(w.isFocusable()) return true;
		}
		
		return false;
	}

	@Override
	public void setEnabled(boolean enabled) {
		for ( DateTimeFieldInterface w : fields) {
			w.setEnabled(enabled);
		}
	}

	@Override
	public void setFocus(boolean focused) {
		for ( DateTimeFieldInterface w : fields) {
			if(w.isFocusable()) {
				w.setFocus(focused);
				return;
			}
		}
	}

	@Override
	public boolean isReadOnlyWidget() {
		boolean ret=true;
		for (DateTimeFieldInterface w : fields) {
			ret=ret&&w.isInReadOnlyMode();
		}
		
		return ret;
	}

}
