package com.allen.dao.basic.menu;

import com.allen.entity.basic.Menu;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Allen on 2016/12/15 0015.
 */
public interface MenuDao extends CrudRepository<Menu, Long> {
}
