package com.myapp.controller.ec.basedata.skillitem;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseF7QueryController;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.EnumUtil;
import com.myapp.core.util.WebUtil;
import com.myapp.entity.ec.basedata.SkillClassInfo;
import com.myapp.enums.ec.SkillType;
import com.myapp.service.ec.basedata.SkillClassService;
import com.myapp.service.ec.basedata.SkillItemService;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("ec/basedata/skillitemf7")
public class SkillItemF7QueryController extends BaseF7QueryController {
	
	@Resource
	public SkillItemService skillItemService;
	@Override
	public AbstractBaseService getService() {
		return skillItemService;
	}
	@Override
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		ColumnModel col = new ColumnModel("number");
		col.setAlias_zh("编码");
		cols.add(col);
		col = new ColumnModel("name");
		col.setAlias_zh("名称");
		cols.add(col);

		col = new ColumnModel("skillClass",DataTypeEnum.F7,SkillClassInfo.class);
		col.setFormat("id,name");
		col.setAlias_zh("id,技术分类");
		cols.add(col);

		col = new ColumnModel("enabled",DataTypeEnum.BOOLEAN);
		col.setAlias_zh("启用");
		cols.add(col);
		return cols;
	}
	@Override
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
	@Override
	public Order getOrder() {
		return Order.asc("number");
	}
	@Override
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
