package com.inepex.ineForm.client.form;

import java.util.Map;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.name.Named;

public interface ValueRangeProviderFactory {
	@Named("default") ServerSideValueRangeProvider createDefault();
	@Named("rest") RestValueRangeProvider createRest(@Assisted Map<String, String> descriptorToUrlMapping);
}
