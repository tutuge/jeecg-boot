package org.jeecg.modules.cable.entity.userOffer;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jeecg.modules.cable.entity.quality.EcquLevel;
import org.jeecg.modules.cable.entity.systemEcable.*;
import org.jeecg.modules.cable.entity.userEcable.*;

import java.math.BigDecimal;

@Schema(description = "方案")
public class EcuOffer {

    @Schema(description = "主键ID")
    private Integer ecuoId;//主键ID

    @Schema(description = "公司ID")
    private Integer ecCompanyId;//公司ID

    @Schema(description = "质量等级ID")
    private Integer ecqulId;//质量等级ID

    @Schema(description = "用户导体ID")
    private Integer ecbucId;//用户导体ID

    @Schema(description = "是否启用")
    private Boolean startType;//是否启用

    @Schema(description = "排序")
    private Integer sortId;//序号

    @Schema(description = "截面")
    private String areaStr;//截面

    @Schema(description = "成本加点")
    private BigDecimal addPercent;//成本加点


    @Schema(description = "粗芯丝号")
    private BigDecimal fireSilkNumber;//粗芯丝号

    @Schema(description = "粗芯根数")
    private Integer fireRootNumber;//粗芯根数

    @Schema(description = "粗芯过膜")
    private Integer fireMembrance;//粗芯过膜

    @Schema(description = "粗芯压型")
    private BigDecimal firePress;//粗芯压型

    @Schema(description = "粗芯绞合系数")
    private BigDecimal fireStrand;//粗芯绞合系数

    @Schema(description = "细芯丝号")
    private BigDecimal zeroSilkNumber;//细芯丝号

    @Schema(description = "细芯根数")
    private Integer zeroRootNumber;//细芯根数

    @Schema(description = "细芯过膜")
    private Integer zeroMembrance;//细芯过膜

    @Schema(description = "细芯压型")
    private BigDecimal zeroPress;//细芯压型

    @Schema(description = "细芯绞系数")
    private BigDecimal zeroStrand;//细芯绞系数

    @Schema(description = "绝缘ID")
    private Integer ecbuiId;//绝缘ID

    @Schema(description = "粗芯绝缘厚度")
    private BigDecimal insulationFireThickness;//粗芯绝缘厚度

    @Schema(description = "细芯绝缘厚度")
    private BigDecimal insulationZeroThickness;//细芯绝缘厚度

    @Schema(description = "包带ID")
    private Integer ecbubId;//包带ID

    @Schema(description = "包带厚度")
    private BigDecimal bagThickness;//包带厚度

    @Schema(description = "铠装包带ID")
    private Integer ecbub22Id;//铠装包带ID

    @Schema(description = "铠装包带厚度")
    private BigDecimal bag22Thickness;//铠装包带厚度


    @Schema(description = "屏蔽ID")
    private Integer ecbusId;//屏蔽ID

    @Schema(description = "屏蔽厚度")
    private BigDecimal shieldThickness;//屏蔽厚度

    @Schema(description = "屏蔽编织系数")
    private BigDecimal shieldPercent;//屏蔽编织系数

    @Schema(description = "钢带类型")
    private Integer ecbusbId;//钢带类型

    @Schema(description = "钢带厚度")
    private BigDecimal steelbandThickness;//钢带厚度

    @Schema(description = "钢带层数")
    private Integer steelbandStorey;//钢带层数

    @Schema(description = "护套ID")
    private Integer ecbusid;//护套ID


    @Schema(description = "护套厚度")
    private BigDecimal sheathThickness;//护套厚度

    @Schema(description = "铠装护套厚度")
    private BigDecimal sheath22Thickness;//铠装护套厚度

    @Schema(description = "云母带ID")
    private Integer ecbumId;//云母带ID

    @Schema(description = "云母带厚度")
    private BigDecimal micatapeThickness;//云母带厚度

    @Schema(description = "填充物ID")
    private Integer ecbuinId;//填充物ID

    @Schema(description = "钢丝ID")
    private Integer ecbuswId;//钢丝ID

    @Schema(description = "钢丝过膜")
    private BigDecimal steelwireMembrance;//钢丝过膜

    @Schema(description = "钢丝压型")
    private BigDecimal steelwirePress;//钢丝压型

    @Schema(description = "成缆系数")
    private BigDecimal cableStrand;//成缆系数

    @Schema(description = "默认重量")
    private BigDecimal defaultWeight;//默认重量

    @Schema(description = "默认金额")
    private BigDecimal defaultMoney;//默认金额

    @Schema(description = "质量等级")
    private EcquLevel ecquLevel;//质量等级

    @Schema(description = "用户云母带")
    private EcbuMicatape ecbuMicatape;//用户云母带

    @Schema(description = "系统云母带")
    private EcbMicatape ecbMicatape;//系统云母带

    @Schema(description = "用户绝缘")
    private EcbuInsulation ecbuInsulation;//用户绝缘

    @Schema(description = "系统绝缘")
    private EcbInsulation ecbInsulation;//系统绝缘

    @Schema(description = "用户包带")
    private EcbuBag ecbuBag;//用户包带

    @Schema(description = "用户铠装包带")
    private EcbuBag ecbu22Bag;//用户铠装包带

    @Schema(description = "铠装系统包带")
    private EcbBag ecb22Bag;//铠装系统包带

    @Schema(description = "系统包带")
    private EcbBag ecbBag;//系统包带

    @Schema(description = "用户屏蔽")
    private EcbuShield ecbuShield;//用户屏蔽

    @Schema(description = "系统屏蔽")
    private EcbShield ecbShield;//系统屏蔽


    @Schema(description = "用户钢带")
    private EcbuSteelband ecbuSteelband;//用户钢带

    @Schema(description = "系统钢带")
    private EcbSteelBand ecbSteelband;//系统钢带

    @Schema(description = "用户护套")
    private EcbuSheath ecbuSheath;//用户护套

    @Schema(description = "系统护套")
    private EcbSheath ecbSheath;//系统护套

    @Schema(description = "用户填充物")
    private EcbuInfilling ecbuInfilling;//用户填充物

    @Schema(description = "系统填充物")
    private EcbInfilling ecbInfilling;//系统填充物

    @Schema(description = "用户导体")
    private EcbuConductor ecbuConductor;//用户导体

    @Schema(description = "系统导体")
    private EcbConductor ecbConductor;//系统导体

    public Integer getEcuoId() {
        return ecuoId;
    }

    public void setEcuoId(Integer ecuoId) {
        this.ecuoId = ecuoId;
    }

    public Integer getEcCompanyId() {
        return ecCompanyId;
    }

    public void setEcCompanyId(Integer ecCompanyId) {
        this.ecCompanyId = ecCompanyId;
    }

    public Integer getEcqulId() {
        return ecqulId;
    }

    public void setEcqulId(Integer ecqulId) {
        this.ecqulId = ecqulId;
    }

    public Integer getEcbucId() {
        return ecbucId;
    }

    public void setEcbucId(Integer ecbucId) {
        this.ecbucId = ecbucId;
    }

    public Boolean getStartType() {
        return startType;
    }

    public void setStartType(Boolean startType) {
        this.startType = startType;
    }

    public Integer getSortId() {
        return sortId;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }

    public String getAreaStr() {
        return areaStr;
    }

    public void setAreaStr(String areaStr) {
        this.areaStr = areaStr;
    }

    public BigDecimal getAddPercent() {
        return addPercent;
    }

    public void setAddPercent(BigDecimal addPercent) {
        this.addPercent = addPercent;
    }

    public BigDecimal getFireSilkNumber() {
        return fireSilkNumber;
    }

    public void setFireSilkNumber(BigDecimal fireSilkNumber) {
        this.fireSilkNumber = fireSilkNumber;
    }

    public Integer getFireRootNumber() {
        return fireRootNumber;
    }

    public void setFireRootNumber(Integer fireRootNumber) {
        this.fireRootNumber = fireRootNumber;
    }

    public Integer getFireMembrance() {
        return fireMembrance;
    }

    public void setFireMembrance(Integer fireMembrance) {
        this.fireMembrance = fireMembrance;
    }

    public BigDecimal getFirePress() {
        return firePress;
    }

    public void setFirePress(BigDecimal firePress) {
        this.firePress = firePress;
    }

    public BigDecimal getFireStrand() {
        return fireStrand;
    }

    public void setFireStrand(BigDecimal fireStrand) {
        this.fireStrand = fireStrand;
    }

    public BigDecimal getZeroSilkNumber() {
        return zeroSilkNumber;
    }

    public void setZeroSilkNumber(BigDecimal zeroSilkNumber) {
        this.zeroSilkNumber = zeroSilkNumber;
    }

    public Integer getZeroRootNumber() {
        return zeroRootNumber;
    }

    public void setZeroRootNumber(Integer zeroRootNumber) {
        this.zeroRootNumber = zeroRootNumber;
    }

    public Integer getZeroMembrance() {
        return zeroMembrance;
    }

    public void setZeroMembrance(Integer zeroMembrance) {
        this.zeroMembrance = zeroMembrance;
    }

    public BigDecimal getZeroPress() {
        return zeroPress;
    }

    public void setZeroPress(BigDecimal zeroPress) {
        this.zeroPress = zeroPress;
    }

    public BigDecimal getZeroStrand() {
        return zeroStrand;
    }

    public void setZeroStrand(BigDecimal zeroStrand) {
        this.zeroStrand = zeroStrand;
    }

    public Integer getEcbuiId() {
        return ecbuiId;
    }

    public void setEcbuiId(Integer ecbuiId) {
        this.ecbuiId = ecbuiId;
    }

    public BigDecimal getInsulationFireThickness() {
        return insulationFireThickness;
    }

    public void setInsulationFireThickness(BigDecimal insulationFireThickness) {
        this.insulationFireThickness = insulationFireThickness;
    }

    public BigDecimal getInsulationZeroThickness() {
        return insulationZeroThickness;
    }

    public void setInsulationZeroThickness(BigDecimal insulationZeroThickness) {
        this.insulationZeroThickness = insulationZeroThickness;
    }

    public Integer getEcbubId() {
        return ecbubId;
    }

    public void setEcbubId(Integer ecbubId) {
        this.ecbubId = ecbubId;
    }

    public BigDecimal getBagThickness() {
        return bagThickness;
    }

    public void setBagThickness(BigDecimal bagThickness) {
        this.bagThickness = bagThickness;
    }

    public Integer getEcbub22Id() {
        return ecbub22Id;
    }

    public void setEcbub22Id(Integer ecbub22Id) {
        this.ecbub22Id = ecbub22Id;
    }

    public BigDecimal getBag22Thickness() {
        return bag22Thickness;
    }

    public void setBag22Thickness(BigDecimal bag22Thickness) {
        this.bag22Thickness = bag22Thickness;
    }

    public Integer getEcbusId() {
        return ecbusId;
    }

    public void setEcbusId(Integer ecbusId) {
        this.ecbusId = ecbusId;
    }

    public BigDecimal getShieldThickness() {
        return shieldThickness;
    }

    public void setShieldThickness(BigDecimal shieldThickness) {
        this.shieldThickness = shieldThickness;
    }

    public BigDecimal getShieldPercent() {
        return shieldPercent;
    }

    public void setShieldPercent(BigDecimal shieldPercent) {
        this.shieldPercent = shieldPercent;
    }

    public Integer getEcbusbId() {
        return ecbusbId;
    }

    public void setEcbusbId(Integer ecbusbId) {
        this.ecbusbId = ecbusbId;
    }

    public BigDecimal getSteelbandThickness() {
        return steelbandThickness;
    }

    public void setSteelbandThickness(BigDecimal steelbandThickness) {
        this.steelbandThickness = steelbandThickness;
    }

    public Integer getSteelbandStorey() {
        return steelbandStorey;
    }

    public void setSteelbandStorey(Integer steelbandStorey) {
        this.steelbandStorey = steelbandStorey;
    }

    public Integer getEcbusid() {
        return ecbusid;
    }

    public void setEcbusid(Integer ecbusid) {
        this.ecbusid = ecbusid;
    }

    public BigDecimal getSheathThickness() {
        return sheathThickness;
    }

    public void setSheathThickness(BigDecimal sheathThickness) {
        this.sheathThickness = sheathThickness;
    }

    public BigDecimal getSheath22Thickness() {
        return sheath22Thickness;
    }

    public void setSheath22Thickness(BigDecimal sheath22Thickness) {
        this.sheath22Thickness = sheath22Thickness;
    }

    public Integer getEcbumId() {
        return ecbumId;
    }

    public void setEcbumId(Integer ecbumId) {
        this.ecbumId = ecbumId;
    }

    public BigDecimal getMicatapeThickness() {
        return micatapeThickness;
    }

    public void setMicatapeThickness(BigDecimal micatapeThickness) {
        this.micatapeThickness = micatapeThickness;
    }

    public Integer getEcbuinId() {
        return ecbuinId;
    }

    public void setEcbuinId(Integer ecbuinId) {
        this.ecbuinId = ecbuinId;
    }

    public Integer getEcbuswId() {
        return ecbuswId;
    }

    public void setEcbuswId(Integer ecbuswId) {
        this.ecbuswId = ecbuswId;
    }

    public BigDecimal getSteelwireMembrance() {
        return steelwireMembrance;
    }

    public void setSteelwireMembrance(BigDecimal steelwireMembrance) {
        this.steelwireMembrance = steelwireMembrance;
    }

    public BigDecimal getSteelwirePress() {
        return steelwirePress;
    }

    public void setSteelwirePress(BigDecimal steelwirePress) {
        this.steelwirePress = steelwirePress;
    }

    public BigDecimal getCableStrand() {
        return cableStrand;
    }

    public void setCableStrand(BigDecimal cableStrand) {
        this.cableStrand = cableStrand;
    }

    public BigDecimal getDefaultWeight() {
        return defaultWeight;
    }

    public void setDefaultWeight(BigDecimal defaultWeight) {
        this.defaultWeight = defaultWeight;
    }

    public BigDecimal getDefaultMoney() {
        return defaultMoney;
    }

    public void setDefaultMoney(BigDecimal defaultMoney) {
        this.defaultMoney = defaultMoney;
    }

    public EcquLevel getEcquLevel() {
        return ecquLevel;
    }

    public void setEcquLevel(EcquLevel ecquLevel) {
        this.ecquLevel = ecquLevel;
    }

    public EcbuMicatape getEcbuMicatape() {
        return ecbuMicatape;
    }

    public void setEcbuMicatape(EcbuMicatape ecbuMicatape) {
        this.ecbuMicatape = ecbuMicatape;
    }

    public EcbMicatape getEcbMicatape() {
        return ecbMicatape;
    }

    public void setEcbMicatape(EcbMicatape ecbMicatape) {
        this.ecbMicatape = ecbMicatape;
    }

    public EcbuInsulation getEcbuInsulation() {
        return ecbuInsulation;
    }

    public void setEcbuInsulation(EcbuInsulation ecbuInsulation) {
        this.ecbuInsulation = ecbuInsulation;
    }

    public EcbInsulation getEcbInsulation() {
        return ecbInsulation;
    }

    public void setEcbInsulation(EcbInsulation ecbInsulation) {
        this.ecbInsulation = ecbInsulation;
    }

    public EcbuBag getEcbuBag() {
        return ecbuBag;
    }

    public void setEcbuBag(EcbuBag ecbuBag) {
        this.ecbuBag = ecbuBag;
    }

    public EcbuBag getEcbu22Bag() {
        return ecbu22Bag;
    }

    public void setEcbu22Bag(EcbuBag ecbu22Bag) {
        this.ecbu22Bag = ecbu22Bag;
    }

    public EcbBag getEcb22Bag() {
        return ecb22Bag;
    }

    public void setEcb22Bag(EcbBag ecb22Bag) {
        this.ecb22Bag = ecb22Bag;
    }

    public EcbBag getEcbBag() {
        return ecbBag;
    }

    public void setEcbBag(EcbBag ecbBag) {
        this.ecbBag = ecbBag;
    }

    public EcbuShield getEcbuShield() {
        return ecbuShield;
    }

    public void setEcbuShield(EcbuShield ecbuShield) {
        this.ecbuShield = ecbuShield;
    }

    public EcbShield getEcbShield() {
        return ecbShield;
    }

    public void setEcbShield(EcbShield ecbShield) {
        this.ecbShield = ecbShield;
    }

    public EcbuSteelband getEcbuSteelband() {
        return ecbuSteelband;
    }

    public void setEcbuSteelband(EcbuSteelband ecbuSteelband) {
        this.ecbuSteelband = ecbuSteelband;
    }

    public EcbSteelBand getEcbSteelband() {
        return ecbSteelband;
    }

    public void setEcbSteelband(EcbSteelBand ecbSteelband) {
        this.ecbSteelband = ecbSteelband;
    }

    public EcbuSheath getEcbuSheath() {
        return ecbuSheath;
    }

    public void setEcbuSheath(EcbuSheath ecbuSheath) {
        this.ecbuSheath = ecbuSheath;
    }

    public EcbSheath getEcbSheath() {
        return ecbSheath;
    }

    public void setEcbSheath(EcbSheath ecbSheath) {
        this.ecbSheath = ecbSheath;
    }

    public EcbuInfilling getEcbuInfilling() {
        return ecbuInfilling;
    }

    public void setEcbuInfilling(EcbuInfilling ecbuInfilling) {
        this.ecbuInfilling = ecbuInfilling;
    }

    public EcbInfilling getEcbInfilling() {
        return ecbInfilling;
    }

    public void setEcbInfilling(EcbInfilling ecbInfilling) {
        this.ecbInfilling = ecbInfilling;
    }

    public EcbuConductor getEcbuConductor() {
        return ecbuConductor;
    }

    public void setEcbuConductor(EcbuConductor ecbuConductor) {
        this.ecbuConductor = ecbuConductor;
    }

    public EcbConductor getEcbConductor() {
        return ecbConductor;
    }

    public void setEcbConductor(EcbConductor ecbConductor) {
        this.ecbConductor = ecbConductor;
    }
}
