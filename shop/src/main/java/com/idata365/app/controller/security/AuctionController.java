package com.idata365.app.controller.security;

import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.idata365.app.constant.AppMsgCode;
import com.idata365.app.locker.Lockers;
import com.idata365.app.remote.ChezuAppService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.idata365.app.config.SystemProperties;
import com.idata365.app.entity.AuctionGoods;
import com.idata365.app.entity.AuctionLogs;
import com.idata365.app.entity.bean.AuctionBean;
import com.idata365.app.enums.UserImgsEnum;
import com.idata365.app.partnerApi.QQSSOTools;
import com.idata365.app.partnerApi.SSOTools;
import com.idata365.app.remote.ChezuAccountService;
import com.idata365.app.remote.ChezuAssetService;
import com.idata365.app.remote.ChezuImService;
import com.idata365.app.service.AuctionService;
import com.idata365.app.util.DateTools;
import com.idata365.app.util.PhoneUtils;
import com.idata365.app.util.ResultUtils;
import com.idata365.app.util.SignUtils;

/**
 * @ClassName: ShopController
 * @Description: TODO(竞拍操作的控制层)
 * @author Lcc
 * @date 2018年6月12日
 *
 */

@RestController
public class AuctionController extends BaseController {
	protected static final Logger LOG = LoggerFactory.getLogger(AuctionController.class);

	@Autowired
	private AuctionService auctionService;
	@Autowired
	private ChezuAssetService chezuAssetService;
	@Autowired
	SystemProperties systemProperties;
	@Autowired
	ChezuImService chezuImService;
	@Autowired
	ChezuAccountService chezuAccountService;
	@Autowired
	ChezuAppService chezuAppService;

	@RequestMapping("/test/doTest2")
	public Map<String, Object> doTest(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<Object, Object> requestBodyParams) {
		AuctionBean auctionBean = auctionService.getAuctionBean(3L);
		chezuImService.notifyAuction(auctionBean, "20", "30");
		return ResultUtils.rtSuccess(null);
	}

	@RequestMapping("/publishAuthority")
	public Map<String, Object> publishAuthority(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<Object, Object> requestBodyParams) {
		Map<String, String> rtMap = new HashMap<String, String>();
		rtMap.put("authority", "0");
		if (this.getUserId() == 1) {
			rtMap.put("authority", "1");
		}
		return ResultUtils.rtSuccess(rtMap);
	}

	/**
	 * 
	 * @Title: publishAuction
	 * @Description: TODO(发布竞拍商品)
	 * @param @param
	 *            allRequestParams
	 * @param @param
	 *            requestBodyParams
	 * @param @return
	 * @param @throws
	 *            Exception 参数
	 * @return Map<String,Object> 返回类型
	 * @throws @author
	 *             LiXing
	 */
	@RequestMapping("/publishAuction")
	public Map<String, Object> publishAuction(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<Object, Object> requestBodyParams) throws Exception {
		Long userId = super.getUserId();
		LOG.info("userId=================" + userId);
		String prizeName = requestBodyParams.get("title").toString();
		String prizeDesc = requestBodyParams.get("desc").toString();
		String prizePic = requestBodyParams.get("imgs").toString();
		String auctionTag = requestBodyParams.get("auctionTag") == null ? "" : requestBodyParams.get("auctionTag").toString();
		int type = Integer.valueOf(requestBodyParams.get("type").toString());
		BigDecimal startDiamond = BigDecimal
				.valueOf(Double.valueOf(String.valueOf(requestBodyParams.get("startValue"))));
		BigDecimal stepPrice = BigDecimal.valueOf(Double.valueOf(String.valueOf(requestBodyParams.get("difference"))));
		Date auctionStartTime = DateTools.getDateTimeOfStr(requestBodyParams.get("startTime").toString(),
				"yyyy-MM-dd HH:mm");
		Date auctionEndTime = DateTools.getDateTimeOfStr(requestBodyParams.get("endTime").toString(),
				"yyyy-MM-dd HH:mm");

		AuctionGoods auctionGoods = new AuctionGoods();
		auctionGoods.setPrizeName(prizeName);
		auctionGoods.setPrizeDesc(prizeDesc);
		auctionGoods.setPrizePic(prizePic);
		auctionGoods.setStartDiamond(startDiamond);
		auctionGoods.setStepPrice(stepPrice);
		auctionGoods.setAuctionStartTime(auctionStartTime);
		auctionGoods.setAuctionEndTime(auctionEndTime);
		auctionGoods.setAuctionRealEndTime(auctionEndTime);
		auctionGoods.setOfUserId(userId);
		auctionGoods.setAuctionGoodsType(type);
		auctionGoods.setAuctionTag(auctionTag);
		int f = auctionService.insertAuctionGoods(auctionGoods);
		if (f == 0) {
			return ResultUtils.rtFail(null);
		} else {
			return ResultUtils.rtSuccess(null);
		}
	}

	/**
	 * 
	 * @Title: listAuctionGoods
	 * @Description: TODO(所有钻石竞拍列表)
	 * @param @return
	 *            参数
	 * @return Map<String,Object> 返回类型
	 * @throws @author
	 *             lcc
	 */
	@RequestMapping("/listAuctionGoods")
	public Map<String, Object> listAuctionGoods() {
		Long userId = this.getUserId();
		String sign = SignUtils.encryptHMAC(String.valueOf(userId));
		LOG.info("userId=================" + userId);
		LOG.info("sign=================" + sign);
		Map<String, Object> rtMap = new HashMap<>();
		Map<String, String> diamond = new HashMap<>();
		Map<String, Object> userAsset = chezuAssetService.getUserAsset(userId, sign);
		diamond.put("diamondHoldNum", BigDecimal.valueOf(Double.valueOf(String.valueOf(userAsset.get("diamondsNum"))))
				.setScale(2, RoundingMode.HALF_UP).toString());
		List<Map<String, String>> list = auctionService.listAuctionGoods();
		rtMap.put("diamondHoldNum", diamond);
		rtMap.put("auctionGoodsList", list);
		return ResultUtils.rtSuccess(rtMap);
	}

	/**
	 * 
	 * @Title: myListAuctionGoods
	 * @Description: TODO(我的竞拍商品（竞拍记录）)
	 * @param @return
	 *            参数
	 * @return Map<String,Object> 返回类型
	 * @throws @author
	 *             lcc
	 */
	@RequestMapping("/myListAuctionGoods")
	public Map<String, Object> myListAuctionGoods(@RequestParam(required = false) Map<String, String> allRequestParams,
		@RequestBody(required = false) Map<Object, Object> requestBodyParams) {
		Long userId = this.getUserId();
		LOG.info("userId=================" + userId);
		int type = Integer.valueOf(requestBodyParams.get("type").toString());
		List<Map<String, String>> list = auctionService.myListAuctionGoods(userId,type);
		return ResultUtils.rtSuccess(list);
	}

	/**
	 * 
	 * @Title: writeChangeInfo
	 * @Description: TODO(填写兑换信息)
	 * @param @return
	 *            参数
	 * @return Map<String,Object> 返回类型
	 * @throws @author
	 *             lcc
	 */
	@RequestMapping("/writeChangeInfo")
	public Map<String, Object> writeChangeInfo(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<Object, Object> requestBodyParams) {
		Long userId = this.getUserId();
		LOG.info("userId=================" + userId);

		Long auctionGoodsId = Long.valueOf(requestBodyParams.get("auctionGoodsId").toString());
		AuctionGoods goods = auctionService.findOneAuctionGoodById(auctionGoodsId);
		Map<String, Object> data = new HashMap<>();
		data.put("name", this.getUserInfo().getNickName()==null?PhoneUtils.hidePhone(this.getUserInfo().getPhone()):this.getUserInfo().getNickName());
		if (goods.getAuctionGoodsType() == 1) {
			String phone = requestBodyParams.get("phone").toString();
			data.put("phone", phone);

		} else {
			String phone = requestBodyParams.get("phone").toString();
			String name = requestBodyParams.get("name").toString();
			String address = requestBodyParams.get("address").toString();
			String provinceCode = requestBodyParams.get("provinceCode").toString();
			String cityCode = requestBodyParams.get("cityCode").toString();
			String areaCode = requestBodyParams.get("areaCode").toString();
			data.put("phone", phone);
			data.put("name", name);
			data.put("address", address);
			data.put("provinceCode", provinceCode);
			data.put("cityCode", cityCode);
			data.put("areaCode", areaCode);
		}
		data.put("userId", userId);
		data.put("auctionGoodsId", auctionGoodsId);
		auctionService.writeChangeInfo(data);
		return ResultUtils.rtSuccess(null);
	}

	/**
	 * 
	 * @Title: uploadAuctionImg
	 * @Description: TODO(上传竞拍商品图片)
	 * @param @param
	 *            file
	 * @param @param
	 *            requestBodyParams
	 * @param @return
	 *            参数
	 * @return Map<String,Object> 返回类型
	 * @throws @author
	 *             Lcc
	 */
	@RequestMapping("/uploadAuctionImg")
	public Map<String, Object> uploadAuctionImg(@RequestParam CommonsMultipartFile file,
			@RequestParam Map<String, Object> map) {
		Long userId = this.getUserId();
		if (file == null) {
			return ResultUtils.rtFailParam(null, "附件为空");
		}
		Map<String, Object> rtMap = new HashMap<String, Object>();
		rtMap.put("userId", userId);
		rtMap.put("imgUrl", "");
		try {
			String key = "";
			if (systemProperties.getSsoQQ().equals("1")) {// 走qq
				key = QQSSOTools.createSSOUsersImgInfoKey(userId, UserImgsEnum.AUCTION);
				File dealFile = new File(systemProperties.getFileTmpDir() + "/" + key);
				File fileParent = dealFile.getParentFile();
				if (!fileParent.exists()) {
					fileParent.mkdirs();
				}
				file.transferTo(dealFile);
				QQSSOTools.saveOSS(dealFile, key);
			} else {// 走阿里
					// 获取输入流 CommonsMultipartFile 中可以直接得到文件的流
				key = SSOTools.createSSOUsersImgInfoKey(userId, UserImgsEnum.AUCTION);
				// 处理图片
				File dealFile = new File(systemProperties.getFileTmpDir() + "/" + key);
				File fileParent = dealFile.getParentFile();
				if (!fileParent.exists()) {
					fileParent.mkdirs();
				}
				InputStream is = file.getInputStream();
				SSOTools.saveOSS(is, key);
				is.close();
				file.transferTo(dealFile);
			}
			rtMap.put("imgUrl", getImgBasePath() + key);
			rtMap.put("key", key);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResultUtils.rtFail(null);
		}
		rtMap.remove("key");
		rtMap.remove("userId");
		return ResultUtils.rtSuccess(rtMap);
	}

	/**
	 * 
	 * @Title: auctionGoodsDetail
	 * @Description: TODO(商品详情)
	 * @param @return
	 *            参数
	 * @return Map<String,Object> 返回类型
	 * @throws @author
	 *             lcc
	 */
	@RequestMapping("/auctionGoodsDetail")
	public Map<String, Object> auctionGoodsDetail(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<Object, Object> requestBodyParams) {
		Map<String, Object> rtMap = new HashMap<>();
		Long userId = this.getUserId();
		String sign = SignUtils.encryptHMAC(String.valueOf(userId));
		Long auctionGoodsId = Long.valueOf(requestBodyParams.get("auctionGoodsId").toString());
		LOG.info("auctionGoodsId=================" + auctionGoodsId);
		LOG.info("userId=================" + userId);
		LOG.info("sign=================" + sign);
		Map<String, Object> userAsset = chezuAssetService.getUserAsset(userId, sign);
		// 商品信息
		Map<String, String> auctionGoodsInfo = auctionService.findAuctionGoodById(auctionGoodsId, userId);
		auctionGoodsInfo.put("diamondHoldNum",
				BigDecimal.valueOf(Double.valueOf(String.valueOf(userAsset.get("diamondsNum"))))
						.setScale(2, RoundingMode.HALF_UP).toString());
		// 该商品出价记录
		List<Map<String, String>> auctionInfo = auctionService.listAuctionGoodsRecord(auctionGoodsId);
		rtMap.put("auctionGoodsInfo", auctionGoodsInfo);
		rtMap.put("auctionInfo", auctionInfo);
		return ResultUtils.rtSuccess(rtMap);
	}
	
	/**
	 * 
	 * @Title: queryChartInfo
	 * @Description: TODO(成交历史折线图)
	 * @param @return
	 *            参数
	 * @return Map<String,Object> 返回类型
	 * @throws @author
	 *             lcc
	 */
	@RequestMapping("/queryChartInfo")
	public Map<String, Object> queryChartInfo(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<Object, Object> requestBodyParams) {
		Map<String, Object> rtMap = new HashMap<>();
		Long auctionGoodsId = Long.valueOf(requestBodyParams.get("auctionGoodsId").toString());
		int param = Integer.valueOf(requestBodyParams.get("param").toString());
		LOG.info("auctionGoodsId=================" + auctionGoodsId);
		LOG.info("param=================" + param);
		// 同标签商品成交折线图
		List<Map<String, String>> chartInfo = auctionService.getChartInfo(auctionGoodsId,param);
		rtMap.put("chartInfo", chartInfo);
		return ResultUtils.rtSuccess(rtMap);
	}

	/**
	 * 
	 * @Title: doAuction
	 * @Description: TODO(竞拍提交，保存订单)
	 * @param @param
	 *            allRequestParams
	 * @param @param
	 *            requestBodyParams
	 * @param @return
	 * @param @throws
	 *            Exception 参数
	 * @return Map<String,Object> 返回类型
	 * @throws @author
	 *             LiXing
	 */
	@RequestMapping("/doAuction")
	public Map<String, Object> doAuction(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<Object, Object> requestBodyParams) throws Exception {
		Long userId = super.getUserId();

		String userName = this.getUserInfo().getNickName();
		LOG.info("userId=================" + userId);
		Long auctionGoodsId = Long.valueOf(requestBodyParams.get("auctionGoodsId").toString());
		LOG.info("auctionGoodsId=================" + auctionGoodsId);
		
		BigDecimal auctionDiamond = BigDecimal
				.valueOf(Double.valueOf(String.valueOf(requestBodyParams.get("auctionDiamond"))));
		LOG.info("auctionDiamond=================" + auctionDiamond);
		Lockers.getLocker(String.valueOf(auctionGoodsId)).lock();
		HashMap<String, Object> datas = new HashMap<String, Object>();
		try{
		AuctionGoods auctionGoods = auctionService.findOneAuctionGoodById(auctionGoodsId);
//		if (auctionGoods.getIsMustVerify() == 1) {// 需要身份验证
//			Map<String, String> authenticated = chezuAccountService.isAuthenticated(userId,
//					SignUtils.encryptHMAC(String.valueOf(userId)));
//			if ("0".equals(authenticated.get("IdCardIsOK")) || "0".equals(authenticated.get("VehicleTravelIsOK"))) {
//				HashMap<String, Object> datas = new HashMap<String, Object>();
//				datas.put("code", "-1");
//				datas.put("msg", "亲！请先去认证身份哦");
//				return ResultUtils.rtSuccess(datas);
//			}
//		}
		Date auctionRealEndTime = auctionGoods.getAuctionRealEndTime();
		if ((auctionRealEndTime.getTime() - System.currentTimeMillis()) < 2 * 1000) {
			//出错了,时间已到
			datas.put("code", "0");
			datas.put("msg", AppMsgCode.M10001.getMsg());
		}
		if(auctionGoods.getDoneDiamond().doubleValue() >= auctionDiamond.doubleValue()){
        	//出错了,价格不合适
			datas.put("code", "0");
			datas.put("msg", AppMsgCode.M10000.getMsg());
			return ResultUtils.rtSuccess(datas);
		}

		long winnerId = auctionGoods.getWinnerId();
//		if (winnerId == userId) {
//			HashMap<String, Object> datas = new HashMap<String, Object>();
//			datas.put("msg", "亲！您已经是当前最高竞价者");
//			return ResultUtils.rtSuccess(datas);
//		}

		// 插入竞拍记录
		AuctionLogs auctionLogs = new AuctionLogs();
		auctionLogs.setAuctionTime(new Date());
		auctionLogs.setAuctionDiamond(auctionDiamond);
		auctionLogs.setAuctionGoodsId(auctionGoodsId);
		auctionLogs.setAuctionUserId(userId);
		auctionLogs.setAuctionUserNick(userName==null?PhoneUtils.hidePhone(this.getUserInfo().getPhone()):userName);

		//此时判断商品当前竞拍价格是否被超越，如果不是超越 则无需更改用户的锁定金额。
		//更改当前的商品展示价格
		boolean changeAsset = false;
		if(auctionDiamond.doubleValue() > auctionGoods.getBidDiamond().doubleValue()){
			//换人,变价格，+stepprice
			auctionGoods.setWinnerId(userId);
			auctionGoods.setDoneDiamond(auctionGoods.getBidDiamond().add(auctionGoods.getStepPrice()));
			auctionGoods.setBidDiamond(auctionDiamond);
			auctionGoods.setWinnerName(auctionLogs.getAuctionUserNick());
			changeAsset = true;
			auctionLogs.setAutoBidMan(auctionLogs.getAuctionUserNick());
			auctionLogs.setAuctionDiamond(auctionGoods.getDoneDiamond());
		}else if(auctionDiamond.doubleValue() ==  auctionGoods.getBidDiamond().doubleValue()) {
			//不换人，变价格，给底牌
			 auctionGoods.setDoneDiamond(auctionDiamond);
			 auctionLogs.setAutoBidMan(auctionGoods.getWinnerName());
			 auctionLogs.setAuctionDiamond(auctionGoods.getDoneDiamond());
		}else{
			//不换人，变价格，+stepprice
			auctionGoods.setDoneDiamond(auctionDiamond.add(auctionGoods.getStepPrice()));
			auctionLogs.setAutoBidMan(auctionGoods.getWinnerName());
			auctionLogs.setAuctionDiamond(auctionGoods.getDoneDiamond());
		}

			auctionService.doAuction(auctionGoods, auctionLogs, userId, winnerId,changeAsset);
		//IM 通知价格变化与列表
			List<AuctionLogs> auctionLogsList = auctionService.listAuctionGoodsBeanRecord(auctionGoodsId);
			AuctionBean auctionBean = new AuctionBean();
			auctionBean.setAuctionGoods(auctionGoods);
			auctionBean.setAuctionLogsList(auctionLogsList);
			Map<String, Object> notifyAuction = chezuImService.notifyAuction(auctionBean,
					String.valueOf(auctionService.joinPersons(auctionGoodsId)),
					String.valueOf(auctionService.joinTimes(auctionGoodsId)));
//			if (1==1) {
//				chezuAppService.sendAuctionRobbedMsg(winnerId, auctionGoods.getPrizeName(), "sign");
//			}

			datas.put("code", "1");
			datas.put("msg", AppMsgCode.M10002.getMsg());
			return ResultUtils.rtSuccess(datas);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if("钻石数量不够支付".equals(e.getMessage())) {
				datas.put("code", "0");
				datas.put("msg", AppMsgCode.M10003.getMsg());
			}else {
				datas.put("code", "2");
				datas.put("msg", AppMsgCode.M20000.getMsg());
			}
			return ResultUtils.rtSuccess(datas);
		}finally {
			Lockers.getLocker(String.valueOf(auctionGoodsId)).unlock();
		}
	}
	
	/**
	 * 
	 * @Title: queryCdKey
	 * @Description: TODO(查看红包兑换码)
	 * @param @return
	 *            参数
	 * @return Map<String,Object> 返回类型
	 * @throws @author
	 *             lcc
	 */
	@RequestMapping("/queryCdKey")
	public Map<String, Object> queryCdKey(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<Object, Object> requestBodyParams) {
		Long logId = Long.valueOf(requestBodyParams.get("convertId").toString());
		LOG.info("logId=================" + logId);
		Map<String, Object> rtMap = auctionService.queryCdKey(logId);
		return ResultUtils.rtSuccess(rtMap);
	}
	
	/**
	 * 
	 * @Title: applyCdKey
	 * @Description: TODO(重新申领红包)
	 * @param @return
	 *            参数
	 * @return Map<String,Object> 返回类型
	 * @throws @author
	 *             lcc
	 */
	@RequestMapping("/applyCdKey")
	public Map<String, Object> applyCdKey(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<Object, Object> requestBodyParams) {
		Long logId = Long.valueOf(requestBodyParams.get("convertId").toString());
		LOG.info("logId=================" + logId);
		auctionService.applyCdKey(logId);
		return ResultUtils.rtSuccess(null);
	}

	public static void main(String []args){
		Date a= new Date();
		System.out.println(a.getTime());
		System.out.println(System.currentTimeMillis());

	}
}
