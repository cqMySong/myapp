package com.myapp.service.ec.basedata;

import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.entity.ec.basedata.QualityTemplateDetailInfo;
import com.myapp.entity.ec.basedata.QualityTemplateInfo;
import org.springframework.stereotype.Service;

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
}
