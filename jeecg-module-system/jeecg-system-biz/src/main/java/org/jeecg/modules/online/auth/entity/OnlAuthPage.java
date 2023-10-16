package org.jeecg.modules.online.auth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@TableName("onl_auth_page")
@Tag(name = "onl_auth_page对象", description = "onl_auth_page")
public class OnlAuthPage implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description  = " 主键")
    private String id;

    @Excel(name = "online表id", width = 15.0D)
    @Schema(description  = "online表id")
    private String cgformId;

    @Excel(name = "字段名/按钮编码", width = 15.0D)
    @Schema(description  = "字段名/按钮编码")
    private String code;

    @Excel(name = "1字段 2按钮", width = 15.0D)
    @Schema(description  = "1字段 2按钮")
    private Integer type;

    @Excel(name = "3可编辑 5可见", width = 15.0D)
    @Schema(description  = "3可编辑 5可见")
    private Integer control;

    @Excel(name = "3列表 5表单", width = 15.0D)
    @Schema(description  = "3列表 5表单")
    private Integer page;

    @Excel(name = "1有效 0无效", width = 15.0D)
    @Schema(description  = "1有效 0无效")
    private Integer status;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Schema(description  = "创建时间")
    @JsonIgnore
    private Date createTime;

    @Schema(description  = "创建人")
    @JsonIgnore
    private String createBy;

    @Schema(description  = "更新人")
    @JsonIgnore
    private String updateBy;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Schema(description  = "更新日期")
    @JsonIgnore
    private Date updateTime;

    public OnlAuthPage setId(String id) {
        this.id = id;
        return this;
    }

    public OnlAuthPage setCgformId(String cgformId) {
        this.cgformId = cgformId;
        return this;
    }

    public OnlAuthPage setCode(String code) {
        this.code = code;
        return this;
    }

    public OnlAuthPage setType(Integer type) {
        this.type = type;
        return this;
    }

    public OnlAuthPage setControl(Integer control) {
        this.control = control;
        return this;
    }

    public OnlAuthPage setPage(Integer page) {
        this.page = page;
        return this;
    }

    public OnlAuthPage setStatus(Integer status) {
        this.status = status;
        return this;
    }

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @JsonIgnore
    public OnlAuthPage setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    @JsonIgnore
    public OnlAuthPage setCreateBy(String createBy) {
        this.createBy = createBy;
        return this;
    }

    @JsonIgnore
    public OnlAuthPage setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
        return this;
    }

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @JsonIgnore
    public OnlAuthPage setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public String getId() {
        return this.id;
    }

    public String getCgformId() {
        return this.cgformId;
    }

    public String getCode() {
        return this.code;
    }

    public Integer getType() {
        return this.type;
    }

    public Integer getControl() {
        return this.control;
    }

    public Integer getPage() {
        return this.page;
    }

    public Integer getStatus() {
        return this.status;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public String getCreateBy() {
        return this.createBy;
    }

    public String getUpdateBy() {
        return this.updateBy;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public OnlAuthPage() {
    }

    public OnlAuthPage(String cgformId, String code, int page, int control) {
        this.type = 1;
        this.cgformId = cgformId;
        this.code = code;
        this.control = Integer.valueOf(control);
        this.page = Integer.valueOf(page);
        this.status = 1;
    }
}


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\auth\entity\OnlAuthPage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
