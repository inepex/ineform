package com.inepex.example.ineForm.client;

import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.dispatch.client.gin.StandardDispatchModule;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.inject.client.GinModules;
import com.inepex.example.ineForm.client.page.CrudPage;
import com.inepex.example.ineForm.client.page.DtoTestPage;
import com.inepex.example.ineForm.client.page.TesterPage;
import com.inepex.ineForm.client.gin.IneFormGinModule;
import com.inepex.ineom.shared.descriptor.DescriptorStore;

@GinModules({ShowcaseGinModule.class, StandardDispatchModule.class, IneFormGinModule.class})
public interface ShowcaseGinjector extends com.google.gwt.inject.client.Ginjector{
	DispatchAsync getDispatchAsync();
	EventBus getEventBus();
	DescriptorStore getDescriptorStore();
	CrudPage getCrudPage();
	DtoTestPage getDtoTestPage();
	TesterPage getTesterPage();
}
