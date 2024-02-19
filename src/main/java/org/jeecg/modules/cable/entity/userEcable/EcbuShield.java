//package org.jeecg.modules.cable.entity.userEcable;
//
//import com.baomidou.mybatisplus.annotation.IdType;
//import com.baomidou.mybatisplus.annotation.TableField;
//import com.baomidou.mybatisplus.annotation.TableId;
//import io.swagger.v3.oas.annotations.media.Schema;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.jeecg.modules.cable.entity.systemEcable.EcbShield;
//
//import java.math.BigDecimal;
//import java.util.Date;
//
//@Schema(description = "用户屏蔽")
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class EcbuShield {
//    @Schema(description = "主键ID")
//    @TableId(type = IdType.AUTO)
//    private Integer ecbusId;
//
//    @Schema(description = "系统屏蔽ID")
//    private Integer ecbsId;
//
//    @Schema(description = "公司ID")
//    private Integer ecCompanyId;
//
//    @Schema(description = "是否启用")
//    private Boolean startType;
//
//    @Schema(description = "自定义名称")
//    private String name;
//
//    @Schema(description = "自定义名称")
//    private BigDecimal unitPrice;
//
//    @Schema(description = "密度")
//    private BigDecimal density;
//
//    @Schema(description = "备注")
//    private String description;
//
//    @Schema(description = "创建时间")
//    private Date createTime;
//
//    @Schema(description = "修改时间")
//    private Date updateTime;
//
//    @Schema(description = "系统屏蔽")
//    @TableField(exist = false)
//    private EcbShield ecbShield;
//}
