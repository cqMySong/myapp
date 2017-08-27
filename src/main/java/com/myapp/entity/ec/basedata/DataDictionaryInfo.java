package com.myapp.entity.ec.basedata;

import com.myapp.core.annotation.MyEntityAnn;
import com.myapp.core.base.entity.CoreBaseDataInfo;
import com.myapp.enums.DataDicType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 包路径：com.myapp.entity.ec.basedata
 * 功能说明：
 * 创建人： ly
 * 创建时间: 2017-08-25 16:47
 */
@Entity
@MyEntityAnn(name="数据字典")
@Table(name="t_ec_data_dic")
public class DataDictionaryInfo  extends CoreBaseDataInfo {
    private DataDicType dataDicType;//类型
    private String pinyinCode;//拼音码

    public void setDataDicType(DataDicType dataDicType) {
        this.dataDicType = dataDicType;
    }
    @Column(name="fDataType",length = 20)
    public DataDicType getDataDicType() {
        return dataDicType;
    }
    @Column(name="fPinyin",length = 10)
    public String getPinyinCode() {
        return pinyinCode;
    }

    public void setPinyinCode(String pinyinCode) {
        this.pinyinCode = pinyinCode;
    }
}
