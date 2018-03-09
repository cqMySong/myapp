package com.myapp.controller.ec.safty.template;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.annotation.PermissionItemAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BasePageListController;
import com.myapp.core.enums.PermissionTypeEnum;
import com.myapp.core.model.WebDataModel;
import com.myapp.core.util.BaseUtil;
import com.myapp.service.ec.basedata.QualityTemplateDetailService;
import com.myapp.service.ec.basedata.SafeTemplateDetailService;
import com.myapp.service.ec.quality.ProQualityTemplateService;
import com.myapp.service.ec.safty.ProSafeTemplateService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @path：com.myapp.controller.ec.engineering.contract
 * @description：项目质量样板一览表
 * @author ： ly
 * @date: 2017-08-28 21:02
 */
@PermissionAnn(name="系统管理.现场管理.安全管理.项目安全样板一览表",number="app.ec.safe.template")
@Controller
@RequestMapping("ec/safe/template/ledger")
public class ProSafeTemplateLedgerListController extends BasePageListController {
    @Resource
    private ProSafeTemplateService proSafeTemplateService;
    @Resource
    private SafeTemplateDetailService safeTemplateDetailService;

    @PermissionItemAnn(name="查看",number="onload",type= PermissionTypeEnum.PAGE)
    @RequestMapping("/list")
    public ModelAndView analysisList(){
        Map params = new HashMap();
        params.put("positionHeader",safeTemplateDetailService.queryPosition());
        return toPage("ec/safty/template/proSafeTemplateLedger", params);
    }
    @Override
    public AbstractBaseService getService() {
        return this.proSafeTemplateService;
    }

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
            this.data = proSafeTemplateService.queryProSafeTemplateLedger(getCurPage(),getPageSize(),params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ajaxModel();
    }

}
