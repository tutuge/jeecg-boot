package org.jeecg.modules.cable.tools;

import lombok.extern.slf4j.Slf4j;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class StringTools {

    //去掉末属的数字
    public static String getNumber(String str) {
        String number = "";
        String pattern = "\\d+";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(str);
        if (matcher.find()) {
            number = matcher.group();
            //log.info("匹配到的数字：" + number);
        }
        return number;
    }

    //getRemoveChinese
    public static String getRemoveChinese(String str) {
        String REGEX_CHINESE = "[一-龥]";// 中文正则
        return str.replaceAll(REGEX_CHINESE, "");
    }

    //isContainString
    public static Boolean isContainString(String str, String[] arr) {
        Boolean flag = false;
        if (arr.length == 0) {
            flag = true;
        } else {
            for (String arrStr : arr) {
                if (arrStr.equals(str)) {
                    return true;
                }
            }
        }
        return flag;
    }
}
