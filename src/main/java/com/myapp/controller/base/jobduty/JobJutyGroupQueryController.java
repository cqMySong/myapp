package com.myapp.controller.base.jobduty;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseTreeF7QueryController;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.service.JobJutyGroupService;
import com.myapp.core.service.basedata.DataGroupService;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.WebUtil;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2018年03月08日 
 * @system: 
 *-----------MySong---------------
 */
@Controller
@RequestMapping("base/jobDutyGroupQuery")
public class JobJutyGroupQueryController extends BaseTreeF7QueryController {
	
	@Resource
	public JobJutyGroupService jobJutyGroupService;
	public AbstractBaseService getService() {
		return this.jobJutyGroupService;
	}
	
	public List<ColumnModel> getTreeDataBinding() {
		List<ColumnModel> cols = super.getTreeDataBinding();
		cols.add(new ColumnModel("displayName"));
		return cols;
	}
	public String getTreeTitle() {
		return "工作职责分组";
	}
	
	public void executeTreeQueryParams(Criteria query) {
		super.executeTreeQueryParams(query);
	}

}
