package com.myapp.core.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.myapp.core.base.dao.MyResultTransFormer;
import com.myapp.core.base.enums.MyEnum;
import com.myapp.core.base.setting.SystemConstant;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.exception.db.QueryException;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.EnumUtil;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年7月5日 
 * @system:
 *
 *-----------MySong---------------
 */
public abstract class BasePageListController extends CoreBaseController {
	private Integer curPage;
	private Integer pageSize;
	
	public Integer getCurPage() {
		String cpage = request.getParameter("curPage");
		if(!BaseUtil.isEmpty(cpage)){
			curPage = Integer.valueOf(cpage);
		}
		if (curPage == null)
			curPage = SystemConstant.DEF_PAGE_BEG;
		return curPage;
	}

	public void setCurPage(Integer curPage) {
		this.curPage = curPage;
	}

	public Integer getPageSize() {
		String pgsize = request.getParameter("pageSize");
		if(!BaseUtil.isEmpty(pgsize)){
			pageSize = Integer.valueOf(pgsize);
		}
		if (pageSize == null)
			pageSize = SystemConstant.DEF_PAGE_SIZE;
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	public void packageListDataColumns(List datas ,List<ColumnModel> cms) throws QueryException{
		if(datas!=null&&datas.size()>0&&cms!=null&&cms.size()>0){
			for(int i=0;i<datas.size();i++){
				Object dm = datas.get(i);
				if(dm!=null&&dm instanceof Map){
					for(ColumnModel cm:cms){
						packageRowCellData((Map)dm,cm);
					}
				}
			}
		}
	}
	
	public void packageRowCellData(Map row ,ColumnModel cm) throws QueryException{
		if(row!=null&&row.size()>0&&cm!=null){
			DataTypeEnum dte = cm.getDataType();
			String key = cm.getName();
			Object objval = row.get(key);
			if(!BaseUtil.isEmpty(key)&&!BaseUtil.isEmpty(objval)){
				if(DataTypeEnum.MUTILF7.equals(dte)&&cm.getClaz()!=null){
					String[] f7ids = objval.toString().split(",");
					Criteria query = getService().initQueryCriteria(cm.getClaz());
					query.add(Restrictions.in("id", f7ids));
					ProjectionList props = Projections.projectionList();
					String col_format = cm.getFormat();
					for(String colItem :col_format.split(",")){
						props.add(createBaseField(colItem));
					}
					query.setProjection(props);
					query.setResultTransformer(new MyResultTransFormer(cm.getClaz()));
					row.put(key, query.list());
				}else if(DataTypeEnum.ENUM.equals(dte)&&cm.getClaz()!=null){
					Class enClaz = cm.getClaz();
					Object obj = EnumUtil.getEnum(enClaz.getName(),objval.toString());
					if(obj!=null&&obj instanceof MyEnum){
						MyEnum myEnum = (MyEnum) obj;
						Map kevMap = new HashMap();
						kevMap.put("key", myEnum.getValue());
						kevMap.put("val", myEnum.getName());
						row.put(key,kevMap);
					}
				}
			}
		}
	}
}
