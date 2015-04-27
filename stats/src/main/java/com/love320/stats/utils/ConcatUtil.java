package com.love320.stats.utils;

import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.love320.stats.core.Config;
import com.love320.stats.core.Constant;

public class ConcatUtil {
	
	
	/**
	 * 拼接可存储的key
	 * @param config
	 * @param map
	 * @return
	 */
	public static String key(Config config,Map<String,Object> map){
		List<String> columns = config.getColumns();
		StringBuilder sb = new StringBuilder();
		sb.append(dbkey(config));//加入头,数据库
		for(String sing:columns){
			String[] strs = StringUtils.split(sing, Constant.COLONS);
			if(strs.length == 2){
				sb.append(Constant.POUND).append(sing).append(Constant.COLON).append(map.get(strs[1]));
			}
		}
		return sb.toString();
	}
	
	/**
	 * 生成数据库的key
	 * @param config
	 * @return
	 */
	public static String dbkey(Config config){
		return dbkey(config,config.isMaster());
	}
	
	/**
	 * 非活动的数据库的key
	 * @param config
	 * @return
	 */
	public static String undbkey(Config config){
		return dbkey(config,!config.isMaster());
	}
	
	/**
	 * 生成数据库的key
	 * @param config
	 * @return
	 */
	public static String dbkey(Config config,boolean isMaster){
		return String.format("db#%s#%d", config.getIndex(),isMaster?1:2);
	}

}
