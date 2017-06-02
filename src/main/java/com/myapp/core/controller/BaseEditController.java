package com.myapp.core.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.Transformers;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.base.dao.MyResultTransFormer;
import com.myapp.core.base.entity.CoreBaseBillInfo;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.entity.CoreInfo;
import com.myapp.core.base.enums.MyEnum;
import com.myapp.core.enums.BaseMethodEnum;
import com.myapp.core.enums.BillState;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.exception.db.QueryException;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.model.WebDataModel;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.DateUtil;
import com.myapp.core.util.EnumUtil;
import com.myapp.core.util.WebUtil;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年3月12日 
 * @system:
 *		编辑类
 *-----------MySong---------------
 */
public abstract class BaseEditController extends CoreBaseController {
	
	public abstract Object createNewData();
	public abstract CoreBaseInfo getEntityInfo();
	
	protected boolean verifyInput(Object editData){
		boolean isVerify = true;
		if(editData!=null){
			if(editData instanceof CoreBaseInfo){
				CoreBaseInfo cbInfo = (CoreBaseInfo) editData;
				if(BaseUtil.isEmpty(cbInfo.getNumber())){
					setErrorMesg("单据编码为空,不允许保存");
					isVerify = false;
				}
				if(BaseUtil.isEmpty(cbInfo.getName())){
					setErrorMesg("单据名称为空,不允许保存");
					isVerify = false;
				}
			}
		}
		return isVerify;
	}
	protected boolean verifyInputSubmit(Object editData){
		return true;
	}
	
	protected boolean checkRemove(){
		return !BaseUtil.isEmpty(getReuestBillId());
	}
	
	protected void beforeStoreData(BaseMethodEnum bme,Object editData){
		
	}
	
	private void storeData(BaseMethodEnum bme){
		Object editData = getEditData();
		if(BaseMethodEnum.SAVE.equals(bme)){
			getService().saveEntity(editData);
		}else if(BaseMethodEnum.SUBMIT.equals(bme)){
			getService().submitEntity(editData);
		}
		setEditData(editData);
	}
	
	protected boolean beforeOperate(BaseMethodEnum bme){
		boolean toGo = true;
		init();
		if(BaseMethodEnum.ADDNEW.equals(bme)){
			setEditData(createNewData());
			initDefDataValue(getEditData());
		}else if(BaseMethodEnum.SAVE.equals(bme)
				||BaseMethodEnum.SUBMIT.equals(bme)){
			packageJson2EditData();
			beforeStoreData(bme,getEditData());
			toGo = verifyInput(getEditData());
			if(toGo&&BaseMethodEnum.SUBMIT.equals(bme)){
				verifyInputSubmit(getEditData());
			}
		}else if(BaseMethodEnum.EDIT.equals(bme)
				||BaseMethodEnum.VIEW.equals(bme)
				||BaseMethodEnum.REMOVE.equals(bme)){
			String billId = getReuestBillId();
			if(BaseUtil.isEmpty(billId)){
				String oprName = BaseMethodEnum.EDIT.equals(bme)?"编辑":(BaseMethodEnum.VIEW.equals(bme)?"查看":"删除");
				setErrorMesg("单据ID为空，无法完成数据的["+oprName+"]操作!");
				toGo = false;
			}else{
				if(BaseMethodEnum.REMOVE.equals(bme)){
					toGo = checkRemove();
				}
			}
		}
		return toGo;
	}
	
	private void afterOperate(BaseMethodEnum bme){
		packageEditData2Json();
	}
	
	private Object getEditDataVal(ColumnModel col,Object objVal){
		DataTypeEnum dte = col.getDataType();
		if(objVal==null){
			objVal = "";
			if(DataTypeEnum.F7.equals(dte)){
				objVal= "{}";
			}else if(DataTypeEnum.ENTRY.equals(dte)){
				objVal = "[]";
			}
		}else{
			if(DataTypeEnum.DATE.equals(dte)&&objVal instanceof Date){
				if(objVal!=null){
					objVal = DateUtil.formatDateByFormat((Date)objVal,col.getFormat());
				}
			}else if(DataTypeEnum.BOOLEAN.equals(dte)&&objVal instanceof Boolean){
				objVal = ((Boolean)objVal).booleanValue();
			}else if(DataTypeEnum.ENUM.equals(dte)&&objVal instanceof MyEnum){
				objVal = ((MyEnum)objVal).getValue();
			}else if(DataTypeEnum.F7.equals(dte)&&objVal instanceof CoreBaseInfo){
				String col_format = col.getFormat();
				if(!BaseUtil.isEmpty(col_format)){
					JSONObject jsonObj_f7 = new JSONObject();
					String[] _cols = col_format.split(",");
					CoreBaseInfo cbInfo = (CoreBaseInfo) objVal;
					for(String colItem :_cols){
						jsonObj_f7.put(colItem, cbInfo.get(colItem));
					}
					objVal = jsonObj_f7;
				}
			}else if(DataTypeEnum.ENTRY.equals(dte)&&objVal instanceof Set){
				Set entrys = (Set) objVal;
				List _entry = new ArrayList();
				List<ColumnModel> cols = col.getCols();
				if(entrys!=null&&entrys.size()>0&&cols!=null&&cols.size()>0){
					for(Object entryObj: entrys) {
						if(entryObj!=null&&entryObj instanceof CoreBaseInfo){
							CoreBaseInfo eInfo = (CoreBaseInfo) entryObj;
							Map eMap = new HashMap();
							for(ColumnModel ecol:cols){
								String name = ecol.getName();
								if(!BaseUtil.isEmpty(name)){
									eMap.put(name, getEditDataVal(ecol,eInfo.get(name)));
								}
							}
							_entry.add(eMap);
						}
					}
				}
				objVal = _entry;
			}
		}
		return objVal;
	} 
	
	private void packageEditData2Json(){
		Object editData = getEditData();
		if(editData!=null){
			if(editData instanceof CoreInfo){
				List<ColumnModel> cols = getDataBinding();
				JSONObject jsonObj = new JSONObject();
				CoreInfo info = (CoreInfo) editData;
				for(ColumnModel col:cols){
					String name = col.getName();
					if(!BaseUtil.isEmpty(name)){
						Object objVal = info.get(name);
						jsonObj.put(name, getEditDataVal(col,objVal));
					}
				}
				this.data = jsonObj;
			}else if(editData instanceof Map){
				this.data = JSONObject.toJSON(editData);
			}
		}
	}
	
	private void packageJson2EditData(){
		String editData_str = request.getParameter("editData");
		if(BaseUtil.isEmpty(editData_str)) return;
		CoreBaseInfo cbInfo = getEntityInfo();
		if(cbInfo!=null){
			List<ColumnModel> cols = getDataBinding();
			Map editData_map = JSONObject.parseObject(editData_str, new HashMap().getClass());
			for(ColumnModel col:cols){
				String name = col.getName();
				if(!BaseUtil.isEmpty(name)&&editData_map.containsKey(name)){
					Object val =  editData_map.get(name);
					if(val!=null) {
						DataTypeEnum dte = col.getDataType();
						if(DataTypeEnum.DATE.equals(dte)){
							val = DateUtil.parseDate(val.toString(), col.getFormat());
						}else if(DataTypeEnum.BOOLEAN.equals(dte)){
							val = ("1".equals(val.toString())||"true".equals(val.toString().toLowerCase()))?Boolean.TRUE:Boolean.FALSE;
						}else if(DataTypeEnum.F7.equals(dte)){
							val = getService().getEntity(col.getClaz(), WebUtil.UUID_ReplaceID(val.toString()));
						}else if(DataTypeEnum.ENUM.equals(dte)){
							val = EnumUtil.getEnum(col.getClaz().getName(), val.toString());
						}else if(DataTypeEnum.NUMBER.equals(dte)){
							BigDecimal bd =  new BigDecimal(val.toString());
							val = bd;
							Class cz = col.getClass();
							if(cz!=null){
								if(cz.equals(Double.class)){
									val = bd.doubleValue();
								}else if(cz.equals(Long.class)){
									val = bd.longValue();
								}else if(cz.equals(Float.class)){
									val = bd.floatValue();
								}else if(cz.equals(Short.class)){
									val = bd.shortValue();
								}
							}
						}else if(DataTypeEnum.INT.equals(dte)){
							val = Integer.valueOf(val.toString());
						}else if(DataTypeEnum.PK.equals(dte)){
							val = WebUtil.UUID_ReplaceID(val.toString());
						}else if(DataTypeEnum.ENTRY.equals(dte)){
							
						}
					}
					cbInfo.put(name, val);
				}
			}
		}
		setEditData(cbInfo);
	}
	
	private void setEditData(Object editData){
		this.data = editData;
	}
	
	private Object getEditData(){
		return this.data;
	}
	
	@ResponseBody
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public WebDataModel save() {
		try{
			if(beforeOperate(BaseMethodEnum.SAVE)){
				storeData(BaseMethodEnum.SAVE);
				afterOperate(BaseMethodEnum.SAVE);
				setBaseMethod(BaseMethodEnum.EDIT);
				setInfoMesg("数据保存成功!");
			}
		}catch (Exception e) {
			e.printStackTrace();
			setExceptionMesg(e.getMessage());
		}
		return ajaxModel();
	}
	
	protected void initDefDataValue(Object editData){
		if(editData!=null){
			if(editData instanceof CoreBaseInfo){
				CoreBaseInfo cbInfo = (CoreBaseInfo) editData;
				cbInfo.setCreateDate(new Date());
				//创建人
			}
			if(editData instanceof CoreBaseBillInfo){
				CoreBaseBillInfo cbbInfo = (CoreBaseBillInfo) editData;
				cbbInfo.setBillState(BillState.ADDNEW);
			}
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/addNew",method=RequestMethod.POST)
	public WebDataModel addNew() {
		try{
			if(beforeOperate(BaseMethodEnum.ADDNEW)){
				afterOperate(BaseMethodEnum.ADDNEW);
				setBaseMethod(BaseMethodEnum.EDIT);
			}
		}catch (Exception e) {
			e.printStackTrace();
			setExceptionMesg(e.getMessage());
		}
		return ajaxModel();
	}
	
	//单个单据查询 不包括集合对象查询
	public void packageEditBillCriteria(Criteria criter,ProjectionList projectList,ColumnModel cm){
		String key = cm.getName();
		String thisKey = cm.getName();
		String thisAlias = cm.getAlias();
		DataTypeEnum dte = cm.getDataType();
		if(DataTypeEnum.F7.equals(dte)){
			criter.createAlias(thisKey, thisAlias, cm.getJoinType());
			String f7_format = cm.getFormat();
			if(!BaseUtil.isEmpty(f7_format)){
				String[] _cols = f7_format.split(",");
				for(String colItem:_cols){
					projectList.add(createBaseField(thisKey+"."+colItem,thisAlias+"_"+colItem));
				}
			}
		}else if(!DataTypeEnum.ENTRY.equals(dte)){
			projectList.add(createBaseField(thisKey,thisAlias));
		}
	}
	
	private void loadData() throws QueryException{
		String billId = getReuestBillId();
		if(!BaseUtil.isEmpty(billId)){
			//如果是多分录的情况 这样是有问题的 
			
			Map editMap = new HashMap();
			Criteria billCrteria = getService().initQueryCriteria();
			ProjectionList billProjectList = Projections.projectionList();
			List<ColumnModel> cols = getDataBinding();
			List<String> entryCols  = new ArrayList<String>();
			Map<String,Object> F7Col = new HashMap<String, Object>();
			for(ColumnModel cm:cols){
				if(!DataTypeEnum.ENTRY.equals(cm.getDataType())){
					packageEditBillCriteria(billCrteria,billProjectList,cm);
					if(DataTypeEnum.F7.equals(cm.getDataType())){
						F7Col.put(cm.getName(), cm);
					}
				}else{
					Class entryClaz = cm.getClaz();
					List<ColumnModel> entry_cols = cm.getCols();
					if(entryClaz!=null){
						Criteria entryCrteria = getService().initQueryCriteria(entryClaz);
						entryCrteria.createAlias("parent", "parent", JoinType.FULL_JOIN);
						ProjectionList entryProjectList = Projections.projectionList();
						List<ColumnModel> entryF7Col =  new ArrayList<ColumnModel>();
						for(ColumnModel entry_cm:entry_cols){
							if(!DataTypeEnum.ENTRY.equals(entry_cm.getDataType())){
								packageEditBillCriteria(entryCrteria,entryProjectList,entry_cm);
								if(DataTypeEnum.F7.equals(entry_cm.getDataType())){
									entryF7Col.add(entry_cm);
								}
							}
						}
						entryCrteria.setProjection(entryProjectList);
						entryCrteria.add(Restrictions.eq("parent.id", billId));
						entryCrteria.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
						entryCols.add(cm.getName());
						if(entryF7Col.size()>0){
							F7Col.put(cm.getName(), entryF7Col);
						}
						editMap.put(cm.getName(), entryCrteria);
					}
				}
			}
			billCrteria.setProjection(billProjectList);
			billCrteria.add(Restrictions.eq("id", billId));
			billCrteria.setResultTransformer(new MyResultTransFormer(getEntityInfo().getClass()));
			List billData = billCrteria.list();
			if(billData!=null&&billData.size()>0){
				editMap = (Map) billData.get(0);
				for(String entryKey:entryCols){
					Criteria entryCrteria = (Criteria) editMap.get(entryKey);
					editMap.put(entryKey, entryCrteria.list());
				}
			}
			//然后再把f7的集合处理成对象
			 for (Map.Entry<String,Object> entry : F7Col.entrySet()) {
				 String colName = entry.getKey();
				 Object colObj = entry.getValue();
				 if(colObj!=null){
					 if(colObj instanceof ColumnModel){
						 ColumnModel cm = (ColumnModel) colObj;
						 packageF7DataMap(cm,editMap);
					 }else if(colObj instanceof List){//处理分录中的f7数据
						Object entrysObj = editMap.get(colName);
						if(entrysObj!=null&&entrysObj instanceof List){
							List entryDatas = (List) entrysObj;
							if(entryDatas.size()>0){
								List entryF7Cols = (List) colObj;
								for(int i=0;i<entryDatas.size();i++){
									Object entryDataMapObj = entryDatas.get(i);
									if(entryDataMapObj!=null&&entryDataMapObj instanceof Map){
										Map entryDataMap = (Map) entryDataMapObj;
										for(int j=0;j<entryF7Cols.size();j++){
											ColumnModel entryF7col = (ColumnModel) entryF7Cols.get(j);
											packageF7DataMap(entryF7col,entryDataMap);
										}
									}
								}
							}
						}
						 
					 }
				 }
			 }
			setEditData(editMap);
		}
	}
	
	private void packageF7DataMap(ColumnModel cm,Map dataMap){
		if(DataTypeEnum.F7.equals(cm.getDataType())){
			String[] _cols = cm.getFormat().split(",");
			Map f7Map = new HashMap();
			for(String colItem:_cols){
				String mapKey = cm.getAlias()+"_"+colItem;
				Object val = dataMap.get(mapKey);
				if(val==null) val =  "";
				f7Map.put(colItem, val);
				dataMap.remove(mapKey);
			}
			dataMap.put(cm.getName(), f7Map);
		 }
	}
	
	@ResponseBody
	@RequestMapping(value="/view",method=RequestMethod.POST)
	public WebDataModel view() {
		try{
			if(beforeOperate(BaseMethodEnum.VIEW)){
				loadData();
				afterOperate(BaseMethodEnum.VIEW);
				setBaseMethod(BaseMethodEnum.VIEW);
			}
		}catch (Exception e) {
			e.printStackTrace();
			setExceptionMesg(e.getMessage());
		}
		return ajaxModel();
	}
	
	@ResponseBody
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	public WebDataModel edit() {
		try{
			if(beforeOperate(BaseMethodEnum.EDIT)){
				loadData();
				afterOperate(BaseMethodEnum.EDIT);
				setBaseMethod(BaseMethodEnum.EDIT);
			}
		}catch (Exception e) {
			e.printStackTrace();
			setExceptionMesg(e.getMessage());
		}
		return ajaxModel();
	}
	
	@ResponseBody
	@RequestMapping(value="/remove",method=RequestMethod.POST)
	public WebDataModel toRemove() {
		try {
			if(beforeOperate(BaseMethodEnum.REMOVE)){
				getService().deleteEntity(getReuestBillId());
				afterOperate(BaseMethodEnum.EDIT);
				setBaseMethod(BaseMethodEnum.EDIT);
			}
		} catch (Exception e) {
			e.printStackTrace();
			setExceptionMesg(e.getMessage());
		}
		return ajaxModel();
	}
	
	protected List getDataParams(){
		List params = new ArrayList();
		params.add(getReuestBillId());
		return params;
	}
	
}
