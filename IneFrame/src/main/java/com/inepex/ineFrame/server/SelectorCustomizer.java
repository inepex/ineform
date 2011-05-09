package com.inepex.ineFrame.server;


public interface SelectorCustomizer<T extends CriteriaSelector<?, ?>> {
	void customizeSelect(T sel);
}
