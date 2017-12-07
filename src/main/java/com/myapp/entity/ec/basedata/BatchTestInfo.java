package com.myapp.entity.ec.basedata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.myapp.core.base.entity.CoreBaseDataInfo;
import com.myapp.enums.ec.TestGroup;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年10月23日 
 * @system: 技术负责人：检验批信息
 *-----------MySong---------------
 */
@Entity
@Table(name="t_ec_bathchtest")
public class BatchTestInfo extends CoreBaseDataInfo {
	private ProBaseWbsInfo proBaseWbs;//检验批分解结构
	private String content;//检验批划分标准
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "fprowbsId")
	public ProBaseWbsInfo getProBaseWbs() {
		return proBaseWbs;
	}
	public void setProBaseWbs(ProBaseWbsInfo proBaseWbs) {
		this.proBaseWbs = proBaseWbs;
	}
	
	@Column(name="fcontent",length=500)
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	
	
	
}
