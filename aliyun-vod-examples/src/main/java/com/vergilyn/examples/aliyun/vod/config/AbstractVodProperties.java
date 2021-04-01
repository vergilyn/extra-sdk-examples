package com.vergilyn.examples.aliyun.vod.config;

public interface AbstractVodProperties {
	default String regionId(){
		return "cn-hangzhou";
	}

	/**
	 * 阿里云 <a href="https://ram.console.aliyun.com/overview">Access Key管理</a>创建主账号Access Key
	 */
	String accessKeyId();
	String accessKeySecret();

	default String stsRole(){
		return "vod-test-role";
	}

	String stsRoleArn();
}
