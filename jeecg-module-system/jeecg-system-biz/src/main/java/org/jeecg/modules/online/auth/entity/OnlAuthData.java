package org.jeecg.modules.online.auth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@TableName("onl_auth_data")
@Tag(name = "onl_auth_data对象", description = "onl_auth_data")
public class OnlAuthData implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    @Schema(name = "主键")
    private String id;

    @Excel(name = "online表ID", width = 15.0D)
    @Schema(name = "online表ID")
    private String cgformId;

    @Excel(name = "规则名", width = 15.0D)
    @Schema(name = "规则名")
    private String ruleName;

    @Excel(name = "规则列", width = 15.0D)
    @Schema(name = "规则列")
    private String ruleColumn;

    @Excel(name = "规则条件 大于小于like", width = 15.0D)
    @Schema(name = "规则条件 大于小于like")
    private String ruleOperator;

    @Excel(name = "规则值", width = 15.0D)
    @Schema(name = "规则值")
    private String ruleValue;

    @Excel(name = "1有效 0无效", width = 15.0D)
    @Schema(name = "1有效 0无效")
    private Integer status;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Schema(name = "创建时间")
    private Date createTime;

    @Schema(name = "创建人")
    private String createBy;

    @Schema(name = "更新人")
    private String updateBy;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Schema(name = "更新日期")
    private Date updateTime;

    public OnlAuthData setId(String id) {
        this.id = id;
        return this;
    }

    public OnlAuthData setCgformId(String cgformId) {
        this.cgformId = cgformId;
        return this;
    }

    public OnlAuthData setRuleName(String ruleName) {
        this.ruleName = ruleName;
        return this;
    }

    public OnlAuthData setRuleColumn(String ruleColumn) {
        this.ruleColumn = ruleColumn;
        return this;
    }

    public OnlAuthData setRuleOperator(String ruleOperator) {
        this.ruleOperator = ruleOperator;
        return this;
    }

    public OnlAuthData setRuleValue(String ruleValue) {
        this.ruleValue = ruleValue;
        return this;
    }

    public OnlAuthData setStatus(Integer status) {
        this.status = status;
        return this;
    }

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    public OnlAuthData setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public OnlAuthData setCreateBy(String createBy) {
        this.createBy = createBy;
        return this;
    }

    public OnlAuthData setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
        return this;
    }

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    public OnlAuthData setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public String getId() {
        return this.id;
    }

    public String getCgformId() {
        return this.cgformId;
    }

    public String getRuleName() {
        return this.ruleName;
    }

    public String getRuleColumn() {
        return this.ruleColumn;
    }

    public String getRuleOperator() {
        return this.ruleOperator;
    }

    public String getRuleValue() {
        return this.ruleValue;
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
}


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\auth\entity\OnlAuthData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
