package cxt.cn.goods.service;

import cxt.cn.goods.utils.ThreadUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * @Author:陈啸掭
 * @Description:
 * @Date:Create in 2020/1/3 21:20
 * @Modeified By:
 */
@Service
public class GoodsHtmlService {
    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private GoodsService goodsService;

    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsHtmlService.class);
    public void createHtml(Long spuId){
        Context context = new Context();
        Map<String, Object> map = goodsService.loadData(spuId);
        context.setVariables(map);
        PrintWriter printWriter = null;
        try {
           printWriter = new PrintWriter(new File("D:\\studysofteware\\nginx-1.14.0\\html\\item\\"+spuId+".html"));
        } catch (FileNotFoundException e) {
            LOGGER.error("页面静态化出错：{}，"+ e, spuId);
        }finally {
            if (printWriter!=null){
                printWriter.close();
            }
        }
        templateEngine.process("item",context,printWriter);
    }
    /**
     * 新建线程处理页面静态化
     * @param spuId
     */
    public void asyncExcute(Long spuId) {
        ThreadUtils.execute(()->createHtml(spuId));
        /*ThreadUtils.execute(new Runnable() {
            @Override
            public void run() {
                createHtml(spuId);
            }
        });*/
    }

    public void deleteHTML(Long spuId) {
        File file = new File("D:\\studysofteware\\nginx-1.14.0\\html\\item\\" + spuId + ".html");
        file.deleteOnExit();
    }
}
