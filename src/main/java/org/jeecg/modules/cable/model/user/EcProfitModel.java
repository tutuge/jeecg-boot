package org.jeecg.modules.cable.model.user;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.user.profit.bo.ProfitBo;
import org.jeecg.modules.cable.controller.user.profit.bo.ProfitListBo;
import org.jeecg.modules.cable.controller.user.profit.bo.ProfitSortBo;
import org.jeecg.modules.cable.controller.user.profit.vo.ProfitListVo;
import org.jeecg.modules.cable.controller.user.profit.vo.ProfitVo;
import org.jeecg.modules.cable.controller.userCommon.position.bo.EcProfitEditBo;
import org.jeecg.modules.cable.entity.price.EcuqInput;
import org.jeecg.modules.cable.entity.user.EcProfit;
import org.jeecg.modules.cable.entity.userEcable.EcuSilkModel;
import org.jeecg.modules.cable.model.price.EcuqDescModel;
import org.jeecg.modules.cable.service.user.EcProfitService;
import org.jeecg.modules.cable.service.userEcable.EcuSilkModelService;
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
    private EcuSilkModelService ecuSilkModelService;


    public ProfitListVo getList(ProfitListBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcProfit record = new EcProfit();
        record.setEcCompanyId(sysUser.getEcCompanyId());
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
            String s = unit.getEcusmId();
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
            List<EcuSilkModel> ecSilks = ecuSilkModelService.listByIds(ids);
            Map<Integer, EcuSilkModel> map = ecSilks.stream().collect(Collectors.toMap(EcuSilkModel::getEcusmId, v -> v));
            for (int i = 0; i < res.size(); i++) {
                ProfitVo vo = res.get(i);
                List<Integer> integers = sids.get(i);
                List<EcuSilkModel> silks = new ArrayList<>();
                if (!integers.isEmpty()) {
                    for (Integer i1 : integers) {
                        EcuSilkModel ecuSilkModel = map.get(i1);
                        if (ObjUtil.isNotNull(ecuSilkModel)) {
                            silks.add(ecuSilkModel);
                        }
                    }
                }
                vo.setSilkModels(silks);
            }
        }
        return res;
    }


    public EcProfit getObject(ProfitBo bo) {
        EcProfit object = getObjectPassEcpId(bo.getEcpId());
        if (ObjUtil.isNull(object)) {
            return null;
        }
        List<ProfitVo> convert = convert(List.of(object));
        return convert.get(0);
    }


    @Transactional(rollbackFor = Exception.class)
    public String deal(EcProfitEditBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
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
            int sortId = 1;
            record.setEcCompanyId(sysUser.getEcCompanyId());
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


    @Transactional(rollbackFor = Exception.class)
    public void sort(List<ProfitSortBo> bos) {
        for (ProfitSortBo bo : bos) {
            Integer ecpId = bo.getEcpId();
            Integer sortId = bo.getSortId();
            EcProfit record = new EcProfit();
            record.setEcpId(ecpId);
            record.setSortId(sortId);
            ecProfitService.update(record);
        }
    }


    @Transactional(rollbackFor = Exception.class)
    public void delete(ProfitBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        Integer ecpId = bo.getEcpId();
        EcProfit record = new EcProfit();
        record.setEcpId(ecpId);
        EcProfit ecProfit = ecProfitService.getObject(record);
        Integer sortId = ecProfit.getSortId();
        record = new EcProfit();
        record.setEcCompanyId(sysUser.getEcCompanyId());
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

    
    // getObjectPassEcpId
    public EcProfit getObjectPassEcpId(Integer ecpId) {
        EcProfit record = new EcProfit();
        record.setEcpId(ecpId);
        return ecProfitService.getObject(record);
    }

    /**
     * 根据质量等级ID 型号 规格 数量 单位 查询利润配置
     * 利润管理也是计算报价使用。界面需要修改，现在不满足需求
     * 型号，下拉选择系统维护的型号，可多选，选择芯数、平方数确定规格，匹配原则是，根据质量等级、型号、规格，查询是否存在此利润配置，
     * 如果存在，判断数量，比如报价选择米，填写了98米，如果我数量区间设置1-99，满足数量关系，如果单价也符合，则满足利润配置
     * 拿到5%利润计入报价单
     *
     * @param ecuqInput 报价明细
     * @param unitMoney 单价
     * @param companyId 公司ID
     * @return
     */
    public BigDecimal dealProfitAuto(EcuqInput ecuqInput, BigDecimal unitMoney, Integer companyId) {
        BigDecimal profit = BigDecimal.ZERO;
        //利润是否手输
        if (!ecuqInput.getProfitInput()) {
            //质量等级ID
            Integer ecqulId = ecuqInput.getEcqulId();
            //型号ID
            Integer ecusmId = ecuqInput.getEcusmId();
            //单位
            Integer ecbuluId = ecuqInput.getEcbuluId();
            String areaStr = ecuqInput.getAreaStr();
            Integer saleNumber = ecuqInput.getSaleNumber();
            //切分规格，获得芯数与平米数
            String[] areaArr = areaStr.split("\\+");
            String[] fireArr = areaArr[0].split("\\*");
            //芯数
            String coreStr = "";
            //平方数
            String area = fireArr[1];
            if (areaArr.length == 2) {
                coreStr = fireArr[0] + "+" + areaArr[1];
            } else {
                coreStr = fireArr[0];
            }

            profit = BigDecimal.ONE;
            //根据公司、质量等级ID，单位ID查询利润
            EcProfit record = new EcProfit();
            record.setStartType(true);
            record.setEcCompanyId(companyId);
            record.setEcqulId(ecqulId);
            record.setEcbuluId(ecbuluId);
            List<EcProfit> list = ecProfitService.getList(record);
            for (EcProfit ecProfit : list) {
                String ecusModelIdStr = ecProfit.getEcusmId();
                String area1 = ecProfit.getArea();
                String coreStr1 = ecProfit.getCoreStr();
                //满足销售数量区间
                Integer startNumber = ecProfit.getStartNumber();
                Integer endNumber = ecProfit.getEndNumber();
                boolean numBool = saleNumber <= endNumber && saleNumber >= startNumber;
                //满足单价区间(只针对材料的单价)
                BigDecimal startUnitPrice = ecProfit.getStartUnitPrice();
                BigDecimal endUnitPrice = ecProfit.getEndUnitPrice();
                boolean moneyMatch = unitMoney.compareTo(startUnitPrice) >= 0 && unitMoney.compareTo(endUnitPrice) <= 0;
                List<Integer> modelIds = Arrays.stream(ecusModelIdStr.split(",")).map(Integer::valueOf).toList();
                if (modelIds.contains(ecusmId) && area1.equals(area) && coreStr1.equals(coreStr)
                        && numBool && moneyMatch) {
                    profit = ecProfit.getProfit();
                    break;
                }
            }
        }
        return profit;
    }
}
