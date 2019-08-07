package com.vergilyn.examples.controller;

import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author VergiLyn
 * @date 2019-08-02
 */
@Controller
@Slf4j
public class IndexController {
    @Autowired private WxMpService wxMpService;

    @RequestMapping("/guide")
    public String guide() {

        String url = wxMpService
                .oauth2buildAuthorizationUrl("http://26uy030473.qicp.vip/index", WxConsts.OAuth2Scope.SNSAPI_USERINFO,
                        "");

        return "redirect:" + url;
    }

    @RequestMapping("/index")
    public String index(Model model, String code) {
        if (StringUtils.isNotBlank(code)) {
            try {
                // code 换 access_token
                WxMpOAuth2AccessToken accessToken = wxMpService.oauth2getAccessToken(code);

                // access_token 获取 用户信息
                WxMpUser wxMpUser = wxMpService.oauth2getUserInfo(accessToken, null);
                String user = JSON.toJSONString(wxMpUser);

                log.info("code: {}, access-token: {}, wx-mp-user: {}", code, JSON.toJSONString(accessToken), user);
            } catch (WxErrorException e) {
                e.printStackTrace();
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
            e.printStackTrace();
        }
        return "failure";
    }
}
