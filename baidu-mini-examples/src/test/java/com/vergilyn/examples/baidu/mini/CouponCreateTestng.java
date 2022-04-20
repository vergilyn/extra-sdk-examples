package com.vergilyn.examples.baidu.mini;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import lombok.SneakyThrows;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.testng.annotations.Test;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 *
 * @author vergilyn
 * @since 2022-04-20
 */
public class CouponCreateTestng extends AbstractBaiduMiniTestng {
	private static final String CALLBACK_URL = "https://www.baidu.com";
	private static final String ACCESS_TOKEN = "<<ACCESS_TOKEN>>";

	@SneakyThrows
	@Test
	public void create(){
		String apiURL = "https://openapi.baidu.com/rest/2.0/smartapp/v1.0/coupon/create?access_token=";

		Map<String, Object> params = paramCash();
		// 卡券领取事件推送地址
		params.put("callbackUrl", CALLBACK_URL);

		String jsonParam = JSON.toJSONString(params);
		System.out.println("create-coupon params: " + jsonParam);

		HttpPost post = new HttpPost(apiURL + ACCESS_TOKEN);
		HttpEntity entity = new StringEntity(jsonParam, ContentType
				.create(ContentType.APPLICATION_JSON.getMimeType(), StandardCharsets.UTF_8));
		post.setEntity(entity);

		// final CloseableHttpResponse response = httpClient.execute(post);
		// final String resp = EntityUtils.toString(response.getEntity());
		//
		// // 6363730141
		// System.out.println(resp);
	}


	private Map<String, Object> paramCash(){
		Map<String, Object> params = Maps.newHashMap();

		params.put("couponType", "CASH");
		params.put("leastCost", 0);

		// 代金券专用，表示减免金额（单位：分）
		params.put("reduceCost", 500L);
		params.put("baseInfo", baseInfo("测试5元现金红包"));

		// 使用须知：卡券使用方法的介绍
		params.put("description", "CASH-代金券 使用说明：<br/> <font color=\"RED\">支持富文本内容。</font>");

		return params;
	}

	/**
	 * 基本的卡券数据，所有卡券通用。
	 *
	 * @param title 优惠券名称，最多 15 字
	 * @return
	 */
	private Map<String, Object> baseInfo(String title){
		Map<String, Object> baseInfo = Maps.newHashMap();

		baseInfo.put("title", title);

		// 卡券 Code 码类型，默认为 1，
		// 1：开发者自定义 code 码，当 codeType=1 时，需要通过「上传 code 码」接口导入 Code，否则影响领券；
		// 2：系统分配 Code 码，当 codeType=2 时，开发者无需上传 Code ，quantity 要求必传非 0 且生效
		baseInfo.put("codeType", 2);

		baseInfo.put("quantity", 100L);

		// 每人领取次数限制
		baseInfo.put("getLimit", 2);

		// 否；已领取的卡券，从详情頁点击「立即使用」打开小程序页面地址，不传默认打开首页
		baseInfo.put("appRedirectPath", "");

		// XXX 2021-07-16 为什么这里（里层）还要再必传 callbackUrl？
		// 是；卡券领取事件推送地址
		baseInfo.put("callbackUrl", CALLBACK_URL);

		// 是否会员券。0：否；1：是。默认为 0（非会员券）
		baseInfo.put("isMemberShip", 0);

		// 使用日期，有效期的信息。
		baseInfo.put("dateInfo", dateInfo());

		return baseInfo;
	}

	/**
	 * 使用日期，有效期的信息。
	 */
	private Map<String, Object> dateInfo(){
		Map<String, Object> dateInfo = Maps.newHashMap();

		// 券使用时间类型：
		// 1：开发者设置使用开始和结束时间；beginTimestamp 和 endTimestamp 必传；
		// 2：领取之后，多久可使用；timeUnit 和 timeValue 必传
		dateInfo.put("type", 1);

		dateInfo.put("beginTimestamp", 1626408000L);  // 2021-07-16 12:00:00
		dateInfo.put("endTimestamp", 1629086400L);  // 2021-08-16 12:00:00

		// Long，10位时间戳 开始/结束 领取时间
		dateInfo.put("getStartTimestamp", 1626408000L);  // 2021-07-16 12:00:00
		dateInfo.put("getEndTimestamp", 1629086400L);  // 2021-08-16 12:00:00

		// 1630296000L   2021-08-30 12:00:00

		return dateInfo;
	}
}
