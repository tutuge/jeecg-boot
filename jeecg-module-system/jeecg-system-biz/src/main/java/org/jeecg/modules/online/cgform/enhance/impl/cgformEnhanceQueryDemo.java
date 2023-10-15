package org.jeecg.modules.online.cgform.enhance.impl;

import org.jeecg.modules.online.cgform.enhance.CgformEnhanceJavaListInter;
import org.jeecg.modules.online.config.exception.BusinessException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component("cgformEnhanceQueryDemo")
public class cgformEnhanceQueryDemo implements CgformEnhanceJavaListInter {

    public void execute(String tableName, List<Map<String, Object>> data) throws BusinessException {
        List<a> list = a();
        if (data == null)
            return;
        for (Map<String, Object> map : data) {
            Object object = map.get("province");
            if (object == null)
                continue;
            String str = list.stream().filter(parama -> object.toString().equals(parama.a()))
                    .map(a::b).findAny().orElse("");
            map.put("province", str);
        }
    }

    private List<a> a() {
        ArrayList<a> arrayList = new ArrayList<>();
        arrayList.add(new a(this, "bj", "北京"));
        arrayList.add(new a(this, "sd", "山东"));
        arrayList.add(new a(this, "ah", "安徽"));
        return arrayList;
    }

    class a {
        String a;

        String b;

        public a(cgformEnhanceQueryDemo this$0, String param1String1, String param1String2) {
            this.a = param1String1;
            this.b = param1String2;
        }

        public String a() {
            return this.a;
        }

        public String b() {
            return this.b;
        }
    }
}
