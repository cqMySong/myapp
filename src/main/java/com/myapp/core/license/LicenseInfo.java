package com.myapp.core.license;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.base.setting.SystemConstant;
import com.myapp.core.util.BaseUtil;

/**
 * -----------MySong--------------- 
 * ©MySong基础框架搭建
 * @author mySong @date 2018年1月23日
 * @system:
 *  license 模型构建
 *-----------MySong---------------
 */
public class LicenseInfo implements Serializable {
	private String appName;
	private String companey;
	private Date begDate;
	private Date endDate;
	private String version = SystemConstant.APP_VERSION;
	private String productID;// 产品唯一序号
	private List<ModelItemInfo> models;
	private LicenseType type = LicenseType.BETA;
	private String machineCode;//机器码
	private Map<String,ModelItemInfo> modelsMap;
	LicenseInfo(String companey,String productID){
		this.companey = companey;
		this.productID = productID;
	}
	
	LicenseInfo(String companey,String productID,Date begDate,Date endDate){
		this.companey = companey;
		this.productID = productID;
		this.begDate = begDate;
		this.endDate = endDate;
	}
	
	public String getCompaney() {
		return companey;
	}
	public void setCompaney(String companey) {
		this.companey = companey;
	}
	public Date getBegDate() {
		return begDate;
	}
	public void setBegDate(Date begDate) {
		this.begDate = begDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getProductID() {
		return productID;
	}
	public void setProductID(String productID) {
		this.productID = productID;
	}
	public List<ModelItemInfo> getModels() {
		if(models==null) models = new ArrayList<ModelItemInfo>();
		return models;
	}
	
	public void addModels(ModelItemInfo modelInfo){
		if(modelInfo!=null){
			getModels().add(modelInfo);
			String entClas = modelInfo.getEntityClaz();
			if(!BaseUtil.isEmpty(entClas)){
				getModelsMap().put(entClas, modelInfo);
			}
		}
	}
	
	public String getVersion() {
		return version;
	}

	public LicenseType getType() {
		return type;
	}

	public void setType(LicenseType type) {
		this.type = type;
	}
	
	public String toJosnString(){
		return JSONObject.toJSON(this).toString();
	}

	public String getMachineCode() {
		return machineCode;
	}

	public void setMachineCode(String machineCode) {
		this.machineCode = machineCode;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public Map<String, ModelItemInfo> getModelsMap() {
		if(modelsMap==null) modelsMap = new HashMap<String, ModelItemInfo>();
		return modelsMap;
	}
}
