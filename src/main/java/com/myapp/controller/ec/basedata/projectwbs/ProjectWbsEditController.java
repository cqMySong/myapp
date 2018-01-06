package com.myapp.controller.ec.basedata.projectwbs;

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
import com.myapp.core.util.WebUtil;
import com.myapp.entity.ec.basedata.ProStructureInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.entity.ec.basedata.ProjectWbsInfo;
import com.myapp.enums.ec.ProWbsType;
import com.myapp.service.ec.basedata.ProjectWbsService;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年12月03日 
 * @system:
 * 工程项目分解结构
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.基础数据.工程项目分解结构",number="app.ec.basedata.prowbs")
@Controller
@RequestMapping("ec/basedata/prowbs")
public class ProjectWbsEditController extends BaseEditController{
	@Resource
	public ProjectWbsService projectWbsService;
	public AbstractBaseService getService() {
		return projectWbsService;
	}

	protected boolean verifyInput(Object editData) {
		boolean isVerify = true;
		if(editData!=null&&editData instanceof ProjectWbsInfo){
			ProjectWbsInfo mInfo = (ProjectWbsInfo) editData;
			ProWbsType pwt = mInfo.getWbsType();
			if(ProWbsType.FJJG.equals(pwt)){
				isVerify = false;
				setErrorMesg("工程结构类型不能为:"+pwt.getName());
			}else{
				if(mInfo.getParent()!=null){
					//TODO 各种限制 后面补齐
				}else{
					if(!ProWbsType.FBGC.equals(pwt)){
						isVerify = false;
						setErrorMesg("此工程节点的工程结构类型必须为:"+ProWbsType.FBGC.getName());
					}
				}
			}
		}
		
		if(isVerify){
			return super.verifyInput(editData);
		}else{
			return isVerify;
		}
	}
	
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("number"));
		cols.add(new ColumnModel("enabled"));
		cols.add(new ColumnModel("remark"));
		cols.add(new ColumnModel("proStruct",DataTypeEnum.F7,ProStructureInfo.class));
		cols.add(new ColumnModel("project",DataTypeEnum.F7,ProjectInfo.class));
		cols.add(new ColumnModel("parent",DataTypeEnum.F7,ProjectWbsInfo.class));
		cols.add(new ColumnModel("wbsType",DataTypeEnum.ENUM,ProWbsType.class));
		return cols;
	}
	
	public Object createNewData() {
//		request.getParameter("uiCtx") {"tree":{"id":"NiEbtFVyTYGc3jn17axYdpn32P4%3D","name":"江北项目xx街道施工项目","number":"01","longNumber":"01","type":"project"}}
		ProjectWbsInfo info = new ProjectWbsInfo();
		Map uiCtx = getUiCtx();
		if(uiCtx!=null){
			Object treeObj = uiCtx.get("tree");
			if(treeObj!=null){
				Map treeMap = JSONObject.parseObject(treeObj.toString(), new HashMap().getClass());
				Object idObj = treeMap.get("id");
				Object type = treeMap.get("type");
				if(idObj!=null&&type!=null){
					String id = WebUtil.UUID_ReplaceID(idObj.toString());
					if("proStructure".equals(type.toString())){//单位工程结构
						StringBuffer sql = new StringBuffer();
						sql.append(" select a.fid as id,a.fname as name,b.fid as proId,b.fname as proName ");
						sql.append(" from t_ec_proStructure as a");
						sql.append(" left join t_ec_project as b on b.fid = a.fprojectId");
						sql.append(" where a.fid =?");
						try {
							List structs = projectWbsService.executeSQLQuery(sql.toString(), new String[]{id});
							if(structs!=null&&structs.size()>0){
								Map dmap = (Map) structs.get(0);
								Object _id = dmap.get("id");
								Object _name = dmap.get("name");
								if(_id!=null&&_name!=null){
									ProStructureInfo pInfo = new ProStructureInfo();
									pInfo.setId(_id.toString());
									pInfo.setName(_name.toString());
									info.setProStruct(pInfo);
								}
								_id = dmap.get("proId");
								_name = dmap.get("proName");
								if(_id!=null&&_name!=null){
									ProjectInfo pInfo = new ProjectInfo();
									pInfo.setId(_id.toString());
									pInfo.setName(_name.toString());
									info.setProject(pInfo);
								}
							}
						} catch (QueryException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		info.setWbsType(ProWbsType.FBGC);
		info.setEnabled(true);
		return info;
	}

	public CoreBaseInfo getEntityInfo() {
		return new ProjectWbsInfo();
	}
}
