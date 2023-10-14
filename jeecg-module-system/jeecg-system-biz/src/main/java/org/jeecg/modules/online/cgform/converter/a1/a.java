 package org.jeecg.modules.online.cgform.converter.a1;

 import java.util.List;
 import java.util.Map;
 import org.jeecg.common.system.api.ISysBaseAPI;
 import org.jeecg.common.system.vo.DictModel;
 import org.jeecg.common.util.SpringContextUtils;
 import org.jeecg.common.util.oConvertUtils;
 import org.jeecg.modules.online.cgform.converter.FieldCommentConverter;

 public class a implements FieldCommentConverter {
   protected ISysBaseAPI a = (ISysBaseAPI)SpringContextUtils.getBean(ISysBaseAPI.class);

   protected String b;

   protected String c;

   protected String d;

   protected String e;

   public a() {}

   public a(String paramString1, String paramString2, String paramString3) {
     this();
     this.c = paramString1;
     this.d = paramString2;
     this.e = paramString3;
   }

   public String getField() {
     return this.b;
   }

   public void setField(String field) {
     this.b = field;
   }

   public String getTable() {
     return this.c;
   }

   public void setTable(String table) {
     this.c = table;
   }

   public String getCode() {
     return this.d;
   }

   public void setCode(String code) {
     this.d = code;
   }

   public String getText() {
     return this.e;
   }

   public void setText(String text) {
     this.e = text;
   }

   public String converterToVal(String txt) {
     if (oConvertUtils.isNotEmpty(txt)) {
       String str1 = this.e + "= '" + txt + "'";
       String str2 = null;
       int i = this.c.indexOf("where");
       if (i > 0) {
         str2 = this.c.substring(0, i).trim();
         str1 = str1 + " and " + this.c.substring(i + 5);
       } else {
         str2 = this.c;
       }
       List<DictModel> list = this.a.queryFilterTableDictInfo(str2, this.e, this.d, str1);
       if (list != null && list.size() > 0)
         return ((DictModel)list.get(0)).getValue();
     }
     return null;
   }

   public String converterToTxt(String val) {
     if (oConvertUtils.isNotEmpty(val)) {
       String str1 = this.d + "= '" + val + "'";
       String str2 = null;
       int i = this.c.indexOf("where");
       if (i > 0) {
         str2 = this.c.substring(0, i).trim();
         str1 = str1 + " and " + this.c.substring(i + 5);
       } else {
         str2 = this.c;
       }
       List<DictModel> list = this.a.queryFilterTableDictInfo(str2, this.e, this.d, str1);
       if (list != null && list.size() > 0)
         return ((DictModel)list.get(0)).getText();
     }
     return null;
   }

   public Map<String, String> getConfig() {
     return null;
   }
 }


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\cgform\converter\a\a.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
