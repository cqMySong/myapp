package com.myapp.core.exception.db;
/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年4月29日 
 * @system:
 *-----------MySong---------------
 */
public class UpdateException extends Exception {

	public UpdateException(Exception e) {
		super(e);
	}

	public UpdateException(String msg) {
		super(msg);
	}
}
