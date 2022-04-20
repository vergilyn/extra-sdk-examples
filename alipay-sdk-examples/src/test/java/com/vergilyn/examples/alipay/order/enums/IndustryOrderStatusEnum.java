package com.vergilyn.examples.alipay.order.enums;

/**
 *
 * @author vergilyn
 * @since 2021-09-01
 */
public enum IndustryOrderStatusEnum {
	BOOK_SUCCESSFULLY("预约成功"),
	CLOSED("已取消"),
	TO_SEND("排单中"),
	ORDER_TAKEN("已接单"),
	SECOND_OFFER("二段报价"),
	WAIT_PAY("待支付"),
	REPAID("二段支付成功"),
	SERVICING("服务中"),
	SERVICED("服务完成"),
	FINISHED("订单完成");

	private final String description;

	IndustryOrderStatusEnum(String description) {
		this.description = description;
	}

	public String description() {
		return description;
	}
}
