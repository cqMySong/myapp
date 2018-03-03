package com.myapp.service.ec.quality;

import com.myapp.core.entity.PositionInfo;
import com.myapp.core.exception.db.DeleteException;
import com.myapp.core.exception.db.SaveException;
import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.entity.ec.basedata.QualityTemplateInfo;
import com.myapp.entity.ec.quality.ProQualityTemplateDetailInfo;
import com.myapp.entity.ec.quality.ProQualityTemplateInfo;
import com.myapp.service.ec.basedata.QualityTemplateDetailService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author ly
 */
@Service("proQualityTemplateService")
public class ProQualityTemplateService extends BaseInterfaceService<ProQualityTemplateInfo> {
    @Resource
    private ProQualityTemplateDetailService proQualityTemplateDetailService;
    @Resource
    private QualityTemplateDetailService qualityTemplateDetailService;
    @Override
    public Object getEntity(String id) {
        ProQualityTemplateInfo proQualityTemplateInfo = (ProQualityTemplateInfo) super.getEntity(id);
        QualityTemplateInfo qualityTemplateInfo = new QualityTemplateInfo();
        qualityTemplateInfo.setId(proQualityTemplateInfo.getQualityTemplateInfo().getId());
        proQualityTemplateInfo.setOldQualityTemplateInfo(qualityTemplateInfo);
        return proQualityTemplateInfo;
    }

    @Override
    public Object saveEntity(Object entity) throws SaveException {
        ProQualityTemplateInfo proQualityTemplateInfo = (ProQualityTemplateInfo) entity;
        //样板发生变化
        if(proQualityTemplateInfo.getOldQualityTemplateInfo()!=null&&!proQualityTemplateInfo.getOldQualityTemplateInfo()
                .getId().equals(proQualityTemplateInfo.getQualityTemplateInfo().getId())){
            try {
                for(ProQualityTemplateDetailInfo proQualityTemplateDetailInfo:proQualityTemplateInfo.getProQualityTemplateDetailInfoSet()){
                    proQualityTemplateDetailService.deleteEntity(proQualityTemplateDetailInfo.getId());
                }
            }catch (DeleteException ex){
                throw new SaveException("删除原有工作要求失败");
            }
        }
        if(proQualityTemplateInfo.getOldQualityTemplateInfo()==null||!proQualityTemplateInfo.getOldQualityTemplateInfo()
                .getId().equals(proQualityTemplateInfo.getQualityTemplateInfo().getId())){
            List<Map<String,Object>> result = qualityTemplateDetailService.queryByQualityTemplateId(
                    proQualityTemplateInfo.getQualityTemplateInfo().getId());
            Set<ProQualityTemplateDetailInfo> proQualityTemplateDetailInfoSet = new HashSet<>();
            ProQualityTemplateDetailInfo proQualityTemplateDetailInfo = null;
            int seq= 1;
            List<String> jobRequireList = null;
            PositionInfo positionInfo = null;
            for(Map<String,Object> map:result){
                jobRequireList = (List<String>) map.get("jobRequirement");
                positionInfo = new PositionInfo();
                positionInfo.setId(map.get("positionId").toString());
                for(String jobRequire:jobRequireList){
                    proQualityTemplateDetailInfo = new ProQualityTemplateDetailInfo();
                    proQualityTemplateDetailInfo.setParent(proQualityTemplateInfo);
                    proQualityTemplateDetailInfo.setSeq(seq);
                    proQualityTemplateDetailInfo.setCheckItem(jobRequire);
                    proQualityTemplateDetailInfo.setChecked(false);
                    proQualityTemplateDetailInfo.setPosition(positionInfo);
                    proQualityTemplateDetailInfoSet.add(proQualityTemplateDetailInfo);
                    seq++;
                }
            }
            proQualityTemplateInfo.setProQualityTemplateDetailInfoSet(proQualityTemplateDetailInfoSet);
        }

        return super.saveEntity(entity);
    }
}
