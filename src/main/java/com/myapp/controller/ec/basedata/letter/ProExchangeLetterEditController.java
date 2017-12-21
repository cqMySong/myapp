package com.myapp.controller.ec.basedata.letter;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseEditController;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.entity.ec.basedata.ProExchangeLetterInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.enums.ec.LetterType;
import com.myapp.service.ec.basedata.ProExchangeLetterService;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年12月21日 
 * @system:项目往来单位函件
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.函件管理.项目往来单位函件",number="app.ec.letter.proexchangeletter")
@Controller
@RequestMapping("ec/letter/proexchangeletter")
public class ProExchangeLetterEditController extends BaseEditController{
	@Resource
	public ProExchangeLetterService proExchangeLetterService;
	public AbstractBaseService getService() {
		return proExchangeLetterService;
	}

	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("number"));
		cols.add(new ColumnModel("dispatchUnit"));
		cols.add(new ColumnModel("receivedUnit"));
		cols.add(new ColumnModel("content"));
		cols.add(new ColumnModel("createUser",DataTypeEnum.F7,UserInfo.class));
		cols.add(new ColumnModel("project",DataTypeEnum.F7,ProjectInfo.class));
		cols.add(new ColumnModel("type",DataTypeEnum.ENUM,LetterType.class));
		cols.add(new ColumnModel("dispatchDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("receivedDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("createDate",DataTypeEnum.DATE));
		return cols;
	}
	
	public Object createNewData() {
		ProExchangeLetterInfo pelInfo = new ProExchangeLetterInfo();
		pelInfo.setCreateDate(new Date());
		pelInfo.setCreateUser(getCurUser());
		return pelInfo;
	}

	public CoreBaseInfo getEntityInfo() {
		return new ProExchangeLetterInfo();
	}
}
