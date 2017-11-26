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
import com.myapp.core.exception.db.QueryException;
import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.DateUtil;
import com.myapp.core.util.WebUtil;
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
		sb.append(" select pte.fid as id,pst.fname as dwgc,ps.fname as fbgc");
		sb.append(" ,psi.fname as fxgc,pte.fcontent as content");
		sb.append(" ,pte.fplanBegDate as bd,pte.fplanEndDate as ed,pte.fproQty as proqty");
		sb.append(" ,pte.fdutyers as sgry,pte.fplanDays as days");
		sb.append(" ,rpt.repid as repid,rpt.bd as relbd,rpt.ed as reled");
		sb.append(" from t_ec_projectTotalPlan as pt");
		sb.append(" left join t_ec_projectTotalPlanItem as pte on pte.fprentid=pt.fid ");
		sb.append(" left join t_ec_proStructure as pst on pst.fid=pte.fproStructureId ");
		sb.append(" left join t_ec_proSub as ps on ps.fid=pte.fproSubId ");
		sb.append(" left join t_ec_proSubItem as psi on psi.fid=pte.fproSubItemId ");
		sb.append(" left join (");
		sb.append("  SELECT a.fPlanItemId as ptid,a.fid as repid,a.fbegDate AS bd,a.fendDate AS ed");
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
						curData.put("fbgc", data.get("fbgc"));
						curData.put("fxgc", data.get("fxgc"));
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
						itemData.put("percent",-1);
						itemData.put("desc",data.get("content"));
						itemData.put("label",data.get("content"));
						items.add(itemData);
						curData.put("values", items);
						ganttDatas.add(curData);
						planSet.add(pid);
					}
					sb.append(" ,rpt.repid as repid,rpt.bd as relbd,rpt.ed as reled");
					String repid = (String)data.get("repid");
					if(!BaseUtil.isEmpty(repid)&&!planSet.contains(repid)){
						Map<String,Object> curData = new HashMap<String,Object>();
						curData.put("dwgc", "   ");
						curData.put("fbgc", "   ");
						curData.put("fxgc", "   ");
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
						itemData.put("percent",-1);
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
	
	
	
}
