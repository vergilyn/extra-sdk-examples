package com.vergilyn.examples.alipay.crypto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class MessageDigestUtilsTests {

	private final String _appKey = "";
	private final String _appSecret = "";

	/**
	 * <pre>
	 * {
	 *     "requestId": "202212150001",
	 *     "code": "200",
	 *     "success": true,
	 *     "msg": "SUCCESS"
	 * }
	 * </pre>
	 */
	@Test
	public void test(){
		String timeMillis = System.currentTimeMillis() + "";

		String requestBody = textLinkBody();
		// String requestBody = textBody();

		String checkSum = MessageDigestUtils.getCheckSum(requestBody, _appSecret, timeMillis);

		System.out.println(">>>> ");
		System.out.println("\t requestBody: " + requestBody);
		System.out.println("\t time: " + timeMillis);
		System.out.println("\t checkSum: " + checkSum);
	}

	/**
	 * <pre>
	 *  {
	 *     "requestId": "202212150001",
	 *     "success": false,
	 *     "code": "INVALID_SIGN",
	 *     "resultMessage": "Failed to validate api signature,checksum not match,appkey=51d71f6c4c567cb42bb81a3edf272cbe,orgignal checksum=ad24c60229f9c3dc7123576bc18bb0c61486da42,current=799ef91f35e75328569326658236290b94a306b8,time=1671105244111,body={\"bizMemo\":\"bizMemo, 聊天简短描述\",\"templateData\":\"{\\\"m\\\":\\\"【测试】文本消息222\\\"}\",\"link\":\"https://www.baidu.com\",\"sessionId\":\"CLOUDSERVICEAPP_2088202215895635_2021002190683179\",\"templateCode\":\"11\"}"
	 * }
	 * </pre>
	 * 怀疑是 固定了body的 key顺序！
	 *
	 * @return
	 */
	private String textBody(){
		// return "{"
		// 		+ "\"sessionId\":\"CLOUDSERVICEAPP_2088202215895635_2021002190683179\","
		// 		+ "\"templateCode\":\"11\","
		// 		+ "\"templateData\":\"{\\\"m\\\":\\\"【测试】文本消息222\\\"}\","
		// 		+ "\"link\":\"https://www.baidu.com\","
		// 		+ "\"bizMemo\":\"bizMemo, 聊天简短描述\""
		// 		+ "}";

		// body的key顺序调整跟错误返回一样
		return "{"
				+ "\"bizMemo\":\"bizMemo, 聊天简短描述\","
				+ "\"templateData\":\"{\\\"m\\\":\\\"【测试】文本消息" + LocalDateTime.now() + "\\\"}\","
				+ "\"link\":\"https://www.baidu.com\","
				+ "\"sessionId\":\"CLOUDSERVICEAPP_2088202215895635_2021002190683179\","
				+ "\"templateCode\":\"11\""
				+ "}";
	}

	/**
	 * <pre>
	 * {
	 *     "requestId": "202212150002",
	 *     "success": false,
	 *     "code": "INTERNAL_ERROR",
	 *     "resultMessage": "INTERNAL_ERROR"
	 * }
	 * </pre>
	 */
	private String textLinkBody(){

		// body的key顺序调整跟错误返回一样
		return "{"
				+ "\"bizMemo\":\"bizMemo, 聊天简短描述\","
				+ "\"templateData\":\"{\\\"m\\\":\\\"【测试】文本消息 " + LocalDateTime.now() + ", <a href=\\\"https://www.baidu.com\\\">点击查看</a>\\\"}\","
				+ "\"link\":\"https://www.baidu.com\","
				+ "\"sessionId\":\"CLOUDSERVICEAPP_2088202215895635_2021002190683179\","
				+ "\"templateCode\":\"410\""
				+ "}";
	}
}
