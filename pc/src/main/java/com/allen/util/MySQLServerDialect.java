package com.allen.util;

import org.hibernate.dialect.SQLServer2008Dialect;
import org.hibernate.type.StandardBasicTypes;

import java.sql.Types;

/**
 * 包路径：com.allen.util
 * 功能说明：
 * 创建人： ly
 * 创建时间: 2017-03-31 0:49
 */
public class MySQLServerDialect extends SQLServer2008Dialect {
    public MySQLServerDialect()
    {
        super();
        registerHibernateType(1, "string");
        registerHibernateType(-9, "string");
        registerHibernateType(-16, "string");
        registerHibernateType(-15, "string");

        registerHibernateType(Types.CHAR, StandardBasicTypes.STRING.getName());
        registerHibernateType(Types.NVARCHAR, StandardBasicTypes.STRING.getName());
        registerHibernateType(Types.LONGNVARCHAR, StandardBasicTypes.STRING.getName());
        registerHibernateType(Types.NCHAR, StandardBasicTypes.STRING.getName());
    }
}
