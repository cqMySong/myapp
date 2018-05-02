package com.myapp.controller.ec.basedata.imagedata;

import com.alibaba.fastjson.JSONObject;
import com.aspose.slides.p2cbca448.and;
import com.aspose.slides.p2cbca448.fid;
import com.myapp.core.annotation.AuthorAnn;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseListController;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.enums.ImageDataType;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.model.WebDataModel;
import com.myapp.core.util.BaseUtil;
import com.myapp.entity.ec.basedata.ProBaseWbsInfo;
import com.myapp.entity.ec.basedata.ProBatchTestInfo;
import com.myapp.entity.ec.basedata.ProStructureInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.service.ec.basedata.ImageDataService;
import com.myapp.service.ec.basedata.ProjectService;
import com.myapp.service.ec.labour.LabourEnterService;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
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
	@Resource
	public ProjectService projectService;
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
		cols.add(new ColumnModel("proBaseWbs",DataTypeEnum.F7,ProBaseWbsInfo.class));
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
					}else if("proStructure".equals(type.toString())){
						se = Restrictions.eq("proStructure.id", idObj.toString());
					}else if("jyphf".equals(type.toString())){
						se = Restrictions.eq("proBaseWbs.id", treeMap.get("parentId").toString());
						SimpleExpression jyphf = Restrictions.eq("proBatchTest.id",idObj.toString());
						query.add(jyphf);
					}else{
						se = Restrictions.eq("proBaseWbs.id", idObj.toString());
						query.add(Restrictions.isNull("proBatchTest.id"));
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

	//项目单位工程结构树
	@AuthorAnn(doLongin=true,doPermission=false)
	@RequestMapping(value="/image/data/tree")
	@ResponseBody
	public WebDataModel treeData() {
		try{
			Map params = new HashMap();
			List<Map> orgList = projectService.getProjectTreeData(params);//项目组织树
			if(orgList!=null&&orgList.size()>0){
				StringBuffer projectIdList = new StringBuffer();
				for(Map projectMap:orgList){
					if("project".equals(projectMap.get("type").toString())){
						projectIdList.append("'").append(projectMap.get("id").toString()).append("',");
					}
				}
				String projectIdStr = projectIdList.toString().substring(0,projectIdList.toString().length()-1);
				//添加项目单位工程
				StringBuffer sql = new StringBuffer();
				sql.append(" select a.fid as id,a.fnumber as number,a.fname as name,case when a.fprentid is null then a.fprojectId else a.fprentid end as parentId,a.flongnumber as longNumber,'proStructure' as type");
				sql.append(" from t_ec_proStructure as a");
				sql.append(" left join t_ec_project as b on b.fid = a.fprojectId");
				sql.append(" where a.fprojectId in (").append(projectIdStr).append(") ");
				sql.append(" order by a.flongnumber asc");
				List orgStruct = projectService.executeSQLQuery(sql.toString(), null);
				if(orgStruct!=null&&orgStruct.size()>0){
					orgList.addAll(orgStruct);
				}
				//添加项目单位工程
				StringBuffer sqlWbs = new StringBuffer();
				sqlWbs.append(" select a.fid as id,a.fnumber as number,a.fname as name,case when a.fprentid is null then a.fproStructId else a.fprentid end as parentId,a.flongnumber as longNumber,a.fwbstype as type");
				sqlWbs.append(" from t_ec_projectwbs as a");
				sqlWbs.append(" left join t_ec_project as b on b.fid = a.fprojectId");
				sqlWbs.append(" where a.fprojectId in (").append(projectIdStr).append(") ");
				sqlWbs.append(" order by a.flongnumber asc");
				List wbsStruct = projectService.executeSQLQuery(sqlWbs.toString(), null);
				if(wbsStruct!=null&&wbsStruct.size()>0){
					orgList.addAll(wbsStruct);
				}
				//检验批划分
				StringBuffer jyphf = new StringBuffer();
				jyphf.append(" select a.fid as id,a.fname as name,b.fid as parentId,concat(b.fnumber,'.',a.fnumber) as longNumber,'jyphf' as type ");
				jyphf.append(" from t_ec_probathchtest a,t_ec_projectwbs b");
				jyphf.append(" where a.fprowbsId = b.fbasewWbsId and b.fprojectId in (").append(projectIdStr).append(") ");
				jyphf.append(" order by longNumber asc");
				List jyphfList = projectService.executeSQLQuery(jyphf.toString(), null);
				if(jyphfList!=null&&jyphfList.size()>0){
					orgList.addAll(jyphfList);
				}
				data = orgList;
			}
		}catch(Exception e){
			e.printStackTrace();
			setErrorMesg(e.getMessage());
		}
		return ajaxModel();
	}

}
