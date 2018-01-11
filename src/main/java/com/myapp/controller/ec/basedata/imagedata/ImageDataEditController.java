package com.myapp.controller.ec.basedata.imagedata;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseEditController;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.enums.ImageDataType;
import com.myapp.core.exception.db.QueryException;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.util.WebUtil;
import com.myapp.entity.ec.basedata.*;
import com.myapp.entity.ec.labour.LabourEnterInfo;
import com.myapp.service.ec.basedata.ImageDataService;
import com.myapp.service.ec.labour.LabourEnterService;
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
 * 劳务人员进场及用工记录
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.基础数据.工程影像资料",number="app.ec.basedata.imagedata")
@Controller
@RequestMapping("ec/basedata/imagedata")
public class ImageDataEditController extends BaseEditController {
	@Resource
	public ImageDataService imageDataService;
	@Override
	public AbstractBaseService getService() {
		return imageDataService;
	}
	@Override
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("number"));
		cols.add(new ColumnModel("imageDataType",DataTypeEnum.ENUM, ImageDataType.class));
		cols.add(new ColumnModel("remark"));
		cols.add(new ColumnModel("lastUpdateUser",DataTypeEnum.F7,UserInfo.class));
		cols.add(new ColumnModel("createDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("lastUpdateDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("createUser",DataTypeEnum.F7,UserInfo.class));

		ColumnModel project = new ColumnModel("project",DataTypeEnum.F7,"id,name");
		project.setClaz(ProjectInfo.class);
		cols.add(project);

		ColumnModel proStructure = new ColumnModel("proStructure",DataTypeEnum.F7,"id,name");
		proStructure.setClaz(ProStructureInfo.class);
		cols.add(proStructure);

		ColumnModel proBatchTest = new ColumnModel("proBatchTest",DataTypeEnum.F7,"id,name");
		proBatchTest.setClaz(ProBatchTestInfo.class);
		cols.add(proBatchTest);

		ColumnModel proBaseWbs = new ColumnModel("proBaseWbs",DataTypeEnum.F7,"id,name");
		proBaseWbs.setClaz(ProBaseWbsInfo.class);
		cols.add(proBaseWbs);
		return cols;
	}
	@Override
	public Object createNewData() {
		ImageDataInfo imageDataInfo= new ImageDataInfo();
		imageDataInfo.setCreateUser(getCurUser());
		Map uiCtx = getUiCtx();
		if(uiCtx!=null){
			Object treeObj = uiCtx.get("tree");
			if(treeObj!=null){
				Map treeMap = JSONObject.parseObject(treeObj.toString(), new HashMap().getClass());
				Object idObj = treeMap.get("id");
				Object type = treeMap.get("type");
				if(idObj!=null&&type!=null){
					String id = WebUtil.UUID_ReplaceID(idObj.toString());
					if("project".equals(type.toString())){
						ProjectInfo pInfo = new ProjectInfo();
						pInfo.setId(id);
						pInfo.setName((String)treeMap.get("name"));
						imageDataInfo.setProject(pInfo);
					}else if("proStructure".equals(type.toString())){//单位工程结构
						StringBuffer sql = new StringBuffer();
						sql.append(" select a.fid as id,a.fname as name,b.fid as proId,b.fname as proName ");
						sql.append(" from t_ec_proStructure as a");
						sql.append(" left join t_ec_project as b on b.fid = a.fprojectId");
						sql.append(" where a.fid =?");
						try {
							List structs = imageDataService.executeSQLQuery(sql.toString(), new String[]{id});
							if(structs!=null&&structs.size()>0){
								Map dmap = (Map) structs.get(0);
								Object _id = dmap.get("id");
								Object _name = dmap.get("name");
								if(_id!=null&&_name!=null){
									ProStructureInfo pInfo = new ProStructureInfo();
									pInfo.setId(_id.toString());
									pInfo.setName(_name.toString());
									imageDataInfo.setProStructure(pInfo);
								}
								_id = dmap.get("proId");
								_name = dmap.get("proName");
								if(_id!=null&&_name!=null){
									ProjectInfo pInfo = new ProjectInfo();
									pInfo.setId(_id.toString());
									pInfo.setName(_name.toString());
									imageDataInfo.setProject(pInfo);
								}
							}
						} catch (QueryException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		return imageDataInfo;
	}
	@Override
	public CoreBaseInfo getEntityInfo() {
		return new ImageDataInfo();
	}
}
