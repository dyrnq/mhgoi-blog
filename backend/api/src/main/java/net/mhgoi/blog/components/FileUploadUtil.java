package net.mhgoi.blog.components;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.IoUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;

/**
 * @author 少年
 */
@Component
@Configuration
public class FileUploadUtil {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(FileUploadUtil.class);

    @Value("${application.upload-path:/data/pic}")
    private String uploadPath;


    public String write(String fileName, byte[] in) throws IORuntimeException, FileNotFoundException, UnsupportedEncodingException {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);//获取年份
        int month = cal.get(Calendar.MONTH) + 1;//获取月份
        int day = cal.get(Calendar.DATE);//获取日

        String folder = uploadPath + File.separator + year + File.separator + month + File.separator + day + File.separator;

        if (!FileUtil.exist(folder)) {
            File directories = new File(folder);
            directories.mkdirs();
        }
        //String fileName=file.getOriginalFilename();
        File outfile = new File(folder + fileName);
        IoUtil.write(new FileOutputStream(outfile), true, in);
        String resultPath = "/" + year + "/" + month + "/" + day + "/" + java.net.URLEncoder.encode(fileName, "UTF-8");
        return resultPath;

    }
}
