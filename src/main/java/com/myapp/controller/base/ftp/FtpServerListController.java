package com.myapp.controller.base.ftp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseListController;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.enums.UserState;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.service.UserService;
import com.myapp.core.service.base.FtpServerService;
import com.myapp.core.util.DateUtil;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年5月6日 
 * @system:
 *
 *-----------MySong---------------
 */
@Controller
@RequestMapping("base/ftps")
public class FtpServerListController extends BaseListController {
	@Resource
	public FtpServerService ftpServerService;
	public AbstractBaseService getService() {
		return this.ftpServerService;
	}
	
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("number"));
		cols.add(new ColumnModel("host"));
		cols.add(new ColumnModel("root"));
		cols.add(new ColumnModel("userName"));
		cols.add(new ColumnModel("password"));
		cols.add(new ColumnModel("port",DataTypeEnum.INT));
		cols.add(new ColumnModel("enabled",DataTypeEnum.BOOLEAN));
		cols.add(new ColumnModel("remark"));
		return cols;
	}
	
	public String getEditUrl() {
		return "ftp/ftpEdit";
	}

	public String getListUrl() {
		return "ftp/ftpList";
	}
	
}
