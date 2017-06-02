package com.myapp.controller.base.user;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.ProjectionList;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseEditController;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.BaseMethodEnum;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.enums.UserState;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.model.EditDataModel;
import com.myapp.core.service.UserService;
import com.myapp.core.util.DateUtil;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年5月20日 
 * @system:
 *-----------MySong---------------
 */
@Controller
@RequestMapping("base/user")
public class UserEditController extends BaseEditController{
	@Resource
	public UserService userService;
	public AbstractBaseService getService() {
		return this.userService;
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
		UserInfo info = new UserInfo();
		info.setName("宋军");
		info.setNumber(DateUtil.formatDate(new Date()));
		info.setUserState(UserState.ENABLE);
		info.setAdmin(true);
		info.setSysUser(true);
		return info;
	}

	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("number"));
		cols.add(new ColumnModel("remark"));
		cols.add(new ColumnModel("userState",DataTypeEnum.ENUM,UserState.class));
		cols.add(new ColumnModel("admin",DataTypeEnum.BOOLEAN));
		cols.add(new ColumnModel("sysUser",DataTypeEnum.BOOLEAN));
		cols.add(new ColumnModel("createDate",DataTypeEnum.DATE,DateUtil.DATEFORMT_YMDHMS));
		cols.add(new ColumnModel("passWord"));
		ColumnModel orgCol = new ColumnModel("defOrg",DataTypeEnum.F7,"id,name");
		cols.add(orgCol);
		return cols;
	}

	public CoreBaseInfo getEntityInfo() {
		return new UserInfo();
	}
}
