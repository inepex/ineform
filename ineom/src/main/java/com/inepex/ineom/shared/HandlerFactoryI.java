package com.inepex.ineom.shared;

import com.inepex.ineom.shared.assistedobject.AssistedObject;

public interface HandlerFactoryI<T extends AssistedObjectHandler> {

    T createHandler();

    T createHandler(AssistedObject assistedObject);
}
