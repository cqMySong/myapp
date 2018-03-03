package com.myapp.service.ec.safty;

import com.myapp.core.entity.PositionInfo;
import com.myapp.core.exception.db.DeleteException;
import com.myapp.core.exception.db.SaveException;
import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.entity.ec.basedata.SafeTemplateInfo;
import com.myapp.entity.ec.safty.ProSafeTemplateDetailInfo;
import com.myapp.entity.ec.safty.ProSafeTemplateInfo;
import com.myapp.service.ec.basedata.SafeTemplateDetailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author ly
 */
@Service("proSafeTemplateService")
public class ProSafeTemplateService extends BaseInterfaceService<ProSafeTemplateInfo> {
    @Resource
    private ProSafeTemplateDetailService proSafeTemplateDetailService;
    @Resource
    private SafeTemplateDetailService safeTemplateDetailService;
    @Override
    public Object getEntity(String id) {
        ProSafeTemplateInfo proSafeTemplateInfo = (ProSafeTemplateInfo) super.getEntity(id);
        SafeTemplateInfo safeTemplateInfo = new SafeTemplateInfo();
        safeTemplateInfo.setId(proSafeTemplateInfo.getSafeTemplateInfo().getId());
        proSafeTemplateInfo.setOldSafeTemplateInfo(safeTemplateInfo);
        return proSafeTemplateInfo;
    }

    @Override
    public Object saveEntity(Object entity) throws SaveException {
        ProSafeTemplateInfo proSafeTemplateInfo = (ProSafeTemplateInfo) entity;
        //样板发生变化
        if(proSafeTemplateInfo.getOldSafeTemplateInfo()!=null&&!proSafeTemplateInfo.getOldSafeTemplateInfo()
                .getId().equals(proSafeTemplateInfo.getSafeTemplateInfo().getId())){
            try {
                for(ProSafeTemplateDetailInfo proSafeTemplateDetailInfo:proSafeTemplateInfo.getProSafeTemplateDetailInfoSet()){
                    proSafeTemplateDetailService.deleteEntity(proSafeTemplateDetailInfo.getId());
                }
            }catch (DeleteException ex){
                throw new SaveException("删除原有工作要求失败");
            }
        }
        if(proSafeTemplateInfo.getOldSafeTemplateInfo()==null||!proSafeTemplateInfo.getOldSafeTemplateInfo()
                .getId().equals(proSafeTemplateInfo.getSafeTemplateInfo().getId())){
            List<Map<String,Object>> result = safeTemplateDetailService.queryBySafeTemplateId(
                    proSafeTemplateInfo.getSafeTemplateInfo().getId());
            Set<ProSafeTemplateDetailInfo> proQualityTemplateDetailInfoSet = new HashSet<>();
            ProSafeTemplateDetailInfo proSafeTemplateDetailInfo = null;
            int seq= 1;
            List<String> jobRequireList = null;
            PositionInfo positionInfo = null;
            for(Map<String,Object> map:result){
                jobRequireList = (List<String>) map.get("jobRequirement");
                positionInfo = new PositionInfo();
                positionInfo.setId(map.get("positionId").toString());
                for(String jobRequire:jobRequireList){
                    proSafeTemplateDetailInfo = new ProSafeTemplateDetailInfo();
                    proSafeTemplateDetailInfo.setParent(proSafeTemplateInfo);
                    proSafeTemplateDetailInfo.setSeq(seq);
                    proSafeTemplateDetailInfo.setCheckItem(jobRequire);
                    proSafeTemplateDetailInfo.setChecked(false);
                    proSafeTemplateDetailInfo.setPosition(positionInfo);
                    proQualityTemplateDetailInfoSet.add(proSafeTemplateDetailInfo);
                    seq++;
                }
            }
            proSafeTemplateInfo.setProSafeTemplateDetailInfoSet(proQualityTemplateDetailInfoSet);
        }

        return super.saveEntity(entity);
    }
}
