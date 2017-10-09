package com.myapp.core.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.myapp.core.entity.PermissionAssignInfo;
import com.myapp.core.entity.PermissionInfo;
import com.myapp.core.exception.db.DeleteException;
import com.myapp.core.exception.db.QueryException;
import com.myapp.core.exception.db.SaveException;
import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.uuid.UuidUtils;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年9月5日 
 * @system:
 *
 *-----------MySong---------------
 */
@Service("permissionAssignService")
public class PermissionAssignService extends BaseInterfaceService<PermissionAssignInfo> {
	@Resource
	public PermissionService permissionService;
	
	public List getHasAssignPermissions(String targetId){
		StringBuffer sb = new StringBuffer();
		sb.append("select pa.id as id,p.id as pid,p.longNumber as pln,p.displayName as pdn");
		sb.append(",p.url as url");
		sb.append(" from PermissionAssignInfo pa");
		sb.append(" left join pa.permission p");
		sb.append(" where p.id is not null");
		List params = new ArrayList();
		if(!BaseUtil.isEmpty(targetId)){
			sb.append(" and pa.targetId=?");
			params.add(targetId);
		}
		return findByHQL(sb.toString(), params.toArray());
	}
	
	public String toAssign(String targetId,String permissionIds) throws SaveException{
		if(BaseUtil.isEmpty(targetId)||BaseUtil.isEmpty(permissionIds)){
			return "相关参数为空，无法完成权限的分配操作!";
		}else{
			String[] pems = permissionIds.split(",");
			for(int i=0;i<pems.length;i++){
				String permId = pems[i];
				Object obj =  permissionService.getEntity(pems[i]);
				if(obj!=null&&obj instanceof PermissionInfo){
					PermissionAssignInfo paInfo = new PermissionAssignInfo();
					paInfo.setTargetId(targetId);
					paInfo.setTargetType(UuidUtils.getEntityTypeById(targetId));
					paInfo.setPermission((PermissionInfo)obj);
					saveEntity(paInfo);
				}
			}
			return "完成了["+pems.length+"]项的权限分配操作!";
		}
	}
	//取消分配
	public void unAssign(String paIds) throws DeleteException{
		if(!BaseUtil.isEmpty(paIds)){
			bathDeleteEntity(paIds.split(","));
		}
	}
	/**
	 * 分配岗位职责权限到用户
	 * @param userId
	 */
	public String assignUserPosition(String userId) throws QueryException, SaveException{
		if(!BaseUtil.isEmpty(userId)){
			StringBuffer sb = new StringBuffer();
			sb.append("select p.fid as pid,p.fname as pname");
			sb.append(" from t_pm_permissionAssign as pa,t_pm_permission as p");
			sb.append(" ,t_pm_jobDuty as jd,t_pm_positionJobDuty as pjd,t_pm_userPosition as up");
			sb.append(" where pa.fpermissionId = p.fid and jd.fid = pa.ftargetId");
			sb.append(" and pjd.fjobDutyId = jd.fid and up.fpositionId = pjd.fprentid");
			sb.append(" and up.fprentid=?");
			sb.append(" and not exists(");
			sb.append(" select pai.fid from t_pm_permissionAssign as pai where pai.fpermissionId = p.fid"
					+ " and pai.ftargetId =?");
			sb.append(")");
			List datas = executeSQLQuery(sb.toString(),new String[]{userId,userId});
			if(datas!=null&&datas.size()>0){
				String uType = UuidUtils.getEntityTypeById(userId);
				int rows = 0;
				for(int i=0;i<datas.size();i++){
					Object rowObj = datas.get(i);
					if(rowObj!=null&&rowObj instanceof Map){
						Map rowMap = (Map) rowObj;
						PermissionInfo pInfo = new PermissionInfo();
						pInfo.setId((String)rowMap.get("pid"));
						pInfo.setName((String)rowMap.get("pname"));
						
						PermissionAssignInfo paInfo = new PermissionAssignInfo();
						paInfo.setTargetId(userId);
						paInfo.setTargetType(uType);
						paInfo.setPermission(pInfo);
						saveEntity(paInfo);
						rows+=1;
					}
				}
				return "完成了["+rows+"]项的权限分配操作!";
			}else{
				return "此用户无对应的岗位信息或者已经岗位职责全部分配完毕!";
			}
		}else{
			throw new QueryException("相关参数为空，无法完成权限的分配操作!");
		}
	}
}
