package com.vergilyn.examples.alibaba.dingtalk.robot;

import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import com.vergilyn.examples.alibaba.dingtalk.config.AbstractDingtalkProperties;
import lombok.SneakyThrows;
import org.assertj.core.util.Lists;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.vergilyn.examples.alibaba.dingtalk.config.AbstractDingtalkProperties.TEST_IMAGE_URL;

/**
 * 钉钉企业内部机器人
 *
 * @author vergilyn
 * @since 2022-04-20
 *
 * @see <a href="https://open.dingtalk.com/document/group/enterprise-created-chatbot">企业内部开发机器人</a>
 */
public class GroupMessageTestng extends AbstractRobotEnterprise {

	/**
	 * 获取 <b>已创建群</b> 的`openConversationId`（开放的群ID）步骤：
	 * <pre>
	 *   <a href="https://open.dingtalk.com/document/org-faq/group-session-management">如何获取会话的cid</a>
	 *   “针对已经存在的会话，可以使用前端api获取会话的cid，服务端接口暂时还获取不到已存在的会话cid。”
	 *
	 *   1. 利用 JS-Api-Explorer: <a href="https://open-dev.dingtalk.com/apiExplorer?#/jsapi?api=biz.chat.chooseConversationByCorpId">https://open-dev.dingtalk.com/apiExplorer?#/jsapi?api=biz.chat.chooseConversationByCorpId</a>
	 *   获取 已创建群的 chat-id。 <b>需要用到 dingtalk corp-id</b>。
	 *
	 *   2. 通过 chat-id 获取 `OpenConversationId`: <a href="https://open.dingtalk.com/document/orgapp-server/obtain-group-openconversationid">https://open.dingtalk.com/document/orgapp-server/obtain-group-openconversationid</a>
	 *
	 *   3. 在对应的群添加 该企业机器人 为群机器人。
	 * </pre>
	 *
	 * 或者，通过 webhook 发送群消息！
	 *
	 * <p> webhook 或者 open-conversation-id  这2种方式都支持`@某人 / @所有人`。
	 * <p> webhook更简单，查找webhook-accessToken也更方便。 但支持的功能有限。（2022-04-20 貌似 webhook 的功能是一样多的！）
	 * <p> 首先获取`OpenConversationId`就比较麻烦，但是 支持的功能更多！
	 * <p> API本身都不支持 批量发送相同消息到多个群。
	 *
	 * <p>
	 *
	 * <h3> <a href="https://open.dingtalk.com/document/group/message-types-and-data-format">消息类型和数据格式</a> </h3>
	 * <p> 通过接口发送机器人消息和通过Webhook发送机器人消息。不同的方式，支持的消息类型和数据格式不同。
	 * <pre>
	 *   +------------+-----+---------+
	 *   | type       | API | Webhook |
	 *   +============+=====+=========+
	 *   | Text       | V   | V       |
	 *   | Markdown   | V   | V       |
	 *   | Image      | V   | -       |
	 *   | ActionCard | V   | V       |
	 *   | FeedCard   | -   | V       |
	 *   | Link       | V   | V       |
	 *   +------------+-----+---------+
	 * </pre>
	 *
	 * @see <a href="https://open.dingtalk.com/document/group/the-robot-sends-a-group-message">发送机器人群聊消息</a>
	 * @see <a href="https://open.dingtalk.com/document/group/assign-a-webhook-url-to-an-internal-chatbot">企业内部机器人使用Webhook发送群聊消息</a>
	 * @see <a href="https://open.dingtalk.com/document/group/message-types-and-data-format">消息类型和数据格式</a>
	 * @see <a href="https://open.dingtalk.com/document/robots/customize-robot-security-settings">自定义机器人安全设置</a>
	 */
	@SneakyThrows
	@ParameterizedTest
	@ValueSource(strings = "markdown")
	public void sendGroupMessage(String msgtype){
		AbstractDingtalkProperties.WebhookConfig webhookConfig = dingtalkProperties().robotWebhook();

		OapiRobotSendRequest request = new OapiRobotSendRequest();

		if ("markdown".equalsIgnoreCase(msgtype)){
			buildMarkdown(request);
		}else if ("image".equalsIgnoreCase(msgtype)){
			buildImage(request);
		}else if ("text".equalsIgnoreCase(msgtype)){
			buildText(request);
		}

		// 貌似只有 markdown & text  才支持 `@某某`
		OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
		at.setAtMobiles(Lists.newArrayList(dingtalkProperties().moblieOther()));
		at.setAtUserIds(Lists.newArrayList(dingtalkProperties().dingtalkUserId()));
		at.setIsAtAll(false);
		request.setAt(at);

		final OapiRobotSendResponse response = executeExplicitUrl(webhookConfig.apiUrl(), request);
		printJSONString(response);
	}

	/**
	 * webhook 自身不支持发送 image的群消息，但是可以通过markdown `![图片](http://name.com/pic.jpg)` 变相实现。
	 */
	private void buildImage(OapiRobotSendRequest request){
		String markdownImage = String.format("![](%s)", TEST_IMAGE_URL);

		OapiRobotSendRequest.Markdown markdown = new OapiRobotSendRequest.Markdown();
		markdown.setTitle("图片消息");
		markdown.setText(markdownImage);

		// error: "{\"errcode\":400401,\"errmsg\":\"miss param : markdown\"}"
		// request.setMarkdown(markdownImage);
		request.setMarkdown(markdown);
		request.setMsgtype("markdown");

	}

	/**
	 * <a href="https://open.dingtalk.com/document/group/message-types-and-data-format">消息类型和数据格式</a> <br/>
	 *
	 * 目前只支持Markdown语法的子集，支持的元素如下：
	 * <pre>
	 * 标题
	 * # 一级标题
	 * ## 二级标题
	 * ### 三级标题
	 * #### 四级标题
	 * ##### 五级标题
	 * ###### 六级标题
	 *
	 * 引用
	 * > A man who stands for nothing will fall for anything.
	 *
	 * 文字加粗、斜体
	 * **bold**
	 * *italic*
	 *
	 * 链接
	 * [this is a link](https://www.dingtalk.com/)
	 *
	 * 图片
	 * ![](http://name.com/pic.jpg)
	 *
	 * 无序列表
	 * - item1
	 * - item2
	 *
	 * 有序列表
	 * 1. item1
	 * 2. item2
	 * </pre>
	 *
	 * <p>
	 * 换行语法：`\n`，或者 双空格`  `都不行。 有效写法`  \n  `
	 */
	private void buildMarkdown(OapiRobotSendRequest request){
		OapiRobotSendRequest.Markdown markdown = new OapiRobotSendRequest.Markdown();
		// 群消息 没有显示换行，例如 `工作通知，内容 >>>>标题：${title}时间：${time}`
		// markdown.setText("[markdown]工作通知，内容 >>>>\n" + "标题：${title}\n" + "时间：${time}");
		markdown.setText("【markdown】机器人群消息，内容 >>>>  \n" + "  标题：${title}  \n" + "  时间：${time}  ");
		markdown.setTitle("markdown消息");

		request.setMarkdown(markdown);
		request.setMsgtype("markdown");
	}

	private void buildText(OapiRobotSendRequest request){
		OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
		// 群消息 会显示换行，例如：
		// [text]工作通知，内容 >>>>
		// 标题：${title}
		// 时间：${time}
		// @某人
		text.setContent("[text]工作通知，内容 >>>>\n" + "标题：${title}\n" + "时间：${time}");

		request.setText(text);
		request.setMsgtype("text");
	}
}
