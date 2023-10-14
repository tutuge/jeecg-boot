 package org.jeecg.modules.online.cgform.model;
 
 import org.jeecg.common.util.oConvertUtils;
 
 public class h {
   public static final String a = "asc";
   
   public static final String b = "desc";
   
   public static final String c = " ORDER BY ";
   
   public static final String d = "ID";
   
   private String e;
   
   private String f;
   
   private String g;
   
   public static h a() {
     return a("");
   }
   
   public static h a(String paramString) {
     h h1 = new h("ID");
     h1.setAlias(paramString);
     return h1;
   }
   
   public String getRealSql() {
     String str = this.g + oConvertUtils.camelToUnderline(this.e);
     if ("asc".equals(this.f)) {
       str = str + " asc";
     } else {
       str = str + " desc";
     } 
     return str;
   }
   
   public h() {}
   
   public h(String paramString1, String paramString2) {
     this.e = paramString1;
     this.f = paramString2;
     this.g = "";
   }
   
   public h(String paramString) {
     this.f = "desc";
     this.e = paramString;
     this.g = "";
   }
   
   public String getColumn() {
     return this.e;
   }
   
   public void setColumn(String column) {
     this.e = column;
   }
   
   public String getRule() {
     return this.f;
   }
   
   public void setRule(String rule) {
     this.f = rule;
   }
   
   public String getAlias() {
     return this.g;
   }
   
   public void setAlias(String alias) {
     this.g = alias;
   }
 }


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\cgform\model\h.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
