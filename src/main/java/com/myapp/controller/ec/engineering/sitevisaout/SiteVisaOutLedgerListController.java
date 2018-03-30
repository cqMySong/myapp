package com.myapp.controller.ec.engineering.sitevisaout;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.AuthorAnn;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.annotation.PermissionItemAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BasePageListController;
import com.myapp.core.enums.PermissionTypeEnum;
import com.myapp.core.exception.db.QueryException;
import com.myapp.core.model.WebDataModel;
import com.myapp.core.util.BaseUtil;
import com.myapp.service.ec.engineering.SiteVisaOutService;
import com.myapp.service.ec.engineering.SubContractPaymentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @path：com.myapp.controller.ec.engineering.sitevisaout
 * @description：
 * @author： ly
 * @date: 2018-01-06 10:57
 */
@PermissionAnn(name="系统管理.现场管理.工程合同.现场签证台帐",number="app.ec.engineering.sitevisaoutledeger")
@Controller
@RequestMapping("ec/engineering/sitevisaoutledegers")
public class SiteVisaOutLedgerListController extends BasePageListController {
    @Resource
    private SiteVisaOutService siteVisaOutService;

    @PermissionItemAnn(name="查看",number="queryLedger",type= PermissionTypeEnum.PAGE)
    @RequestMapping("/list")
    public ModelAndView analysisList(){
        Map params = new HashMap();
        return toPage("ec/engineering/sitevisaout/siteVisaOutLedgerList", params);
    }
    @Override
    public AbstractBaseService getService() {
        return this.siteVisaOutService;
    }
    @AuthorAnn(doLongin=true,doPermission=false)
    @RequestMapping(value="/query")
    @ResponseBody
    public WebDataModel materialAnalysis(){
        init();
        String search = request.getParameter("search");
        String projectId = "-1";
        Map<String,Object> params = new HashMap<>();
        if(!BaseUtil.isEmpty(search)) {
            Map searchMap = JSONObject.parseObject(search, new HashMap().getClass());
            if(searchMap!=null&&searchMap.get("projectId")!=null){
                projectId = searchMap.get("projectId").toString();
            }
            if(searchMap!=null&&searchMap.get("startDate")!=null){
                params.put("startDate",searchMap.get("startDate"));
            }
            if(searchMap!=null&&searchMap.get("endDate")!=null){
                params.put("endDate",searchMap.get("endDate"));
            }
            if(searchMap!=null&&searchMap.get("visaUnit")!=null){
                params.put("visaUnit",searchMap.get("visaUnit"));
            }
        }
        params.put("projectId",projectId);
        try {
            this.data = siteVisaOutService.querySiteVisaOutLedger(getCurPage(),getPageSize(),params);
        } catch (QueryException e) {
            e.printStackTrace();
        }
        return ajaxModel();
    }
}
