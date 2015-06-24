package com.love320.stats.filter;

import org.apache.commons.lang3.StringUtils;



public class ZString implements ZBase {
	
	private String name;
	
	public ZString(String name){
		this.name = name;
	}

	public boolean isValid(Object obj) {
		String str = (String)obj;
		return StringUtils.isBlank(str);
	}

	public String name() {
		return name;
	}


}
