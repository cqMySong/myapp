package com.myapp.controller.ec.basedata.skillitem;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.AuthorAnn;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.annotation.PermissionItemAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseListController;
import com.myapp.core.controller.BasePageListController;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.enums.PermissionTypeEnum;
import com.myapp.core.exception.db.QueryException;
import com.myapp.core.exception.db.SaveException;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.model.PageModel;
import com.myapp.core.model.WebDataModel;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.WebUtil;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.entity.ec.basedata.SkillClassInfo;
import com.myapp.enums.ec.SkillType;
import com.myapp.service.ec.basedata.ProSkillDisclosureService;
import com.myapp.service.ec.plan.ProjectPlanReportService;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@PermissionAnn(name="系统管理.现场管理.技术交底.施工技术交底台账",number="app.ec.skilldisclosure.proqmskillledger")
@Controller
@RequestMapping("ec/skilldisclosure/proqmskillledger")
public class ProSkillDisclosureQMLedgerController extends BasePageListController {
	
	@Resource
	public ProSkillDisclosureService proSkillDisclosureService;
	@PermissionItemAnn(name="查看",number="queryLedger",type= PermissionTypeEnum.PAGE)
	@RequestMapping("/list")
	public ModelAndView analysisList(){
		Map params = new HashMap();
		return toPage("ec/skilldisclosure/proqmSkillLedger", params);
	}
	@Override
	public AbstractBaseService getService() {
		return this.proSkillDisclosureService;
	}
	@AuthorAnn(doLongin=true,doPermission=false)
	@RequestMapping(value="/query")
	@ResponseBody
	public WebDataModel materialAnalysis(){
		init();
		String search = request.getParameter("search");
		String projectId = "-1";
		Map<String,Object> params = new HashMap<>();
		if(!BaseUtil.isEmpty(search)) {
			Map searchMap = JSONObject.parseObject(search, new HashMap().getClass());
			if(searchMap!=null&&searchMap.get("projectId")!=null){
				projectId = searchMap.get("projectId").toString();
			}
			if(searchMap!=null&&searchMap.get("directorName")!=null){
				params.put("directorName",searchMap.get("directorName"));
			}
			if(searchMap!=null&&searchMap.get("sendee")!=null){
				params.put("sendee",searchMap.get("sendee"));
			}
		}
		params.put("projectId",projectId);
		try {
			this.data = proSkillDisclosureService.queryProqmSkilllLedger(getCurPage(),getPageSize(),params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ajaxModel();
	}
}
