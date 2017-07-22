package com.myapp.controller.ec.basedata.prosub;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseEditController;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.exception.db.QueryException;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.service.base.BaseService;
import com.myapp.core.util.WebUtil;
import com.myapp.entity.ec.basedata.ProStructureInfo;
import com.myapp.entity.ec.basedata.ProSubInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年5月20日 
 * @system:
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.基础数据.项目分部",number="app.ec.basedata.prosub")
@Controller
@RequestMapping("ec/basedata/prosub")
public class ProSubEditController extends BaseEditController{
	@Resource
	public BaseService baseService;
	public AbstractBaseService getService() {
		return baseService.newServicInstance(ProSubInfo.class);
	}

	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("number"));
		cols.add(new ColumnModel("enabled"));
		cols.add(new ColumnModel("remark"));
		ColumnModel proStruct = new ColumnModel("proStruct",DataTypeEnum.F7,"id,name");
		proStruct.setClaz(ProStructureInfo.class);
		cols.add(proStruct);
		ColumnModel project = new ColumnModel("project",DataTypeEnum.F7,"id,name");
		project.setClaz(ProjectInfo.class);
		cols.add(project);
		return cols;
	}
	
	public Object createNewData() {
//		request.getParameter("uiCtx") {"tree":{"id":"NiEbtFVyTYGc3jn17axYdpn32P4%3D","name":"江北项目xx街道施工项目","number":"01","longNumber":"01","type":"project"}}
		ProSubInfo proSubInfo = new ProSubInfo();
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
						proSubInfo.setProject(pInfo);
					}else if("proStructure".equals(type.toString())){//单位工程结构
						StringBuffer sql = new StringBuffer();
						sql.append(" select a.fid as id,a.fname as name,b.fid as proId,b.fname as proName ");
						sql.append(" from t_ec_proStructure as a");
						sql.append(" left join t_ec_project as b on b.fid = a.fprojectId");
						sql.append(" where a.fid =?");
						try {
							List structs = baseService.executeSQLQuery(sql.toString(), new String[]{id});
							if(structs!=null&&structs.size()>0){
								Map dmap = (Map) structs.get(0);
								Object _id = dmap.get("id");
								Object _name = dmap.get("name");
								if(_id!=null&&_name!=null){
									ProStructureInfo pInfo = new ProStructureInfo();
									pInfo.setId(_id.toString());
									pInfo.setName(_name.toString());
									proSubInfo.setProStruct(pInfo);
								}
								_id = dmap.get("proId");
								_name = dmap.get("proName");
								if(_id!=null&&_name!=null){
									ProjectInfo pInfo = new ProjectInfo();
									pInfo.setId(_id.toString());
									pInfo.setName(_name.toString());
									proSubInfo.setProject(pInfo);
								}
							}
						} catch (QueryException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		proSubInfo.setEnabled(true);
		return proSubInfo;
	}

	public CoreBaseInfo getEntityInfo() {
		return new ProSubInfo();
	}
}
