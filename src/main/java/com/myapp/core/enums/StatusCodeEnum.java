package com.myapp.core.enums;

import com.myapp.core.base.enums.MyEnum;
import org.apache.commons.net.ftp.FTP;

import java.util.HashMap;
import java.util.Map;

/**
 * @path：com.myapp.core.enums
 * @description：
 * @author： ly
 * @date: 2017-10-26 23:37
 */
public enum StatusCodeEnum implements MyEnum<StatusCodeEnum> {
    INFO("1","提示"),ERROR("-1","错误"),SUCCESS("0","成功"),
    NEEDLOGIN("-99","需要重新登录"),EXCEPTION("EXCEPTION","异常类"),WARNING("100","警告类");

    private String name;
    private String value;

    StatusCodeEnum(String value,String name){
        this.name = name;
        this.value = value;
    }
    private static final Map<String, StatusCodeEnum> map = new HashMap<String, StatusCodeEnum>();
    static {
        map.put(INFO.getValue(), INFO);
        map.put(ERROR.getValue(), ERROR);
        map.put(SUCCESS.getValue(), SUCCESS);
        map.put(NEEDLOGIN.getValue(), NEEDLOGIN);
        map.put(EXCEPTION.getValue(), EXCEPTION);
        map.put(WARNING.getValue(), WARNING);
    }
    @Override
    public StatusCodeEnum getEnum(String value) {
        return map.get(value);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getValue() {
        return this.value;
    }
}
