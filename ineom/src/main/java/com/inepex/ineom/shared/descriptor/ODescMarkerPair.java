package com.inepex.ineom.shared.descriptor;

import com.inepex.ineom.shared.descriptor.DescriptorStore.Marker;

public class ODescMarkerPair {
	
	private ObjectDesc objectDesc;
	private Marker marker;

	public ODescMarkerPair() {
	}

	public ODescMarkerPair(ObjectDesc objectDesc, Marker marker) {
		this.objectDesc = objectDesc;
		this.marker = marker;
	}

	public ObjectDesc getObjectDesc() {
		return objectDesc;
	}

	public Marker getMarker() {
		return marker;
	}
}