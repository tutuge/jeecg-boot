package org.jeecg.modules.online.cgform.dConstants;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;
import org.jeecg.modules.online.cgform.enums.bEnum;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class hConstant {
    private Map<String, OnlCgformField> d;

    private Map<String, OnlCgformField> e;

    private static final String f = ",";

    private static final String g = "第%s行校验信息:";

    private static final String h = "总上传行数:%s,已导入行数:%s,错误行数:%s";

    public static final String a = "error";

    public static final String b = "tip";

    public static final String c = "filePath";

    public hConstant() {
    }

    public hConstant(List<OnlCgformField> paramList) {
        this.d = new HashMap<>(5);
        this.e = new HashMap<>(5);
        for (OnlCgformField onlCgformField : paramList) {
            String str = onlCgformField.getFieldValidType();
            if (str != null && !"".equals(str) && !bEnum.a.getType().equals(str))
                if (bEnum.k.getType().equals(str)) {
                    this.e.put(onlCgformField.getDbFieldName(), onlCgformField);
                } else {
                    this.d.put(onlCgformField.getDbFieldName(), onlCgformField);
                }
            if (onlCgformField.getDbIsNull() == 0 || "1".equals(onlCgformField.getFieldMustInput()))
                if (oConvertUtils.isEmpty(onlCgformField.getDbDefaultVal()))
                    this.e.put(onlCgformField.getDbFieldName(), onlCgformField);
        }
    }

    public String a(String paramString, int paramInt) {
        StringBuffer stringBuffer = new StringBuffer();
        JSONObject jSONObject = JSON.parseObject(paramString);
        for (String str1 : this.e.keySet()) {
            String str2 = jSONObject.getString(str1);
            OnlCgformField onlCgformField = this.e.get(str1);
            if (str2 == null || "".equals(str2))
                stringBuffer.append(onlCgformField.getDbFieldTxt() + bEnum.k.getMsg() + ",");
        }
        for (String str1 : this.d.keySet()) {
            String str2 = jSONObject.getString(str1);
            OnlCgformField onlCgformField = this.d.get(str1);
            String str3 = onlCgformField.getFieldValidType();
            if (str2 == null || "".equals(str2))
                continue;
            String str4 = null, str5 = null;
            if (bEnum.j.getType().equals(str3)) {
                str4 = "^-?[1-9]\\d*$";
                str5 = "请输入整数";
            } else {
                bEnum b = bEnum.b(str3);
                if (b == null) {
                    str4 = str3;
                    str5 = "校验【" + str4 + "】未通过";
                } else {
                    str4 = b.getPattern();
                    str5 = b.getMsg();
                }
            }
            Pattern pattern = Pattern.compile(str4);
            Matcher matcher = pattern.matcher(str2);
            if (!matcher.find())
                stringBuffer.append(onlCgformField.getDbFieldTxt() + str5 + ",");
        }
        if (!stringBuffer.isEmpty())
            return b(stringBuffer.toString(), paramInt);
        return null;
    }

    public static String b(String paramString, int paramInt) {
        return String.format("第%s行校验信息:", new Object[]{Integer.valueOf(paramInt)}) + paramString + "\r\n";
    }

    public static String a(int paramInt1, int paramInt2) {
        int i = paramInt1 - paramInt2;
        return String.format("总上传行数:%s,已导入行数:%s,错误行数:%s", paramInt1, i, paramInt2);
    }
}
