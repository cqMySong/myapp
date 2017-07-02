package com.myapp.controller.ec.basedata.prostructure;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseTreeF7QueryController;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.WebUtil;
import com.myapp.service.ec.basedata.ProStructureService;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年5月30日 
 * @system:
 *
 *-----------MySong---------------
 */
@Controller
@RequestMapping("ec/basedata/proStructureF7")
public class ProStructureF7QueryController extends BaseTreeF7QueryController {
	
	@Resource
	public ProStructureService proStructureService;
	public AbstractBaseService getService() {
		return proStructureService;
	}
	
	public List<ColumnModel> getTreeDataBinding() {
		List<ColumnModel> cols = super.getTreeDataBinding();
		cols.add(new ColumnModel("displayName"));
		return cols;
	}
	public String getTreeTitle() {
		return "项目工程结构";
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
