package com.atguigu.gulimall.thirdparty.config;

import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class MinioTemplate {

    private final OssConfig ossConfig;

    private final MinioClient minioClient;


    /**
     * 删除文件
     *
     * @param objectName 文件名称
     * @throws Exception <a href="https://docs.minio.io/cn/java-client-api-reference.html#removeObject">...</a>
     */
    public void removeObject(String objectName) throws Exception {
        minioClient.removeObject(RemoveObjectArgs.builder().object(objectName).bucket(ossConfig.getBucket()).build());
    }

    /**
     * 获得上传的URL
     *
     * @param objectName 文件名称
     */
    public String getPresignedObjectUrl(String objectName) throws Exception {
        try {
            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().bucket(ossConfig.getBucket()).object(objectName).expiry(10, TimeUnit.MINUTES).method(Method.PUT).build());
        } catch (Exception e) {
            log.error("minio获取上传URL错误：", e);
            // TODO 自定义异常
            throw new Exception("获取上传URL错误");
        }
    }

    /**
     * 上传文件
     *
     * @param bytes       文件字节
     * @param filePath    文件路径
     * @param contentType 文件类型
     * @throws IOException IOException
     */
    public void uploadMinio(byte[] bytes, String filePath, String contentType) throws IOException {
        InputStream input = null;
        try {
            input = new ByteArrayInputStream(bytes);
            minioClient.putObject(PutObjectArgs.builder().bucket(ossConfig.getBucket()).contentType(contentType).stream(input, input.available(), -1).object(filePath).build());
        } catch (Exception e) {
            log.error("minio上传文件错误：", e);
        } finally {
            if (Objects.nonNull(input)) {
                input.close();
            }
        }
    }
}