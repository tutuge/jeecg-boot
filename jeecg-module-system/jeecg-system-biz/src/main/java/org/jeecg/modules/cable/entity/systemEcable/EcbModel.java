// package org.jeecg.modules.cable.entity.systemEcable;
//
// import com.baomidou.mybatisplus.annotation.TableName;
// import com.fasterxml.jackson.annotation.JsonFormat;
// import io.swagger.v3.oas.annotations.media.Schema;
// import lombok.Data;
// import lombok.EqualsAndHashCode;
// import lombok.experimental.Accessors;
// import org.jeecgframework.poi.excel.annotation.Excel;
// import org.springframework.format.annotation.DateTimeFormat;
//
// import java.util.Date;
//
// /**
//  * @Description: 系统型号管理
//  * @Author: jeecg-boot
//  * @Date: 2023-10-25
//  * @Version: V1.0
//  */
// @Data
// @TableName("ecb_model")
// @EqualsAndHashCode(callSuper = false)
// @Accessors(chain = true)
// @Schema(name = "ecb_model对象", description = "系统型号管理")
// public class EcbModel {
//
//     /**
//      * 主键ID
//      */
//     @Excel(name = "主键ID", width = 15)
//     @Schema(description = "主键ID")
//     private Integer ecbmId;
//     /**
//      * 型号名称
//      */
//     @Excel(name = "型号名称", width = 15)
//     @Schema(description = "型号名称")
//     private String modelName;
//
//     /**
//      * 是否启用
//      */
//     @Excel(name = "是否启用", width = 15)
//     @Schema(description = "是否启用")
//     private Integer startType;
//     /**
//      * 添加时间
//      */
//     @Excel(name = "添加时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
//     @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
//     @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//     @Schema(description = "添加时间")
//     private Date addTime;
//     /**
//      * 修改时间
//      */
//     @Excel(name = "修改时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
//     @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
//     @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//     @Schema(description = "修改时间")
//     private Date updateTime;
// }
