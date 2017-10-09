package com.myapp.controller.base.permission;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseF7QueryController;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.enums.PermissionTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.service.JobDutyService;
import com.myapp.core.service.PermissionService;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.EnumUtil;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年8月28日 
 * @system:
 *
 *-----------MySong---------------
 */
@Controller
@RequestMapping("base/permissionf7")
public class PermissionF7QueryController extends BaseF7QueryController {

	@Resource
	public PermissionService permissionService;
	public AbstractBaseService getService() {
		return permissionService;
	}
	
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		ColumnModel col = new ColumnModel("longNumber");
		col.setAlias_zh("编码");
		cols.add(col);
		col = new ColumnModel("displayName");
		col.setAlias_zh("显示名");
		cols.add(col);
		col = new ColumnModel("url");
		col.setAlias_zh("地址");
		cols.add(col);
		col = new ColumnModel("type",DataTypeEnum.ENUM,PermissionTypeEnum.class);
		col.setAlias_zh("类型");
		cols.add(col);
		return cols;
	}
	
	public void executeQueryParams(Criteria query) {
		super.executeQueryParams(query);
		Map params = getQueryUiCtx();
		if(params==null||params.size()<=0) return;
		Boolean isLeaf = Boolean.TRUE;
		Object toLeaf = params.get("leaf");
		if(!BaseUtil.isEmpty(toLeaf)){
			if(toLeaf.toString().equals("A")){//表示全部
				isLeaf = null;
			}else{
				isLeaf = "T".equals(toLeaf.toString());
			}
		}
		if(isLeaf!=null){
			query.add(Restrictions.eq("isLeaf",isLeaf));
		}
		PermissionTypeEnum pte = null;
		Object type = params.get("type");
		if(!BaseUtil.isEmpty(type)){
			pte = EnumUtil.getEnum(PermissionTypeEnum.class.getName(), type);
		}
		if(pte!=null){
			query.add(Restrictions.eq("type",pte));
		}
	}
	
	public Order getOrder() {
		return Order.asc("longNumber");
	}
	public String getUIWinTitle() {
		return "权限项查询";
	}

}
