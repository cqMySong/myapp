package com.myapp.service.ec.basedata;

import com.aspose.slides.p2cbca448.and;
import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.entity.ec.basedata.QualityTemplateDetailInfo;
import com.myapp.entity.ec.basedata.QualityTemplateInfo;
import com.myapp.entity.ec.quality.ProQualityTemplateDetailInfo;
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
@Service("qualityTemplateDetailService")
public class QualityTemplateDetailService extends  BaseInterfaceService<QualityTemplateDetailInfo> {
    /**
     * 功能：根据岗位id和分部分项查询工作要求信息
     * @param branchBaseWbsId
     * @param subentryId
     * @param positionId
     * @return
     */
    public QualityTemplateInfo queryByPositionAndSubEntry(String branchBaseWbsId,
                                                          String subentryId,String positionId){
        String hql = "select qt from QualityTemplateInfo as qt " +
                "where qt.branchBaseWbs.id = ? and qt.subentry.id=? and qt.position.id=? and " +
                "qt.enabled=true ";
        return queryEntity(hql,new Object[]{branchBaseWbsId,subentryId,positionId});
    }

    /**
     * 功能:根据操作要求id查询工作要求
     * @param qualityTemplateId
     * @return
     */
    public List queryByQualityTemplateId(String qualityTemplateId){
        String hql = "select qtd.jobRequirement as jobRequirement,po.name as positionName,po.id as positionId " +
                " from QualityTemplateDetailInfo qtd,PositionInfo po where " +
                "po.id = qtd.position.id and qtd.parent.id=? and qtd.enable=true";
        List<Map<String,Object>> result = findByHQL(hql,new Object[]{qualityTemplateId});
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

    /**
     * 功能：获取所有的
     * @return
     */
    public List queryPosition(){
        String hql = "select distinct pi.id as positionId,pi.name as positionName " +
                " from QualityTemplateDetailInfo qtd,PositionInfo pi " +
                " where pi.id = qtd.position.id order by pi.name";
        return findByHQL(hql,null);
    }
}
