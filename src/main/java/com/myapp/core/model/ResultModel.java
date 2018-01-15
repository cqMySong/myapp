package com.myapp.core.model;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.enums.ResultTypeEnum;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2018年1月14日 
 * @system:
 * 通用的数据结果信息的处理
 *-----------MySong---------------
 */
public class ResultModel {
	private ResultTypeEnum resultType;
	private String mesg;
	public ResultModel(){
		initResult(ResultTypeEnum.SUCCESS,"");
	}
	public ResultModel(ResultTypeEnum resultType){
		initResult(resultType,"");
	}
	public ResultModel(String mesg ){
		initResult(ResultTypeEnum.INFO,mesg);
	}
	public ResultModel(ResultTypeEnum resultType,String mesg){
		initResult(resultType,mesg);
	}
	
	private void initResult(ResultTypeEnum resultType,String mesg){
		this.resultType = resultType;
		this.mesg = mesg;
	}
	
	public ResultTypeEnum getResultType() {
		if(resultType==null) resultType = ResultTypeEnum.SUCCESS;
		return resultType;
	}
	public boolean isSuccess(){
		ResultTypeEnum rte = getResultType();
		return ResultTypeEnum.SUCCESS.equals(rte)||ResultTypeEnum.INFO.equals(rte);
	}
	public void setResultType(ResultTypeEnum resultType) {
		this.resultType = resultType;
	}
	public String getMesg() {
		return mesg;
	}
	public void setMesg(String mesg) {
		this.mesg = mesg;
	}
	public String toString() {
		JSONObject json = new JSONObject();
		json.put("resultType", getResultType().name());
		json.put("mesg", getMesg());
		return json.toJSONString();
	}
	
}
