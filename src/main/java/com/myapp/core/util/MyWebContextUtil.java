package com.myapp.core.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.myapp.core.base.setting.SystemConstant;
import com.myapp.core.model.MyWebContext;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2018年3月4日 
 * @system:
 * ctx 工具类
 *-----------MySong---------------
 */
public class MyWebContextUtil {
	public static HttpSession getRequestSession(HttpServletRequest request){
		return request.getSession();
	}
	
	public static void initWebContext(HttpServletRequest request,MyWebContext myWebCtx){
		HttpSession session = getRequestSession(request);
		clearWebContext(session);
		session.setAttribute(SystemConstant.WEBCONTEXT_NAME, myWebCtx);
	}
	
	public static void clearWebContext(HttpSession session){
		if(session!=null)
			session.removeAttribute(SystemConstant.WEBCONTEXT_NAME);
	}
	
	public static void clearWebContext(HttpServletRequest request){
		clearWebContext(getRequestSession(request));
	}
	
	
	public static MyWebContext getCurWebContext(HttpServletRequest request){
		return getCurWebContext(request.getSession());
	}
	
	public static MyWebContext getCurWebContext(HttpSession session){
		if(session==null) return null;
		Object objCtx = session.getAttribute(SystemConstant.WEBCONTEXT_NAME);
    	if(objCtx!=null&&objCtx instanceof MyWebContext){
    		return (MyWebContext)objCtx;
    	}
    	return null;
	}
	
}
