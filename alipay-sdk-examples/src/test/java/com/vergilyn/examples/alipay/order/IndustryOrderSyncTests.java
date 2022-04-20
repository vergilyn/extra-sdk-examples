package com.vergilyn.examples.alipay.order;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alipay.api.AlipayApiException;
import com.alipay.api.request.AlipayCommerceIndustryOrderSyncRequest;
import com.alipay.api.response.AlipayCommerceIndustryOrderSyncResponse;
import com.google.common.collect.Maps;
import com.vergilyn.examples.alipay.AbstractAlipayClientTests;
import com.vergilyn.examples.alipay.order.enums.IndustryOrderSourceEnum;
import com.vergilyn.examples.alipay.order.enums.IndustryOrderStatusEnum;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * 行业服务标准化订单数据回流(版本1.0)
 *
 * @author vergilyn
 * @since 2021-09-01
 *
 * @see <a href="https://www.yuque.com/docs/share/9bc6760b-c507-47d8-b90e-5e9390ce6fe9?#Ul4rG">订单回流接口</a>
 */
public class IndustryOrderSyncTests extends AbstractAlipayClientTests {

	@Test
	public void waitPay() {
		// `status` 会影响 `industry_info` 数据结构变化。
		String status = IndustryOrderStatusEnum.WAIT_PAY.name();

		AlipayCommerceIndustryOrderSyncRequest request = new AlipayCommerceIndustryOrderSyncRequest();
		Map<String, Object> bizContent = Maps.newHashMap();

		// 类型 | 是否必须 | 最大长度 | 描述
		// String | 是 | 64 | 商户订单号 | 21328342189329482
		bizContent.put("merchant_order_no", "21328342189329482");

		// String | 否 | 64 | 商户订单同步记录id（同一订单，非第一次同步 必填） | 2019103000502
		// TODO 从哪里来？
		bizContent.put("record_id", "2021090110086");

		// String | 是 | 64 | 服务类型, 具体值联系解决方案同学
		// TODO 具体值联系解决方案同学
		bizContent.put("service_type", "TODO, CAR_RENTAL");

		// String | 是 | 16 | 支付宝userid（用户在支付宝平台的2088开头16位id）
		bizContent.put("buyer_id", _alipayUserInfo.alipayUserId());

		// String | 是 | 64 | 服务标识
		// TODO 传什么？
		bizContent.put("service_code", "2021043021000808020160");

		// String | 否 | 64 | 子服务类型
		// bizContent.put("sub_service_type", "CLOTHING_RECYCLING");

		// String | 是 | 32 | 订单类型，枚举
		bizContent.put("order_source", IndustryOrderSourceEnum.ALIPAY_APPLETS.name());

		// Date | 是 | 32 | 订单创建时间 | 2021-01-01 08:00:00
		bizContent.put("order_create_time", "2021-09-01 15:00:00");

		// Date | 是 | 32 | 订单修改时间(订单更新时需有变动) | 2021-01-01 08:00:00
		bizContent.put("order_modify_time", "2021-09-01 15:13:42");

		// String | 是 | 128 | 订单详情链接，链接是商户自己的小程序的（注意参数部分需要encode）
		// alipays://platformapi/startapp?appId=2018xxxx9337&page=/pages/user/myorder/detail/detail?orderid=xxx
		// `page`部分应该是 商户自己小程序的订单详情链接。
		bizContent.put("order_detail_url", "alipays://platformapi/startapp?appId=2018xxxx9337&page=%2Fpages%2Fuser%2Fmyorder%2Fdetail%2Fdetail%3Forderid%3");

		// Price | 是 | 32 | 订单总金额 | 128.40
		bizContent.put("order_amount", "128.40");

		// Price | 否 | 32 | 优惠金额 | 0.13
		// bizContent.put("discount_amount", "0.13");

		// Price | 否 | 32 | 交易支付金额 | 128.27
		// 大致 `payment_amount = order_amount - discount_amount`
		// bizContent.put("payment_amount", "128.27");

		// ExtraInfo | 否 | - | 订单扩展字段，示例展示场景
		// key：scene_type，value：NOTICE_TOUCH；
		// key：cup_type，value: BRING_CUP
		// bizContent.put("order_extra_info", "");

		// String | 是 | 32 | 订单状态枚举
		// 不同状态，对应的 industry_info 是不同的，注意看文档 “订单回流API文档_order_home_service_v1.0.html”
		bizContent.put("status", status);

		// String | 是 | 2048 | 行业信息，根据`status`不同数据格式会不一样，注意看文档 “订单回流API文档_order_home_service_v1.0.html”
		bizContent.put("industry_info", industryInfoWaitPay());

		request.setBizContent(JSON.toJSONString(bizContent));

		System.out.println(JSON.toJSONString(bizContent, SerializerFeature.MapSortField, SerializerFeature.PrettyFormat));
	}

	private AlipayCommerceIndustryOrderSyncResponse request(AlipayCommerceIndustryOrderSyncRequest request)
			throws AlipayApiException {
		return _alipayClient.execute(request, "access_token", "app_auth_token");
	}

	private Map<String, Object> industryInfoWaitPay(){
		Map<String, Object> industryInfo = Maps.newHashMap();

		Map<String, Object> productInfo = putGet(industryInfo, "service_product_info");
		// String | 是 | 32 | ？| 家居保洁
		productInfo.put("service_name", "家居保洁");
		productInfo.put("service_specification", "家居保洁2小时");
		// 商品在当前ISV内部id，可选填
		productInfo.put("goods_id", "202108018888");
		// 上门服务行业填入默认值：上门服务
		productInfo.put("goods_name", "上门服务");
		// 项目类型，服务类型，以下枚举值: 家政保洁 家电清洗 软装清洗 管道疏通 家电维修 家具安装 上门开锁 上门搬家
		productInfo.put("unit", "家政保洁");
		// Number | 是 | 999999 | ？| 1
		productInfo.put("quantity", 1);
		// Number | 否 | 99999999 | ？| 100
		// productInfo.put("unit_price", 100);

		Map<String, Object> providerInfo = putGet(industryInfo, "service_provider_info");
		providerInfo.put("platform_name", "XX同城");
		providerInfo.put("platform_phone", "400-8888-123");
		// String | 否 | 32 | ？| 小明家居公司
		// providerInfo.put("undertaker_name", "小明家居公司");
		// String | 否 | 32 | ？| 4008007777
		// providerInfo.put("undertaker_phone", "4008007777");

		Map<String, Object> staffInfo = putGet(industryInfo, "service_staff_info");
		staffInfo.put("phone", "18888888888");
		staffInfo.put("type", "保洁员");
		// String | 否 | 32 | ？| 20年
		// staffInfo.put("working_years", "20年");

		Map<String, Object> performanceInfo = putGet(industryInfo, "service_performance_info");
		// String | 是 | 32 | ？| 枚举值，正则校验“^(PRICE|BOOKING|RESERVATION)?$”
		// 一口价PRICE：服务标的定价相对标准，浮动空间不大的，如保洁类、上门开锁等；即便后续有增项费用，也一般由服务人员和用户线下交易，平台不管控（主要缺少管控抓手）
		// 预订BOOKING：用户预先支付定金，服务人员上门后确定费用后开单，完成二次支付（一般是维修类）。如果二次报价用户不接受，可以取消订单。
		// 预定RESERVATION：用户先提交意向，服务人员电话后确定涉及大致费用，线上支付定金，服务完成后进行二次支付（主要是搬家类）
		// TODO 传什么？依据什么判断？  枚举值
		performanceInfo.put("payment_type", "BOOKING");

		Map<String, Object> appointmentTime = putGet(performanceInfo, "appointment_time");
		// String | 是 | 32 | ？| 正则校验“^\d{4}-\d{2}-\d{2}\s([0-1]?[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$”
		appointmentTime.put("start_time", "2021-01-01 08:00:00");
		// String | 是 | 32 | ？| 正则校验“^\d{4}-\d{2}-\d{2}\s([0-1]?[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$”
		appointmentTime.put("end_time", "2021-01-01 08:00:00");

		// String | 否 | 32 | ？| 正则校验“^\d{4}-\d{2}-\d{2}\s([0-1]?[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$”
		// performanceInfo.put("check_time", "2022-01-01 08:00:00");

		return industryInfo;
	}
}
