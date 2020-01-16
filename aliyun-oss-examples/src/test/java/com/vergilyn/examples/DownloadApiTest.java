package com.vergilyn.examples;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.aliyun.oss.model.DownloadFileRequest;
import com.aliyun.oss.model.DownloadFileResult;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;

import org.testng.annotations.Test;

/**
 * <a href="https://help.aliyun.com/document_detail/32014.html">oss下载文件</a>
 *
 * @date 2019/2/14
 */
public class DownloadApiTest extends AbstractTestng {

    /**
     * <a href="https://help.aliyun.com/document_detail/84823.html">流式下载</a>
     * <p> 如果要下载的文件太大，或者一次性下载耗时太长，您可以通过流式下载，一次处理部分内容，直到完成文件的下载。</p>
     * <p>
     *     `ossObject.getObjectContent()`实际就是`HttpResponse.getEntity().getContent()`
     * </p>
     */
    @Test
    public void buffer() {
        OSSObject ossObject = null;
        BufferedReader reader = null;
        try{
            // ossObject包含文件所在的存储空间名称、文件名称、文件元信息以及一个输入流。
            ossObject = ossClient.getObject(BUCKET_NAME, EXIST_OBJECT_NAME);

            // 读取文件内容。
            System.out.println("Object content:");
            reader = new BufferedReader(new InputStreamReader(ossObject.getObjectContent()));
            while (true) {
                String line = reader.readLine();
                if (line == null) break;

                System.out.println("\n" + line);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                // 数据读取完成后，获取的流必须关闭，否则会造成连接泄漏，导致请求无连接可用，程序无法正常工作。
                if (reader != null) reader.close();

                // ossObject对象使用完毕后必须关闭，否则会造成连接泄漏，导致请求无连接可用，程序无法正常工作。关闭方法如下：
                if (ossObject != null) ossObject.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * <a href="https://help.aliyun.com/document_detail/84824.html">下载到本地文件</a>
     * <p>
     *     内部已经关闭了InputStream[OssObject.getObjectContent()]、OutputStream
     * </p>
     */
    @Test
    public void toLocalFile(){
        // 下载OSS文件到本地文件。如果指定的本地文件存在会覆盖，不存在则新建。
        ObjectMetadata metadata = ossClient.getObject(new GetObjectRequest(BUCKET_NAME, EXIST_OBJECT_NAME), new File("D://xxx.jpg"));
        print(metadata);
    }

    /**
     * <a href="https://help.aliyun.com/document_detail/84825.html">范围下载</a>
     * <p>如果仅需要文件中的部分数据，您可以使用范围下载，下载指定范围内的数据。</p>
     * <p>
     *   可以通过此结合{@linkplain java.io.RandomAccessFile}可实现从断点处继续下载。
     * </p>
     */
    @Test
    public void range(){
        GetObjectRequest getObjectRequest = new GetObjectRequest(BUCKET_NAME, EXIST_OBJECT_NAME);
        // 获取0~1000字节范围内的数据，包括0和1000，共1001个字节的数据。如果指定的范围无效（比如开始或结束位置的指定值为负数，或指定值大于文件大小），则下载整个文件。
        // 实质就是设置HttpHeader#Range
        getObjectRequest.setRange(0, 1000);

        // 范围下载。
        OSSObject ossObject = ossClient.getObject(getObjectRequest);

        // 读取数据。
        byte[] buf = new byte[1024];
        try(InputStream in = ossObject.getObjectContent()) {
            for (int n = 0; n != -1; ) {
                n = in.read(buf, 0, buf.length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * <a href="https://help.aliyun.com/document_detail/84827.html">断点续传下载</a>
     * <p>
     *  当下载大文件时，如果网络不稳定或者程序异常退出，会导致下载失败，甚至重试多次仍无法完成下载。为此OSS提供了断点续传下载功能。</br>
     *  断点续传下载将需要下载的文件分成若干个分片分别下载，所有分片都下载完成后，将所有分片合并成完整的文件。
     * </p>
     */
    @Test
    public void checkpoint(){
        // 下载请求，10个任务并发下载，启动断点续传。
        DownloadFileRequest downloadFileRequest = new DownloadFileRequest(BUCKET_NAME, EXIST_OBJECT_NAME);

        // 本地文件。OSS文件将下载到该文件。default: OSS文件名称  require: false
        // 不能指定directory，若指定必须全路径包含后缀
//        downloadFileRequest.setDownloadFile("<yourDownloadFile>");

        // 分片大小，取值范围为1B~5GB。default: 文件大小/10000  require: false
        downloadFileRequest.setPartSize(1 * 1024 * 1024);

        // 分片下载的并发数。	default: 1  require: false
        downloadFileRequest.setTaskNum(1);

        // 是否开启断点续传功能。 default: false  require: false
        downloadFileRequest.setEnableCheckpoint(true);

        // 记录本地分片下载结果的文件。开启断点续传功能时需要设置此参数。
        // 下载过程中的进度信息会保存在该文件中，如果某一分片下载失败，再次下载时会根据文件中记录的点继续下载。
        // 下载完成后，该文件会被删除。
        // default: downloadFile.dcp（与DownloadFile同目录）  require: false
//        downloadFileRequest.setCheckpointFile("<yourCheckpointFile>");

        // 下载文件。
        DownloadFileResult downloadRes = null;
        try {
            downloadRes = ossClient.downloadFile(downloadFileRequest);
            // 下载成功时，会返回文件元信息。
            ObjectMetadata metadata = downloadRes.getObjectMetadata();
            print(metadata);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
