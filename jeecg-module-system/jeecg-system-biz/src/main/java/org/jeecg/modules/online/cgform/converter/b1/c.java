 package org.jeecg.modules.online.cgform.converter.b1;

 import java.util.ArrayList;
 import java.util.List;
 import org.jeecg.common.system.api.ISysBaseAPI;
 import org.jeecg.common.util.SpringContextUtils;
 import org.jeecg.common.util.oConvertUtils;
 import org.jeecg.modules.online.cgform.converter.a2.b;
 import org.jeecg.modules.online.cgform.entity.OnlCgformField;

 public class c extends b {
   public c(OnlCgformField paramOnlCgformField) {
     ISysBaseAPI iSysBaseAPI = (ISysBaseAPI)SpringContextUtils.getBean(ISysBaseAPI.class);
     String str1 = paramOnlCgformField.getDictTable();
     String str2 = paramOnlCgformField.getDictText();
     String str3 = paramOnlCgformField.getDictField();
     List list = new ArrayList();
     if (oConvertUtils.isNotEmpty(str1)) {
       List list1 = iSysBaseAPI.queryTableDictItemsByCode(str1, str2, str3);
     } else if (oConvertUtils.isNotEmpty(str3)) {
       list = iSysBaseAPI.queryDictItemsByCode(str3);
     }
     this.b = list;
     this.a = paramOnlCgformField.getDbFieldName();
   }
 }


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\cgform\converter\b\c.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
