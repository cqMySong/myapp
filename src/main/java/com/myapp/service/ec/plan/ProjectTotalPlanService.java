package com.myapp.service.ec.plan;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.myapp.core.enums.BillState;
import com.myapp.core.exception.db.DeleteException;
import com.myapp.core.exception.db.QueryException;
import com.myapp.core.exception.db.ReadException;
import com.myapp.core.model.WebDataModel;
import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.DateUtil;
import com.myapp.core.util.EnumUtil;
import com.myapp.core.util.WebUtil;
import com.myapp.entity.ec.plan.ProjectPlanReportInfo;
import com.myapp.entity.ec.plan.ProjectPlanReportItemInfo;
import com.myapp.entity.ec.plan.ProjectTotalPlanInfo;
import com.myapp.enums.ec.CauseType;
import com.myapp.enums.ec.YesNoEnum;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年6月22日 
 * @system:
 *  工程项目的总体计划
 *-----------MySong---------------
 */
@Service("projectTotalPlanService")
public class ProjectTotalPlanService extends BaseInterfaceService<ProjectTotalPlanInfo> {
	
	/**
	 * 获取工程项目的总计划与实际完成情况对比情况
	 * @param proId 项目组织id
	 * @throws QueryException 
	 * “绿色” :success(提前完成) ，“黄色”:warning(如期完成)，“红色”: danger(延期完成)
	 */
	public List getPlanFinishCompareRpt(String proId,String begDate,String endDate) throws QueryException{
		if(BaseUtil.isEmpty(proId)) return null;
		List params = new ArrayList();
		StringBuffer sb = new StringBuffer();
		sb.append(" select pte.fid as id,pst.fdisplayName as dwgc,pwb.fdisplayName as wbs");
		sb.append(" ,pte.fprogress as pprogress,pte.fcontent as content");
		sb.append(" ,pte.fplanBegDate as bd,pte.fplanEndDate as ed");
		sb.append(" ,pte.frealBegDate as prbd,pte.frealEndDate as pred");
		sb.append(" ,pte.fdutyers as sgry,pte.fplanDays as days,pte.fproQty as proqty");
		sb.append(" ,rpt.repid as repid,rpt.bd as relbd,rpt.ed as reled,rpt.progress as relprogress");
		//处理办法
		sb.append(" ,rpt.dn as dn,rpt.dc as dc,rpt.mc as mc,rpt.mtd as mtd,rpt.td as td,rpt.dd as dd,rpt.dwp as dwp");
		//dn,dc,mc,mtd,td,dd,dwp
		sb.append(" from t_ec_projectTotalPlan as pt");
		sb.append(" left join t_ec_projectTotalPlanItem as pte on pte.fprentid=pt.fid ");
		sb.append(" left join t_ec_proStructure as pst on pst.fid=pte.fproStructureId ");
		sb.append(" left join t_ec_projectwbs as pwb on pwb.fid = pte.fprojectWbsId");
		
		sb.append(" left join (");
		sb.append("  SELECT a.fPlanItemId as ptid,a.fid as repid,a.fbegDate AS bd,a.fendDate AS ed,a.fprogress as progress");
		sb.append(" ,a.fdelayNature as dn,a.fdelayCuse as dc,a.fmeetContent as mc,a.fmeetTodo as mtd,a.ftoDo as td,a.fdoDelay as dd,a.fdoWorkPay as dwp");
		sb.append("  from t_ec_proWorkPlanItem a,t_ec_proWorkPlanReport b");
		sb.append("  where a.fprentid = b.fid and b.fBillState =? and b.fprojectId =?");
		params.add(BillState.AUDIT.getValue());
		params.add(proId);
		sb.append("  and a.fbegDate is not null");
		sb.append(" ) AS rpt ON rpt.ptid=pte.fid");
		
		sb.append(" where pt.fBillState = ? and pt.fprojectId=?");
		params.add(BillState.AUDIT.getValue());
		params.add(proId);
		if(BaseUtil.isNotEmpty(begDate)){
			sb.append(" and pte.fplanBegDate>=?");
			params.add(begDate);
		}
		if(BaseUtil.isNotEmpty(endDate)){
			sb.append(" and pte.fplanBegDate<=?");
			params.add(endDate);
		}
		
		sb.append(" order by pte.fplanBegDate");
		
		
		System.out.println(sb.toString());
//		单位工程	分部工程	分项工程	具体工作内容	生产情况对比	开始时间	截止时间	工程量	施工人员	持续天数
		List<Map> datas = executeSQLQuery(sb.toString(), params.toArray());
		List<Map<String,Object>> ganttDatas = new ArrayList<Map<String,Object>>();
		
		Date curEndDate = DateUtil.parseDate(endDate!=null?endDate:DateUtil.formatDate(new Date()));
		
		if(datas!=null&&datas.size()>0){
			Set<String> planSet = new HashSet<String>();
			Map<String,String> ryMap = new HashMap<String,String>();
			for(Map data:datas){
				if(data!=null&&data.size()>0){
					String pid = (String)data.get("id");
					Date bd = obj2Date(data.get("bd"));
					Date ed = obj2Date(data.get("ed"));
					Date pred = obj2Date(data.get("pred"));
					if(pred==null) pred = curEndDate;
					if(!BaseUtil.isEmpty(pid)&&!planSet.contains(pid)){
						Map<String,Object> curData = new HashMap<String,Object>();
						curData.put("dwgc", data.get("dwgc"));
						curData.put("wbs", data.get("wbs"));
						curData.put("progress", data.get("pprogress"));
						curData.put("content", data.get("content"));
						curData.put("item", "进度计划");
						
						String bdStr = bd!=null?DateUtil.formatDate(bd):"";
						String edStr = bd!=null?DateUtil.formatDate(ed):"";
						curData.put("bd",bdStr);
						curData.put("ed",edStr);
						curData.put("proqty",data.get("proqty"));
						String sgry = (String)data.get("sgry");
						if(!BaseUtil.isEmpty(sgry)){
							String[] ryIds = sgry.split(",");
							sgry = "";
							for(String ryid:ryIds){
								String name = "";
								if(ryMap.containsKey(ryid)){
									name = ryMap.get(ryid);
								}else{
									name = getSgRyName(ryid);
									ryMap.put(ryid, name);
								}
								if(!BaseUtil.isEmpty(name)){
									if(!BaseUtil.isEmpty(sgry)) sgry+=",";
									sgry +=name;
								}
							}
						}
						curData.put("sgry",sgry);
						curData.put("days",data.get("days"));
						//处理了办法 dn,dc,mc,mtd,td,dd,dwp
						curData.put("dn", "");
						curData.put("dc", "");
						curData.put("mc", "");
						curData.put("mtd","");
						curData.put("td", "");
						curData.put("dd", "");
						curData.put("dwp", "");
						List<Map<String,Object>> items = new ArrayList<Map<String,Object>>();
						Map<String,Object> itemData = new HashMap<String,Object>();
						itemData.put("id",WebUtil.UUID2String2(pid));
						itemData.put("from",bdStr);
						itemData.put("to",edStr);
						itemData.put("percent",data.get("pprogress"));
						itemData.put("desc",data.get("content"));
						itemData.put("label",data.get("content"));
						String customClass = "success";
						if(ed!=null){
							if(pred!=null) {
								int c = DateUtil.compareDate(pred, ed);
								if(c==1){
									customClass = "danger";
								}else if(c==-1){
									customClass = "success";
								}else{
									customClass = "warning";
								}
							}
						}
						itemData.put("customClass",customClass);
						items.add(itemData);
						curData.put("values", items);
						ganttDatas.add(curData);
						planSet.add(pid);
					}
					String repid = (String)data.get("repid");
					if(!BaseUtil.isEmpty(repid)&&!planSet.contains(repid)){
						Map<String,Object> curData = new HashMap<String,Object>();
						curData.put("dwgc", "   ");
						curData.put("wbs",  "   ");
						curData.put("progress", data.get("relprogress"));
						curData.put("content", "   ");
						curData.put("item", "实际完成");
						Date rbd = obj2Date(data.get("relbd"));
						Date red = obj2Date(data.get("reled"));
						String bdStr = rbd!=null?DateUtil.formatDate(rbd):"";
						String edStr = red!=null?DateUtil.formatDate(red):"";
						curData.put("bd",bdStr);
						curData.put("ed",edStr);
						curData.put("proqty"," --- ");
						curData.put("sgry"," --- ");
						curData.put("days",getDays(data.get("relbd"),data.get("reled")));
						
						//处理了办法 dn,dc,mc,mtd,td,dd,dwp
						curData.put("dn", data.get("dn"));
						curData.put("dc", toCauseType(data.get("dc")));
						curData.put("mc", data.get("mc"));
						curData.put("mtd",data.get("mtd"));
						curData.put("td", data.get("td"));
						curData.put("dd", toYesNoEnum(data.get("dd")));
						curData.put("dwp", toYesNoEnum(data.get("dwp")));
						
						List<Map<String,Object>> items = new ArrayList<Map<String,Object>>();
						Map<String,Object> itemData = new HashMap<String,Object>();
						itemData.put("id",WebUtil.UUID2String2(pid));
						itemData.put("from",bdStr);
						itemData.put("to",edStr);
						itemData.put("percent",data.get("relprogress"));
						itemData.put("desc","  ");
						itemData.put("label","  ");
						String customClass = "success";
						if(ed!=null){
							int c = DateUtil.compareDate(red!=null?red:curEndDate, ed);
							if(c==1){
								customClass = "danger";
							}else if(c==-1){
								customClass = "success";
							}else{
								customClass = "warning";
							}
						}
						itemData.put("customClass",customClass);
						
						items.add(itemData);
						curData.put("values", items);
						ganttDatas.add(curData);
						planSet.add(repid);
					}
				}
			}
		}
		return ganttDatas;
	}
	
	private String toCauseType(Object obj){
		if(obj==null) return "";
		CauseType ct = EnumUtil.getEnum(CauseType.class.getName(), obj.toString());
		if(ct!=null) return ct.getName();
		return "";
	}
	private String toYesNoEnum(Object obj){
		if(obj==null) return "";
		YesNoEnum ct = EnumUtil.getEnum(YesNoEnum.class.getName(), obj.toString());
		if(ct!=null) return ct.getName();
		return "";
	}
	
	public String getSgRyName(String ryId) throws QueryException{
		if(BaseUtil.isEmpty(ryId)) return "";
		String sql = "select u.fname as name from t_pm_user as u where u.fid=?";
		List params = new ArrayList();
		params.add(ryId);
		List<Map> datas = executeSQLQuery(sql, params.toArray());
		if(datas!=null&&datas.size()>0){
			return (String)datas.get(0).get("name");
		}
		return "";
	}
	
	public Date obj2Date(Object obj){
		Date date= null;
		if(obj!=null){
			if(obj instanceof String){
				date = DateUtil.parseDate(obj.toString());
			}else if(obj instanceof Date){
				date = DateUtil.parseDate(DateUtil.formatDate((Date)obj));
			}
		}
		return date;
	}
	
	public Integer getDays(Object bdObj,Object edObj){
		if(bdObj==null||edObj==null) return null;
		Integer days = null;
		Date bd = obj2Date(bdObj);
		Date ed = obj2Date(edObj);
		if(bd!=null&&ed!=null){
			days = Integer.valueOf(DateUtil.betweenDays(ed, bd));
		}
		return days;
	}
	
	public WebDataModel checkRemoveItemId(String itemId){
		WebDataModel wdm = new WebDataModel();
		wdm.setData(null);
		int code = 0;
		String mesg = "";
		if(!BaseUtil.isEmpty(itemId)){
			String hql = " from ProjectPlanReportItemInfo where planItemId=?";
			try{
				if(isExist(ProjectPlanReportItemInfo.class, hql, new String[]{itemId})){
					code = -1;
					mesg = "已经存在了此工作项的工作汇报情况，不允许删除此项的计划!";
				}
			}catch(Exception e){
				code = -1;
				e.printStackTrace();
				mesg = "查询检索失败!";
			}
		}
		wdm.setStatusCode(code);
		wdm.setStatusMesg(mesg);
		return wdm;
	}
	
	public void deleteEntity(Object entity) throws DeleteException {
		if(entity!=null&&entity instanceof ProjectTotalPlanInfo){
			String ptId = ((ProjectTotalPlanInfo)entity).getId();
			if(!BaseUtil.isEmpty(ptId)){
				String hql = "from ProjectPlanReportInfo where planInfo.id=?";
				try{
					if(isExist(ProjectPlanReportInfo.class, hql, new String[]{ptId})){
						throw new DeleteException("已经存在了此项目进度计划的工作汇报，不允许删除!");
					}
				}catch(QueryException e){
					e.printStackTrace();
				}catch(ReadException e){
					e.printStackTrace();
				}
			}
		}
		super.deleteEntity(entity);
	}
	
	//列表界面中使用的通过id直接删除：在抽象类删除对象都是要处理成对象的 不如在此就转换成对象 不用反复操作
	public void deleteEntity(String id) throws DeleteException {
		if(!BaseUtil.isEmpty(id)){
			deleteEntity(getEntity(id));
		}
	}
}
