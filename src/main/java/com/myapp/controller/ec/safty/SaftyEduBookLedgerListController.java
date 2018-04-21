package com.myapp.controller.ec.safty;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.AuthorAnn;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.annotation.PermissionItemAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseListController;
import com.myapp.core.controller.BasePageListController;
import com.myapp.core.enums.BaseMethodEnum;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.enums.PermissionTypeEnum;
import com.myapp.core.enums.Sex;
import com.myapp.core.exception.db.QueryException;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.model.PageModel;
import com.myapp.core.model.WebDataModel;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.DateUtil;
import com.myapp.core.util.WebUtil;
import com.myapp.service.ec.safty.SaftyEduBookService;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@PermissionAnn(name="系统管理.现场管理.安全管理.劳务人员登记备案台帐",number="app.ec.safe.saftyedubookledger")
@Controller
@RequestMapping("ec/safty/saftyedubookledger")
public class SaftyEduBookLedgerListController extends BasePageListController {
	
	@Resource
	private SaftyEduBookService saftyEduBookService;
	@Override
	public AbstractBaseService getService() {
		return saftyEduBookService;
	}
	@Override
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("sex",DataTypeEnum.ENUM,Sex.class));
		cols.add(new ColumnModel("age"));
		cols.add(new ColumnModel("workType"));
		cols.add(new ColumnModel("homeAddress"));
		cols.add(new ColumnModel("idCardNo"));
		cols.add(new ColumnModel("companyDate",DataTypeEnum.DATE));
		
		return cols;
	}

	public String getListUrl() {
		return "ec/safty/saftyedubook/saftyEduBookLedgerList";
	}

	@PermissionItemAnn(name="查看",number="onload",type= PermissionTypeEnum.PAGE)
	@RequestMapping("/list")
	public ModelAndView list(){
		Map params = new HashMap();
		toListUIParams(params);
		return toPage(getListUrl(), params);
	}

	public void toListUIParams(Map params){
		packageUIParams(params);
	}

	@AuthorAnn(doLongin=false,doPermission=false)
	@RequestMapping(value="/query")
	@ResponseBody
	public WebDataModel toList() {
		try {
			init();
			Criteria query = initQueryCriteria();
			executeQueryParams(query);
			List<Order> orders = getOrders();
			if(query!=null&&orders!=null&&orders.size()>0){
				for(Order order:orders){
					query.addOrder(order);
				}
			}
			afterQuery(getService().toPageQuery(query, getProjectionList(), getCurPage(), getPageSize()));
		} catch (Exception e) {
			e.printStackTrace();
			setExceptionMesg(e.getMessage());
		}
		return ajaxModel();
	}

	public void afterQuery(PageModel pm) throws QueryException {
		List datas = pm.getDatas();
		if(datas!=null&&datas.size()>0){
			packageDatas(datas);
			pm.setDatas(datas);
		}
		this.data = pm;
	}
	@Override
	public void executeQueryParams(Criteria query) {
		super.executeQueryParams(query);
		String serach = request.getParameter("search");
		if(!BaseUtil.isEmpty(serach)){
			Map searchMap = JSONObject.parseObject(serach, new HashMap().getClass());
			Object obj_field = searchMap.get("key");
			Object obj_val = searchMap.get("value");
			Object obj_type = searchMap.get("type");
			if(obj_field!=null){
				if(obj_val==null){
					query.add(Restrictions.isNull(obj_field.toString()));
				}else{
					obj_val = obj_val.toString();
					DataTypeEnum dte = WebUtil.getWebServerDataType(obj_type.toString());
					if(DataTypeEnum.BOOLEAN.equals(dte)){
						obj_val = obj_val.toString().toLowerCase().equals("true")||obj_val.equals("1")?1:0;
					}else if(DataTypeEnum.DATE.equals(dte)){
						obj_val = DateUtil.parseDate(obj_val.toString());
					}else if(DataTypeEnum.DATETIME.equals(dte)){
						obj_val = DateUtil.parseDate(obj_val.toString(),DateUtil.DATEFORMT_YMDHMS);
					}else if(DataTypeEnum.NUMBER.equals(dte)){
						obj_val = new BigDecimal(obj_val.toString());
					}
					query.add(Restrictions.eq(obj_field.toString(),obj_val));
				}
			}
		}
	}

	//统一处理 uiCtx的参数类型传递
	public void packageUIParams(Map params){
		String ciCtx = request.getParameter("uiCtx");
		if(!BaseUtil.isEmpty(ciCtx)){
			ciCtx = ciCtx.replaceAll("\'", "\"");
			Map uiCtx = JSONObject.parseObject(ciCtx, new HashMap().getClass());
			params.put("uiCtx", ciCtx);
		}
		String title = getTitle();
		if(!BaseUtil.isEmpty(title)){
			params.put("title", title);
		}
	}
	public String getTitle(){
		return "";
	}

}
