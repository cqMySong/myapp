package com.myapp.controller.ec.engineering.servicecharge;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.annotation.PermissionItemAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BasePageListController;
import com.myapp.core.enums.PermissionTypeEnum;
import com.myapp.core.exception.db.QueryException;
import com.myapp.core.model.WebDataModel;
import com.myapp.core.util.BaseUtil;
import com.myapp.service.ec.engineering.ServiceChargeService;
import com.myapp.service.ec.engineering.SubContractPaymentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @path：com.myapp.controller.ec.engineering.progressfund
 * @description：人工费（劳务费）结算、支付台帐
 * @author ： ly
 * @date: 2017-08-28 21:02
 */
@PermissionAnn(name="系统管理.现场管理.工程合同.人工费（劳务费）结算、支付台帐",number="app.ec.engineering.servicechargeledger")
@Controller
@RequestMapping("ec/engineering/servicechargeledger")
public class ServiceChargeLedgerListController extends BasePageListController {
    @Resource
    private ServiceChargeService serviceChargeService;

    @PermissionItemAnn(name="查看",number="onload",type= PermissionTypeEnum.PAGE)
    @RequestMapping("/list")
    public ModelAndView analysisList(){
        Map params = new HashMap();
        return toPage("ec/engineering/payment/serviceChargeLedgerList", params);
    }
    @Override
    public AbstractBaseService getService() {
        return this.serviceChargeService;
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
            if(searchMap!=null&&searchMap.get("contractName")!=null){
                params.put("contractName",searchMap.get("contractName"));
            }
        }
        params.put("projectId",projectId);
        try {
            this.data = serviceChargeService.queryServiceChargeLedger(getCurPage(),getPageSize(),params);
        } catch (QueryException e) {
            e.printStackTrace();
        }
        return ajaxModel();
    }
}
