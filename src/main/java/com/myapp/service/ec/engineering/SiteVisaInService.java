package com.myapp.service.ec.engineering;

import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.entity.ec.engineering.SiteVisaInInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * @path：com.myapp.service.ec.engineering
 * @description：现场签证(收入)
 * @author： ly
 * @date: 2017-11-13 22:46
 */
@Service("siteVisaInService")
public class SiteVisaInService extends BaseInterfaceService<SiteVisaInInfo> {

    /**
     * 功能：根据工程id现场签证收入数据
     * @param projectId
     * @return
     */
    public List queryByProject(String projectId){
        String hql = "select svi.name as name,svi.chargingContent as chargingContent," +
                "svi.amount as amount,svi.id as id,svi.chargingBasis as chargingBasis " +
                "from SiteVisaInInfo svi where svi.project.id = ? order by svi.name" ;
        return findByHQL(hql,new Object[]{projectId});
    }
}
