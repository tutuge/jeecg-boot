package org.jeecg.modules.online.cgform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

@TableName("onl_cgform_button")
public class OnlCgformButton implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private String cgformHeadId;

    private String buttonCode;

    private String buttonName;

    private String buttonStyle;

    private String optType;

    private String exp;

    private String buttonStatus;

    private Integer orderNum;

    private String buttonIcon;

    private String optPosition;

    public void setId(String id) {
        this.id = id;
    }

    public void setCgformHeadId(String cgformHeadId) {
        this.cgformHeadId = cgformHeadId;
    }

    public void setButtonCode(String buttonCode) {
        this.buttonCode = buttonCode;
    }

    public void setButtonName(String buttonName) {
        this.buttonName = buttonName;
    }

    public void setButtonStyle(String buttonStyle) {
        this.buttonStyle = buttonStyle;
    }

    public void setOptType(String optType) {
        this.optType = optType;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public void setButtonStatus(String buttonStatus) {
        this.buttonStatus = buttonStatus;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public void setButtonIcon(String buttonIcon) {
        this.buttonIcon = buttonIcon;
    }

    public void setOptPosition(String optPosition) {
        this.optPosition = optPosition;
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

    public String getButtonName() {
        return this.buttonName;
    }

    public String getButtonStyle() {
        return this.buttonStyle;
    }

    public String getOptType() {
        return this.optType;
    }

    public String getExp() {
        return this.exp;
    }

    public String getButtonStatus() {
        return this.buttonStatus;
    }

    public Integer getOrderNum() {
        return this.orderNum;
    }

    public String getButtonIcon() {
        return this.buttonIcon;
    }

    public String getOptPosition() {
        return this.optPosition;
    }
}
