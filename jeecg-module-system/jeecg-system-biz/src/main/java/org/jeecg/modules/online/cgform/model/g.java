 package org.jeecg.modules.online.cgform.model;
 
 public class g {
   private String a;
   
   public void setCustomRender(String customRender) {
     this.a = customRender;
   }
   
   public boolean equals(Object o) {
     if (o == this)
       return true; 
     if (!(o instanceof g))
       return false; 
     g g1 = (g)o;
     if (!g1.a(this))
       return false; 
     String str1 = getCustomRender(), str2 = g1.getCustomRender();
     return !((str1 == null) ? (str2 != null) : !str1.equals(str2));
   }
   
   protected boolean a(Object paramObject) {
     return paramObject instanceof g;
   }
   
   public int hashCode() {
     byte b = 59;
     null = 1;
     String str = getCustomRender();
     return null * 59 + ((str == null) ? 43 : str.hashCode());
   }
   
   public String toString() {
     return "ScopedSlots(customRender=" + getCustomRender() + ")";
   }
   
   public String getCustomRender() {
     return this.a;
   }
   
   public g() {}
   
   public g(String paramString) {
     this.a = paramString;
   }
 }


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\cgform\model\g.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
