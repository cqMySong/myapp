package com.myapp.core.exception.db;
/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2018年1月26日 
 * @system:
 * license验证的异常处理
 *-----------MySong---------------
 */
public class LicenseException extends Exception {
	
	public LicenseException(Exception e) {
		super(e);
	}
	public LicenseException(String msg) {
		super(msg);
	}
}
