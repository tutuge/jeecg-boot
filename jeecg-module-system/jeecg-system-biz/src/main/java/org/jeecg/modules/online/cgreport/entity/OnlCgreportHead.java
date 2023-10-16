package org.jeecg.modules.online.cgreport.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.jeecg.common.aspect.annotation.Dict;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@TableName("onl_cgreport_head")
public class OnlCgreportHead implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    private String code;

    private String name;

    private String cgrSql;

    private String returnValField;

    private String returnTxtField;

    private String returnType;

    @Dict(dicCode = "code", dicText = "name", dictTable = "sys_data_source")
    private String dbSource;

    private String content;

    private String lowAppId;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private String updateBy;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private String createBy;

    public void setId(String id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCgrSql(String cgrSql) {
        this.cgrSql = cgrSql;
    }

    public void setReturnValField(String returnValField) {
        this.returnValField = returnValField;
    }

    public void setReturnTxtField(String returnTxtField) {
        this.returnTxtField = returnTxtField;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public void setDbSource(String dbSource) {
        this.dbSource = dbSource;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setLowAppId(String lowAppId) {
        this.lowAppId = lowAppId;
    }

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getId() {
        return this.id;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public String getCgrSql() {
        return this.cgrSql;
    }

    public String getReturnValField() {
        return this.returnValField;
    }

    public String getReturnTxtField() {
        return this.returnTxtField;
    }

    public String getReturnType() {
        return this.returnType;
    }

    public String getDbSource() {
        return this.dbSource;
    }

    public String getContent() {
        return this.content;
    }

    public String getLowAppId() {
        return this.lowAppId;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public String getUpdateBy() {
        return this.updateBy;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public String getCreateBy() {
        return this.createBy;
    }
}
