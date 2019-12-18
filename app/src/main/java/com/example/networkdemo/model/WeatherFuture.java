package com.example.networkdemo.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class WeatherFuture implements Serializable {
    @JSONField(format = "yyyy-MM-dd")
    private Date date;
    private String temperature;
    private String weather;
    private String direct;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getDirect() {
        return direct;
    }

    public void setDirect(String direct) {
        this.direct = direct;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        String strDate = sdf.format(date);
        return strDate + "：" + temperature + ", " + weather + ", " + direct;
    }
}
