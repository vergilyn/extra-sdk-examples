package com.vergilyn.examples.alibaba.dingtalk.callback;

import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.vergilyn.examples.alibaba.dingtalk.utils.DingCallbackCrypto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/callback")
public class CallbackEventController {
	private static final String EVENT_CHECK_URL = "check_url";
	private static final String SUCCESS = "success";
	@Autowired
	private DingCallbackCrypto crypto;

	/**
	 * <pre>
	 *   curl http://127.0.0.1:8080/callback/event-receive?signature=&msg_signature=&timestamp=1606199502188&nonce=fDf6dLbI
	 *   Content-Type:application/json
	 *   body:
	 *      {"encrypt":"g4jgOUcaIApBmnO...."}
	 * </pre>
	 *
	 * <p>
	 *   针对所有的回调事件，在收到事件推送后，务必返回包含经过加密的字符串“success”的json数据，只有返回了对应的json数据，钉钉才会判断此事件推送成功
	 * </p>
	 *
	 * @see <a href="https://ding-doc.dingtalk.com/doc#/serverapi2/pwz3r5">回调管理</a>
	 * @see <a href="https://ding-doc.dingtalk.com/doc#/serverapi2/lwwrcu">审批事件回调</a>
	 */
	@RequestMapping("/event-receive")
	@ResponseBody
	public String eventReceive(
			@RequestParam String signature,
			@RequestParam("msg_signature") String msgSignature,
			String timestamp,
			String nonce,
			@RequestBody String requestBody) throws DingCallbackCrypto.DingTalkEncryptException {

		System.out.println("signature >>>> " + signature);
		System.out.println("msgSignature >>>> " + msgSignature);
		System.out.println("timestamp >>>> " + timestamp);
		System.out.println("nonce >>>> " + nonce);
		System.out.println("requestBody >>>> " + requestBody);

		JSONObject jsonObject = JSON.parseObject(requestBody);
		String encrypt = jsonObject.getString("encrypt");
		System.out.println("encryptMsg >>>> " + encrypt);

		String decryptMsg = crypto.getDecryptMsg(msgSignature, timestamp, nonce, encrypt);
		System.out.println("decryptMsg >>>> " + decryptMsg);

		JSONObject decryptJson = JSON.parseObject(decryptMsg);
		String eventType = decryptJson.getString("EventType");
		System.out.println("EventType >>>> " + eventType);

		// 根据event-type进行业务处理。建议async处理业务或者写入MQ。

		// 返回包含经过加密的字符串“success”的json数据
		Map<String, String> result = crypto.getEncryptedMap(SUCCESS);
		return JSON.toJSONString(result);
	}
}
