package org.jeecg.modules.cable.entity.price;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.jeecg.modules.cable.entity.systemEcable.EcbSheath;
import org.jeecg.modules.cable.entity.userEcable.*;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Schema(description = "订单的金额详细信息等")
public class EcuqDesc {

    @Schema(description = "主键ID")
    @TableId(type = IdType.AUTO)
    private Integer ecuqdId;

    @Schema(description = "报价单ID")
    private Integer ecuqId;

    @Schema(description = "成本库表ID")
    private Integer ecuoId;

    @Schema(description = "报价明细的Id")
    private Integer ecuqiId;

    @Schema(description = "质量等级ID")
    private Integer ecqulId;

    @Schema(description = "用户导体id")
    private Integer ecbucId;

    @Schema(description = "仓库ID")
    private Integer storeId;

    @Schema(description = "是否启用")
    private Boolean startType;

    @Schema(description = "序号")
    private Integer sortId;

    @Schema(description = "导体单价")
    private BigDecimal cunitPrice;

    @Schema(description = "1米导体重量")
    private BigDecimal cweight;

    @Schema(description = "仓库利润")
    private BigDecimal storePercent;

    @Schema(description = "仓库运费加点")
    private BigDecimal sdunitMoney;

    @Schema(description = "截面")
    private String areaStr;

    @Schema(description = "成本加点")
    private BigDecimal addPercent;

    @Schema(description = "粗芯丝号")
    private BigDecimal fireSilkNumber;

    @Schema(description = "粗芯根数")
    private Integer fireRootNumber;

    @Schema(description = "粗芯过膜")
    private Integer fireMembrance;

    @Schema(description = "粗芯压型")
    private BigDecimal firePress;

    @Schema(description = "粗芯绞合系数")
    private BigDecimal fireStrand;

    @Schema(description = "细芯丝号")
    private BigDecimal zeroSilkNumber;

    @Schema(description = "细芯根数")
    private Integer zeroRootNumber;

    @Schema(description = "细芯过膜")
    private Integer zeroMembrance;

    @Schema(description = "细芯压型")
    private BigDecimal zeroPress;

    @Schema(description = "细芯绞合系数")
    private BigDecimal zeroStrand;

    @Schema(description = "用户绝缘ID")
    private Integer ecbuiId;

    @Schema(description = "绝缘粗芯厚度")
    private BigDecimal insulationFireThickness;

    @Schema(description = "绝缘细芯厚度")
    private BigDecimal insulationZeroThickness;

    @Schema(description = "包带ID")
    private Integer ecbubId;

    @Schema(description = "包带厚度")
    private BigDecimal bagThickness;

    @Schema(description = "铠装包带ID")
    @TableField("ecbub_22_id")
    private Integer ecbub22Id;

    @Schema(description = "铠装包带厚度")
    @TableField("bag_22_thickness")
    private BigDecimal bag22Thickness;

    @Schema(description = "用户屏蔽ID")
    private Integer ecbuShieldId;

    @Schema(description = "屏蔽厚度")
    private BigDecimal shieldThickness;

    @Schema(description = "屏蔽编织系数")
    private BigDecimal shieldPercent;

    @Schema(description = "用户钢带ID")
    private Integer ecbusbId;

    @Schema(description = "钢带厚度")
    private BigDecimal steelbandThickness;

    @Schema(description = "钢带层数")
    private Integer steelbandStorey;

    @Schema(description = "用户护套ID")
    private Integer ecbuSheathId;

    @Schema(description = "护套厚度")
    private BigDecimal sheathThickness;

    @Schema(description = "铠装护套厚度")
    @TableField("sheath_22_thickness")
    private BigDecimal sheath22Thickness;

    @Schema(description = "云母带ID")
    private Integer ecbumId;

    @Schema(description = "云母带厚度")
    private BigDecimal micatapeThickness;

    @Schema(description = "用户填充物ID")
    private Integer ecbuinId;

    @Schema(description = "钢丝ID")
    private Integer ecbuswId;

    @Schema(description = "钢丝过膜")
    private BigDecimal steelwireMembrance;

    @Schema(description = "钢丝压型")
    private BigDecimal steelwirePress;

    @Schema(description = "是否启动手输 true 开启手输 false 不开启")
    private Boolean inputStart;

    @Schema(description = "不开票的单价")
    private BigDecimal nbupsMoney;// no bill unit price single money 不开票的单价

    @Schema(description = "开票单价")
    private BigDecimal bupsMoney;// bill unit price single money 开票单价

    @Schema(description = "不开票小计")
    private BigDecimal nbupcMoney;// no bill unit price compute money 不开票小计

    @Schema(description = "开票小计")
    private BigDecimal bupcMoney;// bill unit price compute money 开票小计


    @Schema(description = "本明细总计重量")
    private BigDecimal weight;

    @Schema(description = "税前单价是否手输")
    private Boolean unitPriceInput;

    @Schema(description = "税前单价")
    private BigDecimal unitPrice;

    @Schema(description = "1米的重量")
    private BigDecimal unitWeight;

    @Schema(description = "木轴ID")
    private Integer ecbuaId;

    @Schema(description = "木轴的数量")
    private Integer axleNumber;

    @Schema(description = "添加时间")
    private Date addTime;

    @Schema(description = "修改时间")
    private Date updateTime;

    @Schema(description = "用户云母带")
    @TableField(exist = false)
    private EcbuMicaTape ecbuMicatape;

    @Schema(description = "用户绝缘")
    @TableField(exist = false)
    private EcbuInsulation ecbuInsulation;

    @Schema(description = "用户填充物")
    @TableField(exist = false)
    private EcbuInfilling ecbuInfilling;

    @Schema(description = "用户包带")
    @TableField(exist = false)
    private EcbuBag ecbuBag;

    @Schema(description = "用户屏蔽")
    @TableField(exist = false)
    private EcbuShield ecbuShield;

    @Schema(description = "用户钢带")
    @TableField(exist = false)
    private EcbuSteelband ecbuSteelband;

    @Schema(description = "用户护套")
    @TableField(exist = false)
    private EcbuSheath ecbuSheath;

    @Schema(description = "系统护套")
    @TableField(exist = false)
    private EcbSheath ecbSheath;
}
