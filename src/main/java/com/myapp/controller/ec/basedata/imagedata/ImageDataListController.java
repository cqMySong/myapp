package com.myapp.controller.ec.basedata.imagedata;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseListController;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.enums.ImageDataType;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.util.BaseUtil;
import com.myapp.entity.ec.basedata.ProBatchTestInfo;
import com.myapp.entity.ec.basedata.ProStructureInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.service.ec.basedata.ImageDataService;
import com.myapp.service.ec.labour.LabourEnterService;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年12月24日 
 * @system:
 * 工程影像资料
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.基础数据.工程影像资料",number="app.ec.basedata.imagedata")
@Controller
@RequestMapping("ec/basedata/imagedatas")
public class ImageDataListController extends BaseListController {
	
	@Resource
	public ImageDataService imageDataService;
	@Override
	public AbstractBaseService getService() {
		return imageDataService;
	}
	@Override
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("name",DataTypeEnum.STRING));
		cols.add(new ColumnModel("number",DataTypeEnum.STRING));
		cols.add(new ColumnModel("imageDataType",DataTypeEnum.ENUM, ImageDataType.class));
		cols.add(new ColumnModel("project",DataTypeEnum.F7,ProjectInfo.class));
		cols.add(new ColumnModel("createUser",DataTypeEnum.F7,UserInfo.class));
		cols.add(new ColumnModel("proBatchTest",DataTypeEnum.F7,ProBatchTestInfo.class));
		cols.add(new ColumnModel("proStructure",DataTypeEnum.F7,ProStructureInfo.class));
		cols.add(new ColumnModel("createDate",DataTypeEnum.DATE));
		return cols;
	}
	@Override
	public void executeQueryParams(Criteria query) {
		super.executeQueryParams(query);
		String serach = request.getParameter("search");
		SimpleExpression se = Restrictions.eq("id","xyz");
		if(!BaseUtil.isEmpty(serach)){
			Map searchMap = JSONObject.parseObject(serach, new HashMap().getClass());
			Object objTree = searchMap.get("tree");
			if(objTree!=null){
				Map treeMap = JSONObject.parseObject(objTree.toString(), new HashMap().getClass());
				Object idObj = treeMap.get("id");
				Object type = treeMap.get("type");
				if(type!=null&&idObj!=null){
					if("project".equals(type.toString())){
						se = Restrictions.eq("project.id", idObj.toString());
					}
				}
			}
		}
		query.add(se);
	}
	@Override
	public String getEditUrl() {
		return "ec/basedata/imagedata/imageDataEdit";
	}
	@Override
	public String getListUrl() {
		return "ec/basedata/imagedata/imageDataList";
	}

}
