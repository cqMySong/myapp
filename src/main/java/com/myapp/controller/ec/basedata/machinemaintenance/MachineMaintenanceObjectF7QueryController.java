package com.myapp.controller.ec.basedata.machinemaintenance;

import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseF7QueryController;
import com.myapp.core.controller.BaseTreeF7QueryController;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.service.basedata.DataGroupService;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.WebUtil;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.entity.ec.basedata.WorkTypeInfo;
import com.myapp.service.ec.basedata.ProLabourService;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年10月29日 
 * @system: 
 *-----------MySong---------------
 */
@Controller
@RequestMapping("base/machineManitenanceObjF7")
public class MachineMaintenanceObjectF7QueryController extends BaseF7QueryController {
	
	@Resource
	public DataGroupService dataGroupService;
	@Override
	public AbstractBaseService getService() {
		return dataGroupService;
	}
	@Override
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		ColumnModel col =  new ColumnModel("name");
		col.setAlias_zh("检查对象");
		cols.add(col);
		return cols;
	}
	@Override
	public void executeQueryParams(Criteria query) {
		super.executeQueryParams(query);
		query.add(Restrictions.eq("code","byjcml"));
		query.add(Restrictions.eq("isLeaf",false));
		query.add(Restrictions.isNotNull("parent"));
	}
	@Override
	public Order getOrder() {
		return Order.asc("number");
	}
	@Override
	public String getUIWinTitle() {
		return "检查对象列表";
	}

}
