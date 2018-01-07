package com.myapp.controller.ec.purchase.apply;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.annotation.PermissionItemAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BasePageListController;
import com.myapp.core.enums.PermissionTypeEnum;
import com.myapp.core.model.WebDataModel;
import com.myapp.core.util.BaseUtil;
import com.myapp.service.ec.purchase.ApplyMaterialService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @path：com.myapp.controller.ec.purchase.stockin
 * @description：材料申购、供应台账
 * @author： ly
 * @date: 2017-11-19 14:58
 */
@PermissionAnn(name="系统管理.现场管理.采购管理.材料申购、供应台账",number="app.ec.purchase.supplyledger")
@Controller
@RequestMapping("ec/purchase/supplyledger")
public class SupplyLedgerListController extends BasePageListController {
    @Resource
    private ApplyMaterialService applyMaterialService;

    @PermissionItemAnn(name="查看",number="onload",type= PermissionTypeEnum.PAGE)
    @RequestMapping("/list")
    public ModelAndView analysisList(){
        Map params = new HashMap();
        return toPage("ec/purchase/apply/supplyLedgerList", params);
    }

    @Override
    public AbstractBaseService getService() {
        return this.applyMaterialService;
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
            if(searchMap!=null&&searchMap.get("materialName")!=null){
                params.put("materialName",searchMap.get("materialName"));
            }
        }
        params.put("projectId",projectId);
        try {
           this.data = applyMaterialService.queryMaterialApplyLedger(getCurPage(),getPageSize(),params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ajaxModel();
    }
}
