package org.jeecg.modules.cable.model.systemEcable;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.systemEcable.silk.bo.EcbSilkBaseBo;
import org.jeecg.modules.cable.controller.systemEcable.silk.bo.EcbSilkBo;
import org.jeecg.modules.cable.controller.systemEcable.silk.bo.EcbSilkSortBo;
import org.jeecg.modules.cable.controller.systemEcable.silk.bo.EcbSilkStartBo;
import org.jeecg.modules.cable.controller.systemEcable.silk.vo.SilkVo;
import org.jeecg.modules.cable.entity.systemEcable.EcSilk;
import org.jeecg.modules.cable.entity.systemEcable.EcbSheath;
import org.jeecg.modules.cable.model.userEcable.EcbuSheathModel;
import org.jeecg.modules.cable.service.price.EcSilkService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class EcSilkModel {
    @Resource
    EcSilkService ecSilkService;
    @Resource
    EcbuSheathModel ecbuSheathModel;


    public List<EcSilk> getList(EcbSilkBo bo) {
        EcSilk record = new EcSilk();
        record.setStartType(bo.getStartType());
        return ecSilkService.getList(record);
    }

    // getListPassSilkName
    public List<EcSilk> getListPassSilkName(EcbSilkStartBo bo) {
        // 获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        Integer ecuId = ecUser.getEcuId();
        String silkName = bo.getSilkName();
        List<EcSilk> list = getListSilkName(ecuId, silkName);
        return list;
    }

    // getListSilkName
    public List<EcSilk> getListSilkName(EcbSilkBo bo) {
        EcSilk record = new EcSilk();
        // 获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        Integer ecuId = ecUser.getEcuId();
        record.setStartType(bo.getStartType());

        List<EcSilk> list = ecSilkService.getList(record);
        List<EcSilk> listAll = new ArrayList<>();
        for (EcSilk ecSilk : list) {
            String silkName = ecSilk.getAbbreviation();
            List<EcSilk> listNew = getListSilkName(ecuId, silkName);
            listAll.addAll(listNew);
        }
        return listAll;
    }

    // getAllList
    public List<EcSilk> getAllList(Integer ecuId) {
        EcSilk record = new EcSilk();
        record.setStartType(true);
        List<EcSilk> list = ecSilkService.getList(record);
        List<EcSilk> listAll = new ArrayList<>();
        for (EcSilk ecSilk : list) {
            String silkName = ecSilk.getAbbreviation();
            List<EcSilk> listNew = getListSilkName(ecuId, silkName);
            listAll.addAll(listNew);
        }
        return listAll;
    }

    /***===数据模型===***/
    // getListSilkName 获取丝类型名称为报价页面提供数据
    public List<EcSilk> getListSilkName(Integer ecuId, String silkName) {
        List<EcSilk> list;
        List<EcSilk> list_new = new ArrayList<>();
        List<EcbSheath> list_sheath = ecbuSheathModel.getListSilkName(ecuId);
        EcSilk record = new EcSilk();
        record.setStartType(true);
        list = ecSilkService.getList(record);
        String abbreviation;
        String abbreviationSilk;
        String abbreviationSheath;
        Integer sortId = 1;
        for (EcSilk ecSilk : list) {
            abbreviationSilk = ecSilk.getAbbreviation();
            // log.info("abbreviationSilk + " + abbreviationSilk);
            if ("YJV".equals(abbreviationSilk)) {
                EcSilk object;
                for (EcbSheath ecbSheath : list_sheath) {
                    if ("YCB".equals(ecbSheath.getAbbreviation()) || "YCA".equals(ecbSheath.getAbbreviation())) {
                        continue;
                    }
                    object = new EcSilk();
                    abbreviationSheath = ecbSheath.getAbbreviation();
                    if ("WDZA".equals(abbreviationSheath)
                            || "WDZB".equals(abbreviationSheath)
                            || "WDZC".equals(abbreviationSheath)) {
                        abbreviationSilk = "YJY";
                    } else {
                        abbreviationSilk = "YJV";
                    }
                    if ("WDZC".equals(abbreviationSheath)) {
                        abbreviationSheath = "WDZ";
                    }
                    if (!"".equals(abbreviationSheath)) {
                        abbreviation = abbreviationSheath + "-" + abbreviationSilk;
                    } else {
                        abbreviation = abbreviationSilk;
                    }
                    object.setSortId(sortId);
                    object.setAbbreviation(abbreviation);
                    object.setEcsId(ecSilk.getEcsId());
                    list_new.add((sortId - 1), object);
                    sortId++;
                    // 22 是否带铠
                    object = new EcSilk();
                    object.setSortId(sortId);
                    if ("YJY".equals(abbreviationSilk)) {
                        object.setAbbreviation(abbreviation + "23");
                    } else {
                        object.setAbbreviation(abbreviation + "22");
                    }
                    object.setEcsId(ecSilk.getEcsId());
                    list_new.add((sortId - 1), object);
                    sortId++;
                    // N或NH
                    object = new EcSilk();
                    object.setSortId(sortId);
                    if (!"YJV".equals(abbreviation)) {
                        abbreviation = abbreviationSheath + "N-" + abbreviationSilk;
                        object.setAbbreviation(abbreviation);
                    } else {
                        abbreviation = "NH-" + abbreviation;
                        object.setAbbreviation(abbreviation);
                    }
                    object.setEcsId(ecSilk.getEcsId());
                    list_new.add((sortId - 1), object);
                    sortId++;
                    // 22 是否带铠
                    object = new EcSilk();
                    object.setEcsId(ecSilk.getEcsId());
                    object.setSortId(sortId);
                    if ("YJY".equals(abbreviationSilk)) {
                        object.setAbbreviation(abbreviation + "23");
                    } else {
                        object.setAbbreviation(abbreviation + "22");
                    }
                    list_new.add((sortId - 1), object);
                    sortId++;
                }
            } else if ("YJLV".equals(abbreviationSilk)) {
                EcSilk object;
                for (EcbSheath ecbSheath : list_sheath) {
                    if ("YCB".equals(ecbSheath.getAbbreviation()) || "YCA".equals(ecbSheath.getAbbreviation())) {
                        continue;
                    }
                    object = new EcSilk();
                    abbreviationSheath = ecbSheath.getAbbreviation();
                    if ("WDZC".equals(abbreviationSheath)) {
                        abbreviationSheath = "WDZ";
                    }
                    if ("WDZA".equals(abbreviationSheath) || "WDZB".equals(abbreviationSheath)) {
                        abbreviationSilk = "YJLY";
                    } else {
                        abbreviationSilk = "YJLV";
                    }
                    if (!"".equals(abbreviationSheath)) {
                        abbreviation = abbreviationSheath + "-" + abbreviationSilk;
                    } else {
                        abbreviation = abbreviationSilk;
                    }
                    object.setSortId(sortId);
                    object.setAbbreviation(abbreviation);
                    object.setEcsId(ecSilk.getEcsId());
                    list_new.add((sortId - 1), object);
                    sortId++;
                    // 22 是否带铠
                    object = new EcSilk();
                    object.setSortId(sortId);
                    if ("YJLY".equals(abbreviationSilk)) {
                        object.setAbbreviation(abbreviation + "23");
                    } else {
                        object.setAbbreviation(abbreviation + "22");
                    }
                    object.setEcsId(ecSilk.getEcsId());
                    list_new.add((sortId - 1), object);
                    sortId++;
                    // N或NH
                    object = new EcSilk();
                    object.setSortId(sortId);
                    if (!"YJLV".equals(abbreviation)) {
                        abbreviation = abbreviationSheath + "N-" + abbreviationSilk;
                        object.setAbbreviation(abbreviation);
                    } else {
                        abbreviation = "NH-" + abbreviation;
                        object.setAbbreviation(abbreviation);
                    }
                    object.setEcsId(ecSilk.getEcsId());
                    list_new.add((sortId - 1), object);
                    sortId++;
                    // 22 是否带铠
                    object = new EcSilk();
                    object.setSortId(sortId);
                    if ("YJLY".equals(abbreviationSilk)) {
                        object.setAbbreviation(abbreviation + "23");
                    } else {
                        object.setAbbreviation(abbreviation + "22");
                    }
                    object.setEcsId(ecSilk.getEcsId());
                    list_new.add((sortId - 1), object);
                    sortId++;
                }
            } else if ("BV".equals(abbreviationSilk)) {
                EcSilk object;
                for (EcbSheath ecbSheath : list_sheath) {
                    if ("ZC".equals(ecbSheath.getAbbreviation())
                            || "ZB".equals(ecbSheath.getAbbreviation())
                            || "ZA".equals(ecbSheath.getAbbreviation())
                            || "YCB".equals(ecbSheath.getAbbreviation())
                            || "YCA".equals(ecbSheath.getAbbreviation())) {
                        continue;
                    }
                    object = new EcSilk();
                    abbreviationSheath = ecbSheath.getAbbreviation();
                    // log.info(abbreviationSheath);
                    if ("WDZC".equals(abbreviationSheath)) {
                        abbreviationSheath = "WDZ";
                    }
                    if (!"".equals(abbreviationSheath)) {
                        abbreviation = abbreviationSheath + "-BYJ";
                    } else {
                        abbreviation = abbreviationSilk;
                    }
                    object.setSortId(sortId);
                    object.setAbbreviation(abbreviation);
                    object.setEcsId(ecSilk.getEcsId());
                    list_new.add((sortId - 1), object);
                    sortId++;
                    // N或NH
                    object = new EcSilk();
                    object.setSortId(sortId);
                    if (!"BV".equals(abbreviation)) {
                        abbreviation = abbreviationSheath + "N-BYJ";
                        object.setAbbreviation(abbreviation);
                    } else {
                        abbreviation = "NH-" + abbreviation;
                        object.setAbbreviation(abbreviation);
                    }
                    object.setEcsId(ecSilk.getEcsId());
                    list_new.add((sortId - 1), object);
                    sortId++;
                }
            } else if ("BVR".equals(abbreviationSilk)) {
                EcSilk object;
                for (EcbSheath ecbSheath : list_sheath) {
                    if ("ZC".equals(ecbSheath.getAbbreviation()) || "ZB".equals(ecbSheath.getAbbreviation()) || "ZA".equals(ecbSheath.getAbbreviation()) || "YCB".equals(ecbSheath.getAbbreviation()) || "YCA".equals(ecbSheath.getAbbreviation())) {
                        continue;
                    }
                    object = new EcSilk();
                    abbreviationSheath = ecbSheath.getAbbreviation();
                    // log.info(abbreviationSheath);
                    if ("WDZC".equals(abbreviationSheath)) {
                        abbreviationSheath = "WDZ";
                    }
                    if (!"".equals(abbreviationSheath)) {
                        abbreviation = abbreviationSheath + "-BYJR";
                    } else {
                        abbreviation = abbreviationSilk;
                    }
                    object.setSortId(sortId);
                    object.setAbbreviation(abbreviation);
                    object.setEcsId(ecSilk.getEcsId());
                    list_new.add((sortId - 1), object);
                    sortId++;
                    // N或NH
                    object = new EcSilk();
                    object.setSortId(sortId);
                    if (!"BVR".equals(abbreviation)) {
                        abbreviation = abbreviationSheath + "N-BYJR";
                        object.setAbbreviation(abbreviation);
                    } else {
                        abbreviation = "NH-" + abbreviation;
                        object.setAbbreviation(abbreviation);
                    }
                    object.setEcsId(ecSilk.getEcsId());
                    list_new.add((sortId - 1), object);
                    sortId++;
                }
            }
        }
        if (!"".equals(silkName)) {
            if ("YJV".equals(silkName)) {
                for (Integer i = 0; i < list_new.size(); i++) {
                    if (!list_new.get(i).getAbbreviation().contains("YJV")
                            && !list_new.get(i).getAbbreviation().contains("YJY")) {
                        list_new.remove(i);
                        i--;
                    }
                }
            } else if ("YJLV".equals(silkName)) {
                for (Integer i = 0; i < list_new.size(); i++) {
                    if (!list_new.get(i).getAbbreviation().contains("YJLV")
                            && !list_new.get(i).getAbbreviation().contains("YJlY")) {
                        list_new.remove(i);
                        i--;
                    }
                }
            } else if ("BV".equals(silkName)) {
                for (Integer i = 0; i < list_new.size(); i++) {
                    if (!list_new.get(i).getAbbreviation().contains("BV")
                            && !list_new.get(i).getAbbreviation().contains("BYJ")) {
                        list_new.remove(i);
                        i--;
                    }
                }
                for (Integer i = 0; i < list_new.size(); i++) {
                    if (list_new.get(i).getAbbreviation().contains("BVR")
                            || list_new.get(i).getAbbreviation().contains("BYJR")) {
                        list_new.remove(i);
                        i--;
                    }
                }
            } else if ("BVR".equals(silkName)) {
                for (Integer i = 0; i < list_new.size(); i++) {
                    if (!list_new.get(i).getAbbreviation().contains("BVR")
                            && !list_new.get(i).getAbbreviation().contains("BYJR")) {
                        list_new.remove(i);
                        i--;
                    }
                }
            }
        }
        // log.info(String.valueOf(list_new.size()));
        return list_new;
    }

    // getListStart
    public List<EcSilk> getListStart() {
        EcSilk record = new EcSilk();
        record.setStartType(true);
        return ecSilkService.getList(record);
    }

    // getObjectPassEcsId
    public SilkVo getObjectPassEcsId(Integer ecsId) {
        EcSilk record = new EcSilk();
        record.setEcsId(ecsId);
        return ecSilkService.getObject(record);
    }

    // getEcsId
    public Integer getEcsId(Integer ecuId, String sName) {
        Integer ecsId = 0;
        EcSilk record = new EcSilk();
        record.setStartType(true);
        List<EcSilk> list = ecSilkService.getList(record);
        List<EcSilk> listAll = new ArrayList<>();
        for (EcSilk ecSilk : list) {
            String silkName = ecSilk.getAbbreviation();
            List<EcSilk> listNew = getListSilkName(ecuId, silkName);
            listAll.addAll(listNew);
        }
        for (EcSilk ecSilk : listAll) {
            if (ecSilk.getAbbreviation().equals(sName)) {
                ecsId = ecSilk.getEcsId();
            }
        }
        return ecsId;
    }

    // getListAllSilkName
    public List<EcSilk> getListAllSilkName(Integer ecuId) {
        EcSilk record = new EcSilk();
        record.setStartType(true);
        List<EcSilk> list = ecSilkService.getList(record);
        List<EcSilk> listAll = new ArrayList<>();
        for (EcSilk ecSilk : list) {
            String silkName = ecSilk.getAbbreviation();
            List<EcSilk> listNew = getListSilkName(ecuId, silkName);
            listAll.addAll(listNew);
        }
        return listAll;
    }

    public void save(EcSilk ecSilk) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        LambdaQueryWrapper<EcSilk> like = Wrappers.lambdaQuery(EcSilk.class)
                .like(EcSilk::getAbbreviation, ecSilk.getAbbreviation())
                .or().like(EcSilk::getFullName, ecSilk.getFullName());
        List<EcSilk> list = ecSilkService.list(like);
        if (!list.isEmpty()) {
            throw new RuntimeException("当前名称已被占用");
        }
        EcSilk object = ecSilkService.getObject(null);
        Integer sortId = 1;
        if (ObjectUtil.isNotNull(object)) {
            sortId = object.getSortId();
        }
        ecSilk.setSortId(sortId);
        ecSilk.setStartType(true);
        ecSilk.setEcaId(sysUser.getUserId());
        ecSilk.setAddTime(new Date());
        ecSilk.setUpdateTime(new Date());
        ecSilkService.save(ecSilk);
    }

    public void sort(List<EcbSilkSortBo> bos) {
        for (EcbSilkSortBo bo : bos) {
            Integer ecbsId = bo.getEcsId();
            Integer sortId = bo.getSortId();
            EcSilk record = new EcSilk();
            record.setEcsId(ecbsId);
            record.setSortId(sortId);
            ecSilkService.updateById(record);
        }
    }

    public String start(EcbSilkBaseBo bo) {
        Integer ecbsId = bo.getEcsId();
        EcSilk record = new EcSilk();
        record.setEcsId(ecbsId);
        EcSilk silk = ecSilkService.getObject(record);
        Boolean startType = silk.getStartType();
        String msg;
        if (!startType) {
            startType = true;
            msg = "数据启用成功";
        } else {
            startType = false;
            msg = "数据禁用成功";
        }
        record = new EcSilk();
        record.setEcsId(silk.getEcsId());
        record.setStartType(startType);
        ecSilkService.updateById(record);
        return msg;
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(EcbSilkBaseBo bo) {
        Integer ecbsId = bo.getEcsId();
        EcSilk record = new EcSilk();
        record.setEcsId(ecbsId);
        EcSilk ecbShield = ecSilkService.getObject(record);
        Integer sortId = ecbShield.getSortId();
        record = new EcSilk();
        record.setSortId(sortId);
        List<EcSilk> list = ecSilkService.getList(record);
        Integer ecbs_id;
        for (EcSilk ecSilk : list) {
            ecbs_id = ecSilk.getEcsId();
            sortId = ecSilk.getSortId() - 1;
            record.setEcsId(ecbs_id);
            record.setSortId(sortId);
            ecSilkService.updateById(record);
        }
        record = new EcSilk();
        record.setEcsId(ecbsId);
        ecSilkService.removeById(record);
    }
}
