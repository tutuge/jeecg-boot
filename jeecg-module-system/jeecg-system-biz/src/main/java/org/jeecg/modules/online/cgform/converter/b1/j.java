package org.jeecg.modules.online.cgform.converter.b1;

import org.jeecg.modules.online.cgform.converter.a1.aConfig;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;

public class j extends aConfig {
    public j(OnlCgformField paramOnlCgformField) {
        String str = paramOnlCgformField.getDictText();
        String[] arrayOfString = str.split(",");
        setTable(paramOnlCgformField.getDictTable());
        setCode(arrayOfString[0]);
        setText(arrayOfString[2]);
    }
}
