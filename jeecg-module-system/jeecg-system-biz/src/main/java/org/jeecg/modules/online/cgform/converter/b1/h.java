 package org.jeecg.modules.online.cgform.converter.b1;

 import org.jeecg.common.constant.ProvinceCityArea;
 import org.jeecg.common.util.SpringContextUtils;
 import org.jeecg.common.util.oConvertUtils;
 import org.jeecg.modules.online.cgform.converter.a2.b;
 import org.jeecg.modules.online.cgform.entity.OnlCgformField;

 public class h extends b {
   ProvinceCityArea c;

   public h(OnlCgformField paramOnlCgformField) {
     this.a = paramOnlCgformField.getDbFieldName();
     this.c = (ProvinceCityArea)SpringContextUtils.getBean(ProvinceCityArea.class);
   }

   public String converterToVal(String txt) {
     if (oConvertUtils.isEmpty(txt))
       return null;
     return this.c.getCode(txt);
   }

   public String converterToTxt(String val) {
     if (oConvertUtils.isEmpty(val))
       return null;
     return this.c.getText(val);
   }
 }


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\cgform\converter\b\h.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
