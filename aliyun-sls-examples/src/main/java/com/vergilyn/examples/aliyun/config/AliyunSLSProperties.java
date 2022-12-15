package com.vergilyn.examples.aliyun.config;

import com.aliyun.openservices.aliyun.log.producer.LogProducer;

/**
 *
 * @author vergilyn
 * @since 2022-08-26
 */
public interface AliyunSLSProperties {
	AliyunSLSProperties INSTANCE = new VergilynAliyunSLSProperties();

	default String host(){
		return "cn-hangzhou.log.aliyuncs.com";
	}

	/**
	 * 如果部署在 aliyun服务器，例如{@link LogProducer} 推荐使用 内网endpoint。
	 */
	default String intranetHost(){
		return host();
	}

	String accessKeyId();
	String accessKeySecret();

}
