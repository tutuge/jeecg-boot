package org.jeecg.modules.online.cgform.converter.b1;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.common.util.SqlInjectionUtil;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.online.cgform.converter.a1.bConfig;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;

import java.util.ArrayList;
import java.util.List;

public class k extends bConfig {
    public k(OnlCgformField paramOnlCgformField) {
        ISysBaseAPI iSysBaseAPI = (ISysBaseAPI) SpringContextUtils.getBean(ISysBaseAPI.class);
        String str1 = "SYS_USER";
        String str2 = "REALNAME";
        String str3 = "USERNAME";
        String str4 = paramOnlCgformField.getFieldExtendJson();
        if (oConvertUtils.isNotEmpty(str4)) {
            JSONObject jSONObject = JSON.parseObject(str4);
            if (jSONObject.containsKey("store")) {
                String str = jSONObject.getString("store");
                SqlInjectionUtil.filterContent(str);
                str3 = oConvertUtils.camelToUnderline(str);
            }
            if (jSONObject.containsKey("text")) {
                String str = jSONObject.getString("text");
                SqlInjectionUtil.filterContent(str);
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
        return String.join(",", (Iterable) arrayList);
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
