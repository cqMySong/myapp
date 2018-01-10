package com.myapp.core.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;

import com.myapp.core.annotation.PermissionItemAnn;
import com.myapp.core.base.entity.CoreBaseDataInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.model.WebDataModel;
import com.myapp.core.util.BaseUtil;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年6月12日 
 * @system:
 *
 *-----------MySong---------------
 */
public abstract class BaseDataListController extends BaseListController {
	
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("number"));
		cols.add(new ColumnModel("enabled"));
		cols.add(new ColumnModel("remark"));
		return cols;
	}
	
	public String getHeadTitle() {
		return "基础资料信息";
	}
	
	public List<ExcelExportEntity> getExportHeader() {
		List<ExcelExportEntity> entity = new ArrayList<ExcelExportEntity>();
		entity.add(new ExcelExportEntity("编码", "number"));
		entity.add(new ExcelExportEntity("名称", "name",80));
		ExcelExportEntity enabled = new ExcelExportEntity("启用", "enabled");
		enabled.setReplace(getBooleanReplace());
		entity.add(enabled);
		ExcelExportEntity remark = new ExcelExportEntity("备注", "remark");
		remark.setWidth(100);
		remark.setWrap(true);
		entity.add(remark);
		return entity;
	}
	
	@PermissionItemAnn(name="启用",number="enable")
	@RequestMapping(value="/enable")
	@ResponseBody
	public WebDataModel toEnabled() {
		try {
			init();
			String billId = getReuestBillId();
			AbstractBaseService server = getService();
			if(!BaseUtil.isEmpty(billId)){
				if(billId.indexOf(",")<0){
					enableData(server,billId,true);
				}else{
					String[] billIds = billId.split(",");
					for(int i=0;i<billIds.length;i++){
						enableData(server,billIds[i],true);
					}
				}
				setInfoMesg("数据启用成功!");
			}else{
				setErrorMesg("单据id为空，无法完成启用操作!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			setExceptionMesg(e.getMessage());
		}
		return ajaxModel();
	}
	
	@PermissionItemAnn(name="禁用",number="disable")
	@RequestMapping(value="/disable")
	@ResponseBody
	public WebDataModel toDisabled() {
		try {
			init();
			String billId = getReuestBillId();
			AbstractBaseService server = getService();
			if(!BaseUtil.isEmpty(billId)){
				if(billId.indexOf(",")<0){
					enableData(server,billId,false);
				}else{
					String[] billIds = billId.split(",");
					for(int i=0;i<billIds.length;i++){
						enableData(server,billIds[i],false);
					}
				}
				setInfoMesg("数据禁用成功!");
			}else{
				setErrorMesg("单据id为空，无法完成禁用操作!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			setExceptionMesg(e.getMessage());
		}
		return ajaxModel();
	}
	
	private void enableData(AbstractBaseService server,String id,boolean enabled) throws Exception{
		if(server==null) return;
		if(BaseUtil.isEmpty(id)) throw new Exception("单据ID为空,无法完成相关操作!");
		Object obj = server.getEntity(id);
		if(obj!=null){
			if(obj instanceof CoreBaseDataInfo){
				CoreBaseDataInfo cbdInfo = (CoreBaseDataInfo)obj;
				cbdInfo.setEnabled(enabled);
				server.saveEntity(cbdInfo);
			}
		}else{
			throw new Exception("单据对象为空,无法完成相关操作!");
		}
	}
	
}
