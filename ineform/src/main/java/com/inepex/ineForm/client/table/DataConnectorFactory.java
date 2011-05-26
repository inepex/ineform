package com.inepex.ineForm.client.table;

import com.google.inject.assistedinject.Assisted;

public interface DataConnectorFactory {
	ServerSideDataConnector createServerSide(@Assisted String descriptorName);
}
