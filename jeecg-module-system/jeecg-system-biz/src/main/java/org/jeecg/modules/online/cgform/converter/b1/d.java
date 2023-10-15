package org.jeecg.modules.online.cgform.converter.b1;

import org.jeecg.modules.online.cgform.converter.a1.aConfig;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;

public class d extends aConfig {
    public d(OnlCgformField paramOnlCgformField) {
        super(paramOnlCgformField.getDictTable(), paramOnlCgformField.getDictField(), paramOnlCgformField.getDictText());
    }
}
