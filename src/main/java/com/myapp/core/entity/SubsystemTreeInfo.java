package com.myapp.core.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.myapp.core.base.entity.CoreInfo;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年4月29日 
 * @system:
 * 子系统树
 *-----------MySong---------------
 */
@Entity
@Table(name="t_base_subsystem")
public class SubsystemTreeInfo extends CoreInfo {
	private long seq;
	private long entitySeq;
	private String entityType;
	private String entityClaz;
	private String entityTable;
	
	@Column(name="fseq")
	public long getSeq() {
		return seq;
	}
	public void setSeq(long seq) {
		this.seq = seq;
	}
	@Column(name="fentitySeq")
	public long getEntitySeq() {
		return entitySeq;
	}
	public void setEntitySeq(long entitySeq) {
		this.entitySeq = entitySeq;
	}
	@Column(name="fentityClaz")
	public String getEntityClaz() {
		return entityClaz;
	}
	public void setEntityClaz(String entityClaz) {
		this.entityClaz = entityClaz;
	}
	@Column(name="fentityTable")
	public String getEntityTable() {
		return entityTable;
	}
	public void setEntityTable(String entityTable) {
		this.entityTable = entityTable;
	}
	@Column(name="fentityType")
	public String getEntityType() {
		return entityType;
	}
	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}
}
