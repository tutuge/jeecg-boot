package org.jeecg.modules.cable.model.user;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.user.profit.bo.ProfitBo;
import org.jeecg.modules.cable.controller.user.profit.bo.ProfitListBo;
import org.jeecg.modules.cable.controller.user.profit.bo.ProfitSortBo;
import org.jeecg.modules.cable.controller.user.profit.vo.ProfitListVo;
import org.jeecg.modules.cable.controller.user.profit.vo.ProfitVo;
import org.jeecg.modules.cable.controller.userCommon.position.bo.EcProfitEditBo;
import org.jeecg.modules.cable.entity.price.EcuqDesc;
import org.jeecg.modules.cable.entity.price.EcuqInput;
import org.jeecg.modules.cable.entity.systemEcable.EcSilk;
import org.jeecg.modules.cable.entity.user.EcProfit;
import org.jeecg.modules.cable.model.price.EcuqDescModel;
import org.jeecg.modules.cable.service.price.EcSilkService;
import org.jeecg.modules.cable.service.user.EcProfitService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EcProfitModel {
    @Resource
    EcProfitService ecProfitService;
    @Resource
    EcuqDescModel ecuqDescModel;

    @Resource
    private EcSilkService silkService;

    // getList
    public ProfitListVo getList(ProfitListBo bo) {
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
        List<ProfitVo> convert = convert(list);
        return new ProfitListVo(convert, count);
    }


    /**
     * 转换vo
     *
     * @param list 原始数据
     * @return 增加型号list
     */
    private List<ProfitVo> convert(List<EcProfit> list) {
        // 将型号的id转换成数字的集合再进行数据库查询
        Set<Integer> ids = new HashSet<>();
        List<List<Integer>> sids = new ArrayList<>();
        // 最终结果
        List<ProfitVo> res = new ArrayList<>();
        for (EcProfit unit : list) {
            ProfitVo vo = new ProfitVo();
            BeanUtils.copyProperties(unit, vo);
            res.add(vo);
            String s = unit.getSilkName();
            if (StrUtil.isNotBlank(s)) {
                String[] split = s.split(",");
                List<Integer> list1 = Arrays.stream(split).map(Integer::valueOf).toList();
                sids.add(list1);
                ids.addAll(list1);
            } else {
                sids.add(new ArrayList<>());
            }
        }
        // 确定有对应型号的情况下，转换成map进行赋值
        if (!ids.isEmpty()) {
            List<EcSilk> ecSilks = silkService.listByIds(ids);
            Map<Integer, EcSilk> map = ecSilks.stream().collect(Collectors.toMap(EcSilk::getEcsId, v -> v));
            for (int i = 0; i < res.size(); i++) {
                ProfitVo vo = res.get(i);
                List<Integer> integers = sids.get(i);
                List<EcSilk> silks = new ArrayList<>();
                if (!integers.isEmpty()) {
                    for (Integer i1 : integers) {
                        silks.add(map.get(i1));
                    }
                }
                vo.setSilks(silks);
            }
        }
        return res;
    }

    // getObject
    public EcProfit getObject(ProfitBo bo) {
        EcProfit object = getObjectPassEcpId(bo.getEcpId());
        if (ObjUtil.isNull(object)) {
            return null;
        }
        List<ProfitVo> convert = convert(List.of(object));
        return convert.get(0);
    }

    // deal
    @Transactional(rollbackFor = Exception.class)
    public String deal(EcProfitEditBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();

        Integer ecpId = bo.getEcpId();
        String profitName = bo.getProfitName();// 名称

        EcProfit record = new EcProfit();
        BeanUtils.copyProperties(bo, record);
        record.setEcpId(ecpId);
        record.setProfitName(profitName);
        EcProfit ecProfit = ecProfitService.getObject(record);
        String msg = "";
        if (ecProfit != null) {
            throw new RuntimeException("名称已占用");
        }
        if (ObjectUtil.isNull(ecpId)) {// 插入
            Integer sortId = 1;
            record.setEcCompanyId(ecUser.getEcCompanyId());
            ecProfit = ecProfitService.getObject(record);
            if (ecProfit != null) {
                sortId = ecProfit.getSortId() + 1;
            }
            record.setStartType(true);
            record.setSortId(sortId);
            ecProfitService.insert(record);
            msg = "正常新增数据";
        } else {// 修改
            record.setEcpId(ecpId);
            ecProfitService.update(record);
            msg = "正常更新数据";
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
        ecProfitService.delete(ecpId);
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
                    if (Objects.equals(ecqulId, ecProfit.getEcqulId())) {
                        profit = ecProfit.getProfit();
                    } else {
                        profit = new BigDecimal("0");
                    }
                }
                if (!"".equals(ecProfit.getSilkName())) {// 丝型号
                    if (ecuqInput.getSilkName().contains(ecProfit.getSilkName())
                            && profit.compareTo(new BigDecimal("1")) == 0) {
                        profit = ecProfit.getProfit();

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
