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
当然，也可以继续改散，不