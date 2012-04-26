package com.inepex.ineForm.shared.types;

import java.io.Serializable;

import com.inepex.ineForm.client.form.widgets.FormWidget;

/**
 * Defines names for different {@link FormWidget} types
 * 
 * 
 * @author istvan
 *
 */
public class FWTypes implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3542896731206796817L;
	
	public final static String p_label="label";
	public final static String p_asDate = "asDate";
	
	public final static FWTypes DONTRENDER = new FWTypes("DONTRENDER");
	public final static FWTypes PLACEFORSOMETHING = new FWTypes("PLACEFORSOMETHING");
	public final static FWTypes TEXTBOX = new FWTypes("TEXTBOX");
	public final static FWTypes CAPTCHA = new FWTypes("CAPTCHA");
	public final static FWTypes PASSWORDTEXTBOX = new FWTypes("PASSWORDTEXTBOX");
	public final static FWTypes CHECKBOX = new FWTypes("CHECKBOX");
	public final static FWTypes TEXTAREA = new FWTypes("TEXTAREA");
	public final static FWTypes NUMBERTEXTBOX = new FWTypes("NUMBERTEXTBOX");
	public final static FWTypes LABEL = new FWTypes("LABEL");
	public final static FWTypes ENUMLABEL = new FWTypes("ENUMLABEL");
	public final static FWTypes DATEBOX = new FWTypes("DATEBOX");
	public static final FWTypes LISTBOX = new FWTypes("LISTBOX");
	public static final FWTypes RELATEDFORM = new FWTypes("RELATEDFORM");
	public static final FWTypes STRINGLIST = new FWTypes("STRINGLIST");
	public static final FWTypes RELATIONLIST = new FWTypes("RELATIONLIST");
	public static final FWTypes ENUMLISTBOX = new FWTypes("ENUMLISTBOX");
	public static final FWTypes RADIOENUMSELECTOR = new FWTypes("RADIOENUMSELECTOR");
	public static final FWTypes CHOOSER = new FWTypes("CHOOSER");
	public static final FWTypes PHONE = new FWTypes("PHONE");
	public static final FWTypes FILEUPLOAD = new FWTypes("FILEUPLOAD");
	public static final FWTypes RADIOBOOL = new FWTypes("RADIOBOOL");
	public static final FWTypes LABELBOOL = new FWTypes("LABELBOOL");
	public static final FWTypes THREEWAYBOOL = new FWTypes("THREEWAYBOOL");
	public static final FWTypes RICHTEXTAREA = new FWTypes("RICHTEXTAREA");
	public static final FWTypes STRINGLISTBOX = new FWTypes("STRINGLISTBOX");
	public static final FWTypes SUGGESTBOX = new FWTypes("SUGGESTBOX");
	public static final FWTypes TABLE = new FWTypes("TABLE");
	public static final FWTypes CUSTOMKVO = new FWTypes("CUSTOMKVO");
	public static final FWTypes CUSTOMKVOREADONLY = new FWTypes("CUSTOMKVOREADONLY");
	
	/**
	 * A label that holds the value and a clickable label after it.
	 */
	public static final FWTypes LABELCLICKABLE = new FWTypes("LABELBUTTON");
	
	private String typeName = "";

	public FWTypes(){
		
	}

	public FWTypes(String typeName){
		this.typeName = typeName;
	}
	
	public String getTypeName() {
		return typeName;
	}	

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		
		if (obj instanceof FWTypes)
			return ((FWTypes)obj).getTypeName().equals(getTypeName());
		
		if (obj instanceof String)
			return ((String)obj).equals(getTypeName());
				
		return super.equals(obj);
	}
	
}
