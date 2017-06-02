package com.myapp.controller.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.myapp.core.base.controller.BaseController;
import com.myapp.core.entity.BaseOrgInfo;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.UserState;
import com.myapp.core.exception.db.QueryException;
import com.myapp.core.model.PageModel;
import com.myapp.core.service.OrgService;
import com.myapp.core.service.UserService;
import com.myapp.core.util.EnumUtil;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年3月12日 
 * @system:
 *    登陆之后的门户首页
 *-----------MySong---------------
 */
@Controller
@RequestMapping("/main")
public class MainController extends BaseController {
	@Resource
	public OrgService orgService;
	@Resource
	public UserService userService;
	
	
	@RequestMapping("/index")
	public ModelAndView index(){
		Map params = new HashMap();
//		try {
//			String arias ="_t";
//			DetachedCriteria dca = DetachedCriteria.forClass(UserInfo.class,arias);
//			ProjectionList pList = Projections.projectionList();  
//			pList.add(Projections.property(arias+".id").as("id"));
//			pList.add(Projections.property(arias+".name").as("name"));
//			pList.add(Projections.property(arias+".number").as("number"));
//			dca.createAlias(arias+".defOrg", "defOrg");
//			dca.setFetchMode(arias+".defOrg",FetchMode.JOIN);
//			pList.add(Projections.property("defOrg.id").as("defOrgId"));
//			dca.setProjection(pList);
//			dca.add(Restrictions.like("name", "宋军%"));
//			dca.addOrder(Order.desc("number").asc("name"));
////			dca.setResultTransformer(Transformers.aliasToBean(UserInfo.class));
////			dca.setProjection(Property.forName("name").like("宋军", MatchMode.END));
//			
//			List<UserInfo> us = userService.findByDetachedCriteria(dca);
//			System.out.println(us.size());
//			for(UserInfo u:us){
//				System.out.println(u.getName()+" == "+u.getNumber());
//			}
//			
//			PageModel pms = userService.toPageDetachedCriteria(dca,pList, 1, 10);
//			System.out.println(pms.getDatas());
//			PageModel pms2 = userService.toPageDetachedCriteria(dca,pList, 2, 10);
//			System.out.println(pms2.getDatas());
//			
//		} catch (QueryException e) {
//			e.printStackTrace();
//		}
		
		
		return toPage("main/main", params);
	}
	
	@RequestMapping("/home")
	public ModelAndView toHome(){
		Map params = new HashMap();
		return toPage("main/home", params);
	}
	
	public static void main(String[] args){
		UserInfo uInfo = new UserInfo();
		uInfo.setUserState(UserState.DISABLE);
		uInfo.put("userState", EnumUtil.getEnum(UserState.class.getName(), "DISABLE"));
//		uInfo.setCreateDate(new Date());
		BaseOrgInfo orgInfo = new BaseOrgInfo();
		orgInfo.setName("asdfald啊塑料袋放进");
		uInfo.setDefOrg(orgInfo);
		System.out.println(uInfo.get("defOrg"));
	}
	
}
