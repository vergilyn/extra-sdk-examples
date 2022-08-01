package com.vergilyn.examples.aliyun.rocketmq;

import com.aliyun.openservices.ons.api.*;
import com.vergilyn.examples.aliyun.rocketmq.config.AliyunBusinessRocketMQProperties;
import com.vergilyn.examples.aliyun.rocketmq.config.DefaultAliyunBusinessRocketMQProperties;

import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author vergilyn
 * @since 2022-08-01
 */
public abstract class AbstractAliyunBusinessRocketMQTests {
	private static final AtomicBoolean _isProviderStart = new AtomicBoolean(false);
	private static Producer _producer;

	protected AliyunBusinessRocketMQProperties _properties = new DefaultAliyunBusinessRocketMQProperties();
	protected final String _topic = _properties.topic();

	protected Consumer createConsumer(){
		Properties consumerProp = buildPropertiesConsumer();
		Consumer consumer = ONSFactory.createConsumer(consumerProp);
		return consumer;
	}

	protected Producer startProducer(){
		if (!_isProviderStart.compareAndSet(false, true)) {
			return _producer;
		}

		_producer = ONSFactory.createProducer(buildPropertiesProducer());
		_producer.start();

		return _producer;
	}

	private Properties buildPropertiesProducer(){
		Properties properties = buildPropertiesCommon(_properties.groupProvider());
		properties.put(PropertyKeyConst.ProducerId, _properties.groupProvider()); // 兼容性处理

		//设置发送超时时间，单位毫秒
		properties.setProperty(PropertyKeyConst.SendMsgTimeoutMillis, "20000");

		// 消息消费失败时的最大重试次数
		properties.setProperty(PropertyKeyConst.MaxReconsumeTimes, "3");

		return properties;
	}

	private Properties buildPropertiesConsumer(){
		Properties properties = buildPropertiesCommon(_properties.groupConsumer());
		// 集群订阅方式(默认)。
		properties.put(PropertyKeyConst.MessageModel, PropertyValueConst.CLUSTERING);
		// 广播订阅方式。
		// properties.put(PropertyKeyConst.MessageModel, PropertyValueConst.BROADCASTING);

		return properties;
	}

	private Properties buildPropertiesCommon(String groupId){
		Properties properties = new Properties();

		// AccessKey 阿里云身份验证，在阿里云服务器管理控制台创建
		properties.put(PropertyKeyConst.AccessKey, _properties.accessKey());
		// SecretKey 阿里云身份验证，在阿里云服务器管理控制台创建
		properties.put(PropertyKeyConst.SecretKey, _properties.secretKey());
		properties.put(PropertyKeyConst.GROUP_ID, groupId);

		// 设置TCP接入域名，进入控制台的实例详情页面的TCP协议客户端接入点区域查看。
		properties.put(PropertyKeyConst.NAMESRV_ADDR, _properties.namesrvAddr());

		return properties;
	}
}
