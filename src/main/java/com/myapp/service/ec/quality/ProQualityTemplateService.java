package com.myapp.service.ec.quality;

import com.myapp.core.entity.PositionInfo;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.exception.db.DeleteException;
import com.myapp.core.exception.db.QueryException;
import com.myapp.core.exception.db.SaveException;
import com.myapp.core.model.PageModel;
import com.myapp.core.model.WebDataModel;
import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.core.util.BaseUtil;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.entity.ec.basedata.QualityTemplateInfo;
import com.myapp.entity.ec.quality.ProQualityTemplateDetailInfo;
import com.myapp.entity.ec.quality.ProQualityTemplateInfo;
import com.myapp.service.ec.basedata.ProjectWbsService;
import com.myapp.service.ec.basedata.QualityTemplateDetailService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author ly
 */
@Service("proQualityTemplateService")
public class ProQualityTemplateService extends BaseInterfaceService<ProQualityTemplateInfo> {
    @Resource
    private ProQualityTemplateDetailService proQualityTemplateDetailService;
    @Resource
    private QualityTemplateDetailService qualityTemplateDetailService;
    @Resource
    private ProjectWbsService projectWbsService;
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

    /**
     * 功能：项目质量样板一览表
     * @param curPage
     * @param pageSize
     * @param params
     * @return
     * @throws QueryException
     */
    public PageModel queryProQualityTemplateLedger(Integer curPage, Integer pageSize, Map<String,Object> params)
            throws QueryException {
        StringBuffer sql = new StringBuffer();
        sql.append("select a.fid,a.wbsName,a.wbsxName,a.fExpectStartDate,a.fAcceptanceDate,")
            .append("group_concat(a.fCheckItem separator ';') as checkItem from (")
            .append(" select pqt.fid,wbs.fname as wbsName,wbsx.fname as wbsxName,pqt.fExpectStartDate,")
            .append(" pqt.fAcceptanceDate,concat(pqtd.fPositionId,'::',group_concat(concat(CAST(pqtd.fChecked as signed),'_',pqtd.fCheckItem) separator '!')) as fCheckItem")
            .append(" from t_ec_pro_quality_template pqt")
            .append(" left join t_ec_projectwbs wbs on pqt.fBranchBaseWbsId = wbs.fId")
            .append(" left join t_ec_projectwbs wbsx on pqt.fSubentryId = wbsx.fId")
            .append(" left join t_ec_pro_quality_template_detail pqtd on pqtd.fprentid = pqt.fid")
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

        String hql = " from QualityTemplateInfo as ri where ri.enabled=? " +
                " and ri.id in('"+wbsIds.replaceAll(",","','")+"') "
                + " and not exists(from ProQualityTemplateInfo as pri where pri.qualityTemplateInfo.id = ri.id and pri.project.id=? )";
        List params = new ArrayList();
        params.add(Boolean.TRUE);
        params.add(projectId);

        List<QualityTemplateInfo> datas = findByHQL(hql, params.toArray());
        if(datas!=null&&datas.size()>0){
            code = 0;
            mesg = "数据成功导入["+datas.size()+"]个!";
            for(QualityTemplateInfo dgInfo:datas){
                ProQualityTemplateInfo priInfo = new ProQualityTemplateInfo();
                ProjectInfo pinfo = new ProjectInfo();
                pinfo.setId(projectId);
                priInfo.setProject(pinfo);
                priInfo.setName(dgInfo.getName());
                priInfo.setNumber(dgInfo.getNumber());
                priInfo.setQualityTemplateInfo(dgInfo);
                priInfo.setCreateUser(userInfo);
                priInfo.setCreateDate(new Date());
                if (dgInfo.getSubentry()!=null&&!StringUtils.isEmpty(dgInfo.getSubentry().getId())) {
                   priInfo.setSubentry(projectWbsService.queryByWsBase(projectId,dgInfo.getSubentry().getId()));
                }
                if (dgInfo.getBranchBaseWbs()!=null&&!StringUtils.isEmpty(dgInfo.getBranchBaseWbs().getId())) {
                    priInfo.setBranchBaseWbs(projectWbsService.queryByWsBase(projectId,dgInfo.getBranchBaseWbs().getId()));
                }
                saveEntity(priInfo);
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
