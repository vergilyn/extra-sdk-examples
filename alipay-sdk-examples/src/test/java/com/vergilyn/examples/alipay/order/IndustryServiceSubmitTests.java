package com.vergilyn.examples.alipay.order;

import com.alibaba.fastjson.JSON;
import com.alipay.api.request.AlipayCommerceIndustryServiceSubmitRequest;
import com.alipay.api.response.AlipayCommerceIndustryServiceSubmitResponse;
import com.google.common.collect.Maps;
import com.vergilyn.examples.alipay.AbstractAlipayClientTests;
import com.vergilyn.examples.alipay.order.enums.IndustryServiceTypeEnum;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 *
 * @author vergilyn
 * @since 2021-09-02
 *
 * @see <a href="https://www.yuque.com/docs/share/9bc6760b-c507-47d8-b90e-5e9390ce6fe9#xtJ18">服务提报</a>
 * @see <a href="https://opendocs.alipay.com/apis/028xqh">alipay.commerce.industry.service.submit(行业服务标准化服务提报)</a>
 */
public class IndustryServiceSubmitTests extends AbstractAlipayClientTests {

	@SneakyThrows
	@Test
	public void test(){
		AlipayCommerceIndustryServiceSubmitRequest request = new AlipayCommerceIndustryServiceSubmitRequest();
		request.setBizContent("{" +
				"\"service_type\":\"CAR_RENTAL\"," +
				"\"service_name\":\"某某行业某某服务某某门店\"," +
				"\"service_description\":\"某某到家服务提供优质服务,提升生活品质\"," +
				"\"service_action\":\"SERVICE_CREATE\"," +
				"\"service_url\":\"alipays://platformapi/startapp?appId=2018052560231442&pages/index/index\"," +
				"      \"extra_info\":[{" +
				"        \"extra_info_value\":\"xxxx\"," +
				"\"extra_info_key\":\"scene_type\"" +
				"        }]," +
				"\"industry_info\":\"{\\\"platform_info\\\":{\\\"platform_name\\\":\\\"XX回收服务\\\",\\\"platform_telephone\\\":\\\"400-8888-123\\\",\\\"service_citys\\\":\\\"北京,上海,杭州\\\"},\\\"service_info\\\":{\\\"service_name\\\":\\\"搬家\\\",\\\"service_type\\\":\\\"搬家服务\\\",\\\"service_desc\\\":\\\"XXX\\\"}}\"" +
				"  }");
		AlipayCommerceIndustryServiceSubmitResponse response = _alipayClient.execute(request);

		System.out.println(JSON.toJSONString(response, true));
	}


	// 到家业务
	@SneakyThrows
	@Test
	public void homeService() {
		AlipayCommerceIndustryServiceSubmitRequest request = new AlipayCommerceIndustryServiceSubmitRequest();

		Map<String, Object> bizContent = Maps.newHashMap();
		// 租车: CAR_RENTAL, 到家服务: HOME_SERVICE, 上门回收: DOOR_RECYCLING
		bizContent.put("service_type", "HOME_SERVICE");

		// String | 是 | 45 | 描述服务名称(不可重复)
		bizContent.put("service_name", "某某行业vergilyn门店");

		bizContent.put("service_description", "某某到家服务提供优质服务,提升生活品质");

		// 服务动作 SERVICE_CREATE：服务创建, SERVICE_UPDATE：服务更新
		bizContent.put("service_action", "SERVICE_CREATE");

		// String | 是 | 256 | 服务url（可直接跳转对应的服务子类型小程序页面）
		bizContent.put("service_url", "alipays://platformapi/startapp?appId=" + _alipayConfig.appId() + "&pages/index/index");

		// bizContent.put("extra_info", "");

		// String | 是 | 2048 | 行业信息
		bizContent.put("industry_info", JSON.toJSONString(industryInfoHomeService()));

		request.setBizContent(JSON.toJSONString(bizContent));

		AlipayCommerceIndustryServiceSubmitResponse response = _alipayClient.execute(request);
		System.out.println(JSON.toJSONString(response, true));
	}

	private Map<String, Object> industryInfoHomeService(){
		Map<String, Object> industryInfo = Maps.newHashMap();

		Map<String, Object> platform = putGet(industryInfo, "platform_info");
		platform.put("platform_name", "xxx同城到家");
		platform.put("platform_telephone", "400-000-0000");

		Map<String, Object> service = putGet(industryInfo, "service_info");

		// String | 是 | 256 | 服务覆盖城市 需上传相应区域的行政编码，用英文逗号分隔，最小可精确到区 | 500100-重庆
		service.put("service_city", "500100");

		// String | 是 | 32 | 服务类型
		service.put("service_type", IndustryServiceTypeEnum.RUSH_PIPE.name());

		// String | 否 | 32 | 收费单位：面积、房间数、点位、自定义
		// service.put("charging_unit", "面积");

		// String | 否 | 32 | 服务模式：平台——PLATFORM，自营——PROPRIETARY
		// service.put("service_mode", "PROPRIETARY");

		// Number | 是 | 12 | 服务价格 用于按“xxx元起”的流量分发，填入精度为1的数，例如“100.1”
		service.put("min_service_fee", "30.0");

		// Object | 否 | 0
		Map<String, Object> business = putGet(service, "business_hour");

		// String | 否 | 32 |经营日期 填写周几，并用英文逗号分割，**若填写该属性，必须填写start_time和end_time**
		business.put("business_day", "周一,周二");
		business.put("start_time", "09:00");
		business.put("end_time", "17:00");

		return industryInfo;
	}
}
