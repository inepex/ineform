package com.inepex.ineForm.client.form.error;

import java.util.List;

public interface ErrorMessageManagerInterface {
    public void clearErrorMsg();

    public void addErrorMsg(List<String> errorlist);

    public void setVisible(boolean visible);
}
