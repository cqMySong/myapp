package com.myapp.core.controller;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import cn.afterturn.easypoi.excel.entity.result.ExcelVerifyHanlderResult;
import cn.afterturn.easypoi.excel.imports.ExcelImportServer;
import cn.afterturn.easypoi.exception.excel.ExcelExportException;
import cn.afterturn.easypoi.handler.inter.IExcelVerifyHandler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.AuthorAnn;
import com.myapp.core.enums.FileType;
import com.myapp.core.model.ResultModel;
import com.myapp.core.model.WebDataModel;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.MyExcelExportUtil;

/**
 * @path：com.myapp.core.controller
 * @description：含有文件导入功能的业务单据
 * @author： ly
 * @date: 2017-10-24 22:08
 */
public abstract class BaseBillEditImportController extends BaseBillEditController {
    //此方法可以暂时保留 方便以后灵活运用
	public String getImportTemplateName(){
    	return "";
    }

    /**
     * 功能：跳转到导入操作界面
     * @return
     */
    @RequestMapping(value = "/import/view")
    public String forwardImportView(Model model, HttpServletRequest request){
        model.addAttribute("uploadPath",request.getRequestURI().replace("/import/view","/importData"));
        model.addAttribute("downTempURL",request.getRequestURI().replace("/import/view","/downTemp"));
        model.addAttribute("templateName",getImportTemplateName());
        model.addAttribute("uiCtx",JSON.toJSONString(getUiCtx()));
        return  "base/excel_import";
    }
    
    private boolean isAbort = true;//验证到错误是否终止数据的解析
	private int rowIdx = 0;
	private static String resultKey = "__result";
	private Map uiParams = new HashMap();
	
	public Map getParams(){
		return this.uiParams;
	}
	public ResultModel verifyRowData(int rowIdx,Map obj){
		return new ResultModel();
	}
	
	public ExcelVerifyHanlderResult toVerifyHandler(Map obj) {
		rowIdx++;
		ResultModel result = verifyRowData(rowIdx,obj);
		boolean success = true;
		String mesg = "";
		if(result!=null){
			success = result.isSuccess();
			mesg = result.getMesg();
			if(!success){
				if(isAbort){
					throw new ExcelExportException(mesg);
				}else{
					success = true;
					obj.put(resultKey, result);
				}
			}else{
				obj.put(resultKey, result);
			}
		}
		return new ExcelVerifyHanlderResult(success,mesg);
	}
	
	public int getTitleRows(){
		int trow = 2;
		if(getFillRemark()!=null) trow = 3;
		return trow;//包括主标题和副标题
	}
	
	public int[] getHeadRows(){
		//form：从哪一行开始 ,length: 跨度
		return new int[]{2,1};
	}
	
	public ImportParams initImportParams(){
		ImportParams params = new ImportParams();
		params.setTitleRows(getTitleRows()+getHeadRows()[1]);//把表列那一行也算着title
		params.setHeadRows(1);//
		params.setNeedVerfiy(false);
		params.setVerifyHanlder(new IExcelVerifyHandler<Map>() {
			public ExcelVerifyHanlderResult verifyHandler(Map obj) {
				return toVerifyHandler(obj);
			}
		});
		return params;
	}
	
	public ExcelImportResult toDoImportData(InputStream in) throws Exception{
		if(in==null) return null;
		ExcelImportServer server = new ExcelImportServer();
		return server.importExcelByIs(in, Map.class, initImportParams());
	}
	
	public Map<String ,String> getHeadMap(List<ExcelExportEntity> headers){
		Map<String ,String> hm = new HashMap<String,String>();
		if(headers!=null&&headers.size()>0){
			if(headers!=null&&headers.size()>0){
				for(ExcelExportEntity entity : headers){
					String key = entity.getGroupName();
					if(key==null) key = "";
					if(!BaseUtil.isEmpty(key)) key +="_";
					key +=entity.getName();
					Object headKey = entity.getKey();
					if(headKey!=null){
						hm.put(headKey.toString(), key+"_"+headKey);
					}
				}
			}
		}
		return hm;
	}
	
	public WebDataModel packageImportData(List<Map<String,Object>> datas,String params){
		WebDataModel wdm = new WebDataModel();
		wdm.setData(datas);
		if(datas.size()>0){
			wdm.setStatusCode(STATUSCODE_INFO);
			wdm.setStatusMesg("数据导入成功("+datas.size()+")!");
		}else{
			wdm.setStatusCode(STATUSCODE_WARNING);
			wdm.setStatusMesg("无任何数据导入，请注意检查模板!");
		}
		
		return wdm;
	}
	
	@AuthorAnn(doLongin=false,doPermission=false)
	@RequestMapping(value="importData",method = RequestMethod.POST)
	public String importData(@RequestBody MultipartFile file,@RequestParam String uiCtx){
		WebDataModel wdm = new WebDataModel();
		try{
			if(file!=null){
				if(!BaseUtil.isEmpty(uiCtx)){
					uiParams = JSONObject.parseObject(uiCtx, new HashMap().getClass());
				}
				ExcelImportResult eir = toDoImportData(file.getInputStream());
				List<Map<String,Object>> importDatas = eir.getList();
				if(importDatas!=null&&importDatas.size()>0){
					wdm = packageImportData(importDatas,uiCtx);
				}else{
					wdm.setData(null);
					wdm.setStatusCode(STATUSCODE_WARNING);
					wdm.setStatusMesg("无任何数据导入，请注意检查模板!");
				}
			}else{
				wdm.setData(null);
				wdm.setStatusMesg("上传文件为空!");
				wdm.setStatusCode(STATUSCODE_WARNING);
			}
		}catch(Exception e){
			e.printStackTrace();
			setExceptionMesg(e.getMessage());
			wdm.setStatusMesg("数据处理异常!"+e.getMessage().replaceAll("cn.afterturn.easypoi.exception.excel.ExcelExportException:", ""));
			wdm.setStatusCode(STATUSCODE_EXCEPTION);
		}
		JSONObject json = new JSONObject();
        json.put("statusCode", wdm.getStatusCode());
        json.put("statusMesg", wdm.getStatusMesg());
        json.put("data",wdm.getData());
        request.setAttribute("result",json.toJSONString());
        return "base/upload";
	}
	
	public String getHeadTitle() {
		return "数据模板";
	}
	
	public String getSecondTitle() {
		return getHeadTitle()+" v1";
	}
	
	public CellStyle getHeadStyle(Workbook workbook){
		CellStyle cellStyle= workbook.createCellStyle(); 
		cellStyle.setWrapText(true);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setBorderBottom(BorderStyle.DOTTED); //下边框    
		cellStyle.setBorderLeft(BorderStyle.DOTTED);//左边框    
		cellStyle.setBorderTop(BorderStyle.DOTTED);//上边框    
		cellStyle.setBorderRight(BorderStyle.DOTTED);//右边框   
		cellStyle.setLocked(true);
		cellStyle.setDataFormat(getDataFormat("@"));
		return cellStyle;
	}
	
	public CellStyle getColumnStyle(Workbook workbook,String key){
		CellStyle columnStyle= workbook.createCellStyle();
		columnStyle.setDataFormat(getDataFormat("@"));
		return columnStyle;
	}
	
	public void initHeader(Workbook workbook,List<ExcelExportEntity> headers){
		int[] rows = getHeadRows();
		if(rows!=null&&rows.length==2){
			Sheet sheet = workbook.getSheetAt(0);
			int startRowIdx = rows[0];
			int length = rows[1];
			CellStyle headStyle = getHeadStyle(workbook);
			Row hedadKeyRow = sheet.createRow(startRowIdx+length);
			for(int i=0;i<length;i++){
				Row headRow = sheet.getRow(startRowIdx+i);
				if(headRow!=null){
					for(int j=0;j<headers.size();j++){
						Cell tCell = headRow.getCell(j);
						if(tCell==null){
							tCell = headRow.createCell(j, CellType.STRING);
						}
						if(tCell!=null) tCell.setCellStyle(headStyle);
						if(i==length-1){
							String key = headers.get(j).getKey().toString();
							sheet.setDefaultColumnStyle(j, getColumnStyle(workbook,key));
							Cell keyCell = hedadKeyRow.createCell(j);
							keyCell.setCellValue(key);
						}
					}
				}
			}
			sheet.createFreezePane(0, startRowIdx+length);
			hedadKeyRow.setZeroHeight(true);
		}
	}
	
	public short getRemrkHeight(){
		return (short)800;//800 大约为三行显示
	}
	
	public String getFillRemark(){
		return "";
	}
	
	//此方法只能用于一些内置的格式
	public short getDataFormat(String format){
		if(BaseUtil.isEmpty(format))  format = "@";
		return HSSFDataFormat.getBuiltinFormat(format);
	}
	
	public CellStyle getRemarkStyle(Workbook workbook){
		CellStyle cellStyle= workbook.createCellStyle(); 
		cellStyle.setWrapText(true);
		cellStyle.setAlignment(HorizontalAlignment.LEFT);
		cellStyle.setVerticalAlignment(VerticalAlignment.TOP);
		cellStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setBorderBottom(BorderStyle.DOTTED); //下边框    
		cellStyle.setBorderLeft(BorderStyle.DOTTED);//左边框    
		cellStyle.setBorderTop(BorderStyle.DOTTED);//上边框    
		cellStyle.setBorderRight(BorderStyle.DOTTED);//右边框   
		cellStyle.setLocked(true);
		cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("@"));
		return cellStyle;
	}
	public void initRemark(Workbook workbook,int length){
		int[] rows = getHeadRows();
		String remark = getFillRemark();
		if(remark!=null&&rows!=null&&rows.length==2){
			int startRowIndx =  rows[0]-1;
			Sheet sheet = workbook.getSheetAt(0);
			int lastRowNo = sheet.getLastRowNum();  
			sheet.shiftRows(startRowIndx, lastRowNo, 1);

			StringBuffer rtx = new StringBuffer();
			rtx.append("★★★填写说明:");
			rtx.append(remark);
			RichTextString reamrk = new HSSFRichTextString(rtx.toString());
			
			Row row = sheet.createRow(startRowIndx);
			row.setHeight(getRemrkHeight());
			CellStyle remarkStyle = getRemarkStyle(workbook);
			for(int i=0;i<length;i++){
				Cell cell = row.createCell(i);
				cell.setCellStyle(remarkStyle);
				if(i==0) cell.setCellValue(new HSSFRichTextString(rtx.toString()));
			}
			sheet.addMergedRegion(new CellRangeAddress(startRowIndx,startRowIndx,0,length-1));
		}
	}
	
	
	@AuthorAnn(doLongin=false,doPermission=false)
	@RequestMapping(value="/downTemp",produces= MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void downTemp(HttpServletRequest request,HttpServletResponse response){
		FileType fileType = getExportType();
    	if(fileType!=null&&FileType.EXCEL.equals(fileType)){
    		try{
    			ExportParams params = getExportParams();
        		List<ExcelExportEntity> headers = getExportHeader();
        		if(params!=null&&headers!=null&&headers.size()>0){
        			for(int i=0;i<headers.size();i++ ){
        				headers.get(i).setOrderNum(i+1);
        			}
    				Workbook workbook = MyExcelExportUtil.exportExcel(params, headers, new ArrayList()); 
    				initRemark(workbook,headers.size());
    				initHeader(workbook,headers);
    				String fileName = getFileName();
    				if(BaseUtil.isEmpty(fileName)) fileName = "数据模板导出";
    				fileName =  new String(fileName.getBytes("gb2312"),"iso8859-1")+".xls";
    				response.setHeader("content-Type", "application/vnd.ms-excel");
    				response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
    				response.setCharacterEncoding("UTF-8");
    				workbook.write(response.getOutputStream());
        		}
    		}catch (Exception e) {
    			e.printStackTrace();
    			setExceptionMesg(e.getMessage());
    		}
    	}
	}

	public boolean isAbort() {
		return isAbort;
	}

	public void setAbort(boolean isAbort) {
		this.isAbort = isAbort;
	}
	
	
}
