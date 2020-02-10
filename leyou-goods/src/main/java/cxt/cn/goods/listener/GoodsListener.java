package cxt.cn.goods.listener;

import cxt.cn.goods.service.GoodsHtmlService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author:陈啸掭
 * @Description:
 * @Date:Create in 2020/2/10 14:35
 * @Modeified By:
 */
@Component
public class GoodsListener {
    @Autowired
    private GoodsHtmlService goodsHtmlService;
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "LEYOU.CREATE.WEB.QUEUE", durable = "true"),
            exchange = @Exchange(
                    value = "LEYOU.ITEM.EXCHANGE",
                    ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.TOPIC),
            key = {"item.insert", "item.update"}))
    public void saveGoodsMessage(Long spuId){
        if (spuId==null){
            return;
        }
        goodsHtmlService.createHtml(spuId);
    }
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "LEYOU.DELETE.WEB.QUEUE", durable = "true"),
            exchange = @Exchange(
                    value = "LEYOU.ITEM.EXCHANGE",
                    ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.TOPIC),
            key = {"item.delete"}))
    public void deleteGoodsMessage(Long spuId){
        if (spuId==null){
            return;
        }
        goodsHtmlService.deleteHTML(spuId);
    }
}
