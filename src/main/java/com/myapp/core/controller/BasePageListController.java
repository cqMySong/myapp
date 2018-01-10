package com.myapp.core.controller;

import java.net.URLEncoder;
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
import com.myapp.core.annotation.PermissionItemAnn;
import com.myapp.core.base.dao.MyResultTransFormer;
import com.myapp.core.base.setting.SystemConstant;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.enums.FileType;
import com.myapp.core.exception.db.QueryException;
import com.myapp.core.model.ColumnModel;
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
				}else if((DataTypeEnum.ENUM.equals(dte)||DataTypeEnum.MUTILENUM.equals(dte))
						&&cm.getClaz()!=null){
					String enClazName = cm.getClaz().getName();
					boolean isMutil = DataTypeEnum.MUTILENUM.equals(dte);
					if(isMutil){
						String[] ems = objval.toString().split(",");
						List emList = new ArrayList();
						for(String em:ems){
							emList.add(EnumUtil.getEnumItemKv(enClazName,em));
						}
						row.put(key,emList);
					}else{
						row.put(key,EnumUtil.getEnumItemKv(enClazName,objval.toString()));
					}
				}
			}
		}
	}
	public String[] getBooleanReplace(){
		return new String[]{"是_true","否_false"};
	}
	public void executeQueryParams(Criteria query) {
		
	}
	
	public List<Order> getOrders(){
		return new ArrayList<Order>();
	}
	public String getHeadTitle(){
		return "信息表";
	}
	public String getSecondTitle(){
		return getCurUser().getName()+" "+DateUtil.formatDate(new Date())+" 制表";
	}
	public String getSheetName(){
		return getHeadTitle();
	}
	
	public String getFileName() {
		return getHeadTitle();
	}
	
	public ExportParams getExportParams() {
		return new ExportParams(getHeadTitle(),getSecondTitle(), getSheetName());
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
        			List<Map<String, Object>> datas = getExportData();
        			if(datas==null){
        				init();
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
