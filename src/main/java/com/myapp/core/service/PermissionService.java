package com.myapp.core.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.annotation.PermissionItemAnn;
import com.myapp.core.entity.PermissionInfo;
import com.myapp.core.enums.PermissionTypeEnum;
import com.myapp.core.exception.db.SaveException;
import com.myapp.core.model.PermissionModel;
import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.core.util.BaseUtil;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年5月1日 
 * @system:
 *
 *-----------MySong---------------
 */
@Service("permissionService")
public class PermissionService extends BaseInterfaceService<PermissionInfo> {
	
	
	public Map<String, PermissionModel> getAppPermission(HttpServletRequest request){
		 Map<String, PermissionModel> pmMap = new TreeMap<String, PermissionModel>();
		ServletContext servletContext = request.getSession().getServletContext();
		if(servletContext!=null){
			 WebApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			 //获取所有的RequestMapping
		        Map<String, HandlerMapping> requestMappings = 
		        		BeanFactoryUtils.beansOfTypeIncludingAncestors(appContext, HandlerMapping.class, true, false);
		        for (HandlerMapping handlerMapping : requestMappings.values()) {
		        	if (handlerMapping instanceof RequestMappingHandlerMapping)  {
		        		 RequestMappingHandlerMapping requestMappingHandlerMapping = (RequestMappingHandlerMapping) handlerMapping;
		                 Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
		                 for (Map.Entry<RequestMappingInfo, HandlerMethod> requestMappingInfoHandlerMethodEntry : handlerMethods.entrySet()){
		                     RequestMappingInfo requestMappingInfo = requestMappingInfoHandlerMethodEntry.getKey();
		                     HandlerMethod mappingInfoValue = requestMappingInfoHandlerMethodEntry.getValue();
		                     PermissionAnn perann  = mappingInfoValue.getBeanType().getAnnotation(PermissionAnn.class);
		                     PermissionItemAnn perItem = mappingInfoValue.getMethod().getAnnotation(PermissionItemAnn.class);
		                     PatternsRequestCondition patternsCondition = requestMappingInfo.getPatternsCondition();
		                     Set<String> requestUrl = patternsCondition.getPatterns();
		                     if(perann!=null&&perItem!=null&&requestUrl!=null&&requestUrl.size()>0){
		                     	String thisUrl = requestUrl.iterator().next();
		                     	String thisName = perann.name()+"."+perItem.name();
		                     	String thisNumber = perann.number()+"."+perItem.number();
		                     	PermissionTypeEnum thisType = perItem.type();
		                     	PermissionModel thisPm = pmMap.get(thisNumber);
		                     	if(thisPm!=null){
		                     		thisPm.setUrl(thisUrl);
		                     	}else{
		                     		thisPm =  new PermissionModel(thisNumber,thisName,thisUrl,thisType);
		                     	}
		                     	pmMap.put(thisNumber, thisPm);
		                     }
		                 }
		        	}
		        }
		}
		return pmMap;
	}
	
	public Map<String,Integer> syncSysPermission(HttpServletRequest request) throws SaveException{
		Map<String, PermissionModel> appPermissions =  getAppPermission(request);
		Map<String ,PermissionInfo> dbPerInfoMap = new HashMap<String ,PermissionInfo>();
		int total = appPermissions.size();
		int insert = 0;
		int update = 0;
		
		for (String thisCurLongNubmer : appPermissions.keySet()) {
			if(!BaseUtil.isEmpty(thisCurLongNubmer)){
				PermissionModel curPm  = appPermissions.get(thisCurLongNubmer);
				thisCurLongNubmer = thisCurLongNubmer.replaceAll("\\.", "!");
				if(!dbPerInfoMap.containsKey(thisCurLongNubmer)){
					String[] number_split = thisCurLongNubmer.split("!");
					String curNumber = "";
					String[] name_split = curPm.getName().split("\\.");
					for(int i=0;i<number_split.length;i++){
						if(i>0) curNumber += "!";
						curNumber += number_split[i];
						if(dbPerInfoMap.containsKey(curNumber)) continue;
						PermissionInfo curPInfo = getPermissionByLongNumber(curNumber);
						if(curPInfo!=null){
							if(i==number_split.length-1){
								curPInfo.setUrl(curPm.getUrl());
								curPInfo.setType(curPm.getType());
								if(i<name_split.length){
									curPInfo.setName(name_split[i]);
								}
								saveEntity(curPInfo);
								update +=1;
							}
						}else{
							curPInfo = new PermissionInfo();
							curPInfo.setNumber(number_split[i]);
							if(i<name_split.length){
								curPInfo.setName(name_split[i]);
							}
							if(i==number_split.length-1){
								curPInfo.setUrl(curPm.getUrl());
								curPInfo.setType(curPm.getType());
							}
							
							if(i>0) curPInfo.setParent(dbPerInfoMap.get(curNumber.substring(0, curNumber.lastIndexOf("!"))));
							System.out.println("curNumber :::: "+curPInfo.getNumber());
							saveEntity(curPInfo);
							insert+=1;
						}
						dbPerInfoMap.put(curNumber, curPInfo);
					}
				}
			}
		}
		
		Map<String,Integer> reMap = new HashMap<String,Integer>();
		reMap.put("total",total);
		reMap.put("insert",insert);
		reMap.put("update",update);
		return reMap;
	}
	private PermissionInfo getPermissionByLongNumber(String longNumber){
		if(BaseUtil.isEmpty(longNumber)) return null;
		String hql = "from PermissionInfo where longNumber=?";
		return getEntity(hql, new String[]{longNumber});
	}
	
	public static void packageString(String args,Map map){
		if(!map.containsKey(args)){
			map.put(args, args);
			if(args.indexOf("!")>0){
				packageString(args.substring(0, args.lastIndexOf("!")),map);
			}
		}
	}
	
	public static void main(String[] args){
		String strs = "a!b!c!d!e";
		Map m = new TreeMap();
		packageString(strs,m);
		System.out.println(m.values());
	}
	
}
