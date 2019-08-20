/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alipay.demo.controller.rest;

import java.util.Date;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import com.alipay.demo.config.RestConstants;
import com.alipay.demo.pojo.UserInfo;

import org.jboss.resteasy.spi.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author alipay.demo
 *
 */
@Component
public class UserControllerImpl implements UserController {
	private static final Logger   logger = LoggerFactory.getLogger(UserControllerImpl.class);
	
	@Autowired
	private AlipayClient alipayClient;

	@Override
	public UserInfo authUser(String authCode, HttpResponse response) throws Exception {
		if (isBlank(authCode)) {
			logger.warn("授权编码不能为空!");
			throw new Exception("授权编码不能为空");
		}
		// token and userId
		AlipaySystemOauthTokenResponse alipaySystemOauthTokenResponse = this.getAccessToken(authCode);
		if (!alipaySystemOauthTokenResponse.isSuccess()) {
			logger.warn("换取 AuthToken 失败！错误编码：{}, 错误信息：{}", alipaySystemOauthTokenResponse.getCode(), alipaySystemOauthTokenResponse.getMsg() );
			throw new Exception("换取 AuthToken 失败 code=" + alipaySystemOauthTokenResponse.getCode() + ", msg="
					+ alipaySystemOauthTokenResponse.getMsg());
		}
		String accessToken = alipaySystemOauthTokenResponse.getAccessToken();
		
		logger.info("通过AuthCode:{} 换取AuthToken:{} 成功！", authCode, accessToken );
		
		// get user by accessToken
		AlipayUserInfoShareResponse alipayUserInfoShareResponse = this.getUserInfoFromAlipay(accessToken);
		String userId = alipayUserInfoShareResponse.getUserId();
		if (isBlank(userId)) {
			logger.warn("获取用户信息失败 错误编码：{}, 错误信息：{}", alipaySystemOauthTokenResponse.getCode(), alipaySystemOauthTokenResponse.getMsg() );
			throw new Exception("获取用户信息失败 code=" + alipayUserInfoShareResponse.getCode() + ", msg="
					+ alipayUserInfoShareResponse.getBody());
		}

		UserInfo userInfo = this.convertFromAlipayUserInfoShareResponse(alipayUserInfoShareResponse);
		logger.info("获取用户信息成功！ 当前用户名：{}， 用户图标：{}", userInfo.getNickName(), userInfo.getAvatar() );
		
		userInfo.setAccessToken(accessToken);
		userInfo.setRefreshToken(alipaySystemOauthTokenResponse.getRefreshToken());
		
		// header added
		response.getOutputHeaders().putSingle(RestConstants.ACCESS_TOKEN_KEY, accessToken);
		response.getOutputHeaders().putSingle(RestConstants.USER_ID_KEY, alipayUserInfoShareResponse.getUserId());
		
		return userInfo;
	}

	private AlipaySystemOauthTokenResponse getAccessToken(String authCode) throws AlipayApiException {
		AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
		request.setGrantType("authorization_code");
		request.setCode(authCode);
		request.setRefreshToken("201208134b203fe6c11548bcabd8da5bb087a83b");
		return alipayClient.execute(request);
	}

	private AlipayUserInfoShareResponse getUserInfoFromAlipay(String accessToken) throws AlipayApiException {
		AlipayUserInfoShareRequest request = new AlipayUserInfoShareRequest();
		return alipayClient.execute(request, accessToken);
	}

	private UserInfo convertFromAlipayUserInfoShareResponse(AlipayUserInfoShareResponse alipayUserInfoShareResponse) {
		UserInfo userInfo = new UserInfo();
		String nickName = alipayUserInfoShareResponse.getNickName();
		userInfo.setUserId(alipayUserInfoShareResponse.getUserId());
		userInfo.setNickName(isBlank(nickName) ? "" : nickName);
		String avatar = alipayUserInfoShareResponse.getAvatar();
		userInfo.setAvatar(isBlank(avatar) ? "" : avatar);
		// now
		userInfo.setModifyTime(new Date());
		return userInfo;
	}
	public static boolean isBlank(String str) {
        int length;
        if (str != null && (length = str.length()) != 0) {
            for(int i = 0; i < length; ++i) {
                if (!Character.isWhitespace(str.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }
}
