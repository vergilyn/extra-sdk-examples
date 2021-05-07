package com.vergilyn.examples.alibaba.dingtalk.notifications;

import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request.Msg;
import com.dingtalk.api.request.OapiMessageCorpconversationRecallRequest;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.dingtalk.api.response.OapiMessageCorpconversationRecallResponse;
import com.vergilyn.examples.alibaba.dingtalk.AbstractDingTalkClientTestng;

/**
 * 工作通知消息是以企业工作通知会话中某个微应用的名义推送到员工的通知消息，例如生日祝福、入职提醒等等。
 * @author vergilyn
 * @since 2021-05-07
 *
 * @see <a href="https://developers.dingtalk.com/document/app/asynchronous-sending-of-enterprise-session-messages">发送工作通知</a>
 */
public abstract class AbstractAsyncSendEnterpriseMessages extends AbstractDingTalkClientTestng {

	/**
	 * 异步发送工作通知消息（实际情况 近实时。）
	 *
	 * <p>
	 *   重要：返回结果包含task_id，可以用于“撤回”。
	 * </p>
	 *
	 * <p>
	 * 发送工作通知消息需要注意以下事项： <br/>
	 * 1. (重要)同一个应用相同消息的内容同一个用户一天只能接收一次。<br/>
	 * 2. 同一个应用给同一个用户发送消息，企业内部开发方式一天不得超过500次。<br/>
	 * 3. 通过设置to_all_user参数全员推送消息，一天最多3次。<br/>
	 * 4. 超出以上限制次数后，接口返回成功，但用户无法接收到。详细的限制说明，请参考“工作通知消息的限制”。<br/>
	 * 5. 该接口是异步发送消息，接口返回成功并不表示用户一定会收到消息，需要通过“查询工作通知消息的发送结果”接口查询是否给用户发送成功。<br/>
	 * </p>
	 *
	 * @see <a href="https://developers.dingtalk.com/document/app/asynchronous-sending-of-enterprise-session-messages">异步发送工作通知消息</a>
	 */
	protected OapiMessageCorpconversationAsyncsendV2Response asyncSend(Msg msg){
		String serverUrl = "/topapi/message/corpconversation/asyncsend_v2";

		OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();

		/* 类型：Number
		 * 必填：是
		 * 示例：123
		 * 描述：发送消息时使用的微应用的ID。可在开发者后台的应用详情页面查看。
		 */
		request.setAgentId(getAgentId());

		/* 类型：String
		 * 必填：否
		 * 示例：123,345
		 * 描述：接收者的userid列表，最大用户列表长度100。
		 */
		request.setUseridList(getDingtalkProperties().getDingtalkUserId());

		/* 类型：String
		 * 必填：否
		 * 示例：123,345
		 * 描述：接收者的部门id列表，最大列表长度20。接收者是部门ID下包括子部门下的所有用户。
		 */
		// request.setDeptIdList("37xxxx095,1");

		/* 类型：Boolean
		 * 必填：否
		 * 示例：false
		 * 描述：是否发送给企业全部用户。（当设置为false时必须指定userid_list或dept_id_list其中一个参数的值。）
		 */
		request.setToAllUser(false);

		/* 类型：JSON Object
		 * 是否必填：是
		 * 示例值：{"msgtype":"text","text":{"content":"请提交日报。"}}
		 * 描述：消息内容，最长不超过2048个字节。
		 */
		// request.setMsg("{\"msgtype\":\"text\",\"text\":{\"content\":\"vergilyn，" + now + "测试API `异步发送工作通知消息`。\"}}");

		request.setMsg(msg);

		return execute(serverUrl, request);
	}

	/**
	 * 1) `task_id`在发送工作通知时返回。（后续貌似没有接口可以获取到）<br/>
	 * 2)仅支持撤回24小时内的工作消息通知。<br/>
	 * <p>
	 *   撤回后，dingtalk没有留痕。（没有“xx 撤回了一条消息”）
	 * </p>
	 * @see <a href="https://developers.dingtalk.com/document/app/notification-of-work-withdrawal">工作通知消息撤回</a>
	 * @see #asyncSend(Msg)
	 */
	protected OapiMessageCorpconversationRecallResponse recall(Long taskId){
		String serverUrl = "/topapi/message/corpconversation/recall";

		OapiMessageCorpconversationRecallRequest request = new OapiMessageCorpconversationRecallRequest();

		/* 类型：Number
		 * 必填：是
		 * 示例：123
		 * 描述：应用的agentid
		 */
		request.setAgentId(getAgentId());

		/* 类型：Number
		 * 必填：是
		 * 示例：456
		 * 描述：钉钉返回的任务id。仅支持撤回24小时内的工作消息通知。
		 */
		request.setMsgTaskId(taskId);

		return execute(serverUrl, request);

	}
}
