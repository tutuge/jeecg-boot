package org.jeecg.modules.system.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

/**
 * @Title: DuplicateCheckVo
 * @Description: 重复校验VO
 * @Author 张代浩
 * @Date 2019-03-25
 * @Version V1.0
 */
@Data
@Tag(name = "重复校验数据模型", description = "重复校验数据模型")
public class DuplicateCheckVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 表名
     */
    @Schema(name = "表名", example = "sys_log")
    private String tableName;

    /**
     * 字段名
     */
    @Schema(name = "字段名", example = "id")
    private String fieldName;

    /**
     * 字段值
     */
    @Schema(name = "字段值", example = "1000")
    private String fieldVal;

    /**
     * 数据ID
     */
    @Schema(name = "数据ID", example = "2000")
    private String dataId;

}
