package com.xuxiang.common.util;

import android.content.Context;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    /**
     * 字符串拼接转列表
     * @param string
     * @return
     */
    public static ArrayList<String> stringsToList(String string) {
        if (TextUtils.isEmpty(string)) {
            return null;
        }

        String[] split = string.split(",");
        ArrayList<String> resultList = new ArrayList<>();
        for (String s : split) {
            resultList.add(s);
        }
        return resultList;
    }

    /**
     * 列表转字符串拼接
     *
     * @param stringList
     * @return
     */
    public static String listToString(List<String> stringList) {
        if (stringList == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < stringList.size(); i++) {
            result.append(stringList.get(i));
            if (i != stringList.size() - 1)
                result.append(",");
        }
        return result.toString();
    }

    public static boolean isDegree(String input) {
        if (TextUtils.isEmpty(input)) return false;
        String pattern = "[0-9]+([.]{1}[0-9]+){0,1}";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(input);
        return m.matches();//如果不是0到360的度数，则返回false，是号码则返回true
    }

    /**
     * 使用正则表达式提取中括号中的内容
     *
     * @param msg
     * @return
     */
    public static List<String> extractMessageByRegular(String msg) {
        List<String> list = new ArrayList<String>();
        Pattern p = Pattern.compile("(\\「[^\\」]*\\」)");
        Matcher m = p.matcher(msg);
        while (m.find()) {
            list.add(m.group().substring(1, m.group().length() - 1));
        }
        return list;
    }

    /**
     * 使括号内的字体变色
     *
     * @param msg
     * @return
     */
    public static String htmlStrByregular(String msg) {
        if(TextUtils.isEmpty(msg)) return "";
        List<String> list = extractMessageByRegular(msg);
        if(list == null || list.size() < 1)
            return msg;
        String newMsg = "";
        for (String s : list) {
            if (TextUtils.isEmpty(newMsg))
                newMsg = msg.replace(s, "<font color = '#FF355A'>" + s + "</font>");
            else
                newMsg = newMsg.replace(s, "<font color = '#FF355A'>" + s + "</font>");
        }
        return newMsg;
    }


    /**
     * 获取当前所在的八卦方位
     *
     * @param degressStr
     * @return
     */
    public static String getCompassPosition(String degressStr) {
        String str = "";
        float degress = Float.parseFloat(degressStr);
        if (degress > 352.5 && degress <= 360 || degress >= 0 && degress <= 7.5) {
            str = "子";
        } else if (degress > 7.5 && degress <= 22.5) {
            str = "癸";
        } else if (degress > 22.5 && degress <= 37.5) {
            str = "丑";
        } else if (degress > 37.5 && degress <= 52.5) {
            str = "艮";
        } else if (degress > 52.5 && degress <= 67.5) {
            str = "寅";
        } else if (degress > 67.5 && degress <= 82.5) {
            str = "甲";
        } else if (degress > 82.5 && degress <= 97.5) {
            str = "卯";
        } else if (degress > 97.5 && degress <= 112.5) {
            str = "乙";
        } else if (degress > 112.5 && degress <= 127.5) {
            str = "辰";
        } else if (degress > 127.5 && degress <= 142.5) {
            str = "巽";
        } else if (degress > 142.5 && degress <= 157.5) {
            str = "巳";
        } else if (degress > 157.5 && degress <= 172.5) {
            str = "丙";
        } else if (degress > 172.5 && degress <= 187.5) {
            str = "午";
        } else if (degress > 187.5 && degress <= 202.5) {
            str = "丁";
        } else if (degress > 202.5 && degress <= 217.5) {
            str = "未";
        } else if (degress > 217.5 && degress <= 232.5) {
            str = "坤";
        } else if (degress > 232.5 && degress <= 247.5) {
            str = "申";
        } else if (degress > 247.5 && degress <= 262.5) {
            str = "庚";
        } else if (degress > 262.5 && degress <= 277.5) {
            str = "酉";
        } else if (degress > 277.5 && degress <= 292.5) {
            str = "辛";
        } else if (degress > 292.5 && degress <= 307.5) {
            str = "戌";
        } else if (degress > 307.5 && degress <= 322.5) {
            str = "乾";
        } else if (degress > 322.5 && degress <= 337.5) {
            str = "亥";
        } else if (degress > 337.5 && degress <= 352.5) {
            str = "壬";
        }
        return str;
    }

    /**
     * 获取地理方位
     *
     * @param degressStr
     * @return
     */
    public static String getCompassLocation(String degressStr) {
        String str = "";
        float degress = Float.parseFloat(degressStr);
        if (degress > 337.5 && degress <= 360 || degress >= 0 && degress <= 22.5) {
            str = "正北";
        } else if (degress > 22.5 && degress <= 67.5) {
            str = "东北";
        } else if (degress > 67.5 && degress <= 112.5) {
            str = "正东";
        } else if (degress > 112.5 && degress <= 157.5) {
            str = "东南";
        } else if (degress > 157.5 && degress <= 202.5) {
            str = "正南";
        } else if (degress > 202.5 && degress <= 247.5) {
            str = "西南";
        } else if (degress > 247.5 && degress <= 292.5) {
            str = "正西";
        } else if (degress > 292.5 && degress <= 337.5) {
            str = "西北";
        }
        return str;
    }
}
