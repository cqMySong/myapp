package com.myapp.service.ec.basedata;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.entity.ec.basedata.SkillClassInfo;
import com.myapp.enums.SkillType;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年10月22日 
 * @system: 技术分类 基础资料类别
 *-----------MySong---------------
 */
@Service("skillClassService")
public class SkillClassService extends BaseInterfaceService<SkillClassInfo> {
	
	public List getSkillItems(SkillType skt){
		StringBuffer sb = new StringBuffer();
		sb.append(" select ski.id as id,ski.name as name,ski.number as number");
		sb.append(" from SkillClassInfo ski");
		List params = new ArrayList();
		sb.append(" where ski.enabled=?");
		params.add(Boolean.TRUE);
		if(skt!=null){
			sb.append(" and ski.skillType = ?");
			params.add(skt);
		}
		sb.append(" order by ski.number ");
		return findByHQL(sb.toString(), params.toArray());
	}
}
