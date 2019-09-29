# weixin-sdk-examples

GitHub：[https://github.com/wechat-group/WxJava](https://github.com/wechat-group/WxJava)

微信开发新手请务必阅读[【开发文档 Wiki 首页】](https://github.com/Wechat-Group/WxJava/wiki)的常见问题部分，可以少走很多弯路，节省不少时间。

## `Demo` 项目
### 说明
1. 在码云和 `GitHub` 上均可访问，会尽量保持同步，请根据自己情况选用。
2. 一般来说，`Github`上的版本应该是最新的，但也有可能没及时同步，此种情况下请以 `Github` 上的版本为准，有问题也请在 `Github` 对应项目 `Issues` 页面提问）。
3. 欢迎提供更多的 demo 实现。

### `Spring Boot Starter` 实现
- 微信支付：[点击查看使用方法](https://github.com/Wechat-Group/WxJava/tree/master/spring-boot-starters/wx-java-pay-spring-boot-starter)
- 微信公众号：[点击查看使用方法](https://github.com/Wechat-Group/WxJava/tree/master/spring-boot-starters/wx-java-mp-spring-boot-starter)
- 微信小程序：[点击查看使用方法](https://github.com/Wechat-Group/WxJava/tree/master/spring-boot-starters/wx-java-miniapp-spring-boot-starter)

### Demo 列表
1. 微信支付 Demo：[GitHub](http://github.com/binarywang/weixin-java-pay-demo)、[码云](http://gitee.com/binary/weixin-java-pay-demo)
2. 企业号/企业微信 Demo：[GitHub](http://github.com/binarywang/weixin-java-cp-demo)、[码云](http://gitee.com/binary/weixin-java-cp-demo)
3. 微信小程序 Demo：[GitHub](http://github.com/binarywang/weixin-java-miniapp-demo)、[码云](http://gitee.com/binary/weixin-java-miniapp-demo)
4. 开放平台 Demo：[GitHub](http://github.com/Wechat-Group/weixin-java-open-demo)、[码云](http://gitee.com/binary/weixin-java-open-demo)
5. 公众号 Demo：
	- 使用 `Spring MVC` 实现的公众号 Demo：[GitHub](http://github.com/binarywang/weixin-java-mp-demo-springmvc)、[码云](https://gitee.com/binary/weixin-java-mp-demo)
	- 使用 `Spring Boot` 实现的公众号 Demo（支持多公众号）：[GitHub](http://github.com/binarywang/weixin-java-mp-demo-springboot)、[码云](http://gitee.com/binary/weixin-java-mp-demo-springboot)
	- 含公众号和部分微信支付代码的 Demo：[GitHub](http://github.com/Wechat-Group/weixin-java-springmvc)、[码云](http://gitee.com/binary/weixin-java-tools-springmvc)



## 公众号（包括订阅号和服务号）：weixin-java-mp/wx-java-mp-spring-boot-starter

- [wx-java-mp-spring-boot-starter](https://github.com/Wechat-Group/WxJava/tree/master/spring-boot-starters/wx-java-mp-spring-boot-starter)

1. 引入依赖
    ```xml
    <dependency>
        <groupId>com.github.binarywang</groupId>
        <artifactId>wx-java-mp-spring-boot-starter</artifactId>
        <version>${version}</version>
    </dependency>
    ```
2. 添加配置(application.properties)
    ```properties
    # 公众号配置(必填)
    wx.mp.appId = @appId
	wx.mp.secret = @secret
	wx.mp.token = @token
	wx.mp.aesKey = @aesKey
	# 存储配置redis(可选)
	wx.mp.config-storage.type = redis
	wx.mp.config-storage.redis.host = 127.0.0.1
	wx.mp.config-storage.redis.port = 6379
    ```
3. 支持自动注入的类型

`WxMpService`以及相关的服务类, 比如: `wxMpService.getXxxService`。


## 微信支付：weixin-java-pay/wx-java-pay-spring-boot-starter

- [wx-java-pay-spring-boot-starter](https://github.com/Wechat-Group/WxJava/tree/master/spring-boot-starters/wx-java-pay-spring-boot-starter)

1. 在自己的Spring Boot项目里，引入maven依赖
```xml
    <dependency>
        <groupId>com.github.binarywang</groupId>
        <artifactId>wx-java-pay-spring-boot-starter</artifactId>
        <version>${version}</version>
    </dependency>
```

2. 添加配置(application.yml)
```yml
wx:
  pay:
    appId: #微信公众号或者小程序等的appid
    mchId: #微信支付商户号
    mchKey: #微信支付商户密钥
    subAppId: #服务商模式下的子商户公众账号ID
    subMchId: #服务商模式下的子商户号
    keyPath: # p12证书的位置，可以指定绝对路径，也可以指定类路径（以classpath:开头）
```


## 错误
1. 付款金额限制
返回代码：SUCCESS，
返回信息：支付失败，
结果代码：FAIL，
错误代码：AMOUNT_LIMIT，
错误详情：付款金额超出限制。低于最小金额1.00元或累计超过20000.00元。

2. 姓名强校验




