package com.vergilyn.examples.alibaba.dingtalk;

import com.alibaba.fastjson.JSON;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.taobao.api.ApiException;
import com.taobao.api.TaobaoRequest;
import com.taobao.api.TaobaoResponse;

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
	 * <p>
	 *   FIXME 2020-11-24,vergilyn 正式项目中可以通过redis/cache维护access_token，避免无意义的请求
	 * </p>
	 * @see <a href="https://ding-doc.dingtalk.com/doc#/serverapi2/eev437">获取access_token</a>
	 */
	protected String getAccessToken() throws ApiException {
		String serverUrl = "https://oapi.dingtalk.com/gettoken";

		OapiGettokenRequest request = new OapiGettokenRequest();
		request.setAppkey(getAccessKey());
		request.setAppsecret(getAccessSecret());

		OapiGettokenResponse response = client(serverUrl).execute(request, getAccessKey(), getAccessSecret());
		return response.getAccessToken();
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

	protected DefaultDingTalkClient client(String serverUrl){
		return new DefaultDingTalkClient(serverUrl);
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

	protected String getAccessKey(){
		return DINGTALK_PROPERTIES.getAccessKey();
	}

	protected String getAccessSecret(){
		return DINGTALK_PROPERTIES.getAccessSecret();
	}

	protected Long getAgentId(){
		return DINGTALK_PROPERTIES.getAgentId();
	}

	protected void printJSONString(TaobaoResponse response){
		System.out.println(JSON.toJSONString(response, PrettyFormat));
	}

}
