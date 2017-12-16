package com.myapp.controller.ec.other.rewardandpunish;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseDataEditController;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.entity.ec.basedata.StructTypeInfo;
import com.myapp.entity.ec.other.RewardPunishTypeInfo;
import com.myapp.enums.ec.RewardPunish;
import com.myapp.enums.ec.WorkFollow;
import com.myapp.enums.ec.WorkType;
import com.myapp.service.ec.other.RewardPunishTypeService;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年5月20日 
 * @system:
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.其他管理.奖惩类型",number="app.ec.other.rewardpunishtypes")
@Controller
@RequestMapping("ec/other/rewardpunishtype")
public class RewardPunishTypeEditController extends BaseDataEditController{
	@Resource
	public RewardPunishTypeService rewardPunishTypeService;
	
	public AbstractBaseService getService() {
		return rewardPunishTypeService;
	}
	
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("rewardPunish",DataTypeEnum.ENUM,RewardPunish.class));
		cols.add(new ColumnModel("remark"));
		return cols;
	}

	public Object createNewData() {
		return new RewardPunishTypeInfo();
	}

	public CoreBaseInfo getEntityInfo() {
		return new RewardPunishTypeInfo();
	}
}
