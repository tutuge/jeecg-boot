package org.jeecg.modules.online.auth.vo;

import org.jeecg.modules.online.cgform.entity.OnlCgformField;

import java.io.Serializable;

public class AuthColumnVO implements Serializable {
    private static final long serialVersionUID = 5445993027926933917L;

    private String id;

    private String cgformId;

    public void setId(String id) {
        this.id = id;
    }

    public void setCgformId(String cgformId) {
        this.cgformId = cgformId;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setListShow(boolean listShow) {
        this.listShow = listShow;
    }

    public void setFormShow(boolean formShow) {
        this.formShow = formShow;
    }

    public void setFormEditable(boolean formEditable) {
        this.formEditable = formEditable;
    }

    public void setIsShowForm(Integer isShowForm) {
        this.isShowForm = isShowForm;
    }

    public void setIsShowList(Integer isShowList) {
        this.isShowList = isShowList;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setTableNameTxt(String tableNameTxt) {
        this.tableNameTxt = tableNameTxt;
    }

    public void setSwitchFlag(int switchFlag) {
        this.switchFlag = switchFlag;
    }

    public void setDbIsPersist(Integer dbIsPersist) {
        this.dbIsPersist = dbIsPersist;
    }

    public void setIsMain(Boolean isMain) {
        this.isMain = isMain;
    }

    public String getId() {
        return this.id;
    }

    public String getCgformId() {
        return this.cgformId;
    }

    private Integer type = Integer.valueOf(1);

    private String code;

    private String title;

    private Integer status;

    private boolean listShow;

    private boolean formShow;

    private boolean formEditable;

    private Integer isShowForm;

    private Integer isShowList;

    private String tableName;

    private String tableNameTxt;

    private int switchFlag;

    private Integer dbIsPersist;

    private Boolean isMain;

    public Integer getType() {
        return this.type;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public Integer getStatus() {
        return this.status;
    }

    public boolean isListShow() {
        return this.listShow;
    }

    public boolean isFormShow() {
        return this.formShow;
    }

    public boolean isFormEditable() {
        return this.formEditable;
    }

    public Integer getIsShowForm() {
        return this.isShowForm;
    }

    public Integer getIsShowList() {
        return this.isShowList;
    }

    public String getTableName() {
        return this.tableName;
    }

    public String getTableNameTxt() {
        return this.tableNameTxt;
    }

    public int getSwitchFlag() {
        return this.switchFlag;
    }

    public Integer getDbIsPersist() {
        return this.dbIsPersist;
    }

    public Boolean getIsMain() {
        return this.isMain;
    }

    public AuthColumnVO(OnlCgformField field) {
        this.id = field.getId();
        this.cgformId = field.getCgformHeadId();
        this.code = field.getDbFieldName();
        this.title = field.getDbFieldTxt();
        this.type = Integer.valueOf(1);
        this.isShowForm = field.getIsShowForm();
        this.isShowList = field.getIsShowList();
        this.dbIsPersist = field.getDbIsPersist();
    }

    public AuthColumnVO() {
    }
}


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\auth\vo\AuthColumnVO.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
