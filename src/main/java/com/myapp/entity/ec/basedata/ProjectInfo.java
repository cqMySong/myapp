package com.myapp.entity.ec.basedata;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.entity.BaseOrgInfo;
import com.myapp.enums.IndustryType;
import com.myapp.enums.ProjectState;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年6月12日 
 * @system:
 * 工程项目树
 *-----------MySong---------------
 */
@Entity
@Table(name="t_ec_project")
public class ProjectInfo extends CoreBaseInfo{
	private ProjectState proState;//项目状态
	private IndustryType industryType;//工程行业
	private String address;//地址
	private String scale;//规模(建筑面积)
	private BigDecimal eavesHeight;//檐高(m)
	private BigDecimal floorHeight;//层高(m)
	private StructTypeInfo structType;//结构类型
	private BigDecimal area;//占地面积
	private String aseismicLevel;//抗震等级
	private String remark;
	private BaseOrgInfo org;//所属组织
	
	@Column(name="findustryType",length=20)
	@Type(type="myEnum",parameters={@Parameter(name="enumClass",value="com.myapp.enums.IndustryType")})
	public IndustryType getIndustryType() {
		return industryType;
	}
	public void setIndustryType(IndustryType industryType) {
		this.industryType = industryType;
	}
	@Column(name="fproState",length=20)
	@Type(type="myEnum",parameters={@Parameter(name="enumClass",value="com.myapp.enums.ProjectState")})
	public ProjectState getProState() {
		return proState;
	}
	public void setProState(ProjectState proState) {
		this.proState = proState;
	}
	@Column(name="faddress",length=200)
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Column(name="fscale",length=100)
	public String getScale() {
		return scale;
	}
	public void setScale(String scale) {
		this.scale = scale;
	}
	@Column(name="feavesHeight")
	public BigDecimal getEavesHeight() {
		return eavesHeight;
	}
	public void setEavesHeight(BigDecimal eavesHeight) {
		this.eavesHeight = eavesHeight;
	}
	@Column(name="ffloorHeight")
	public BigDecimal getFloorHeight() {
		return floorHeight;
	}
	public void setFloorHeight(BigDecimal floorHeight) {
		this.floorHeight = floorHeight;
	}
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "fstructTypeId")
	public StructTypeInfo getStructType() {
		return structType;
	}
	public void setStructType(StructTypeInfo structType) {
		this.structType = structType;
	}
	@Column(name="farea")
	public BigDecimal getArea() {
		return area;
	}
	public void setArea(BigDecimal area) {
		this.area = area;
	}
	@Column(name="faseismicLevel")
	public String getAseismicLevel() {
		return aseismicLevel;
	}
	public void setAseismicLevel(String aseismicLevel) {
		this.aseismicLevel = aseismicLevel;
	}
	@Column(name="fremark",length=200)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "forgId")
	public BaseOrgInfo getOrg() {
		return org;
	}
	public void setOrg(BaseOrgInfo org) {
		this.org = org;
	}
	
}
