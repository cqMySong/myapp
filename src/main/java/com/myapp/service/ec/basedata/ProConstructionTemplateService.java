package com.myapp.service.ec.basedata;

import com.myapp.core.entity.UserInfo;
import com.myapp.core.exception.db.SaveException;
import com.myapp.core.model.WebDataModel;
import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.core.util.BaseUtil;
import com.myapp.entity.ec.basedata.*;
import com.myapp.enums.ec.ResourceType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @path：com.myapp.service.ec.basedata
 * @description：
 * @author： ly
 * @date: 2018-01-31 0:44
 */
@Service("proConstructionTemplateService")
public class ProConstructionTemplateService extends BaseInterfaceService<ProConstructionTemplateInfo> {
    /**
     * 功能：项目级资料目录
     * @param projectId
     * @param userInfo
     * @param wbsIds
     */
    public WebDataModel batchSave(String projectId, UserInfo userInfo, String wbsIds) throws SaveException {
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

        String hql = " from ConstructionTemplateInfo as ri where ri.enabled=? " +
                " and ri.id in('"+wbsIds.replaceAll(",","','")+"') "
                + " and not exists(from ProConstructionTemplateInfo as pri where pri.constructionTemplateInfo.id = ri.id and pri.project.id=? )";
        List params = new ArrayList();
        params.add(Boolean.TRUE);
        params.add(projectId);

        List<ConstructionTemplateInfo> datas = findByHQL(hql, params.toArray());
        if(datas!=null&&datas.size()>0){
            code = 0;
            mesg = "数据成功导入["+datas.size()+"]个!";
            for(ConstructionTemplateInfo dgInfo:datas){
                ProConstructionTemplateInfo priInfo = new ProConstructionTemplateInfo();
                ProjectInfo pinfo = new ProjectInfo();
                pinfo.setId(projectId);
                priInfo.setProject(pinfo);
                priInfo.setName(dgInfo.getTemplateType());
                priInfo.setNumber(dgInfo.getNumber());
                priInfo.setConstructionTemplateInfo(dgInfo);
                priInfo.setContent(dgInfo.getContent());
                priInfo.setRemark(dgInfo.getRemark());
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
