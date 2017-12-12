package com.myapp.controller.base.datagroup;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseTreeF7QueryController;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.service.basedata.DataGroupService;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.WebUtil;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年10月29日 
 * @system: 
 *-----------MySong---------------
 */
@Controller
@RequestMapping("base/datagroupF7")
public class DataGroupF7QueryController extends BaseTreeF7QueryController {
	
	@Resource
	public DataGroupService dataGroupService;
	public AbstractBaseService getService() {
		return dataGroupService;
	}
	
	public List<ColumnModel> getTreeDataBinding() {
		List<ColumnModel> cols = super.getTreeDataBinding();
		cols.add(new ColumnModel("code"));
		return cols;
	}
	public String getTreeTitle() {
		return "资料目录";
	}
	
	public void executeTreeQueryParams(Criteria query) {
		super.executeTreeQueryParams(query);
		String code = WebUtil.UUID_ReplaceID(request.getParameter("code"));
		if(!BaseUtil.isEmpty(code)){
			query.add(Restrictions.eq("code",code));
		}
		query.add(Restrictions.eq("enabled", Boolean.TRUE));
	}

}
