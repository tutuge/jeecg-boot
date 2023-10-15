package org.jeecg.modules.online.cgform.converter.b1;

import org.jeecg.common.constant.ProvinceCityArea;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.online.cgform.converter.a1.bConfig;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;

public class h extends bConfig {
    ProvinceCityArea c;

    public h(OnlCgformField paramOnlCgformField) {
        this.a = paramOnlCgformField.getDbFieldName();
        this.c = SpringContextUtils.getBean(ProvinceCityArea.class);
    }

    public String converterToVal(String txt) {
        if (oConvertUtils.isEmpty(txt))
            return null;
        return this.c.getCode(txt);
    }

    public String converterToTxt(String val) {
        if (oConvertUtils.isEmpty(val))
            return null;
        return this.c.getText(val);
    }
}
