package org.jeecg.modules.cable.model.userDelivery;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ObjectUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.userDelivery.money.bo.*;
import org.jeecg.modules.cable.controller.userDelivery.money.vo.MoneyVo;
import org.jeecg.modules.cable.domain.DeliveryPriceBo;
import org.jeecg.modules.cable.entity.pcc.EcProvince;
import org.jeecg.modules.cable.entity.userDelivery.EcbudMoney;
import org.jeecg.modules.cable.model.efficiency.EcduPccModel;
import org.jeecg.modules.cable.service.pcc.EcProvinceService;
import org.jeecg.modules.cable.service.userDelivery.EcbudMoneyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class EcbudMoneyModel {
    @Resource
    EcbudMoneyService ecbudMoneyService;
    @Resource
    EcProvinceService ecProvinceService;// 省
    @Resource
    EcduPccModel ecduPccModel;// 省市县加载

    // load
    public void load(Integer ecbudId) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcbudMoney record = new EcbudMoney();
        record.setEcbudId(ecbudId);
        List<EcbudMoney> listPrice = ecbudMoneyService.getList(record);
        Boolean startType = true;
        if (listPrice.isEmpty()) {
            record.setEcbudId(ecbudId);
            record.setStartType(startType);
            record.setFirstWeight(0);
            record.setFirstMoney(BigDecimal.ZERO);
            record.setContinueMoney(BigDecimal.ZERO);
            EcProvince recordProvince = new EcProvince();
            recordProvince.setStartType(true);
            List<EcProvince> list = ecProvinceService.getList(recordProvince);
            EcbudMoney ecbudMoney = ecbudMoneyService.getLatestObject(record);
            int sortId = 1;
            if (ecbudMoney != null) {
                sortId = ecbudMoney.getSortId() + 1;
            }
            for (EcProvince province : list) {
                Integer ecpId = province.getEcpId();
                record.setEcpId(ecpId);
                record.setSortId(sortId);
                record.setProvinceName(province.getProvinceName());
                ecbudMoneyService.insert(record);
                sortId = sortId + 1;
            }
            ecduPccModel.load(1, sysUser.getEcCompanyId());
        }
    }


    public MoneyVo getListAndCount(EcbuMoneyBo bo) {
        Integer ecbudId = bo.getEcbudId();
        // 每次查询先确认下是否是已经初始化过了
        load(ecbudId);
        EcbudMoney record = new EcbudMoney();
        record.setStartType(bo.getStartType());
        record.setEcbudId(ecbudId);
        List<EcbudMoney> list = ecbudMoneyService.getList(record);
        long count = ecbudMoneyService.getCount(record);
        return new MoneyVo(list, count);
    }


    public EcbudMoney getObject(EcbuMoneyBaseBo bo) {
        EcbudMoney record = new EcbudMoney();
        Integer ecbudmId = bo.getEcbudmId();
        record.setEcbudmId(ecbudmId);
        return ecbudMoneyService.getObject(record);
    }


    @Transactional(rollbackFor = Exception.class)
    public String deal(EcbuMoneyInsertBo bo) {
        Integer ecbudmId = bo.getEcbudmId();
        Integer ecbudId = bo.getEcbudId();
        String provinceName = bo.getProvinceName();
        Integer firstWeight = bo.getFirstWeight();
        BigDecimal firstMoney = bo.getFirstMoney();
        BigDecimal continueMoney = bo.getContinueMoney();

        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcbudMoney record = new EcbudMoney();
        record.setEcbudmId(ecbudmId);
        record.setEcbudId(ecbudId);
        record.setProvinceName(provinceName);
        EcbudMoney ecbudMoney = ecbudMoneyService.getObjectPassProvinceName(record);
        String msg = "";
        if (ecbudMoney != null) {
            throw new RuntimeException("名称已占用");
        }
        if (ObjectUtil.isNull(ecbudmId)) {// 插入
            int sortId = 1;
            ecbudMoney = ecbudMoneyService.getLatestObject(record);
            if (ecbudMoney != null) {
                sortId = ecbudMoney.getSortId() + 1;
            }
            record = new EcbudMoney();
            record.setEcbudId(ecbudId);
            record.setSortId(sortId);
            record.setStartType(true);
            record.setEcpId(0);
            record.setProvinceName(provinceName);
            record.setFirstMoney(firstMoney);
            record.setFirstWeight(firstWeight);
            record.setContinueMoney(continueMoney);
            ecbudMoneyService.insert(record);
            msg = "正常插入数据";
        } else {// 更新
            record.setEcbudmId(ecbudmId);
            record.setFirstMoney(firstMoney);
            record.setFirstWeight(firstWeight);
            record.setContinueMoney(continueMoney);
            if (!Objects.equals(provinceName, "")) {
                record.setProvinceName(provinceName);
            }
            ecbudMoneyService.update(record);
            msg = "正常更新数据";
        }
        ecduPccModel.load(1, sysUser.getEcCompanyId());
        return msg;
    }


    public void sort(List<EcbuMoneySortBo> bos) {
        for (EcbuMoneySortBo bo : bos) {
            Integer ecbudmId = bo.getEcbudmId();
            Integer sortId = bo.getSortId();
            EcbudMoney record = new EcbudMoney();
            record.setEcbudmId(ecbudmId);
            record.setSortId(sortId);
            ecbudMoneyService.update(record);
        }
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        ecduPccModel.load(1, sysUser.getEcCompanyId());
    }

    @Transactional(rollbackFor = Exception.class)
    public String weight(List<EcbuMoneyWeightBo> bos) {
        for (EcbuMoneyWeightBo bo : bos) {
            Integer ecbudmId = bo.getEcbudmId();
            Integer firstWeight = bo.getFirstWeight();
            EcbudMoney record = new EcbudMoney();
            record.setEcbudmId(ecbudmId);
            record.setFirstWeight(firstWeight);
            ecbudMoneyService.update(record);

        }
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        ecduPccModel.load(1, sysUser.getEcCompanyId());
        return "修改成功";
    }


    @Transactional(rollbackFor = Exception.class)
    public void delete(EcbuMoneyBaseBo bo) {
        Integer ecbudmId = bo.getEcbudmId();
        EcbudMoney record = new EcbudMoney();
        record.setEcbudmId(ecbudmId);
        EcbudMoney ecbudMoney = ecbudMoneyService.getObject(record);
        if (ObjUtil.isNull(ecbudMoney)) {
            throw new RuntimeException("此行记录不存在，无法操作");
        }
        Integer sortId = ecbudMoney.getSortId();
        record = new EcbudMoney();
        record.setSortId(sortId);
        record.setEcbudId(ecbudMoney.getEcbudId());
        List<EcbudMoney> list = ecbudMoneyService.getListGreaterThanSortId(record);
        Integer ecbudm_id;
        for (EcbudMoney ecbud_money : list) {
            ecbudm_id = ecbud_money.getEcbudmId();
            sortId = ecbud_money.getSortId() - 1;
            record.setEcbudmId(ecbudm_id);
            record.setSortId(sortId);
            ecbudMoneyService.update(record);
        }
        record = new EcbudMoney();
        record.setEcbudmId(ecbudmId);
        ecbudMoneyService.delete(record);

        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        ecduPccModel.load(1, sysUser.getEcCompanyId());
    }


    public String start(EcbuMoneyBaseBo bo) {
        Integer ecbudmId = bo.getEcbudmId();
        EcbudMoney record = new EcbudMoney();
        record.setEcbudmId(ecbudmId);
        EcbudMoney ecbudMoney = ecbudMoneyService.getObject(record);
        Boolean startType = ecbudMoney.getStartType();
        String msg = "";
        if (!startType) {
            startType = true;

            msg = "数据启用成功";
        } else {
            startType = false;
            msg = "数据禁用成功";
        }
        record = new EcbudMoney();
        record.setEcbudmId(ecbudMoney.getEcbudmId());
        record.setStartType(startType);
        ecbudMoneyService.update(record);
        return msg;
    }

    /***===数据模型===***/
    /**
     * 根据省份和重量获取运费
     *
     * @param ecbudId    ecbu_delivery主键ID
     * @param provinceId 省份ID
     * @param weight     重量
     * @return
     */
    public DeliveryPriceBo getPricePassEcbudIdAndProvinceIdAndWeight(Integer ecbudId, Integer provinceId, BigDecimal weight) {
        weight = weight.divide(BigDecimal.ONE, 0, RoundingMode.UP);
        BigDecimal price = BigDecimal.ZERO;
        BigDecimal unitPrice = BigDecimal.ZERO;
        EcbudMoney record = new EcbudMoney();
        record.setEcbudId(ecbudId);
        record.setStartType(true);
        record.setEcpId(provinceId);
        EcbudMoney object = ecbudMoneyService.getObject(record);
        if (object != null) {
            BigDecimal firstWeight = new BigDecimal(object.getFirstWeight());
            //比首重小，取首重价格
            if (firstWeight.compareTo(weight) > -1) {
                price = object.getFirstMoney();
            } else {
                //否则取续重价格
                BigDecimal countContinue = weight.subtract(new BigDecimal(object.getFirstWeight()))
                        .divide(BigDecimal.ONE, 0, RoundingMode.CEILING);
                BigDecimal continueMoney = countContinue.multiply(object.getContinueMoney());
                price = object.getFirstMoney().add(continueMoney);
            }
            unitPrice = price.divide(weight, 16, RoundingMode.HALF_UP);
        }
        return new DeliveryPriceBo(price, unitPrice);
    }

    /***===数据模型===***/

    @Transactional(rollbackFor = Exception.class)
    public void deal(EcbudMoney record) {
        EcbudMoney recordEcbudMoney = new EcbudMoney();
        recordEcbudMoney.setEcbudId(record.getEcbudId());
        recordEcbudMoney.setEcpId(record.getEcpId());
        recordEcbudMoney.setProvinceName(record.getProvinceName());
        EcbudMoney ecbudMoney = ecbudMoneyService.getObject(recordEcbudMoney);
        if (ecbudMoney != null) {
            ecbudMoneyService.update(record);
        } else {
            ecbudMoneyService.insert(record);
        }
    }

    // deletePassEcbudId
    public void deletePassEcbudId(Integer ecbudId) {
        EcbudMoney record = new EcbudMoney();
        record.setEcbudId(ecbudId);
        ecbudMoneyService.delete(record);
    }
}
