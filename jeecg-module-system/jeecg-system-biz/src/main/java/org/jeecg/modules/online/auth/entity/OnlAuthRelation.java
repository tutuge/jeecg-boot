package org.jeecg.modules.online.auth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.io.Serializable;

@TableName("onl_auth_relation")
@Tag(name = "onl_auth_relation对象", description = "onl_auth_relation")
public class OnlAuthRelation implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description  = "id")
    private String id;

    @Excel(name = "角色id", width = 15.0D)
    @Schema(description  = "角色id")
    private String roleId;

    @Excel(name = "权限id", width = 15.0D)
    @Schema(description  = "权限id")
    private String authId;

    @Excel(name = "1字段 2按钮 3数据权限", width = 15.0D)
    @Schema(description  = "1字段 2按钮 3数据权限")
    private Integer type;

    private String cgformId;

    private String authMode;

    public OnlAuthRelation setId(String id) {
        this.id = id;
        return this;
    }

    public OnlAuthRelation setRoleId(String roleId) {
        this.roleId = roleId;
        return this;
    }

    public OnlAuthRelation setAuthId(String authId) {
        this.authId = authId;
        return this;
    }

    public OnlAuthRelation setType(Integer type) {
        this.type = type;
        return this;
    }

    public OnlAuthRelation setCgformId(String cgformId) {
        this.cgformId = cgformId;
        return this;
    }

    public OnlAuthRelation setAuthMode(String authMode) {
        this.authMode = authMode;
        return this;
    }

    public String getId() {
        return this.id;
    }

    public String getRoleId() {
        return this.roleId;
    }

    public String getAuthId() {
        return this.authId;
    }

    public Integer getType() {
        return this.type;
    }

    public String getCgformId() {
        return this.cgformId;
    }

    public String getAuthMode() {
        return this.authMode;
    }
}
