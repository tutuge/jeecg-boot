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
        Iterator<String> iterator = a.keySet().iterator();
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
