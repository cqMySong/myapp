package com.myapp.core.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.myapp.core.annotation.PermissionItemAnn;
import com.myapp.core.base.entity.CoreBaseBillInfo;
import com.myapp.core.enums.BaseMethodEnum;
import com.myapp.core.enums.BillState;
import com.myapp.core.exception.db.SaveException;
import com.myapp.core.model.WebDataModel;
import com.myapp.core.util.BaseUtil;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年3月12日 
 * @system:
 *		编辑类
 *-----------MySong---------------
 */
public abstract class BaseBillEditController extends BaseEditController {
	
	@PermissionItemAnn(name="提交",number="submit")
	@ResponseBody
	@RequestMapping(value="/submit",method=RequestMethod.POST)
	public WebDataModel submit() {
		return billOperate(BaseMethodEnum.SUBMIT);
	}
	protected boolean beforeOperate(BaseMethodEnum bme)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException {
		boolean toGo = super.beforeOperate(bme);
		if(toGo&&(BaseMethodEnum.AUDIT.equals(bme)
				||BaseMethodEnum.UNAUDIT.equals(bme))){
			String billId = getReuestBillId();
			if(BaseUtil.isEmpty(billId)){
				setErrorMesg("单据ID为空，无法完成数据的["+bme.getName()+"]操作!");
				toGo = false;
			}
			if(toGo){
				setEditData(getService().getEntity(billId));
				beforeStoreData(bme,getEditData());
			}
		}
		return toGo;
	}
	
	
	protected void storeData(BaseMethodEnum bme, Object editData) throws SaveException {
		super.storeData(bme, editData);
		if(BaseMethodEnum.AUDIT.equals(bme)
				||BaseMethodEnum.UNAUDIT.equals(bme)){
			if(BaseMethodEnum.AUDIT.equals(bme)){
				getService().auditEntity(editData, getCurWebContext());
			}else{
				getService().unauditEntity(editData, getCurWebContext());
			}
		}
	}
	
	protected void beforeStoreData(BaseMethodEnum bme, Object editData) {
		super.beforeStoreData(bme, editData);
	}
	
	protected boolean verifyEditData(BaseMethodEnum bme){
		boolean toGo = super.verifyEditData(bme);
		if(!toGo) return toGo;
		Object editObj = null;
		if(BaseMethodEnum.REMOVE.equals(bme)){
			editObj = getService().getEntity(getReuestBillId());
		}else{
			editObj = getEditData();
		}
		if(editObj!=null&&editObj instanceof CoreBaseBillInfo){
			CoreBaseBillInfo cbInfo = (CoreBaseBillInfo) editObj; 
			BillState bs = cbInfo.getBillState();
			if(bs!=null){
				if(BaseMethodEnum.SAVE.equals(bme)
						||BaseMethodEnum.SUBMIT.equals(bme)){
					if(BillState.AUDIT.equals(bs)){
						toGo = false;
					}
				}else if(BaseMethodEnum.REMOVE.equals(bme)){
					if(BillState.AUDIT.equals(bs)){
						toGo = false;
					}
				}else if(BaseMethodEnum.AUDIT.equals(bme)){
					if(!BillState.SUBMIT.equals(bs)){
						toGo = false;
					}
				}else if(BaseMethodEnum.UNAUDIT.equals(bme)){
					if(!BillState.AUDIT.equals(bs)){
						toGo = false;
					}
				}
				
				if(!toGo){
					setErrorMesg("当前单据状态为["+bs.getName()+"]不允许"+bme.getName()+"操作!");
				}
			}
		}
		return toGo;
	}
	private WebDataModel billOperate(BaseMethodEnum bme){
		try{
			if(beforeOperate(bme)){
				storeData(bme);
				afterOperate(bme);
				setBaseMethod(BaseMethodEnum.VIEW);
				setInfoMesg("数据"+bme.getName()+"成功!");
			}
		}catch (Exception e) {
			e.printStackTrace();
			setExceptionMesg(e.getMessage());
		}
		return ajaxModel();
	}
	
	@PermissionItemAnn(name="审核",number="audit")
	@ResponseBody
	@RequestMapping(value="/audit",method=RequestMethod.POST)
	public WebDataModel audit() {
		return billOperate(BaseMethodEnum.AUDIT);
	}
	
	@PermissionItemAnn(name="反审核",number="unAudit")
	@ResponseBody
	@RequestMapping(value="/unAudit",method=RequestMethod.POST)
	public WebDataModel unAudit() {
		return billOperate(BaseMethodEnum.UNAUDIT);
	}
	
}
