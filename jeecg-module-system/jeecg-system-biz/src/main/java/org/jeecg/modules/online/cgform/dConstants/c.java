 package org.jeecg.modules.online.cgform.dConstants;

 import java.util.List;
 import java.util.regex.Matcher;
 import java.util.regex.Pattern;
 import org.jeecg.common.util.oConvertUtils;
 import org.jeecg.modules.online.cgform.entity.OnlCgformButton;
 import org.jeecg.modules.online.cgform.entity.OnlCgformEnhanceJs;
 import org.jeecg.modules.online.cgform.entity.OnlCgformField;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;

 public class c {
   private static final Logger e = LoggerFactory.getLogger(c.class);

   private static final String f = "setup,beforeSubmit,beforeAdd,beforeEdit,afterAdd,afterEdit,beforeDelete,afterDelete,mounted,created,show,loaded";

   private static final String g = "\\}\\s*\r*\n*\\s*";

   private static final String h = ",";

   public static final Pattern a = Pattern.compile("^import\\s+(.*)\\s+from\\s+(['\"].*['\"])[;]?$");

   public static final String b = "import";

   public static final String c = "customImport";

   public static final String d = "_hook";

   public static String a(String paramString1, String paramString2) {
     String str1 = "(" + paramString2 + "\\s*\\(row\\)\\s*\\{)";
     String str2 = paramString2 + ":function(that,row){const getAction=this._getAction,postAction=this._postAction,deleteAction=this._deleteAction;";
     String str3 = b(paramString1, "\\}\\s*\r*\n*\\s*" + str1, "}," + str2);
     if (str3 == null) {
       paramString1 = c(paramString1, str1, str2);
     } else {
       paramString1 = str3;
     }
     paramString1 = a(paramString1, paramString2, (String)null);
     return paramString1;
   }

   public static String a(String paramString1, String paramString2, String paramString3) {
     String str1 = "(" + oConvertUtils.getString(paramString3) + paramString2 + "\\s*\\(\\)\\s*\\{)";
     String str2 = paramString2 + ":function(that){const getAction=this._getAction,postAction=this._postAction,deleteAction=this._deleteAction;";
     String str3 = b(paramString1, "\\}\\s*\r*\n*\\s*" + str1, "}," + str2);
     if (str3 == null) {
       paramString1 = c(paramString1, str1, str2);
     } else {
       paramString1 = str3;
     }
     return paramString1;
   }

   public static String b(String paramString1, String paramString2, String paramString3) {
     Pattern pattern = Pattern.compile(paramString2);
     Matcher matcher = pattern.matcher(paramString1);
     if (matcher.find()) {
       paramString1 = paramString1.replace(matcher.group(0), paramString3);
       return paramString1;
     }
     return null;
   }

   public static String c(String paramString1, String paramString2, String paramString3) {
     String str = b(paramString1, paramString2, paramString3);
     if (str != null)
       return str;
     return paramString1;
   }

   public static String a(String paramString, List<OnlCgformButton> paramList) {
     return "class OnlineEnhanceJs{constructor(getAction,postAction,deleteAction){this._getAction=getAction;this._postAction=postAction;this._deleteAction=deleteAction;}" + paramString + "}";
   }

   public static String b(String paramString1, String paramString2) {
     String str1 = "(\\s+" + paramString2 + "\\s*\\(\\)\\s*\\{)";
     String str2 = paramString2 + ":function(that,event){";
     String str3 = b(paramString1, "\\}\\s*\r*\n*\\s*" + str1, "}," + str2);
     if (str3 == null) {
       paramString1 = c(paramString1, str1, str2);
     } else {
       paramString1 = str3;
     }
     return paramString1;
   }

   public static String a(String paramString) {
     return "function OnlineEnhanceJs(getAction,postAction,deleteAction){return {_getAction:getAction,_postAction:postAction,_deleteAction:deleteAction," + paramString + "}}";
   }

   public static String b(String paramString, List<OnlCgformButton> paramList) {
     paramString = c(paramString, paramList);
     return "function OnlineEnhanceJs(getAction,postAction,deleteAction){return {_getAction:getAction,_postAction:postAction,_deleteAction:deleteAction," + paramString + "}}";
   }

   public static String c(String paramString, List<OnlCgformButton> paramList) {
     paramString = b(paramString);
     if (paramList != null)
       for (OnlCgformButton onlCgformButton : paramList) {
         String str = onlCgformButton.getButtonCode();
         if ("link".equals(onlCgformButton.getButtonStyle())) {
           paramString = a(paramString, str);
           paramString = a(paramString, str + "_hook");
           continue;
         }
         if ("button".equals(onlCgformButton.getButtonStyle()) || "form".equals(onlCgformButton.getButtonStyle())) {
           paramString = a(paramString, str, (String)null);
           paramString = a(paramString, str + "_hook", (String)null);
         }
       }
     for (String str : "setup,beforeSubmit,beforeAdd,beforeEdit,afterAdd,afterEdit,beforeDelete,afterDelete,mounted,created,show,loaded".split(",")) {
       if ("setup,beforeAdd,afterAdd,mounted,created,show,loaded".indexOf(str) >= 0) {
         paramString = a(paramString, str, (String)null);
       } else {
         paramString = a(paramString, str);
       }
     }
     return paramString;
   }

   public static void a(OnlCgformEnhanceJs paramOnlCgformEnhanceJs, String paramString, List<OnlCgformField> paramList) {
     if (paramOnlCgformEnhanceJs == null || oConvertUtils.isEmpty(paramOnlCgformEnhanceJs.getCgJs()))
       return;
     String str = " " + paramOnlCgformEnhanceJs.getCgJs();
     Pattern pattern = Pattern.compile("(\\s{1}onlChange\\s*\\(\\)\\s*\\{)");
     Matcher matcher = pattern.matcher(str);
     if (matcher.find()) {
       str = a(str, "onlChange", "\\s{1}");
       for (OnlCgformField onlCgformField : paramList)
         str = b(str, onlCgformField.getDbFieldName());
     }
     paramOnlCgformEnhanceJs.setCgJs(str);
   }

   public static void b(OnlCgformEnhanceJs paramOnlCgformEnhanceJs, String paramString, List<OnlCgformField> paramList) {
     if (paramOnlCgformEnhanceJs == null || oConvertUtils.isEmpty(paramOnlCgformEnhanceJs.getCgJs()))
       return;
     String str1 = paramOnlCgformEnhanceJs.getCgJs();
     String str2 = paramString + "_" + "onlChange";
     Pattern pattern = Pattern.compile("(" + str2 + "\\s*\\(\\)\\s*\\{)");
     Matcher matcher = pattern.matcher(str1);
     if (matcher.find()) {
       str1 = a(str1, str2, (String)null);
       for (OnlCgformField onlCgformField : paramList)
         str1 = b(str1, onlCgformField.getDbFieldName());
     }
     paramOnlCgformEnhanceJs.setCgJs(str1);
   }

   private static String b(String paramString) {
     String str = "\n";
     String[] arrayOfString = paramString.split(str);
     for (byte b = 0; b < arrayOfString.length; b++) {
       String str1 = arrayOfString[b].trim();
       if (str1.startsWith("import")) {
         Matcher matcher = a.matcher(str1);
         if (matcher.find()) {
           String str2 = String.format("const %s = %s(%s)", new Object[] { matcher.group(1), "customImport", matcher.group(2) });
           arrayOfString[b] = arrayOfString[b].replace(str1, str2);
         }
       }
     }
     return String.join(str, (CharSequence[])arrayOfString);
   }
 }


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\cgform\d\c.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
