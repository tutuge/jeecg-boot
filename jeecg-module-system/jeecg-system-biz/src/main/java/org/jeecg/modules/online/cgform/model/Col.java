package org.jeecg.modules.online.cgform.model;

import org.jeecg.common.system.vo.DictModel;
import org.jeecg.modules.online.cgform.entity.OnlCgformButton;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Col implements Serializable {
    private static final long b = 1L;

    private String c;

    private String d;

    private String e;

    private String f;

    private Integer g;

    private String h;

    private String i;

    private Integer j;

    private List<OnlColumn> k;

    private List<String> l;

    public void setCode(String code) {
        this.c = code;
    }

    public void setFormTemplate(String formTemplate) {
        this.d = formTemplate;
    }

    public void setDescription(String description) {
        this.e = description;
    }

    public void setCurrentTableName(String currentTableName) {
        this.f = currentTableName;
    }

    public void setTableType(Integer tableType) {
        this.g = tableType;
    }

    public void setPaginationFlag(String paginationFlag) {
        this.h = paginationFlag;
    }

    public void setCheckboxFlag(String checkboxFlag) {
        this.i = checkboxFlag;
    }

    public void setScrollFlag(Integer scrollFlag) {
        this.j = scrollFlag;
    }

    public void setColumns(List<OnlColumn> columns) {
        this.k = columns;
    }

    public void setHideColumns(List<String> hideColumns) {
        this.l = hideColumns;
    }

    public void setDictOptions(Map<String, List<DictModel>> dictOptions) {
        this.m = dictOptions;
    }

    public void setCgButtonList(List<OnlCgformButton> cgButtonList) {
        this.n = cgButtonList;
    }

    public void setFieldHrefSlots(List<HrefSlots> fieldHrefSlots) {
        this.a = fieldHrefSlots;
    }

    public void setEnhanceJs(String enhanceJs) {
        this.o = enhanceJs;
    }

    public void setForeignKeys(List<cTable> foreignKeys) {
        this.p = foreignKeys;
    }

    public void setPidField(String pidField) {
        this.q = pidField;
    }

    public void setHasChildrenField(String hasChildrenField) {
        this.r = hasChildrenField;
    }

    public void setTextField(String textField) {
        this.s = textField;
    }

    public void setIsDesForm(String isDesForm) {
        this.t = isDesForm;
    }

    public void setDesFormCode(String desFormCode) {
        this.u = desFormCode;
    }

    public void setRelationType(Integer relationType) {
        this.v = relationType;
    }

    protected boolean a(Object paramObject) {
        return paramObject instanceof Col;
    }

    public String getCode() {
        return this.c;
    }

    public String getFormTemplate() {
        return this.d;
    }

    public String getDescription() {
        return this.e;
    }

    public String getCurrentTableName() {
        return this.f;
    }

    public Integer getTableType() {
        return this.g;
    }

    public String getPaginationFlag() {
        return this.h;
    }

    public String getCheckboxFlag() {
        return this.i;
    }

    public Integer getScrollFlag() {
        return this.j;
    }

    public List<OnlColumn> getColumns() {
        return this.k;
    }

    public List<String> getHideColumns() {
        return this.l;
    }

    private Map<String, List<DictModel>> m = new HashMap<>();

    private List<OnlCgformButton> n;

    List<HrefSlots> a;

    private String o;

    private List<cTable> p;

    private String q;

    private String r;

    private String s;

    private String t;

    private String u;

    private Integer v;

    public Map<String, List<DictModel>> getDictOptions() {
        return this.m;
    }

    public List<OnlCgformButton> getCgButtonList() {
        return this.n;
    }

    public List<HrefSlots> getFieldHrefSlots() {
        return this.a;
    }

    public String getEnhanceJs() {
        return this.o;
    }

    public List<cTable> getForeignKeys() {
        return this.p;
    }

    public String getPidField() {
        return this.q;
    }

    public String getHasChildrenField() {
        return this.r;
    }

    public String getTextField() {
        return this.s;
    }

    public String getIsDesForm() {
        return this.t;
    }

    public String getDesFormCode() {
        return this.u;
    }

    public Integer getRelationType() {
        return this.v;
    }
}
