package com.myapp.controller.base.actmodel;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.annotation.PermissionItemAnn;
import com.myapp.core.base.controller.BaseController;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseListController;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.model.PageModel;
import com.myapp.core.model.WebDataModel;
import com.myapp.core.service.act.ActModelService;
import com.myapp.core.util.BaseUtil;
import org.activiti.engine.repository.Model;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 包路径：com.myapp.controller.base.flow
 * 功能说明：流程使用信息
 * 创建人： ly
 * 创建时间: 2017-06-24 23:29
 */
@PermissionAnn(name="系统管理.模型管理",number="app.actmodel")
@RequestMapping("base/actmodels")
@Controller
public class ActModelListController extends BaseListController {
    @Resource
    private ActModelService actModelService;

    @Override
    public String getEditUrl() {
        return "actmodel/actModelAdd";
    }

    @Override
    public String getListUrl() {
        return "actmodel/actModelList";
    }

    @Override
    public WebDataModel toList() {
        init();
        data = actModelService.modelList(getCurPage(),getPageSize(),null);
        return  ajaxModel();
    }
    @PermissionItemAnn(name="部署",number="deploy")
    @ResponseBody
    @RequestMapping("/deploy")
    public WebDataModel actDeploy(){
        try {
            init();
            String billId = getReuestBillId();
            if(!BaseUtil.isEmpty(billId)){
                String msg = actModelService.deploy(billId);
                setInfoMesg(msg);
            }else{
                setErrorMesg("模型id为空,无法完成部署操作!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            setExceptionMesg(e.getMessage());
        }
        return ajaxModel();
    }
    @PermissionItemAnn(name="导出",number="export")
    @RequestMapping(value = "/export/{actModelId}")
    public ResponseEntity<byte[]> actExport(@PathVariable String actModelId) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        //下载显示的文件名，解决中文名称乱码问题
        String downloadFielName = new String((actModelId+".bpmn20.xml").getBytes("UTF-8"),"iso-8859-1");
        //通知浏览器以attachment（下载方式）打开图片
        headers.setContentDispositionFormData("attachment", downloadFielName);
        //application/octet-stream ： 二进制流数据（最常见的文件下载）。
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<byte[]>(actModelService.export(actModelId),
                headers, HttpStatus.CREATED);
    }
    @Override
    public AbstractBaseService getService() {
        return this.actModelService;
    }

    public List<ColumnModel> getDataBinding() {
        List<ColumnModel> cols = super.getDataBinding();
        cols.add(new ColumnModel("name"));
        cols.add(new ColumnModel("key"));
        cols.add(new ColumnModel("createDate"));
        return cols;
    }

}
