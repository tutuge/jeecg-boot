//package org.jeecg.modules.cable.entity.userEcable;
//
//import com.baomidou.mybatisplus.annotation.IdType;
//import com.baomidou.mybatisplus.annotation.TableField;
//import com.baomidou.mybatisplus.annotation.TableId;
//import io.swagger.v3.oas.annotations.media.Schema;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.jeecg.modules.cable.entity.systemEcable.EcbMaterials;
//
//import java.math.BigDecimal;
//import java.util.Date;
//
//@Schema(description = "用户导体")
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class EcbuConductor {
//
//    @Schema(description = "主键ID")
//    @TableId(type = IdType.AUTO)
//    private Integer ecbucId;
//
//    @Schema(description = "系统导体ID")
//    private Integer ecbcId;
//
//    @Schema(description = "公司ID")
//    private Integer ecCompanyId;
//
//    @Schema(description = "1 铜 2 铝")
//    private Integer conductorType;
//
//    @Schema(description = "是否启用")
//    private Boolean startType;
//
//    @Schema(description = "自定义名称")
//    private String name;
//
//    @Schema(description = "单价")
//    private BigDecimal unitPrice;
//
//    @Schema(description = "密度")
//    private BigDecimal density;
//
//    @Schema(description = "电阻")
//    private BigDecimal resistivity;
//
//    @Schema(description = "详情")
//    private String description;
//
//    @Schema(description = "创建时间")
//    private Date createTime;
//
//    @Schema(description = "修改时间")
//    private Date updateTime;
//
//    @Schema(description = "系统导体")
//    @TableField(exist = false)
//    private EcbMaterials ecbMaterials;
//
//}
