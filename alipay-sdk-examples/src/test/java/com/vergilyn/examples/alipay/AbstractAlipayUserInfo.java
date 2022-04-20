package com.vergilyn.examples.alipay;

public interface AbstractAlipayUserInfo {

	/**
	 * 支付宝userid（用户在支付宝平台的2088开头16位id）
	 */
	String alipayUserId();

	/**
	 * 商户UID
	 */
	String commerceUid();
}
