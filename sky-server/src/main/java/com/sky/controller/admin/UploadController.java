package com.sky.controller.admin;

import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/common/upload")
public class UploadController {
    @Autowired
    private AliOssUtil ossUtil;
    @PostMapping
    public Result upload(@RequestParam("file") MultipartFile file) {
        log.info("开始上传文件：{}", file.getOriginalFilename());
        try {
            // 1. 获取原始文件名
            String originalFilename = file.getOriginalFilename();
            // 2. 截取文件后缀 (例如: .jpg)
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            // 3. 构造新文件名 (UUID + 后缀)
            String objectName = UUID.randomUUID().toString() + extension;

            // 4. 调用工具类上传到阿里云 OSS，返回的是图片的完整 URL 路径
            String filePath = ossUtil.upload(file.getBytes(), objectName);

            return Result.success(filePath);
        } catch (IOException e) {
            log.error("文件上传失败：{}", e.getMessage());
        }

        return Result.error(MessageConstant.UPLOAD_FAILED);
    }

    }
