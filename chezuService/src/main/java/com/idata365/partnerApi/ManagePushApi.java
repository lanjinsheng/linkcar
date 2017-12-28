package com.idata365.partnerApi;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.jiguang.common.ClientConfig;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

/**
 * 极光推送服务端，相关处理
 * 极光官网:https://www.jpush.cn/
 */
public class ManagePushApi {

    private static Logger logger = Logger.getLogger(ManagePushApi.class);

    /**
     * 极光推送使用，需要的基本信息
     */
    private static final String appKey="e5c3638bbf5901cbd3895969";
    private static final String secret="801f707e8580d906f8ee1e6c";
    /**
     * 点击通知，跳转到车险支付
     */
    public static final Integer PUSH_TYPE_PAY=0;
    public static final String  PUSH_TYPE_PAY_STRING="您的订单已经精准报价，请付款";

    /**
     * 点击通知，跳转到行车历史
     */
    public static final Integer PUSH_TYPE_MILE=1;

    /**
     * 点击通知，跳转到通知页面
     */
    public static final Integer PUSH_TYPE_NOTI=2;

    /**
     * 点击通知，跳转到办理加油卡
     */
    public static final Integer PUSH_TYPE_GAS_INSERT=3;

    /**
     * 点击通知，跳转到加油充值
     */
    public static final Integer PUSH_TYPE_GAS_UPDATE=4;

    /**
     * 点击通知，跳转到加油充值记录
     */
    public static final Integer PUSH_TYPE_GAS_SELECT=5;

    /**
     * 点击通知，跳转到优惠券
     */
    public static final Integer PUSH_TYPE_COUPON=6;

    /**
     * 点击通知，跳转到推广提现
     */
    public static final Integer PUSH_TYPE_MONEY=7;

    /**
     * 点击通知，跳转到快修
     */
    public static final Integer PUSH_TYPE_QUERY_REPAIR=8;
    public static final String PUSH_TPPE_QUERY_STRING_MSG="尊敬的用户，修博士已为您的车损咨询提出一份初步建议！";
    public static final String PUSH_TPPE_QUERY_STRING_PRICE="尊敬的用户，手机车宝客服已针对您的车损咨询提出一份精准报价！";
    
    /**
     * 点击通知，跳转到行程地图页面
     */
    public static final Integer PUSH_TYPE_DRIVERMAP=9;

    /**
     * ios平台
     */
    public static final String PLATFORM_IOS = "ios";

    /**
     * android平台
     */
    public static final String PLATFORM_ANDROID = "android";

    /**
     * 所有平台
     */
    public static final String PLATFORM_ALL_ANDROID = "all_android";
    public static final String PLATFORM_ALL_IOS = "all_IOS";

    /**
     * 极光推送，ios平台   生产模式：true 测试模式：false
     */
    private static final Boolean IOS_MODEL = false;

    /**
     * 对所有用户进行推送
     * @param msg   需要推送的消息
     * @param type  app点击通知，跳转的页面
     */
    public static void SendMsgToAll(String msg,Integer type,String platform) throws Exception{
        ClientConfig.getInstance().setMaxRetryTimes(3);
        JPushClient jpushClient = new JPushClient(secret, appKey);
        PushPayload payload = BuildMsg(msg, null, platform, type);
        jpushClient.sendPush(payload);
    }

    /**
     * 针对个人推送信息
     * @param message   需要推送的消息
     * @param userId    用户id
     * @param platform  平台
     * @param type      app点击通知，跳转的页面
     */
    public static void SendMsgToOne(String message,String userId,String platform,Integer type){
        try {
            ClientConfig.getInstance().setMaxRetryTimes(3);
            JPushClient jpushClient = new JPushClient(secret, appKey);
            PushPayload payload = BuildMsg(message, userId, platform, type);
            jpushClient.sendPush(payload);
        }catch (Exception e){
            //原因是：推送必须是在app集成了sdk，用户登录过app之后，才能进行推送。若用户没有使用最新版本登录过，推送就会出错
            logger.info("推送失败-------------------------------");
        }
    }
    
    /**
     * 针对个人推送信息
     * @param message   需要推送的消息
     * @param userId    用户id
     * @param platform  平台
     * @param extraMap   extra 集合
     */
    public static void SendMsgToOne(String message,String userId,String platform,Map<String,String> extraMap){
        try {
            JPushClient jpushClient = new JPushClient(secret, appKey);
            PushPayload payload = BuildMsg(message, userId, platform, extraMap);
            jpushClient.sendPush(payload);
        }catch (Exception e){
            //原因是：推送必须是在app集成了sdk，用户登录过app之后，才能进行推送。若用户没有使用最新版本登录过，推送就会出错
            logger.info("推送失败-------------------------------");
        }
    }

    /**
     * 极光推送参数组合
     * @param message   消息主题
     * @param userId    tag推送，tag=userId
     * @param platform  平台:android,ios,all
     * @param type      app点击通知，跳转的页面
     * @return          组合好的消息体
     */
    private static PushPayload BuildMsg(String message,String userId,String platform,Integer type){
        if (platform.equals(PLATFORM_ANDROID)) {
            return PushPayload.newBuilder()
                    .setPlatform(Platform.android())
                    .setAudience(Audience.newBuilder()
                            .addAudienceTarget(AudienceTarget.tag(userId)).build())
                    .setNotification(Notification.newBuilder()
                            .addPlatformNotification(AndroidNotification.newBuilder()
                                .setAlert(message)
                                .addExtra("type",type)
                                .build())
                            .build())
                    .build();
        }else if(platform.equals(PLATFORM_IOS)){
            return PushPayload.newBuilder()
                    .setPlatform(Platform.ios())
                    .setAudience(Audience.newBuilder()
                            .addAudienceTarget(AudienceTarget.tag(userId)).build())
                    .setNotification(Notification.newBuilder()
                            .addPlatformNotification(IosNotification.newBuilder()
                                    .setAlert(message)
                                    .addExtra("type",type)
                                    .setSound("default")
                                    .build())
                            .build())
                    //使用最新的推送接口，ios开发环境和生产环境设置必须通过接口参数来确定，ApnsProduction true：生产环境，false：开发环境
                    .setOptions(Options.newBuilder().setApnsProduction(IOS_MODEL).build())
                    .build();
        }else if(platform.equals(PLATFORM_ALL_ANDROID)){
            return PushPayload.newBuilder()
                    .setPlatform(Platform.android())
                    .setAudience(Audience.all())
                    .setNotification(Notification.newBuilder()
                            .addPlatformNotification(AndroidNotification.newBuilder()
                                .setAlert(message)
                                .addExtra("type",type)
                                .build())
                            .build())
                    .build();
        }else{
            return PushPayload.newBuilder()
                    .setPlatform(Platform.ios())
                    .setAudience(Audience.all())
                    .setNotification(Notification.newBuilder()
                            .addPlatformNotification(IosNotification.newBuilder()
                                    .setAlert(message)
                                    .addExtra("type",type)
                                    .setSound("default")
                                    .build())
                            .build())
                    //使用最新的推送接口，ios开发环境和生产环境设置必须通过接口参数来确定，ApnsProduction true：生产环境，false：开发环境
                    .setOptions(Options.newBuilder().setApnsProduction(IOS_MODEL).build())
                    .build();
        }
    }
    
    /**
     * 极光推送参数组合
     * @param message   消息主题
     * @param userId    tag推送，tag=userId
     * @param platform  平台:android,ios,all
     * @param extraMap      extra 集合
     * @return          组合好的消息体
     */
    private static PushPayload BuildMsg(String message,String userId,String platform,Map<String,String> extraMap){
        if (platform.equals(PLATFORM_ANDROID)) {
            return PushPayload.newBuilder()
                    .setPlatform(Platform.android())
                    .setAudience(Audience.newBuilder()
                            .addAudienceTarget(AudienceTarget.tag(userId)).build())
                    .setNotification(Notification.newBuilder()
                            .addPlatformNotification(AndroidNotification.newBuilder()
                                .setAlert(message)
                                .addExtras(extraMap)
                                .build())
                            .build())
                    .build();
        }else if(platform.equals(PLATFORM_IOS)){
            return PushPayload.newBuilder()
                    .setPlatform(Platform.ios())
                    .setAudience(Audience.newBuilder()
                            .addAudienceTarget(AudienceTarget.alias(userId)).build())
                    .setNotification(Notification.newBuilder()
                            .addPlatformNotification(IosNotification.newBuilder()
                                    .setAlert(message)
                                    .addExtras(extraMap)
                                    .setSound("default")
                                    .build())
                            .build())
                    //使用最新的推送接口，ios开发环境和生产环境设置必须通过接口参数来确定，ApnsProduction true：生产环境，false：开发环境
                    .setOptions(Options.newBuilder().setApnsProduction(IOS_MODEL).build())
                    .build();
        }else if(platform.equals(PLATFORM_ALL_ANDROID)){
            return PushPayload.newBuilder()
                    .setPlatform(Platform.android())
                    .setAudience(Audience.all())
                    .setNotification(Notification.newBuilder()
                            .addPlatformNotification(AndroidNotification.newBuilder()
                                .setAlert(message)
                                 .addExtras(extraMap)
                                .build())
                            .build())
                    .build();
        }else{
            return PushPayload.newBuilder()
                    .setPlatform(Platform.ios())
                    .setAudience(Audience.all())
                    .setNotification(Notification.newBuilder()
                            .addPlatformNotification(IosNotification.newBuilder()
                                    .setAlert(message)
                                   .addExtras(extraMap)
                                    .setSound("default")
                                    .build())
                            .build())
                    //使用最新的推送接口，ios开发环境和生产环境设置必须通过接口参数来确定，ApnsProduction true：生产环境，false：开发环境
                    .setOptions(Options.newBuilder().setApnsProduction(IOS_MODEL).build())
                    .build();
        }
    }

    public static void main(String args[]) throws Exception {

        String msg = "您的本次行程结束，用时8分钟，里程2.52公里，驾驶评分：97.0";
        Map<String,String> extraMap = new HashMap<String, String>();
        extraMap.put("type", "9");
        extraMap.put("url_type", "1");
        extraMap.put("url", "CBModule://DrivingDetail?json={\"start_time\":\"2016-09-09 09:14:46\",\"end_time\":\"2016-09-09 09:23:36\"}");
        SendMsgToOne(msg,"17_0",PLATFORM_IOS,extraMap);
    }

}
