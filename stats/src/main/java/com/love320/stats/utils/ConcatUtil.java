package com.love320.stats.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
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
			String[] strs = StringUtils.split(sing, Constant.COLON);
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
		return String.format("%s#%d", config.getIndex(),isMaster?1:2);
	}
	
	/**
	 * key信息转map
	 */
	public static Map<String,Object> keyToMap(String key){
		//b8a3-4696-b60e-60135bdc9af2#2#S:mid:ssid1bcacb7f6decdab1#S:appId:APPWALL
		Map<String,Object> dataMap = new HashMap<String, Object>();
		//dataMap.put("dtk", key);//数据key
		String[] keys = StringUtils.split(key,Constant.POUND);
		if(keys.length >= 3){
			for(int i = 0;i<keys.length;i++){
				String[] ss = StringUtils.split(keys[i],Constant.COLON);
				if(ss.length == 3){
					if("S".equals(ss[0])) dataMap.put(ss[1], String.valueOf(ss[2]));
					if("I".equals(ss[0])) dataMap.put(ss[1], Integer.valueOf(ss[2]));
				}else{
					if( i == 0 ) dataMap.put("dbi", keys[i]);
					if( i == 1 ) dataMap.put("dbn", Integer.valueOf(keys[i]));
				}
			}
		}
		return dataMap;
	}

}
