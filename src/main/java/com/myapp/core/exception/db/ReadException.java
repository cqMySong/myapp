package com.myapp.core.exception.db;
/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年4月29日 
 * @system:
 *
 *-----------MySong---------------
 */
public class ReadException extends Exception {
	
	public ReadException(Exception e) {
		super(e);
	}

	public ReadException(String msg) {
		super(msg);
	}
}
