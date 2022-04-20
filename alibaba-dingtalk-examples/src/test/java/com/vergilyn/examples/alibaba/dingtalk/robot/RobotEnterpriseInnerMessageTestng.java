package com.vergilyn.examples.alibaba.dingtalk.robot;

import lombok.SneakyThrows;
import org.testng.annotations.Test;

/**
 * 钉钉企业内部机器人
 *
 * @author vergilyn
 * @since 2022-04-20
 *
 * @see <a href="https://open.dingtalk.com/document/group/enterprise-created-chatbot">企业内部开发机器人</a>
 */
public class RobotEnterpriseInnerMessageTestng extends AbstractRobotEnterprise {

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
	 * @see <a href="https://open.dingtalk.com/document/group/the-robot-sends-a-group-message">发送机器人群聊消息</a>
	 * @see <a href="https://open.dingtalk.com/document/group/assign-a-webhook-url-to-an-internal-chatbot">企业内部机器人使用Webhook发送群聊消息</a>
	 */
	@SneakyThrows
	@Test
	public void sendGroupMessage(){

	}
}
