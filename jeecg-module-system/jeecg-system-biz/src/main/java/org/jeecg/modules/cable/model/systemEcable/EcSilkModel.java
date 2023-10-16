package org.jeecg.modules.cable.model.systemEcable;

import org.jeecg.modules.cable.entity.systemEcable.EcSilk;
import org.jeecg.modules.cable.entity.systemEcable.EcbSheath;
import org.jeecg.modules.cable.model.user.EcuLoginModel;
import org.jeecg.modules.cable.service.price.EcSilkService;
import org.jeecg.modules.cable.tools.CommonFunction;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class EcSilkModel {
    @Resource
    EcuLoginModel ecuLoginModel;
    @Resource
    EcSilkService ecSilkService;
    @Resource
    EcbSheathModel ecbSheathModel;

    //getList
    public Map<String, Object> getList(HttpServletRequest request) {
        Map<String, Object> map;
        int status;
        String code;
        String msg;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            EcSilk record = new EcSilk();
            if (request.getParameter("startType") != null) {
                boolean startType = true;
                if (!"0".equals(request.getParameter("startType"))) {
                    if ("2".equals(request.getParameter("startType"))) {
                        startType = false;
                    }
                    record.setStartType(startType);
                }
            }
            List<EcSilk> list = ecSilkService.getList(record);
            map.put("list", list);
            status = 3;//正常获取列表
            code = "200";
            msg = "正常获取列表";
            CommonFunction.getCommonMap(map, status, code, msg);
        }
        return map;
    }

    //getListPassSilkName
    public Map<String, Object> getListPassSilkName(HttpServletRequest request) {
        Map<String, Object> map;
        int status;
        String code;
        String msg;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            String silkName = request.getParameter("silkName");
            List<EcSilk> list = getListSilkName(ecuId, silkName);
            map.put("list", list);
            status = 3;//正常获取列表
            code = "200";
            msg = "正常获取列表";
            CommonFunction.getCommonMap(map, status, code, msg);
        }
        return map;
    }

    //getListSilkName
    public Map<String, Object> getListSilkName(HttpServletRequest request) {
        Map<String, Object> map;
        int status;
        String code;
        String msg;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            EcSilk record = new EcSilk();
            if (request.getParameter("startType") != null) {
                boolean startType = true;
                if (!"0".equals(request.getParameter("startType"))) {
                    if ("2".equals(request.getParameter("startType"))) {
                        startType = false;
                    }
                    record.setStartType(startType);
                }
            }
            List<EcSilk> list = ecSilkService.getList(record);
            List<EcSilk> listAll = new ArrayList<>();
            for (EcSilk ecSilk : list) {
                String silkName = ecSilk.getAbbreviation();
                List<EcSilk> listNew = getListSilkName(ecuId, silkName);
                listAll.addAll(listNew);
            }
            map.put("list", listAll);
            status = 3;//正常获取列表
            code = "200";
            msg = "正常获取列表";
            CommonFunction.getCommonMap(map, status, code, msg);
        }
        return map;
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
