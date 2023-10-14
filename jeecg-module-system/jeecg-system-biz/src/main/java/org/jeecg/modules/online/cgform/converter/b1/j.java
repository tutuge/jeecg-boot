 package org.jeecg.modules.online.cgform.converter.b1;

 import org.jeecg.modules.online.cgform.converter.a2.a;
 import org.jeecg.modules.online.cgform.entity.OnlCgformField;

 public class j extends a {
   public j(OnlCgformField paramOnlCgformField) {
     String str = paramOnlCgformField.getDictText();
     String[] arrayOfString = str.split(",");
     setTable(paramOnlCgformField.getDictTable());
     setCode(arrayOfString[0]);
     setText(arrayOfString[2]);
   }
 }


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\cgform\converter\b\j.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
