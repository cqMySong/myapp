package com.myapp.core.base.websocket;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import com.myapp.core.model.MyWebContext;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.MyWebContextUtil;
import com.myapp.core.util.WebSocketSessionUtil;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2018年3月4日 
 * @system:
 *
 *-----------MySong---------------
 */
public class MyWebSocketHandshakeInterceptor extends
		HttpSessionHandshakeInterceptor {
	
	public boolean beforeHandshake(ServerHttpRequest serverHttpRequest,
			ServerHttpResponse serverHttpResponse, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {
		System.out.println("hi get request.");
		ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) serverHttpRequest;
		HttpServletRequest request = servletRequest.getServletRequest();
		MyWebContext webCtx = MyWebContextUtil.getCurWebContext(request);
		String userName = "";
		if(webCtx!=null&&BaseUtil.isNotEmpty(webCtx.getUserId())){
			userName = webCtx.getUserId();
		}
		userName = request.getParameter("userName");
		if(BaseUtil.isNotEmpty(userName)){
			attributes.put(WebSocketSessionUtil.WEB_SOCKET_KEY,userName);
			System.out.println("a client userName=" + userName);
		}
		super.beforeHandshake(serverHttpRequest, serverHttpResponse, wsHandler,attributes);
		return true;
	}

	public void afterHandshake(ServerHttpRequest request,
			ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception ex) {
		super.afterHandshake(request, response, wsHandler, ex);
	}
}
