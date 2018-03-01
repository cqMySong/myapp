package com.myapp.controller.base.user;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.base.setting.SystemConstant;
import com.myapp.core.controller.BaseEditController;
import com.myapp.core.entity.BaseOrgInfo;
import com.myapp.core.entity.PositionInfo;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.entity.UserPositionInfo;
import com.myapp.core.enums.BaseMethodEnum;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.enums.UserState;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.service.UserService;
import com.myapp.core.util.BaseUtil;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年5月20日 
 * @system:
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.用户管理",number="app.user")
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
			if(editData!=null&&editData instanceof UserInfo){
				UserInfo ui = (UserInfo) editData;
				if(BaseUtil.isEmpty(ui.getId())){
					try {
						ui.setPassWord(BaseUtil.md5Encrypt(SystemConstant.DEF_USERPWD_INIT));
					} catch (NoSuchAlgorithmException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public Object createNewData() {
		UserInfo info = new UserInfo();
		info.setName("");
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
		cols.add(new ColumnModel("linkers"));
		cols.add(new ColumnModel("userState",DataTypeEnum.ENUM,UserState.class));
		cols.add(new ColumnModel("admin",DataTypeEnum.BOOLEAN));
		cols.add(new ColumnModel("sysUser",DataTypeEnum.BOOLEAN));
		ColumnModel orgCol = new ColumnModel("defOrg",DataTypeEnum.F7,"id,name");
		orgCol.setClaz(BaseOrgInfo.class);
		cols.add(orgCol);
		
		ColumnModel positionItems = new ColumnModel("positionItems",DataTypeEnum.ENTRY,UserPositionInfo.class);
		positionItems.getCols().add(new ColumnModel("id",DataTypeEnum.PK));
		
		ColumnModel org = new ColumnModel("org",DataTypeEnum.F7,"id,name,displayName");
		org.setClaz(BaseOrgInfo.class);
		positionItems.getCols().add(org);
		
		ColumnModel position = new ColumnModel("position",DataTypeEnum.F7,"id,name");
		position.setClaz(PositionInfo.class);
		positionItems.getCols().add(position);
		positionItems.getCols().add(new ColumnModel("main",DataTypeEnum.BOOLEAN));
		cols.add(positionItems);
		
		return cols;
	}

	public CoreBaseInfo getEntityInfo() {
		return new UserInfo();
	}
}
