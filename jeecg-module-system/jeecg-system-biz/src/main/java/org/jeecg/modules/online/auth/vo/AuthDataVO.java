package org.jeecg.modules.online.auth.vo;

import java.io.Serializable;

public class AuthDataVO implements Serializable {
    private static final long serialVersionUID = 1057819436991228603L;

    private String id;

    private String title;

    private String relId;

    private Boolean checked;

    public void setId(String id) {
        
        this.id = id;
    }

    public void setTitle(String title) {
        
        this.title = title;
    }

    public void setRelId(String relId) {
        
        this.relId = relId;
    }

    public void setChecked(Boolean checked) {
        
        this.checked = checked;
    }

    public String getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getRelId() {
        return this.relId;
    }

    public Boolean getChecked() {
        return this.checked;
    }

    public Boolean isChecked() {
        return Boolean.valueOf((this.relId != null && this.relId.length() > 0));
    }
}


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\auth\vo\AuthDataVO.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
