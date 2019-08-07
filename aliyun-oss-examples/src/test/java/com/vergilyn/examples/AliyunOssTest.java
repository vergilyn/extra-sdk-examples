package com.vergilyn.examples;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import com.aliyun.oss.model.Callback;
import com.aliyun.oss.model.DownloadFileRequest;
import com.aliyun.oss.model.DownloadFileResult;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.SimplifiedObjectMeta;
import com.aliyun.oss.model.UploadFileRequest;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.testng.annotations.Test;

/**
 * <a href="https://help.aliyun.com/document_detail/52834.html">aliyun-oss SDK文档简介<a/>
 * @date 2019/1/10
 */
public class AliyunOssTest extends BasicTestng {

    @Test
    public void doesObjectExist() {
        // 判断文件是否存在。doesObjectExist还有一个参数isOnlyInOSS，如果为true则忽略302重定向或镜像；
        // 如果为false，则考虑302重定向或镜像。
        boolean found = ossClient.doesObjectExist(BUCKET_NAME, EXIST_OBJECT_NAME);
        System.out.printf("bucket: %s \nobject: %s \nisFound: %b \n", BUCKET_NAME, EXIST_OBJECT_NAME, found);
    }

    @Test
    public void metadata(){
        // 获取文件的部分元信息。
        SimplifiedObjectMeta objectMeta = ossClient.getSimplifiedObjectMeta(BUCKET_NAME, EXIST_OBJECT_NAME);
        System.out.println("size: " + objectMeta.getSize());
        System.out.println("etag: " + objectMeta.getETag());
        System.out.println("LastModified: " + objectMeta.getLastModified());

        // 获取文件的全部元信息。
        ObjectMetadata metadata = ossClient.getObjectMetadata(BUCKET_NAME, EXIST_OBJECT_NAME);
        System.out.println("ContentType: " + metadata.getContentType());
        System.out.println("LastModified: " + metadata.getLastModified());
        try {
            System.out.println("ExpirationTime: " + metadata.getExpirationTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * <a href="https://help.aliyun.com/document_detail/84785.html?spm=a2c4g.11186623.6.666.51324d83Zmy6Er">断点续传上传</a>
     * <a href="https://help.aliyun.com/knowledge_detail/39533.html?spm=5176.11065259.1996646101.searchclickresult.648c4ab97ZMBQ5">OSS MD5校验说明</a>
     */
    @Test
    public void uploadFile(){
        File uploadFile = new File("D:\\src_image.jpg");

        // 通过UploadFileRequest设置多个参数。
        UploadFileRequest uploadFileRequest = new UploadFileRequest(BUCKET_NAME, uploadFile.getName());

        // 指定上传的本地文件。
        uploadFileRequest.setUploadFile(uploadFile.getPath());
        // 指定上传并发线程数，默认为1。
        uploadFileRequest.setTaskNum(5);
        // 指定上传的分片大小，范围为100KB~5GB，默认为文件大小/10000。
        uploadFileRequest.setPartSize(1024 * 1024);
        // 开启断点续传，默认关闭。
        uploadFileRequest.setEnableCheckpoint(true);

        // 记录本地分片上传结果的文件。
        // 开启断点续传功能时需要设置此参数，上传过程中的进度信息会保存在该文件中，如果某一分片上传失败，再次上传时会根据文件中记录的点继续上传。
        // 上传完成后，该文件会被删除。默认与待上传的本地文件同目录，为uploadFile.ucp。
        try {
            File checkpointFile = new File(uploadFile.getPath() + ".ucp");
            checkpointFile.createNewFile();
            uploadFileRequest.setCheckpointFile(checkpointFile.getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 文件的元数据。
        ObjectMetadata meta = new ObjectMetadata();
        // 指定上传的内容类型。
        meta.setContentType("text/plain");
        // uploadFileRequest.setObjectMetadata(meta);

        // 断点续传上传。
        try {
            // 设置上传成功回调，参数为Callback类型。
            // 上传回调参数。
            Callback callback = new Callback();
            callback.setCallbackUrl("callbackUrl");
            // 设置回调请求消息头中Host的值，如oss-cn-hangzhou.aliyuncs.com。
            callback.setCallbackHost("oss-cn-hangzhou.aliyuncs.com");
            // 设置发起回调时请求body的值。
            callback.setCallbackBody("{\\\"mimeType\\\":${mimeType},\\\"size\\\":${size}}");
            // 设置发起回调请求的Content-Type。
            callback.setCalbackBodyType(Callback.CalbackBodyType.JSON);
            // 设置发起回调请求的自定义参数，由Key和Value组成，Key必须以x:开始。
            callback.addCallbackVar("x:var1", "value1");
            callback.addCallbackVar("x:var2", "value2");
            // uploadFileRequest.setCallback(callback);

            ossClient.uploadFile(uploadFileRequest);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    /**
     * 利用"回源"实现文件上传至aliyun-oss。（只需在aliyun-oss控制台设置回源规则，访问该资源。若aliyun-oss不存在，则会访问源地址并下载至aliyun-oss）
     * <p>缺点：无法指定目录路径。</p>
     */
    @Test
    public void backToSource(){
        HttpClient httpClient = null;
        HttpGet httpGet = null;
        try {

            httpClient = HttpClientBuilder.create().build(); //创建默认的httpClient实例
            httpGet = new HttpGet("<uri>"); // 配置了回源规则的uri
            HttpResponse response = httpClient.execute(httpGet);
            response.getStatusLine();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                ((CloseableHttpClient) httpClient).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * <a href="https://help.aliyun.com/document_detail/84827.html?spm=a2c4g.11186623.6.674.a3113ab7ibcZFO">断点续传下载</a>
     * <ol>
     *   <li>本地目录需要提前创建；</li>
     *   <li>需要完整的本地路径，包括文件后缀；</li>
     *   <li>若本地存在，会覆盖本地。</li>
     * </ol>
     */
    @Test
    public void downloadFile(){
        String objectName = "src_image.jpg";
        // 下载请求，10个任务并发下载，启动断点续传。
        DownloadFileRequest downloadFileRequest = new DownloadFileRequest(BUCKET_NAME, objectName);
        downloadFileRequest.setDownloadFile("D://aliyun-oss-download.jpg");  // 目录需要提前创建; 需要指定后缀
        downloadFileRequest.setPartSize(1 * 1024 * 1024);
        downloadFileRequest.setTaskNum(10);

        // 断点续传下载 ucp文件
        downloadFileRequest.setEnableCheckpoint(true);
        // downloadFileRequest.setCheckpointFile("<yourCheckpointFile>");

        // 下载文件。
        DownloadFileResult downloadRes = null;
        try {
            downloadRes = ossClient.downloadFile(downloadFileRequest);
            // 下载成功时，会返回文件元信息。
            ObjectMetadata metadata = downloadRes.getObjectMetadata();
            System.out.println("ContentType: " + metadata.getContentType());
            System.out.println("LastModified: " + metadata.getLastModified());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
