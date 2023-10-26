package org.jeecg.modules.cable.model.userDelivery;

import cn.hutool.core.util.ObjectUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.userDelivery.money.bo.EcbuMoneyBo;
import org.jeecg.modules.cable.controller.userDelivery.money.bo.EcbuMoneyInsertBo;
import org.jeecg.modules.cable.controller.userDelivery.money.bo.EcbuMoneyStartBo;
import org.jeecg.modules.cable.controller.userDelivery.money.vo.MoneyVo;
import org.jeecg.modules.cable.entity.pcc.EcProvince;
import org.jeecg.modules.cable.entity.userDelivery.EcbudMoney;
import org.jeecg.modules.cable.model.efficiency.EcduPccModel;
import org.jeecg.modules.cable.service.pcc.EcProvinceService;
import org.jeecg.modules.cable.service.userDelivery.EcbudMoneyService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class EcbudMoneyModel {
    @Resource
    EcbudMoneyService ecbudMoneyService;
    @Resource
    EcProvinceService ecProvinceService;//省
    @Resource
    EcduPccModel ecduPccModel;//省市县加载

    //load
    public void load(EcbuMoneyBo bo, HttpServletRequest request) {
        Integer ecbudId = bo.getEcbudId();
        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        Integer ecuId = ecUser.getEcuId();
        EcbudMoney record = new EcbudMoney();
        record.setEcbudId(ecbudId);
        List<EcbudMoney> list_price = ecbudMoneyService.getList(record);
        boolean startType = true;
        Integer sortId = 1;
        if (list_price.isEmpty()) {
            record.setEcbudId(ecbudId);
            record.setStartType(startType);
            record.setFirstWeight(0);
            record.setFirstMoney(new BigDecimal("0"));
            record.setContinueMoney(new BigDecimal("0"));
            EcProvince recordProvince = new EcProvince();
            recordProvince.setStartType(true);
            List<EcProvince> list = ecProvinceService.getList(recordProvince);
            for (EcProvince province : list) {
                EcbudMoney ecbudMoney = ecbudMoneyService.getLatestObject(record);
                if (ecbudMoney != null) {
                    sortId = ecbudMoney.getSortId() + 1;
                }
                Integer ecpId = province.getEcpId();
                record.setEcpId(ecpId);
                record.setSortId(sortId);
                record.setProvinceName(province.getProvinceName());
                ecbudMoneyService.insert(record);
            }
            ecduPccModel.load(request, 1, ecuId);
        }
    }

    //getListAndCount
    public MoneyVo getListAndCount(EcbuMoneyBo bo) {
        Integer ecbudId = bo.getEcbudId();
        EcbudMoney record = new EcbudMoney();
        record.setStartType(bo.getStartType());
        record.setEcbudId(ecbudId);
        List<EcbudMoney> list = ecbudMoneyService.getList(record);
        long count = ecbudMoneyService.getCount(record);
        return new MoneyVo(list, count);
    }

    //getObject
    public EcbudMoney getObject(EcbuMoneyBo bo) {

        EcbudMoney record = new EcbudMoney();
        Integer ecbudmId = bo.getEcbudmId();
        record.setEcbudmId(ecbudmId);

        return ecbudMoneyService.getObject(record);
    }

    //deal
    public String deal(EcbuMoneyInsertBo bo, HttpServletRequest request) {

        Integer ecbudmId = bo.getEcbudmId();
        Integer ecbudId = bo.getEcbudId();
        String provinceName = bo.getProvinceName();
        Integer firstWeight = bo.getFirstWeight();
        BigDecimal firstMoney = bo.getFirstMoney();
        BigDecimal continueMoney = bo.getContinueMoney();

        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        Integer ecuId = ecUser.getEcuId();

        EcbudMoney record = new EcbudMoney();
        record.setEcbudmId(ecbudmId);
        record.setEcbudId(ecbudId);
        record.setProvinceName(provinceName);
        EcbudMoney ecbudMoney = ecbudMoneyService.getObjectPassProvinceName(record);
        String msg = "";
        if (ecbudMoney != null) {
            throw new RuntimeException("名称已占用");
        } else {
            if (ObjectUtil.isNull(ecbudmId)) {//插入
                Integer sortId = 1;
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
                ecduPccModel.load(request, 1, ecuId);
                msg = "正常插入数据";
            } else {//更新
                record.setEcbudmId(ecbudmId);
                record.setFirstMoney(firstMoney);
                record.setFirstWeight(firstWeight);
                record.setContinueMoney(continueMoney);
                if (!Objects.equals(provinceName, "")) {
                    record.setProvinceName(provinceName);
                }
                ecbudMoneyService.update(record);
                ecduPccModel.load(request, 1, ecuId);
                msg = "正常更新数据";
            }
        }
        return msg;
    }

    //sort
    public void sort(EcbuMoneyBo bo, HttpServletRequest request) {

        Integer ecbudmId = bo.getEcbudmId();
        Integer sortId = bo.getSortId();

        EcbudMoney record = new EcbudMoney();
        record.setEcbudmId(ecbudmId);
        record.setSortId(sortId);
        ecbudMoneyService.update(record);

        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        Integer ecuId = ecUser.getEcuId();

        ecduPccModel.load(request, 1, ecuId);
    }

    //delete
    public void delete(EcbuMoneyBo bo, HttpServletRequest request) {

        Integer ecbudmId = bo.getEcbudmId();
        EcbudMoney record = new EcbudMoney();
        record.setEcbudmId(ecbudmId);
        EcbudMoney ecbudMoney = ecbudMoneyService.getObject(record);
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
        EcUser ecUser = sysUser.getEcUser();
        Integer ecuId = ecUser.getEcuId();

        ecduPccModel.load(request, 1, ecuId);
    }

    //start
    public String start(EcbuMoneyStartBo bo) {

        Integer ecbudmId = bo.getEcbudmId();
        EcbudMoney record = new EcbudMoney();
        record.setEcbudmId(ecbudmId);
        EcbudMoney ecbudMoney = ecbudMoneyService.getObject(record);
        boolean startType = ecbudMoney.getStartType();
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
    //getPricePassEcbudIdAndAndProvinceNameAndWeight 根据省份和重量获取运费
    public Map<String, Object> getPricePassEcbudIdAndAndProvinceNameAndWeight(Integer ecbudId,
                                                                              String provinceName,
                                                                              BigDecimal weight) {
        Map<String, Object> map = new HashMap<>();
        weight = weight.divide(new BigDecimal("1"), 0, RoundingMode.UP);
        BigDecimal price = new BigDecimal("0");
        BigDecimal unitPrice = new BigDecimal("0");
        EcbudMoney record = new EcbudMoney();
        record.setEcbudId(ecbudId);
        record.setStartType(true);
        record.setProvinceName(provinceName);
        EcbudMoney object = ecbudMoneyService.getObject(record);
        if (object != null) {
            BigDecimal firstWeight = new BigDecimal(object.getFirstWeight());
            if (firstWeight.compareTo(weight) > -1) {
                price = object.getFirstMoney();
            } else {
                BigDecimal continueMoney;
                BigDecimal countContinue = weight
                        .subtract(new BigDecimal(object.getFirstWeight()))
                        .divide(new BigDecimal("1"), 0, RoundingMode.CEILING);
                continueMoney = countContinue.multiply(object.getContinueMoney());
                price = object.getFirstMoney().add(continueMoney);
            }
            unitPrice = price.divide(weight, 6, RoundingMode.HALF_UP);
        }
        map.put("price", price);
        map.put("unitPrice", unitPrice);
        return map;
    }

    /***===数据模型===***/
    //deal
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

    //deletePassEcbudId
    public void deletePassEcbudId(Integer ecbudId) {
        EcbudMoney record = new EcbudMoney();
        record.setEcbudId(ecbudId);
        ecbudMoneyService.delete(record);
    }
}
