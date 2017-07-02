package com.myapp.core.base.interceptors;

import java.io.PrintWriter;
import java.lang.reflect.Method;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.NeedLoginAnn;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.annotation.PermissionItemAnn;
import com.myapp.core.entity.BaseOrgInfo;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.model.MyWebContent;
import com.myapp.core.service.base.BaseService;
import com.myapp.core.util.BaseUtil;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年4月30日 
 * @system:
 *
 *-----------MySong---------------
 */
public class BaseInterceptor implements HandlerInterceptor {
	private static final Logger log = LogManager.getLogger(BaseInterceptor.class);
	@Resource
	public BaseService baseService;
	/**
	 * 在controller前拦截
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8"); 
		boolean toGo = true;
		if(object instanceof HandlerMethod){
			HandlerMethod hm = (HandlerMethod) object;
			Method method = hm.getMethod();
			boolean isNeedLogin = true;
			NeedLoginAnn nla = method.getAnnotation(NeedLoginAnn.class);
			if(nla!=null){
				isNeedLogin = nla.doLongin();
			}
			if(isNeedLogin){//需要登录
				//检查session 是否有对应的用户信息
				Object webCtxObj = request.getSession().getAttribute("webCtx");
				if(webCtxObj!=null&&webCtxObj instanceof MyWebContent){
					toGo = !BaseUtil.isEmpty(((MyWebContent)webCtxObj).getUserId());
				}else{
					toGo = false;
				}
				if(!toGo){
					PrintWriter pw = response.getWriter();
					boolean isAjaxRequest = (request.getHeader("X-Requested-With") != null  
						    && "XMLHttpRequest".equals(request.getHeader("X-Requested-With").toString()));
					String appRoot = request.getContextPath();
					String loginUrl = appRoot+"/main/login";
					String logInMesg = "用户未登录或者登录超时，请重新登录!";
					String logTitle = "用户检测异常!";
					if(isAjaxRequest){
						JSONObject json = new JSONObject();
						json.put("statusCode", -99);
						json.put("statusMesg",logInMesg);
						json.put("loginUrl", loginUrl);
						json.put("title", logTitle);
						pw.write(json.toJSONString());
					}else{
						StringBuffer sb = new StringBuffer();
						sb.append("<script src='"+appRoot+"/assets/lib/jquery/jquery.js'></script>");
						sb.append("<script src='"+appRoot+"/assets/lib/layer/layer.js'></script>");
						sb.append("<script type='text/javascript'>");
							sb.append("parent.layer.open({title:'"+logTitle+"',content:'"+logInMesg+"',icon:5,end:function(){");
								sb.append("window.open('"+loginUrl+"','_top');");
							sb.append("}});");
						sb.append("</script>");
						sb.append("<body style='height:500px;width:400px;'> &nbsp;");
						sb.append("</body>");
						pw.write(sb.toString());
					}
				}
			}
			PermissionItemAnn pitem = hm.getMethod().getAnnotation(PermissionItemAnn.class);
			PermissionAnn clazPi = hm.getBean().getClass().getAnnotation(PermissionAnn.class);
			baseService.newServicInstance(UserInfo.class).getEntityCollection();
			if(pitem!=null&&clazPi!=null){
				System.out.println(pitem.name()+"  -- "+pitem.number()+ " --"+pitem.type().name());
				System.out.println(clazPi.name()+"  -- "+clazPi.number());
			}
		}
		request.setAttribute("myKey", "我的内容在这里");
		log.info("BaseInterceptor:preHandle");
		return toGo;
	}
	

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView modelAndView) throws Exception {
		log.info("BaseInterceptor:postHandle");
	}
	
	/**
	 * 在controller后拦截
	 */
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception exception) throws Exception {
		log.info("BaseInterceptor:afterCompletion");
	}
}
