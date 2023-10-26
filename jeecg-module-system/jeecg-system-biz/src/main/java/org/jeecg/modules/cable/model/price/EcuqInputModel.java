package org.jeecg.modules.cable.model.price;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.price.input.bo.*;
import org.jeecg.modules.cable.controller.price.input.vo.InputListVo;
import org.jeecg.modules.cable.controller.price.input.vo.InputStructureVo;
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
import org.jeecg.modules.cable.model.user.EcUserModel;

import org.jeecg.modules.cable.model.userCommon.EcbuPcompanyModel;
import org.jeecg.modules.cable.model.userCommon.EcbuStoreModel;
import org.jeecg.modules.cable.model.userCommon.EcbulUnitModel;
import org.jeecg.modules.cable.model.userDelivery.EcbuDeliveryModel;
import org.jeecg.modules.cable.service.price.EcSilkService;
import org.jeecg.modules.cable.service.price.EcuQuotedService;
import org.jeecg.modules.cable.service.price.EcuqDescService;
import org.jeecg.modules.cable.service.price.EcuqInputService;
import org.jeecg.modules.cable.service.quality.EcquLevelService;
import org.jeecg.modules.cable.service.quality.EcquParameterService;
import org.jeecg.modules.cable.service.systemEcable.EcbSheathService;
import org.jeecg.modules.cable.service.user.EcUserService;
import org.jeecg.modules.cable.service.userCommon.*;
import org.jeecg.modules.cable.service.userDelivery.EcbudDeliveryService;
import org.jeecg.modules.cable.service.userEcable.*;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.jeecg.modules.cable.tools.EcableFunction;
import org.jeecg.modules.cable.tools.ExcelUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class EcuqInputModel {
    @Resource
    EcuQuotedService ecuQuotedService;//报价单
    @Resource
    EcuqInputService ecuqInputService;//手输详情
    @Resource
    EcbuStoreService ecbuStoreService;//仓库
    @Resource
    EcuqDescModel ecuqDescModel;//结构详情
    @Resource
    EcquLevelService ecquLevelService;//质量等级
    @Resource
    EcSilkService ecSilkService;//丝类型
    @Resource
    EcduTaxpointService ecduTaxpointService;//发票税点
    @Resource
    EcuqDescService ecuqDescService;//详情
    @Resource
    EcbulUnitService ecbulUnitService;//单位长度
    @Resource
    EcbuConductorService ecbuConductorService;//用户导体
    @Resource
    EcquParameterService ecquParameterService;//质量等级参数
    @Resource
    EcbuMicatapeService ecbuMicatapeService;//用户云母带
    @Resource
    EcbuInsulationService ecbuInsulationService;//用户绝缘
    @Resource
    EcbuInfillingService ecbuInfillingService;//填充物
    @Resource
    EcbuBagService ecbuBagService;//用户包带
    @Resource
    EcbuShieldService ecbuShieldService;//用户屏蔽
    @Resource
    EcbuSteelbandService ecbuSteelbandService;//用户钢带
    @Resource
    EcbuSheathService ecbuSheathService;//用户护套
    @Resource
    EcduCompanyService ecduCompanyService;//公司数据相关
    @Resource
    EcbuAxleService ecbuAxleService;//木轴
    @Resource
    EcbuDeliveryModel ecbuDeliveryModel;//快递
    @Resource
    EcbudDeliveryService ecbudDeliveryService;//默认快递
    @Resource
    EcbSheathService ecbSheathService;//系统护套
    @Resource
    EcuQuotedModel ecuQuotedModel;
    @Resource
    EcSilkModel ecSilkModel;
    @Resource
    EcquLevelModel ecquLevelModel;//质量等级
    @Resource
    EcProfitModel ecProfitModel;//利润
    @Resource
    EcbuPcompanyModel ecbuPcompanyModel;
    ExcelUtils excelUtils = new ExcelUtils();
    @Resource
    EcbuStoreModel ecbuStoreModel;//仓库
    @Resource
    EcUserModel ecUserModel;//用户
    @Resource
    EcdAreaModel ecdAreaModel;//截面库
    @Resource
    EcbulUnitModel ecbulUnitModel;//单位

    //deal
    public EcuqInput deal(InputDealBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        Integer ecuId = ecUser.getEcuId();

        Integer ecuqiId = bo.getEcuqiId();//主键ID
        Integer ecuqId = 0;//报价单ID
        if (bo.getEcuqId() != null) {
            ecuqId = bo.getEcuqId();
        }
        Integer ecqulId = 0;//质量等级ID
        if (bo.getEcqulId() != null) {
            ecqulId = bo.getEcqulId();
        }
        Integer storeId = 0;//仓库ID
        String storeName = bo.getStoreName();
        if (StrUtil.isNotBlank(storeName)) {
            EcbuStore recordStore = new EcbuStore();
            recordStore.setEcCompanyId(ecUser.getEcCompanyId());
            recordStore.setStoreName(storeName);
            EcbuStore ecbuStore = ecbuStoreService.getObjectPassStoreName(recordStore);
            if (ecbuStore != null) {
                storeId = ecbuStore.getEcbusId();
            }
        }
        String silkName = "";//丝名称
        if (bo.getSilkName() != null) {
            silkName = bo.getSilkName();
        }
        String areaStr = "";//截面
        if (bo.getAreaStr() != null) {
            areaStr = bo.getAreaStr();
        }
        //log.info("areaStr + " + areaStr);
        Integer saleNumber = 1;//销售数量
        if (bo.getSaleNumber() != null) {
            saleNumber = bo.getSaleNumber();
        }
        Integer ecbuluId = 0;//单位长度
        Integer ecbuluId1 = bo.getEcbuluId();
        if (ObjUtil.isNotNull(ecbuluId1)) {
            ecbuluId = ecbuluId1;
        }
        BigDecimal profit = new BigDecimal("0");//利润
        if (bo.getProfit() != null) {
            profit = bo.getProfit();
        }
        BigDecimal billPercent = new BigDecimal("0");//实际税点
        if (bo.getBillPercent() != null) {
            billPercent = bo.getBillPercent();
        }
        EcuqInput record = new EcuqInput();
        List<EcSilk> listSilk = ecSilkModel.getAllList(ecuId);
        //log.info("h2");

        EcuqInput object = new EcuqInput();
        if (ecuqiId == 0) {//插入
            Integer sortId = 1;
            record.setEcuqId(ecuqId);
            EcuqInput ecuqInput = ecuqInputService.getLatestObject(record);
            if (ecuqInput != null) {
                sortId = ecuqInput.getSortId() + 1;
            }
            record.setEcuqId(ecuqId);
            record.setEcqulId(ecqulId);
            record.setStoreId(storeId);
            record.setStartType(true);
            record.setSortId(sortId);
            record.setSilkName(silkName);
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
            boolean silkNameIsExists = false;
            for (EcSilk ecSilk : listSilk) {
                if (ecSilk.getAbbreviation().equals(silkName)) {
                    silkNameIsExists = true;
                    break;
                }
            }
            log.info("silkNameIsExists + " + silkNameIsExists);
            if (!silkNameIsExists) {
                throw new RuntimeException("型号错误");
            } else {
                ecuqInputService.insert(record);
            }
            //新增时返回最后一个input
            EcuqInput recordEcuqInput = new EcuqInput();
            recordEcuqInput.setEcuqId(ecuqId);
            object = ecuqInputService.getLatestObject(record);
            if (object != null && object.getStoreId() != 0
                    && object.getEcqulId() != 0
                    && !"".equals(object.getSilkName())
                    && !"".equals(object.getAreaStr())) {
                //log.info("详情修改");
                ecuqDescModel.deal(object, ecUser.getEcCompanyId(), ecuId);
            }
        } else {//修改
            log.info("update");
            record.setEcuqiId(ecuqiId);
            if (ecqulId != 0) {//质量等级ID
                record.setEcqulId(ecqulId);
                ecuqDescModel.cleanUnitPriceInput(ecuqiId, false);
            }
            if (storeId != 0) {//仓库ID
                record.setStoreId(storeId);
                ecuqDescModel.cleanUnitPriceInput(ecuqiId, false);
            }
            if (!silkName.isEmpty()) {//丝名称
                record.setSilkName(silkName);
                ecuqDescModel.cleanUnitPriceInput(ecuqiId, false);
            }
            if (!areaStr.isEmpty()) {//截面
                record.setAreaStr(areaStr);
            }
            if (bo.getSaleNumber() != null) {//销售数量
                record.setSaleNumber(saleNumber);
            }
            if (bo.getEcbuluId() != null) {//单位长度
                record.setEcbuluId(ecbuluId);
            }
            if (bo.getProfit() != null) {//利润
                EcuqInput ecuqInput = getObjectPassEcuqiId(ecuqiId);
                profit = ecProfitModel.dealProfitAuto(ecuqInput);
                if (profit.compareTo(new BigDecimal("0")) == 0 && ecuqInput.getProfitInput()) {
                    profit = bo.getProfit();
                }
                record.setProfit(profit);
            }
            if (billPercent.compareTo(new BigDecimal("0")) > 0) {//实际税点
                record.setBillPercent(billPercent);
            }
            log.info("record + " + CommonFunction.getGson().toJson(record));
            boolean silkNameIsExists = false;
            for (EcSilk ecSilk : listSilk) {
                if (ecSilk.getAbbreviation().equals(silkName)) {
                    silkNameIsExists = true;
                    break;
                }
            }
            log.info("silkNameIsExists + " + silkNameIsExists);
            if (!silkNameIsExists) {
                ecuqInputService.delete(record);
                ecuqDescModel.delete(ecuqiId);
                throw new RuntimeException("型号错误");
            } else {
                ecuqInputService.update(record);
            }
            //新增时返回最后一个input
            EcuqInput recordEcuqInput = new EcuqInput();
            recordEcuqInput.setEcuqiId(ecuqiId);
            object = ecuqInputService.getObject(record);
        }
        if (ecuqiId != 0) {
            EcuqInput recordEcuqInput = new EcuqInput();
            recordEcuqInput.setEcuqiId(ecuqiId);
            //log.info(CommonFunction.getGson().toJson(recordEcuqInput));
            EcuqInput o = ecuqInputService.getObject(recordEcuqInput);
            //log.info(CommonFunction.getGson().toJson(o));
            if (o != null && o.getStoreId() != 0
                    && o.getEcqulId() != 0
                    && !"".equals(o.getSilkName())
                    && !"".equals(o.getAreaStr())) {
                //log.info("详情修改");
                ecuqDescModel.deal(o, ecUser.getEcCompanyId(), ecuId);
            }
        }
        return object;
    }


    //getObject 通过EcuqInput获取EcuqInput
    public EcuqInput getObject(InputGetBo bo) {

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
        if (object.getStoreId() != 0) {
            EcbuStore recordEcbuStore = new EcbuStore();
            recordEcbuStore.setEcbusId(object.getStoreId());
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

    //delete
    public void delete(InputGetBo bo) {

        Integer ecuqi_id;
        Integer ecuqiId = bo.getEcuqiId();
        EcuqInput record = new EcuqInput();
        record.setEcuqiId(ecuqiId);
        EcuqInput object = ecuqInputService.getObject(record);
        Integer sortId = object.getSortId();
        record.setSortId(sortId);
        record.setEcuqId(object.getEcuqId());
        List<EcuqInput> list = ecuqInputService.getListGreaterThanSortId(record);
        for (EcuqInput ecuqInput : list) {
            ecuqi_id = ecuqInput.getEcuqiId();
            sortId = ecuqInput.getSortId() - 1;
            record.setEcuqiId(ecuqi_id);
            record.setSortId(sortId);
            ecuqInputService.update(record);
        }
        record = new EcuqInput();
        record.setEcuqiId(ecuqiId);
        ecuqInputService.delete(record);
        ecuqDescService.deletePassEcuqiId(ecuqiId);//删除对应的Ecq_desc 报价详情
    }

    //getListQuoted
    public InputListVo getListQuoted(InputListBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        Integer ecuId = ecUser.getEcuId();


        Integer ecuqId = bo.getEcuqId();
        Integer ecbudId = bo.getEcbudId();
        List<EcuqInput> listInput;
        listInput = getList(ecuqId);
        EcuQuoted recordEcuQuoted = new EcuQuoted();
        recordEcuQuoted.setEcuqId(ecuqId);
        EcuQuoted ecuQuoted = ecuQuotedService.getObject(recordEcuQuoted);
        BigDecimal billTotalMoney = new BigDecimal("0");//开票总计
        BigDecimal noBillTotalMoney = new BigDecimal("0");//不开票总计
        BigDecimal allWeight = new BigDecimal("0");//总重量
        BigDecimal deliverySinglePercentMoney;//加点运费
        BigDecimal billSingleMoney;//开票单价
        BigDecimal noBillSingleMoney;//不开票单价
        BigDecimal billComputeMoney;//开票小计
        BigDecimal noBillComputeMoney;//不开票小计
        for (EcuqInput ecuqInput : listInput) {
            if (ecuqInput.getStoreId() == 0 ||
                    ecuqInput.getEcqulId() == 0 ||
                    "".equals(ecuqInput.getSilkName()) ||
                    "".equals(ecuqInput.getAreaStr()) ||
                    ecuqInput.getSaleNumber() == 0) {
                continue;
            }
            //log.info("h1");
            //单位
            EcbulUnit ecbulUnit = null;
            Integer meterNumber = ecuqInput.getSaleNumber();
            if (ecuqInput.getEcbuluId() != 0) {
                EcbulUnit recordEcbulUnit = new EcbulUnit();
                recordEcbulUnit.setEcbuluId(ecuqInput.getEcbuluId());
                ecbulUnit = ecbulUnitService.getObject(recordEcbulUnit);
                ecuqInput.setEcbulUnit(ecbulUnit);
                meterNumber = ecbulUnit.getMeterNumber() * ecuqInput.getSaleNumber();
            }
            ecuqInput.setMeterNumber(meterNumber);
            //log.info("h12");
            Integer ecuqiId = ecuqInput.getEcuqiId();
            EcuqDesc recordEcuqDesc = new EcuqDesc();
            recordEcuqDesc.setEcuqiId(ecuqiId);
            EcuqDesc ecuqDesc = ecuqDescService.getObject(recordEcuqDesc);
            if (ecuqDesc == null) {
                continue;
            }
            recordEcuQuoted = new EcuQuoted();
            recordEcuQuoted.setEcuqId(ecuqId);
            ecuQuoted = ecuQuotedService.getObject(recordEcuQuoted);
            ecuqInput.setEcuqDesc(ecuqDesc);
            Integer ecbucId = ecuqDesc.getEcbucId();
            EcbuConductor recordEcbuConductor = new EcbuConductor();
            recordEcbuConductor.setEcbucId(ecbucId);
            EcbuConductor ecbuConductor = ecbuConductorService.getObject(recordEcbuConductor);//导体
            ecuqInput.setEcbuConductor(ecbuConductor);
            EcquParameter recordEcquParameter = new EcquParameter();
            recordEcquParameter.setEcbusId(ecuqInput.getStoreId());
            recordEcquParameter.setEcqulId(ecuqInput.getEcqulId());
            //log.info("recordEcuqParameter + " + CommonFunction.getGson().toJson(recordEcquParameter));
            EcquParameter ecquParameter = ecquParameterService.getObject(recordEcquParameter);//参数
            if (ecquParameter == null) {
                ecquParameter = new EcquParameter();
                ecquParameter.setLength(new BigDecimal("1"));
                ecquParameter.setCost(new BigDecimal("1"));
            }
            //~~~~~~计算各种金额
            Map<String, Object> mapConductor = EcableFunction.getConductorData(ecuqInput, ecuqDesc, ecquParameter, ecbuConductor);
            BigDecimal unitMoney;//单位金额 1米长度的金额 需要配合仓库参数
            BigDecimal fireDiameter = new BigDecimal(mapConductor.get("fireDiameter").toString());//粗芯外径
            BigDecimal zeroDiameter = new BigDecimal(mapConductor.get("zeroDiameter").toString());//细芯外径
            BigDecimal externalDiameter;//导体外径
            BigDecimal conductorWeight = new BigDecimal(mapConductor
                    .get("conductorWeight").toString());//导体重量
            BigDecimal conductorMoney = new BigDecimal(mapConductor
                    .get("conductorMoney").toString());//导体金额
            ecuqDescModel.updateCweight(ecuqInput, conductorWeight);//更新导体重量
            //log.info("h3");
            //计算云母带数据
            EcbuMicatape ecbuMicatape = null;
            if (ecuqDesc.getEcbumId() != 0) {
                EcbuMicatape recordEcbuMicatape = new EcbuMicatape();
                recordEcbuMicatape.setEcbumId(ecuqDesc.getEcbumId());
                ecbuMicatape = ecbuMicatapeService.getObject(recordEcbuMicatape);
            }
            Map<String, Object> mapMicatape = EcableFunction
                    .getMicatapeData(ecuqInput, ecuqDesc, ecbuMicatape, fireDiameter, zeroDiameter, ecquParameter);
            BigDecimal fireMicatapeRadius = new BigDecimal(mapMicatape
                    .get("fireMicatapeRadius").toString());//粗芯云母带半径
            BigDecimal zeroMicatapeRadius = new BigDecimal(mapMicatape
                    .get("zeroMicatapeRadius").toString());//细芯云母带半径
            BigDecimal micatapeThickness = ecuqDesc.getMicatapeThickness();//云母带厚度
            BigDecimal micatapeWeight = new BigDecimal(mapMicatape
                    .get("micatapeWeight").toString());//云母带重量
            BigDecimal micatapeMoney = new BigDecimal(mapMicatape
                    .get("micatapeMoney").toString());//云母带金额
            //计算绝缘数据
            EcbuInsulation ecbuInsulation = null;
            if (ecuqDesc.getEcbuiId() != 0) {
                EcbuInsulation recordEcbuInsulation = new EcbuInsulation();
                recordEcbuInsulation.setEcbuiId(ecuqDesc.getEcbuiId());
                ecbuInsulation = ecbuInsulationService.getObject(recordEcbuInsulation);
            }
            Map<String, Object> mapInsulation = EcableFunction.getInsulationData(ecuqInput,
                    ecuqDesc, ecbuInsulation, fireDiameter, zeroDiameter, fireMicatapeRadius,
                    zeroMicatapeRadius, ecquParameter);
            BigDecimal insulationFireThickness = ecuqDesc.getInsulationFireThickness();//粗芯绝缘厚度
            BigDecimal insulationZeroThickness = ecuqDesc.getInsulationZeroThickness();//细芯绝缘厚度
            BigDecimal insulationWeight = new BigDecimal(mapInsulation.get("insulationWeight").toString());//绝缘重量
            BigDecimal insulationMoney = new BigDecimal(mapInsulation.get("insulationMoney").toString());//绝缘金额
            //计算填充物数据
            EcbuInfilling ecbuInfilling = null;
            if (ecuqDesc.getEcbuinId() != 0) {
                EcbuInfilling recordEcbuInfilling = new EcbuInfilling();
                recordEcbuInfilling.setEcbuiId(ecuqDesc.getEcbuinId());
                System.out.println(CommonFunction.getGson().toJson(recordEcbuInfilling));
                ecbuInfilling = ecbuInfillingService.getObject(recordEcbuInfilling);
            }
            Map<String, Object> mapInfilling = EcableFunction.getInfillingData(ecuqInput,
                    ecuqDesc, ecquParameter, ecbuInfilling, fireDiameter, zeroDiameter,
                    micatapeThickness, insulationFireThickness, insulationZeroThickness);
            externalDiameter = new BigDecimal(mapInfilling.get("externalDiameter").toString());//总外径
            BigDecimal infillingWeight = new BigDecimal(mapInfilling.get("infillingWeight").toString());//填充物重量
            BigDecimal infillingMoney = new BigDecimal(mapInfilling.get("infillingMoney").toString());//填充物金额
            //计算包带数据
            EcbuBag ecbuBag = null;
            if (ecuqInput.getSilkName().contains("22")
                    || ecuqInput.getSilkName().contains("23")) {//凯装
                if (ecuqDesc.getEcbub22Id() != 0) {
                    EcbuBag recordEcbuBag = new EcbuBag();
                    recordEcbuBag.setEcbubId(ecuqDesc.getEcbub22Id());
                    ecbuBag = ecbuBagService.getObject(recordEcbuBag);
                }
            } else {
                if (ecuqDesc.getEcbubId() != 0) {
                    EcbuBag recordEcbuBag = new EcbuBag();
                    recordEcbuBag.setEcbubId(ecuqDesc.getEcbubId());
                    ecbuBag = ecbuBagService.getObject(recordEcbuBag);
                }
            }
            Map<String, Object> mapBag = EcableFunction.getBagData(ecuqInput, ecuqDesc,
                    ecquParameter, ecbuBag, externalDiameter);
            BigDecimal bagWeight = new BigDecimal(mapBag.get("bagWeight").toString());//包带重量
            BigDecimal bagMoney = new BigDecimal(mapBag.get("bagMoney").toString());//包带金额
            //计算屏蔽数据
            BigDecimal shieldWeight = new BigDecimal("0");
            BigDecimal shieldMoney = new BigDecimal("0");
            if (ecuqDesc.getEcbusId() != 0 && ecuqDesc.getShieldThickness()
                    .compareTo(new BigDecimal("0")) != 0) {
                EcbuShield recordEcbuShield = new EcbuShield();
                recordEcbuShield.setEcbusId(ecuqDesc.getEcbusId());
                EcbuShield ecbuShield = ecbuShieldService.getObject(recordEcbuShield);
                if (ecbuShield != null) {
                    BigDecimal totalShieldDiameter = externalDiameter
                            .add(
                                    ecuqDesc.getBagThickness()
                                            .divide(new BigDecimal("100"), 20, RoundingMode.HALF_UP)
                                            .multiply(new BigDecimal("2"))
                            )
                            .add(
                                    ecuqDesc.getShieldThickness()
                                            .divide(new BigDecimal("100"), 20, RoundingMode.HALF_UP)
                                            .multiply(new BigDecimal("2"))
                            );
                    BigDecimal totalShieldVolume = totalShieldDiameter
                            .divide(new BigDecimal("2"), 20, RoundingMode.HALF_UP)
                            .multiply(
                                    totalShieldDiameter
                                            .divide(new BigDecimal("2"), 20, RoundingMode.HALF_UP)
                            );
                    BigDecimal innerShieldDiameter = externalDiameter
                            .add(
                                    ecuqDesc.getBagThickness()
                                            .divide(new BigDecimal("100"), 20, RoundingMode.HALF_UP)
                                            .multiply(new BigDecimal("2"))
                            );
                    BigDecimal innerShieldVolume = innerShieldDiameter
                            .divide(new BigDecimal("2"), 20, RoundingMode.HALF_UP)
                            .multiply(
                                    innerShieldDiameter.divide(new BigDecimal("2"),
                                            20, RoundingMode.HALF_UP)
                            );
                    BigDecimal remainShieldVolume = (totalShieldVolume
                            .subtract(innerShieldVolume))
                            .multiply(BigDecimal.valueOf(Math.PI));
                    shieldMoney = remainShieldVolume
                            .multiply(new BigDecimal("1").add(ecuqDesc.getShieldPercent()))
                            .multiply(ecquParameter.getLength());
                }
            }
            //计算钢带数据
            EcbuSteelband ecbuSteelband = null;
            if (ecuqDesc.getEcbusbId() != 0 && ecuqDesc.getSteelbandThickness()
                    .compareTo(new BigDecimal("0")) != 0) {
                EcbuSteelband recordEcbuSteelband = new EcbuSteelband();
                recordEcbuSteelband.setEcbusId(ecuqDesc.getEcbusbId());
                ecbuSteelband = ecbuSteelbandService.getObject(recordEcbuSteelband);
            }
            Map<String, Object> mapSteelband = EcableFunction
                    .getSteelbandData(ecuqDesc, ecquParameter, ecbuSteelband, externalDiameter);
            BigDecimal steelbandWeight = new BigDecimal(mapSteelband.get("steelbandWeight").toString());//钢带重量
            BigDecimal steelbandMoney = new BigDecimal(mapSteelband.get("steelbandMoney").toString());//钢带金额
            //计算护套数据
            EcbuSheath ecbuSheath = null;
            if (ecuqDesc.getEcbusid() != 0 && ecuqDesc.getSheathThickness()
                    .compareTo(new BigDecimal("0")) != 0) {
                EcbuSheath recordEcbuSheath = new EcbuSheath();
                recordEcbuSheath.setEcbusId(ecuqDesc.getEcbusid());
                ecbuSheath = ecbuSheathService.getObject(recordEcbuSheath);
            }
            Map<String, Object> mapSheath = EcableFunction
                    .getSheathData(ecuqInput, ecuqDesc, ecquParameter, ecbuSheath, externalDiameter);
            BigDecimal sheathWeight = new BigDecimal(mapSheath.get("sheathWeight").toString());//护套重量
            BigDecimal sheathMoney = new BigDecimal(mapSheath.get("sheathMoney").toString());//护套金额
            unitMoney = conductorMoney//导体金额
                    .add(micatapeMoney)//云母带金额
                    .add(insulationMoney)//绝缘金额
                    .add(infillingMoney)//填充物金额
                    .add(bagMoney)//包带金额
                    .add(shieldMoney)//屏蔽数据
                    .add(steelbandMoney)//钢带数据
                    .add(sheathMoney)//护套数据
            ;//总额
            unitMoney = unitMoney.multiply(new BigDecimal("1").add(ecuqDesc.getStorePercent()));//仓库对应导体加点
            unitMoney = unitMoney.multiply(new BigDecimal("1").add(ecuqInput.getProfit()));//利润
            //更新税前单价 当税前价格变为手输以后不再更新价格
            if (ecuqDesc.getUnitPriceInput()) {
                unitMoney = ecuqDesc.getUnitPrice();
            } else {
                ecuqDescModel.dealUnitPrice(ecuqiId, ecuqDesc.getUnitPriceInput(), unitMoney);//税前单价提交
                ecuqDesc.setUnitPrice(unitMoney);
                ecuqInput.setEcuqDesc(ecuqDesc);
            }
            BigDecimal singleWeight = conductorWeight
                    .add(micatapeWeight)
                    .add(insulationWeight)
                    .add(infillingWeight)
                    .add(bagWeight)
                    .add(shieldWeight)
                    .add(steelbandWeight)
                    .add(sheathWeight);
            BigDecimal totalWeight = singleWeight.multiply(new BigDecimal(ecuqInput.getSaleNumber()));
            //log.info("conductorWeight + " + conductorWeight);
            //log.info("micatapeWeight + " + micatapeWeight);
            //log.info("totalWeight + " + totalWeight);
            if (ecbulUnit != null) {
                totalWeight = singleWeight.multiply(new BigDecimal(ecuqInput.getSaleNumber())
                        .multiply(new BigDecimal(ecbulUnit.getMeterNumber())));
            }
            ecuqInput.setTotalWeight(totalWeight);
            allWeight = allWeight.add(totalWeight);
            deliverySinglePercentMoney = singleWeight
                    .multiply(ecuqDesc.getSdunitMoney());
            unitMoney = unitMoney.add(deliverySinglePercentMoney);
            //最终价格
            EcduCompany recordEcduCompany = new EcduCompany();
            recordEcduCompany.setEcCompanyId(ecUser.getEcCompanyId());
            recordEcduCompany.setDefaultType(true);
            EcduCompany ecduCompany = ecduCompanyService.getObject(recordEcduCompany);
            //==获取发票数据
            Map<String, Object> mapBillPercent = EcableFunction
                    .getBillPercentData(ecuqInput, ecduCompany, unitMoney, ecbulUnit);
            billSingleMoney = new BigDecimal(mapBillPercent.get("billSingleMoney").toString());
            billComputeMoney = new BigDecimal(mapBillPercent.get("billComputeMoney").toString());
            noBillSingleMoney = unitMoney;//不开票单价
            noBillComputeMoney = unitMoney.multiply(new BigDecimal(ecuqInput.getSaleNumber()));//不开票小计
            if (ecbulUnit != null) {
                noBillComputeMoney = unitMoney
                        .multiply(new BigDecimal(ecuqInput.getSaleNumber()))
                        .multiply(new BigDecimal(ecbulUnit.getMeterNumber()));//不开票小计
            }
            //平台加点
            EcbuPcompany ecbuPcompany = ecbuPcompanyModel.getObjectPassEcbupId(ecuQuoted.getEcbupId());
            BigDecimal pcPercent = ecbuPcompany.getPcPercent();
            billSingleMoney = billSingleMoney.multiply(new BigDecimal("1").add(pcPercent));
            noBillSingleMoney = noBillSingleMoney.multiply(new BigDecimal("1").add(pcPercent));
            billComputeMoney = billComputeMoney.multiply(new BigDecimal("1").add(pcPercent));
            noBillComputeMoney = noBillComputeMoney.multiply(new BigDecimal("1").add(pcPercent));
            //是否启用手输价格
            if (ecuqDesc.getInputStart()) {
                billSingleMoney = ecuqDesc.getBupsMoney();
                billComputeMoney = ecuqDesc.getBupcMoney();
                if (ecbulUnit == null) {
                    //log.info("ecuqiId + " + ecuqInput.getEcuqiId());
                    //log.info("billSingleMoney + " + billSingleMoney);
                    //log.info("saleNumber + " + ecuqInput.getSaleNumber());
                    if (billSingleMoney.compareTo(new BigDecimal("0")) > 0) {
                        if (billComputeMoney.divide(billSingleMoney, 0, RoundingMode.HALF_UP)
                                .compareTo(new BigDecimal(ecuqInput.getSaleNumber())) != 0) {
                            billComputeMoney = ecuqDesc.getBupsMoney()
                                    .multiply(new BigDecimal(ecuqInput.getSaleNumber()));
                        }
                    }
                } else {
                    if (noBillComputeMoney.compareTo(new BigDecimal("0")) > 0) {
                        if (billComputeMoney.divide(billSingleMoney, 0, RoundingMode.HALF_UP)
                                .compareTo(new BigDecimal(ecuqInput.getSaleNumber())
                                        .multiply(new BigDecimal(ecbulUnit.getMeterNumber()))) != 0) {
                            billComputeMoney = ecuqDesc.getBupsMoney()
                                    .multiply(new BigDecimal(ecuqInput.getSaleNumber()))
                                    .multiply(new BigDecimal(ecbulUnit.getMeterNumber()));
                        }
                    }
                }
                noBillSingleMoney = ecuqDesc.getNbupsMoney();
                noBillComputeMoney = ecuqDesc.getNbupcMoney();
                if (ecbulUnit == null) {
                    if (billSingleMoney.compareTo(new BigDecimal("0")) > 0) {
                        if (noBillComputeMoney.divide(noBillSingleMoney, 0, RoundingMode.HALF_UP)
                                .compareTo(new BigDecimal(ecuqInput.getSaleNumber())) != 0) {
                            noBillComputeMoney = ecuqDesc.getNbupsMoney()
                                    .multiply(new BigDecimal(ecuqInput.getSaleNumber()));
                        }
                    }
                } else {
                    if (noBillComputeMoney.compareTo(new BigDecimal("0")) > 0) {
                        if (noBillComputeMoney.divide(noBillSingleMoney, 0,
                                RoundingMode.HALF_UP).compareTo(new BigDecimal(ecuqInput.getSaleNumber())
                                .multiply(new BigDecimal(ecbulUnit.getMeterNumber()))) != 0) {
                            noBillComputeMoney = ecuqDesc.getNbupsMoney()
                                    .multiply(new BigDecimal(ecuqInput.getSaleNumber()))
                                    .multiply(new BigDecimal(ecbulUnit.getMeterNumber()));
                        }
                    }
                }
            }
            //加上木轴价格
            if (ecuqDesc.getEcbuaId() != 0) {
                EcbuAxle recordEcbcAxle = new EcbuAxle();
                recordEcbcAxle.setEcbuaId(ecuqDesc.getEcbuaId());
                EcbuAxle ecbuAxle = ecbuAxleService.getObject(recordEcbcAxle);
                BigDecimal axlePrice = ecbuAxle.getAxlePrice()
                        .multiply(new BigDecimal(ecuqDesc.getAxleNumber()));
                if (ecbulUnit == null) {
                    billSingleMoney = billSingleMoney
                            .add(axlePrice.divide(new BigDecimal(ecuqInput.getSaleNumber()),
                                    6, RoundingMode.HALF_UP));
                    billComputeMoney = billComputeMoney.add(axlePrice);
                    noBillSingleMoney = noBillSingleMoney
                            .add(axlePrice
                                    .divide(new BigDecimal(ecuqInput.getSaleNumber()),
                                            6, RoundingMode.HALF_UP));
                } else {
                    billSingleMoney = billSingleMoney
                            .add(
                                    axlePrice
                                            .divide(
                                                    new BigDecimal(ecuqInput.getSaleNumber())
                                                            .multiply(new BigDecimal(ecbulUnit.getMeterNumber())),
                                                    6,
                                                    RoundingMode.HALF_UP));
                    billComputeMoney = billComputeMoney.add(axlePrice);
                    noBillSingleMoney = noBillSingleMoney
                            .add(
                                    axlePrice
                                            .divide(
                                                    new BigDecimal(ecuqInput.getSaleNumber())
                                                            .multiply(new BigDecimal(ecbulUnit.getMeterNumber())),
                                                    6,
                                                    RoundingMode.HALF_UP));
                }
                noBillComputeMoney = noBillComputeMoney.add(axlePrice);
                allWeight = allWeight.add(ecbuAxle.getAxleWeight()
                        .multiply(new BigDecimal(ecuqDesc.getAxleNumber())));//总重加上木轴的重量
            }
            ecuqInput.setBillSingleMoney(billSingleMoney);//有票单价
            ecuqInput.setNoBillSingleMoney(noBillSingleMoney);//无票单价
            ecuqInput.setBillComputeMoney(billComputeMoney);//有票小计
            ecuqInput.setNoBillComputeMoney(noBillComputeMoney);//无票小计
            //log.info("billSingMoney + " + billSingleMoney);
            //log.info("billComputeMoney + " + billComputeMoney);
            //提交详情金额
            ecuqDescModel.dealMoney(ecuqInput.getEcuqiId(), noBillSingleMoney,
                    billSingleMoney, noBillComputeMoney, billComputeMoney);
            ecuqDescModel.dealWeight(ecuqDesc.getEcuqdId(), totalWeight);
            billTotalMoney = billTotalMoney.add(billComputeMoney);//开票总额
            noBillTotalMoney = noBillTotalMoney.add(noBillComputeMoney);
        }
        List<DeliveryObj> listDeliveryPrice;
        ecuQuotedModel.dealTotalWeight(ecuqId, allWeight);
        //log.info("allWeight + " + allWeight);
        //if (ecuQuoted.getEcpId() != 0 || !ecuQuoted.getProvinceName().isEmpty()) {
        //log.info("allWeight + " + allWeight);
        //以下是快递数据
        BigDecimal price;
        listDeliveryPrice = ecbuDeliveryModel.getDeliveryPriceList(ecuId,
                ecuQuoted.getDeliveryStoreId(), ecuQuoted, allWeight);
        log.info("listDeliveryPrice + " + CommonFunction.getGson().toJson(listDeliveryPrice));
        EcbudDelivery recordEcbudDelivery = new EcbudDelivery();
        recordEcbudDelivery.setEcCompanyId(ecUser.getEcCompanyId());
        recordEcbudDelivery.setEcuId(ecuId);//用户
        EcbudDelivery dDelivery = ecbudDeliveryService.getObject(recordEcbudDelivery);
        if (dDelivery == null) {
            recordEcbudDelivery.setSortId(1);
            ecbudDeliveryService.insert(recordEcbudDelivery);
            recordEcbudDelivery = new EcbudDelivery();
            recordEcbudDelivery.setEcCompanyId(ecUser.getEcCompanyId());
            recordEcbudDelivery.setEcuId(ecuId);//用户
            dDelivery = ecbudDeliveryService.getObject(recordEcbudDelivery);
        }
        //log.info(CommonFunction.getGson().toJson(dDelivery));
        Map<String, Object> mapDelivery = EcableFunction
                .getDeliveryData(ecuQuoted, listDeliveryPrice, dDelivery, ecbudId);
        DeliveryObj objectDelivery = CommonFunction.getGson()
                .fromJson(mapDelivery.get("objectDelivery").toString(), DeliveryObj.class);
        //log.info("objectDelivery + " + CommonFunction.getGson().toJson(objectDelivery));
        //以上是快递数据
        //log.info("ecbudId + " + ecbudId);
        if (ecbudId != -1 && ecuQuoted.getEcbudId() != -1) {
            price = objectDelivery.getPrice();
            //log.info("price + " + price);
            if (ecuQuoted.getDeliveryDivide().compareTo(new BigDecimal("0")) != 0) {
                price = price.divide(ecuQuoted.getDeliveryDivide(), 6, RoundingMode.HALF_UP);
            }
            if (ecuQuoted.getDeliveryAdd().compareTo(new BigDecimal("0")) != 0) {
                price = price.add(ecuQuoted.getDeliveryAdd());
            }
            //log.info("price + " + price);
            ecuQuotedModel.dealDeliveryMoney(ecuqId, price);
            //修改运费
            recordEcuQuoted = new EcuQuoted();
            recordEcuQuoted.setEcuqId(ecuqId);
            recordEcuQuoted.setDeliveryMoney(price);
            ecuQuotedService.update(recordEcuQuoted);
            billTotalMoney = new BigDecimal("0");
            noBillTotalMoney = new BigDecimal("0");
            for (EcuqInput ecuqInput : listInput) {
                if (ecuqInput.getStoreId() == 0
                        || ecuqInput.getEcqulId() == 0
                        || "".equals(ecuqInput.getSilkName())
                        || "".equals(ecuqInput.getAreaStr())
                        || ecuqInput.getSaleNumber() == 0) {
                    continue;
                }
                EcuqDesc recordEcuqDesc = new EcuqDesc();
                recordEcuqDesc.setEcuqiId(ecuqInput.getEcuqiId());
                EcuqDesc ecuqDesc = ecuqDescService.getObject(recordEcuqDesc);
                if (ecuqDesc == null) {
                    continue;
                }
                if (!ecuqDesc.getInputStart()) {
                    //单位
                    EcbulUnit ecbulUnit = null;
                    if (ecuqInput.getEcbuluId() != 0) {
                        EcbulUnit recordEcbulUnit = new EcbulUnit();
                        recordEcbulUnit.setEcbuluId(ecuqInput.getEcbuluId());
                        ecbulUnit = ecbulUnitService.getObject(recordEcbulUnit);
                    }
                    billSingleMoney = ecuqInput.getBillSingleMoney();//开票单价
                    noBillSingleMoney = ecuqInput.getNoBillSingleMoney();//不开票单价
                    billComputeMoney = ecuqInput.getBillComputeMoney();//开票小计
                    noBillComputeMoney = ecuqInput.getNoBillComputeMoney();//不开票小计
                    BigDecimal singleWeight = ecuqDesc.getWeight();
                    //如果有木轴所分得的单价
                    if (ecuqDesc.getEcbuaId() != 0) {
                        EcbuAxle recordEcbuAxle = new EcbuAxle();
                        recordEcbuAxle.setEcbuaId(ecuqDesc.getEcbuaId());
                        EcbuAxle ecbuAxle = ecbuAxleService.getObject(recordEcbuAxle);
                        singleWeight = ecuqDesc.getWeight()
                                .add(ecbuAxle.getAxleWeight()
                                        .multiply(new BigDecimal(ecuqDesc.getAxleNumber())));
                    }
                    BigDecimal percent = singleWeight.divide(allWeight, 6, RoundingMode.HALF_UP);
                    BigDecimal singleMoney = price.multiply(percent)
                            .divide(new BigDecimal(ecuqInput.getSaleNumber()), 6, RoundingMode.HALF_UP);
                    if (ecbulUnit != null) {
                        singleMoney = price.multiply(percent)
                                .divide(
                                        (new BigDecimal(ecuqInput.getSaleNumber())
                                                .multiply(new BigDecimal(
                                                        ecbulUnit.getMeterNumber())))
                                        , 6, RoundingMode.HALF_UP);
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
                    //log.info("billSingMoney + " + billSingleMoney);
                    //log.info("billComputeMoney + " + billComputeMoney);
                    //提交详情金额
                    ecuqInput.setBillSingleMoney(billSingleMoney);//有票单价
                    ecuqInput.setNoBillSingleMoney(noBillSingleMoney);//无票单价
                    ecuqInput.setBillComputeMoney(billComputeMoney);//有票小计
                    ecuqInput.setNoBillComputeMoney(noBillComputeMoney);//无票小计
                    ecuqDescModel.dealMoney(ecuqInput.getEcuqiId(),
                            noBillSingleMoney, billSingleMoney,
                            noBillComputeMoney, billComputeMoney);
                }
            }
        }
        //单位加价金额 加价百分比
        billTotalMoney = new BigDecimal("0");
        noBillTotalMoney = new BigDecimal("0");
        for (EcuqInput ecuqInput : listInput) {
            if (ecuqInput.getStoreId() == 0
                    || ecuqInput.getEcqulId() == 0
                    || "".equals(ecuqInput.getSilkName())
                    || "".equals(ecuqInput.getAreaStr())
                    || ecuqInput.getSaleNumber() == 0) {
                continue;
            }
            EcuqDesc recordEcuqDesc = new EcuqDesc();
            recordEcuqDesc.setEcuqiId(ecuqInput.getEcuqiId());
            EcuqDesc ecuqDesc = ecuqDescService.getObject(recordEcuqDesc);
            if (ecuqDesc == null) {
                continue;
            }
            if (!ecuqDesc.getInputStart()) {
                //单位
                EcbulUnit ecbulUnit = null;
                if (ecuqInput.getEcbuluId() != 0) {
                    EcbulUnit recordEcbulUnit = new EcbulUnit();
                    recordEcbulUnit.setEcbuluId(ecuqInput.getEcbuluId());
                    ecbulUnit = ecbulUnitService.getObject(recordEcbulUnit);
                    ecuqInput.setEcbulUnit(ecbulUnit);
                }
                billSingleMoney = ecuqInput.getBillSingleMoney();//开票单价
                noBillSingleMoney = ecuqInput.getNoBillSingleMoney();//不开票单价
                //billComputeMoney = ecuqInput.getBillComputeMoney();//开票小计
                //noBillComputeMoney = ecuqInput.getNoBillComputeMoney();//不开票小计
                //log.info("billSingMoney + " + billSingleMoney);
                //log.info("billComputeMoney + " + billComputeMoney);
                //单位加价金额
                billSingleMoney = billSingleMoney.add(ecuQuoted.getUnitPriceAdd());
                    /*billComputeMoney = billSingleMoney.multiply(new BigDecimal(ecuqInput.getSaleNumber()));
                    if (ecbulUnit != null) {
                        billComputeMoney = billSingleMoney
                                .multiply(new BigDecimal(ecuqInput.getSaleNumber()))
                                .multiply(new BigDecimal(ecbulUnit.getMeterNumber()));
                    }*/
                noBillSingleMoney = noBillSingleMoney.add(ecuQuoted.getUnitPriceAdd());
                    /*noBillComputeMoney = noBillSingleMoney.multiply(new BigDecimal(ecuqInput.getSaleNumber()));
                    if (ecbulUnit != null) {
                        noBillComputeMoney = noBillSingleMoney
                                .multiply(new BigDecimal(ecuqInput.getSaleNumber()))
                                .multiply(new BigDecimal(ecbulUnit.getMeterNumber()));
                    }*/
                //log.info("billSingMoney + " + billSingleMoney);
                //log.info("billComputeMoney + " + billComputeMoney);
                //加价百分比
                billSingleMoney = billSingleMoney.multiply(new BigDecimal("1")
                        .add(ecuQuoted.getAddPricePercent()));
                billComputeMoney = billSingleMoney
                        .multiply(new BigDecimal(ecuqInput.getSaleNumber()));
                if (ecbulUnit != null) {
                    billSingleMoney = billSingleMoney
                            .multiply(new BigDecimal(ecbulUnit.getMeterNumber()));
                    billComputeMoney = billSingleMoney
                            .multiply(new BigDecimal(ecuqInput.getSaleNumber()));

                }
                noBillSingleMoney = noBillSingleMoney
                        .multiply(new BigDecimal("1")
                                .add(ecuQuoted.getAddPricePercent()));
                noBillComputeMoney = noBillSingleMoney
                        .multiply(new BigDecimal(ecuqInput.getSaleNumber()));
                if (ecbulUnit != null) {
                    noBillSingleMoney = noBillSingleMoney.multiply(new BigDecimal(ecbulUnit.getMeterNumber()));//必须后算
                    noBillComputeMoney = noBillSingleMoney
                            .multiply(new BigDecimal(ecuqInput.getSaleNumber()));
                }
                ecuqInput.setBillSingleMoney(billSingleMoney);//有票单价
                ecuqInput.setNoBillSingleMoney(noBillSingleMoney);//无票单价
                ecuqInput.setBillComputeMoney(billComputeMoney);//有票小计
                ecuqInput.setNoBillComputeMoney(noBillComputeMoney);//无票小计
                ecuqDescModel.dealMoney(ecuqInput.getEcuqiId(),
                        noBillSingleMoney, billSingleMoney,
                        noBillComputeMoney, billComputeMoney);
                billTotalMoney = billTotalMoney.add(billComputeMoney);
                noBillTotalMoney = noBillTotalMoney.add(noBillComputeMoney);
            }
        }
        //}
        ecuQuotedModel.dealMoney(ecuqId, noBillTotalMoney, billTotalMoney);
        //添加报价单总额
        return new InputListVo(billTotalMoney, noBillTotalMoney, listInput, listDeliveryPrice);
    }

    //getStructurePassId 通过ecuqiId获取结构体
    public InputStructureVo getStructurePassId(InputGetBo bo) {

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
        recordEcquParameter.setEcbusId(ecuqInput.getStoreId());
        recordEcquParameter.setEcqulId(ecuqInput.getEcqulId());
        EcquParameter ecquParameter = ecquParameterService.getObject(recordEcquParameter);
        //计算导体
        String[] areaArr = (ecuqInput.getAreaStr()).split("\\+");
        Map<String, Object> mapConductor = EcableFunction.getConductorData(ecuqInput,
                ecuqDesc,
                ecquParameter,
                ecbuConductor);

        InputStructureVo inputStructureVo = new InputStructureVo();
        //log.info(CommonFunction.getGson().toJson(CommonFunction.getGson().toJson(mapConductor)));
        BigDecimal fireDiameter = new BigDecimal(mapConductor.get("fireDiameter").toString());//粗芯外径
        BigDecimal zeroDiameter = new BigDecimal(mapConductor.get("zeroDiameter").toString());//细芯外径
        BigDecimal externalDiameter;//导体外径
        BigDecimal conductorWeight = new BigDecimal(mapConductor.get("conductorWeight").toString());//导体重量
        BigDecimal conductorMoney = new BigDecimal(mapConductor.get("conductorMoney").toString());//导体金额
        String conductorDiameter = mapConductor.get("externalDiameter").toString();
        recordEcuqDesc = new EcuqDesc();
        recordEcuqDesc.setEcuqdId(ecuqDesc.getEcuqdId());
        recordEcuqDesc.setCweight(conductorWeight);
        ecuqDescService.update(recordEcuqDesc);//更新导体重量

        inputStructureVo.setConductorDiameter(conductorDiameter);
        inputStructureVo.setFireDiameter(fireDiameter);
        inputStructureVo.setFireDiameter(fireDiameter);
        inputStructureVo.setZeroDiameter(zeroDiameter);
        inputStructureVo.setFireWeight(mapConductor.get("fireWeight").toString());
        inputStructureVo.setFireMoney(mapConductor.get("fireMoney").toString());
        inputStructureVo.setZeroWeight(mapConductor.get("zeroWeight").toString());
        inputStructureVo.setZeroMoney(mapConductor.get("zeroMoney").toString());
        inputStructureVo.setConductorWeight(conductorWeight);
        inputStructureVo.setConductorMoney(conductorMoney);

        //计算云母带数据
        BigDecimal fireMicatapeDiameter;
        BigDecimal zeroMicatapeDiameter;
        BigDecimal micatapeMoney = new BigDecimal("0");
        BigDecimal micatapeWeight = new BigDecimal("0");
        BigDecimal micatapeThickness = ecuqDesc.getMicatapeThickness();
        BigDecimal fireMicatapeRadius = new BigDecimal("0");//粗芯云母带半径
        BigDecimal zeroMicatapeRadius = new BigDecimal("0");//细芯云母带半径
        if (ecuqDesc.getEcbumId() != 0) {
            EcbuMicatape recordEcbuMicatape = new EcbuMicatape();
            recordEcbuMicatape.setEcbumId(ecuqDesc.getEcbumId());
            EcbuMicatape ecbuMicatape = ecbuMicatapeService.getObject(recordEcbuMicatape);
            ecuqDesc.setEcbuMicatape(ecbuMicatape);
            Map<String, Object> mapMicatape = EcableFunction.getMicatapeData(ecuqInput, ecuqDesc,
                    ecbuMicatape, fireDiameter, zeroDiameter, ecquParameter);
            fireMicatapeRadius = new BigDecimal(mapMicatape.get("fireMicatapeRadius").toString());//粗芯云母带半径
            zeroMicatapeRadius = new BigDecimal(mapMicatape.get("zeroMicatapeRadius").toString());//细芯云母带半径
            micatapeThickness = ecuqDesc.getMicatapeThickness();//云母带厚度
            micatapeWeight = new BigDecimal(mapMicatape.get("micatapeWeight").toString());//云母带重量
            micatapeMoney = new BigDecimal(mapMicatape.get("micatapeMoney").toString());//云母带金额
            fireMicatapeDiameter = new BigDecimal(mapMicatape.get("fireMicatapeRadius").toString())
                    .multiply(new BigDecimal("2"));//粗芯云母带半径
            zeroMicatapeDiameter = new BigDecimal(mapMicatape.get("zeroMicatapeRadius").toString())
                    .multiply(new BigDecimal("2"));//细芯云母带半径

            inputStructureVo.setFireMicatapeDiameter(fireMicatapeDiameter);
            inputStructureVo.setZeroMicatapeDiameter(zeroMicatapeDiameter);
            inputStructureVo.setMicatapeWeight(micatapeWeight);
            inputStructureVo.setMicatapeMoney(micatapeMoney);

        }
        //计算绝缘数据
        //System.out.println("h5");
        BigDecimal insulationWeight = new BigDecimal("0");
        BigDecimal insulationMoney = new BigDecimal("0");
        BigDecimal insulationFireThickness = ecuqDesc.getInsulationFireThickness();//粗芯绝缘厚度
        BigDecimal insulationZeroThickness = ecuqDesc.getInsulationZeroThickness();//细芯绝缘厚度
        BigDecimal insulationFireDiameter;
        BigDecimal insulationZeroDiameter;
        if (ecuqDesc.getEcbuiId() != 0) {
            EcbuInsulation recordEcbuInsulation = new EcbuInsulation();
            recordEcbuInsulation.setEcbuiId(ecuqDesc.getEcbuiId());
            EcbuInsulation ecbuInsulation = ecbuInsulationService.getObject(recordEcbuInsulation);
            ecuqDesc.setEcbuInsulation(ecbuInsulation);
            Map<String, Object> mapInsulation = EcableFunction.getInsulationData(ecuqInput, ecuqDesc,
                    ecbuInsulation, fireDiameter, zeroDiameter, fireMicatapeRadius,
                    zeroMicatapeRadius, ecquParameter);
            insulationFireThickness = ecuqDesc.getInsulationFireThickness();//粗芯绝缘厚度
            insulationZeroThickness = ecuqDesc.getInsulationZeroThickness();//细芯绝缘厚度
            insulationWeight = new BigDecimal(mapInsulation.get("insulationWeight").toString());//绝缘重量
            insulationMoney = new BigDecimal(mapInsulation.get("insulationMoney").toString());//绝缘金额
            insulationFireDiameter = new BigDecimal(mapInsulation.get("fireInsulationRadius").toString())
                    .multiply(new BigDecimal("2"));
            insulationZeroDiameter = new BigDecimal(mapInsulation.get("zeroInsulationRadius").toString())
                    .multiply(new BigDecimal("2"));
//            map.put("insulationFireDiameter", insulationFireDiameter);
//            map.put("insulationZeroDiameter", insulationZeroDiameter);
//            map.put("insulationWeight", insulationWeight);
//            map.put("insulationMoney", insulationMoney);

            inputStructureVo.setInsulationFireDiameter(insulationFireDiameter);
            inputStructureVo.setInsulationZeroDiameter(insulationZeroDiameter);
            inputStructureVo.setInsulationWeight(insulationWeight);
            inputStructureVo.setInsulationMoney(insulationMoney);
        }
        //计算填充物数据
        BigDecimal wideDiameter = fireDiameter//粗芯直径
                .add(micatapeThickness.multiply(new BigDecimal("2")))
                .add(insulationFireThickness.multiply(new BigDecimal("2")));
        BigDecimal fineDiameter = zeroDiameter//细芯直径
                .add(micatapeThickness.multiply(new BigDecimal("2")))
                .add(insulationZeroThickness.multiply(new BigDecimal("2")));
        externalDiameter = EcableFunction.getExternalDiameter(areaArr, wideDiameter, fineDiameter);//外径

        inputStructureVo.setExternalDiameter(externalDiameter);


        BigDecimal infillingWeight = new BigDecimal("0");
        BigDecimal infillingMoney = new BigDecimal("0");
        if (ecuqDesc.getEcbuinId() != 0) {
            EcbuInfilling recordEcbuInfilling = new EcbuInfilling();
            recordEcbuInfilling.setEcbuiId(ecuqDesc.getEcbuinId());
            EcbuInfilling ecbuInfilling = ecbuInfillingService.getObject(recordEcbuInfilling);
            ecuqDesc.setEcbuInfilling(ecbuInfilling);
            Map<String, Object> mapInfilling = EcableFunction.getInfillingData(ecuqInput, ecuqDesc,
                    ecquParameter, ecbuInfilling, fireDiameter, zeroDiameter,
                    micatapeThickness, insulationFireThickness, insulationZeroThickness);
            infillingWeight = new BigDecimal(mapInfilling.get("infillingWeight").toString());//填充物重量
            infillingMoney = new BigDecimal(mapInfilling.get("infillingMoney").toString());//填充物金额

            inputStructureVo.setInfillingWeight(infillingWeight);
            inputStructureVo.setInsulationMoney(infillingMoney);
        }
        //计算包带数据
        BigDecimal bagWeight = new BigDecimal("0");
        BigDecimal bagMoney = new BigDecimal("0");
        BigDecimal bagDiameter;
        Map<String, Object> mapBag;
        if (ecuqInput.getSilkName().contains("22")
                || ecuqInput.getSilkName().contains("23")) {//凯装
            if (ecuqDesc.getEcbub22Id() != 0) {
                EcbuBag recordEcbuBag = new EcbuBag();
                recordEcbuBag.setEcbubId(ecuqDesc.getEcbub22Id());
                EcbuBag ecbuBag = ecbuBagService.getObject(recordEcbuBag);
                ecuqDesc.setEcbuBag(ecbuBag);
                mapBag = EcableFunction.getBagData(ecuqInput, ecuqDesc,
                        ecquParameter, ecbuBag, externalDiameter);
                bagWeight = new BigDecimal(mapBag.get("bagWeight").toString());//包带重量
                bagMoney = new BigDecimal(mapBag.get("bagMoney").toString());//包带金额
                bagDiameter = new BigDecimal(mapBag.get("bagDiameter").toString());

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
                bagWeight = new BigDecimal(mapBag.get("bagWeight").toString());//包带重量
                bagMoney = new BigDecimal(mapBag.get("bagMoney").toString());//包带金额
                bagDiameter = new BigDecimal(mapBag.get("bagDiameter").toString());

                inputStructureVo.setBagDiameter(bagDiameter);
                inputStructureVo.setBagWeight(bagWeight);
                inputStructureVo.setBagMoney(bagMoney);
            }
        }

        //计算屏蔽数据
        BigDecimal shieldWeight = new BigDecimal("0");
        BigDecimal shieldMoney = new BigDecimal("0");
        if (ecuqDesc.getEcbusId() != 0 && ecuqDesc.getShieldThickness()
                .compareTo(new BigDecimal("0")) != 0) {
            EcbuShield recordEcbuShield = new EcbuShield();
            recordEcbuShield.setEcbusId(ecuqDesc.getEcbusbId());
            EcbuShield ecbuShield = ecbuShieldService.getObject(recordEcbuShield);
            ecuqDesc.setEcbuShield(ecbuShield);
            if (ecbuShield != null) {
                BigDecimal totalShieldDiameter = externalDiameter
                        .add(
                                ecuqDesc.getBagThickness()
                                        .divide(new BigDecimal("100"), 20, RoundingMode.HALF_UP)
                                        .multiply(new BigDecimal("2"))
                        )
                        .add(
                                ecuqDesc.getShieldThickness()
                                        .divide(new BigDecimal("100"), 20, RoundingMode.HALF_UP)
                                        .multiply(new BigDecimal("2"))
                        );
                BigDecimal totalShieldVolume = totalShieldDiameter
                        .divide(new BigDecimal("2"), 20, RoundingMode.HALF_UP)
                        .multiply(
                                totalShieldDiameter.divide(new BigDecimal("2"), 20, RoundingMode.HALF_UP)
                        );
                BigDecimal innerShieldDiameter = externalDiameter
                        .add(
                                ecuqDesc.getBagThickness()
                                        .divide(new BigDecimal("100"), 20, RoundingMode.HALF_UP)
                                        .multiply(new BigDecimal("2"))
                        );
                BigDecimal innerShieldVolume = innerShieldDiameter
                        .divide(new BigDecimal("2"), 20, RoundingMode.HALF_UP)
                        .multiply(
                                innerShieldDiameter.divide(new BigDecimal("2"), 20, RoundingMode.HALF_UP)
                        );
                shieldWeight = (totalShieldVolume
                        .subtract(innerShieldVolume))
                        .multiply(ecbuShield.getDensity())
                        .multiply(new BigDecimal("1").add(ecuqDesc.getShieldPercent()))
                        .multiply(ecquParameter.getLength())
                        .multiply(BigDecimal.valueOf(Math.PI));
                shieldMoney = shieldWeight.multiply(ecbuShield.getUnitPrice());
                inputStructureVo.setShieldWeight(shieldWeight);
                inputStructureVo.setShieldMoney(shieldMoney);
            }
        }
        //计算钢带数据
        //System.out.println("h9");
        BigDecimal steelbandWeight = new BigDecimal("0");
        BigDecimal steelbandMoney = new BigDecimal("0");
        if (ecuqDesc.getEcbusbId() != 0 && ecuqDesc.getSteelbandThickness()
                .compareTo(new BigDecimal("0")) != 0) {
            EcbuSteelband recordEcbuSteelband = new EcbuSteelband();
            recordEcbuSteelband.setEcbusId(ecuqDesc.getEcbusbId());
            EcbuSteelband ecbuSteelband = ecbuSteelbandService.getObject(recordEcbuSteelband);
            ecuqDesc.setEcbuSteelband(ecbuSteelband);
            Map<String, Object> mapSteelband = EcableFunction.getSteelbandData(ecuqDesc,
                    ecquParameter, ecbuSteelband,
                    externalDiameter);

            inputStructureVo.setSteelbandDiameter(new BigDecimal(mapSteelband.get("steelbandDiameter").toString()));
            inputStructureVo.setSteelbandWeight(new BigDecimal(mapSteelband.get("steelbandWeight").toString()));
            inputStructureVo.setSteelbandMoney(new BigDecimal(mapSteelband.get("steelbandMoney").toString()));

        }
        //计算护套数据
        BigDecimal sheathWeight = new BigDecimal("0");
        BigDecimal sheathMoney = new BigDecimal("0");
        if (ecuqDesc.getEcbusid() != 0 && ecuqDesc.getSheathThickness().compareTo(new BigDecimal("0")) != 0) {
            EcbuSheath recordEcbuSheath = new EcbuSheath();
            recordEcbuSheath.setEcbusId(ecuqDesc.getEcbusid());
            EcbuSheath ecbuSheath = ecbuSheathService.getObject(recordEcbuSheath);
            ecuqDesc.setEcbuSheath(ecbuSheath);
            EcbSheath recordEcbSheath = new EcbSheath();
            recordEcbSheath.setEcbsId(ecuqDesc.getEcbusid());
            EcbSheath ecbSheath = ecbSheathService.getObject(recordEcbSheath);
            ecuqDesc.setEcbSheath(ecbSheath);
            Map<String, Object> mapSheath = EcableFunction.getSheathData(ecuqInput,
                    ecuqDesc, ecquParameter,
                    ecbuSheath, externalDiameter);
            inputStructureVo.setSheathDiameter(new BigDecimal(mapSheath.get("sheathDiameter").toString()));
            inputStructureVo.setSheathWeight(new BigDecimal(mapSheath.get("sheathWeight").toString()));
            inputStructureVo.setSheathMoney(new BigDecimal(mapSheath.get("sheathMoney").toString()));

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

    //getStructureTemporary 通过ecuqiId获取结构体
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
        recordEcquParameter.setEcbusId(ecuqInput.getStoreId());
        recordEcquParameter.setEcqulId(ecuqInput.getEcqulId());
        EcquParameter ecquParameter = ecquParameterService.getObject(recordEcquParameter);
        //计算导体
        BigDecimal fireSilkNumber = bo.getFireSilkNumber();
        BigDecimal fireStrand = bo.getFireStrand();
        BigDecimal zeroSilkNumber = bo.getZeroSilkNumber();
        BigDecimal zeroStrand = bo.getZeroStrand();

        ecuqDesc.setFireSilkNumber(fireSilkNumber);
        ecuqDesc.setFireStrand(fireStrand);
        ecuqDesc.setZeroSilkNumber(zeroSilkNumber);
        ecuqDesc.setZeroStrand(zeroStrand);
        String[] areaArr = (ecuqInput.getAreaStr()).split("\\+");
        Map<String, Object> mapConductor = EcableFunction.getConductorData(ecuqInput,
                ecuqDesc,
                ecquParameter,
                ecbuConductor);
        BigDecimal fireDiameter =bo.getFireDiameter() ;//粗芯外径
        BigDecimal zeroDiameter =bo.getZeroDiameter() ;//细芯外径
        BigDecimal externalDiameter;//导体外径
        BigDecimal conductorWeight = bo.getConductorWeight();//导体重量
        BigDecimal conductorMoney =bo.getConductorMoney();//导体金额
        recordEcuqDesc = new EcuqDesc();
        recordEcuqDesc.setEcuqdId(ecuqDesc.getEcuqdId());
        recordEcuqDesc.setCweight(conductorWeight);
        ecuqDescService.update(recordEcuqDesc);//更新导体重量
        map.put("fireDiameter", fireDiameter);
        map.put("zeroDiameter", zeroDiameter);
        map.put("fireWeight", mapConductor.get("fireWeight").toString());
        map.put("fireMoney", mapConductor.get("fireMoney").toString());
        map.put("zeroWeight", mapConductor.get("zeroWeight").toString());
        map.put("zeroMoney", mapConductor.get("zeroMoney").toString());
        map.put("conductorWeight", conductorWeight);
        map.put("conductorMoney", conductorMoney);
        //计算云母带数据
        BigDecimal micatapeMoney = new BigDecimal("0");
        BigDecimal micatapeWeight = new BigDecimal("0");
        BigDecimal micatapeThickness = ecuqDesc.getMicatapeThickness();
        BigDecimal fireMicatapeRadius = new BigDecimal("0");//粗芯云母带半径
        BigDecimal zeroMicatapeRadius = new BigDecimal("0");//细芯云母带半径
        BigDecimal fireMicatapeDiameter;//粗芯云母带直径
        BigDecimal zeroMicatapeDiameter;//细芯云母带真径
        if (ecuqDesc.getEcbumId() != 0) {
            Integer ecbumId =bo.getEcbumId();
            micatapeThickness =bo.getMicatapeThickness();
            ecuqDesc.setEcbumId(ecbumId);
            ecuqDesc.setMicatapeThickness(micatapeThickness);
            EcbuMicatape recordEcbuMicatape = new EcbuMicatape();
            recordEcbuMicatape.setEcbumId(ecuqDesc.getEcbumId());
            EcbuMicatape ecbuMicatape = ecbuMicatapeService.getObject(recordEcbuMicatape);
            ecuqDesc.setEcbuMicatape(ecbuMicatape);
            Map<String, Object> mapMicatape = EcableFunction.getMicatapeData(ecuqInput,
                    ecuqDesc, ecbuMicatape,
                    fireDiameter,
                    zeroDiameter,
                    ecquParameter);
            fireMicatapeRadius = new BigDecimal(mapMicatape.get("fireMicatapeRadius").toString());//粗芯云母带半径
            zeroMicatapeRadius = new BigDecimal(mapMicatape.get("zeroMicatapeRadius").toString());//细芯云母带半径
            fireMicatapeDiameter = new BigDecimal(mapMicatape.get("fireMicatapeRadius").toString())
                    .multiply(new BigDecimal("2"));//粗芯云母带半径
            zeroMicatapeDiameter = new BigDecimal(mapMicatape.get("zeroMicatapeRadius").toString())
                    .multiply(new BigDecimal("2"));//细芯云母带半径
            micatapeThickness = ecuqDesc.getMicatapeThickness();//云母带厚度
            micatapeWeight = new BigDecimal(mapMicatape.get("micatapeWeight").toString());//云母带重量
            micatapeMoney = new BigDecimal(mapMicatape.get("micatapeMoney").toString());//云母带金额
            map.put("fireMicatapeDiameter", fireMicatapeDiameter);
            map.put("zeroMicatapeDiameter", zeroMicatapeDiameter);
            map.put("micatapeWeight", micatapeWeight);
            map.put("micatapeMoney", micatapeMoney);
        }
        //计算绝缘数据
        //System.out.println("h5");
        BigDecimal insulationWeight = new BigDecimal("0");
        BigDecimal insulationMoney = new BigDecimal("0");
        BigDecimal insulationFireThickness = ecuqDesc.getInsulationFireThickness();//粗芯绝缘厚度
        BigDecimal insulationZeroThickness = ecuqDesc.getInsulationZeroThickness();//细芯绝缘厚度
        BigDecimal insulationFireDiameter;
        BigDecimal insulationZeroDiameter;
        if (ecuqDesc.getEcbuiId() != 0) {
            Integer ecbuiId =bo.getEcbuiId() ;
            insulationFireThickness =bo.getInsulationFireThickness();
            insulationZeroThickness =bo.getInsulationZeroThickness();
            ecuqDesc.setEcbuiId(ecbuiId);
            ecuqDesc.setInsulationFireThickness(insulationFireThickness);
            ecuqDesc.setInsulationZeroThickness(insulationZeroThickness);
            EcbuInsulation recordEcbuInsulation = new EcbuInsulation();
            recordEcbuInsulation.setEcbuiId(ecuqDesc.getEcbuiId());
            EcbuInsulation ecbuInsulation = ecbuInsulationService.getObject(recordEcbuInsulation);
            ecuqDesc.setEcbuInsulation(ecbuInsulation);
            Map<String, Object> mapInsulation = EcableFunction.getInsulationData(ecuqInput,
                    ecuqDesc, ecbuInsulation,
                    fireDiameter,
                    zeroDiameter,
                    fireMicatapeRadius,
                    zeroMicatapeRadius,
                    ecquParameter);
            insulationFireThickness = ecuqDesc.getInsulationFireThickness();//粗芯绝缘厚度
            insulationZeroThickness = ecuqDesc.getInsulationZeroThickness();//细芯绝缘厚度
            insulationWeight = new BigDecimal(mapInsulation.get("insulationWeight").toString());//绝缘重量
            insulationMoney = new BigDecimal(mapInsulation.get("insulationMoney").toString());//绝缘金额
            insulationFireDiameter = new BigDecimal(mapInsulation.get("fireInsulationRadius").toString())
                    .multiply(new BigDecimal("2"));
            insulationZeroDiameter = new BigDecimal(mapInsulation.get("zeroInsulationRadius").toString())
                    .multiply(new BigDecimal("2"));
            map.put("insulationFireDiameter", insulationFireDiameter);
            map.put("insulationZeroDiameter", insulationZeroDiameter);
            map.put("insulationWeight", insulationWeight);
            map.put("insulationMoney", insulationMoney);
        }
        //计算填充物数据
        BigDecimal wideDiameter = fireDiameter//粗芯直径
                .add(micatapeThickness.multiply(new BigDecimal("2")))
                .add(insulationFireThickness.multiply(new BigDecimal("2")));
        BigDecimal fineDiameter = zeroDiameter//细芯直径
                .add(micatapeThickness.multiply(new BigDecimal("2")))
                .add(insulationZeroThickness.multiply(new BigDecimal("2")));
        externalDiameter = EcableFunction.getExternalDiameter(areaArr, wideDiameter, fineDiameter);//外径
        map.put("externalDiameter", externalDiameter);
        BigDecimal infillingWeight = new BigDecimal("0");
        BigDecimal infillingMoney = new BigDecimal("0");
        if (ecuqDesc.getEcbuinId() != 0) {
            Integer ecbuinId =bo.getEcbuinId();
            ecuqDesc.setEcbuinId(ecbuinId);
            EcbuInfilling recordEcbuInfilling = new EcbuInfilling();
            recordEcbuInfilling.setEcbuiId(ecuqDesc.getEcbuinId());
            EcbuInfilling ecbuInfilling = ecbuInfillingService.getObject(recordEcbuInfilling);
            ecuqDesc.setEcbuInfilling(ecbuInfilling);
            Map<String, Object> mapInfilling = EcableFunction.getInfillingData(ecuqInput,
                    ecuqDesc, ecquParameter, ecbuInfilling,
                    fireDiameter, zeroDiameter, micatapeThickness,
                    insulationFireThickness,
                    insulationZeroThickness);
            infillingWeight = new BigDecimal(mapInfilling.get("infillingWeight").toString());//填充物重量
            infillingMoney = new BigDecimal(mapInfilling.get("infillingMoney").toString());//填充物金额
            map.put("infillingWeight", infillingWeight);
            map.put("infillingMoney", infillingMoney);
        }
        //计算包带数据
        BigDecimal bagWeight = new BigDecimal("0");
        BigDecimal bagMoney = new BigDecimal("0");
        BigDecimal bagDiameter;

        Map<String, Object> mapBag;
        if (ecuqInput.getSilkName().contains("22") || ecuqInput.getSilkName().contains("23")) {//凯装
            if (ecuqDesc.getEcbub22Id() != 0) {
                Integer ecbub22Id =bo.getEcbub22Id() ;
                BigDecimal bag22Thickness =bo.getBag22Thickness() ;
                ecuqDesc.setEcbub22Id(ecbub22Id);
                ecuqDesc.setBag22Thickness(bag22Thickness);
                EcbuBag recordEcbuBag = new EcbuBag();
                recordEcbuBag.setEcbubId(ecuqDesc.getEcbub22Id());
                EcbuBag ecbuBag = ecbuBagService.getObject(recordEcbuBag);
                ecuqDesc.setEcbuBag(ecbuBag);
                mapBag = EcableFunction.getBagData(ecuqInput, ecuqDesc,
                        ecquParameter, ecbuBag, externalDiameter);
                bagWeight = new BigDecimal(mapBag.get("bagWeight").toString());//包带重量
                bagMoney = new BigDecimal(mapBag.get("bagMoney").toString());//包带金额
                bagDiameter = new BigDecimal(mapBag.get("bagDiameter").toString());
                map.put("bagDiameter", bagDiameter);
                map.put("bagWeight", bagWeight);
                map.put("bagMoney", bagMoney);
            }
        } else {
            if (ecuqDesc.getEcbubId() != 0) {
                Integer ecbubId =bo.getEcbubId() ;
                BigDecimal bagThickness =bo.getBagThickness();
                ecuqDesc.setEcbubId(ecbubId);
                ecuqDesc.setBagThickness(bagThickness);
                EcbuBag recordEcbuBag = new EcbuBag();
                recordEcbuBag.setEcbubId(ecuqDesc.getEcbubId());
                EcbuBag ecbuBag = ecbuBagService.getObject(recordEcbuBag);
                ecuqDesc.setEcbuBag(ecbuBag);
                mapBag = EcableFunction.getBagData(ecuqInput, ecuqDesc,
                        ecquParameter, ecbuBag, externalDiameter);
                bagWeight = new BigDecimal(mapBag.get("bagWeight").toString());//包带重量
                bagMoney = new BigDecimal(mapBag.get("bagMoney").toString());//包带金额
                bagDiameter = new BigDecimal(mapBag.get("bagDiameter").toString());
                map.put("bagDiameter", bagDiameter);
                map.put("bagWeight", bagWeight);
                map.put("bagMoney", bagMoney);
            }
        }

        //计算屏蔽数据
        BigDecimal shieldWeight = new BigDecimal("0");
        BigDecimal shieldMoney = new BigDecimal("0");
        if (ecuqDesc.getEcbusId() != 0 && ecuqDesc
                .getShieldThickness().compareTo(new BigDecimal("0")) != 0) {
            EcbuShield recordEcbuShield = new EcbuShield();
            recordEcbuShield.setEcbusId(ecuqDesc.getEcbusbId());
            EcbuShield ecbuShield = ecbuShieldService.getObject(recordEcbuShield);
            ecuqDesc.setEcbuShield(ecbuShield);
            if (ecbuShield != null) {
                BigDecimal totalShieldDiameter = externalDiameter
                        .add(
                                ecuqDesc.getBagThickness()
                                        .divide(new BigDecimal("100"), 20, RoundingMode.HALF_UP)
                                        .multiply(new BigDecimal("2"))
                        )
                        .add(
                                ecuqDesc.getShieldThickness()
                                        .divide(new BigDecimal("100"), 20, RoundingMode.HALF_UP)
                                        .multiply(new BigDecimal("2"))
                        );
                BigDecimal totalShieldVolume = totalShieldDiameter
                        .divide(new BigDecimal("2"), 20, RoundingMode.HALF_UP)
                        .multiply(
                                totalShieldDiameter.divide(new BigDecimal("2"), 20, RoundingMode.HALF_UP)
                        );
                BigDecimal innerShieldDiameter = externalDiameter
                        .add(
                                ecuqDesc.getBagThickness()
                                        .divide(new BigDecimal("100"), 20, RoundingMode.HALF_UP)
                                        .multiply(new BigDecimal("2"))
                        );
                BigDecimal innerShieldVolume = innerShieldDiameter
                        .divide(new BigDecimal("2"), 20, RoundingMode.HALF_UP)
                        .multiply(
                                innerShieldDiameter.divide(new BigDecimal("2"), 20, RoundingMode.HALF_UP)
                        );
                shieldWeight = (totalShieldVolume
                        .subtract(innerShieldVolume))
                        .multiply(ecbuShield.getDensity())
                        .multiply(new BigDecimal("1").add(ecuqDesc.getShieldPercent()))
                        .multiply(ecquParameter.getLength())
                        .multiply(BigDecimal.valueOf(Math.PI));
                shieldMoney = shieldWeight.multiply(ecbuShield.getUnitPrice());
                map.put("shieldWeight", shieldWeight);
                map.put("shieldMoney", shieldMoney);
            }
        }
        //计算钢带数据
        //System.out.println("h9");
        BigDecimal steelbandWeight = new BigDecimal("0");
        BigDecimal steelbandMoney = new BigDecimal("0");
        if (ecuqDesc.getEcbusbId() != 0 && ecuqDesc
                .getSteelbandThickness()
                .compareTo(new BigDecimal("0")) != 0) {
            Integer ecbusbId =bo.getEcbusbId();
            BigDecimal steelbandThickness = bo.getSteelbandThickness();
            Integer steelbandStorey = bo.getSteelbandStorey();
            ecuqDesc.setEcbusbId(ecbusbId);
            ecuqDesc.setSteelbandThickness(steelbandThickness);
            ecuqDesc.setSteelbandStorey(steelbandStorey);
            EcbuSteelband recordEcbuSteelband = new EcbuSteelband();
            recordEcbuSteelband.setEcbusId(ecuqDesc.getEcbusbId());
            EcbuSteelband ecbuSteelband = ecbuSteelbandService.getObject(recordEcbuSteelband);
            ecuqDesc.setEcbuSteelband(ecbuSteelband);
            Map<String, Object> mapSteelband = EcableFunction
                    .getSteelbandData(ecuqDesc,
                            ecquParameter,
                            ecbuSteelband,
                            externalDiameter);
            steelbandWeight = new BigDecimal(mapSteelband.get("steelbandWeight").toString());//钢带重量
            steelbandMoney = new BigDecimal(mapSteelband.get("steelbandMoney").toString());//钢带金额
            map.put("steelbandDiameter", mapSteelband.get("steelbandDiameter").toString());
            map.put("steelbandWeight", steelbandWeight);
            map.put("steelbandMoney", steelbandMoney);
        }
        //计算护套数据
        BigDecimal sheathWeight = new BigDecimal("0");
        BigDecimal sheathMoney = new BigDecimal("0");
        if (ecuqDesc.getEcbusid() != 0 && ecuqDesc.getSheathThickness()
                .compareTo(new BigDecimal("0")) != 0) {
            Integer ecbusid = bo.getEcbsid();
            BigDecimal sheathThickness =bo.getSheathThickness();
            ecuqDesc.setEcbusid(ecbusid);
            ecuqDesc.setSheathThickness(sheathThickness);
            EcbuSheath recordEcbuSheath = new EcbuSheath();
            recordEcbuSheath.setEcbusId(ecuqDesc.getEcbusid());
            EcbuSheath ecbuSheath = ecbuSheathService.getObject(recordEcbuSheath);
            ecuqDesc.setEcbuSheath(ecbuSheath);
            EcbSheath recordEcbSheath = new EcbSheath();
            recordEcbSheath.setEcbsId(ecuqDesc.getEcbusid());
            EcbSheath ecbSheath = ecbSheathService.getObject(recordEcbSheath);
            ecuqDesc.setEcbSheath(ecbSheath);
            Map<String, Object> mapSheath = EcableFunction.getSheathData(ecuqInput,
                    ecuqDesc, ecquParameter,
                    ecbuSheath, externalDiameter);
            sheathWeight = new BigDecimal(mapSheath.get("sheathWeight").toString());//护套重量
            sheathMoney = new BigDecimal(mapSheath.get("sheathMoney").toString());//护套金额
            map.put("sheathDiameter", mapSheath.get("sheathDiameter").toString());//护套外径
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

    //dealBatchBillPercent 当更新到EcuqDesc时更新billPercent
    public void dealBatchBillPercent(InputBatchDealBo bo) {

        Integer ecuqId = bo.getEcuqId();
        Integer priceType = bo.getPriceType();

        BigDecimal billPercent = new BigDecimal("0");
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
                if ("YJV".equals(ecSilk.getAbbreviation())) {//铜
                    EcduTaxpoint recordEcduTaxpoint = new EcduTaxpoint();
                    recordEcduTaxpoint.setEcdtId(1);
                    EcduTaxpoint ecduTaxpoint = ecduTaxpointService.getObject(recordEcduTaxpoint);//铜
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
                    EcuqDesc ecuqDesc = ecuqDescService.getObject(recordEcuqDesc);//清除金额
                    if (ecuqDesc != null) {
                        ecuqDescModel.cleanMoney(ecuqDesc.getEcuqdId());
                    }
                }
            }
        }
        EcuQuoted recordEcuQuoted = new EcuQuoted();
        recordEcuQuoted.setEcuqId(ecuqId);
        recordEcuQuoted.setBillPercentType(priceType);
        //log.info(String.valueOf(recordEcuQuoted));
        ecuQuotedService.update(recordEcuQuoted);

        ecuQuotedModel.cleanMoney(ecuqId);//清除报价单总额
    }

    //dealSort
    public void dealSort(InputSortBo bo) {

        Integer ecuqiId = bo.getEcuqiId();
        Integer sortId = bo.getSortId();
        EcuqInput record = new EcuqInput();
        record.setEcuqiId(ecuqiId);
        record.setSortId(sortId);
        ecuqInputService.update(record);
    }

    //dealItemDesc
    public void dealItemDesc(InputItemDescBo bo) {

        Integer ecuqiId = bo.getEcuqiId();
        String itemDesc = bo.getItemDesc();
        EcuqInput record = new EcuqInput();
        record.setEcuqiId(ecuqiId);
        record.setItemDesc(itemDesc);
        ecuqInputService.update(record);
    }

    //dealProfitInput
    public void dealProfitInput(InputProfitBo bo) {

        Integer ecuqiId = bo.getEcuqiId();
        boolean profitInput = bo.getProfitInput();
        EcuqInput record = new EcuqInput();
        record.setEcuqiId(ecuqiId);
        record.setProfitInput(profitInput);
        ecuqInputService.update(record);
    }

    //importData
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    public void importData(HttpServletRequest request) {

        Integer ecuqId = Integer.parseInt(request.getParameter("ecuqId"));

        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        Integer ecuId = ecUser.getEcuId();

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("file");
        assert file != null;
        InputStream in = file.getInputStream();
        List<List<Object>> listob = excelUtils.getListByExcel(in, file.getOriginalFilename());
        Integer ecbusId;//仓库ID
        Integer ecqulId = 0;//质量等级
        String silkName;//型号
        String areaStr;//规格
        Integer saleNumber;//数量
        Integer ecbuluId;//单位
        Integer i = 0;
        List<EcuqInput> list = new ArrayList<>();
        for (List<Object> objects : listob) {
            //log.info("i + " + i);
            //log.info(CommonFunction.getGson().toJson(objects));
            String storeName = objects.get(0).toString();//仓库名称
            EcbuStore ecbuStore = ecbuStoreModel
                    .getObjectPassEcCompanyIdAndStoreName(ecUser.getEcCompanyId(), storeName);
            if (ecbuStore == null) {
                list = new ArrayList<>();
                break;
            } else {
                ecbusId = ecbuStore.getEcbusId();
            }
            String levelName = objects.get(1).toString();//质量等级名称
            EcquLevel ecquLevel = ecquLevelModel
                    .getObjectPassEcCompanyIdAndName(ecUser.getEcCompanyId(), levelName);
            if (ecquLevel == null) {
                list = new ArrayList<>();
                break;
            } else {
                ecqulId = ecquLevel.getEcqulId();
                areaStr = objects.get(3).toString();//截面
                boolean areaIsExists = ecdAreaModel.isExistsPassEcqulId(ecqulId, areaStr);
                if (!areaIsExists) {
                    list = new ArrayList<>();
                    break;
                }
            }
            silkName = objects.get(2).toString();//型号
            List<EcSilk> listSilk = ecSilkModel.getListAllSilkName(ecuId);
            for (EcSilk ecSilk : listSilk) {
                if (!silkName.equals(ecSilk.getAbbreviation())) {
                    list = new ArrayList<>();
                    break;
                }
            }
            String saleNumberStr = objects.get(4).toString();//销售数量
            if (!CommonFunction.isNumeric(saleNumberStr)) {
                list = new ArrayList<>();
                break;
            } else {
                saleNumber = Integer.parseInt(saleNumberStr);
            }
            String lengthName = objects.get(5).toString();//销售数量
            lengthName = lengthName.replace("（", "(");
            lengthName = lengthName.replace("）", ")");
            if ("米".equals(lengthName)) {
                ecbuluId = 0;
            } else {
                EcbulUnit ecbulUnit = ecbulUnitModel
                        .getObjectPassEcCompanyIdAndLengthName(ecUser.getEcCompanyId(), lengthName);
                if (ecbulUnit == null) {
                    list = new ArrayList<>();
                    break;
                } else {
                    ecbuluId = ecbulUnit.getEcbuluId();
                }
            }
            EcuqInput recordInput = new EcuqInput();
            recordInput.setStoreId(ecbusId);
            recordInput.setEcqulId(ecqulId);
            recordInput.setSilkName(silkName);
            recordInput.setAreaStr(areaStr);
            recordInput.setSaleNumber(saleNumber);
            recordInput.setEcbuluId(ecbuluId);
            list.add(recordInput);
        }
        if (!list.isEmpty()) {
            for (EcuqInput ecuqInput : list) {
                BigDecimal profit = new BigDecimal("0");//利润
                if (request.getParameter("profit") != null) {
                    profit = new BigDecimal(request.getParameter("profit"));
                }
                BigDecimal billPercent = new BigDecimal("0");//实际税点
                if (request.getParameter("billPercent") != null) {
                    billPercent = new BigDecimal(request.getParameter("billPercent"));
                }
                EcuqInput record = new EcuqInput();
                Integer sortId = 1;
                record.setEcuqId(ecuqId);
                EcuqInput inputObject = ecuqInputService.getLatestObject(record);
                if (inputObject != null) {
                    sortId = inputObject.getSortId() + 1;
                }
                record.setEcuqId(ecuqId);
                record.setEcqulId(ecqulId);
                record.setStoreId(ecuqInput.getStoreId());
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
                //新增时返回最后一个input
                EcuqInput recordEcuqInput = new EcuqInput();
                recordEcuqInput.setEcuqId(ecuqId);
                EcuqInput object = ecuqInputService.getLatestObject(record);
                if (object.getStoreId() != 0
                        && object.getEcqulId() != 0
                        && !"".equals(object.getSilkName())
                        && !"".equals(object.getAreaStr())) {
                    //log.info("详情修改");
                    ecuqDescModel.deal(object, ecUser.getEcCompanyId(), ecuId);
                }
            }
        }
    }

    //getObjectPassSilkName 根据丝型号获取默认的质量等级
    public Integer getObjectPassSilkName(InputSilkNameBo bo) {

        Integer ecqulId = bo.getEcqulId();
        String silkName = bo.getSilkName();

        Integer mecqulId = null;
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        Integer ecuId = ecUser.getEcuId();
        Integer ecsId = ecSilkModel.getEcsId(ecuId, silkName);
        EcquLevel ecquLevel = ecquLevelModel.getObjectPassEcqulId(ecqulId);
        if (ecquLevel == null || ecsId != ecquLevel.getEcsId()) {
            ecquLevel = ecquLevelModel.getObjectPassEcsIdAndDefaultType(ecuId, ecsId);
            if (ecquLevel != null) {
                mecqulId = ecquLevel.getEcqulId();
            }
        }

        return mecqulId;
    }

    //dealSilkNameAs 修改丝名称的别名
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

    //dealAreaStrAs 修改丝名称的别名
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

    //dealSilkNameInput 修改丝名称是否手输
    public void dealSilkNameInput(InputBo bo) {

        Integer ecuqiId = bo.getEcuqiId();
        EcuqInput record = new EcuqInput();
        record.setEcuqiId(ecuqiId);
        record.setSilkNameInput(false);
        //log.info("record + " + CommonFunction.getGson().toJson(record));
        ecuqInputService.update(record);
    }

    //dealAreaStrInput 修改截面是否手输
    public void dealAreaStrInput(InputBo bo) {

        Integer ecuqiId = bo.getEcuqiId();

        EcuqInput record = new EcuqInput();
        record.setEcuqiId(ecuqiId);
        record.setAreaStrInput(false);
        //log.info("record + " + CommonFunction.getGson().toJson(record));
        ecuqInputService.update(record);
    }

    /***===数据模型===***/
//dealBillPercent 当更新到EcuqDesc时更新billPercent
    public void dealBillPercent(Integer ecuqiId) {
        EcuqInput recordEcuqInput = new EcuqInput();
        recordEcuqInput.setEcuqiId(ecuqiId);
        EcuqInput ecuqInput = ecuqInputService.getObject(recordEcuqInput);
        EcuqInput record = new EcuqInput();
        record.setEcuqiId(ecuqInput.getEcuqiId());
        BigDecimal billPercent;
        record.setEcuqiId(ecuqiId);
        EcquLevel recordEcquLevel = new EcquLevel();
        recordEcquLevel.setEcqulId(ecuqInput.getEcqulId());
        EcquLevel ecquLevel = ecquLevelService.getObject(recordEcquLevel);
        EcSilk recordEcSilk = new EcSilk();
        recordEcSilk.setEcsId(ecquLevel.getEcsId());
        EcSilk ecSilk = ecSilkService.getObject(recordEcSilk);
        if ("YJV".equals(ecSilk.getAbbreviation())) {//铜
            EcduTaxpoint recordEcduTaxpoint = new EcduTaxpoint();
            recordEcduTaxpoint.setEcdtId(1);
            EcduTaxpoint ecduTaxpoint = ecduTaxpointService.getObject(recordEcduTaxpoint);//铜
            billPercent = ecduTaxpoint.getPercentSpecial();
            record.setBillPercent(billPercent);
        } else if ("YJLV".equals(ecSilk.getAbbreviation())) {//铝
            EcduTaxpoint recordEcduTaxpoint = new EcduTaxpoint();
            recordEcduTaxpoint.setEcdtId(2);
            EcduTaxpoint ecduTaxpoint = ecduTaxpointService.getObject(recordEcduTaxpoint);//铜
            billPercent = ecduTaxpoint.getPercentSpecial();
            record.setBillPercent(billPercent);
        } else if ("BV".equals(ecSilk.getAbbreviation())) {//铜
            EcduTaxpoint recordEcduTaxpoint = new EcduTaxpoint();
            recordEcduTaxpoint.setEcdtId(1);
            EcduTaxpoint ecduTaxpoint = ecduTaxpointService.getObject(recordEcduTaxpoint);//铝
            billPercent = ecduTaxpoint.getPercentSpecial();
            record.setBillPercent(billPercent);
        } else if ("BVR".equals(ecSilk.getAbbreviation())) {//铜
            EcduTaxpoint recordEcduTaxpoint = new EcduTaxpoint();
            recordEcduTaxpoint.setEcdtId(1);
            EcduTaxpoint ecduTaxpoint = ecduTaxpointService.getObject(recordEcduTaxpoint);//铝
            billPercent = ecduTaxpoint.getPercentSpecial();
            record.setBillPercent(billPercent);
        }
        ecuqInputService.update(record);
    }

    //getList
    public List<EcuqInput> getList(Integer ecuqId) {
        List<EcuqInput> list;
        EcuqInput record = new EcuqInput();
        record.setEcuqId(ecuqId);
        list = ecuqInputService.getList(record);
        return list;
    }

    //getCount
    public long getCount(Integer ecuqId) {
        long count;
        EcuqInput record = new EcuqInput();
        record.setEcuqId(ecuqId);
        count = ecuqInputService.getCount(record);
        return count;
    }

    //getObjectPassEcuqiId
    public EcuqInput getObjectPassEcuqiId(Integer ecuqiId) {
        EcuqInput record = new EcuqInput();
        record.setEcuqiId(ecuqiId);
        return ecuqInputService.getObject(record);
    }

}
