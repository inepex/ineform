package com.inepex.ineom.shared.dispatch;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.inepex.ineom.shared.IFConsts;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum ManipulationTypes implements IsSerializable {
    CREATE_OR_EDIT_REQUEST,
    DELETE,
    UNDELETE,
    REFRESH;

    public static String getValuesAsString() {
        return Arrays.stream(values())
                .map(Enum::name)
                .collect(Collectors.joining(IFConsts.enumValueSplitChar));
    }
}
