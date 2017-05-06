package com.myapp.core.exception.login;
/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年4月29日 
 * @system:
 *
 *-----------MySong---------------
 */
public class UserLoginException extends Exception{
	public UserLoginException(Exception e){
		super(e);
	}
	public UserLoginException(String msg){
		super(msg);
	}

}
