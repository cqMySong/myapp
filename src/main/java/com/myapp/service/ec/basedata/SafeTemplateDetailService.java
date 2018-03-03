package com.myapp.service.ec.basedata;

import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.entity.ec.basedata.SafeTemplateDetailInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.text.Collator;
import java.util.*;

/**
 * @path：com.myapp.service.ec.basedata
 * @description：
 * @author： ly
 * @date: 2018-01-11 0:16
 */
@Service("safeTemplateDetailService")
public class SafeTemplateDetailService extends  BaseInterfaceService<SafeTemplateDetailInfo> {
    /**
     * 功能:根据操作要求id查询工作要求
     * @param safeTemplateId
     * @return
     */
    public List queryBySafeTemplateId(String safeTemplateId){
        String hql = "select qtd.jobRequirement as jobRequirement,po.name as positionName,po.id as positionId " +
                " from SafeTemplateDetailInfo qtd,PositionInfo po where " +
                "po.id = qtd.position.id and qtd.parent.id=? and qtd.enable=true";
        List<Map<String,Object>> result = findByHQL(hql,new Object[]{safeTemplateId});
        if(result!=null&&result.size()>0){
            String jobRequirement = null;
            String[] jobRequirementArr = null;
            List<String> jobRequirementList = null;
            for(Map<String,Object> map:result){
                jobRequirement = map.get("jobRequirement")==null?"":map.get("jobRequirement").toString();
                if(!StringUtils.isEmpty(jobRequirement)){
                    jobRequirementArr = jobRequirement.split("\n");
                    jobRequirementList = new ArrayList<>();
                    for(String jobStr:jobRequirementArr){
                        if(!StringUtils.isEmpty(jobStr)){
                            jobRequirementList.add(jobStr);
                        }
                    }
                    Collator collator = Collator.getInstance(Locale.CHINESE);
                    Collections.sort(jobRequirementList,collator);
                    map.put("jobRequirement",jobRequirementList);
                }
            }
        }
        return result;
    }
}
