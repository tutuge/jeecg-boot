package org.jeecg.modules.online.cgform.dConstants;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.system.query.MatchTypeEnum;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.query.QueryRuleEnum;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.system.vo.DictModel;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.system.vo.SysPermissionDataRuleModel;
import org.jeecg.common.util.*;
import org.jeecg.common.util.a.JsonPut;
import org.jeecg.common.util.a.JsonSchema;
import org.jeecg.common.util.a.a1.c;
import org.jeecg.common.util.a.a1.d;
import org.jeecg.common.util.a.a1.e;
import org.jeecg.common.util.a.a1.f;
import org.jeecg.common.util.a.a1.h;
import org.jeecg.common.util.a.a1.*;
import org.jeecg.common.util.a.aTable;
import org.jeecg.common.util.a.b1;
import org.jeecg.config.mybatis.MybatisPlusSaasConfig;
import org.jeecg.modules.online.cgform.entity.*;
import org.jeecg.modules.online.cgform.mapper.OnlCgformHeadMapper;
import org.jeecg.modules.online.cgform.model.i;
import org.jeecg.modules.online.config.bAttribute.eTableConfig;
import org.jeecg.modules.online.config.dUtil.eDbTableHandleI;
import org.jeecg.modules.online.config.exception.AException;
import org.jeecgframework.poi.excel.entity.params.ExcelExportEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URLDecoder;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class bConstant {
    private static final Logger aS = LoggerFactory.getLogger(bConstant.class);

    public static final String a = "SELECT ";

    public static final String b = " FROM ";

    public static final String c = " JOIN ";

    public static final String d = " ON ";

    public static final String e = " AND ";

    public static final String f = " like ";

    public static final String g = " COUNT(*) ";

    public static final String h = " where 1=1  ";

    public static final String i = " where  ";

    public static final String j = " ORDER BY ";

    public static final String k = "asc";

    public static final String l = "desc";

    public static final String m = "=";

    public static final String n = "!=";

    public static final String o = ">=";

    public static final String p = ">";

    public static final String q = "<=";

    public static final String r = "<";

    public static final String s = " or ";

    public static final String t = "jeecg_row_key";

    public static final String u = "Y";

    public static final String v = "$";

    public static final String w = "CREATE_TIME";

    public static final String x = "CREATE_BY";

    public static final String y = "UPDATE_TIME";

    public static final String z = "UPDATE_BY";

    public static final String A = "SYS_ORG_CODE";

    public static final int B = 2;

    public static final String C = "'";

    public static final String D = "N";

    public static final String E = ",";

    public static final String F = "single";

    public static final String G = "id";

    public static final String H = "bpm_status";

    public static final String I = "1";

    public static final String J = "force";

    public static final String K = "normal";

    public static final String L = "switch";

    public static final String M = "popup";

    public static final String N = "sel_search";

    public static final String O = "image";

    public static final String P = "file";

    public static final String Q = "sel_tree";

    public static final String R = "cat_tree";

    public static final String S = "link_down";

    public static final String T = "SYS_USER";

    public static final String U = "REALNAME";

    public static final String V = "USERNAME";

    public static final String W = "SYS_DEPART";

    public static final String X = "DEPART_NAME";

    public static final String Y = "ID";

    public static final String Z = "SYS_CATEGORY";

    public static final String aa = "NAME";

    public static final String ab = "CODE";

    public static final String ac = "ID";

    public static final String ad = "PID";

    public static final String ae = "HAS_CHILD";

    public static final String af = "sel_search";

    public static final String ag = "link_table";

    public static final String ah = "link_table_field";

    public static final String ai = "sub-table-design_";

    public static final String aj = "sub-table-one2one_";

    public static final String ak = "import";

    public static final String al = "export";

    public static final String am = "query";

    public static final String an = "form";

    public static final String ao = "list";

    public static final String ap = "1";

    public static final String aq = "start";

    public static final String ar = "erp";

    public static final String as = "innerTable";

    public static final String at = "exportSingleOnly";

    public static final String au = "isSingleTableImport";

    public static final String av = "validateStatus";

    public static final String aw = "1";

    public static final String ax = "foreignKeys";

    public static final int ay = 1;

    public static final int az = 2;

    public static final int aA = 0;

    public static final int aB = 1;

    public static final int aC = 1;

    public static final String aD = "1";

    public static final Integer aE = Integer.valueOf(2);

    public static final String aF = "1";

    public static final String aG = "id";

    public static final String aH = "center";

    public static final String aI = "modules/bpm/task/form/OnlineFormDetail";

    public static final String aJ = "check/onlineForm/detail";

    public static final String aK = "onl_";

    public static final String aL = "jeecg_submit_form_and_flow";

    public static final String aM = "joinQuery";

    public static final String aN = "properties";

    public static final String aO = "title";

    public static final String aP = "view";

    public static final String aQ = "table";

    public static final String aR = "searchFieldList";

    private static final String aT = "beforeAdd,beforeEdit,afterAdd,afterEdit,beforeDelete,afterDelete,mounted,created";

    private static String aU;

    public static boolean a7(OnlCgformHead paramOnlCgformHead) {
        if (paramOnlCgformHead != null && aE.equals(paramOnlCgformHead.getTableType())) {
            String str1 = paramOnlCgformHead.getThemeTemplate();
            if ("erp".equals(str1) || "innerTable".equals(str1) || "Y".equals(paramOnlCgformHead.getIsTree()))
                return false;
            String str2 = paramOnlCgformHead.getExtConfigJson();
            if (str2 != null && !"".equals(str2)) {
                JSONObject jSONObject = JSON.parseObject(str2);
                if (jSONObject.containsKey("joinQuery") && 1 == jSONObject.getInteger("joinQuery").intValue())
                    return true;
            }
        }
        return false;
    }

    public static void a7(String paramString, List<OnlCgformField> paramList, StringBuffer paramStringBuffer) {
        if (paramList == null || paramList.size() == 0) {
            paramStringBuffer.append("SELECT id");
        } else {
            paramStringBuffer.append("SELECT ");
            int i = paramList.size();
            boolean bool = false;
            for (byte b1 = 0; b1 < i; b1++) {
                OnlCgformField onlCgformField = paramList.get(b1);
                if (org.jeecg.modules.online.cgform.b1.bConstant.b.equals(onlCgformField.getDbIsPersist())) {
                    if ("id".equals(onlCgformField.getDbFieldName()))
                        bool = true;
                    if ("cat_tree".equals(onlCgformField.getFieldShowType()) && oConvertUtils.isNotEmpty(onlCgformField.getDictText()))
                        paramStringBuffer.append(onlCgformField.getDictText() + ",");
                    if (b1 == i - 1) {
                        paramStringBuffer.append(onlCgformField.getDbFieldName() + " ");
                    } else {
                        paramStringBuffer.append(onlCgformField.getDbFieldName() + ",");
                    }
                }
            }
            String str = paramStringBuffer.substring(paramStringBuffer.length() - 1);
            if (",".equals(str))
                paramStringBuffer.deleteCharAt(paramStringBuffer.length() - 1);
            if (!bool)
                paramStringBuffer.append(",id");
        }
        paramStringBuffer.append(" FROM " + f(paramString));
    }

    public static String a7(String paramString) {
        return " to_date('" + paramString + "','yyyy-MM-dd HH24:mi:ss')";
    }

    public static String b(String paramString) {
        return " to_date('" + paramString + "','yyyy-MM-dd')";
    }

    public static boolean c(String paramString) {
        if ("list".equals(paramString))
            return true;
        if ("radio".equals(paramString))
            return true;
        if ("checkbox".equals(paramString))
            return true;
        if ("list_multi".equals(paramString))
            return true;
        return false;
    }

    public static boolean a7(OnlCgformField paramOnlCgformField) {
        if (oConvertUtils.isNotEmpty(paramOnlCgformField.getMainField()) && oConvertUtils.isNotEmpty(paramOnlCgformField.getMainTable())) {
            String str = paramOnlCgformField.getFieldExtendJson();
            if (oConvertUtils.isNotEmpty(str) && str.indexOf("textField") > 0) {
                paramOnlCgformField.setDictTable(paramOnlCgformField.getMainTable());
                paramOnlCgformField.setDictField(paramOnlCgformField.getMainField());
                paramOnlCgformField.setFieldShowType("sel_search");
                JSONObject jSONObject = JSON.parseObject(str);
                paramOnlCgformField.setDictText(jSONObject.getString("textField"));
                return true;
            }
        }
        return false;
    }

    public static void a7(StringBuilder paramStringBuilder, String paramString, JSONObject paramJSONObject1, MatchTypeEnum paramMatchTypeEnum, JSONObject paramJSONObject2, boolean paramBoolean) {
        if (!paramBoolean)
            paramStringBuilder.append(" ").append(paramMatchTypeEnum.getValue()).append(" ");
        String str1 = paramJSONObject1.getString("type");
        String str2 = paramJSONObject1.getString("val");
        String str3 = b(str1, str2);
        QueryRuleEnum queryRuleEnum = QueryRuleEnum.getByValue(paramJSONObject1.getString("rule"));
        if (queryRuleEnum == null)
            queryRuleEnum = QueryRuleEnum.EQ;
        if (paramJSONObject2 != null) {
            String str4 = paramJSONObject2.getString("subTableName");
            String str5 = paramJSONObject2.getString("subField");
            String str6 = paramJSONObject2.getString("mainTable");
            String str7 = paramJSONObject2.getString("mainField");
            paramStringBuilder.append("(").append(str7).append(" IN (SELECT ").append(str5).append(" FROM ").append(str4).append(" WHERE ");
            if ("popup".equals(str1)) {
                paramStringBuilder.append(c(paramString, str2));
            } else {
                paramStringBuilder.append(paramString);
                a7(paramStringBuilder, queryRuleEnum, str2, str3, str1);
            }
            paramStringBuilder.append("))");
        } else if ("popup".equals(str1)) {
            paramStringBuilder.append(c(paramString, str2));
        } else {
            paramStringBuilder.append(paramString);
            a7(paramStringBuilder, queryRuleEnum, str2, str3, str1);
        }
    }

    private static void a7(StringBuilder paramStringBuilder, QueryRuleEnum paramQueryRuleEnum, String paramString1, String paramString2, String paramString3) {
        String[] arrayOfString;
        byte b1;
        if ("date".equals(paramString3) &&
                "ORACLE".equalsIgnoreCase(getDatabseType())) {
            paramString2 = paramString2.replace("'", "");
            if (paramString2.length() == 10) {
                paramString2 = b(paramString2);
            } else {
                paramString2 = a7(paramString2);
            }
        }
        switch (paramQueryRuleEnum.ordinal()) {
            case 1:
                paramStringBuilder.append(">").append(paramString2);
                return;
            case 2:
                paramStringBuilder.append(">=").append(paramString2);
                return;
            case 3:
                paramStringBuilder.append("<").append(paramString2);
                return;
            case 4:
                paramStringBuilder.append("<=").append(paramString2);
                return;
            case 5:
                paramStringBuilder.append("!=").append(paramString2);
                return;
            case 6:
                paramStringBuilder.append(" IN (");
                arrayOfString = paramString1.split(",");
                for (b1 = 0; b1 < arrayOfString.length; b1++) {
                    String str = arrayOfString[b1];
                    if (StringUtils.isNotBlank(str)) {
                        String str1 = b(paramString3, str);
                        paramStringBuilder.append(str1);
                        if (b1 < arrayOfString.length - 1)
                            paramStringBuilder.append(",");
                    }
                }
                paramStringBuilder.append(")");
                return;
            case 7:
                paramStringBuilder.append(" like ").append("N").append("'").append("%").append(paramString1).append("%").append("'");
                return;
            case 8:
                paramStringBuilder.append(" like ").append("N").append("'").append("%").append(paramString1).append("'");
                return;
            case 9:
                paramStringBuilder.append(" like ").append("N").append("'").append(paramString1).append("%").append("'");
                return;
        }
        paramStringBuilder.append("=").append(paramString2);
    }

    private static String b(String paramString1, String paramString2) {
        if ("int".equals(paramString1) || "number".equals(paramString1))
            return paramString2;
        if ("date".equals(paramString1))
            return "'" + paramString2 + "'";
        if ("SQLSERVER".equals(getDatabseType()))
            return "N'" + paramString2 + "'";
        return "'" + paramString2 + "'";
    }

    public static Map<String, Object> a7(HttpServletRequest paramHttpServletRequest) {
        Map map = paramHttpServletRequest.getParameterMap();
        HashMap<Object, Object> hashMap = new HashMap<>(5);
        Iterator<Map.Entry> iterator = map.entrySet().iterator();
        String str1 = "";
        String str2 = "";
        Object object = null;
        while (iterator.hasNext()) {
            Map.Entry entry = iterator.next();
            str1 = (String) entry.getKey();
            object = entry.getValue();
            if ("_t".equals(str1) || null == object) {
                str2 = "";
            } else if (object instanceof String[]) {
                String[] arrayOfString = (String[]) object;
                for (byte b1 = 0; b1 < arrayOfString.length; b1++)
                    str2 = arrayOfString[b1] + ",";
                str2 = str2.substring(0, str2.length() - 1);
            } else {
                str2 = object.toString();
            }
            hashMap.put(str1, str2);
        }
        return (Map) hashMap;
    }

    public static boolean a7(String paramString, List<OnlCgformField> paramList) {
        for (OnlCgformField onlCgformField : paramList) {
            if (paramString.equals(onlCgformField.getDbFieldName()))
                return true;
        }
        return false;
    }

    public static JSONObject a7(List<OnlCgformField> paramList, List<String> paramList1, i parami) {
        JSONObject jSONObject = new JSONObject();
        ArrayList<String> arrayList1 = new ArrayList();
        ArrayList<b1> arrayList = new ArrayList();
        ISysBaseAPI iSysBaseAPI = SpringContextUtils.getBean(ISysBaseAPI.class);
        OnlCgformHeadMapper onlCgformHeadMapper = (OnlCgformHeadMapper) SpringContextUtils.getBean(OnlCgformHeadMapper.class);
        ArrayList<String> arrayList2 = new ArrayList();
        for (OnlCgformField onlCgformField : paramList) {
            f f;
            String str1 = onlCgformField.getDbFieldName();
            if ("id".equals(str1))
                continue;
            if (arrayList2.contains(str1))
                continue;
            String str2 = onlCgformField.getDbFieldTxt();
            if ("1".equals(onlCgformField.getFieldMustInput()))
                arrayList1.add(str1);
            String str3 = onlCgformField.getFieldShowType();
            PropertyJson g = null;
            if ("switch".equals(str3)) {
                g = new PropertyJson(str1, str2, onlCgformField.getFieldExtendJson());
            } else if (c(str3) || "link_table_field".equals(str3)) {
                aDict a = new aDict(str1, str3, str2, onlCgformField.getDictTable(), onlCgformField.getDictField(), onlCgformField.getDictText());
                if (gType.a(onlCgformField.getDbType()))
                    a.setType("number");
            } else if ("sel_search".equals(str3) || a7(onlCgformField)) {
                aDict a = new aDict(str1, str2, onlCgformField.getDictTable(), onlCgformField.getDictField(), onlCgformField.getDictText());
            } else if ("link_table".equals(str3)) {
                aDict a = new aDict(str1, str2, onlCgformField.getDictTable(), onlCgformField.getDictField(), onlCgformField.getDictText());
                a.setView("link_table");
            } else if (gType.a(onlCgformField.getDbType())) {
                d d2 = new d(str1, str2, "number");
                if (org.jeecg.modules.online.cgform.enums.b.j.getType().equals(onlCgformField.getFieldValidType()))
                    d2.setPattern(org.jeecg.modules.online.cgform.enums.b.j.getPattern());
                d d1 = d2;
            } else if ("popup".equals(str3)) {
                e e2 = new e(str1, str2, onlCgformField.getDictTable(), onlCgformField.getDictText(), onlCgformField.getDictField());
                String str4 = onlCgformField.getDictText();
                if (str4 != null && !"".equals(str4)) {
                    String[] arrayOfString = str4.split(",");
                    for (String str : arrayOfString) {
                        if (!a7(str, paramList)) {
                            bHidden b1 = new bHidden(str, str);
                            b1.setOrder(onlCgformField.getOrderNum());
                            arrayList.add(b1);
                        }
                    }
                }
                String str5 = onlCgformField.getFieldExtendJson();
                if (str5 != null && !"".equals(str5)) {
                    JSONObject jSONObject1 = JSONObject.parseObject(str5);
                    if (jSONObject1.containsKey("popupMulti"))
                        e2.setPopupMulti(jSONObject1.getBoolean("popupMulti"));
                }
                e e1 = e2;
            } else if ("link_down".equals(str3)) {
                c c2 = new c(str1, str2, onlCgformField.getDictTable());
                a5(c2, paramList, arrayList2);
                c c1 = c2;
            } else if ("sel_tree".equals(str3)) {
                String str4 = onlCgformField.getDictText();
                String[] arrayOfString = str4.split(",");
                String str5 = onlCgformField.getDictTable() + "," + arrayOfString[2] + "," + arrayOfString[0];
                h h2 = new h(str1, str2, str5, arrayOfString[1], onlCgformField.getDictField());
                if (arrayOfString.length > 3)
                    h2.setHasChildField(arrayOfString[3]);
                h h1 = h2;
            } else if ("cat_tree".equals(str3)) {
                String str4 = onlCgformField.getDictText();
                String str5 = onlCgformField.getDictField();
                String str6 = "0";
                if (oConvertUtils.isNotEmpty(str5) && !"0".equals(str5))
                    str6 = onlCgformHeadMapper.queryCategoryIdByCode(str5);
                if (oConvertUtils.isEmpty(str4)) {
                    h h = new h(str1, str2, str6);
                } else {
                    h h = new h(str1, str2, str6, str4);
                    bHidden b1 = new bHidden(str4, str4);
                    arrayList.add(b1);
                }
            } else if (parami != null && str1.equals(parami.getFieldName())) {
                String str = parami.getTableName() + "," + parami.getTextField() + "," + parami.getCodeField();
                h h2 = new h(str1, str2, str, parami.getPidField(), parami.getPidValue());
                h2.setHasChildField(parami.getHsaChildField());
                h2.setPidComponent(Integer.valueOf(1));
                h h1 = h2;
            } else {
                f f1 = new f(str1, str2, str3, onlCgformField.getDbLength());
                if (oConvertUtils.isNotEmpty(onlCgformField.getFieldValidType())) {
                    org.jeecg.modules.online.cgform.enums.b b1 = org.jeecg.modules.online.cgform.enums.b.b(onlCgformField.getFieldValidType());
                    String str = a6("validateError", onlCgformField.getFieldExtendJson());
                    if (b1 != null) {
                        if (org.jeecg.modules.online.cgform.enums.b.k == b1) {
                            arrayList1.add(str1);
                        } else {
                            f1.setPattern(b1.getPattern());
                            if (oConvertUtils.isEmpty(str)) {
                                f1.setErrorInfo(b1.getMsg());
                            } else {
                                f1.setErrorInfo(str);
                            }
                        }
                    } else {
                        f1.setPattern(onlCgformField.getFieldValidType());
                        if (oConvertUtils.isEmpty(str)) {
                            f1.setErrorInfo("输入的值不合法");
                        } else {
                            f1.setErrorInfo(str);
                        }
                    }
                }
                f = f1;
            }
            if (onlCgformField.getIsReadOnly() == 1 || (paramList1 != null && paramList1.contains(str1))) {
                f.setDisabled(true);
            }
            f.setOrder(onlCgformField.getOrderNum());
            f.setDefVal(onlCgformField.getFieldDefaultValue());
            f.setFieldExtendJson(onlCgformField.getFieldExtendJson());
            f.setDbPointLength(onlCgformField.getDbPointLength());
            f.setMode(onlCgformField.getQueryMode());
            arrayList.add(f);
        }
        if (arrayList1.size() > 0) {
            JsonSchema c = new JsonSchema(arrayList1);
            jSONObject = JsonPut.a(c, arrayList);
        } else {
            JsonSchema c = new JsonSchema();
            jSONObject = JsonPut.a(c, arrayList);
        }
        return jSONObject;
    }

    public static String a6(String paramString1, String paramString2) {
        String str = "";
        if (paramString2 != null && !"".equals(paramString2)) {
            JSONObject jSONObject = JSONObject.parseObject(paramString2);
            if (jSONObject.containsKey(paramString1))
                str = jSONObject.getString(paramString1);
        }
        return str;
    }

    public static JSONObject b(String paramString, List<OnlCgformField> paramList) {
        JSONObject jSONObject = new JSONObject();
        ArrayList<String> arrayList = new ArrayList<>();
        List<b1> arrayList1 = new ArrayList<>();
        ISysBaseAPI iSysBaseAPI = SpringContextUtils.getBean(ISysBaseAPI.class);
        for (OnlCgformField onlCgformField : paramList) {
            f f = null;
            String str1 = onlCgformField.getDbFieldName();
            if ("id".equals(str1))
                continue;
            String str2 = onlCgformField.getDbFieldTxt();
            if ("1".equals(onlCgformField.getFieldMustInput()))
                arrayList.add(str1);
            String str3 = onlCgformField.getFieldShowType();
            String str4 = onlCgformField.getDictField();
            d d = null;
            if (gType.a(onlCgformField.getDbType())) {
                d = new d(str1, str2, "number");
            } else if (c(str3)) {
                List list = iSysBaseAPI.queryDictItemsByCode(str4);
                f = new f(str1, str2, str3, onlCgformField.getDbLength(), list);
            } else {
                f = new f(str1, str2, str3, onlCgformField.getDbLength());
            }
            f.setOrder(onlCgformField.getOrderNum());
            arrayList1.add(f);
        }
        jSONObject = JsonPut.a(paramString, arrayList, arrayList1);
        return jSONObject;
    }

    public static Set<String> a7(List<OnlCgformField> paramList) {
        HashSet<String> hashSet = new HashSet();
        for (OnlCgformField onlCgformField : paramList) {
            if ("popup".equals(onlCgformField.getFieldShowType())) {
                String str = onlCgformField.getDictText();
                if (str != null && !"".equals(str))
                    hashSet.addAll(Arrays.stream(str.split(",")).collect(Collectors.toSet()));
            }
            if ("cat_tree".equals(onlCgformField.getFieldShowType())) {
                String str = onlCgformField.getDictText();
                if (oConvertUtils.isNotEmpty(str))
                    hashSet.add(str);
            }
        }
        for (OnlCgformField onlCgformField : paramList) {
            String str = onlCgformField.getDbFieldName();
            if (onlCgformField.getIsShowForm() == 1 && hashSet.contains(str))
                hashSet.remove(str);
        }
        return hashSet;
    }

    public static Map<String, Object> a7(String paramString, List<OnlCgformField> paramList, JSONObject paramJSONObject) {
        StringBuffer stringBuffer1 = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        String str1 = "";
        try {
            str1 = eDbTableHandleI.getDatabaseType();
        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
        } catch (AException a) {
            a.printStackTrace();
        }
        HashMap<Object, Object> hashMap = new HashMap<>(5);
        boolean bool = false;
        String str2 = null;
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        if (loginUser == null)
            throw new JeecgBootException("online保存表单数据异常:系统未找到当前登陆用户信息");
        Set<String> set = a7(paramList);
        String str3 = "tenant_id";
        String str4 = f(paramString);
        boolean bool1 = j(str4);
        for (OnlCgformField onlCgformField : paramList) {
            if (!org.jeecg.modules.online.cgform.b1.bConstant.b.equals(onlCgformField.getDbIsPersist()))
                continue;
            String str6 = onlCgformField.getDbFieldName();
            if (null == str6)
                continue;
            if ("id".equals(str6.toLowerCase())) {
                bool = true;
                str2 = paramJSONObject.getString(str6);
                continue;
            }
            if (bool1 && str3.equalsIgnoreCase(str6))
                continue;
            a7(onlCgformField, loginUser, paramJSONObject, new String[]{"CREATE_BY", "CREATE_TIME", "SYS_ORG_CODE"});
            if ("bpm_status".equals(str6.toLowerCase())) {
                stringBuffer1.append("," + str6);
                stringBuffer2.append(",'1'");
                continue;
            }
            if (set.contains(str6)) {
                stringBuffer1.append("," + str6);
                String str = gType.a(str1, onlCgformField, paramJSONObject, (Map) hashMap);
                stringBuffer2.append("," + str);
                continue;
            }
            if (onlCgformField.getIsShowForm().intValue() != 1 && oConvertUtils.isEmpty(onlCgformField.getMainField()) &&
                    oConvertUtils.isEmpty(onlCgformField.getDbDefaultVal()))
                continue;
            if (oConvertUtils.isEmpty(paramJSONObject.get(str6))) {
                if (oConvertUtils.isEmpty(onlCgformField.getDbDefaultVal()))
                    continue;
                paramJSONObject.put(str6, onlCgformField.getDbDefaultVal());
            }
            if ("".equals(paramJSONObject.get(str6))) {
                String str = onlCgformField.getDbType();
                if (gType.a(str) || gType.b(str))
                    continue;
            }
            stringBuffer1.append("," + str6);
            String str7 = gType.a(str1, onlCgformField, paramJSONObject, (Map) hashMap);
            stringBuffer2.append("," + str7);
        }
        if (bool) {
            if (oConvertUtils.isEmpty(str2))
                str2 = a7();
        } else {
            str2 = a7();
        }
        if (bool1) {
            stringBuffer1.append("," + str3);
            stringBuffer2.append(",#{" + str3 + "}");
            String str = SpringContextUtils.getHttpServletRequest().getHeader("X-Tenant-Id");
            hashMap.put(str3, str);
        }
        String str5 = "insert into " + str4 + "(" + "id" + stringBuffer1.toString() + ") values(#{id,jdbcType=VARCHAR}" + stringBuffer2.toString() + ")";
        hashMap.put("execute_sql_string", str5);
        hashMap.put("id", str2);
        return (Map) hashMap;
    }

    public static Map<String, Object> b(String paramString, List<OnlCgformField> paramList, JSONObject paramJSONObject) {
        StringBuffer stringBuffer = new StringBuffer();
        HashMap<Object, Object> hashMap = new HashMap<>(5);
        String str1 = "";
        try {
            str1 = eDbTableHandleI.getDatabaseType();
        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
        } catch (AException a) {
            a.printStackTrace();
        }
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        if (loginUser == null)
            throw new JeecgBootException("online修改表单数据异常:系统未找到当前登陆用户信息");
        Set<String> set = a7(paramList);
        for (OnlCgformField onlCgformField : paramList) {
            if (!org.jeecg.modules.online.cgform.b1.bConstant.b.equals(onlCgformField.getDbIsPersist()))
                continue;
            String str4 = onlCgformField.getDbFieldName();
            if (null == str4)
                continue;
            a7(onlCgformField, loginUser, paramJSONObject, new String[]{"UPDATE_BY", "UPDATE_TIME"});
            if (set.contains(str4))
                if (paramJSONObject.get(str4) != null && !"".equals(paramJSONObject.getString(str4))) {
                    String str = gType.a(str1, onlCgformField, paramJSONObject, (Map) hashMap);
                    stringBuffer.append(str4 + "=" + str + ",");
                    continue;
                }
            if (onlCgformField.getIsShowForm().intValue() != 1)
                continue;
            if ("id".equals(str4))
                continue;
            if ("".equals(paramJSONObject.get(str4))) {
                String str = onlCgformField.getDbType();
                if (gType.a(str) || gType.b(str))
                    continue;
            }
            if (oConvertUtils.isNotEmpty(onlCgformField.getMainTable()) && oConvertUtils.isNotEmpty(onlCgformField.getMainField()))
                if (oConvertUtils.isEmpty(paramJSONObject.get(str4)))
                    continue;
            String str5 = gType.a(str1, onlCgformField, paramJSONObject, (Map) hashMap);
            stringBuffer.append(str4 + "=" + str5 + ",");
        }
        String str2 = stringBuffer.toString();
        if (str2.endsWith(","))
            str2 = str2.substring(0, str2.length() - 1);
        String str3 = "update " + f(paramString) + " set " + str2 + " where  " + "id" + "=" + "'" + paramJSONObject.getString("id") + "'";
        hashMap.put("execute_sql_string", str3);
        hashMap.put("id", paramJSONObject.getString("id"));
        return (Map) hashMap;
    }

    public static String a7(String paramString1, List<OnlCgformField> paramList, String paramString2) {
        return a7(paramString1, paramList, "id", paramString2);
    }

    public static String a7(String paramString1, List<OnlCgformField> paramList, String paramString2, String paramString3) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("SELECT ");
        int i = paramList.size();
        boolean bool = false;
        for (byte b1 = 0; b1 < i; b1++) {
            OnlCgformField onlCgformField = paramList.get(b1);
            if (org.jeecg.modules.online.cgform.b1.bConstant.b.equals(onlCgformField.getDbIsPersist())) {
                String str1 = onlCgformField.getDbFieldName();
                if ("id".equals(str1))
                    bool = true;
                stringBuffer.append(str1);
                if (i > b1 + 1)
                    stringBuffer.append(",");
            }
        }
        String str = stringBuffer.substring(stringBuffer.length() - 1);
        if (",".equals(str))
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        if (!bool)
            stringBuffer.append(",id");
        stringBuffer.append(" FROM " + f(paramString1) + " where 1=1  " + " AND " + paramString2 + "=" + "'" + paramString3 + "'");
        return stringBuffer.toString();
    }

    public static void a7(OnlCgformField paramOnlCgformField, LoginUser paramLoginUser, JSONObject paramJSONObject, String... paramVarArgs) {
        String str = paramOnlCgformField.getDbFieldName();
        boolean bool = false;
        for (String str1 : paramVarArgs) {
            if (str.toUpperCase().equals(str1)) {
                if (paramOnlCgformField.getIsShowForm().intValue() == 1) {
                    if (paramJSONObject.get(str) == null)
                        bool = true;
                } else {
                    paramOnlCgformField.setIsShowForm(Integer.valueOf(1));
                    bool = true;
                }
                if (bool)
                    switch (str1) {
                        case "CREATE_BY":
                            paramJSONObject.put(str, paramLoginUser.getUsername());
                            break;
                        case "CREATE_TIME":
                            paramOnlCgformField.setFieldShowType("datetime");
                            paramJSONObject.put(str, DateUtils.formatDateTime());
                            break;
                        case "UPDATE_BY":
                            paramJSONObject.put(str, paramLoginUser.getUsername());
                            break;
                        case "UPDATE_TIME":
                            paramOnlCgformField.setFieldShowType("datetime");
                            paramJSONObject.put(str, DateUtils.formatDateTime());
                            break;
                        case "SYS_ORG_CODE":
                            paramJSONObject.put(str, paramLoginUser.getOrgCode());
                            break;
                    }
                break;
            }
        }
    }

    public static boolean a7(Object paramObject1, Object paramObject2) {
        if (oConvertUtils.isEmpty(paramObject1) && oConvertUtils.isEmpty(paramObject2))
            return true;
        if (oConvertUtils.isNotEmpty(paramObject1) && paramObject1.equals(paramObject2))
            return true;
        return false;
    }

    public static boolean a7(OnlCgformField paramOnlCgformField1, OnlCgformField paramOnlCgformField2) {
        if (!org.jeecg.modules.online.cgform.b1.bConstant.b.equals(paramOnlCgformField2.getDbIsPersist()) && !org.jeecg.modules.online.cgform.b1.bConstant.b.equals(paramOnlCgformField1.getDbIsPersist()))
            return false;
        if (!a7(paramOnlCgformField1.getDbFieldName(), paramOnlCgformField2.getDbFieldName()) ||
                !a7(paramOnlCgformField1.getDbFieldTxt(), paramOnlCgformField2.getDbFieldTxt()) ||
                !a7(paramOnlCgformField1.getDbLength(), paramOnlCgformField2.getDbLength()) ||
                !a7(paramOnlCgformField1.getDbPointLength(), paramOnlCgformField2.getDbPointLength()) ||
                !a7(paramOnlCgformField1.getDbType(), paramOnlCgformField2.getDbType()) ||
                !a7(paramOnlCgformField1.getDbIsNull(), paramOnlCgformField2.getDbIsNull()) ||
                !a7(paramOnlCgformField1.getDbIsPersist(), paramOnlCgformField2.getDbIsPersist()) ||

                !a7(paramOnlCgformField1.getDbIsKey(), paramOnlCgformField2.getDbIsKey()) ||

                !a7(paramOnlCgformField1.getDbDefaultVal(), paramOnlCgformField2.getDbDefaultVal()))
            return true;
        return false;
    }

    public static boolean a7(OnlCgformIndex paramOnlCgformIndex1, OnlCgformIndex paramOnlCgformIndex2) {
        if (!a7(paramOnlCgformIndex1.getIndexName(), paramOnlCgformIndex2.getIndexName()) ||
                !a7(paramOnlCgformIndex1.getIndexField(), paramOnlCgformIndex2.getIndexField()) ||
                !a7(paramOnlCgformIndex1.getIndexType(), paramOnlCgformIndex2.getIndexType()))
            return true;
        return false;
    }

    public static boolean a7(OnlCgformHead paramOnlCgformHead1, OnlCgformHead paramOnlCgformHead2) {
        if (!a7(paramOnlCgformHead1.getTableName(), paramOnlCgformHead2.getTableName()) || !a7(paramOnlCgformHead1.getTableTxt(), paramOnlCgformHead2.getTableTxt()))
            return true;
        return false;
    }

    public static String a7(String paramString, List<OnlCgformField> paramList, Map<String, Object> paramMap) {
        StringBuffer stringBuffer1 = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        String str1 = paramString + "@";
        HashMap<Object, Object> hashMap = new HashMap<>(5);
        for (String str : paramMap.keySet()) {
            if (str.startsWith(str1)) {
                hashMap.put(str.replace(str1, ""), paramMap.get(str));
                continue;
            }
            hashMap.put(str, paramMap.get(str));
        }
        for (OnlCgformField onlCgformField : paramList) {
            String str3 = onlCgformField.getDbFieldName();
            String str4 = onlCgformField.getDbType();
            if (onlCgformField.getIsShowList().intValue() == 1)
                stringBuffer2.append("," + str3);
            if (oConvertUtils.isNotEmpty(onlCgformField.getMainField())) {
                boolean bool = !gType.a(str4) ? true : false;
                String str = QueryGenerator.getSingleQueryConditionSql(str3, "", hashMap.get(str3), bool);
                if (!"".equals(str))
                    stringBuffer1.append(" AND " + str);
            }
            if (onlCgformField.getIsQuery().intValue() == 1) {
                if ("single".equals(onlCgformField.getQueryMode())) {
                    if (hashMap.get(str3) == null)
                        continue;
                    boolean bool = !gType.a(str4) ? true : false;
                    String str = QueryGenerator.getSingleQueryConditionSql(str3, "", hashMap.get(str3), bool);
                    if ("".equals(str))
                        continue;
                    stringBuffer1.append(" AND " + str);
                    continue;
                }
                Object object1 = hashMap.get(str3 + "_begin");
                if (object1 != null) {
                    stringBuffer1.append(" AND " + str3 + ">=");
                    if (gType.a(str4)) {
                        stringBuffer1.append(object1.toString());
                    } else {
                        stringBuffer1.append("'" + object1.toString() + "'");
                    }
                }
                Object object2 = hashMap.get(str3 + "_end");
                if (object2 != null) {
                    stringBuffer1.append(" AND " + str3 + "<=");
                    if (gType.a(str4)) {
                        stringBuffer1.append(object2.toString());
                        continue;
                    }
                    stringBuffer1.append("'" + object2.toString() + "'");
                }
            }
        }
        String str2 = b(paramString, paramList, (Map) hashMap);
        return "SELECT id" + stringBuffer2.toString() + " FROM " + f(paramString) + " where 1=1  " + stringBuffer1.toString() + str2;
    }

    public static String b(String paramString, List<OnlCgformField> paramList, Map<String, Object> paramMap) {
        boolean bool = true;
        JSONArray jSONArray = b(paramMap);
        MatchTypeEnum matchTypeEnum = c(paramMap);
        StringBuilder stringBuilder = new StringBuilder();
        if (jSONArray != null)
            for (byte b1 = 0; b1 < jSONArray.size(); b1++) {
                JSONObject jSONObject = jSONArray.getJSONObject(b1);
                String str1 = jSONObject.getString("field");
                String[] arrayOfString = str1.split(",");
                if (arrayOfString.length != 1) {
                    String str2 = arrayOfString[1];
                    if (paramString.equalsIgnoreCase(arrayOfString[0]) &&
                            c(str2, paramList)) {
                        a7(stringBuilder, str2, jSONObject, matchTypeEnum, null, bool);
                        bool = false;
                    }
                }
            }
        String str = stringBuilder.toString();
        if (str == null || "".equals(str))
            return "";
        return " AND (" + str + ") ";
    }

    public static boolean c(String paramString, List<OnlCgformField> paramList) {
        boolean bool = false;
        for (OnlCgformField onlCgformField : paramList) {
            if (oConvertUtils.camelToUnderline(paramString).equalsIgnoreCase(onlCgformField.getDbFieldName())) {
                bool = true;
                break;
            }
        }
        return bool;
    }

    @Deprecated
    public static List<ExcelExportEntity> a7(List<OnlCgformField> paramList, String paramString) {
        ArrayList<ExcelExportEntity> arrayList = new ArrayList<>();
        for (byte b1 = 0; b1 < paramList.size(); b1++) {
            if (null == paramString || !paramString.equals((paramList.get(b1)).getDbFieldName()))
                if ((paramList.get(b1)).getIsShowList() == 1) {
                    ExcelExportEntity excelExportEntity = new ExcelExportEntity((paramList.get(b1)).getDbFieldTxt(), (paramList.get(b1)).getDbFieldName());
                    byte b2 = (byte) (((paramList.get(b1)).getDbLength() == 0) ? 12
                            : (((paramList.get(b1)).getDbLength() > 30) ? 30
                            : (paramList.get(b1)).getDbLength()));
                    if ("date".equals((paramList.get(b1)).getFieldShowType())) {
                        excelExportEntity.setFormat("yyyy-MM-dd");
                    } else if ("datetime".equals((paramList.get(b1)).getFieldShowType())) {
                        excelExportEntity.setFormat("yyyy-MM-dd HH:mm:ss");
                    }
                    if (b2 < 10)
                        b2 = 10;
                    excelExportEntity.setWidth(b2);
                    arrayList.add(excelExportEntity);
                }
        }
        return arrayList;
    }

    public static boolean a7(OnlCgformEnhanceJava paramOnlCgformEnhanceJava) {
        String str1 = paramOnlCgformEnhanceJava.getCgJavaType();
        String str2 = paramOnlCgformEnhanceJava.getCgJavaValue();
        if (oConvertUtils.isNotEmpty(str2))
            try {
                if ("class".equals(str1)) {
                    Class<?> clazz = Class.forName(str2);
                    if (clazz == null || clazz.newInstance() == null)
                        return false;
                }
                if ("spring".equals(str1)) {
                    Object object = SpringContextUtils.getBean(str2);
                    if (object == null)
                        return false;
                }
            } catch (Exception exception) {
                aS.error(exception.getMessage(), exception);
                return false;
            }
        return true;
    }

    public static void b(List<String> paramList) {
        Collections.sort(paramList, new Comparator<String>() {
            public int a(String param1String1, String param1String2) {
                if (param1String1 == null || param1String2 == null)
                    return -1;
                if (param1String1.compareTo(param1String2) > 0)
                    return 1;
                if (param1String1.compareTo(param1String2) < 0)
                    return -1;
                if (param1String1.compareTo(param1String2) == 0)
                    return 0;
                return 0;
            }
        });
    }

    public static void c(List<String> paramList) {
        Collections.sort(paramList, new Comparator<String>() {
            public int a(String param1String1, String param1String2) {
                if (param1String1 == null || param1String2 == null)
                    return -1;
                if (param1String1.length() > param1String2.length())
                    return 1;
                if (param1String1.length() < param1String2.length())
                    return -1;
                if (param1String1.compareTo(param1String2) > 0)
                    return 1;
                if (param1String1.compareTo(param1String2) < 0)
                    return -1;
                if (param1String1.compareTo(param1String2) == 0)
                    return 0;
                return 0;
            }
        });
    }

    private static String a7(String paramString, boolean paramBoolean, QueryRuleEnum paramQueryRuleEnum) {
        if (paramQueryRuleEnum == QueryRuleEnum.IN)
            return a7(paramString, paramBoolean);
        if (paramBoolean)
            return "'" + QueryGenerator.converRuleValue(paramString) + "'";
        return QueryGenerator.converRuleValue(paramString);
    }

    private static String a7(String paramString, boolean paramBoolean) {
        if (paramString == null || paramString.length() == 0)
            return "()";
        paramString = QueryGenerator.converRuleValue(paramString);
        String[] arrayOfString = paramString.split(",");
        ArrayList<String> arrayList = new ArrayList();
        for (String str : arrayOfString) {
            if (str != null && str.length() != 0)
                if (paramBoolean) {
                    arrayList.add("'" + str + "'");
                } else {
                    arrayList.add(str);
                }
        }
        return "(" + StringUtils.join(arrayList, ",") + ")";
    }

    public static void a7(String paramString1, SysPermissionDataRuleModel paramSysPermissionDataRuleModel, String paramString2, String paramString3, StringBuffer paramStringBuffer) {
        QueryRuleEnum queryRuleEnum = QueryRuleEnum.getByValue(paramSysPermissionDataRuleModel.getRuleConditions());
        boolean bool = !gType.a(paramString3) ? true : false;
        String str = a7(paramSysPermissionDataRuleModel.getRuleValue(), bool, queryRuleEnum);
        if (str == null || queryRuleEnum == null)
            return;
        if ("ORACLE".equalsIgnoreCase(paramString1) &&
                "Date".equals(paramString3)) {
            str = str.replace("'", "");
            if (str.length() == 10) {
                str = b(str);
            } else {
                str = a7(str);
            }
        }
        switch (queryRuleEnum.ordinal()) {
            case 1:
                paramStringBuffer.append(" AND " + paramString2 + ">" + str);
                break;
            case 2:
                paramStringBuffer.append(" AND " + paramString2 + ">=" + str);
                break;
            case 3:
                paramStringBuffer.append(" AND " + paramString2 + "<" + str);
                break;
            case 4:
                paramStringBuffer.append(" AND " + paramString2 + "<=" + str);
                break;
            case 10:
                paramStringBuffer.append(" AND " + paramString2 + "=" + str);
                break;
            case 5:
                paramStringBuffer.append(" AND " + paramString2 + " <> " + str);
                break;
            case 6:
                paramStringBuffer.append(" AND " + paramString2 + " IN " + str);
                break;
            case 7:
                paramStringBuffer.append(" AND " + paramString2 + " LIKE '%" + QueryGenerator.trimSingleQuote(str) + "%'");
                break;
            case 8:
                paramStringBuffer.append(" AND " + paramString2 + " LIKE '%" + QueryGenerator.trimSingleQuote(str) + "'");
                break;
            case 9:
                paramStringBuffer.append(" AND " + paramString2 + " LIKE '" + QueryGenerator.trimSingleQuote(str) + "%'");
                break;
        }
    }

    public static String a7(String paramString, JSONObject paramJSONObject) {
        if (paramJSONObject == null)
            return paramString;
        paramString = paramString.replace("#{UUID}", UUIDGenerator.generate());
        Set<String> set = QueryGenerator.getSqlRuleParams(paramString);
        for (String str1 : set) {
            if (paramJSONObject.get(str1.toUpperCase()) == null && paramJSONObject.get(str1.toLowerCase()) == null) {
                String str = JwtUtil.getUserSystemData(str1, null);
                if (str == null) {
                    paramString = paramString.replace("'#{" + str1 + "}'", "NULL");
                    paramString = paramString.replace("#{" + str1 + "}", "NULL");
                    continue;
                }
                paramString = paramString.replace("#{" + str1 + "}", str);
                continue;
            }
            String str2 = null;
            if (paramJSONObject.containsKey(str1.toLowerCase())) {
                str2 = paramJSONObject.getString(str1.toLowerCase());
            } else if (paramJSONObject.containsKey(str1.toUpperCase())) {
                str2 = paramJSONObject.getString(str1.toUpperCase());
            }
            paramString = paramString.replace("#{" + str1 + "}", str2);
        }
        return paramString;
    }

    public static String d(String paramString, List<OnlCgformButton> paramList) {
        paramString = e(paramString, paramList);
        for (String str : "beforeAdd,beforeEdit,afterAdd,afterEdit,beforeDelete,afterDelete,mounted,created".split(",")) {
            if ("beforeAdd,afterAdd,mounted,created".indexOf(str) >= 0) {
                Pattern pattern = Pattern.compile("(" + str + "\\s*\\(\\)\\s*\\{)");
                Matcher matcher = pattern.matcher(paramString);
                if (matcher.find())
                    paramString = paramString.replace(matcher.group(0), str + "(that){const getAction=this._getAction,postAction=this._postAction,deleteAction=this._deleteAction;");
            } else {
                Pattern pattern = Pattern.compile("(" + str + "\\s*\\(row\\)\\s*\\{)");
                Matcher matcher = pattern.matcher(paramString);
                if (matcher.find()) {
                    paramString = paramString.replace(matcher.group(0), str + "(that,row){const getAction=this._getAction,postAction=this._postAction,deleteAction=this._deleteAction;");
                } else {
                    Pattern pattern1 = Pattern.compile("(" + str + "\\s*\\(\\)\\s*\\{)");
                    Matcher matcher1 = pattern1.matcher(paramString);
                    if (matcher1.find())
                        paramString = paramString.replace(matcher1.group(0), str + "(that){const getAction=this._getAction,postAction=this._postAction,deleteAction=this._deleteAction;");
                }
            }
        }
        return d(paramString);
    }

    public static void a7(OnlCgformEnhanceJs paramOnlCgformEnhanceJs, String paramString, List<OnlCgformField> paramList) {
        if (paramOnlCgformEnhanceJs == null || oConvertUtils.isEmpty(paramOnlCgformEnhanceJs.getCgJs()))
            return;
        String str1 = paramOnlCgformEnhanceJs.getCgJs();
        String str2 = "onlChange";
        Pattern pattern = Pattern.compile("(" + paramString + "_" + str2 + "\\s*\\(\\)\\s*\\{)");
        Matcher matcher = pattern.matcher(str1);
        if (matcher.find()) {
            str1 = str1.replace(matcher.group(0), paramString + "_" + str2 + "(){const getAction=this._getAction,postAction=this._postAction,deleteAction=this._deleteAction;");
            for (OnlCgformField onlCgformField : paramList) {
                Pattern pattern1 = Pattern.compile("(" + onlCgformField.getDbFieldName() + "\\s*\\(\\))");
                Matcher matcher1 = pattern1.matcher(str1);
                if (matcher1.find())
                    str1 = str1.replace(matcher1.group(0), onlCgformField.getDbFieldName() + "(that,event)");
            }
        }
        paramOnlCgformEnhanceJs.setCgJs(str1);
    }

    public static void a7(OnlCgformEnhanceJs paramOnlCgformEnhanceJs, String paramString, List<OnlCgformField> paramList, boolean paramBoolean) {
        if (paramOnlCgformEnhanceJs == null || oConvertUtils.isEmpty(paramOnlCgformEnhanceJs.getCgJs()))
            return;
        String str1 = paramOnlCgformEnhanceJs.getCgJs();
        String str2 = "onlChange";
        Pattern pattern = Pattern.compile("([^_]" + str2 + "\\s*\\(\\)\\s*\\{)");
        Matcher matcher = pattern.matcher(str1);
        if (matcher.find()) {
            str1 = str1.replace(matcher.group(0), str2 + "(){const getAction=this._getAction,postAction=this._postAction,deleteAction=this._deleteAction;");
            for (OnlCgformField onlCgformField : paramList) {
                Pattern pattern1 = Pattern.compile("(" + onlCgformField.getDbFieldName() + "\\s*\\(\\))");
                Matcher matcher1 = pattern1.matcher(str1);
                if (matcher1.find())
                    str1 = str1.replace(matcher1.group(0), onlCgformField.getDbFieldName() + "(that,event)");
            }
        }
        paramOnlCgformEnhanceJs.setCgJs(str1);
        a7(paramOnlCgformEnhanceJs);
        a7(paramOnlCgformEnhanceJs, paramString, paramList);
    }

    public static void a7(OnlCgformEnhanceJs paramOnlCgformEnhanceJs) {
        String str1 = paramOnlCgformEnhanceJs.getCgJs();
        String str2 = "show";
        Pattern pattern = Pattern.compile("(" + str2 + "\\s*\\(\\)\\s*\\{)");
        Matcher matcher = pattern.matcher(str1);
        if (matcher.find())
            str1 = str1.replace(matcher.group(0), str2 + "(that){const getAction=this._getAction,postAction=this._postAction,deleteAction=this._deleteAction;");
        paramOnlCgformEnhanceJs.setCgJs(str1);
    }

    public static String d(String paramString) {
        return "class OnlineEnhanceJs{constructor(getAction,postAction,deleteAction){this._getAction=getAction;this._postAction=postAction;this._deleteAction=deleteAction;}" + paramString + "}";
    }

    public static String e(String paramString, List<OnlCgformButton> paramList) {
        if (paramList != null)
            for (OnlCgformButton onlCgformButton : paramList) {
                String str = onlCgformButton.getButtonCode();
                if ("link".equals(onlCgformButton.getButtonStyle())) {
                    Pattern pattern1 = Pattern.compile("(" + str + "\\s*\\(row\\)\\s*\\{)");
                    Matcher matcher1 = pattern1.matcher(paramString);
                    if (matcher1.find()) {
                        paramString = paramString.replace(matcher1.group(0), str + "(that,row){const getAction=this._getAction,postAction=this._postAction,deleteAction=this._deleteAction;");
                        continue;
                    }
                    Pattern pattern2 = Pattern.compile("(" + str + "\\s*\\(\\)\\s*\\{)");
                    Matcher matcher2 = pattern2.matcher(paramString);
                    if (matcher2.find())
                        paramString = paramString.replace(matcher2.group(0), str + "(that){const getAction=this._getAction,postAction=this._postAction,deleteAction=this._deleteAction;");
                    continue;
                }
                if ("button".equals(onlCgformButton.getButtonStyle()) || "form".equals(onlCgformButton.getButtonStyle())) {
                    Pattern pattern = Pattern.compile("(" + str + "\\s*\\(\\)\\s*\\{)");
                    Matcher matcher = pattern.matcher(paramString);
                    if (matcher.find())
                        paramString = paramString.replace(matcher.group(0), str + "(that){const getAction=this._getAction,postAction=this._postAction,deleteAction=this._deleteAction;");
                }
            }
        return paramString;
    }

    public static JSONArray a7(List<OnlCgformField> paramList, List<String> paramList1) {
        JSONArray jSONArray = new JSONArray();
        ISysBaseAPI iSysBaseAPI = (ISysBaseAPI) SpringContextUtils.getBean(ISysBaseAPI.class);
        for (OnlCgformField onlCgformField : paramList) {
            String str1 = onlCgformField.getDbFieldName();
            if ("id".equals(str1))
                continue;
            JSONObject jSONObject = new JSONObject();
            if (paramList1 != null && paramList1.indexOf(str1) >= 0)
                jSONObject.put("disabled", Boolean.valueOf(true));
            if (onlCgformField.getIsReadOnly() != null && 1 == onlCgformField.getIsReadOnly().intValue())
                jSONObject.put("disabled", Boolean.valueOf(true));
            jSONObject.put("title", onlCgformField.getDbFieldTxt());
            jSONObject.put("key", str1);
            String str2 = d(onlCgformField);
            jSONObject.put("type", str2);
            if (onlCgformField.getFieldLength() == null)
                onlCgformField.setFieldLength(Integer.valueOf(186));
            if (("sel_depart".equals(str2) || "sel_user".equals(str2)) && onlCgformField.getFieldLength().intValue() < 170) {
                jSONObject.put("width", "170px");
            } else if ("date".equals(str2) && onlCgformField.getFieldLength().intValue() < 140) {
                jSONObject.put("width", "140px");
            } else if ("datetime".equals(str2) && onlCgformField.getFieldLength().intValue() < 190) {
                jSONObject.put("width", "190px");
            } else {
                jSONObject.put("width", onlCgformField.getFieldLength() + "px");
            }
            if ("file".equals(str2) || "image".equals(str2)) {
                jSONObject.put("responseName", "message");
                jSONObject.put("token", Boolean.valueOf(true));
            }
            if ("switch".equals(str2)) {
                jSONObject.put("type", "checkbox");
                JSONArray jSONArray1 = new JSONArray();
                if (oConvertUtils.isEmpty(onlCgformField.getFieldExtendJson())) {
                    jSONArray1.add("Y");
                    jSONArray1.add("N");
                } else {
                    jSONArray1 = JSONArray.parseArray(onlCgformField.getFieldExtendJson());
                }
                jSONObject.put("customValue", jSONArray1);
            }
            if ("popup".equals(str2)) {
                jSONObject.put("popupCode", onlCgformField.getDictTable());
                jSONObject.put("orgFields", onlCgformField.getDictField());
                jSONObject.put("destFields", onlCgformField.getDictText());
                String str = onlCgformField.getDictText();
                if (str != null && !"".equals(str)) {
                    ArrayList<String> arrayList = new ArrayList();
                    String[] arrayOfString = str.split(",");
                    for (String str3 : arrayOfString) {
                        if (!a7(str3, paramList)) {
                            arrayList.add(str3);
                            JSONObject jSONObject1 = new JSONObject();
                            jSONObject1.put("title", str3);
                            jSONObject1.put("key", str3);
                            jSONObject1.put("type", "hidden");
                            jSONArray.add(jSONObject1);
                        }
                    }
                }
            }
            jSONObject.put("defaultValue", onlCgformField.getDbDefaultVal());
            jSONObject.put("fieldDefaultValue", onlCgformField.getFieldDefaultValue());
            jSONObject.put("placeholder", "请输入" + onlCgformField.getDbFieldTxt());
            jSONObject.put("validateRules", c(onlCgformField));
            if ("list".equals(onlCgformField.getFieldShowType()) || "radio"
                    .equals(onlCgformField.getFieldShowType()) || "checkbox_meta"
                    .equals(onlCgformField.getFieldShowType()) || "list_multi"
                    .equals(onlCgformField.getFieldShowType()) || "sel_search"
                    .equals(onlCgformField.getFieldShowType())) {
                jSONObject.put("view", onlCgformField.getFieldShowType());
                jSONObject.put("dictTable", onlCgformField.getDictTable());
                jSONObject.put("dictText", onlCgformField.getDictText());
                jSONObject.put("dictCode", onlCgformField.getDictField());
                if ("list_multi".equals(onlCgformField.getFieldShowType()))
                    jSONObject.put("width", "230px");
            }
            jSONObject.put("fieldExtendJson", onlCgformField.getFieldExtendJson());
            jSONArray.add(jSONObject);
        }
        return jSONArray;
    }

    private static JSONArray c(OnlCgformField paramOnlCgformField) {
        JSONArray jSONArray = new JSONArray();
        if (paramOnlCgformField.getDbIsNull().intValue() == 0 || "1".equals(paramOnlCgformField.getFieldMustInput())) {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("required", Boolean.valueOf(true));
            jSONObject.put("message", paramOnlCgformField.getDbFieldTxt() + "不能为空!");
            jSONArray.add(jSONObject);
        }
        if (oConvertUtils.isNotEmpty(paramOnlCgformField.getFieldValidType())) {
            JSONObject jSONObject = new JSONObject();
            if ("only".equals(paramOnlCgformField.getFieldValidType())) {
                jSONObject.put("unique", Boolean.valueOf(true));
                jSONObject.put("message", paramOnlCgformField.getDbFieldTxt() + "不能重复");
            } else {
                jSONObject.put("pattern", paramOnlCgformField.getFieldValidType());
                String str = a6("validateError", paramOnlCgformField.getFieldExtendJson());
                if (oConvertUtils.isEmpty(str)) {
                    jSONObject.put("message", paramOnlCgformField.getDbFieldTxt() + "格式不正确");
                } else {
                    jSONObject.put("message", str);
                }
            }
            jSONArray.add(jSONObject);
        }
        return jSONArray;
    }

    public static Map<String, Object> a7(Map<String, Object> paramMap) {
        HashMap<Object, Object> hashMap = new HashMap<>(5);
        if (paramMap == null || paramMap.isEmpty())
            return (Map) hashMap;
        Set<String> set = paramMap.keySet();
        for (String str1 : set) {
            Object object = paramMap.get(str1);
            if (object instanceof Clob) {
                object = a7((Clob) object);
            } else if (object instanceof byte[]) {
                object = new String((byte[]) object);
            } else if (object instanceof Blob) {
                try {
                    if (object != null) {
                        Blob blob = (Blob) object;
                        object = new String(blob.getBytes(1L, (int) blob.length()), "UTF-8");
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
            String str2 = str1.toLowerCase();
            hashMap.put(str2, (object == null) ? "" : object);
        }
        return (Map) hashMap;
    }

    public static JSONObject a7(JSONObject paramJSONObject) {
        if (eDbTableHandleI.a()) {
            JSONObject jSONObject = new JSONObject();
            if (paramJSONObject == null || paramJSONObject.isEmpty())
                return jSONObject;
            Set<String> set = paramJSONObject.keySet();
            for (String str1 : set) {
                String str2 = str1.toLowerCase();
                jSONObject.put(str2, paramJSONObject.get(str1));
            }
            return jSONObject;
        }
        return paramJSONObject;
    }

    public static List<Map<String, Object>> d(List<Map<String, Object>> paramList) {
        return a7(paramList, (Collection<String>) null);
    }

    public static List<Map<String, Object>> a7(List<Map<String, Object>> paramList, Collection<String> paramCollection) {
        ArrayList<HashMap<Object, Object>> arrayList = new ArrayList();
        for (Map<String, Object> map : paramList) {
            if (map == null)
                continue;
            HashMap<Object, Object> hashMap = new HashMap<>(5);
            Set<String> set = map.keySet();
            for (String str1 : set) {
                Object object = map.get(str1);
                if (object instanceof Clob) {
                    object = a7((Clob) object);
                } else if (object instanceof byte[]) {
                    object = new String((byte[]) object);
                } else if (object instanceof Long) {
                    if (object != null)
                        object = String.valueOf(object);
                } else if (object instanceof Blob) {
                    try {
                        if (object != null) {
                            Blob blob = (Blob) object;
                            object = new String(blob.getBytes(1L, (int) blob.length()), "UTF-8");
                        }
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
                String str2 = str1.toLowerCase();
                hashMap.put(str2, (object == null) ? "" : object);
            }
            String str = a7((Map) hashMap, paramCollection);
            hashMap.put("jeecg_row_key", str);
            arrayList.add(hashMap);
        }
        return (List) arrayList;
    }

    private static String a7(Map<String, Object> paramMap, Collection<String> paramCollection) {
        String str = paramMap.containsKey("id") ? paramMap.get("id").toString() : null;
        if (oConvertUtils.isNotEmpty(str) && paramCollection != null)
            for (String str1 : paramCollection) {
                String str2 = str1.toLowerCase() + "_id";
                Object object = paramMap.get(str2);
                if (object == null) {
                    str = str + "@";
                    continue;
                }
                str = str + "@" + object.toString();
            }
        return str;
    }

    public static String a7(Clob paramClob) {
        String str = "";
        try {
            Reader reader = paramClob.getCharacterStream();
            char[] arrayOfChar = new char[(int) paramClob.length()];
            reader.read(arrayOfChar);
            str = new String(arrayOfChar);
            reader.close();
        } catch (IOException iOException) {
            iOException.printStackTrace();
        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
        }
        return str;
    }

    public static Map<String, Object> c(String paramString, List<OnlCgformField> paramList, JSONObject paramJSONObject) {
        StringBuffer stringBuffer1 = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        String str1 = "";
        try {
            str1 = eDbTableHandleI.getDatabaseType();
        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
        } catch (AException a) {
            a.printStackTrace();
        }
        HashMap<Object, Object> hashMap = new HashMap<>(5);
        boolean bool = false;
        String str2 = null;
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        if (loginUser == null) {
            String str = JwtUtil.getUserNameByToken(SpringContextUtils.getHttpServletRequest());
            if (oConvertUtils.isNotEmpty(str)) {
                loginUser = new LoginUser();
                loginUser.setUsername(str);
            } else {
                throw new JeecgBootException("online保存表单数据异常:系统未找到当前登陆用户信息");
            }
        }
        String str3 = "tenant_id";
        String str4 = f(paramString);
        boolean bool1 = j(str4);
        for (OnlCgformField onlCgformField : paramList) {
            String str6 = onlCgformField.getDbFieldName();
            if (null == str6)
                continue;
            if (paramJSONObject.get(str6) == null && !"CREATE_BY".equalsIgnoreCase(str6) && !"CREATE_TIME".equalsIgnoreCase(str6) && !"SYS_ORG_CODE".equalsIgnoreCase(str6))
                continue;
            if (bool1 && str3.equalsIgnoreCase(str6))
                continue;
            a7(onlCgformField, loginUser, paramJSONObject, new String[]{"CREATE_BY", "CREATE_TIME", "SYS_ORG_CODE"});
            if ("".equals(paramJSONObject.get(str6))) {
                String str = onlCgformField.getDbType();
                if (gType.a(str) || gType.b(str))
                    continue;
            }
            if ("id".equals(str6.toLowerCase())) {
                bool = true;
                str2 = paramJSONObject.getString(str6);
                continue;
            }
            if ("link_table_field".equals(onlCgformField.getFieldShowType()) &&
                    !org.jeecg.modules.online.cgform.b1.bConstant.b.equals(onlCgformField.getDbIsPersist()))
                continue;
            stringBuffer1.append("," + str6);
            String str7 = gType.a(str1, onlCgformField, paramJSONObject, (Map) hashMap);
            stringBuffer2.append("," + str7);
        }
        if (!bool || oConvertUtils.isEmpty(str2))
            str2 = a7();
        if (bool1) {
            stringBuffer1.append("," + str3);
            stringBuffer2.append(",#{" + str3 + "}");
            String str = SpringContextUtils.getHttpServletRequest().getHeader("X-Tenant-Id");
            hashMap.put(str3, str);
        }
        String str5 = "insert into " + str4 + "(" + "id" + stringBuffer1.toString() + ") values(" + "'" + str2 + "'" + stringBuffer2.toString() + ")";
        hashMap.put("execute_sql_string", str5);
        hashMap.put("id", str2);
        return (Map) hashMap;
    }

    public static Map<String, Object> d(String paramString, List<OnlCgformField> paramList, JSONObject paramJSONObject) {
        StringBuffer stringBuffer = new StringBuffer();
        HashMap<Object, Object> hashMap = new HashMap<>(5);
        String str1 = "";
        try {
            str1 = eDbTableHandleI.getDatabaseType();
        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
        } catch (AException a) {
            a.printStackTrace();
        }
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        if (loginUser == null) {
            String str = JwtUtil.getUserNameByToken(SpringContextUtils.getHttpServletRequest());
            if (oConvertUtils.isNotEmpty(str)) {
                loginUser = new LoginUser();
                loginUser.setUsername(str);
            } else {
                throw new JeecgBootException("online保存表单数据异常:系统未找到当前登陆用户信息");
            }
        }
        for (OnlCgformField onlCgformField : paramList) {
            if (!org.jeecg.modules.online.cgform.b1.bConstant.b.equals(onlCgformField.getDbIsPersist()))
                continue;
            String str4 = onlCgformField.getDbFieldName();
            if (null == str4)
                continue;
            if ("id".equals(str4))
                continue;
            if (paramJSONObject.get(str4) == null && !"UPDATE_BY".equalsIgnoreCase(str4) && !"UPDATE_TIME".equalsIgnoreCase(str4) && !"SYS_ORG_CODE".equalsIgnoreCase(str4))
                continue;
            a7(onlCgformField, loginUser, paramJSONObject, new String[]{"UPDATE_BY", "UPDATE_TIME", "SYS_ORG_CODE"});
            if ("".equals(paramJSONObject.get(str4))) {
                String str = onlCgformField.getDbType();
                if (gType.a(str) || gType.b(str))
                    continue;
            }
            String str5 = gType.a(str1, onlCgformField, paramJSONObject, (Map) hashMap);
            stringBuffer.append(str4 + "=" + str5 + ",");
        }
        String str2 = stringBuffer.toString();
        if (str2.endsWith(","))
            str2 = str2.substring(0, str2.length() - 1);
        String str3 = "update " + f(paramString) + " set " + str2 + " where  " + "id" + "=" + "'" + paramJSONObject.getString("id") + "'";
        hashMap.put("execute_sql_string", str3);
        hashMap.put("id", paramJSONObject.getString("id"));
        return (Map) hashMap;
    }

    public static Map<String, Object> a7(String paramString1, String paramString2, String paramString3) {
        HashMap<Object, Object> hashMap = new HashMap<>(5);
        String str = "update " + f(paramString1) + " set " + paramString2 + "=" + "'" + Character.MIN_VALUE + "'" + " where  " + "id" + "=" + "'" + paramString3 + "'";
        hashMap.put("execute_sql_string", str);
        return (Map) hashMap;
    }

    public static String e(String paramString) {
        if (paramString == null || "".equals(paramString) || "0".equals(paramString))
            return "";
        return "CODE like '" + paramString + "%" + "'";
    }

    public static String f(String paramString) {
        if (Pattern.matches("^[a-zA-z].*\\$\\d+$", paramString))
            return paramString.substring(0, paramString.lastIndexOf("$"));
        return paramString;
    }

    public static void a5(c paramc, List<OnlCgformField> paramList, List<String> paramList1) {
        String str1 = paramc.getDictTable();
        JSONObject jSONObject = JSONObject.parseObject(str1);
        String str2 = jSONObject.getString("linkField");
        ArrayList<aTable> arrayList = new ArrayList<>();
        if (oConvertUtils.isNotEmpty(str2)) {
            String[] arrayOfString = str2.split(",");
            for (OnlCgformField onlCgformField : paramList) {
                String str = onlCgformField.getDbFieldName();
                for (String str3 : arrayOfString) {
                    if (str3.equals(str)) {
                        paramList1.add(str);
                        arrayList.add(new aTable(onlCgformField.getDbFieldTxt(), str, onlCgformField.getOrderNum()));
                        break;
                    }
                }
            }
        }
        paramc.setOtherColumns(arrayList);
    }

    public static String a7(byte[] paramArrayOfbyte, String paramString1, String paramString2, String paramString3) {
        return CommonUtils.uploadOnlineImage(paramArrayOfbyte, paramString1, paramString2, paramString3);
    }

    public static List<String> e(List<OnlCgformField> paramList) {
        ArrayList<String> arrayList = new ArrayList();
        for (OnlCgformField onlCgformField : paramList) {
            if ("image".equals(onlCgformField.getFieldShowType()))
                arrayList.add(onlCgformField.getDbFieldTxt());
        }
        return arrayList;
    }

    public static List<String> b(List<OnlCgformField> paramList, String paramString) {
        ArrayList<String> arrayList = new ArrayList();
        for (OnlCgformField onlCgformField : paramList) {
            if ("image".equals(onlCgformField.getFieldShowType()))
                arrayList.add(paramString + "_" + onlCgformField.getDbFieldTxt());
        }
        return arrayList;
    }

    public static String a7() {
        long l = IdWorker.getId();
        return String.valueOf(l);
    }

    public static String a7(Exception paramException) {
        String str = (paramException.getCause() != null) ? paramException.getCause().getMessage() : paramException.getMessage();
        if (str.indexOf("ORA-01452") != -1) {
            str = "ORA-01452: 无法 CREATE UNIQUE INDEX; 找到重复的关键字";
        } else if (str.indexOf("duplicate key") != -1) {
            str = "无法 CREATE UNIQUE INDEX; 找到重复的关键字";
        }
        return str;
    }

    public static List<DictModel> b(OnlCgformField paramOnlCgformField) {
        ArrayList<DictModel> arrayList = new ArrayList();
        String str1 = paramOnlCgformField.getFieldExtendJson();
        String str2 = "是", str3 = "否";
        JSONArray jSONArray = JSONArray.parseArray("[\"Y\",\"N\"]");
        if (oConvertUtils.isNotEmpty(str1))
            jSONArray = JSONArray.parseArray(str1);
        DictModel dictModel1 = new DictModel(jSONArray.getString(0), str2);
        DictModel dictModel2 = new DictModel(jSONArray.getString(1), str3);
        arrayList.add(dictModel1);
        arrayList.add(dictModel2);
        return arrayList;
    }

    private static String d(OnlCgformField paramOnlCgformField) {
        if ("checkbox".equals(paramOnlCgformField.getFieldShowType()))
            return "checkbox";
        if ("pca".equals(paramOnlCgformField.getFieldShowType()))
            return "pca";
        if ("list".equals(paramOnlCgformField.getFieldShowType()))
            return "select";
        if ("switch".equals(paramOnlCgformField.getFieldShowType()))
            return "switch";
        if ("sel_user".equals(paramOnlCgformField.getFieldShowType()))
            return "sel_user";
        if ("sel_depart".equals(paramOnlCgformField.getFieldShowType()))
            return "sel_depart";
        if ("textarea".equals(paramOnlCgformField.getFieldShowType()))
            return "textarea";
        if ("image".equals(paramOnlCgformField.getFieldShowType()) || "file"
                .equals(paramOnlCgformField.getFieldShowType()) || "radio"
                .equals(paramOnlCgformField.getFieldShowType()) || "popup"
                .equals(paramOnlCgformField.getFieldShowType()) || "list_multi"
                .equals(paramOnlCgformField.getFieldShowType()) || "sel_search"
                .equals(paramOnlCgformField.getFieldShowType()))
            return paramOnlCgformField.getFieldShowType();
        if ("datetime".equals(paramOnlCgformField.getFieldShowType()))
            return "datetime";
        if ("date".equals(paramOnlCgformField.getFieldShowType()))
            return "date";
        if ("time".equals(paramOnlCgformField.getFieldShowType()))
            return "time";
        if ("int".equals(paramOnlCgformField.getDbType()))
            return "inputNumber";
        if ("double".equals(paramOnlCgformField.getDbType()) || "BigDecimal".equals(paramOnlCgformField.getDbType()))
            return "inputNumber";
        return "input";
    }

    private static String getDatabseType() {
        if (oConvertUtils.isNotEmpty(aU))
            return aU;
        try {
            aU = eDbTableHandleI.getDatabaseType();
            return aU;
        } catch (Exception exception) {
            exception.printStackTrace();
            return aU;
        }
    }

    public static List<String> f(List<String> paramList) {
        ArrayList<String> arrayList = new ArrayList();
        for (String str : paramList)
            arrayList.add(str.toLowerCase());
        return arrayList;
    }

    private static String c(String paramString1, String paramString2) {
        String str = "";
        if (paramString2 == null || "".equals(paramString2))
            return str;
        String[] arrayOfString = paramString2.split(",");
        for (byte b1 = 0; b1 < arrayOfString.length; b1++) {
            if (b1 > 0)
                str = str + " AND ";
            str = str + paramString1 + " like ";
            if ("SQLSERVER".equals(getDatabseType()))
                str = str + "N";
            str = str + "'%" + arrayOfString[b1] + "%" + "'";
        }
        return str;
    }

    public static String a7(String paramString1, String paramString2, StringBuffer paramStringBuffer) {
        String str1 = "logs" + File.separator + ((SimpleDateFormat) DateUtils.yyyyMMdd.get()).format(new Date()) + File.separator;
        String str2 = paramString1 + File.separator + str1;
        File file = new File(str2);
        if (!file.exists())
            file.mkdirs();
        String str3 = paramString2 + Math.round(Math.random() * 10000.0D);
        String str4 = str2 + str3 + ".txt";
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(str4));
            bufferedWriter.write(paramStringBuffer.toString());
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (Exception exception) {
        }
        return "/sys/common/static/" + str1 + str3 + ".txt";
    }

    public static JSONObject b(JSONObject paramJSONObject) {
        JSONObject jSONObject;
        if (paramJSONObject.containsKey("properties")) {
            jSONObject = paramJSONObject.getJSONObject("properties");
        } else {
            JSONObject jSONObject1 = paramJSONObject.getJSONObject("schema");
            jSONObject = jSONObject1.getJSONObject("properties");
        }
        ISysBaseAPI iSysBaseAPI = (ISysBaseAPI) SpringContextUtils.getBean(ISysBaseAPI.class);
        for (String str1 : jSONObject.keySet()) {
            JSONObject jSONObject1 = jSONObject.getJSONObject(str1);
            String str2 = jSONObject1.getString("view");
            if (c(str2)) {
                String str3 = jSONObject1.getString("dictCode");
                String str4 = jSONObject1.getString("dictText");
                String str5 = jSONObject1.getString("dictTable");
                List list = new ArrayList();
                if (oConvertUtils.isNotEmpty(str5)) {
                    List list1 = iSysBaseAPI.queryTableDictItemsByCode(str5, str4, str3);
                } else if (oConvertUtils.isNotEmpty(str3)) {
                    list = iSysBaseAPI.queryEnableDictItemsByCode(str3);
                }
                if (list != null && list.size() > 0)
                    jSONObject1.put("enum", list);
                continue;
            }
            if ("tab".equals(str2)) {
                String str = jSONObject1.getString("relationType");
                if ("1".equals(str)) {
                    b(jSONObject1);
                    continue;
                }
                JSONArray jSONArray = jSONObject1.getJSONArray("columns");
                for (byte b1 = 0; b1 < jSONArray.size(); b1++) {
                    JSONObject jSONObject2 = jSONArray.getJSONObject(b1);
                    if (c(jSONObject2)) {
                        String str3 = jSONObject2.getString("dictCode");
                        String str4 = jSONObject2.getString("dictText");
                        String str5 = jSONObject2.getString("dictTable");
                        List list = new ArrayList();
                        if (oConvertUtils.isNotEmpty(str5)) {
                            List list1 = iSysBaseAPI.queryTableDictItemsByCode(str5, str4, str3);
                        } else if (oConvertUtils.isNotEmpty(str3)) {
                            list = iSysBaseAPI.queryEnableDictItemsByCode(str3);
                        }
                        if (list != null && list.size() > 0)
                            jSONObject2.put("options", list);
                    }
                }
            }
        }
        return paramJSONObject;
    }

    private static boolean c(JSONObject paramJSONObject) {
        Object object = paramJSONObject.get("view");
        if (object != null) {
            String str = object.toString();
            if ("list".equals(str) || "radio"
                    .equals(str) || "checkbox_meta"
                    .equals(str) || "list_multi"
                    .equals(str) || "sel_search"
                    .equals(str))
                return true;
        }
        return false;
    }

    public static JSONArray b(Map<String, Object> paramMap) {
        Object object = paramMap.get("superQueryParams");
        if (object != null)
            try {
                String str = URLDecoder.decode(object.toString(), "UTF-8");
                return JSONArray.parseArray(str);
            } catch (UnsupportedEncodingException unsupportedEncodingException) {
                aS.error("高级查询json参数转换失败" + unsupportedEncodingException.getMessage());
            }
        return null;
    }

    public static MatchTypeEnum c(Map<String, Object> paramMap) {
        Object object = paramMap.get("superQueryMatchType");
        MatchTypeEnum matchTypeEnum = MatchTypeEnum.getByValue(object);
        if (matchTypeEnum == null)
            matchTypeEnum = MatchTypeEnum.AND;
        return matchTypeEnum;
    }

    public static boolean g(String paramString) {
        for (byte b1 = 0; b1 < paramString.length(); b1++) {
            char c = paramString.charAt(b1);
            if (c != '.' && c != '-' && c != '+')
                if (!Character.isDigit(c))
                    return false;
        }
        return true;
    }

    public static List<eTableConfig> g(List<OnlCgformField> paramList) {
        ArrayList<eTableConfig> arrayList = new ArrayList();
        for (OnlCgformField onlCgformField : paramList) {
            if (!org.jeecg.modules.online.cgform.b1.bConstant.b.equals(onlCgformField.getDbIsPersist()))
                continue;
            arrayList.add(new eTableConfig(onlCgformField));
        }
        return arrayList;
    }

    public static String a7(Map<String, Object> paramMap, String paramString) {
        Object object = b(paramMap, paramString);
        if (object != null)
            return object.toString();
        return null;
    }

    public static Object b(Map<String, Object> paramMap, String paramString) {
        Object object1 = paramMap.get(paramString);
        if (object1 != null)
            return object1;
        Object object2 = paramMap.get(paramString.toLowerCase());
        if (object2 != null)
            return object2;
        Object object3 = paramMap.get(paramString.toUpperCase());
        if (object3 != null)
            return object3;
        return null;
    }

    public static List<String> h(String paramString) {
        ArrayList<String> arrayList = new ArrayList();
        if (oConvertUtils.isNotEmpty(paramString)) {
            String[] arrayOfString = paramString.split(",");
            for (String str : arrayOfString) {
                int i = str.indexOf("@");
                if (i > 0) {
                    String str1 = str.substring(0, i);
                    arrayList.add(str1);
                } else {
                    arrayList.add(str);
                }
            }
        }
        return arrayList;
    }

    public static Map<String, List<String>> f(String paramString, List<String> paramList) {
        HashMap<Object, Object> hashMap = new HashMap<>(5);
        hashMap.put("id", new ArrayList());
        if (paramList != null)
            for (byte b1 = 0; b1 < paramList.size(); b1++) {
                String str = paramList.get(b1);
                hashMap.put(str + "_id", new ArrayList());
            }
        if (oConvertUtils.isNotEmpty(paramString)) {
            String[] arrayOfString = paramString.split(",");
            for (String str : arrayOfString) {
                String[] arrayOfString1 = str.split("@");
                if (arrayOfString1.length > 0)
                    ((List<String>) hashMap.get("id")).add(arrayOfString1[0]);
                if (arrayOfString1.length > 1 && paramList != null && paramList.size() > 0)
                    for (byte b1 = 1; b1 < arrayOfString1.length; b1++) {
                        int i = b1 - 1;
                        String str1 = paramList.get(i);
                        ((List<String>) hashMap.get(str1 + "_id")).add(arrayOfString1[b1]);
                    }
            }
        }
        return (Map) hashMap;
    }

    public static List<ExcelExportEntity> a7(List<OnlCgformField> paramList, String paramString1, String paramString2) {
        ArrayList<ExcelExportEntity> arrayList = new ArrayList<>();
        for (byte b1 = 0; b1 < paramList.size(); b1++) {
            if (null == paramString1 || !paramString1.equals((paramList.get(b1)).getDbFieldName()))
                if ((paramList.get(b1)).getIsShowList() == 1) {
                    String str = (paramList.get(b1)).getDbFieldName();
                    ExcelExportEntity excelExportEntity = new ExcelExportEntity((paramList.get(b1)).getDbFieldTxt(), str);
                    if ("image".equals((paramList.get(b1)).getFieldShowType())) {
                        excelExportEntity.setType(2);
                        excelExportEntity.setExportImageType(3);
                        excelExportEntity.setImageBasePath(paramString2);
                        excelExportEntity.setHeight(50.0D);
                        excelExportEntity.setWidth(60.0D);
                    } else {
                        int b2 = ((paramList.get(b1)).getDbLength() == 0) ? 12 :
                                (((paramList.get(b1)).getDbLength() > 30) ? 30 :
                                        (paramList.get(b1)).getDbLength());
                        if ("date".equals((paramList.get(b1)).getFieldShowType())) {
                            excelExportEntity.setFormat("yyyy-MM-dd");
                        } else if ("datetime".equals((paramList.get(b1)).getFieldShowType())) {
                            excelExportEntity.setFormat("yyyy-MM-dd HH:mm:ss");
                        }
                        if (b2 < 10)
                            b2 = 10;
                        excelExportEntity.setWidth(b2);
                    }
                    arrayList.add(excelExportEntity);
                }
        }
        return arrayList;
    }

    public static <T> List<T> a7(Object paramObject, Class<T> paramClass) {
        ArrayList<T> arrayList = new ArrayList<>();
        if (paramObject instanceof List) {
            for (Object object : paramObject) {
                arrayList.add(paramClass.cast(object));
            }
            return arrayList;
        }
        return null;
    }

    public static boolean i(String paramString) {
        return ("CREATE_TIME".equalsIgnoreCase(paramString) || "CREATE_BY"
                .equalsIgnoreCase(paramString) || "UPDATE_TIME"
                .equalsIgnoreCase(paramString) || "UPDATE_BY"
                .equalsIgnoreCase(paramString) || "SYS_ORG_CODE"
                .equalsIgnoreCase(paramString) || "id"
                .equalsIgnoreCase(paramString));
    }

    public static boolean j(String paramString) {
        boolean bool = false;
        for (String str : MybatisPlusSaasConfig.TENANT_TABLE) {
            if (str.equalsIgnoreCase(paramString)) {
                bool = true;
                break;
            }
        }
        return bool;
    }

    public static String k(String paramString) {
        if (paramString.indexOf("@") > 0)
            paramString = paramString.substring(0, paramString.indexOf("@"));
        return paramString;
    }
}
