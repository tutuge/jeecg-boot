package org.jeecg.modules.cable.entity.user;

import org.jeecg.modules.cable.entity.quality.EcquLevel;
import org.jeecg.modules.cable.entity.userCommon.EcbulUnit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcProfit {
    private Integer ecpId;//主键ID

    private Integer ecCompanyId;//公司ID

    private String profitName;//利润名称

    private Boolean startType;//是否启用

    private Integer sortId;//序号

    private Integer ecqulId;//质量等级ID

    private String silkName;//丝型号

    private String area;//平方数

    private Integer startNumber;//销售数量 起

    private Integer endNumber;//销售数量 止

    private Integer ecbuluId;//单位

    private BigDecimal startUnitPrice;//单价 开始

    private BigDecimal endUnitPrice;//单价 结束

    private BigDecimal profit;//利润

    private String exceptSilkName;//去除的丝型号名称

    private String description;//备注

    private Long addTime;//添加时间

    private Long updateTime;//修改时间

    private Integer startNum;

    private Integer pageNumber;

    private EcquLevel ecquLevel;//质量等级

    private EcbulUnit ecbulUnit;//单位
}
