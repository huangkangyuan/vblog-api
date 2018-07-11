package com.seu.blog.controller;

import com.alibaba.fastjson.JSONObject;
import com.seu.common.component.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 图片上传
 *
 * @author liangfeihu
 * @since 2018/7/10 18:19.
 */
@RestController
public class UploadController {

    private static final Logger logger = LoggerFactory.getLogger(UploadController.class);


    @Value("${vblog.upload.path}")
    private String baseFolderPath;

    @PostMapping("/upload")
    public R upload(HttpServletRequest request, MultipartFile image) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String filePath = sdf.format(new Date());

        File baseFolder = new File(baseFolderPath + filePath);
        if (!baseFolder.exists()) {
            baseFolder.mkdirs();
        }

        StringBuffer url = new StringBuffer();
        url.append(request.getScheme())
                .append("://")
                .append(request.getServerName())
                .append(":")
                .append(request.getServerPort())
                .append(request.getContextPath())
                .append("/")
                .append(filePath);

        String imgName = UUID.randomUUID().toString().replace("_", "") + "_" + image.getOriginalFilename().replaceAll(" ", "");
        try {
            File dest = new File(baseFolder, imgName);
            image.transferTo(dest);
            url.append("/").append(imgName);

            JSONObject object = new JSONObject();
            object.put("url", url);

            return R.ok(object);
        } catch (IOException e) {
            logger.error("文件上传错误 , uri: {} , caused by: ", request.getRequestURI(), e);
            return R.error("文件上传错误");
        }
    }
}
