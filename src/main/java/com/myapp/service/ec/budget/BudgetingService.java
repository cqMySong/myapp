package com.myapp.service.ec.budget;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.base.controller.BaseController;
import com.myapp.core.base.service.IImportConvertService;
import com.myapp.core.entity.MaterialInfo;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.MaterialType;
import com.myapp.core.enums.StatusCodeEnum;
import com.myapp.core.exception.db.SaveException;
import com.myapp.core.model.MyWebContext;
import com.myapp.core.model.WebDataModel;
import com.myapp.core.service.MaterialService;
import com.myapp.core.service.UserService;
import com.myapp.core.service.act.ActTaskService;
import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.entity.ec.budget.BudgetingDetailInfo;
import com.myapp.entity.ec.budget.BudgetingInfo;
import com.myapp.entity.ec.drawing.DiscussionDrawingInfo;
import com.myapp.model.BudgetingModel;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 包路径：com.myapp.service.ec.drawing
 * 功能说明：
 * @author ： ly
 * 创建时间: 2017-07-30 19:37
 */
@Service("budgetingService")
public class BudgetingService extends BaseInterfaceService<BudgetingInfo>
        implements ExecutionListener,IImportConvertService {
    @Resource
    private MaterialService materialService;
    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {

    }

    @Override
    public WebDataModel importConvertBusiness(List<Object> importList) {
        WebDataModel webDataModel = new WebDataModel();
        if(importList==null){
            webDataModel.setStatusCode(Integer.parseInt(StatusCodeEnum.INFO.getValue()));
            webDataModel.setStatusMesg("没有读取到预算信息,请检查导入文件");
            return webDataModel;
        }
        List<JSONObject> budgetingDetailInfoList = new ArrayList<>();
        BudgetingModel budgetingModel = null;
        JSONObject budgetingDetailInfo = null;
        MaterialInfo materialInfo = null;
        StringBuffer buffer = new StringBuffer();
        JSONObject columnJson = null;
        for(Object object:importList){
             budgetingModel = (BudgetingModel)object;
             materialInfo = materialService.queryByCode(budgetingModel.getMaterialCode());
             if(materialInfo==null){
                 buffer.append("序号").append(budgetingModel.getsNo())
                     .append("材料名称【").append(budgetingModel.getMaterialName())
                     .append("】编号【").append(budgetingModel.getMaterialCode()).append("】")
                     .append("在物料基础信息中未找到，请检查<br/>");
                 continue;
             }
             if(!materialInfo.getSpecification().equals(budgetingModel.getSpecification())){
                buffer.append("序号").append(budgetingModel.getsNo())
                        .append("材料名称【").append(budgetingModel.getMaterialName())
                        .append("】编号【").append(budgetingModel.getMaterialCode()).append("】")
                        .append("与物料基础信息中规格不一致,请检查<br/>");
                continue;
             }
             budgetingDetailInfo = new JSONObject();
             budgetingDetailInfo.put("quantity",budgetingModel.getQuantity());
             budgetingDetailInfo.put("budgetaryPrice",budgetingModel.getBudgetaryPrice());
             budgetingDetailInfo.put("totalPrice",budgetingModel.getTotalPrice());
             columnJson = new JSONObject();
             columnJson.put("id",materialInfo.getId());
             columnJson.put("name",materialInfo.getName());
             budgetingDetailInfo.put("material",columnJson);
             columnJson =  new JSONObject();
             columnJson.put("id",materialInfo.getUnit().getId());
             columnJson.put("name",materialInfo.getUnit().getName());
             budgetingDetailInfo.put("measureUnitInfo",columnJson);
             budgetingDetailInfo.put("specification",materialInfo.getSpecification());
             columnJson =  new JSONObject();
             columnJson.put("key",materialInfo.getMaterialType().getValue());
             columnJson.put("val",materialInfo.getMaterialType().getName());
             budgetingDetailInfo.put("materialType",columnJson);
             budgetingDetailInfoList.add(budgetingDetailInfo);
        }
        webDataModel.setStatusCode(Integer.parseInt(StatusCodeEnum.INFO.getValue()));
        webDataModel.setStatusMesg(buffer.toString());
        webDataModel.setData(budgetingDetailInfoList);
        return webDataModel;
    }
}
