package com.myapp.controller.ec.basedata.project;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseEditController;
import com.myapp.core.entity.BaseOrgInfo;
import com.myapp.core.enums.BaseMethodEnum;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.enums.UserState;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.service.OrgService;
import com.myapp.core.util.DateUtil;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.entity.ec.basedata.StructTypeInfo;
import com.myapp.enums.IndustryType;
import com.myapp.enums.ProjectState;
import com.myapp.service.ec.basedata.ProjectService;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年5月20日 
 * @system:
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.基础数据.工程项目",number="app.ec.basedata.project")
@Controller
@RequestMapping("ec/basedata/project")
public class ProjectEditController extends BaseEditController{
	@Resource
	public ProjectService projectService;
	public AbstractBaseService getService() {
		return projectService;
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
		ProjectInfo info = new ProjectInfo();
		return info;
	}

	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("number"));
		cols.add(new ColumnModel("proState",DataTypeEnum.ENUM,ProjectState.class));
		cols.add(new ColumnModel("industryType",DataTypeEnum.ENUM,IndustryType.class));
		cols.add(new ColumnModel("remark"));
		cols.add(new ColumnModel("address"));
		cols.add(new ColumnModel("scale"));
		cols.add(new ColumnModel("eavesHeight",DataTypeEnum.NUMBER));
		cols.add(new ColumnModel("floorHeight",DataTypeEnum.NUMBER));
		cols.add(new ColumnModel("area",DataTypeEnum.NUMBER));
		cols.add(new ColumnModel("aseismicLevel"));
		
		ColumnModel structCols = new ColumnModel("structTypes",DataTypeEnum.MUTILF7,"name,number");
		structCols.setClaz(StructTypeInfo.class);
		cols.add(structCols);
		
		ColumnModel orgCol = new ColumnModel("org",DataTypeEnum.F7,"id,name");
		orgCol.setClaz(BaseOrgInfo.class);
		cols.add(orgCol);
		return cols;
	}

	public CoreBaseInfo getEntityInfo() {
		return new ProjectInfo();
	}
}
