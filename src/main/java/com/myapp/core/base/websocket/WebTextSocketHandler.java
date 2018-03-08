package com.myapp.core.base.websocket;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.WebSocketSessionUtil;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2018年3月4日 
 * @system:
 *
 *-----------MySong---------------
 */
public class WebTextSocketHandler extends TextWebSocketHandler {
	public void afterConnectionEstablished(WebSocketSession session)
			throws Exception {
		System.out.println("connection established");
		WebSocketSessionUtil.add(getUserNameFromSession(session), session);
	}

	public void handleTextMessage(WebSocketSession session, TextMessage message)
			throws Exception {
		System.out.println("receive a message." + message);
		String payload = message.getPayload();
		if(BaseUtil.isNotEmpty(payload)){
			Map payloadMap = JSONObject.parseObject(payload, new HashMap().getClass());
			String from  = (String)payloadMap.get("from");
			String to  = (String)payloadMap.get("to");
			String mesg  = (String)payloadMap.get("mesg");
			WebSocketSessionUtil.broadcast(to, mesg);
		}
	}

	public void handleTransportError(WebSocketSession session,
			Throwable exception) throws Exception {
		WebSocketSessionUtil.remove(getUserNameFromSession(session));
	}

	public void afterConnectionClosed(WebSocketSession session,
			CloseStatus closeStatus) throws Exception {
		System.out.println("conection closed." + closeStatus);

		WebSocketSessionUtil.add(getUserNameFromSession(session), session);
	}

	public boolean supportsPartialMessages() {
		return false;
	}

	private String getUserNameFromSession(WebSocketSession session) {
		Map<String, Object> params = session.getAttributes();
		return params.get(WebSocketSessionUtil.WEB_SOCKET_KEY).toString();
	}
}
