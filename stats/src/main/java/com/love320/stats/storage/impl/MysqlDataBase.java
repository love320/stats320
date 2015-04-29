package com.love320.stats.storage.impl;

import java.util.Calendar;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Resource;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.love320.stats.storage.IDataBase;
import com.love320.stats.utils.SQLUtil;

@Service
public class MysqlDataBase implements IDataBase {
	
	private static final Logger logger = LoggerFactory.getLogger(MysqlDataBase.class);

	@Resource(name="jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	
	public boolean write(String table, Map<String, Object> data,int value) {
		//logger.info(String.format("写入数据成功!-%s", table));
		Calendar calendar = Calendar.getInstance();
		data.put("date", DateFormatUtils.ISO_DATE_FORMAT.format(calendar));
		data.put("hour", Integer.valueOf(calendar.get(Calendar.HOUR_OF_DAY)));
		data.put("minute", Integer.valueOf(calendar.get(Calendar.MINUTE)));
		data.put("value", value);
		
		StringBuffer names = new StringBuffer();
		StringBuffer values = new StringBuffer();
		for(Entry<String, Object> sing:data.entrySet()){
			names.append((names.length() == 0 ?"":",")+sing.getKey());
			values.append((values.length() == 0 ?"":",")+SQLUtil.ifTypeToString(sing.getValue()));
		}
		
		String insertSQL = String.format("insert into %s (%s) VALUES (%s) ", table,names,values);
		logger.info(insertSQL);
		jdbcTemplate.update(insertSQL);
		return false;
	}

}
