package org.jeecg.modules.cable.tools;

import com.google.gson.Gson;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Random;

public class CommonFunction {


    // getMd5Str 获取md5加密字符串
    public static String getMd5Str(String content) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(content.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("NoSuchAlgorithmException", e);
        }
        StringBuilder hex = new StringBuilder(hash.length * 2);
        byte b;
        Integer i;
        byte[] arrayOfByte1;
        for (i = (arrayOfByte1 = hash).length, b = 0; b < i; ) {
            byte b1 = arrayOfByte1[b];
            if ((b1 & 0xFF) < 16) {
                hex.append("0");
            }
            hex.append(Integer.toHexString(b1 & 0xFF));
            b++;
        }
        return hex.toString();
    }

    // getGson 获取gson
    public static Gson getGson() {
        return new Gson();
    }

    // getRandom 获取随机数
    public static Integer getRandom(Integer min, Integer max) {
        Integer number;
        Random random = new Random();
        number = random.nextInt(max) % (max - min + 1) + min;
        return number;
    }

    // getLockTime 获取销屏时间
    public static Integer getLockTime(String lock_name) {
        Integer lock_time;
        lock_time = switch (lock_name) {
            case "5分钟" -> 5 * 60;
            case "10分钟" -> 10 * 60;
            case "20分钟" -> 20 * 60;
            case "30分钟" -> 30 * 60;
            case "1小时" -> 60 * 60;
            case "2小时" -> 2 * 60 * 60;
            case "4小时" -> 4 * 60 * 60;
            case "8小时" -> 8 * 60 * 60;
            case "12小时" -> 12 * 60 * 60;
            case "16小时" -> 16 * 60 * 60;
            case "24小时" -> 24 * 60 * 60;
            default -> 0;
        };
        return lock_time;
    }

    // getLoginTime 获取登录时间
    public static Integer getLoginTime(String login_name) {
        Integer login_time;
        login_time = switch (login_name) {
            case "1小时" -> 60 * 60;
            case "2小时" -> 2 * 60 * 60;
            case "4小时" -> 4 * 60 * 60;
            case "8小时" -> 8 * 60 * 60;
            case "12小时" -> 12 * 60 * 60;
            case "16小时" -> 16 * 60 * 60;
            case "24小时" -> 24 * 60 * 60;
            default -> 0;
        };
        return login_time;
    }

    // getSaveTime 获取保存时间
    public static Integer getSaveTime(Integer type_id) {
        Integer saveTime = 0;
        if (type_id == 1) {
            saveTime = 2592000;
        } else if (type_id == 2) {
            saveTime = 86400;
        }
        return saveTime;
    }

    // 时间戳转时间 年-月-日 时:分:秒
    public static String stampToYearToMinute(Long time) {
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    // isNumeric 判断字符串是否为数字
    public static Boolean isNumeric(String str) {
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    // getNowData 获取本年年份本月月份
    public static Integer getNowData(Integer typeId) {
        Integer now_data = 0;
        Calendar cal = Calendar.getInstance();
        if (typeId == 1) {// 当前年份
            now_data = cal.get(Calendar.YEAR);
        } else if (typeId == 2) {// 当前月份
            now_data = cal.get(Calendar.MONTH);
        }
        return now_data;
    }

    // 日期转时间戳
    public static long dateToStamp(String s) {
        long res;
        // 设置时间模版
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = simpleDateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert date != null;
        res = date.getTime();
        return res;
    }

    // getMonthDay 获取本月第一天和最后一天
    public static String getMonthDay(String dateStr, Integer typeId) throws ParseException {
        String month_day = "";
        Calendar cale = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        if (typeId == 1) {// 第一天
            cale.setTime(formatter.parse(dateStr));
            cale.add(Calendar.MONTH, 0);
            cale.set(Calendar.DAY_OF_MONTH, 1);
            month_day = formatter.format(cale.getTime());
        } else if (typeId == 2) {// 最后一天
            cale.setTime(formatter.parse(dateStr));
            cale.add(Calendar.MONTH, 1);
            cale.set(Calendar.DAY_OF_MONTH, 0);
            month_day = formatter.format(cale.getTime());
        }
        return month_day;
    }

    // 获取用户名前缀，用于注册时用
    public static String getUsernamePrefix() {
        String usernamePrefix;
        usernamePrefix = "J";
        return usernamePrefix;
    }

    // getCommonMap 通用map
    public static Map<String, Object> getCommonMap(Map<String, Object> map, Integer status, String code, String msg) {
        map.put("status", status);
        map.put("code", code);
        map.put("msg", msg);
        return map;
    }

    // 文件储存路径
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static String pathContact(String sign, String cart, String base_path) {
        Date date = new Date();
        SimpleDateFormat format_year = new SimpleDateFormat("yyyy");
        SimpleDateFormat format_month = new SimpleDateFormat("MM");
        SimpleDateFormat format_date = new SimpleDateFormat("dd");
        SimpleDateFormat format_hour = new SimpleDateFormat("HH");
        String project = "lanchacha";
        String year = format_year.format(date);
        String month = format_month.format(date);
        String day = format_date.format(date);
        String time = format_hour.format(date);
        String project_path = base_path + project;
        File projectPath = new File(project_path);
        String sign_path = base_path + project + "/" + sign;
        File signPath = new File(sign_path);
        String cart_path = base_path + project + "/" + sign + "/" + cart;
        File cartPath = new File(cart_path);
        String year_path = base_path + project + "/" + sign + "/" + cart + "/" + year;
        File yearPath = new File(year_path);
        String month_path = base_path + project + "/" + sign + "/" + cart + "/" + year + "/" + month;
        File monthPath = new File(month_path);
        String day_path = base_path + project + "/" + sign + "/" + cart + "/" + year + "/" + month + "/" + day;
        File dayPath = new File(day_path);
        String time_path = base_path + project + "/" + sign + "/" + cart + "/" + year + "/" + month + "/" + day + "/" + time;
        File timePath = new File(time_path);
        String real_path = project + "/" + sign + "/" + cart + "/" + year + "/" + month + "/" + day + "/" + time;
        if (!projectPath.exists()) {
            projectPath.mkdir();
            signPath.mkdir();
            cartPath.mkdir();
            yearPath.mkdir();
            monthPath.mkdir();
            dayPath.mkdir();
            timePath.mkdir();
        } else if (!signPath.exists()) {
            signPath.mkdir();
            cartPath.mkdir();
            yearPath.mkdir();
            monthPath.mkdir();
            dayPath.mkdir();
            timePath.mkdir();
        } else if (!cartPath.exists()) {
            cartPath.mkdir();
            yearPath.mkdir();
            monthPath.mkdir();
            dayPath.mkdir();
            timePath.mkdir();
        } else if (!yearPath.exists()) {
            yearPath.mkdir();
            monthPath.mkdir();
            dayPath.mkdir();
            timePath.mkdir();
        } else if (!monthPath.exists()) {
            monthPath.mkdir();
            dayPath.mkdir();
            timePath.mkdir();
        }
        if (!dayPath.exists()) {
            dayPath.mkdir();
            timePath.mkdir();
        } else if (!timePath.exists()) {
            timePath.mkdir();
        }
        return real_path;
    }

    // 文件储存路径 ecdCollect
    public static String pathTxt(String base_path, String ecCompanyId, String cart) {
        Date date = new Date();
        SimpleDateFormat format_year = new SimpleDateFormat("yyyy");
        SimpleDateFormat format_month = new SimpleDateFormat("MM");
        SimpleDateFormat format_date = new SimpleDateFormat("dd");
        String year = format_year.format(date);
        String month = format_month.format(date);
        String day = format_date.format(date);
        String project = "lanchacha";
        String project_path = base_path + project;
        File projectPath = new File(project_path);
        String sign_path = base_path + project + "/txt";
        File signPath = new File(sign_path);
        String year_path = base_path + project + "/txt/" + year;
        File yearPath = new File(year_path);
        String month_path = base_path + project + "/txt/" + year + "/" + month;
        File monthPath = new File(month_path);
        String day_path = base_path + project + "/txt/" + year + "/" + month + "/" + day;
        File dayPath = new File(day_path);
        String company_path = base_path + project + "/txt/" + year + "/" + month + "/" + day + "/" + ecCompanyId;
        File companyPath = new File(company_path);
        String cart_path = base_path + project + "/txt/" + year + "/" + month + "/" + day + "/" + ecCompanyId + "/" + cart;
        File cartPath = new File(cart_path);
        String realPath = project + "/txt/" + year + "/" + month + "/" + day + "/" + ecCompanyId + "/" + cart;
        if (!projectPath.exists()) {
            projectPath.mkdir();
            signPath.mkdir();
            yearPath.mkdir();
            monthPath.mkdir();
            dayPath.mkdir();
            companyPath.mkdir();
            cartPath.mkdir();
        } else if (!signPath.exists()) {
            signPath.mkdir();
            yearPath.mkdir();
            monthPath.mkdir();
            dayPath.mkdir();
            companyPath.mkdir();
            cartPath.mkdir();
        } else if (!yearPath.exists()) {
            yearPath.mkdir();
            monthPath.mkdir();
            dayPath.mkdir();
            companyPath.mkdir();
            cartPath.mkdir();
        } else if (!monthPath.exists()) {
            monthPath.mkdir();
            dayPath.mkdir();
            companyPath.mkdir();
            cartPath.mkdir();
        } else if (!dayPath.exists()) {
            dayPath.mkdir();
            companyPath.mkdir();
            cartPath.mkdir();
        } else if (!companyPath.exists()) {
            companyPath.mkdir();
            cartPath.mkdir();
        } else if (!cartPath.exists()) {
            cartPath.mkdir();
        }
        return realPath;
    }

    // 文件储存路径 ecdArea
    public static String pathTxtArea(String basePath, String ecCompanyId, String cart, String ecqulId) {
        Date date = new Date();
        String project = "lanchacha";
        SimpleDateFormat format_year = new SimpleDateFormat("yyyy");
        SimpleDateFormat format_month = new SimpleDateFormat("MM");
        SimpleDateFormat format_date = new SimpleDateFormat("dd");
        String year = format_year.format(date);
        String month = format_month.format(date);
        String day = format_date.format(date);
        String project_path = basePath + project;
        File projectPath = new File(project_path);
        String sign_path = basePath + project + "/txt";
        File signPath = new File(sign_path);
        String year_path = basePath + project + "/txt/" + year;
        File yearPath = new File(year_path);
        String month_path = basePath + project + "/txt/" + year + "/" + month;
        File monthPath = new File(month_path);
        String day_path = basePath + project + "/txt/" + year + "/" + month + "/" + day;
        File dayPath = new File(day_path);
        String company_path = basePath + project + "/txt/" + year + "/" + month + "/" + day + "/" + ecCompanyId;
        File companyPath = new File(company_path);
        String cart_path = basePath + project + "/txt/" + year + "/" + month + "/" + day + "/" + ecCompanyId + "/" + cart;
        File cartPath = new File(cart_path);
        String ecqulId_path = basePath + project + "/txt/" + year + "/" + month + "/" + day + "/" + ecCompanyId + "/" + cart + "/" + ecqulId;
        File ecqulIdPath = new File(ecqulId_path);
        String realPath = project + "/txt/" + year + "/" + month + "/" + day + "/" + ecCompanyId + "/" + cart + "/" + ecqulId;
        if (!projectPath.exists()) {
            projectPath.mkdir();
            signPath.mkdir();
            yearPath.mkdir();
            monthPath.mkdir();
            dayPath.mkdir();
            companyPath.mkdir();
            cartPath.mkdir();
            ecqulIdPath.mkdir();
        } else if (!signPath.exists()) {
            signPath.mkdir();
            yearPath.mkdir();
            monthPath.mkdir();
            dayPath.mkdir();
            companyPath.mkdir();
            cartPath.mkdir();
            ecqulIdPath.mkdir();
        } else if (!yearPath.exists()) {
            yearPath.mkdir();
            monthPath.mkdir();
            dayPath.mkdir();
            companyPath.mkdir();
            cartPath.mkdir();
            ecqulIdPath.mkdir();
        } else if (!monthPath.exists()) {
            monthPath.mkdir();
            dayPath.mkdir();
            companyPath.mkdir();
            cartPath.mkdir();
            ecqulIdPath.mkdir();
        } else if (!dayPath.exists()) {
            dayPath.mkdir();
            companyPath.mkdir();
            cartPath.mkdir();
            ecqulIdPath.mkdir();
        } else if (!companyPath.exists()) {
            companyPath.mkdir();
            cartPath.mkdir();
            ecqulIdPath.mkdir();
        } else if (!cartPath.exists()) {
            cartPath.mkdir();
            ecqulIdPath.mkdir();
        } else if (!ecqulIdPath.exists()) {
            ecqulIdPath.mkdir();
        }
        return realPath;
    }

    // 文件储存路径 ecdPcc
    public static String pathTxtPcc(String basePath) {
        String project = "lanchacha";
        String project_path = basePath + project;
        File projectPath = new File(project_path);
        String sign_path = basePath + project + "/txt";
        File signPath = new File(sign_path);
        String pcc_path = basePath + project + "/txt/pcc";
        File pccPath = new File(pcc_path);
        String cart_path = basePath + project + "/txt/pcc/ecdPcc";
        File cartPath = new File(cart_path);
        String realPath = project + "/txt/pcc/ecdPcc";
        if (!projectPath.exists()) {
            projectPath.mkdir();
            signPath.mkdir();
            pccPath.mkdir();
            cartPath.mkdir();
        } else if (!signPath.exists()) {
            signPath.mkdir();
            pccPath.mkdir();
            cartPath.mkdir();
        } else if (!pccPath.exists()) {
            pccPath.mkdir();
            cartPath.mkdir();
        } else if (!cartPath.exists()) {
            cartPath.mkdir();
        }
        return realPath;
    }

    // 文件储存路径 ecdArea
    public static String pathTxtEcduPcc(String basePath, String ecCompanyId, String cart) {
        Date date = new Date();
        String project = "lanchacha";
        SimpleDateFormat format_year = new SimpleDateFormat("yyyy");
        SimpleDateFormat format_month = new SimpleDateFormat("MM");
        SimpleDateFormat format_date = new SimpleDateFormat("dd");
        String year = format_year.format(date);
        String month = format_month.format(date);
        String day = format_date.format(date);
        String project_path = basePath + project;
        File projectPath = new File(project_path);
        String sign_path = basePath + project + "/txt";
        File signPath = new File(sign_path);
        String year_path = basePath + project + "/txt/" + year;
        File yearPath = new File(year_path);
        String month_path = basePath + project + "/txt/" + year + "/" + month;
        File monthPath = new File(month_path);
        String day_path = basePath + project + "/txt/" + year + "/" + month + "/" + day;
        File dayPath = new File(day_path);
        String company_path = basePath + project + "/txt/" + year + "/" + month + "/" + day + "/" + ecCompanyId;
        File companyPath = new File(company_path);
        String cart_path = basePath + project + "/txt/" + year + "/" + month + "/" + day + "/" + ecCompanyId + "/" + cart;
        File cartPath = new File(cart_path);
        String realPath = project + "/txt/" + year + "/" + month + "/" + day + "/" + ecCompanyId + "/" + cart;
        if (!projectPath.exists()) {
            projectPath.mkdir();
            signPath.mkdir();
            yearPath.mkdir();
            monthPath.mkdir();
            dayPath.mkdir();
            companyPath.mkdir();
            cartPath.mkdir();
        } else if (!signPath.exists()) {
            signPath.mkdir();
            yearPath.mkdir();
            monthPath.mkdir();
            dayPath.mkdir();
            companyPath.mkdir();
            cartPath.mkdir();
        } else if (!yearPath.exists()) {
            yearPath.mkdir();
            monthPath.mkdir();
            dayPath.mkdir();
            companyPath.mkdir();
            cartPath.mkdir();
        } else if (!monthPath.exists()) {
            monthPath.mkdir();
            dayPath.mkdir();
            companyPath.mkdir();
            cartPath.mkdir();
        } else if (!dayPath.exists()) {
            dayPath.mkdir();
            companyPath.mkdir();
            cartPath.mkdir();
        } else if (!companyPath.exists()) {
            companyPath.mkdir();
            cartPath.mkdir();
        } else if (!cartPath.exists()) {
            cartPath.mkdir();
        }
        return realPath;
    }

    // getTxtContent
    public static String getTxtContent(String path, String txtUrl) {
        String txtContent = "";
        String filePath = path + txtUrl;
        Map<Integer, String> map = TxtUtils.readTxtFile(filePath);
        txtContent = map.get(1);
        return txtContent;
    }
}
