package com.inepex.ineForm.client.table;

import com.google.inject.assistedinject.Assisted;

public interface DataConnectorFactory {
    ServerSideDataConnector createServerSide(@Assisted String descriptorName);

    RestDataConnector createRest(
        @Assisted("descriptorName") String descriptorName,
        @Assisted("getUrl") String getUrl,
        @Assisted("newUrl") String newUrl,
        @Assisted("modifyUrl") String modifyUrl,
        @Assisted("deleteUrl") String deleteUrl,
        @Assisted boolean serializeId);
}
