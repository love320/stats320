package com.love320.stats.storage.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.love320.stats.storage.IDataBase;

@Service
public class MysqlDataBase implements IDataBase {

	public boolean write(String table, Map<String, Object> data) {
		System.out.println(String.format("写入数据成功!-%s", table));
		return false;
	}

}
