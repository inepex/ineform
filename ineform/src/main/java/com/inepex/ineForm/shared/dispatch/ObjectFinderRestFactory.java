package com.inepex.ineForm.shared.dispatch;

import java.util.Map;

import com.google.inject.assistedinject.Assisted;
import com.inepex.ineForm.shared.dispatch.ObjectFinderRest.ResultExtractor;
import com.inepex.ineFrame.client.kvo.KvoJsonParser.ResultObjectExtractor;

public interface ObjectFinderRestFactory {

	ObjectFinderRest create(@Assisted("descriptorName") String descriptorName
			, @Assisted("url") String url
			, @Assisted ResultExtractor resultExtractor
			, @Assisted Map<String, ResultObjectExtractor> customResultExtractors);
	
}
