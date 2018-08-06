package com.idata365.app.serviceV2;
/**
 * 
    * @ClassName: MessageServiceV2
    * @Description: TODO(这里用一句话描述这个类的作用)
    * @author Lcc
    * @date 2018年08月06日
    *
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idata365.app.mapper.MessageMapper;
import com.idata365.app.service.BaseService;

@Service
public class MessageServiceV2 extends BaseService<MessageServiceV2> {
	private final static Logger LOG = LoggerFactory.getLogger(MessageServiceV2.class);

	@Autowired
	MessageMapper messageMapper;

	public MessageServiceV2() {
	}

	public void deleteMessage(String msgIds) {
		LOG.info("msgIds==============" + msgIds);
		String[] arr = msgIds.split(",");
		int[] a = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			a[i] = Integer.parseInt(String.valueOf(arr[i]));
		}
		messageMapper.deleteMessage(a);
	}

}
