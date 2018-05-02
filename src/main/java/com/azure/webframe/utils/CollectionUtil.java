package com.azure.webframe.utils;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.collections4.*;

/**
 * Created by 28029 on 2018/4/25.
 */
public class CollectionUtil {
    public static boolean isEmpty(Collection<?> collection)
    {
        return CollectionUtils.isEmpty(collection);
    }

    public static boolean isNotEmpty(Collection<?> collection){
        return !isEmpty(collection);
    }

    public static boolean isEmpty(Map<?,?> map)
    {
        return MapUtils.isEmpty(map);
    }
    public static boolean isNotEmpty(Map<?,?> map)
    {
        return !MapUtils.isEmpty(map);
    }

}
