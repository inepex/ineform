package com.inepex.inei18n.util;

import com.inepex.inei18n.util.CsvMerge;


public class CsvMergeApplicationFileIOTest {

	public static void main(String[] args) {
//		CsvMerge.merge(
//				"/cuccos/prog/ew_mvm/IneForm/utilTest/com/inepex/ineForm/i18n/target.csv", 7, ";",
//				"/cuccos/prog/ew_mvm/IneForm/utilTest/com/inepex/ineForm/i18n/patch.csv", 3, ";",
//				"/cuccos/prog/ew_mvm/IneForm/utilTest/com/inepex/ineForm/i18n/target.csv", ";",
//				"dummy");
		
		CsvMerge.merge(
			"/cuccos/prog/ew_mvm/PowerforumEJB/ejbModule/com/kirowski/mvm/shared/i18n/PfI18n.csv", 2, ";",
			"/home/sebi/Asztal/loc20110201", 2, ";",
			"/cuccos/prog/ew_mvm/PowerforumEJB/ejbModule/com/kirowski/mvm/shared/i18n/PfI18n.csv", ";",
			"\"\"");
	}
}
