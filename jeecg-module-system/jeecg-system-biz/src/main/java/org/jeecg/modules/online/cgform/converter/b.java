package org.jeecg.modules.online.cgform.converter;

import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class b {
    public static final int a = 2;

    public static final int b = 1;

    public static void a(int paramInt, List<Map<String, Object>> paramList, List<OnlCgformField> paramList1) {
        Map<String, FieldCommentConverter> map = a2.a(paramList1);

        for (Map<String, Object> map1 : paramList) {

            Iterator<Map.Entry<String, Object>> iterator = map1.entrySet().iterator();

            HashMap<String, Object> hashMap = new HashMap<>(5);

            while (iterator.hasNext()) {

                Map.Entry entry = iterator.next();

                Object object = entry.getValue();

                if (object == null)
                    continue;

                String str = (String) entry.getKey();

                FieldCommentConverter fieldCommentConverter = map.get(str.toLowerCase());

                if (fieldCommentConverter != null) {

                    String str1 = object.toString();

                    String str2 = (paramInt == 1) ? fieldCommentConverter.converterToTxt(str1) : fieldCommentConverter.converterToVal(str1);

                    if (str2 == null)
                        str2 = str1;

                    a(fieldCommentConverter, map1, paramInt);

                    a(fieldCommentConverter, (Map) hashMap, str1);

                    map1.put(str, str2);
                }
            }

            for (String str : hashMap.keySet())

                map1.put(str, hashMap.get(str));
        }
    }

    private static void a(FieldCommentConverter paramFieldCommentConverter, Map<String, Object> paramMap, int paramInt) {

        Map<String, String> map = paramFieldCommentConverter.getConfig();

        if (map != null) {

            String str = map.get("linkField");

            if (oConvertUtils.isNotEmpty(str))
                for (String str1 : str.split(",")) {

                    Object object = paramMap.get(str1);

                    if (object != null) {

                        String str2 = object.toString();

                        String str3 = (paramInt == 1) ? paramFieldCommentConverter.converterToTxt(str2) : paramFieldCommentConverter.converterToVal(str2);
                        paramMap.put(str1, str3);
                    }
                }
        }
    }

    private static void a(FieldCommentConverter paramFieldCommentConverter, Map<String, Object> paramMap, String paramString) {

        Map<String, String> map = paramFieldCommentConverter.getConfig();

        if (map != null) {
            String str = map.get("treeText");
            if (oConvertUtils.isNotEmpty(str))
                paramMap.put(str, paramString);
        }
    }
}
