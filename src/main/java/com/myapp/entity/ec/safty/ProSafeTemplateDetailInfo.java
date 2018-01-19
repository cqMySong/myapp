package com.myapp.entity.ec.safty;

import com.myapp.core.annotation.MyEntityAnn;
import com.myapp.core.base.entity.CoreBaseEntryInfo;
import com.myapp.core.entity.PositionInfo;
import com.myapp.core.entity.UserInfo;

import javax.persistence.*;
import java.util.Date;

@MyEntityAnn(name="项目质量样板职责详细")
@Entity
@Table(name = "t_ec_pro_safe_template_detail")
public class ProSafeTemplateDetailInfo extends CoreBaseEntryInfo<ProSafeTemplateInfo> {
	/**
	 * 检查人
	 */
	private UserInfo checker;
	/**
	 * 检查状态
	 */
	private Boolean checked;
	/**
	 * 工作项
	 */
	private String checkItem;

	/**
	 * 岗位信息
	 */
	private PositionInfo position;
	/**
	 * 最后更新时间
	 */
	private Date lastUpdateDate;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fCheckerId")
	public UserInfo getChecker() {
		return checker;
	}

	public void setChecker(UserInfo checker) {
		this.checker = checker;
	}

	@Column(name = "fChecked")
	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	@Column(name = "fCheckItem",length = 100)
	public String getCheckItem() {
		return checkItem;
	}

	public void setCheckItem(String checkItem) {
		this.checkItem = checkItem;
	}
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fPositionId")
	public PositionInfo getPosition() {
		return position;
	}

	public void setPosition(PositionInfo position) {
		this.position = position;
	}
	@Column(name = "fLastUpdateDate")
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
}
