package com.inepex.ineForm.client.general;

import java.util.List;

public interface ErrorMessageManagerInterface {
	public void clearErrorMsg();
	public void addErrorMsg(List<String> errorlist);
}
