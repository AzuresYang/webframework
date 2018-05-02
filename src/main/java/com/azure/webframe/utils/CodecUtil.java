package com.azure.webframe.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by 28029 on 2018/4/27.
 */
public class CodecUtil {
    private static Logger LOGGER = LoggerFactory.getLogger(CodecUtil.class);

    public static String encodeURL(String src)
    {
        String target;
        try
        {
            target = URLEncoder.encode(src,"UTF-8");
        }catch (Exception e)
        {
            LOGGER.error("encode url failure",e);
            throw new RuntimeException(e);
        }
        return target;
    }

    public static String decodeURL(String src)
    {
        String target;
        try
        {
            target = URLDecoder.decode(src,"UTF-8");
        }catch (Exception e)
        {
            LOGGER.error("decode url failure",e);
            throw new RuntimeException(e);
        }
        return target;

    }
}
