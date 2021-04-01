package com.vergilyn.examples.aliyun.vod.config;

import com.aliyuncs.AcsRequest;
import com.aliyuncs.AcsResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;

public class AliyunVodClient {
	private static final AliyunVodClient INSTANCE = new AliyunVodClient();

	private final AbstractVodProperties _properties;
	private final DefaultAcsClient _acsClient;

	private AliyunVodClient() {
		this._properties = new VergilynVodProperties();

		DefaultProfile profile = DefaultProfile.getProfile(
										_properties.regionId(),
										_properties.accessKeyId(),
										_properties.accessKeySecret());
		this._acsClient = new DefaultAcsClient(profile);
	}

	public static AliyunVodClient getInstance() {
		return INSTANCE;
	}

	public AbstractVodProperties getProperties(){
		return this._properties;
	}

	public DefaultAcsClient getAcsClient(){
		return this._acsClient;
	}

	public <T extends AcsResponse> T getAcsResponse(AcsRequest<T> request) throws ServerException, ClientException {
		return getAcsClient().getAcsResponse(request);
	}
}
