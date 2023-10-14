 package org.jeecg.modules.online.cgreport.c;
 
 import com.alibaba.fastjson.JSONArray;
 import com.alibaba.fastjson.JSONObject;
 import java.io.UnsupportedEncodingException;
 import java.text.SimpleDateFormat;
 import java.util.Date;
 import java.util.Iterator;
 import java.util.List;
 import java.util.Map;
 import jakarta.servlet.http.HttpServletRequest;
 import org.jeecg.common.util.DateUtils;
 import org.jeecg.common.util.oConvertUtils;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 
 public class a {
   private static final Logger a = LoggerFactory.getLogger(a.class);
   
   public static void a(HttpServletRequest paramHttpServletRequest, Map<String, Object> paramMap1, Map<String, Object> paramMap2, Map<String, Object> paramMap3) {
     String str1 = (String)paramMap1.get("field_name");
     String str2 = (String)paramMap1.get("search_mode");
     String str3 = (String)paramMap1.get("field_type");
     if ("single".equals(str2)) {
       String str = paramHttpServletRequest.getParameter(str1.toLowerCase());
       try {
         if (oConvertUtils.isEmpty(str))
           return; 
         String str4 = paramHttpServletRequest.getQueryString();
         if (str4.contains(str1 + "=")) {
           String str5 = new String(str.getBytes("ISO-8859-1"), "UTF-8");
           str = str5;
         } 
       } catch (UnsupportedEncodingException unsupportedEncodingException) {
         a.error(unsupportedEncodingException.getMessage(), unsupportedEncodingException);
         return;
       } 
       if (oConvertUtils.isNotEmpty(str))
         if (str.contains("*")) {
           str = str.replaceAll("\\*", "%");
           paramMap2.put(str1, " LIKE :" + str1);
         } else {
           paramMap2.put(str1, " = :" + str1);
         }  
       paramMap3.put(str1, a(str3, str, true));
     } else if ("group".equals(str2)) {
       String str4 = paramHttpServletRequest.getParameter(str1.toLowerCase() + "_begin");
       String str5 = paramHttpServletRequest.getParameter(str1.toLowerCase() + "_end");
       if (oConvertUtils.isNotEmpty(str4)) {
         String str = " >= :" + str1 + "_begin";
         paramMap2.put(str1, str);
         paramMap3.put(str1 + "_begin", a(str3, str4, true));
       } 
       if (oConvertUtils.isNotEmpty(str5)) {
         String str = " <= :" + str1 + "_end";
         paramMap2.put(new String(str1), str);
         paramMap3.put(str1 + "_end", a(str3, str5, false));
       } 
     } 
   }
   
   private static Object a(String paramString1, String paramString2, boolean paramBoolean) {
     String str = null;
     if (oConvertUtils.isNotEmpty(paramString2))
       if ("String".equalsIgnoreCase(paramString1)) {
         str = paramString2;
       } else if ("Date".equalsIgnoreCase(paramString1)) {
         if (paramString2.length() != 19 && 
           paramString2.length() == 10)
           if (paramBoolean) {
             paramString2 = paramString2 + " 00:00:00";
           } else {
             paramString2 = paramString2 + " 23:59:59";
           }  
         SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         Date date = DateUtils.str2Date(paramString2, simpleDateFormat);
       } else if ("Double".equalsIgnoreCase(paramString1)) {
         str = paramString2;
       } else if ("Integer".equalsIgnoreCase(paramString1)) {
         str = paramString2;
       } else {
         str = paramString2;
       }  
     return str;
   }
   
   public static String a(List<Map<String, Object>> paramList, Long paramLong) {
     JSONObject jSONObject = new JSONObject();
     JSONArray jSONArray = new JSONArray();
     jSONObject.put("total", paramLong);
     if (paramList != null)
       for (Map<String, Object> map : paramList) {
         JSONObject jSONObject1 = new JSONObject();
         Iterator<String> iterator = map.keySet().iterator();
         while (iterator.hasNext()) {
           String str1 = iterator.next();
           String str2 = String.valueOf(map.get(str1));
           str1 = str1.toLowerCase();
           if (str1.contains("time") || str1.contains("date"))
             str2 = a(str2); 
           jSONObject1.put(str1, str2);
         } 
         jSONArray.add(jSONObject1);
       }  
     jSONObject.put("rows", jSONArray);
     return jSONObject.toString();
   }
   
   public static String a(List<Map<String, Object>> paramList) {
     JSONArray jSONArray = new JSONArray();
     for (Map<String, Object> map : paramList) {
       JSONObject jSONObject = new JSONObject();
       Iterator<String> iterator = map.keySet().iterator();
       while (iterator.hasNext()) {
         String str1 = iterator.next();
         String str2 = String.valueOf(map.get(str1));
         str1 = str1.toLowerCase();
         if (str1.contains("time") || str1.contains("date"))
           str2 = a(str2); 
         jSONObject.put(str1, str2);
       } 
       jSONArray.add(jSONObject);
     } 
     return jSONArray.toString();
   }
   
   public static String a(String paramString) {
     SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
     SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
     Date date = null;
     try {
       date = simpleDateFormat1.parse(paramString);
       return simpleDateFormat2.format(date);
     } catch (Exception exception) {
       return paramString;
     } 
   }
 }


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\cgreport\c\a.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
