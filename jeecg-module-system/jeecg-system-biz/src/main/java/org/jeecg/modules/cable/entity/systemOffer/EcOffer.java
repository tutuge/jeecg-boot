package org.jeecg.modules.cable.entity.systemOffer;

import lombok.Data;

import java.math.BigDecimal;


@Data
public class EcOffer {
    private Integer ecoId;//主键ID

    private Integer ecsId;//电缆丝型号ID

    private Integer sortId;//序号

    private String areaStr;//截面

    private BigDecimal addPercent;//成本加点

    private BigDecimal fireSilkNumber;//粗芯丝号

    private Integer fireRootNumber;//粗芯根数

    private Integer fireMembrance;//粗芯过膜

    private BigDecimal firePress;//粗芯压型

    private BigDecimal fireStrand;//粗芯绞合系数

    private BigDecimal zeroSilkNumber;//细芯丝号

    private Integer zeroRootNumber;//细芯根数

    private Integer zeroMembrance;//细芯过膜

    private BigDecimal zeroPress;//细芯压型

    private BigDecimal zeroStrand;//细芯绞系数

    private Integer ecbiId;//绝缘ID

    private BigDecimal insulationFireThickness;//粗芯绝缘厚度

    private BigDecimal insulationZeroThickness;//细芯绝缘厚度

    private Integer ecbbId;//包带ID

    private BigDecimal bagThickness;//包带厚度
    private Integer ecbb22Id;//铠装包带ID

    private BigDecimal bag22Thickness;//铠装包带厚度

    private Integer ecbsId;//屏蔽ID

    private BigDecimal shieldThickness;//屏蔽厚度

    private BigDecimal shieldPercent;//屏蔽编织系数

    private Integer ecbsbId;//钢带类型

    private BigDecimal steelbandThickness;//钢带厚度

    private Integer steelbandStorey;//钢带层数

    private Integer ecbsid;//护套ID

    private BigDecimal sheathThickness;//护套厚度

    private BigDecimal sheath22Thickness;//铠装护套厚度

    private Integer ecbmId;//云母带ID

    private BigDecimal micatapeThickness;//云母带厚度

    private Integer ecbinId;//填充物ID

    private Integer ecbswId;//钢丝ID

    private BigDecimal steelwireMembrance;//钢丝过膜

    private BigDecimal steelwirePress;//钢丝压型

    private BigDecimal cableStrand;//成缆系数

    private BigDecimal defaultWeight;//重量

    private BigDecimal defaultMoney;//金额

    public Integer getEcoId() {
        return ecoId;
    }

    public void setEcoId(Integer ecoId) {
        this.ecoId = ecoId;
    }

    public Integer getEcsId() {
        return ecsId;
    }

    public void setEcsId(Integer ecsId) {
        this.ecsId = ecsId;
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

    public Integer getEcbiId() {
        return ecbiId;
    }

    public void setEcbiId(Integer ecbiId) {
        this.ecbiId = ecbiId;
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

    public Integer getEcbbId() {
        return ecbbId;
    }

    public void setEcbbId(Integer ecbbId) {
        this.ecbbId = ecbbId;
    }

    public BigDecimal getBagThickness() {
        return bagThickness;
    }

    public void setBagThickness(BigDecimal bagThickness) {
        this.bagThickness = bagThickness;
    }

    public Integer getEcbb22Id() {
        return ecbb22Id;
    }

    public void setEcbb22Id(Integer ecbb22Id) {
        this.ecbb22Id = ecbb22Id;
    }

    public BigDecimal getBag22Thickness() {
        return bag22Thickness;
    }

    public void setBag22Thickness(BigDecimal bag22Thickness) {
        this.bag22Thickness = bag22Thickness;
    }

    public Integer getEcbsId() {
        return ecbsId;
    }

    public void setEcbsId(Integer ecbsId) {
        this.ecbsId = ecbsId;
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

    public Integer getEcbsbId() {
        return ecbsbId;
    }

    public void setEcbsbId(Integer ecbsbId) {
        this.ecbsbId = ecbsbId;
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

    public Integer getEcbsid() {
        return ecbsid;
    }

    public void setEcbsid(Integer ecbsid) {
        this.ecbsid = ecbsid;
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

    public Integer getEcbmId() {
        return ecbmId;
    }

    public void setEcbmId(Integer ecbmId) {
        this.ecbmId = ecbmId;
    }

    public BigDecimal getMicatapeThickness() {
        return micatapeThickness;
    }

    public void setMicatapeThickness(BigDecimal micatapeThickness) {
        this.micatapeThickness = micatapeThickness;
    }

    public Integer getEcbinId() {
        return ecbinId;
    }

    public void setEcbinId(Integer ecbinId) {
        this.ecbinId = ecbinId;
    }

    public Integer getEcbswId() {
        return ecbswId;
    }

    public void setEcbswId(Integer ecbswId) {
        this.ecbswId = ecbswId;
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
}
