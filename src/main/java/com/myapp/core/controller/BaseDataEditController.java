package com.myapp.core.controller;

import java.util.Date;
import java.util.List;

import com.myapp.core.base.entity.CoreBaseDataInfo;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.model.ColumnModel;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年6月12日 
 * @system:
 *
 *-----------MySong---------------
 */
public abstract class BaseDataEditController extends BaseEditController {
	
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("number"));
		cols.add(new ColumnModel("enabled"));
		cols.add(new ColumnModel("remark"));
		return cols;
	}
	
	protected void initDefDataValue(Object editData){
		super.initDefDataValue(editData);
		if(editData!=null){
			if(editData instanceof CoreBaseDataInfo){
				CoreBaseDataInfo cbdInfo = (CoreBaseDataInfo) editData;
				cbdInfo.setEnabled(true);
			}
		}
	}
}
