package org.jeecg.modules.online.cgform.converter.b1;

import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.online.cgform.converter.a1.bConfig;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;

import java.util.ArrayList;
import java.util.List;

public class g extends bConfig {
    public g(OnlCgformField paramOnlCgformField) {
        ISysBaseAPI iSysBaseAPI = SpringContextUtils.getBean(ISysBaseAPI.class);
        String str1 = paramOnlCgformField.getDictTable();
        String str2 = paramOnlCgformField.getDictText();
        String str3 = paramOnlCgformField.getDictField();
        List list = new ArrayList<>();
        if (oConvertUtils.isNotEmpty(str1)) {
            List list1 = iSysBaseAPI.queryTableDictItemsByCode(str1, str2, str3);
        } else if (oConvertUtils.isNotEmpty(str3)) {
            list = iSysBaseAPI.queryDictItemsByCode(str3);
        }
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
        return String.join(",", arrayList);
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
        return String.join(",", arrayList);
    }
}
