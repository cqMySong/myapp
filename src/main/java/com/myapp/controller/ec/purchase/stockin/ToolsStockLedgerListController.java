package com.myapp.controller.ec.purchase.stockin;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.AuthorAnn;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.annotation.PermissionItemAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BasePageListController;
import com.myapp.core.enums.PermissionTypeEnum;
import com.myapp.core.model.WebDataModel;
import com.myapp.core.util.BaseUtil;
import com.myapp.service.ec.purchase.PurchaseStockDetailService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @path：com.myapp.controller.ec.purchase.stockin
 * @description：工、器具出入库台账
 * @author： ly
 * @date: 2017-11-19 14:58
 */
@PermissionAnn(name="系统管理.现场管理.采购管理.工、器具出入库台账",number="app.ec.stock.toolsstockledger")
@Controller
@RequestMapping("ec/purchase/toolsstockledger")
public class ToolsStockLedgerListController extends BasePageListController {
    @Resource
    private PurchaseStockDetailService purchaseStockDetailService;

    @RequestMapping("/list")
    @PermissionItemAnn(name="查看",number="stockLoedger",type= PermissionTypeEnum.PAGE)
    public ModelAndView analysisList(){
        Map params = new HashMap();
        return toPage("ec/purchase/instock/toolsStockLedgerList", params);
    }
    @Override
    public AbstractBaseService getService() {
        return this.purchaseStockDetailService;
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
            if(searchMap!=null&&searchMap.get("materialName")!=null){
                params.put("materialName",searchMap.get("materialName"));
            }
        }
        params.put("projectId",projectId);
        try {
            this.data = purchaseStockDetailService.queryMaterialStockLedger(getCurPage(),getPageSize(),params,"'TOOL'");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ajaxModel();
    }
}
