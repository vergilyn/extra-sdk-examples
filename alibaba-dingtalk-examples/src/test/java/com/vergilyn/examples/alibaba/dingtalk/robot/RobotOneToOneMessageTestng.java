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
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
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
	 * <p> 3. <a href="https://open.dingtalk.com/document/orgapp-server/invocation-frequency-limit">调用频率限制（有效期至2022年10月31日）</a>
	 * <blockquote>
	 *     i) 每个机器人每分钟最多发送20条。
	 *     ii) 如果超过20条，会限流10分钟。
	 * </blockquote>
	 * <br/> (这个貌似指的是 群聊消息。单聊没这个限制)
	 *
	 * <p><h3>备注</h3>
	 * <p> 1. 个人感觉：这SDK不太友好，不如直接 http请求 来的简单。
	 * <p> 2. 验证了下：没有每分钟发多少条限制，也没有不能发送重复内容的限制。
	 */
	@RepeatedTest(1)
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
		String content = "## 【markdown】公司内部机器人，单聊消息 >>>> " + markdownLF
				+ "  序号：" + index.incrementAndGet() + markdownLF
				+ "  时间：" + LocalDateTime.now() + markdownLF
				+ "  链接：[百度 baidu.con](https://www.baidu.com)  <https://www.baidu.com>";

		BatchSendOTORequest request = new BatchSendOTORequest()
				// `RobotCode`机器人的编码，即为开发者后台创建的企业内部机器人应用的Appkey值
				.setRobotCode(dingtalkProperties().robotEnterpriseAppKey())
				// 用户的userid。每次最多传20个userid值
				.setUserIds(java.util.Arrays.asList(
						dingtalkProperties().dingtalkUserId()
				))
				.setMsgKey("sampleActionCard")

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

	/**
	 *
	 */
	@Test
	@SneakyThrows
	public void invocationFrequencyLimit(){
		ExecutorService executor = Executors.newFixedThreadPool(10);

		String accessToken = getRobotEnterpriseAccessToken();

		AtomicInteger index = new AtomicInteger(0);
		for (int i = 0; i < 40; i++) {
			Future<?> future = executor.submit(() -> {
				Config config = new Config();
				// 不支持 `http` 协议
				config.protocol = "https";

				Client client = null;
				try {
					client = new Client(config);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}

				BatchSendOTOHeaders requestHeader = new BatchSendOTOHeaders();
				requestHeader.xAcsDingtalkAccessToken = accessToken;

				RuntimeOptions runtimeOptions = new RuntimeOptions();

				String content = String.format("%02d", index.getAndIncrement()) + "," + LocalDateTime.now();

				BatchSendOTORequest request = new BatchSendOTORequest()
						// `RobotCode`机器人的编码，即为开发者后台创建的企业内部机器人应用的Appkey值
						.setRobotCode(dingtalkProperties().robotEnterpriseAppKey())
						// 用户的userid。每次最多传20个userid值
						.setUserIds(Arrays.asList(dingtalkProperties().dingtalkUserId())).setMsgKey("sampleText")

						// title 显示在 左侧聊天列表。
						.setMsgParam("{\"content\": \"" + content + "\"}");

				try {
					BatchSendOTOResponse response = client.batchSendOTOWithOptions(request, requestHeader,
					                                                               runtimeOptions);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}

			});
		}

		executor.shutdown();

		executor.awaitTermination(2, TimeUnit.SECONDS);
	}
}
