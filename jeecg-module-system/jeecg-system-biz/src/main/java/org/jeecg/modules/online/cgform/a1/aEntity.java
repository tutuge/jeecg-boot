package org.jeecg.modules.online.cgform.a1;

public class aEntity {
    private String a;

    private String b;

    private String c;

    private String d;

    private String e;

    private String f;

    private String g;

    private String h;

    public void setTable(String table) {

        this.a = table;
    }

    public void setTxt(String txt) {

        this.b = txt;
    }

    public void setKey(String key) {

        this.c = key;
    }

    public void setLinkField(String linkField) {

        this.d = linkField;
    }

    public void setIdField(String idField) {

        this.e = idField;
    }

    public void setPidField(String pidField) {

        this.f = pidField;
    }

    public void setPidValue(String pidValue) {

        this.g = pidValue;
    }

    public void setCondition(String condition) {

        this.h = condition;
    }


    protected boolean a(Object paramObject) {

        return paramObject instanceof aEntity;
    }


    public String getTable() {
        return this.a;
    }

    public String getTxt() {
        return this.b;
    }

    public String getKey() {
        return this.c;
    }

    public String getLinkField() {
        return this.d;
    }

    public String getIdField() {
        return this.e;
    }

    public String getPidField() {
        return this.f;
    }

    public String getPidValue() {
        return this.g;
    }

    public String getCondition() {
        return this.h;
    }

    private String getQuerySql() {
        StringBuffer stringBuffer = new StringBuffer();
        String str = " ";
        stringBuffer.append("SELECT ");
        return null;
    }
}


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\cgform\a\a.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
