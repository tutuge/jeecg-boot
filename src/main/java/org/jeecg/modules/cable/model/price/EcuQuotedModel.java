package org.jeecg.modules.cable.model.price;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ObjectUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.price.quoted.bo.*;
import org.jeecg.modules.cable.controller.price.quoted.vo.QuotedVo;
import org.jeecg.modules.cable.entity.price.EcuQuoted;
import org.jeecg.modules.cable.entity.price.EcuqDesc;
import org.jeecg.modules.cable.entity.user.EcuNotice;
import org.jeecg.modules.cable.entity.userCommon.EcbuPlatformCompany;
import org.jeecg.modules.cable.entity.userCommon.EcbuStore;
import org.jeecg.modules.cable.entity.userDelivery.EcbuDelivery;
import org.jeecg.modules.cable.model.user.EcuNoticeModel;
import org.jeecg.modules.cable.model.userCommon.EcbuStoreModel;
import org.jeecg.modules.cable.service.price.EcuQuotedService;
import org.jeecg.modules.cable.service.price.EcuqDescService;
import org.jeecg.modules.cable.service.userCommon.EcbuPlatformcompanyService;
import org.jeecg.modules.cable.service.userCommon.EcuConductorPriceService;
import org.jeecg.modules.cable.service.userDelivery.EcbuDeliveryService;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.jeecg.modules.cable.tools.SerialNumber;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static org.jeecg.common.enums.UserTypeEnum.USER;

@Service
@Slf4j
public class EcuQuotedModel {
    @Resource
    EcuQuotedService ecuQuotedService;
    @Resource
    EcbuPlatformcompanyService ecbuPlatformcompanyService;
    @Resource
    EcbuDeliveryService ecbuDeliveryService;
    @Resource
    EcbuStoreModel ecbuStoreModel;
    @Resource
    EcuqDescModel ecuqDescModel;
    @Resource
    EcuqDescService ecuqDescService;
    @Resource
    EcuNoticeModel ecuNoticeModel;
    @Resource
    private EcuConductorPriceService ecuConductorPriceService;


    public QuotedVo getListAndCount(EcuQuotedListBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcuQuoted record = new EcuQuoted();
        record.setEcCompanyId(sysUser.getEcCompanyId());
        Integer ecuId = sysUser.getUserId();
        if (Objects.equals(sysUser.getUserType(), USER.getUserType())) {
            record.setEcuId(ecuId);
        }
        BeanUtils.copyProperties(bo, record);
        if (bo.getPageNo() != null) {
            Integer pageNumber = bo.getPageSize();
            Integer startNumber = (bo.getPageNo() - 1) * pageNumber;
            record.setStartNumber(startNumber);
            record.setPageNumber(pageNumber);
        }
        List<EcuQuoted> list = ecuQuotedService.getList(record);
        long count = ecuQuotedService.getCount(record);
        return new QuotedVo(list, count);
    }


    public EcuQuoted getObject(EcuQuotedObjectBo bo) {
        EcuQuoted record = new EcuQuoted();
        if (bo.getEcuqId() != null) {
            Integer ecuqId = bo.getEcuqId();
            record.setEcuqId(ecuqId);
        }
        return ecuQuotedService.getObject(record);
    }


    @Transactional(rollbackFor = Exception.class)
    public String deal(EcuQuotedBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        Integer ecuId = sysUser.getUserId();
        Integer ecCompanyId = sysUser.getEcCompanyId();
        String msg;
        Integer ecuqId = bo.getEcuqId();
        Integer newDeliveryStoreId = bo.getDeliveryStoreId();
        String companyName = "";
        BigDecimal deliveryDivide = BigDecimal.ONE;// 运费除以
        BigDecimal deliveryAdd = BigDecimal.ZERO;// 运费加减
        Integer tradeType = 1;
        if (bo.getTradeType() != null) {
            tradeType = bo.getTradeType();
        }
        //卖价与出厂价
        Integer priceType = 1;
        if (bo.getPriceType() != null) {
            priceType = bo.getPriceType();
        }
        Integer billPercentType = 1;
        if (bo.getBillPercentType() != null) {
            billPercentType = bo.getBillPercentType();
        }
        Integer ecpId = 0;// 默认省份不填写
        if (ObjUtil.isNotNull(bo.getEcpId())) {
            ecpId = bo.getEcpId();
        }
        String provinceName = bo.getProvinceName();
        //导体折扣
        BigDecimal reduction = bo.getReduction();
        EcuQuoted record = new EcuQuoted();
        if (ObjectUtil.isNull(ecuqId) || ecuqId == 0) {// 插入
            String billName = "";// 开票公司
            BigDecimal nbuptMoney = BigDecimal.ZERO;// 不开发票总计
            BigDecimal buptMoney = BigDecimal.ZERO;// 开发票总计
            Integer ecbupId = 0;
            EcbuPlatformCompany recordEcbuPlatformCompany = new EcbuPlatformCompany();
            recordEcbuPlatformCompany.setSortId(1);
            recordEcbuPlatformCompany.setEcCompanyId(ecCompanyId);
            EcbuPlatformCompany ecbuPlatformCompany = ecbuPlatformcompanyService.getObject(recordEcbuPlatformCompany);
            if (ecbuPlatformCompany != null) {
                ecbupId = ecbuPlatformCompany.getEcbupId();// 平台公司ID
            }
            //仓库ID在创建时候不存在话，使用默认仓库
            Integer deliveryStoreId = 0;
            if (newDeliveryStoreId == null) {
                EcbuStore ecbuStore = ecbuStoreModel.getDefaultStore();
                if (ecbuStore != null) {
                    deliveryStoreId = ecbuStore.getEcbusId();
                }
            } else {
                deliveryStoreId = newDeliveryStoreId;
            }
            record.setEcCompanyId(ecCompanyId);
            record.setEcbudId(0);// 默认快递是0
            record.setEcuId(ecuId);
            record.setEccuId(0);// 客户默认是没有的
            record.setCompanyName(companyName);
            record.setDeliveryStoreId(deliveryStoreId);
            record.setDeliveryDivide(deliveryDivide);// 运费除以
            record.setDeliveryAdd(deliveryAdd);// 运费加减
            String serialNumber = SerialNumber.getTradeNumber();// 流水号
            record.setSerialNumber(serialNumber);// 流水号
            record.setTradeType(tradeType);// 交易类型
            record.setName(bo.getName());// 报价单名称
            record.setEcpId(ecpId);// 省ID
            record.setProvinceName(provinceName);// 省名称
            record.setTotalWeight(BigDecimal.ZERO);// 总重
            record.setTotalMoney(BigDecimal.ZERO);// 总金额
            record.setDeliveryMoney(BigDecimal.ZERO);// 快递费
            record.setBillPercentType(billPercentType);// 发票类型
            record.setEcbupId(ecbupId);// 销售平台ID
            record.setBillName(billName);// 开票公司
            record.setAddTime(new Date());
            record.setCompleteTime(new Date());
            record.setNbuptMoney(nbuptMoney);// 不开发票总计
            record.setBuptMoney(buptMoney);// 开发票总计
            record.setUnitPriceAdd(BigDecimal.ZERO);// 单位加价
            record.setAddPricePercent(BigDecimal.ZERO);// 加价百分比
            record.setPriceType(priceType);// 价格类型 1卖价 2 出厂价
            record.setReduction(reduction);
            String totalTitle = "";
            String totalDesc = "";
            EcuNotice ecuNotice = ecuNoticeModel.getObjectDefaultPassEcuId(ecuId);
            if (ecuNotice != null) {
                totalTitle = ecuNotice.getTitle();
                totalDesc = ecuNotice.getContent();
            }
            record.setTotalTitle(totalTitle);
            record.setTotalDesc(totalDesc);
            ecuQuotedService.insert(record);
            msg = "正常插入数据";
        } else {// 更新
            record.setEcuqId(ecuqId);
            EcuQuoted quoted = ecuQuotedService.getObjectById(ecuqId);
            if (ObjUtil.isNull(quoted)) {
                throw new RuntimeException("报价单不存在，请刷新页面重试");
            }
            //根据已有的报价单的仓库，与准备改变的仓库进行比对，如果仓库发生了变化，需要改变快递的id
            Integer oldDeliveryStoreId = quoted.getDeliveryStoreId();
            if (newDeliveryStoreId != null) {// 发货地仓库
                record.setDeliveryStoreId(newDeliveryStoreId);
                //如果编辑的仓库Id跟原来的仓库不一致，那么就对仓库对应的快递方式进行修改
                if (!oldDeliveryStoreId.equals(newDeliveryStoreId)) {
                    EcbuDelivery recordDelivery = new EcbuDelivery();
                    recordDelivery.setStartType(true);
                    recordDelivery.setEcCompanyId(ecCompanyId);
                    recordDelivery.setEcbusId(newDeliveryStoreId);
                    List<EcbuDelivery> listDelivery = ecbuDeliveryService.getList(recordDelivery);
                    if (!listDelivery.isEmpty()) {
                        record.setEcbudId(listDelivery.get(0).getEcbudId());
                    } else {
                        record.setEcbudId(0);
                    }
                }
            }
            if (bo.getEcbupId() != null) {// 销售平台
                Integer ecbupId = bo.getEcbupId();// 销售平台
                record.setEcbupId(ecbupId);
            }
            if (bo.getTradeType() != null) {// 交易类型
                record.setTradeType(tradeType);
            }
            if (bo.getName() != null) {// 报价单名称
                record.setName(bo.getName());
            }
            if (ecpId != 0) {// 省ID
                record.setEcpId(ecpId);
            }
            if (bo.getProvinceName() != null) {// 省名称
                record.setProvinceName(provinceName);
            }

            if (bo.getBillPercentType() != null) {// 发票类型
                record.setBillPercentType(billPercentType);
            }
            if (bo.getEcbudId() != null) {// 快递类型
                Integer ecbudId = bo.getEcbudId();
                record.setEcbudId(ecbudId);
            }
            if (bo.getDeliveryDivide() != null) {// 运费加点
                deliveryDivide = bo.getDeliveryDivide();
                record.setDeliveryDivide(deliveryDivide);
            }
            if (bo.getDeliveryAdd() != null) {// 运费加减
                deliveryAdd = bo.getDeliveryAdd();
                record.setDeliveryAdd(deliveryAdd);
            }
            if (bo.getUnitPriceAdd() != null) {// 单位加价
                BigDecimal unitPriceAdd = bo.getUnitPriceAdd();
                record.setUnitPriceAdd(unitPriceAdd);
            }
            if (bo.getAddPricePercent() != null) {// 加价百分比
                BigDecimal addPricePercent = bo.getAddPricePercent();
                record.setAddPricePercent(addPricePercent);
            }
            if (bo.getCompanyName() != null) {
                companyName = bo.getCompanyName();
                record.setCompanyName(companyName);
            }
            record.setPriceType(priceType);// 价格类型 1卖价 2 出厂价
            record.setReduction(reduction);
            ecuQuotedService.update(record);
            //如果是修改的话，查询下对应报价单下面的明细，给导体Id一致的报价单明细中的导体价格修改了
            Integer ecbucId = bo.getEcbucId();
            BigDecimal cunitPrice = bo.getCunitPrice();
            if (ObjUtil.isNotNull(ecbucId) && ecbucId != 0 && ObjUtil.isNotNull(cunitPrice)
                    && BigDecimal.ZERO.compareTo(cunitPrice) < 0) {
                //创建或者更新报价单上最顶部的导体价格
                ecuConductorPriceService.saveOrUpdateByEcuqId(ecuqId, ecbucId, cunitPrice);
                //更新一次现有的所有报价单明细
                ecuqDescService.updateConductorPriceById(ecuqId, ecbucId, cunitPrice);
            }
            msg = "正常更新数据";
        }
        return msg;
    }

    public EcuQuoted getLatestObject() {
        //todo 此处如果是平台管理员的话，是否要将不分公司的最新报价单传回去？
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        Integer ecuId = sysUser.getUserId();
        EcuQuoted record = new EcuQuoted();
        record.setEcuId(ecuId);
        EcuQuoted latestObject = ecuQuotedService.getLatestObject(record);
        if (ObjUtil.isNull(latestObject)) {
            EcuQuotedBo bo = new EcuQuotedBo();
            bo.setEcuqId(0);
            deal(bo);
        }
        latestObject = ecuQuotedService.getLatestObject(record);
        return latestObject;
    }

    // dealMoneyPassInput 通过手输的方式改变总额
    public void dealMoneyPassInput(QuotedDealMoneyPassBo bo) {
        Integer ecuqId = bo.getEcuqId();
        BigDecimal nbuptMoney = BigDecimal.ZERO;
        BigDecimal buptMoney = BigDecimal.ZERO;
        EcuQuoted record = new EcuQuoted();
        record.setEcuqId(ecuqId);
        if (bo.getNbuptMoney() != null) {
            nbuptMoney = bo.getNbuptMoney();
            record.setNbuptMoney(nbuptMoney);
        }
        if (bo.getBuptMoney() != null) {
            buptMoney = bo.getBuptMoney();
            record.setBuptMoney(buptMoney);
        }
        // System.out.println(CommonFunction.getGson().toJson(record));
        // 更新desc
        ecuqDescModel.dealMoneyPassQuoted(ecuqId, buptMoney, nbuptMoney);
        // 更新本表
        ecuQuotedService.update(record);
    }

    // complete 提交报价单，成交
    public void dealComplete(QuotedDealCompleteBo bo) {
        Integer ecuqId = bo.getEcuqId();
        BigDecimal totalMoney = bo.getTotalMoney();
        // 总重量和总金额
        EcuqDesc recordEcuqDesc = new EcuqDesc();
        recordEcuqDesc.setEcuqdId(ecuqId);
        List<EcuqDesc> listDesc = ecuqDescService.getList(recordEcuqDesc);
        BigDecimal totalWeight = BigDecimal.ZERO;
        for (EcuqDesc ecuqDesc : listDesc) {
            totalWeight = totalWeight.add(ecuqDesc.getWeight());
        }
        EcuQuoted record = new EcuQuoted();
        record.setEcuqId(ecuqId);
        record.setTotalWeight(totalWeight);
        record.setTotalMoney(totalMoney);
        record.setTradeType(2);// 已成交
        ecuQuotedService.update(record);
    }

    // dealTotalDesc
    public void dealTotalDesc(QuotedTotalDealBo bo) {
        Integer ecuqId = bo.getEcuqId();
        String totalTitle = bo.getTotalTitle();
        String totalDesc = bo.getTotalDesc();
        EcuQuoted record = new EcuQuoted();
        record.setEcuqId(ecuqId);
        record.setTotalTitle(totalTitle);
        record.setTotalDesc(totalDesc);
        ecuQuotedService.update(record);
    }


    // cleanMoney 清除金额
    public void cleanMoney(Integer ecuqId) {
        BigDecimal nbuptMoney = BigDecimal.ZERO;
        BigDecimal buptMoney = BigDecimal.ZERO;
        BigDecimal deliveryMoney = BigDecimal.ZERO;
        EcuQuoted record = new EcuQuoted();
        record.setEcuqId(ecuqId);
        record.setNbuptMoney(nbuptMoney);
        record.setBuptMoney(buptMoney);
        record.setDeliveryMoney(deliveryMoney);
        // System.out.println("cleanMoney + " + CommonFunction.getGson().toJson(record));
        ecuQuotedService.update(record);
    }

    // dealEccuId 更新关联客户信息
    public void dealEccuId(Integer ecuqId, Integer eccuId) {
        EcuQuoted record = new EcuQuoted();
        record.setEcuqId(ecuqId);
        record.setEccuId(eccuId);
        ecuQuotedService.update(record);
    }

    public List<EcuQuoted> queryByCustomerId(Integer eccuId) {
        EcuQuoted quoted = new EcuQuoted();
        quoted.setEccuId(eccuId);
        return ecuQuotedService.getList(quoted);
    }

    public EcuQuoted getById(Integer ecuqId) {
        EcuQuoted ecuQuoted = ecuQuotedService.getObjectById(ecuqId);
        if (ObjUtil.isNull(ecuQuoted)) {
            throw new RuntimeException("未查询到当前报价单");
        }
        return ecuQuoted;
    }
}
