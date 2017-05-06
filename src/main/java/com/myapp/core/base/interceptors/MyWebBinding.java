package com.myapp.core.base.interceptors;

import java.util.Date;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年5月1日 
 * @system:
 *
 *-----------MySong---------------
 */
public class MyWebBinding implements WebBindingInitializer {

	public void initBinder(WebDataBinder binder, WebRequest request) {
		//自定义的PropertyEditorSupport
		binder.registerCustomEditor(Date.class, new DateConvertEditor());
	}

}
