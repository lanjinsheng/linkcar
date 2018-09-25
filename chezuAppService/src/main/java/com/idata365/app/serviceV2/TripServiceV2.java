package com.idata365.app.serviceV2;

import com.idata365.app.config.CheZuAppProperties;
import com.idata365.app.entity.UserScoreDayStat;
import com.idata365.app.mapper.UserScoreDayStatMapper;
import com.idata365.app.service.BaseService;
import com.idata365.app.util.DateTools;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

@Service
public class TripServiceV2 extends BaseService<TripServiceV2> {
    protected static final Logger LOGGER = LoggerFactory.getLogger(TripServiceV2.class);

    @Autowired
    private UserScoreDayStatMapper userScoreDayStatMapper;
    @Autowired
    CheZuAppProperties cheZuAppProperties;

    @Transactional
    public Map<String, Object> getTodayAllTravel(long userId) {
        Map<String, Object> rtMap = new HashMap<>();
        UserScoreDayStat userScoreDayStat = userScoreDayStatMapper.getTodayAllTravel(userId, DateTools.getYYYY_MM_DD());
        if (userScoreDayStat == null||userScoreDayStat.getTime()==0) {
            return rtMap;
        }
        rtMap.put("time", DurationFormatUtils.formatDuration(userScoreDayStat.getTime().longValue() * 1000, "HH小时mm分钟"));
        rtMap.put("mileage", BigDecimal.valueOf(userScoreDayStat.getMileage() / 1000.0).setScale(2, RoundingMode.HALF_EVEN).toString());
        rtMap.put("score", BigDecimal.valueOf(userScoreDayStat.getAvgScore()).setScale(0,RoundingMode.HALF_EVEN).toString());
        rtMap.put("isShowMapFlag", "0");

        // 超速系数
        double overScore = userScoreDayStat.getOverspeedTimesScore()==null?0:userScoreDayStat.getOverspeedTimesScore();
        double overspeedNum = overScore / cheZuAppProperties.getOverSpeedAllScore();
        rtMap.put("overspeedNum", overspeedNum < 0 ? 0 : overspeedNum);
        // 急转系数
        double turnScore = userScoreDayStat.getTurnTimesScore()==null?0:userScoreDayStat.getTurnTimesScore();
        double turnNum = turnScore / cheZuAppProperties.getTurnAllScore();
        rtMap.put("turnNum", turnNum < 0 ? 0 : turnNum);
        // 急刹系数
        double breakScore = userScoreDayStat.getBrakeTimesScore()==null?0:userScoreDayStat.getBrakeTimesScore();
        double brakeNum = breakScore / cheZuAppProperties.getBrakeAllScore();
        rtMap.put("brakeNum", brakeNum < 0 ? 0 : brakeNum);
        rtMap.put("brakeScore", breakScore);
        rtMap.put("overspeedScore", overScore);
        rtMap.put("turnScore", turnScore);
        return rtMap;
    }

}
