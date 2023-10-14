 package org.jeecg.modules.online.config.dUtil;

 import com.alibaba.fastjson.JSONArray;
 import com.alibaba.fastjson.JSONObject;
 import java.io.UnsupportedEncodingException;
 import java.net.URLDecoder;
 import java.util.ArrayList;
 import java.util.Date;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import org.apache.commons.lang.StringUtils;
 import org.jeecg.common.system.query.MatchTypeEnum;
 import org.jeecg.common.system.query.QueryGenerator;
 import org.jeecg.common.system.query.QueryRuleEnum;
 import org.jeecg.common.system.util.JeecgDataAutorUtils;
 import org.jeecg.common.system.vo.SysPermissionDataRuleModel;
 import org.jeecg.common.util.DateUtils;
 import org.jeecg.common.util.SpringContextUtils;
 import org.jeecg.common.util.oConvertUtils;
 import org.jeecg.modules.online.cgform.dConstants.bConstant;
 import org.jeecg.modules.online.cgform.entity.OnlCgformField;
 import org.jeecg.modules.online.cgform.service.IOnlCgformFieldService;
 import org.jeecg.modules.online.config.bAttribute.eTableConfig;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;

 public class b {
   public void setAlias(String alias) {
     this.c = alias;
   }

   public void setAliasNoPoint(String aliasNoPoint) {
     this.d = aliasNoPoint;
   }

   public void setDataBaseType(String dataBaseType) {
     this.e = dataBaseType;
   }

   public void setDateStringSearch(boolean dateStringSearch) {
     this.f = dateStringSearch;
   }

   public void setFieldList(List<eTableConfig> fieldList) {
     this.g = fieldList;
   }

   public void setNeedList(List<String> needList) {
     this.h = needList;
   }

   public void setAuthDatalist(List<SysPermissionDataRuleModel> authDatalist) {
     this.i = authDatalist;
   }

   public void setReqParams(Map<String, Object> reqParams) {
     this.j = reqParams;
   }

   public void setSql(StringBuffer sql) {
     this.k = sql;
   }

   public void setSuperQuerySql(StringBuffer superQuerySql) {
     this.l = superQuerySql;
   }

   public void setSqlParams(Map<String, Object> sqlParams) {
     this.m = sqlParams;
   }

   public void setDaoType(String daoType) {
     this.n = daoType;
   }

   public void setSuperQuery(boolean superQuery) {
     this.o = superQuery;
   }

   public void setMatchType(String matchType) {
     this.p = matchType;
   }

   public void setUsePage(int usePage) {
     this.q = usePage;
   }

   public void setFirst(boolean first) {
     this.r = first;
   }

   public void setParamPrefix(String paramPrefix) {
     this.s = paramPrefix;
   }

   public void setDuplicateSqlNameRecord(Map<String, String> duplicateSqlNameRecord) {
     this.t = duplicateSqlNameRecord;
   }

   public void setDuplicateParamNameRecord(Map<String, String> duplicateParamNameRecord) {
     this.u = duplicateParamNameRecord;
   }

   public void setTableName(String tableName) {
     this.v = tableName;
   }

   public void setSubTableStr(String subTableStr) {
     this.a = subTableStr;
   }

   public boolean equals(Object o) {
     if (o == this)
       return true;
     if (!(o instanceof b))
       return false;
     b b1 = (b)o;
     if (!b1.a(this))
       return false;
     if (a() != b1.a())
       return false;
     if (b() != b1.b())
       return false;
     if (getUsePage() != b1.getUsePage())
       return false;
     if (c() != b1.c())
       return false;
     String str1 = getAlias(), str2 = b1.getAlias();
     if ((str1 == null) ? (str2 != null) : !str1.equals(str2))
       return false;
     String str3 = getAliasNoPoint(), str4 = b1.getAliasNoPoint();
     if ((str3 == null) ? (str4 != null) : !str3.equals(str4))
       return false;
     String str5 = getDataBaseType(), str6 = b1.getDataBaseType();
     if ((str5 == null) ? (str6 != null) : !str5.equals(str6))
       return false;
     List<eTableConfig> list1 = getFieldList(), list2 = b1.getFieldList();
     if ((list1 == null) ? (list2 != null) : !list1.equals(list2))
       return false;
     List<String> list3 = getNeedList(), list4 = b1.getNeedList();
     if ((list3 == null) ? (list4 != null) : !list3.equals(list4))
       return false;
     List<SysPermissionDataRuleModel> list5 = getAuthDatalist(), list6 = b1.getAuthDatalist();
     if ((list5 == null) ? (list6 != null) : !list5.equals(list6))
       return false;
     Map<String, Object> map1 = getReqParams(), map2 = b1.getReqParams();
     if ((map1 == null) ? (map2 != null) : !map1.equals(map2))
       return false;
     StringBuffer stringBuffer1 = getSql(), stringBuffer2 = b1.getSql();
     if ((stringBuffer1 == null) ? (stringBuffer2 != null) : !stringBuffer1.equals(stringBuffer2))
       return false;
     StringBuffer stringBuffer3 = getSuperQuerySql(), stringBuffer4 = b1.getSuperQuerySql();
     if ((stringBuffer3 == null) ? (stringBuffer4 != null) : !stringBuffer3.equals(stringBuffer4))
       return false;
     Map<String, Object> map3 = getSqlParams(), map4 = b1.getSqlParams();
     if ((map3 == null) ? (map4 != null) : !map3.equals(map4))
       return false;
     String str7 = getDaoType(), str8 = b1.getDaoType();
     if ((str7 == null) ? (str8 != null) : !str7.equals(str8))
       return false;
     String str9 = getMatchType(), str10 = b1.getMatchType();
     if ((str9 == null) ? (str10 != null) : !str9.equals(str10))
       return false;
     String str11 = getParamPrefix(), str12 = b1.getParamPrefix();
     if ((str11 == null) ? (str12 != null) : !str11.equals(str12))
       return false;
     Map<String, String> map5 = getDuplicateSqlNameRecord(), map6 = b1.getDuplicateSqlNameRecord();
     if ((map5 == null) ? (map6 != null) : !map5.equals(map6))
       return false;
     Map<String, String> map7 = getDuplicateParamNameRecord(), map8 = b1.getDuplicateParamNameRecord();
     if ((map7 == null) ? (map8 != null) : !map7.equals(map8))
       return false;
     String str13 = getTableName(), str14 = b1.getTableName();
     if ((str13 == null) ? (str14 != null) : !str13.equals(str14))
       return false;
     String str15 = getSubTableStr(), str16 = b1.getSubTableStr();
     return !((str15 == null) ? (str16 != null) : !str15.equals(str16));
   }

   protected boolean a(Object paramObject) {
     return paramObject instanceof b;
   }

   public int hashCode() {
     byte b1 = 59;
     null = 1;
     null = null * 59 + (a() ? 79 : 97);
     null = null * 59 + (b() ? 79 : 97);
     null = null * 59 + getUsePage();
     null = null * 59 + (c() ? 79 : 97);
     String str1 = getAlias();
     null = null * 59 + ((str1 == null) ? 43 : str1.hashCode());
     String str2 = getAliasNoPoint();
     null = null * 59 + ((str2 == null) ? 43 : str2.hashCode());
     String str3 = getDataBaseType();
     null = null * 59 + ((str3 == null) ? 43 : str3.hashCode());
     List<eTableConfig> list = getFieldList();
     null = null * 59 + ((list == null) ? 43 : list.hashCode());
     List<String> list1 = getNeedList();
     null = null * 59 + ((list1 == null) ? 43 : list1.hashCode());
     List<SysPermissionDataRuleModel> list2 = getAuthDatalist();
     null = null * 59 + ((list2 == null) ? 43 : list2.hashCode());
     Map<String, Object> map1 = getReqParams();
     null = null * 59 + ((map1 == null) ? 43 : map1.hashCode());
     StringBuffer stringBuffer1 = getSql();
     null = null * 59 + ((stringBuffer1 == null) ? 43 : stringBuffer1.hashCode());
     StringBuffer stringBuffer2 = getSuperQuerySql();
     null = null * 59 + ((stringBuffer2 == null) ? 43 : stringBuffer2.hashCode());
     Map<String, Object> map2 = getSqlParams();
     null = null * 59 + ((map2 == null) ? 43 : map2.hashCode());
     String str4 = getDaoType();
     null = null * 59 + ((str4 == null) ? 43 : str4.hashCode());
     String str5 = getMatchType();
     null = null * 59 + ((str5 == null) ? 43 : str5.hashCode());
     String str6 = getParamPrefix();
     null = null * 59 + ((str6 == null) ? 43 : str6.hashCode());
     Map<String, String> map3 = getDuplicateSqlNameRecord();
     null = null * 59 + ((map3 == null) ? 43 : map3.hashCode());
     Map<String, String> map4 = getDuplicateParamNameRecord();
     null = null * 59 + ((map4 == null) ? 43 : map4.hashCode());
     String str7 = getTableName();
     null = null * 59 + ((str7 == null) ? 43 : str7.hashCode());
     String str8 = getSubTableStr();
     return null * 59 + ((str8 == null) ? 43 : str8.hashCode());
   }

   public String toString() {
     return "ConditionHandler(alias=" + getAlias() + ", aliasNoPoint=" + getAliasNoPoint() + ", dataBaseType=" + getDataBaseType() + ", dateStringSearch=" + a() + ", fieldList=" + getFieldList() + ", needList=" + getNeedList() + ", authDatalist=" + getAuthDatalist() + ", reqParams=" + getReqParams() + ", sql=" + getSql() + ", superQuerySql=" + getSuperQuerySql() + ", sqlParams=" + getSqlParams() + ", daoType=" + getDaoType() + ", superQuery=" + b() + ", matchType=" + getMatchType() + ", usePage=" + getUsePage() + ", first=" + c() + ", paramPrefix=" + getParamPrefix() + ", duplicateSqlNameRecord=" + getDuplicateSqlNameRecord() + ", duplicateParamNameRecord=" + getDuplicateParamNameRecord() + ", tableName=" + getTableName() + ", subTableStr=" + getSubTableStr() + ")";
   }

   private static final Logger b = LoggerFactory.getLogger(b.class);

   private String c;

   private String d;

   private String e;

   private boolean f;

   private List<eTableConfig> g;

   private List<String> h;

   private List<SysPermissionDataRuleModel> i;

   private Map<String, Object> j;

   private StringBuffer k;

   private StringBuffer l;

   private Map<String, Object> m;

   private String n;

   private boolean o;

   private String p;

   private int q;

   private boolean r;

   private String s;

   private Map<String, String> t;

   private Map<String, String> u;

   private String v;

   public String a;

   public String getAlias() {
     return this.c;
   }

   public String getAliasNoPoint() {
     return this.d;
   }

   public String getDataBaseType() {
     return this.e;
   }

   public boolean a() {
     return this.f;
   }

   public List<eTableConfig> getFieldList() {
     return this.g;
   }

   public List<String> getNeedList() {
     return this.h;
   }

   public List<SysPermissionDataRuleModel> getAuthDatalist() {
     return this.i;
   }

   public Map<String, Object> getReqParams() {
     return this.j;
   }

   public StringBuffer getSql() {
     return this.k;
   }

   public StringBuffer getSuperQuerySql() {
     return this.l;
   }

   public Map<String, Object> getSqlParams() {
     return this.m;
   }

   public String getDaoType() {
     return this.n;
   }

   public boolean b() {
     return this.o;
   }

   public String getMatchType() {
     return this.p;
   }

   public int getUsePage() {
     return this.q;
   }

   public boolean c() {
     return this.r;
   }

   public String getParamPrefix() {
     return this.s;
   }

   public Map<String, String> getDuplicateSqlNameRecord() {
     return this.t;
   }

   public Map<String, String> getDuplicateParamNameRecord() {
     return this.u;
   }

   public String getTableName() {
     return this.v;
   }

   public b() {}

   public b(String paramString1, String paramString2) {
     this.c = paramString1;
     this.d = paramString1.replace(".", "");
     this.e = paramString2;
     this.f = e(paramString2);
     this.k = new StringBuffer();
     this.m = new HashMap<>(5);
     this.i = null;
     this.h = null;
     this.p = " AND ";
     this.q = 1;
     this.r = true;
     this.s = "";
     this.t = new HashMap<>(5);
     this.u = new HashMap<>(5);
   }

   public b(String paramString) {
     this(paramString, null);
     this.q = 2;
   }

   public b(String paramString1, boolean paramBoolean, String paramString2) {
     this(paramString1, null);
     this.o = paramBoolean;
     this.p = " " + paramString2 + " ";
     this.q = 2;
   }

   public String a(List<eTableConfig> paramList, Map<String, Object> paramMap) {
     c(paramList, paramMap);
     d();
     return this.k.toString();
   }

   public String a(List<eTableConfig> paramList, Map<String, Object> paramMap, List<SysPermissionDataRuleModel> paramList1) {
     setAuthDatalist(paramList1);
     c(paramList, paramMap);
     b(paramList);
     a(paramMap);
     e();
     return this.k.toString();
   }

   public String a(List<eTableConfig> paramList, Map<String, Object> paramMap, List<SysPermissionDataRuleModel> paramList1, String paramString) {
     setAuthDatalist(paramList1);
     this.s = paramString;
     c(paramList, paramMap);
     b(paramList);
     e();
     return this.k.toString();
   }

   public String b(List<eTableConfig> paramList, Map<String, Object> paramMap) {
     c(paramList, paramMap);
     return this.k.toString();
   }

   public String a(List<eTableConfig> paramList) {
     if (this.o)
       for (eTableConfig e : paramList) {
         String str1 = e.getName();
         String str2 = e.getVal();
         if (str2 != null) {
           QueryRuleEnum queryRuleEnum = QueryRuleEnum.getByValue(e.getRule());
           if (queryRuleEnum == null)
             queryRuleEnum = QueryRuleEnum.EQ;
           a(str1, e.getType(), str2, queryRuleEnum);
         }
       }
     return this.k.toString();
   }

   public void c(List<eTableConfig> paramList, Map<String, Object> paramMap) {
     for (eTableConfig e : paramList) {
       String str1 = e.getName();
       String str2 = e.getType();
       if (this.h != null && this.h.contains(str1)) {
         e.setIsSearch(Integer.valueOf(1));
         e.setMode("single");
       }
       if (oConvertUtils.isNotEmpty(e.getMainField()) && oConvertUtils.isNotEmpty(e.getMainTable())) {
         e.setIsSearch(Integer.valueOf(1));
         e.setMode("single");
       }
       if (1 == e.getIsSearch().intValue()) {
         if ("time".equals(e.getView()) && "group".equals(e.getMode()))
           e.setMode("single");
         if ("group".equals(e.getMode())) {
           String str3 = str1 + "_begin";
           Object object1 = paramMap.get(this.s + str3);
           if (null != object1) {
             b(str1, " >= ");
             b(str3, str2, object1);
           }
           String str4 = str1 + "_end";
           Object object2 = paramMap.get(this.s + str4);
           if (null != object2) {
             b(str1, " <= ");
             a(str4, str2, object2, "end");
           }
           continue;
         }
         Object object = paramMap.get(this.s + str1);
         if (object != null) {
           String str = e.getView();
           if ("list_multi".equals(str)) {
             e(str1, object);
             continue;
           }
           if ("popup".equals(str)) {
             f(str1, object);
             continue;
           }
           a(str1, str2, object);
         }
       }
     }
     for (String str : paramMap.keySet()) {
       if (str.startsWith("popup_param_pre__")) {
         Object object = paramMap.get(str);
         if (object == null)
           continue;
         String str1 = str.replace("popup_param_pre__", "");
         a(str1, "", object);
       }
     }
   }

   public void setAuthList(List<SysPermissionDataRuleModel> authDatalist) {
     this.i = authDatalist;
   }

   private void d() {
     List<SysPermissionDataRuleModel> list = JeecgDataAutorUtils.loadDataSearchConditon();
     if (list != null && list.size() > 0)
       for (byte b1 = 0; b1 < list.size(); b1++) {
         SysPermissionDataRuleModel sysPermissionDataRuleModel = list.get(b1);
         if (sysPermissionDataRuleModel == null)
           break;
         String str = sysPermissionDataRuleModel.getRuleValue();
         if (!oConvertUtils.isEmpty(str)) {
           String str1 = sysPermissionDataRuleModel.getRuleConditions();
           if (QueryRuleEnum.SQL_RULES.getValue().equals(str1)) {
             b("", QueryGenerator.getSqlRuleValue(str));
           } else {
             QueryRuleEnum queryRuleEnum = QueryRuleEnum.getByValue(sysPermissionDataRuleModel.getRuleConditions());
             String str2 = "Integer";
             str = str.trim();
             if (str.startsWith("'") && str.endsWith("'")) {
               str2 = "string";
               str = str.substring(1, str.length() - 1);
             } else if (str.startsWith("#{") && str.endsWith("}")) {
               str2 = "string";
             }
             String str3 = QueryGenerator.converRuleValue(str);
             a(sysPermissionDataRuleModel.getRuleColumn(), str2, str3, queryRuleEnum);
           }
         }
       }
   }

   private eTableConfig a(String paramString, List<eTableConfig> paramList) {
     if (paramList != null && paramString != null) {
       String str = oConvertUtils.camelToUnderline(paramString);
       for (byte b1 = 0; b1 < paramList.size(); b1++) {
         eTableConfig e = paramList.get(b1);
         String str1 = e.getName();
         if (paramString.equals(str1) || str.equals(str1))
           return e;
       }
     }
     return null;
   }

   private void b(List<eTableConfig> paramList) {
     List<SysPermissionDataRuleModel> list = this.i;
     if (list == null)
       list = JeecgDataAutorUtils.loadDataSearchConditon();
     if (list != null && list.size() > 0)
       for (byte b1 = 0; b1 < list.size(); b1++) {
         SysPermissionDataRuleModel sysPermissionDataRuleModel = list.get(b1);
         if (sysPermissionDataRuleModel == null)
           break;
         String str = sysPermissionDataRuleModel.getRuleValue();
         if (!oConvertUtils.isEmpty(str)) {
           String str1 = sysPermissionDataRuleModel.getRuleConditions();
           if (QueryRuleEnum.SQL_RULES.getValue().equals(str1)) {
             b("", QueryGenerator.getSqlRuleValue(str));
           } else {
             String str2 = sysPermissionDataRuleModel.getRuleColumn();
             eTableConfig e = a(str2, paramList);
             if (e != null) {
               String str3 = QueryGenerator.converRuleValue(str);
               QueryRuleEnum queryRuleEnum = QueryRuleEnum.getByValue(sysPermissionDataRuleModel.getRuleConditions());
               a(e.getName(), e.getType(), str3, queryRuleEnum);
             }
           }
         }
       }
   }

   private void e() {
     String str = bConstant.f(this.v);
     boolean bool = bConstant.j(str);
     if (bool) {
       String str1 = SpringContextUtils.getHttpServletRequest().getHeader("X-Tenant-Id");
       a("tenant_id", "int", str1, QueryRuleEnum.EQ);
     }
   }

   private void a(String paramString1, String paramString2, Object paramObject) {
     a(paramString1, paramString2, paramObject, (QueryRuleEnum)null);
   }

   private void a(String paramString1, String paramString2, Object paramObject, QueryRuleEnum paramQueryRuleEnum) {
     if (paramObject != null) {
       String str = paramObject.toString();
       boolean bool = false;
       if (paramQueryRuleEnum == null) {
         bool = true;
         paramQueryRuleEnum = QueryGenerator.convert2Rule(paramObject);
       }
       if (bool)
         str = str.trim();
       switch (null.a[paramQueryRuleEnum.ordinal()]) {
         case 1:
         case 2:
           b(paramString1, paramQueryRuleEnum.getValue());
           if (bool)
             str = str.substring(1);
           b(paramString1, paramString2, str);
           return;
         case 3:
         case 4:
           b(paramString1, paramQueryRuleEnum.getValue());
           if (bool)
             str = str.substring(2);
           b(paramString1, paramString2, str);
           return;
         case 5:
           b(paramString1, paramQueryRuleEnum.getValue());
           b(paramString1, paramString2, str);
           return;
         case 6:
           b(paramString1, paramQueryRuleEnum.getValue());
           if (bool)
             str = str.replaceAll("\\+\\+", ",");
           b(paramString1, paramString2, str);
           return;
         case 7:
           b(paramString1, " <> ");
           if (bool)
             str = str.substring(1);
           b(paramString1, paramString2, str);
           return;
         case 8:
           b(paramString1, " in ");
           a(paramString1, paramString2, str);
           return;
         case 9:
         case 10:
         case 11:
           b(paramString1, " like ");
           if (bool) {
             a(paramString1, str);
           } else {
             a(paramString1, str, paramQueryRuleEnum);
           }
           return;
       }
       b(paramString1, " = ");
       b(paramString1, paramString2, str);
     }
   }

   private void a(String paramString1, String paramString2, String paramString3) {
     String[] arrayOfString = paramString3.split(",");
     if (arrayOfString.length == 0) {
       a("('')");
     } else {
       String str1 = "foreach_%s_%s";
       String str2 = "";
       for (byte b1 = 0; b1 < arrayOfString.length; b1++) {
         String str3 = arrayOfString[b1].trim();
         String str4 = String.format(str1, new Object[] { paramString1, Integer.valueOf(b1) });
         if (b1 > 0)
           str2 = str2 + ",";
         String str5 = f(str4);
         if ("jdbcTemplate".equals(this.n)) {
           str2 = str2 + ":" + str5;
         } else {
           str2 = str2 + "#{" + b(str5) + "}";
         }
         if ("Long".equals(paramString2) || "Integer".equals(paramString2)) {
           a(str4, Integer.valueOf(Integer.parseInt(str3)));
         } else {
           a(str4, str3);
         }
       }
       a("(" + str2 + ")");
     }
   }

   private void a(String paramString1, String paramString2) {
     String str1 = c(paramString1, "VARCHAR");
     a(str1);
     String str2 = "";
     if ((paramString2.startsWith("*") && paramString2.endsWith("*")) || (paramString2.startsWith("%") && paramString2.endsWith("%"))) {
       str2 = "%" + paramString2.substring(1, paramString2.length() - 1) + "%";
     } else if (paramString2.startsWith("*") || paramString2.startsWith("%")) {
       str2 = "%" + paramString2.substring(1);
     } else if (paramString2.endsWith("*") || paramString2.endsWith("%")) {
       str2 = paramString2.substring(0, paramString2.length() - 1) + "%";
     } else {
       str2 = "%" + paramString2 + "%";
     }
     a(paramString1, str2);
   }

   private void a(String paramString1, String paramString2, QueryRuleEnum paramQueryRuleEnum) {
     String str = c(paramString1, "VARCHAR");
     a(str);
     if (paramQueryRuleEnum == QueryRuleEnum.LEFT_LIKE) {
       a(paramString1, "%" + paramString2);
     } else if (paramQueryRuleEnum == QueryRuleEnum.RIGHT_LIKE) {
       a(paramString1, paramString2 + "%");
     } else {
       a(paramString1, "%" + paramString2 + "%");
     }
   }

   private void b(String paramString1, String paramString2, Object paramObject) {
     a(paramString1, paramString2, paramObject, (String)null);
   }

   private void a(String paramString1, String paramString2, Object paramObject, String paramString3) {
     String str = paramString2.toLowerCase();
     if (d(paramString2)) {
       if (bConstant.g(paramObject.toString())) {
         a(paramObject.toString());
       } else {
         a("''");
       }
     } else if ("datetime".equals(str)) {
       String str1 = paramObject.toString().trim();
       if (str1.length() <= 10)
         if ("end".equals(paramString3)) {
           str1 = str1 + " 23:59:59";
         } else {
           str1 = str1 + " 00:00:00";
         }
       Date date = DateUtils.str2Date(str1, DateUtils.datetimeFormat.get());
       b(paramString1, date);
     } else if ("date".equals(str)) {
       String str1 = paramObject.toString().trim();
       if (str1.length() > 10)
         str1 = str1.substring(0, 10);
       Date date = DateUtils.str2Date(str1, DateUtils.date_sdf.get());
       c(paramString1, date);
     } else {
       String str1 = paramObject.toString().trim();
       if (str1.startsWith("'") && str1.endsWith("'") && this.q == 1) {
         a(str1);
       } else {
         d(paramString1, str1);
       }
     }
   }

   private void b(String paramString1, String paramString2) {
     b(paramString1, paramString2, this.p);
   }

   private void b(String paramString1, String paramString2, String paramString3) {
     if (this.r == true) {
       this.r = false;
     } else {
       this.k.append(paramString3);
     }
     if (paramString1.length() > 0) {
       this.k.append(this.c).append(paramString1).append(paramString2);
     } else {
       this.k.append(" ").append(paramString2).append(" ");
     }
   }

   private void a(String paramString) {
     this.k.append(paramString);
   }

   private String c(String paramString1, String paramString2) {
     paramString1 = f(paramString1);
     if ("jdbcTemplate".equals(this.n))
       return ":" + paramString1;
     String str = b(paramString1);
     if (paramString2 == null)
       return String.format("#{%s}", new Object[] { str });
     return String.format("#{%s, jdbcType=%s}", new Object[] { str, paramString2 });
   }

   private String b(String paramString) {
     return "param." + c(paramString);
   }

   private void a(String paramString, Object paramObject) {
     paramString = g(paramString);
     this.m.put(c(paramString), paramObject);
   }

   private String c(String paramString) {
     if (this.q == 1)
       return paramString;
     return this.d + "_" + paramString;
   }

   private void b(String paramString, Object paramObject) {
     if (paramObject != null) {
       String str = c(paramString, "TIMESTAMP");
       a(str);
       a(paramString, paramObject);
     }
   }

   private void c(String paramString, Object paramObject) {
     if (paramObject != null) {
       String str = c(paramString, "DATE");
       a(str);
       a(paramString, paramObject);
     }
   }

   private void d(String paramString, Object paramObject) {
     if (paramObject != null) {
       String str = c(paramString, (String)null);
       a(str);
       a(paramString, paramObject);
     }
   }

   private boolean d(String paramString) {
     return ("Long".equals(paramString) || "Integer".equals(paramString) || "int".equals(paramString) || "double".equals(paramString) || "BigDecimal".equals(paramString) || "number".equals(paramString));
   }

   private boolean e(String paramString) {
     return !"ORACLE".equals(paramString);
   }

   public static String a(String paramString, long paramLong) {
     return paramString.replaceFirst("\\?", String.valueOf(paramLong));
   }

   public static String a(String paramString, long paramLong1, long paramLong2) {
     paramString = paramString.replaceFirst("\\?", String.valueOf(paramLong1));
     return paramString.replaceFirst("\\?", String.valueOf(paramLong2));
   }

   private void e(String paramString, Object paramObject) {
     if (paramObject != null) {
       String[] arrayOfString = paramObject.toString().split(",");
       String str1 = "";
       String str2 = this.c + paramString;
       for (byte b1 = 0; b1 < arrayOfString.length; b1++) {
         String str = str2 + " like '%" + arrayOfString[b1] + ",%' or " + str2 + " like '%," + arrayOfString[b1] + "%' or " + str2 + " = '" + arrayOfString[b1] + "'";
         if (str1.length() == 0) {
           str1 = str;
         } else {
           str1 = str1 + " or " + str;
         }
       }
       if (str1.length() > 0) {
         String str = "(" + str1 + ")";
         b("", str);
       }
     }
   }

   private void f(String paramString, Object paramObject) {
     if (paramObject != null) {
       String str1 = this.c + paramString;
       String str2 = "";
       String str3 = "popup_%s_%s";
       String[] arrayOfString = paramObject.toString().split(",");
       for (byte b1 = 0; b1 < arrayOfString.length; b1++) {
         String str4 = String.format(str3, new Object[] { paramString, Integer.valueOf(b1) });
         String str5 = c(str4, "VARCHAR");
         String str6 = "%" + arrayOfString[b1] + "%";
         a(str4, str6);
         String str7 = str1 + " like " + str5;
         if (str2.length() == 0) {
           str2 = str7;
         } else {
           str2 = str2 + " and " + str7;
         }
       }
       if (str2.length() > 0) {
         String str = "(" + str2 + ")";
         b("", str);
       }
     }
   }

   public String getSubTableStr() {
     return this.a;
   }

   private void a(Map<String, Object> paramMap) {
     Object object1 = paramMap.get("superQueryMatchType");
     MatchTypeEnum matchTypeEnum = MatchTypeEnum.getByValue(object1);
     if (matchTypeEnum == null)
       matchTypeEnum = MatchTypeEnum.AND;
     Object object2 = paramMap.get("superQueryParams");
     if (object2 == null || StringUtils.isBlank(object2.toString()))
       return;
     String str1 = null;
     try {
       str1 = URLDecoder.decode(object2.toString(), "UTF-8");
     } catch (UnsupportedEncodingException unsupportedEncodingException) {
       unsupportedEncodingException.printStackTrace();
       return;
     }
     JSONArray jSONArray = JSONArray.parseArray(str1);
     IOnlCgformFieldService iOnlCgformFieldService = (IOnlCgformFieldService)SpringContextUtils.getBean(IOnlCgformFieldService.class);
     ArrayList<String> arrayList = new ArrayList();
     arrayList.add("JEECG_SUPER_QUERY_MAIN_TABLE");
     if (this.a != null && !"".equals(this.a)) {
       String[] arrayOfString = this.a.split(",");
       for (String str : arrayOfString)
         arrayList.add(str);
     }
     HashMap<Object, Object> hashMap = new HashMap<>(5);
     StringBuffer stringBuffer = new StringBuffer();
     for (byte b1 = 0; b1 < arrayList.size(); b1++) {
       String str = arrayList.get(b1);
       ArrayList<eTableConfig> arrayList1 = new ArrayList();
       for (byte b2 = 0; b2 < jSONArray.size(); b2++) {
         JSONObject jSONObject = jSONArray.getJSONObject(b2);
         String str3 = jSONObject.getString("field");
         if (!oConvertUtils.isEmpty(str3)) {
           String[] arrayOfString = str3.split(",");
           eTableConfig e = new eTableConfig(jSONObject);
           if ("JEECG_SUPER_QUERY_MAIN_TABLE".equals(str) && arrayOfString.length == 1) {
             arrayList1.add(e);
           } else if (arrayOfString.length == 2 && arrayOfString[0].equals(str)) {
             arrayList1.add(e);
             JSONObject jSONObject1 = (JSONObject)hashMap.get(str);
             if (jSONObject1 == null) {
               List list = iOnlCgformFieldService.queryFormFieldsByTableName(str);
               jSONObject1 = new JSONObject();
               for (OnlCgformField onlCgformField : list) {
                 if (StringUtils.isNotBlank(onlCgformField.getMainTable())) {
                   jSONObject1.put("subTableName", str);
                   jSONObject1.put("subField", onlCgformField.getDbFieldName());
                   jSONObject1.put("mainTable", onlCgformField.getMainTable());
                   jSONObject1.put("mainField", onlCgformField.getMainField());
                 }
               }
               hashMap.put(str, jSONObject1);
             }
           }
         }
       }
       if (arrayList1.size() > 0) {
         String str3 = (b1 == 0) ? this.c : (this.d + b1 + ".");
         b b3 = new b(str3, true, matchTypeEnum.getValue());
         b3.setDuplicateParamNameRecord(getDuplicateParamNameRecord());
         b3.setDuplicateSqlNameRecord(getDuplicateSqlNameRecord());
         String str4 = b3.a(arrayList1);
         Map<String, Object> map = b3.getSqlParams();
         if (str4 != null && str4.length() > 0)
           if (b1 == 0) {
             stringBuffer.append(" ").append(str4).append(" ");
             this.m.putAll(map);
           } else {
             JSONObject jSONObject = (JSONObject)hashMap.get(str);
             String str5 = jSONObject.getString("subTableName");
             String str6 = jSONObject.getString("subField");
             String str7 = jSONObject.getString("mainField");
             String str8 = " %s in (select %s from %s %s where ";
             String str9 = String.format(str8, new Object[] { str7, str6, str5, this.d + b1 });
             this.m.putAll(map);
             stringBuffer.append(matchTypeEnum.getValue()).append(str9).append(str4).append(") ");
           }
       }
     }
     String str2 = stringBuffer.toString();
     if (str2.length() > 0) {
       if (str2.startsWith("AND ")) {
         str2 = str2.substring(3);
       } else if (str2.startsWith("OR ")) {
         str2 = str2.substring(2);
       }
       b("", "(" + str2 + ")");
     }
   }

   private String f(String paramString) {
     return a(paramString, this.t);
   }

   private String g(String paramString) {
     return a(paramString, this.u);
   }

   private String a(String paramString, Map<String, String> paramMap) {
     String str = paramMap.get(paramString);
     if (str == null) {
       str = paramString;
       paramMap.put(paramString, paramString + "_1");
     } else {
       String str1 = str.substring(str.lastIndexOf("_") + 1);
       String str2 = paramString + "_" + (Integer.parseInt(str1) + 1);
       paramMap.put(paramString, str2);
     }
     return str;
   }
 }


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\config\d\b.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
