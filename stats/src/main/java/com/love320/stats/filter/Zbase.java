package com.love320.stats.filter;

public interface Zbase {
	
	/**
	 * true=无效
	 * false=有值
	 * @param obj
	 * @return
	 */
	public boolean isValid(Object obj);
	
	public String name();
	
}
