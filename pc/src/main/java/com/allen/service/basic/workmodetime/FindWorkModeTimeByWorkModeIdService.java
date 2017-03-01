package com.allen.service.basic.workmodetime;
import com.allen.entity.basic.WorkTime;

import java.util.List;

/**
 * Created by Allen on 2016/12/20.
 */
public interface FindWorkModeTimeByWorkModeIdService {
    public List<WorkTime> findWorkModeTimeByWorkModeId(long workModeId);
}
