# alibaba-dingtalk-examples

- [企业内部应用 - 服务端API](https://ding-doc.dingtalk.com/doc#/serverapi2/gh60vz)

SDK: <https://ding-doc.dingtalk.com/doc#/faquestions/vzbp02>
```
<dependency>
    <groupId>com.aliyun</groupId>
    <artifactId>alibaba-dingtalk-service-sdk</artifactId>
    <version>1.0.1</version>
</dependency>
```

2020-11-24 >>>>  
与其用dingtalk-sdk，不如http-client自己写。


## 1. 发起工作流审批
- [工作流 - 发起审批实例](https://ding-doc.dingtalk.com/doc#/serverapi2/cmct1a)
- [注册事件回调](https://ding-doc.dingtalk.com/doc#/serverapi2/pwz3r5)
- [审批事件回调](https://ding-doc.dingtalk.com/doc#/serverapi2/lwwrcu)

1. 如何判断审批**最终**结果？
例如存在请假流程`发起请假 -> 审批人A -> 审批人B`。

以上流程中，如果注册监听了事件`bpms_task_change & bpms_instance_change`会接收到很多回调事件。

判断**审批最终结果**需要通过`EventType == "bpms_instance_change"`以及`type & agree`联合判断。
```JSON
{
    "EventType": "bpms_instance_change",
    "type": "finish",
    "result": "agree"
}
```

备注：  
如果只是在审批完成后处理，可以只监听`bpms_instance_change`，**这样回调少很多**。

2.`获取回调失败的结果`
调用该API后，每次返回的时间列表最多200个，且**dingtalk会立即删除该页的数据！**  
返回的数据包含很多的event（**不支持查询指定的event**），并且数据中还包含`回调失败数据所属corpid`。
