package com.inepex.ineForm.client.table;

import com.google.inject.assistedinject.Assisted;

public interface DataConnectorFactory {
	IneDataConnector createServerSide(@Assisted String descriptorName);
}
