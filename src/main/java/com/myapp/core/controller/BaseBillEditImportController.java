package com.myapp.core.controller;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.alibaba.fastjson.JSONObject;
import com.myapp.core.base.service.IImportConvertService;
import com.myapp.core.entity.AttachFileInfo;
import com.myapp.core.model.WebDataModel;
import com.myapp.core.service.base.AttachFileService;
import com.myapp.core.util.UploadUtil;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @path：com.myapp.core.controller
 * @description：含有文件导入功能的业务单据
 * @author： ly
 * @date: 2017-10-24 22:08
 */
public abstract class BaseBillEditImportController extends BaseBillEditController {
    @Resource
    public AttachFileService attachFileService;
    /**
     * 功能：获取excel导入转换的实体对象
     * @return
     */
    public abstract Class getExcelToEntityClass();

    /**
     * 功能：获取excel的读取参数
     * @return
     */
    public abstract ImportParams getExcelImportParams();

    /**
     * 获取导入后业务处理类
     * @return
     */
    public abstract IImportConvertService getImportConvertService();

    /**
     * 功能:获取模版下载名称
     * @return
     */
    public abstract String getImportTemplateName();

    /**
     * 共鞥：跳转到导入操作界面
     * @return
     */
    @RequestMapping(value = "/import/view")
    public String forwardImportView(Model model, HttpServletRequest request){
        model.addAttribute("uploadPath",request.getRequestURI().replace("/import/view","/upload"));
        model.addAttribute("templateName",getImportTemplateName());
        return  "base/excel_import";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String upload(@RequestBody MultipartFile file
            , @RequestParam String fileName, @RequestParam String md5
            ,@RequestParam String sourceBillID, @RequestParam String formatSize
            , @RequestParam String fileSize){
        init();
        try {
            Map params = new HashMap();
            params.put("md5", md5);
            params.put("fileName", fileName);
            params.put("file", file);
            params.put("sourceBillID", sourceBillID);
            params.put("formatSize", formatSize);
            params.put("fileSize", fileSize);
            String attachDir = UploadUtil.UPLOAD_DEFPATH;
            Object attDirObj = request.getServletContext().getAttribute("attachDir");
            if(attDirObj!=null){
                attachDir = attDirObj.toString();
            }
            Map retMap = UploadUtil.toUpLoadFile(attachFileService, params, attachDir);
            boolean isOk = (boolean) retMap.get("status");
            if(isOk){
                AttachFileInfo atFile = (AttachFileInfo) retMap.get("attachInfo");
                if(atFile!=null&&atFile.getItems().size()>0){
                    ImportParams importParams = getExcelImportParams();
                    if(importParams==null){
                        importParams = new ImportParams();
                        importParams.setTitleRows(0);
                        importParams.setHeadRows(1);
                    }
                    List<Object> list = ExcelImportUtil.importExcel(
                            new File(atFile.getPath()+"/"+atFile.getFile()),
                            getExcelToEntityClass(), importParams);
                    WebDataModel convertResult = getImportConvertService().importConvertBusiness(list);
                    this.statusMesg = convertResult.getStatusMesg();
                    this.data = convertResult.getData();
                }else{
                    this.statusCode = STATUSCODE_ERROR;
                    this.statusMesg = "文件上传失败！";
                }
            }else{
                this.statusCode = STATUSCODE_ERROR;
                this.statusMesg = (String) retMap.get("mesg");
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.statusCode = STATUSCODE_EXCEPTION;
            this.statusMesg = e.getMessage();
        }
        JSONObject json = new JSONObject();
        json.put("status", true);
        json.put("errMesg", this.statusMesg);
        json.put("data",this.data);
        request.setAttribute("result",json.toJSONString());
        return "base/upload";
    }
}
