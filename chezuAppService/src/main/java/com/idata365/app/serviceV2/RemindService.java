package com.idata365.app.serviceV2;

import com.idata365.app.entity.FamilyParamBean;
import com.idata365.app.entity.FamilyRelation;
import com.idata365.app.entity.Message;
import com.idata365.app.enums.MessageEnum;
import com.idata365.app.mapper.FamilyMapper;
import com.idata365.app.mapper.FamilyRelationMapper;
import com.idata365.app.mapper.RemindLogMapper;
import com.idata365.app.service.BaseService;
import com.idata365.app.service.MessageService;
import com.idata365.app.service.UserInfoService;
import com.idata365.app.util.DateTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class RemindService extends BaseService<RemindService> {
    private static final Logger LOG = LoggerFactory.getLogger(RemindService.class);

    @Autowired
    private RemindLogMapper remindLogMapper;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private FamilyMapper familyMapper;
    @Autowired
    private FamilyRelationMapper familyRelationMapper;
    @Autowired
    private MessageService messageService;


    /**
     * 今天还能不能'提醒老板'
     *
     * @param userId
     * @param familyId
     * @return
     */
    public String isCanRemindBoss(Long userId, Long familyId) {
        Long createFamilyId = this.familyMapper.queryCreateFamilyId(userId);
        if (createFamilyId != null && createFamilyId.longValue() == familyId.longValue()) {
            return "0";
        }

        Integer remindCount = this.remindLogMapper.queryTodayRemindCount(userId, familyId);
        if (remindCount > 0) {
            return "0";
        } else {
            return "1";
        }
    }

    @Transactional
    public void remindBoss(Long userId) {
        Long joinFamilyId = this.familyMapper.queryJoinFamilyId(userId);
        Map<String, Object> log = new HashMap<>();
        log.put("userId", userId);
        log.put("familyId", joinFamilyId);
        log.put("eventType", "1");
        log.put("remark", "");
        remindLogMapper.insertRemindLog(log);
    }

    public boolean remindBossByTask() {
        Calendar c = Calendar.getInstance();
        if (c.get(Calendar.HOUR_OF_DAY) != 20) {
            //晚上8点
            return false;
        } else {
            List<Long> notFightFamilyId = new ArrayList<>();
            String tomorrow=DateTools.getTomorrowDateStr();

            List<FamilyRelation> familyRelations = familyRelationMapper.queryAllNotFightFamily(tomorrow);
            for (FamilyRelation relation : familyRelations) {
                if (relation.getRelationType()==1) {
                    notFightFamilyId.add(relation.getSelfFamilyId());
                } else if (relation.getRelationType()==2) {
                    notFightFamilyId.add(relation.getSelfFamilyId());
                    notFightFamilyId.add(relation.getCompetitorFamilyId());
                }
            }

            List<Long> allFamily = familyMapper.queryAllFamily();
            allFamily.remove(notFightFamilyId);
            String nick = "";
            for (Long familyId : allFamily) {
                List<Long> userIds = remindLogMapper.queryTodayRemindUserIdsByFamilyId(familyId);
                if (userIds!=null&&userIds.size()!=0) {
                    for (Long userId : userIds) {
                        nick.concat(userInfoService.getUsersAccount(userId).getNickName()+",");
                    }
                }
                FamilyParamBean bean = new FamilyParamBean();
                bean.setFamilyId(familyId);
                Long createUserId = familyMapper.queryCreateUserId(bean);
                Message msg=messageService.buildRemindMessage(createUserId, nick, familyId);
                //插入消息
                messageService.insertMessage(msg, MessageEnum.RemindBoss);
                //用定时器推送
                messageService.pushMessageTrans(msg,MessageEnum.RemindBoss);
            }
        }
        return true;
    }
}
