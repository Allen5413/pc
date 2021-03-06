package com.allen.entity.basic;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 生产计划主键表
 * Created by Allen on 2017/3/4.
 */
@Entity
@Table(name = "z_pln_planorder")
public class ZPlanOrder {
    @Id
    @GeneratedValue
    private long Id;
    private long Column1 ;

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public long getColumn1() {
        return Column1;
    }

    public void setColumn1(long column1) {
        Column1 = column1;
    }
}
