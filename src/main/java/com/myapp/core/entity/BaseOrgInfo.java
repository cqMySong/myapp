package com.myapp.core.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.myapp.core.base.entity.CoreBaseTreeInfo;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年5月1日 
 * @system:
 *
 *-----------MySong---------------
 */
@Entity
@Table(name="t_base_Org")
public class BaseOrgInfo extends CoreBaseTreeInfo<BaseOrgInfo> {
	private String shortCode;
	private String remark;
	
	@Column(name="fshortcode",length=50)
	public String getShortCode() {
		return shortCode;
	}
	public void setShortCode(String shortCode) {
		this.shortCode = shortCode;
	}
	@Column(name="fremark",length=1000)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public static void main(String[] args){
		BaseOrgInfo ui= new BaseOrgInfo();
		ui.put("name","p1");
		ui.put("number","01");
		ui.put("createDate", new Date());
		
		BaseOrgInfo ui2= new BaseOrgInfo();
		ui2.put("name","p11");
		ui2.put("number","0101");
		ui2.put("createDate", new Date());
		
		BaseOrgInfo ui3= new BaseOrgInfo();
		ui3.put("name","p12");
		ui3.put("number","0102");
		ui3.put("createDate", new Date());
		Set<BaseOrgInfo> childs = new HashSet<BaseOrgInfo>();
		childs.add(ui2);
		childs.add(ui3);
		
		ui.put("children", childs);
		System.out.println(ui.get("name")+":"+ui.get("number"));
		Set<BaseOrgInfo> cds = (Set<BaseOrgInfo>) ui.get("children");
		for(BaseOrgInfo oinfo:cds){
			System.out.println(oinfo.get("name")+":"+oinfo.get("number"));
		}
		
	}
}
