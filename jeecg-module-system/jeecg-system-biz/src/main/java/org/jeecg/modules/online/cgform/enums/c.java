 package org.jeecg.modules.online.cgform.enums;
 
 public enum c {
   a("MYSQL", "1"),
   b("ORACLE", "2"),
   c("SQLSERVER", "3"),
   d("POSTGRESQL", "4");
   
   public static c[] a() {
     return (c[])g.clone();
   }
   
   public static c a(String paramString) {
     return Enum.<c>valueOf(c.class, paramString);
   }
   
   private String e;
   
   private String f;
   
   c(String paramString1, String paramString2) {
     this.e = paramString1;
     this.f = paramString2;
   }
   
   public static String b(String paramString) {
     for (c c1 : a()) {
       if (c1.f.equals(paramString))
         return c1.e; 
     } 
     return a.e;
   }
   
   public String getName() {
     return this.e;
   }
   
   public void setName(String name) {
     this.e = name;
   }
   
   public String getValue() {
     return this.f;
   }
   
   public void setValue(String value) {
     this.f = value;
   }
 }


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\cgform\enums\c.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
