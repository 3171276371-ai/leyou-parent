package leyou.com.item.pojo;

import lombok.Data;

import java.util.List;

@Data
public class SpuBo extends TbSpu {

    String cname;// 商品分类名称
    String bname;// 品牌名称
    TSpuDetail spuDetail;// 商品详情
    List<TbSku> skus;// sku列表
}