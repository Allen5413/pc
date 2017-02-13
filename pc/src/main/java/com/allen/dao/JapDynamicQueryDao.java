package com.allen.dao;

import com.allen.util.GenericsUtil;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Allen on 2016/12/20.
 */
public abstract class JapDynamicQueryDao<T> {
    @PersistenceContext
    protected EntityManager entityManager;
    protected Class<T> entityClass;

    public JapDynamicQueryDao() {
    }

    protected PageInfo<T> pagedQueryByJpql(PageInfo<T> pageInfo, String ql, Object... args) {
        long totalCount = this.queryCount(false, ql, args);
        pageInfo.setTotalCount(totalCount);
        Query query = this.entityManager.createQuery(ql);
        if(args != null && args.length != 0) {
            for(int i = 0; i < args.length; ++i) {
                query.setParameter(i, args[i]);
            }
        }

        query.setMaxResults(pageInfo.getCountOfCurrentPage());
        query.setFirstResult(pageInfo.getCountOfCurrentPage() * (pageInfo.getCurrentPage() - 1));
        pageInfo.setPageResults(query.getResultList());
        return pageInfo;
    }

    protected PageInfo<T> pagedQueryByNativeSql(PageInfo<T> pageInfo, String sql, Object... args) {
        return this.queryByNativeSql(this.getEntityClass(), pageInfo, sql, args);
    }

    protected PageInfo<T> queryByNativeSql(Class<T> resultClass, PageInfo<T> pageInfo, String sql, Object... args) {
        long totalCount = this.queryCount(true, sql, args);
        pageInfo.setTotalCount(totalCount);
        Query query = this.entityManager.createNativeQuery(sql, resultClass);
        if(args != null && args.length != 0) {
            for(int i = 0; i < args.length; ++i) {
                query.setParameter(i, args[i]);
            }
        }

        query.setMaxResults(pageInfo.getCountOfCurrentPage());
        query.setFirstResult(pageInfo.getCountOfCurrentPage() * (pageInfo.getCurrentPage() - 1));
        pageInfo.setPageResults(query.getResultList());
        return pageInfo;
    }

    protected List<T> queryByJpql(String jpql, Object... args) {
        Query query = this.entityManager.createQuery(jpql);
        if(args != null && args.length != 0) {
            for(int i = 0; i < args.length; ++i) {
                query.setParameter(i, args[i]);
            }
        }

        return query.getResultList();
    }

    protected List<T> queryByNativeSql(Class<T> resultClass, String sql, Object... args) {
        Query query = this.entityManager.createNativeQuery(sql, resultClass);
        if(args != null && args.length != 0) {
            for(int i = 0; i < args.length; ++i) {
                query.setParameter(i, args[i]);
            }
        }

        return query.getResultList();
    }

    private Class<T> getEntityClass() {
        this.entityClass = GenericsUtil.getSuperClassGenricType(this.getClass());
        return this.entityClass;
    }

    private long queryCount(boolean nativeSql, String ql, Object... args) {
        String countQueryString = "select count (*) " + removeSelect(removeOrders(ql));
        Query query = null;
        if(nativeSql) {
            query = this.entityManager.createNativeQuery(countQueryString, Long.class);
        } else {
            query = this.entityManager.createQuery(countQueryString);
        }

        if(args != null && args.length != 0) {
            for(int i = 0; i < args.length; ++i) {
                query.setParameter(i, args[i]);
            }
        }

        return ((Long)query.getSingleResult()).longValue();
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

    public EntityManager getEntityManager() {
        return this.entityManager;
    }
}
