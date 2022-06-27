package com.vergilyn.examples.weixin.mp.controller;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

/**
 * 网页授权
 * <a href="https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140842">网页授权</a>
 * @author vergilyn
 * @date 2019-08-02
 */
@Controller
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    /**
     * FIXME 2020-11-19 回调地址
     */
    public static final String REDIRECT_URI = "http://26uy030473.qicp.vip/auth/index";

    @Autowired
    private WxMpService wxMpService;

    @RequestMapping("/base")
    public String guide() {
        String url = wxMpService.getOAuth2Service()
                    .buildAuthorizationUrl(REDIRECT_URI, WxConsts.OAuth2Scope.SNSAPI_BASE, "");

        return "redirect:" + url;
    }

    @RequestMapping("/userinfo")
    public String userinfo() {
        String url = wxMpService.getOAuth2Service()
                        .buildAuthorizationUrl(REDIRECT_URI, WxConsts.OAuth2Scope.SNSAPI_USERINFO, "");

        return "redirect:" + url;
    }

    @RequestMapping("/index")
    public String index(Model model, String code) {
        if (StringUtils.isNotBlank(code)) {
            try {
                // code 换 access_token
                WxOAuth2AccessToken accessToken = wxMpService.getOAuth2Service().getAccessToken(code);

                // access_token 获取 用户信息
                WxOAuth2UserInfo wxMpUser = wxMpService.getOAuth2Service().getUserInfo(accessToken, null);
                String user = JSON.toJSONString(wxMpUser);

                model.addAttribute("user", user);

                log.info("code: {}, access-token: {}, wx-mp-user: {}", code, JSON.toJSONString(accessToken), user);
            } catch (WxErrorException e) {
                log.error("获取微信用户信息错误", e);
            }
        }

        // return "redirect:http://www.baidu.com";
        return "index";
    }

    @RequestMapping("/basic_access_token")
    @ResponseBody
    public String basicAccessToken() {
        try {
           return wxMpService.getAccessToken();
        } catch (WxErrorException e) {
            log.error("获取access_token错误", e);
        }
        return "failure";
    }

    public static void main(String[] args) {
        System.out.println(UUID.randomUUID().toString().replace("-", ""));
    }
}
