 package org.jeecg.modules.online.cgform.converter.b1;

 import com.alibaba.fastjson.JSONArray;
 import java.util.ArrayList;
 import org.jeecg.common.system.vo.DictModel;
 import org.jeecg.modules.online.cgform.converter.a2.b;
 import org.jeecg.modules.online.cgform.entity.OnlCgformField;

 public class i extends b {
   public i(OnlCgformField paramOnlCgformField) {
     String str1 = paramOnlCgformField.getFieldExtendJson();
     String str2 = "Y", str3 = "N";
     if (str1 != null && !"".equals(str1)) {
       JSONArray jSONArray = JSONArray.parseArray(str1);
       if (jSONArray != null && jSONArray.size() == 2) {
         str2 = jSONArray.get(0).toString();
         str3 = jSONArray.get(1).toString();
       }
     }
     ArrayList<DictModel> arrayList = new ArrayList();
     DictModel dictModel1 = new DictModel(str2, "是");
     DictModel dictModel2 = new DictModel(str3, "否");
     arrayList.add(dictModel1);
     arrayList.add(dictModel2);
     this.b = arrayList;
     this.a = paramOnlCgformField.getDbFieldName();
   }
 }


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\cgform\converter\b\i.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
