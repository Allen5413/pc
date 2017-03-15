package com.allen.entity.basic;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 工厂日历
 * Created by Allen on 2017/2/15 0015.
 */
@Entity
@Table(name = "t_eng_workcalfulldata")
public class FactoryDate{

    /**
     * 是否上班
     * 0：否
     * 1：是
     */
    private final static int ISWORK_NOT = 0;
    private final static int ISWORK_YES = 1;

    @Id
    @GeneratedValue
    private long FID;
    private String year;
    private String month;
    private String day;
    private int isWork;
}
