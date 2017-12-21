package com.myapp.controller.ec.basedata.letter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseListController;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.util.BaseUtil;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.enums.ec.LetterType;
import com.myapp.service.ec.basedata.ProExchangeLetterService;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年12月21日 
 * @system:项目往来单位函件
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.函件管理.项目往来单位函件",number="app.ec.letter.proexchangeletter")
@Controller
@RequestMapping("ec/letter/proexchangeletters")
public class ProExchangeLetterListController extends BaseListController {
	
	@Resource
	public ProExchangeLetterService proExchangeLetterService;
	public AbstractBaseService getService() {
		return proExchangeLetterService;
	}

	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("number"));
		cols.add(new ColumnModel("dispatchUnit"));
		cols.add(new ColumnModel("receivedUnit"));
		cols.add(new ColumnModel("createUser",DataTypeEnum.F7,UserInfo.class));
		cols.add(new ColumnModel("project",DataTypeEnum.F7,ProjectInfo.class));
		cols.add(new ColumnModel("type",DataTypeEnum.ENUM,LetterType.class));
		cols.add(new ColumnModel("dispatchDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("receivedDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("createDate",DataTypeEnum.DATE));
		return cols;
	}
	
	public void executeQueryParams(Criteria query) {
		super.executeQueryParams(query);
		String serach = request.getParameter("search");
		SimpleExpression se = Restrictions.eq("id","xyz");
		if(!BaseUtil.isEmpty(serach)){
			Map searchMap = JSONObject.parseObject(serach, new HashMap().getClass());
			Object objTree = searchMap.get("tree");
			if(objTree!=null){
				Map treeMap = JSONObject.parseObject(objTree.toString(), new HashMap().getClass());
				Object idObj = treeMap.get("id");
				Object type = treeMap.get("type");
				if(type!=null&&idObj!=null){
					if("project".equals(type.toString())){
						se = Restrictions.eq("project.id", idObj.toString());
					}
				}
			}
		}
		query.add(se);
	}
	
	public String getEditUrl() {
		return "ec/basedata/letter/proExchangeLetterEdit";
	}

	public String getListUrl() {
		return "ec/basedata/letter/proExchangeLetterList";
	}

}
