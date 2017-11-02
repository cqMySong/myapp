package com.myapp.model;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @path：com.myapp.model
 * @description：
 * @author： ly
 * @date: 2017-10-23 23:26
 */
public class BudgetingModel implements Serializable {

    @Excel(name="序号",isImportField = "true")
    private String sNo;
    /**
     * 物料编号
     */
    @Excel(name = "材料编号",isImportField = "true")
    private String materialCode;
    /**
     * 材料名称
     */
    @Excel(name = "材料名称",isImportField = "true")
    private String materialName;
    /**
     * 规格
     */
    @Excel(name = "规格",isImportField = "true")
    private String specification;
    /**
     * 计量单位
     */
    @Excel(name = "单位",isImportField = "true")
    private String measureUnit;
    /**
     * 预算数量
     */
    @Excel(name = "预算数量",isImportField = "true")
    private BigDecimal quantity;
    /**
     * 预算单价
     */
    @Excel(name = "预算单价",isImportField = "true")
    private BigDecimal budgetaryPrice;

    private BigDecimal totalPrice;

    public String getsNo() {
        return sNo;
    }

    public void setsNo(String sNo) {
        this.sNo = sNo;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getMeasureUnit() {
        return measureUnit;
    }

    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getBudgetaryPrice() {
        return budgetaryPrice;
    }

    public void setBudgetaryPrice(BigDecimal budgetaryPrice) {
        this.budgetaryPrice = budgetaryPrice;
    }

    public BigDecimal getTotalPrice() {
        if(quantity!=null && budgetaryPrice!=null){
            return quantity.multiply(budgetaryPrice);
        }
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
