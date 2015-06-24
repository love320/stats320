package com.love320.stats.filter;

/**
 * 过滤器接口
 */
public interface ZBase {
	
	/**
	 * true=无效
	 * false=有值
	 * @param obj 对象
	 * @return 真 假
	 */
	public boolean isValid(Object obj);
	
	public String name();
	
}
