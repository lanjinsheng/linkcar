package com.idata365.app.partnerApi;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.idata365.app.config.SystemProperties;

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
@Component
public class ManagePushApi {
    @Autowired
	SystemProperties systemProperties;
    private static Logger logger = Logger.getLogger(ManagePushApi.class);
 
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
     * 对所有用户进行推送
     * @param msg   需要推送的消息
     * @param type  app点击通知，跳转的页面
     */
    public   void SendMsgToAll(String msg,Map<String,String> extraMap,String platform) throws Exception{
        ClientConfig.getInstance().setMaxRetryTimes(3);
        JPushClient jpushClient = new JPushClient(systemProperties.getJgSecret(),systemProperties.getJgAppKey());
        PushPayload payload = BuildMsgAll(msg, platform, extraMap);
        jpushClient.sendPush(payload);
    }

    /**
     * 针对个人推送信息
     * @param message   需要推送的消息
     * @param alias    用户alias
     * @param platform  平台
     * @param type      app点击通知，跳转的页面
     */
    public   void SendMsgToOne(String message,String alias,String platform,Integer type){
        try {
            ClientConfig.getInstance().setMaxRetryTimes(3);
            JPushClient jpushClient = new JPushClient( systemProperties.getJgSecret(),systemProperties.getJgAppKey());
            PushPayload payload = BuildMsgAlias(message, alias, platform, type);
            jpushClient.sendPush(payload);
        }catch (Exception e){
            //原因是：推送必须是在app集成了sdk，用户登录过app之后，才能进行推送。若用户没有使用最新版本登录过，推送就会出错
            logger.info("推送失败-------------------------------");
        }
    }
    
    /**
     * 针对个人推送信息
     * @param message   需要推送的消息
     * @param alias    用户alias
     * @param platform  平台
     * @param extraMap   extra 集合
     */
    public   void SendMsgToOne(String message,String alias,String platform,Map<String,String> extraMap){
        try {
        	String appKey=systemProperties.getJgAppKey();
        	String secret=systemProperties.getJgSecret();
//        	logger.info(appKey+"---"+secret);
            JPushClient jpushClient = new JPushClient(systemProperties.getJgSecret(), systemProperties.getJgAppKey());
            PushPayload payload = BuildMsgAlias(message, alias, platform, extraMap);
            jpushClient.sendPush(payload);
        }catch (Exception e){
            //原因是：推送必须是在app集成了sdk，用户登录过app之后，才能进行推送。若用户没有使用最新版本登录过，推送就会出错
            e.printStackTrace();
        	logger.info("推送失败-------------------------------");
        }
    }

    /**
     * 极光推送参数组合
     * @param message   消息主题
     * @param userId    alias推送，alias=alias
     * @param platform  平台:android,ios,all
     * @param type      app点击通知，跳转的页面
     * @return          组合好的消息体
     */
    private   PushPayload BuildMsgAlias(String message,String alias,String platform,Integer type){
        if (platform.equals(PLATFORM_ANDROID)) {
            return PushPayload.newBuilder()
                    .setPlatform(Platform.android())
                    .setAudience(Audience.newBuilder()
                            .addAudienceTarget(AudienceTarget.alias(alias)).build())
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
                            .addAudienceTarget(AudienceTarget.alias(alias)).build())
                    .setNotification(Notification.newBuilder()
                            .addPlatformNotification(IosNotification.newBuilder()
                                    .setAlert(message)
                                    .addExtra("type",type)
                                    .setSound("default")
                                    .build())
                            .build())
                    //使用最新的推送接口，ios开发环境和生产环境设置必须通过接口参数来确定，ApnsProduction true：生产环境，false：开发环境
                    .setOptions(Options.newBuilder().setApnsProduction(Boolean.valueOf(systemProperties.getProduct())).build())
                    .build();
        }
        return null;
    }
    
    
    /**
     * 极光推送参数组合
     * @param message   消息主题
     * @param platform   all
     * @param type      app点击通知，跳转的页面
     * @return          组合好的消息体
     */
    private   PushPayload BuildMsgAll_noUse(String message,String platform,Integer type){
       if(platform.equals(PLATFORM_ALL_ANDROID)){
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
                .setOptions(Options.newBuilder().setApnsProduction(Boolean.valueOf(systemProperties.getProduct())).build())
                .build();
      }
    }
    
    
    
    /**
     * 极光推送参数组合
     * @param message   消息主题
     * @param userId    alias推送，alias=alias
     * @param platform  平台:android,ios,all
     * @param extraMap      extra 集合
     * @return          组合好的消息体
     */
    private     PushPayload BuildMsgAlias(String message,String alias,String platform,Map<String,String> extraMap){
        if (platform.equals(PLATFORM_ANDROID)) {
            return PushPayload.newBuilder()
                    .setPlatform(Platform.android())
                    .setAudience(Audience.newBuilder()
                            .addAudienceTarget(AudienceTarget.alias(alias)).build())
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
                            .addAudienceTarget(AudienceTarget.alias(alias)).build())
                    .setNotification(Notification.newBuilder()
                            .addPlatformNotification(IosNotification.newBuilder()
                                    .setAlert(message)
                                    .addExtras(extraMap)
                                    .setSound("default")
                                    .build())
                            .build())
                    //使用最新的推送接口，ios开发环境和生产环境设置必须通过接口参数来确定，ApnsProduction true：生产环境，false：开发环境
                    .setOptions(Options.newBuilder().setApnsProduction(Boolean.valueOf(systemProperties.getProduct())).build())
                    .build();
        } 
        return null;
    }
    /**
     * 极光推送参数组合
     * @param message   消息主题
     * @param platform  平台:android,ios,all
     * @param extraMap      extra 集合
     * @return          组合好的消息体
     */
    private   PushPayload BuildMsgAll(String message,String platform,Map<String,String> extraMap){
        if(platform.equals(PLATFORM_ALL_ANDROID)){
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
                    .setOptions(Options.newBuilder().setApnsProduction(Boolean.valueOf(systemProperties.getProduct())).build())
                    .build();
        }
    }

}
