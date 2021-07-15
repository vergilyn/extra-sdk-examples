package com.vergilyn.examples.alibaba.dingtalk.scene;

import com.dingtalk.api.request.OapiImChatScencegroupMessageSendV2Request;
import com.dingtalk.api.response.OapiImChatScencegroupMessageSendV2Response;
import com.vergilyn.examples.alibaba.dingtalk.AbstractDingTalkClientTestng;

import org.testng.annotations.Test;

public class SceneRobotTestng extends AbstractDingTalkClientTestng {

	/**
	 * <a href="https://developers.dingtalk.com/document/chatgroup/send-a-dingtalk-robot-message">发送群助手消息</a>
	 */
	@Test
	public void sendRobotMessage(){
		String apiUrl = "/topapi/im/chat/scencegroup/message/send_v2";


		OapiImChatScencegroupMessageSendV2Request request = new OapiImChatScencegroupMessageSendV2Request();
		request.setTargetOpenConversationId(dingtalkProperties().openConversationId());
		request.setMsgTemplateId("inner_app_template_markdown");

		request.setIsAtAll(false);

		// 消息模板内容替换参数，普通文本类型。
		request.setMsgParamMap("{\"title\":\"测试\",\"markdown_content\":\"# 测试内容 \n @180xxxx3120\"}");

		request.setRobotCode("required");

		request.setAtMobiles(dingtalkProperties().moblie());

		OapiImChatScencegroupMessageSendV2Response response = execute(apiUrl, request);
		printJSONString(response);
	}
}
