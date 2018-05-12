package com.myapp.core.service;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.myapp.core.entity.BaseOrgInfo;
import com.myapp.core.entity.PositionInfo;
import com.myapp.core.exception.db.QueryException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myapp.core.base.setting.SystemConstant;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.exception.db.SaveException;
import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.core.util.BaseUtil;

@Service("userService")
@Transactional
public class UserService extends BaseInterfaceService<UserInfo> {
	
	
	public String resetUserEncrypt(String userId) throws NoSuchAlgorithmException, SaveException{
		if(BaseUtil.isEmpty(userId)) return "用户id为空无法完成用户密码的重置操作!";
		UserInfo uInfo = (UserInfo) getEntity(userId);
		if(uInfo!=null){
			uInfo.setPassWord(BaseUtil.md5Encrypt(SystemConstant.DEF_USERPWD_INIT));
			saveEntity(uInfo);
			return "用户："+uInfo.getName()+" 的密码已经重新初始化完成!";
		}else{
			return "用户不存在不能完成用户都密码重置操作!";
		}
	}

	/**
	 * 功能：根据用户名获取登录用户信息
	 * @param number
	 * @return
	 * @throws QueryException
	 */
	public UserInfo queryUserByNumber(String number)throws QueryException{
		String hql = "select u from UserInfo as u where u.number=?";
		return  queryEntity(hql,new String[]{number});
	}

	public List<PositionInfo> queryPositionByMain(String userId){
		String hql = "select b.position as position from UserInfo as u,UserPositionInfo as  b " +
				"where u.id = b.parent.id and u.id=? and b.main=true";
		List<Map<String,Object>> result = findByHQL(hql,new Object[]{userId});
		if(result!=null&&result.size()>0){
			List<PositionInfo> positionInfos = new ArrayList<>();
			for(Map<String,Object> map:result){
				positionInfos.add((PositionInfo) map.get("position"));
			}
			return  positionInfos;
		}
		return null;
	}
	
	public List<PositionInfo> queryPosition(String userId){
		String hql = "select b.position as position from UserInfo as u,UserPositionInfo as  b " +
				"where u.id = b.parent.id and u.id=?";
		List<Map<String,Object>> result = findByHQL(hql,new Object[]{userId});
		if(result!=null&&result.size()>0){
			List<PositionInfo> positionInfos = new ArrayList<>();
			for(Map<String,Object> map:result){
				positionInfos.add((PositionInfo) map.get("position"));
			}
			return  positionInfos;
		}
		return null;
	}

	/**
	 * 功能：根据用户id获取当前
	 * @param userId
	 * @return
	 * @throws QueryException
	 */
	public List<Map> queryDeptManager(String userId,String projectId)throws QueryException{
		//查询当前人主要岗位和工程项目，所在部门
		String sql = "select distinct d.forgId as orgId " +
				"from t_pm_user a,t_pm_userposition b,t_base_org c,t_pm_position d " +
				"where a.fid = b.fprentid and b.fisAdmin = true " +
				"and d.forgId = c.fid " +
				"and b.fpositionId = d.fid " +
				"and a.fid=? " +
				"and c.flongnumber like  " +
				"(select concat(a.flongnumber,'%') from t_base_org a,t_ec_project b where a.fid=b.forgId and b.fid=?) ";
		List<Map> orgIdList = executeSQLQuery(sql,new String[]{userId,projectId});
		if(orgIdList!=null&&orgIdList.size()>0){
			StringBuffer orgIdStr = new StringBuffer();
			for(Map map:orgIdList){
				orgIdStr.append("'").append(map.get("orgId")).append("',");
			}
			String hql = "select distinct c.number as number,c.id as id from PositionInfo a,UserPositionInfo b,UserInfo c where a.id = b.position.id " +
					"and b.parent.id = c.id and a.respible = true and a.org.id in ("
					+orgIdStr.toString().substring(0,orgIdStr.toString().length()-1)+")";

			return findByHQL(hql,null);
		}
		return null;
	}

	/**
	 * 功能：根据项目id和岗位编码查询负责人账号
	 * @param projectId
	 * @param positionNumber
	 * @return
	 */
	public List queryUserByPosition(String projectId,String positionNumber) throws QueryException {
		String sql = "select distinct f.fnumber as number " +
				"from t_pm_jobdutygroup a,t_pm_jobduty b,t_pm_positionjobduty c,t_pm_position d, " +
				"t_pm_userposition e,t_pm_user f,t_base_org g " +
				"where b.fGroupId=a.fid and c.fJobDutyId = b.fId and d.fid = c.fprentId  " +
				"and e.fpositionId = d.fid and f.fid = e.fprentid and d.forgId = g.fid " +
				"and  a.fnumber=? and e.fisAdmin = true and d.frespible = true " +
				"and g.flongnumber like (select concat(a.flongnumber,'%') from t_base_org a,t_ec_project b " +
				"where a.fid=b.forgId and b.fid=?)";
		return executeSQLQuery(sql,new Object[]{positionNumber,projectId});
	}

}
