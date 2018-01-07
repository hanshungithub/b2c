package com.taotao.service.impl;

import com.taotao.common.utils.FtpUtil;
import com.taotao.common.utils.IDUtils;
import com.taotao.service.PictureService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class PictureServiceImpl implements PictureService {

    @Value("${FTP_ADDRESS}")
    private String FTP_ADDRESS;

    @Value("${FTP_PORT}")
    private int FTP_PORT;

    @Value("${FTP_USERNAME}")
    private String FTP_USERNAME;

    @Value("${FTP_PASSWORD}")
    private String FTP_PASSWORD;

    @Value("${FTP_BASE_PATH}")
    private String FTP_BASE_PATH;

    @Value("${IMAGE_BASE_URL}")
    private String IMAGE_BASE_URL;

    @Override
    public Map uploadPicture(MultipartFile uploadFile) {
        String oldName = uploadFile.getOriginalFilename();
        String newName = IDUtils.genImageName();
        newName = newName + oldName.substring(oldName.indexOf("."));

        String filePath = new DateTime().toString("/yyyy/MM/dd");

        Map resultMap = new HashMap();

        try {
            boolean result = FtpUtil.uploadFile(FTP_ADDRESS, FTP_PORT, FTP_USERNAME, FTP_PASSWORD, FTP_BASE_PATH,
                    filePath, newName, uploadFile.getInputStream());
            if (!result) {
                resultMap.put("error", 1);
                resultMap.put("message", "文件上传失败，请重新上传！");
                return resultMap;
            }
            resultMap.put("error", 0);
            resultMap.put("url", IMAGE_BASE_URL + filePath + "/" + newName);
            return resultMap;

        } catch (IOException e) {
            resultMap.put("error", 1);
            resultMap.put("message", "文件上传失败，请重新上传！");
            return resultMap;
        }
    }
}
