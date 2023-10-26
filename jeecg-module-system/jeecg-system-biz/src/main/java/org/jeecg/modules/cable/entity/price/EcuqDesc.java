package org.jeecg.modules.cable.entity.price;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jeecg.modules.cable.entity.systemEcable.EcbSheath;
import org.jeecg.modules.cable.entity.userEcable.*;

import java.math.BigDecimal;

public class EcuqDesc {

    @Schema(description = "主键ID")
    private Integer ecuqdId;// 主键ID

    @Schema(description = "报价单ID")
    private Integer ecuqId;// 报价单ID


    @Schema(description = "库数据ID")
    private Integer ecuoId;// 库数据ID

    @Schema(description = "inputId")
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
    private Integer ecbub22Id;// 铠装包带ID

    @Schema(description = "铠装包带厚度")
    private BigDecimal bag22Thickness;// 铠装包带厚度

    @Schema(description = "用户屏蔽ID")
    private Integer ecbusId;// 用户屏蔽ID

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
    private Integer ecbusid;// 用户护套ID

    @Schema(description = "护套厚度")
    private BigDecimal sheathThickness;// 护套厚度

    @Schema(description = "铠装护套厚度")
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

    @Schema(description = "是否启动手输")
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
    private EcbuMicatape ecbuMicatape;// 用户云母带

    @Schema(description = "用户绝缘")
    private EcbuInsulation ecbuInsulation;// 用户绝缘

    @Schema(description = "用户填充物")
    private EcbuInfilling ecbuInfilling;// 用户填充物

    @Schema(description = "用户包带")
    private EcbuBag ecbuBag;// 用户包带

    @Schema(description = "用户屏蔽")
    private EcbuShield ecbuShield;// 用户屏蔽

    @Schema(description = "用户钢带")
    private EcbuSteelband ecbuSteelband;// 用户钢带

    @Schema(description = "用户护套")
    private EcbuSheath ecbuSheath;// 用户护套

    @Schema(description = "系统护套")
    private EcbSheath ecbSheath;// 系统护套

    public Integer getEcuqdId() {
        return ecuqdId;
    }

    public void setEcuqdId(Integer ecuqdId) {
        this.ecuqdId = ecuqdId;
    }

    public Integer getEcuqId() {
        return ecuqId;
    }

    public void setEcuqId(Integer ecuqId) {
        this.ecuqId = ecuqId;
    }

    public Integer getEcuoId() {
        return ecuoId;
    }

    public void setEcuoId(Integer ecuoId) {
        this.ecuoId = ecuoId;
    }

    public Integer getEcuqiId() {
        return ecuqiId;
    }

    public void setEcuqiId(Integer ecuqiId) {
        this.ecuqiId = ecuqiId;
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

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
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

    public BigDecimal getCunitPrice() {
        return cunitPrice;
    }

    public void setCunitPrice(BigDecimal cunitPrice) {
        this.cunitPrice = cunitPrice;
    }

    public BigDecimal getCweight() {
        return cweight;
    }

    public void setCweight(BigDecimal cweight) {
        this.cweight = cweight;
    }

    public BigDecimal getStorePercent() {
        return storePercent;
    }

    public void setStorePercent(BigDecimal storePercent) {
        this.storePercent = storePercent;
    }

    public BigDecimal getSdunitMoney() {
        return sdunitMoney;
    }

    public void setSdunitMoney(BigDecimal sdunitMoney) {
        this.sdunitMoney = sdunitMoney;
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

    public Boolean getInputStart() {
        return inputStart;
    }

    public void setInputStart(Boolean inputStart) {
        this.inputStart = inputStart;
    }

    public BigDecimal getNbupsMoney() {
        return nbupsMoney;
    }

    public void setNbupsMoney(BigDecimal nbupsMoney) {
        this.nbupsMoney = nbupsMoney;
    }

    public BigDecimal getBupsMoney() {
        return bupsMoney;
    }

    public void setBupsMoney(BigDecimal bupsMoney) {
        this.bupsMoney = bupsMoney;
    }

    public BigDecimal getNbupcMoney() {
        return nbupcMoney;
    }

    public void setNbupcMoney(BigDecimal nbupcMoney) {
        this.nbupcMoney = nbupcMoney;
    }

    public BigDecimal getBupcMoney() {
        return bupcMoney;
    }

    public void setBupcMoney(BigDecimal bupcMoney) {
        this.bupcMoney = bupcMoney;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public Boolean getUnitPriceInput() {
        return unitPriceInput;
    }

    public void setUnitPriceInput(Boolean unitPriceInput) {
        this.unitPriceInput = unitPriceInput;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getUnitWeight() {
        return unitWeight;
    }

    public void setUnitWeight(BigDecimal unitWeight) {
        this.unitWeight = unitWeight;
    }

    public Integer getEcbuaId() {
        return ecbuaId;
    }

    public void setEcbuaId(Integer ecbuaId) {
        this.ecbuaId = ecbuaId;
    }

    public Integer getAxleNumber() {
        return axleNumber;
    }

    public void setAxleNumber(Integer axleNumber) {
        this.axleNumber = axleNumber;
    }

    public Long getAddTime() {
        return addTime;
    }

    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }

    public EcbuMicatape getEcbuMicatape() {
        return ecbuMicatape;
    }

    public void setEcbuMicatape(EcbuMicatape ecbuMicatape) {
        this.ecbuMicatape = ecbuMicatape;
    }

    public EcbuInsulation getEcbuInsulation() {
        return ecbuInsulation;
    }

    public void setEcbuInsulation(EcbuInsulation ecbuInsulation) {
        this.ecbuInsulation = ecbuInsulation;
    }

    public EcbuInfilling getEcbuInfilling() {
        return ecbuInfilling;
    }

    public void setEcbuInfilling(EcbuInfilling ecbuInfilling) {
        this.ecbuInfilling = ecbuInfilling;
    }

    public EcbuBag getEcbuBag() {
        return ecbuBag;
    }

    public void setEcbuBag(EcbuBag ecbuBag) {
        this.ecbuBag = ecbuBag;
    }

    public EcbuShield getEcbuShield() {
        return ecbuShield;
    }

    public void setEcbuShield(EcbuShield ecbuShield) {
        this.ecbuShield = ecbuShield;
    }

    public EcbuSteelband getEcbuSteelband() {
        return ecbuSteelband;
    }

    public void setEcbuSteelband(EcbuSteelband ecbuSteelband) {
        this.ecbuSteelband = ecbuSteelband;
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
}
