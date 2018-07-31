package com.idata365.app.service.common;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idata365.app.entity.UserScoreDayStat;
import com.idata365.app.mapper.UserScoreDayStatMapper;

/**
 * 
 * @author jinsheng
 *
 */
@Service
public class FamilyScoreService {
	private static final Logger LOG = LoggerFactory
			.getLogger(FamilyScoreService.class);
	@Autowired
	UserScoreDayStatMapper userScoreDayStatMapper;

	public Double familyScore(Long familyId, String daystamp) {
		UserScoreDayStat pUserScoreDayStat = new UserScoreDayStat();
		pUserScoreDayStat.setFamilyId(familyId);
		pUserScoreDayStat.setDaystamp(daystamp);
		List<UserScoreDayStat> list = userScoreDayStatMapper
				.getUsersDayScoreByFamily(pUserScoreDayStat);
		int maxUserCount = 4;
		int i = 0;
		Double avgScore = 0D;
		for (UserScoreDayStat userDayScore : list) {
			i++;
			if (i > maxUserCount) {
				break;
			}
			if (userDayScore != null) {

				avgScore += userDayScore.getAvgScore();

			}
		}
		return BigDecimal.valueOf(avgScore).setScale(2,RoundingMode.HALF_EVEN).doubleValue();
	}
}
