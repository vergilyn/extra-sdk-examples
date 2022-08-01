package com.vergilyn.examples.aliyun.rocketmq.config;

public interface AliyunBusinessRocketMQProperties {

	/**
	 * NAMESRV_ADDR, 请在mq控制台 <a href="https://ons.console.aliyun.com">https://ons.console.aliyun.com</a>
	 * 通过"实例管理--获取接入点信息--TCP协议接入点"获取
	 */
	String namesrvAddr();
	String accessKey();
	String secretKey();

	String topic();
	String groupProvider();
	String groupConsumer();
	String tag();

}
