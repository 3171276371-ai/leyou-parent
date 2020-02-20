package leyou.com.gateway.filter;

import com.leyou.utils.JwtUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import leyou.com.gateway.config.FileterProperties;
import leyou.com.gateway.config.JwtProperties;
import leyou.com.utils.CookieUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author:陈啸掭
 * @Description:
 * @Date:Create in 2020/2/17 11:51
 * @Modeified By:
 */
@Component
@EnableConfigurationProperties(value = {JwtProperties.class,FileterProperties.class})
public class LoginFilter extends ZuulFilter {

    @Autowired
    private JwtProperties properties;

    @Autowired
    private FileterProperties fileterProperties;

    private static final Logger logger = LoggerFactory.getLogger(LoginFilter.class);
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 10;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        String requestURI = request.getRequestURI();
        List<String> allowPaths = fileterProperties.getAllowPaths();
        for (String allowPath : allowPaths) {
            if (requestURI.contains(allowPath)){
                return false;
            }
        }
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        String token = CookieUtils.getCookieValue(request, properties.getCookieName());
        try {
            JwtUtils.getInfoFromToken(token,properties.getPublicKey());
        } catch (Exception e) {
            currentContext.setSendZuulResponse(false);
            currentContext.setResponseStatusCode(HttpStatus.FORBIDDEN.value());
            logger.error("非法访问，未登录，地址：{}", request.getRemoteHost(), e );
        }
        return null;
    }
}
