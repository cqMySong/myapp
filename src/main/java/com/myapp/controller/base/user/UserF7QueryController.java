package com.myapp.controller.base.user;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseF7QueryController;
import com.myapp.core.entity.BaseOrgInfo;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.enums.UserState;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.service.UserService;
import com.myapp.entity.ec.basedata.ProStructureInfo;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年7月7日 
 * @system:
 *
 *-----------MySong---------------
 */
@Controller
@RequestMapping("base/userf7")
public class UserF7QueryController extends BaseF7QueryController {

	@Resource
	public UserService userService;
	public AbstractBaseService getService() {
		return this.userService;
	}
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		ColumnModel col = new ColumnModel("defOrg",DataTypeEnum.F7,BaseOrgInfo.class);
		col.setFormat("id,name");
		col.setAlias_zh("id,所属组织");
		cols.add(col);
		
		col = new ColumnModel("number");
		col.setAlias_zh("账号");
		cols.add(col);
		col = new ColumnModel("name");
		col.setAlias_zh("姓名");
		cols.add(col);
		
		col = new ColumnModel("userState",DataTypeEnum.ENUM,UserState.class);
		col.setAlias_zh("状态");
		cols.add(col);
		col = new ColumnModel("remark");
		col.setAlias_zh("备注");
		cols.add(col);
		return cols;
	}
	public void executeQueryParams(Criteria query) {
		super.executeQueryParams(query);
	}
	public Order getOrder() {
		return Order.asc("number");
	}
	public String getUIWinTitle() {
		return "系统用户";
	}

}
