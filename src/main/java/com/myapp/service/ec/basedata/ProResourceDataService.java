package com.myapp.service.ec.basedata;

import com.aspose.cad.internal.H.C;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.entity.basedate.DataGroupInfo;
import com.myapp.core.exception.db.SaveException;
import com.myapp.core.model.WebDataModel;
import com.myapp.core.util.BaseUtil;
import com.myapp.entity.ec.basedata.ProResourceItemInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.entity.ec.basedata.ResourceItemInfo;
import com.myapp.enums.ec.ResourceType;
import org.springframework.stereotype.Service;

import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.entity.ec.basedata.ProResourceDataInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年12月11日 
 * @system:
 * 项目级资料目录（安全资料，技术资料）
 *-----------MySong---------------
 */
@Service("proResourceDataService")
public class ProResourceDataService extends BaseInterfaceService<ProResourceDataInfo> {
    /**
     * 功能：项目级资料目录
     * @param projectId
     * @param userInfo
     * @param wbsIds
     */
    public WebDataModel batchSave(String projectId, UserInfo userInfo, String wbsIds, String typeCode) throws SaveException {
        WebDataModel wdm = new WebDataModel();
        wdm.setData(null);
        int code = 0;
        String mesg = "";
        if(BaseUtil.isEmpty(projectId)){
            wdm.setStatusCode(-1);
            wdm.setStatusMesg("对应的工程项目单位工程为空，无法完成对应项目工程下的数据导入!");
            return wdm;
        }
        if(BaseUtil.isEmpty(wbsIds)){
            wdm.setStatusCode(-1);
            wdm.setStatusMesg("未选择对应的标准信息!");
            return wdm;
        }

        String hql = " from DataGroupInfo as ri where  ri.code=? " +
                " and ri.id in('"+wbsIds.replaceAll(",","','")+"') "
                + " and not exists(from ProResourceDataInfo as pri where pri.group.id = ri.id and pri.project.id=? and pri.code=?)";
        List params = new ArrayList();
        params.add(typeCode);
        params.add(projectId);
        params.add(typeCode);

        List<DataGroupInfo> datas = findByHQL(hql, params.toArray());
        if(datas!=null&&datas.size()>0){
            code = 0;
            mesg = "数据成功导入["+datas.size()+"]个!";
            for(DataGroupInfo dgInfo:datas){
                ProResourceDataInfo priInfo = new ProResourceDataInfo();
                ProjectInfo pinfo = new ProjectInfo();
                pinfo.setId(projectId);
                priInfo.setProject(pinfo);
                priInfo.setName(dgInfo.getName());
                priInfo.setNumber(dgInfo.getNumber());
                priInfo.setGroup(dgInfo);
                priInfo.setCode(typeCode);
                priInfo.setEnabled(Boolean.TRUE);
                priInfo.setRemark(dgInfo.getRemark());
                priInfo.setCreateDate(new Date());
                addNewEntity(priInfo);
            }
        }else{
            code = 1;
            mesg = "没有对应的数据可供导入!";
        }
        wdm.setStatusCode(code);
        wdm.setStatusMesg(mesg);
        return wdm;
    }
}
