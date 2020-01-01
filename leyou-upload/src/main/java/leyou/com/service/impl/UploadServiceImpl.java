package leyou.com.service.impl;

import leyou.com.service.UploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * @Author:陈啸掭
 * @Description:
 * @CreateTime: 2019/12/6 15:32
 */
@Service
public class UploadServiceImpl implements UploadService {
    private static final List<String> LIST = Arrays.asList("application/x-img","image/jpeg","image/gif");
    private static final Logger LOGGER = LoggerFactory.getLogger(UploadServiceImpl.class);
    @Override
    public String uploadImage(MultipartFile file) {
        //校验文件名后缀
            String contentType = file.getContentType();
            if (!LIST.contains(contentType)) {
                LOGGER.info("文件类型不合法：{}", file.getOriginalFilename());
                return null;
            }
        try {
            //校验文件内容
            BufferedImage read = ImageIO.read(file.getInputStream());
            if (read==null){
                LOGGER.info("文件内容不合法：{}", file.getOriginalFilename());
                return null;
            }
            //保存文件
            file.transferTo(new File("D:\\JAVAProject2\\leyou-parent\\leyou-upload\\src\\main\\java\\leyou\\com\\image\\" + file.getOriginalFilename()));
            return "http://image.leyou.com/" + file.getOriginalFilename();
        }catch (Exception e){
            LOGGER.info("服务器内部错误：{}", file.getOriginalFilename());
            e.printStackTrace();
        }
        return null;
    }
}
