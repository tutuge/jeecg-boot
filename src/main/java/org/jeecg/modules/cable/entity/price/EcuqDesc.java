package org.jeecg.modules.cable.entity.price;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.jeecg.modules.cable.entity.systemEcable.EcbSheath;
import org.jeecg.modules.cable.entity.userEcable.*;

import java.math.BigDecimal;

@Data
public class EcuqDesc {

    @Schema(description = "主键ID")
    @TableId(type = IdType.AUTO)
    private Integer ecuqdId;// 主键ID

    @Schema(description = "报价单ID")
    private Integer ecuqId;// 报价单ID

    @Schema(description = "仓库数据ID")
    private Integer ecuoId;// 库数据ID

    @Schema(description = "EcuqInput 的Id")
    private Integer ecuqiId;// inputId

    @Schema(description = "质量等级ID")
    private Integer ecqulId;// 质量等级ID

    @Schema(description = "用户导体id")
    private Integer ecbucId;// 用户导体id

    @Schema(description = "仓库ID")
    private Integer storeId;// 仓库ID

    @Schema(description = "是否启用")
    private Boolean startType;// 是否启用

    @Schema(description = "序号")
    private Integer sortId;// 序号

    @Schema(description = "导体单价")
    private BigDecimal cunitPrice;// 导体单价

    @Schema(description = "导体重量")
    private BigDecimal cweight;// 导体重量

    @Schema(description = "仓库利润")
    private BigDecimal storePercent;// 仓库利润

    @Schema(description = "仓库运费加点")
    private BigDecimal sdunitMoney;// 仓库运费加点

    @Schema(description = "截面")
    private String areaStr;// 截面

    @Schema(description = "成本加点")
    private BigDecimal addPercent;// 成本加点

    @Schema(description = "粗芯丝号")
    private BigDecimal fireSilkNumber;// 粗芯丝号

    @Schema(description = "粗芯根数")
    private Integer fireRootNumber;// 粗芯根数

    @Schema(description = "粗芯过膜")
    private Integer fireMembrance;// 粗芯过膜

    @Schema(description = "粗芯压型")
    private BigDecimal firePress;// 粗芯压型

    @Schema(description = "粗芯绞合系数")
    private BigDecimal fireStrand;// 粗芯绞合系数

    @Schema(description = "细芯丝号")
    private BigDecimal zeroSilkNumber;// 细芯丝号

    @Schema(description = "细芯根数")
    private Integer zeroRootNumber;// 细芯根数

    @Schema(description = "细芯过膜")
    private Integer zeroMembrance;// 细芯过膜

    @Schema(description = "细芯压型")
    private BigDecimal zeroPress;// 细芯压型

    @Schema(description = "细芯绞合系数")
    private BigDecimal zeroStrand;// 细芯绞合系数

    @Schema(description = "用户绝缘ID")
    private Integer ecbuiId;// 用户绝缘ID

    @Schema(description = "绝缘粗芯厚度")
    private BigDecimal insulationFireThickness;// 绝缘粗芯厚度

    @Schema(description = "绝缘细芯厚度")
    private BigDecimal insulationZeroThickness;// 绝缘细芯厚度

    @Schema(description = "包带ID")
    private Integer ecbubId;// 包带ID

    @Schema(description = "包带厚度")
    private BigDecimal bagThickness;// 包带厚度

    @Schema(description = "铠装包带ID")
    @TableField("ecbub_22_id")
    private Integer ecbub22Id;// 铠装包带ID

    @Schema(description = "铠装包带厚度")
    @TableField("bag_22_thickness")
    private BigDecimal bag22Thickness;// 铠装包带厚度

    @Schema(description = "用户屏蔽ID")
    private Integer ecbuShieldId;// 用户屏蔽ID

    @Schema(description = "屏蔽厚度")
    private BigDecimal shieldThickness;// 屏蔽厚度

    @Schema(description = "屏蔽编织系数")
    private BigDecimal shieldPercent;// 屏蔽编织系数

    @Schema(description = "用户钢带ID")
    private Integer ecbusbId;// 用户钢带ID

    @Schema(description = "钢带厚度")
    private BigDecimal steelbandThickness;// 钢带厚度

    @Schema(description = "钢带层数")
    private Integer steelbandStorey;// 钢带层数

    @Schema(description = "用户护套ID")
    private Integer ecbuSheathId;// 用户护套ID

    @Schema(description = "护套厚度")
    private BigDecimal sheathThickness;// 护套厚度

    @Schema(description = "铠装护套厚度")
    @TableField("sheath_22_thickness")
    private BigDecimal sheath22Thickness;// 铠装护套厚度

    @Schema(description = "云母带ID")
    private Integer ecbumId;// 云母带ID

    @Schema(description = "云母带厚度")
    private BigDecimal micatapeThickness;// 云母带厚度

    @Schema(description = "用户填充物ID")
    private Integer ecbuinId;// 用户填充物ID

    @Schema(description = "钢丝ID")
    private Integer ecbuswId;// 钢丝ID

    @Schema(description = "钢丝过膜")
    private BigDecimal steelwireMembrance;// 钢丝过膜

    @Schema(description = "钢丝压型")
    private BigDecimal steelwirePress;// 钢丝压型

    @Schema(description = "是否启动手输 true 开启手输 false 不开启")
    private Boolean inputStart;// 是否启动手输

    @Schema(description = "不开票的单价")
    private BigDecimal nbupsMoney;// no bill unit price single money 不开票的单价

    @Schema(description = "开票单价")
    private BigDecimal bupsMoney;// bill unit price single money 开票单价

    @Schema(description = "不开票小计")
    private BigDecimal nbupcMoney;// no bill unit price compute money 不开票小计

    @Schema(description = "开票小计")
    private BigDecimal bupcMoney;// bill unit price compute money 开票小计

    @Schema(description = "重量")
    private BigDecimal weight;// 重量

    @Schema(description = "税前单价是否手输")
    private Boolean unitPriceInput;// 税前单价是否手输

    @Schema(description = "税前单价")
    private BigDecimal unitPrice;// 税前单价

    @Schema(description = "1米的重量")
    private BigDecimal unitWeight;// 1米的重量

    @Schema(description = "木轴ID")
    private Integer ecbuaId;// 木轴ID

    @Schema(description = "木轴的数量")
    private Integer axleNumber;// 木轴的数量

    @Schema(description = "添加时间")
    private Long addTime;// 添加时间

    @Schema(description = "用户云母带")
    @TableField(exist = false)
    private EcbuMicaTape ecbuMicatape;// 用户云母带

    @Schema(description = "用户绝缘")
    @TableField(exist = false)
    private EcbuInsulation ecbuInsulation;// 用户绝缘

    @Schema(description = "用户填充物")
    @TableField(exist = false)
    private EcbuInfilling ecbuInfilling;// 用户填充物

    @Schema(description = "用户包带")
    @TableField(exist = false)
    private EcbuBag ecbuBag;// 用户包带

    @Schema(description = "用户屏蔽")
    @TableField(exist = false)
    private EcbuShield ecbuShield;// 用户屏蔽

    @Schema(description = "用户钢带")
    @TableField(exist = false)
    private EcbuSteelband ecbuSteelband;// 用户钢带

    @Schema(description = "用户护套")
    @TableField(exist = false)
    private EcbuSheath ecbuSheath;// 用户护套

    @Schema(description = "系统护套")
    @TableField(exist = false)
    private EcbSheath ecbSheath;// 系统护套
}
