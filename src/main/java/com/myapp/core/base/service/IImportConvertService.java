package com.myapp.core.base.service;

import com.myapp.core.model.WebDataModel;

import java.util.List;
import java.util.Map;

/**
 * @path：com.myapp.core.base.service
 * @description：
 * @author： ly
 * @date: 2017-10-26 23:18
 */
public interface IImportConvertService {
    /**
     * 功能：导入数据转换为业务需要的数据
     * @param importList
     * @return
     */
    public WebDataModel importConvertBusiness(List<Object> importList);
}
