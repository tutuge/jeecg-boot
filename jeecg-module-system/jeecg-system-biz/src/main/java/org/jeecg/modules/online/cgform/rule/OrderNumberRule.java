package org.jeecg.modules.online.cgform.rule;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.jeecg.common.handler.IFillRuleHandler;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderNumberRule implements IFillRuleHandler {
    public Object execute(JSONObject params, JSONObject formData) {
        String str1 = "CN";
        if (params != null) {
            Object object = params.get("prefix");
            if (object != null)
                str1 = object.toString();
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        int i = RandomUtils.nextInt(90) + 10;
        String str2 = str1 + simpleDateFormat.format(new Date()) + i;
        String str3 = formData.getString("name");
        if (!StringUtils.isEmpty(str3))
            str2 = str2 + str3;
        return str2;
    }
}
