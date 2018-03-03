package com.myapp.controller.ec.basedata.qualitytemplate;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseF7QueryController;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.entity.ec.basedata.ProBaseWbsInfo;
import com.myapp.service.ec.basedata.QualityTemplateService;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("ec/basedata/qualitytemplatef7")
public class QualityTemplateF7QueryController extends BaseF7QueryController {
	
	@Resource
	public QualityTemplateService qualityTemplateService;
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
