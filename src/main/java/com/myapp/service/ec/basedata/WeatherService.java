package com.myapp.service.ec.basedata;

import com.myapp.core.exception.db.QueryException;
import com.myapp.core.exception.db.SaveException;
import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.entity.ec.basedata.WeatherInfo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @path：com.myapp.service.ec.basedata
 * @description：
 * @author： ly
 * @date: 2017-12-19 23:38
 */
@Service("weatherService")
public class WeatherService extends BaseInterfaceService<WeatherInfo> {
    public final static int MONTH = 12;
    /**
     * 功能：添加晴雨表年份信息
     * @param year
     * @param projectId
     * @return
     */
    public List addYearWeather(int year,String projectId) throws SaveException, QueryException {
        if(!hasYearWeather(year,projectId)){
            throw new SaveException("该工程项目"+year+"年晴雨表已存在");
        }
        WeatherInfo weatherInfo = null;
        for(int i=1;i<=MONTH;i++){
            weatherInfo = new WeatherInfo();
            weatherInfo.setProjectId(projectId);
            weatherInfo.setYear(year);
            weatherInfo.setType("温度");
            weatherInfo.setMonth(monthToCn(i));
            weatherInfo.setMonthInt(i);
            saveEntity(weatherInfo);

            weatherInfo =  new WeatherInfo();
            weatherInfo.setProjectId(projectId);
            weatherInfo.setYear(year);
            weatherInfo.setType("天气");
            weatherInfo.setMonth(monthToCn(i));
            weatherInfo.setMonthInt(i);
            saveEntity(weatherInfo);
        }
        return queryYearWeather(year,projectId);
    }

    public boolean hasYearWeather(int year,String projectId){
        String hql = "select count(1) as count from WeatherInfo weatherInfo " +
                "where weatherInfo.projectId=? and weatherInfo.year=? ";
        List<Map> result = findByHQL(hql,new Object[]{projectId,year});
        if(result==null){
            return  true;
        }
        return Integer.parseInt(result.get(0).get("count").toString())==0;
    }

    public List queryYearWeather(int year,String projectId) throws QueryException {
        String hql = "select a.* from t_ec_weather a " +
                "where a.fProjectId=? and a.fYear=? order by a.fmonthint,a.ftype desc";
        return executeSQLQuery(hql,new Object[]{projectId,year});
    }

    private String monthToCn(int month){
        String cnMonth = "";
        switch (month){
            case 1:cnMonth = "一月"; break;
            case 2:cnMonth = "二月";break;
            case 3:cnMonth = "三月";break;
            case 4:cnMonth = "四月";break;
            case 5:cnMonth = "五月";break;
            case 6:cnMonth = "六月";break;
            case 7:cnMonth = "七月";break;
            case 8:cnMonth = "八月";break;
            case 9:cnMonth = "九月";break;
            case 10:cnMonth = "十月";break;
            case 11:cnMonth = "十一月";break;
            case 12:cnMonth = "十二月";break;
            default: cnMonth = "一月";break;
        }
        return  cnMonth;
    }

    public void saveWeather(List<WeatherInfo> weatherInfoList) throws SaveException {
        for (WeatherInfo weatherInfo:weatherInfoList){
            saveEntity(weatherInfo);
        }
    }
}
