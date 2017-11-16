package com.myapp.controller.base.mainmenu;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseEditController;
import com.myapp.core.entity.MainMenuInfo;
import com.myapp.core.enums.BaseMethodEnum;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.enums.IconCodeType;
import com.myapp.core.enums.IconType;
import com.myapp.core.enums.MenuOpenType;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.service.MainMenuService;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年11月13日 
 * @system: 菜单配置
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.门户管理.菜单管理",number="app.home.menu")
@Controller
@RequestMapping("base/home/menu")
public class MainMenuEditController extends BaseEditController{
	@Resource
	public MainMenuService mainMenuService;
	public AbstractBaseService getService() {
		return mainMenuService;
	}
	
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("number"));
		cols.add(new ColumnModel("icon"));
		cols.add(new ColumnModel("url"));
		cols.add(new ColumnModel("params"));
		cols.add(new ColumnModel("remark"));
		cols.add(new ColumnModel("sysMenu",DataTypeEnum.BOOLEAN));
		cols.add(new ColumnModel("onShow",DataTypeEnum.BOOLEAN));
		cols.add(new ColumnModel("iconCodeType",DataTypeEnum.ENUM,IconCodeType.class));
		cols.add(new ColumnModel("iconType",DataTypeEnum.ENUM,IconType.class));
		cols.add(new ColumnModel("menuOpenType",DataTypeEnum.ENUM,MenuOpenType.class));
		ColumnModel menu = new ColumnModel("parent",DataTypeEnum.F7,MainMenuInfo.class);
		cols.add(menu);
		return cols;
	}
	
	protected boolean verifyInput(Object editData) {
		boolean isVerify = true;
		if(editData!=null&&editData instanceof MainMenuInfo){
			MainMenuInfo mInfo = (MainMenuInfo) editData;
			if(mInfo.getParent()!=null){
				if(mInfo.getParent().getLevel()>3){
					isVerify = false;
					setErrorMesg("菜单配置项不能大于4级!");
				}
			}
		}
		
		if(isVerify){
			return super.verifyInput(editData);
		}else{
			return isVerify;
		}
	}
	
	protected void beforeStoreData(BaseMethodEnum bme,Object editData) {
		super.beforeStoreData(bme,editData);
		//保存前可以做对数据进行处理
		if(BaseMethodEnum.SAVE.equals(bme)){
			
		}
	}

	public Object createNewData() {
		MainMenuInfo info = new MainMenuInfo();
		info.setOnShow(true);
		info.setSysMenu(false);
		info.setMenuOpenType(MenuOpenType.MAINTAB);
		return info;
	}

	public CoreBaseInfo getEntityInfo() {
		return new MainMenuInfo();
	}
}
