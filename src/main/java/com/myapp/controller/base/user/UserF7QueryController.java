package com.myapp.controller.base.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.AuthorAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BasePageListController;
import com.myapp.core.model.WebDataModel;
import com.myapp.core.service.UserService;
import com.myapp.core.util.BaseUtil;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年7月7日 
 * @system:
 * 修改成左树（组织树）右表的结构
 *-----------MySong---------------
 */
@Controller
@RequestMapping("base/userf7")
public class UserF7QueryController extends BasePageListController {

	@Resource
	public UserService userService;
	public AbstractBaseService getService() {
		return this.userService;
	}
	@AuthorAnn(doPermission=false)
	@RequestMapping("/f7show")
	public ModelAndView f7show(){
		Map params = new HashMap();
		return toPage(getF7URL(), params);
	}
	
	@AuthorAnn(doPermission=false)
	@RequestMapping(value="/f7Data")
	@ResponseBody
	public WebDataModel toList() {
		try {
			init();
			StringBuffer sql = new StringBuffer();
			List params = new ArrayList();
			String curFln="";
			String serach = request.getParameter("search");
			if(!BaseUtil.isEmpty(serach)){
				Map searchMap = JSONObject.parseObject(serach, new HashMap().getClass());
				Object objTree = searchMap.get("tree");
				if(objTree!=null){
					Map treeMap = JSONObject.parseObject(objTree.toString(), new HashMap().getClass());
					Object lnObj = treeMap.get("longNumber");
					if(lnObj!=null){
						curFln = lnObj.toString();
					}
				}
			}
//			select o.fdisplayname,o.fname,o.flongnumber,u.fname 
//			from t_pm_userPosition ups 
//			
//			
//			where o.fid is not null;
			
			sql.append("select o.fid as org_id,o.fnumber as org_number,o.fname as org_name,o.fdisplayname as org_displayname");
			sql.append(" ,p.fid as position_id,p.fnumber as position_number,p.fname as position_name");
			sql.append(" ,u.fid as id,u.fnumber as number,u.fname as name");
			sql.append(" from t_pm_userPosition as ups");
			sql.append(" left join t_base_org as o on o.fid = ups.forgId");
			sql.append(" left join t_pm_user as u on u.fid = ups.fprentid");
			sql.append(" left join t_pm_position as p on p.fid = ups.fpositionId");
			sql.append(" where o.fid is not null and ups.fisAdmin=1");
			if(!BaseUtil.isEmpty(curFln)){
				sql.append(" and o.flongnumber like '"+curFln+"%'");
			}
			data = getService().toPageSqlQuery(getCurPage(), getPageSize(),sql.toString(),params.toArray());
		} catch (Exception e) {
			e.printStackTrace();
			setExceptionMesg(e.getMessage());
		}
		return ajaxModel();
	}
	
	public String getF7URL() {
		return "user/userTreeQuery";
	}
	public String getUIWinTitle() {
		return "系统用户";
	}

}
