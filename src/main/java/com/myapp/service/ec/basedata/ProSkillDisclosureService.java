package com.myapp.service.ec.basedata;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.myapp.core.entity.UserInfo;
import com.myapp.entity.ec.basedata.ConstructionSchemeInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.myapp.core.exception.db.SaveException;
import com.myapp.core.model.WebDataModel;
import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.core.util.BaseUtil;
import com.myapp.entity.ec.basedata.ProSkillDisclosureInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.entity.ec.basedata.SkillItemInfo;
import com.myapp.enums.ec.SkillType;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年12月20日 
 * @system:
 * 技术交底：施工技术和安全技术
 *-----------MySong---------------
 */
@Service("proSkillDisclosureService")
public class ProSkillDisclosureService extends
		BaseInterfaceService<ProSkillDisclosureInfo> {

	//导入标准的技术交底内容
	public WebDataModel batchInitProData(String proJectId,SkillType st) throws SaveException{
		WebDataModel wdm = new WebDataModel();
		wdm.setData(null);
		int code = 0;
		String mesg = "";
		if(BaseUtil.isEmpty(proJectId)||st==null){
			code = -1;
			mesg = "对应的工程项目为空，无法完成对应项目的基础数据导入!";
		}else{
			String hql = " from SkillItemInfo as si where si.enabled=? and si.skillType=?"
					+ " and not exists(from ProSkillDisclosureInfo as pds where pds.skillItem.id = si.id and pds.project.id=?)";
			List params = new ArrayList();
			params.add(Boolean.TRUE);
			params.add(st);
			params.add(proJectId);
			List<SkillItemInfo> datas = findByHQL(hql, params.toArray());
			if(datas!=null&&datas.size()>0){
				code = 0;
				mesg = st.getName()+"标准数据成功导入["+datas.size()+"]个!";
				for(SkillItemInfo siInfo:datas){
					ProSkillDisclosureInfo psdInfo = new ProSkillDisclosureInfo();
					ProjectInfo pinfo = new ProjectInfo();
					pinfo.setId(proJectId);
					psdInfo.setProject(pinfo);
					psdInfo.setSkillClass(siInfo.getSkillClass());
					psdInfo.setSkillType(st);
					psdInfo.setSkillItem(siInfo);
					psdInfo.setName(siInfo.getName());
					psdInfo.setNumber(siInfo.getNumber());
					addNewEntity(psdInfo);
				}
			}else{
				code = 1;
				mesg = "没有对应的"+st.getName()+"标准数据可供导入!";
			}
		}
		wdm.setStatusCode(code);
		wdm.setStatusMesg(mesg);
		return wdm;
	}

	/**
	 * 功能：项目施工技术交底
	 * @param qmSkillBatch
	 * @param userInfo
	 */
	public void batchSave(String qmSkillBatch,UserInfo userInfo) throws SaveException {
		if(StringUtils.isEmpty(qmSkillBatch)){
			throw new RuntimeException("请选择项目技术交底");
		}
		List<ProSkillDisclosureInfo> proSkillDisclosureInfoList =
				JSON.parseArray(qmSkillBatch,ProSkillDisclosureInfo.class);
		if(proSkillDisclosureInfoList==null||proSkillDisclosureInfoList.size()==0){
			throw new RuntimeException("请选择项目技术交底");
		}
		for(ProSkillDisclosureInfo proSkillDisclosureInfo:proSkillDisclosureInfoList){
			proSkillDisclosureInfo.setCreateDate(new Date());
			saveEntity(proSkillDisclosureInfo);
		}
	}
}
