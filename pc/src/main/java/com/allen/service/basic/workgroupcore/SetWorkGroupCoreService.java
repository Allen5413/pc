package com.allen.service.basic.workgroupcore;

/**
 * Created by Allen on 2017/2/15 0015.
 */
public interface SetWorkGroupCoreService {

    /**
     * 通过工作中心选工作组来设置关联
     * @param wcId
     * @param wgIds
     * @param loginName
     * @throws Exception
     */
    public void setForCore(long wcId, Long[] wgIds, String loginName)throws Exception;

    /**
     * 通过工作组选中心来设置关联
     * @param wgId
     * @param wcIds
     * @param loginName
     * @throws Exception
     */
    public void setForGroup(long wgId, Long[] wcIds, String loginName)throws Exception;
}
