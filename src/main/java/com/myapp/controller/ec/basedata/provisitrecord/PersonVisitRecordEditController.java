package com.myapp.controller.ec.basedata.provisitrecord;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseEditController;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.BaseMethodEnum;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.entity.ec.basedata.ProVisitRecordInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.enums.ec.IDType;
import com.myapp.enums.ec.VisitType;
import com.myapp.service.ec.basedata.ProVisitRecordService;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年10月30日 
 * @system: 项目安保预案
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.基础数据.到访人员记录",number="app.ec.basedata.personvisitrecord")
@Controller
@RequestMapping("ec/basedata/personvisitrecord")
public class PersonVisitRecordEditController extends BaseEditController{
	@Resource
	public ProVisitRecordService proVisitRecordService;
	public AbstractBaseService getService() {
		return proVisitRecordService;
	}

	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("createDate",DataTypeEnum.DATETIME));
		cols.add(new ColumnModel("visitType",DataTypeEnum.ENUM,VisitType.class));
		cols.add(new ColumnModel("visitDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("cause"));
		
		cols.add(new ColumnModel("visitor"));
		cols.add(new ColumnModel("idType",DataTypeEnum.ENUM,IDType.class));
		cols.add(new ColumnModel("idNo"));
		
		cols.add(new ColumnModel("inDate",DataTypeEnum.DATETIME));
		cols.add(new ColumnModel("outDate",DataTypeEnum.DATETIME));
		cols.add(new ColumnModel("confirm"));
		
		ColumnModel creator = new ColumnModel("creator",DataTypeEnum.F7,"id,name");
		creator.setClaz(UserInfo.class);
		cols.add(creator);
		ColumnModel project = new ColumnModel("project",DataTypeEnum.F7,"id,name");
		project.setClaz(ProjectInfo.class);
		cols.add(project);
		return cols;
	}
	
	protected void beforeStoreData(BaseMethodEnum bme, Object editData) {
		super.beforeStoreData(bme, editData);
		if(editData!=null&&editData instanceof ProVisitRecordInfo){
			ProVisitRecordInfo pvrInfo = (ProVisitRecordInfo) editData;
			pvrInfo.setVisitType(VisitType.PERSON);
		}
	}
	
	protected void initDefDataValue(Object editData) {
		super.initDefDataValue(editData);
		if(editData!=null&&editData instanceof ProVisitRecordInfo){
			ProVisitRecordInfo odrInfo = (ProVisitRecordInfo) editData;
			Date curDate = new Date();
			odrInfo.setCreateDate(curDate);
			odrInfo.setInDate(curDate);
			odrInfo.setVisitDate(curDate);
			odrInfo.setCreator(getCurUser());
			odrInfo.setIdType(IDType.SFZ);
			odrInfo.setVisitType(VisitType.PERSON);
		}
	}
	
	public Object createNewData() {
		return new ProVisitRecordInfo();
	}

	public CoreInfo getEntityInfo() {
		return new ProVisitRecordInfo();
	}
}
