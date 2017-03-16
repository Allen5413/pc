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
    public final static int ISWORK_NOT = 0;
    public final static int ISWORK_YES = 1;

    @Id
    @GeneratedValue
    private long FENTRYID;
    private Date FDAY;
    private int FISWORKTIME;

    public long getFENTRYID() {
        return FENTRYID;
    }

    public void setFENTRYID(long FENTRYID) {
        this.FENTRYID = FENTRYID;
    }

    public Date getFDAY() {
        return FDAY;
    }

    public void setFDAY(Date FDAY) {
        this.FDAY = FDAY;
    }

    public int getFISWORKTIME() {
        return FISWORKTIME;
    }

    public void setFISWORKTIME(int FISWORKTIME) {
        this.FISWORKTIME = FISWORKTIME;
    }
}
