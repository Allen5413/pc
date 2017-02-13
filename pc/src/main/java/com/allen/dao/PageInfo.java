package com.allen.dao;

import java.util.List;

/**
 * Created by Allen on 2016/12/20.
 */
public class PageInfo<T> {
    private List<T> pageResults;
    private int countOfCurrentPage = 10;
    private int currentPage;
    private long totalCount;
    private long totalPage;

    public PageInfo() {
        this.currentPage = 1;
        this.countOfCurrentPage = 10;
        this.totalCount = 0L;
        this.totalPage = 0L;
    }

    public PageInfo(int countOfCurrentPage, int currentPage) {
        this.countOfCurrentPage = countOfCurrentPage;
        this.currentPage = currentPage;
    }

    public boolean hasNext() {
        return (long)this.currentPage != this.totalPage;
    }

    public boolean hasPrevious() {
        return this.currentPage != 1;
    }

    public int getCountOfCurrentPage() {
        return this.countOfCurrentPage;
    }

    public void setCountOfCurrentPage(int countOfCurrentPage) {
        this.countOfCurrentPage = countOfCurrentPage;
    }

    public int getCurrentPage() {
        return this.currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public List<T> getPageResults() {
        return this.pageResults;
    }

    public void setPageResults(List<T> pageResults) {
        this.pageResults = pageResults;
    }

    public long getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public long getTotalPage() {
        return (this.totalCount + (long)this.countOfCurrentPage - 1L) / (long)this.countOfCurrentPage;
    }

    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }

    public void calPageResults(List<T> results) {
        if(results == null) {
            throw new IllegalArgumentException("null argument!");
        } else {
            int iDatasSize = results.size();
            int iOffset;
            if(iDatasSize >= this.countOfCurrentPage * this.currentPage) {
                iOffset = this.countOfCurrentPage;
            } else {
                iOffset = iDatasSize - this.countOfCurrentPage * (this.currentPage - 1);
            }

            int iStart = this.countOfCurrentPage * (this.currentPage - 1);
            this.pageResults = results.subList(iStart, iStart + iOffset);
        }
    }
}
