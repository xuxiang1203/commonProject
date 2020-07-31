package com.xuxiang.common.util;

import java.util.List;

public class NullObjectUtil {

    /**
     * 判断列表是否不为空
     *
     * @param list
     * @return
     */
    public static boolean isEmpty(List list) {
        if (list == null || list.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

}
