package org.jeecg.modules.cable.model.userEcable;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.userEcable.silk.bo.EcubSilkBaseBo;
import org.jeecg.modules.cable.controller.userEcable.silk.bo.EcubSilkSortBo;
import org.jeecg.modules.cable.entity.userEcable.EcuSilk;
import org.jeecg.modules.cable.service.userEcable.EcuSilkService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class EcuSilkServiceModel {
    @Resource
    EcuSilkService ecuSilkService;


    //public List<EcuSilk> getList(EcbSilkBo bo) {
    //    EcuSilk record = new EcuSilk();
    //    record.setStartType(bo.getStartType());
    //    return ecuSilkService.getList(record);
    //}
    //
    //// getListPassSilkName
    //public List<EcuSilk> getListPassSilkName(EcbSilkStartBo bo) {
    //    // 获取当前用户id
    //    LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
    //    EcUser ecUser = sysUser.getEcUser();
    //    Integer ecuId = ecUser.getEcuId();
    //    String silkName = bo.getSilkName();
    //    List<EcuSilk> list = getListSilkName(ecuId, silkName);
    //    return list;
    //}

    //// getListSilkName
    //public List<EcuSilk> getListSilkName(EcbSilkBo bo) {
    //    EcuSilk record = new EcuSilk();
    //    // 获取当前用户id
    //    LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
    //    EcUser ecUser = sysUser.getEcUser();
    //    Integer ecuId = ecUser.getEcuId();
    //    record.setStartType(bo.getStartType());
    //
    //    List<EcuSilk> list = ecuSilkService.getList(record);
    //    List<EcuSilk> listAll = new ArrayList<>();
    //    for (EcuSilk ecSilk : list) {
    //        String silkName = ecSilk.getAbbreviation();
    //        List<EcuSilk> listNew = getListSilkName(ecuId, silkName);
    //        listAll.addAll(listNew);
    //    }
    //    return listAll;
    //}
    //
    //// getAllList
    //public List<EcuSilk> getAllList(Integer ecuId) {
    //    EcuSilk record = new EcuSilk();
    //    record.setStartType(true);
    //    List<EcuSilk> list = ecuSilkService.getList(record);
    //    List<EcuSilk> listAll = new ArrayList<>();
    //    for (EcuSilk ecSilk : list) {
    //        String silkName = ecSilk.getAbbreviation();
    //        List<EcuSilk> listNew = getListSilkName(ecuId, silkName);
    //        listAll.addAll(listNew);
    //    }
    //    return listAll;
    //}

    /***===数据模型===***/
    // getListSilkName 获取丝类型名称为报价页面提供数据
    //public List<EcuSilk> getListSilkName(Integer ecuId, String silkName) {
    //    List<EcuSilk> list;
    //    List<EcuSilk> list_new = new ArrayList<>();
    //    List<EcbSheath> list_sheath = ecbuSheathModel.getListSilkName(ecuId);
    //    EcuSilk record = new EcuSilk();
    //    record.setStartType(true);
    //    list = ecuSilkService.getList(record);
    //    String abbreviation;
    //    String abbreviationSilk;
    //    String abbreviationSheath;
    //    Integer sortId = 1;
    //    for (EcuSilk ecSilk : list) {
    //        abbreviationSilk = ecSilk.getAbbreviation();
    //        // log.info("abbreviationSilk + " + abbreviationSilk);
    //        if ("YJV".equals(abbreviationSilk)) {
    //            EcuSilk object;
    //            for (EcbSheath ecbSheath : list_sheath) {
    //                if ("YCB".equals(ecbSheath.getAbbreviation()) || "YCA".equals(ecbSheath.getAbbreviation())) {
    //                    continue;
    //                }
    //                object = new EcuSilk();
    //                abbreviationSheath = ecbSheath.getAbbreviation();
    //                if ("WDZA".equals(abbreviationSheath)
    //                        || "WDZB".equals(abbreviationSheath)
    //                        || "WDZC".equals(abbreviationSheath)) {
    //                    abbreviationSilk = "YJY";
    //                } else {
    //                    abbreviationSilk = "YJV";
    //                }
    //                if ("WDZC".equals(abbreviationSheath)) {
    //                    abbreviationSheath = "WDZ";
    //                }
    //                if (!"".equals(abbreviationSheath)) {
    //                    abbreviation = abbreviationSheath + "-" + abbreviationSilk;
    //                } else {
    //                    abbreviation = abbreviationSilk;
    //                }
    //                object.setSortId(sortId);
    //                object.setAbbreviation(abbreviation);
    //                object.setEcsId(ecSilk.getEcsId());
    //                list_new.add((sortId - 1), object);
    //                sortId++;
    //                // 22 是否带铠
    //                object = new EcuSilk();
    //                object.setSortId(sortId);
    //                if ("YJY".equals(abbreviationSilk)) {
    //                    object.setAbbreviation(abbreviation + "23");
    //                } else {
    //                    object.setAbbreviation(abbreviation + "22");
    //                }
    //                object.setEcsId(ecSilk.getEcsId());
    //                list_new.add((sortId - 1), object);
    //                sortId++;
    //                // N或NH
    //                object = new EcuSilk();
    //                object.setSortId(sortId);
    //                if (!"YJV".equals(abbreviation)) {
    //                    abbreviation = abbreviationSheath + "N-" + abbreviationSilk;
    //                    object.setAbbreviation(abbreviation);
    //                } else {
    //                    abbreviation = "NH-" + abbreviation;
    //                    object.setAbbreviation(abbreviation);
    //                }
    //                object.setEcsId(ecSilk.getEcsId());
    //                list_new.add((sortId - 1), object);
    //                sortId++;
    //                // 22 是否带铠
    //                object = new EcuSilk();
    //                object.setEcsId(ecSilk.getEcsId());
    //                object.setSortId(sortId);
    //                if ("YJY".equals(abbreviationSilk)) {
    //                    object.setAbbreviation(abbreviation + "23");
    //                } else {
    //                    object.setAbbreviation(abbreviation + "22");
    //                }
    //                list_new.add((sortId - 1), object);
    //                sortId++;
    //            }
    //        } else if ("YJLV".equals(abbreviationSilk)) {
    //            EcuSilk object;
    //            for (EcbSheath ecbSheath : list_sheath) {
    //                if ("YCB".equals(ecbSheath.getAbbreviation()) || "YCA".equals(ecbSheath.getAbbreviation())) {
    //                    continue;
    //                }
    //                object = new EcuSilk();
    //                abbreviationSheath = ecbSheath.getAbbreviation();
    //                if ("WDZC".equals(abbreviationSheath)) {
    //                    abbreviationSheath = "WDZ";
    //                }
    //                if ("WDZA".equals(abbreviationSheath) || "WDZB".equals(abbreviationSheath)) {
    //                    abbreviationSilk = "YJLY";
    //                } else {
    //                    abbreviationSilk = "YJLV";
    //                }
    //                if (!"".equals(abbreviationSheath)) {
    //                    abbreviation = abbreviationSheath + "-" + abbreviationSilk;
    //                } else {
    //                    abbreviation = abbreviationSilk;
    //                }
    //                object.setSortId(sortId);
    //                object.setAbbreviation(abbreviation);
    //                object.setEcsId(ecSilk.getEcsId());
    //                list_new.add((sortId - 1), object);
    //                sortId++;
    //                // 22 是否带铠
    //                object = new EcuSilk();
    //                object.setSortId(sortId);
    //                if ("YJLY".equals(abbreviationSilk)) {
    //                    object.setAbbreviation(abbreviation + "23");
    //                } else {
    //                    object.setAbbreviation(abbreviation + "22");
    //                }
    //                object.setEcsId(ecSilk.getEcsId());
    //                list_new.add((sortId - 1), object);
    //                sortId++;
    //                // N或NH
    //                object = new EcuSilk();
    //                object.setSortId(sortId);
    //                if (!"YJLV".equals(abbreviation)) {
    //                    abbreviation = abbreviationSheath + "N-" + abbreviationSilk;
    //                    object.setAbbreviation(abbreviation);
    //                } else {
    //                    abbreviation = "NH-" + abbreviation;
    //                    object.setAbbreviation(abbreviation);
    //                }
    //                object.setEcsId(ecSilk.getEcsId());
    //                list_new.add((sortId - 1), object);
    //                sortId++;
    //                // 22 是否带铠
    //                object = new EcuSilk();
    //                object.setSortId(sortId);
    //                if ("YJLY".equals(abbreviationSilk)) {
    //                    object.setAbbreviation(abbreviation + "23");
    //                } else {
    //                    object.setAbbreviation(abbreviation + "22");
    //                }
    //                object.setEcsId(ecSilk.getEcsId());
    //                list_new.add((sortId - 1), object);
    //                sortId++;
    //            }
    //        } else if ("BV".equals(abbreviationSilk)) {
    //            EcuSilk object;
    //            for (EcbSheath ecbSheath : list_sheath) {
    //                if ("ZC".equals(ecbSheath.getAbbreviation())
    //                        || "ZB".equals(ecbSheath.getAbbreviation())
    //                        || "ZA".equals(ecbSheath.getAbbreviation())
    //                        || "YCB".equals(ecbSheath.getAbbreviation())
    //                        || "YCA".equals(ecbSheath.getAbbreviation())) {
    //                    continue;
    //                }
    //                object = new EcuSilk();
    //                abbreviationSheath = ecbSheath.getAbbreviation();
    //                // log.info(abbreviationSheath);
    //                if ("WDZC".equals(abbreviationSheath)) {
    //                    abbreviationSheath = "WDZ";
    //                }
    //                if (!"".equals(abbreviationSheath)) {
    //                    abbreviation = abbreviationSheath + "-BYJ";
    //                } else {
    //                    abbreviation = abbreviationSilk;
    //                }
    //                object.setSortId(sortId);
    //                object.setAbbreviation(abbreviation);
    //                object.setEcsId(ecSilk.getEcsId());
    //                list_new.add((sortId - 1), object);
    //                sortId++;
    //                // N或NH
    //                object = new EcuSilk();
    //                object.setSortId(sortId);
    //                if (!"BV".equals(abbreviation)) {
    //                    abbreviation = abbreviationSheath + "N-BYJ";
    //                    object.setAbbreviation(abbreviation);
    //                } else {
    //                    abbreviation = "NH-" + abbreviation;
    //                    object.setAbbreviation(abbreviation);
    //                }
    //                object.setEcsId(ecSilk.getEcsId());
    //                list_new.add((sortId - 1), object);
    //                sortId++;
    //            }
    //        } else if ("BVR".equals(abbreviationSilk)) {
    //            EcuSilk object;
    //            for (EcbSheath ecbSheath : list_sheath) {
    //                if ("ZC".equals(ecbSheath.getAbbreviation()) || "ZB".equals(ecbSheath.getAbbreviation()) || "ZA".equals(ecbSheath.getAbbreviation()) || "YCB".equals(ecbSheath.getAbbreviation()) || "YCA".equals(ecbSheath.getAbbreviation())) {
    //                    continue;
    //                }
    //                object = new EcuSilk();
    //                abbreviationSheath = ecbSheath.getAbbreviation();
    //                // log.info(abbreviationSheath);
    //                if ("WDZC".equals(abbreviationSheath)) {
    //                    abbreviationSheath = "WDZ";
    //                }
    //                if (!"".equals(abbreviationSheath)) {
    //                    abbreviation = abbreviationSheath + "-BYJR";
    //                } else {
    //                    abbreviation = abbreviationSilk;
    //                }
    //                object.setSortId(sortId);
    //                object.setAbbreviation(abbreviation);
    //                object.setEcsId(ecSilk.getEcsId());
    //                list_new.add((sortId - 1), object);
    //                sortId++;
    //                // N或NH
    //                object = new EcuSilk();
    //                object.setSortId(sortId);
    //                if (!"BVR".equals(abbreviation)) {
    //                    abbreviation = abbreviationSheath + "N-BYJR";
    //                    object.setAbbreviation(abbreviation);
    //                } else {
    //                    abbreviation = "NH-" + abbreviation;
    //                    object.setAbbreviation(abbreviation);
    //                }
    //                object.setEcsId(ecSilk.getEcsId());
    //                list_new.add((sortId - 1), object);
    //                sortId++;
    //            }
    //        }
    //    }
    //    if (!"".equals(silkName)) {
    //        if ("YJV".equals(silkName)) {
    //            for (Integer i = 0; i < list_new.size(); i++) {
    //                if (!list_new.get(i).getAbbreviation().contains("YJV")
    //                        && !list_new.get(i).getAbbreviation().contains("YJY")) {
    //                    list_new.remove(i);
    //                    i--;
    //                }
    //            }
    //        } else if ("YJLV".equals(silkName)) {
    //            for (Integer i = 0; i < list_new.size(); i++) {
    //                if (!list_new.get(i).getAbbreviation().contains("YJLV")
    //                        && !list_new.get(i).getAbbreviation().contains("YJlY")) {
    //                    list_new.remove(i);
    //                    i--;
    //                }
    //            }
    //        } else if ("BV".equals(silkName)) {
    //            for (Integer i = 0; i < list_new.size(); i++) {
    //                if (!list_new.get(i).getAbbreviation().contains("BV")
    //                        && !list_new.get(i).getAbbreviation().contains("BYJ")) {
    //                    list_new.remove(i);
    //                    i--;
    //                }
    //            }
    //            for (Integer i = 0; i < list_new.size(); i++) {
    //                if (list_new.get(i).getAbbreviation().contains("BVR")
    //                        || list_new.get(i).getAbbreviation().contains("BYJR")) {
    //                    list_new.remove(i);
    //                    i--;
    //                }
    //            }
    //        } else if ("BVR".equals(silkName)) {
    //            for (Integer i = 0; i < list_new.size(); i++) {
    //                if (!list_new.get(i).getAbbreviation().contains("BVR")
    //                        && !list_new.get(i).getAbbreviation().contains("BYJR")) {
    //                    list_new.remove(i);
    //                    i--;
    //                }
    //            }
    //        }
    //    }
    //    // log.info(String.valueOf(list_new.size()));
    //    return list_new;
    //}


    public List<EcuSilk> getListStart() {
        EcuSilk record = new EcuSilk();
        record.setStartType(true);
        return ecuSilkService.getList(record);
    }

    // getObjectPassEcsId
    //public EcuSilk getObjectPassEcsId(Integer ecsId) {
    //    EcuSilk record = new EcuSilk();
    //    record.setEcsId(ecsId);
    //    return ecuSilkService.getObject(record);
    //}

    //// getEcsId
    //public Integer getEcsId(Integer ecuId, String sName) {
    //    Integer ecsId = 0;
    //    EcuSilk record = new EcuSilk();
    //    record.setStartType(true);
    //    List<EcuSilk> list = ecuSilkService.getList(record);
    //    List<EcuSilk> listAll = new ArrayList<>();
    //    for (EcuSilk ecSilk : list) {
    //        String silkName = ecSilk.getAbbreviation();
    //        List<EcuSilk> listNew = getListSilkName(ecuId, silkName);
    //        listAll.addAll(listNew);
    //    }
    //    for (EcuSilk ecSilk : listAll) {
    //        if (ecSilk.getAbbreviation().equals(sName)) {
    //            ecsId = ecSilk.getEcsId();
    //        }
    //    }
    //    return ecsId;
    //}
    //
    //// getListAllSilkName
    //public List<EcuSilk> getListAllSilkName(Integer ecuId) {
    //    EcuSilk record = new EcuSilk();
    //    record.setStartType(true);
    //    List<EcuSilk> list = ecuSilkService.getList(record);
    //    List<EcuSilk> listAll = new ArrayList<>();
    //    for (EcuSilk ecSilk : list) {
    //        String silkName = ecSilk.getAbbreviation();
    //        List<EcuSilk> listNew = getListSilkName(ecuId, silkName);
    //        listAll.addAll(listNew);
    //    }
    //    return listAll;
    //}

    public void save(EcuSilk ecuSilk) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        List<EcuSilk> list = ecuSilkService.list(ecuSilk);
        if (!list.isEmpty()) {
            throw new RuntimeException("当前名称已被占用");
        }
        EcuSilk object = ecuSilkService.getObject(null);
        Integer sortId = 1;
        if (ObjectUtil.isNotNull(object)) {
            sortId = object.getSortId();
        }
        ecuSilk.setSortId(sortId);
        ecuSilk.setStartType(true);
        ecuSilk.setEcuId(sysUser.getUserId());
        ecuSilk.setAddTime(new Date());
        ecuSilk.setUpdateTime(new Date());
        ecuSilkService.insert(ecuSilk);
    }

    public void sort(List<EcubSilkSortBo> bos) {
        for (EcubSilkSortBo bo : bos) {
            Integer ecbsId = bo.getEcusId();
            Integer sortId = bo.getSortId();
            EcuSilk record = new EcuSilk();
            record.setEcusId(ecbsId);
            record.setSortId(sortId);
            ecuSilkService.updateById(record);
        }
    }

    public String start(EcubSilkBaseBo bo) {
        Integer ecusId = bo.getEcusId();
        EcuSilk record = new EcuSilk();
        record.setEcusId(ecusId);
        EcuSilk silk = ecuSilkService.getObject(record);
        Boolean startType = silk.getStartType();
        String msg;
        if (!startType) {
            startType = true;
            msg = "数据启用成功";
        } else {
            startType = false;
            msg = "数据禁用成功";
        }
        record = new EcuSilk();
        record.setEcusId(silk.getEcusId());
        record.setStartType(startType);
        ecuSilkService.updateById(record);
        return msg;
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(EcubSilkBaseBo bo) {
        Integer ecusId = bo.getEcusId();
        EcuSilk record = new EcuSilk();
        record.setEcusId(ecusId);
        EcuSilk ecuSilk = ecuSilkService.getObject(record);
        Integer sortId = ecuSilk.getSortId();
        record = new EcuSilk();
        record.setSortId(sortId);
        List<EcuSilk> list = ecuSilkService.getList(record);
        Integer ecbs_id;
        for (EcuSilk ecSilk : list) {
            ecbs_id = ecSilk.getEcusId();
            sortId = ecSilk.getSortId() - 1;
            record.setEcusId(ecbs_id);
            record.setSortId(sortId);
            ecuSilkService.updateById(record);
        }
        record = new EcuSilk();
        record.setEcusId(ecusId);
        ecuSilkService.removeById(record);
    }
}
