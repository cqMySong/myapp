package com.myapp.core.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.sql.JoinType;

import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.DateUtil;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年5月20日 
 * @system:
 *
 *-----------MySong---------------
 */
public class ColumnModel {
	private String name;//字段名
	private DataTypeEnum dataType; // 数据类型
	private boolean isNotNull = false; //control 验证的时候 校验不能为空
	private List<ColumnModel> cols; //分录集合列
	private String format; //格式化 如：日期，f7的显示值 
	private Class claz;//f7对象 或者 分录对象 对应的entity
	private String alias; //查询的字段别名
	private JoinType joinType;// 数据查询的时候 采用的级联类型
	private boolean queryDisplay = true;//f7查询的时候是否显示的列
	private boolean queryFilter = true;//f7查询是否可用作过滤
	private String alias_zh ;//f7查询的列中文名称  针对f7 多字段的情况，请用,分隔开
	
	public ColumnModel(String name){
		this.name = name;
		this.dataType = DataTypeEnum.STRING;
		this.claz = String.class;
		this.alias = name;
	}
	public ColumnModel(String name,String ailas){
		this.name = name;
		this.dataType = DataTypeEnum.STRING;
		this.claz = String.class;
		this.alias = ailas;
	}

	public ColumnModel(String name,DataTypeEnum dataType){
		this.name = name;
		this.dataType = dataType;
		if(DataTypeEnum.DATE.equals(dataType)){
			this.format = DateUtil.DATEFORMT_YMD;
			this.claz = Date.class;
		}else if(DataTypeEnum.F7.equals(dataType)
				||DataTypeEnum.MUTILF7.equals(dataType)){
			this.format = "id,name";
		}
	}
	
	public ColumnModel(String name,DataTypeEnum dataType,Class claz){
		this.name = name;
		this.dataType = dataType;
		this.claz = claz;
		if(DataTypeEnum.F7.equals(dataType)
				||DataTypeEnum.MUTILF7.equals(dataType)){
			this.format = "id,name";
		}
	}
	public ColumnModel(String name,DataTypeEnum dataType,Class claz,String format){
		this.name = name;
		this.dataType = dataType;
		this.claz = claz;
		this.format = format;
	}
	
	public ColumnModel(String name,DataTypeEnum dataType,String format){
		this.name = name;
		this.dataType = dataType;
		this.format = format;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public DataTypeEnum getDataType() {
		if(dataType==null) dataType = DataTypeEnum.STRING;
		return dataType;
	}

	public void setDataType(DataTypeEnum dataType) {
		
		this.dataType = dataType;
	}

	public boolean isNotNull() {
		return isNotNull;
	}
	public void setNotNull(boolean isNotNull) {
		this.isNotNull = isNotNull;
	}
	public List<ColumnModel> getCols() {
		if(cols==null) cols = new ArrayList<ColumnModel>();
		return cols;
	}

	public String getFormat() {
		if(BaseUtil.isEmpty(format)){
			if(DataTypeEnum.DATE.equals(getDataType())){
				format = DateUtil.DATEFORMT_YMD;
			}if(DataTypeEnum.DATETIME.equals(getDataType())){
				format = DateUtil.DATEFORMT_YMDHMS;
			}else if(DataTypeEnum.F7.equals(dataType)||DataTypeEnum.MUTILF7.equals(dataType)){
				this.format = "id,name";
			}
		}
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}

	public Class getClaz() {
		return claz;
	}

	public void setClaz(Class claz) {
		this.claz = claz;
	}
	public String getAlias() {
		if(BaseUtil.isEmpty(alias)) alias = this.name;
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public JoinType getJoinType() {
		if(joinType==null) joinType = JoinType.LEFT_OUTER_JOIN;
		return joinType;
	}
	public void setJoinType(JoinType joinType) {
		this.joinType = joinType;
	}
	public boolean isQueryDisplay() {
		return queryDisplay;
	}
	public void setQueryDisplay(boolean queryDisplay) {
		this.queryDisplay = queryDisplay;
	}
	public String getAlias_zh() {
		if(BaseUtil.isEmpty(alias_zh)){
			if(DataTypeEnum.F7.equals(dataType)
					||DataTypeEnum.MUTILF7.equals(dataType)){
				alias_zh = "id,名称";
			}else{
				alias_zh = getAlias(); 
			}
		}
		return alias_zh;
	}
	public void setAlias_zh(String alias_zh) {
		this.alias_zh = alias_zh;
	}
	public boolean isQueryFilter() {
		return queryFilter;
	}
	public void setQueryFilter(boolean queryFilter) {
		this.queryFilter = queryFilter;
	}
	
}
