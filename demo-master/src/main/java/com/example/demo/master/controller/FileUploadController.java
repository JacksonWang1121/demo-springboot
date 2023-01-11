package com.example.demo.master.controller;

import com.example.demo.master.constance.PublicConstance;
import com.example.demo.master.entity.CommonResponseEntity;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.net.URL;
import java.net.URLEncoder;

/**
 * <p>
 * <h2>简述</h2>
 * 	   <ol>无</ol>
 *   <h2>功能描述</h2>
 * 	   <ol>无</ol>
 *   <h2>修改历史</h2>
 *     <ol>无</ol>
 * </p>
 *
 * @author wangjisen
 * @version 1.0
 * @date 2022/10/21 17:21
 */

@RestController
@RequestMapping("/fileupload")
public class FileUploadController {

    //logback日志
    private static final Logger LOGGER_LOGBACK = LoggerFactory.getLogger(FileUploadController.class);

    /**
     * yml配置中
     */
//    @Value("${appletsdemo.fileupload.path}")
//    private String fileUploadPath;
    @Value("${demomaster.rootpath}")
    private String rootPath;

    /**
     * 文件上传
     * @param files
     * @return
     */
    @PostMapping("/uplaod")
    public CommonResponseEntity<String> fileUpload(@RequestParam("files") MultipartFile[] files) {
        //计时器
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("fileUpload");
        LOGGER_LOGBACK.info("---------------------------Upload Files Start---------------------------");

        if (files == null) {
            CommonResponseEntity<String> responseEntity = new CommonResponseEntity<>();
            responseEntity.setResultCode(PublicConstance.RESPONSE_CODE_SUCCESS);
            responseEntity.setResultDesc("未获取到上传文件，请检查");
            return responseEntity;
        }

        for (MultipartFile multipartFile : files) {
            try {
                if(multipartFile.isEmpty()) {
                    LOGGER_LOGBACK.warn("文件不存在");
                    continue;
                }

                LOGGER_LOGBACK.info(">>>>>>>>>>>>>>>正在上传文件：" + multipartFile.getOriginalFilename());
                File file = new File(rootPath + "\\backup\\" + multipartFile.getOriginalFilename());
                if(file.exists()) {
                    LOGGER_LOGBACK.info(">>>>>>>>>>>>>>>文件：" + multipartFile.getOriginalFilename() + "已存在");
                    continue;
                }
                //保存文件
                multipartFile.transferTo(file);
                LOGGER_LOGBACK.info(">>>>>>>>>>>>>>>文件：" + multipartFile.getOriginalFilename() + "上传成功");
            } catch (Exception ex) {
                LOGGER_LOGBACK.info(">>>>>>>>>>>>>>>文件：" + multipartFile.getOriginalFilename() + "上传失败");
                ex.printStackTrace();
            }
        }

        stopWatch.stop();
        LOGGER_LOGBACK.info("---------------------------Upload Files, Take " + stopWatch.getTotalTimeSeconds() + "s---------------------------");
        return CommonResponseEntity.success();
    }

    /**
     * 文件下载
     * @param request
     * @param userAgent  为了兼容IE判断
     * @param filePath  基于项目根路径的相对路径
     * @param fileName  下载至本地的文件名
     * @param inline  是否在线浏览
     * @return
     */
    @GetMapping("/download")
    public ResponseEntity<byte[]> fileDownload(HttpServletRequest request,
                                               @RequestHeader("user-agent") String userAgent,
                                               @RequestParam("filePath") String filePath,
                                               @RequestParam("fileName") String fileName,
                                               @RequestParam(value = "inline", required = false, defaultValue = "false") boolean inline) {
        //计时器
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("fileDownload");
        LOGGER_LOGBACK.info("---------------------------Download File Start---------------------------");

        File file = new File(rootPath + filePath);

        if(!file.exists()) {
            LOGGER_LOGBACK.warn("文件不存在：" + file.getAbsolutePath());
            return (ResponseEntity<byte[]>) ResponseEntity.badRequest();
        }

        //构建响应
        ResponseEntity.BodyBuilder bodyBuilder = ResponseEntity.ok();
        bodyBuilder.contentLength(file.length());
        //二进制数据流
        bodyBuilder.contentType(MediaType.APPLICATION_OCTET_STREAM);
        try {
            //文件名编码
            String encodeFileName = URLEncoder.encode(fileName, PublicConstance.CHARSET_DEFAULT);
            //IE浏览器
            if(userAgent.indexOf("MSIE") > 0) {
                bodyBuilder.header("Content-Disposition", "attachment;filename=" + encodeFileName);
            } else {
                //其他浏览器
                if(inline) {
                    //在浏览器中打开
                    URL url = new URL("file:///" + file);
                    bodyBuilder.header("Content-Type", url.openConnection().getContentType());
                    bodyBuilder.header("Content-Disposition","inline;filename*=UTF-8''" + encodeFileName);
                } else {
                    // 直接下载
                    bodyBuilder.header("Content-Disposition","attachment;filename*=UTF-8''"+encodeFileName);
                }
            }

            // 下载成功返回二进制流
            return bodyBuilder.body(FileUtils.readFileToByteArray(file));
        } catch (Exception ex) {

        } finally {

        }

        stopWatch.stop();
        LOGGER_LOGBACK.info("---------------------------Download File, Take " + stopWatch.getTotalTimeSeconds() + "s---------------------------");
        return (ResponseEntity<byte[]>) ResponseEntity.badRequest();
    }

}
