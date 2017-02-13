package com.allen.service.basic.menu;

import com.allen.entity.basic.Menu;

/**
 * Created by Allen on 2016/12/15 0015.
 */
public interface FindMenuByIdService {
    public Menu find(long id)throws Exception;
}
