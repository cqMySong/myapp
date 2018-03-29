package com.myapp.core.base.interceptors;

import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.AuthorAnn;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.annotation.PermissionItemAnn;
import com.myapp.core.base.setting.SystemConstant;
import com.myapp.core.license.LicenseInfo;
import com.myapp.core.license.SystemTool;
import com.myapp.core.model.MyWebContext;
import com.myapp.core.service.base.BaseService;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.DateUtil;

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
			String appRoot = request.getContextPath();//   /myapp
			HandlerMethod hm = (HandlerMethod) object;
			Method method = hm.getMethod();
			String toUrl = "";
			String mesg = "";
			String mesgTitle = "";
			boolean isNeedLogin = true;
			Boolean doPermission = true;
			String uri = request.getRequestURI();
			//所有验证前 先验证license是否存在切合法
			boolean licCheck = true;
			ServletContext serverContext = request.getServletContext();
			
			if(serverContext!=null&&!uri.equals(appRoot+"/license/show")){
				Object licObj = serverContext.getAttribute(SystemConstant.LICENSE_KEY);
				if(licObj!=null&&licObj instanceof LicenseInfo){
					LicenseInfo licInfo = (LicenseInfo) licObj;
					Date curDate = new Date();
					Date begDate = licInfo.getBegDate();
					Date endDate = licInfo.getEndDate();
					if(begDate!=null&&endDate!=null
							&&curDate.after(begDate)&&curDate.before(endDate)){
						licCheck = true;
						String licMcode = licInfo.getMachineCode();
						if(BaseUtil.isEmpty(licMcode)||!SystemTool.getMachineCode().equals(licMcode)){
							licCheck = false;
							mesg = "授权许可文件非法或机器特征码不一致！";
						}
					}else{
						licCheck = false;
						mesg = "系统授权未在有效时间内("+DateUtil.formatDate(begDate)+"-"+DateUtil.formatDate(endDate)+")使用在！";
					}
				}else{
					licCheck = false;
					mesg = "授权许可验证失败:"+serverContext.getAttribute(SystemConstant.LICENSEVERIFY_KEY);
				}
			}
			if(licCheck){
				AuthorAnn nla = method.getAnnotation(AuthorAnn.class);
				MyWebContext myContext = null;
				Object webCtxObj = request.getSession().getAttribute("webCtx");
				if(webCtxObj!=null&&webCtxObj instanceof MyWebContext){
					myContext = (MyWebContext)webCtxObj;
				}
				if(nla!=null){
					isNeedLogin = nla.doLongin();
					doPermission = nla.doPermission();
				}
				if(isNeedLogin){//需要登录
					//检查session 是否有对应的用户信息
					if(myContext==null){
						toGo = false;
						doPermission=null;
					}else{
						toGo = myContext!=null&&!BaseUtil.isEmpty(myContext.getUserId());
					}
					if(!toGo){
						toUrl = appRoot+"/main/login";
						mesg = "用户未登录或者登录超时，请重新登录!";
						mesgTitle = "用户检测异常!";
					}
				}
				if(myContext!=null&&toGo){
					if(myContext.getAdmin()&&myContext.getSysUser()){//系统内置的管理员不需要权限验证
						doPermission = false;
					}
				}
				if(doPermission!=null&&doPermission&&toGo){
					if(myContext==null){
						toGo = false;
					}else{
						Map<String, Map<String, String>> perms =  myContext.getPermission();
						// 	uri = /myapp/base/permissionAssign/hasAssign
						if(perms!=null&&perms.size()>0&&!BaseUtil.isEmpty(uri)&&uri.length()>appRoot.length()){
							uri = uri.substring(appRoot.length()+1);
							System.out.println("URI = "+uri);
							toGo = perms.containsKey(uri)&&isNeedLogin;
						}
					}
					if(!toGo){
						mesg = "用户未操作此功能权限，无权访问!";
						mesgTitle = "用户权限验证失败!";
					}
				}
			}else{
				toGo = false;
				mesgTitle = "系统授权许可验证";
				doPermission = Boolean.TRUE;
			}
			
			if(!toGo){
				PrintWriter pw = response.getWriter();
				boolean isAjaxRequest = (request.getHeader("X-Requested-With") != null  
					    && "XMLHttpRequest".equals(request.getHeader("X-Requested-With").toString()));
				
				if(isAjaxRequest){
					JSONObject json = new JSONObject();
					json.put("statusCode", -99);
					json.put("statusMesg",mesg);
					json.put("toUrl", toUrl);
					json.put("title", mesgTitle);
					pw.write(json.toJSONString());
				}else{
					StringBuffer sb = new StringBuffer();
					sb.append("<script src='"+appRoot+"/assets/lib/jquery/jquery.js'></script>");
					sb.append("<script src='"+appRoot+"/assets/lib/layer/layer.js'></script>");
					if(doPermission!=null&&doPermission){
						sb.append("<link rel='stylesheet' href='"+appRoot+"/assets/css/quirk.css'/>");
					}else{
						sb.append("<script type='text/javascript'>");
						sb.append("parent.layer.open({title:'"+mesgTitle+"',content:'"+mesg+"',icon:5,end:function(){");
							if(!BaseUtil.isEmpty(toUrl)){
								sb.append("window.open('"+toUrl+"','_top');");
							}
						sb.append("}});");
						sb.append("</script>");
					}
					sb.append("<body class='contenstpanel' style='padding:5px;height:100%;width:100%;'>");
					if(doPermission!=null&&doPermission){
						sb.append("<div class='row'>");
							sb.append("<div class='col-md-12'>");
								sb.append("<div class='panel panel-danger'>");
									sb.append("<div class='panel-heading'><h3 class='panel-title'>");
										sb.append(mesgTitle);
									sb.append("</h3></div>");
									sb.append("<div class='panel-body'>");
										sb.append(mesg);
									sb.append("</div>");
								sb.append("</div>");
							sb.append("</div>");
						sb.append("</div>");
					}
//						sb.append(mesgTitle+"："+mesg);
					sb.append("</body>");
					pw.write(sb.toString());
				}
			}
			PermissionItemAnn pitem = hm.getMethod().getAnnotation(PermissionItemAnn.class);
			PermissionAnn clazPi = hm.getBean().getClass().getAnnotation(PermissionAnn.class);
			if(pitem!=null&&clazPi!=null){
				System.out.println(pitem.name()+"  -- "+pitem.number()+ " --"+pitem.type().name());
				System.out.println(clazPi.name()+"  -- "+clazPi.number());
			}
		}
		request.setAttribute("appRoot",request.getContextPath());
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
