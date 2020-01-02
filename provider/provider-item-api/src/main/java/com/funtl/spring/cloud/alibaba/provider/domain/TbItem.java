package com.funtl.spring.cloud.alibaba.provider.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table(name = "tb_item")
public class TbItem implements Serializable {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "num")
    private Integer num;

    private static final long serialVersionUID = 1L;
}