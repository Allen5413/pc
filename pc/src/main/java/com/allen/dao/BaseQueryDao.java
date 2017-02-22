package com.allen.dao;

import com.allen.util.StringUtil;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.util.Assert;

import javax.persistence.Query;
import java.math.BigInteger;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Allen on 2016/12/20.
 */
public class BaseQueryDao extends JapDynamicQueryDao {

    /**
     * 功能：根据原声sql 分页查询 返回object[]
     * @param pageInfo 分页信息
     * @param fields 查询字段名称
     * @param tableNames 数据库表名称
     * @param paramsMap 参数对象
     * @param sortMap 排序方式
     * @return
     * @throws Exception
     */
    public PageInfo findPageByNativeSql(PageInfo pageInfo, String fields, String[] tableNames, Map<String, Object> paramsMap, Map<String, Boolean> sortMap)throws Exception{
        List<Object> paramsList = new ArrayList<Object>();
        String sql = new String("from ");
        this.queryTableWhereConfigure(paramsList, sql, null, tableNames, paramsMap, sortMap);
        this.pageSqlQueryByNativeSql(pageInfo, sql, fields, paramsList.toArray());
        return pageInfo;
    }

    /**
     * 功能：根据原声sql 分页查询 返回map 带有默认条件的，如：多个表关联
     * @param pageInfo 分页信息
     * @param fields 查询字段名称
     * @param defaultWhere 关联条件
     * @param tableNames 数据库表名称
     * @param paramsMap 参数对象
     * @param sortMap 排序方式
     * @return
     * @throws Exception
     */
    public PageInfo findPageByNativeSqlToMap(PageInfo pageInfo, String fields, String defaultWhere, String[] tableNames,
                                             Map<String, Object> paramsMap, Map<String, Boolean> sortMap)throws Exception{
        List<Object> paramsList = new ArrayList<Object>();
        String sql = new String("from ");
        this.queryTableWhereConfigure(paramsList, sql, defaultWhere, tableNames, paramsMap, sortMap);
        this.pageSqlQueryByNativeSqlToMap(pageInfo, sql, fields, paramsList.toArray());
        return pageInfo;
    }

    /**
     * Hql 分页查询 没有设置查询字段 适用于单表查询
     * @param pageInfo
     * @param tableNames
     * @param paramsMap
     * @param sortMap
     * @return
     * @throws Exception
     */
    public PageInfo findPageByJpal(PageInfo pageInfo, String[] tableNames, Map<String, Object> paramsMap, Map<String, Boolean> sortMap)throws Exception{
        List paramsList = new ArrayList();
        String sql = new String("from ");
        this.queryTableWhereConfigure(paramsList, sql, null, tableNames, paramsMap, sortMap);
        this.pagedQueryByJpql(pageInfo, sql.toString(), paramsList.toArray());
        return pageInfo;
    }

    /**
     * Hql 分页查询 没有设置查询字段  带有默认条件的
     * @param pageInfo
     * @param tableNames
     * @param defaultWhere
     * @param paramsMap
     * @param sortMap
     * @return
     * @throws Exception
     */
    public PageInfo findPageByJpal(PageInfo pageInfo, String[] tableNames, String defaultWhere, Map<String, Object> paramsMap, Map<String, Boolean> sortMap)throws Exception{
        List paramsList = new ArrayList();
        String sql = new String("from ");
        this.queryTableWhereConfigure(paramsList, sql, defaultWhere, tableNames, paramsMap, sortMap);
        this.pagedQueryByJpql(pageInfo, sql.toString(), paramsList.toArray());
        return pageInfo;
    }

    /**
     * Hql 分页查询 可以设置查询字段
     * @param pageInfo
     * @param fields
     * @param tableNames
     * @param paramsMap
     * @param sortMap
     * @return
     * @throws Exception
     */
    public PageInfo findPageByJpal(PageInfo pageInfo, String fields, String[] tableNames, Map<String, Object> paramsMap, Map<String, Boolean> sortMap)throws Exception{
        List paramsList = new ArrayList();
        String sql = new String("select "+fields+" from ");
        this.queryTableWhereConfigure(paramsList, sql, null, tableNames, paramsMap, sortMap);
        this.pagedQueryByJpql(pageInfo, sql.toString(), paramsList.toArray());
        return pageInfo;
    }

    /**
     * Hql 分页查询 可以设置查询字段 带有默认条件的，如：多个表关联
     * @param pageInfo
     * @param fields
     * @param tableNames
     * @param defaultWhere
     * @param paramsMap
     * @param sortMap
     * @return
     * @throws Exception
     */
    public PageInfo findPageByJpal(PageInfo pageInfo, String fields, String[] tableNames, String defaultWhere, Map<String, Object> paramsMap, Map<String, Boolean> sortMap)throws Exception{
        List paramsList = new ArrayList();
        String sql = new String("select "+fields+" from ");
        this.queryTableWhereConfigure(paramsList, sql, defaultWhere, tableNames, paramsMap, sortMap);
        this.pagedQueryByJpql(pageInfo, sql.toString(), paramsList.toArray());
        return pageInfo;
    }

    /**
     * Hql 分页查询 可以设置查询字段 返回指定class对象
     * @param tableNames
     * @param fields
     * @param paramsMap
     * @param sortMap
     * @param returnClass
     * @return
     */
    public List findListByHql(String[] tableNames, String fields, Map<String, Object> paramsMap, Map<String, Boolean> sortMap, Class returnClass){
        List paramsList = new ArrayList();
        String sql = new String("select "+fields+" from ");
        this.queryTableWhereConfigure(paramsList, sql, null, tableNames, paramsMap, sortMap);
        return this.sqlQueryByHql(sql, returnClass, paramsList.toArray());
    }

    /**
     * Hql 分页查询 可以设置查询字段 带有默认条件的，如：多个表关联  返回指定class对象
     * @param tableNames
     * @param fields
     * @param defaultWhere
     * @param paramsMap
     * @param sortMap
     * @param returnClass
     * @return
     */
    public List findListByHql(String[] tableNames, String fields, String defaultWhere, Map<String, Object> paramsMap, Map<String, Boolean> sortMap, Class returnClass){
        List paramsList = new ArrayList();
        String sql = new String("select "+fields+" from ");
        this.queryTableWhereConfigure(paramsList, sql, defaultWhere, tableNames, paramsMap, sortMap);
        return this.sqlQueryByHql(sql, returnClass, paramsList.toArray());
    }

    /**
     * 查询某一个对象，返回指定class对象
     * @param tableNames
     * @param fields
     * @param paramsMap
     * @param sortMap
     * @param returnClass
     * @return
     */
    public Object findByHql(String[] tableNames, String fields, Map<String, Object> paramsMap,Map<String, Boolean> sortMap, Class returnClass){
        List list =  this.findListByHql(tableNames, fields, paramsMap, sortMap, returnClass);
        if(null != list && 0 < list.size()){
            return list.get(0);
        }else{
            return  null;
        }
    }

    /***********************************下面是为了上面提供dao调用的方法的一些公用计算，组装方法；批量新增，批量修改方法******************************************************/

    /**
     * 配置各种查询语句组装，条件参数组装
     * @param paramsList
     * @param sql
     * @param tableNames
     * @param paramsMap
     * @param sortMap
     */
    private void queryTableWhereConfigure(List<Object> paramsList, String sql, String defaultWhere, String[] tableNames,
                                          Map<String, Object> paramsMap, Map<String, Boolean> sortMap){
        for(int i=0; i<tableNames.length; i++){
            sql += tableNames[i];
            if(i == tableNames.length - 1){
                sql += " ";
            }else{
                sql += ", ";
            }
        }
        sql += StringUtil.isEmpty(defaultWhere) ? "where 1=1 " : "where "+defaultWhere+" ";
        sql = getParamListVal(sql,paramsMap,paramsList);
        if(null != sortMap) {
            sql += "order by ";
            int i = 0;
            for (Iterator it = sortMap.keySet().iterator(); it.hasNext(); ) {
                if(0 < i){
                    sql += ",";
                }
                String key = it.next().toString();
                sql += key + " " + (sortMap.get(key) ? "asc" : "desc");
                i++;
            }
        }
    }

    /**
     * 组装查询条件参数值
     * @param sql
     * @param paramsMap
     * @param paramsList
     * @return
     */
    private String getParamListVal(String sql,Map<String,Object> paramsMap,List<Object> paramsList){
        if(null != paramsMap && 0 < paramsMap.size()){
            for (Object key : paramsMap.keySet()) {
                Object value = paramsMap.get(key);
                if(null != value&&!StringUtil.isEmpty(value.toString())) {
                    if(value.getClass().isArray()){
                        if (StringUtil.isEmpty(((Object[])value)[0].toString())){
                            continue;
                        }
                        sql += "and " + key + " " + ((Object[])value)[1] + " ? ";
                        paramsList.add(((Object[])value)[0]);
                        continue;
                    }
                    sql += "and " + key + " = ? ";
                    paramsList.add(value);
                }
            }
        }
        return  sql;
    }

    /**
     * 执行sql原生方法，可以返回任何字段，不受entity的影响
     * @param sql
     * @param args
     * @return
     */
    protected List sqlQueryByNativeSql(String sql, Object... args) {
        Session session = super.entityManager.unwrap(Session.class);
        SQLQuery sqlQuery = session.createSQLQuery(sql);
        if(args != null && args.length != 0) {
            for(int i = 0; i < args.length; ++i) {
                sqlQuery.setParameter((i+1), args[i]);
            }
        }
        return sqlQuery.list();
    }

    protected List sqlQueryByHql(String hql, Class returnClass, Object... args) {
        Query query = this.entityManager.createQuery(hql, returnClass);
        if(args != null && args.length != 0) {
            for(int i = 0; i < args.length; ++i) {
                query.setParameter((i+1), args[i]);
            }
        }
        return query.getResultList();
    }

    /**
     * 原生sql分页查询，返回object[]
     * @param pageInfo
     * @param sql
     * @param field
     * @param args
     * @return
     */
    protected PageInfo pageSqlQueryByNativeSql(PageInfo pageInfo, String sql, String field, Object... args) {
        this.pageQueryConfigure(pageInfo, sql, field, 0, false, args);
        return pageInfo;
    }


    /**
     * 原生sql分页查询，返回map
     * @param pageInfo
     * @param sql
     * @param field
     * @param args
     * @return
     */
    protected PageInfo pageSqlQueryByNativeSqlToMap(PageInfo pageInfo, String sql, String field, Object... args) {
        this.pageQueryConfigure(pageInfo, sql, field, 1, false, args);
        return pageInfo;
    }

    /**
     * Hql分页查询
     * @param pageInfo
     * @param ql
     * @param args
     * @return
     */
    protected PageInfo pagedQueryByJpql(PageInfo pageInfo, String ql, Object... args) {
        this.pageQueryConfigure(pageInfo, ql, "", 0, true, args);
        return pageInfo;
    }

    /**
     * 分页查询装配
     * @param pageInfo
     * @param sql
     * @param field
     * @param flag   原生sql时用，0：返回object[]；1：返回map
     * @param isHql
     * @param args
     */
    private void pageQueryConfigure(PageInfo pageInfo, String sql, String field, int flag, boolean isHql, Object...args){
        long totalCount = this.queryCount(true, sql, args);
        pageInfo.setTotalCount(totalCount);
        if(isHql){
            Query query = this.entityManager.createQuery(sql);
            if(args != null && args.length != 0) {
                for(int i = 0; i < args.length; ++i) {
                    query.setParameter(i+1, args[i]);
                }
            }

            query.setMaxResults(pageInfo.getCountOfCurrentPage());
            query.setFirstResult(pageInfo.getCountOfCurrentPage() * (pageInfo.getCurrentPage() - 1));
            pageInfo.setPageResults(query.getResultList());
        }else{
            Session session = super.entityManager.unwrap(Session.class);
            SQLQuery sqlQuery = session.createSQLQuery("select "+field+" "+sql);
            if(args != null && args.length != 0) {
                for(int i = 0; i < args.length; ++i) {
                    sqlQuery.setParameter(i, args[i]);
                }
            }
            sqlQuery.setMaxResults(pageInfo.getCountOfCurrentPage());
            sqlQuery.setFirstResult(pageInfo.getCountOfCurrentPage() * (pageInfo.getCurrentPage() - 1));
            if(0 == flag){
                pageInfo.setPageResults(sqlQuery.list());
            }
            if(1 == flag){
                pageInfo.setPageResults(sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list());
            }
        }
    }

    private long queryCount(boolean nativeSql, String ql, Object... args) {
        Query query = null;
        if(nativeSql) {
            String countQueryString = "select count(1) from (select 1 " + ql + ") tempAlias";
            query = super.entityManager.createNativeQuery(countQueryString);
        } else {
            String countQueryString = "select count(*) " + removeSelect(removeOrders(ql));
            query = super.entityManager.createQuery(countQueryString);
        }

        if(args != null && args.length != 0) {
            for(int i = 0; i < args.length; ++i) {
                query.setParameter(i+1, args[i]);
            }
        }
        if(nativeSql) {
            BigInteger result = (BigInteger)(query.getSingleResult());
            return Long.valueOf(result.toString());
        } else {
            return ((Long)query.getSingleResult()).longValue();
        }
    }

    private static String removeSelect(String hql) {
        Assert.hasText(hql);
        int beginPos = hql.toLowerCase().indexOf("from");
        Assert.isTrue(beginPos != -1, " hql : " + hql + " must has a keyword \'from\'");
        return hql.substring(beginPos);
    }

    private static String removeOrders(String hql) {
        Assert.hasText(hql);
        Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", 2);
        Matcher m = p.matcher(hql);
        StringBuffer sb = new StringBuffer();

        while(m.find()) {
            m.appendReplacement(sb, "");
        }

        m.appendTail(sb);
        return sb.toString();
    }

    /**
     * 批量添加数据
     * @param list
     * @param num
     * @throws Exception
     */
    protected void batchInsert(List list, int num)throws Exception{
        try {
            if(null != list && 0 < list.size()) {
                int tmp = 1;
                for (int i = 0; i < list.size(); i++) {
                    Session session = super.entityManager.unwrap(Session.class);
                    session.persist(list.get(i));
                    if (tmp == num) {
                        session.flush();
                        session.clear();
                        tmp = 1;
                    }
                    tmp++;
                }
            }
        }catch(Exception e){
            throw e;
        }
    }

    /**
     * 批量修改数据
     * @param list
     * @param num
     * @throws Exception
     */
    protected void batchUpdate(List list, int num)throws Exception{
        try {
            if(null != list && 0 < list.size()) {
                int tmp = 1;
                for (int i = 0; i < list.size(); i++) {
                    Session session = super.entityManager.unwrap(Session.class);
                    session.merge(list.get(i));
                    if (tmp == num) {
                        session.flush();
                        session.clear();
                        tmp = 1;
                    }
                    tmp++;
                }
            }
        }catch(Exception e){
            throw e;
        }
    }
}
