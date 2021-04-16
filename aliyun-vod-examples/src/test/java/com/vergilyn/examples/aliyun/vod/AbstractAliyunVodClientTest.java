package com.vergilyn.examples.aliyun.vod;

import com.aliyuncs.AcsRequest;
import com.aliyuncs.AcsResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.vergilyn.examples.aliyun.vod.config.AbstractVodProperties;
import com.vergilyn.examples.aliyun.vod.config.AliyunVodClient;

/**
 *
 * @author vergilyn
 * @since 2021-03-29
 *
 * @see <a href="https://help.aliyun.com/document_detail/61062.html">Java SDK 初始化</a>
 */
public abstract class AbstractAliyunVodClientTest {

	protected final AliyunVodClient _vodClient;
	protected final AbstractVodProperties _properties;
	protected final DefaultAcsClient _acsClient;

	public AbstractAliyunVodClientTest() {
		this._vodClient = AliyunVodClient.getInstance();
		this._acsClient = this._vodClient.getAcsClient();
		this._properties = this._vodClient.getProperties();
	}

	protected <T extends AcsResponse> T getAcsResponse(AcsRequest<T> request) throws ServerException, ClientException {
		return this._vodClient.getAcsResponse(request);
	}
}
