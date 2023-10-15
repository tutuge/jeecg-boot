package org.jeecg.modules.online.config.dUtil;

import org.jeecg.modules.online.cgform.entity.OnlCgformField;

import java.util.Comparator;

public class g implements Comparator<OnlCgformField> {
    public int compare(OnlCgformField paramOnlCgformField1, OnlCgformField paramOnlCgformField2) {
        if (paramOnlCgformField1 == null ||
                paramOnlCgformField1.getOrderNum() == null ||
                paramOnlCgformField2 == null ||
                paramOnlCgformField2.getOrderNum() == null)
            return -1;
        Integer integer1 = paramOnlCgformField1.getOrderNum();
        Integer integer2 = paramOnlCgformField2.getOrderNum();
        return (integer1 < integer2) ? -1 : (integer1.equals(integer2) ? 0 : 1);
    }
}
