package org.jeecg.modules.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecg.common.validate.AddGroup;
import org.jeecg.modules.cable.entity.user.EcCompany;
import org.jeecg.modules.cable.entity.userCommon.EcduCompany;
import org.jeecg.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @Author scott
 * @since 2018-12-20
 */
@Schema(description = "用户信息")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "用户主键")
    private String id;


    @Excel(name = "用户ID", width = 15)
    @Schema(description = "用户ID")
    private Integer userId;

    @Excel(name = "用户类型 1是后台管理员 2是平台用户 3是普通用户", width = 15)
    @NotNull(message = "userType用户类型不能为空", groups = {AddGroup.class})
    @Schema(description = "用户类型 1是后台管理员 2是平台用户 3是普通用户")
    private Integer userType;

    /**
     * 登录账号
     */
    @Excel(name = "登录账号", width = 15)
    @Schema(description = "登录账号")
    private String username;

    /**
     * 真实姓名
     */
    @Excel(name = "真实姓名", width = 15)
    @Schema(description = "真实姓名")
    private String realname;

    /**
     * 密码
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    /**
     * md5密码盐
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String salt;

    /**
     * 头像
     */
    @Excel(name = "头像", width = 15, type = 2)
    @Schema(description = "头像")
    private String avatar;

    /**
     * 生日
     */
    @Excel(name = "生日", width = 15, format = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "生日")
    private Date birthday;

    /**
     * 性别（1：男 2：女）
     */
    @Excel(name = "性别", width = 15, dicCode = "sex")
    @Dict(dicCode = "sex")
    @Schema(description = "性别")
    private Integer sex;

    /**
     * 电子邮件
     */
    @Excel(name = "电子邮件", width = 15)
    @Schema(description = "电子邮件")
    private String email;

    /**
     * 电话
     */
    @Excel(name = "电话", width = 15)
    @Schema(description = "电话")
    private String phone;

    /**
     * 登录选择部门编码
     */
    @Schema(description = "登录选择部门编码")
    private String orgCode;
    /**
     * 登录选择租户ID
     */
    @Schema(description = "登录选择租户ID")
    private Integer loginTenantId;

    /**
     * 部门名称
     */
    @Schema(description = "部门名称")
    private transient String orgCodeTxt;

    /**
     * 状态(1：正常  2：冻结 ）
     */
    @Schema(description = "状态(1：正常  2：冻结 ）")
    @Excel(name = "状态", width = 15, dicCode = "user_status")
    @Dict(dicCode = "user_status")
    private Integer status;

    /**
     * 删除状态（0，正常，1已删除）
     */
    @Schema(description = "删除状态（0，正常，1已删除）")
    @Excel(name = "删除状态", width = 15, dicCode = "del_flag")
    @TableLogic
    private Integer delFlag;

    /**
     * 工号，唯一键
     */
    @Excel(name = "工号", width = 15)
    @Schema(description = "工号")
    private String workNo;

    /**
     * 职务，关联职务表
     */
    @Excel(name = "职务", width = 15)
    @Dict(dictTable = "sys_position", dicText = "name", dicCode = "code")
    @Schema(description = "职务，关联职务表")
    private String post;

    /**
     * 座机号
     */
    @Excel(name = "座机号", width = 15)
    @Schema(description = "座机号")
    private String telephone;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 同步工作流引擎1同步0不同步
     */
    private Integer activitiSync;

    /**
     * 身份（0 普通成员 1 上级）
     */
    @Excel(name = "（1普通成员 2上级）", width = 15)
    private Integer userIdentity;

    /**
     * 负责部门
     */
    @Excel(name = "负责部门", width = 15, dictTable = "sys_depart", dicText = "depart_name", dicCode = "id")
    @Dict(dictTable = "sys_depart", dicText = "depart_name", dicCode = "id")
    private String departIds;

    /**
     * 多租户ids临时用，不持久化数据库(数据库字段不存在)
     */
    @TableField(exist = false)
    @Schema(description = "租户ID")
    private String relTenantIds;

    /**
     * 设备id uniapp推送用
     */
    private String clientId;

    /**
     * 登录首页地址
     */
    @TableField(exist = false)
    private String homePath;

    /**
     * 职位名称
     */
    @TableField(exist = false)
    private String postText;

    /**
     * 流程状态
     */
    private String bpmStatus;
    /**
     * 公司ID
     */
    @Schema(description = "公司ID")
    private Integer ecCompanyId;

    /**
     * 代号
     */
    private String code;
    /**
     * 简介
     */
    @Schema(description = "简介")
    private String introduction;
    /**
     * 利润
     */
    @Schema(description = "利润")
    private BigDecimal profit;
    /**
     * 到期时间
     */
    @Schema(description = "到期时间")
    private Date expireTime;
    /**
     * 微信账户
     */
    @Schema(description = "微信账户")
    private String wxAccount;

    /**
     * qq账户
     */
    @Schema(description = "qq账户")
    private String qqAccount;

    @Schema(description = "角色信息")
    @TableField(exist = false)
    private List<SysRole> roles;

    @Schema(description = "公司信息")
    @TableField(exist = false)
    private EcCompany ecCompany;


}
