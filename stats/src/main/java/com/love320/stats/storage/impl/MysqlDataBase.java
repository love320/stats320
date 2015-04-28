package com.love320.stats.storage.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.love320.stats.storage.IDataBase;
import com.love320.stats.task.Task;

@Service
public class MysqlDataBase implements IDataBase {
	
	private static final Logger logger = LoggerFactory.getLogger(MysqlDataBase.class);

	public boolean write(String table, Map<String, Object> data) {
		logger.info(String.format("写入数据成功!-%s", table));
		return false;
	}

}
