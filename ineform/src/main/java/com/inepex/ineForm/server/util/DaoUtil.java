package com.inepex.ineForm.server.util;

import java.util.List;

public class DaoUtil {

	/**
	 * @return a string from ids like this: "(1, 23, 3, 5)"
	 * 		suitable for sql queries "select .... where id in (1, 23, 3, 5)";
	 */
	public static String longIdListForQuery(List<Long> ids) {
		if(ids==null || ids.isEmpty())
			return "()";
		
		StringBuffer sb = new StringBuffer("(");
		for(Long l : ids) {
			if(sb.length()>1)
				sb.append(",");
			sb.append(l);
		}
		sb.append(")");
		
		return sb.toString();
	}
}
