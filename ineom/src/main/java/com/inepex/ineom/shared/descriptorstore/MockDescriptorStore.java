package com.inepex.ineom.shared.descriptorstore;

import java.util.List;

import com.inepex.ineom.shared.descriptor.DescriptorBase;
import com.inepex.ineom.shared.descriptor.ObjectDesc;
import com.inepex.ineom.shared.descriptor.fdesc.FDesc;

public abstract class MockDescriptorStore extends DescriptorStore{

	@Override
	public String getOdNames(Decoration decor) {
		return null;
	}

	@Override
	public <D extends DescriptorBase> D getNamedTypedDesc(String objDescName,
			String namedDescName, Class<D> clazz) {
		return null;
	}

	@Override
	public <D extends DescriptorBase> void addNamedTypedDesc(Marker marker,
			String objDescName, String namedDescName, D namedDesc) {
	}

	@Override
	public void registerDescriptors(Marker marker, ObjectDesc descriptor,
			DescriptorBase... defaultDescriptors) {

	}

	@Override
	public void registerObjectDesc(Marker marker, ObjectDesc descriptor) {
	}

	@Override
	public FDesc getRelatedFieldDescrMultiLevel(ObjectDesc baseOD,
			List<String> path) {
		return null;
	}
}
