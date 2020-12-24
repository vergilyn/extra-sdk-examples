package com.vergilyn.examples.aliyun.sms.dto;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;

/**
 * 阿里云短信报告实体类。
 *
 * e.g.
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
 *
 * @see <a href="https://help.aliyun.com/document_detail/101867.html">短信状态报告（SmsReport）。</a>
 */
@Data
public class SmsReportDTO {

	/**
	 * 手机号。
	 * 示例：13900000001
	 */
	@JSONField(name = "phone_number")
	private String phoneNumber;

	/**
	 * 发送时间。
	 * 示例：2017-01-01 00:00:00
	 */
	@JSONField(name = "send_time", format = "yyyy-MM-dd HH:mm:ss")
	private Date sendTime;

	/**
	 * 状态报告时间。
	 * 示例：2017-01-01 00:00:00
	 */
	@JSONField(name = "report_time", format = "yyyy-MM-dd HH:mm:ss")
	private Date reportTime;

	/**
	 * 是否接收成功。
	 * 示例：true\false
	 */
	@JSONField(name = "success")
	public Boolean success;

	/**
	 * 状态报告编码。
	 * 示例：DELIVERED
	 */
	@JSONField(name = "err_code")
	private String errCode;

	/**
	 * 状态报告说明。
	 * 示例：用户接收成功
	 */
	@JSONField(name = "err_msg")
	private String errMsg;

	/**
	 * 短信长度，140字节算一条短信，短信长度超过140字节时会拆分成多条短信发送。
	 * 示例：1，2，3
	 *
	 * <p>
	 *   <b>接口文档标明是`String`，不是`Integer`</b>
	 * </p>
	 */
	@JSONField(name = "sms_size")
	private String smsSize;

	/**
	 * 发送序列号。
	 * 示例：12345
	 */
	@JSONField(name = "biz_id")
	private String bizId;

	/**
	 * 用户序列号。
	 * 示例：67890
	 */
	@JSONField(name = "out_id")
	private String outId;
}
