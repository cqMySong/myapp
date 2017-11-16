package com.myapp.core.base.setting;


public abstract class SystemConstant {
	/**
	 * 分页模型值
	 */
	public static final Integer DEF_PAGE_SIZE = 50;// 默认分页大小
	public static final Integer DEF_PAGE_BEG = 1;// 默认第一页页码
	
	
	public static final String DEF_USERPWD_INIT = "123456";// 用户初始密码

	//request中的Session保存的上下文内容 的key值
	public static String WEBCONTEXT_NAME = "webCtx"; 
	
}
