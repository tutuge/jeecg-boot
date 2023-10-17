package org.jeecg.modules.cable.model.userDelivery;

import org.jeecg.modules.cable.entity.pcc.EcProvince;
import org.jeecg.modules.cable.entity.userDelivery.EcbudMoney;
import org.jeecg.modules.cable.model.efficiency.EcduPccModel;
import org.jeecg.modules.cable.service.pcc.EcProvinceService;
import org.jeecg.modules.cable.service.userDelivery.EcbudMoneyService;
import org.jeecg.modules.cable.tools.CommonFunction;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
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
    public void load(HttpServletRequest request) {
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        int ecbudId = Integer.parseInt(request.getParameter("ecbudId"));
        EcbudMoney record = new EcbudMoney();
        record.setEcbudId(ecbudId);
        List<EcbudMoney> list_price = ecbudMoneyService.getList(record);
        boolean startType = true;
        int sortId = 1;
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
                int ecpId = province.getEcpId();
                record.setEcpId(ecpId);
                record.setSortId(sortId);
                record.setProvinceName(province.getProvinceName());
                ecbudMoneyService.insert(record);
            }
            ecduPccModel.load(request, 1, ecuId);
        }
    }

    //getListAndCount
    public Map<String, Object> getListAndCount(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        int status;
        String code;
        String msg;
        int ecbudId = Integer.parseInt(request.getParameter("ecbudId"));
        EcbudMoney record = new EcbudMoney();
record.setStartType(bo.getStartType());
        record.setEcbudId(ecbudId);
        List<EcbudMoney> list = ecbudMoneyService.getList(record);
        long count = ecbudMoneyService.getCount(record);

    }

    //getObject
    public Map<String, Object> getObject(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        int status;
        String code;
        String msg;
        EcbudMoney record = new EcbudMoney();
        if (request.getParameter("ecbudmId") != null) {
            int ecbudmId = Integer.parseInt(request.getParameter("ecbudmId"));
            record.setEcbudmId(ecbudmId);
        }
        map.put("object", ecbudMoneyService.getObject(record));
        status = 3;//正常获取数据
        code = "200";
        msg = "正常获取数据";
        CommonFunction.getCommonMap(map, status, code, msg);
        return map;
    }

    //deal
    public Map<String, Object> deal(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        int status;
        String code;
        String msg;
        int ecbudmId = Integer.parseInt(request.getParameter("ecbudmId"));
        int ecbudId = Integer.parseInt(request.getParameter("ecbudId"));
        String provinceName = request.getParameter("provinceName");
        int firstWeight = Integer.parseInt(request.getParameter("firstWeight"));
        BigDecimal firstMoney = new BigDecimal(request.getParameter("firstMoney"));
        BigDecimal continueMoney = new BigDecimal(request.getParameter("continueMoney"));
        EcbudMoney record = new EcbudMoney();
        record.setEcbudmId(ecbudmId);
        record.setEcbudId(ecbudId);
        record.setProvinceName(provinceName);
        EcbudMoney ecbudMoney = ecbudMoneyService.getObjectPassProvinceName(record);
        if (ecbudMoney != null) {
            status = 3;//名称已占用
            code = "103";
            msg = "名称已占用";
        } else {
            if (ecbudmId == 0) {//插入
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
                int ecuId = Integer.parseInt(request.getParameter("ecuId"));
                ecduPccModel.load(request, 1, ecuId);
                status = 4;//正常插入数据
                code = "200";
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
                int ecuId = Integer.parseInt(request.getParameter("ecuId"));
                ecduPccModel.load(request, 1, ecuId);
                status = 5;//正常更新数据
                code = "201";
                msg = "正常更新数据";
            }
        }
        CommonFunction.getCommonMap(map, status, code, msg);
        return map;
    }

    //sort
    public Map<String, Object> sort(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        int status;
        String code;
        String msg;
        int ecbudmId = Integer.parseInt(request.getParameter("ecbudmId"));
        int sortId = Integer.parseInt(request.getParameter("sortId"));
        EcbudMoney record = new EcbudMoney();
        record.setEcbudmId(ecbudmId);
        record.setSortId(sortId);
        ecbudMoneyService.update(record);
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        ecduPccModel.load(request, 1, ecuId);
        status = 3;//数据操作成功
        code = "200";
        msg = "数据操作成功";
        CommonFunction.getCommonMap(map, status, code, msg);
        return map;
    }

    //delete
    public Map<String, Object> delete(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        int status;
        String code;
        String msg;
        int ecbudmId = Integer.parseInt(request.getParameter("ecbudmId"));
        EcbudMoney record = new EcbudMoney();
        record.setEcbudmId(ecbudmId);
        EcbudMoney ecbudMoney = ecbudMoneyService.getObject(record);
        int sortId = ecbudMoney.getSortId();
        record = new EcbudMoney();
        record.setSortId(sortId);
        record.setEcbudId(ecbudMoney.getEcbudId());
        List<EcbudMoney> list = ecbudMoneyService.getListGreaterThanSortId(record);
        int ecbudm_id;
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
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        ecduPccModel.load(request, 1, ecuId);
        status = 3;//数据操作成功
        code = "200";
        msg = "数据操作成功";
        CommonFunction.getCommonMap(map, status, code, msg);
        return map;
    }

    //start
    public Map<String, Object> start(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        int status;
        String code;
        String msg;
        int ecbudmId = Integer.parseInt(request.getParameter("ecbudmId"));
        EcbudMoney record = new EcbudMoney();
        record.setEcbudmId(ecbudmId);
        EcbudMoney ecbudMoney = ecbudMoneyService.getObject(record);
        boolean startType = ecbudMoney.getStartType();
        if (!startType) {
            startType = true;
            status = 3;
            code = "200";
            msg = "数据启用成功";
        } else {
            startType = false;
            status = 4;
            code = "201";
            msg = "数据禁用成功";
        }
        record = new EcbudMoney();
        record.setEcbudmId(ecbudMoney.getEcbudmId());
        record.setStartType(startType);
        ecbudMoneyService.update(record);
        CommonFunction.getCommonMap(map, status, code, msg);
        return map;
    }

    /***===数据模型===***/
    //getPricePassEcbudIdAndAndProvinceNameAndWeight 根据省份和重量获取运费
    public Map<String, Object> getPricePassEcbudIdAndAndProvinceNameAndWeight(int ecbudId,
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
    public void deletePassEcbudId(int ecbudId) {
        EcbudMoney record = new EcbudMoney();
        record.setEcbudId(ecbudId);
        ecbudMoneyService.delete(record);
    }
}
