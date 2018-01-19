package com.myapp.service.ec.safty;

import com.alibaba.fastjson.JSON;
import com.myapp.core.entity.PositionInfo;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.exception.db.SaveException;
import com.myapp.core.service.UserService;
import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.entity.ec.basedata.SafeTemplateInfo;
import com.myapp.entity.ec.safty.ProSafeTemplateDetailInfo;
import com.myapp.entity.ec.safty.ProSafeTemplateInfo;
import com.myapp.service.ec.basedata.SafeTemplateService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.Collator;
import java.util.*;

/**
 * @author ly
 */
@Service("prSafeTemplateDetailService")
public class ProSafeTemplateDetailService extends BaseInterfaceService<ProSafeTemplateDetailInfo> {
    @Resource
    private UserService userService;
    @Resource
    private SafeTemplateService safeTemplateService;
    @Resource
    private ProSafeTemplateService proSafeTemplateService;
    /**
     * 功能：根据检查人查询工作要求数据
     * @param checkerId
     * @param proSafeTemplateId
     * @return
     */
    public Map<String,List<ProSafeTemplateDetailInfo>> queryByChecker(String checkerId,
                                                                         String proSafeTemplateId){
        String hql = "select p.name as positionName,pqtd.checker as checker,pqtd.position as position,pqtd.id as id," +
                "pqtd.checkItem  as checkItem,pqtd.lastUpdateDate as lastUpdateDate,pqtd.checked as checked " +
                " from ProSafeTemplateDetailInfo pqtd,PositionInfo p" +
                " where pqtd.position.id = p.id and pqtd.parent.id=? and " +
                "pqtd.checker.id = ? order by p.name,pqtd.checker.id,pqtd.checkItem desc";
        List<Map<String,Object>> list = findByHQL(hql,new Object[]{proSafeTemplateId,checkerId});
        Map<String,List<ProSafeTemplateDetailInfo>> result= new HashMap<>();
        List<ProSafeTemplateDetailInfo> proSafeTemplateDetailInfos = null;
        ProSafeTemplateDetailInfo proSafeTemplateDetailInfo = null;
        if(list!=null&&list.size()!=0){
            for(Map<String,Object> map:list){
                proSafeTemplateDetailInfo = new ProSafeTemplateDetailInfo();
                proSafeTemplateDetailInfo.setChecker((UserInfo) map.get("checker"));
                proSafeTemplateDetailInfo.setPosition((PositionInfo) map.get("position"));
                proSafeTemplateDetailInfo.setCheckItem((String) map.get("checkItem"));
                proSafeTemplateDetailInfo.setLastUpdateDate((Date) map.get("lastUpdateDate"));
                proSafeTemplateDetailInfo.setChecked((Boolean) map.get("checked"));
                proSafeTemplateDetailInfo.setId((String) map.get("id"));
                if(result.get(map.get("positionName").toString())==null){
                    proSafeTemplateDetailInfos = new ArrayList<>();
                }
                proSafeTemplateDetailInfos.add(proSafeTemplateDetailInfo);
                result.put(map.get("positionName").toString(),proSafeTemplateDetailInfos);
            }
        }else{
            //查询登录人主要岗位信息
            List<PositionInfo> userMainPosition = userService.queryPositionByMain(checkerId);
            if(userMainPosition!=null){
                ProSafeTemplateInfo proSafeTemplateInfo = proSafeTemplateService.loadEntity(proSafeTemplateId);
                SafeTemplateInfo SafeTemplateInfo = null;
                String jobRequirement = null;
                String[] jobRequirementArr = null;
                List<String> jobRequirementList = null;
                for(PositionInfo positionInfo:userMainPosition){
                    SafeTemplateInfo = safeTemplateService.queryByPositionAndSubEntry(
                            proSafeTemplateInfo.getBranchBaseWbs().getBaseWbs().getId(),
                            proSafeTemplateInfo.getSubentry().getBaseWbs().getId(),positionInfo.getId());
                    if(SafeTemplateInfo!=null&& !StringUtils.isEmpty(SafeTemplateInfo.getJobRequirement())){
                        jobRequirement = SafeTemplateInfo.getJobRequirement();
                        jobRequirementArr = jobRequirement.split("\n");
                        jobRequirementList = new ArrayList<>();
                        for(String jobStr:jobRequirementArr){
                            if(!StringUtils.isEmpty(jobStr)){
                                jobRequirementList.add(jobStr);
                            }
                        }
                        Collator collator = Collator.getInstance(Locale.CHINESE);
                        Collections.sort(jobRequirementList,collator);
                        proSafeTemplateDetailInfos = new ArrayList<>();
                        for(String jobStr:jobRequirementList){
                            proSafeTemplateDetailInfo = new ProSafeTemplateDetailInfo();
                            proSafeTemplateDetailInfo.setCheckItem(jobStr);
                            proSafeTemplateDetailInfo.setChecked(false);
                            proSafeTemplateDetailInfo.setPosition(positionInfo);
                            proSafeTemplateDetailInfo.setLastUpdateDate(new Date());
                            proSafeTemplateDetailInfos.add(proSafeTemplateDetailInfo);
                        }
                        result.put(positionInfo.getName(),proSafeTemplateDetailInfos);
                    }
                }
            }
        }
        return result;
    }


    public void saveJobRequire(String jobRequireItems,UserInfo userInfo) throws SaveException {
        if(StringUtils.isEmpty(jobRequireItems)){
            throw new RuntimeException("参数信息错误");
        }
        List<ProSafeTemplateDetailInfo> proSafeTemplateDetailInfos = JSON.parseArray(jobRequireItems,
                ProSafeTemplateDetailInfo.class);
        for(ProSafeTemplateDetailInfo proSafeTemplateDetailInfo:proSafeTemplateDetailInfos){
            proSafeTemplateDetailInfo.setChecker(userInfo);
            proSafeTemplateDetailInfo.setLastUpdateDate(new Date());
            saveEntity(proSafeTemplateDetailInfo);
        }
    }
}
