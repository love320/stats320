package com.love320.stats.storage.impl;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
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
public class AfterService implements IAfter ,Runnable{
	
	@Autowired
	private Server server;
	
	private CopyOnWriteArrayList<String> srclist = new CopyOnWriteArrayList<String>();
	
	private ObjectMapper objectMapper = new ObjectMapper();

    private static ConcurrentLinkedQueue<Map<String, Object>> dataSS = new ConcurrentLinkedQueue<Map<String, Object>>();
	
	public boolean processor(long millis,Config config,Map<String, Object> data,int value){
		for(String sing :srclist){
			if(sing.equals(config.getIndex())){
				data.put(sing, sing);
				data.put("value", Integer.valueOf(value));
                dataSS.offer(data);
			}
		}
		return true;
	}

    public void run(){
        while(true){
                boolean isGo = true;
                while (isGo) {
                    Map<String, Object> data = null;
                    synchronized(dataSS) {
                        isGo = !dataSS.isEmpty();
                        if(isGo == true) data = dataSS.poll();
                    }
                    if(data != null) server.read(data);
                }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

	public void addSrcIndex(String index){
		srclist.add(index);
	}

	
	
}
