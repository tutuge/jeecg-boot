package org.jeecg.common.util.a;

import com.alibaba.fastjson.JSONObject;
import org.jeecg.common.system.vo.DictModel;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public abstract class b1 implements Serializable {
    private static final long m = -426159949502493187L;

    protected String a;

    protected String b;

    protected List<DictModel> c;

    protected Object d;

    protected String e;

    protected String f;

    protected Integer g;

    protected boolean h;

    protected String i;

    protected String j;

    protected Integer k;

    protected String l;

    public String getDefVal() {
        return this.i;
    }

    public void setDefVal(String defVal) {
        this.i = defVal;
    }

    public boolean a() {
        return this.h;
    }

    public void setDisabled(boolean disabled) {
        this.h = disabled;
    }

    public String getView() {
        return this.e;
    }

    public void setView(String view) {
        this.e = view;
    }

    public String getKey() {
        return this.a;
    }

    public void setKey(String key) {
        this.a = key;
    }

    public String getType() {
        return this.b;
    }

    public void setType(String type) {
        this.b = type;
    }

    public List<DictModel> getInclude() {
        return this.c;
    }

    public void setInclude(List<DictModel> include) {
        this.c = include;
    }

    public Object getConstant() {
        return this.d;
    }

    public void setConstant(Object constant) {
        this.d = constant;
    }

    public String getTitle() {
        return this.f;
    }

    public void setTitle(String title) {
        this.f = title;
    }

    public Integer getOrder() {
        return this.g;
    }

    public void setOrder(Integer order) {
        this.g = order;
    }

    public String getFieldExtendJson() {
        return this.j;
    }

    public void setFieldExtendJson(String fieldExtendJson) {
        this.j = fieldExtendJson;
    }

    public Integer getDbPointLength() {
        return this.k;
    }

    public void setDbPointLength(Integer dbPointLength) {
        this.k = dbPointLength;
    }

    public String getMode() {
        return this.l;
    }

    public void setMode(String mode) {
        this.l = mode;
    }

    public abstract Map<String, Object> getPropertyJson();

    public JSONObject getCommonJson() {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("type", this.b);
        if (this.c != null && this.c.size() > 0)
            jSONObject.put("enum", this.c);
        if (this.d != null)
            jSONObject.put("const", this.d);
        if (this.f != null)
            jSONObject.put("title", this.f);
        if (this.g != null)
            jSONObject.put("order", this.g);
        if (this.e == null) {
            jSONObject.put("view", "input");
        } else {
            jSONObject.put("view", this.e);
        }
        if (this.h) {
            String str = "{\"widgetattrs\":{\"disabled\":true}}";
            JSONObject jSONObject1 = JSONObject.parseObject(str);
            jSONObject.put("ui", jSONObject1);
        }
        if (this.i != null && this.i.length() > 0)
            jSONObject.put("defVal", this.i);
        if (this.j != null)
            jSONObject.put("fieldExtendJson", this.j);
        if (this.k != null)
            jSONObject.put("dbPointLength", this.k);
        if (this.l != null)
            jSONObject.put("mode", this.l);
        return jSONObject;
    }
}


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\commo\\util\a\b.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
