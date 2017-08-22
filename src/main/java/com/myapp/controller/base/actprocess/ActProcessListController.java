package com.myapp.controller.base.actprocess;

import com.alibaba.fastjson.JSON;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseListController;
import com.myapp.core.enums.EntityTypeEnum;
import com.myapp.core.exception.db.QueryException;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.model.WebDataModel;
import com.myapp.core.service.act.ActProcessService;
import com.myapp.core.service.base.BaseSubSystemService;
import com.myapp.core.util.BaseUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.List;

/**
 * 包路径：com.myapp.controller.base.flow
 * 功能说明：流程使用信息
 * 创建人： ly
 * 创建时间: 2017-06-24 23:29
 */
@PermissionAnn(name="系统管理.流程管理",number="app.actprocess")
@RequestMapping("base/actprocesses")
@Controller
public class ActProcessListController extends BaseListController {
    @Resource
    private ActProcessService processService;
    @Resource
    private BaseSubSystemService baseSubSystemService;
    @Override
    public String getEditUrl() {
        return "";
    }

    @Override
    public String getListUrl() {
        return "actprocess/actProcessList";
    }
    @Override
    public WebDataModel toList() {
        init();
        data = processService.processList(getCurPage(),getPageSize(),null);
        return  ajaxModel();
    }
    @Override
    public ModelAndView list() {
        ModelAndView modelAndView= super.list();
        try {
            modelAndView.addObject("category",
                    JSON.toJSONString(baseSubSystemService.queryByEntityType(EntityTypeEnum.BIZBILL)));
        } catch (QueryException e) {
            e.printStackTrace();
        }
        return modelAndView;
    }
    /**
     * 挂起、激活流程实例
     */
    @RequestMapping(value = "update/{state}")
    @ResponseBody
    public WebDataModel updateState(@PathVariable("state") String state) {
        try {
            init();
            String billId = getReuestBillId();
            if(!BaseUtil.isEmpty(billId)){
                String message = processService.updateState(state, billId);
                setInfoMesg(message);
            }else{
                setErrorMesg("流程id为空,无法完成挂起/激活操作!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            setExceptionMesg(e.getMessage());
        }
        return ajaxModel();
    }
    @RequestMapping(value = "resource/read/{procDefId}/{resType}")
    public void resourceRead(@PathVariable("procDefId") String procDefId,@PathVariable("resType") String resType, HttpServletResponse response) throws Exception {
        InputStream resourceAsStream = processService.resourceRead(procDefId, null, resType);
        if(resType.equals("image")){
            response.setContentType("image/png");
        }else{
            response.setContentType("text/plain");
        }

        byte[] b = new byte[1024];
        int len = -1;
        while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
            response.getOutputStream().write(b, 0, len);
        }
    }
    @Override
    public AbstractBaseService getService() {
        return this.processService;
    }

    public List<ColumnModel> getDataBinding() {
        List<ColumnModel> cols = super.getDataBinding();
        cols.add(new ColumnModel("name"));
        cols.add(new ColumnModel("version"));
        cols.add(new ColumnModel("createDate"));
        cols.add(new ColumnModel("deploymentId"));
        return cols;
    }
}
