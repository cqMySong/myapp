package com.myapp.service.ec.other;

import com.myapp.core.exception.db.QueryException;
import com.myapp.core.model.PageModel;
import com.myapp.core.util.BaseUtil;
import org.springframework.stereotype.Service;

import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.entity.ec.other.RectifyNoticeInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Phoenix
 *
 */
@Service("rectifyNoticeService")
public class RectifyNoticeService extends BaseInterfaceService<RectifyNoticeInfo>{
    /**
     * 功能：获取现场签证支出台帐信息
     * @param curPage
     * @param pageSize
     * @param params
     * @return
     * @throws QueryException
     */
    public PageModel queryRectifyNoticeLedger(Integer curPage, Integer pageSize, Map<String,Object> params)
            throws QueryException {
        List<Object> paramList = new ArrayList<>();
        StringBuffer sql = new StringBuffer();
        sql.append("select a.fnumber as rectifyNoticeNumber, a.fid as rectifyNoticeId,a.fOrgUnit as orgUnit,")
           .append("(select count(t.fid) from t_base_attachFile t where t.fsourceBillId = a.fid) as rectifyNoticeAttach,")
           .append("b.fid as feedBackId,b.fisFeedBack as isFeedBack,")
           .append("(select count(t.fid) from t_base_attachFile t where t.fsourceBillId = b.fid) as feedBackAttach ")
           .append(" from t_ec_rectifyNotice a ")
           .append("left join t_ec_rectifyfeedback b on a.fid = b.fRectifyNoticeId ")
           .append("where a.fProjectId=? ");
        paramList.add(params.get("projectId"));
        if(!BaseUtil.isEmpty(params.get("visaUnit"))){
            sql.append(" and a.fOrgUnit like ? ");
            paramList.add("%"+params.get("visaUnit")+"%");
        }
        return toPageSqlQuery(curPage,pageSize,sql.toString(),paramList.toArray());
    }
}
