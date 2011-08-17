package com.inepex.ineForm.server;



public interface SelectorCustomizer<T extends CriteriaSelector<?, ?>> {
	void customizeSelect(T sel);
}
