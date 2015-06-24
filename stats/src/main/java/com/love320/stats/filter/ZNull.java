package com.love320.stats.filter;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by zhangdi on 15-6-24.
 *
 * 支持对空的过滤
 */
public class ZNull implements ZBase{

    private String name;

    public ZNull(String name){
        this.name = name;
    }

    @Override
    public boolean isValid(Object obj) {
        if(obj == null) return true;
        String str = (String)obj;
        return StringUtils.isNotBlank(str);
    }

    @Override
    public String name() {
        return name;
    }
}
