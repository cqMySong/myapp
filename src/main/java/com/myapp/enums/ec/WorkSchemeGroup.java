package com.myapp.enums.ec;

import java.util.HashMap;
import java.util.Map;

import com.myapp.core.base.enums.MyEnum;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年10月29日 
 * @system: 施工方案分类类别
 * 技术负责人：施工方案编制
 *-----------MySong---------------
 */
public enum WorkSchemeGroup implements MyEnum<WorkSchemeGroup> {
	/*一、施工组织设计
	二、季节性施工方案
	三、安全文明施工方案
	四、工程质量控制方案
	五、地基与基础施工方案
	六、主体结构
	七、建筑装饰装修
	八、屋面
	九、建筑给水排水及供暖
	十、通风与空调
	十一、建筑电气
	十二、智能建筑
	十三、建筑节能
	十四、电梯
	十五、园林*/

	SGZZSJ("SGZZSJ","施工组织设计") ,JJXSGFA("JJXSGFA","季节性施工方案"),AQWMSGFA("AQWMSGFA","安全文明施工方案")
	,GCZLKZFA("GCZLKZFA","工程质量控制方案"),DJJCFA("DJJCFA","地基与基础施工方案"),ZTJG("ZTJG","主体结构")
	,JZZSZX("JZZSZX","建筑装饰装修"),WM("WM","屋面"),JSPSGN("JSPSGN","建筑给水排水及供暖")
	,TFKT("TFKT","通风与空调"),ZJDQ("ZJDQ","建筑电气"),ZNJZ("ZNJZ","智能建筑")
	,JZJN("JZJN","建筑节能"),TD("TD","电梯"),YL("YL","园林");

	private String name;
	private String value;
	private static final Map<String, WorkSchemeGroup> map = new HashMap<String, WorkSchemeGroup>();
	static {
		map.put(SGZZSJ.getValue(), SGZZSJ);
        map.put(JJXSGFA.getValue(), JJXSGFA);
        map.put(AQWMSGFA.getValue(), AQWMSGFA);
        map.put(GCZLKZFA.getValue(), GCZLKZFA);
        map.put(DJJCFA.getValue(), DJJCFA);
        map.put(ZTJG.getValue(), ZTJG);
        map.put(JZZSZX.getValue(), JZZSZX);
        map.put(WM.getValue(), WM);
        map.put(JSPSGN.getValue(), JSPSGN);
        map.put(TFKT.getValue(), TFKT);
        map.put(ZJDQ.getValue(), ZJDQ);
        map.put(ZNJZ.getValue(), ZNJZ);
        map.put(JZJN.getValue(), JZJN);
        map.put(TD.getValue(), TD);
        map.put(YL.getValue(), YL);
    }

	WorkSchemeGroup(String value,String name){
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return this.name;
	}

	public String getValue() {
		return this.value;
	}

	public WorkSchemeGroup getEnum(String value) {
		return map.get(value);
	}

}
