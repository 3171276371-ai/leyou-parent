package leyou.com.item.pojo;

import lombok.Data;

import javax.persistence.*;

/**
 * @Author:陈啸掭
 * @Description:
 * @CreateTime: 2019/12/9 13:05
 */
@Table(name = "tb_spec_param")
@Data
public class TbSpecParam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long cid;
    private Long groupId;
    private String name;
    @Column(name = "`numeric`")
    private Boolean numeric;
    private String unit;
    private Boolean generic;
    private Boolean searching;
    private String segments;
}
