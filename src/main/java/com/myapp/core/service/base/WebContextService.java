package com.myapp.core.service.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.base.setting.SystemConstant;
import com.myapp.core.entity.BaseOrgInfo;
import com.myapp.core.entity.JobDutyInfo;
import com.myapp.core.entity.PermissionInfo;
import com.myapp.core.entity.PositionInfo;
import com.myapp.core.entity.PositionJobDutyInfo;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.entity.UserPositionInfo;
import com.myapp.core.exception.db.QueryException;
import com.myapp.core.model.MyWebContext;
import com.myapp.core.service.MainMenuService;
import com.myapp.core.service.PermissionAssignService;
import com.myapp.core.util.BaseUtil;


/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年9月24日 
 * @system:
 * 上下文处理
 *-----------MySong---------------
 */
@Transactional
@Service("webContextService")
public class WebContextService extends AbstractBaseService{
	@Resource
	public PermissionAssignService permissionAssignService;
	@Resource
	public MainMenuService mainMenuService;
	
	public void initWebContext(HttpServletRequest request,UserInfo uInfo) throws QueryException{
		clearWebContext(request);
		if(uInfo!=null){
			MyWebContext myWebCtx = new MyWebContext();
			myWebCtx.setUserId(uInfo.getId());
			myWebCtx.setUserName(uInfo.getName());
			myWebCtx.setUserNumber(uInfo.getNumber());
			myWebCtx.setLinker(uInfo.getLinkers());
			myWebCtx.setAdmin(uInfo.getAdmin());
			myWebCtx.setSysUser(uInfo.getSysUser());
			BaseOrgInfo org = uInfo.getDefOrg();
			if(org!=null){
				myWebCtx.setOrgId(org.getId());
				myWebCtx.setOrgName(org.getName());
			}
			Set<UserPositionInfo> ups = uInfo.getPositionItems();
			//此处先暂时通过对象的级联查找对应的工作职责，如果后期速度问题，在改为sql查询
			if(ups.size()>0){
				List<Map<String, Object>> positions = new ArrayList<Map<String,Object>>();
				for(UserPositionInfo upInfo:ups){
					PositionInfo pInfo = upInfo.getPosition();
					if(pInfo!=null){
						Map<String,Object> pm = new HashMap<String,Object>();
						pm.put("id", pInfo.getId());
						pm.put("name",pInfo.getName());
						pm.put("isMain",upInfo.getMain());//只要岗位
						pm.put("respible",pInfo.getRespible());//负责人岗位
						String orgName = "";
						if(pInfo.getOrg()!=null) orgName = pInfo.getOrg().getDisplayName();
						pm.put("org", orgName);//岗位对应的部门
						if(upInfo.getMain()){
							positions.add(0,pm);
							myWebCtx.setMainPosition(pInfo.getName());
						}else{
							positions.add(pm);
						}
						Set<PositionJobDutyInfo> jobItems = pInfo.getJobDutyItems();
						if(jobItems.size()>0){
							List<Map<String, String>> jobItem = new ArrayList<Map<String,String>>(); 
							for(PositionJobDutyInfo p_jobDutyInfo:jobItems){
								JobDutyInfo jobDutyInfo = p_jobDutyInfo.getJobDuty();
								if(jobDutyInfo==null) continue;
								Map<String, String> item = new HashMap<String, String>();
								item.put("id", jobDutyInfo.getId());
								item.put("name", jobDutyInfo.getName());
								item.put("remark", jobDutyInfo.getRemark());
								PermissionInfo pmenuInfo = jobDutyInfo.getShortCutMenu();
								if(pmenuInfo!=null){
									String dn = pmenuInfo.getDisplayName();
									String[] dns = dn.split("_");
									if(dns.length>1){
										dn = dns[dns.length-2];
									}
									item.put("menuName", dn);
									item.put("menuId", pmenuInfo.getId());
									item.put("menuUrl", pmenuInfo.getUrl());
								}
								jobItem.add(item);
							}
							pm.put("jobItems", jobItem);
						}
						System.out.println(jobItems.size());
					}
					myWebCtx.setPositions(positions);
					
					//设置权限范围
					myWebCtx.setPermission(getUserPermissions(uInfo.getId()));
					System.out.println(upInfo.getPosition().getName());
				}
			}
			List menus = mainMenuService.getTopMainMenu(uInfo);
			myWebCtx.setMainMenu(menus);
			request.getSession().setAttribute(SystemConstant.WEBCONTEXT_NAME, myWebCtx);
		}
	}
	
	public Map<String,Map<String,String>> getUserPermissions(String userId){
		Map<String,Map<String,String>> permissions = new HashMap<String,Map<String,String>>();
		if(BaseUtil.isEmpty(userId)) return permissions;
		List perms = permissionAssignService.getHasAssignPermissions(userId);
		if(perms!=null&&perms.size()>0){
			for(int i=0;i<perms.size();i++){
				Map permItem = (Map)perms.get(i);
				if(permItem!=null&&permItem.size()>0){
					String url = (String)permItem.get("url");
					Map item = new HashMap();
					if(url.indexOf(",")>0){
						String[] urls = url.split(",");
						for(int j=0;j<urls.length;j++){
							String curUrl = urls[j];
							permissions.put(curUrl, packPermissionItem(curUrl,permItem));
						}
					}else{
						permissions.put(url, packPermissionItem(url,permItem));
					}
				}
			}
		}
		System.out.println(perms.size());
		return permissions;
	}
	
	public String toString(Object obj){
		if(obj!=null) return obj.toString();
		return "";
	}
	
	public Map<String,String> packPermissionItem(String url,Map item){
		Map<String,String> curItem = new HashMap<String,String>();
		if(!BaseUtil.isEmpty(url)&&item!=null&&item.size()>0){
			curItem.put("id", toString(item.get("pid")));
			String name = toString(item.get("pid"));
			if(!BaseUtil.isEmpty(name)&&name.indexOf("_")>0){
				String[] ds = name.split("_");
				if(ds.length>2){
					name = ds[ds.length-2]+"_"+ds[ds.length-1];
				}
			}
			curItem.put("name",name );
			curItem.put("url", url);
		}
		
		return curItem;
	}
	
	public void clearWebContext(HttpServletRequest request){
		request.getSession().removeAttribute(SystemConstant.WEBCONTEXT_NAME);
	}
	
	public static void main(String[] args){
		String d = "系统管理_岗位管理_编辑";
		String[] ds = d.split("_");
		System.out.println(ds[ds.length-2]);
	}

	protected Class getEntityClass() {
		return null;
	}
}
