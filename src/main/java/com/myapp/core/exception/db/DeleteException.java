package com.myapp.core.exception.db;
/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年4月29日 
 * @system:
 *
 *-----------MySong---------------
 */
public class DeleteException extends Exception {
	public DeleteException(Exception e) {
		super(e);
	}
	public DeleteException(String msg) {
		super(msg);
	}
}
