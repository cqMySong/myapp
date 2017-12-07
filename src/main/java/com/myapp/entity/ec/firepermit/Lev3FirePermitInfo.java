package com.myapp.entity.ec.firepermit;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.myapp.core.entity.UserInfo;
import com.myapp.entity.ec.EcProjectBaseBillInfo;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年12月7日 
 * @system:
 * 3级安全动火许可证
 *-----------MySong---------------
 */
@Entity
@Table(name="t_ec_lev3firepermit")
public class Lev3FirePermitInfo extends EcProjectBaseBillInfo {
	
	//单位名称（分包单位或班组）用name来替代
	private String firePart;//动火部位
	private Date fireDate;//动火时间
	
	private String welder;//焊工
	private String guarder;//监护人
	private String proposer;//申请动火人
	
	private String welderbz;//焊工班长
	
	private UserInfo pzUser;//批准人
	private UserInfo jsUser;//技术科
	private UserInfo aqUser;//安全科
	
	@Column(name="ffirepart",length=100)
	public String getFirePart() {
		return firePart;
	}
	public void setFirePart(String firePart) {
		this.firePart = firePart;
	}
	@Column(name="ffiredate")
	public Date getFireDate() {
		return fireDate;
	}
	public void setFireDate(Date fireDate) {
		this.fireDate = fireDate;
	}
	@Column(name="fwelder",length=100)
	public String getWelder() {
		return welder;
	}
	public void setWelder(String welder) {
		this.welder = welder;
	}
	@Column(name="fguarder",length=100)
	public String getGuarder() {
		return guarder;
	}
	public void setGuarder(String guarder) {
		this.guarder = guarder;
	}
	@Column(name="fproposer",length=100)
	public String getProposer() {
		return proposer;
	}
	public void setProposer(String proposer) {
		this.proposer = proposer;
	}
	@Column(name="fwelderbz",length=100)
	public String getWelderbz() {
		return welderbz;
	}
	public void setWelderbz(String welderbz) {
		this.welderbz = welderbz;
	}
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "fpzuserId")
	public UserInfo getPzUser() {
		return pzUser;
	}
	public void setPzUser(UserInfo pzUser) {
		this.pzUser = pzUser;
	}
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "fjsuserId")
	public UserInfo getJsUser() {
		return jsUser;
	}
	public void setJsUser(UserInfo jsUser) {
		this.jsUser = jsUser;
	}
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "faquserId")
	public UserInfo getAqUser() {
		return aqUser;
	}
	public void setAqUser(UserInfo aqUser) {
		this.aqUser = aqUser;
	}
}
