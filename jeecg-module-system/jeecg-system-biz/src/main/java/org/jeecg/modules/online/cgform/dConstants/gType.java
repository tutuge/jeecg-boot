package org.jeecg.modules.online.cgform.dConstants;

import com.alibaba.fastjson.JSONObject;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;

import java.math.BigDecimal;
import java.util.Map;

public class gType {
    public static final String a = "int";

    public static final String b = "Integer";

    public static final String c = "double";

    public static final String d = "BigDecimal";

    public static final String e = "Blob";

    public static final String f = "Date";

    public static final String g = "datetime";

    public static final String h = "Timestamp";

    public static final String i = "Long";

    public static boolean a(String paramString) {
        return ("int".equals(paramString) || "double".equals(paramString) || "BigDecimal".equals(paramString) || "Integer".equals(paramString) || "Long".equals(paramString));
    }

    public static boolean b(String paramString) {
        return ("Date".equalsIgnoreCase(paramString) || "datetime".equalsIgnoreCase(paramString) || "Timestamp".equalsIgnoreCase(paramString));
    }

    public static String a(String paramString, OnlCgformField paramOnlCgformField, JSONObject paramJSONObject, Map<String, Object> paramMap) {
        String str1 = paramOnlCgformField.getDbType();
        String str2 = paramOnlCgformField.getDbFieldName();
        String str3 = paramOnlCgformField.getFieldShowType();
        if (paramJSONObject.get(str2) == null)
            return "null";
        if ("int".equals(str1)) {
            paramMap.put(str2, Integer.valueOf(paramJSONObject.getIntValue(str2)));
            return "#{" + str2 + ",jdbcType=INTEGER}";
        }
        if ("double".equals(str1)) {
            paramMap.put(str2, Double.valueOf(paramJSONObject.getDoubleValue(str2)));
            return "#{" + str2 + ",jdbcType=DOUBLE}";
        }
        if ("BigDecimal".equals(str1)) {
            paramMap.put(str2, new BigDecimal(paramJSONObject.getString(str2)));
            return "#{" + str2 + ",jdbcType=DECIMAL}";
        }
        if ("Blob".equals(str1)) {
            paramMap.put(str2, (paramJSONObject.getString(str2) != null) ? paramJSONObject.getString(str2).getBytes() : null);
            return "#{" + str2 + ",jdbcType=BLOB}";
        }
        if ("Date".equals(str1) || "datetime".equalsIgnoreCase(str1)) {
            String str = paramJSONObject.getString(str2);
            if ("ORACLE".equals(paramString)) {
                if ("date".equals(str3)) {
                    paramMap.put(str2, (str.length() > 10) ? str.substring(0, 10) : str);
                    return "to_date(#{" + str2 + "},'yyyy-MM-dd')";
                }
                paramMap.put(str2, (str.length() == 10) ? (paramJSONObject.getString(str2) + " 00:00:00") : str);
                return "to_date(#{" + str2 + "},'yyyy-MM-dd HH24:mi:ss')";
            }
            if ("POSTGRESQL".equals(paramString)) {
                if ("date".equals(str3)) {
                    paramMap.put(str2, (str.length() > 10) ? str.substring(0, 10) : str);
                    return "CAST(#{" + str2 + "} as DATE)";
                }
                paramMap.put(str2, (str.length() == 10) ? (paramJSONObject.getString(str2) + " 00:00:00") : str);
                return "CAST(#{" + str2 + "} as TIMESTAMP)";
            }
            paramMap.put(str2, paramJSONObject.getString(str2));
            return "#{" + str2 + "}";
        }
        paramMap.put(str2, paramJSONObject.getString(str2));
        return "#{" + str2 + ",jdbcType=VARCHAR}";
    }
}
