package com.myapp.core.base.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年5月29日 
 * @system: 基础分录信息模型
 * 基础分录集合数据模型
 *-----------MySong---------------
 */
@MappedSuperclass
public class CoreBaseEntryInfo<T> extends CoreInfo {
	private T parent;
	private Integer seq;
	           
	@ManyToOne
	@JoinColumn(name="fprentid",updatable=false)  
	public T getParent() {
		return parent;
	}
	public void setParent(T parent) {
		this.parent = parent;
	}
	@Column(name="fseq",nullable=false)
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	
}
