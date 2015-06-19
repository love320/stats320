package com.love320.stats.core;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.love320.stats.handler.Handler;
import com.love320.stats.handler.HandlerService;
import com.love320.stats.utils.CommonUtil;


/**
 * 中心服务
 *
 */

@Service
public class Server {
	
	/**
	 * handler服务.处理
	 */
	@Autowired
	private HandlerService handlerService;

	public boolean read(Map<String,Object> map){
		map.put(Constant.KEYMSG, UUID.randomUUID().toString());//给每一条信息打上标注
		for(Handler handler:handlerService.handlers()) handler.exe(CommonUtil.copyMap(map));
		return true;
	}

}
