package com.myapp.controller.ec.basedata.prosubitem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.dao.MyResultTransFormer;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseEditController;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.exception.db.QueryException;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.util.WebUtil;
import com.myapp.entity.ec.basedata.ProStructureInfo;
import com.myapp.entity.ec.basedata.ProSubInfo;
import com.myapp.entity.ec.basedata.ProSubItemInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.service.ec.basedata.ProSubItemService;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年5月20日 
 * @system:
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.基础数据.项目分部分项",number="app.ec.basedata.prosubitem")
@Controller
@RequestMapping("ec/basedata/prosubitem")
public class ProSubItemEditController extends BaseEditController{
	@Resource
	public ProSubItemService proSubItemService;
	
	public AbstractBaseService getService() {
		return proSubItemService;
	}

	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("number"));
		cols.add(new ColumnModel("enabled"));
		cols.add(new ColumnModel("remark"));
		ColumnModel proSub = new ColumnModel("proSub",DataTypeEnum.F7,"id,name");
		proSub.setClaz(ProSubInfo.class);
		cols.add(proSub);
		ColumnModel proStruct = new ColumnModel("proStruct",DataTypeEnum.F7,"id,name");
		proStruct.setClaz(ProStructureInfo.class);
		cols.add(proStruct);
		ColumnModel project = new ColumnModel("project",DataTypeEnum.F7,"id,name");
		project.setClaz(ProjectInfo.class);
		cols.add(project);
		return cols;
	}
	
	public Object createNewData() {
		ProSubItemInfo proSubInfo = new ProSubItemInfo();
		Map uiCtx = getUiCtx();
		if(uiCtx!=null){
			Object proSubObj = uiCtx.get("proSub");
			if(proSubObj!=null){
				Map proSubMap = JSONObject.parseObject(proSubObj.toString(), new HashMap().getClass());
				Object idObj = proSubMap.get("id");
				Object name = proSubMap.get("name");
				if(idObj!=null&&name!=null){
					try {
						Criteria query = proSubItemService.initQueryCriteria(ProSubInfo.class);
						query.createAlias("project", "project", JoinType.LEFT_OUTER_JOIN);
						query.createAlias("proStruct", "proStruct", JoinType.LEFT_OUTER_JOIN);
						ProjectionList props = Projections.projectionList();
						props.add(createBaseField("id"));
						props.add(createBaseField("name"));
						props.add(createBaseField("project.id","project_id"));
						props.add(createBaseField("project.name","project_name"));
						props.add(createBaseField("proStruct.id","proStruct_id"));
						props.add(createBaseField("proStruct.name","proStruct_name"));
						query.setProjection(props);
						query.add(Restrictions.eq("id", WebUtil.UUID_ReplaceID(idObj.toString())));
						query.setResultTransformer(new MyResultTransFormer(ProSubInfo.class));
						List datas = query.list();
						if(datas!=null&&datas.size()>0){
							Map dm = (Map) datas.get(0);
							ProjectInfo pInfo = new ProjectInfo();
							pInfo.setId((String)dm.get("project_id"));
							pInfo.setName((String)dm.get("project_name"));
							proSubInfo.setProject(pInfo);
							ProStructureInfo pstInfo = new ProStructureInfo();
							pstInfo.setId((String)dm.get("proStruct_id"));
							pstInfo.setName((String)dm.get("proStruct_name"));
							proSubInfo.setProStruct(pstInfo);
//							
							ProSubInfo psInfo = new ProSubInfo();
							psInfo.setId((String)dm.get("id"));
							psInfo.setName((String)dm.get("name"));
							proSubInfo.setProSub(psInfo);
						}
					} catch (QueryException e) {
						e.printStackTrace();
					}
				}
			}else{
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
								List structs = proSubItemService.executeSQLQuery(sql.toString(), new String[]{id});
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
		}
		proSubInfo.setEnabled(true);
		return proSubInfo;
	}

	public CoreBaseInfo getEntityInfo() {
		return new ProSubItemInfo();
	}
}
