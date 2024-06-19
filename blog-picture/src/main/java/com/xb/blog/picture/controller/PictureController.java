package com.xb.blog.picture.controller;

import com.xb.blog.picture.entity.PictureEntity;
import com.xb.blog.picture.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

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
