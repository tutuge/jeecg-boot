package org.jeecg.modules.online.cgform.converter;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component("customDemoConverter")
public class customDemoConverter implements FieldCommentConverter {
    public String converterToVal(String txt) {
        if (txt != null && "管理员1".equals(txt))
            return "admin";
        return txt;
    }

    public String converterToTxt(String val) {
        if (val != null) {
            if ("admin".equals(val))
                return "管理员1";
            if ("scott".equals(val))
                return "管理员2";
        }
        return val;
    }

    public Map<String, String> getConfig() {
        return null;
    }
}
