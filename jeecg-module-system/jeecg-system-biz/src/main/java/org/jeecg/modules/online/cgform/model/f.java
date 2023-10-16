package org.jeecg.modules.online.cgform.model;

import org.jeecg.common.system.vo.SysPermissionDataRuleModel;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;

import java.util.List;

public class f {
    private String a;

    private String b;

    private List<OnlCgformField> c;

    private List<OnlCgformField> d;

    private List<SysPermissionDataRuleModel> e;

    private String f;

    private String g;

    private String h;

    private boolean i;

    public void setTableName(String tableName) {
        this.a = tableName;
    }

    public void setTableId(String tableId) {
        this.b = tableId;
    }

    public void setAllFieldList(List<OnlCgformField> allFieldList) {
        this.c = allFieldList;
    }

    public void setSelectFieldList(List<OnlCgformField> selectFieldList) {
        this.d = selectFieldList;
    }

    public void setAuthList(List<SysPermissionDataRuleModel> authList) {
        this.e = authList;
    }

    public void setMainField(String mainField) {
        this.f = mainField;
    }

    public void setJoinField(String joinField) {
        this.g = joinField;
    }

    public void setAlias(String alias) {
        this.h = alias;
    }

    public void setMain(boolean isMain) {
        this.i = isMain;
    }

    protected boolean a(Object paramObject) {
        return paramObject instanceof f;
    }


    public String getTableName() {
        return this.a;
    }

    public String getTableId() {
        return this.b;
    }

    public List<OnlCgformField> getAllFieldList() {
        return this.c;
    }

    public List<OnlCgformField> getSelectFieldList() {
        return this.d;
    }

    public List<SysPermissionDataRuleModel> getAuthList() {
        return this.e;
    }

    public String getMainField() {
        return this.f;
    }

    public String getJoinField() {
        return this.g;
    }

    public String getAlias() {
        return this.h;
    }

    public boolean a() {
        return this.i;
    }

    public void setAliasByIntValue(int index) {
        char c = (char) index;
        this.h = String.valueOf(c);
    }

    public String getTableAlias() {
        return this.h + ".";
    }

    public f() {
    }

    public f(String paramString1, String paramString2, boolean paramBoolean) {
        this.a = paramString1;
        this.b = paramString2;
        this.i = paramBoolean;
    }
}


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\cgform\model\f.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
