package leyou.com.item.pojo;

import lombok.Data;

import javax.annotation.Generated;
import javax.persistence.*;
import java.util.List;

/**
 * @Author:陈啸掭
 * @Description:
 * @CreateTime: 2019/12/9 13:02
 */
@Data
@Table(name = "tb_spec_group")
public class TbSpecGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long cid;
    private String name;
    @Transient
    private List<TbSpecParam> tbSpecParams;

}
