package com.vergilyn.examples.alibaba.dingtalk.callback;

import java.util.Arrays;

import com.dingtalk.api.request.OapiCallBackGetCallBackFailedResultRequest;
import com.dingtalk.api.request.OapiCallBackGetCallBackRequest;
import com.dingtalk.api.request.OapiCallBackRegisterCallBackRequest;
import com.dingtalk.api.request.OapiCallBackUpdateCallBackRequest;
import com.dingtalk.api.response.OapiCallBackGetCallBackFailedResultResponse;
import com.dingtalk.api.response.OapiCallBackGetCallBackResponse;
import com.dingtalk.api.response.OapiCallBackRegisterCallBackResponse;
import com.dingtalk.api.response.OapiCallBackUpdateCallBackResponse;
import com.vergilyn.examples.alibaba.dingtalk.AbstractDingTalkClientTestng;

import org.testng.annotations.Test;


/**
 * VQUESTION 2020-11-24 回调失败，重复回调的间隔时长和次数？
 * @see <a href="https://ding-doc.dingtalk.com/document#/org-dev-guide/callback-overview">业务事件回调</a>
 * @see <a href="https://github.com/open-dingtalk/dingtalk-callback-Crypto">钉钉回调加解密类库和对应demo</a>
 */
public class CallbackTestng extends AbstractDingTalkClientTestng {
	private final String callbackUrl = dingtalkProperties().callbackDomain() + "/callback/event-receive";

	/**
	 * VQUESTION 2020-11-24,vergilyn 是否需要避免多次registry？
	 * <pre>
	 *   重复注册时（与aes_key、token无关，与access_token有关）：
	 *   {
	 *      "errcode":71006,
	 *  	"errmsg":"回调地址已经存在"
	 *   }
	 * </pre>
	 *
	 * @see CallbackEventController
	 * @see <a href="https://ding-doc.dingtalk.com/document#/org-dev-guide/registers-event-callback-interfaces">注册事件回调接口</a>
	 */
	@Test
	public void registersEventCallbackInterface(){
		String serverUrl = "/call_back/register_call_back";

		OapiCallBackRegisterCallBackRequest request = new OapiCallBackRegisterCallBackRequest();
		// 接收事件回调的url，必须是公网可以访问的url地址
		request.setUrl(callbackUrl);

		// 数据加密密钥。用于回调数据的加密，长度固定为43个字符，从a-z, A-Z, 0-9共62个字符中选取,您可以随机生成，ISV(服务提供商)推荐使用注册套件时填写的EncodingAESKey
		request.setAesKey(getAesKey());

		// 加解密需要用到的token，ISV(服务提供商)推荐使用注册套件时填写的token，普通企业可以随机填写
		request.setToken(getToken());

		// 需要监听的事件类型
		request.setCallBackTag(Arrays.asList("bpms_task_change", "bpms_instance_change"));

		OapiCallBackRegisterCallBackResponse response = execute(serverUrl, request);

		printJSONString(response);
	}

	/**
	 * @see <a href="https://ding-doc.dingtalk.com/doc#/serverapi2/pwz3r5/2ec909c1">更新事件回调接口</a>
	 */
	@Test
	public void updateCallback(){
		String serverUrl = "/call_back/update_call_back";

		OapiCallBackUpdateCallBackRequest request = new OapiCallBackUpdateCallBackRequest();
		request.setUrl(callbackUrl);
		request.setAesKey(getAesKey());
		request.setToken(getToken());
		request.setCallBackTag(Arrays.asList("bpms_task_change", "bpms_instance_change"));

		OapiCallBackUpdateCallBackResponse response = execute(serverUrl, request);

		printJSONString(response);
	}

	/**
	 *
	 * @see <a href="https://ding-doc.dingtalk.com/doc#/serverapi2/pwz3r5/1cd40e12">查询事件回调接口</a>
	 */
	@Test
	public void getCallback(){
		String serverUrl = "/call_back/get_call_back";

		OapiCallBackGetCallBackRequest request = new OapiCallBackGetCallBackRequest();
		request.setHttpMethod("GET");
		OapiCallBackGetCallBackResponse response = execute(serverUrl, request);

		printJSONString(response);
	}

	/**
	 * 钉钉服务器给回调接口推送时，有可能因为各种原因推送失败(比如网络异常)，此时钉钉将保留此次变更事件。
	 * 用户可以通过此回调接口获取推送失败的变更事件。
	 *
	 * <p>事件列表，一次最多200个</p>
	 *
	 * <p>
	 *   重要：调用该API后，dingtalk会立即删除该次的数据！<br/>
	 *   返回的数据包含很多的event（不支持查询指定的event），并且数据中还包含`回调失败数据所属corpid`。
	 * </p>
	 *
	 * @see <a href="https://ding-doc.dingtalk.com/doc#/serverapi2/pwz3r5/e45c03d0">获取回调失败的结果</a>
	 */
	@Test
	public void getCallbackFailedResult(){
		String serverUrl = "/call_back/get_call_back_failed_result";

		OapiCallBackGetCallBackFailedResultRequest request = new OapiCallBackGetCallBackFailedResultRequest();
		request.setHttpMethod("GET");
		OapiCallBackGetCallBackFailedResultResponse response = execute(serverUrl, request);

		printJSONString(response);
	}
}
