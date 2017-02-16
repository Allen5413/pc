package com.allen.service.basic.menu;

import com.allen.dao.PageInfo;
import com.allen.util.EntityTree;

import java.util.List;
import java.util.Map;

/**
 * Created by Allen on 2016/12/20.
 */
public interface FindMenuTreeService {
    public List<EntityTree> findMenuToTree();
}
