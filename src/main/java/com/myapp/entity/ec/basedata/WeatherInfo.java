package com.myapp.entity.ec.basedata;

import com.alibaba.fastjson.annotation.JSONField;
import com.myapp.core.annotation.MyEntityAnn;
import com.myapp.core.base.entity.CoreInfo;

import javax.persistence.*;

/**
 * @path：com.myapp.entity.ec.basedata
 * @description：项目天气信息表
 * @author： ly
 * @date: 2017-12-19 23:07
 */
@Entity
@MyEntityAnn(name="项目天气信息")
@Table(name="t_ec_weather")
public class WeatherInfo extends CoreInfo {
    /**
     * 工程项目
     */
    private String projectId;
    /**
     * 年份
     */
    private int year;
    /**
     * 月份
     */
    private String month;

    private int monthInt;
    /**
     * 天气类型
     */
    private String type;

    private String one;

    private String two;

    private String three;

    private String four;

    private String five;

    private String six;

    private String seven;

    private String eight;

    private String nine;

    private String ten;

    private String eleven;

    private String twelve;

    private String thirteen;

    private String fourteen;

    private String fifteen;

    private String sixteen;

    private String seventeen;

    private String eighteen;

    private String nineteen;

    private String twenty;

    private String twentyOne;

    private String twentyTwo;

    private String twentyThree;

    private String twentyFour;

    private String twentyFive;

    private String twentySix;

    private String twentySeven;

    private String twentyEight;

    private String twentyNine;

    private String thirty;

    private String thirtyOne;

    @Column(name = "fProjectId")
    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
    @Column(name="fYear")
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
    @Column(name="fMonth",length = 20)
    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    @Column(name="fType",length = 20)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name="fOne",length = 20)
    public String getOne() {
        return one;
    }

    public void setOne(String one) {
        this.one = one;
    }

    @Column(name="fTwo",length = 20)
    public String getTwo() {
        return two;
    }

    public void setTwo(String two) {
        this.two = two;
    }
    @Column(name="fThree",length = 20)
    public String getThree() {
        return three;
    }

    public void setThree(String three) {
        this.three = three;
    }
    @Column(name="fFour",length = 20)
    public String getFour() {
        return four;
    }

    public void setFour(String four) {
        this.four = four;
    }
    @Column(name="fFive",length = 20)
    public String getFive() {
        return five;
    }

    public void setFive(String five) {
        this.five = five;
    }
    @Column(name="fSix",length = 20)
    public String getSix() {
        return six;
    }

    public void setSix(String six) {
        this.six = six;
    }
    @Column(name="fSeven",length = 20)
    public String getSeven() {
        return seven;
    }

    public void setSeven(String seven) {
        this.seven = seven;
    }
    @Column(name="fEight",length = 20)
    public String getEight() {
        return eight;
    }

    public void setEight(String eight) {
        this.eight = eight;
    }
    @Column(name="fNine",length = 20)
    public String getNine() {
        return nine;
    }

    public void setNine(String nine) {
        this.nine = nine;
    }
    @Column(name="fTen",length = 20)
    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }
    @Column(name="fEleven",length = 20)
    public String getEleven() {
        return eleven;
    }

    public void setEleven(String eleven) {
        this.eleven = eleven;
    }
    @Column(name="fTwelve",length = 20)
    public String getTwelve() {
        return twelve;
    }

    public void setTwelve(String twelve) {
        this.twelve = twelve;
    }
    @Column(name="fThirteen",length = 20)
    public String getThirteen() {
        return thirteen;
    }

    public void setThirteen(String thirteen) {
        this.thirteen = thirteen;
    }
    @Column(name="fFourteen",length = 20)
    public String getFourteen() {
        return fourteen;
    }

    public void setFourteen(String fourteen) {
        this.fourteen = fourteen;
    }
    @Column(name="fFifteen",length = 20)
    public String getFifteen() {
        return fifteen;
    }

    public void setFifteen(String fifteen) {
        this.fifteen = fifteen;
    }
    @Column(name="fSixteen",length = 20)
    public String getSixteen() {
        return sixteen;
    }

    public void setSixteen(String sixteen) {
        this.sixteen = sixteen;
    }
    @Column(name="fSeventeen",length = 20)
    public String getSeventeen() {
        return seventeen;
    }

    public void setSeventeen(String seventeen) {
        this.seventeen = seventeen;
    }
    @Column(name="fEighteen",length = 20)
    public String getEighteen() {
        return eighteen;
    }

    public void setEighteen(String eighteen) {
        this.eighteen = eighteen;
    }
    @Column(name="fNineteen",length = 20)
    public String getNineteen() {
        return nineteen;
    }

    public void setNineteen(String nineteen) {
        this.nineteen = nineteen;
    }
    @Column(name="fTwenty",length = 20)
    public String getTwenty() {
        return twenty;
    }

    public void setTwenty(String twenty) {
        this.twenty = twenty;
    }
    @Column(name="fTwentyOne",length = 20)
    public String getTwentyOne() {
        return twentyOne;
    }

    public void setTwentyOne(String twentyOne) {
        this.twentyOne = twentyOne;
    }
    @Column(name="fTwentyTwo",length = 20)
    public String getTwentyTwo() {
        return twentyTwo;
    }

    public void setTwentyTwo(String twentyTwo) {
        this.twentyTwo = twentyTwo;
    }
    @Column(name="fTwentyThree",length = 20)
    public String getTwentyThree() {
        return twentyThree;
    }

    public void setTwentyThree(String twentyThree) {
        this.twentyThree = twentyThree;
    }
    @Column(name="fTwentyFour",length = 20)
    public String getTwentyFour() {
        return twentyFour;
    }

    public void setTwentyFour(String twentyFour) {
        this.twentyFour = twentyFour;
    }
    @Column(name="fTwentyFive",length = 20)
    public String getTwentyFive() {
        return twentyFive;
    }

    public void setTwentyFive(String twentyFive) {
        this.twentyFive = twentyFive;
    }
    @Column(name="fTwentySix",length = 20)
    public String getTwentySix() {
        return twentySix;
    }

    public void setTwentySix(String twentySix) {
        this.twentySix = twentySix;
    }
    @Column(name="fTwentySeven",length = 20)
    public String getTwentySeven() {
        return twentySeven;
    }

    public void setTwentySeven(String twentySeven) {
        this.twentySeven = twentySeven;
    }
    @Column(name="fTwentyEight",length = 20)
    public String getTwentyEight() {
        return twentyEight;
    }

    public void setTwentyEight(String twentyEight) {
        this.twentyEight = twentyEight;
    }
    @Column(name="fTwentyNine",length = 20)
    public String getTwentyNine() {
        return twentyNine;
    }

    public void setTwentyNine(String twentyNine) {
        this.twentyNine = twentyNine;
    }
    @Column(name="fThirty",length = 20)
    public String getThirty() {
        return thirty;
    }

    public void setThirty(String thirty) {
        this.thirty = thirty;
    }
    @Column(name="fThirtyOne",length = 20)
    public String getThirtyOne() {
        return thirtyOne;
    }

    public void setThirtyOne(String thirtyOne) {
        this.thirtyOne = thirtyOne;
    }

    @Column(name="fMonthInt",length = 2)
    public int getMonthInt() {
        return monthInt;
    }

    public void setMonthInt(int monthInt) {
        this.monthInt = monthInt;
    }
}
