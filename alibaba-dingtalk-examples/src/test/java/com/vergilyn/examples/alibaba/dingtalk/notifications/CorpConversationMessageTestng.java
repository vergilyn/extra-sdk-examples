package com.vergilyn.examples.alibaba.dingtalk.notifications;

import java.time.LocalDateTime;

import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.request.OapiMessageCorpconversationRecallRequest;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.dingtalk.api.response.OapiMessageCorpconversationRecallResponse;
import com.vergilyn.examples.alibaba.dingtalk.AbstractDingTalkClientTestng;

import org.testng.annotations.Test;
import org.testng.collections.Lists;

import static com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request.ActionCard;
import static com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request.BtnJsonList;
import static com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request.Link;
import static com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request.Msg;
import static com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request.Text;

/**
 * 工作通知消息是以企业工作通知会话中某个微应用的名义推送到员工的通知消息，例如生日祝福、入职提醒等等。
 *
 * @see <a href="https://ding-doc.dingtalk.com/doc#/serverapi2/pgoxpy">工作通知消息</a>
 */
public class CorpConversationMessageTestng extends AbstractDingTalkClientTestng {

	/**
	 * 发送工作通知返回的taskId，可以用于“消息撤回”、“查询工作通知消息的发送结果”等
	 */
	private Long taskId;

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
	@Test
	public void corpConversationMsg() {
		String serverUrl = "/topapi/message/corpconversation/asyncsend_v2";
		LocalDateTime now = LocalDateTime.now();

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

		request.setMsg(buildSingleActionCardMsg(now));

		OapiMessageCorpconversationAsyncsendV2Response response = execute(serverUrl, request);

		this.taskId = response.getTaskId();

		printJSONString(response);
	}

	/**
	 * `task_id`在发送工作通知时返回。（后续貌似没有接口可以获取到）
	 *
	 * <p>
	 *   撤回后，dingtalk没有留痕。（没有“xx 撤回了一条消息”）
	 * </p>
	 * @see <a href="https://ding-doc.dingtalk.com/doc#/serverapi2/pgoxpy/hYyV8">工作通知消息撤回</a>
	 * @see #corpConversationMsg()
	 */
	@Test(dependsOnMethods = "corpConversationMsg")
	public void recallCorpConversationMsg(){
		org.springframework.util.Assert.notNull(this.taskId);

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
		 * 描述：钉钉返回的任务id。仅支持查询24小时内的任务id
		 */
		request.setMsgTaskId(this.taskId);

		OapiMessageCorpconversationRecallResponse response = execute(serverUrl, request);
		printJSONString(response);
	}

	/**
	 * @see <a href="https://developers.dingtalk.com/document/app/message-types-and-data-format/title-dfs-oxn-29n">文本消息</a>
	 */
	private Msg buildTextMsg(LocalDateTime date){
		Text text = new Text();
		text.setContent("vergilyn 测试API异步发送工作通知消息（text类型）" + date);

		Msg msg = new Msg();
		msg.setMsgtype("text");
		msg.setText(text);

		return msg;
	}

	/**
	 * @see <a href=https://developers.dingtalk.com/document/app/message-types-and-data-format/title-48c-gj4-sbp">链接消息</a>
	 */
	private Msg buildLinkMsg(LocalDateTime date){
		Link link = new Link();
		/* 类型：String
		 * 必填：是
		 * 描述：消息标题，建议100字符以内
		 */
		link.setTitle("api link消息测试-" + date);

		/* 类型：String
		 * 必填：是
		 * 描述：消息描述，建议500字符以内
		 */
		link.setText("vergilyn 测试API异步发送工作通知消息（link类型）");

		/* 类型：String
		 * 必填：是
		 * 描述：消息点击链接地址，当发送消息为小程序时支持小程序跳转链接
		 */
		link.setMessageUrl("https://ding-doc.dingtalk.com/doc#/serverapi2/pgoxpy");

		/* 类型：String
		 * 必填：是
		 * 描述：图片地址。可以通过媒体文件接口上传图片获取。
		 */
		link.setPicUrl("@lALOACZwe2Rk");

		Msg msg = new Msg();
		msg.setMsgtype("link");
		msg.setLink(link);

		return msg;
	}

	/**
	 * 整体跳转ActionCard样式，支持一个点击Action，必须传入参数 single_title 和 single_url。
	 *
	 * @see <a href="https://developers.dingtalk.com/document/app/message-types-and-data-format/title-qbr-ab2-nks">卡片消息</a>
	 */
	private Msg buildSingleActionCardMsg(LocalDateTime date){
		ActionCard card = new ActionCard();

		card.setTitle("vergilyn 整体跳转ActionCard样式");
		card.setMarkdown("支持**markdown**格式的正文内容 , > " + date);
		card.setSingleTitle("single_title查看详情");
		card.setSingleUrl("https://open.dingtalk.com");

		Msg msg = new Msg();
		msg.setMsgtype("action_card");
		msg.setActionCard(card);

		return msg;
	}

	/**
	 * 独立跳转ActionCard样式，支持多个点击Action，必须传入参数 btn_orientation 和 btn_json_list。
	 *
	 * @see <a href="https://developers.dingtalk.com/document/app/message-types-and-data-format/title-qbr-ab2-nks">卡片消息</a>
	 */
	private Msg buildMultiActionCardMsg(LocalDateTime date){
		ActionCard card = new ActionCard();

		card.setTitle("vergilyn 独立跳转ActionCard样式");
		card.setMarkdown("支持**markdown**格式的正文内容, > " + date);

		/* 类型：String
		 * 必填：否
		 * 描述：使用独立跳转ActionCard样式时的按钮排列方式：
		 *      0：竖直排列
		 *      1：横向排列必须与btn_json_list同时设置。
		 */
		card.setBtnOrientation("1");

		BtnJsonList btn1 = new BtnJsonList();
		btn1.setTitle("按钮A");
		btn1.setActionUrl("https://www.taobao.com");

		BtnJsonList btn2 = new BtnJsonList();
		btn2.setTitle("按钮B");
		btn2.setActionUrl("https://www.tmall.com");

		card.setBtnJsonList(Lists.newArrayList(btn1, btn2));

		Msg msg = new Msg();
		msg.setMsgtype("action_card");
		msg.setActionCard(card);

		return msg;
	}
}
