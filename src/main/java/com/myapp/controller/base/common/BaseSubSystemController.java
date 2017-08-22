package com.myapp.controller.base.common;

import com.myapp.core.base.controller.BaseController;
import com.myapp.core.entity.SubsystemTreeInfo;
import com.myapp.core.enums.EntityTypeEnum;
import com.myapp.core.exception.db.QueryException;
import com.myapp.core.model.WebDataModel;
import com.myapp.core.service.base.BaseSubSystemService;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.EnumUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 包路径：com.myapp.controller.base.common
 * 功能说明：业务类型获数据获取
 * 创建人： ly
 * 创建时间: 2017-08-18 22:22
 */
@Controller
@RequestMapping("/subsystem")
public class BaseSubSystemController extends BaseController {
    @Resource
    private BaseSubSystemService baseSubSystemService;
    @RequestMapping("/combox/{entityType}")
    @ResponseBody
    public WebDataModel showSelect(@PathVariable String entityType) throws QueryException {
        try {
            init();
            data = baseSubSystemService.queryByEntityType(EnumUtil.getEnum(EntityTypeEnum.class.getName(),entityType));
        } catch (Exception e) {
            e.printStackTrace();
            setExceptionMesg(e.getMessage());
        }
        return ajaxModel();
    }
}
