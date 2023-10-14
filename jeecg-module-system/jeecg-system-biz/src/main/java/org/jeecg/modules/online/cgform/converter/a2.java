package org.jeecg.modules.online.cgform.converter;

import org.jeecg.common.util.MyClassLoader;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.online.cgform.converter.b1.b;
import org.jeecg.modules.online.cgform.converter.b1.c;
import org.jeecg.modules.online.cgform.converter.b1.*;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class a2 {
    private static final Logger a = LoggerFactory.getLogger(a2.class);

    private static final String b = "list";

    private static final String c = "radio";

    private static final String d = "checkbox";

    private static final String e = "list_multi";

    private static final String f = "sel_search";

    private static final String g = "sel_tree";

    private static final String h = "cat_tree";

    private static final String i = "link_down";

    private static final String j = "sel_depart";

    private static final String k = "sel_user";

    private static final String l = "pca";

    private static final String m = "switch";

    private static final String n = "input";

    public static FieldCommentConverter a(OnlCgformField paramOnlCgformField) {
        g g;
        d d;
        j j;
        a2 a1;
        e e;
        b b;
        k k;
        h h;
        i i;

        String str2, str1 = paramOnlCgformField.getFieldShowType();

        c c2 = null;

        switch (str1) {
            case "list":
            case "radio":

                c2 = new c(paramOnlCgformField);

                return (FieldCommentConverter) c2;
            case "list_multi":
            case "checkbox":
                return (FieldCommentConverter) new g(paramOnlCgformField);
            case "sel_search":
                return (FieldCommentConverter) new d(paramOnlCgformField);
            case "sel_tree":
                return (FieldCommentConverter) new j(paramOnlCgformField);
            case "cat_tree":
                return (FieldCommentConverter) new a2(paramOnlCgformField);
            case "link_down":
                return (FieldCommentConverter) new e(paramOnlCgformField);
            case "sel_depart":
                return (FieldCommentConverter) new b(paramOnlCgformField);
            case "sel_user":
                return (FieldCommentConverter) new k(paramOnlCgformField);
            case "pca":
                return (FieldCommentConverter) new h(paramOnlCgformField);
            case "switch":
                i = new i(paramOnlCgformField);

                return (FieldCommentConverter) i;
            case "input":
                str2 = paramOnlCgformField.getDictField();
                if (str2 == null || "".equals(str2)) {
                    i = null;
                } else {
                    c1 = new c(paramOnlCgformField);
                }

                return (FieldCommentConverter) c1;
        }
        c c1 = null;

        return (FieldCommentConverter) c1;
    }

    public static Map<String, FieldCommentConverter> a(List<OnlCgformField> paramList) {

        HashMap<Object, Object> hashMap = new HashMap<>(5);

        for (OnlCgformField onlCgformField : paramList) {

            FieldCommentConverter fieldCommentConverter = null;

            if (oConvertUtils.isNotEmpty(onlCgformField.getConverter())) {

                fieldCommentConverter = a(onlCgformField.getConverter().trim());
            } else {

                fieldCommentConverter = a(onlCgformField);
            }

            if (fieldCommentConverter == null)
                continue;

            hashMap.put(onlCgformField.getDbFieldName().toLowerCase(), fieldCommentConverter);
        }

        return (Map) hashMap;
    }

    private static FieldCommentConverter a(String paramString) {

        Object object = null;

        if (paramString.indexOf(".") > 0) {
            try {

                object = MyClassLoader.getClassByScn(paramString).newInstance();

            } catch (InstantiationException instantiationException) {

                a.error(instantiationException.getMessage(), instantiationException);

            } catch (IllegalAccessException illegalAccessException) {

                a.error(illegalAccessException.getMessage(), illegalAccessException);
            }
        } else {

            object = SpringContextUtils.getBean(paramString);
        }

        if (object != null && object instanceof FieldCommentConverter)
            return (FieldCommentConverter) object;

        return null;
    }
}
