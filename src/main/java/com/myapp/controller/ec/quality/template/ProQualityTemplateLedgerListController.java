package com.myapp.controller.ec.quality.template;

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
import com.myapp.service.ec.basedata.QualityTemplateDetailService;
import com.myapp.service.ec.basedata.QualityTemplateService;
import com.myapp.service.ec.budget.EnquiryPriceDetailService;
import com.myapp.service.ec.quality.ProQualityTemplateService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @path：com.myapp.controller.ec.engineering.contract
 * @description：质量样板工程规划、实施一览表
 * @author ： ly
 * @date: 2017-08-28 21:02
 */
@PermissionAnn(name="系统管理.现场管理.质量管理.质量样板工程规划、实施一览表",number="app.ec.quality.templateledger")
@Controller
@RequestMapping("ec/quality/template/ledger")
public class ProQualityTemplateLedgerListController extends BasePageListController {
    @Resource
    private ProQualityTemplateService proQualityTemplateService;
    @Resource
    private QualityTemplateDetailService qualityTemplateDetailService;

    @PermissionItemAnn(name="查看",number="queryLedger",type= PermissionTypeEnum.PAGE)
    @RequestMapping("/list")
    public ModelAndView analysisList(){
        Map params = new HashMap();
        params.put("positionHeader",qualityTemplateDetailService.queryPosition());
        return toPage("ec/quality/template/proQualityTemplateLedger", params);
    }
    @Override
    public AbstractBaseService getService() {
        return this.proQualityTemplateService;
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
        }
        params.put("projectId",projectId);
        try {
            this.data = proQualityTemplateService.queryProQualityTemplateLedger(getCurPage(),getPageSize(),params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ajaxModel();
    }

}
