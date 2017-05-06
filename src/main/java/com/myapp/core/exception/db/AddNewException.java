package com.myapp.core.exception.db;
/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年4月29日 
 * @system:
 *
 *-----------MySong---------------
 */
public class AddNewException extends Exception {
	public AddNewException(Exception e) {
		super(e);
	}
	public AddNewException(String msg) {
		super(msg);
	}
}
