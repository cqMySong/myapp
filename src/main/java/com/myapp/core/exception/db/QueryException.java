package com.myapp.core.exception.db;
/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年4月29日 
 * @system:
 *
 *-----------MySong---------------
 */
public class QueryException extends Exception {
	public QueryException(Exception e) {
		super(e);
	}
	public QueryException(String msg) {
		super(msg);
	}
}
