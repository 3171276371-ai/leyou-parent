package leyou.com.item.pojo;


import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
@Data
@Table(name = "tb_spu")
public class TbSpu {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String title;
  private String subTitle;
  private Long cid1;
  private Long cid2;
  private Long cid3;
  private Long brandId;
  private Boolean saleable;
  private Boolean valid;
  private Date createTime;
  private Date lastUpdateTime;



}
