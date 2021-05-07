# aliyun-vod-examples

+ [视频点播](https://help.aliyun.com/document_detail/61062.html)
+ [视频点播 - 计费](https://www.aliyun.com/price/product?/vod/detail#/vod/detail)
+ [视频点播 - console](https://vod.console.aliyun.com/overview#/overview)
+ [视频点播 - WEB播放器在线配置](https://player.alicdn.com/aliplayer/setting/setting.html)

视频点播（ApsaraVideo VoD，简称VoD）是集视频采集、编辑、上传、媒体资源管理、
自动化转码处理（窄带高清TM）、视频审核分析、分发加速于一体的一站式音视频点播解决方案。


### 客户端JavaScript上传
- [使用JavaScript-STS方式上传](https://help.aliyun.com/document_detail/65857.html)
- [使用JavaScript上传SDK](https://help.aliyun.com/document_detail/52204.html)

#### 设置视频封面
+ [设置视频封面](https://help.aliyun.com/document_detail/101153.html)

1. [视频点播 - console](https://vod.console.aliyun.com)
2. [服务端API - 获取视频上传地址和凭证](https://help.aliyun.com/document_detail/55407.html)
3. [服务端API - URL批量拉取上传](https://help.aliyun.com/document_detail/86311.html)
4. [服务端API - 注册媒资信息](https://help.aliyun.com/document_detail/124532.html)
5. [服务端API - 修改视频信息](https://help.aliyun.com/document_detail/52836.html)
6. [WEB播放器](https://player.alicdn.com/aliplayer/setting/setting.html)

### 阿里云Aliplayer播放器
+ [阿里云Aliplayer播放器 - 在线配置](https://player.alicdn.com/aliplayer/setting/setting.html)
+ [视频点播 > 播放器SDK > Web播放器 > 属性和接口说明](https://help.aliyun.com/document_detail/125572.html)

### Q&A
#### 1. JS上传完成后，立即获取视频信息，可能返回NULL的 `cover & duration`
- [获取视频播放地址 GetPlayInfo](https://help.aliyun.com/document_detail/56124.html)
- [获取视频信息 GetVideoInfo](https://help.aliyun.com/document_detail/52835.html)
- code参考：[sts-upload.html](src/main/resources/static/vod-upload-demo/sts-upload.html)

> 视频状态 https://help.aliyun.com/document_detail/52839.html#title-vqg-8cz-7p8
<table data-tag="table" id="table-lod-moz-2u4" class="table"><colgroup data-tag="colspec" colname="col1" colwidth="1*" id="colspec-2vo-wqc-142" copies="1" style="width:33.33333333333333%" colnum="1" class="colspec"></colgroup><colgroup data-tag="colspec" colname="col2" colwidth="1*" id="colspec-wwc-6gd-8l3" copies="1" style="width:33.33333333333333%" colnum="2" class="colspec"></colgroup><colgroup data-tag="colspec" colname="col3" colwidth="1*" id="colspec-j9q-qw5-wkq" copies="1" style="width:33.33333333333333%" colnum="3" class="colspec"></colgroup><thead data-tag="thead" id="thead-415-tmo-a2w" class="thead"><tr data-tag="row" id="row-6oo-2kj-skw"><th data-tag="entry" id="entry-pg3-d4o-noc" class="entry"><p id="p-al1-0ue-kay">取值</p></th><th data-tag="entry" id="entry-9g0-pnr-78j" class="entry"><p id="p-74h-gg4-c49">描述</p></th><th data-tag="entry" id="entry-cnt-qcg-g51" class="entry"><p id="p-nyj-5zx-h9d">备注</p></th></tr></thead><tbody data-tag="tbody" id="tbody-v8s-bqi-mkq" class="tbody" data-spm-anchor-id="a2c4g.11186623.2.i87.64a3119cFLwrsu"><tr data-tag="row" id="row-a61-kjc-3cf"><td data-tag="entry" id="entry-70h-qun-g31" class="entry"><p id="p-jc0-a9b-0tj">Uploading</p></td><td data-tag="entry" id="entry-nxs-mve-3a6" class="entry"><p id="p-1t3-0l7-xj9">上传中</p></td><td data-tag="entry" id="entry-7qz-k72-ukc" class="entry"><p id="p-xxr-kaj-mfu">视频的初始状态，表示正在上传。</p></td></tr><tr data-tag="row" id="row-3bq-eka-f30"><td data-tag="entry" id="entry-yxt-5ly-m1m" class="entry"><p id="p-pm0-yu1-uo2">UploadFail</p></td><td data-tag="entry" id="entry-xvr-ejw-9rc" class="entry"><p id="p-yo4-oiv-d90">上传失败</p></td><td data-tag="entry" id="entry-2qe-arg-vu7" class="entry"><p id="p-md2-yup-xo6">由于是断点续传，无法确定上传是否失败，故暂不会出现此值。</p></td></tr><tr data-tag="row" id="row-e3x-2am-3ac"><td data-tag="entry" id="entry-zbw-jyy-x9l" class="entry"><p id="p-m1w-652-q3j">UploadSucc</p></td><td data-tag="entry" id="entry-sci-42w-e0b" class="entry"><p id="p-g4p-day-dds">上传完成</p></td><td data-tag="entry" id="entry-qq1-3mf-icx" class="entry"><p id="p-hek-m2s-8ao">-</p></td></tr><tr data-tag="row" id="row-y77-kjg-e34"><td data-tag="entry" id="entry-0pq-x9v-mht" class="entry"><p id="p-qb6-048-1fv">Transcoding</p></td><td data-tag="entry" id="entry-27m-akq-rpe" class="entry"><p id="p-leb-oqr-i6f">转码中</p></td><td data-tag="entry" id="entry-t4c-6zz-nff" class="entry"><p id="p-6l8-s72-r3g">-</p></td></tr><tr data-tag="row" id="row-hqh-dpy-xgk"><td data-tag="entry" id="entry-fxd-pzt-zgz" class="entry"><p id="p-0th-crn-qk6">TranscodeFail</p></td><td data-tag="entry" id="entry-j13-ro2-iy7" class="entry"><p id="p-9ld-qy2-xu8">转码失败</p></td><td data-tag="entry" id="entry-ldg-fbo-raf" class="entry"><p id="p-heg-wbn-qv7">转码失败，一般是由于原片存在问题。可在事件通知的<span><a href="https://help.aliyun.com/document_detail/55638.html">转码完成消息</a></span> 获取ErrorMessage失败信息，或提交工单联系我们。</p></td></tr><tr data-tag="row" id="row-zdt-k58-h2i"><td data-tag="entry" id="entry-38u-4d9-47u" class="entry"><p id="p-ctl-rju-w6x">Checking</p></td><td data-tag="entry" id="entry-217-rxy-pib" class="entry"><p id="p-qsp-mtq-fp2">审核中</p></td><td data-tag="entry" id="entry-5u1-yqn-4ez" class="entry"><p id="p-qv9-7mf-qnu">在<span><a href="https://www.aliyun.com/product/vod?spm=5176.8142029.388261.421.78de76f4IhQkZa">视频点播控制台</a></span> &gt; <b data-tag="uicontrol" id="uicontrol-jz5-h7y-un9" class="uicontrol">审核管理</b> &gt; <b data-tag="uicontrol" id="uicontrol-097-4ln-ez2" class="uicontrol">审核设置</b> 开启了 <b data-tag="uicontrol" id="uicontrol-shg-rhp-07f" class="uicontrol">先审后发</b>，转码成功后视频状态会变成 <b data-tag="uicontrol" id="uicontrol-rjt-kr6-vvk" class="uicontrol">审核中</b>，此时视频只能在控制台播放。</p></td></tr><tr data-tag="row" id="row-82n-e6o-r1j"><td data-tag="entry" id="entry-qjg-tlb-53d" class="entry"><p id="p-fct-77n-pda">Blocked</p></td><td data-tag="entry" id="entry-gmm-en8-hfb" class="entry"><p id="p-i1b-uwt-dy1">屏蔽</p></td><td data-tag="entry" id="entry-v08-0ne-dc5" class="entry"><p id="p-ezu-fqb-ty5">在审核时屏蔽视频。</p></td></tr><tr data-tag="row" id="row-p50-273-teh"><td data-tag="entry" id="entry-0k1-be3-ro8" class="entry"><p id="p-34b-3rv-x27">Normal</p></td><td data-tag="entry" id="entry-ja4-j5q-x8c" class="entry"><p id="p-j5s-aej-dyz">正常</p></td><td data-tag="entry" id="entry-xhx-zgr-7im" class="entry"><p id="p-wq2-fbs-1ll">视频可正常播放。</p></td></tr><tr data-tag="row" id="row-00l-5tp-qjt"><td data-tag="entry" id="entry-61n-295-a24" class="entry"><p id="p-4d3-og8-bwu">ProduceFail</p></td><td data-tag="entry" id="entry-h5l-e03-5r6" class="entry"><p id="p-vkh-7v7-pp2">合成失败</p></td><td data-tag="entry" id="entry-enh-2e1-hxt" class="entry" data-spm-anchor-id="a2c4g.11186623.2.i88.64a3119cFLwrsu"><p id="p-o89-c1v-ypy" data-spm-anchor-id="a2c4g.11186623.2.i86.64a3119cFLwrsu">-</p></td></tr></tbody></table>


JS上传完成后，如果立即调用`GetPlayInfo`，可能该视频还处于`videoBase.status:"UploadSucc"`，  
此时播放信息中：`"duration":"0.0", "coverURL": null`。

如果立即调用`GetVideoInfo`，其中的 `cover` 也不是一定是有效的。

+ [事件通知](https://help.aliyun.com/document_detail/55627.htm)
- [视频转码完成 TranscodeComplete](https://help.aliyun.com/document_detail/55638.htm)
- [视频截图完成 SnapshotComplete](https://help.aliyun.com/document_detail/57337.htm)

> 截图和转码是并行处理，二者无法确定先后顺序。  
> 如果是封面截图类型，并且没有设置视频封面（CoverUrl），则默认取截图的中间一张为视频封面。
> 关于封面截图，更多信息，请参见[视频截图](https://help.aliyun.com/document_detail/99368.htm)。


