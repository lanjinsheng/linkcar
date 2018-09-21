package com.idata365.app.serviceV2;

import com.idata365.app.constant.DicLivenessConstant;
import com.idata365.app.entity.v2.DicLiveness;
import com.idata365.app.mapper.UserLivenessLogMapper;
import com.idata365.app.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class LivenessService extends BaseService<LivenessService> {

    @Autowired
    private UserLivenessLogMapper userLivenessLogMapper;

    public int getTodayCountById(long userId, int livenessId) {
        int countById = userLivenessLogMapper.getTodayCountById(userId, livenessId);
        if (countById > 0) {
            return 1;
        } else {
            return 0;
        }
    }

    @Transactional
    public int insertUserLivenessLog(long userId, int livenessId) {
        DicLiveness dicLiveness = DicLivenessConstant.getDicLiveness(livenessId);
        int countById = userLivenessLogMapper.getTodayCountById(userId, livenessId);
        if (countById > 0) {
            return 0;
        } else {
            Map<String, Object> bean = new HashMap<>();
            bean.put("userId", userId);
            bean.put("livenessId", livenessId);
            bean.put("eventTime", new Date());
            bean.put("livenessName", dicLiveness.getLivenessName());
            bean.put("livenessValue", dicLiveness.getLivenessValue());
            userLivenessLogMapper.insertUserLivenessLog(bean);
            return 1;
        }
    }
}
