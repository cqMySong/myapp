package com.myapp.core.base.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.BaseMethodEnum;
import com.myapp.core.model.MyWebContext;
import com.myapp.core.model.WebDataModel;
import com.myapp.core.util.BaseUtil;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年3月12日 
 * @system:
 *  基础controller的核心类
 *-----------MySong---------------
 */
public class BaseController {
	protected final Logger log = LogManager.getLogger(BaseController.class);
	/**
	 * jsp 与 controller 数据交互的 状态码
	 */
	protected static Integer STATUSCODE_INFO = 1; // 提示
	protected static Integer STATUSCODE_ERROR = -1; // 错误类
	protected static Integer STATUSCODE_SUCCESS = 0; // 成功类
	protected static Integer STATUSCODE_NEEDLOGIN = -99; // 需要重新登录
	protected static Integer STATUSCODE_EXCEPTION = -100; // 异常类
	protected static Integer STATUSCODE_WARNING = 100; // 警告类
    protected int statusCode = STATUSCODE_SUCCESS;//默认值
    protected String statusMesg = "";
    protected Object data;
    protected BaseMethodEnum baseMethod;
    protected Object otherData;
    @Autowired
    public HttpServletRequest request;
    
    @Autowired
    public HttpServletResponse response;
    
    private void initModelAndViewParams(Map params){
    	if(params==null) params = new HashMap();
    	params.put("statusCode", this.statusCode);
    	params.put("statusMesg", this.statusMesg);
    	params.put("operate", getBaseMethod().getValue());
    }
    public ModelAndView toPage(String page,Map params){
    	return initMav(page,params);
    }
    
    private ModelAndView initMav(String action,Map params){
    	if(BaseUtil.isEmpty(action)) return null;
    	ModelAndView mav = new ModelAndView();
    	mav.setViewName(action);
    	initModelAndViewParams(params);
    	if(!BaseUtil.isEmpty(params)&&params.size()>0){
			mav.addAllObjects(params);
		}
    	return mav;
    }
    
    public ModelAndView redirectAction(String actionName,Map params){
    	if(BaseUtil.isEmpty(actionName)) return null;
    	if(actionName.charAt(0)!='/'){
    		actionName = "/"+actionName;
    	}
    	return initMav("redirect:"+actionName,params);
    }
    //请求转发
    public ModelAndView forwardAction(String actionName,Map params){
    	if(BaseUtil.isEmpty(actionName)) return null;
    	if(actionName.charAt(0)!='/'){
    		actionName = "/"+actionName;
    	}
    	return initMav("forward:"+actionName,params);
    }
    
    /**
     * 默认 初始化
     */
    public void init(){
    	this.statusCode = STATUSCODE_SUCCESS;
		this.statusMesg = "";
		this.data = null;
		this.baseMethod = BaseMethodEnum.ADDNEW;
    }
    
    public void setMesg(int code,String mesg){
    	this.statusCode = code;
		this.statusMesg = mesg;
    }
    
    public void setInfoMesg(String mesg){
    	setMesg(STATUSCODE_INFO,mesg);
    }
    public void setExceptionMesg(String mesg){
    	setMesg(STATUSCODE_INFO,mesg);
    	this.data = null;
    }
    public void setErrorMesg(String mesg){
    	setMesg(STATUSCODE_ERROR,mesg);
    	this.data = null;
    }
    
    private Object getOtherData() {
		return otherData;
	}
	public void setOtherData(Object otherData) {
		this.otherData = otherData;
	}
	public WebDataModel ajaxModel(){
    	WebDataModel wdm = new WebDataModel();
    	wdm.setData(data);
    	wdm.setStatusMesg(statusMesg);
    	wdm.setStatusCode(statusCode);
    	wdm.setOther(getOtherData());
    	wdm.setOperate(getBaseMethod().getValue());
    	return wdm;
    }
    
    protected String getEntityPk(){
		return "id";
	}
    
	public BaseMethodEnum getBaseMethod() {
		if(baseMethod==null) baseMethod = BaseMethodEnum.BILL;
		return baseMethod;
	}
	public void setBaseMethod(BaseMethodEnum baseMethod) {
		this.baseMethod = baseMethod;
	}
    public String getParamterByRequest(String key){
    	if(BaseUtil.isEmpty(key)) return "";
    	String paramter = request.getParameter(key);
    	if(paramter==null) paramter = "";
    	return paramter;
    }
    
    public MyWebContext getCurWebContext(){
    	Object objCtx = request.getSession().getAttribute("webCtx");
    	if(objCtx!=null&&objCtx instanceof MyWebContext){
    		return (MyWebContext)objCtx;
    	}
    	return null;
    }
    public UserInfo getCurUser(){
    	MyWebContext webCtx = getCurWebContext();
    	if(webCtx!=null) return webCtx.getCurUserInfo();
    	return null;
    }
    
    
}
