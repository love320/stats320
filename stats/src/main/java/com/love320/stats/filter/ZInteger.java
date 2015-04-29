package com.love320.stats.filter;


public class ZInteger implements Zbase {
	
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
