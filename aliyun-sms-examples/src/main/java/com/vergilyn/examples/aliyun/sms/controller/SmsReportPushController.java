package com.vergilyn.examples.aliyun.sms.controller;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.vergilyn.examples.aliyun.sms.dto.SmsReportDTO;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.alibaba.fastjson.serializer.SerializerFeature.PrettyFormat;

/**
 * 第一次推送失败后，间隔1分钟、5分钟、10分钟、30分钟、60分钟、60分钟、60分钟、60分钟、60分钟后会进行重推，直至推送成功为止。
 * <b>如果推送10次后仍失败，不再重试。</b>
 *
 * @see <a href="https://help.aliyun.com/document_detail/101867.html">通过HTTP批量推送方式可以拉取短信状态报告（SmsReport）。</a>
 */
@RestController
@RequestMapping("/sms/report")
public class SmsReportPushController {

	/**
	 * <pre>
	 * [
	 *   {
	 *     "phone_number" : "13900000001",
	 *     "send_time" : "2017-01-01 00:00:00",
	 *     "report_time" : "2017-01-01 00:00:00",
	 *     "success" : true,
	 *     "err_code" : "DELIVERED",
	 *     "err_msg" : "用户接收成功",
	 *     "sms_size" : "1",
	 *     "biz_id" : "12345",
	 *     "out_id" : "67890"
	 *   }
	 * ]
	 * </pre>
	 * @return e.g. `{"code":0,"msg":"接收成功"}`
	 */
	@RequestMapping("/push")
	public String push(@RequestBody String body){
		System.out.println("request-body >>>> " + body);

		List<SmsReportDTO> reports = JSON.parseArray(body, SmsReportDTO.class);

		System.out.println("reports >>>> " +JSON.toJSONString(reports, PrettyFormat));

		return "{\"code\":0,\"msg\":\"接收成功\"}";
	}
}
