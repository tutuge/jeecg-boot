package org.jeecg.modules.cable.model.price;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ObjectUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.price.desc.bo.*;
import org.jeecg.modules.cable.domain.computeBo.Conductor;
import org.jeecg.modules.cable.entity.price.EcuQuoted;
import org.jeecg.modules.cable.entity.price.EcuqDesc;
import org.jeecg.modules.cable.entity.price.EcuqInput;
import org.jeecg.modules.cable.entity.userCommon.EcbuStore;
import org.jeecg.modules.cable.entity.userCommon.EcduCompany;
import org.jeecg.modules.cable.entity.userCommon.EcuConductorPrice;
import org.jeecg.modules.cable.entity.userEcable.EcbuMaterials;
import org.jeecg.modules.cable.entity.userEcable.EcuSilkModel;
import org.jeecg.modules.cable.entity.userOffer.EcuOffer;
import org.jeecg.modules.cable.model.userEcable.EcbuMaterialsModel;
import org.jeecg.modules.cable.model.userOffer.EcuOfferModel;
import org.jeecg.modules.cable.service.price.EcuQuotedService;
import org.jeecg.modules.cable.service.price.EcuqDescService;
import org.jeecg.modules.cable.service.price.EcuqInputService;
import org.jeecg.modules.cable.service.userCommon.EcbuStoreService;
import org.jeecg.modules.cable.service.userCommon.EcduCompanyService;
import org.jeecg.modules.cable.service.userCommon.EcuConductorPriceService;
import org.jeecg.modules.cable.service.userEcable.EcuSilkModelService;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@Slf4j
public class EcuqDescModel {
    @Resource
    EcuqDescService ecuqDescService;
    @Resource
    EcuOfferModel ecuOfferModel;// 库数据
    @Resource
    EcbuStoreService ecbuStoreService;// 用户仓库
    //@Resource
    //EcbuConductorService ecbuConductorService;// 用户导体
    @Resource
    EcuSilkModelService ecuSilkModelService;// 丝型号
    @Resource
    @Lazy
    EcuqInputModel ecuqInputModel;
    //@Resource
    //EcbuSheathService ecbuSheathService;
    @Resource
    EcuqInputService ecuqInputService;
    @Resource
    EcduCompanyService ecduCompanyService;
    @Resource
    EcuQuotedService ecuQuotedService;
    @Resource
    private EcuConductorPriceService ecuConductorPriceService;
    @Resource
    private EcbuMaterialsModel ecbuMaterialsModel;

    public void dealStructure(DescDealBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        Integer ecuqiId = bo.getEcuqiId();
        EcuqDesc recordEcuqDesc = new EcuqDesc();
        recordEcuqDesc.setEcuqiId(ecuqiId);
        EcuqDesc ecuqDesc = ecuqDescService.getObject(recordEcuqDesc);
        if (ecuqDesc == null) {
            throw new RuntimeException("数据错误");
        }
        EcuqDesc record = new EcuqDesc();
        BeanUtils.copyProperties(bo, record);
        record.setEcuqdId(ecuqDesc.getEcuqdId());
        //if (bo.getEcbsid() != null) {// 护套类型
        //    Integer ecbsId = bo.getEcbsid();
        //    EcbuSheath recordEcbuSheath = new EcbuSheath();
        //    recordEcbuSheath.setEcbsId(ecbsId);
        //    recordEcbuSheath.setEcCompanyId(sysUser.getEcCompanyId());
        //    EcbuSheath ecbuSheath = ecbuSheathService.getObject(recordEcbuSheath);
        //    record.setEcbuSheathId(ecbuSheath.getEcbusId());
        //}
        log.info("record + " + CommonFunction.getGson().toJson(record));
        ecuqDescService.update(record);
    }

    // cleanMoney 清除金额
    public void cleanMoney(Integer ecuqdId) {
        BigDecimal nbupsMoney = BigDecimal.ZERO;// 不开票单价
        BigDecimal bupsMoney = BigDecimal.ZERO;// 开发票单价
        BigDecimal nbupcMoney = BigDecimal.ZERO;// 不开票小计
        BigDecimal bupcMoney = BigDecimal.ZERO;// 开票小计
        BigDecimal weight = BigDecimal.ZERO;
        EcuqDesc record = new EcuqDesc();
        record.setEcuqdId(ecuqdId);
        record.setBupsMoney(bupsMoney);
        record.setBupcMoney(bupcMoney);
        record.setNbupsMoney(nbupsMoney);
        record.setNbupcMoney(nbupcMoney);
        record.setWeight(weight);
        // System.out.println(CommonFunction.getGson().toJson(record));
        ecuqDescService.update(record);
    }

    /**
     * 修改导体重量
     */
    public void updateConductorWeight(EcuqInput ecuqInput, BigDecimal cweight) {
        EcuqDesc record = new EcuqDesc();
        record.setEcuqiId(ecuqInput.getEcuqiId());
        EcuqDesc ecuqDesc = ecuqDescService.getObject(record);
        record.setEcuqdId(ecuqDesc.getEcuqdId());
        record.setCweight(cweight);// 导体重量
        ecuqDescService.update(record);
    }

    // dealInputStart 更改为手输或是自动计算价格 false 是自动 true 是手输
    public void dealInputStart(DescStartBo bo) {
        Integer ecuqiId = bo.getEcuqiId();
        Boolean inputStart = bo.getInputStart();
        EcuqDesc record = new EcuqDesc();
        record.setEcuqiId(ecuqiId);
        record.setInputStart(inputStart);
        ecuqDescService.update(record);
    }

    // dealUnitPrice
    public void dealUnitPrice(DescDealUnitPriceBo bo) {
        Integer ecuqiId = bo.getEcuqiId();
        EcuqDesc record = new EcuqDesc();
        record.setEcuqiId(ecuqiId);
        EcuqDesc ecuqDesc = ecuqDescService.getObject(record);
        if (ObjUtil.isNull(ecuqDesc)) {
            throw new RuntimeException("报价单对应数据行不存在！");
        }
        Boolean unitPriceInput = bo.getUnitPriceInput();
        BigDecimal unitPrice = bo.getUnitPrice();
        record.setEcuqdId(ecuqDesc.getEcuqdId());
        record.setUnitPriceInput(unitPriceInput);
        record.setUnitPrice(unitPrice);
        ecuqDescService.update(record);
    }

    // cleanUnitPriceInput 清空税前单价 即将税前单价的手输模式改为false
    public void cleanUnitPriceInput(Integer ecuqiId, Boolean unitPriceInput) {
        EcuqDesc record = new EcuqDesc();
        record.setEcuqiId(ecuqiId);
        EcuqDesc ecuqDesc = ecuqDescService.getObject(record);
        if (ecuqDesc != null) {
            record.setEcuqdId(ecuqDesc.getEcuqdId());
            record.setUnitPriceInput(unitPriceInput);
            ecuqDescService.update(record);
        }
    }

    // dealAxle 木轴类型和木轴数量提交
    public void dealAxle(DescDealAxleBo bo) {
        Integer ecuqiId = bo.getEcuqiId();
        EcuqDesc record = new EcuqDesc();
        record.setEcuqiId(ecuqiId);
        EcuqDesc ecuqDesc = ecuqDescService.getObject(record);
        if (ecuqDesc == null) {
            throw new RuntimeException("保价明细保存出现问题，请再次选择型号、规格信息");
        }
        record.setEcuqdId(ecuqDesc.getEcuqdId());
        Integer ecbuaId = bo.getEcbuaId();
        if (ecbuaId != null) {
            record.setEcbuaId(ecbuaId);
            if (ecuqDesc.getEcbuaId() == 0 && ecbuaId != 0) {
                record.setAxleNumber(1);
            } else if (ObjectUtil.isNull(ecbuaId)) {
                record.setAxleNumber(0);
            }
        }
        if (bo.getAxleNumber() != null) {
            Integer axleNumber = bo.getAxleNumber();
            record.setAxleNumber(axleNumber);
        }
        log.info(CommonFunction.getGson().toJson(record));
        ecuqDescService.update(record);
    }

    // dealMoney 提交金额
    @Transactional(rollbackFor = Exception.class)
    public void dealMoney(DescDealMoneyBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        Integer ecuqiId = bo.getEcuqiId();
        EcuqInput recordEcuqInput = new EcuqInput();
        recordEcuqInput.setEcuqiId(ecuqiId);
        EcuqInput ecuqInput = ecuqInputService.getObject(recordEcuqInput);
        BigDecimal nbupsMoney = BigDecimal.ZERO;
        BigDecimal bupsMoney = BigDecimal.ZERO;
        BigDecimal nbupcMoney = BigDecimal.ZERO;
        BigDecimal bupcMoney = BigDecimal.ZERO;
        Integer saleNumber = ecuqInput.getSaleNumber();
        if (bo.getNbupsMoney() != null) {// 不开票单价
            nbupsMoney = bo.getNbupsMoney();
            nbupcMoney = nbupsMoney.multiply(new BigDecimal(saleNumber));
        }
        if (bo.getNbupcMoney() != null) {// 不开票小计
            nbupcMoney = bo.getNbupcMoney();
            nbupsMoney = nbupcMoney.divide(new BigDecimal(saleNumber), 16, RoundingMode.HALF_UP);
        }
        if (bo.getBupsMoney() != null) {// 开票单价
            bupsMoney = bo.getBupsMoney();
            bupcMoney = bupsMoney.multiply(new BigDecimal(saleNumber));
        }
        if (bo.getBupcMoney() != null) {// 开票小计
            bupcMoney = bo.getBupcMoney();
            bupsMoney = bupcMoney.divide(new BigDecimal(saleNumber), 16, RoundingMode.HALF_UP);
        }
        EcduCompany recordEcduCompany = new EcduCompany();
        recordEcduCompany.setEcCompanyId(sysUser.getEcCompanyId());
        recordEcduCompany.setDefaultType(true);
        EcduCompany company = ecduCompanyService.getObject(recordEcduCompany);
        //开票税点
        BigDecimal billPercent = ecuqInput.getBillPercent();
        //减去税点
        BigDecimal subtract = BigDecimal.ONE.subtract(billPercent);
        //加上税点
        BigDecimal add = BigDecimal.ONE.add(billPercent);
        //发票税点类型
        Integer billPercentType = company.getBillPercentType();
        if (bo.getNbupsMoney() != null || bo.getNbupcMoney() != null) {
            if (billPercentType == 1) {// 算法1
                bupsMoney = nbupsMoney.divide(subtract, 16, RoundingMode.HALF_UP);
                bupcMoney = nbupcMoney.divide(subtract, 16, RoundingMode.HALF_UP);
            } else if (billPercentType == 2) {// 算法2
                bupsMoney = nbupsMoney.multiply(add);
                bupcMoney = nbupcMoney.multiply(add);
            }
        }
        if (bo.getBupsMoney() != null || bo.getBupcMoney() != null) {
            if (billPercentType == 1) {// 算法1
                nbupsMoney = bupsMoney.multiply(subtract);
                nbupcMoney = bupcMoney.multiply(subtract);
            } else if (billPercentType == 2) {// 算法2
                nbupsMoney = bupsMoney.divide(add, 16, RoundingMode.HALF_UP);
                nbupcMoney = bupcMoney.divide(add, 16, RoundingMode.HALF_UP
                );
            }
        }
        EcuqDesc record = new EcuqDesc();
        record.setEcuqiId(ecuqiId);
        record.setNbupsMoney(nbupsMoney);
        record.setNbupcMoney(nbupcMoney);
        record.setBupsMoney(bupsMoney);
        record.setBupcMoney(bupcMoney);
        record.setInputStart(true);
        ecuqDescService.update(record);
    }

    // dealMoneyPassQuoted
    public void dealMoneyPassQuoted(Integer ecuqId, BigDecimal buptMoney, BigDecimal nbuptMoney) {
        log.info("nbuptMoney + " + nbuptMoney);
        EcuqDesc record = new EcuqDesc();
        EcuQuoted recordEcuQuoted = new EcuQuoted();
        recordEcuQuoted.setEcuqId(ecuqId);
        EcuQuoted ecuQuoted = ecuQuotedService.getObject(recordEcuQuoted);
        record.setEcuqId(ecuqId);
        List<EcuqDesc> list = ecuqDescService.getList(record);
        // System.out.println(CommonFunction.getGson().toJson(list));
        BigDecimal buptM = ecuQuoted.getBuptMoney();
        BigDecimal nbuptM = ecuQuoted.getNbuptMoney();
        for (EcuqDesc ecuqDesc : list) {
            Integer ecuqiId = ecuqDesc.getEcuqiId();
            EcuqInput recordEcuqInput = new EcuqInput();
            recordEcuqInput.setEcuqiId(ecuqiId);
            EcuqInput ecuqInput = ecuqInputService.getObject(recordEcuqInput);
            if (buptMoney.compareTo(new BigDecimal("0")) != 0) {// 开发票总计
                BigDecimal bupcMoney = ecuqDesc.getBupcMoney();
                BigDecimal percent = bupcMoney.divide(buptM, 16, RoundingMode.HALF_UP);// 百分比
                bupcMoney = buptMoney.multiply(percent);
                BigDecimal bupsMoney = bupcMoney.divide(new BigDecimal(ecuqInput.getSaleNumber()), 16, RoundingMode.HALF_UP);
                record.setEcuqdId(ecuqDesc.getEcuqdId());
                record.setBupcMoney(bupcMoney);
                record.setBupsMoney(bupsMoney);
                ecuqDescService.update(record);
            } else {// 不开发票总计
                BigDecimal nbupcMoney = ecuqDesc.getNbupcMoney();
                BigDecimal percent = nbupcMoney.divide(nbuptM, 16, RoundingMode.HALF_UP);// 百分比
                nbupcMoney = nbuptMoney.multiply(percent);
                BigDecimal nbupsMoney = nbupcMoney.divide(new BigDecimal(ecuqInput.getSaleNumber()), 16, RoundingMode.HALF_UP);
                record.setEcuqdId(ecuqDesc.getEcuqdId());
                record.setNbupcMoney(nbupcMoney);
                record.setNbupsMoney(nbupsMoney);
                ecuqDescService.update(record);
            }
        }
    }

    // dealUnitPriceInput 计算税前单价改为自动
    public void dealUnitPriceInput(DescBo bo) {
        Integer ecuqiId = bo.getEcuqiId();
        EcuqDesc record = new EcuqDesc();
        record.setEcuqiId(ecuqiId);
        EcuqDesc ecuqDesc = ecuqDescService.getObject(record);
        if (ecuqDesc != null) {
            record.setEcuqdId(ecuqDesc.getEcuqdId());
            record.setUnitPriceInput(false);
            ecuqDescService.update(record);
        }
    }


    @Transactional(rollbackFor = Exception.class)
    public void deal(EcuqInput ecuqInput, EcuSilkModel silkModel, Integer ecCompanyId) {
        EcuOffer ecuOffer = ecuOfferModel.getOfferPassEcuqInput(ecuqInput);
        //报价单行数据ID
        Integer ecuqiId = ecuqInput.getEcuqiId();
        //报价单ID
        Integer ecuqId = ecuqInput.getEcuqId();
        if (ecuOffer != null) {
            Integer ecqulId = ecuOffer.getEcqulId();// 质量等级ID
            Integer storeId = ecuqInput.getEcbusId();
            //查询报价单的仓库
            EcuQuoted quoted = ecuQuotedService.getObjectById(ecuqId);
            //使用顶部仓库信息，不使用每行的仓库信息
            EcbuStore store = new EcbuStore();
            store.setEcbusId(quoted.getDeliveryStoreId());
            EcbuStore ecbuStore = ecbuStoreService.getObject(store);
            // 成本库表对应的导体
            Conductor conductor = ecuOffer.getConductor();
            Integer ecbucId = conductor.getId();// 导体ID
            Integer sortId = ecuqInput.getSortId();
            BigDecimal cunitPrice = BigDecimal.ZERO;// 导体单价
            // 先查询一下本报价单是否存在单独设置的导体价格
            EcuConductorPrice ecuConductorPrice = ecuConductorPriceService.selectByEcuqIdEcbucId(ecuqId, ecbucId);
            EcbuMaterials ecbuConductor = ecbuMaterialsModel.getObjectPassId(ecbucId);
            if (ObjUtil.isNotNull(ecuConductorPrice)) {
                cunitPrice = ecuConductorPrice.getCunitPrice();
            } else {
                if (ecbuConductor != null) {
                    cunitPrice = ecbuConductor.getUnitPrice();
                }
            }
            BigDecimal cweight = BigDecimal.ZERO;// 导体重量
            BigDecimal storePercent = BigDecimal.ZERO;// 仓库利润
            BigDecimal sdunitMoney = BigDecimal.ZERO;// 仓库运费加点
            Integer conductorType = 0;
            if (ecbuConductor != null) {
                conductorType = ecbuConductor.getConductorType();
            }
            if (ecbuStore != null) {
                if (conductorType == 1) {
                    //铜利润
                    storePercent = ecbuStore.getPercentCopper();
                }
                if (conductorType == 2) {
                    //铝利润
                    storePercent = ecbuStore.getPercentAluminium();
                }
                sdunitMoney = ecbuStore.getDunitMoney();// 仓库运费加点
            }
            String areaStr = ecuOffer.getAreaStr();// 截面str
            //BigDecimal fireSilkNumber = ecuOffer.getFireSilkNumber();// 粗芯丝号
            //Integer fireRootNumber = ecuOffer.getFireRootNumber();// 粗芯根数
            //Integer fireMembrance = ecuOffer.getFireMembrance();// 粗芯过膜
            //BigDecimal firePress = ecuOffer.getFirePress();// 粗芯压型
            //BigDecimal fireStrand = ecuOffer.getFireStrand();// 粗芯绞合系数
            //BigDecimal zeroSilkNumber = ecuOffer.getZeroSilkNumber();// 细芯丝号
            //Integer zeroRootNumber = ecuOffer.getZeroRootNumber();// 细芯根数
            //Integer zeroMembrance = ecuOffer.getZeroMembrance();// 细芯过膜
            //BigDecimal zeroPress = ecuOffer.getZeroPress();// 细芯压型
            //BigDecimal zeroStrand = ecuOffer.getZeroStrand();// 细芯绞合系数
            //Integer ecbuiId = 0;
            //if (silkModel.getInsulation()) {
            //    ecbuiId = ecuOffer.getEcbuiId();// 绝缘类型
            //}
            //BigDecimal insulationFireThickness = ecuOffer.getInsulationFireThickness();// 绝缘粗芯厚度
            //BigDecimal insulationZeroThickness = ecuOffer.getInsulationZeroThickness();// 绝缘细芯厚度
            //Integer ecbubId = 0;
            //Integer ecbub22Id = 0;
            //BigDecimal bagThickness = BigDecimal.ZERO;
            //BigDecimal bag22Thickness = BigDecimal.ZERO;
            //if (silkModel.getBag()) {
            //    ecbubId = ecuOffer.getEcbubId();// 包带类型
            //    ecbub22Id = ecuOffer.getEcbub22Id();// 铠装包带类型
            //    bagThickness = ecuOffer.getBagThickness();// 包带厚度
            //}
            //if (ecuOffer.getBag22Thickness() != null) {
            //    bag22Thickness = ecuOffer.getBag22Thickness();// 铠装包带厚度
            //}
            // 屏蔽 1 带 "-P" 2 带 "-P2"
            //Integer ecbusId = 0;// 屏蔽类型
            //BigDecimal shieldThickness = BigDecimal.ZERO;// 屏蔽厚度
            //BigDecimal shieldPercent = BigDecimal.ZERO;// 屏蔽厚度
            //if (silkModel.getShield()) {
            //    ecbusId = ecuOffer.getEcbuShieldId();
            //    shieldThickness = ecuOffer.getShieldThickness();
            //    shieldPercent = ecuOffer.getShieldPercent();
            //}
            //-----------钢带-----------
            //Integer ecbusbId = 0;// 钢带类型
            //BigDecimal steelbandThickness = BigDecimal.ZERO;// 钢带厚度
            //Integer steelbandStorey = 0;// 钢带层数
            //if (silkModel.getSteelBand()) {
            //    ecbusbId = ecuOffer.getEcbusbId();// 钢带类型
            //    steelbandThickness = ecuOffer.getSteelbandThickness();// 钢带厚度
            //    steelbandStorey = ecuOffer.getSteelbandStorey();// 钢带层数
            //}
            //-----------护套-----------
            //Integer ecbuSheathId = 0;// 护套类型
            //BigDecimal sheathThickness = ecuOffer.getSheathThickness();// 护套厚度
            //BigDecimal sheath22Thickness = ecuOffer.getSheath22Thickness();// 铠装护套厚度
            //if (silkModel.getSheath()) {// 默认护套
            //    ecbuSheathId = ecuOffer.getEcbuSheathId();// 护套类型
            //}
            //------------- 云母带 -------------------
            //Integer ecbumId = 0;// 云母带类型
            //BigDecimal micatapeThickness = BigDecimal.ZERO;// 云母带厚度
            //if (silkModel.getMicaTape()) {
            //    ecbumId = ecuOffer.getEcbumId();// 云母带类型
            //    micatapeThickness = ecuOffer.getMicatapeThickness();// 云母带厚度
            //}
            //Integer ecbuinId = ecuOffer.getEcbuinId();// 填充物类型
            //Integer ecbuswId = ecuOffer.getEcbuswId();// 钢丝类型
            //BigDecimal steelwireMembrance = ecuOffer.getSteelwireMembrance();// 钢丝过膜
            //BigDecimal steelwirePress = ecuOffer.getSteelwirePress();// 钢丝压型
            //材料json
            String material = ecuOffer.getMaterial();
            BigDecimal nbupsMoney = BigDecimal.ZERO;// 不开发票单价
            BigDecimal bupsMoney = BigDecimal.ZERO;// 开发票单价
            BigDecimal nbupcMoney = BigDecimal.ZERO;// 不开发票小计
            BigDecimal bupcMoney = BigDecimal.ZERO;// 开发票小计
            BigDecimal weight = BigDecimal.ZERO;// 重量
            EcuqDesc record = new EcuqDesc();
            record.setEcuqiId(ecuqiId);
            EcuqDesc ecuqDesc = ecuqDescService.getObject(record);
            if (ecuqDesc == null) {// 插入
                record.setEcuoId(ecuOffer.getEcuoId());
                record.setEcuqId(ecuqId);// 报价单ID
                record.setEcuqiId(ecuqiId);
                record.setEcqulId(ecqulId);
                record.setStoreId(storeId);
                //record.setEcbucId(ecbucId);
                record.setStartType(true);// 默认开启，暂时没用，做备用
                record.setSortId(sortId);
                record.setCunitPrice(cunitPrice);// 导体单价
                record.setCweight(cweight);// 导体重量
                record.setStorePercent(storePercent);// 仓库利润
                record.setSdunitMoney(sdunitMoney);// 仓库运费加点
                record.setAddPercent(ecuOffer.getAddPercent());
                record.setAreaStr(areaStr);// 截面str
                record.setMaterial(material);
                //record.setFireSilkNumber(fireSilkNumber);// 粗芯丝号
                //record.setFireRootNumber(fireRootNumber);// 粗芯根数
                //record.setFireMembrance(fireMembrance);// 粗芯过膜
                //record.setFirePress(firePress);// 粗芯压型
                //record.setZeroSilkNumber(zeroSilkNumber);// 细芯丝号
                //record.setZeroRootNumber(zeroRootNumber);// 细芯根数
                //record.setZeroMembrance(zeroMembrance);// 细芯过膜
                //record.setZeroPress(zeroPress);// 细芯过型
                //record.setEcbuiId(ecbuiId);// 绝缘类型
                //record.setInsulationFireThickness(insulationFireThickness);// 粗芯绝缘厚度
                //record.setInsulationZeroThickness(insulationZeroThickness);// 细芯绝缘厚度
                //record.setEcbubId(ecbubId);// 包带类型
                //record.setBagThickness(bagThickness);// 包带厚度
                //record.setEcbub22Id(ecbub22Id);// 铠装包带类型
                //record.setBag22Thickness(bag22Thickness);// 铠装包带厚度
                //record.setEcbuShieldId(ecbusId);// 屏蔽类型
                //record.setShieldThickness(shieldThickness);// 屏蔽厚度
                //record.setShieldPercent(shieldPercent);// 屏蔽编织系数
                //record.setEcbusbId(ecbusbId);// 钢带类型
                //record.setSteelbandThickness(steelbandThickness);// 钢带厚度
                //record.setSteelbandStorey(steelbandStorey);// 钢带层数
                //record.setEcbuSheathId(ecbuSheathId);// 护套类型
                //record.setSheathThickness(sheathThickness);// 护套厚度
                //record.setSheath22Thickness(sheath22Thickness);
                //record.setEcbumId(ecbumId);// 云母带类型
                //record.setMicatapeThickness(micatapeThickness);// 云母带厚度
                //record.setFireStrand(fireStrand);// 火线绞合系数
                //record.setZeroStrand(zeroStrand);// 零线绞合系数
                //record.setEcbuinId(ecbuinId);// 填充物类型
                //record.setEcbuswId(ecbuswId);// 钢丝类型
                //record.setSteelwireMembrance(steelwireMembrance);// 钢丝过膜
                //record.setSteelwirePress(steelwirePress);// 钢丝压型
                record.setInputStart(false);// 默认不开启手输状态
                record.setNbupsMoney(nbupsMoney);// 不开发票单价
                record.setBupsMoney(bupsMoney);// 开发票单价
                record.setNbupcMoney(nbupcMoney);// 不开发票小计
                record.setBupcMoney(bupcMoney);// 开发票小计
                record.setWeight(weight);
                record.setUnitPriceInput(false);
                record.setUnitPrice(BigDecimal.ZERO);
                record.setUnitWeight(BigDecimal.ZERO);
                record.setEcbuaId(0);// 木轴默认没有
                record.setAxleNumber(0);// 木轴默认数量为0
                ecuqDescService.insert(record);
                ecuqInputModel.dealBillPercent(ecuqiId, conductorType, ecCompanyId);
            } else {// 修改
                record.setEcuqdId(ecuqDesc.getEcuqdId());
                record.setEcqulId(ecqulId);
                record.setStoreId(storeId);
                //record.setEcbucId(ecbucId);
                record.setCunitPrice(cunitPrice);// 导体单价
                record.setCweight(cweight);// 导体重量
                record.setStorePercent(storePercent);// 仓库利润
                record.setSdunitMoney(sdunitMoney);// 仓库运费加点
                record.setAreaStr(areaStr);// 截面str
                record.setMaterial(material);
                //record.setFireSilkNumber(fireSilkNumber);// 粗芯丝号
                //record.setFireRootNumber(fireRootNumber);
                //record.setFireMembrance(fireMembrance);// 粗芯过膜
                //record.setFirePress(firePress);// 粗芯压型
                //record.setZeroSilkNumber(zeroSilkNumber);// 细芯丝号
                //record.setZeroRootNumber(zeroRootNumber);
                //record.setZeroMembrance(zeroMembrance);// 细芯过膜
                //record.setZeroPress(zeroPress);// 细芯过型
                //record.setEcbuiId(ecbuiId);// 绝缘类型
                //record.setInsulationFireThickness(insulationFireThickness);// 粗芯绝缘厚度
                //record.setInsulationZeroThickness(insulationZeroThickness);// 细芯绝缘厚度
                //record.setEcbubId(ecbubId);// 包带类型
                //record.setBagThickness(bagThickness);// 包带厚度
                //record.setEcbub22Id(ecbub22Id);// 包带类型
                //record.setBag22Thickness(bag22Thickness);// 包带厚度
                //record.setEcbuShieldId(ecbusId);// 屏蔽类型
                //record.setShieldThickness(shieldThickness);// 屏蔽厚度
                //record.setShieldPercent(shieldPercent);// 屏蔽编织系数
                //record.setEcbusbId(ecbusbId);// 钢带类型
                //record.setSteelbandThickness(steelbandThickness);// 钢带厚度
                //record.setSteelbandStorey(steelbandStorey);// 钢带层数
                //record.setEcbuSheathId(ecbuSheathId);// 护套类型
                //record.setSheathThickness(sheathThickness);// 护套厚度
                //record.setEcbumId(ecbumId);// 云母带类型
                //record.setMicatapeThickness(micatapeThickness);// 云母带厚度
                //record.setFireStrand(fireStrand);// 火线绞合系数
                //record.setZeroStrand(zeroStrand);// 零线绞合系数
                //record.setEcbuinId(ecbuinId);// 填充物类型
                //record.setEcbuswId(ecbuswId);// 钢丝类型
                //record.setSteelwireMembrance(steelwireMembrance);// 钢丝过膜
                //record.setSteelwirePress(steelwirePress);// 钢丝压型
                ecuqDescService.update(record);
            }
        } else {
            ecuqDescService.deletePassEcuqiId(ecuqiId);
            ecuqInputService.delete(ecuqiId);
            throw new RuntimeException("当前规格未找到对应的成本库表");
        }
    }

    //  单价提交
    public void dealUnitPrice(Integer ecuqiId, Boolean unitPriceInput, BigDecimal unitPrice) {
        EcuqDesc record = new EcuqDesc();
        record.setEcuqiId(ecuqiId);
        //EcuqDesc ecuqDesc = ecuqDescService.getObject(record);
        //record.setEcuqdId(ecuqDesc.getEcuqdId());
        record.setUnitPriceInput(unitPriceInput);
        record.setUnitPrice(unitPrice);
        ecuqDescService.update(record);
    }

    // dealMoney 提交金额
    public void dealMoney(Integer ecuqdId, BigDecimal nbupsMoney, BigDecimal bupsMoney,
                          BigDecimal nbupcMoney, BigDecimal bupcMoney, BigDecimal weight) {
        EcuqDesc record = new EcuqDesc();
        record.setEcuqdId(ecuqdId);
        //不开票的单价
        record.setNbupsMoney(nbupsMoney);
        //开票单价
        record.setBupsMoney(bupsMoney);
        //不开票小计
        record.setNbupcMoney(nbupcMoney);
        //开票小计
        record.setBupcMoney(bupcMoney);
        record.setWeight(weight);
        ecuqDescService.update(record);
    }


    // getObjectPassEcuqiId
    public EcuqDesc getObjectPassEcuqiId(Integer ecuqiId) {
        EcuqDesc record = new EcuqDesc();
        record.setEcuqiId(ecuqiId);
        return ecuqDescService.getObject(record);
    }


    public void delete(Integer ecuqiId) {
        ecuqDescService.deletePassEcuqiId(ecuqiId);
    }
}
