package com.vergilyn.examples.alibaba.dingtalk.scene;

import com.dingtalk.api.request.OapiImChatScenegroupCreateRequest;
import com.dingtalk.api.response.OapiImChatScenegroupCreateResponse;
import com.vergilyn.examples.alibaba.dingtalk.AbstractDingTalkClientTestng;

import org.testng.annotations.Test;

public class SceneGroupTestng extends AbstractDingTalkClientTestng {

	/**
	 * @see <a href="https://developers.dingtalk.com/document/chatgroup/fill-in-a-group-template">申请群模板</a>
	 * @see <a href="https://developers.dingtalk.com/document/chatgroup/create-a-scene-group-v2">创建场景群</a>
	 */
	@Test
	public void createSceneGroup(){
		String apiUrl = "/topapi/im/chat/scenegroup/create";

		OapiImChatScenegroupCreateRequest request = new OapiImChatScenegroupCreateRequest();
		// 群主的userid
		request.setOwnerUserId(dingtalkProperties().dingtalkUserId());

		// 群成员userid列表
		request.setUserIds(dingtalkProperties().dingtalkUserId());

		// 建群去重的业务ID
		request.setUuid("vergilyn409839163");

		// 群名称
		request.setTitle("vergilyn场景群");

		// 群模板ID，详见 `申请群模板`
		request.setTemplateId(dingtalkProperties().sceneTemplateId());

		OapiImChatScenegroupCreateResponse response = execute(apiUrl, request);

		printJSONString(response);
		System.out.println(" >>>>>>>>> OpenConversationId: " + response.getResult().getOpenConversationId());
	}
}
