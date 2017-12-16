package com.myapp.controller.ec.other.rewardandpunish;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseF7QueryController;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.enums.ec.RewardPunish;
import com.myapp.service.ec.other.RewardPunishTypeService;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年5月30日 
 * @system:
 *
 *-----------MySong---------------
 */
@Controller
@RequestMapping("ec/other/punishtypeF7")
public class PunishTypeF7QueryController extends BaseF7QueryController {
	
	@Resource
	public RewardPunishTypeService rewardPunishTypeService;
	public AbstractBaseService getService() {
		return rewardPunishTypeService;
	}
	
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		ColumnModel col = new ColumnModel("number");
		col.setAlias_zh("编码");
		cols.add(col);
		col = new ColumnModel("name");
		col.setAlias_zh("名称");
		cols.add(col);
		col = new ColumnModel("rewardPunish",DataTypeEnum.ENUM,RewardPunish.class);
		col.setAlias_zh("奖惩类型");
		col = new ColumnModel("enabled",DataTypeEnum.BOOLEAN);
		col.setAlias_zh("启用");
		cols.add(col);
		col = new ColumnModel("remark");
		col.setAlias_zh("备注");
		cols.add(col);
		return cols;
	}
	
	public void executeQueryParams(Criteria query) {
		super.executeQueryParams(query);
		query.add(Restrictions.eq("rewardPunish",RewardPunish.PUNISH));
		query.add(Restrictions.eq("enabled",Boolean.TRUE));
	}
	
	public Order getOrder() {
		return Order.asc("number");
	}
	
	public String getUIWinTitle() {
		return "奖惩类型查询";
	}
}
