package org.jeecg.modules.online.cgform.model;

public class cTable {
    private String a;

    private String b;

    private String c;

    public void setField(String field) {
        this.a = field;
    }

    public void setTable(String table) {
        this.b = table;
    }

    public void setKey(String key) {
        this.c = key;
    }

    protected boolean a(Object paramObject) {
        return paramObject instanceof cTable;
    }


    public String getField() {
        return this.a;
    }

    public String getTable() {
        return this.b;
    }

    public String getKey() {
        return this.c;
    }

    public cTable() {
    }

    public cTable(String paramString1, String paramString2) {
        this.c = paramString2;
        this.a = paramString1;
    }
}


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\cgform\model\c.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
