 package org.jeecg.modules.online.config.dUtil;

 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.Map;

 public class f {
   protected static Map<String, String> a = new HashMap<>(5);

   static {
     a.put("class", "clazz");
   }

   private static String a(String paramString, int paramInt) {
     String str = paramString;
     Iterator iterator = a.keySet().iterator();
     while (iterator.hasNext()) {
       String str1 = String.valueOf(iterator.next());
       String str2 = String.valueOf(a.get(str1));
       if (paramInt == 1) {
         str = paramString.replaceAll(str1, str2);
         continue;
       }
       if (paramInt == 2)
         str = paramString.replaceAll(str2, str1);
     }
     return str;
   }
 }


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\config\d\f.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
