package com.vergilyn.examples.alibaba.dingtalk.robot;

import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.taobao.api.ApiException;
import com.vergilyn.examples.alibaba.dingtalk.AbstractDingTalkClientTestng;

/**
 * @author vergilyn
 * @since 2022-04-20
 */
public abstract class AbstractRobotEnterprise extends AbstractDingTalkClientTestng {

	protected String getRobotEnterpriseAccessToken() throws ApiException {
		String serverUrl = "/gettoken";

		String appKey = dingtalkProperties().robotEnterpriseAppKey();
		String appSecret = dingtalkProperties().robotEnterpriseAppSecret();

		OapiGettokenRequest request = new OapiGettokenRequest();
		request.setAppkey(appKey);
		request.setAppsecret(appSecret);

		OapiGettokenResponse response = client(serverUrl).execute(request, getAccessKey(), getAccessSecret());
		String accessToken = response.getAccessToken();

		System.out.println("Robot AccessToken >>>> " + accessToken);
		return accessToken;
	}
}
