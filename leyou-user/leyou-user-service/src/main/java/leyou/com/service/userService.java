package leyou.com.service;

/**
 * @Author:陈啸掭
 * @Description:
 * @Date:Create in 2020/2/11 13:30
 * @Modeified By:
 */

public interface userService {


    /**
     * 校验用户准确性
     * @param data
     * @param type
     * @return
     */
    Boolean checkData(String data, Integer type);
}
