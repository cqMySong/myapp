package com.myapp.entity.ec.labour;

import com.myapp.core.annotation.MyEntityAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.entity.UserInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;

import javax.persistence.*;
import java.util.Date;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年12月23日 
 * @system:劳务人员进场及用工记录
 *-----------MySong---------------
 */
@MyEntityAnn(name="劳务人员进场及用工记录")
@Entity
@Table(name="t_ec_labour_enter")
public class LabourEnterInfo extends CoreBaseInfo {
	/**
	 * 项目工程
	 */
	private ProjectInfo project;
	/**
	 * 创建人
	 */
	private UserInfo createUser;
	/**
	 * 最近更新人
	 */
	private UserInfo lastUpdateUser;
	/**
	 * 进场记录时间
	 */
	private Date enterDate;

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "fProjectId")
	public ProjectInfo getProject() {
		return project;
	}

	public void setProject(ProjectInfo project) {
		this.project = project;
	}

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "fCreateUserId")
	public UserInfo getCreateUser() {
		return createUser;
	}

	public void setCreateUser(UserInfo createUser) {
		this.createUser = createUser;
	}

	@Column(name="fEnterDate")
	public Date getEnterDate() {
		return enterDate;
	}

	public void setEnterDate(Date enterDate) {
		this.enterDate = enterDate;
	}

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "fLastUpdateUserId")
	public UserInfo getLastUpdateUser() {
		return lastUpdateUser;
	}

	public void setLastUpdateUser(UserInfo lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}
}
