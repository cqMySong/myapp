package com.myapp.core.base.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

import com.myapp.core.util.BaseUtil;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年5月1日 
 * @system:
 * 树形结构模型
 *-----------MySong---------------
 */
@MappedSuperclass
public class CoreBaseTreeInfo<T> extends CoreBaseInfo {
	private T parent;
	private Set<T> children = new HashSet<T>();   
	private Integer level;
	private String longNumber;
	private String displayName;
	private boolean isLeaf;
	
	@ManyToOne   
	@JoinColumn(name="fprentid")  
	public T getParent() {
		return parent;
	}
	public void setParent(T t) {
		this.parent = t;
	}
	@OneToMany(mappedBy="parent",fetch=FetchType.EAGER)
	public Set<T> getChildren() {
		return children;
	}
	public void setChildren(Set<T> ts) {
		this.children = ts;
	}
	
	@Column(name="flevel",nullable=false)
	public Integer getLevel() {
		if(level==null){
			if(this.getParent()!=null){
				level = ((CoreBaseTreeInfo)this.getParent()).getLevel();
				if(level!=null){
					level = level+1;
				}
			}
			if(level==null) {
				level = 1;
			}
		}
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	
	@Column(name="flongnumber",nullable=false)
	public String getLongNumber() {
		String curNumber = getNumber();
		if(BaseUtil.isEmpty(curNumber)) curNumber = "01";
		if(this.getParent()!=null){
			longNumber = ((CoreBaseTreeInfo)this.getParent()).getLongNumber();
			if(!BaseUtil.isEmpty(longNumber)){
				longNumber = longNumber+"!"+curNumber;
			}else{
				longNumber = curNumber;
			}
		}else{
			longNumber = curNumber;
		}
		
		return longNumber;
	}
	
	public void setLongNumber(String longNumber) {
		this.longNumber = longNumber;
	}
	
	@Column(name="fdisplayname",nullable=false)
	public String getDisplayName() {
		String curName = getName();
		if(BaseUtil.isEmpty(curName)) curName = "01";
		if(this.getParent()!=null){
			displayName = ((CoreBaseTreeInfo)this.getParent()).getDisplayName();
			if(!BaseUtil.isEmpty(displayName)){
				displayName = displayName+"_"+curName;
			}else{
				displayName = curName;
			}
		}else{
			displayName = curName;
		}
		
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	@Column(name="fisleaf",nullable=false)
	public boolean isLeaf() {
		return isLeaf;
	}
	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}
	
	
}
