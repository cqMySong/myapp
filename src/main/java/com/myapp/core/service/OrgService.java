package com.myapp.core.service;

import org.springframework.stereotype.Service;

import com.myapp.core.entity.BaseOrgInfo;
import com.myapp.core.enums.OrgTypeEnum;
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
@Service("orgService")
public class OrgService extends BaseInterfaceService<BaseOrgInfo> {
	
	
	/**
	 *  有待进一步优化
	 */
	public BaseOrgInfo getCurOrg(String orgId,OrgTypeEnum ote){
		if(ote==null||BaseUtil.isEmpty(orgId)) return null;
		BaseOrgInfo orgInfo = getEntityInfo(orgId);
		if(ote.equals(orgInfo.getOrgType())){
			return orgInfo;
		}else{
			BaseOrgInfo pOrgInfo = orgInfo.getParent();
			if(pOrgInfo!=null){
				return getCurOrg(pOrgInfo.getId(),ote);
			}else{
				return null;
			}
		}
	}
}
