package com.love320.stats;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.love320.stats.utils.CommonUtil;

import junit.framework.TestCase;

public class MqMsgTest extends TestCase {
	
	
	public void commonUtilTest(){
		for(int i = 0 ;i < 200;i++) System.out.println(CommonUtil.generateShortUuid());
	}
	
	public void sendTest(){
		ApplicationContext  context = new ClassPathXmlApplicationContext("classpath:spring-root.xml");
		JmsTemplate jmsTemplate = (JmsTemplate) context.getBean("sendjmstemplate");
		for(int i=0 ;i<1500;i++) sendMq(jmsTemplate);
		
	}
	
	private boolean sendMq(JmsTemplate jmsTemplate){
		final ObjectMapper objectMapper = new ObjectMapper();
		jmsTemplate.send(new MessageCreator(){
			public Message createMessage(Session session) throws JMSException {
				Map sing = new HashMap<String,Object>();
				sing.put("adGroupId", 0);
				sing.put("adType", 0);
				sing.put("appId", "APPWALL");
				sing.put("calcCount", 1);
				sing.put("channel", "LK5_LK9");
				sing.put("channelId", "LK5");
				sing.put("city", "");
				sing.put("createTime", 0);
				sing.put("machType", "ZTE 100");
				sing.put("mid", "ssid1bcacb7f6decdab1");
				sing.put("netType", "");
				sing.put("operator", "default");
				sing.put("province", "");
				sing.put("sdkVersion",1);
				sing.put("subChannelId", "LK9");
				sing.put("tMobile", "");
				String msg = null;
				try {
					msg = objectMapper.writeValueAsString(sing);
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
				return session.createTextMessage(msg);
			}
		});
		return true;
	}

}
