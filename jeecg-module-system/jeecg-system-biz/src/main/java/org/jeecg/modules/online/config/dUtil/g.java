 package org.jeecg.modules.online.config.dUtil;

 import java.util.Comparator;
 import org.jeecg.modules.online.cgform.entity.OnlCgformField;

 public class g implements Comparator<OnlCgformField> {
   public int a(OnlCgformField paramOnlCgformField1, OnlCgformField paramOnlCgformField2) {
     if (paramOnlCgformField1 == null || paramOnlCgformField1.getOrderNum() == null || paramOnlCgformField2 == null || paramOnlCgformField2.getOrderNum() == null)
       return -1;
     Integer integer1 = paramOnlCgformField1.getOrderNum();
     Integer integer2 = paramOnlCgformField2.getOrderNum();
     return (integer1.intValue() < integer2.intValue()) ? -1 : (integer1.equals(integer2) ? 0 : 1);
   }
 }


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\config\d\g.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
