package com.vergilyn.examples.aliyun.vod.controller;

import com.alibaba.fastjson.JSON;
import com.vergilyn.examples.aliyun.vod.api.AliyunStsApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/aliyun/vod/")
public class AliyunStsController {
	@Autowired
	private AliyunStsApi stsApi;

	@GetMapping(value = "/get_upload_sts", produces = APPLICATION_JSON_VALUE)
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public String getUploadSts(@RequestParam("callback") String callback){
		return callback + "(" + JSON.toJSONString(stsApi.getUploadSts()) + ")";
	}
}
