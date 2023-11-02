package org.jeecg.modules.cable.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class PhoneUtils {
    //大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数
    public static Boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {
        String regExp = "^((13[0-9])|(14[5-9])|(15[0-3,5-9])|(16[5,6,7])|(17[0-8])" +
                "|(18[0-9])|(19[0-3,5-9]))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    //判断座机号是否正确
    public static Boolean isTelephone(String phone) {
        String regex = "^(\\(\\d{3,4}\\)|\\d{3,4}-)?\\d{7,9}$";
        return phone.matches(regex);
    }
}
