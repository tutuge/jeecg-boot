package org.jeecg.modules.online.cgform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

@TableName("onl_cgform_enhance_java")
public class OnlCgformEnhanceJava implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private String cgformHeadId;

    private String buttonCode;

    private String cgJavaType;

    private String cgJavaValue;

    private String activeStatus;

    private String event;

    public void setId(String id) {

        this.id = id;
    }

    public void setCgformHeadId(String cgformHeadId) {

        this.cgformHeadId = cgformHeadId;
    }

    public void setButtonCode(String buttonCode) {

        this.buttonCode = buttonCode;
    }

    public void setCgJavaType(String cgJavaType) {

        this.cgJavaType = cgJavaType;
    }

    public void setCgJavaValue(String cgJavaValue) {

        this.cgJavaValue = cgJavaValue;
    }

    public void setActiveStatus(String activeStatus) {

        this.activeStatus = activeStatus;
    }

    public void setEvent(String event) {

        this.event = event;
    }


    public String getId() {

        return this.id;
    }

    public String getCgformHeadId() {
        
        return this.cgformHeadId;
    }

    public String getButtonCode() {
        
        return this.buttonCode;
    }

    public String getCgJavaType() {
        
        return this.cgJavaType;
    }

    public String getCgJavaValue() {
        
        return this.cgJavaValue;
    }

    public String getActiveStatus() {
        
        return this.activeStatus;
    }

    public String getEvent() {
        
        return this.event;
    }
}


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\cgform\entity\OnlCgformEnhanceJava.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
