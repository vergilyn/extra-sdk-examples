package com.vergilyn.examples.weixin.mp;

import java.util.List;

import com.alibaba.fastjson.JSON;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpTemplateMsgService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import static com.alibaba.fastjson.serializer.SerializerFeature.PrettyFormat;

public class WxMpTemplateMsgServiceTestng extends AbstractWeixinMpTestng {
	@Autowired
	private WxMpService wxMpService;

	/**
	 * "错误代码：48001, 错误信息：api 功能未授权"
	 */
	@Test
	public void exception(){
		WxMpTemplateMsgService templateMsgService = wxMpService.getTemplateMsgService();
		try {
			List<WxMpTemplate> tpls = templateMsgService.getAllPrivateTemplate();

			System.out.println(JSON.toJSONString(tpls, PrettyFormat));

		} catch (WxErrorException e) {
			e.printStackTrace();
		}
	}
}
