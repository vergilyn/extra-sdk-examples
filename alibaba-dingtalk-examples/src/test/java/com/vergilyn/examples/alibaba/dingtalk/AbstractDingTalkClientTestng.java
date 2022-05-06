package com.vergilyn.examples.alibaba.dingtalk;

import com.alibaba.fastjson.JSON;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.taobao.api.ApiException;
import com.taobao.api.TaobaoRequest;
import com.taobao.api.TaobaoResponse;
import com.vergilyn.examples.alibaba.dingtalk.config.AbstractDingtalkProperties;

import static com.alibaba.fastjson.serializer.SerializerFeature.PrettyFormat;
import static com.vergilyn.examples.alibaba.dingtalk.DingtalkApplication.DINGTALK_PROPERTIES;

/**
 * <a href="https://ding-doc.dingtalk.com/doc#/faquestions/vzbp02">SDK请求示例</a>
 * @author vergilyn
 * @since 2020-11-20
 */
public abstract class AbstractDingTalkClientTestng {
	/**
	 * 正常情况下access_token有效期为7200秒，有效期内重复获取返回相同结果，并自动续期。
	 *
	 * FIXME 2020-11-24,vergilyn 正式项目中可以通过redis/cache维护access_token，避免无意义的请求
	 *
	 * @see <a href="https://ding-doc.dingtalk.com/doc#/serverapi2/eev437">获取access_token</a>
	 */
	protected String getAccessToken() throws ApiException {
		String serverUrl = "/gettoken";

		OapiGettokenRequest request = new OapiGettokenRequest();
		request.setAppkey(getAccessKey());
		request.setAppsecret(getAccessSecret());

		OapiGettokenResponse response = client(serverUrl).execute(request, getAccessKey(), getAccessSecret());
		String accessToken = response.getAccessToken();

		System.out.println("AccessToken >>>> " + accessToken);
		return accessToken;
	}

	protected final <T extends TaobaoResponse> T execute(String serverUrl, TaobaoRequest<T> request) {
		DefaultDingTalkClient client = client(appendAccessToken(serverUrl));
		try {
			return client.execute(request, getAccessKey(), getAccessSecret());
		} catch (ApiException e) {
			e.printStackTrace();
			return null;
		}
	}

	protected final <T extends TaobaoResponse> T executeExplicitUrl(String serverUrl, TaobaoRequest<T> request) {
		DefaultDingTalkClient client = new DefaultDingTalkClient(serverUrl);;
		try {
			return client.execute(request);
		} catch (ApiException e) {
			e.printStackTrace();
			return null;
		}
	}

	protected DefaultDingTalkClient client(String serverUrl){
		return new DefaultDingTalkClient(getDingtalkOapiHost() + serverUrl);
	}

	private String appendAccessToken(String serverUrl){
		String accessToken = "";
		try {
			accessToken = getAccessToken();
		} catch (ApiException e) {
			e.printStackTrace();
		}
		return serverUrl + "?access_token=" + accessToken;
	}

	protected AbstractDingtalkProperties dingtalkProperties(){
		return DINGTALK_PROPERTIES;
	}

	protected String getDingtalkOapiHost(){
		return dingtalkProperties().dingtalkOapiHost();
	}

	protected String getAccessKey(){
		return dingtalkProperties().accessKey();
	}

	protected String getAccessSecret(){
		return dingtalkProperties().accessSecret();
	}

	protected Long getAgentId(){
		return dingtalkProperties().agentId();
	}

	protected String getAesKey(){
		return dingtalkProperties().aesKey();
	}

	protected String getToken(){
		return dingtalkProperties().token();
	}

	protected void printJSONString(TaobaoResponse response){
		System.out.println(JSON.toJSONString(response, PrettyFormat));
	}

}
