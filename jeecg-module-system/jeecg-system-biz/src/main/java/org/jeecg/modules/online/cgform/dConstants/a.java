 package org.jeecg.modules.online.cgform.dConstants;

 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import org.jeecg.common.system.api.ISysBaseAPI;
 import org.jeecg.common.util.SpringContextUtils;
 import org.jeecg.modules.online.cgform.entity.OnlCgformField;
 import org.jeecgframework.poi.handler.impl.ExcelDataHandlerDefaultImpl;
 import org.jeecgframework.poi.util.PoiPublicUtil;

 public class a extends ExcelDataHandlerDefaultImpl {
   Map<String, OnlCgformField> a;

   ISysBaseAPI b;

   String c;

   String d;

   String e;

   public a(List<OnlCgformField> paramList, String paramString1, String paramString2) {
     this.a = a(paramList);
     this.c = paramString1;
     this.d = "online";
     this.e = paramString2;
     this.b = (ISysBaseAPI)SpringContextUtils.getBean(ISysBaseAPI.class);
   }

   private Map<String, OnlCgformField> a(List<OnlCgformField> paramList) {
     HashMap<Object, Object> hashMap = new HashMap<>();
     for (OnlCgformField onlCgformField : paramList)
       hashMap.put(onlCgformField.getDbFieldTxt(), onlCgformField);
     return (Map)hashMap;
   }

   public void setMapValue(Map<String, Object> map, String originKey, Object value) {
     String str = a(originKey);
     if (value instanceof Double) {
       map.put(str, PoiPublicUtil.doubleToString((Double)value));
     } else if (value instanceof byte[]) {
       byte[] arrayOfByte = (byte[])value;
       String str1 = b.a(arrayOfByte, this.c, this.d, this.e);
       if (str1 != null)
         map.put(str, str1);
     } else {
       map.put(str, (value == null) ? "" : value.toString());
     }
   }

   private String a(String paramString) {
     if (this.a.containsKey(paramString))
       return "$mainTable$" + ((OnlCgformField)this.a.get(paramString)).getDbFieldName();
     return "$subTable$" + paramString;
   }
 }


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\cgform\d\a.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
