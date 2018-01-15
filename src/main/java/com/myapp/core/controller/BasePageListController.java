package com.myapp.core.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;

import com.myapp.core.annotation.AuthorAnn;
import com.myapp.core.base.dao.MyResultTransFormer;
import com.myapp.core.base.setting.SystemConstant;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.enums.FileType;
import com.myapp.core.exception.db.QueryException;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.model.KeyValueModel;
import com.myapp.core.model.PageModel;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.DateUtil;
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
	public boolean toDoData = false;
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
	public List<ColumnModel> getPackageDataCol(){
		List<ColumnModel> cols = getDataBinding();
		List<ColumnModel> toDoCols = new ArrayList<ColumnModel>(); 
		for(ColumnModel cm:cols){
			DataTypeEnum dte = cm.getDataType();
			if(dte.equals(DataTypeEnum.MUTILF7)&&cm.getClaz()!=null){
				toDoCols.add(cm);
			}else if((DataTypeEnum.ENUM.equals(dte)||DataTypeEnum.MUTILENUM.equals(dte))
					&&cm.getClaz()!=null){
				toDoCols.add(cm);
			}
		}
		return toDoCols;
	}
	public void packageDatas(List datas) throws QueryException{
		if(datas==null||datas.size()<=0) return;
		List<ColumnModel> cms = getPackageDataCol();
		if(cms!=null&&cms.size()>0){
			packageListDataColumns(datas, cms);
		}
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
					List list = query.list();
					if(toDoData){
						String keyDatas = "";
						if(list!=null&&list.size()>0){
							for(int i=0;i<list.size();i++){
								Object objItem = list.get(i);
								if(objItem!=null&&objItem instanceof Map){
									Map objMap = (Map) objItem;
									Object objVal = objMap.get("name");
									if(objVal==null) objVal = objMap.get("number");
									if(objVal!=null){
										if(!BaseUtil.isEmpty(keyDatas)) keyDatas +=",";
										keyDatas +=objVal.toString();
									}
								}
							}
						}
						row.put(key,keyDatas);
					}else{
						row.put(key,list);
					}
				}else if((DataTypeEnum.ENUM.equals(dte)||DataTypeEnum.MUTILENUM.equals(dte))
						&&cm.getClaz()!=null){
					String enClazName = cm.getClaz().getName();
					boolean isMutil = DataTypeEnum.MUTILENUM.equals(dte);
					if(isMutil){
						String[] ems = objval.toString().split(",");
						String name = "";
						List emList = new ArrayList();
						for(String em:ems){
							KeyValueModel kvm = EnumUtil.getEnumItemKv(enClazName,em);
							emList.add(kvm);
							if(toDoData&&kvm!=null){
								if(!BaseUtil.isEmpty(name)) name +=",";
								name+=kvm.getVal();
							}
						}
						if(toDoData){
							row.put(key,name);
						}else{
							row.put(key,emList);
						}
					}else{
						KeyValueModel kvm = EnumUtil.getEnumItemKv(enClazName,objval.toString());
						if(toDoData){
							row.put(key, kvm.getVal());
						}else{
							row.put(key,kvm);
						}
					}
				}
			}
		}
	}
	
	public void executeQueryParams(Criteria query) {
		
	}
	
	public List<Order> getOrders(){
		return new ArrayList<Order>();
	}
	
	public String getFileName(){
    	return "数据表导出";
    }
	
	public String getHeadTitle() {
		return "数据信息";
	}
	
	public String getSecondTitle(){
		return getCurUser().getName()+" "+DateUtil.formatDate(new Date())+" 制表";
	}
	
	@AuthorAnn(doLongin=true,doPermission=false)
	@RequestMapping(value="/export",produces= MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void export(HttpServletRequest request,HttpServletResponse response){
    	FileType fileType = getExportType();
    	if(fileType!=null&&FileType.EXCEL.equals(fileType)){
    		try{
    			ExportParams params = getExportParams();
        		List<ExcelExportEntity> headers = getExportHeader();
        		if(params!=null&&headers!=null&&headers.size()>0){
        			for(int i=0;i<headers.size();i++ ){
        				headers.get(i).setOrderNum(i+1);
        			}
        			List<Map<String, Object>> datas = getExportData();
        			if(datas==null){
        				init();
        				toDoData = true;
        				Criteria query = initQueryCriteria();
        				executeQueryParams(query);
        				List<Order> orders = getOrders();
        				if(query!=null&&orders!=null&&orders.size()>0){
        					for(Order order:orders){
        						query.addOrder(order);
        					}
        				}
        				String pgsize = request.getParameter("pageSize");
            			int pages = -1;
            			if(!BaseUtil.isEmpty(pgsize)){
            				pages = Integer.valueOf(pgsize);
            			}
            			PageModel pm = getService().toPageQuery(query, getProjectionList(), getCurPage(), pages);
            			datas = pm.getDatas();
            			toDoData = true;
            			packageDatas(datas);
            			toDoData = false;
            			
        			}
        			if(datas==null) datas = new ArrayList<Map<String, Object>>();
        			if(datas!=null){
        				Workbook workbook = ExcelExportUtil.exportExcel(params, headers, datas); 
        				String fileName = getFileName();
        				if(BaseUtil.isEmpty(fileName)) fileName = "数据导出";
        				fileName =  new String(fileName.getBytes("gb2312"),"iso8859-1")+".xls";
        				response.setHeader("content-Type", "application/vnd.ms-excel");
        				response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        				response.setCharacterEncoding("UTF-8");
        				workbook.write(response.getOutputStream());
        			}
        		}
    		}catch (Exception e) {
    			e.printStackTrace();
    			setExceptionMesg(e.getMessage());
    		}
    	}
    }
}
