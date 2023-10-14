 package org.jeecg.common.util.a.a1;

 import com.alibaba.fastjson.JSONObject;
 import java.util.HashMap;
 import java.util.Map;
 import org.jeecg.common.util.a.b1;

 public class e extends b1 {
   private static final long m = -3200493311633999539L;

   private String n;

   private String o;

   private String p;

   private Boolean q;

   public String getCode() {
     return this.n;
   }

   public void setCode(String code) {
     this.n = code;
   }

   public String getDestFields() {
     return this.o;
   }

   public void setDestFields(String destFields) {
     this.o = destFields;
   }

   public String getOrgFields() {
     return this.p;
   }

   public void setOrgFields(String orgFields) {
     this.p = orgFields;
   }

   public Boolean getPopupMulti() {
     return this.q;
   }

   public void setPopupMulti(Boolean popupMulti) {
     this.q = popupMulti;
   }

   public e() {}

   public e(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) {
     this.e = "popup";
     this.b = "string";
     this.a = paramString1;
     this.f = paramString2;
     this.n = paramString3;
     this.o = paramString4;
     this.p = paramString5;
     this.q = Boolean.valueOf(true);
   }

   public Map<String, Object> getPropertyJson() {
     HashMap<Object, Object> hashMap = new HashMap<>(5);
     hashMap.put("key", getKey());
     JSONObject jSONObject = getCommonJson();
     if (this.n != null)
       jSONObject.put("code", this.n);
     if (this.o != null)
       jSONObject.put("destFields", this.o);
     if (this.p != null)
       jSONObject.put("orgFields", this.p);
     jSONObject.put("popupMulti", this.q);
     hashMap.put("prop", jSONObject);
     return (Map)hashMap;
   }
 }


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\commo\\util\a\a\e.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
