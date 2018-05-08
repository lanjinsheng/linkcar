package com.idata365.app.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.idata365.app.config.SystemProperties;
import com.idata365.app.entity.FamilyInvite;
import com.idata365.app.entity.Message;
import com.idata365.app.entity.UsersAccount;
import com.idata365.app.enums.AchieveEnum;
import com.idata365.app.enums.MessageEnum;
import com.idata365.app.service.FamilyInviteService;
import com.idata365.app.service.FamilyService;
import com.idata365.app.service.LoginRegService;
import com.idata365.app.service.MessageService;
import com.idata365.app.service.common.AchieveCommService;
import com.idata365.app.util.ResultUtils;
import com.idata365.app.util.SignUtils;

@RestController
public class ShareCommController extends BaseController
{
	private final static Logger LOG = LoggerFactory.getLogger(ShareCommController.class);

	@Autowired
	private FamilyService familyService;
	@Autowired
	private FamilyInviteService familyInviteService;
	@Autowired
	private LoginRegService loginRegService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private SystemProperties systemProperties;
	@Autowired
	private AchieveCommService acchieveCommService;

	public ShareCommController()
	{
		
	}

	// @RequestMapping("/share/goInvite")
	// public String goInvite(@RequestParam (required = false) Map<String,
	// String> allRequestParams,Map<String, Object> model){
	// String content=allRequestParams.get("key");
	// if(content==null) {
	// return "error";
	// }
	// try {
	// String key=SignUtils.decryptDataAes(content);
	// String []arrayString = key.split(":");
	// Long familyId=Long.valueOf(arrayString[0]);
	// Long createTimeLong=Long.valueOf(arrayString[2]);
	// String inviteCode=arrayString[1];
	// Long now=System.currentTimeMillis()-(3600*1000);//一天过期
	// if(now>createTimeLong) {
	// LOG.info("过期的数据 key："+key);
	// return "error";
	// }
	// //跳转到加盟页面
	// //
	// String datas=familyId+":"+inviteCode+":"+System.currentTimeMillis();
	// String sign=SignUtils.encryptDataAes(datas);
	// model.put("sign", sign);
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// return "error";
	// }
	// return "invite1";
	// }
	@RequestMapping("/share/goInvite")
	public Map<String, Object> goInvite(@RequestParam(required = false) Map<String, String> allRequestParams,@RequestBody(required = false) Map<String, Object> requestBodyParams)
	{
		String content = String.valueOf(requestBodyParams.get("key"));
		if (content == null)
		{
			return ResultUtils.rtFailParam(null, "无效参数");

		}
		Map<String, Object> rt = new HashMap<String, Object>();
		try
		{
			String key = SignUtils.decryptDataAes(content);
			String[] arrayString = key.split(":");
			Long familyId = Long.valueOf(arrayString[0]);
			Long createTimeLong = Long.valueOf(arrayString[2]);
			String inviteCode = arrayString[1];
			Long now = System.currentTimeMillis() - (100*24*3600*1000);// 100天过期
			if (now > createTimeLong)
			{
				LOG.info("过期的数据 key：" + key);
				return ResultUtils.rtFailParam(null, "过期数据");
			}
			String datas = familyId + ":" + inviteCode + ":" + System.currentTimeMillis();
			String sign = SignUtils.encryptDataAes(datas);

			rt.put("sign", sign);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResultUtils.rtFail(null);
		}
		return ResultUtils.rtSuccess(rt);
	}

	@RequestMapping("/share/getCode")
	public Map<String, Object> getCode(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<String, String> requestBodyParams)
	{
		String content = requestBodyParams.get("sign");
		String phone = requestBodyParams.get("phone");
		if (content == null || phone == null)
		{
			return ResultUtils.rtFailParam(null, "无效参数");
		}
		try
		{
			String key = SignUtils.decryptDataAes(content);
			String[] arrayString = key.split(":");
			Long familyId = Long.valueOf(arrayString[0]);
			Long createTimeLong = Long.valueOf(arrayString[2]);
			String inviteCode = arrayString[1];
			Long now = System.currentTimeMillis() - (3600 * 1000);// 1小时过期
			if (now > createTimeLong)
			{
				LOG.info("过期的数据 key：" + key);
				return ResultUtils.rtFailParam(null, "过期数据");
			}
			// 跳转到加盟页面
			// 判断并给出code
			loginRegService.getVerifyCode(phone, 4);// 分享校验码类型4
			return ResultUtils.rtSuccess(null);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResultUtils.rtFail(null);
		}
	}

	@RequestMapping("/share/submitInvite")
	public Map<String, Object> submitInvite(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<String, String> requestBodyParams)
	{

		Map<String, String> rtMap = new HashMap<String, String>();
		String content = requestBodyParams.get("sign");
		String phone = requestBodyParams.get("phone");
		String code = requestBodyParams.get("code");
		if (content == null || phone == null)
		{
			return ResultUtils.rtFailParam(null, "无效参数");
		}
		try
		{
			String key = SignUtils.decryptDataAes(content);
			String[] arrayString = key.split(":");
			Long familyId = Long.valueOf(arrayString[0]);
			Long createTimeLong = Long.valueOf(arrayString[2]);
			String inviteCode = arrayString[1];
			Long now = System.currentTimeMillis() - (3600 * 1000);// 一天过期
			if (now > createTimeLong)
			{
				LOG.info("过期的数据 key：" + key);
				return ResultUtils.rtFailParam(null, "过期数据");
			}
			// 判断验证码是否正常
			String status = LoginRegService.VC_ERR;
			if (code.equals(systemProperties.getNbcode()))
			{// 测试万能验证码
				status = LoginRegService.OK;
			}
			else
			{
				status = loginRegService.validVerifyCode(phone, 4, code);
			}
			if (status.equals(LoginRegService.OK))
			{// 校验码通过
				// 插入关联信息
				FamilyInvite familyInvite = new FamilyInvite();
				familyInvite.setFamilyId(familyId);
				familyInvite.setMemberPhone(phone);
				UsersAccount user = loginRegService.getUserByPhone(phone);

				if (user != null)
				{
					// 提交审核消息,待写
					rtMap.put("userExist", "1");
					rtMap.put("familyCode", inviteCode);
					familyInvite.setMemberUserId(user.getId());
					familyInvite.setSendInviteMsg(1);
					Map<String, Object> family = familyService.findFamilyByFamilyId(familyId);
					Long toUserId = Long.valueOf(family.get("createUserId").toString());

					// 插入业务关联信息
					Long inviteId = familyInviteService.insertInviteFamily(familyInvite);
					// 构建成员加入消息
					Message message = messageService.buildMessage(user.getId(), user.getPhone(), user.getNickName(),
							toUserId, inviteId, MessageEnum.INVITE_FAMILY);
					LOG.info("message.getToUrl():" + message.getToUrl());
					// 插入消息
					messageService.insertMessage(message, MessageEnum.INVITE_FAMILY);
					// 推送消息
					messageService.pushMessageTrans(message, MessageEnum.INVITE_FAMILY);
				}
				else
				{
					familyInvite.setMemberUserId(0L);
					familyInvite.setSendInviteMsg(0);
					rtMap.put("userExist", "0");
					rtMap.put("familyCode", inviteCode);
					familyInviteService.insertInviteFamily(familyInvite);
					
				}
				rtMap.put("userNum", String.valueOf(familyService.getUsersCount()));
				return ResultUtils.rtSuccess(rtMap);
			}
			else
			{
				if (status.equals(LoginRegService.VC_ERR))
					return ResultUtils.rtFailParam(null, "校验码无效");
				if (status.equals(LoginRegService.VC_EX))
					return ResultUtils.rtFailParam(null, "校验码过期");
			}
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResultUtils.rtFail(null);
		}
		return ResultUtils.rtFail(null);
	}

	// @RequestMapping("/share/createInviteTest")
	// @ResponseBody
	// public Map<String,Object> createInviteTest(@RequestParam (required =
	// false) Map<String, String> allRequestParams,@RequestBody (required =
	// false) Map<Object, Object> requestBodyParams){
	// Long userId= Long.valueOf(allRequestParams.get("userId"));
	// Map<String,Object> rtMap=new HashMap<String,Object>();
	// Map<String,Object> family=familyService.findFamilyIdByUserId(userId);
	// if(family==null) {
	// return ResultUtils.rtFailParam(null,"参数错误，或者用户家族未创建");
	// }
	// try {
	// Long familyId=Long.valueOf(rtMap.get("id").toString());
	// String inviteCode=rtMap.get("inviteCode").toString();
	// String datas=familyId+":"+inviteCode+":"+System.currentTimeMillis();
	// String key=SignUtils.encryptDataAes(String.valueOf(datas));
	// String
	// shareUrl=this.getFamilyInviteBasePath(systemProperties.getH5Host())+key;
	// rtMap.put("shareUrl", shareUrl);
	// return ResultUtils.rtSuccess(rtMap);
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// return ResultUtils.rtFail(null);
	// }
	// }

	@RequestMapping("/share/createInviteTest")
	public Map<String, Object> createInviteTest(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<Object, Object> requestBodyParams)
	{
		Long userId = Long.valueOf(allRequestParams.get("userId"));
		Map<String, Object> rtMap = new HashMap<String, Object>();
		Map<String, Object> family = familyService.findFamilyIdByUserId(userId);
		if (family == null)
		{
			return ResultUtils.rtFailParam(null, "参数错误，或者用户家族未创建");
		}
		try
		{
			Long familyId = Long.valueOf(family.get("id").toString());
			String inviteCode = family.get("inviteCode").toString();
			String datas = familyId + ":" + inviteCode + ":" + System.currentTimeMillis();
			String key = SignUtils.encryptDataAes(String.valueOf(datas));
			String shareUrl = this.getFamilyInviteBasePath(systemProperties.getH5Host()) + key;
			rtMap.put("shareUrl", shareUrl);
			rtMap.put("title", String.format("邀请您参与【%s】游戏", family.get("familyName").toString()));
			rtMap.put("content", "安全驾驶，即有机会获得超丰厚奖品！");
			List<String> imgs = new ArrayList<String>();
			imgs.add("http://apph5.idata365.com/appImgs/logo.png");
			rtMap.put("imgs", imgs);
			return ResultUtils.rtSuccess(rtMap);
		}
		catch (Exception e)
		{
			return ResultUtils.rtFail(null);
		}
	}

	@RequestMapping("/share/successShare")
	@ResponseBody
	public Map<String, Object> successShare(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<Object, Object> requestBodyParams)
	{
		Long userId = Long.valueOf(requestBodyParams.get("userId").toString());
		Long shareType = Long.valueOf(requestBodyParams.get("shareType").toString());
		if (shareType == 1)
		{
			// 分享邀请码
			acchieveCommService.addAchieve(userId, 0d, AchieveEnum.AddShareTimes);
		}
		Map<String, Object> rtMap = new HashMap<String, Object>();
		return ResultUtils.rtSuccess(rtMap);

	}

	public static void main(String[] args)
	{
		System.out.println("112:345353".split(":")[0]);
	}

}