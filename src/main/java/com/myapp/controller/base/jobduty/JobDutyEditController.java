package com.myapp.controller.base.jobduty;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.ProjectionList;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseDataEditController;
import com.myapp.core.controller.BaseEditController;
import com.myapp.core.entity.JobDutyInfo;
import com.myapp.core.entity.RoleInfo;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.BaseMethodEnum;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.enums.UserState;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.model.EditDataModel;
import com.myapp.core.service.JobDutyService;
import com.myapp.core.service.RoleService;
import com.myapp.core.service.UserService;
import com.myapp.core.util.DateUtil;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年8月26日 
 * @system:
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.工作职责",number="app.jobduty")
@Controller
@RequestMapping("base/jobduty")
public class JobDutyEditController extends BaseDataEditController{
	@Resource
	public JobDutyService jobDutyService;
	public AbstractBaseService getService() {
		return jobDutyService;
	}

	public Object createNewData() {
		return new JobDutyInfo();
	}

	public CoreBaseInfo getEntityInfo() {
		return new JobDutyInfo();
	}
}
