package org.jeecg.modules.cable.tools;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class SerialNumber {
    // 获取现在时间
    public static String getStringDate() {
        Date currentTime = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        return formatter.format(currentTime);
    }

    // 由年月日时分秒+3位随机数
    public static String getTradeNumber() {
        String t = getStringDate();
        double x = (int) (Math.random() * 900) + 100;
        return t + x;
    }
}
