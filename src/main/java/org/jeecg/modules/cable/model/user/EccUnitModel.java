package org.jeecg.modules.cable.model.user;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.user.unit.bo.EccUnitBaseBo;
import org.jeecg.modules.cable.controller.user.unit.bo.EccUnitDealBo;
import org.jeecg.modules.cable.controller.user.unit.bo.EccUnitPageBo;
import org.jeecg.modules.cable.controller.user.unit.bo.EccUnitSortBo;
import org.jeecg.modules.cable.controller.user.unit.vo.UnitListVo;
import org.jeecg.modules.cable.controller.user.unit.vo.UnitVo;
import org.jeecg.modules.cable.entity.systemEcable.EcSilk;
import org.jeecg.modules.cable.entity.user.EccUnit;
import org.jeecg.modules.cable.service.systemEcable.EcSilkService;
import org.jeecg.modules.cable.service.user.EccUnitService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EccUnitModel {

    @Resource
    EccUnitService eccUnitService;

    @Resource
    private EcSilkService silkService;


    public UnitListVo getList(EccUnitPageBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();

        EccUnit record = new EccUnit();
        record.setEcCompanyId(ecUser.getEcCompanyId());
        record.setStartType(bo.getStartType());

        BeanUtils.copyProperties(bo, record);

        Integer pageNumber = bo.getPageSize();
        Integer startNumber = (bo.getPageNum() - 1) * pageNumber;
        record.setStartNumber(startNumber);
        record.setPageNumber(pageNumber);

        List<EccUnit> list = eccUnitService.getList(record);
        Long count = eccUnitService.getCount(record);
        // 转换
        List<UnitVo> res = convert(list);
        return new UnitListVo(res, count);
    }


    /**
     * 转换vo
     *
     * @param list 原始数据
     * @return 增加型号list
     */
    private List<UnitVo> convert(List<EccUnit> list) {
        // 将型号的id转换成数字的集合再进行数据库查询
        Set<Integer> ids = new HashSet<>();
        List<List<Integer>> sids = new ArrayList<>();
        // 最终结果
        List<UnitVo> res = new ArrayList<>();
        for (EccUnit unit : list) {
            UnitVo vo = new UnitVo();
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
                UnitVo vo = res.get(i);
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


    public EccUnit getObject(EccUnitBaseBo bo) {
        EccUnit object = getObjectPassEccuId(bo.getEccuId());
        if (ObjUtil.isNull(object)) {
            return null;
        }
        List<UnitVo> convert = convert(List.of(object));
        return convert.get(0);
    }


    @Transactional(rollbackFor = Exception.class)
    public String deal(EccUnitDealBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();

        Integer eccuId = bo.getEccuId();
        String silkName = bo.getSilkName();// 丝型号
        Integer ecbuluId = bo.getEcbuluId();
        String description = bo.getDescription();

        EccUnit record = new EccUnit();
        record.setEccuId(eccuId);
        record.setSilkName(silkName);
        EccUnit ecProfit = eccUnitService.getObject(record);
        String msg = "";
        if (ecProfit != null) {
            throw new RuntimeException("名称已占用");
        }
        if (ObjectUtil.isNull(eccuId)) {// 插入
            Integer sortId = 1;
            record = new EccUnit();
            record.setEcCompanyId(ecUser.getEcCompanyId());
            ecProfit = eccUnitService.getObject(record);
            if (ecProfit != null) {
                sortId = ecProfit.getSortId() + 1;
            }
            record.setSilkName(silkName);
            record.setStartType(true);
            record.setSortId(sortId);
            record.setSilkName(silkName);
            record.setEcbuluId(ecbuluId);
            record.setDescription(description);
            record.setAddTime(System.currentTimeMillis());
            record.setUpdateTime(System.currentTimeMillis());
            // log.info("record + " + CommonFunction.getGson().toJson(record));
            eccUnitService.insert(record);
            msg = "正常新增数据";
        } else {// 修改
            record.setEccuId(eccuId);
            record.setSilkName(silkName);
            record.setEcbuluId(ecbuluId);
            record.setDescription(description);
            record.setAddTime(System.currentTimeMillis());
            record.setUpdateTime(System.currentTimeMillis());
            eccUnitService.update(record);
            msg = "正常更新数据";
        }
        return msg;
    }


    public String start(EccUnitBaseBo bo) {
        Integer eccuId = bo.getEccuId();
        EccUnit eccUnit = getObjectPassEccuId(eccuId);
        Boolean startType = eccUnit.getStartType();
        String msg = "";
        if (!startType) {
            startType = true;
            msg = "启用成功";
        } else {
            startType = false;
            msg = "禁用成功";
        }
        EccUnit record = new EccUnit();
        record.setEccuId(eccuId);
        record.setStartType(startType);
        eccUnitService.update(record);
        return msg;
    }


    @Transactional(rollbackFor = Exception.class)
    public void sort(List<EccUnitSortBo> bos) {
        for (EccUnitSortBo bo : bos) {
            Integer eccuId = bo.getEccuId();
            Integer sortId = bo.getSortId();
            EccUnit record = new EccUnit();
            record.setEccuId(eccuId);
            record.setSortId(sortId);
            eccUnitService.update(record);
        }
    }


    @Transactional(rollbackFor = Exception.class)
    public void delete(EccUnitBaseBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        Integer eccuId = bo.getEccuId();
        EccUnit record = new EccUnit();
        record.setEccuId(eccuId);
        EccUnit eccUnit = eccUnitService.getObject(record);
        Integer sortId = eccUnit.getSortId();
        record = new EccUnit();
        record.setEcCompanyId(ecUser.getEcCompanyId());
        record.setSortId(sortId);
        List<EccUnit> list = eccUnitService.getList(record);
        Integer eccu_id;
        for (EccUnit unit : list) {
            eccu_id = unit.getEccuId();
            sortId = unit.getSortId() - 1;
            record.setEccuId(eccu_id);
            record.setSortId(sortId);
            eccUnitService.update(record);
        }
        eccUnitService.delete(eccuId);
    }

    /***===数据模型===***/

    public EccUnit getObjectPassEccuId(Integer eccuId) {
        EccUnit record = new EccUnit();
        record.setEccuId(eccuId);
        return eccUnitService.getObject(record);
    }
}
