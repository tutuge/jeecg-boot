package org.jeecg.modules.cable.controller.userCommon.contract.vo;

import lombok.Data;

/**
 * 为导出doc设置的vo类
 */
@Data
public class DetailVo {
    /**
     * 序号
     */
    private String no;
    /**
     * 型号
     */
    private String modelName;
    /**
     * 规格
     */
    private String specifications;
    /**
     * 数量
     */
    private String quantity;
    /**
     * 单价
     */
    private String unitPrice;
    /**
     * 小计
     */
    private String subtotal;
    /**
     * 备注
     */
    private String remarks;
}
