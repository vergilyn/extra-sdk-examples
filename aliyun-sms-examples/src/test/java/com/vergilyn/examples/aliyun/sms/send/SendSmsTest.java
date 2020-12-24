package com.vergilyn.examples.aliyun.sms.send;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse.SmsSendDetailDTO;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.vergilyn.examples.aliyun.sms.AbstractAliyunSmsClientTest;

import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class SendSmsTest extends AbstractAliyunSmsClientTest {

	/**
	 * @see <a href="https://help.aliyun.com/document_detail/101414.html">调用SendSms发送短信。</a>
	 */
	@Test
	public void sendSms() {
		SendSmsRequest request = new SendSmsRequest();
		//必填:待发送手机号
		request.setPhoneNumbers("15000000000");
		//必填:短信签名-可在短信控制台中找到
		request.setSignName("vergiyln");
		//必填:短信模板-可在短信控制台中找到
		request.setTemplateCode("SMS_1000000");
		//可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
		request.setTemplateParam("{\"name\":\"Tom\", \"code\":\"123\"}");

		//选填-上行短信扩展码(无特殊需求用户请忽略此字段)
		//request.setSmsUpExtendCode("90997");

		// 外部流水扩展字段。
		request.setOutId("yourOutId");

		//hint 此处可能会抛出异常，注意catch
		try {

			// 发送回执ID，可根据该ID在接口QuerySendDetails中查询具体的发送状态。
			SendSmsResponse response = getAcsClient().getAcsResponse(request);
			// printJson(response);

			System.out.println("短信接口返回的数据----------------");
			System.out.println("Code=" + response.getCode());
			System.out.println("Message=" + response.getMessage());
			System.out.println("RequestId=" + response.getRequestId());
			System.out.println("BizId=" + response.getBizId());  // 业务ID

			// 发送成功
			// response.getCode() != null && response.getCode().equals("OK")

		} catch (ClientException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @param bizId 发送回执ID，即发送流水号。调用发送接口SendSms或SendBatchSms发送短信时，返回值中的BizId字段
	 * @see <a href="https://help.aliyun.com/document_detail/102352.html">查看短信发送记录和发送状态</a>
	 */
	@Test
	@Parameters("bizId")
	public void queryByBizId(@Optional("12345") String bizId) {
		QuerySendDetailsRequest request = new QuerySendDetailsRequest();
		//必填-号码
		request.setPhoneNumber("15000000000");
		//可选-发送回执ID，即发送流水号。调用发送接口SendSms或SendBatchSms发送短信时，返回值中的BizId字段
		request.setBizId(bizId);
		//必填-发送日期 支持30天内记录查询，格式yyyyMMdd
		SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
		request.setSendDate(ft.format(new Date()));
		//必填-页大小
		request.setPageSize(10L);
		//必填-当前页码从1开始计数
		request.setCurrentPage(1L);

		//hint 此处可能会抛出异常，注意catch
		try {
			QuerySendDetailsResponse response = getAcsClient().getAcsResponse(request);

			// printJson(response);

			System.out.println("短信明细查询接口返回数据----------------");
			System.out.println("Code=" + response.getCode());
			System.out.println("Message=" + response.getMessage());
			System.out.println("TotalCount=" + response.getTotalCount());
			System.out.println("RequestId=" + response.getRequestId());

			int i = 0;
			for (SmsSendDetailDTO detail : response.getSmsSendDetailDTOs()) {
				System.out.println("SmsSendDetailDTO[" + i + "]:");
				System.out.println("Content=" + detail.getContent());
				System.out.println("ErrCode=" + detail.getErrCode());
				System.out.println("OutId=" + detail.getOutId());
				System.out.println("PhoneNum=" + detail.getPhoneNum());
				System.out.println("ReceiveDate=" + detail.getReceiveDate());
				System.out.println("SendDate=" + detail.getSendDate());
				System.out.println("SendStatus=" + detail.getSendStatus());
				System.out.println("Template=" + detail.getTemplateCode());
			}

		} catch (ClientException e) {
			e.printStackTrace();
		}
	}
}
