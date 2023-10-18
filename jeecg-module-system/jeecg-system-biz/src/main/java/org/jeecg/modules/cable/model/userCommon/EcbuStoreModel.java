package org.jeecg.modules.cable.model.userCommon;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.userCommon.store.bo.StoreBo;
import org.jeecg.modules.cable.controller.userCommon.store.vo.StoreVo;
import org.jeecg.modules.cable.entity.userCommon.EcbuStore;
import org.jeecg.modules.cable.model.efficiency.EcdCollectModel;
import org.jeecg.modules.cable.service.user.EcUserService;
import org.jeecg.modules.cable.service.userCommon.EcbuStoreService;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class EcbuStoreModel {
    @Resource
    EcbuStoreService ecbuStoreService;
    @Resource
    EcdCollectModel ecdCollectModel;
    @Resource
    EcUserService ecUserService;//用户

    //getListAndCount
    public StoreVo getListAndCount(StoreBo bo) {
        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        EcbuStore record = new EcbuStore();
        record.setStartType(bo.getStartType());
        record.setEcCompanyId(ecUser.getEcCompanyId());
        List<EcbuStore> list = ecbuStoreService.getList(record);
        long count = ecbuStoreService.getCount(record);

        return new StoreVo(list, count);

    }

    //getObject
    public EcbuStore getObject(HttpServletRequest request) {

        EcbuStore record = new EcbuStore();
        if (request.getParameter("ecbusId") != null) {
            int ecbusId = Integer.parseInt(request.getParameter("ecbusId"));
            record.setEcbusId(ecbusId);
        }
        return ecbuStoreService.getObject(record);

    }

    //deal
    public String deal(HttpServletRequest request) {
        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        int ecbusId = Integer.parseInt(request.getParameter("ecbusId"));
        String storeName = request.getParameter("storeName");
        BigDecimal percentCopper = new BigDecimal(request.getParameter("percentCopper"));
        BigDecimal percentAluminium = new BigDecimal(request.getParameter("percentAluminium"));
        BigDecimal dunitMoney = new BigDecimal(request.getParameter("dunitMoney"));
        String description = request.getParameter("description");
        EcbuStore record = new EcbuStore();
        record.setEcbusId(ecbusId);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        record.setStoreName(storeName);
        String msg = "";
        EcbuStore ecbuStore = ecbuStoreService.getObjectPassStoreName(record);
        if (ecbuStore != null) {

            throw new RuntimeException("仓库名称已占用");
        } else {
            if (ecbusId == 0) {//插入
                int sortId = 1;
                ecbuStore = ecbuStoreService.getLatestObject(record);
                if (ecbuStore != null) {
                    sortId = ecbuStore.getSortId() + 1;
                }
                record = new EcbuStore();
                record.setEcCompanyId(ecUser.getEcCompanyId());
                record.setStoreName(storeName);
                record.setSortId(sortId);
                record.setStartType(false);
                record.setDefaultType(false);
                record.setPercentCopper(percentCopper);
                record.setPercentAluminium(percentAluminium);
                record.setDunitMoney(dunitMoney);
                record.setDescription(description);
                ecbuStoreService.insert(record);
                msg = "正常插入数据";
            } else {//更新
                record = new EcbuStore();
                record.setEcbusId(ecbusId);
                record.setStoreName(storeName);
                record.setPercentCopper(percentCopper);
                record.setPercentAluminium(percentAluminium);
                record.setDunitMoney(dunitMoney);
                record.setDescription(description);
                ecbuStoreService.update(record);
                msg = "正常更新数据";
            }
            loadData(ecUser.getEcCompanyId());//加载load为集成数据
        }

        return msg;
    }

    //sort
    public void sort(HttpServletRequest request) {
        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        int ecbusId = Integer.parseInt(request.getParameter("ecbusId"));
        int sortId = Integer.parseInt(request.getParameter("sortId"));
        EcbuStore record = new EcbuStore();
        record.setEcbusId(ecbusId);
        record.setSortId(sortId);
        ecbuStoreService.update(record);
        loadData(ecUser.getEcCompanyId());//加载load为集成数据

    }

    //delete
    public void delete(HttpServletRequest request) {
        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        int ecbusId = Integer.parseInt(request.getParameter("ecbusId"));
        EcbuStore record = new EcbuStore();
        record.setEcbusId(ecbusId);
        EcbuStore ecbuStore = ecbuStoreService.getObject(record);
        int sortId = ecbuStore.getSortId();
        record = new EcbuStore();
        record.setSortId(sortId);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        List<EcbuStore> list = ecbuStoreService.getListGreaterThanSortId(record);
        int ecbus_id;
        for (EcbuStore ecbu_store : list) {
            ecbus_id = ecbu_store.getEcbusId();
            sortId = ecbu_store.getSortId() - 1;
            record.setEcbusId(ecbus_id);
            record.setSortId(sortId);
            ecbuStoreService.update(record);
        }
        record = new EcbuStore();
        record.setEcbusId(ecbusId);
        ecbuStoreService.delete(record);
        loadData(ecUser.getEcCompanyId());//加载load为集成数据
    }

    //dealDefault 设置默认项
    public void dealDefault(HttpServletRequest request) {
        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        int ecbusId = Integer.parseInt(request.getParameter("ecbusId"));
        EcbuStore record = new EcbuStore();
        record.setEcCompanyId(ecUser.getEcCompanyId());
        ecbuStoreService.updateNotDefaultPassEcCompanyId(record);
        //设置为默认项
        record.setEcbusId(ecbusId);
        record.setDefaultType(true);
        ecbuStoreService.update(record);
        loadData(ecUser.getEcCompanyId());//加载load为集成数据
    }

    //start
    public String start(HttpServletRequest request) {
        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        int ecbusId = Integer.parseInt(request.getParameter("ecbusId"));
        EcbuStore record = new EcbuStore();
        record.setEcbusId(ecbusId);
        EcbuStore ecbuStore = ecbuStoreService.getObject(record);
        boolean startType = ecbuStore.getStartType();
        String msg = "";
        if (!startType) {
            startType = true;
            msg = "数据启用成功";
        } else {
            startType = false;
            msg = "数据禁用成功";
        }
        record = new EcbuStore();
        record.setEcbusId(ecbuStore.getEcbusId());
        record.setStartType(startType);
        //System.out.println(CommonFunction.getGson().toJson(record));
        ecbuStoreService.update(record);
        loadData(ecUser.getEcCompanyId());//加载load为集成数据
        return msg;
    }

    //getDefaultStore
    public EcbuStore getDefaultStore(HttpServletRequest request) {
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        EcUser recordEcUser = new EcUser();
        recordEcUser.setEcuId(ecuId);
        EcUser ecUser = ecUserService.getObject(recordEcUser);
        EcbuStore record = new EcbuStore();
        record.setEcCompanyId(ecUser.getEcCompanyId());
        record.setDefaultType(true);
        return ecbuStoreService.getObject(record);
    }

    /***===数据模型===***/
    //加截集成数据
    public void loadData(int ecCompanyId) {
        EcbuStore record = new EcbuStore();
        record.setEcCompanyId(ecCompanyId);
        record.setStartType(true);
        List<EcbuStore> list = ecbuStoreService.getList(record);
        List<String> txtList = new ArrayList<>();
        txtList.add(CommonFunction.getGson().toJson(list));
        System.out.println();
        ecdCollectModel.deal(ecCompanyId, 1, txtList);
    }

    /***===数据模型===***/
    //dealDefault 加载默认仓库
    public void dealDefault(EcbuStore record) {
        EcbuStore recordEcbuStore = new EcbuStore();
        recordEcbuStore.setEcCompanyId(record.getEcCompanyId());
        recordEcbuStore.setStoreName(record.getStoreName());
        EcbuStore ecbuStore = ecbuStoreService.getObject(recordEcbuStore);
        if (ecbuStore != null) {
            ecbuStoreService.update(record);
        } else {
            ecbuStoreService.insert(record);
        }
    }

    //getObjectDefaultPassEcCompanyId 获取公司下的默认仓库
    public EcbuStore getObjectDefaultPassEcCompanyId(EcbuStore record) {
        return ecbuStoreService.getObject(record);
    }

    //deletePassEcCompanyId
    public void deletePassEcCompanyId(int ecCompanyId) {
        EcbuStore record = new EcbuStore();
        record.setEcCompanyId(ecCompanyId);
        ecbuStoreService.delete(record);
    }

    //getObjectPassEcCompanyIdAndStoreName
    public EcbuStore getObjectPassEcCompanyIdAndStoreName(int ecCompanyId, String storeName) {
        EcbuStore record = new EcbuStore();
        record.setEcCompanyId(ecCompanyId);
        record.setStoreName(storeName);
        return ecbuStoreService.getObject(record);
    }

}
