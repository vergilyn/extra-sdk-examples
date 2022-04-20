package com.vergilyn.examples.alipay;

/**
 * <a href="https://opendocs.alipay.com/open/200/105311">沙箱环境</a>
 *
 * @author vergilyn
 * @since 2021-09-01
 * 
 * @see com.alipay.api.AlipayConfig
 * @see com.alipay.api.DefaultAlipayClient#DefaultAlipayClient(com.alipay.api.AlipayConfig)
 * @see com.alipay.api.DefaultAlipayClient#DefaultAlipayClient(String, String, String, String, String, String, String)
 */
public interface AbstractAlipayClientConfig {
	String SERVER_URL = "https://openapi.alipay.com/gateway.do";
	String SERVER_URL_SANDBOX = "https://openapi.alipaydev.com/gateway.do";

	String appId();

	String privateKey();

	String alipayPublicKey();

	default String serverUrl() {
		return SERVER_URL;
	}

	default String format() {
		return "json";
	}

	default String charset() {
		return "utf-8";
	}

	default String signType() {
		return "RSA2";
	}
}
