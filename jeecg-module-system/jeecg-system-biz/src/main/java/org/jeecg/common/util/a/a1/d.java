 package org.jeecg.common.util.a.a1;

 import com.alibaba.fastjson.JSONObject;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import org.jeecg.common.system.vo.DictModel;
 import org.jeecg.common.util.a.b1;

 public class d extends b1 {
   private static final long m = -558615331436437200L;

   private Integer n;

   private Integer o;

   private Integer p;

   private Integer q;

   private Integer r;

   private String s;

   public Integer getMultipleOf() {
     return this.n;
   }

   public void setMultipleOf(Integer multipleOf) {
     this.n = multipleOf;
   }

   public Integer getMaxinum() {
     return this.o;
   }

   public void setMaxinum(Integer maxinum) {
     this.o = maxinum;
   }

   public Integer getExclusiveMaximum() {
     return this.p;
   }

   public void setExclusiveMaximum(Integer exclusiveMaximum) {
     this.p = exclusiveMaximum;
   }

   public Integer getMinimum() {
     return this.q;
   }

   public void setMinimum(Integer minimum) {
     this.q = minimum;
   }

   public Integer getExclusiveMinimum() {
     return this.r;
   }

   public void setExclusiveMinimum(Integer exclusiveMinimum) {
     this.r = exclusiveMinimum;
   }

   public String getPattern() {
     return this.s;
   }

   public void setPattern(String pattern) {
     this.s = pattern;
   }

   public d() {}

   public d(String paramString1, String paramString2, String paramString3) {
     this.a = paramString1;
     this.b = paramString3;
     this.f = paramString2;
     this.e = "number";
   }

   public d(String paramString1, String paramString2, String paramString3, List<DictModel> paramList) {
     this.b = "integer";
     this.a = paramString1;
     this.e = paramString3;
     this.f = paramString2;
     this.c = paramList;
   }

   public Map<String, Object> getPropertyJson() {
     HashMap<Object, Object> hashMap = new HashMap<>(5);
     hashMap.put("key", getKey());
     JSONObject jSONObject = getCommonJson();
     if (this.n != null)
       jSONObject.put("multipleOf", this.n);
     if (this.o != null)
       jSONObject.put("maxinum", this.o);
     if (this.p != null)
       jSONObject.put("exclusiveMaximum", this.p);
     if (this.q != null)
       jSONObject.put("minimum", this.q);
     if (this.r != null)
       jSONObject.put("exclusiveMinimum", this.r);
     if (this.s != null)
       jSONObject.put("pattern", this.s);
     hashMap.put("prop", jSONObject);
     return (Map)hashMap;
   }
 }


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\commo\\util\a\a\d.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
