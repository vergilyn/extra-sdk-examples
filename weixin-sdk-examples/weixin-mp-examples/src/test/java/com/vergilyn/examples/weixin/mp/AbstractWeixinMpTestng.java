package com.vergilyn.examples.weixin.mp;

import java.util.concurrent.atomic.AtomicBoolean;

import com.alibaba.fastjson.JSON;

import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpTemplateMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;

import static com.alibaba.fastjson.serializer.SerializerFeature.PrettyFormat;

@SpringBootTest(classes = {WeixinMpApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractWeixinMpTestng extends AbstractTestNGSpringContextTests {
	private static final AtomicBoolean init = new AtomicBoolean(false);

	@Autowired
	protected WxMpService wxMpService;

	protected WxMpTemplateMsgService templateMsgService;

	@BeforeMethod
	public void init(){
		if (!init.compareAndSet(false, true)){
			return;
		}

		this.templateMsgService = wxMpService.getTemplateMsgService();
	}

	protected void printJsonString(Object object){
		System.out.println(JSON.toJSONString(object, PrettyFormat));
	}
}
