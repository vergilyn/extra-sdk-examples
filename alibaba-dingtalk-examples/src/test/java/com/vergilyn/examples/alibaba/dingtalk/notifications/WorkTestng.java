package com.vergilyn.examples.alibaba.dingtalk.notifications;

import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.vergilyn.examples.alibaba.dingtalk.AbstractDingTalkClientTestng;

import org.testng.annotations.Test;

/**
 * <a href="https://ding-doc.dingtalk.com/document#/org-dev-guide/message-notification-overview">消息通知概述</a>
 */
public class WorkTestng extends AbstractDingTalkClientTestng {

	/**
	 * <a href="https://ding-doc.dingtalk.com/document#/org-dev-guide/send-work-notifications">发送工作通知</a>
	 */
	@Test
	public void sendWorkNotifications(){
		String serverUrl = "/topapi/message/corpconversation/asyncsend_v2";

		OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();

		/* 类型：Number
		 * 是否必填：是
		 * 示例值：123
		 * 描述：发送消息时使用的微应用的ID。可在开发者后台的应用详情页面查看。
		 */
		request.setAgentId(getAgentId());

		/* 类型：String
		 * 是否必填：否
		 * 示例值：123,345
		 * 描述：接收者的部门id列表，最大列表长度20。接收者是部门ID下包括子部门下的所有用户。
		 */
		request.setUseridList(null);

		/* 类型：Boolean
		 * 是否必填：否
		 * 示例值：false
		 * 描述：是否发送给企业全部用户。（当设置为false时必须指定userid_list或dept_id_list其中一个参数的值。）
		 */
		request.setToAllUser(true);

		/* 类型：JSON Object
		 * 是否必填：是
		 * 示例值：{"msgtype":"text","text":{"content":"请提交日报。"}}
		 * 描述：消息内容，最长不超过2048个字节。
		 */
		request.setMsg("{\"msgtype\":\"text\",\"text\":{\"content\":\"vergilyn，2020-11-20个人测试工作消息发送。\"}}");

		OapiMessageCorpconversationAsyncsendV2Response response = execute(serverUrl, request);

		printJSONString(response);
	}
}
