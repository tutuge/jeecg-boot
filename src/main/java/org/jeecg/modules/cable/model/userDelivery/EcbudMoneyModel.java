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
import org.jeecg.modules.cable.entity.systemPcc.EcProvince;
import org.jeecg.modules.cable.entity.userDelivery.EcbudMoney;
import org.jeecg.modules.cable.entity.userPcc.EcuProvince;
import org.jeecg.modules.cable.service.systemPcc.EcProvinceService;
import org.jeecg.modules.cable.service.userDelivery.EcbudMoneyService;
import org.jeecg.modules.cable.service.userPcc.EcuProvinceService;
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
    private EcuProvinceService ecuProvinceService;

    public void load(Integer ecbudId) {
        EcbudMoney record = new EcbudMoney();
        record.setEcbudId(ecbudId);
        List<EcbudMoney> listPrice = ecbudMoneyService.getList(record);
        if (listPrice.isEmpty()) {
            EcProvince recordProvince = new EcProvince();
            recordProvince.setStartType(true);
            List<EcProvince> list = ecProvinceService.getList(recordProvince);
            EcbudMoney ecbudMoney = ecbudMoneyService.getLatestObject(record);
            int sortId = 1;
            if (ecbudMoney != null) {
                sortId = ecbudMoney.getSortId() + 1;
            }
            for (EcProvince province : list) {
                record = new EcbudMoney();
                record.setEcbudId(ecbudId);
                record.setStartType(true);
                record.setFirstWeight(0);
                record.setFirstMoney(BigDecimal.ZERO);
                record.setContinueMoney(BigDecimal.ZERO);
                Integer ecpId = province.getEcpId();
                record.setEcpId(ecpId);
                record.setSortId(sortId);
                record.setProvinceName(province.getProvinceName());
                ecbudMoneyService.insert(record);
                sortId = sortId + 1;
            }
        }
    }


    @Transactional(rollbackFor = Exception.class)
    public MoneyVo getListAndCount(EcbuMoneyBo bo) {
        Integer ecbudId = bo.getEcbudId();
        EcbudMoney record = new EcbudMoney();
        record.setStartType(bo.getStartType());
        record.setEcbudId(ecbudId);
        List<EcbudMoney> list = ecbudMoneyService.getList(record);
        long count = ecbudMoneyService.getCount(record);
        if (count == 0L) {
            // 每次查询先确认下是否是已经初始化过了
            load(ecbudId);
        }
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
            throw new RuntimeException("省级名称已占用");
        }
        if (ObjectUtil.isNull(ecbudmId)) {// 插入
            int sortId = 1;
            ecbudMoney = ecbudMoneyService.getLatestObject(record);
            if (ecbudMoney != null) {
                sortId = ecbudMoney.getSortId() + 1;
            }
            //先插入省级信息
            EcuProvince province = ecuProvinceService.insertProvinceName(provinceName, sysUser.getEcCompanyId());
            record = new EcbudMoney();
            record.setEcbudId(ecbudId);
            record.setSortId(sortId);
            record.setStartType(true);
            record.setEcpId(province.getEcpId());
            record.setProvinceName(provinceName);
            record.setFirstMoney(firstMoney);
            record.setFirstWeight(firstWeight);
            record.setContinueMoney(continueMoney);
            ecbudMoneyService.insert(record);
            msg = "正常插入数据";
        } else {// 更新
            EcbudMoney money = new EcbudMoney();
            money.setEcbudId(ecbudmId);
            EcbudMoney object = ecbudMoneyService.getObject(money);
            record.setEcbudmId(ecbudmId);
            record.setFirstMoney(firstMoney);
            record.setFirstWeight(firstWeight);
            record.setContinueMoney(continueMoney);
            if (!Objects.equals(provinceName, "")) {
                record.setProvinceName(provinceName);
            }
            ecuProvinceService.updateProvinceName(provinceName, object.getEcpId(), sysUser.getEcCompanyId());
            ecbudMoneyService.update(record);
            msg = "正常更新数据";
        }
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
        Integer ecpId = ecbudMoney.getEcpId();
        ecbudMoneyService.reduceSort(ecbudMoney.getEcbudId(),sortId);
        record = new EcbudMoney();
        record.setEcbudmId(ecbudmId);
        ecbudMoneyService.delete(record);
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        ecuProvinceService.deleteByEcpId(ecpId, sysUser.getEcCompanyId());
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
        EcbudMoney object = ecbudMoneyService.getPricePassEcbudIdAndProvinceIdAndWeight(ecbudId, true, provinceId);
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

    //根据快递ID进行删除
    public void deletePassEcbudId(Integer ecbudId) {
        EcbudMoney record = new EcbudMoney();
        record.setEcbudId(ecbudId);
        ecbudMoneyService.delete(record);
    }
}
