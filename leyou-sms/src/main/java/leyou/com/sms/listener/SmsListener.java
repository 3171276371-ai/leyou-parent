package leyou.com.sms.listener;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import leyou.com.sms.config.SmsConfig;
import leyou.com.sms.utils.SmsUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author:陈啸掭
 * @Description:
 * @Date:Create in 2020/2/11 15:07
 * @Modeified By:
 */
@Component
@EnableConfigurationProperties(SmsConfig.class)
public class SmsListener {

    @Autowired
    private SmsUtils smsUtils;

    @Autowired
    private SmsConfig smsConfig;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "LEYOU.SMS.QUEUE",durable = "true"),
            exchange = @Exchange(value = "LEYOU.SMS.EXCHANGE", type = ExchangeTypes.TOPIC,ignoreDeclarationExceptions = "true"),
            key = {"sms.verify.code"}
    ))
    public void listen(Map<String, String> msg) throws ClientException {
        if (msg == null || msg.size() <= 0) {
            // 放弃处理
            return;
        }
        String phone = msg.get("phone");
        String code = msg.get("code");

        if (StringUtils.isBlank(phone) || StringUtils.isBlank(code)) {
            // 放弃处理
            return;
        }
        // 发送消息
        SendSmsResponse resp = this.smsUtils.sendSms(phone, code,
                smsConfig.getSignName(),
                smsConfig.getVerifyCodeTemplate());
    }


}
