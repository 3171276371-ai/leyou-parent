package leyou.com.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Author:陈啸掭
 * @Description:
 * @CreateTime: 2019/12/6 15:32
 */
public interface UploadService {

    String uploadImage(MultipartFile file);
}
