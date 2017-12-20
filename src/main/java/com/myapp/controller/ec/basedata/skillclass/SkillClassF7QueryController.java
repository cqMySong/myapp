package com.myapp.controller.ec.basedata.skillclass;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseF7QueryController;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.EnumUtil;
import com.myapp.core.util.WebUtil;
import com.myapp.enums.ec.SkillType;
import com.myapp.enums.ec.WorkSchemeGroup;
import com.myapp.service.ec.basedata.SchemeTypeService;
import com.myapp.service.ec.basedata.SkillClassService;

@Controller
@RequestMapping("ec/basedata/skillclassf7")
public class SkillClassF7QueryController extends BaseF7QueryController {
	
	@Resource
	public SkillClassService skillClassService;
	public AbstractBaseService getService() {
		return skillClassService;
	}
	
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		ColumnModel col = new ColumnModel("number");
		col.setAlias_zh("编码");
		cols.add(col);
		col = new ColumnModel("name");
		col.setAlias_zh("名称");
		cols.add(col);
		
		col = new ColumnModel("skillType",DataTypeEnum.ENUM,SkillType.class);
		col.setAlias_zh("类别");
		cols.add(col);
		
		col = new ColumnModel("enabled",DataTypeEnum.BOOLEAN);
		col.setAlias_zh("启用");
		cols.add(col);
		return cols;
	}
	
	public void executeQueryParams(Criteria query) {
		super.executeQueryParams(query);
		String search = request.getParameter("search");
		if(!BaseUtil.isEmpty(search)){ //{"uiCtx":{"type":"SM"}}
			Map searchMap = JSONObject.parseObject(search, new HashMap().getClass());
			Object uiCtxObj = searchMap.get("uiCtx");
			if(uiCtxObj!=null){
				Map uiCtxMap = JSONObject.parseObject(uiCtxObj.toString(), new HashMap().getClass());
				if(uiCtxMap!=null){
					Object type = uiCtxMap.get("type");
					if(!BaseUtil.isEmpty(type)){
						SkillType st = EnumUtil.getEnum(SkillType.class.getName(), type);
						if(st!=null)
							query.add(Restrictions.eq("skillType",st));
					}
				}
			}
		}
		query.add(Restrictions.eq("enabled", Boolean.TRUE));
	}
	
	public Order getOrder() {
		return Order.asc("number");
	}
	
	public String getUIWinTitle() {
		String title = "技术分类";
		String type = WebUtil.UUID_ReplaceID(request.getParameter("type"));
		if(!BaseUtil.isEmpty(type)){
			SkillType st = EnumUtil.getEnum(SkillType.class.getName(), type);
			if(st!=null){
				title = st.getName();
			}
		}
		return title;
	}
}
