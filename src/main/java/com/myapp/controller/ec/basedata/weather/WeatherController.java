package com.myapp.controller.ec.basedata.weather;

import com.alibaba.fastjson.JSON;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.annotation.PermissionItemAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BasePageListController;
import com.myapp.core.enums.PermissionTypeEnum;
import com.myapp.core.exception.db.QueryException;
import com.myapp.core.exception.db.SaveException;
import com.myapp.core.model.WebDataModel;
import com.myapp.entity.ec.basedata.WeatherInfo;
import com.myapp.service.ec.basedata.WeatherService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @path：com.myapp.controller.ec.basedata.weather
 * @description：
 * @author： ly
 * @date: 2017-12-19 23:34
 */
@PermissionAnn(name="系统管理.现场管理.基础数据.天气信息",number="app.ec.basedata.weather")
@Controller
@RequestMapping("ec/basedata/weather")
public class WeatherController extends BasePageListController {
    @Resource
    private WeatherService  weatherService;

    @PermissionItemAnn(name="查看",number="onload",type= PermissionTypeEnum.PAGE)
    @RequestMapping("/list")
    public ModelAndView analysisList(){
        Map params = new HashMap();
        return toPage("ec/basedata/weather/weatherList", params);
    }
    @Override
    public AbstractBaseService getService() {
        return this.weatherService;
    }

    @RequestMapping("/query")
    @ResponseBody
    public WebDataModel queryYearWeather(int year,String projectId) throws QueryException {
        init();
        this.data = weatherService.queryYearWeather(year,projectId);
        return ajaxModel();
    }

    @RequestMapping("/add/year")
    @ResponseBody
    public WebDataModel addYearWeather(int year,String projectId){
        init();
        try {
            this.data = weatherService.addYearWeather(year,projectId);
        } catch (Exception e) {
           setErrorMesg(e.getMessage());
           this.statusCode = STATUSCODE_ERROR;
        }
        return ajaxModel();
    }

    @RequestMapping("/save")
    @ResponseBody
    public WebDataModel saveYearWeather(String weatherDetail) throws QueryException {
        init();
        List<WeatherInfo> weatherInfoList = JSON.parseArray(weatherDetail,WeatherInfo.class);
        try {
            weatherService.saveWeather(weatherInfoList);
        } catch (SaveException e) {
            e.printStackTrace();
            setErrorMesg(e.getMessage());
            this.statusCode = STATUSCODE_ERROR;
        }
        return ajaxModel();
    }
}
