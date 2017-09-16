package com.coolweather.app.coolweather.model;

/**
 * Created by dongdong on 2016/8/29.
 */
public class City {
    private int id;
    private String CityName;
    private String CityCode;
    private int provinceId;

    public void setId(int id) {
        this.id = id;
    }

    public void setCityName(String cityName) {
        CityName = cityName;
    }

    public void setCityCode(String cityCode) {
        CityCode = cityCode;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public String getCityName() {
        return CityName;
    }

    public int getId() {
        return id;
    }

    public String getCityCode() {
        return CityCode;
    }

    public int getProvinceId() {
        return provinceId;
    }
}