package com.myapp.service.ec.safty;

import com.myapp.core.entity.PositionInfo;
import com.myapp.core.exception.db.DeleteException;
import com.myapp.core.exception.db.QueryException;
import com.myapp.core.exception.db.SaveException;
import com.myapp.core.model.PageModel;
import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.core.util.BaseUtil;
import com.myapp.entity.ec.basedata.SafeTemplateInfo;
import com.myapp.entity.ec.safty.ProSafeTemplateDetailInfo;
import com.myapp.entity.ec.safty.ProSafeTemplateInfo;
import com.myapp.service.ec.basedata.SafeTemplateDetailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

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

    /**
     * 功能：项目质量样板一览表
     * @param curPage
     * @param pageSize
     * @param params
     * @return
     * @throws QueryException
     */
    public PageModel queryProSafeTemplateLedger(Integer curPage, Integer pageSize, Map<String,Object> params)
            throws QueryException {
        StringBuffer sql = new StringBuffer();
        sql.append("select a.fid,a.wbsName,a.wbsxName,a.fExpectStartDate,a.fAcceptanceDate,")
                .append("group_concat(a.fCheckItem separator ';') as checkItem from (")
                .append(" select pqt.fid,wbs.fname as wbsName,wbsx.fname as wbsxName,pqt.fExpectStartDate,")
                .append(" pqt.fAcceptanceDate,concat(pqtd.fPositionId,'::',group_concat(concat(CAST(pqtd.fChecked as signed),'_',pqtd.fCheckItem) separator '!')) as fCheckItem")
                .append(" from t_ec_pro_safe_template pqt")
                .append(" left join t_ec_projectwbs wbs on pqt.fBranchBaseWbsId = wbs.fId")
                .append(" left join t_ec_projectwbs wbsx on pqt.fSubentryId = wbsx.fId")
                .append(" left join t_ec_pro_safe_template_detail pqtd on pqtd.fprentid = pqt.fid")
                .append(" where pqt.fProjectId = ? ");
        List<Object> paramList = new ArrayList<>();
        paramList.add(params.get("projectId"));
        if(!BaseUtil.isEmpty(params.get("startDate"))){
            sql.append("and pqt.fAcceptanceDate>=? ");
            paramList.add(params.get("startDate"));
        }
        if(!BaseUtil.isEmpty(params.get("endDate"))){
            sql.append("and pqt.fAcceptanceDate<=? ");
            paramList.add(params.get("endDate"));
        }
        sql.append(" group by pqtd.fPositionId")
                .append(") a group by a.fid");

        PageModel pageModel =  toPageSqlQuery(curPage,pageSize,sql.toString(),paramList.toArray());
        List<Map<String,Object>> dataList = pageModel.getDatas();
        if(dataList!=null){
            String checkItem = null;
            String[] positionArr = null;
            String[] jobRequireArr = null;
            for(Map<String,Object> map:dataList){
                checkItem = map.get("checkItem").toString();
                positionArr = checkItem.split(";");
                for(String position:positionArr){
                    jobRequireArr = position.split("::");
                    map.put(jobRequireArr[0],jobRequireArr[1]);
                }

            }
        }
        return pageModel;
    }
}
