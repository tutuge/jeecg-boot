 package org.jeecg.common.util.a.a1;

 import com.alibaba.fastjson.JSONObject;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import org.jeecg.common.util.a.aTable;
 import org.jeecg.common.util.a.b1;

 public class c extends b1 {
   String m;

   List<aTable> n;

   public String getDictTable() {
     return this.m;
   }

   public void setDictTable(String dictTable) {
     this.m = dictTable;
   }

   public List<aTable> getOtherColumns() {
     return this.n;
   }

   public void setOtherColumns(List<aTable> otherColumns) {
     this.n = otherColumns;
   }

   public c() {}

   public c(String paramString1, String paramString2, String paramString3) {
     this.b = "string";
     this.e = "link_down";
     this.a = paramString1;
     this.f = paramString2;
     this.m = paramString3;
   }

   public Map<String, Object> getPropertyJson() {
     HashMap<Object, Object> hashMap = new HashMap<>(5);
     hashMap.put("key", getKey());
     JSONObject jSONObject1 = getCommonJson();
     JSONObject jSONObject2 = JSONObject.parseObject(this.m);
     jSONObject1.put("config", jSONObject2);
     jSONObject1.put("others", this.n);
     hashMap.put("prop", jSONObject1);
     return (Map)hashMap;
   }
 }


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\commo\\util\a\a\c.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
