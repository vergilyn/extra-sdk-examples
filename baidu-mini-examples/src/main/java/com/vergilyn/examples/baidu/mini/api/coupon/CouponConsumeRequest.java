package com.vergilyn.examples.baidu.mini.api.coupon;

import com.vergilyn.examples.baidu.mini.api.BaiduMiniRequest;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author vergilyn
 * @since 2021-07-16
 *
 * @see <a href="https://smartprogram.baidu.com/docs/develop/api/open/coupons_delete/">核销卡券接口</a>
 */
@Data
@NoArgsConstructor
public class CouponConsumeRequest implements BaiduMiniRequest {

	/**
	 * 百度：卡券 ID
	 */
	private String couponId;

	/**
	 * 百度：分配的用户领取 id
	 */
	private String couponTakeId;

	/**
	 * 百度：用户id
	 */
	private String openId;


	@Override
	public String getApiURL() {
		return "/rest/2.0/smartapp/v1.0/coupon/code/consume";
	}
}
