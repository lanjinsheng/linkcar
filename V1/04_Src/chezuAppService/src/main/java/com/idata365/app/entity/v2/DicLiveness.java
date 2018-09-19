package com.idata365.app.entity.v2;

import java.io.Serializable;

public class DicLiveness implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -9222315647447481118L;

    private Long id;
    private Integer livenessId;
    private Integer livenessValue;
    private Integer timesPerDay;
    private String livenessName;

    public Long getId() {
        return id;
    }

    public Integer getTimesPerDay() {
        return timesPerDay;
    }

    public void setTimesPerDay(Integer timesPerDay) {
        this.timesPerDay = timesPerDay;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLivenessId() {
        return livenessId;
    }

    public void setLivenessId(Integer livenessId) {
        this.livenessId = livenessId;
    }

    public Integer getLivenessValue() {
        return livenessValue;
    }

    public void setLivenessValue(Integer livenessValue) {
        this.livenessValue = livenessValue;
    }

    public String getLivenessName() {
        return livenessName;
    }

    public void setLivenessName(String livenessName) {
        this.livenessName = livenessName;
    }
}
