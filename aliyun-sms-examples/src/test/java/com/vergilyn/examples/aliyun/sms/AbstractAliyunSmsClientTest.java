package com.vergilyn.examples.aliyun.sms;

import java.util.concurrent.CountDownLatch;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.AcsResponse;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.vergilyn.examples.aliyun.sms.properties.AbstractAliyunSmsProperties;
import com.vergilyn.examples.aliyun.sms.properties.VergilynAliyunSmsProperties;

import static com.alibaba.fastjson.serializer.SerializerFeature.PrettyFormat;

/**
 * @see <a href="https://help.aliyun.com/document_detail/55359.html">SDK及DEMO下载</a>
 */
public abstract class AbstractAliyunSmsClientTest {
	private static final AbstractAliyunSmsProperties ALIYUN_SMS_PROPERTIES = new VergilynAliyunSmsProperties();

	/**
	 * 线程安全
	 * VUNVERIFIY 2020-12-23 待验证线程安全性！（demo中每次都是new）
	 */
	private static final IAcsClient ACS_CLIENT;

	static {
		//可自助调整超时时间
		System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
		System.setProperty("sun.net.client.defaultReadTimeout", "10000");

		String regionId = "cn-hangzhou";
		//初始化acsClient,暂不支持region化
		IClientProfile profile = DefaultProfile.getProfile(regionId,
								ALIYUN_SMS_PROPERTIES.getAccessKeyId(),
								ALIYUN_SMS_PROPERTIES.getAccessKeySecret());

		// 产品名称:云通信短信API产品,开发者无需替换
		String product = "Dysmsapi";
		// 产品域名,开发者无需替换
		String domain = "dysmsapi.aliyuncs.com";
		DefaultProfile.addEndpoint(regionId, product, domain);

		ACS_CLIENT = new DefaultAcsClient(profile);
	}

	protected CommonResponse execute(CommonRequest request){
		IAcsClient acsClient = getAcsClient();

		CommonResponse commonResponse = null;
		try {
			commonResponse = acsClient.getCommonResponse(request);
		} catch (ClientException e) {
			e.printStackTrace();
		}

		return commonResponse;
	}

	protected void printJson(CommonResponse response){
		System.out.println(JSON.toJSONString(response, PrettyFormat));
	}

	protected void printJson(AcsResponse response){
		System.out.println(JSON.toJSONString(response, PrettyFormat));
	}

	protected void preventExit(){
		try {
			new CountDownLatch(1).await();
		} catch (InterruptedException e) {
		}
	}

	protected IAcsClient getAcsClient(){
		return ACS_CLIENT;
	}

	protected AbstractAliyunSmsProperties properties(){
		return ALIYUN_SMS_PROPERTIES;
	}
}
