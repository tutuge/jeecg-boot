 package org.jeecg.modules.online.cgreport.c;

 import com.baomidou.mybatisplus.annotation.DbType;
 import com.baomidou.mybatisplus.extension.plugins.pagination.DialectFactory;
 import com.baomidou.mybatisplus.extension.plugins.pagination.DialectModel;
 import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
 import com.baomidou.mybatisplus.extension.plugins.pagination.dialects.IDialect;
 import com.baomidou.mybatisplus.extension.toolkit.JdbcUtils;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.List;
 import java.util.Map;
 import java.util.regex.Matcher;
 import java.util.regex.Pattern;
 import jakarta.servlet.http.HttpServletRequest;
 import org.jeecg.common.system.vo.DynamicDataSourceModel;
 import org.jeecg.common.util.ReflectHelper;
 import org.jeecg.common.util.dynamic.db.DataSourceCachePool;
 import org.jeecg.common.util.dynamic.db.DynamicDBUtil;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;

 public class c {
   private static final Logger d = LoggerFactory.getLogger(c.class);

   public static final String a = " where ";

   public static final String b = " and ";

   public static final String c = " or ";

   public static String a(String paramString) {
     paramString = paramString.replaceAll("(?i) where ", " where ");
     paramString = paramString.replaceAll("(?i) and ", " and ");
     paramString = paramString.replaceAll("(?i) or ", " or ");
     String str = "(,\\s*|\\s*(\\w|\\.)+\\s*[^, ]+ *\\S*)\\$\\{\\w+\\}\\S*";
     Pattern pattern = Pattern.compile(str);
     Matcher matcher = pattern.matcher(paramString);
     while (matcher.find()) {
       String str1 = matcher.group();
       if (str1.indexOf(" where ") != -1) {
         String str2 = str1.substring(0, str1.indexOf(" where "));
         paramString = paramString.replace(str1, str2 + " where 1=1");
         continue;
       }
       if (str1.indexOf(" and ") != -1) {
         str1 = str1.substring(str1.indexOf("and"));
         if (str1.indexOf("(") > 0) {
           str1 = str1.substring(str1.indexOf("(") + 1);
           paramString = paramString.replace(str1, " 1=1 ");
           continue;
         }
         paramString = paramString.replace(str1, "and 1=1");
         continue;
       }
       if (str1.indexOf(" or ") != -1) {
         str1 = str1.substring(str1.indexOf("or"));
         if (str1.indexOf("(") > 0) {
           str1 = str1.substring(str1.indexOf("(") + 1);
           paramString = paramString.replace(str1, " 1=1 ");
           continue;
         }
         paramString = paramString.replace(str1, "or 1=1");
         continue;
       }
       if (str1.startsWith(",")) {
         paramString = paramString.replace(str1, " ,1 ");
         continue;
       }
       paramString = paramString.replace(str1, " 1=1 ");
     }
     paramString = paramString.replaceAll("(?i)\\(\\s*1=1\\s*(AND|OR)", "(");
     paramString = paramString.replaceAll("(?i)(AND|OR)\\s*1=1", "");
     return paramString;
   }

   public static Map<String, Object> a(HttpServletRequest paramHttpServletRequest) {
     Map map = paramHttpServletRequest.getParameterMap();
     HashMap<Object, Object> hashMap = new HashMap<>(5);
     Iterator<Map.Entry> iterator = map.entrySet().iterator();
     String str1 = "";
     String str2 = "";
     Object object = null;
     while (iterator.hasNext()) {
       Map.Entry entry = iterator.next();
       str1 = (String)entry.getKey();
       object = entry.getValue();
       if ("_t".equals(str1) || null == object) {
         str2 = "";
       } else if (object instanceof String[]) {
         String[] arrayOfString = (String[])object;
         for (byte b = 0; b < arrayOfString.length; b++)
           str2 = arrayOfString[b] + ",";
         str2 = str2.substring(0, str2.length() - 1);
       } else {
         str2 = object.toString();
       }
       hashMap.put(str1, str2);
     }
     return (Map)hashMap;
   }

   public static boolean b(String paramString) {
     String str = paramString.toLowerCase();
     return (str.indexOf("select") == 0);
   }

   public static String c(String paramString) {
     return String.format("SELECT COUNT(1) \"total\" FROM ( %s ) temp_count", new Object[] { paramString });
   }

   public static Map<String, Object> a(String paramString1, String paramString2) {
     Map<String, Object> map = null;
     DynamicDataSourceModel dynamicDataSourceModel = DataSourceCachePool.getCacheDynamicDataSourceModel(paramString1);
     DbType dbType = JdbcUtils.getDbType(dynamicDataSourceModel.getDbUrl());
     IDialect iDialect = DialectFactory.getDialect(dbType);
     if (paramString2.toUpperCase().contains("LIMIT") || paramString2.toUpperCase().contains("OFFSET")) {
       List<Map> list = DynamicDBUtil.findList(paramString1, paramString2, new Object[0]);
       if (list != null && list.size() > 0)
         map = list.get(0);
     } else {
       Page page = new Page(1L, 1L);
       DialectModel dialectModel = iDialect.buildPaginationSql(paramString2, page.offset(), page.getSize());
       String str = dialectModel.getDialectSql();
       if (str.contains("?")) {
         long l1 = ((Long)ReflectHelper.getFieldVal("firstParam", dialectModel)).longValue();
         long l2 = ((Long)ReflectHelper.getFieldVal("secondParam", dialectModel)).longValue();
         int i = str.length() - str.replaceAll("\\?", "").length();
         if (i == 1) {
           map = (Map)DynamicDBUtil.findOne(paramString1, str, new Object[] { Long.valueOf(l1) });
         } else {
           map = (Map)DynamicDBUtil.findOne(paramString1, str, new Object[] { Long.valueOf(l1), Long.valueOf(l2) });
         }
       } else {
         map = (Map)DynamicDBUtil.findOne(paramString1, str, new Object[0]);
       }
     }
     return map;
   }

   public static List<Map<String, Object>> a(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2, Map<String, Object> paramMap) {
     DynamicDataSourceModel dynamicDataSourceModel = DataSourceCachePool.getCacheDynamicDataSourceModel(paramString2);
     String str = paramString3;
     DialectModel dialectModel = null;
     List<Map<String, Object>> list = null;
     if (!Boolean.valueOf(paramString1).booleanValue()) {
       DbType dbType = JdbcUtils.getDbType(dynamicDataSourceModel.getDbUrl());
       IDialect iDialect = DialectFactory.getDialect(dbType);
       Page page = new Page(paramInt1, paramInt2);
       dialectModel = iDialect.buildPaginationSql(paramString3, page.offset(), page.getSize());
       str = dialectModel.getDialectSql();
     }
     if (str.contains("?")) {
       long l1 = ((Long)ReflectHelper.getFieldVal("firstParam", dialectModel)).longValue();
       long l2 = ((Long)ReflectHelper.getFieldVal("secondParam", dialectModel)).longValue();
       int i = str.length() - str.replaceAll("\\?", "").length();
       if (i == 1) {
         str = b.a(str, l1);
       } else {
         str = b.a(str, l1, l2);
       }
     }
     list = DynamicDBUtil.findListByNamedParam(paramString2, str, paramMap);
     return list;
   }
 }


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\cgreport\c\c.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
