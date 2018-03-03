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
     * 功能：根据项目样板id查询工作要求情况
     * @param proSafeTemplateId
     * @return
     */
    public List<Map<String,Object>> queryJobRequireByProSafeTemplateId(String proSafeTemplateId){
        String hql = "select pqtd.checked as checked,pqtd.checkItem as checkItem,pqtd.lastUpdateDate as lastUpdate," +
                "po.name as positionName,po.id as positionId,pqtd.id as id,ui.name as checkerName " +
                " from PositionInfo po,ProSafeTemplateDetailInfo pqtd " +
                " left join UserInfo ui on ui.id = pqtd.checker.id " +
                " where pqtd.position.id = po.id and pqtd.parent.id = ? order by po.id";
        List<Map<String,Object>> result = findByHQL(hql,new Object[]{proSafeTemplateId});
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
        List<ProSafeTemplateDetailInfo> proSafeTemplateDetailInfos = JSON.parseArray(jobRequireItems,
                ProSafeTemplateDetailInfo.class);
        for(ProSafeTemplateDetailInfo proSafeTemplateDetailInfo:proSafeTemplateDetailInfos){
            proSafeTemplateDetailInfo = getEntityInfo(proSafeTemplateDetailInfo.getId());
            proSafeTemplateDetailInfo.setChecked(Boolean.TRUE);
            proSafeTemplateDetailInfo.setChecker(userInfo);
            proSafeTemplateDetailInfo.setLastUpdateDate(new Date());
            saveEntity(proSafeTemplateDetailInfo);
        }
    }
}
