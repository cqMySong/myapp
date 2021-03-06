package com.myapp.service.ec.basedata;

import com.aspose.slides.p2cbca448.and;
import com.myapp.core.exception.db.SaveException;
import com.myapp.core.model.WebDataModel;
import com.myapp.core.util.BaseUtil;
import com.myapp.entity.ec.basedata.ProBaseWbsInfo;
import com.myapp.entity.ec.basedata.ProStructureInfo;
import com.myapp.enums.ec.ProWbsType;
import org.springframework.stereotype.Service;

import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.entity.ec.basedata.ProjectWbsInfo;

import java.util.*;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2018年1月3日 
 * @system:
 * 工程项目分解结构
 *-----------MySong---------------
 */
@Service("projectWbsService")
public class ProjectWbsService extends BaseInterfaceService<ProjectWbsInfo> {
    // 导入单位工程对应下的标准分解结构
    public WebDataModel importProjectWbs(String structId,String structCode,String wbsIds) throws SaveException {
        WebDataModel wdm = new WebDataModel();
        wdm.setData(null);
        int code = 0;
        String mesg = "";
        if(BaseUtil.isEmpty(structId)){
            wdm.setStatusCode(-1);
            wdm.setStatusMesg("对应的工程项目单位工程为空，无法完成对应项目单位工程下的工程分解结构导入!");
            return wdm;
        }
        if(BaseUtil.isEmpty(wbsIds)){
            wdm.setStatusCode(-1);
            wdm.setStatusMesg("未选择对应的标准信息!");
            return wdm;
        }
        String hql = " from ProBaseWbsInfo as pbw where pbw.enabled=? and pbw.wbsType !=?"
                + " and pbw.id in('"+wbsIds.replaceAll(",","','")+"') " +
                "and not exists(from ProjectWbsInfo as pw where pw.baseWbs.id = pbw.id and pw.proStruct.id=?)"
                + " order by longNumber asc";
        List params = new ArrayList();
        params.add(Boolean.TRUE);
        params.add(ProWbsType.FJJG);
        params.add(structId);

        List<ProBaseWbsInfo> datas = findByHQL(hql, params.toArray());
        if(datas!=null&&datas.size()>0){
            code = 0;
            mesg = "工程分解结构数据成功导入["+datas.size()+"]个!";
            ProStructureInfo proSinfo = getEntity(ProStructureInfo.class, structId);
            Map<String,ProjectWbsInfo> parentMap = new HashMap<String,ProjectWbsInfo>();
            for(ProBaseWbsInfo dgInfo:datas){
                ProjectWbsInfo priInfo = new ProjectWbsInfo();
                priInfo.setProject(proSinfo.getProject());
                priInfo.setName(dgInfo.getName());
                priInfo.setNumber(dgInfo.getNumber());
                priInfo.setProStruct(proSinfo);
                priInfo.setWbsType(dgInfo.getWbsType());
                priInfo.setRemark(dgInfo.getRemark());
                priInfo.setEnabled(true);
                priInfo.setBaseWbs(dgInfo);
                if(dgInfo.getParent()!=null&&!ProWbsType.FJJG.equals(dgInfo.getWbsType())){
                    priInfo.setParent(parentMap.get(dgInfo.getParent().getId()));
                }
                addNewEntity(priInfo);
                parentMap.put(dgInfo.getId(), priInfo);
            }
        }else{
            code = 1;
            mesg = "没有对应的工程分解结构数据可供导入!";
        }
        wdm.setStatusCode(code);
        wdm.setStatusMesg(mesg);
        return wdm;
    }

    /**
     *
     * @param projectId
     * @param basewWbsId
     * @return
     */
    public ProjectWbsInfo queryByWsBase(String projectId,String basewWbsId){
        String hql = "from ProjectWbsInfo a where a.project.id=? and a.baseWbs.id=?";
        return getEntity(hql,new Object[]{projectId,basewWbsId});
    }

    public List<String> queryWsBaseByProject(String projectId){
        String hql = "select distinct a.baseWbs.id as wbsId from ProjectWbsInfo a where a.project.id=?";
        List<Map<String,Object>> result = findByHQL(hql,new Object[]{projectId});
        List<String> wbsIdList = new ArrayList<>();
        for(Map<String,Object> map:result){
            wbsIdList.add(map.get("wbsId").toString());
        }
        return wbsIdList;
    }
}
