 package org.jeecg.modules.online.cgform.converter.b1;

 import com.alibaba.fastjson.JSON;
 import com.alibaba.fastjson.JSONObject;
 import java.util.ArrayList;
 import java.util.List;
 import org.jeecg.common.system.api.ISysBaseAPI;
 import org.jeecg.common.util.SpringContextUtils;
 import org.jeecg.common.util.oConvertUtils;
 import org.jeecg.modules.online.cgform.entity.OnlCgformField;

 public class b extends b {
   public b(OnlCgformField paramOnlCgformField) {
     ISysBaseAPI iSysBaseAPI = (ISysBaseAPI)SpringContextUtils.getBean(ISysBaseAPI.class);
     String str1 = "SYS_DEPART";
     String str2 = "DEPART_NAME";
     String str3 = "ID";
     String str4 = paramOnlCgformField.getFieldExtendJson();
     if (oConvertUtils.isNotEmpty(str4)) {
       JSONObject jSONObject = JSON.parseObject(str4);
       if (jSONObject.containsKey("store")) {
         String str = jSONObject.getString("store");
         str3 = oConvertUtils.camelToUnderline(str);
       }
       if (jSONObject.containsKey("text")) {
         String str = jSONObject.getString("text");
         str2 = oConvertUtils.camelToUnderline(str);
       }
     }
     List list = iSysBaseAPI.queryTableDictItemsByCode(str1, str2, str3);
     this.b = list;
     this.a = paramOnlCgformField.getDbFieldName();
   }

   public String converterToVal(String txt) {
     if (oConvertUtils.isEmpty(txt))
       return null;
     ArrayList<String> arrayList = new ArrayList<>();
     for (String str1 : txt.split(",")) {
       String str2 = super.converterToVal(str1);
       if (str2 != null)
         arrayList.add(str2);
     }
     return String.join(",", (Iterable)arrayList);
   }

   public String converterToTxt(String val) {
     if (oConvertUtils.isEmpty(val))
       return null;
     ArrayList<String> arrayList = new ArrayList<>();
     for (String str1 : val.split(",")) {
       String str2 = super.converterToTxt(str1);
       if (str2 != null)
         arrayList.add(str2);
     }
     return String.join(",", (Iterable)arrayList);
   }
 }


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\cgform\converter\b\b.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
