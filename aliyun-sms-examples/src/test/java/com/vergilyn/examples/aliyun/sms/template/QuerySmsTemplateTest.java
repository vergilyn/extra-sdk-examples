package com.vergilyn.examples.aliyun.sms.template;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.http.MethodType;
import com.vergilyn.examples.aliyun.sms.AbstractAliyunSmsClientTest;

import org.testng.annotations.Test;

/**
 * @see <a href="https://help.aliyun.com/document_detail/121206.html">查询短信模板的审核状态</a>
 */
public class QuerySmsTemplateTest extends AbstractAliyunSmsClientTest {

	@Test
	public void querySmsTemplate(){
		CommonRequest request = new CommonRequest();
		request.setSysMethod(MethodType.POST);
		request.setSysAction("QuerySmsTemplate");
		request.setSysProduct("Dysmsapi");
		request.putQueryParameter("TemplateCode", "SMS_152550005");

		CommonResponse response = execute(request);

		printJson(response);

	}
}
