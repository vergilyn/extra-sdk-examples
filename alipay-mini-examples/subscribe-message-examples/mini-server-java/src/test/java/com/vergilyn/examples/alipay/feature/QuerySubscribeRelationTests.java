package com.vergilyn.examples.alipay.feature;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayOpenAppMessagetemplateSubscribeQueryModel;
import com.alipay.api.request.AlipayOpenAppMessagetemplateSubscribeQueryRequest;
import com.alipay.api.response.AlipayOpenAppMessagetemplateSubscribeQueryResponse;
import com.vergilyn.examples.alipay.AbstractAlipayMiniSubscribeMessageTests;
import com.vergilyn.examples.alipay.SubscribeMessageTemplateInfo;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.testng.collections.Lists;

public class QuerySubscribeRelationTests extends AbstractAlipayMiniSubscribeMessageTests {

	/**
	 * <a href="https://opendocs.alipay.com/mini/03l9bj?ref=api">模板订阅关系查询</a>
	 * <pre>
	 *   商户使用该接口可以查询用户对其消息模板的订阅关系，可以根据是否订阅引导用户进行订阅和精准触达消息
	 * </pre>
	 *
	 * @see <a href="https://opensupport.alipay.com/support/helpcenter/97/201602487011">
	 *          isv.invalid-method（不存在的方法名）</a>
	 */
	@SneakyThrows
	@Test
	public void query(){
		AlipayOpenAppMessagetemplateSubscribeQueryModel model = new AlipayOpenAppMessagetemplateSubscribeQueryModel();

		// 消息模板id，可以填写多个，最多不超过3个。
		// 模板id需要保持同一个应用主体，并且展示在同一个订阅组件中的模板id。
		model.setTemplateIdList(Lists.newArrayList(
				SubscribeMessageTemplateInfo.VIP_AUTO_RENEW.getTemplateId(),
				SubscribeMessageTemplateInfo.BUSINESS_FINISH.getTemplateId(),
				SubscribeMessageTemplateInfo.TASK_FINISH.getTemplateId()
		));

		model.setUserId(_miniProperties.userId());

		AlipayOpenAppMessagetemplateSubscribeQueryRequest request = new AlipayOpenAppMessagetemplateSubscribeQueryRequest();
		request.setBizModel(model);

		AlipayClient alipayClient = createAlipayClient();
		try {

			AlipayOpenAppMessagetemplateSubscribeQueryResponse response = alipayClient.execute(request);
			System.out.println("response >>>> \n\t" + JSON.toJSONString(response, true));

			System.out.println("response-body >>>> \n\t" + response.getBody());

			/*
			 * 1. 客户端唤起订阅 2个模版：
			 *      - cbbd808de97a495a9967f1256d05ab6e	业务完成通知。  勾选订阅
			 *      - 9f04e7c1721b4c1799329717dea6b41f	任务完成提醒。  未订阅。
			 *  接口返回结果：
			 *	{
			 *		"show":true,
			 *		"subscribeState":"1",  // 0：拒绝订阅。1：订阅
			 *		"subscribeType":"onetime",
			 *		"templateId":"cbbd808de97a495a9967f1256d05ab6e"  // 业务完成通知
			 *	}
			 *
			 * 2. 成功发送后 1次 订阅消息后，再次查询接口返回：
			 * 	{
			 *		"show":true,
			 *		"subscribeState":"0",
			 *		"subscribeType":"onetime",
			 *		"templateId":"cbbd808de97a495a9967f1256d05ab6e"
			 *	}
			 */
			System.out.println("response-subscribe-relations >>>> \n\t" + JSON.toJSONString(response.getSubscribeRelations(), true));

		}catch (Exception e){
			e.printStackTrace();
		}

	}
}
