package com.vergilyn.examples.alipay.order.enums;


public enum IndustryServiceTypeEnum {
	RUSH_PIPE("管道疏通"),
	HOUSEHOLD_ELECTRICAL_APPLIANCES_CLEANING("家电清洗"),
	HOUSEHOLD_ELECTRICAL_APPLIANCES_MAINTAIN("家电维修"),
	FURNITURE_INSTALLATION("家具安装"),
	HOUSEKEEPING("家政保洁"),
	SOFT_LOADING_CLEANING("软装清洗"),
	HOME_IN_HOUSE_MOVING("上门搬家"),
	HOME_IN_UNLOCKING("上门开锁");

	private final String description;

	IndustryServiceTypeEnum(String description) {
		this.description = description;
	}

	public String description() {
		return description;
	}
}
