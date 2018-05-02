package com.myapp.controller.ec.basedata.qualitytemplate;
import com.alibaba.fastjson.JSONObject;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseF7QueryController;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.util.WebUtil;
import com.myapp.entity.ec.basedata.ProBaseWbsInfo;
import com.myapp.entity.ec.basedata.ProjectWbsInfo;
import com.myapp.service.ec.basedata.ProjectWbsService;
import com.myapp.service.ec.basedata.QualityTemplateService;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("ec/basedata/qualitytemplatef7")
public class QualityTemplateF7QueryController extends BaseF7QueryController {
	
	@Resource
	public QualityTemplateService qualityTemplateService;
	@Resource
	public ProjectWbsService projectWbsService;
	@Override
	public AbstractBaseService getService() {
		return qualityTemplateService;
	}
	@Override
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();

		ColumnModel col = new ColumnModel("branchBaseWbs",DataTypeEnum.F7,ProBaseWbsInfo.class);
		col.setFormat("id,name");
		col.setAlias_zh("id,分部");
		cols.add(col);

		col = new ColumnModel("subentry",DataTypeEnum.F7,ProBaseWbsInfo.class);
		col.setFormat("id,name");
		col.setAlias_zh("id,分项");
		cols.add(col);

		 col = new ColumnModel("number");
		col.setAlias_zh("样板编码");
		cols.add(col);
		col = new ColumnModel("name");
		col.setAlias_zh("样板名称");
		cols.add(col);

		return cols;
	}
	@Override
	public void executeQueryParams(Criteria query) {
		super.executeQueryParams(query);
		query.add(Restrictions.eq("enabled", Boolean.TRUE));
		String search = request.getParameter("search");
		if(!StringUtils.isEmpty(search)) {
			Map searchMapObj = JSONObject.parseObject(search, new HashMap().getClass());
			if(searchMapObj.get("uiCtx")==null){
				return;
			}
			Map searchMap =  JSONObject.parseObject(searchMapObj.get("uiCtx").toString(), new HashMap().getClass());
			Object subentry = searchMap.get("subentry");
			if(subentry!=null) {
				String subentryStr = subentry.toString();
				if(!StringUtils.isEmpty(subentryStr)) {
					subentryStr = WebUtil.UUID_ReplaceID(subentryStr);
					ProjectWbsInfo projectWbsInfo = (ProjectWbsInfo) projectWbsService.getEntity(subentryStr);
					if(projectWbsInfo!=null){
						query.add(Restrictions.eq("subentry.id",projectWbsInfo.getBaseWbs().getId()));
					}
				}
			}
			Object branchBaseWbs = searchMap.get("branchBaseWbs");
			if(subentry!=null) {
				String branchBaseWbsStr = branchBaseWbs.toString();
				if(!StringUtils.isEmpty(branchBaseWbsStr)) {
					branchBaseWbsStr = WebUtil.UUID_ReplaceID(branchBaseWbsStr);
					ProjectWbsInfo projectWbsInfo = (ProjectWbsInfo) projectWbsService.getEntity(branchBaseWbsStr);
					if(projectWbsInfo!=null){
						query.add(Restrictions.eq("branchBaseWbs.id",projectWbsInfo.getBaseWbs().getId()));
					}
				}
			}
		}

	}
	@Override
	public Order getOrder() {
		return Order.asc("number");
	}
	@Override
	public String getUIWinTitle() {
		String title = "质量样板";
		return title;
	}
}
