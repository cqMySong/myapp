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
import com.myapp.core.util.WebUtil;
import com.myapp.entity.ec.plan.ProjectPlanReportInfo;
import com.myapp.entity.ec.plan.ProjectPlanReportItemInfo;
import com.myapp.entity.ec.plan.ProjectTotalPlanInfo;

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
	 */
	public List getPlanFinishCompareRpt(String proId) throws QueryException{
		if(BaseUtil.isEmpty(proId)) return null;
		StringBuffer sb = new StringBuffer();
		sb.append(" select pte.fid as id,pst.fdisplayName as dwgc,pwb.fdisplayName as wbs");
		sb.append(" ,pte.fprogress as pprogress,pte.fcontent as content");
		sb.append(" ,pte.fplanBegDate as bd,pte.fplanEndDate as ed,pte.fproQty as proqty");
		sb.append(" ,pte.fdutyers as sgry,pte.fplanDays as days");
		sb.append(" ,rpt.repid as repid,rpt.bd as relbd,rpt.ed as reled,rpt.progress as relprogress");
		sb.append(" from t_ec_projectTotalPlan as pt");
		sb.append(" left join t_ec_projectTotalPlanItem as pte on pte.fprentid=pt.fid ");
		sb.append(" left join t_ec_proStructure as pst on pst.fid=pte.fproStructureId ");
		sb.append(" left join t_ec_projectwbs as pwb on pwb.fid = pte.fprojectWbsId");
		
		sb.append(" left join (");
		sb.append("  SELECT a.fPlanItemId as ptid,a.fid as repid,a.fbegDate AS bd,a.fendDate AS ed,a.fprogress as progress");
		sb.append("  from t_ec_proWorkPlanItem a,t_ec_proWorkPlanReport b");
		sb.append("  where a.fprentid = b.fid and b.fBillState =? and b.fprojectId =?");
		sb.append("  and a.fbegDate is not null and a.fendDate is not null");
		sb.append(" ) AS rpt ON rpt.ptid=pte.fid");
		
		sb.append(" where pt.fBillState = ? and pt.fprojectId=?");
		sb.append(" order by pte.fplanBegDate");
		List params = new ArrayList();
		params.add(BillState.AUDIT.getValue());
		params.add(proId);
		params.add(BillState.AUDIT.getValue());
		params.add(proId);
		
		System.out.println(sb.toString());
//		单位工程	分部工程	分项工程	具体工作内容	生产情况对比	开始时间	截止时间	工程量	施工人员	持续天数
		List<Map> datas = executeSQLQuery(sb.toString(), params.toArray());
		List<Map<String,Object>> ganttDatas = new ArrayList<Map<String,Object>>();
		if(datas!=null&&datas.size()>0){
			Set<String> planSet = new HashSet<String>();
			Map<String,String> ryMap = new HashMap<String,String>();
			for(Map data:datas){
				if(data!=null&&data.size()>0){
					String pid = (String)data.get("id");
					if(!BaseUtil.isEmpty(pid)&&!planSet.contains(pid)){
						Map<String,Object> curData = new HashMap<String,Object>();
						curData.put("dwgc", data.get("dwgc"));
						curData.put("wbs", data.get("wbs"));
						curData.put("progress", data.get("pprogress"));
						curData.put("content", data.get("content"));
						curData.put("item", "进度计划");
						Date bd = obj2Date(data.get("bd"));
						Date ed = obj2Date(data.get("ed"));
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
						List<Map<String,Object>> items = new ArrayList<Map<String,Object>>();
						Map<String,Object> itemData = new HashMap<String,Object>();
						itemData.put("id",WebUtil.UUID2String2(pid));
						itemData.put("from",bdStr);
						itemData.put("to",edStr);
						itemData.put("percent",data.get("pprogress"));
						itemData.put("desc",data.get("content"));
						itemData.put("label",data.get("content"));
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
						Date bd = obj2Date(data.get("relbd"));
						Date ed = obj2Date(data.get("reled"));
						String bdStr = bd!=null?DateUtil.formatDate(bd):"";
						String edStr = bd!=null?DateUtil.formatDate(ed):"";
						curData.put("bd",bdStr);
						curData.put("ed",edStr);
						curData.put("proqty"," --- ");
						curData.put("sgry"," --- ");
						curData.put("days",getDays(data.get("relbd"),data.get("reled")));
						List<Map<String,Object>> items = new ArrayList<Map<String,Object>>();
						Map<String,Object> itemData = new HashMap<String,Object>();
						itemData.put("id",WebUtil.UUID2String2(pid));
						itemData.put("from",bdStr);
						itemData.put("to",edStr);
						itemData.put("percent",data.get("relprogress"));
						itemData.put("desc","  ");
						itemData.put("label","  ");
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
