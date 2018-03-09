package com.myapp.controller.ec.other.rectifyfeedback;

import java.util.List;

import javax.annotation.Resource;

import com.myapp.entity.ec.basedata.ProjectInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseBillEditController;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.entity.ec.other.RectifyFeedBackInfo;
import com.myapp.entity.ec.other.RectifyNoticeInfo;
import com.myapp.service.ec.other.RectifyFeedBackService;


@PermissionAnn(name="系统管理.现场管理.其他管理.整改回复",number="app.ec.other.rectifyfeedback")
@Controller
@RequestMapping("ec/other/rectifyFeedBack")
public class RectifyFeedBackEditController extends BaseBillEditController {
	
	@Resource
	public RectifyFeedBackService rectifyFeedBackService;
	@Override
	public AbstractBaseService getService() {
		return rectifyFeedBackService;
	}
	@Override
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("number"));
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("rectifyNotice",DataTypeEnum.F7,RectifyNoticeInfo.class));
		cols.add(new ColumnModel("requires"));
		cols.add(new ColumnModel("isDone",DataTypeEnum.BOOLEAN));
		cols.add(new ColumnModel("doneDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("endDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("isFeedBack",DataTypeEnum.BOOLEAN));
		cols.add(new ColumnModel("project",DataTypeEnum.F7,ProjectInfo.class));
		return cols;
	}
	@Override
	public Object createNewData() {
		RectifyFeedBackInfo info = new RectifyFeedBackInfo();
		return info;
	}
	@Override
	public CoreInfo getEntityInfo() {
		return new RectifyFeedBackInfo();
	}

}
