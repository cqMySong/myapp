package com.myapp.core.enums;

import com.myapp.core.base.enums.MyEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * @path:com.myapp.core.enums
 * @description:会议纪要类型
 * @author :ly
 * @date : 2017-10-18
 */
public enum MeetingSummaryType implements MyEnum<MeetingSummaryType> {
	DRAWING("DRAWING","图纸会审"),
	PRODUCTION_MANAGE("PRODUCTION_MANAGE","生产管理"),
	SAFE_CIVILIZATION("SAFE_CIVILIZATION","安全文明"),
	SAMPLE_ACCEPTANCE("SAMPLE_ACCEPTANCE","样板验收"),
	STAGE_ACCEPTANCE("STAGE_ACCEPTANCE","阶段验收"),
	OTHER("OTHER","其它");

	private String name;
	private String value;

	public static final Map<String, MeetingSummaryType> map = new HashMap<String, MeetingSummaryType>();
	static {
        map.put(DRAWING.getValue(), DRAWING);
        map.put(PRODUCTION_MANAGE.getValue(), PRODUCTION_MANAGE);
        map.put(SAFE_CIVILIZATION.getValue(), SAFE_CIVILIZATION);
		map.put(SAFE_CIVILIZATION.getValue(), SAFE_CIVILIZATION);
		map.put(STAGE_ACCEPTANCE.getValue(), STAGE_ACCEPTANCE);
		map.put(OTHER.getValue(), OTHER);
    }

	MeetingSummaryType(String value, String name){
		this.name = name;
		this.value = value;
	}
	@Override
	public String getName() {
		return this.name;
	}
	@Override
	public String getValue() {
		return this.value;
	}
	@Override
	public MeetingSummaryType getEnum(String value) {
		return map.get(value);
	}

}
