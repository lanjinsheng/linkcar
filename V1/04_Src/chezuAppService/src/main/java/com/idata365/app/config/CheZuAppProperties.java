package com.idata365.app.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:chezuapp.properties")
@ConfigurationProperties(prefix = "app")
public class CheZuAppProperties {
    //#超速项满分
    private Double overSpeedAllScore = 20.0;
    //#急转项满分
    private Double turnAllScore = 20.0;
    //#急刹项满分
    private Double brakeAllScore = 20.0;

    //任务对应宝箱需要完成的任务数量
    private Integer dayMissionBox1SureNum = 2;
    private Integer dayMissionBox2SureNum = 4;
    private Integer dayMissionBox3SureNum = 6;
    private Integer dayMissionBox4SureNum = 7;
    private Integer actMissionBox1SureNum = 2;
    private Integer actMissionBox2SureNum = 6;
    private Integer actMissionBox3SureNum = 10;
    private Integer actMissionBox4SureNum = 14;
    //对应宝箱的动力奖励
    private Long dayMissionBox1PowerNum = 20L;
    private Long dayMissionBox2PowerNum = 40L;
    private Long dayMissionBox3PowerNum = 60L;
    private Long dayMissionBox4PowerNum = 100L;
    private Long actMissionBox1PowerNum = 50L;
    private Long actMissionBox2PowerNum = 80L;
    private Long actMissionBox3PowerNum = 100L;
    private Long actMissionBox4PowerNum = 120L;

    //俱乐部计算分数人数
    private Integer maxUserCount = 4;

    //段位动力基数
    private Long cardinalNumQ = 100L;
    private Long cardinalNumB = 150L;
    private Long cardinalNumH = 200L;
    private Long cardinalNumZ = 250L;
    private Long cardinalNumG = 300L;

    //视频任务奖励动力值
    private Integer adPowerNum = 30;
    //活动任务奖励动力增长值
    private Integer actPowerAddNum = 40;

    //俱乐部老板挑战宝箱算分三维度
    //无挑战
    private Double noFight = 1D;
    //胜利
    private Double win = 2D;
    //失败
    private Double loss = 1D;
    //平局
    private Double dogfall = 1.5D;

    public Double getOverSpeedAllScore() {
        return overSpeedAllScore;
    }

    public void setOverSpeedAllScore(Double overSpeedAllScore) {
        this.overSpeedAllScore = overSpeedAllScore;
    }

    public Double getTurnAllScore() {
        return turnAllScore;
    }

    public void setTurnAllScore(Double turnAllScore) {
        this.turnAllScore = turnAllScore;
    }

    public Double getBrakeAllScore() {
        return brakeAllScore;
    }

    public void setBrakeAllScore(Double brakeAllScore) {
        this.brakeAllScore = brakeAllScore;
    }

    public Integer getDayMissionBox1SureNum() {
        return dayMissionBox1SureNum;
    }

    public void setDayMissionBox1SureNum(Integer dayMissionBox1SureNum) {
        this.dayMissionBox1SureNum = dayMissionBox1SureNum;
    }

    public Integer getDayMissionBox2SureNum() {
        return dayMissionBox2SureNum;
    }

    public void setDayMissionBox2SureNum(Integer dayMissionBox2SureNum) {
        this.dayMissionBox2SureNum = dayMissionBox2SureNum;
    }

    public Integer getDayMissionBox3SureNum() {
        return dayMissionBox3SureNum;
    }

    public void setDayMissionBox3SureNum(Integer dayMissionBox3SureNum) {
        this.dayMissionBox3SureNum = dayMissionBox3SureNum;
    }

    public Integer getDayMissionBox4SureNum() {
        return dayMissionBox4SureNum;
    }

    public void setDayMissionBox4SureNum(Integer dayMissionBox4SureNum) {
        this.dayMissionBox4SureNum = dayMissionBox4SureNum;
    }

    public Integer getActMissionBox1SureNum() {
        return actMissionBox1SureNum;
    }

    public void setActMissionBox1SureNum(Integer actMissionBox1SureNum) {
        this.actMissionBox1SureNum = actMissionBox1SureNum;
    }

    public Integer getActMissionBox2SureNum() {
        return actMissionBox2SureNum;
    }

    public void setActMissionBox2SureNum(Integer actMissionBox2SureNum) {
        this.actMissionBox2SureNum = actMissionBox2SureNum;
    }

    public Integer getActMissionBox3SureNum() {
        return actMissionBox3SureNum;
    }

    public void setActMissionBox3SureNum(Integer actMissionBox3SureNum) {
        this.actMissionBox3SureNum = actMissionBox3SureNum;
    }

    public Integer getActMissionBox4SureNum() {
        return actMissionBox4SureNum;
    }

    public void setActMissionBox4SureNum(Integer actMissionBox4SureNum) {
        this.actMissionBox4SureNum = actMissionBox4SureNum;
    }

    public Long getDayMissionBox1PowerNum() {
        return dayMissionBox1PowerNum;
    }

    public void setDayMissionBox1PowerNum(Long dayMissionBox1PowerNum) {
        this.dayMissionBox1PowerNum = dayMissionBox1PowerNum;
    }

    public Long getDayMissionBox2PowerNum() {
        return dayMissionBox2PowerNum;
    }

    public void setDayMissionBox2PowerNum(Long dayMissionBox2PowerNum) {
        this.dayMissionBox2PowerNum = dayMissionBox2PowerNum;
    }

    public Long getDayMissionBox3PowerNum() {
        return dayMissionBox3PowerNum;
    }

    public void setDayMissionBox3PowerNum(Long dayMissionBox3PowerNum) {
        this.dayMissionBox3PowerNum = dayMissionBox3PowerNum;
    }

    public Long getDayMissionBox4PowerNum() {
        return dayMissionBox4PowerNum;
    }

    public void setDayMissionBox4PowerNum(Long dayMissionBox4PowerNum) {
        this.dayMissionBox4PowerNum = dayMissionBox4PowerNum;
    }

    public Long getActMissionBox1PowerNum() {
        return actMissionBox1PowerNum;
    }

    public void setActMissionBox1PowerNum(Long actMissionBox1PowerNum) {
        this.actMissionBox1PowerNum = actMissionBox1PowerNum;
    }

    public Long getActMissionBox2PowerNum() {
        return actMissionBox2PowerNum;
    }

    public void setActMissionBox2PowerNum(Long actMissionBox2PowerNum) {
        this.actMissionBox2PowerNum = actMissionBox2PowerNum;
    }

    public Long getActMissionBox3PowerNum() {
        return actMissionBox3PowerNum;
    }

    public void setActMissionBox3PowerNum(Long actMissionBox3PowerNum) {
        this.actMissionBox3PowerNum = actMissionBox3PowerNum;
    }

    public Long getActMissionBox4PowerNum() {
        return actMissionBox4PowerNum;
    }

    public void setActMissionBox4PowerNum(Long actMissionBox4PowerNum) {
        this.actMissionBox4PowerNum = actMissionBox4PowerNum;
    }

    public Integer getMaxUserCount() {
        return maxUserCount;
    }

    public void setMaxUserCount(Integer maxUserCount) {
        this.maxUserCount = maxUserCount;
    }

    public Long getCardinalNumQ() {
        return cardinalNumQ;
    }

    public void setCardinalNumQ(Long cardinalNumQ) {
        this.cardinalNumQ = cardinalNumQ;
    }

    public Long getCardinalNumB() {
        return cardinalNumB;
    }

    public void setCardinalNumB(Long cardinalNumB) {
        this.cardinalNumB = cardinalNumB;
    }

    public Long getCardinalNumH() {
        return cardinalNumH;
    }

    public void setCardinalNumH(Long cardinalNumH) {
        this.cardinalNumH = cardinalNumH;
    }

    public Long getCardinalNumZ() {
        return cardinalNumZ;
    }

    public void setCardinalNumZ(Long cardinalNumZ) {
        this.cardinalNumZ = cardinalNumZ;
    }

    public Long getCardinalNumG() {
        return cardinalNumG;
    }

    public void setCardinalNumG(Long cardinalNumG) {
        this.cardinalNumG = cardinalNumG;
    }

    public Integer getAdPowerNum() {
        return adPowerNum;
    }

    public void setAdPowerNum(Integer adPowerNum) {
        this.adPowerNum = adPowerNum;
    }

    public Integer getActPowerAddNum() {
        return actPowerAddNum;
    }

    public void setActPowerAddNum(Integer actPowerAddNum) {
        this.actPowerAddNum = actPowerAddNum;
    }

    public Double getNoFight() {
        return noFight;
    }

    public void setNoFight(Double noFight) {
        this.noFight = noFight;
    }

    public Double getWin() {
        return win;
    }

    public void setWin(Double win) {
        this.win = win;
    }

    public Double getLoss() {
        return loss;
    }

    public void setLoss(Double loss) {
        this.loss = loss;
    }

    public Double getDogfall() {
        return dogfall;
    }

    public void setDogfall(Double dogfall) {
        this.dogfall = dogfall;
    }
}
