package com.myapp.controller.ec.basedata.prosubitem;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseTreeF7QueryController;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.WebUtil;
import com.myapp.service.ec.basedata.ProSubItemService;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年5月30日 
 * @system:
 *
 *-----------MySong---------------
 */
@Controller
@RequestMapping("ec/basedata/proSubItemF7")
public class ProSubItemF7QueryController extends BaseTreeF7QueryController {
	
	@Resource
	public ProSubItemService proSubItemService;
	public AbstractBaseService getService() {
		return proSubItemService;
	}
	
	public String getTreeTitle() {
		return "项目工程分部分项";
	}
	
	public void executeTreeQueryParams(Criteria query) {
		super.executeTreeQueryParams(query);
		String proId = WebUtil.UUID_ReplaceID(request.getParameter("projectId"));
		if(BaseUtil.isEmpty(proId)) proId = "xyz";
		if(!BaseUtil.isEmpty(proId)){
			query.add(Restrictions.eq("project.id",proId));
		}
		String billId = WebUtil.UUID_ReplaceID(request.getParameter("billId"));
		if(!BaseUtil.isEmpty(billId)){
			query.add(Restrictions.ne("id",billId));
		}
	}
}
