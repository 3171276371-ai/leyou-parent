package leyou.com.item.pojo;

import lombok.Data;
import lombok.Generated;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author:陈啸掭
 * @Description:
 * @CreateTime: 2019/12/5 17:15
 */
@Data
@Table(name = "tb_brand")
public class TbBrand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String image;
    private Character letter;

}
