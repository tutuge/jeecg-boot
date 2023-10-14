package org.jeecg.common.util.a;

public class aTable {
    private String title;

    private String field;

    private Integer order;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setField(String field) {
        this.field = field;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }


    protected boolean a(Object paramObject) {
        return paramObject instanceof aTable;
    }

    public String getTitle() {
        return this.title;
    }

    public String getField() {
        return this.field;
    }

    public Integer getOrder() {
        return this.order;
    }

    public aTable() {
    }

    public aTable(String paramString1, String paramString2, Integer paramInteger) {
        this.title = paramString1;
        this.field = paramString2;
        this.order = paramInteger;
    }
}
