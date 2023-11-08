package org.jeecg.modules.cable.model.price;

import cn.hutool.core.util.ObjUtil;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.price.input.bo.*;
import org.jeecg.modules.cable.controller.price.input.vo.EcuqInputVo;
import org.jeecg.modules.cable.controller.price.input.vo.InputListVo;
import org.jeecg.modules.cable.controller.price.input.vo.InputStructureVo;
import org.jeecg.modules.cable.domain.*;
import org.jeecg.modules.cable.entity.hand.DeliveryObj;
import org.jeecg.modules.cable.entity.price.EcuQuoted;
import org.jeecg.modules.cable.entity.price.EcuqDesc;
import org.jeecg.modules.cable.entity.price.EcuqInput;
import org.jeecg.modules.cable.entity.quality.EcquLevel;
import org.jeecg.modules.cable.entity.quality.EcquParameter;
import org.jeecg.modules.cable.entity.systemEcable.EcSilk;
import org.jeecg.modules.cable.entity.systemEcable.EcbSheath;
import org.jeecg.modules.cable.entity.userCommon.*;
import org.jeecg.modules.cable.entity.userDelivery.EcbudDelivery;
import org.jeecg.modules.cable.entity.userEcable.*;
import org.jeecg.modules.cable.model.efficiency.EcdAreaModel;
import org.jeecg.modules.cable.model.quality.EcquLevelModel;
import org.jeecg.modules.cable.model.systemEcable.EcSilkModel;
import org.jeecg.modules.cable.model.user.EcProfitModel;
import org.jeecg.modules.cable.model.userCommon.EcbuPcompanyModel;
import org.jeecg.modules.cable.model.userCommon.EcbuStoreModel;
import org.jeecg.modules.cable.model.userCommon.EcbulUnitModel;
import org.jeecg.modules.cable.model.userDelivery.EcbuDeliveryModel;
import org.jeecg.modules.cable.service.price.EcuQuotedService;
import org.jeecg.modules.cable.service.price.EcuqDescService;
import org.jeecg.modules.cable.service.price.EcuqInputService;
import org.jeecg.modules.cable.service.quality.EcquLevelService;
import org.jeecg.modules.cable.service.quality.EcquParameterService;
import org.jeecg.modules.cable.service.systemEcable.EcSilkService;
import org.jeecg.modules.cable.service.systemEcable.EcbSheathService;
import org.jeecg.modules.cable.service.userCommon.*;
import org.jeecg.modules.cable.service.userDelivery.EcbudDeliveryService;
import org.jeecg.modules.cable.service.userEcable.*;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.jeecg.modules.cable.tools.EcableFunction;
import org.jeecg.modules.cable.tools.ExcelUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
@Slf4j
public class EcuqInputModel {
    @Resource
    EcuQuotedService ecuQuotedService;// 报价单
    @Resource
    EcuqInputService ecuqInputService;// 手输详情
    @Resource
    EcbuStoreService ecbuStoreService;// 仓库
    @Resource
    EcuqDescModel ecuqDescModel;// 结构详情
    @Resource
    EcquLevelService ecquLevelService;// 质量等级
    @Resource
    EcSilkService ecSilkService;// 丝类型
    @Resource
    EcduTaxpointService ecduTaxpointService;// 发票税点
    @Resource
    EcuqDescService ecuqDescService;// 详情
    @Resource
    EcbulUnitService ecbulUnitService;// 单位长度
    @Resource
    EcbuConductorService ecbuConductorService;// 用户导体
    @Resource
    EcquParameterService ecquParameterService;// 质量等级参数
    @Resource
    EcbuMicaTapeService ecbuMicatapeService;// 用户云母带
    @Resource
    EcbuInsulationService ecbuInsulationService;// 用户绝缘
    @Resource
    EcbuInfillingService ecbuInfillingService;// 填充物
    @Resource
    EcbuBagService ecbuBagService;// 用户包带
    @Resource
    EcbuShieldService ecbuShieldService;// 用户屏蔽
    @Resource
    EcbuSteelbandService ecbuSteelbandService;// 用户钢带
    @Resource
    EcbuSheathService ecbuSheathService;// 用户护套
    @Resource
    EcduCompanyService ecduCompanyService;// 公司数据相关
    @Resource
    EcbuAxleService ecbuAxleService;// 木轴
    @Resource
    EcbuDeliveryModel ecbuDeliveryModel;// 快递
    @Resource
    EcbudDeliveryService ecbudDeliveryService;// 默认快递
    @Resource
    EcbSheathService ecbSheathService;// 系统护套
    @Resource
    EcuQuotedModel ecuQuotedModel;
    @Resource
    EcSilkModel ecSilkModel;
    @Resource
    private EcuSilkModelService ecuSilkModelService;
    @Resource
    EcquLevelModel ecquLevelModel;// 质量等级
    @Resource
    EcProfitModel ecProfitModel;// 利润
    @Resource
    EcbuPcompanyModel ecbuPcompanyModel;
    ExcelUtils excelUtils = new ExcelUtils();
    @Resource
    EcbuStoreModel ecbuStoreModel;// 仓库
    @Resource
    EcdAreaModel ecdAreaModel;// 截面库
    @Resource
    EcbulUnitModel ecbulUnitModel;// 单位


    public EcuqInput deal(InputDealBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        Integer ecuqiId = bo.getEcuqiId();// 主键ID
        Integer ecuqId = bo.getEcuqId();// 报价单ID
        ecuQuotedModel.getById(ecuqId);

        Integer ecqulId = 0;// 质量等级ID
        if (bo.getEcqulId() != null) {
            ecqulId = bo.getEcqulId();
        }
        Integer storeId = 0;// 仓库ID
        if (bo.getEcbusId() != null) {
            storeId = bo.getEcbusId();
        }
        //String storeName = bo.getStoreName();
        //if (StrUtil.isNotBlank(storeName)) {
        //    EcbuStore recordStore = new EcbuStore();
        //    recordStore.setEcCompanyId(sysUser.getEcCompanyId());
        //    recordStore.setStoreName(storeName);
        //    EcbuStore ecbuStore = ecbuStoreService.getObjectPassStoreName(recordStore);
        //    if (ecbuStore != null) {
        //        storeId = ecbuStore.getEcbusId();
        //    }
        //}
        //型号系列ID
        Integer silkId = bo.getSilkId();
        //型号ID
        Integer silkModelId = bo.getEcusmId();
        String SilkModelName = "";
        if (ObjUtil.isNotNull(silkModelId)) {
            EcuSilkModel silkModel = ecuSilkModelService.getById(silkModelId);
            SilkModelName = silkModel.getAbbreviation();
        }
        String silkName = "";// 型号系列名称
        if (bo.getSilkName() != null) {
            silkName = bo.getSilkName();
        }
        String areaStr = "";// 截面
        if (bo.getAreaStr() != null) {
            areaStr = bo.getAreaStr();
        }
        Integer saleNumber = 1;// 销售数量
        if (bo.getSaleNumber() != null) {
            saleNumber = bo.getSaleNumber();
        }
        Integer ecbuluId = 0;// 单位长度
        Integer ecbuluId1 = bo.getEcbuluId();
        if (ObjUtil.isNotNull(ecbuluId1)) {
            ecbuluId = ecbuluId1;
        }
        BigDecimal profit = BigDecimal.ZERO;// 利润
        if (bo.getProfit() != null) {
            profit = bo.getProfit();
        }
        BigDecimal billPercent = BigDecimal.ZERO;// 实际税点
        if (bo.getBillPercent() != null) {
            billPercent = bo.getBillPercent();
        }
        EcuqInput record = new EcuqInput();
        //List<EcuSilk> listSilk = ecuSilkService.getListByCompanyId(sysUser.getEcCompanyId(), true);
        // log.info("h2");
        EcuqInput object;
        if (ecuqiId == 0) {// 插入
            int sortId = 1;
            record.setEcuqId(ecuqId);
            EcuqInput ecuqInput = ecuqInputService.getLatestObject(record);
            if (ecuqInput != null) {
                sortId = ecuqInput.getSortId() + 1;
            }
            record.setEcuqId(ecuqId);
            record.setEcqulId(ecqulId);
            record.setEcbusId(storeId);
            record.setStartType(true);
            record.setSortId(sortId);

            record.setSilkId(silkId);
            record.setSilkName(silkName);
            record.setEcusmId(silkModelId);
            record.setSilkModelName(SilkModelName);
            record.setSilkNameAs("");
            record.setSilkNameInput(false);
            record.setAreaStr(areaStr);
            record.setAreaStrAs("");
            record.setAreaStrInput(false);
            record.setSaleNumber(saleNumber);
            record.setEcbuluId(ecbuluId);
            record.setProfit(profit);
            record.setProfitInput(false);
            record.setBillPercent(billPercent);
            record.setItemDesc("");
            //boolean silkNameIsExists = false;
            //for (EcuSilk ecuSilk : listSilk) {
            //    if (ecuSilk.getAbbreviation().equals(silkName)) {
            //        silkNameIsExists = true;
            //        break;
            //    }
            //}
            //log.info("silkNameIsExists + " + silkNameIsExists);
            //if (!silkNameIsExists) {
            //    throw new RuntimeException("型号错误");
            //} else {
            ecuqInputService.insert(record);
            //}
            object = record;
        } else {// 修改
            log.info("update");
            record.setEcuqiId(ecuqiId);
            if (ecqulId != 0) {// 质量等级ID
                record.setEcqulId(ecqulId);
                ecuqDescModel.cleanUnitPriceInput(ecuqiId, false);
            }
            if (storeId != 0) {// 仓库ID
                record.setEcbusId(storeId);
                ecuqDescModel.cleanUnitPriceInput(ecuqiId, false);
            }
            if (!silkName.isEmpty()) {// 型号名称
                record.setSilkName(silkName);
                ecuqDescModel.cleanUnitPriceInput(ecuqiId, false);
            }
            if (!areaStr.isEmpty()) {// 截面
                record.setAreaStr(areaStr);
            }
            if (bo.getSaleNumber() != null) {// 销售数量
                record.setSaleNumber(saleNumber);
            }
            if (bo.getEcbuluId() != null) {// 单位长度
                record.setEcbuluId(ecbuluId);
            }
            if (bo.getProfit() != null) {// 利润
                EcuqInput ecuqInput = getObjectPassEcuqiId(ecuqiId);
                profit = ecProfitModel.dealProfitAuto(ecuqInput);
                if (profit.compareTo(BigDecimal.ZERO) == 0 && ecuqInput.getProfitInput()) {
                    profit = bo.getProfit();
                }
                record.setProfit(profit);
            }
            if (billPercent.compareTo(BigDecimal.ZERO) > 0) {// 实际税点
                record.setBillPercent(billPercent);
            }
            log.info("record + " + CommonFunction.getGson().toJson(record));
            //boolean silkNameIsExists = false;
            //for (EcuSilk ecSilk : listSilk) {
            //    if (ecSilk.getAbbreviation().equals(silkName)) {
            //        silkNameIsExists = true;
            //        break;
            //    }
            //}
            //log.info("silkNameIsExists + " + silkNameIsExists);
            //if (!silkNameIsExists) {
            //    ecuqInputService.delete(ecuqiId);
            //    ecuqDescModel.delete(ecuqiId);
            //    throw new RuntimeException("型号错误");
            //} else {
            record.setEcusmId(silkModelId);
            record.setSilkModelName(SilkModelName);
            ecuqInputService.update(record);
            //}
            object = getObjectPassEcuqiId(ecuqiId);
        }
        if (object != null && object.getEcbusId() != 0
                && object.getEcqulId() != 0
                && !"".equals(object.getSilkName())
                && !"".equals(object.getAreaStr())) {
            log.info("详情修改");
            ecuqDescModel.deal(object, sysUser.getEcCompanyId());
        }
        return object;
    }

    public EcuqInput getObject(InputBaseBo bo) {
        Integer ecuqiId = bo.getEcuqiId();
        EcuqInput object;
        EcuqInput record = new EcuqInput();
        record.setEcuqiId(ecuqiId);
        object = ecuqInputService.getObject(record);
        if (object.getEcqulId() != 0) {
            EcquLevel recordEcquLevel = new EcquLevel();
            recordEcquLevel.setEcqulId(object.getEcqulId());
            EcquLevel ecquLevel = ecquLevelService.getObject(recordEcquLevel);
            object.setEcquLevel(ecquLevel);
        }
        if (object.getEcbusId() != 0) {
            EcbuStore recordEcbuStore = new EcbuStore();
            recordEcbuStore.setEcbusId(object.getEcbusId());
            EcbuStore ecbuStore = ecbuStoreService.getObject(recordEcbuStore);
            object.setEcbuStore(ecbuStore);
        }
        EcuqDesc recordEcuqDesc = new EcuqDesc();
        recordEcuqDesc.setEcuqiId(ecuqiId);
        EcuqDesc ecuqDesc = ecuqDescService.getObject(recordEcuqDesc);
        if (ecuqDesc != null) {
            object.setEcuqDesc(ecuqDesc);
        }
        return object;
    }


    @Transactional(rollbackFor = Exception.class)
    public void delete(InputBaseBo bo) {
        Integer ecuqi_id;
        Integer ecuqiId = bo.getEcuqiId();
        EcuqInput record = new EcuqInput();
        record.setEcuqiId(ecuqiId);
        EcuqInput object = ecuqInputService.getObject(record);
        if (ObjUtil.isNull(object)) {
            throw new RuntimeException("数据不存在，请刷新页面重试！");
        }
        Integer ecuqId = object.getEcuqId();
        Integer sortId = object.getSortId();
        ecuqInputService.reduceSort(ecuqId, sortId);
        ecuqInputService.delete(ecuqiId);
        // 删除对应的Ecq_desc 报价详情
        ecuqDescService.deletePassEcuqiId(ecuqiId);
    }

    public InputListVo getListQuoted(InputListBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        Integer ecuId = sysUser.getUserId();
        Integer ecCompanyId = sysUser.getEcCompanyId();
        Integer ecuqId = bo.getEcuqId();
        EcuQuoted ecuQuoted = ecuQuotedService.getById(ecuqId);
        if (ObjUtil.isNull(ecuQuoted)) {
            throw new RuntimeException("未查询到当前报价单");
        }
        EcbuPcompany ecbuPcompany = ecbuPcompanyModel.getObjectPassEcbupId(ecuQuoted.getEcbupId());
        if (ObjUtil.isNull(ecbuPcompany)) {
            throw new RuntimeException("对应平台加点方案不存在");
        }
        Integer ecbudId = bo.getEcbudId();

        List<EcuqInput> listInput = getListByQuoteId(ecuqId);
        BigDecimal billTotalMoney = BigDecimal.ZERO;// 开票总计
        BigDecimal noBillTotalMoney = BigDecimal.ZERO;// 不开票总计
        BigDecimal allWeight = BigDecimal.ZERO;// 总重量
        BigDecimal deliverySinglePercentMoney;// 加点运费
        BigDecimal billSingleMoney;// 开票单价
        BigDecimal noBillSingleMoney;// 不开票单价
        BigDecimal billComputeMoney;// 开票小计
        BigDecimal noBillComputeMoney;// 不开票小计

        boolean delivery = ecbudId != -1 && ecuQuoted.getEcbudId() != -1;
        BigDecimal price = BigDecimal.ZERO;

        for (EcuqInput ecuqInput : listInput) {
            String silkName = ecuqInput.getSilkName();
            Integer storeId = ecuqInput.getEcbusId();
            Integer ecqulId = ecuqInput.getEcqulId();
            String areaStr = ecuqInput.getAreaStr();
            Integer saleNumber = ecuqInput.getSaleNumber();
            if (storeId == 0 || ecqulId == 0 || "".equals(silkName) || "".equals(areaStr) || saleNumber == 0) {
                continue;
            }
            // log.info("h12");
            Integer ecuqiId = ecuqInput.getEcuqiId();
            EcuqDesc recordEcuqDesc = new EcuqDesc();
            recordEcuqDesc.setEcuqiId(ecuqiId);
            EcuqDesc ecuqDesc = ecuqDescService.getObject(recordEcuqDesc);
            if (ecuqDesc == null) {
                continue;
            }
            ecuqInput.setEcuqDesc(ecuqDesc);
            EcbuConductor ecbuConductor = ecbuConductorService.getById(ecuqDesc.getEcbucId());// 导体
            ecuqInput.setEcbuConductor(ecbuConductor);
            EcquParameter recordEcquParameter = new EcquParameter();
            recordEcquParameter.setEcbusId(storeId);
            recordEcquParameter.setEcqulId(ecqulId);
            // log.info("recordEcuqParameter + " + CommonFunction.getGson().toJson(recordEcquParameter));
            EcquParameter ecquParameter = ecquParameterService.getObject(recordEcquParameter);// 参数
            if (ecquParameter == null) {
                ecquParameter = new EcquParameter();
                ecquParameter.setLength(BigDecimal.ONE);
                ecquParameter.setCost(BigDecimal.ONE);
            }
            //导体计算
            ConductorComputeExtendBo mapConductor = EcableFunction.getConductorData(ecuqInput, ecuqDesc, ecquParameter, ecbuConductor);
            BigDecimal unitMoney;// 单位金额 1米长度的金额 需要配合仓库参数
            BigDecimal externalDiameter;// 导体外径
            BigDecimal fireDiameter = mapConductor.getFireDiameter();// 粗芯外径
            BigDecimal zeroDiameter = mapConductor.getZeroDiameter();// 细芯外径
            BigDecimal conductorWeight = mapConductor.getConductorWeight();// 导体重量
            BigDecimal conductorMoney = mapConductor.getConductorMoney();// 导体金额
            ecuqDescModel.updateConductorWeight(ecuqInput, conductorWeight);// 更新导体重量
            // 计算云母带数据
            EcbuMicaTape ecbuMicatape = null;
            if (ecuqDesc.getEcbumId() != 0) {
                ecbuMicatape = ecbuMicatapeService.getById(ecuqDesc.getEcbumId());
            }
            MicaTapeComputeBo mapMicaTape = EcableFunction
                    .getMicaTapeData(ecuqInput, ecuqDesc, ecbuMicatape, fireDiameter, zeroDiameter, ecquParameter);
            BigDecimal fireMicaTapeRadius = mapMicaTape.getFireMicaTapeRadius();
            BigDecimal zeroMicaTapeRadius = mapMicaTape.getZeroMicaTapeRadius();
            BigDecimal micaTapeWeight = mapMicaTape.getMicaTapeWeight();// 云母带重量
            BigDecimal micaTapeMoney = mapMicaTape.getMicaTapeMoney();// 云母带金额
            BigDecimal micaTapeThickness = ecuqDesc.getMicatapeThickness();// 云母带厚度
            // 计算绝缘数据
            EcbuInsulation ecbuInsulation = null;
            if (ecuqDesc.getEcbuiId() != 0) {
                ecbuInsulation = ecbuInsulationService.getById(ecuqDesc.getEcbuiId());
            }
            InsulationComputeBo mapInsulation = EcableFunction.getInsulationData(ecuqInput,
                    ecuqDesc, ecbuInsulation, fireDiameter, zeroDiameter, fireMicaTapeRadius,
                    zeroMicaTapeRadius, ecquParameter);
            BigDecimal insulationFireThickness = ecuqDesc.getInsulationFireThickness();// 粗芯绝缘厚度
            BigDecimal insulationZeroThickness = ecuqDesc.getInsulationZeroThickness();// 细芯绝缘厚度
            BigDecimal insulationWeight = mapInsulation.getInsulationWeight();// 绝缘重量
            BigDecimal insulationMoney = mapInsulation.getInsulationMoney();// 绝缘金额
            // 计算填充物数据
            EcbuInfilling ecbuInfilling = null;
            if (ecuqDesc.getEcbuinId() != 0) {
                ecbuInfilling = ecbuInfillingService.getById(ecuqDesc.getEcbuinId());
            }
            InfillingComputeBo mapInfilling = EcableFunction.getInfillingData(ecuqInput, ecquParameter, ecbuInfilling,
                    fireDiameter, zeroDiameter,
                    micaTapeThickness, insulationFireThickness, insulationZeroThickness);
            externalDiameter = mapInfilling.getExternalDiameter();
            BigDecimal infillingWeight = mapInfilling.getInfillingWeight();// 填充物重量
            BigDecimal infillingMoney = mapInfilling.getInfillingMoney();// 填充物金额
            // 计算包带数据
            EcbuBag ecbuBag = null;
            Integer ecbub22Id = ecuqDesc.getEcbub22Id();
            if (silkName.contains("22") || silkName.contains("23")) {// 铠装
                if (ecbub22Id != 0) {
                    ecbuBag = ecbuBagService.getById(ecbub22Id);
                }
            } else {
                if (ecuqDesc.getEcbubId() != 0) {
                    ecbuBag = ecbuBagService.getById(ecuqDesc.getEcbubId());
                }
            }
            BagComputeBo mapBag = EcableFunction.getBagData(ecuqInput, ecuqDesc, ecquParameter, ecbuBag, externalDiameter);
            BigDecimal bagWeight = mapBag.getBagWeight();// 包带重量
            BigDecimal bagMoney = mapBag.getBagMoney();// 包带金额
            // 计算屏蔽数据
            BigDecimal shieldWeight = BigDecimal.ZERO;
            BigDecimal shieldMoney = BigDecimal.ZERO;
            BigDecimal shieldThickness = ecuqDesc.getShieldThickness();
            if (ecuqDesc.getEcbuShieldId() != 0 && shieldThickness.compareTo(BigDecimal.ZERO) != 0) {
                EcbuShield ecbuShield = ecbuShieldService.getById(ecuqDesc.getEcbuShieldId());
                if (ecbuShield != null) {
                    BigDecimal totalShieldDiameter = externalDiameter
                            .add(ecuqDesc.getBagThickness()
                                    .divide(new BigDecimal("100"), 20, RoundingMode.HALF_UP)
                                    .multiply(new BigDecimal("2"))
                            )
                            .add(shieldThickness.divide(new BigDecimal("100"), 20, RoundingMode.HALF_UP)
                                    .multiply(new BigDecimal("2"))
                            );
                    BigDecimal totalShieldVolume = totalShieldDiameter
                            .divide(new BigDecimal("2"), 20, RoundingMode.HALF_UP)
                            .multiply(totalShieldDiameter.divide(new BigDecimal("2"), 20, RoundingMode.HALF_UP)
                            );
                    BigDecimal innerShieldDiameter = externalDiameter
                            .add(ecuqDesc.getBagThickness()
                                    .divide(new BigDecimal("100"), 20, RoundingMode.HALF_UP)
                                    .multiply(new BigDecimal("2"))
                            );
                    BigDecimal innerShieldVolume = innerShieldDiameter
                            .divide(new BigDecimal("2"), 20, RoundingMode.HALF_UP)
                            .multiply(innerShieldDiameter.divide(new BigDecimal("2"),
                                    20, RoundingMode.HALF_UP)
                            );
                    BigDecimal remainShieldVolume = (totalShieldVolume
                            .subtract(innerShieldVolume))
                            .multiply(BigDecimal.valueOf(Math.PI));
                    shieldMoney = remainShieldVolume
                            .multiply(BigDecimal.ONE.add(ecuqDesc.getShieldPercent()))
                            .multiply(ecquParameter.getLength());
                }
            }
            // 计算钢带数据
            EcbuSteelband ecbuSteelband = null;
            if (ecuqDesc.getEcbusbId() != 0 && ecuqDesc.getSteelbandThickness().compareTo(BigDecimal.ZERO) != 0) {
                EcbuSteelband recordEcbuSteelband = new EcbuSteelband();
                recordEcbuSteelband.setEcbusId(ecuqDesc.getEcbusbId());
                ecbuSteelband = ecbuSteelbandService.getObject(recordEcbuSteelband);
            }
            SteelBandComputeBo mapSteelBand = EcableFunction
                    .getSteelBandData(ecuqDesc, ecquParameter, ecbuSteelband, externalDiameter);
            BigDecimal steelBandWeight = mapSteelBand.getSteelbandWeight();// 钢带重量
            BigDecimal steelBandMoney = mapSteelBand.getSteelbandMoney();// 钢带金额
            // 计算护套数据
            EcbuSheath ecbuSheath = null;
            if (ecuqDesc.getEcbuSheathId() != 0 && ecuqDesc.getSheathThickness()
                    .compareTo(BigDecimal.ZERO) != 0) {
                EcbuSheath recordEcbuSheath = new EcbuSheath();
                recordEcbuSheath.setEcbusId(ecuqDesc.getEcbuSheathId());
                ecbuSheath = ecbuSheathService.getObject(recordEcbuSheath);
            }
            SheathComputeBo mapSheath = EcableFunction.getSheathData(ecuqInput, ecuqDesc, ecquParameter, ecbuSheath, externalDiameter);
            BigDecimal sheathWeight = mapSheath.getSheathWeight();// 护套重量
            BigDecimal sheathMoney = mapSheath.getSheathMoney();// 护套金额
            // 总额
            unitMoney = conductorMoney// 导体金额
                    .add(micaTapeMoney)// 云母带金额
                    .add(insulationMoney)// 绝缘金额
                    .add(infillingMoney)// 填充物金额
                    .add(bagMoney)// 包带金额
                    .add(shieldMoney)// 屏蔽数据
                    .add(steelBandMoney)// 钢带数据
                    .add(sheathMoney);// 护套数据
            unitMoney = unitMoney.multiply(BigDecimal.ONE.add(ecuqDesc.getStorePercent()));// 仓库对应导体加点
            unitMoney = unitMoney.multiply(BigDecimal.ONE.add(ecuqInput.getProfit()));// 利润
            // 更新税前单价 当税前价格变为手输以后不再更新价格
            if (ecuqDesc.getUnitPriceInput()) {
                unitMoney = ecuqDesc.getUnitPrice();
            } else {
                ecuqDescModel.dealUnitPrice(ecuqiId, ecuqDesc.getUnitPriceInput(), unitMoney);// 税前单价提交
                ecuqDesc.setUnitPrice(unitMoney);
                ecuqInput.setEcuqDesc(ecuqDesc);
            }
            //单位重量
            BigDecimal singleWeight = conductorWeight
                    .add(micaTapeWeight)
                    .add(insulationWeight)
                    .add(infillingWeight)
                    .add(bagWeight)
                    .add(shieldWeight)
                    .add(steelBandWeight)
                    .add(sheathWeight);
            //销售数量
            BigDecimal saleDecimal = new BigDecimal(saleNumber);
            BigDecimal totalWeight = singleWeight.multiply(saleDecimal);
            // 单位
            EcbulUnit ecbulUnit = null;
            Integer meterNumber = saleNumber;
            BigDecimal meterNumberDecimal = BigDecimal.ZERO;
            if (ecuqInput.getEcbuluId() != 0) {
                ecbulUnit = ecbulUnitService.getById(ecuqInput.getEcbuluId());
                ecuqInput.setEcbulUnit(ecbulUnit);
                //如果有单位数量的话，重新计算销售的米数
                meterNumber = ecbulUnit.getMeterNumber() * saleNumber;
                meterNumberDecimal = new BigDecimal(ecbulUnit.getMeterNumber());
            }
            ecuqInput.setMeterNumber(meterNumber);
            BigDecimal saleMeterNumber = saleDecimal.multiply(meterNumberDecimal);
            if (ecbulUnit != null) {
                totalWeight = singleWeight.multiply(saleMeterNumber);
            }
            ecuqInput.setTotalWeight(totalWeight);
            allWeight = allWeight.add(totalWeight);
            deliverySinglePercentMoney = singleWeight.multiply(ecuqDesc.getSdunitMoney());
            unitMoney = unitMoney.add(deliverySinglePercentMoney);
            // 最终价格
            EcduCompany recordEcduCompany = new EcduCompany();
            recordEcduCompany.setEcCompanyId(ecCompanyId);
            recordEcduCompany.setDefaultType(true);
            EcduCompany ecduCompany = ecduCompanyService.getObject(recordEcduCompany);
            //获取发票数据
            BillBo mapBillPercent = EcableFunction.getBillPercentData(ecuqInput, ecduCompany, unitMoney, ecbulUnit);
            billSingleMoney = mapBillPercent.getBillSingleMoney();
            billComputeMoney = mapBillPercent.getBillComputeMoney();
            noBillSingleMoney = unitMoney;// 不开票单价
            noBillComputeMoney = unitMoney.multiply(saleDecimal);// 不开票小计
            if (ecbulUnit != null) {
                noBillComputeMoney = unitMoney.multiply(saleDecimal).multiply(meterNumberDecimal);// 不开票小计
            }
            // 平台加点
            BigDecimal pcPercent = ecbuPcompany.getPcPercent();
            BigDecimal add = BigDecimal.ONE.add(pcPercent);
            billSingleMoney = billSingleMoney.multiply(add);
            noBillSingleMoney = noBillSingleMoney.multiply(add);
            billComputeMoney = billComputeMoney.multiply(add);
            noBillComputeMoney = noBillComputeMoney.multiply(add);
            // 启用手输价格
            if (ecuqDesc.getInputStart()) {
                billSingleMoney = ecuqDesc.getBupsMoney();
                billComputeMoney = ecuqDesc.getBupcMoney();
                if (ecbulUnit == null) {
                    if (billSingleMoney.compareTo(BigDecimal.ZERO) > 0) {
                        if (billComputeMoney.divide(billSingleMoney, 0, RoundingMode.HALF_UP)
                                .compareTo(saleDecimal) != 0) {
                            billComputeMoney = ecuqDesc.getBupsMoney()
                                    .multiply(saleDecimal);
                        }
                    }
                } else {
                    if (noBillComputeMoney.compareTo(BigDecimal.ZERO) > 0) {
                        if (billComputeMoney.divide(billSingleMoney, 0, RoundingMode.HALF_UP)
                                .compareTo(saleMeterNumber) != 0) {
                            billComputeMoney = ecuqDesc.getBupsMoney()
                                    .multiply(saleDecimal)
                                    .multiply(meterNumberDecimal);
                        }
                    }
                }
                noBillSingleMoney = ecuqDesc.getNbupsMoney();
                noBillComputeMoney = ecuqDesc.getNbupcMoney();
                if (ecbulUnit == null) {
                    if (billSingleMoney.compareTo(BigDecimal.ZERO) > 0) {
                        if (noBillComputeMoney.divide(noBillSingleMoney, 0, RoundingMode.HALF_UP)
                                .compareTo(saleDecimal) != 0) {
                            noBillComputeMoney = ecuqDesc.getNbupsMoney()
                                    .multiply(saleDecimal);
                        }
                    }
                } else {
                    if (noBillComputeMoney.compareTo(BigDecimal.ZERO) > 0) {
                        if (noBillComputeMoney.divide(noBillSingleMoney, 0, RoundingMode.HALF_UP)
                                .compareTo(saleMeterNumber) != 0) {
                            noBillComputeMoney = ecuqDesc.getNbupsMoney().multiply(saleDecimal).multiply(meterNumberDecimal);
                        }
                    }
                }
            }
            //不启用手输价格
            if (!ecuqDesc.getInputStart()) {
                //billSingleMoney = ecuqInput.getBillSingleMoney();// 开票单价
                //noBillSingleMoney = ecuqInput.getNoBillSingleMoney();// 不开票单价
                billSingleMoney = billSingleMoney.add(ecuQuoted.getUnitPriceAdd());
                noBillSingleMoney = noBillSingleMoney.add(ecuQuoted.getUnitPriceAdd());
                // 加价百分比
                billSingleMoney = billSingleMoney.multiply(BigDecimal.ONE.add(ecuQuoted.getAddPricePercent()));
                billComputeMoney = billSingleMoney.multiply(saleDecimal);
                noBillSingleMoney = noBillSingleMoney.multiply(BigDecimal.ONE.add(ecuQuoted.getAddPricePercent()));
                noBillComputeMoney = noBillSingleMoney.multiply(saleDecimal);
                if (ecbulUnit != null) {
                    billSingleMoney = billSingleMoney.multiply(meterNumberDecimal);
                    billComputeMoney = billSingleMoney.multiply(saleDecimal);
                    noBillSingleMoney = noBillSingleMoney.multiply(meterNumberDecimal);// 必须后算
                    noBillComputeMoney = noBillSingleMoney.multiply(saleDecimal);
                }
                ecuqInput.setBillSingleMoney(billSingleMoney.stripTrailingZeros());// 有票单价
                ecuqInput.setNoBillSingleMoney(noBillSingleMoney.stripTrailingZeros());// 无票单价
                ecuqInput.setBillComputeMoney(billComputeMoney.stripTrailingZeros());// 有票小计
                ecuqInput.setNoBillComputeMoney(noBillComputeMoney.stripTrailingZeros());// 无票小计
                ecuqDescModel.dealMoney(ecuqiId, noBillSingleMoney, billSingleMoney, noBillComputeMoney, billComputeMoney);
                billTotalMoney = billTotalMoney.add(billComputeMoney).stripTrailingZeros();
                noBillTotalMoney = noBillTotalMoney.add(noBillComputeMoney).stripTrailingZeros();

                if (delivery) {
                    billSingleMoney = ecuqInput.getBillSingleMoney();// 开票单价
                    noBillSingleMoney = ecuqInput.getNoBillSingleMoney();// 不开票单价
                    billComputeMoney = ecuqInput.getBillComputeMoney();// 开票小计
                    noBillComputeMoney = ecuqInput.getNoBillComputeMoney();// 不开票小计
                    singleWeight = ecuqDesc.getWeight();
                    // 如果有木轴所分得的单价
                    if (ecuqDesc.getEcbuaId() != 0) {
                        EcbuAxle recordEcbuAxle = new EcbuAxle();
                        recordEcbuAxle.setEcbuaId(ecuqDesc.getEcbuaId());
                        EcbuAxle ecbuAxle = ecbuAxleService.getObject(recordEcbuAxle);
                        singleWeight = ecuqDesc.getWeight()
                                .add(ecbuAxle.getAxleWeight().multiply(new BigDecimal(ecuqDesc.getAxleNumber())));
                    }
                    BigDecimal percent = singleWeight.divide(allWeight, 6, RoundingMode.HALF_UP);
                    BigDecimal singleMoney = price.multiply(percent)
                            .divide(saleDecimal, 6, RoundingMode.HALF_UP);
                    if (ecbulUnit != null) {
                        singleMoney = price.multiply(percent)
                                .divide(saleMeterNumber, 6, RoundingMode.HALF_UP);
                    }
                    BigDecimal computeMoney = price.multiply(percent);
                    if (!ecuqDesc.getInputStart()) {
                        billSingleMoney = billSingleMoney.add(singleMoney);
                        billComputeMoney = billComputeMoney.add(computeMoney);
                        noBillSingleMoney = noBillSingleMoney.add(singleMoney);
                        noBillComputeMoney = noBillComputeMoney.add(computeMoney);
                    }
                    billTotalMoney = billTotalMoney.add(billComputeMoney);
                    noBillTotalMoney = noBillTotalMoney.add(noBillComputeMoney);
                    // 提交详情金额
                    ecuqInput.setBillSingleMoney(billSingleMoney);// 有票单价
                    ecuqInput.setNoBillSingleMoney(noBillSingleMoney);// 无票单价
                    ecuqInput.setBillComputeMoney(billComputeMoney);// 有票小计
                    ecuqInput.setNoBillComputeMoney(noBillComputeMoney);// 无票小计
                    ecuqDescModel.dealMoney(ecuqiId, noBillSingleMoney, billSingleMoney, noBillComputeMoney, billComputeMoney);
                }
            }
            // 加上木轴价格
            if (ecuqDesc.getEcbuaId() != 0) {
                EcbuAxle ecbuAxle = ecbuAxleService.getById(ecuqDesc.getEcbuaId());
                BigDecimal axlePrice = ecbuAxle.getAxlePrice().multiply(new BigDecimal(ecuqDesc.getAxleNumber()));
                if (ecbulUnit == null) {
                    billSingleMoney = billSingleMoney.add(axlePrice.divide(saleDecimal, 6, RoundingMode.HALF_UP));
                    billComputeMoney = billComputeMoney.add(axlePrice);
                    noBillSingleMoney = noBillSingleMoney.add(axlePrice.divide(saleDecimal, 6, RoundingMode.HALF_UP));
                } else {
                    billSingleMoney = billSingleMoney.add(axlePrice.divide(saleMeterNumber, 6, RoundingMode.HALF_UP));
                    billComputeMoney = billComputeMoney.add(axlePrice);
                    noBillSingleMoney = noBillSingleMoney
                            .add(axlePrice.divide(saleMeterNumber, 6, RoundingMode.HALF_UP));
                }
                noBillComputeMoney = noBillComputeMoney.add(axlePrice);
                // 总重加上木轴的重量
                allWeight = allWeight.add(ecbuAxle.getAxleWeight().multiply(new BigDecimal(ecuqDesc.getAxleNumber())));
            }
            ecuqInput.setBillSingleMoney(billSingleMoney);// 有票单价
            ecuqInput.setNoBillSingleMoney(noBillSingleMoney);// 无票单价
            ecuqInput.setBillComputeMoney(billComputeMoney);// 有票小计
            ecuqInput.setNoBillComputeMoney(noBillComputeMoney);// 无票小计
            // log.info("billSingMoney + " + billSingleMoney);
            // log.info("billComputeMoney + " + billComputeMoney);
            // 提交详情金额
            ecuqDescModel.dealMoney(ecuqiId, noBillSingleMoney, billSingleMoney, noBillComputeMoney, billComputeMoney);
            ecuqDescModel.dealWeight(ecuqDesc.getEcuqdId(), totalWeight);
            billTotalMoney = billTotalMoney.add(billComputeMoney);// 开票总额
            noBillTotalMoney = noBillTotalMoney.add(noBillComputeMoney);
        }
        List<EcuqInputVo> voList = new ArrayList<>();
        for (EcuqInput ecuqInput : listInput) {
            EcuqInputVo vo = new EcuqInputVo();
            BeanUtils.copyProperties(ecuqInput, vo);
            EcuqDesc ecuqDesc = ecuqInput.getEcuqDesc();
            if (ObjUtil.isNotNull(ecuqDesc)) {
                vo.setEcbuaId(ecuqDesc.getEcbuaId());
                vo.setAxleNumber(ecuqDesc.getAxleNumber());
                vo.setUnitPrice(ecuqDesc.getUnitPrice());
            }
            voList.add(vo);
        }

        // ------以下是快递数据-------------
        List<DeliveryObj> listDeliveryPrice = ecbuDeliveryModel.getDeliveryPriceList(ecCompanyId, ecuQuoted.getDeliveryStoreId(), ecuQuoted, allWeight);
        log.info("listDeliveryPrice + " + CommonFunction.getGson().toJson(listDeliveryPrice));
        EcbudDelivery recordEcbudDelivery = new EcbudDelivery();
        recordEcbudDelivery.setEcCompanyId(ecCompanyId);
        recordEcbudDelivery.setEcuId(ecuId);// 用户
        EcbudDelivery dDelivery = ecbudDeliveryService.getObject(recordEcbudDelivery);
        if (dDelivery == null) {
            recordEcbudDelivery.setSortId(1);
            ecbudDeliveryService.insert(recordEcbudDelivery);
            dDelivery = recordEcbudDelivery;
            //recordEcbudDelivery = new EcbudDelivery();
            //recordEcbudDelivery.setEcCompanyId(ecCompanyId);
            //recordEcbudDelivery.setEcuId(ecuId);// 用户
            //dDelivery = ecbudDeliveryService.getObject(recordEcbudDelivery);
        }
        DeliveryBo mapDelivery = EcableFunction.getDeliveryData(ecuQuoted, listDeliveryPrice, dDelivery, ecbudId);
        DeliveryObj objectDelivery = mapDelivery.getObjectDelivery();
        // ------以上是快递数据-------------
        // log.info("ecbudId + " + ecbudId);
        if (delivery) {
            price = objectDelivery.getPrice();
            // log.info("price + " + price);
            if (ecuQuoted.getDeliveryDivide().compareTo(BigDecimal.ZERO) != 0) {
                price = price.divide(ecuQuoted.getDeliveryDivide(), 6, RoundingMode.HALF_UP);
            }
            if (ecuQuoted.getDeliveryAdd().compareTo(BigDecimal.ZERO) != 0) {
                price = price.add(ecuQuoted.getDeliveryAdd());
            }
            log.info("本报价单运费金额 : {} ", price);
        }
        ecuQuotedModel.dealMoney(ecuqId, noBillTotalMoney, billTotalMoney, price, allWeight);
        // 添加报价单总额
        return new InputListVo(billTotalMoney, noBillTotalMoney, voList, listDeliveryPrice);
    }

    // getStructurePassId 通过ecuqiId获取结构体
    public InputStructureVo getStructurePassId(InputBaseBo bo) {
        Integer ecuqiId = bo.getEcuqiId();
        EcuqInput recordEcuqInput = new EcuqInput();
        recordEcuqInput.setEcuqiId(ecuqiId);
        EcuqInput ecuqInput = ecuqInputService.getObject(recordEcuqInput);
        EcuqDesc recordEcuqDesc = new EcuqDesc();
        recordEcuqDesc.setEcuqiId(ecuqiId);
        EcuqDesc ecuqDesc = ecuqDescService.getObject(recordEcuqDesc);
        EcbuConductor recordEcbuConductor = new EcbuConductor();
        recordEcbuConductor.setEcbucId(ecuqDesc.getEcbucId());
        EcbuConductor ecbuConductor = ecbuConductorService.getObject(recordEcbuConductor);
        EcquParameter recordEcquParameter = new EcquParameter();
        recordEcquParameter.setEcbusId(ecuqInput.getEcbusId());
        recordEcquParameter.setEcqulId(ecuqInput.getEcqulId());
        EcquParameter ecquParameter = ecquParameterService.getObject(recordEcquParameter);
        // 计算导体
        String[] areaArr = (ecuqInput.getAreaStr()).split("\\+");
        ConductorComputeExtendBo mapConductor = EcableFunction.getConductorData(ecuqInput, ecuqDesc, ecquParameter, ecbuConductor);

        InputStructureVo inputStructureVo = new InputStructureVo();
        // log.info(CommonFunction.getGson().toJson(CommonFunction.getGson().toJson(mapConductor)));
        BigDecimal externalDiameter;// 导体外径

        BigDecimal fireDiameter = mapConductor.getFireDiameter();// 粗芯外径
        BigDecimal zeroDiameter = mapConductor.getZeroDiameter();// 细芯外径
        BigDecimal conductorWeight = mapConductor.getConductorWeight();// 导体重量
        BigDecimal conductorMoney = mapConductor.getConductorMoney();// 导体金额

        BigDecimal conductorDiameter = mapConductor.getExternalDiameter();
        recordEcuqDesc = new EcuqDesc();
        recordEcuqDesc.setEcuqdId(ecuqDesc.getEcuqdId());
        recordEcuqDesc.setCweight(conductorWeight);
        ecuqDescService.update(recordEcuqDesc);// 更新导体重量

        inputStructureVo.setConductorDiameter(conductorDiameter);
        inputStructureVo.setFireDiameter(fireDiameter);
        inputStructureVo.setFireDiameter(fireDiameter);
        inputStructureVo.setZeroDiameter(zeroDiameter);
        inputStructureVo.setFireWeight(mapConductor.getFireWeight());
        inputStructureVo.setFireMoney(mapConductor.getFireMoney());
        inputStructureVo.setZeroWeight(mapConductor.getZeroWeight());
        inputStructureVo.setZeroMoney(mapConductor.getZeroMoney());
        inputStructureVo.setConductorWeight(conductorWeight);
        inputStructureVo.setConductorMoney(conductorMoney);

        // 计算云母带数据
        BigDecimal fireMicatapeDiameter;
        BigDecimal zeroMicatapeDiameter;
        BigDecimal micatapeMoney = BigDecimal.ZERO;
        BigDecimal micatapeWeight = BigDecimal.ZERO;
        BigDecimal micatapeThickness = ecuqDesc.getMicatapeThickness();
        BigDecimal fireMicatapeRadius = BigDecimal.ZERO;// 粗芯云母带半径
        BigDecimal zeroMicatapeRadius = BigDecimal.ZERO;// 细芯云母带半径
        if (ecuqDesc.getEcbumId() != 0) {
            EcbuMicaTape recordEcbuMicaTape = new EcbuMicaTape();
            recordEcbuMicaTape.setEcbumId(ecuqDesc.getEcbumId());
            EcbuMicaTape ecbuMicatape = ecbuMicatapeService.getObject(recordEcbuMicaTape);
            ecuqDesc.setEcbuMicatape(ecbuMicatape);
            MicaTapeComputeBo mapMicatape = EcableFunction.getMicaTapeData(ecuqInput, ecuqDesc,
                    ecbuMicatape, fireDiameter, zeroDiameter, ecquParameter);

            fireMicatapeRadius = mapMicatape.getFireMicaTapeRadius();// 粗芯云母带半径
            zeroMicatapeRadius = mapMicatape.getZeroMicaTapeRadius();// 细芯云母带半径

            micatapeWeight = mapMicatape.getMicaTapeWeight();// 云母带重量
            micatapeMoney = mapMicatape.getMicaTapeMoney();// 云母带金额

            fireMicatapeDiameter = mapMicatape.getFireMicaTapeRadius().multiply(new BigDecimal("2"));// 粗芯云母带直径
            zeroMicatapeDiameter = mapMicatape.getZeroMicaTapeRadius().multiply(new BigDecimal("2"));// 细芯云母带直径

            micatapeThickness = ecuqDesc.getMicatapeThickness();// 云母带厚度
            inputStructureVo.setFireMicatapeDiameter(fireMicatapeDiameter);
            inputStructureVo.setZeroMicatapeDiameter(zeroMicatapeDiameter);
            inputStructureVo.setMicatapeWeight(micatapeWeight);
            inputStructureVo.setMicatapeMoney(micatapeMoney);

        }
        // 计算绝缘数据
        // System.out.println("h5");
        BigDecimal insulationWeight = BigDecimal.ZERO;
        BigDecimal insulationMoney = BigDecimal.ZERO;
        BigDecimal insulationFireThickness = ecuqDesc.getInsulationFireThickness();// 粗芯绝缘厚度
        BigDecimal insulationZeroThickness = ecuqDesc.getInsulationZeroThickness();// 细芯绝缘厚度
        BigDecimal insulationFireDiameter;
        BigDecimal insulationZeroDiameter;
        if (ecuqDesc.getEcbuiId() != 0) {
            EcbuInsulation recordEcbuInsulation = new EcbuInsulation();
            recordEcbuInsulation.setEcbuiId(ecuqDesc.getEcbuiId());
            EcbuInsulation ecbuInsulation = ecbuInsulationService.getObject(recordEcbuInsulation);
            ecuqDesc.setEcbuInsulation(ecbuInsulation);
            InsulationComputeBo mapInsulation = EcableFunction.getInsulationData(ecuqInput, ecuqDesc,
                    ecbuInsulation, fireDiameter, zeroDiameter, fireMicatapeRadius,
                    zeroMicatapeRadius, ecquParameter);
            insulationFireThickness = ecuqDesc.getInsulationFireThickness();// 粗芯绝缘厚度
            insulationZeroThickness = ecuqDesc.getInsulationZeroThickness();// 细芯绝缘厚度
            insulationWeight = mapInsulation.getInsulationWeight();// 绝缘重量
            insulationMoney = mapInsulation.getInsulationMoney();// 绝缘金额
            insulationFireDiameter = mapInsulation.getFireInsulationRadius().multiply(new BigDecimal("2"));
            insulationZeroDiameter = mapInsulation.getZeroInsulationRadius().multiply(new BigDecimal("2"));

            inputStructureVo.setInsulationFireDiameter(insulationFireDiameter);
            inputStructureVo.setInsulationZeroDiameter(insulationZeroDiameter);
            inputStructureVo.setInsulationWeight(insulationWeight);
            inputStructureVo.setInsulationMoney(insulationMoney);
        }
        // 计算填充物数据
        BigDecimal wideDiameter = fireDiameter// 粗芯直径
                .add(micatapeThickness.multiply(new BigDecimal("2")))
                .add(insulationFireThickness.multiply(new BigDecimal("2")));
        BigDecimal fineDiameter = zeroDiameter// 细芯直径
                .add(micatapeThickness.multiply(new BigDecimal("2")))
                .add(insulationZeroThickness.multiply(new BigDecimal("2")));
        externalDiameter = EcableFunction.getExternalDiameter(areaArr, wideDiameter, fineDiameter);// 外径

        inputStructureVo.setExternalDiameter(externalDiameter);

        BigDecimal infillingWeight = BigDecimal.ZERO;
        BigDecimal infillingMoney = BigDecimal.ZERO;
        if (ecuqDesc.getEcbuinId() != 0) {
            EcbuInfilling recordEcbuInfilling = new EcbuInfilling();
            recordEcbuInfilling.setEcbuiId(ecuqDesc.getEcbuinId());
            EcbuInfilling ecbuInfilling = ecbuInfillingService.getObject(recordEcbuInfilling);
            ecuqDesc.setEcbuInfilling(ecbuInfilling);
            InfillingComputeBo mapInfilling = EcableFunction.getInfillingData(ecuqInput,
                    ecquParameter, ecbuInfilling, fireDiameter, zeroDiameter,
                    micatapeThickness, insulationFireThickness, insulationZeroThickness);
            infillingWeight = mapInfilling.getInfillingWeight();// 填充物重量
            infillingMoney = mapInfilling.getInfillingMoney();// 填充物金额

            inputStructureVo.setInfillingWeight(infillingWeight);
            inputStructureVo.setInsulationMoney(infillingMoney);
        }
        // 计算包带数据
        BigDecimal bagWeight = BigDecimal.ZERO;
        BigDecimal bagMoney = BigDecimal.ZERO;
        BigDecimal bagDiameter;
        BagComputeBo mapBag;
        if (ecuqInput.getSilkName().contains("22") || ecuqInput.getSilkName().contains("23")) {// 铠装
            if (ecuqDesc.getEcbub22Id() != 0) {
                EcbuBag recordEcbuBag = new EcbuBag();
                recordEcbuBag.setEcbubId(ecuqDesc.getEcbub22Id());
                EcbuBag ecbuBag = ecbuBagService.getObject(recordEcbuBag);
                ecuqDesc.setEcbuBag(ecbuBag);
                mapBag = EcableFunction.getBagData(ecuqInput, ecuqDesc, ecquParameter, ecbuBag, externalDiameter);
                bagWeight = mapBag.getBagWeight();// 包带重量
                bagMoney = mapBag.getBagMoney();// 包带金额
                bagDiameter = mapBag.getBagRadius().multiply(BigDecimal.valueOf(2));

                inputStructureVo.setBagDiameter(bagDiameter);
                inputStructureVo.setBagWeight(bagWeight);
                inputStructureVo.setBagMoney(bagMoney);
            }
        } else {
            if (ecuqDesc.getEcbubId() != 0) {
                EcbuBag recordEcbuBag = new EcbuBag();
                recordEcbuBag.setEcbubId(ecuqDesc.getEcbubId());
                EcbuBag ecbuBag = ecbuBagService.getObject(recordEcbuBag);
                ecuqDesc.setEcbuBag(ecbuBag);
                mapBag = EcableFunction.getBagData(ecuqInput, ecuqDesc,
                        ecquParameter, ecbuBag, externalDiameter);
                bagWeight = mapBag.getBagWeight();// 包带重量
                bagMoney = mapBag.getBagMoney();// 包带金额
                bagDiameter = mapBag.getBagRadius().multiply(BigDecimal.valueOf(2));

                inputStructureVo.setBagDiameter(bagDiameter);
                inputStructureVo.setBagWeight(bagWeight);
                inputStructureVo.setBagMoney(bagMoney);
            }
        }

        // 计算屏蔽数据
        BigDecimal shieldWeight = BigDecimal.ZERO;
        BigDecimal shieldMoney = BigDecimal.ZERO;
        if (ecuqDesc.getEcbuShieldId() != 0 && ecuqDesc.getShieldThickness().compareTo(BigDecimal.ZERO) != 0) {
            EcbuShield recordEcbuShield = new EcbuShield();
            recordEcbuShield.setEcbusId(ecuqDesc.getEcbusbId());
            EcbuShield ecbuShield = ecbuShieldService.getObject(recordEcbuShield);
            ecuqDesc.setEcbuShield(ecbuShield);
            if (ecbuShield != null) {
                BigDecimal totalShieldDiameter = externalDiameter.add(ecuqDesc.getBagThickness()
                        .divide(new BigDecimal("100"), 20, RoundingMode.HALF_UP)
                        .multiply(new BigDecimal("2"))
                ).add(ecuqDesc.getShieldThickness()
                        .divide(new BigDecimal("100"), 20, RoundingMode.HALF_UP)
                        .multiply(new BigDecimal("2"))
                );
                BigDecimal totalShieldVolume = totalShieldDiameter
                        .divide(new BigDecimal("2"), 20, RoundingMode.HALF_UP)
                        .multiply(totalShieldDiameter.divide(new BigDecimal("2"), 20, RoundingMode.HALF_UP));
                BigDecimal innerShieldDiameter = externalDiameter
                        .add(ecuqDesc.getBagThickness().divide(new BigDecimal("100"), 20, RoundingMode.HALF_UP)
                                .multiply(new BigDecimal("2")));
                BigDecimal innerShieldVolume = innerShieldDiameter
                        .divide(new BigDecimal("2"), 20, RoundingMode.HALF_UP)
                        .multiply(innerShieldDiameter.divide(new BigDecimal("2"), 20, RoundingMode.HALF_UP));
                shieldWeight = (totalShieldVolume
                        .subtract(innerShieldVolume))
                        .multiply(ecbuShield.getDensity())
                        .multiply(BigDecimal.ONE.add(ecuqDesc.getShieldPercent()))
                        .multiply(ecquParameter.getLength())
                        .multiply(BigDecimal.valueOf(Math.PI));
                shieldMoney = shieldWeight.multiply(ecbuShield.getUnitPrice());
                inputStructureVo.setShieldWeight(shieldWeight);
                inputStructureVo.setShieldMoney(shieldMoney);
            }
        }
        // 计算钢带数据
        // System.out.println("h9");
        BigDecimal steelbandWeight = BigDecimal.ZERO;
        BigDecimal steelbandMoney = BigDecimal.ZERO;
        if (ecuqDesc.getEcbusbId() != 0 && ecuqDesc.getSteelbandThickness().compareTo(BigDecimal.ZERO) != 0) {
            EcbuSteelband recordEcbuSteelband = new EcbuSteelband();
            recordEcbuSteelband.setEcbusId(ecuqDesc.getEcbusbId());
            EcbuSteelband ecbuSteelband = ecbuSteelbandService.getObject(recordEcbuSteelband);
            ecuqDesc.setEcbuSteelband(ecbuSteelband);
            SteelBandComputeBo mapSteelband = EcableFunction.getSteelBandData(ecuqDesc, ecquParameter, ecbuSteelband, externalDiameter);

            steelbandWeight = mapSteelband.getSteelbandWeight();// 钢带重量
            steelbandMoney = mapSteelband.getSteelbandMoney();// 钢带金额
            BigDecimal steelbandDiameter = mapSteelband.getTotalSteelBandRadius().multiply(new BigDecimal("2"));

            inputStructureVo.setSteelbandDiameter(steelbandDiameter);
            inputStructureVo.setSteelbandWeight(steelbandWeight);
            inputStructureVo.setSteelbandMoney(steelbandMoney);

        }
        // 计算护套数据
        BigDecimal sheathWeight = BigDecimal.ZERO;
        BigDecimal sheathMoney = BigDecimal.ZERO;
        if (ecuqDesc.getEcbuSheathId() != 0 && ecuqDesc.getSheathThickness().compareTo(BigDecimal.ZERO) != 0) {
            EcbuSheath recordEcbuSheath = new EcbuSheath();
            recordEcbuSheath.setEcbusId(ecuqDesc.getEcbuSheathId());
            EcbuSheath ecbuSheath = ecbuSheathService.getObject(recordEcbuSheath);
            ecuqDesc.setEcbuSheath(ecbuSheath);
            EcbSheath recordEcbSheath = new EcbSheath();
            recordEcbSheath.setEcbsId(ecuqDesc.getEcbuSheathId());
            EcbSheath ecbSheath = ecbSheathService.getObject(recordEcbSheath);
            ecuqDesc.setEcbSheath(ecbSheath);
            SheathComputeBo mapSheath = EcableFunction.getSheathData(ecuqInput, ecuqDesc, ecquParameter, ecbuSheath, externalDiameter);

            sheathWeight = mapSheath.getSheathWeight();// 护套重量
            sheathMoney = mapSheath.getSheathMoney();// 护套金额
            inputStructureVo.setSheathDiameter(mapSheath.getTotalSheathRadius().multiply(BigDecimal.valueOf(2)));
            inputStructureVo.setSheathWeight(sheathWeight);
            inputStructureVo.setSheathMoney(sheathMoney);

        }
        BigDecimal totalWeight = conductorWeight
                .add(micatapeWeight)
                .add(bagWeight)
                .add(shieldWeight)
                .add(sheathWeight)
                .add(insulationWeight)
                .add(infillingWeight)
                .add(steelbandWeight)
                .add(sheathWeight);
        BigDecimal totalMoney = conductorMoney
                .add(micatapeMoney)
                .add(bagMoney)
                .add(shieldMoney)
                .add(sheathMoney)
                .add(insulationMoney)
                .add(infillingMoney)
                .add(steelbandMoney)
                .add(sheathMoney);

        inputStructureVo.setTotalWeight(totalWeight);
        inputStructureVo.setTotalMoney(totalMoney);
        inputStructureVo.setEcuqDesc(ecuqDesc);
        return inputStructureVo;
    }

    // getStructureTemporary 通过ecuqiId获取结构体
    public Map<String, Object> getStructureTemporary(InputStructBo bo) {

        Map<String, Object> map = new HashMap<>();
        Integer ecuqiId = bo.getEcuqiId();
        EcuqInput recordEcuqInput = new EcuqInput();
        recordEcuqInput.setEcuqiId(ecuqiId);
        EcuqInput ecuqInput = ecuqInputService.getObject(recordEcuqInput);
        EcuqDesc recordEcuqDesc = new EcuqDesc();
        recordEcuqDesc.setEcuqiId(ecuqiId);
        EcuqDesc ecuqDesc = ecuqDescService.getObject(recordEcuqDesc);
        Integer ecbucId = bo.getEcbucId();
        ecuqDesc.setEcbucId(ecbucId);
        EcbuConductor recordEcbuConductor = new EcbuConductor();
        recordEcbuConductor.setEcbucId(ecuqDesc.getEcbucId());
        EcbuConductor ecbuConductor = ecbuConductorService.getObject(recordEcbuConductor);
        EcquParameter recordEcquParameter = new EcquParameter();
        recordEcquParameter.setEcbusId(ecuqInput.getEcbusId());
        recordEcquParameter.setEcqulId(ecuqInput.getEcqulId());
        EcquParameter ecquParameter = ecquParameterService.getObject(recordEcquParameter);
        // 计算导体
        BigDecimal fireSilkNumber = bo.getFireSilkNumber();
        BigDecimal fireStrand = bo.getFireStrand();
        BigDecimal zeroSilkNumber = bo.getZeroSilkNumber();
        BigDecimal zeroStrand = bo.getZeroStrand();

        ecuqDesc.setFireSilkNumber(fireSilkNumber);
        ecuqDesc.setFireStrand(fireStrand);
        ecuqDesc.setZeroSilkNumber(zeroSilkNumber);
        ecuqDesc.setZeroStrand(zeroStrand);
        String[] areaArr = (ecuqInput.getAreaStr()).split("\\+");
        ConductorComputeExtendBo mapConductor = EcableFunction.getConductorData(ecuqInput, ecuqDesc, ecquParameter, ecbuConductor);
        BigDecimal fireDiameter = bo.getFireDiameter();// 粗芯外径
        BigDecimal zeroDiameter = bo.getZeroDiameter();// 细芯外径
        BigDecimal externalDiameter;// 导体外径
        BigDecimal conductorWeight = bo.getConductorWeight();// 导体重量
        BigDecimal conductorMoney = bo.getConductorMoney();// 导体金额
        recordEcuqDesc = new EcuqDesc();
        recordEcuqDesc.setEcuqdId(ecuqDesc.getEcuqdId());
        recordEcuqDesc.setCweight(conductorWeight);
        ecuqDescService.update(recordEcuqDesc);// 更新导体重量
        map.put("fireDiameter", fireDiameter);
        map.put("zeroDiameter", zeroDiameter);
        map.put("fireWeight", mapConductor.getFireWeight());
        map.put("fireMoney", mapConductor.getFireMoney());
        map.put("zeroWeight", mapConductor.getZeroWeight());
        map.put("zeroMoney", mapConductor.getZeroMoney());
        map.put("conductorWeight", conductorWeight);
        map.put("conductorMoney", conductorMoney);
        // 计算云母带数据
        BigDecimal micatapeMoney = BigDecimal.ZERO;
        BigDecimal micatapeWeight = BigDecimal.ZERO;
        BigDecimal micatapeThickness = ecuqDesc.getMicatapeThickness();
        BigDecimal fireMicatapeRadius = BigDecimal.ZERO;// 粗芯云母带半径
        BigDecimal zeroMicatapeRadius = BigDecimal.ZERO;// 细芯云母带半径
        BigDecimal fireMicatapeDiameter;// 粗芯云母带直径
        BigDecimal zeroMicatapeDiameter;// 细芯云母带真径
        if (ecuqDesc.getEcbumId() != 0) {
            Integer ecbumId = bo.getEcbumId();
            micatapeThickness = bo.getMicatapeThickness();
            ecuqDesc.setEcbumId(ecbumId);
            ecuqDesc.setMicatapeThickness(micatapeThickness);
            EcbuMicaTape recordEcbuMicaTape = new EcbuMicaTape();
            recordEcbuMicaTape.setEcbumId(ecuqDesc.getEcbumId());
            EcbuMicaTape ecbuMicatape = ecbuMicatapeService.getObject(recordEcbuMicaTape);
            ecuqDesc.setEcbuMicatape(ecbuMicatape);
            MicaTapeComputeBo mapMicatape = EcableFunction.getMicaTapeData(ecuqInput,
                    ecuqDesc, ecbuMicatape,
                    fireDiameter,
                    zeroDiameter,
                    ecquParameter);
            fireMicatapeRadius = mapMicatape.getFireMicaTapeRadius();// 粗芯云母带半径
            zeroMicatapeRadius = mapMicatape.getZeroMicaTapeRadius();// 细芯云母带半径
            fireMicatapeDiameter = mapMicatape.getFireMicaTapeRadius().multiply(new BigDecimal("2"));// 粗芯云母带半径
            zeroMicatapeDiameter = mapMicatape.getZeroMicaTapeRadius().multiply(new BigDecimal("2"));// 细芯云母带半径
            micatapeThickness = ecuqDesc.getMicatapeThickness();// 云母带厚度
            micatapeWeight = mapMicatape.getMicaTapeWeight();// 云母带重量
            micatapeMoney = mapMicatape.getMicaTapeMoney();// 云母带金额
            map.put("fireMicatapeDiameter", fireMicatapeDiameter);
            map.put("zeroMicatapeDiameter", zeroMicatapeDiameter);
            map.put("micatapeWeight", micatapeWeight);
            map.put("micatapeMoney", micatapeMoney);
        }
        // 计算绝缘数据
        // System.out.println("h5");
        BigDecimal insulationWeight = BigDecimal.ZERO;
        BigDecimal insulationMoney = BigDecimal.ZERO;
        BigDecimal insulationFireThickness = ecuqDesc.getInsulationFireThickness();// 粗芯绝缘厚度
        BigDecimal insulationZeroThickness = ecuqDesc.getInsulationZeroThickness();// 细芯绝缘厚度
        BigDecimal insulationFireDiameter;
        BigDecimal insulationZeroDiameter;
        if (ecuqDesc.getEcbuiId() != 0) {
            Integer ecbuiId = bo.getEcbuiId();
            insulationFireThickness = bo.getInsulationFireThickness();
            insulationZeroThickness = bo.getInsulationZeroThickness();
            ecuqDesc.setEcbuiId(ecbuiId);
            ecuqDesc.setInsulationFireThickness(insulationFireThickness);
            ecuqDesc.setInsulationZeroThickness(insulationZeroThickness);
            EcbuInsulation recordEcbuInsulation = new EcbuInsulation();
            recordEcbuInsulation.setEcbuiId(ecuqDesc.getEcbuiId());
            EcbuInsulation ecbuInsulation = ecbuInsulationService.getObject(recordEcbuInsulation);
            ecuqDesc.setEcbuInsulation(ecbuInsulation);
            InsulationComputeBo mapInsulation = EcableFunction.getInsulationData(ecuqInput,
                    ecuqDesc, ecbuInsulation,
                    fireDiameter,
                    zeroDiameter,
                    fireMicatapeRadius,
                    zeroMicatapeRadius,
                    ecquParameter);
            insulationFireThickness = ecuqDesc.getInsulationFireThickness();// 粗芯绝缘厚度
            insulationZeroThickness = ecuqDesc.getInsulationZeroThickness();// 细芯绝缘厚度
            insulationWeight = mapInsulation.getInsulationWeight();// 绝缘重量
            insulationMoney = mapInsulation.getInsulationMoney();// 绝缘金额
            insulationFireDiameter = mapInsulation.getFireInsulationRadius().multiply(new BigDecimal("2"));
            insulationZeroDiameter = mapInsulation.getZeroInsulationRadius().multiply(new BigDecimal("2"));

            map.put("insulationFireDiameter", insulationFireDiameter);
            map.put("insulationZeroDiameter", insulationZeroDiameter);
            map.put("insulationWeight", insulationWeight);
            map.put("insulationMoney", insulationMoney);
        }
        // 计算填充物数据
        BigDecimal wideDiameter = fireDiameter// 粗芯直径
                .add(micatapeThickness.multiply(new BigDecimal("2")))
                .add(insulationFireThickness.multiply(new BigDecimal("2")));
        BigDecimal fineDiameter = zeroDiameter// 细芯直径
                .add(micatapeThickness.multiply(new BigDecimal("2")))
                .add(insulationZeroThickness.multiply(new BigDecimal("2")));
        externalDiameter = EcableFunction.getExternalDiameter(areaArr, wideDiameter, fineDiameter);// 外径
        map.put("externalDiameter", externalDiameter);
        BigDecimal infillingWeight = BigDecimal.ZERO;
        BigDecimal infillingMoney = BigDecimal.ZERO;
        if (ecuqDesc.getEcbuinId() != 0) {
            Integer ecbuinId = bo.getEcbuinId();
            ecuqDesc.setEcbuinId(ecbuinId);
            EcbuInfilling recordEcbuInfilling = new EcbuInfilling();
            recordEcbuInfilling.setEcbuiId(ecuqDesc.getEcbuinId());
            EcbuInfilling ecbuInfilling = ecbuInfillingService.getObject(recordEcbuInfilling);
            ecuqDesc.setEcbuInfilling(ecbuInfilling);
            InfillingComputeBo mapInfilling = EcableFunction.getInfillingData(ecuqInput, ecquParameter, ecbuInfilling,
                    fireDiameter, zeroDiameter, micatapeThickness,
                    insulationFireThickness,
                    insulationZeroThickness);
            infillingWeight = mapInfilling.getInfillingWeight();// 填充物重量
            infillingMoney = mapInfilling.getInfillingMoney();// 填充物金额
            map.put("infillingWeight", infillingWeight);
            map.put("infillingMoney", infillingMoney);
        }
        // 计算包带数据
        BigDecimal bagWeight = BigDecimal.ZERO;
        BigDecimal bagMoney = BigDecimal.ZERO;
        BigDecimal bagDiameter;

        BagComputeBo mapBag;
        if (ecuqInput.getSilkName().contains("22") || ecuqInput.getSilkName().contains("23")) {// 铠装
            if (ecuqDesc.getEcbub22Id() != 0) {
                Integer ecbub22Id = bo.getEcbub22Id();
                BigDecimal bag22Thickness = bo.getBag22Thickness();
                ecuqDesc.setEcbub22Id(ecbub22Id);
                ecuqDesc.setBag22Thickness(bag22Thickness);
                EcbuBag recordEcbuBag = new EcbuBag();
                recordEcbuBag.setEcbubId(ecuqDesc.getEcbub22Id());
                EcbuBag ecbuBag = ecbuBagService.getObject(recordEcbuBag);
                ecuqDesc.setEcbuBag(ecbuBag);
                mapBag = EcableFunction.getBagData(ecuqInput, ecuqDesc, ecquParameter, ecbuBag, externalDiameter);
                bagWeight = mapBag.getBagWeight();// 包带重量
                bagMoney = mapBag.getBagMoney();// 包带金额
                bagDiameter = mapBag.getBagRadius().multiply(BigDecimal.valueOf(2));
                map.put("bagDiameter", bagDiameter);
                map.put("bagWeight", bagWeight);
                map.put("bagMoney", bagMoney);
            }
        } else {
            if (ecuqDesc.getEcbubId() != 0) {
                Integer ecbubId = bo.getEcbubId();
                BigDecimal bagThickness = bo.getBagThickness();
                ecuqDesc.setEcbubId(ecbubId);
                ecuqDesc.setBagThickness(bagThickness);
                EcbuBag recordEcbuBag = new EcbuBag();
                recordEcbuBag.setEcbubId(ecuqDesc.getEcbubId());
                EcbuBag ecbuBag = ecbuBagService.getObject(recordEcbuBag);
                ecuqDesc.setEcbuBag(ecbuBag);
                mapBag = EcableFunction.getBagData(ecuqInput, ecuqDesc, ecquParameter, ecbuBag, externalDiameter);
                bagWeight = mapBag.getBagWeight();// 包带重量
                bagMoney = mapBag.getBagMoney();// 包带金额
                bagDiameter = mapBag.getBagRadius().multiply(BigDecimal.valueOf(2));
                map.put("bagDiameter", bagDiameter);
                map.put("bagWeight", bagWeight);
                map.put("bagMoney", bagMoney);
            }
        }

        // 计算屏蔽数据
        BigDecimal shieldWeight = BigDecimal.ZERO;
        BigDecimal shieldMoney = BigDecimal.ZERO;
        if (ecuqDesc.getEcbuShieldId() != 0 && ecuqDesc.getShieldThickness().compareTo(BigDecimal.ZERO) != 0) {
            EcbuShield recordEcbuShield = new EcbuShield();
            recordEcbuShield.setEcbusId(ecuqDesc.getEcbusbId());
            EcbuShield ecbuShield = ecbuShieldService.getObject(recordEcbuShield);
            ecuqDesc.setEcbuShield(ecbuShield);
            if (ecbuShield != null) {
                BigDecimal totalShieldDiameter = externalDiameter
                        .add(ecuqDesc.getBagThickness().divide(new BigDecimal("100"), 20, RoundingMode.HALF_UP)
                                .multiply(new BigDecimal("2")))
                        .add(ecuqDesc.getShieldThickness().divide(new BigDecimal("100"), 20, RoundingMode.HALF_UP)
                                .multiply(new BigDecimal("2")));
                BigDecimal totalShieldVolume = totalShieldDiameter.divide(new BigDecimal("2"), 20, RoundingMode.HALF_UP)
                        .multiply(totalShieldDiameter.divide(new BigDecimal("2"), 20, RoundingMode.HALF_UP)
                        );
                BigDecimal innerShieldDiameter = externalDiameter.add(ecuqDesc.getBagThickness()
                        .divide(new BigDecimal("100"), 20, RoundingMode.HALF_UP)
                        .multiply(new BigDecimal("2"))
                );
                BigDecimal innerShieldVolume = innerShieldDiameter.divide(new BigDecimal("2"), 20, RoundingMode.HALF_UP)
                        .multiply(innerShieldDiameter.divide(new BigDecimal("2"), 20, RoundingMode.HALF_UP));
                shieldWeight = (totalShieldVolume
                        .subtract(innerShieldVolume))
                        .multiply(ecbuShield.getDensity())
                        .multiply(BigDecimal.ONE.add(ecuqDesc.getShieldPercent()))
                        .multiply(ecquParameter.getLength())
                        .multiply(BigDecimal.valueOf(Math.PI));
                shieldMoney = shieldWeight.multiply(ecbuShield.getUnitPrice());
                map.put("shieldWeight", shieldWeight);
                map.put("shieldMoney", shieldMoney);
            }
        }
        // 计算钢带数据
        // System.out.println("h9");
        BigDecimal steelbandWeight = BigDecimal.ZERO;
        BigDecimal steelbandMoney = BigDecimal.ZERO;
        if (ecuqDesc.getEcbusbId() != 0 && ecuqDesc.getSteelbandThickness().compareTo(BigDecimal.ZERO) != 0) {
            Integer ecbusbId = bo.getEcbusbId();
            BigDecimal steelbandThickness = bo.getSteelbandThickness();
            Integer steelbandStorey = bo.getSteelbandStorey();
            ecuqDesc.setEcbusbId(ecbusbId);
            ecuqDesc.setSteelbandThickness(steelbandThickness);
            ecuqDesc.setSteelbandStorey(steelbandStorey);
            EcbuSteelband recordEcbuSteelband = new EcbuSteelband();
            recordEcbuSteelband.setEcbusId(ecuqDesc.getEcbusbId());
            EcbuSteelband ecbuSteelband = ecbuSteelbandService.getObject(recordEcbuSteelband);
            ecuqDesc.setEcbuSteelband(ecbuSteelband);
            SteelBandComputeBo mapSteelband = EcableFunction.getSteelBandData(ecuqDesc, ecquParameter, ecbuSteelband, externalDiameter);

            steelbandWeight = mapSteelband.getSteelbandWeight();// 钢带重量
            steelbandMoney = mapSteelband.getSteelbandMoney();// 钢带金额
            BigDecimal steelbandDiameter = mapSteelband.getTotalSteelBandRadius().multiply(new BigDecimal("2"));
            map.put("steelbandDiameter", steelbandDiameter);
            map.put("steelbandWeight", steelbandWeight);
            map.put("steelbandMoney", steelbandMoney);
        }
        // 计算护套数据
        BigDecimal sheathWeight = BigDecimal.ZERO;
        BigDecimal sheathMoney = BigDecimal.ZERO;
        if (ecuqDesc.getEcbuSheathId() != 0 && ecuqDesc.getSheathThickness().compareTo(BigDecimal.ZERO) != 0) {
            Integer ecbusid = bo.getEcbsid();
            BigDecimal sheathThickness = bo.getSheathThickness();
            ecuqDesc.setEcbuSheathId(ecbusid);
            ecuqDesc.setSheathThickness(sheathThickness);
            EcbuSheath recordEcbuSheath = new EcbuSheath();
            recordEcbuSheath.setEcbusId(ecuqDesc.getEcbuSheathId());
            EcbuSheath ecbuSheath = ecbuSheathService.getObject(recordEcbuSheath);
            ecuqDesc.setEcbuSheath(ecbuSheath);
            EcbSheath recordEcbSheath = new EcbSheath();
            recordEcbSheath.setEcbsId(ecuqDesc.getEcbuSheathId());
            EcbSheath ecbSheath = ecbSheathService.getObject(recordEcbSheath);
            ecuqDesc.setEcbSheath(ecbSheath);
            SheathComputeBo mapSheath = EcableFunction.getSheathData(ecuqInput, ecuqDesc, ecquParameter, ecbuSheath, externalDiameter);
            sheathWeight = mapSheath.getSheathWeight();// 护套重量
            sheathMoney = mapSheath.getSheathMoney();// 护套金额
            map.put("sheathDiameter", mapSheath.getTotalSheathRadius().multiply(BigDecimal.valueOf(2)));// 护套外径
            map.put("sheathWeight", sheathWeight);
            map.put("sheathMoney", sheathMoney);
        }
        BigDecimal totalWeight = conductorWeight
                .add(micatapeWeight)
                .add(bagWeight)
                .add(shieldWeight)
                .add(insulationWeight)
                .add(infillingWeight)
                .add(steelbandWeight)
                .add(sheathWeight);
        BigDecimal totalMoney = conductorMoney
                .add(micatapeMoney)
                .add(bagMoney)
                .add(shieldMoney)
                .add(insulationMoney)
                .add(infillingMoney)
                .add(steelbandMoney)
                .add(sheathMoney);
        map.put("totalWeight", totalWeight);
        map.put("totalMoney", totalMoney);
        map.put("ecuqDesc", ecuqDesc);
        return map;
    }

    /**
     * 当更新到EcuqDesc时更新billPercent
     *
     * @param bo
     */
    @Transactional(rollbackFor = Exception.class)
    public void dealBatchBillPercent(InputBatchDealBo bo) {

        Integer ecuqId = bo.getEcuqId();
        Integer priceType = bo.getPriceType();

        BigDecimal billPercent = BigDecimal.ZERO;
        EcuqInput recordEcuqInput = new EcuqInput();
        recordEcuqInput.setEcuqId(ecuqId);
        List<EcuqInput> list = ecuqInputService.getList(recordEcuqInput);
        for (EcuqInput ecuqInput : list) {
            if (ecuqInput.getEcqulId() != 0) {
                EcquLevel recordEcquLevel = new EcquLevel();
                recordEcquLevel.setEcqulId(ecuqInput.getEcqulId());
                EcquLevel ecquLevel = ecquLevelService.getObject(recordEcquLevel);
                EcSilk recordEcSilk = new EcSilk();
                recordEcSilk.setEcsId(ecquLevel.getEcsId());
                EcSilk ecSilk = ecSilkService.getObject(recordEcSilk);
                if ("YJV".equals(ecSilk.getAbbreviation())) {// 铜
                    EcduTaxPoint recordEcduTaxPoint = new EcduTaxPoint();
                    recordEcduTaxPoint.setEcdtId(1);
                    EcduTaxPoint ecduTaxpoint = ecduTaxpointService.getObject(recordEcduTaxPoint);// 铜
                    EcuqInput record = new EcuqInput();
                    record.setEcuqiId(ecuqInput.getEcuqiId());
                    if (priceType == 3 || priceType == 5) {
                        billPercent = ecduTaxpoint.getPercentCommon();
                    } else if (priceType == 1 || priceType == 4) {
                        billPercent = ecduTaxpoint.getPercentSpecial();
                    }
                    record.setBillPercent(billPercent);
                    ecuqInputService.update(record);
                    EcuqDesc recordEcuqDesc = new EcuqDesc();
                    recordEcuqDesc.setEcuqiId(ecuqInput.getEcuqiId());
                    EcuqDesc ecuqDesc = ecuqDescService.getObject(recordEcuqDesc);// 清除金额
                    if (ecuqDesc != null) {
                        ecuqDescModel.cleanMoney(ecuqDesc.getEcuqdId());
                    }
                }
            }
        }
        EcuQuoted recordEcuQuoted = new EcuQuoted();
        recordEcuQuoted.setEcuqId(ecuqId);
        recordEcuQuoted.setBillPercentType(priceType);
        // log.info(String.valueOf(recordEcuQuoted));
        ecuQuotedService.update(recordEcuQuoted);
        ecuQuotedModel.cleanMoney(ecuqId);// 清除报价单总额
    }

    // dealSort
    public void dealSort(InputSortBo bo) {

        Integer ecuqiId = bo.getEcuqiId();
        Integer sortId = bo.getSortId();
        EcuqInput record = new EcuqInput();
        record.setEcuqiId(ecuqiId);
        record.setSortId(sortId);
        ecuqInputService.update(record);
    }

    // dealItemDesc
    public void dealItemDesc(InputItemDescBo bo) {

        Integer ecuqiId = bo.getEcuqiId();
        String itemDesc = bo.getItemDesc();
        EcuqInput record = new EcuqInput();
        record.setEcuqiId(ecuqiId);
        record.setItemDesc(itemDesc);
        ecuqInputService.update(record);
    }

    // dealProfitInput
    public void dealProfitInput(InputProfitBo bo) {

        Integer ecuqiId = bo.getEcuqiId();
        Boolean profitInput = bo.getProfitInput();
        EcuqInput record = new EcuqInput();
        record.setEcuqiId(ecuqiId);
        record.setProfitInput(profitInput);
        ecuqInputService.update(record);
    }

    // importData
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    public void importData(MultipartFile file, InputImportBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        Integer ecuqId = bo.getEcuqId();
        assert file != null;
        InputStream in = file.getInputStream();
        List<List<Object>> listob = excelUtils.getListByExcel(in, file.getOriginalFilename());
        Integer ecbusId;// 仓库ID
        Integer ecqulId = 0;// 质量等级
        String silkName;// 型号
        String areaStr;// 规格
        Integer saleNumber;// 数量
        Integer ecbuluId;// 单位
        List<EcuqInput> list = new ArrayList<>();
        for (List<Object> objects : listob) {
            // log.info(CommonFunction.getGson().toJson(objects));
            String storeName = objects.get(0).toString();// 仓库名称
            EcbuStore ecbuStore = ecbuStoreModel
                    .getObjectPassEcCompanyIdAndStoreName(sysUser.getEcCompanyId(), storeName);
            if (ecbuStore == null) {
                list = new ArrayList<>();
                break;
            } else {
                ecbusId = ecbuStore.getEcbusId();
            }
            String levelName = objects.get(1).toString();// 质量等级名称
            EcquLevel ecquLevel = ecquLevelModel
                    .getObjectPassEcCompanyIdAndName(sysUser.getEcCompanyId(), levelName);
            if (ecquLevel == null) {
                list = new ArrayList<>();
                break;
            } else {
                ecqulId = ecquLevel.getEcqulId();
                areaStr = objects.get(3).toString();// 截面
                Boolean areaIsExists = ecdAreaModel.isExistsPassEcqulId(ecqulId, areaStr);
                if (!areaIsExists) {
                    list = new ArrayList<>();
                    break;
                }
            }
            silkName = objects.get(2).toString();// 型号
            List<EcSilk> listSilk = ecSilkModel.getListAllSilkName(sysUser.getEcCompanyId());
            for (EcSilk ecSilk : listSilk) {
                if (!silkName.equals(ecSilk.getAbbreviation())) {
                    list = new ArrayList<>();
                    break;
                }
            }
            String saleNumberStr = objects.get(4).toString();// 销售数量
            if (!CommonFunction.isNumeric(saleNumberStr)) {
                list = new ArrayList<>();
                break;
            } else {
                saleNumber = Integer.parseInt(saleNumberStr);
            }
            String lengthName = objects.get(5).toString();// 销售数量
            lengthName = lengthName.replace("（", "(");
            lengthName = lengthName.replace("）", ")");
            if ("米".equals(lengthName)) {
                ecbuluId = 0;
            } else {
                EcbulUnit ecbulUnit = ecbulUnitModel
                        .getObjectPassEcCompanyIdAndLengthName(sysUser.getEcCompanyId(), lengthName);
                if (ecbulUnit == null) {
                    list = new ArrayList<>();
                    break;
                } else {
                    ecbuluId = ecbulUnit.getEcbuluId();
                }
            }
            EcuqInput recordInput = new EcuqInput();
            recordInput.setEcbusId(ecbusId);
            recordInput.setEcqulId(ecqulId);
            recordInput.setSilkName(silkName);
            recordInput.setAreaStr(areaStr);
            recordInput.setSaleNumber(saleNumber);
            recordInput.setEcbuluId(ecbuluId);
            list.add(recordInput);
        }
        if (!list.isEmpty()) {
            EcuqInput record = new EcuqInput();
            record.setEcuqId(ecuqId);
            Integer sortId = 1;
            EcuqInput inputObject = ecuqInputService.getLatestObject(record);
            if (inputObject != null) {
                sortId = inputObject.getSortId() + 1;
            }
            for (EcuqInput ecuqInput : list) {
                BigDecimal profit = BigDecimal.ZERO;// 利润
                if (bo.getProfit() != null) {
                    profit = bo.getProfit();
                }
                BigDecimal billPercent = BigDecimal.ZERO;// 实际税点
                if (bo.getBillPercent() != null) {
                    billPercent = bo.getBillPercent();
                }
                record = new EcuqInput();
                record.setEcuqId(ecuqId);
                record.setEcqulId(ecqulId);
                record.setEcbusId(ecuqInput.getEcbusId());
                record.setStartType(true);
                record.setSortId(sortId);
                record.setSilkName(ecuqInput.getSilkName());
                record.setAreaStr(ecuqInput.getAreaStr());
                record.setSaleNumber(ecuqInput.getSaleNumber());
                record.setEcbuluId(ecuqInput.getEcbuluId());
                record.setProfit(profit);
                record.setProfitInput(false);
                record.setBillPercent(billPercent);
                record.setItemDesc("");
                ecuqInputService.insert(record);
                // 新增时返回最后一个input
                //EcuqInput recordEcuqInput = new EcuqInput();
                //recordEcuqInput.setEcuqId(ecuqId);
                //EcuqInput object = ecuqInputService.getLatestObject(record);
                if (record.getEcbusId() != 0
                        && record.getEcqulId() != 0
                        && !"".equals(record.getSilkName())
                        && !"".equals(record.getAreaStr())) {
                    // log.info("详情修改");
                    ecuqDescModel.deal(record, sysUser.getEcCompanyId());
                }
                sortId = sortId + 1;
            }
        }
    }

    // getObjectPassSilkName 根据丝型号获取默认的质量等级
    public Integer getObjectPassSilkName(InputSilkNameBo bo) {
        Integer ecqulId = bo.getEcqulId();
        String silkName = bo.getSilkName();

        Integer mecqulId = null;
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        Integer ecuId = sysUser.getUserId();
        Integer ecCompanyId = sysUser.getEcCompanyId();
        Integer ecsId = ecSilkModel.getEcsId(ecCompanyId, silkName);
        EcquLevel ecquLevel = ecquLevelModel.getObjectPassEcqulId(ecqulId);
        if (ecquLevel == null || !Objects.equals(ecsId, ecquLevel.getEcsId())) {
            ecquLevel = ecquLevelModel.getObjectPassEcsIdAndDefaultType(ecCompanyId, ecsId);
            if (ecquLevel != null) {
                mecqulId = ecquLevel.getEcqulId();
            }
        }
        return mecqulId;
    }

    // dealSilkNameAs 修改丝名称的别名
    public void dealSilkNameAs(InputSilkNameAsBo bo) {

        Integer ecuqiId = bo.getEcuqiId();
        String silkNameAs = bo.getSilkNameAs();
        EcuqInput record = new EcuqInput();
        record.setEcuqiId(ecuqiId);
        record.setSilkNameInput(true);
        record.setSilkNameAs(silkNameAs);
        log.info("record + " + CommonFunction.getGson().toJson(record));
        ecuqInputService.update(record);
    }

    // dealAreaStrAs 修改丝名称的别名
    public void dealAreaStrAs(InputAreaStrAsBo bo) {
        Integer ecuqiId = bo.getEcuqiId();
        String areaStrAs = bo.getAreaStrAs();
        log.info("areaStrAs + " + areaStrAs);
        EcuqInput record = new EcuqInput();
        record.setEcuqiId(ecuqiId);
        record.setAreaStrAs(areaStrAs);
        record.setAreaStrInput(true);
        log.info("record + " + CommonFunction.getGson().toJson(record));
        ecuqInputService.update(record);
    }

    // dealSilkNameInput 修改丝名称是否手输
    public void dealSilkNameInput(InputBo bo) {

        Integer ecuqiId = bo.getEcuqiId();
        EcuqInput record = new EcuqInput();
        record.setEcuqiId(ecuqiId);
        record.setSilkNameInput(false);
        // log.info("record + " + CommonFunction.getGson().toJson(record));
        ecuqInputService.update(record);
    }

    // dealAreaStrInput 修改截面是否手输
    public void dealAreaStrInput(InputBo bo) {

        Integer ecuqiId = bo.getEcuqiId();

        EcuqInput record = new EcuqInput();
        record.setEcuqiId(ecuqiId);
        record.setAreaStrInput(false);
        // log.info("record + " + CommonFunction.getGson().toJson(record));
        ecuqInputService.update(record);
    }

    /***===数据模型===***/
    /**
     * 根据导体是铜是铝更新税点信息
     *
     * @param ecuqiId
     * @param conductorType
     */
    public void dealBillPercent(Integer ecuqiId, Integer conductorType) {
        //EcuqInput recordEcuqInput = new EcuqInput();
        //recordEcuqInput.setEcuqiId(ecuqiId);
        //EcuqInput ecuqInput = ecuqInputService.getObject(recordEcuqInput);
        EcuqInput record = new EcuqInput();
        record.setEcuqiId(ecuqiId);
        //系统税点设置 1 铜 2 铝
        EcduTaxPoint recordEcduTaxPoint = new EcduTaxPoint();
        recordEcduTaxPoint.setEcdtId(conductorType);
        EcduTaxPoint ecduTaxpoint = ecduTaxpointService.getObject(recordEcduTaxPoint);
        BigDecimal billPercent = ecduTaxpoint.getPercentSpecial();
        record.setBillPercent(billPercent);
        //}
        ecuqInputService.update(record);
    }


    public List<EcuqInput> getListByQuoteId(Integer ecuqId) {
        EcuqInput record = new EcuqInput();
        record.setEcuqId(ecuqId);
        List<EcuqInput> list = ecuqInputService.getList(record);
        return list;
    }


    public long getCount(Integer ecuqId) {
        long count;
        EcuqInput record = new EcuqInput();
        record.setEcuqId(ecuqId);
        count = ecuqInputService.getCount(record);
        return count;
    }

    // getObjectPassEcuqiId
    public EcuqInput getObjectPassEcuqiId(Integer ecuqiId) {
        EcuqInput record = new EcuqInput();
        record.setEcuqiId(ecuqiId);
        return ecuqInputService.getObject(record);
    }

}
