package com.vergilyn.examples.aliyun.client;

import com.aliyun.openservices.aliyun.log.producer.LogProducer;
import com.aliyun.openservices.aliyun.log.producer.Producer;
import com.aliyun.openservices.aliyun.log.producer.ProducerConfig;
import com.aliyun.openservices.aliyun.log.producer.ProjectConfig;
import com.aliyun.openservices.log.Client;
import com.vergilyn.examples.aliyun.config.AliyunSLSProperties;

/**
 * <a href="https://help.aliyun.com/document_detail/279525.html">Java SDK快速入门</a>
 *
 * @author vergilyn
 * @since 2022-08-26
 */
public class AliyunSLSClient {

	private static final AliyunSLSClient INSTANCE = new AliyunSLSClient();

	private final AliyunSLSProperties _properties = AliyunSLSProperties.INSTANCE;
	private final Client _client;

	public AliyunSLSClient() {

		this._client = new Client(_properties.host(), _properties.accessKeyId(), _properties.accessKeySecret());
	}

	public static AliyunSLSClient getInstance(){
		return INSTANCE;
	}

	public Client getClient(){
		return this._client;
	}

	public Producer createSlsProducer(String slsProject){
		Producer producer = new LogProducer(new ProducerConfig());

		ProjectConfig projectConfig = new ProjectConfig(slsProject,
		                                                _properties.intranetHost(),
		                                                _properties.accessKeyId(),
		                                                _properties.accessKeySecret());
		producer.putProjectConfig(projectConfig);

		return producer;
	}
}
