package com.myapp.controller.ec.rectify;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseListController;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.BillState;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.util.BaseUtil;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.enums.ec.CheckType;
import com.myapp.service.ec.firepermit.Lev3FirePermitService;
import com.myapp.service.ec.rectify.HiddenDangerNoticeService;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年12月07日 
 * @system:隐患整改通知单
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.整改管理.隐患整改通知单",number="app.ec.rectify.hiddendangernotice")
@Controller
@RequestMapping("ec/rectify/hiddendangernotices")
public class HiddenDangerNoticeListController extends BaseListController {
	
	@Resource
	public HiddenDangerNoticeService hiddenDangerNoticeService;
	public AbstractBaseService getService() {
		return hiddenDangerNoticeService;
	}
	
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("number"));
		cols.add(new ColumnModel("checkDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("endDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("bizDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("billState",DataTypeEnum.ENUM,BillState.class));
		cols.add(new ColumnModel("project",DataTypeEnum.F7,ProjectInfo.class));
		cols.add(new ColumnModel("sendUnit"));
		cols.add(new ColumnModel("checker",DataTypeEnum.F7,UserInfo.class));
		return cols;
	}
	
	public void executeQueryParams(Criteria query) {
		super.executeQueryParams(query);
		String serach = request.getParameter("search");
		String projectId = "xyz";
		if(!BaseUtil.isEmpty(serach)){
			Map searchMap = JSONObject.parseObject(serach, new HashMap().getClass());
			Object objTree = searchMap.get("tree");
			if(objTree!=null){
				Map treeMap = JSONObject.parseObject(objTree.toString(), new HashMap().getClass());
				Object idObj = treeMap.get("id");
				Object type = treeMap.get("type");
				if(type!=null&&idObj!=null){
					if("project".equals(type.toString())){
						projectId = idObj.toString();
					}
				}
			}
		}
		query.add(Restrictions.eq("project.id",projectId));
	}
	
	public String getEditUrl() {
		return "ec/rectify/hiddenDangerNoticeEdit";
	}

	public String getListUrl() {
		return "ec/rectify/hiddenDangerNoticeList";
	}
}
