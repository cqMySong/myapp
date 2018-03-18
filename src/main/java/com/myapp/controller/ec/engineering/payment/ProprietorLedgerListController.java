package com.myapp.controller.ec.engineering.payment;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.annotation.PermissionItemAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BasePageListController;
import com.myapp.core.enums.PermissionTypeEnum;
import com.myapp.core.enums.ProprietorContractType;
import com.myapp.core.exception.db.QueryException;
import com.myapp.core.model.WebDataModel;
import com.myapp.core.util.BaseUtil;
import com.myapp.service.ec.engineering.ProprietorContractService;
import com.myapp.service.ec.engineering.ProprietorPaymentService;
import com.myapp.service.ec.engineering.SiteVisaInService;
import com.myapp.service.ec.settle.MaterialSettleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @path：com.myapp.controller.ec.engineering.payment
 * @description：工程预、结算一览表
 * @author： ly
 * @date: 2017-12-21 23:40
 */
@PermissionAnn(name="系统管理.现场管理.工程合同.工程预结算一览表",number="app.ec.engineering.proprietorledger")
@Controller
@RequestMapping("ec/engineering/proprietorledgers")
public class ProprietorLedgerListController extends BasePageListController {
    @Resource
    private ProprietorContractService proprietorContractService;
    @Resource
    private ProprietorPaymentService proprietorPaymentService;
    @Resource
    private SiteVisaInService siteVisaInService;

    @PermissionItemAnn(name="查看",number="queryLedger",type= PermissionTypeEnum.PAGE)
    @RequestMapping("/list")
    public ModelAndView analysisList(){
        Map params = new HashMap();
        return toPage("ec/engineering/contract/proprietorLedgerList", params);
    }
    @Override
    public AbstractBaseService getService() {
        return this.proprietorContractService;
    }

    @RequestMapping(value="/query")
    @ResponseBody
    public WebDataModel proprietorLedger(String projectId){
        init();
        try {
            Map<String,Object> result = new HashMap<>();
            result.put("budgeting",proprietorContractService.queryByProjectAndType(projectId,
                    ProprietorContractType.BUDGET));
            result.put("settle",proprietorContractService.queryByProjectAndType(projectId,
                    ProprietorContractType.SETTLE));
            result.put("payment",proprietorPaymentService.queryByProject(projectId));
            result.put("siteVisa",siteVisaInService.queryByProject(projectId));
            this.data = result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ajaxModel();
    }
}
