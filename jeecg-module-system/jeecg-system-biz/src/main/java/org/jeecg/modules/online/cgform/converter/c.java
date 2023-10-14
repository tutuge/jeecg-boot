 package org.jeecg.modules.online.cgform.converter;
 
 import java.util.Map;
 import org.springframework.stereotype.Component;
 
 @Component("customDemoConverter")
 public class c implements FieldCommentConverter {
   public String converterToVal(String txt) {
     if (txt != null && "管理员1".equals(txt))
       return "admin"; 
     return txt;
   }
   
   public String converterToTxt(String val) {
     if (val != null) {
       if ("admin".equals(val))
         return "管理员1"; 
       if ("scott".equals(val))
         return "管理员2"; 
     } 
     return val;
   }
   
   public Map<String, String> getConfig() {
     return null;
   }
 }


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\cgform\converter\c.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
