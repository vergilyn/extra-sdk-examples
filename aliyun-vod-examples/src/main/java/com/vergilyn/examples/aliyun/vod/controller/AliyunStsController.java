package com.vergilyn.examples.aliyun.vod.controller;

import java.util.concurrent.atomic.AtomicLong;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.vergilyn.examples.aliyun.vod.api.AliyunStsApi;
import com.vergilyn.examples.aliyun.vod.config.AliyunVodClient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping("/aliyun/vod/")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AliyunStsController {
	private static final AtomicLong PLAY_COUNT = new AtomicLong(0);
	@Autowired
	private AliyunStsApi stsApi;

	@GetMapping(value = "/get_upload_sts", produces = APPLICATION_JSON_VALUE)
	public String getUploadSts(@RequestParam("callback") String callback){
		return callback + "(" + JSON.toJSONString(stsApi.getUploadSts()) + ")";
	}

	@GetMapping(value = "/get_play_auth", produces = APPLICATION_JSON_VALUE)
	public GetVideoPlayAuthResponse getPlayAuth(){
		return stsApi.getPlayAuth(AliyunVodClient.DEFAULT_VIDEO_ID);
	}

	@GetMapping(value = "/get_play_info", produces = APPLICATION_JSON_VALUE)
	public GetPlayInfoResponse getPlayInfo(){
		return stsApi.getPlayInfo(AliyunVodClient.getInstance().getProperties().videoId());
	}

	@PutMapping(value = "/incr_play_count/{vid}", produces = APPLICATION_JSON_VALUE)
	public Long incrPlayCount(@PathVariable String vid){
		log.info("[vergilyn] >>>> incr_play_count videoId: {}, play_count: {}", vid, PLAY_COUNT.incrementAndGet());
		return PLAY_COUNT.get();
	}
}
