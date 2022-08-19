# 支付宝小程序，订阅消息

- 支付宝小程序消息订阅插件: <https://opendocs.alipay.com/mini/plugin/message-subscription>
- From 蚂蚁消息服务使用: <https://opendocs.alipay.com/common/02km9j>
- WebSocket长连接接入: <https://opendocs.alipay.com/common/02kiq1>


## Q&A
1. 取消用户授权是否会有通知
<https://opendocs.alipay.com/support/01rg6p>

备注：按文档中的描述，用户主动取消订阅消息，应该是会有From消息的。

2. 页面唤起消息订阅，是否有From消息？
未知。可能没有。

但是，可能的做法：唤起订阅的回调事件中，可以发起请求记录用户订阅的信息！