 package org.jeecg.modules.online.cgform.converter.b1;

 import java.util.HashMap;
 import java.util.Map;
 import org.jeecg.common.util.oConvertUtils;
 import org.jeecg.modules.online.cgform.entity.OnlCgformField;

 public class a extends a {
   private String f;

   public String getTreeText() {
     return this.f;
   }

   public void setTreeText(String treeText) {
     this.f = treeText;
   }

   public a(OnlCgformField paramOnlCgformField) {
     super("SYS_CATEGORY", "ID", "NAME");
     this.f = paramOnlCgformField.getDictText();
     this.b = paramOnlCgformField.getDbFieldName();
   }

   public Map<String, String> getConfig() {
     if (oConvertUtils.isEmpty(this.f))
       return null;
     HashMap<Object, Object> hashMap = new HashMap<>(5);
     hashMap.put("treeText", this.f);
     hashMap.put("field", this.b);
     return (Map)hashMap;
   }
 }


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\cgform\converter\b\a.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
