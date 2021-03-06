package com.idata365.app.serviceV2;


import java.util.*;

import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import com.google.gson.Gson;
import com.idata365.app.config.CheZuAppProperties;
import com.idata365.app.constant.DicLivenessConstant;
import com.idata365.app.entity.FamilyParamBean;
import com.idata365.app.entity.v2.*;
import com.idata365.app.mapper.*;
import com.idata365.app.util.GsonUtils;
import com.idata365.app.util.ResultUtils;
import com.netflix.discovery.converters.wrappers.CodecWrappers;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata365.app.constant.DicComponentConstant;
import com.idata365.app.entity.Message;
import com.idata365.app.entity.TaskPowerLogs;
import com.idata365.app.entity.bean.ReturnMessage;
import com.idata365.app.enums.MessageEnum;
import com.idata365.app.enums.PowerEnum;
import com.idata365.app.remote.ChezuImService;
import com.idata365.app.service.BaseService;
import com.idata365.app.service.ImService;
import com.idata365.app.service.MessageService;
import com.idata365.app.util.DateTools;
import com.idata365.app.util.RandUtils;

/**
 * @author LanYeYe
 * @ClassName: CarService
 * @Description: TODO(车辆业务)
 * @date 2018年4月27日
 */
@Service
public class ComponentService extends BaseService<ComponentService> {
    private final static Logger LOG = LoggerFactory.getLogger(ComponentService.class);
    @Autowired
    ComponentMapper componentMapper;
    @Autowired
    FamilyMapper familyMapper;
    @Autowired
    UsersAccountMapper usersAccountMapperr;
    @Autowired
    TaskPowerLogsMapper taskPowerLogsMapper;
    @Autowired
    MessageService messageService;
    @Autowired
    UsersAccountMapper usersAccountMapper;
    @Autowired
    ChezuImService chezuImService;
    @Autowired
    CompoundMapper compoundMapper;
    @Autowired
    CheZuAppProperties appProperties;
    @Autowired
    LivenessService livenessService;

    public Map<String, Object> getUserComponent(long userId) {
        Map<String, Object> rtMap = new HashMap<>();
        Map<String, List<Map<String, Object>>> paramMap = new HashMap<>();
        List<Map<String, Object>> componentList = new ArrayList<>();
        List<Map<String, Object>> componentLT = new ArrayList<>();
        List<Map<String, Object>> componentJY = new ArrayList<>();
        List<Map<String, Object>> componentHHS = new ArrayList<>();
        List<Map<String, Object>> componentSCP = new ArrayList<>();
        List<Map<String, Object>> componentXDC = new ArrayList<>();
        paramMap.put("1", componentLT);
        paramMap.put("2", componentHHS);
        paramMap.put("3", componentJY);
        paramMap.put("4", componentSCP);
        paramMap.put("5", componentXDC);
        List<ComponentUser> components = componentMapper.getFreeComponentUser(userId);

        if (components == null || components.size() == 0) {

        } else {
            for (ComponentUser component : components) {
                Map<String, Object> m1 = new HashMap<>();
                m1.put("userComponentId", String.valueOf(component.getId()));
                DicComponent dicComponent = DicComponentConstant.getDicComponent(component.getComponentId());
                m1.put("componentName", dicComponent.getComponentValue());
                m1.put("quality", dicComponent.getQuality());
                m1.put("imgUrl", dicComponent.getComponentUrl());
                m1.put("componentNum", "1");
                m1.put("componentType", dicComponent.getComponentType());
                m1.put("componentDesc", dicComponent.getComponentDesc());
                m1.put("componentAttribute", "动力加成" + (int) (dicComponent.getPowerAddition() * 100) + "%");
                m1.put("componentLoss", dicComponent.getTravelNum() + "次行程");
                m1.put("power", dicComponent.getPower().toString());
                Integer componentType = dicComponent.getComponentType();
                List<Map<String, Object>> list = paramMap.get(String.valueOf(componentType));
                list.add(m1);
//				   paramMap.get(String.valueOf(componentType)).add(m1);
                componentList.add(m1);
            }
        }
        rtMap.put("componentList", componentList);
        rtMap.put("componentLT", componentLT);
        rtMap.put("componentJY", componentJY);
        rtMap.put("componentHHS", componentHHS);
        rtMap.put("componentSCP", componentSCP);
        rtMap.put("componentXDC", componentXDC);

        componentMapper.updateCompUHadLooked(userId);

        return rtMap;
    }

    public Map<String, Object> getUserComponentV2(long userId) {
        Map<String, Object> rtMap = new HashMap<>();
        Map<String, List<Map<String, Object>>> paramMap = new HashMap<>();
        List<Map<String, Object>> componentLT = new ArrayList<>();
        List<Map<String, Object>> componentJY = new ArrayList<>();
        List<Map<String, Object>> componentHHS = new ArrayList<>();
        List<Map<String, Object>> componentSCP = new ArrayList<>();
        List<Map<String, Object>> componentXDC = new ArrayList<>();
        paramMap.put("1", componentLT);
        paramMap.put("2", componentHHS);
        paramMap.put("3", componentJY);
        paramMap.put("4", componentSCP);
        paramMap.put("5", componentXDC);
        List<ComponentUser> components = componentMapper.getFreeComponentUser(userId);

        if (components == null || components.size() == 0) {

        } else {
            for (ComponentUser component : components) {
                Map<String, Object> m1 = new HashMap<>();
                List<String> ids = new ArrayList<>();
                ids.add(component.getId().toString());
                m1.put("userComponentIds", ids);
                DicComponent dicComponent = DicComponentConstant.getDicComponent(component.getComponentId());
                m1.put("componentName", dicComponent.getComponentValue());
                m1.put("quality", dicComponent.getQuality());
                m1.put("imgUrl", dicComponent.getComponentUrl());
                m1.put("flag", dicComponent.getTravelNum());
                m1.put("componentNum", "1");
                m1.put("componentType", dicComponent.getComponentType());
                m1.put("componentDesc", dicComponent.getComponentDesc());
                m1.put("componentAttribute", "动力加成" + (int) (dicComponent.getPowerAddition() * 100) + "%");
                m1.put("componentLoss", component.getLeftTravelNum() + "次行程");
                m1.put("damageDegree", component.getLeftTravelNum()+"/"+dicComponent.getTravelNum());
                Integer componentType = dicComponent.getComponentType();
                List<Map<String, Object>> list = paramMap.get(String.valueOf(componentType));

                if (list.size() == 0) {
                    list.add(m1);
                } else {
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).get("quality").equals(dicComponent.getQuality()) && Integer.valueOf(list.get(i).get("componentLoss").toString().substring(0, 1)) == component.getLeftTravelNum()) {
                            list.get(i).put("componentNum", String.valueOf(Integer.valueOf(list.get(i).get("componentNum").toString()) + 1));
                            List<String> ids1 = (List<String>) list.get(i).get("userComponentIds");
                            ids1.add(component.getId().toString());
                            list.get(i).put("userComponentIds", ids1);
                            break;
                        } else if (i == list.size() - 1) {
                            list.add(m1);
                            break;
                        }
                    }
                }

                // 排序--- flag 1-->2-->3
                Collections.sort(list, new Comparator<Map<String, Object>>() {
                    public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                        return Double.valueOf(o1.get("flag").toString()).compareTo(Double.valueOf(o2.get("flag").toString()));
                    }
                });
            }
        }
        rtMap.put("componentLT", componentLT);
        rtMap.put("componentJY", componentJY);
        rtMap.put("componentHHS", componentHHS);
        rtMap.put("componentSCP", componentSCP);
        rtMap.put("componentXDC", componentXDC);
        return rtMap;
    }

    public Map<String, Object> getFamilyComponent(long familyId) {
        Map<String, Object> rtMap = new HashMap<>();
        List<Map<String, Object>> componentList = new ArrayList<>();
        List<ComponentFamily> components = componentMapper.getFreeComponentFamily(familyId);

        if (components == null || components.size() == 0) {

        } else {
            for (ComponentFamily component : components) {
                Map<String, Object> m1 = new HashMap<>();
                m1.put("familyComponentId", String.valueOf(component.getId()));
                DicComponent dicComponent = DicComponentConstant.getDicComponent(component.getComponentId());
                m1.put("componentName", dicComponent.getComponentValue());
                m1.put("quality", dicComponent.getQuality());
                m1.put("imgUrl", dicComponent.getComponentUrl());
                m1.put("componentNum", "1");
                m1.put("componentType", dicComponent.getComponentType());
                m1.put("componentDesc", dicComponent.getComponentDesc());
                m1.put("componentAttribute", "动力加成" + (int) (dicComponent.getPowerAddition() * 100) + "%");
                m1.put("componentLoss", dicComponent.getTravelNum() + "次行程");
                componentList.add(m1);
            }
        }
        rtMap.put("componentList", componentList);

        return rtMap;
    }

    public Map<String, Object> getFamilyComponentV2(long familyId) {
        Map<String, Object> rtMap = new HashMap<>();
        Map<String, List<Map<String, Object>>> paramMap = new HashMap<>();
        List<Map<String, Object>> componentLT = new ArrayList<>();
        List<Map<String, Object>> componentJY = new ArrayList<>();
        List<Map<String, Object>> componentHHS = new ArrayList<>();
        List<Map<String, Object>> componentSCP = new ArrayList<>();
        List<Map<String, Object>> componentXDC = new ArrayList<>();
        paramMap.put("1", componentLT);
        paramMap.put("2", componentHHS);
        paramMap.put("3", componentJY);
        paramMap.put("4", componentSCP);
        paramMap.put("5", componentXDC);
        List<ComponentFamily> components = componentMapper.getFreeComponentFamily(familyId);

        if (components == null || components.size() == 0) {

        } else {
            for (ComponentFamily component : components) {
                Map<String, Object> m1 = new HashMap<>();
                List<String> ids = new ArrayList<>();
                ids.add(component.getId().toString());
                m1.put("familyComponentIds", ids);
                DicComponent dicComponent = DicComponentConstant.getDicComponent(component.getComponentId());
                m1.put("componentName", dicComponent.getComponentValue());
                m1.put("quality", dicComponent.getQuality());
                m1.put("imgUrl", dicComponent.getComponentUrl());
                m1.put("flag", dicComponent.getTravelNum());
                m1.put("componentNum", "1");
                m1.put("componentType", dicComponent.getComponentType());
                m1.put("componentDesc", dicComponent.getComponentDesc());
                m1.put("componentAttribute", "动力加成" + (int) (dicComponent.getPowerAddition() * 100) + "%");

                Integer travelNum = 0;
                if (component.getGainType() == 2) {
                    //道具由成员贡献，计算行程损耗
                    Long userComponentId = componentMapper.getComponentUserUseLogById(component.getEffectId()).getUserComponentId();
                    //剩余行程数
                    travelNum = componentMapper.getComponentUser(userComponentId).getLeftTravelNum();
                } else {
                    travelNum = dicComponent.getTravelNum();
                }
                m1.put("componentLoss", travelNum + "次行程");
                m1.put("isCanCompound", "1");
                m1.put("damageDegree", travelNum+"/"+dicComponent.getTravelNum());
                Integer componentType = dicComponent.getComponentType();
                List<Map<String, Object>> list = paramMap.get(String.valueOf(componentType));

                if (list.size() == 0) {
                    list.add(m1);
                } else {
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).get("quality").equals(dicComponent.getQuality()) && Integer.valueOf(list.get(i).get("componentLoss").toString().substring(0, 1)) == travelNum) {
                            list.get(i).put("componentNum", String.valueOf(Integer.valueOf(list.get(i).get("componentNum").toString()) + 1));
                            List<String> ids1 = (List<String>) list.get(i).get("familyComponentIds");
                            ids1.add(component.getId().toString());
                            list.get(i).put("familyComponentIds", ids1);
                            break;
                        } else if (i == list.size() - 1) {
                            list.add(m1);
                            break;
                        }
                    }
                }
                // 排序--- flag 1-->2-->3
                Collections.sort(list, new Comparator<Map<String, Object>>() {
                    public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                        return Double.valueOf(o1.get("flag").toString()).compareTo(Double.valueOf(o2.get("flag").toString()));
                    }
                });
            }
        }
        rtMap.put("componentLT", componentLT);
        rtMap.put("componentJY", componentJY);
        rtMap.put("componentHHS", componentHHS);
        rtMap.put("componentSCP", componentSCP);
        rtMap.put("componentXDC", componentXDC);
        return rtMap;
    }

    public Map<String, Object> dropFamilyComponent(long familyComponentId) {
        Map<String, Object> rtMap = new HashMap<>();
        int dropCount = componentMapper.dropFamilyComponent(familyComponentId);
        if (dropCount == 0) {
            return null;
        }
        return rtMap;
    }

    //俱乐部零件分配提交
    @Transactional
    public int submitComponentDistribute(long familyComponentId, long familyId, long toUserId, long userId) {
        ComponentFamily cf = componentMapper.getFamilyComponent(familyComponentId);
        ComponentGiveLog log = new ComponentGiveLog();
        log.setComponentId(cf.getComponentId());
        log.setFromComponentId(familyComponentId);
        log.setFromId(cf.getFamilyId());
        log.setGiveStatus(1);
        log.setLogType(1);
        log.setOperationManId(userId);
        log.setToUserId(toUserId);
        log.setDaystamp(DateTools.getYYYYMMDD());
        log.setUniKey(log.getFromId() + "-" + log.getLogType() + "-" + log.getToUserId() + DateTools.getYYYYMMDD());
        componentMapper.insertComponentGiveLog(log);
        Map<String, Object> updateMap = new HashMap<>();
        updateMap.put("componentStatus", 3);
        updateMap.put("familyComponentId", familyComponentId);
        int update = componentMapper.updateFamilyComponent(updateMap);
        DicComponent dc = DicComponentConstant.getDicComponent(log.getComponentId());
        sendSysMsg(userId, toUserId, log.getId().longValue(), MessageEnum.ApplyGiveLog, null, dc.getComponentValue());
        //赠送配件加入活跃值业务
        livenessService.insertUserLivenessLog(userId, DicLivenessConstant.livenessId17);
        return update;
    }


    //分配列表
    public Map<String, Object> getComponentDistribute(long userId, long familyId) {
        Map<String, Object> rtMap = new HashMap<>();
        List<Map<String, Object>> users = familyMapper.getFamilyUsersMoreInfo(familyId);
        Map<Long, Object> keyMap = new HashMap<>();
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("familyId", familyId);
        paramMap.put("logType", 1);
        paramMap.put("eventTime", DateTools.getYYYY_MM_DD() + " 00:00:00");
        List<ComponentGiveLog> logs = componentMapper.getComponentGiveLog(paramMap);
        for (ComponentGiveLog log : logs) {
            keyMap.put(log.getToUserId(), log);
        }

        for (Map<String, Object> user : users) {//成员循环
            Map<String, Object> m = new HashMap<>();
            Long memberId = Long.valueOf(user.get("userId").toString());
            if (userId == memberId.longValue()) {
                continue;//自己忽略
            }
            m.put("userId", String.valueOf(memberId));
            m.put("nick", String.valueOf(user.get("nickName")));
            if (keyMap.get(memberId) == null) {
                m.put("numerator", "0");
                m.put("denominator", "1");
                m.put("authType", "1");

            } else {
                m.put("numerator", "1");
                m.put("denominator", "1");
                m.put("authType", "0");
            }
            list.add(m);

        }
        rtMap.put("members", list);
        return rtMap;
    }


    //分配列表
    public Map<String, Object> getComponentGiveLog(long componentGiveLogId, Long userId) {
        Map<String, Object> rtMap = new HashMap<>();
        ComponentGiveLog log = componentMapper.findComponentGiveLog(componentGiveLogId);
        if (log == null) {
            return rtMap;
        }

        rtMap.put("logType", String.valueOf(log.getLogType()));
        DicComponent dicComponent = DicComponentConstant.getDicComponent(log.getComponentId());
        rtMap.put("componentName", dicComponent.getComponentValue());
        rtMap.put("quality", dicComponent.getQuality());
        rtMap.put("imgUrl", dicComponent.getComponentUrl());
        rtMap.put("componentType", dicComponent.getComponentType());
        rtMap.put("componentDesc", dicComponent.getComponentDesc());
        rtMap.put("componentAttribute", "动力加成" + (int) (dicComponent.getPowerAddition() * 100) + "%");
        rtMap.put("componentLoss", dicComponent.getTravelNum() + "次行程");
        Integer status = log.getGiveStatus();
        Long toUserId = log.getToUserId();
        if (userId != toUserId) {
            if (log.getLogType() == 3 && (status == 1 || status == 2)) {
                status = -2;
            }
        }
        rtMap.put("giveStatus", String.valueOf(status));

        Long fromId = log.getFromId();
        long userIdA = 0L;
        if (log.getLogType() == 2) {
            userIdA = fromId;
        } else {
            userIdA = Long.valueOf(familyMapper.queryFamilyByFId(fromId).get("createUserId").toString());
        }

        String nickNameA = usersAccountMapper.findAccountById(userIdA).getNickName();
        String nickNameB = usersAccountMapper.findAccountById(toUserId).getNickName();
        if (log.getLogType() == 1) {
            rtMap.put("title", "零件分配");
            rtMap.put("desc", "发福利了!俱乐部老板" + nickNameA + "给您分配了一个 " + dicComponent.getComponentValue() + "(" + dicComponent.getQuality() + "级),快去看看吧");
        } else if (log.getLogType() == 2) {
            rtMap.put("title", "零件祈愿");
            rtMap.put("desc", nickNameA + " 在俱乐部祈愿中给您赠送了一个" + dicComponent.getComponentValue() + "(" + dicComponent.getQuality() + "级)!");
        } else if (log.getLogType() == 3) {
            if (userId == log.getToUserId()) {
                rtMap.put("title", "零件分配");
                rtMap.put("desc", "发福利了!俱乐部老板" + nickNameA + "给您分配了一个 " + dicComponent.getComponentValue() + "(" + dicComponent.getQuality() + "级),快去看看吧");
            } else {
                rtMap.put("title", "零件申请");
                rtMap.put("desc", "俱乐部成员 " + nickNameB + " 在零件库中申请领取一个" + dicComponent.getComponentValue() + "(" + dicComponent.getQuality() + "级),是否同意发放?");
            }

        }

        return rtMap;
    }

    //	  消息点击领取  (发放方已经销毁置位了，该领取只是对用户记录的增加)
    @Transactional
    public Map<String, Object> recieveGiveLog(long componentGiveLogId) {
        Map<String, Object> rtMap = new HashMap<>();
        ComponentGiveLog log = componentMapper.findComponentGiveLog(componentGiveLogId);
        ComponentUser componentUser = new ComponentUser();
        if (log.getLogType().intValue() == 1) {//俱乐部赠送
            componentUser.setGainType(3);
        } else if (log.getLogType().intValue() == 2) {//个人赠送
            componentUser.setGainType(2);
        } else if (log.getLogType().intValue() == 3) {//个人申请获取
            componentUser.setGainType(4);
        }
        componentUser.setComponentId(log.getComponentId());
        componentUser.setComponentStatus(1);
        DicComponent dicComponent = DicComponentConstant.getDicComponent(log.getComponentId());
        componentUser.setComponentType(dicComponent.getComponentType());

        componentUser.setInUse(0);
        if (log.getLogType().intValue() == 1||log.getLogType().intValue() == 3) {
            ComponentFamily componentFamily = componentMapper.getFamilyComponent(log.getFromComponentId());
            if (componentFamily.getGainType() == 2) {//由个人贡献的俱乐部配件
                //查找componentUser的id
                Long userComponentId = componentMapper.getComponentUserUseLogById(componentFamily.getEffectId()).getUserComponentId();
                //剩余行程数
                Integer leftTravelNum = componentMapper.getComponentUser(userComponentId).getLeftTravelNum();
                componentUser.setLeftTravelNum(leftTravelNum);
            } else {
                componentUser.setLeftTravelNum(dicComponent.getTravelNum());
            }

        }

        componentUser.setUserId(log.getToUserId());
        componentUser.setHadLooked(0);
        //插入道具
        componentMapper.insertComponentUser(componentUser);
        //更新ComponentGiveLog 为已领取
        componentMapper.recieveComponentGiveLog(componentGiveLogId);
        return rtMap;
    }

    @Transactional
    public Map<String, Object> deployComponent(long userComponentId, long userCarId, long userId) {
        ComponentUser componentUser = componentMapper.getComponentUser(userComponentId);
        //根据userId和type查询使用中的配件
        Integer componentType = DicComponentConstant.getDicComponent(componentUser.getComponentId()).getComponentType();
        List<ComponentUser> inUses = componentMapper.getUserComponentByTypeInUse(componentType, userId);
        //老的返回仓库
        if (inUses!=null) {
            for (ComponentUser inUse : inUses) {
                if (inUse!=null&&inUse.getId() > 0) {
                    Long destroyComponentId = inUse.getId();
                    ComponentUser componentUser2 = componentMapper.getComponentUser(destroyComponentId);
                    //插入componentUserUseLog
                    ComponentUserUseLog log2 = new ComponentUserUseLog();
                    log2.setUserCarId(0L);
                    log2.setComponentId(componentUser2.getComponentId());
                    log2.setEventType(7);
                    log2.setUserId(componentUser2.getUserId());
                    log2.setUserComponentId(userComponentId);
                    componentMapper.insertComponentUserUseLog(log2);

                    //更新零件
                    Map<String, Object> userCompUpdate2 = new HashMap<>();
                    userCompUpdate2.put("inUse", 0);
                    userCompUpdate2.put("componentStatus", 1);
                    userCompUpdate2.put("userComponentId", destroyComponentId);
                    componentMapper.updateUserComponent(userCompUpdate2);
                }
            }
        }

        //插入componentUserUseLog
        ComponentUserUseLog log = new ComponentUserUseLog();
        log.setUserCarId(userCarId);
        log.setComponentId(componentUser.getComponentId());
        log.setEventType(1);
        log.setUserId(componentUser.getUserId());
        log.setUserComponentId(userComponentId);
        componentMapper.insertComponentUserUseLog(log);

        //更新零件
        Map<String, Object> userCompUpdate = new HashMap<>();
        userCompUpdate.put("inUse", 1);
        userCompUpdate.put("componentStatus", 1);
        userCompUpdate.put("userComponentId", userComponentId);
        userCompUpdate.put("userCarId", userCarId);
        componentMapper.updateUserComponent(userCompUpdate);


        return null;
    }

    public Map<String, Object> getCompoundOdds(String componentIds) {
        Map<String, Object> rtMap = new HashMap<>();
        if (componentIds==null||componentIds.equals("")) {
            return ResultUtils.rtFailParam(null, "componentIds参数不能为空");
        }
        String[] ids = componentIds.split(",");
        if (ids.length!=3) {//目前合成材料长度为3个
            return ResultUtils.rtFailParam(null, "componentIds参数长度异常，日了狗了");
        }
        Integer odd_tmp=0;

        for (String componentId : ids) {
            ComponentFamily componentFamily = componentMapper.getFamilyComponent(Long.valueOf(componentId));
            DicComponent dicComponent = DicComponentConstant.getDicComponent(componentFamily.getComponentId());
            String quality = dicComponent.getQuality();
            Integer travelNum = 0;
            if (componentFamily.getGainType() == 2) {
                //道具由成员贡献，计算行程损耗
                Long userComponentId = componentMapper.getComponentUserUseLogById(componentFamily.getEffectId()).getUserComponentId();
                //剩余行程数
                travelNum = componentMapper.getComponentUser(userComponentId).getLeftTravelNum();
            } else {
                travelNum = dicComponent.getTravelNum();
            }
            odd_tmp+=compoundMapper.getOddByQualityAndTravelNum(quality, travelNum);
        }
        Integer odd_s = odd_tmp>100?100:odd_tmp;
        Integer odd_a = Long.valueOf(Math.round((100 - odd_s) * 0.7)).intValue();
        Integer odd_b = 100 - odd_s - odd_a;
        String [] arr={odd_b+"%",odd_a+"%",odd_s+"%"};
        rtMap.put("odds", arr);
        return rtMap;
    }

    @Transactional
    public Map<String, Object> compoundComponent(Long userId, String componentIds, Integer stoveId) {

        String[] ids = componentIds.split(",");
        if (ids.length!=3) {//目前合成材料长度为3个
            return ResultUtils.rtFailParam(null, "componentIds参数长度异常，日了狗了");
        }
        for (String componentId : ids) {
            componentMapper.dropFamilyComponentByCompound(Long.valueOf(componentId));
        }
        Long familyId = familyMapper.queryCreateFamilyId(userId);
        CompoundInfo compoundInfo = compoundMapper.getCompoundInfoByFamilyIdAndStoveId(familyId, stoveId);
        if (compoundInfo!=null) {
            return ResultUtils.rtFailParam(null, "请勿重复提交");
        }
        CompoundInfo info = new CompoundInfo();
        info.setFamilyId(familyId);
        info.setStoveId(stoveId);
        info.setUserId(userId);
        info.setComponentIds(componentIds);
        info.setStartTime(new Date());
        info.setEndTime(DateTools.getAddMinuteDateTime(new Date(),60*appProperties.getCompoundCoolHour().intValue()));
        info.setStatus(0);
        info.setFinalComponentId(0);
        info.setLookAdUserIds("");
        info.setLookAdCount(0);
        compoundMapper.insertCompoundInfo(info);
        //合成配件加入活跃值业务
        livenessService.insertUserLivenessLog(userId, DicLivenessConstant.livenessId16);
        return null;
    }

    @Transactional
    public Map<String, Object> queryCompoundStatus(Long userId, Long familyId, Integer stoveId) {
        Map<String, Object> rtMap = new HashMap<>();
        List<Map<String, Object>> componentList = new ArrayList<>();

        FamilyParamBean bean = new FamilyParamBean();
        bean.setFamilyId(familyId);
        Long createUserId = familyMapper.queryCreateUserId(bean);
        if (userId == createUserId) {
            rtMap.put("haveIcon", "1");
        } else {
            rtMap.put("haveIcon", "0");
        }
        CompoundInfo compoundInfo = compoundMapper.getCompoundInfoByFamilyIdAndStoveId(familyId, stoveId);
        if (compoundInfo == null) {
            return null;
        }

        String componentIds = compoundInfo.getComponentIds();
        String[] ids = componentIds.split(",");
        Date endTime = compoundInfo.getEndTime();
        long time = endTime.getTime()-new Date().getTime();
        time = time<0?0:time;
        if (time == 0) {
            rtMap.put("status", "1");
            //处理合成业务
            if (compoundInfo.getFinalComponentId() == 0) {
                String componentIdss = compoundInfo.getComponentIds();
                String[] idss = componentIdss.split(",");
                Integer odd_tmp=0;
                Integer finalComponentId=0;
                int type=0;
                for (String componentId : idss) {
                    ComponentFamily componentFamily = componentMapper.getFamilyComponent(Long.valueOf(componentId));
                    DicComponent dicComponent = DicComponentConstant.getDicComponent(componentFamily.getComponentId());
                    type = dicComponent.getComponentType();
                    String quality = dicComponent.getQuality();
                    Integer travelNum = 0;
                    if (componentFamily.getGainType() == 2) {
                        //道具由成员贡献，计算行程损耗
                        Long userComponentId = componentMapper.getComponentUserUseLogById(componentFamily.getEffectId()).getUserComponentId();
                        //剩余行程数
                        travelNum = componentMapper.getComponentUser(userComponentId).getLeftTravelNum();
                    } else {
                        travelNum = dicComponent.getTravelNum();
                    }
                    odd_tmp+=compoundMapper.getOddByQualityAndTravelNum(quality, travelNum);
                }
                Integer odd_s = odd_tmp>100?100:odd_tmp;
                Integer odd_a = Long.valueOf(Math.round((100 - odd_s) * 0.7)).intValue();
                Integer odd_b = 100 - odd_s - odd_a;
                int rand = RandUtils.generateRand(1, 100);
                if (rand <= odd_b) {
                    //b
                    finalComponentId = DicComponentConstant.dicComponentMapB.get(type);
                } else if (rand > odd_b && rand <= odd_a + odd_b) {
                    //a
                    finalComponentId = DicComponentConstant.dicComponentMapA.get(type);
                } else {
                    //s
                    finalComponentId = DicComponentConstant.dicComponentMapS.get(type);
                }

                compoundMapper.updateFinalComponentId(compoundInfo.getId(),finalComponentId);
            }
        } else {
            rtMap.put("status", "0");
        }
        for (String componentId : ids) {
            Map<String, Object> m1 = new HashMap<>();
            ComponentFamily componentFamily = componentMapper.getFamilyComponent(Long.valueOf(componentId));
            if (componentFamily!=null) {
                DicComponent dicComponent = DicComponentConstant.getDicComponent(componentFamily.getComponentId());
                Integer travelNum = 0;
                if (componentFamily.getGainType() == 2) {
                    //道具由成员贡献，计算行程损耗
                    Long userComponentId = componentMapper.getComponentUserUseLogById(componentFamily.getEffectId()).getUserComponentId();
                    //剩余行程数
                    travelNum = componentMapper.getComponentUser(userComponentId).getLeftTravelNum();
                } else {
                    travelNum = dicComponent.getTravelNum();
                }
                m1.put("quality",dicComponent.getQuality());
                m1.put("imgUrl",dicComponent.getComponentUrl());
                m1.put("travelNum",travelNum+"/"+dicComponent.getTravelNum());
                componentList.add(m1);
            }
        }
        CompoundInfo info = compoundMapper.getCompoundInfoByFamilyIdAndStoveId_KCache(familyId, stoveId);
        if (info.getStatus()>0&&info.getFinalComponentId()!=0) {
            DicComponent dicComponent = DicComponentConstant.getDicComponent(info.getFinalComponentId());
            Map<String, Object> m1 = new HashMap<>();
            m1.put("quality",dicComponent.getQuality());
            m1.put("imgUrl",dicComponent.getComponentUrl());
            m1.put("travelNum",dicComponent.getTravelNum()+"/"+dicComponent.getTravelNum());
            componentList.add(m1);
        }
        rtMap.put("leftTime", DurationFormatUtils.formatDuration(time, "HH小时mm分钟"));
        rtMap.put("componentList", componentList);

        String lookAdUserIds = info.getLookAdUserIds();
        if (lookAdUserIds.length() > 0) {
            String[] split = lookAdUserIds.split(",");
            List<String> list = Arrays.asList(split);
            if (list.contains(String.valueOf(userId))) {
                rtMap.put("canLookAd", "0");
                rtMap.put("desc", "");
            }
        } else {
            rtMap.put("canLookAd", "1");
            rtMap.put("desc", "时间太长？快去看视频吧！可以缩短时间哦~");
        }
        return rtMap;
    }

    @Transactional
    public Map<String, Object> reduceCompoundTime(Long userId, Long familyId, Integer stoveId) {
        CompoundInfo compoundInfo = compoundMapper.getCompoundInfoByFamilyIdAndStoveId(familyId, stoveId);
        CompoundInfo info = new CompoundInfo();
        info.setFamilyId(familyId);
        info.setStoveId(stoveId);
        String lookAdUserIds = compoundInfo.getLookAdUserIds();
        if (lookAdUserIds.equals("")) {
            lookAdUserIds +=userId;
        } else {
            lookAdUserIds += "," + userId;
        }
        info.setLookAdUserIds(lookAdUserIds);
        info.setLookAdCount(compoundInfo.getLookAdCount()+1);
        Date newEndTime = DateTools.getAddMinuteDateTime(compoundInfo.getEndTime(), (int)(-60.0 * appProperties.getAdReduceHour()));
        if (newEndTime.getTime()<new Date().getTime()) {
            newEndTime = new Date();
            compoundMapper.updateCompoundInfoStatus(1, familyId, stoveId, compoundInfo.getStatus());
        }
        info.setEndTime(newEndTime);
        compoundMapper.updateCompoundInfo(info);
        return null;
    }

    @Transactional
    public Map<String, Object> submitCompound(Long familyId, Integer stoveId) {

        CompoundInfo compoundInfo = compoundMapper.getCompoundInfoByFamilyIdAndStoveId(familyId, stoveId);
        if (compoundInfo==null) {
            return ResultUtils.rtFailParam(null, "请勿重复提交");
        }
        if (compoundInfo!=null&&compoundInfo.getStatus()==2) {
            return ResultUtils.rtFailParam(null, "请勿重复提交");
        }
        ComponentFamily c = new ComponentFamily();
        DicComponent dicComponent = DicComponentConstant.getDicComponent(compoundInfo.getFinalComponentId());
        c.setComponentId(dicComponent.getComponentId());
        c.setGainType(3);//配件合成
        c.setComponentType(dicComponent.getComponentType());
        c.setFamilyId(familyId);
        c.setComponentStatus(1);
        c.setEffectId(compoundInfo.getId());
        componentMapper.insertComponentFamily(c);

        compoundMapper.updateCompoundInfoStatus(2, familyId, stoveId, compoundInfo.getStatus());
        return null;
    }

    public Map<String, Object> queryStoveStatus(Long userId, Long familyId) {
        Map<String, Object> rtMap = new HashMap<>();
        List<Map<String, Object>> componentList = new ArrayList<>();
        int[] arr = {1,2,3};
        String[] status = new String[3];
        for (int i = 0; i < arr.length; i++) {
            Map<String, Object> m1 = new HashMap<>();
            CompoundInfo compoundInfo = compoundMapper.getCompoundInfoByFamilyIdAndStoveId(familyId, arr[i]);
            if (compoundInfo==null) {
                status[i] = "0";
            } else if (compoundInfo.getStatus()==0&&compoundInfo.getEndTime().getTime()>new Date().getTime()) {
                status[i] = "1";
            } else if (compoundInfo.getStatus()==1||(compoundInfo.getStatus()==0&&compoundInfo.getEndTime().getTime()<=new Date().getTime())) {
                status[i] = "2";
                //处理合成业务
                if (compoundInfo.getFinalComponentId() == 0) {
                    String componentIds = compoundInfo.getComponentIds();
                    String[] ids = componentIds.split(",");
                    Integer odd_tmp=0;
                    Integer finalComponentId=0;
                    int type=0;
                    for (String componentId : ids) {
                        ComponentFamily componentFamily = componentMapper.getFamilyComponent(Long.valueOf(componentId));
                        DicComponent dicComponent = DicComponentConstant.getDicComponent(componentFamily.getComponentId());
                        type = dicComponent.getComponentType();
                        String quality = dicComponent.getQuality();
                        Integer travelNum = 0;
                        if (componentFamily.getGainType() == 2) {
                            //道具由成员贡献，计算行程损耗
                            Long userComponentId = componentMapper.getComponentUserUseLogById(componentFamily.getEffectId()).getUserComponentId();
                            //剩余行程数
                            travelNum = componentMapper.getComponentUser(userComponentId).getLeftTravelNum();
                        } else {
                            travelNum = dicComponent.getTravelNum();
                        }
                        odd_tmp+=compoundMapper.getOddByQualityAndTravelNum(quality, travelNum);
                    }
                    Integer odd_s = odd_tmp>100?100:odd_tmp;
                    Integer odd_a = Long.valueOf(Math.round((100 - odd_s) * 0.7)).intValue();
                    Integer odd_b = 100 - odd_s - odd_a;
                    int rand = RandUtils.generateRand(1, 100);
                    if (rand <= odd_b) {
                        //b
                        finalComponentId = DicComponentConstant.dicComponentMapB.get(type);
                    } else if (rand > odd_b && rand <= odd_a + odd_b) {
                        //a
                        finalComponentId = DicComponentConstant.dicComponentMapA.get(type);
                    } else {
                        //s
                        finalComponentId = DicComponentConstant.dicComponentMapS.get(type);
                    }

                    compoundMapper.updateFinalComponentId(compoundInfo.getId(),finalComponentId);
                }
            }
        }
        rtMap.put("statusList", status);
        return rtMap;
    }


    final String jsonValue = "{\"userId\":%d,\"power\":%d,\"effectId\":%d}";

    @Transactional
    public Map<String, Object> sellComponent(long userComponentId, long userId) {
        ComponentUser componentUser = componentMapper.getComponentUser(userComponentId);
        if (componentUser == null) {
            return null;
        }
        //插入componentUserUseLog
        ComponentUserUseLog log = new ComponentUserUseLog();
        log.setUserCarId(0L);
        log.setComponentId(componentUser.getComponentId());
        log.setEventType(4);
        log.setUserId(componentUser.getUserId());
        log.setUserComponentId(userComponentId);
        componentMapper.insertComponentUserUseLog(log);

        //更新零件
        Map<String, Object> userCompUpdate = new HashMap<>();
        userCompUpdate.put("inUse", 0);
        userCompUpdate.put("componentStatus", 4);
        userCompUpdate.put("userComponentId", userComponentId);
        componentMapper.updateUserComponent(userCompUpdate);
        //获取奖励
        DicComponent dicComponent = DicComponentConstant.getDicComponent(componentUser.getComponentId());
        TaskPowerLogs taskPowerLogs = new TaskPowerLogs();
        taskPowerLogs.setUserId(userId);
        taskPowerLogs.setTaskType(PowerEnum.SellComponent);
        int power = dicComponent.getPower();
        //type =1  行程动力
        taskPowerLogs.setJsonValue(String.format(jsonValue, userId, power, userComponentId));
        int hadAdd = taskPowerLogsMapper.insertTaskPowerLogs(taskPowerLogs);
        if (hadAdd > 0) {
            //贡献配件加入活跃值业务
            livenessService.insertUserLivenessLog(userId, DicLivenessConstant.livenessId13);
            return new HashMap<>();
        }
        return null;
    }

    final String jsonValue2 = "{\"userId\":%d,\"power\":%d,\"effectId\":%d}";

    @Transactional
    public Map<String, Object> contributeComponent(long userComponentId, long userId, long familyId) {
        //Long joinFamilyId = familyMapper.queryJoinFamilyId(userId);
        //if (joinFamilyId==null||joinFamilyId.longValue()==0) {
        //	return null;
        //}
        ComponentUser componentUser = componentMapper.getComponentUser(userComponentId);
        if (componentUser == null) {
            return null;
        }
        //插入componentUserUseLog
        ComponentUserUseLog log = new ComponentUserUseLog();
        log.setUserCarId(0L);
        log.setComponentId(componentUser.getComponentId());
        log.setEventType(6);
        log.setUserId(componentUser.getUserId());
        log.setUserComponentId(userComponentId);
        componentMapper.insertComponentUserUseLog(log);

        //更新零件
        Map<String, Object> userCompUpdate = new HashMap<>();
        userCompUpdate.put("inUse", 0);
        userCompUpdate.put("componentStatus", 5);
        userCompUpdate.put("userComponentId", userComponentId);
        componentMapper.updateUserComponent(userCompUpdate);

        ComponentFamily c = new ComponentFamily();
        c.setComponentId(componentUser.getComponentId());
        c.setGainType(2);
        c.setComponentType(componentUser.getComponentType());
        c.setFamilyId(familyId);
        c.setComponentStatus(1);
        c.setEffectId(log.getId());
        componentMapper.insertComponentFamily(c);
        return new HashMap<>();

    }

    public Map<String, Object> listPraying(long userId, String imgBase) {

        Map<String, Object> rtMap = new HashMap<>();
        List<Map<String, Object>> prayingList = new ArrayList<>();
        List<Map<String, Object>> list = familyMapper.getFamilyByUserId(userId);
        Map<Long, Object> usersKey = new HashMap<>();

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("daystamp", DateTools.getYYYYMMDD());
        Map<String, Object> keyMap = new HashMap<>();
        List<Map<String, Object>> groupNum = componentMapper.getFreeComponentUserGroupType(userId);
        for (Map<String, Object> freeComp : groupNum) {
            keyMap.put(String.valueOf(freeComp.get("componentType")), freeComp.get("hadNum"));
        }

        for (Map<String, Object> map : list) {//俱乐部循环
            Long familyId = Long.valueOf(map.get("familyId").toString());
            //获取俱乐部成员列表
            List<Map<String, Object>> users = familyMapper.getFamilyUsersMoreInfo(familyId);
            for (Map<String, Object> user : users) {//成员循环
                Long memberId = Long.valueOf(user.get("userId").toString());
                paramMap.put("userId", memberId);
                ComponentGiveLog componentGiveLog = componentMapper.findComponentGiveLogByMap(paramMap);
                if (usersKey.get(memberId) != null) {
                    continue;
                }
                usersKey.put(memberId, "1");
                if (componentGiveLog == null) {
                    continue;
                } else {
                    Map<String, Object> m = new HashMap<>();
                    m.put("componentGiveLogId", String.valueOf(componentGiveLog.getId()));
                    m.put("userId", String.valueOf(memberId));
                    m.put("logType", "2");
                    m.put("headImg", imgBase + user.get("imgUrl"));
                    m.put("nick", user.get("nickName"));
                    m.put("time", componentGiveLog.getEventTime().getTime());

                    DicComponent dicComponent = DicComponentConstant.getDicComponent(componentGiveLog.getComponentId());
                    m.put("imgUrl", dicComponent.getComponentUrl());
                    int hadNum = 0;
                    if (keyMap.get(dicComponent.getComponentType().toString()) != null) {
                        hadNum = Integer.valueOf(keyMap.get(dicComponent.getComponentType().toString()).toString());
                    }
                    m.put("hadNum", hadNum);

                    //0：自己	1：无多余零件	2：已受赠	3：可赠送
                    if (memberId.longValue() == userId) {
                        m.put("showGiven", "0");
                    } else if (hadNum == 0) {
                        m.put("showGiven", "1");
                    } else {
                        m.put("showGiven", "3");
                    }
                    if (componentGiveLog.getGiveStatus() == 1 || componentGiveLog.getGiveStatus() == 2) {
                        m.put("showGiven", "2");
                    }

                    prayingList.add(m);
                }
            }
        }
        // 排序--- flag 1-->2-->3
        Collections.sort(prayingList, new Comparator<Map<String, Object>>() {
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                return Double.valueOf(o2.get("time").toString()).compareTo(Double.valueOf(o1.get("time").toString()));
            }
        });
        int i = componentMapper.countOfPray(userId);
        rtMap.put("isCanPray", i > 0 ? "0" : "1");
        rtMap.put("prayingList", prayingList);
        return rtMap;
    }

    @Transactional
    public int submitPraying(Integer componentId, Long userId, String nick) {
        componentMapper.updateGiveLogStatus(userId);
        DicComponent dicComponent = DicComponentConstant.getDicComponent(componentId);
        ComponentGiveLog giveLog = new ComponentGiveLog();
        giveLog.setComponentId(dicComponent.getComponentId());//祈愿只需要类型
        giveLog.setFromComponentId(0L);//还未发放
        giveLog.setFromId(0L);//请求发放的用户未知。
        giveLog.setGiveStatus(0);//申请中
        giveLog.setLogType(2);//个人祈愿
        giveLog.setOperationManId(0L);//发配件人未知
        giveLog.setToUserId(userId);
        giveLog.setDaystamp(DateTools.getYYYYMMDD());
//          logType-toUserId-daystamp-giveStatus
        giveLog.setUniKey(giveLog.getLogType() + "-" + userId + "-" + giveLog.getDaystamp() + "-" + 0);//一天只能祈愿一次
        int insert = componentMapper.insertComponentGiveLog(giveLog);
        if (insert > 0) {
            Map<String, String> bean = new HashMap<>();
            bean.put("userId", String.valueOf(userId));
            bean.put("toUser", nick);
            bean.put("infoTitle", "正在祈愿");
            bean.put("infoImgUrl", dicComponent.getComponentUrl());
            bean.put("infoDesc", "");
            bean.put("aTitle", "资助");
            Gson gson = new Gson();
            String aInfo = gson.toJson(bean);

            chezuImService.prayingSubmit("", nick, String.valueOf(userId), dicComponent.getComponentValue(), aInfo, "");

            //发布祈愿加入活跃值业务
            livenessService.insertUserLivenessLog(userId, DicLivenessConstant.livenessId12);
        }
        return insert;
    }

    @Transactional
    public ReturnMessage requestComponent(Long familyComponentId, Long userId, String nickName) {
        ReturnMessage msg = new ReturnMessage();
        int i = componentMapper.countHadRequest(userId, DateTools.getYYYYMMDD());
        if (i > 0) {
            msg.setStatus(0);
            msg.setMsg("今天已分配");
            return msg;
        }
        ComponentFamily cf = componentMapper.getFamilyComponent(familyComponentId);
        if (cf.getComponentStatus() != 1) {
            msg.setStatus(0);
            msg.setMsg("此零件已经被送出");
            return msg;
            //此零件已经被送出
        }
        ComponentGiveLog giveLog = new ComponentGiveLog();
        giveLog.setComponentId(cf.getComponentId());//
        giveLog.setFromComponentId(cf.getId());//
        giveLog.setFromId(cf.getFamilyId());//请求发放的用户未知。
        giveLog.setGiveStatus(0);//申请中
        giveLog.setLogType(3);//个人申请俱乐部零件库
        giveLog.setOperationManId(0L);//发配件人未知
        giveLog.setToUserId(userId);
        giveLog.setDaystamp(DateTools.getYYYYMMDD());
//          logType-toUserId-daystamp-giveStatus[familyComponentId]
        giveLog.setUniKey(giveLog.getLogType() + "-" + userId + "-" + giveLog.getDaystamp() + "-" + familyComponentId);//一天只能祈愿一次
        int insert = componentMapper.insertComponentGiveLog(giveLog);
        if (insert == 0) {
            msg.setStatus(0);
            msg.setMsg("此零件已经申请过");
            return msg;
        }
        //发送消息
        DicComponent dc = DicComponentConstant.getDicComponent(cf.getComponentId());
        Long toUserId = familyMapper.getLeaderIdByFamilyId(cf.getFamilyId());
        sendSysMsg(userId, toUserId, giveLog.getId(), MessageEnum.RequestComponent, nickName, dc.getComponentValue() + "(" + dc.getQuality() + ")");

        return msg;
    }

    //个人申请零件库的审批
    @Transactional
    public ReturnMessage applyGiveLog(long componentGiveLogId, int clickEvent, long userId) {
        ReturnMessage msg = new ReturnMessage();
        ComponentGiveLog log = componentMapper.findComponentGiveLog(componentGiveLogId);
        if (clickEvent == 1) {//忽略
            log.setGiveStatus(-1);
            log.setOperationManId(userId);
            componentMapper.updateComponentGiveLog(log);
        } else {
            int i = componentMapper.countHadRequest(log.getToUserId(), log.getDaystamp());
            if (i > 0) {
                msg.setStatus(0);
                msg.setMsg("该申请已过期");
                return msg;
            }
            //到componentFamily 查询零件是否存在
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("componentStatus", 4);
            paramMap.put("familyComponentId", log.getFromComponentId());
            int updateCompFamily = componentMapper.gotFamilyComponent(paramMap);
            if (updateCompFamily > 0) {
                log.setGiveStatus(1);
                log.setOperationManId(userId);
                componentMapper.updateComponentGiveLog(log);

                //将该道具的其他的消息置位为忽略
                log.setGiveStatus(-1);
                log.setFromComponentId(log.getFromComponentId());
                componentMapper.ignoreGiveLog(log);

            } else {
                msg.setStatus(0);
                msg.setMsg("道具已经被别人申领");
                return msg;
            }
        }
        DicComponent dc = DicComponentConstant.getDicComponent(log.getComponentId());
        Long fromId = log.getFromId();
        long userIdA = Long.valueOf(familyMapper.queryFamilyByFId(fromId).get("createUserId").toString());
        sendSysMsg(userIdA, log.getToUserId(), componentGiveLogId, MessageEnum.ApplyGiveLog,
                null, dc.getComponentValue());
        return msg;
    }


    public Map<String, Object> prayingSelect(long userId, long componentGiveLogId) {
        ComponentGiveLog log = componentMapper.findComponentGiveLog(componentGiveLogId);
        Map<String, Object> rtMap = new HashMap<>();
        Map<String, Object> paramMap = new HashMap<>();
        List<Map<String, Object>> componentList = new ArrayList<>();
        int componentType = DicComponentConstant.getDicComponent(log.getComponentId()).getComponentType();
        paramMap.put("userId", userId);
        paramMap.put("componentType", componentType);
        List<ComponentUser> components = componentMapper.getFreeComponentTypeUser(paramMap);

        if (components == null || components.size() == 0) {

        } else {
            for (ComponentUser component : components) {
                Map<String, Object> m1 = new HashMap<>();
                m1.put("userComponentId", String.valueOf(component.getId()));
                DicComponent dicComponent = DicComponentConstant.getDicComponent(component.getComponentId());
                m1.put("componentName", dicComponent.getComponentValue());
                m1.put("quality", dicComponent.getQuality());
                m1.put("imgUrl", dicComponent.getComponentUrl());
                m1.put("componentNum", "1");
                m1.put("componentType", dicComponent.getComponentType());
                m1.put("componentDesc", dicComponent.getComponentDesc());
                m1.put("componentAttribute", "动力加成" + (int) (dicComponent.getPowerAddition() * 100) + "%");
                m1.put("componentLoss", dicComponent.getTravelNum() + "次行程");
                componentList.add(m1);
            }
        }
        rtMap.put("componentList", componentList);
        return rtMap;
    }

    //个人祈愿审批通过
    @Transactional
    public ReturnMessage applyPraying(long componentGiveLogId, long userComponentId, long userId, String nickName) {
        ReturnMessage msg = new ReturnMessage();
        ComponentGiveLog log = componentMapper.findComponentGiveLog(componentGiveLogId);

        //通过类型来给予用户锁定发放
//		  Map<String,Object> map=new HashMap<>();
//		  map.put("componentType", dicComponent.getComponentType());
//		  map.put("userId",userId);
        ComponentUser cmpUser = componentMapper.getComponentUser(userComponentId);
        if (cmpUser == null || cmpUser.getComponentStatus() > 1 || cmpUser.getInUse() == 1) {
            msg.setStatus(0);
            msg.setMsg("零件使用中或者已销毁");
            return msg;
        }
        //更新销毁零件
        int hadGot = componentMapper.gotUserComponent(cmpUser);
        if (hadGot == 0) {
            msg.setStatus(0);
            msg.setMsg("无闲置的零件");
            return msg;
        }
        //插入销毁日志
        ComponentUserUseLog useLog = new ComponentUserUseLog();
        useLog.setUserCarId(0L);
        useLog.setComponentId(cmpUser.getComponentId());
        useLog.setEventType(5);
        useLog.setUserId(cmpUser.getUserId());
        useLog.setUserComponentId(cmpUser.getId());
        componentMapper.insertComponentUserUseLog(useLog);

        //更新giveLog，并发送消息
        log.setGiveStatus(1);
        log.setOperationManId(userId);
        log.setComponentId(cmpUser.getComponentId());
        log.setFromComponentId(cmpUser.getId());
        log.setFromId(cmpUser.getUserId());
        log.setGiveStatus(1);
        log.setOperationManId(cmpUser.getUserId());
        componentMapper.updateComponentGiveLogApplyPraying(log);

        //发送消息
        DicComponent dicComponent = DicComponentConstant.getDicComponent(useLog.getComponentId());
        sendSysMsg(userId, log.getToUserId(), componentGiveLogId, MessageEnum.ApplyPraying,
                nickName, dicComponent.getComponentValue());
        //发放奖励
        int power = RandUtils.generateRand(10, 50);
        TaskPowerLogs taskPowerLogs = new TaskPowerLogs();
        taskPowerLogs.setUserId(userId);
        taskPowerLogs.setTaskType(PowerEnum.ApplyPraying);
        //type =1  行程动力
        taskPowerLogs.setJsonValue(String.format(jsonValue, userId, power, cmpUser.getId()));
        int hadAdd = taskPowerLogsMapper.insertTaskPowerLogs(taskPowerLogs);
        msg.getData().put("power", String.valueOf(power));
        String toUserNickName = usersAccountMapper.findAccountById(log.getToUserId()).getNickName();
        chezuImService.prayingRealize(nickName, toUserNickName, String.valueOf(log.getToUserId()), dicComponent.getComponentValue(), "");

        //满足祈愿加入活跃值业务
        livenessService.insertUserLivenessLog(userId, DicLivenessConstant.livenessId11);
        return msg;
    }

    private void sendSysMsg(Long fromUser, Long toUser,
                            Long componentGiveLogId, MessageEnum msgType, String nickName, String componentName) {
        switch (msgType) {
            case ApplyPraying:
                Message msg = messageService.buildApplyPrayingMessage(fromUser, toUser, nickName, componentName, componentGiveLogId);
                messageService.insertMessage(msg, MessageEnum.ApplyPraying);
                messageService.pushMessageNotrans(msg, MessageEnum.ApplyPraying);
                break;
            case RequestComponent:
                Message msg2 = messageService.buildRequestComponentMessage(fromUser, toUser, nickName, componentName, componentGiveLogId);
                messageService.insertMessage(msg2, MessageEnum.RequestComponent);
                messageService.pushMessageNotrans(msg2, MessageEnum.RequestComponent);
                break;
            case ApplyGiveLog:
                Message msg3 = messageService.buildApplyGiveLogMessage(fromUser, toUser, componentName, componentGiveLogId);
                messageService.insertMessage(msg3, MessageEnum.ApplyGiveLog);
                messageService.pushMessageNotrans(msg3, MessageEnum.ApplyGiveLog);
                break;
            default:
                break;
        }
    }

}
