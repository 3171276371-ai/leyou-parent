package leyou.com.item.pojo;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Table(name = "tb_sku")
public class TbSku {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long spuId;
  private String title;
  private String images;
  private Long price;
  private String indexes;
  private String ownSpec;
  private Boolean enable;
  private Date createTime;
  private Date lastUpdateTime;
  @Transient
  private Integer stock;// 库存
}
