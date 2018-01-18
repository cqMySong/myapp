package com.myapp.controller.ec.plan.projecttotalplan;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;

import com.myapp.core.annotation.AuthorAnn;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseBillEditImportController;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.BillState;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.enums.ResultTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.model.ResultModel;
import com.myapp.core.model.WebDataModel;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.DateUtil;
import com.myapp.entity.ec.basedata.ProStructureInfo;
import com.myapp.entity.ec.basedata.ProSubInfo;
import com.myapp.entity.ec.basedata.ProSubItemInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.entity.ec.basedata.ProjectWbsInfo;
import com.myapp.entity.ec.plan.ProjectTotalPlanInfo;
import com.myapp.entity.ec.plan.ProjectTotalPlanItemInfo;
import com.myapp.service.ec.plan.ProjectTotalPlanService;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年5月20日 
 * @system: 
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.计划管理.项目总体计划",number="app.ec.plan.projecttotalplan")
@Controller
@RequestMapping("ec/plan/projecttotalplan")
public class ProjectTotalPlanEditController extends BaseBillEditImportController{
	@Resource
	public ProjectTotalPlanService projectTotalPlanService;
	public AbstractBaseService getService() {
		return projectTotalPlanService;
	}

	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("number"));
		cols.add(new ColumnModel("remark"));
		cols.add(new ColumnModel("bizDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("billState",DataTypeEnum.ENUM,BillState.class));
		cols.add(new ColumnModel("createDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("lastUpdateDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("auditDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("project",DataTypeEnum.F7,ProjectInfo.class));
		cols.add(new ColumnModel("createUser",DataTypeEnum.F7,UserInfo.class));
		cols.add(new ColumnModel("lastUpdateUser",DataTypeEnum.F7,UserInfo.class));
		cols.add(new ColumnModel("auditor",DataTypeEnum.F7,UserInfo.class));
		
		ColumnModel planItems = new ColumnModel("planItems",DataTypeEnum.ENTRY,ProjectTotalPlanItemInfo.class);
		planItems.getCols().add(new ColumnModel("id",DataTypeEnum.PK));
		
		ColumnModel psCol = new ColumnModel("proStructure",DataTypeEnum.F7,ProStructureInfo.class);
		psCol.setFormat("id,name,displayName");
		planItems.getCols().add(psCol);
		
		ColumnModel prowbs = new ColumnModel("projectWbs",DataTypeEnum.F7,ProjectWbsInfo.class);
		prowbs.setFormat("id,name,displayName");
		planItems.getCols().add(prowbs);
		
		//作废
		ColumnModel proSubItem = new ColumnModel("proSubItem",DataTypeEnum.F7,"id,name");
		proSubItem.setClaz(ProSubItemInfo.class);
		planItems.getCols().add(proSubItem);
		ColumnModel proSub = new ColumnModel("proSub",DataTypeEnum.F7,"id,name");
		proSub.setClaz(ProSubInfo.class);
		planItems.getCols().add(proSub);
		
		ColumnModel dutyers = new ColumnModel("dutyers",DataTypeEnum.MUTILF7,"id,name");
		dutyers.setClaz(UserInfo.class);
		planItems.getCols().add(dutyers);
		
		planItems.getCols().add(new ColumnModel("planBegDate",DataTypeEnum.DATE));
		planItems.getCols().add(new ColumnModel("planEndDate",DataTypeEnum.DATE));
		planItems.getCols().add(new ColumnModel("planDays",DataTypeEnum.INT));
		planItems.getCols().add(new ColumnModel("realBegDate",DataTypeEnum.DATE));
		planItems.getCols().add(new ColumnModel("realEndDate",DataTypeEnum.DATE));
		planItems.getCols().add(new ColumnModel("realDays",DataTypeEnum.INT));
		planItems.getCols().add(new ColumnModel("progress",DataTypeEnum.NUMBER));
		planItems.getCols().add(new ColumnModel("content"));
		planItems.getCols().add(new ColumnModel("proQty"));
		planItems.getCols().add(new ColumnModel("remark",DataTypeEnum.STRING));
		cols.add(planItems);
		return cols;
	}
	
	protected boolean verifyInput(Object editData) {
		boolean isOk = super.verifyInput(editData);
		if(isOk){
			if(editData!=null&&editData instanceof ProjectTotalPlanInfo){
				ProjectTotalPlanInfo ptInfo = (ProjectTotalPlanInfo) editData;
				ProjectInfo project = ptInfo.getProject();
				if(project==null){
					isOk = false;
					setErrorMesg("项目总进度计划中工程项目不能为空!");
				}else{
					String hql = "from ProjectTotalPlanInfo where project.id=?";
					List params = new ArrayList();
					params.add(project.getId());
					if(!BaseUtil.isEmpty(ptInfo.getId())){
						hql+=" and id!=?";
						params.add(ptInfo.getId());
					}
					try{
						if(projectTotalPlanService.isExist(hql, params.toArray())){
							isOk = false;
							setErrorMesg(project.getName()+"已经存在了项目的总进度计划，不允许重复出现!");
						}
					}catch(Exception e){
						isOk = false;
						e.printStackTrace();
						setExceptionMesg(e.getMessage());
					}
				}
			}
		}
		return isOk;
	}
	
	protected boolean verifyInputSubmit(Object editData) {
		boolean isOk = super.verifyInputSubmit(editData);
		if(isOk){
			if(editData!=null&&editData instanceof ProjectTotalPlanInfo){
				ProjectTotalPlanInfo ptInfo = (ProjectTotalPlanInfo) editData;
				Set<ProjectTotalPlanItemInfo> items = ptInfo.getPlanItems();
				if(items==null||items.size()<=0) {
					isOk = false;
					setErrorMesg("项目总进度计划明细不能为空!");
				}
			}
		}
		return isOk;
	}
	
	@AuthorAnn(doPermission=false)
	@RequestMapping("/checkRemoveItem")
	@ResponseBody
	public WebDataModel checkRemoveItem(){
		return projectTotalPlanService.checkRemoveItemId(request.getParameter("itemId"));
	}
	
	
	public Object createNewData() {
		ProjectTotalPlanInfo proTotalPlanInfo = new ProjectTotalPlanInfo();
		return proTotalPlanInfo;
	}

	public CoreBaseInfo getEntityInfo() {
		return new ProjectTotalPlanInfo();
	}
	
	public String getHeadTitle() {
    	return "项目总进度计划";
    }
	
	public String getSecondTitle() {
		Map uiCtx = getUiCtx();
		String proName = uiCtx.containsKey("proName")?uiCtx.get("proName").toString():"";
		return proName+"_项目总进度计划清单编制";
	}
	
    public int[] getHeadRows() {
    	return new int[]{3,2};
    }
    
    public String getFillRemark() {
    	StringBuffer sb = new StringBuffer();
    	sb.append("\r\n\t 1:单位工程编码与名称必须对应存在且一致!");
    	sb.append("\r\n\t 2:工程分解结构编码与名称必须对应存在且一致!");
    	sb.append("\r\n\t 3:责任人为用户编码，多人用  , 分隔开!");
    	sb.append("\r\n\t 4:计划开始日期和截止日期必须为日期格式[Y-M-D]，且开始日期小于截止日期!");
    	return sb.toString();
    }
    
    public short getRemrkHeight() {
    	return 1350;
    }
    
    public List<ExcelExportEntity> getExportHeader() {
    	List<ExcelExportEntity> headers = new ArrayList<ExcelExportEntity>();
    	headers.add(stringEntity("编码", "proStructure_number","单位工程"));
    	headers.add(stringEntity("名称", "proStructure_name","单位工程"));
    	headers.add(stringEntity("编码", "projectWbs_number","工程分解结构"));
    	headers.add(stringEntity("名称", "projectWbs_name","工程分解结构"));
    	headers.add(stringEntity("开始日期", "planBegDate","计划"));
    	headers.add(stringEntity("截止日期", "planEndDate","计划"));
    	headers.add(stringEntity("责任人", "dutyers"));
    	headers.add(stringEntity("工程量", "proQty"));
    	headers.add(stringEntity("工作内容", "content"));
    	headers.add(stringEntity("备注", "remark"));
    	return headers;
    }
    
    public ExcelImportResult toDoImportData(InputStream in) throws Exception {
    	setAbort(true);
    	return super.toDoImportData(in);
    }
    
    public Map getF7Map(CoreBaseInfo info){
    	return getF7Map(info,"id,name");
    }
    public Map getF7Map(CoreBaseInfo info,String keys){
    	Map ftMap = new HashMap();
    	if(info!=null&&!BaseUtil.isEmpty(keys)){
    		String[] keyss = keys.split(",");
        	for(String key:keyss){
        		ftMap.put(key, info.get(key));
        	}
    	}
    	return ftMap;
    }
    
   private String proId = "";
   public String getCurProId(){
    	if(BaseUtil.isEmpty(proId)){
    		Map params = getParams();
    		if(params!=null&&params.size()>0)
    			proId = (String)params.get("proId");
    	}
    	return proId;
   }
    public ResultModel verifyRowData(int rowIdx, Map rowMap) {
    	ResultModel rm = new ResultModel();
    	if(rowMap!=null&&rowMap.size()>0){
    		String mesg = "";
    		String proId = getCurProId();
    		if(BaseUtil.isEmpty(proId)){
    			mesg +="<br/>当前工程项目为空，不允许导入!";
    		}else{
    			ProStructureInfo psInfo = null;
    			Object obj = rowMap.get("proStructure_number");
    			if(obj==null){
        			mesg +="<br/>单位工程编码为空，不允许导入!"; 
        		}else{
        			String hql = "from ProStructureInfo where number=? and project.id=?";
        			psInfo = getService().getEntity(ProStructureInfo.class, hql, new String[]{obj.toString(),proId});
        			if(psInfo==null){
        				mesg +="<br/>未找到编码为["+obj.toString()+"]的单位工程，不允许导入!"; 
        			}else{
        				obj = rowMap.get("proStructure_name");
        				if(obj!=null&&!obj.toString().equals(psInfo.getName())){
        					mesg +="<br/>单位工程编码["+psInfo.getNumber()+"]与单位工程名称["+obj.toString()+"]不一致，不允许导入!"; 
        				}else{
        					rowMap.put("proStructure", getF7Map(psInfo,"id,name,displayName"));
        				}
        			}
        		}
        		obj = rowMap.get("projectWbs_number");
        		if(obj==null){
        			mesg +="<br/>工程分解结构编码为空，不允许导入!"; 
        		}else{
        			String hql = "from ProjectWbsInfo where number=? and project.id=?";
        			ProjectWbsInfo pwsInfo = getService().getEntity(ProjectWbsInfo.class, hql, new String[]{obj.toString(),proId});
        			if(psInfo==null){
        				mesg +="<br/>未找到编码为["+obj.toString()+"]的工程分解结构，不允许导入!"; 
        			}else{
        				obj = rowMap.get("projectWbs_name");
        				if(obj!=null&&!obj.toString().equals(psInfo.getName())){
        					mesg +="<br/>工程分解结构编码["+psInfo.getNumber()+"]与工程分解结构名称["+obj.toString()+"]不一致，不允许导入!"; 
        				}else{
        					ProStructureInfo wbs2struct = pwsInfo.getProStruct();
        					if(psInfo!=null&&wbs2struct!=null&&!psInfo.getId().equals(wbs2struct.getId())){
        						mesg +="<br/>单位工程编码["+psInfo.getNumber()+"]与工程分解结构对应的单位工程编码["+wbs2struct.getNumber()+"]不一致，不允许导入!"; 
        					}
        					rowMap.put("projectWbs", getF7Map(psInfo,"id,name,displayName"));
        				}
        			}
        		}
        		Object begDateobj = rowMap.get("planBegDate");
        		Date begDate = null;
        		if(begDateobj!=null){
        			begDate = DateUtil.parseDate(begDateobj.toString());
        		}
        		if(begDate==null){
        			mesg +="<br/>计划开始日期为空或者格式有误，不允许导入!"; 
        		}
        		Object endDateobj  = rowMap.get("planEndDate");
        		Date endDate = null;
        		if(endDateobj!=null){
        			endDate = DateUtil.parseDate(endDateobj.toString());
        		}
        		if(endDate==null){
        			mesg +="<br/>计划截止日期为空或者格式有误，不允许导入!"; 
        		}
        		
        		if(begDate!=null&&endDate!=null){
        			if(begDate.after(endDate)){
        				mesg +="<br/>计划开始必须小于计划结束日期，不允许导入!"; 
        			}else{
        				rowMap.put("planDays", DateUtil.betweenDays(endDate, begDate));
        			}
        		}
        		
        		obj = rowMap.get("dutyers");
        		if(obj!=null){
        			String d_number = "'"+obj.toString().replaceAll(",", "','")+"'";
        			String hql = "select u.id as id ,u.name as name from UserInfo as u where u.number in("+d_number+")";
        			List users = getService().findByHQL(hql, null);
        			rowMap.put("dutyers", users);
        		}
    		}
    		
    		if(!BaseUtil.isEmpty(mesg)){
    			rm.setResultType(ResultTypeEnum.ERROR);
    			rm.setMesg("第["+(rowIdx+6)+"]行中:"+mesg);
    		}
    	}
    	
    	return rm;
    }
}
