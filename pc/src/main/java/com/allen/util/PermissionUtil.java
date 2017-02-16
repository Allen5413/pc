package com.allen.util;

import com.allen.entity.basic.Resource;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Allen on 2015/4/27.
 */
public class PermissionUtil {
    /**
     * 功能：判断是否有权限
     * @param resourceId 资源id
     * @param buttonCode 按钮编码
     * @return
     */
    public static boolean isPermission(long resourceId, String buttonCode, Map<String,List<Resource>> resource){
        if(resource==null||StringUtil.isEmpty(buttonCode)){
            return false;
        }
        Set<String> mapValues = resource.keySet();
       for (String key:mapValues){
           List<Resource> resources =  resource.get(key);
           if(resources==null||resources.size()==0){
                continue;
           }
           for (Resource pResource:resources){
               if(pResource.getParentId()==resourceId&&buttonCode.equals(pResource.getButtonCode())){
                   return true;
               }
           }
       }
        return false;
    }
}
