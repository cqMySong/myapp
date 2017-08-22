package com.myapp.core.service;

import java.security.NoSuchAlgorithmException;
import java.util.List;

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
		if(BaseUtil.isEmpty(userId)) return "用户id为空无法完成用户密码都重置操作!";
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
}
