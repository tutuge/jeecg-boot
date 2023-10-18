package org.jeecg.modules.cable.model.user;

import com.google.gson.reflect.TypeToken;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.user.profit.vo.ProfitVo;
import org.jeecg.modules.cable.entity.price.EcuqDesc;
import org.jeecg.modules.cable.entity.price.EcuqInput;
import org.jeecg.modules.cable.entity.user.EcProfit;
import org.jeecg.modules.cable.model.price.EcuqDescModel;
import org.jeecg.modules.cable.service.user.EcProfitService;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class EcProfitModel {
    @Resource
    EcProfitService ecProfitService;
    @Resource
    EcuqDescModel ecuqDescModel;

    //getList
    public ProfitVo getList(HttpServletRequest request) {

        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        EcProfit record = new EcProfit();
        record.setEcCompanyId(ecUser.getEcCompanyId());
        if (request.getParameter("startType") != null) {
            boolean startType = true;
            if (!"0".equals(request.getParameter("startType"))) {
                if ("2".equals(request.getParameter("startType"))) {
                    startType = false;
                }
                record.setStartType(startType);
            }
        }
        if (request.getParameter("pageNumber") != null) {
            int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
            int startNumber = (Integer.parseInt(request.getParameter("page")) - 1) * pageNumber;
            record.setStartNum(startNumber);
            record.setPageNumber(pageNumber);
        }
        List<EcProfit> list = ecProfitService.getList(record);
        long count = ecProfitService.getCount(record);
        return new ProfitVo(list, count);
    }

    //getObject
    public EcProfit getObject(HttpServletRequest request) {
        int ecpId = Integer.parseInt(request.getParameter("ecpId"));
        return getObjectPassEcpId(ecpId);
    }

    //deal
    public String deal(HttpServletRequest request) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        int ecpId = Integer.parseInt(request.getParameter("ecpId"));
        String profitName = request.getParameter("profitName");//名称
        int ecqulId = Integer.parseInt(request.getParameter("ecqulId"));//质量等级
        String silkName = request.getParameter("silkName");//丝型号
        String area = request.getParameter("area");
        int startNumber = Integer.parseInt(request.getParameter("startNumber"));
        int endNumber = Integer.parseInt(request.getParameter("endNumber"));
        int ecbuluId = Integer.parseInt(request.getParameter("ecbuluId"));
        BigDecimal startUnitPrice = new BigDecimal(request.getParameter("startUnitPrice"));
        BigDecimal endUnitPrice = new BigDecimal(request.getParameter("endUnitPrice"));
        BigDecimal profit = new BigDecimal(request.getParameter("profit"));
        String exceptSilkName = request.getParameter("exceptSilkName");
        String description = request.getParameter("description");
        EcProfit record = new EcProfit();
        record.setEcpId(ecpId);
        record.setProfitName(profitName);
        EcProfit ecProfit = ecProfitService.getObject(record);
        String msg = "";
        if (ecProfit != null) {
            throw new RuntimeException("名称已占用");
        } else {
            if (ecpId == 0) {//插入
                int sortId = 1;
                record = new EcProfit();
                record.setEcCompanyId(ecUser.getEcCompanyId());
                ecProfit = ecProfitService.getObject(record);
                if (ecProfit != null) {
                    sortId = ecProfit.getSortId() + 1;
                }
                record.setProfitName(profitName);
                record.setStartType(true);
                record.setSortId(sortId);
                record.setEcqulId(ecqulId);
                record.setSilkName(silkName);
                record.setArea(area);
                record.setStartNumber(startNumber);
                record.setEndNumber(endNumber);
                record.setEcbuluId(ecbuluId);
                record.setStartUnitPrice(startUnitPrice);
                record.setEndUnitPrice(endUnitPrice);
                record.setProfit(profit);
                record.setExceptSilkName(exceptSilkName);
                record.setDescription(description);
                record.setAddTime(System.currentTimeMillis());
                record.setUpdateTime(System.currentTimeMillis());
                //log.info("record + " + CommonFunction.getGson().toJson(record));
                ecProfitService.insert(record);
                msg = "正常新增数据";
            } else {//修改
                record.setEcpId(ecpId);
                record.setProfitName(profitName);
                record.setEcqulId(ecqulId);
                record.setSilkName(silkName);
                record.setArea(area);
                record.setStartNumber(startNumber);
                record.setEndNumber(endNumber);
                record.setEcbuluId(ecbuluId);
                record.setStartUnitPrice(startUnitPrice);
                record.setEndUnitPrice(endUnitPrice);
                record.setProfit(profit);
                record.setExceptSilkName(exceptSilkName);
                record.setDescription(description);
                record.setAddTime(System.currentTimeMillis());
                record.setUpdateTime(System.currentTimeMillis());
                ecProfitService.update(record);

                msg = "正常更新数据";
            }
        }
        return msg;
    }

    //start
    public String start(HttpServletRequest request) {
        String msg = "";
        int ecpId = Integer.parseInt(request.getParameter("ecpId"));
        EcProfit ecProfit = getObjectPassEcpId(ecpId);
        boolean startType = ecProfit.getStartType();
        if (!startType) {
            startType = true;
            msg = "启用成功";
        } else {
            startType = false;
            msg = "禁用成功";
        }
        EcProfit record = new EcProfit();
        record.setEcpId(ecpId);
        record.setStartType(startType);
        ecProfitService.update(record);
        return msg;
    }

    //sort
    public void sort(HttpServletRequest request) {
        int ecpId = Integer.parseInt(request.getParameter("ecpId"));
        int sortId = Integer.parseInt(request.getParameter("sortId"));
        EcProfit record = new EcProfit();
        record.setEcpId(ecpId);
        record.setSortId(sortId);
        ecProfitService.update(record);
    }

    //delete
    public void delete(HttpServletRequest request) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();

        int ecpId = Integer.parseInt(request.getParameter("ecpId"));
        EcProfit record = new EcProfit();
        record.setEcpId(ecpId);
        EcProfit ecProfit = ecProfitService.getObject(record);
        int sortId = ecProfit.getSortId();
        record = new EcProfit();
        record.setEcCompanyId(ecUser.getEcCompanyId());
        record.setSortId(sortId);
        List<EcProfit> list = ecProfitService.getList(record);
        int ecp_id;
        for (EcProfit profit : list) {
            ecp_id = profit.getEcpId();
            sortId = profit.getSortId() - 1;
            record.setEcpId(ecp_id);
            record.setSortId(sortId);
            ecProfitService.update(record);
        }
        record = new EcProfit();
        record.setEcpId(ecpId);
        ecProfitService.delete(record);
    }

    /***===数据模型===***/
    //getObjectPassEcpId
    public EcProfit getObjectPassEcpId(int ecpId) {
        EcProfit record = new EcProfit();
        record.setEcpId(ecpId);
        return ecProfitService.getObject(record);
    }

    //dealProfitAuto 自动修改利润
    public BigDecimal dealProfitAuto(EcuqInput ecuqInput) {
        BigDecimal profit = new BigDecimal("0");
        if (!ecuqInput.getProfitInput()) {
            int ecqulId = ecuqInput.getEcqulId();
            profit = new BigDecimal("1");
            EcProfit record = new EcProfit();
            record.setStartType(true);
            List<EcProfit> list = ecProfitService.getList(record);
            for (EcProfit ecProfit : list) {
                if (ecProfit.getEcqulId() != 0) {//质量等级
                    if (ecqulId == ecProfit.getEcqulId()) {
                        profit = ecProfit.getProfit();
                    } else {
                        profit = new BigDecimal("0");
                    }
                }
                if (!"".equals(ecProfit.getSilkName())) {//丝型号
                    if (ecuqInput.getSilkName().contains(ecProfit.getSilkName())
                            && profit.compareTo(new BigDecimal("1")) == 0) {
                        String exceptSilkName = ecProfit.getExceptSilkName();
                        if (!"".equals(exceptSilkName)) {//除去某些丝型号
                            List<String> listStr = CommonFunction.getGson().fromJson(exceptSilkName,
                                    new TypeToken<List<String>>() {
                                    }.getType());
                            for (String str : listStr) {
                                if (ecuqInput.getSilkName().equals(str)) {
                                    profit = new BigDecimal("0");
                                } else {
                                    profit = ecProfit.getProfit();
                                }
                            }
                        } else {
                            profit = ecProfit.getProfit();
                        }
                    } else {
                        profit = new BigDecimal("0");
                    }
                }
                if (ecProfit.getStartNumber() != 0 && ecProfit.getEndNumber() != 0) {//销售数量
                    if (ecuqInput.getSaleNumber() > ecProfit.getStartNumber()
                            && ecuqInput.getSaleNumber() < ecProfit.getEndNumber()
                            && profit.compareTo(new BigDecimal("1")) == 0) {
                        profit = ecProfit.getProfit();
                    } else {
                        profit = new BigDecimal("0");
                    }
                }
                //单位
                if (!Objects.equals(ecuqInput.getEcbuluId(), ecProfit.getEcbuluId())) {
                    if (profit.compareTo(new BigDecimal("1")) == 0) {
                        profit = ecProfit.getProfit();
                    }
                } else {
                    profit = new BigDecimal("0");
                }
                //单价
                BigDecimal startUnitPrice = ecProfit.getStartUnitPrice();
                BigDecimal endUnitPrice = ecProfit.getEndUnitPrice();
                EcuqDesc ecuqDesc = ecuqDescModel.getObjectPassEcuqiId(ecuqInput.getEcuqiId());
                if (startUnitPrice.compareTo(new BigDecimal("0")) != 0
                        && endUnitPrice.compareTo(new BigDecimal("0")) != 0
                        && ecuqDesc != null) {
                    BigDecimal unitPrice = ecuqDesc.getUnitPrice();
                    if (unitPrice.compareTo(startUnitPrice) > -1
                            && unitPrice.compareTo(endUnitPrice) < 0) {
                        profit = ecProfit.getProfit();
                    } else {
                        profit = new BigDecimal("0");
                    }
                }
            }

        }
        return profit;
    }
}
