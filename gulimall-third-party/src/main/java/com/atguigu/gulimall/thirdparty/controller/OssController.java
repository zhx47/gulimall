package com.atguigu.gulimall.thirdparty.controller;

import com.atguigu.common.utils.R;
import com.atguigu.gulimall.thirdparty.config.MinioTemplate;
import com.atguigu.gulimall.thirdparty.config.OssConfig;
import com.atguigu.gulimall.thirdparty.utils.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/oss")
public class OssController {

    private final MinioTemplate minioTemplate;
    private final OssConfig ossConfig;

    /**
     * 上传文件
     *
     * @param file 文件
     * @return R
     * @throws IOException IOException
     */
    @PostMapping("/upload_minio")
    public R uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return R.ok();
        }
        String dir = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE) + "/";
        String fileName = UUID.randomUUID() + getFileExtension(file);
        minioTemplate.uploadMinio(file.getBytes(), dir + fileName, file.getContentType());

        String url = ossConfig.getEndpoint() + "/" + ossConfig.getBucket() + "/" + dir + fileName;
        return R.ok().put("data", url);
    }

    private String getFileExtension(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if (originalFilename != null) {
            int extensionIndex = originalFilename.lastIndexOf(".");
            if (extensionIndex >= 0) {
                return originalFilename.substring(extensionIndex);
            }
        }
        return "";
    }
}