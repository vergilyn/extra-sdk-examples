package com.vergilyn.examples.alibaba.dingtalk.callback;

import java.util.Arrays;

import com.dingtalk.api.request.OapiCallBackRegisterCallBackRequest;
import com.dingtalk.api.response.OapiCallBackRegisterCallBackResponse;
import com.vergilyn.examples.alibaba.dingtalk.AbstractDingTalkClientTestng;
import com.vergilyn.examples.alibaba.dingtalk.utils.DingCallbackCrypto;

import org.junit.jupiter.api.Test;

import static com.vergilyn.examples.alibaba.dingtalk.DingtalkApplication.DINGTALK_PROPERTIES;

/**
 * @see <a href="https://ding-doc.dingtalk.com/document#/org-dev-guide/callback-overview">业务事件回调</a>
 * @see <a href="https://github.com/open-dingtalk/dingtalk-callback-Crypto">钉钉回调加解密类库和对应demo</a>
 */
public class CallbackTestng extends AbstractDingTalkClientTestng {

	/**
	 * @see CallbackEventController
	 * @see <a href="https://ding-doc.dingtalk.com/document#/org-dev-guide/registers-event-callback-interfaces">注册事件回调接口</a>
	 */
	@Test
	public void registersEventCallbackInterface(){
		String serverUrl = "https://oapi.dingtalk.com/call_back/register_call_back";

		OapiCallBackRegisterCallBackRequest request = new OapiCallBackRegisterCallBackRequest();
		// 接收事件回调的url，必须是公网可以访问的url地址
		request.setUrl(DINGTALK_PROPERTIES.getCallbackDomain() + "/callback/event-receive");

		// 数据加密密钥。用于回调数据的加密，长度固定为43个字符，从a-z, A-Z, 0-9共62个字符中选取,您可以随机生成，ISV(服务提供商)推荐使用注册套件时填写的EncodingAESKey
		request.setAesKey(DINGTALK_PROPERTIES.getAesKey());

		// 加解密需要用到的token，ISV(服务提供商)推荐使用注册套件时填写的token，普通企业可以随机填写
		request.setToken(DINGTALK_PROPERTIES.getToken());

		// 需要监听的事件类型
		request.setCallBackTag(Arrays.asList("bpms_task_change", "bpms_instance_change"));

		OapiCallBackRegisterCallBackResponse response = execute(serverUrl, request);

		printJSONString(response);
	}

	@Test
	public void generator(){
		String aesKey = DingCallbackCrypto.Utils.getRandomStr(43);
		System.out.println("aesKey >>>> " + aesKey);

		String token = DingCallbackCrypto.Utils.getRandomStr(32);
		System.out.println("token >>>> " + token);
	}
}
