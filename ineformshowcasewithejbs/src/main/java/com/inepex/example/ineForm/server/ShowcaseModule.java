package com.inepex.example.ineForm.server;

import com.google.inject.Inject;
import com.inepex.core.command.filter.CommandFilter;
import com.inepex.core.command.proto.ProtoCommandType.PrCommandType;
import com.inepex.core.module.DispatcherClient;
import com.inepex.core.module.GetModuleBaseDataHandler;
import com.inepex.core.module.IneModule;
import com.inepex.core.module.IneModuleCommandRegistry;
import com.inepex.core.module.guice.annotations.TestMode;
import com.inepex.core.module.server.handler.ObjectListCommandHandler;
import com.inepex.core.module.server.handler.ObjectManipulationCommandHandler;
import com.inepex.core.module.server.handler.RelationListCommandHandler;

public class ShowcaseModule extends IneModule {
	ObjectListCommandHandler objectListHandler;
	ObjectManipulationCommandHandler objectManipulationHandler;
	RelationListCommandHandler relationListHandler;	

	@Inject
	public ShowcaseModule(DispatcherClient dispatcherClient,
			GetModuleBaseDataHandler getModuleBaseDataHandler,
			IneModuleCommandRegistry ineModuleCommandRegistry,
			ObjectListCommandHandler objectListHandler,
			ObjectManipulationCommandHandler objectManipulationHandler,
			RelationListCommandHandler relationListHandler,
			@TestMode boolean isTestMode
			) {
		super(dispatcherClient, getModuleBaseDataHandler, ineModuleCommandRegistry, isTestMode);
		this.objectListHandler = objectListHandler;
		this.objectManipulationHandler = objectManipulationHandler;
		this.relationListHandler = relationListHandler;
	}

	@Override
	public String getModuleName() {
		return "ShowcaseModule";
	}

	@Override
	protected void registerHandledCommands(IneModuleCommandRegistry registry) {
		registry.registerHandledHardCommand(new CommandFilter(PrCommandType.OBJECTLISTCOMMAND, 0), objectListHandler);
		registry.registerHandledHardCommand(new CommandFilter(PrCommandType.OBJECTMANIPULATIONCOMMAND, 0), objectManipulationHandler);
		registry.registerHandledHardCommand(new CommandFilter(PrCommandType.RELATIONLISTCOMMAND, 0), relationListHandler);
	}

	@Override
	protected void registerInvokedCommands(IneModuleCommandRegistry registry) {
		registry.registerInvokedHardCommand(new CommandFilter(PrCommandType.OBJECTLISTCOMMAND, 0));
		registry.registerInvokedHardCommand(new CommandFilter(PrCommandType.OBJECTMANIPULATIONCOMMAND, 0));
		registry.registerInvokedHardCommand(new CommandFilter(PrCommandType.RELATIONLISTCOMMAND, 0));
	}

	@Override
	public void onModuleReady() {
	}
}
