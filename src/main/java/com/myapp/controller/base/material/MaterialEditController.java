package com.myapp.controller.base.material;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseDataEditController;
import com.myapp.core.entity.MeasureUnitInfo;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.entity.MaterialInfo;
import com.myapp.core.enums.MaterialType;
import com.myapp.core.service.MaterialService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author :ly
 * @date:2017-10-25
 */
@PermissionAnn(name="系统管理.基础资料.物料信息",number="app.basedata.material")
@Controller
@RequestMapping("base/material")
public class MaterialEditController extends BaseDataEditController {
	
	@Resource
	public MaterialService materialService;
	@Override
	public AbstractBaseService getService() {
		return materialService;
	}
	@Override
	public Object createNewData() {
		MaterialInfo materialInfo = new MaterialInfo();
		materialInfo.setMaterialType(MaterialType.STRUCTURE);
		return materialInfo;
	}
	@Override
	public CoreBaseInfo getEntityInfo() {
		return new MaterialInfo();
	}
	@Override
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("pinyin"));
		cols.add(new ColumnModel("specification"));
		cols.add(new ColumnModel("unit",DataTypeEnum.F7, MeasureUnitInfo.class));
		cols.add(new ColumnModel("materialType",DataTypeEnum.ENUM,MaterialType.class));
		return cols;
	}


}
