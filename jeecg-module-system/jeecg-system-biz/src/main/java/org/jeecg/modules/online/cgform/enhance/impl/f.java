 package org.jeecg.modules.online.cgform.enhance.impl;
 
 import java.util.ArrayList;
 import java.util.List;
 import java.util.Map;
 import org.jeecg.modules.online.cgform.enhance.CgformEnhanceJavaListInter;
 import org.jeecg.modules.online.config.exception.BusinessException;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 import org.springframework.stereotype.Component;
 
 @Component("cgformEnhanceQueryDemo")
 public class f implements CgformEnhanceJavaListInter {
   private static final Logger a = LoggerFactory.getLogger(f.class);
   
   public void execute(String tableName, List<Map<String, Object>> data) throws BusinessException {
     List<a> list = a();
     if (data == null)
       return; 
     for (Map<String, String> map : data) {
       Object object = map.get("province");
       if (object == null)
         continue; 
       String str = list.stream().filter(parama -> paramObject.toString().equals(parama.a())).map(a::b).findAny().orElse("");
       map.put("province", str);
     } 
   }
   
   private List<a> a() {
     ArrayList<a> arrayList = new ArrayList();
     arrayList.add(new a(this, "bj", "北京"));
     arrayList.add(new a(this, "sd", "山东"));
     arrayList.add(new a(this, "ah", "安徽"));
     return arrayList;
   }
   
   class a {
     String a;
     
     String b;
     
     public a(f this$0, String param1String1, String param1String2) {
       this.a = param1String1;
       this.b = param1String2;
     }
     
     public String a() {
       return this.a;
     }
     
     public String b() {
       return this.b;
     }
   }
 }


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\cgform\enhance\impl\f.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
