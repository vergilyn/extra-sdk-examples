package com.vergilyn.examples.alibaba.dingtalk.notifications;

import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.dingtalk.api.response.OapiMessageCorpconversationRecallResponse;
import lombok.SneakyThrows;
import org.testng.annotations.Test;
import org.testng.collections.Lists;

import java.time.LocalDateTime;

import static com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request.*;

/**
 *
 *
 */
public class CorpConversationMessageTestng extends AbstractAsyncSendEnterpriseMessages {

	/**
	 * 发送工作通知返回的taskId，可以用于“消息撤回”、“查询工作通知消息的发送结果”等
	 */
	private Long taskId;


	@Test
	public void corpConversationMsg() {
		LocalDateTime now = LocalDateTime.now();
		Msg msg = buildTextMsg(now);

		OapiMessageCorpconversationAsyncsendV2Response response = asyncSend(msg);

		this.taskId = response.getTaskId();

		printJSONString(response);
	}


	@Test(dependsOnMethods = "corpConversationMsg")
	public void recallCorpConversationMsg(){
		org.springframework.util.Assert.notNull(this.taskId);

		OapiMessageCorpconversationRecallResponse response = recall(taskId);

		printJSONString(response);
	}

	/**
	 * @see <a href="https://developers.dingtalk.com/document/app/message-types-and-data-format/title-dfs-oxn-29n">文本消息</a>
	 */
	private Msg buildTextMsg(LocalDateTime date){
		Text text = new Text();
		text.setContent("vergilyn https://www.baidu.com 测试API异步发送工作通知消息（text类型）\n换行1111\n换行2222\n" + date);

		Msg msg = new Msg();
		msg.setMsgtype("text");
		msg.setText(text);

		return msg;
	}

	private Msg buildMarkdownMsg(LocalDateTime date){
		Markdown markdown = new Markdown();
		// title 被没有展示
		markdown.setTitle("消息系统消息发送失败");
		// 换行还是`\n`，不是 双空格
		String LF = "  \n  ";
		markdown.setText(
				"### <font color=\"blue\">消息系统消息发送失败</font>" + LF
				+ "场景ID: 123" + LF
				+ "时间：" + date + LF
				+ "原因: {\"data\":\"{\\\"ret\\\":\\\"SUCCESS\\\",\\\"data\\\":{\\\"msg_id\\\":\\\"uasg16e164620280981901\\\"}}\",\"status\":200}"
		);

		Msg msg = new Msg();
		msg.setMarkdown(markdown);
		msg.setMsgtype("markdown");

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
	@SneakyThrows
	private Msg buildSingleActionCardMsg(LocalDateTime date){
		ActionCard card = new ActionCard();

		String targetUrl = "https://www.baidu.com";

		card.setTitle("vergilyn 整体跳转ActionCard样式");
		card.setMarkdown("https://pic.cnblogs.com/avatar/1025273/20171112211439.png  支持**markdown**格式的正文内容 , > " + date);
		card.setSingleTitle("single_title查看详情");
		card.setSingleUrl(targetUrl);

		// dingtalk://dingtalkclient/page/link?url=http%3A%2F%2Fwww.dingtalk.com&pc_slide=true
		// pc_slide
		// true：表示在PC客户端侧边栏打开
		// false：表示在浏览器打开
		// String url = String.format("dingtalk://dingtalkclient/page/link?url=%s&pc_slide=false", URLEncoder.encode(targetUrl, "UTF-8"));
		// card.setSingleUrl(url);

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
