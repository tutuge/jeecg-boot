package org.jeecg.modules.cable.model.user;

import cn.hutool.core.util.ObjectUtil;
import com.google.gson.reflect.TypeToken;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.user.profit.bo.ProfitBo;
import org.jeecg.modules.cable.controller.user.profit.bo.ProfitListBo;
import org.jeecg.modules.cable.controller.user.profit.bo.ProfitSortBo;
import org.jeecg.modules.cable.controller.user.profit.vo.ProfitVo;
import org.jeecg.modules.cable.controller.userCommon.position.bo.EcProfitEditBo;
import org.jeecg.modules.cable.entity.price.EcuqDesc;
import org.jeecg.modules.cable.entity.price.EcuqInput;
import org.jeecg.modules.cable.entity.user.EcProfit;
import org.jeecg.modules.cable.model.price.EcuqDescModel;
import org.jeecg.modules.cable.service.user.EcProfitService;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    // getList
    public ProfitVo getList(ProfitListBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();

        EcProfit record = new EcProfit();
        record.setEcCompanyId(ecUser.getEcCompanyId());
        record.setStartType(bo.getStartType());

        if (bo.getPageNum() != null) {
            Integer pageNumber = bo.getPageSize();
            Integer startNumber = (bo.getPageNum() - 1) * pageNumber;
            record.setStartNum(startNumber);
            record.setPageNumber(pageNumber);
        }
        List<EcProfit> list = ecProfitService.getList(record);
        Long count = ecProfitService.getCount(record);
        return new ProfitVo(list, count);
    }

    // getObject
    public EcProfit getObject(ProfitBo bo) {
        return getObjectPassEcpId(bo.getEcpId());
    }

    // deal
    @Transactional(rollbackFor = Exception.class)
    public String deal(EcProfitEditBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();

        Integer ecpId = bo.getEcpId();
        String profitName = bo.getProfitName();// 名称
        Integer ecqulId = bo.getEcqulId();// 质量等级
        String silkName = bo.getSilkName();// 丝型号
        String area = bo.getArea();
        Integer startNumber = bo.getStartNumber();
        Integer endNumber = bo.getEndNumber();
        Integer ecbuluId = bo.getEcbuluId();
        BigDecimal startUnitPrice = bo.getStartUnitPrice();
        BigDecimal endUnitPrice = bo.getEndUnitPrice();
        BigDecimal profit = bo.getProfit();
        String exceptSilkName = bo.getExceptSilkName();
        String description = bo.getDescription();

        EcProfit record = new EcProfit();
        record.setEcpId(ecpId);
        record.setProfitName(profitName);
        EcProfit ecProfit = ecProfitService.getObject(record);
        String msg = "";
        if (ecProfit != null) {
            throw new RuntimeException("名称已占用");
        } else {
            if (ObjectUtil.isNull(ecpId)) {// 插入
                Integer sortId = 1;
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
                ecProfitService.insert(record);
                msg = "正常新增数据";
            } else {// 修改
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

    // start
    public String start(ProfitBo bo) {
        String msg = "";
        Integer ecpId = bo.getEcpId();
        EcProfit ecProfit = getObjectPassEcpId(ecpId);
        Boolean startType = ecProfit.getStartType();
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

    // sort
    public void sort(ProfitSortBo bo) {
        Integer ecpId = bo.getEcpId();
        Integer sortId = bo.getSortId();
        EcProfit record = new EcProfit();
        record.setEcpId(ecpId);
        record.setSortId(sortId);
        ecProfitService.update(record);
    }

    // delete
    @Transactional(rollbackFor = Exception.class)
    public void delete(ProfitBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();

        Integer ecpId = bo.getEcpId();
        EcProfit record = new EcProfit();
        record.setEcpId(ecpId);
        EcProfit ecProfit = ecProfitService.getObject(record);
        Integer sortId = ecProfit.getSortId();
        record = new EcProfit();
        record.setEcCompanyId(ecUser.getEcCompanyId());
        record.setSortId(sortId);
        List<EcProfit> list = ecProfitService.getList(record);
        Integer ecp_id;
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
    // getObjectPassEcpId
    public EcProfit getObjectPassEcpId(Integer ecpId) {
        EcProfit record = new EcProfit();
        record.setEcpId(ecpId);
        return ecProfitService.getObject(record);
    }

    // dealProfitAuto 自动修改利润
    public BigDecimal dealProfitAuto(EcuqInput ecuqInput) {
        BigDecimal profit = new BigDecimal("0");
        if (!ecuqInput.getProfitInput()) {
            Integer ecqulId = ecuqInput.getEcqulId();
            profit = new BigDecimal("1");
            EcProfit record = new EcProfit();
            record.setStartType(true);
            List<EcProfit> list = ecProfitService.getList(record);
            for (EcProfit ecProfit : list) {
                if (ecProfit.getEcqulId() != 0) {// 质量等级
                    if (ecqulId == ecProfit.getEcqulId()) {
                        profit = ecProfit.getProfit();
                    } else {
                        profit = new BigDecimal("0");
                    }
                }
                if (!"".equals(ecProfit.getSilkName())) {// 丝型号
                    if (ecuqInput.getSilkName().contains(ecProfit.getSilkName())
                            && profit.compareTo(new BigDecimal("1")) == 0) {
                        String exceptSilkName = ecProfit.getExceptSilkName();
                        if (!"".equals(exceptSilkName)) {// 除去某些丝型号
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
                if (ecProfit.getStartNumber() != 0 && ecProfit.getEndNumber() != 0) {// 销售数量
                    if (ecuqInput.getSaleNumber() > ecProfit.getStartNumber()
                            && ecuqInput.getSaleNumber() < ecProfit.getEndNumber()
                            && profit.compareTo(new BigDecimal("1")) == 0) {
                        profit = ecProfit.getProfit();
                    } else {
                        profit = new BigDecimal("0");
                    }
                }
                // 单位
                if (!Objects.equals(ecuqInput.getEcbuluId(), ecProfit.getEcbuluId())) {
                    if (profit.compareTo(new BigDecimal("1")) == 0) {
                        profit = ecProfit.getProfit();
                    }
                } else {
                    profit = new BigDecimal("0");
                }
                // 单价
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
