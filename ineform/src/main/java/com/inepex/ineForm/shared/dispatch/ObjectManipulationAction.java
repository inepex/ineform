package com.inepex.ineForm.shared.dispatch;

import net.customware.gwt.dispatch.shared.Action;

import com.inepex.ineFrame.shared.dispatch.Loggable;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.assistedobject.KeyValueObject;
import com.inepex.ineom.shared.descriptor.CustomKVOObjectDesc;
import com.inepex.ineom.shared.dispatch.ManipulationTypes;
import com.inepex.ineom.shared.dispatch.interfaces.ObjectManipulation;

/**
 * This action can be easily used with ServerSideTableModel that supports
 * general create, modify, delete, undelete functionality
 *
 * @author István Szoboszlai
 *
 */
public class ObjectManipulationAction implements Action<ObjectManipulationActionResult>, Loggable, ObjectManipulation {

	private static final long serialVersionUID = -7036154087856790606L;

	private ManipulationTypes manipulationType;
	private AssistedObject object;
	private Long idToRefresh;
	private CustomKVOObjectDesc[] customOdescs;
	
	public ObjectManipulationAction() {
	}
	
	public ObjectManipulationAction(String descriptorName, Long idToRefresh) {
		this.manipulationType=ManipulationTypes.REFRESH;
		this.idToRefresh=idToRefresh;
		this.object = new KeyValueObject(descriptorName);
	}

	public ObjectManipulationAction(ManipulationTypes manipulationType,
			AssistedObject object) {
		this.manipulationType = manipulationType;
		this.object = object;

	}

	@Override
	public ManipulationTypes getManipulationType() {
		return manipulationType;
	}

	@Override
	public void setManipulationType(ManipulationTypes manipulationType) {
		this.manipulationType = manipulationType;
	}

	@Override
	public AssistedObject getObject() {
		return object;
	}

	@Override
	public void setObject(AssistedObject object) {
		this.object = object;
	}

	@Override
	public String getActionName() {
		return "Manipulate '" + object.getDescriptorName() + "'";
	}

	@Override
	public String getActionParamteres() {
		return this.toString();
	}

	@Override
	public String toString() {
		return "ObjectManipulationAction [manipulationType=" + manipulationType
				+ ", object=" + object + ", idToRefresh=" + idToRefresh + "]";
	}
	
	@Override
	public Long getIdToRefresh() {
		return idToRefresh;
	}
	
	@Override
	public void setIdToRefresh(Long idToRefresh) {
		this.idToRefresh = idToRefresh;
	}

	@Override
	public CustomKVOObjectDesc[] getCustomObjectDescritors() {
		return customOdescs;
	}
	
	@Override
	public void setCustomOdescs(CustomKVOObjectDesc[] customOdescs) {
		this.customOdescs = customOdescs;
	}
}
