package com.myapp.core.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.PermissionItemAnn;
import com.myapp.core.base.dao.MyResultTransFormer;
import com.myapp.core.base.entity.CoreBaseBillInfo;
import com.myapp.core.base.entity.CoreBaseEntryInfo;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.entity.CoreInfo;
import com.myapp.core.base.enums.MyEnum;
import com.myapp.core.enums.BaseMethodEnum;
import com.myapp.core.enums.BillState;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.enums.PermissionTypeEnum;
import com.myapp.core.exception.db.DeleteException;
import com.myapp.core.exception.db.QueryException;
import com.myapp.core.exception.db.SaveException;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.model.KeyValueModel;
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
	public abstract CoreInfo getEntityInfo();
	
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
	
	protected void storeData(BaseMethodEnum bme) throws SaveException, QueryException{
		Object editData = getEditData();
		if(editData!=null&&editData instanceof CoreBaseBillInfo){
			CoreBaseBillInfo cbInfo = (CoreBaseBillInfo) editData;
			cbInfo.setLastUpdateUser(getCurUser());
		}
		if(BaseMethodEnum.SAVE.equals(bme)){
			getService().saveEntity(editData);
		}else if(BaseMethodEnum.SUBMIT.equals(bme)){
			getService().submitEntity(editData);
		}else{
			storeData(bme,editData);
		}
		
		if(editData!=null&&editData instanceof CoreInfo){
			String billId = ((CoreInfo)editData).getId();
			if(!BaseUtil.isEmpty(billId)){
				loadData(billId);
			}
		}
	}
	protected void storeData(BaseMethodEnum bme,Object editData) throws SaveException{
	}
	
	protected boolean beforeOperate(BaseMethodEnum bme) throws ClassNotFoundException, InstantiationException, IllegalAccessException, DeleteException{
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
				toGo = verifyInputSubmit(getEditData());
			}
		}else if(BaseMethodEnum.EDIT.equals(bme)
				||BaseMethodEnum.VIEW.equals(bme)
				||BaseMethodEnum.REMOVE.equals(bme)){
			String billId = getReuestBillId();
			if(BaseUtil.isEmpty(billId)){
				setErrorMesg("单据ID为空，无法完成数据的["+bme.getName()+"]操作!");
				toGo = false;
			}else{
				if(BaseMethodEnum.REMOVE.equals(bme)){
					toGo = checkRemove();
				}
			}
		}
		if(toGo){
			toGo = verifyEditData(bme);
		}
		return toGo;
	}
	protected boolean verifyEditData(BaseMethodEnum bme){
		return true;
	}
	protected void afterOperate(BaseMethodEnum bme) throws HibernateException, QueryException{
		packageEditData2Json();
	}
	
	private Object getEditF7DataVal(Object objVal,String col_format){
		if(objVal==null&&BaseUtil.isEmpty(objVal)) return "{}";
		if(BaseUtil.isEmpty(col_format)) col_format = "name";
		if(objVal instanceof CoreBaseInfo){
			JSONObject jsonObj_f7 = new JSONObject();
			String[] _cols = col_format.split(",");
			CoreBaseInfo cbInfo = (CoreBaseInfo) objVal;
			for(String colItem :_cols){
				jsonObj_f7.put(colItem, cbInfo.get(colItem));
			}
			return jsonObj_f7;
		}else if(objVal instanceof List){//f7的对象集合处理
			List items = new ArrayList();
			List objSet = (List) objVal;
			for(Object itemObj: objSet) {
				items.add(getEditF7DataVal(itemObj,col_format));
			}
			return items;
		}
		return "{}";
	}
	
	private Object getEditDataVal(ColumnModel col,Object objVal) throws HibernateException, QueryException{
		DataTypeEnum dte = col.getDataType();
		if(objVal==null){
			objVal = "";
			if(DataTypeEnum.F7.equals(dte)){
				objVal= "{}";
			}else if(DataTypeEnum.ENTRY.equals(dte)
					||DataTypeEnum.MUTILF7.equals(dte)
					||DataTypeEnum.MUTILENUM.equals(dte)){
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
			}else if(DataTypeEnum.MUTILENUM.equals(dte)&&col.getClaz()!=null
					&&!BaseUtil.isEmpty(objVal)&&objVal instanceof String){//多选的枚举
				String[] ems = objVal.toString().split(",");
				String enClazName = col.getClaz().getName();
				List vals = new ArrayList();
				for(String em:ems){
					Object eum = EnumUtil.getEnum(enClazName, em);
					if(eum!=null&&eum instanceof MyEnum){
						vals.add(((MyEnum)eum).getValue());
					}
				}
				objVal = vals;
			}else if(DataTypeEnum.F7.equals(dte)&&objVal instanceof CoreBaseInfo){
				objVal = getEditF7DataVal(objVal,col.getFormat());
			}else if(DataTypeEnum.MUTILF7.equals(dte)&&col.getClaz()!=null
					&&!BaseUtil.isEmpty(objVal)&&objVal instanceof String){//处理id关联对象转变成界面上的f7对象
				String[] f7ids = objVal.toString().split(",");
				Criteria query = getService().initQueryCriteria(col.getClaz());
				query.add(Restrictions.in("id", f7ids));
				ProjectionList props = Projections.projectionList();
				for(String colItem :col.getFormat().split(",")){
					props.add(createBaseField(colItem));
				}
				query.setProjection(props);
				query.setResultTransformer(new MyResultTransFormer(col.getClaz()));
				objVal = query.list();
			}else if(DataTypeEnum.ENTRY.equals(dte)&&objVal instanceof Set){
				Set entrys = (Set) objVal;
				List _entry = new ArrayList();
				List<ColumnModel> cols = col.getCols();
				if(entrys!=null&&entrys.size()>0&&cols!=null&&cols.size()>0){
					for(Object entryObj: entrys) {
						if(entryObj!=null&&entryObj instanceof CoreBaseEntryInfo){
							CoreBaseEntryInfo eInfo = (CoreBaseEntryInfo) entryObj;
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
	
	private void packageEditData2Json() throws HibernateException, QueryException{
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
	
	private Object getBaseColumnData(Map editData_map,ColumnModel col){
		String name = col.getName();
		Object val =  editData_map.get(name);
		if(val!=null) {
			DataTypeEnum dte = col.getDataType();
			if(DataTypeEnum.DATE.equals(dte)||DataTypeEnum.DATETIME.equals(dte)){
				if(BaseUtil.isEmpty(val)){
					val = null;
				}else{
					val = DateUtil.parseDate(val.toString(), col.getFormat());
				}
			}else if(DataTypeEnum.BOOLEAN.equals(dte)){
				if(BaseUtil.isEmpty(val)) {
					val = Boolean.FALSE;
				}else{
					val = ("1".equals(val.toString())||"true".equals(val.toString().toLowerCase()))?Boolean.TRUE:Boolean.FALSE;
				}
			}else if(DataTypeEnum.F7.equals(dte)){
				if(BaseUtil.isEmpty(val)) {
					val = null;
				}else{
					val = getService().getEntity(col.getClaz(), WebUtil.UUID_ReplaceID(val.toString()));
				}
				
			}else if(DataTypeEnum.MUTILF7.equals(dte)){//多选f7的处理
				if(BaseUtil.isEmpty(val)) {
					val = "";
				}else{//id集合支付串
					val = WebUtil.UUID_ReplaceID(val.toString());
				}
			}else if(DataTypeEnum.ENUM.equals(dte)){
				if(BaseUtil.isEmpty(val)) {
					val = null;
				}else{
					val = EnumUtil.getEnum(col.getClaz().getName(), val.toString());
				}
			}else if(DataTypeEnum.MUTILENUM.equals(dte)){//多选枚举
				if(BaseUtil.isEmpty(val)) {
					val = "";
				}
			}else if(DataTypeEnum.NUMBER.equals(dte)){
				if(BaseUtil.isEmpty(val)){
					val = null;
				}else{
					String num_str = val.toString().trim();
					BigDecimal bd =  new BigDecimal(num_str);
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
				}
			}else if(DataTypeEnum.INT.equals(dte)){
				if(BaseUtil.isEmpty(val)){
					val = null;
				}else{
					val = Integer.valueOf(val.toString());
				}
			}else if(DataTypeEnum.PK.equals(dte)){
				if(BaseUtil.isEmpty(val)) {
					val = null;
				}else{
					val = WebUtil.UUID_ReplaceID(val.toString());
				}
			}
		}
		return val;	
	}
	
	private void packageJson2EditData() throws ClassNotFoundException, InstantiationException, IllegalAccessException, DeleteException{
		String editData_str = request.getParameter("editData");
		if(BaseUtil.isEmpty(editData_str)) return;
		Map editData_map = JSONObject.parseObject(editData_str, new HashMap().getClass());
		CoreInfo cbInfo = null;
		String billId = (String)editData_map.get("id");
		if(BaseUtil.isEmpty(billId)){
			cbInfo = getEntityInfo();
		}else{
			cbInfo = (CoreInfo) getService().getEntity(billId);
		}
		if(cbInfo!=null){
			List<ColumnModel> cols = getDataBinding();
			for(ColumnModel col:cols){
				DataTypeEnum dte = col.getDataType();
				String name = col.getName();
				if(!BaseUtil.isEmpty(name)&&editData_map.containsKey(name)){
					if(DataTypeEnum.ENTRY.equals(dte)&&col.getCols().size()>0&&col.getClaz()!=null){
						Map<String,CoreBaseEntryInfo> entrysMap = new HashMap<String,CoreBaseEntryInfo>();
						Object objEntrySet = cbInfo.get(name);
						if(objEntrySet!=null&&objEntrySet instanceof Set){
							Set entrySet = (Set) objEntrySet;
							Iterator it = entrySet.iterator();
							while(it.hasNext()){
								Object entryObj = it.next();
								if(entryObj!=null&&entryObj instanceof CoreBaseEntryInfo){
									CoreBaseEntryInfo eInfo = (CoreBaseEntryInfo) entryObj;
									entrysMap.put(eInfo.getId(), eInfo);
								}
							}
						}
						Set entrySet = new HashSet();
						Object entrys_str = editData_map.get(name);
						if(!BaseUtil.isEmpty(entrys_str)){
							List entry_List = JSONObject.parseObject(entrys_str.toString(), new ArrayList().getClass());
							if(entry_List!=null&&entry_List.size()>0){
								List<ColumnModel> entryCols = col.getCols();
								Class entryClaz = col.getClaz();
								for(int i=0;i<entry_List.size();i++){
									Object entryObj = entry_List.get(i);
									if(!BaseUtil.isEmpty(entryObj)){
										Map entryObj_map = JSONObject.parseObject(entryObj.toString(), new HashMap().getClass());
										CoreBaseEntryInfo entryInfo = null;
										String entryId = (String)entryObj_map.get("id");
										if(!BaseUtil.isEmpty(entryId)){
											if(entrysMap.containsKey(entryId)){
												entryInfo = entrysMap.get(entryId);
												entrysMap.remove(entryId);
											}else{
												entryInfo = (CoreBaseEntryInfo)getService().getEntity(entryClaz, entryId);
											}
										}else{
											Object enObj = Class.forName(entryClaz.getName()).newInstance();
											if(enObj!=null&&enObj instanceof CoreBaseEntryInfo){
												entryInfo = (CoreBaseEntryInfo) enObj;
											}
										}
										if(entryInfo!=null){
											for(ColumnModel entry_col:entryCols){
												String entryColName = entry_col.getName();
												if(BaseUtil.isEmpty(entryColName)) continue;
												entryInfo.put(entryColName, getBaseColumnData(entryObj_map,entry_col));
											}
											entryInfo.setSeq(i+1);
											entryInfo.setParent(cbInfo);
											entrySet.add(entryInfo);
										}
									}
								}
							}
						}
						cbInfo.put(name, entrySet);
						if(entrysMap!=null&&entrysMap.size()>0){
							for (CoreBaseEntryInfo eInfo : entrysMap.values()) {  
//								eInfo.setParent(null);
								getService().deleteEntity(eInfo);
							}
						}
						
					}else{
						cbInfo.put(name, getBaseColumnData(editData_map,col));
					}
				}
			}
		}
		setEditData(cbInfo);
	}
	
	protected void setEditData(Object editData){
		this.data = editData;
	}
	
	protected Object getEditData(){
		return this.data;
	}
	
	@PermissionItemAnn(name="保存",number="save")
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
				cbbInfo.setCreateUser(getCurUser());
				cbbInfo.setBizDate(new Date());
			}
		}
	}
	@PermissionItemAnn(name="新增",number="addnew")
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
	
	private void loadData(String billId) throws QueryException{
		if(!BaseUtil.isEmpty(billId)){
			Map editMap = new HashMap();
			Map entryMap = new HashMap();
			Criteria billCrteria = getService().initQueryCriteria();
			ProjectionList billProjectList = Projections.projectionList();
			List<ColumnModel> cols = getDataBinding();
			List<String> entryCols  = new ArrayList<String>();
			Map<String,Object> F7Col = new HashMap<String, Object>();
			Map<String,Object> mutilF7Col = new HashMap<String, Object>();
			Map<String,Object> enumCols = new HashMap<String,Object>();
			for(ColumnModel cm:cols){
				if(!DataTypeEnum.ENTRY.equals(cm.getDataType())){
					packageEditBillCriteria(billCrteria,billProjectList,cm);
					if(DataTypeEnum.F7.equals(cm.getDataType())){
						F7Col.put(cm.getName(), cm);
					}else if(DataTypeEnum.MUTILF7.equals(cm.getDataType())){
						mutilF7Col.put(cm.getName(), cm);
					}else if(DataTypeEnum.MUTILENUM.equals(cm.getDataType())){
						enumCols.put(cm.getName(), cm);
					}
				}else{
					Class entryClaz = cm.getClaz();
					List<ColumnModel> entry_cols = cm.getCols();
					if(entryClaz!=null){
						Criteria entryCrteria = getService().initQueryCriteria(entryClaz);
						entryCrteria.createAlias("parent", "parent", JoinType.INNER_JOIN);
						ProjectionList entryProjectList = Projections.projectionList();
						List<ColumnModel> entryF7Col =  new ArrayList<ColumnModel>();
						List<ColumnModel> entryMutilF7Col =  new ArrayList<ColumnModel>();
						List<ColumnModel> entryEnumCol =  new ArrayList<ColumnModel>();
						for(ColumnModel entry_cm:entry_cols){
							if(!DataTypeEnum.ENTRY.equals(entry_cm.getDataType())){
								packageEditBillCriteria(entryCrteria,entryProjectList,entry_cm);
								if(DataTypeEnum.F7.equals(entry_cm.getDataType())){
									entryF7Col.add(entry_cm);
								}else if(DataTypeEnum.MUTILF7.equals(entry_cm.getDataType())){
									entryMutilF7Col.add(entry_cm);
								}else if(DataTypeEnum.ENUM.equals(entry_cm.getDataType())
										||DataTypeEnum.MUTILENUM.equals(entry_cm.getDataType())){//枚举多选
									entryEnumCol.add(entry_cm);
								}
							}
						}
						entryProjectList.add(createBaseField("seq","seq"));
						entryCrteria.setProjection(entryProjectList);
						entryCrteria.add(Restrictions.eq("parent.id", billId));
						entryCrteria.addOrder(Order.asc("seq"));
						entryCrteria.setResultTransformer(new MyResultTransFormer(entryClaz));
						entryCols.add(cm.getName());
						if(entryF7Col.size()>0){
							F7Col.put(cm.getName(), entryF7Col);
						}
						if(entryMutilF7Col.size()>0){
							mutilF7Col.put(cm.getName(), entryMutilF7Col);
						}
						if(entryEnumCol.size()>0){
							enumCols.put(cm.getName(),entryEnumCol);
						}
						entryMap.put(cm.getName(), entryCrteria);
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
					Criteria entryCrteria = (Criteria) entryMap.get(entryKey);
					if(entryCrteria!=null)
						editMap.put(entryKey, entryCrteria.list());
				}
				//
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
			 // 处理多选f7的数据格式
			 for (Map.Entry<String,Object> entry : mutilF7Col.entrySet()) {
				 String colName = entry.getKey();
				 Object colObj = entry.getValue();
				 if(colObj!=null){
					 if(colObj instanceof ColumnModel){
						 ColumnModel cm = (ColumnModel) colObj;
						 packageMutilF7DataMap(cm,editMap);
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
											packageMutilF7DataMap(entryF7col,entryDataMap);
										}
									}
								}
							}
						}
						 
					 }
				 }
			 }
			 //处理枚举类型
			for (Map.Entry<String,Object> entry : enumCols.entrySet()) {
				String colName = entry.getKey();
				Object colObj = entry.getValue();
				if(colObj!=null){
					if(colObj instanceof ColumnModel){
						 ColumnModel cm = (ColumnModel) colObj;
						 packageEnumsDataMap(false,cm,editMap);
					}else if(colObj instanceof List){//处理分录中的枚举类型
						List entryEnumList = (List) colObj;
						if(entryEnumList.size()>0){
							Object entrysObj = editMap.get(colName);
							if(entrysObj!=null&&entrysObj instanceof List) {
								List entryDatas = (List) entrysObj;
								if (entryDatas.size() > 0) {
									for(int i=0;i<entryDatas.size();i++){
										Object entryDataMapObj = entryDatas.get(i);
										if(entryDataMapObj!=null&&entryDataMapObj instanceof Map){
											Map entryDataMap = (Map) entryDataMapObj;
											for(int j=0;j<entryEnumList.size();j++){
												ColumnModel entryEnumModel = (ColumnModel) entryEnumList.get(j);
												packageEnumsDataMap(true,entryEnumModel,entryDataMap);
											}
										}
									}
								}
							}
						}
					}
				}
			}
			System.out.println("$$$$$ editMap : "+JSONObject.toJSONString(editMap));
			setEditData(editMap);
		}
	}
	
	private void packageMutilF7DataMap(ColumnModel cm,Map dataMap) throws HibernateException, QueryException{
		if(DataTypeEnum.MUTILF7.equals(cm.getDataType())){
			String key = cm.getName();
			Object objVal = dataMap.get(key);
			List datas = new ArrayList();
			if(objVal!=null){
				String[] f7ids = objVal.toString().split(",");
				Criteria query = getService().initQueryCriteria(cm.getClaz());
				query.add(Restrictions.in("id", f7ids));
				ProjectionList props = Projections.projectionList();
				for(String colItem :cm.getFormat().split(",")){
					props.add(createBaseField(colItem));
				}
				query.setProjection(props);
				query.setResultTransformer(new MyResultTransFormer(cm.getClaz()));
				List queryDatas = query.list();
				if(queryDatas!=null&&queryDatas.size()>0){
					datas = queryDatas;
					log.info("$$$ MUTILF7 "+key +"'s size = "+queryDatas.size());
				}
			}
			dataMap.put(key, datas);
		}
	}
	
	//处理枚举对象包括多选枚举 到界面上
	//注意分录中的枚举和表单上的枚举 封装不一样的
	private void packageEnumsDataMap(boolean isEntry,ColumnModel colM,Map dataMap){
		String enClazName = colM.getClaz().getName();
		String colKey = colM.getName();
		if(dataMap.containsKey(colKey)){
			Object objVal = dataMap.get(colKey);
			boolean isMutil = DataTypeEnum.MUTILENUM.equals(colM.getDataType());
			if(!BaseUtil.isEmpty(objVal)){
				if(isMutil){
					List emList = new ArrayList();
					String[] ems = objVal.toString().split(",");
					for(String em:ems){
						KeyValueModel kvm = EnumUtil.getEnumItemKv(enClazName,em);
						if(isEntry){
							emList.add(kvm);
						}else{
							emList.add(kvm.getKey());
						}
					}
					dataMap.put(colKey,emList);
				}else{
					KeyValueModel kvm = EnumUtil.getEnumItemKv(enClazName,objVal.toString());
					if(isEntry){
						dataMap.put(colKey,kvm);
					}else{
						dataMap.put(colKey,kvm.getKey());
					}
				}
			}else{
				dataMap.put(colKey, isMutil?"[]":"");
			}
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
	
	public WebDataModel loadData(BaseMethodEnum bme) {
		try{
			if(bme==null) bme = BaseMethodEnum.VIEW;
			if(beforeOperate(bme)){
				loadData(getReuestBillId());
				afterOperate(bme);
				setBaseMethod(bme);
			}
		}catch (Exception e) {
			e.printStackTrace();
			setExceptionMesg(e.getMessage());
		}
		return ajaxModel();
	}
	
	@PermissionItemAnn(name="查看",number="view",type=PermissionTypeEnum.FUNCTION)
	@ResponseBody
	@RequestMapping(value="/view",method=RequestMethod.POST)
	public WebDataModel view() {
		return loadData(BaseMethodEnum.VIEW);
	}
	
	@PermissionItemAnn(name="编辑",number="edit",type=PermissionTypeEnum.FUNCTION)
	@ResponseBody
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	public WebDataModel edit() {
		return loadData(BaseMethodEnum.EDIT);
	}
	
	@PermissionItemAnn(name="删除",number="remove")
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
	
	
	
}
