# aliyun-oss-examples

## 1. 断点分片下载实现思路
see: 
- com.aliyun.oss.internal.OSSDownloadOperation.downloadFile(DownloadFileRequest downloadFileRequest)
- com.aliyun.oss.internal.OSSDownloadOperation.downloadFileWithCheckpoint(DownloadFileRequest downloadFileRequest)

1. 校验必要的参数，及设置默认值。
`com.aliyun.oss.internal.OSSDownloadOperation.downloadFile(DownloadFileRequest downloadFileRequest)`


2. 若`enableCheckpoint=true`开启断点续传功能，则从`dcp`加载已记录本地分片下载结果的文件。
校验记录信息和服务器文件信息是否匹配：size、lastModified、ETag（类似文件UUID标识）。 若不匹配，则会重新下载(删除之前的dcp文件)。

> ETag：  
ETag (entity tag) 在每个Object生成的时候被创建，用于标示一个Object的内容。
对于PutObject请求创建的Object，ETag值是其内容的MD5值；对于其他方式创建的Object，ETag值是其内容的UUID。
**ETag值可以用于检查Object内容是否发生变化**。*不建议用户使用ETag来作为Object内容的MD5校验数据完整性*。

性能疑问：
1. 构建分片信息文件时，会去获取 服务器文件 的信息。`com.aliyun.oss.internal.OSSDownloadOperation.prepare()` 的 ObjectStat.getFileStat...
    若后面是复用此连接，那么不会存在多次建立连接的性能问题。
    
2. 当某分片下载完成时，都会覆写dcp文件。 `com.aliyun.oss.internal.OSSDownloadOperation.Task.call`

## 2. 下载进度条
通过下载源码可知，实际的百分比是：`已下载分片数 / 总分片数`。
并且是在某个分片下载成功后，才会去更新这百分比。并不是实时性的，也不是`已下载大小 / 文件总大小`;
（备注：若某分片下载完成99%，下次重新下载也会重新下载整个分片，而不是剩余的1%。
感觉修改源码也可以保留这99%，只需要增加记录每个分片已下载大小，然后每个分片下载时再计算需要请求的ranges和RandomAccessFile.seek。

**记录每个分片已下载的大小应该会相当耗性能**
）

疑问：
想知道迅雷或其余下载进度条是如何实现的？
若分片数足够多（相对的分片的大小更小），感觉也有`已下载大小 / 文件总大小`。
如果要改也是可以的，即每次write后，就更新进度条，而不是在某个分片下载完后，
但这同时牵涉到无效的分片大小，会导致百分比错误（或者表现为原本是40%，但会变成35%后正常。 5%即某个只下载99%的分片）。


### **Aliyun OSS 是否支持网络抓取？**
问题描述：网络资源（例如视频、图片）如何直接上传到OSS？
场景假设：
  我方不定时会同步甲方提供的数据（例如一篇新闻内容），新闻正文中包含一些资源（主要是视频、图片），这些资源属于甲方域名下。
  我方现在需要将这些资源保存在我方自己的OSS上，有什么较好的方案？

简单直接的做法是：
  1. 先将资源下载到我方的服务器；
  2. 再通过SDK上传到OSS。

在查看官方文档和提交工单咨询后，得到的答复是：
> OSS目前还没有提供抓取网络自有的方案，只能您这边**下载到本地再上传到OSS**或者使用在线迁移的https迁移方式。
> [HTTP/HTTPS 源迁移教程](https://help.aliyun.com/document_detail/95134.html)
> [OSS 分片上传和断点续传](https://help.aliyun.com/document_detail/31850.html)

方案虽然可行，但其中需要考虑的：
1) 服务器用的也是Aliyun ECS，下载速度 100mb/s没问题（具体可以验证一下）。

2) 如果执行下载的服务实例所在的服务器还在做别的事情（例如存在提供给用户访问的服务实例），
那么“下载到本地再上传到OSS”一定会占用 下行&上行 带宽，从而严重影响用户体验。

3) 例如一篇新闻中，有5张图和1个视频。如何保证这6个资源完整的上传到了OSS，以及（何时）替换成OSS的访问路径？  


2020-01-17: 建议异步 下载&上传
1) 资源最初保留源地址，不做替换
2) 异步下载到服务器本地（最好保存 下载记录，便于重新下载）
3) 本地下载完，再创建上传OSS任务（保存 上传记录，便于重新上传）
4) 上传完成后，通过 OSS上传回调 事件，再通过callback-body来找到 源数据，进行资源路径替换

只是存在一段时间，资源地址是数据来源的源地址。但这种流程目前能想到较好的流程。
同步下载&上传&保存数据，也不是不可行，但要特别注意“长事务”。

#### 针对资源替换`3)`
假如`下载-上传-替换资源-保存`在同一个事务（并且是`同步`），若资源过大，很可能是“长事务”。

但如果是`异步`，找出需要下载的资源，并替换URL后直接保存（异步下载资源）。
这样避免了“长事务”，但是<font color="red">若新闻保存后直接是“发布状态”，意味着一段时间用户查看到会是 破图 和 无法观看的视频。</font>  
若保存后是`草稿状态`，通过程序判断“资源是否下载”完毕 也是意见麻烦的事情。



