package leyou.com.gateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @Author:陈啸掭
 * @Description:
 * @Date:Create in 2020/2/17 13:33
 * @Modeified By:
 */
@ConfigurationProperties(prefix = "leyou.filter")
public class FileterProperties {
    private List<String> allowPaths;

    public List<String> getAllowPaths() {
        return allowPaths;
    }

    public void setAllowPaths(List<String> allowPaths) {
        this.allowPaths = allowPaths;
    }
}
