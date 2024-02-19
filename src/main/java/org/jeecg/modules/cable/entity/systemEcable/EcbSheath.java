//package org.jeecg.modules.cable.entity.systemEcable;
//
//import com.baomidou.mybatisplus.annotation.IdType;
//import com.baomidou.mybatisplus.annotation.TableField;
//import com.baomidou.mybatisplus.annotation.TableId;
//import io.swagger.v3.oas.annotations.media.Schema;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.jeecg.modules.cable.entity.userEcable.EcbuSheath;
//
//import java.math.BigDecimal;
//import java.util.Date;
//
//@Schema(description = "系统护套")
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class EcbSheath {
//
//    @Schema(description = "主键ID")
//    @TableId(type = IdType.AUTO)
//    private Integer ecbsId;
//
//    @Schema(description = "管理ID")
//    private Integer ecaId;
//
//    @Schema(description = "管理员名称")
//    private String ecaName;
//
//    @Schema(description = "是否启用")
//    private Boolean startType;
//
//    @Schema(description = "序号")
//    private Integer sortId;
//
//    @Schema(description = "简介")
//    private String abbreviation;
//
//    @Schema(description = "全称")
//    private String fullName;
//
//    @Schema(description = "单价")
//    private BigDecimal unitPrice;
//
//    @Schema(description = "密度")
//    private BigDecimal density;
//
//    @Schema(description = "备注")
//    private String description;
//
//    @Schema(description = "添加时间")
//    private Date addTime;
//
//    @Schema(description = "更新时间")
//    private Date updateTime;
//
//    @Schema(description = "用户护套")
//    @TableField(exist = false)
//    private EcbuSheath ecbuSheath;
//
//    @Schema(description = "公司ID")
//    @TableField(exist = false)
//    private Integer ecCompanyId;
//}
