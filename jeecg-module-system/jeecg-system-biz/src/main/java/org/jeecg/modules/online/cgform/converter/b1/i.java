package org.jeecg.modules.online.cgform.converter.b1;

import com.alibaba.fastjson.JSONArray;
import org.jeecg.common.system.vo.DictModel;
import org.jeecg.modules.online.cgform.converter.a1.bConfig;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;

import java.util.ArrayList;

public class i extends bConfig {
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
        ArrayList<DictModel> arrayList = new ArrayList<>();
        DictModel dictModel1 = new DictModel(str2, "是");
        DictModel dictModel2 = new DictModel(str3, "否");
        arrayList.add(dictModel1);
        arrayList.add(dictModel2);
        this.b = arrayList;
        this.a = paramOnlCgformField.getDbFieldName();
    }
}
