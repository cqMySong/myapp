package com.myapp.core.base.listener;

import java.util.Date;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.myapp.core.model.MyWebContext;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.DateUtil;
import com.myapp.core.util.MyWebContextUtil;
import com.myapp.core.util.WebSocketSessionUtil;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2018年3月4日 
 * @system:
 * 系统seesion 监听
 *-----------MySong---------------
 */
public class MyHttpSessionListener implements HttpSessionListener {

	public void sessionCreated(HttpSessionEvent se) {
		
	}

	public void sessionDestroyed(HttpSessionEvent se) {
		HttpSession session = se.getSession();
		System.out.println("session is destoryed !!!!");
		MyWebContext webCtx = MyWebContextUtil.getCurWebContext(session);
		if(webCtx!=null){
			String userId = webCtx.getUserId();
			userId = "a";
			try {
				WebSocketSessionUtil.broadcast("b", "a已经下线了!");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			if(BaseUtil.isNotEmpty(userId)){
				WebSocketSessionUtil.remove(userId);
			}
		}
	}
}
