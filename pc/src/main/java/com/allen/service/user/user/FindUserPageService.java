package com.allen.service.user.user;

import com.allen.dao.PageInfo;

/**
 * Created by Allen on 2016/12/20.
 */
public interface FindUserPageService {
    public PageInfo find(PageInfo pageInfo, String name, Integer state)throws Exception;
}
