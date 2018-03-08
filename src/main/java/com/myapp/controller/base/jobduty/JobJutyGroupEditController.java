package com.myapp.controller.base.jobduty;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseEditController;
import com.myapp.core.entity.JobDutyGroupInfo;
import com.myapp.core.enums.BaseMethodEnum;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.service.JobJutyGroupService;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年5月20日 
 * @system:
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.工作职责分组",number="app.jobdutygroup")
@Controller
@RequestMapping("base/jobdutygroup")
public class JobJutyGroupEditController extends BaseEditController{
	@Resource
	public JobJutyGroupService jobJutyGroupService;
	public AbstractBaseService getService() {
		return this.jobJutyGroupService;
	}
	
	protected boolean verifyInput(Object editData) {
		return super.verifyInput(editData);
	}
	
	protected void beforeStoreData(BaseMethodEnum bme,Object editData) {
		super.beforeStoreData(bme,editData);
		//保存前可以做对数据进行处理
		if(BaseMethodEnum.SAVE.equals(bme)){
			
		}
	}

	public Object createNewData() {
		JobDutyGroupInfo info = new JobDutyGroupInfo();
		return info;
	}

	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("number"));
		cols.add(new ColumnModel("remark"));
		ColumnModel parent = new ColumnModel("parent",DataTypeEnum.F7,"id,name");
		parent.setClaz(JobDutyGroupInfo.class);
		cols.add(parent);
		return cols;
	}

	public CoreBaseInfo getEntityInfo() {
		return new JobDutyGroupInfo();
	}
}
