package com.love320.stats.listener;

import java.io.IOException;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.love320.stats.core.Server;

public class MQJsonData {
	
	
	@Autowired
	private Server server;
	
	private ObjectMapper mapper = new ObjectMapper();  
	
	public void onMessage(String message) {
		try {
			Map<String,Object> map = mapper.readValue(message,Map.class);
			server.read(map);//转成map
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
