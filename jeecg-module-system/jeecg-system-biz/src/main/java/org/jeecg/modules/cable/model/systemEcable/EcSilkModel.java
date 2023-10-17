package org.jeecg.modules.cable.model.systemEcable;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.systemEcable.silk.bo.EcbSilkBo;
import org.jeecg.modules.cable.controller.systemEcable.silk.bo.EcbSilkStartBo;
import org.jeecg.modules.cable.entity.systemEcable.EcSilk;
import org.jeecg.modules.cable.entity.systemEcable.EcbSheath;
import org.jeecg.modules.cable.service.price.EcSilkService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class EcSilkModel {
    @Resource
    EcSilkService ecSilkService;
    @Resource
    EcbSheathModel ecbSheathModel;

    //getList
    public List<EcSilk> getList(EcbSilkBo bo) {

        EcSilk record = new EcSilk();
        record.setStartType(bo.getStartType());
        List<EcSilk> list = ecSilkService.getList(record);
        return list;
    }

    //getListPassSilkName
    public List<EcSilk> getListPassSilkName(EcbSilkStartBo bo) {
        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        Integer ecuId = ecUser.getEcuId();
        String silkName = bo.getSilkName();
        List<EcSilk> list = getListSilkName(ecuId, silkName);
        return list;
    }

    //getListSilkName
    public List<EcSilk> getListSilkName(EcbSilkBo bo) {

        EcSilk record = new EcSilk();
        //获取当前用户id
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

    //getAllList
    public List<EcSilk> getAllList(int ecuId) {
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
    //getListSilkName 获取丝类型名称为报价页面提供数据
    public List<EcSilk> getListSilkName(int ecuId, String silkName) {
        List<EcSilk> list;
        List<EcSilk> list_new = new ArrayList<>();
        List<EcbSheath> list_sheath = ecbSheathModel.getListSilkName(ecuId);
        EcSilk record = new EcSilk();
        record.setStartType(true);
        list = ecSilkService.getList(record);
        String abbreviation;
        String abbreviationSilk;
        String abbreviationSheath;
        int sortId = 1;
        for (EcSilk ecSilk : list) {
            abbreviationSilk = ecSilk.getAbbreviation();
            //log.info("abbreviationSilk + " + abbreviationSilk);
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
                    //22 是否带凯
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
                    //N或NH
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
                    //22 是否带凯
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
                    //22 是否带凯
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
                    //N或NH
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
                    //22 是否带凯
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
                    //log.info(abbreviationSheath);
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
                    //N或NH
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
                    //log.info(abbreviationSheath);
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
                    //N或NH
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
                for (int i = 0; i < list_new.size(); i++) {
                    if (!list_new.get(i).getAbbreviation().contains("YJV")
                            && !list_new.get(i).getAbbreviation().contains("YJY")) {
                        list_new.remove(i);
                        i--;
                    }
                }
            } else if ("YJLV".equals(silkName)) {
                for (int i = 0; i < list_new.size(); i++) {
                    if (!list_new.get(i).getAbbreviation().contains("YJLV")
                            && !list_new.get(i).getAbbreviation().contains("YJlY")) {
                        list_new.remove(i);
                        i--;
                    }
                }
            } else if ("BV".equals(silkName)) {
                for (int i = 0; i < list_new.size(); i++) {
                    if (!list_new.get(i).getAbbreviation().contains("BV")
                            && !list_new.get(i).getAbbreviation().contains("BYJ")) {
                        list_new.remove(i);
                        i--;
                    }
                }
                for (int i = 0; i < list_new.size(); i++) {
                    if (list_new.get(i).getAbbreviation().contains("BVR")
                            || list_new.get(i).getAbbreviation().contains("BYJR")) {
                        list_new.remove(i);
                        i--;
                    }
                }
            } else if ("BVR".equals(silkName)) {
                for (int i = 0; i < list_new.size(); i++) {
                    if (!list_new.get(i).getAbbreviation().contains("BVR")
                            && !list_new.get(i).getAbbreviation().contains("BYJR")) {
                        list_new.remove(i);
                        i--;
                    }
                }
            }
        }
        //log.info(String.valueOf(list_new.size()));
        return list_new;
    }

    //getListStart
    public List<EcSilk> getListStart() {
        EcSilk record = new EcSilk();
        record.setStartType(true);
        return ecSilkService.getList(record);
    }

    //getObjectPassEcsId
    public EcSilk getObjectPassEcsId(int ecsId) {
        EcSilk record = new EcSilk();
        record.setEcsId(ecsId);
        return ecSilkService.getObject(record);
    }

    //getEcsId
    public int getEcsId(int ecuId, String sName) {
        int ecsId = 0;
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

    //getListAllSilkName
    public List<EcSilk> getListAllSilkName(int ecuId) {
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
}
