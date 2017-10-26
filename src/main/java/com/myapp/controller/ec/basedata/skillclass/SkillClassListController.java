package com.myapp.controller.ec.basedata.skillclass;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.myapp.core.annotation.AuthorAnn;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseDataListController;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.model.WebDataModel;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.EnumUtil;
import com.myapp.enums.ec.SkillType;
import com.myapp.service.ec.basedata.SkillClassService;

@PermissionAnn(name="系统管理.现场管理.基础数据.技术分类",number="app.ec.basedata.skillclass")
@Controller
@RequestMapping("ec/basedata/skillclasss")
public class SkillClassListController extends BaseDataListController {
	
	@Resource
	public SkillClassService skillClassService;

	public AbstractBaseService getService() {
		return skillClassService;
	}
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("skillType",DataTypeEnum.ENUM,SkillType.class));
		return cols;
	}
	
	public String getRootName(SkillType skt){
		if(skt!=null) return skt.getName()+"交底分类";
		return "分类";
	}
	@AuthorAnn(doPermission=false)
	@RequestMapping(value="/treeData")
	@ResponseBody
	public WebDataModel treeData() {
		try{
			Map params = new HashMap();
			String skType = request.getParameter("skillType");
			SkillType skt = null;
			if(!BaseUtil.isEmpty(skType)){
				skt = EnumUtil.getEnum(SkillType.class.getName(), skType);
			}
			Map root = new HashMap();
			root.put("id", "");
			root.put("name", getRootName(skt));
			root.put("open", true);
			root.put("children", skillClassService.getSkillItems(skt));
			data = root;
		}catch(Exception e){
			setErrorMesg(e.getMessage());
		}
		return ajaxModel();
	}
	
	public String getEditUrl() {
		return "ec/basedata/skillclass/skillClassEdit";
	}

	public String getListUrl() {
		return "ec/basedata/skillclass/skillClassList";
	}

}
