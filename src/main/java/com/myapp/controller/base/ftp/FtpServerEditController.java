package com.myapp.controller.base.ftp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseEditController;
import com.myapp.core.entity.FtpServerInfo;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.BaseMethodEnum;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.enums.UserState;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.service.base.FtpServerService;
import com.myapp.core.util.DateUtil;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年6月3日 
 * @system:
 *  ftp配置处理
 *-----------MySong---------------
 */
@Controller
@RequestMapping("base/ftp")
public class FtpServerEditController extends BaseEditController{
	@Resource
	public FtpServerService ftpServerService;
	public AbstractBaseService getService() {
		return this.ftpServerService;
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
		FtpServerInfo info = new FtpServerInfo();
		info.setNumber("web-app-001");
		info.setName("ftp附件配置");
		info.setRoot("root");
		info.setEnabled(true);
		return info;
	}

	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("number"));
		cols.add(new ColumnModel("host"));
		cols.add(new ColumnModel("root"));
		cols.add(new ColumnModel("userName"));
		cols.add(new ColumnModel("password"));
		cols.add(new ColumnModel("port",DataTypeEnum.INT));
		cols.add(new ColumnModel("enabled",DataTypeEnum.BOOLEAN));
		cols.add(new ColumnModel("remark"));
		return cols;
	}

	public CoreBaseInfo getEntityInfo() {
		return new FtpServerInfo();
	}
}
