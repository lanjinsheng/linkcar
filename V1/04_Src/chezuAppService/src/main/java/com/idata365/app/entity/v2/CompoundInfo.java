package com.idata365.app.entity.v2;

import java.io.Serializable;
import java.util.Date;

public class CompoundInfo implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long familyId;
    private Long userId;
    private Integer stoveId;
    private String componentIds;
    private Date startTime;
    private Date endTime;
    private Integer status;
    private Integer finalComponentId;
    private String lookAdUserIds;
    private Integer lookAdCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFamilyId() {
        return familyId;
    }

    public void setFamilyId(Long familyId) {
        this.familyId = familyId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getStoveId() {
        return stoveId;
    }

    public void setStoveId(Integer stoveId) {
        this.stoveId = stoveId;
    }

    public String getComponentIds() {
        return componentIds;
    }

    public void setComponentIds(String componentIds) {
        this.componentIds = componentIds;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getFinalComponentId() {
        return finalComponentId;
    }

    public void setFinalComponentId(Integer finalComponentId) {
        this.finalComponentId = finalComponentId;
    }

    public String getLookAdUserIds() {
        return lookAdUserIds;
    }

    public void setLookAdUserIds(String lookAdUserIds) {
        this.lookAdUserIds = lookAdUserIds;
    }

    public Integer getLookAdCount() {
        return lookAdCount;
    }

    public void setLookAdCount(Integer lookAdCount) {
        this.lookAdCount = lookAdCount;
    }
}
