package com.myapp.entity.ec.basedata;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.myapp.core.base.entity.CoreBaseBillInfo;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.SchemeState;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

/**
 * 施工方案
 * @author Phoenix
 *
 */
@Entity
@Table(name="t_ec_constructionScheme")
public class ConstructionSchemeInfo extends CoreBaseBillInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8835706898745437739L;

	private SchemeTypeInfo schemeType;//方案类别

	private ProjectInfo project;// 工程项目
	
	private Date compileDate;//编制日期
	
	private Date lastFinishDate;//最迟完成日期
	
	private UserInfo compiler;//编制人

	private SchemeState schemeState;//施工方案状态

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "fschemeTypeId")
	public SchemeTypeInfo getSchemeType() {
		return schemeType;
	}

	public void setSchemeType(SchemeTypeInfo schemeType) {
		this.schemeType = schemeType;
	}
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fprojectId")
	public ProjectInfo getProject() {
		return project;
	}

	public void setProject(ProjectInfo project) {
		this.project = project;
	}

	@Column(name="fcompileDate")
	public Date getCompileDate() {
		return compileDate;
	}

	public void setCompileDate(Date compileDate) {
		this.compileDate = compileDate;
	}

	@Column(name="flastFinishDate")
	public Date getLastFinishDate() {
		return lastFinishDate;
	}

	public void setLastFinishDate(Date lastFinishDate) {
		this.lastFinishDate = lastFinishDate;
	}
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fcompilerId")
	public UserInfo getCompiler() {
		return compiler;
	}

	public void setCompiler(UserInfo compiler) {
		this.compiler = compiler;
	}

	@Column(name="fSchemeState",length = 20)
	@Type(type="myEnum",parameters={@org.hibernate.annotations.Parameter(name="enumClass",value="com.myapp.core.enums.SchemeState")})
	public SchemeState getSchemeState() {
		return schemeState;
	}

	public void setSchemeState(SchemeState schemeState) {
		this.schemeState = schemeState;
	}
}
