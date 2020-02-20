package leyou.com.cart.config;

import leyou.com.cart.interceptor.CartInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author:陈啸掭
 * @Description:
 * @Date:Create in 2020/2/18 10:47
 * @Modeified By:
 */
@Configuration
public class CartConfig implements WebMvcConfigurer {
    @Autowired
    private CartInterceptor cartInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(cartInterceptor).addPathPatterns("/**");
    }
}
