package com.myapp.core.base.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

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
	protected static Integer STATUSCODE_EXCEPTION = -100; // 异常类
	protected static Integer STATUSCODE_WARNING = 100; // 警告类
    protected int statusCode = STATUSCODE_SUCCESS;//默认值
    protected String statusMesg = "";
    protected Object data;
    @Autowired
    public HttpServletRequest request;
    
    public ModelAndView toPage(String page,Map params){
    	return initMav(page,params);
    }
    
    private ModelAndView initMav(String action,Map params){
    	if(BaseUtil.isEmpty(action)) return null;
    	ModelAndView mav = new ModelAndView();
    	mav.setViewName(action);
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
    public void onLoad(){
    	this.statusCode = STATUSCODE_SUCCESS;
		this.statusMesg = "";
		this.data = null;
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
    
    public Object getOtherData(){
    	return null;
    }
    
    public WebDataModel ajaxModel(){
    	WebDataModel wdm = new WebDataModel();
    	wdm.setData(data);
    	wdm.setStatusMesg(statusMesg);
    	wdm.setStatusCode(statusCode);
    	wdm.setOther(getOtherData());
    	return wdm;
    }
    
}
