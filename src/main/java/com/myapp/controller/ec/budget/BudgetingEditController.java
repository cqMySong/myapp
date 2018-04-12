package com.myapp.controller.ec.budget;

import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;

import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.service.IImportConvertService;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseBillEditImportController;
import com.myapp.core.entity.MeasureUnitInfo;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.BillState;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.enums.ResultTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.entity.MaterialInfo;
import com.myapp.core.model.ResultModel;
import com.myapp.core.service.MaterialService;
import com.myapp.core.util.BaseUtil;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.entity.ec.budget.BudgetingDetailInfo;
import com.myapp.entity.ec.budget.BudgetingInfo;
import com.myapp.core.enums.MaterialType;
import com.myapp.model.BudgetingModel;
import com.myapp.service.ec.budget.BudgetingService;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @path：com.myapp.controller.ec.budget
 * @description：材料设备预算清单
 * @author ： ly
 * @date: 2017-07-30 14:49
 */
@PermissionAnn(name="系统管理.现场管理.预算.材料设备预算清单",number="app.ec.budget.budgeting")
@Controller
@RequestMapping("ec/budget/budgeting")
public class BudgetingEditController extends BaseBillEditImportController {
    @Resource
    private BudgetingService budgetingService;
    @Resource
    private MaterialService materialService;

    @Override
    public Object createNewData() {
        return new BudgetingInfo();
    }
    @Override
    public CoreBaseInfo getEntityInfo() {
        return new BudgetingInfo();
    }
    @Override
    public AbstractBaseService getService() {
        return this.budgetingService;
    }

    @Override
    public List<ColumnModel> getDataBinding() {
        List<ColumnModel> cols = super.getDataBinding();
        cols.add(new ColumnModel("name"));
        cols.add(new ColumnModel("number"));
        cols.add(new ColumnModel("remark"));
        cols.add(new ColumnModel("billState",DataTypeEnum.ENUM, BillState.class));
        cols.add(new ColumnModel("createUser",DataTypeEnum.F7,UserInfo.class));
        cols.add(new ColumnModel("lastUpdateUser",DataTypeEnum.F7,UserInfo.class));
        cols.add(new ColumnModel("auditor",DataTypeEnum.F7,UserInfo.class));
        cols.add(new ColumnModel("createDate", DataTypeEnum.DATE));
        cols.add(new ColumnModel("auditDate", DataTypeEnum.DATE));
        cols.add(new ColumnModel("lastUpdateDate", DataTypeEnum.DATE));
        ColumnModel project = new ColumnModel("project",DataTypeEnum.F7,"id,name");
        project.setClaz(ProjectInfo.class);
        cols.add(project);

        ColumnModel budgetingDetail = new ColumnModel("budgetingDetailInfos",DataTypeEnum.ENTRY,
                BudgetingDetailInfo.class);

        ColumnModel material = new ColumnModel("material",DataTypeEnum.F7,"id,name,specification");
        material.setClaz(MaterialInfo.class);
        budgetingDetail.getCols().add(material);

        ColumnModel measureUnitInfo = new ColumnModel("measureUnitInfo",DataTypeEnum.F7,"id,name");
        measureUnitInfo.setClaz(MeasureUnitInfo.class);
        budgetingDetail.getCols().add(measureUnitInfo);

        budgetingDetail.getCols().add(new ColumnModel("id",DataTypeEnum.PK));
        budgetingDetail.getCols().add(new ColumnModel("budgetaryPrice",DataTypeEnum.NUMBER));
        budgetingDetail.getCols().add(new ColumnModel("quantity",DataTypeEnum.NUMBER));
        budgetingDetail.getCols().add(new ColumnModel("specification"));
        budgetingDetail.getCols().add(new ColumnModel("remark"));
        budgetingDetail.getCols().add(new ColumnModel("materialName"));
        budgetingDetail.getCols().add(new ColumnModel("materialType",DataTypeEnum.ENUM, MaterialType.class));
        cols.add(budgetingDetail);

        return cols;
    }
    @Override
    public ExcelImportResult toDoImportData(InputStream in) throws Exception {
        setAbort(true);
        return super.toDoImportData(in);
    }
    @Override
    public String getHeadTitle() {
    	return "材料设备预算清单";
    }
    @Override
    public int[] getHeadRows() {
    	return new int[]{3,1};
    }
    @Override
    public String getFillRemark() {
    	StringBuffer sb = new StringBuffer();
    	sb.append("\r\n\t 1:材料编码不能为空!");
    	sb.append("\r\n\t 2:材料名称不能为空!");
        sb.append("\r\n\t 3:规格不能为空!");
        sb.append("\r\n\t 4:单位不能为空!");
    	sb.append("\r\n\t 5:预算单价和数量为数字类型!");
    	return sb.toString();
    }
    @Override
    public short getRemrkHeight() {
    	return 1080;
    }
    @Override
    public List<ExcelExportEntity> getExportHeader() {
    	List<ExcelExportEntity> headers = new ArrayList<ExcelExportEntity>();
    	headers.add(stringEntity("序号", "seq"));
    	headers.add(stringEntity("材料编号", "matNumber"));
    	headers.add(stringEntity("材料名称", "matName"));
    	headers.add(stringEntity("规格", "model"));
    	headers.add(stringEntity("单位", "unit"));
    	headers.add(stringEntity("预算数量", "qty"));
    	headers.add(stringEntity("预算单价", "price"));
    	return headers;
    }
    private MaterialInfo materialInfo = null;
    private JSONObject budgetingDetailInfo = null;
    private JSONObject columnJson = null;
    @Override
    public ResultModel verifyRowData(int rowIdx, Map rowMap) {
        ResultModel rm = new ResultModel();
        if(rowMap!=null&&rowMap.size()>0){
            StringBuffer msg = new StringBuffer();
            Object obj = rowMap.get("qty");
            if(obj==null){
                msg.append("<br/>预算数量为空，不允许导入!");
            }else if(!BaseUtil.isNumeric(obj.toString())){
                msg.append("<br/>预算数量，不是数字类型的不允许导入!");
            }
            obj = rowMap.get("price");
            if(obj==null){
                msg.append("<br/>预算单价为空，不允许导入!");
            }else if(!BaseUtil.isNumeric(obj.toString())){
                msg.append("<br/>预算单价，不是数字类型的不允许导入!");
            }
            obj = rowMap.get("matNumber");
            if(obj==null){
                msg.append("<br/>材料编号为空，不允许导入!");
            }
            obj = rowMap.get("matName");
            if(obj==null){
                msg.append("<br/>材料名称为空，不允许导入!");
            }
            obj = rowMap.get("model");
            if(obj==null){
                msg.append("<br/>规格为空，不允许导入!");
            }
            obj = rowMap.get("unit");
            if(obj==null){
                msg.append("<br/>单位为空，不允许导入!");
            }
            if(!BaseUtil.isEmpty(msg.toString())){
                rm.setResultType(ResultTypeEnum.ERROR);
                rm.setMesg("第["+(rowIdx+5)+"]行中:"+msg.toString());
            }else{
                try {
                    materialInfo = materialService.saveOrUpdate(rowMap);
                    budgetingDetailInfo = new JSONObject();
                    budgetingDetailInfo.put("quantity",rowMap.get("qty"));
                    budgetingDetailInfo.put("budgetaryPrice",rowMap.get("price"));
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
                    rowMap.put("returnJson",budgetingDetailInfo);
                }catch (Exception ex){
                    rm.setMesg("第["+(rowIdx+5)+"]行导入信息失败");
                }
            }
        }
        return rm;
    }
}
