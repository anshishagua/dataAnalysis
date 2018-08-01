package com.anshishagua.controller;

import com.anshishagua.utils.FileUtils;
import com.anshishagua.utils.ImageUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * User: lixiao
 * Date: 2018/8/1
 * Time: 下午4:10
 */

@Controller
@RequestMapping("/image")
public class ImageController {
    @RequestMapping("")
    public ModelAndView index() {
        return new ModelAndView("image/index");
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public void upload(@RequestParam(value = "width", defaultValue = "0") int width,
                       @RequestParam(value = "height", defaultValue = "0") int height,
                       @RequestParam(value = "uploadFile") MultipartFile uploadFile,
                       HttpServletResponse response) {
        String originalFileName = uploadFile.getOriginalFilename();

        String extension = FileUtils.getExtension(originalFileName);
        String destFile = "/tmp/" + UUID.randomUUID().toString() + "." + extension;

        try {
            ImageUtils.resize(destFile, uploadFile.getInputStream(), width, height);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        response.setContentType("application/force-download");// 设置强制下载不打开
        response.addHeader("Content-Disposition", "attachment;fileName=" + destFile);

        try (OutputStream outputStream = response.getOutputStream();
             FileInputStream inputStream = new FileInputStream(destFile)) {
            IOUtils.copy(inputStream, outputStream);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
