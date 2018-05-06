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
}
