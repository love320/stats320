package com.love320.stats.storage.impl;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.love320.stats.core.Config;
import com.love320.stats.core.Server;
import com.love320.stats.storage.IAfter;


/**
 * 收集信息后处理器
 * @author z{user}d
 *
 */

@Service
public class AfterService implements IAfter {
	
	@Autowired
	private Server server;
	
	private CopyOnWriteArrayList<String> srclist = new CopyOnWriteArrayList<String>();
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	public boolean processor(long millis,Config config,Map<String, Object> data,int value){
		for(String sing :srclist){
			if(sing.equals(config.getIndex())){
				data.put(sing, sing);
				data.put("value", Integer.valueOf(value));
				try {
					String json = objectMapper.writeValueAsString(data);
					server.read(objectMapper.readValue(json,Map.class));
				} catch (JsonGenerationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JsonMappingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		return true;
	}

	public void addSrcIndex(String index){
		srclist.add(index);
	}

	
	
}
