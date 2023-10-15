package org.jeecg.modules.online.cgform.converter.b1;

import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.online.cgform.converter.a1.bConfig;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;

import java.util.ArrayList;
import java.util.List;

public class c extends bConfig {
    public c(OnlCgformField paramOnlCgformField) {
        ISysBaseAPI iSysBaseAPI = (ISysBaseAPI) SpringContextUtils.getBean(ISysBaseAPI.class);
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
}
