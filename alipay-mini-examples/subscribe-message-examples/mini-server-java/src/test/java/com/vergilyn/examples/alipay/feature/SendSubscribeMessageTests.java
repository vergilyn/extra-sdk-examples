package com.vergilyn.examples.alipay.feature;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.domain.AlipayOpenAppMiniTemplatemessageSendModel;
import com.alipay.api.request.AlipayOpenAppMiniTemplatemessageSendRequest;
import com.alipay.api.response.AlipayOpenAppMiniTemplatemessageSendResponse;
import com.google.common.collect.Maps;
import com.vergilyn.examples.alipay.AbstractAlipayMiniSubscribeMessageTests;
import com.vergilyn.examples.alipay.SubscribeMessageTemplateInfo;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class SendSubscribeMessageTests extends AbstractAlipayMiniSubscribeMessageTests {

	/**
	 * <a href="https://opendocs.alipay.com/mini/02cth2">小程序发送模板消息</a>
	 */
	@SneakyThrows
	@Test
	public void send(){
		AlipayOpenAppMiniTemplatemessageSendModel model = new AlipayOpenAppMiniTemplatemessageSendModel();
		model.setToUserId(_miniProperties.userId());
		// model.setFormId("2017010100000000580012345678");
		model.setUserTemplateId(SubscribeMessageTemplateInfo.TASK_FINISH.getTemplateId());

		// 必填：小程序跳转页面
		// 任意填一个地址也可以发送成功，支付宝点击进错误页面。（与 微信不一样，微信直接接口返回失败。）
		model.setPage("page/invalid-index");  // 实际不存在的页面
		model.setPage("page/index/index");    // 存在的页面

		//   业务名称: {{ keyword1 }}
		//   业务类型: {{ keyword2 }}
		//   完成时间: {{ keyword3 }}
		Map<String, String> data = Maps.newHashMap();
		data.put("keyword1", "【测试】小程序订阅消息-业务完成通知");
		data.put("keyword2", "业务类型001");
		data.put("keyword3", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		model.setData(toDataJsonString(data));

		AlipayOpenAppMiniTemplatemessageSendRequest request = new AlipayOpenAppMiniTemplatemessageSendRequest();
		request.setBizModel(model);

		AlipayOpenAppMiniTemplatemessageSendResponse response = createAlipayClient().execute(request);
		System.out.println(response.getBody());

		// “一次性订阅” 发送成功后，再次发送返回结果：
		//   code: 40004
		//   msg: Business Failed
		//   sub_code: MSG_UNSUBSCRIBED
		//   sub_msg: 用户未订阅，发送消息失败
		System.out.println("code: " + response.getCode());
		System.out.println("msg: " + response.getMsg());
		System.out.println("sub_code: " + response.getSubCode());
		System.out.println("sub_msg: " + response.getSubMsg());
	}

	private String toDataJsonString(Map<String, String> dataMap){
		JSONObject jsonObject = new JSONObject();

		for (Map.Entry<String, String> entry : dataMap.entrySet()) {
			JSONObject value = new JSONObject();
			value.put("value", entry.getValue());

			jsonObject.put(entry.getKey(), value);
		}


		return jsonObject.toJSONString();
	}
}
