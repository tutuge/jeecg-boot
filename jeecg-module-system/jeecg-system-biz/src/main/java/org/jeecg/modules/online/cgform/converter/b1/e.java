 package org.jeecg.modules.online.cgform.converter.b1;

 import com.alibaba.fastjson.JSONObject;
 import java.util.HashMap;
 import java.util.Map;
 import org.jeecg.modules.online.cgform.a1.aEntity;
 import org.jeecg.modules.online.cgform.entity.OnlCgformField;

 public class e extends aEntity {
   private String f;

   public String getLinkField() {
     return this.f;
   }

   public void setLinkField(String linkField) {
     this.f = linkField;
   }

   public e(OnlCgformField paramOnlCgformField) {
     String str = paramOnlCgformField.getDictTable();
     aEntity a1 = (aEntity)JSONObject.parseObject(str, aEntity.class);
     setTable(a1.getTable());
     setCode(a1.getKey());
     setText(a1.getTxt());
     this.f = a1.getLinkField();
   }

   public Map<String, String> getConfig() {
     HashMap<Object, Object> hashMap = new HashMap<>(5);
     hashMap.put("linkField", this.f);
     return (Map)hashMap;
   }
 }


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\cgform\converter\b\e.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
