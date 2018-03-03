package com.myapp.service.ec.quality;

import com.alibaba.fastjson.JSON;
import com.myapp.core.entity.PositionInfo;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.exception.db.SaveException;
import com.myapp.core.service.UserService;
import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.entity.ec.basedata.QualityTemplateInfo;
import com.myapp.entity.ec.quality.ProQualityTemplateDetailInfo;
import com.myapp.entity.ec.quality.ProQualityTemplateInfo;
import com.myapp.service.ec.basedata.QualityTemplateService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.Collator;
import java.util.*;

/**
 * @author ly
 */
@Service("proQualityTemplateDetailService")
public class ProQualityTemplateDetailService extends BaseInterfaceService<ProQualityTemplateDetailInfo> {
    @Resource
    private UserService userService;
    @Resource
    private QualityTemplateService qualityTemplateService;
    @Resource
    private ProQualityTemplateService proQualityTemplateService;
    /**
     * 功能：根据检查人查询工作要求数据
     * @param checkerId
     * @param proQualityTemplateId
     * @return
     */
    public Map<String,List<ProQualityTemplateDetailInfo>> queryByChecker(String checkerId,
                                                                         String proQualityTemplateId){
        String hql = "select p.name as positionName,pqtd.checker as checker,pqtd.position as position,pqtd.id as id," +
                "pqtd.checkItem  as checkItem,pqtd.lastUpdateDate as lastUpdateDate,pqtd.checked as checked " +
                " from ProQualityTemplateDetailInfo pqtd,PositionInfo p" +
                " where pqtd.position.id = p.id and pqtd.parent.id=? and " +
                "pqtd.checker.id = ? order by p.name,pqtd.checker.id,pqtd.checkItem desc";
        List<Map<String,Object>> list = findByHQL(hql,new Object[]{proQualityTemplateId,checkerId});
        Map<String,List<ProQualityTemplateDetailInfo>> result= new HashMap<>();
        List<ProQualityTemplateDetailInfo> proQualityTemplateDetailInfos = null;
        ProQualityTemplateDetailInfo proQualityTemplateDetailInfo = null;
        if(list!=null&&list.size()!=0){
            for(Map<String,Object> map:list){
                proQualityTemplateDetailInfo = new ProQualityTemplateDetailInfo();
                proQualityTemplateDetailInfo.setChecker((UserInfo) map.get("checker"));
                proQualityTemplateDetailInfo.setPosition((PositionInfo) map.get("position"));
                proQualityTemplateDetailInfo.setCheckItem((String) map.get("checkItem"));
                proQualityTemplateDetailInfo.setLastUpdateDate((Date) map.get("lastUpdateDate"));
                proQualityTemplateDetailInfo.setChecked((Boolean) map.get("checked"));
                proQualityTemplateDetailInfo.setId((String) map.get("id"));
                if(result.get(map.get("positionName").toString())==null){
                    proQualityTemplateDetailInfos = new ArrayList<>();
                }
                proQualityTemplateDetailInfos.add(proQualityTemplateDetailInfo);
                result.put(map.get("positionName").toString(),proQualityTemplateDetailInfos);
            }
        }else{
            //查询登录人主要岗位信息
            List<PositionInfo> userMainPosition = userService.queryPositionByMain(checkerId);
            if(userMainPosition!=null){
                ProQualityTemplateInfo proQualityTemplateInfo = proQualityTemplateService.loadEntity(proQualityTemplateId);
                String jobRequirement = null;
                String[] jobRequirementArr = null;
                List<String> jobRequirementList = null;
                for(PositionInfo positionInfo:userMainPosition){
                    jobRequirement = qualityTemplateService.queryByPositionAndSubEntry(
                            proQualityTemplateInfo.getBranchBaseWbs().getBaseWbs().getId(),
                            proQualityTemplateInfo.getSubentry().getBaseWbs().getId(),positionInfo.getId());
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
                        proQualityTemplateDetailInfos = new ArrayList<>();
                        for(String jobStr:jobRequirementList){
                            proQualityTemplateDetailInfo = new ProQualityTemplateDetailInfo();
                            proQualityTemplateDetailInfo.setCheckItem(jobStr);
                            proQualityTemplateDetailInfo.setChecked(false);
                            proQualityTemplateDetailInfo.setPosition(positionInfo);
                            proQualityTemplateDetailInfo.setLastUpdateDate(new Date());
                            proQualityTemplateDetailInfos.add(proQualityTemplateDetailInfo);
                        }
                        result.put(positionInfo.getName(),proQualityTemplateDetailInfos);
                    }
                }
            }
        }
        return result;
    }

    /**
     * 功能：根据项目样板id查询工作要求情况
     * @param proQualityTemplateId
     * @return
     */
    public List<Map<String,Object>> queryJobRequireByProQualityTemplateId(String proQualityTemplateId){
        String hql = "select pqtd.checked as checked,pqtd.checkItem as checkItem,pqtd.lastUpdateDate as lastUpdate," +
                "po.name as positionName,po.id as positionId,pqtd.id as id,ui.name as checkerName " +
                " from PositionInfo po,ProQualityTemplateDetailInfo pqtd " +
                " left join UserInfo ui on ui.id = pqtd.checker.id " +
                " where pqtd.position.id = po.id and pqtd.parent.id = ? order by po.id";
        List<Map<String,Object>> result = findByHQL(hql,new Object[]{proQualityTemplateId});
        List<Map<String,Object>> returnResult = new ArrayList<>();
        List<Map<String,Object>> jobRequireList = null;
        Map<String,Object> returnObj = null;
        Map<String,Object> jobRequireObj = null;
        String lastPositionId = null;
        for(Map<String,Object> map:result){
            if(lastPositionId==null||!lastPositionId.equals(map.get("positionId").toString())){
                returnObj = new HashMap<>();
                jobRequireList = new ArrayList<>();
                returnObj.put("positionName",map.get("positionName").toString());
                returnObj.put("jobRequire",jobRequireList);
                returnResult.add(returnObj);
            }
            jobRequireObj = new HashMap<>();
            jobRequireObj.put("checked",map.get("checked"));
            jobRequireObj.put("checkItem",map.get("checkItem"));
            jobRequireObj.put("lastUpdate",map.get("lastUpdate"));
            jobRequireObj.put("id",map.get("id"));
            jobRequireObj.put("positionId",map.get("positionId"));
            jobRequireObj.put("checkName",map.get("checkerName"));
            jobRequireList.add(jobRequireObj);

            lastPositionId = map.get("positionId").toString();
        }
        return returnResult;
    }

    public void saveJobRequire(String jobRequireItems,UserInfo userInfo) throws SaveException {
        if(StringUtils.isEmpty(jobRequireItems)){
            throw new RuntimeException("参数信息错误");
        }
        List<ProQualityTemplateDetailInfo> proQualityTemplateDetailInfos = JSON.parseArray(jobRequireItems,
                ProQualityTemplateDetailInfo.class);
        for(ProQualityTemplateDetailInfo proQualityTemplateDetailInfo:proQualityTemplateDetailInfos){
            proQualityTemplateDetailInfo = getEntityInfo(proQualityTemplateDetailInfo.getId());
            proQualityTemplateDetailInfo.setChecked(Boolean.TRUE);
            proQualityTemplateDetailInfo.setChecker(userInfo);
            proQualityTemplateDetailInfo.setLastUpdateDate(new Date());
            saveEntity(proQualityTemplateDetailInfo);
        }
    }
}
