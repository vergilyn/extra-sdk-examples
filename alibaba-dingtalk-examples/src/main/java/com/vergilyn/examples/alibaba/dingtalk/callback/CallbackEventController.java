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
		if (EVENT_CHECK_URL.equals(eventType)){
			Map<String, String> result = crypto.getEncryptedMap(SUCCESS);
			return JSON.toJSONString(result);
		}

		return null;
	}
}
