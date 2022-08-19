package com.vergilyn.examples.alipay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.vergilyn.examples.alipay.config.MiniProperties;

public abstract class AbstractAlipayMiniSubscribeMessageTests {
	protected final MiniProperties _miniProperties = MiniProperties.INSTANCE;

	/**
	 * {@link DefaultAlipayClient} 线程安全，可以不用每次都 new-instance。
	 */
	protected AlipayClient createAlipayClient(){
		AlipayConfig alipayConfig = new AlipayConfig();
		alipayConfig.setServerUrl(_miniProperties.serverUrl());
		alipayConfig.setAppId(_miniProperties.appId());
		alipayConfig.setPrivateKey(_miniProperties.privateKey());
		alipayConfig.setFormat("json");
		alipayConfig.setAlipayPublicKey(_miniProperties.alipayPublicKey());
		alipayConfig.setCharset("UTF8");
		alipayConfig.setSignType("RSA2");

		try {
			return new DefaultAlipayClient(alipayConfig);
		}catch (AlipayApiException e){
			e.printStackTrace();
			return null;
		}
	}
}
