package com.vergilyn.examples.alipay;

import com.alipay.api.msg.AlipayMsgClient;
import com.alipay.api.msg.MsgHandler;
import com.vergilyn.examples.alipay.config.MiniProperties;
import lombok.SneakyThrows;

import java.time.LocalDateTime;

public class AlipayMiniSubscribeMessageListener {

	/**
	 * <a href="https://opendocs.alipay.com/common/02kiq1">WebSocket长连接接入</a>
	 *
	 * <p> 未正确接收到订阅消息，不清楚具体原因。（可能是 小程序未发布..）
	 */
	@SneakyThrows
	public static void main(String[] args) {
		MiniProperties miniProperties = MiniProperties.INSTANCE;

		AlipayMsgClient alipayMsgClient = AlipayMsgClient.getInstance(miniProperties.appId());

		// 目标支付宝服务端地址，线上环境为 openchannel.alipay.com
		alipayMsgClient.setConnector("openchannel.alipay.com");

		alipayMsgClient.setSecurityConfig("RSA2", miniProperties.privateKey(), miniProperties.alipayPublicKey());

		alipayMsgClient.setMessageHandler(new MsgHandler() {
			/**
			 * 客户端接收到消息后回调此方法
			 *  @param  msgApi 接收到的消息的消息api名
			 *  @param  msgId 接收到的消息的消息id
			 *  @param  bizContent 接收到的消息的内容，json格式
			 */
			@Override
			public void onMessage(String msgApi, String msgId, String bizContent) {
				System.out.printf("[%s]alipay-From-message >>>> \n", LocalDateTime.now());
				System.out.println("  \tmsgApi: " + msgApi);
				System.out.println("  \tmsgId: " + msgId);
				System.out.println("  \tbizContent: " + bizContent);
			}
		});

		alipayMsgClient.connect();
	}
}
