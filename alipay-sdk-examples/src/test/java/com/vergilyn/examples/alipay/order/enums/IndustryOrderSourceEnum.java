package com.vergilyn.examples.alipay.order.enums;

/**
 *
 * @author vergilyn
 * @since 2021-09-01
 */
public enum IndustryOrderSourceEnum {
	ALIPAY_APPLETS("支付宝小程序产生的订单"),
	ALIPAY_POS("收银POS产生的支付宝订单");

	private final String description;

	IndustryOrderSourceEnum(String description) {
		this.description = description;
	}

	public String description() {
		return description;
	}
}
