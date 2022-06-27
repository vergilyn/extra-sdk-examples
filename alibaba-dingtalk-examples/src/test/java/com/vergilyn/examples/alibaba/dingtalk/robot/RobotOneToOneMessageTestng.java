package com.vergilyn.examples.alibaba.dingtalk.robot;

import com.alibaba.fastjson.JSON;
import com.aliyun.dingtalkrobot_1_0.Client;
import com.aliyun.dingtalkrobot_1_0.models.BatchSendOTOHeaders;
import com.aliyun.dingtalkrobot_1_0.models.BatchSendOTORequest;
import com.aliyun.dingtalkrobot_1_0.models.BatchSendOTOResponse;
import com.aliyun.dingtalkrobot_1_0.models.BatchSendOTOResponseBody;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;
import lombok.SneakyThrows;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * <p> 消息类型，参考：<a href="https://open.dingtalk.com/document/group/message-types-and-data-format">
 *     消息类型和数据格式</a>
 *
 * @author vergilyn
 * @since 2022-06-27
 */
public class RobotOneToOneMessageTestng extends AbstractRobotEnterprise{

	private static final AtomicInteger index = new AtomicInteger(0);

	/**
	 * <p> 1. <a href="https://open.dingtalk.com/document/orgapp-server/dingtalk-openapi-overview">如何调用新版服务端API</a>
	 * <p> 2. 新版服务端API参数，还是参考旧文档：
	 *          <a href="https://open.dingtalk.com/document/group/chatbots-send-one-on-one-chat-messages-in-batches">钉钉单聊机器人，批量发送单聊消息</a>
	 */
	@Test
	public void newAPI(){

	}

	/**
	 * <p> 1. <a href="https://open.dingtalk.com/document/group/chatbots-send-one-on-one-chat-messages-in-batches">
	 *     钉钉单聊机器人，批量发送单聊消息</a>
	 *
	 * <p> 2. 消息类型，参考：<a href="https://open.dingtalk.com/document/group/message-types-and-data-format">
	 *     消息类型和数据格式</a>
	 *
	 *
	 * <p><h3>备注</h3>
	 * <p> 1. 个人感觉：这SDK不太友好，不如直接 http请求 来的简单。
	 * <p> 2. 验证了下：没有每分钟发多少条限制，也没有不能发送重复内容的限制。
	 */
	@RepeatedTest(2)
	@SneakyThrows
	public void api(){
		com.aliyun.teaopenapi.models.Config config = new Config();
		// 不支持 `http` 协议
		config.protocol = "https";

		com.aliyun.dingtalkrobot_1_0.Client client = new Client(config);

		BatchSendOTOHeaders requestHeader = new BatchSendOTOHeaders();
		requestHeader.xAcsDingtalkAccessToken = getRobotEnterpriseAccessToken();

		RuntimeOptions runtimeOptions = new RuntimeOptions();

		String markdownLF = "  \n  ";
		String content = "##【markdown】公司内部机器人，单聊消息 >>>> " + markdownLF
				+ "  序号：" + index.incrementAndGet() + markdownLF
				+ "  时间：" + LocalDateTime.now() + markdownLF
				+ "  链接：[百度 baidu.con](https://www.baidu.com)";

		BatchSendOTORequest request = new BatchSendOTORequest()
				// `RobotCode`机器人的编码，即为开发者后台创建的企业内部机器人应用的Appkey值
				.setRobotCode(dingtalkProperties().robotEnterpriseAppKey())
				// 用户的userid。每次最多传20个userid值
				.setUserIds(java.util.Arrays.asList(
						dingtalkProperties().dingtalkUserId()
				))
				.setMsgKey("sampleMarkdown")

				// title 显示在 左侧聊天列表。
				.setMsgParam("{\"text\": \"" + content + "\",\"title\": \"sampleMarkdown title\"}");

		BatchSendOTOResponse response = client.batchSendOTOWithOptions(request, requestHeader, runtimeOptions);

		BatchSendOTOResponseBody responseBody = response.getBody();

		System.out.printf("response >>>> "
				                  + "\n\tprocessQueryKey(消息id): %s, "
				                  + "\n\tinvalidStaffIdList(无效的用户userid列表): %s, "
				                  + "\n\tflowControlledStaffIdList(被限流的userid列表): %s"
					, responseBody.getProcessQueryKey()
					, JSON.toJSONString(responseBody.getInvalidStaffIdList())
					, JSON.toJSONString(responseBody.getFlowControlledStaffIdList()));
	}
}
