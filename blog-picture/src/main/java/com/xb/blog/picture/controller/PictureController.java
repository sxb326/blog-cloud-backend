package com.xb.blog.picture.controller;

import com.xb.blog.common.core.constants.Result;
import com.xb.blog.picture.entity.PictureEntity;
import com.xb.blog.picture.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Date;

@RestController
public class PictureController {

    @Autowired
    private PictureService pictureService;

    @GetMapping("/{uid}")
    public void getPic(HttpServletResponse response, @PathVariable("uid") String uid) throws IOException {
        PictureEntity entity = pictureService.getById(uid);
        if (entity != null) {
            String storageType = entity.getStorageType();
            if ("local".equals(storageType)) {
                //本地图片 直接读取
                String localPath = entity.getLocalPath();
                String contentType = getContentType(localPath);
                response.setContentType(contentType);
                response.setHeader("Content-Disposition", "inline; filename=\"" + entity.getName() + "\"");
                try (InputStream in = Files.newInputStream(Paths.get(localPath));
                     OutputStream out = response.getOutputStream()) {
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = in.read(buffer)) != -1) {
                        out.write(buffer, 0, bytesRead);
                    }
                    out.flush();
                } catch (IOException e) {
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
            } else if ("os".equals(storageType)) {
                //todo 对象存储图片，从对象存储获取图片
            }
        }
    }

    @PostMapping("/upload")
    public Result upload(MultipartFile file) {
        //todo 判断当前图片保存方式，这里测试方便 先默认为本地存储
        String path = "D:/blog-cloud-img/" + LocalDate.now() + "/" + file.getOriginalFilename();
        File f = new File(path);
        try {
            if (!f.exists()) {
                f.mkdirs();
            }
            file.transferTo(f);
            PictureEntity entity = new PictureEntity();
            entity.setName(file.getOriginalFilename());
            entity.setStorageType("local");
            entity.setLocalPath(path);
            entity.setStatus(1);
            entity.setCreateTime(new Date());
            pictureService.save(entity);
            return Result.success(null, entity.getUid());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getContentType(String path) {
        String contentType = "application/octet-stream";
        int dotIndex = path.lastIndexOf('.');
        if (dotIndex > 0) {
            String extension = path.substring(dotIndex + 1).toLowerCase();
            if ("png".equals(extension)) {
                contentType = "image/png";
            } else if ("jpg".equals(extension) || "jpeg".equals(extension)) {
                contentType = "image/jpeg";
            }
        }
        return contentType;
    }
}
