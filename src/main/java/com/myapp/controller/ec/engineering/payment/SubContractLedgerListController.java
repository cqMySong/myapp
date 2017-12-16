package com.myapp.controller.ec.engineering.payment;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BasePageListController;
import com.myapp.core.enums.BaseMethodEnum;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.enums.ExpenseType;
import com.myapp.core.exception.db.QueryException;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.model.WebDataModel;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.WebUtil;
import com.myapp.service.ec.engineering.SubContractPaymentService;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @path：com.myapp.controller.ec.engineering.progressfund
 * @description：分包进度款(结算)
 * @author ： ly
 * @date: 2017-08-28 21:02
 */
@PermissionAnn(name="系统管理.现场管理.工程合同.分包进度款(结算)",number="app.ec.engineering.subcontractledger")
@Controller
@RequestMapping("ec/engineering/subcontractledgers")
public class SubContractLedgerListController extends BasePageListController {
    @Resource
    private SubContractPaymentService subContractPaymentService;

    @RequestMapping("/list")
    public ModelAndView analysisList(){
        Map params = new HashMap();
        return toPage("ec/engineering/settle/subContractLedgerList", params);
    }
    @Override
    public AbstractBaseService getService() {
        return this.subContractPaymentService;
    }

    @RequestMapping(value="/query")
    @ResponseBody
    public WebDataModel materialAnalysis(){
        init();
        try {
            //this.data = materialSettleService.queryMaterialAnalysis(getCurPage(),getPageSize(),null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ajaxModel();
    }
}
