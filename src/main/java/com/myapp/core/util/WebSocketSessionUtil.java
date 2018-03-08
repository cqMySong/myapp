package com.myapp.core.util;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2018年3月4日 
 * @system:
 * WebSocketSession 处理的相关工具类
 *-----------MySong---------------
 */
public class WebSocketSessionUtil {
	public static String WEB_SOCKET_KEY = "_websocket"; //代表request中存放监听的user
	private static Map<String, WebSocketSession> clients = new ConcurrentHashMap<>();

	/** clientKey : 代表的是具体的客户端这边长连接的 userId **/
	public static void add(String clientKey, WebSocketSession session) {
		clients.put(clientKey, session);
	}

	public static WebSocketSession get(String clientKey) {
		return clients.get(clientKey);
	}

	public static void remove(String clientKey) {
		clients.remove(clientKey);
	}

	//广播所有的消息到客户端中
	public static void broadcast(TextMessage message)
			throws Exception {
		Set<String> allUsers = clients.keySet();
		for (String name : allUsers) {
			WebSocketSession session = clients.get(name);
			session.sendMessage(message);
		}
	}
	
	public static void broadcast(String to,String mesg)
			throws Exception {
		WebSocketSession session = clients.get(to);
		if(session!=null){
			session.sendMessage(new TextMessage(mesg));
		}
	}
}
