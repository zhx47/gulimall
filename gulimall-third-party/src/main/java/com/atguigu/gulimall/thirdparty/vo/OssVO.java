package com.atguigu.gulimall.thirdparty.vo;

import com.atguigu.gulimall.thirdparty.utils.UUID;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author FrozenWatermelon
 * @date 2020/9/12
 */
@Data
public class OssVO {

    private String dir;
    private String fileName;

    public OssVO(MultipartFile file) {

    }


}