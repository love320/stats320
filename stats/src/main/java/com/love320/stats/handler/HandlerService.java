package com.love320.stats.handler;

import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.stereotype.Service;


/**
 * 管理handler服务
 * @author z{user}d
 *
 */
@Service
public class HandlerService {
	
	/**
	 * 处理集合组
	 */
	private CopyOnWriteArrayList<Handler> handlers = new CopyOnWriteArrayList<Handler>();
	
	public CopyOnWriteArrayList<Handler> handlers(){
		return handlers;
	}
	
	/**
	 * 添加处理
	 * @param handler 处理器
	 * @return 真 假
	 */
	public boolean add(Handler handler){
		handlers.add(handler);
		return true;
	}
	
	/**
	 * 移除handler
	 * @param handler 处理器
	 * @return 真 假
	 */
	public boolean remove(Handler handler){
		handlers.remove(handler);
		return true;
	}
	
	/**
	 * 查找handler
	 * @param handler 处理器
	 * @return 真 假
	 */
	public Handler find(Handler handler){
		return find(handler.getIndex());
	}
	
	public Handler find(String index){
		for(Handler sing:handlers) if(index.equals(sing.getIndex())) return sing;
		return null;
	} 
	
	

}
