package org.jeecg.modules.cable.model.price;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ObjectUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.price.quoted.bo.*;
import org.jeecg.modules.cable.controller.price.quoted.vo.QuotedVo;
import org.jeecg.modules.cable.entity.pcc.EcProvince;
import org.jeecg.modules.cable.entity.price.EcuQuoted;
import org.jeecg.modules.cable.entity.price.EcuqDesc;
import org.jeecg.modules.cable.entity.user.EcuNotice;
import org.jeecg.modules.cable.entity.userCommon.EcbuPcompany;
import org.jeecg.modules.cable.entity.userCommon.EcbuStore;
import org.jeecg.modules.cable.model.user.EcuNoticeModel;
import org.jeecg.modules.cable.model.userCommon.EcbuStoreModel;
import org.jeecg.modules.cable.service.pcc.EcProvinceService;
import org.jeecg.modules.cable.service.price.EcuQuotedService;
import org.jeecg.modules.cable.service.price.EcuqDescService;
import org.jeecg.modules.cable.service.userCommon.EcbuPcompanyService;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.jeecg.modules.cable.tools.SerialNumber;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import static org.jeecg.common.enums.UserTypeEnum.USER;

@Service
@Slf4j
public class EcuQuotedModel {
    @Resource
    EcuQuotedService ecuQuotedService;
    @Resource
    EcbuPcompanyService ecbuPcompanyService;
    @Resource
    EcProvinceService ecProvinceService;
    @Resource
    EcbuStoreModel ecbuStoreModel;
    @Resource
    EcuqDescModel ecuqDescModel;
    @Resource
    EcuqDescService ecuqDescService;
    @Resource
    EcuNoticeModel ecuNoticeModel;


    public QuotedVo getListAndCount(EcuQuotedListBo bo) {
        // 获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcuQuoted record = new EcuQuoted();
        record.setEcCompanyId(sysUser.getEcCompanyId());
        Integer ecuId = sysUser.getUserId();
        if (Objects.equals(sysUser.getUserType(), USER.getUserType())) {
            record.setEcuId(ecuId);
        }
        BeanUtils.copyProperties(bo, record);
        if (bo.getPageNum() != null) {
            Integer pageNumber = bo.getPageSize();
            Integer startNumber = (bo.getPageNum() - 1) * pageNumber;
            record.setStartNumber(startNumber);
            record.setPageNumber(pageNumber);
        }
        log.info("record + " + CommonFunction.getGson().toJson(record));
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
        String msg;
        Integer ecuqId = bo.getEcuqId();
        String serialNumber = SerialNumber.getTradeNumber();// 流水号
        String companyName = "";
        BigDecimal deliveryDivide = BigDecimal.ONE;// 运费除以
        BigDecimal deliveryAdd = BigDecimal.ZERO;// 运费加减
        Integer tradeType = 1;
        if (bo.getTradeType() != null) {
            tradeType = bo.getTradeType();
        }
        Integer billPercentType = 1;
        if (bo.getBillPercentType() != null) {
            billPercentType = bo.getBillPercentType();
        }

        Integer deliveryStoreId = 0;
        if (bo.getDeliveryStoreId() == null) {
            EcbuStore ecbuStore = ecbuStoreModel.getDefaultStore();
            if (ecbuStore != null) {
                deliveryStoreId = ecbuStore.getEcbusId();
            }
        } else {
            deliveryStoreId = bo.getDeliveryStoreId();
        }
        Integer ecpId = 0;// 默认省份不填写
        String provinceName = "";
        if (bo.getProvinceName() != null) {
            provinceName = bo.getProvinceName();
            EcProvince recordProvince = new EcProvince();
            recordProvince.setProvinceName(provinceName);
            EcProvince province = ecProvinceService.getObject(recordProvince);
            if (province != null) {
                ecpId = province.getEcpId();
                provinceName = province.getProvinceName();
            }
        }
        BigDecimal totalWeight = BigDecimal.ZERO;// 总重量
        BigDecimal totalMoney = BigDecimal.ZERO;// 总金额
        BigDecimal deliveryMoney = BigDecimal.ZERO;// 快递金额
        EcuQuoted record = new EcuQuoted();
        if (ObjectUtil.isNull(ecuqId)) {// 插入
            String billName = "";// 开票公司
            BigDecimal nbuptMoney = BigDecimal.ZERO;// 不开发票总计
            BigDecimal buptMoney = BigDecimal.ZERO;// 开发票总计
            Integer ecbupId = 0;
            EcbuPcompany recordEcbuPcompany = new EcbuPcompany();
            recordEcbuPcompany.setSortId(1);
            EcbuPcompany ecbuPcompany = ecbuPcompanyService.getObject(recordEcbuPcompany);
            if (ecbuPcompany != null) {
                ecbupId = ecbuPcompany.getEcbupId();// 平台公司ID
            }
            record.setEcCompanyId(sysUser.getEcCompanyId());
            record.setEcbudId(0);// 默认快递是0
            record.setEcuId(ecuId);
            record.setEccuId(0);// 客户默认是没有的
            record.setCompanyName(companyName);
            record.setDeliveryStoreId(deliveryStoreId);
            record.setDeliveryDivide(deliveryDivide);// 运费除以
            record.setDeliveryAdd(deliveryAdd);// 运费加减
            record.setSerialNumber(serialNumber);// 流水号
            record.setTradeType(tradeType);// 交易类型
            record.setName(bo.getName());// 报价单名称
            record.setEcpId(ecpId);// 省ID
            record.setProvinceName(provinceName);// 省名称
            record.setTotalWeight(totalWeight);// 总重
            record.setTotalMoney(totalMoney);// 总金额
            record.setDeliveryMoney(deliveryMoney);// 快递费
            record.setBillPercentType(billPercentType);// 发票类型
            record.setEcbupId(ecbupId);// 销售平台ID
            record.setBillName(billName);// 开票公司
            record.setAddTime(System.currentTimeMillis());
            record.setCompleteTime(System.currentTimeMillis());
            record.setNbuptMoney(nbuptMoney);// 不开发票总计
            record.setBuptMoney(buptMoney);// 开发票总计
            record.setUnitPriceAdd(new BigDecimal("0"));// 单位加价
            record.setAddPricePercent(new BigDecimal("0"));// 加价百分比
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
            if (bo.getDeliveryStoreId() != null) {// 发货地
                record.setDeliveryStoreId(deliveryStoreId);
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
            // log.info(CommonFunction.getGson().toJson(record));
            ecuQuotedService.update(record);
            // 更新客户及公司信息
            msg = "正常更新数据";
        }
        return msg;
    }


    public EcuQuoted getLatestObject() {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        Integer ecuId = sysUser.getUserId();
        EcuQuoted record = new EcuQuoted();
        record.setEcuId(ecuId);
        return ecuQuotedService.getLatestObject(record);
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

    /***===数据模型===***/
    // 修改总额
    public void dealMoney(Integer ecuqId, BigDecimal nbuptMoney, BigDecimal buptMoney) {
        EcuQuoted record = new EcuQuoted();
        record.setEcuqId(ecuqId);
        record.setNbuptMoney(nbuptMoney);
        record.setBuptMoney(buptMoney);
        ecuQuotedService.update(record);
    }

    // dealDeliveryMoney 修改运费
    public void dealDeliveryMoney(Integer ecuqId, BigDecimal deliveryMoney) {
        EcuQuoted record = new EcuQuoted();
        record.setEcuqId(ecuqId);
        record.setDeliveryMoney(deliveryMoney);
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

    // dealTotalWeight
    public void dealTotalWeight(Integer ecuqId, BigDecimal totalWeight) {
        EcuQuoted record = new EcuQuoted();
        record.setEcuqId(ecuqId);
        record.setTotalWeight(totalWeight);
        ecuQuotedService.update(record);
    }

    // dealEccuId 更新关联客户信息
    public void dealEccuId(Integer ecuqId, Integer eccuId) {
        EcuQuoted record = new EcuQuoted();
        record.setEcuqId(ecuqId);
        record.setEccuId(eccuId);
        log.info("record + " + CommonFunction.getGson().toJson(record));
        ecuQuotedService.update(record);
    }

    public List<EcuQuoted> queryByCustomerId(Integer eccuId) {
        EcuQuoted quoted = new EcuQuoted();
        quoted.setEccuId(eccuId);
        return ecuQuotedService.getList(quoted);
    }

    public EcuQuoted getById(Integer ecuqId) {
        EcuQuoted ecuQuoted = ecuQuotedService.getById(ecuqId);
        if (ObjUtil.isNull(ecuQuoted)) {
            throw new RuntimeException("未查询到当前报价单");
        }
        return ecuQuoted;
    }
}
