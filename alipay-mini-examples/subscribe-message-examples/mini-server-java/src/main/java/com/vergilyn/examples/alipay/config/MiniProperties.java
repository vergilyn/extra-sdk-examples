package com.vergilyn.examples.alipay.config;

public interface MiniProperties {
	MiniProperties INSTANCE = new VergilynMiniProperties();

	default String serverUrl(){
		// return "https://openapi.alipaydev.com/gateway.do";
		return "https://openapi.alipay.com/gateway.do";
	}

	/**
	 * 请填写您的AppId，例如：2019091767145019
	 */
	String appId();

	/**
	 * 请填写您的应用私钥，例如：MIIEvQIBADANB
	 */
	String privateKey();

	/**
	 * 请填写您的支付宝公钥，例如：MIIBIjANBg...
	 */
	String alipayPublicKey();




	String userId();
}
