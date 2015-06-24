package com.love320.stats.filter;

/**
 * 整形过滤器
 */
public class ZInteger implements ZBase {
	
	private String name;
	
	public ZInteger(String name){
		this.name = name;
	}

	public boolean isValid(Object obj) {
		return obj == null;
	}

	public String name() {
		return name;
	}


}
