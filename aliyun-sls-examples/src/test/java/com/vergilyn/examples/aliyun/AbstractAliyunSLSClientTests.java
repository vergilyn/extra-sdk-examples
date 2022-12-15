package com.vergilyn.examples.aliyun;

import com.aliyun.openservices.aliyun.log.producer.Producer;
import com.aliyun.openservices.log.Client;
import com.vergilyn.examples.aliyun.client.AliyunSLSClient;

public abstract class AbstractAliyunSLSClientTests {
	protected final String _applicationName = "aliyun-sls-examples";

	protected final AliyunSLSClient _aliyunSLSClient = AliyunSLSClient.getInstance();
	protected final Client _slsClient = _aliyunSLSClient.getClient();

	protected Producer createSLSProducer(String slsProject){
		return _aliyunSLSClient.createSlsProducer(slsProject);
	}
}
