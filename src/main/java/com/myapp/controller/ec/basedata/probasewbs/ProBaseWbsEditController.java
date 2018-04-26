package com.myapp.controller.ec.basedata.probasewbs;

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
import com.myapp.core.model.ColumnModel;
import com.myapp.entity.ec.basedata.ProBaseWbsInfo;
import com.myapp.enums.ec.ProWbsType;
import com.myapp.service.ec.basedata.ProBaseWbsService;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年12月04日 
 * @system:
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.基础数据.分部分项工程标准",number="app.ec.basedata.probasewbs")
@Controller
@RequestMapping("ec/basedata/probasewbs")
public class ProBaseWbsEditController extends BaseEditController{
	@Resource
	public ProBaseWbsService proBaseWbsService;
	public AbstractBaseService getService() {
		return proBaseWbsService;
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
		ProBaseWbsInfo info = new ProBaseWbsInfo();
		info.setEnabled(true);
		return info;
	}

	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("number"));
		cols.add(new ColumnModel("remark"));
		cols.add(new ColumnModel("enabled",DataTypeEnum.BOOLEAN));
		cols.add(new ColumnModel("wbsType",DataTypeEnum.ENUM,ProWbsType.class));
		cols.add(new ColumnModel("parent",DataTypeEnum.F7,ProBaseWbsInfo.class));
		return cols;
	}

	public CoreBaseInfo getEntityInfo() {
		return new ProBaseWbsInfo();
	}
}
