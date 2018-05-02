package com.myapp.controller.ec.basedata.testpiece;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseEditController;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.entity.ec.basedata.ProVisitRecordInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.entity.ec.basedata.TestPieceInfo;
import com.myapp.service.ec.basedata.TestPieceService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ly
 */
@PermissionAnn(name="系统管理.现场管理.基础数据.试件台",number="app.ec.basedata.testpiece")
@Controller
@RequestMapping("ec/basedata/testpiece")
public class TestPieceEditController extends BaseEditController {
	
	@Resource
	public TestPieceService testPieceService;

	@Override
	public AbstractBaseService getService() {
		return testPieceService;
	}

	@Override
	public Object createNewData() {
		TestPieceInfo testPieceInfo = new TestPieceInfo();
		testPieceInfo.setCreator(getCurUser());
		return testPieceInfo;
	}

	@Override
	public CoreBaseInfo getEntityInfo() {
		return new TestPieceInfo();
	}

	@Override
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("name",DataTypeEnum.STRING));
		cols.add(new ColumnModel("number",DataTypeEnum.STRING));
		cols.add(new ColumnModel("specification",DataTypeEnum.STRING));
		cols.add(new ColumnModel("productionDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("inspectionDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("inspectionNo",DataTypeEnum.STRING));
		cols.add(new ColumnModel("inspectionResult",DataTypeEnum.BOOLEAN));
		cols.add(new ColumnModel("evidentialTesting",DataTypeEnum.BOOLEAN));
		cols.add(new ColumnModel("remark",DataTypeEnum.STRING));
		cols.add(new ColumnModel("createDate", DataTypeEnum.DATETIME));;
		cols.add(new ColumnModel("lastUpdateDate", DataTypeEnum.DATETIME));
		ColumnModel columnModel = new ColumnModel("projectInfo",DataTypeEnum.F7,"id,name");
		columnModel.setClaz(ProjectInfo.class);
		cols.add(columnModel);

		ColumnModel creator = new ColumnModel("creator",DataTypeEnum.F7,"id,name");
		creator.setClaz(UserInfo.class);
		cols.add(creator);
		return cols;
	}
}
