package com.love320.stats.utils;

public class SQLUtil {
	
	
	public static String ifTypeToString(Object obj){
		if(obj instanceof String) return String.format("'%s'", obj);
		if(obj instanceof Integer) return String.format("%d", obj);
		if(obj instanceof Long) return String.format("%d", obj);
		return "";
	}

}
