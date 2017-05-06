package com.myapp.core.exception.db;
/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年4月29日 
 * @system:
 *
 *-----------MySong---------------
 */
public class SaveException extends Exception {

	public SaveException(Exception e) {
		super(e);
	}

	public SaveException(String msg) {
		super(msg);
	}
}
