package org.jeecg.modules.online.cgform.converter.b1;

import com.alibaba.fastjson.JSONObject;
import org.jeecg.modules.online.cgform.a1.aEntity;
import org.jeecg.modules.online.cgform.converter.a1.aConfig;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;

import java.util.HashMap;
import java.util.Map;

public class e extends aConfig {
    private String f;

    public String getLinkField() {
        return this.f;
    }

    public void setLinkField(String linkField) {
        this.f = linkField;
    }

    public e(OnlCgformField paramOnlCgformField) {
        String str = paramOnlCgformField.getDictTable();
        aEntity a1 = JSONObject.parseObject(str, aEntity.class);
        setTable(a1.getTable());
        setCode(a1.getKey());
        setText(a1.getTxt());
        this.f = a1.getLinkField();
    }

    public Map<String, String> getConfig() {
        HashMap<Object, Object> hashMap = new HashMap<>(5);
        hashMap.put("linkField", this.f);
        return (Map) hashMap;
    }
}
