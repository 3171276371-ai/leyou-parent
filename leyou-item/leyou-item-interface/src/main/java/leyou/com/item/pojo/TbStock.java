package leyou.com.item.pojo;

import lombok.Data;

import javax.persistence.Table;

@Data
@Table(name = "tb_stock")
public class TbStock {
  private Long skuId;
  private Integer seckillStock;
  private Integer seckillTotal;
  private Integer stock;
}
