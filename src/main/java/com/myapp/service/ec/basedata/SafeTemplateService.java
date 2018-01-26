package com.myapp.service.ec.basedata;

import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.entity.ec.basedata.SafeTemplateInfo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @path：com.myapp.service.ec.basedata
 * @description：
 * @author： ly
 * @date: 2018-01-11 0:16
 */
@Service("safeTemplateService")
public class SafeTemplateService extends  BaseInterfaceService<SafeTemplateInfo> {
    /**
     * 功能：根据岗位id和分部分项查询工作要求信息
     * @param branchBaseWbsId
     * @param subentryId
     * @param positionId
     * @return
     */
    public String queryByPositionAndSubEntry(String branchBaseWbsId, String subentryId,String positionId){
        String hql = "select qtd.jobRequirement as jobRequirement from SafeTemplateInfo as qt,SafeTemplateDetailInfo as qtd " +
                "where qtd.parent.id = qt.id and qt.branchBaseWbs.id = ? and qt.subentry.id=? " +
                "and qtd.position.id=? and qt.enabled=true and qtd.enable = true";
        List<Map<String,String>> result = findByHQL(hql,new Object[]{branchBaseWbsId,subentryId,positionId});
        if(result!=null&&result.size()>0){
            return result.get(0).get("jobRequirement");
        }
        return null;
    }
}
