package org.jeecg.modules.cable.model.price;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
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
import org.jeecg.modules.cable.entity.systemCommon.EcSpecifications;
import org.jeecg.modules.cable.entity.systemEcable.EcSilk;
import org.jeecg.modules.cable.entity.systemEcable.EcbSheath;
import org.jeecg.modules.cable.entity.user.EccUnit;
import org.jeecg.modules.cable.entity.user.EcuDesc;
import org.jeecg.modules.cable.entity.user.EcuNotice;
import org.jeecg.modules.cable.entity.userCommon.*;
import org.jeecg.modules.cable.entity.userDelivery.EcbudDelivery;
import org.jeecg.modules.cable.entity.userEcable.*;
import org.jeecg.modules.cable.entity.userQuality.EcquLevel;
import org.jeecg.modules.cable.entity.userQuality.EcquParameter;
import org.jeecg.modules.cable.model.systemEcable.EcSilkServiceModel;
import org.jeecg.modules.cable.model.user.EcProfitModel;
import org.jeecg.modules.cable.model.user.EccUnitModel;
import org.jeecg.modules.cable.model.user.EcuNoticeModel;
import org.jeecg.modules.cable.model.userCommon.EcbuPlatformCompanyModel;
import org.jeecg.modules.cable.model.userCommon.EcbuStoreModel;
import org.jeecg.modules.cable.model.userCommon.EcbulUnitModel;
import org.jeecg.modules.cable.model.userDelivery.EcbuDeliveryModel;
import org.jeecg.modules.cable.model.userQuality.EcquLevelModel;
import org.jeecg.modules.cable.model.userQuality.EcuAreaModel;
import org.jeecg.modules.cable.service.price.EcuQuotedService;
import org.jeecg.modules.cable.service.price.EcuqDescService;
import org.jeecg.modules.cable.service.price.EcuqInputService;
import org.jeecg.modules.cable.service.systemCommon.EcSpecificationsService;
import org.jeecg.modules.cable.service.systemEcable.EcbSheathService;
import org.jeecg.modules.cable.service.user.EcuDescService;
import org.jeecg.modules.cable.service.userCommon.*;
import org.jeecg.modules.cable.service.userDelivery.EcbudDeliveryService;
import org.jeecg.modules.cable.service.userEcable.*;
import org.jeecg.modules.cable.service.userQuality.EcquLevelService;
import org.jeecg.modules.cable.service.userQuality.EcquParameterService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    EcduTaxPointService ecduTaxpointService;// 发票税点
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
    EcuQuotedModel ecuQuotedModel; //报价单
    @Resource
    EcSilkServiceModel ecSilkServiceModel; //型号系列
    @Resource
    private EcuSilkModelService ecuSilkModelService; //型号
    @Resource
    EcquLevelModel ecquLevelModel;// 质量等级
    @Resource
    EcProfitModel ecProfitModel;// 利润
    @Resource
    EcbuPlatformCompanyModel ecbuPlatformCompanyModel; //第三方平台公司设置
    final ExcelUtils excelUtils = new ExcelUtils();
    @Resource
    EcbuStoreModel ecbuStoreModel;// 仓库
    @Resource
    EcuAreaModel ecuAreaModel;// 截面库
    @Resource
    EcbulUnitModel ecbulUnitModel;// 单位
    @Resource
    private EcuDescService ecuDescService; //备注管理
    @Resource
    private EccUnitModel eccUnitModel; //型号默认单位
    @Resource
    private EcuNoticeModel ecuNoticeModel; //保价说明
    @Resource
    private EcuConductorPriceService ecuConductorPriceService;//导体价格
    @Resource
    private EcSpecificationsService ecSpecificationsService;


    public EcuqInput uniappDeal(InputUniappDealBo bo) {
        String area = bo.getArea();
        String coreStr = bo.getCoreStr();
        //平米数跟芯数都不为空的情况下，去寻找规格对应的全称，再用全称去找成本库表明细对应的id
        String areaStr = "";
        Integer ecusmId = bo.getEcusmId();//型号ID
        //调用deal方法前的对象创建
        InputDealBo inputDealBo = new InputDealBo();
        BeanUtils.copyProperties(bo, inputDealBo);
        if (StrUtil.isNotBlank(area) && StrUtil.isNotBlank(coreStr) && ObjUtil.isNotNull(ecusmId) && ecusmId != 0) {
            String[] split = coreStr.split("\\+");
            //多根芯数的情况
            if (split.length == 2) {
                String fire = split[0];
                String zero = split[1];
                areaStr = fire + "*" + area + "+" + zero;
            } else if (split.length == 1) {
                String fire = split[0];
                areaStr = fire + "*" + area;
            }
            if (split.length > 2) {
                throw new RuntimeException("芯数选择错误");
            }
            //查询型号
            EcuSilkModel ecuSilkModel = ecuSilkModelService.getObjectById(ecusmId);
            String abbreviation = ecuSilkModel.getAbbreviation();
            String fullName = ecuSilkModel.getFullName();
            boolean special = false;
            if (abbreviation.toUpperCase().contains("YC")
                    || abbreviation.toUpperCase().contains("YZ")
                    || abbreviation.toUpperCase().contains("JHS")
                    || fullName.toUpperCase().contains("YC")
                    || fullName.toUpperCase().contains("YZ")
                    || fullName.toUpperCase().contains("JHS")
            ) {
                special = true;
            }
            //根据型号和简写的规格，查询全称的规格
            List<EcSpecifications> specifications = ecSpecificationsService.selectListByName(special, areaStr);
            if (specifications.isEmpty()) {
                throw new RuntimeException("当前选择的型号与平方、芯数的组合，未能匹配到对应的规格");
            } else {
                EcSpecifications specifications1 = specifications.get(0);
                String fullName1 = specifications1.getFullName();
                inputDealBo.setAreaStr(fullName1);
            }
        }
        //调用deal方法
        EcuqInput deal = deal(inputDealBo);
        return deal;
    }


    public EcuqInput deal(InputDealBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        Integer ecuqiId = bo.getEcuqiId();// 主键ID
        Integer ecuqId = bo.getEcuqId();// 报价单ID
        //EcuQuoted quoted = ecuQuotedModel.getById(ecuqId);
        //Integer ecpId = quoted.getEcpId();
        //Integer deliveryStoreId = quoted.getDeliveryStoreId();
        //if (ObjUtil.isNull(deliveryStoreId) || deliveryStoreId == 0) {
        //    throw new RuntimeException("请先选择发货地仓库");
        //}
        //if (ObjUtil.isNull(ecpId) || ecpId == 0) {
        //    throw new RuntimeException("请先选择目的省份");
        //}
        Integer ecqulId = 0;// 质量等级ID
        if (bo.getEcqulId() != null) {
            ecqulId = bo.getEcqulId();
        }
        int storeId = 0;// 仓库ID
        if (bo.getEcbusId() != null) {
            storeId = bo.getEcbusId();
        }
        //型号系列ID
        Integer silkId = bo.getSilkId();
        String silkName = "";// 型号系列名称
        if (bo.getSilkName() != null) {
            silkName = bo.getSilkName();
        }
        //型号ID
        Integer silkModelId = 0;
        String SilkModelName = "";
        EcuSilkModel silkModel = null;
        if (ObjUtil.isNotNull(bo.getEcusmId()) && bo.getEcusmId() != 0) {
            //有型号的情况下，判断下默认单位
            silkModelId = bo.getEcusmId();
            silkModel = ecuSilkModelService.getObjectById(silkModelId);
            SilkModelName = silkModel.getAbbreviation();
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
        if (ObjUtil.isNotNull(bo.getEcbuluId())) {
            ecbuluId = bo.getEcbuluId();
        } else {
            //如果传进来的单位长度是null，那么就查一下是否有型号ID
            //有的话，给设置一个默认的单位Id
            if (ObjUtil.isNotNull(bo.getEcusmId()) && bo.getEcusmId() != 0) {
                EccUnit eccUnit = eccUnitModel.selectByModelId(bo.getEcusmId());
                if (ObjUtil.isNotNull(eccUnit)) {
                    ecbuluId = eccUnit.getEcbuluId();
                }
            }
        }
        BigDecimal profit = BigDecimal.ZERO;// 利润
        if (bo.getProfit() != null) {
            profit = bo.getProfit();
        }
        BigDecimal billPercent = BigDecimal.ZERO;// 实际税点
        BigDecimal newBillPercent = bo.getBillPercent();
        if (newBillPercent != null) {
            if (newBillPercent.compareTo(BigDecimal.ONE) >= 0) {
                throw new RuntimeException("实际税点设置不得大于等于100%");
            }
            if (newBillPercent.compareTo(BigDecimal.ZERO) < 0) {
                throw new RuntimeException("实际税点设置不得为负值");
            }
            billPercent = newBillPercent;
        }
        EcuqInput record = new EcuqInput();// log.info("h2");
        EcuqInput object;
        if (ecuqiId == 0) {// 插入
            int sortId = 1;
            record.setEcuqId(ecuqId);
            EcuqInput ecuqInput = ecuqInputService.getLatestObject(record);
            if (ecuqInput != null) {
                sortId = ecuqInput.getSortId() + 1;
            }
            record.setEcuqId(ecuqId);
            record.setEcbusId(storeId);
            record.setEcqulId(ecqulId);
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
            if (bo.getProfit() != null) {
                record.setProfitInput(true);
            } else {
                record.setProfitInput(false);
            }
            record.setBillPercent(billPercent);
            record.setItemDesc("");
            ecuqInputService.insert(record);
            object = record;
        } else {// 修改
            log.info("update");
            record.setEcuqiId(ecuqiId);
            if (ecqulId != 0 || storeId != 0 || silkModelId != 0) {
                record.setEcqulId(ecqulId);
                //选择的报价明细发生变动后就将状态改为非手动
                ecuqDescModel.cleanUnitPriceInput(ecuqiId, false);
            }
            if (!areaStr.isEmpty()) {// 规格
                record.setAreaStr(areaStr);
            }
            //先查看下原来的明细单，判断下质量等级ID是否发生了变动，如果发生了变动，需要将规格清空
            EcuqInput ecuqInput = ecuqInputService.getObject(record);
            Integer oldEcqulId = ecuqInput.getEcqulId();
            if (!ecqulId.equals(oldEcqulId)) {
                record.setAreaStr("");
            }
            if (storeId != 0) {
                record.setEcbusId(storeId);
            }
            if (bo.getSaleNumber() != null) {// 销售数量
                record.setSaleNumber(saleNumber);
            }
            if (bo.getEcbuluId() != null) {// 单位长度
                record.setEcbuluId(ecbuluId);
            }
            record.setProfit(profit);
            // 利润
            record.setProfitInput(bo.getProfit() != null);
            if (billPercent.compareTo(BigDecimal.ZERO) > 0) {// 实际税点
                record.setBillPercent(billPercent);
            }
            log.info("record + " + CommonFunction.getGson().toJson(record));
            if (silkModelId != 0) {
                record.setEcusmId(silkModelId);
                record.setSilkModelName(SilkModelName);
            }
            ecuqInputService.update(record);
            object = getObjectPassEcuqiId(ecuqiId);
        }
        if (object != null && object.getEcbusId() != 0
                && object.getEcqulId() != 0
                && ObjUtil.isNotNull(bo.getEcusmId()) && bo.getEcusmId() != 0
                && StrUtil.isNotBlank(object.getAreaStr())) {
            ecuqDescModel.deal(object, silkModel, sysUser.getEcCompanyId());
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
        Integer ecuqiId = bo.getEcuqiId();
        EcuqInput record = new EcuqInput();
        record.setEcuqiId(ecuqiId);
        EcuqInput object = ecuqInputService.getObject(record);
        if (ObjUtil.isNull(object)) {
            throw new RuntimeException("数据不存在，请刷新页面重试！");
        }
        //报价单id，同一报价单下的行数据被删除后，后面的排序自动向前
        Integer ecuqId = object.getEcuqId();
        Integer sortId = object.getSortId();
        ecuqInputService.reduceSort(ecuqId, sortId);
        ecuqInputService.delete(ecuqiId);
        // 删除对应的Ecq_desc 报价详情
        ecuqDescService.deletePassEcuqiId(ecuqiId);
    }

    @Transactional(rollbackFor = Exception.class)
    public InputListVo getListQuoted(InputListBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        Integer userEcCompanyId = sysUser.getEcCompanyId();
        Integer userType = sysUser.getUserType();
        //获取报价单
        Integer ecuqId = bo.getEcuqId();
        EcuQuoted ecuQuoted = ecuQuotedService.getObjectById(ecuqId);
        if (ObjUtil.isNull(ecuQuoted)) {
            throw new RuntimeException("未查询到当前报价单");
        }
        //判断下请求的这个报价单是不是当前这个用户所在公司的报价单，如果当前用户不是后台管理员，就报错
        Integer ecCompanyId = ecuQuoted.getEcCompanyId();
        if (!Objects.equals(userEcCompanyId, ecCompanyId)) {
            throw new RuntimeException("当前订单不属于您所在的公司，您无权操作！");
        }
        EcbuPlatformCompany ecbuPlatformCompany = ecbuPlatformCompanyModel.getObjectPassEcbupId(ecuQuoted.getEcbupId());
        if (ObjUtil.isNull(ecbuPlatformCompany)) {
            throw new RuntimeException("对应销售平台不存在");
        }
        //根据报价单获取报价单明细
        List<EcuqInput> listInput = getListByQuoteId(ecuqId);
        BigDecimal allWeight = BigDecimal.ZERO;// 总重量
        BigDecimal billTotalMoney = BigDecimal.ZERO;// 开票总计
        BigDecimal noBillTotalMoney = BigDecimal.ZERO;// 不开票总计
        BigDecimal tempNoBillTotalMoney = BigDecimal.ZERO;// 不加运费的不开票总计
        BigDecimal freight = BigDecimal.ZERO; //报价单对应总重量算出来的运费价格
        for (EcuqInput ecuqInput : listInput) {
            String silkName = ecuqInput.getSilkName();
            Integer ecqulId = ecuqInput.getEcqulId();
            String areaStr = ecuqInput.getAreaStr();
            Integer saleNumber = ecuqInput.getSaleNumber();
            if (ecqulId == 0 || "".equals(silkName) || "".equals(areaStr) || saleNumber == 0) {
                ecuqInput.setEcuqDesc(new EcuqDesc());
                continue;
            }
            //查询报价明细的重量价格等信息
            Integer ecuqiId = ecuqInput.getEcuqiId();
            EcuqDesc recordEcuqDesc = new EcuqDesc();
            recordEcuqDesc.setEcuqiId(ecuqiId);
            EcuqDesc ecuqDesc = ecuqDescService.getObject(recordEcuqDesc);
            if (ecuqDesc == null) {
                ecuqInput.setEcuqDesc(new EcuqDesc());
                continue;
            }
            //装载报价明细的详细信息
            ecuqInput.setEcuqDesc(ecuqDesc);
            inputCompute(ecuQuoted, ecuqInput, ecuqDesc, ecbuPlatformCompany, ecCompanyId);
            //总重量
            allWeight = allWeight.add(ecuqInput.getTotalWeight());
            tempNoBillTotalMoney = tempNoBillTotalMoney.add(ecuqInput.getNoBillComputeMoney());
        }
        log.info("【 本报价单加上木轴后总重量 】 ---------> : {} ", allWeight);
        // ------以下是计算总运费的数据(运费需要按照报价明细的小计进行按比例分配)-------------
        Integer ecbudId = bo.getEcbudId();
        boolean delivery = ecbudId != -1 && ecuQuoted.getEcbudId() != -1;
        List<DeliveryObj> listDeliveryPrice = ecbuDeliveryModel.getDeliveryPriceList(ecCompanyId, ecuQuoted, allWeight);
        if (delivery) {
            //此处新增对应这个报价单的快递选择（选择价位第几个的快递方式）
            EcbudDelivery recordEcbudDelivery = new EcbudDelivery();
            recordEcbudDelivery.setEcCompanyId(ecCompanyId);
            recordEcbudDelivery.setEcuqId(ecuqId);
            EcbudDelivery dDelivery = ecbudDeliveryService.getObject(recordEcbudDelivery);
            if (dDelivery == null) {
                recordEcbudDelivery.setSortId(1);
                ecbudDeliveryService.insert(recordEcbudDelivery);
                dDelivery = recordEcbudDelivery;
            }
            DeliveryObj objectDelivery = EcableFunction.getDeliveryData(ecuQuoted, listDeliveryPrice, dDelivery);
            freight = objectDelivery.getPrice();
            //运费除以
            if (ecuQuoted.getDeliveryDivide().compareTo(BigDecimal.ZERO) != 0) {
                freight = freight.divide(ecuQuoted.getDeliveryDivide(), 16, RoundingMode.HALF_UP);
            }
            //运费加减
            if (ecuQuoted.getDeliveryAdd().compareTo(BigDecimal.ZERO) != 0) {
                freight = freight.add(ecuQuoted.getDeliveryAdd());
            }
            log.info("本报价单运费计算后总金额 ---------> : {} ", freight);
        }
        List<EcuqInputVo> voList = new ArrayList<>();
        //获取当前用户所在公司设置的发票数据
        EcduCompany ecduCompany = ecduCompanyService.getObjectByCompanyId(ecCompanyId);
        log.info("本报价单获取的公司信息 ---------> : {} ", ecduCompany);
        for (EcuqInput ecuqInput : listInput) {
            EcuqInputVo vo = new EcuqInputVo();
            EcuqDesc ecuqDesc = ecuqInput.getEcuqDesc();
            //无票小计
            BigDecimal noBillComputeMoney = BigDecimal.ZERO;
            //开票小计
            BigDecimal billComputeMoney = BigDecimal.ZERO;
            //无票单价
            BigDecimal noBillSingleMoney = BigDecimal.ZERO;
            //开票单价
            BigDecimal billSingleMoney = BigDecimal.ZERO;
            if (ObjUtil.isNotNull(ecuqDesc)) {
                vo.setEcbuaId(ecuqDesc.getEcbuaId());
                vo.setAxleNumber(ecuqDesc.getAxleNumber());
                vo.setUnitPrice(ecuqDesc.getUnitPrice());
                // 启用手输价格
                Integer saleNumber = ecuqInput.getSaleNumber();
                //销售数量
                BigDecimal saleDecimal = BigDecimal.valueOf(saleNumber);
                if (ecuqDesc.getInputStart()) {
                    ///手动输入的话，直接取手动输入的值
                    //开票单价
                    billSingleMoney = ecuqDesc.getBupsMoney();
                    //开票小计
                    billComputeMoney = ecuqDesc.getBupcMoney();
                    EcbulUnit ecbulUnit = ecuqInput.getEcbulUnit();
                    if (ecbulUnit == null) {
                        if (billSingleMoney.compareTo(BigDecimal.ZERO) > 0) {
                            if (billComputeMoney.divide(billSingleMoney, 0, RoundingMode.HALF_UP).compareTo(saleDecimal) != 0) {
                                billComputeMoney = billSingleMoney.multiply(saleDecimal);
                            }
                        }
                    } else {
                        //BigDecimal meterNumberDecimal = saleDecimal.multiply(BigDecimal.valueOf(ecbulUnit.getMeterNumber()));
                        if (billComputeMoney.compareTo(BigDecimal.ZERO) > 0) {
                            if (billComputeMoney.divide(billSingleMoney, 0, RoundingMode.HALF_UP).compareTo(saleDecimal) != 0) {
                                billComputeMoney = billSingleMoney.multiply(saleDecimal);
                                //.multiply(meterNumberDecimal);
                            }
                        }
                    }
                    //不开票单价
                    noBillSingleMoney = ecuqDesc.getNbupsMoney();
                    //不开票小计
                    noBillComputeMoney = ecuqDesc.getNbupcMoney();
                    if (ecbulUnit == null) {
                        if (billSingleMoney.compareTo(BigDecimal.ZERO) > 0) {
                            if (noBillComputeMoney.divide(noBillSingleMoney, 0, RoundingMode.HALF_UP).compareTo(saleDecimal) != 0) {
                                noBillComputeMoney = noBillSingleMoney.multiply(saleDecimal);
                            }
                        }
                    } else {
                        if (noBillComputeMoney.compareTo(BigDecimal.ZERO) > 0) {
                            if (noBillComputeMoney.divide(noBillSingleMoney, 0, RoundingMode.HALF_UP).compareTo(saleDecimal) != 0) {
                                //不开票单价*销售数量
                                //BigDecimal meterNumberDecimal = saleDecimal.multiply(BigDecimal.valueOf(ecbulUnit.getMeterNumber()));
                                noBillComputeMoney = ecuqDesc.getNbupsMoney().multiply(saleDecimal);
                                //.multiply(meterNumberDecimal);
                            }
                        }
                    }
                } else {
                    noBillComputeMoney = ecuqInput.getNoBillComputeMoney();
                    //报价明细的无票小计除以报价单的未加上运费的无票总计，得到快递的分配比例
                    BigDecimal divide = BigDecimal.ZERO;
                    if (tempNoBillTotalMoney.compareTo(BigDecimal.ZERO) > 0) {
                        divide = noBillComputeMoney.divide(tempNoBillTotalMoney, 16, RoundingMode.HALF_UP);
                    }
                    BigDecimal multiply = freight.multiply(divide);
                    //无票小计加上快递金额得到新的无票小计
                    noBillComputeMoney = noBillComputeMoney.add(multiply);
                    //注意这里是通过  无票小计-->无票单价-->有票单价-->有票小计
                    noBillSingleMoney = noBillComputeMoney.divide(saleDecimal, 16, RoundingMode.HALF_UP);
                    //开票单价
                    billSingleMoney = EcableFunction.getBillPercentData(ecuqInput, ecduCompany, noBillSingleMoney);
                    billComputeMoney = billSingleMoney.multiply(saleDecimal).stripTrailingZeros();
                    // 提交详情金额
                }
                billSingleMoney = billSingleMoney.setScale(2, RoundingMode.HALF_UP).stripTrailingZeros();
                noBillComputeMoney = noBillComputeMoney.setScale(2, RoundingMode.HALF_UP).stripTrailingZeros();
                billComputeMoney = billComputeMoney.setScale(2, RoundingMode.HALF_UP).stripTrailingZeros();
                noBillComputeMoney = noBillComputeMoney.setScale(2, RoundingMode.HALF_UP).stripTrailingZeros();
                if (ObjUtil.isNotNull(ecuqDesc.getEcuqdId())) {
                    ecuqDescModel.dealMoney(ecuqDesc.getEcuqdId(),
                            billSingleMoney, noBillComputeMoney, billComputeMoney, noBillComputeMoney,
                            ecuqInput.getTotalWeight());
                }
                ecuqInput.setNoBillSingleMoney(noBillSingleMoney.setScale(2, RoundingMode.HALF_UP).stripTrailingZeros());// 无票单价
                ecuqInput.setNoBillComputeMoney(noBillComputeMoney.stripTrailingZeros());// 无票小计
                ecuqInput.setBillComputeMoney(billComputeMoney.stripTrailingZeros());// 有票小计
                ecuqInput.setBillSingleMoney(billSingleMoney.stripTrailingZeros());// 有票单价
            }
            // 开票总额
            billTotalMoney = billTotalMoney.add(billComputeMoney);
            //无票总额
            noBillTotalMoney = noBillTotalMoney.add(noBillComputeMoney);
            ecuqInput.setEcuqDesc(ecuqDesc);
            BeanUtils.copyProperties(ecuqInput, vo);

            //根据报价单明细中的型号规格查询备注
            if (ObjUtil.isNotNull(ecuqDesc) && StrUtil.isNotBlank(ecuqDesc.getAreaStr())
                    && ObjUtil.isNotNull(ecuqInput.getEcusmId())) {
                EcuDesc desc = ecuDescService.getObjectByModelAndAreaStr(ecCompanyId, ecuqDesc.getAreaStr(), ecuqInput.getEcusmId());
                vo.setEcuDesc(desc);
            }
            voList.add(vo);
        }
        ecuQuotedService.dealMoney(ecuqId, noBillTotalMoney, billTotalMoney, freight, allWeight);
        ecuQuoted.setNbuptMoney(noBillTotalMoney);
        ecuQuoted.setBuptMoney(billTotalMoney);
        ecuQuoted.setDeliveryMoney(freight);
        ecuQuoted.setTotalWeight(allWeight);
        //获取报价单对应的设置的价格信息
        List<EcuConductorPrice> ecuConductorPrices = ecuConductorPriceService.listByQuotedId(ecuqId);
        //查找报价说明
        EcuNotice ecuNotice = ecuNoticeModel.getObjectForQuoted(sysUser.getUserId());
        // 添加报价单总额
        return new InputListVo(billTotalMoney, noBillTotalMoney, ecuQuoted, ecuConductorPrices, voList, listDeliveryPrice, ecuNotice);
    }


    /**
     * 报价单上附属信息对金额的影响
     * A=（总材料成本*（1+成本库的成本加点））*（1+仓库利润点）+重量*运费单价（工厂到仓库）
     * B=A*(1+平台费率)
     * C=B+重量*运费单价(仓库到客户的)
     * C就是不含税总额，按照公司数据配置的税额算法进行计算
     *
     * @param ecuQuoted           报价单
     * @param ecuqInput           报价单明细
     * @param ecuqDesc            报价单材质、金额明细
     * @param ecbuPlatformCompany 平台公司信息
     * @param ecCompanyId         用户对应公司信息
     */
    public void inputCompute(EcuQuoted ecuQuoted, EcuqInput ecuqInput, EcuqDesc ecuqDesc, EcbuPlatformCompany ecbuPlatformCompany,
                             Integer ecCompanyId) {
        //每销售单位的米数
        BigDecimal meterNumberDecimal = BigDecimal.ONE;
        //总米数
        BigDecimal totalMeterDecimal = BigDecimal.ZERO;
        //报价单明细销售的数量
        Integer saleNumber = ecuqInput.getSaleNumber();
        BigDecimal saleDecimal = BigDecimal.valueOf(saleNumber);
        if (ecuqInput.getEcbuluId() != 0) {
            // 单位长度数据
            EcbulUnit ecbulUnit = ecbulUnitService.getObjectById(ecuqInput.getEcbuluId());
            Integer meterNumber = ecbulUnit.getMeterNumber();
            meterNumberDecimal = BigDecimal.valueOf(meterNumber);
            //如果有单位数量的话，重新计算销售的米数
            int totalMeter = meterNumber * saleNumber;
            totalMeterDecimal = BigDecimal.valueOf(totalMeter);
            ecuqInput.setEcbulUnit(ecbulUnit);
        } else {
            totalMeterDecimal = BigDecimal.valueOf(saleNumber);
        }
        BigDecimal reduction = BigDecimal.ONE;
        BigDecimal reduction1 = ecuQuoted.getReduction();
        if (ObjUtil.isNotNull(reduction1) && reduction1.compareTo(BigDecimal.ZERO) > 0) {
            reduction = reduction1.divide(BigDecimal.valueOf(100D), 16, RoundingMode.HALF_UP);
        }
        //计算单纯材质的重量金额
        InputStructureVo compute = computeWeightPrice(ecuqDesc, ecuqInput, reduction);
        EcuqDesc recordEcuqDesc = new EcuqDesc();
        recordEcuqDesc.setEcuqdId(ecuqDesc.getEcuqdId());
        recordEcuqDesc.setCweight(compute.getConductorWeight());
        recordEcuqDesc.setUnitWeight(compute.getTotalWeight());
        ecuqDescService.update(recordEcuqDesc);

        // A 总材料成本 单位重量和材料单价
        BigDecimal unitWeight = compute.getTotalWeight();
        log.info("【本报价电缆每米重量】 ---------> : {} ", unitWeight);
        BigDecimal unitMoney = compute.getTotalMoney();
        log.info("【本报价电缆每米价格】 ---------> : {} ", unitMoney);
        //-----------销售数量计算逻辑开始-----------
        ecuqInput.setMeterNumber(totalMeterDecimal.intValue());
        //当前报价明细总重量 = 每米总重量*米数
        BigDecimal totalWeight = unitWeight.multiply(totalMeterDecimal);
        //当前销售单位的价格（不管是米还是卷） = 每米总价格*单位米数
        unitMoney = unitMoney.multiply(meterNumberDecimal);
        log.info("【本报价电缆每单位价格】 ---------> : {} ", unitMoney);
        //判断利润是否手输，改变利润
        if (!ecuqInput.getProfitInput()) {
            BigDecimal profitRate = ecProfitModel.dealProfitAuto(ecuqInput, unitMoney, ecCompanyId);
            log.info("【本报价明细利润不是手输，计算后匹配的利润为】 ---------> : {} ", profitRate);
            ecuqInput.setProfit(profitRate);
            EcuqInput input = new EcuqInput();
            input.setEcuqiId(ecuqInput.getEcuqiId());
            input.setProfit(profitRate);
            ecuqInputService.update(input);
        }
        // 木轴计算
        if (ecuqDesc.getEcbuaId() != 0) {
            EcbuAxle ecbuAxle = ecbuAxleService.getById(ecuqDesc.getEcbuaId());
            //木轴总价 = 木轴单价 * 木轴数量
            Integer axleNumber = ecuqDesc.getAxleNumber();
            BigDecimal axlePrice = ecbuAxle.getAxlePrice().multiply(new BigDecimal(axleNumber));
            //加上木轴单价后的每销售单位的单价  = 单价+（木轴总价/销售数量）
            unitMoney = unitMoney.add(axlePrice.divide(saleDecimal, 16, RoundingMode.HALF_UP));
            BigDecimal multiply = ecbuAxle.getAxleWeight().multiply(new BigDecimal(axleNumber));
            log.info("【本报价单木轴总重量】 ---------> : {} ", multiply);
            // 总重加上木轴的重量
            totalWeight = totalWeight.add(multiply);
        }
        // 单价的材料成本*（1+成本库的成本加点）
        unitMoney = unitMoney.multiply(BigDecimal.ONE.add(ecuqDesc.getAddPercent()));
        if (ecuQuoted.getPriceType().equals(1)) {
            // 仓库对应导体铜或者铝的利润加点
            //仓库铜利润表示，如果用户购买的型号的材质是铜，仓库铜利润设置2%。如果报价单计算报价为1000，那么实际报价应加上2%的利润。1000+1000*2%。
            // 也就是这里是总价的利润乘以2%
            unitMoney = unitMoney.multiply(BigDecimal.ONE.add(ecuqDesc.getStorePercent()));
        }
        // 报价单明细行上面的的利润
        unitMoney = unitMoney.multiply(BigDecimal.ONE.add(ecuqInput.getProfit()));
        //仓库运费增加的金额 = 每米重量*单位长度 * 工厂到仓库运费加点（kg/元）
        BigDecimal deliverySinglePercentMoney = unitWeight.multiply(meterNumberDecimal).multiply(ecuqDesc.getSdunitMoney());
        // 每销售单位价格，也就是销售单价
        unitMoney = unitMoney.add(deliverySinglePercentMoney);
        //------------整个报价单对应的所有报价明细的单位加价(这里的单位都是按照米来算的)----------
        BigDecimal unitPriceAdd = ecuQuoted.getUnitPriceAdd();
        unitPriceAdd = unitPriceAdd.multiply(meterNumberDecimal);
        unitMoney = unitMoney.add(unitPriceAdd);
        // 此报价单的加价百分比
        BigDecimal addPricePercent = ecuQuoted.getAddPricePercent();
        unitMoney = unitMoney.multiply(BigDecimal.ONE.add(addPricePercent));
        // 销售平台加点（京东淘宝之类的平台加点）
        BigDecimal pcPercent = ecbuPlatformCompany.getPcPercent();
        BigDecimal add = BigDecimal.ONE.add(pcPercent);
        unitMoney = unitMoney.multiply(add);
        //-------------------将计算值赋值给input对应的字段-------------------
        // 不开票单价
        ecuqInput.setNoBillSingleMoney(unitMoney);
        // 不开票小计（此处做一个开票小计，为的是后面第二次循环的时候，给运费做一个分配）
        BigDecimal noBillComputeMoney = unitMoney.multiply(saleDecimal);
        ecuqInput.setNoBillComputeMoney(noBillComputeMoney);
        // 本报价明细的总重量
        ecuqInput.setTotalWeight(totalWeight);
    }

    /**
     * 报价明细中所使用材料的计算
     *
     * @param ecuqDesc           报价明细金额
     * @param ecuqInput          报价明细
     * @param conductorReduction 导体折扣
     * @return
     */
    public InputStructureVo computeWeightPrice(EcuqDesc ecuqDesc, EcuqInput ecuqInput, BigDecimal conductorReduction) {
        InputStructureVo inputStructureVo = new InputStructureVo();
        Integer ecusmId = ecuqInput.getEcusmId();
        if (ObjUtil.isNotNull(ecusmId)) {
            EcuSilkModel silkModel = ecuSilkModelService.getObjectById(ecusmId);
            if (ObjUtil.isNull(silkModel)) {
                throw new RuntimeException("本报价单明细对应型号不存在");
            }
            //查询质量等级对应参数，拥有每米长度和成本加点
            EcquParameter recordEcquParameter = new EcquParameter();
            recordEcquParameter.setEcbusId(ecuqInput.getEcbusId());
            recordEcquParameter.setEcqulId(ecuqInput.getEcqulId());
            EcquParameter ecquParameter = ecquParameterService.getObject(recordEcquParameter);
            if (ecquParameter == null) {
                ecquParameter = new EcquParameter();
                ecquParameter.setLength(BigDecimal.ONE);
                ecquParameter.setCost(BigDecimal.ONE);
            }
            //创建电缆对象
            Cable cable = new Cable(ecuqInput.getAreaStr());
            cable.setLength(ecquParameter.getLength());
            // 计算导体
            Integer ecbucId = ecuqDesc.getEcbucId();
            if (ecbucId != 0 && silkModel.getConductor()) {
                //导体价格可以单独设置
                //报价单增加一个下拉框、和一个输入框。比如选择导体铜，如果设置的价格是5，显示5，用户可以更改这个价格。
                // 比如改成5.4，那么整体报价单按照导体铜是5.4的单价进行计算。只对这个报价单生效。
                EcbuConductor ecbuConductor = ecbuConductorService.getObjectById(ecbucId);// 导体
                ecuqInput.setEcbuConductor(ecbuConductor);
                cable.setConductorMaterial(
                        ecbuConductor.getDensity(), ecbuConductor.getUnitPrice(),
                        ecuqDesc.getFireRootNumber(), ecuqDesc.getZeroRootNumber(),
                        ecuqDesc.getFireSilkNumber(), ecuqDesc.getZeroSilkNumber(),
                        ecuqDesc.getFireStrand(), ecuqDesc.getZeroStrand(),
                        BigDecimal.ONE
                );
                ConductorMaterial conductorMaterial = cable.getConductorMaterial();
                inputStructureVo.setConductorDiameter(conductorMaterial.getExternalDiameter());
                inputStructureVo.setFireDiameter(conductorMaterial.getFireDiameter());
                inputStructureVo.setZeroDiameter(conductorMaterial.getZeroDiameter());
                inputStructureVo.setFireWeight(conductorMaterial.getFireWeight());
                inputStructureVo.setFireMoney(conductorMaterial.getFireMoney());
                inputStructureVo.setZeroWeight(conductorMaterial.getZeroWeight());
                inputStructureVo.setZeroMoney(conductorMaterial.getZeroMoney());
                inputStructureVo.setConductorWeight(conductorMaterial.getConductorWeight());
                inputStructureVo.setConductorMoney(conductorMaterial.getConductorMoney());
            }
            // 计算云母带数据
            Integer ecbumId = ecuqDesc.getEcbumId();
            if (ecbumId != 0 && silkModel.getMicaTape()) {
                EcbuMicaTape ecbuMicaTape = ecbuMicatapeService.getObjectById(ecbumId);
                ecuqDesc.setEcbuMicatape(ecbuMicaTape);
                cable.addInternalMaterial(ecbuMicaTape.getDensity(),
                        ecbuMicaTape.getUnitPrice(), BigDecimal.ONE,
                        ecuqDesc.getMicatapeThickness(), ecuqDesc.getMicatapeThickness());
                List<InternalMaterial> internalMaterial = cable.getInternalMaterial();
                InternalMaterial internalMaterial1 = internalMaterial.get(internalMaterial.size() - 1);
                inputStructureVo.setFireMicatapeDiameter(internalMaterial1.getFireRadius().multiply(new BigDecimal("2")));
                inputStructureVo.setZeroMicatapeDiameter(internalMaterial1.getZeroRadius().multiply(new BigDecimal("2")));
                inputStructureVo.setMicatapeWeight(internalMaterial1.getMaterialWeight());
                inputStructureVo.setMicatapeMoney(internalMaterial1.getMaterialMoney());
            }
            // 计算绝缘数据
            Integer ecbuiId = ecuqDesc.getEcbuiId();
            if (ecbuiId != 0 && silkModel.getInsulation()) {
                EcbuInsulation ecbuInsulation = ecbuInsulationService.getObjectById(ecbuiId);
                ecuqDesc.setEcbuInsulation(ecbuInsulation);
                cable.addInternalMaterial(ecbuInsulation.getDensity(),
                        ecbuInsulation.getUnitPrice(), BigDecimal.ONE,
                        ecuqDesc.getInsulationFireThickness(), ecuqDesc.getInsulationZeroThickness());
                List<InternalMaterial> internalMaterial = cable.getInternalMaterial();
                InternalMaterial internalMaterial1 = internalMaterial.get(internalMaterial.size() - 1);
                inputStructureVo.setInsulationFireDiameter(internalMaterial1.getFireRadius().multiply(new BigDecimal("2")));
                inputStructureVo.setInsulationZeroDiameter(internalMaterial1.getZeroRadius().multiply(new BigDecimal("2")));
                inputStructureVo.setInsulationWeight(internalMaterial1.getMaterialWeight());
                inputStructureVo.setInsulationMoney(internalMaterial1.getMaterialMoney());
            }
            // 计算填充物数据
            Integer ecbuinId = ecuqDesc.getEcbuinId();
            if (ecbuinId != 0 && silkModel.getInfilling()) {
                EcbuInfilling ecbuInfilling = ecbuInfillingService.getObjectById(ecbuinId);
                ecuqDesc.setEcbuInfilling(ecbuInfilling);
                cable.setInfillingMaterial(ecbuInfilling.getDensity(), ecbuInfilling.getUnitPrice());
                InfillingMaterial infillingMaterial = cable.getInfillingMaterial();
                inputStructureVo.setInfillingWeight(infillingMaterial.getInfillingWeight());
                inputStructureVo.setInfillingMoney(infillingMaterial.getInfillingMoney());
                inputStructureVo.setExternalDiameter(infillingMaterial.getExternalDiameter());
            }
            // 计算包带数据
            Integer ecbub22Id = ecuqDesc.getEcbub22Id();
            int bagId = 0;
            BigDecimal bagThickness = BigDecimal.ZERO;
            //有钢带的就是铠装
            Boolean steelBand = silkModel.getSteelBand();
            if (steelBand) {// 铠装
                if (ecbub22Id != 0) {
                    bagId = ecbub22Id;
                }
            } else {
                if (ecuqDesc.getEcbubId() != 0) {
                    bagId = ecuqDesc.getEcbubId();
                }
            }
            Boolean bag = silkModel.getBag();
            if (bagId != 0 && bag) {
                EcbuBag ecbuBag = ecbuBagService.getObjectById(bagId);
                ecuqDesc.setEcbuBag(ecbuBag);
                if (steelBand) {//有钢带才是铠装，铠装的话是用22的包带
                    bagThickness = ecuqDesc.getBag22Thickness();
                } else {
                    bagThickness = ecuqDesc.getBagThickness();
                }
                //此处的包带系数
                cable.addExternalMaterials(ecbuBag.getDensity(), ecbuBag.getUnitPrice(), BigDecimal.valueOf(1.1), bagThickness);
                List<ExternalMaterial> externalMaterials = cable.getExternalMaterials();
                ExternalMaterial externalMaterial = externalMaterials.get(externalMaterials.size() - 1);
                BigDecimal bagWeight = externalMaterial.getMaterialWeight();// 包带重量
                BigDecimal bagMoney = externalMaterial.getMaterialMoney();// 包带金额
                //包带外径
                BigDecimal bagDiameter = externalMaterial.getExternalRadius().multiply(BigDecimal.valueOf(2));
                inputStructureVo.setBagDiameter(bagDiameter);
                inputStructureVo.setBagWeight(bagWeight);
                inputStructureVo.setBagMoney(bagMoney);
            }
            // 计算屏蔽数据
            if (ecuqDesc.getEcbuShieldId() != 0 && silkModel.getShield()) {
                EcbuShield ecbuShield = ecbuShieldService.getObjectById(ecuqDesc.getEcbusbId());
                ecuqDesc.setEcbuShield(ecbuShield);
                if (ecbuShield != null) {
                    cable.addExternalMaterials(ecbuShield.getDensity(), ecbuShield.getUnitPrice(), BigDecimal.ONE, ecuqDesc.getShieldThickness());
                    List<ExternalMaterial> externalMaterials = cable.getExternalMaterials();
                    ExternalMaterial externalMaterial = externalMaterials.get(externalMaterials.size() - 1);
                    BigDecimal shieldWeight = externalMaterial.getMaterialWeight();
                    BigDecimal shieldMoney = externalMaterial.getMaterialMoney();
                    BigDecimal shieldDiameter = externalMaterial.getExternalRadius().multiply(BigDecimal.valueOf(2));
                    inputStructureVo.setShieldDiameter(shieldDiameter);
                    inputStructureVo.setShieldWeight(shieldWeight);
                    inputStructureVo.setShieldMoney(shieldMoney);
                }
            }

            // 计算钢带数据
            if (ecuqDesc.getEcbusbId() != 0 && silkModel.getSteelBand()) {
                EcbuSteelBand ecbuSteelband = ecbuSteelbandService.getObjectById(ecuqDesc.getEcbusbId());
                ecuqDesc.setEcbuSteelband(ecbuSteelband);
                cable.addExternalMaterials(ecbuSteelband.getDensity(), ecbuSteelband.getUnitPrice(), BigDecimal.ONE, ecuqDesc.getSteelbandThickness());
                List<ExternalMaterial> externalMaterials = cable.getExternalMaterials();
                ExternalMaterial externalMaterial = externalMaterials.get(externalMaterials.size() - 1);

                BigDecimal steelbandWeight = externalMaterial.getMaterialWeight();// 钢带重量
                BigDecimal steelbandMoney = externalMaterial.getMaterialMoney();// 钢带金额
                BigDecimal steelBandDiameter = externalMaterial.getExternalRadius().multiply(new BigDecimal("2"));
                inputStructureVo.setSteelbandDiameter(steelBandDiameter);
                inputStructureVo.setSteelbandWeight(steelbandWeight);
                inputStructureVo.setSteelbandMoney(steelbandMoney);
            }
            // 计算护套数据
            if (ecuqDesc.getEcbuSheathId() != 0 && silkModel.getSheath()) {
                EcbuSheath ecbuSheath = ecbuSheathService.getObjectById(ecuqDesc.getEcbuSheathId());
                ecuqDesc.setEcbuSheath(ecbuSheath);
                //系统护套
                EcbSheath recordEcbSheath = new EcbSheath();
                recordEcbSheath.setEcbsId(ecbuSheath.getEcbsId());
                EcbSheath ecbSheath = ecbSheathService.getObject(recordEcbSheath);
                ecuqDesc.setEcbSheath(ecbSheath);
                //护套厚度
                BigDecimal sheathThickness;
                if (silkModel.getSteelBand()) { //带钢带的情况下就是使用铠装护套
                    sheathThickness = ecuqDesc.getSheath22Thickness();
                } else {
                    sheathThickness = ecuqDesc.getSheathThickness();
                }
                cable.addExternalMaterials(ecbSheath.getDensity(), ecbSheath.getUnitPrice(), BigDecimal.ONE, sheathThickness);
                List<ExternalMaterial> externalMaterials = cable.getExternalMaterials();
                ExternalMaterial externalMaterial = externalMaterials.get(externalMaterials.size() - 1);
                BigDecimal sheathWeight = externalMaterial.getMaterialWeight();// 护套重量
                BigDecimal sheathMoney = externalMaterial.getMaterialMoney();// 护套金额
                inputStructureVo.setSheathDiameter(externalMaterial.getExternalRadius().multiply(BigDecimal.valueOf(2)));
                inputStructureVo.setSheathWeight(sheathWeight);
                inputStructureVo.setSheathMoney(sheathMoney);
            }
            //单位长度重量
            BigDecimal unitWeight = cable.getUnitWeight();
            //单位长度金额
            BigDecimal unitMoney = cable.getUnitMoney();
            inputStructureVo.setTotalWeight(unitWeight);
            inputStructureVo.setTotalMoney(unitMoney);
            inputStructureVo.setEcuqDesc(ecuqDesc);
        }
        return inputStructureVo;
    }


    // getStructurePassId 通过报价单明细Id获取结构体
    public InputStructureVo getStructurePassId(InputBaseBo bo) {
        Integer ecuqiId = bo.getEcuqiId();
        EcuqInput recordEcuqInput = new EcuqInput();
        recordEcuqInput.setEcuqiId(ecuqiId);
        EcuqInput ecuqInput = ecuqInputService.getObject(recordEcuqInput);
        EcuqDesc recordEcuqDesc = new EcuqDesc();
        recordEcuqDesc.setEcuqiId(ecuqiId);
        EcuqDesc ecuqDesc = ecuqDescService.getObject(recordEcuqDesc);
        Integer ecuqId = ecuqInput.getEcuqId();

        //报价单中获取导体折扣
        EcuQuoted ecuQuoted = ecuQuotedService.getObjectById(ecuqId);
        BigDecimal reduction = ecuQuoted.getReduction();
        if (ObjUtil.isNull(reduction) || reduction.compareTo(BigDecimal.ZERO) <= 0) {
            reduction = BigDecimal.ONE;
        } else {
            reduction = reduction.divide(BigDecimal.valueOf(100D), 16, RoundingMode.HALF_UP);
        }
        InputStructureVo inputStructureVo = computeWeightPrice(ecuqDesc, ecuqInput, reduction);
        //型号信息
        Integer ecusmId = ecuqInput.getEcusmId();
        if (ObjUtil.isNotNull(ecusmId)) {
            EcuSilkModel silkModel = ecuSilkModelService.getObjectById(ecusmId);
            inputStructureVo.setEcuSilkModel(silkModel);
        }
        return inputStructureVo;
    }

    /**
     * 临时改变报价明细信息，改变报价重量跟金额，并不是要改变报价明细单
     *
     * @param bo
     * @return
     */
    public InputStructureVo getStructureTemporary(InputStructBo bo) {
        Integer ecuqiId = bo.getEcuqiId();
        EcuqInput recordEcuqInput = new EcuqInput();
        recordEcuqInput.setEcuqiId(ecuqiId);
        EcuqInput ecuqInput = ecuqInputService.getObject(recordEcuqInput);
        EcuSilkModel silkModel = ecuSilkModelService.getObjectById(ecuqInput.getEcusmId());
        EcuqDesc recordEcuqDesc = new EcuqDesc();
        recordEcuqDesc.setEcuqiId(ecuqiId);
        EcuqDesc ecuqDesc = ecuqDescService.getObject(recordEcuqDesc);
        //导体Id
        Integer ecbucId = bo.getEcbucId();
        ecuqDesc.setEcbucId(ecbucId);
        BigDecimal fireSilkNumber = bo.getFireSilkNumber();
        BigDecimal fireStrand = bo.getFireStrand();
        BigDecimal zeroSilkNumber = bo.getZeroSilkNumber();
        BigDecimal zeroStrand = bo.getZeroStrand();
        ecuqDesc.setFireSilkNumber(fireSilkNumber);
        ecuqDesc.setFireStrand(fireStrand);
        ecuqDesc.setZeroSilkNumber(zeroSilkNumber);
        ecuqDesc.setZeroStrand(zeroStrand);
        if (bo.getEcbumId() != 0) {
            ecuqDesc.setEcbumId(bo.getEcbumId());
            ecuqDesc.setMicatapeThickness(bo.getMicatapeThickness());
        }
        if (bo.getEcbuiId() != 0) {
            ecuqDesc.setEcbuiId(bo.getEcbuiId());
            ecuqDesc.setInsulationFireThickness(bo.getInsulationFireThickness());
            ecuqDesc.setInsulationZeroThickness(bo.getInsulationZeroThickness());
        }
        if (bo.getEcbuinId() != 0) {
            ecuqDesc.setEcbuinId(bo.getEcbuinId());
        }
        //铠装
        if (silkModel.getSteelBand()) {
            if (bo.getEcbub22Id() != 0) {
                ecuqDesc.setEcbub22Id(bo.getEcbub22Id());
                ecuqDesc.setBag22Thickness(bo.getBag22Thickness());
            }
        } else {
            if (bo.getEcbubId() != 0) {
                ecuqDesc.setEcbubId(bo.getEcbubId());
                ecuqDesc.setBagThickness(bo.getBagThickness());
            }
        }
        if (bo.getEcbusbId() != 0) {
            ecuqDesc.setEcbusbId(bo.getEcbusbId());
            ecuqDesc.setSteelbandThickness(bo.getSteelbandThickness());
            ecuqDesc.setSteelbandStorey(bo.getSteelbandStorey());
        }
        //护套
        if (bo.getEcbuSheathId() != 0) {
            ecuqDesc.setEcbuSheathId(bo.getEcbuSheathId());
            ecuqDesc.setSheathThickness(bo.getSheathThickness());
            ecuqDesc.setSheath22Thickness(bo.getSheath22Thickness());
        }
        //护套屏蔽
        if (ecuqDesc.getEcbuShieldId() != 0) {
            ecuqDesc.setEcbuShieldId(bo.getEcbuShieldId());
            ecuqDesc.setShieldThickness(bo.getShieldThickness());
            ecuqDesc.setShieldPercent(bo.getShieldPercent());
        }
        //报价单中获取导体折扣
        EcuQuoted ecuQuoted = ecuQuotedService.getObjectById(ecuqInput.getEcuqId());
        BigDecimal reduction = ecuQuoted.getReduction();
        if (ObjUtil.isNull(reduction) || reduction.compareTo(BigDecimal.ZERO) <= 0) {
            reduction = BigDecimal.ONE;
        } else {
            reduction = reduction.divide(BigDecimal.valueOf(100D), 16, RoundingMode.HALF_UP);
        }
        InputStructureVo compute = computeWeightPrice(ecuqDesc, ecuqInput, reduction);
        compute.setEcuqDesc(ecuqDesc);
        //型号信息
        Integer ecusmId = ecuqInput.getEcusmId();
        if (ObjUtil.isNotNull(ecusmId)) {
            compute.setEcuSilkModel(silkModel);
        }
        return compute;
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
                Integer ecbucId = ecquLevel.getEcbucId();
                EcbuConductor conductor = ecbuConductorService.getObjectById(ecbucId);
                Integer conductorType = conductor.getConductorType();
                //EcSilk recordEcSilk = new EcSilk();
                //recordEcSilk.setEcsId(ecquLevel.getEcsId());
                //EcSilk ecSilk = ecSilkService.getObject(recordEcSilk);
                if (conductorType == 1) {// 铜
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
        String silkName;// 型号系列
        String areaStr;// 规格
        int saleNumber;// 数量
        Integer ecbuluId;// 单位
        List<EcuqInput> list = new ArrayList<>();
        for (List<Object> objects : listob) {
            String storeName = objects.get(0).toString();// 仓库名称
            EcbuStore ecbuStore = ecbuStoreModel.getObjectPassEcCompanyIdAndStoreName(sysUser.getEcCompanyId(), storeName);
            if (ecbuStore == null) {
                list = new ArrayList<>();
                break;
            } else {
                ecbusId = ecbuStore.getEcbusId();
            }
            String levelName = objects.get(1).toString();// 质量等级名称
            EcquLevel ecquLevel = ecquLevelModel.getObjectPassEcCompanyIdAndName(sysUser.getEcCompanyId(), levelName);
            if (ecquLevel == null) {
                list = new ArrayList<>();
                break;
            } else {
                ecqulId = ecquLevel.getEcqulId();
                areaStr = objects.get(3).toString();// 截面
                Boolean areaIsExists = ecuAreaModel.isExistsPassEcqulId(sysUser.getEcCompanyId(), ecqulId, areaStr);
                if (!areaIsExists) {
                    list = new ArrayList<>();
                    break;
                }
            }
            silkName = objects.get(2).toString();// 型号
            List<EcSilk> listSilk = ecSilkServiceModel.getListAllSilkName(sysUser.getEcCompanyId());
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
                EcbulUnit ecbulUnit = ecbulUnitModel.getObjectPassEcCompanyIdAndLengthName(sysUser.getEcCompanyId(), lengthName);
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
            int sortId = 1;
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
                EcuSilkModel silkModel = null;
                String silkModelName = "";
                if (ObjUtil.isNotNull(ecuqInput.getEcusmId()) && ecuqInput.getEcusmId() != 0) {
                    silkModel = ecuSilkModelService.getObjectById(ecuqInput.getEcusmId());
                    silkModelName = silkModel.getAbbreviation();
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
                record.setSilkModelName(silkModelName);
                record.setProfit(profit);
                record.setProfitInput(false);
                record.setBillPercent(billPercent);
                record.setItemDesc("");
                ecuqInputService.insert(record);
                if (record.getEcbusId() != 0
                        && record.getEcqulId() != 0
                        && ObjUtil.isNotNull(record.getEcusmId()) && record.getEcusmId() != 0
                        && !"".equals(record.getSilkName())
                        && !"".equals(record.getAreaStr())) {
                    ecuqDescModel.deal(record, silkModel, sysUser.getEcCompanyId());
                }
                sortId = sortId + 1;
            }
        }
    }

    // 根据丝型号获取默认的质量等级
    public Integer getObjectPassSilkName(InputSilkNameBo bo) {
        Integer ecqulId = bo.getEcqulId();
        String silkName = bo.getSilkName();
        Integer mecqulId = null;
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        Integer ecuId = sysUser.getUserId();
        Integer ecCompanyId = sysUser.getEcCompanyId();
        Integer ecsId = ecSilkServiceModel.getEcsId(ecCompanyId, silkName);
        EcquLevel ecquLevel = ecquLevelModel.getObjectPassEcqulId(ecqulId);
        if (ecquLevel == null || !Objects.equals(ecsId, ecquLevel.getEcusId())) {
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


    /**
     * 根据导体是铜是铝更新税点信息
     *
     * @param ecuqiId
     * @param conductorType
     */
    public void dealBillPercent(Integer ecuqiId, Integer conductorType, Integer ecCompanyId) {
        //EcuqInput recordEcuqInput = new EcuqInput();
        //recordEcuqInput.setEcuqiId(ecuqiId);
        //EcuqInput ecuqInput = ecuqInputService.getObject(recordEcuqInput);
        EcuqInput record = new EcuqInput();
        record.setEcuqiId(ecuqiId);
        //系统税点设置 1 铜 2 铝
        EcduTaxPoint recordEcduTaxPoint = new EcduTaxPoint();
        recordEcduTaxPoint.setEcdtId(conductorType);
        recordEcduTaxPoint.setEcCompanyId(ecCompanyId);
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
