package com.idata365.app.constant;

import com.idata365.app.entity.v2.DicLiveness;
import com.idata365.app.service.DicService;
import com.idata365.app.service.SpringContextUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DicLivenessConstant {
    public static Map<Integer, DicLiveness> dicLivenessMap = new HashMap<Integer, DicLiveness>();
    public final static Integer livenessId1 = 1;//登录  10
    public final static Integer livenessId2 = 2;//分享链车  30
    public final static Integer livenessId3 = 3;//擦车1次  5
    public final static Integer livenessId4 = 4;//完成视频任务  2
    public final static Integer livenessId5 = 5;//贴条  10
    public final static Integer livenessId6 = 6;//替缴罚单  10
    public final static Integer livenessId7 = 7;//偷取动力  1
    public final static Integer livenessId8 = 8;//开车  10
    public final static Integer livenessId9 = 9;//搭乘顺风车  20
    public final static Integer livenessId10 = 10;//接纳顺风车  20
    public final static Integer livenessId11 = 11;//满足祈愿  20
    public final static Integer livenessId12 = 12;//发布祈愿  10
    public final static Integer livenessId13 = 13;//贡献配件  20
    public final static Integer livenessId14 = 14;//提醒老板  5
    public final static Integer livenessId15 = 15;//赛车对战  30
    public final static Integer livenessId16 = 16;//合成配件  30
    public final static Integer livenessId17 = 17;//赠送配件  20

    /**
     * @param carId
     * @return
     */
    public static DicLiveness getDicLiveness(Integer livenessId) {
        if (dicLivenessMap.size() == 0) {
            DicService dicService = SpringContextUtil.getBean("dicService", DicService.class);
            List<DicLiveness> list = dicService.getLiveness();
            for (DicLiveness dic : list) {
                dicLivenessMap.put(dic.getLivenessId(), dic);
            }
        }
        return dicLivenessMap.get(livenessId);

    }
}
