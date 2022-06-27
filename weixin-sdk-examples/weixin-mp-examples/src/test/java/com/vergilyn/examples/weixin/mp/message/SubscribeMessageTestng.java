package com.vergilyn.examples.weixin.mp.message;

import cn.binarywang.wx.miniapp.bean.WxMaSubscribeMessage;
import cn.binarywang.wx.miniapp.bean.WxMaSubscribeMessage.MsgData;
import com.vergilyn.examples.weixin.mp.AbstractWeixinMpTestng;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class SubscribeMessageTestng extends AbstractWeixinMpTestng {

	/**
	 *
	 * <p> 1. <a href="https://developers.weixin.qq.com/miniprogram/dev/framework/open-ability/subscribe-message.html">
	 *          小程序订阅消息</a>：
	 *     首先需要小程序端唤起用户订阅（目前2022-06-27，只支持 一次性订阅），并配置 事件通知开发者服务器地址
	 *     （参考：<a href="https://developers.weixin.qq.com/miniprogram/dev/framework/server-ability/message-push.html#option-url">开发者服务器接收消息推送</a>）
	 *
	 * <p> 2. <a href="https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/subscribe-message/subscribeMessage.send.html">
	 *          发送订阅消息</a>
	 */
	@Test
	public void subscribeMessage() {
		WxMaSubscribeMessage message = new WxMaSubscribeMessage();
		message.setToUser("ToUser");
		message.setTemplateId("TemplateId");
		message.setPage("page");

		List<MsgData> data = new ArrayList<MsgData>(){{
			add(new MsgData("key1", "value1"));
			add(new MsgData("key2", "value2"));
		}};

		message.setData(data);

		System.out.println(message.toJson());
	}
}
