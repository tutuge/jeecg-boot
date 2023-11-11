package org.jeecg.modules.cable.model.userCommon;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ObjectUtil;
import jakarta.annotation.Resource;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.userCommon.store.bo.EcbuStoreBaseBo;
import org.jeecg.modules.cable.controller.userCommon.store.bo.EcbuStoreDealBo;
import org.jeecg.modules.cable.controller.userCommon.store.bo.EcbuStoreSortBo;
import org.jeecg.modules.cable.controller.userCommon.store.bo.StoreBo;
import org.jeecg.modules.cable.controller.userCommon.store.vo.StoreVo;
import org.jeecg.modules.cable.entity.price.EcuQuoted;
import org.jeecg.modules.cable.entity.price.EcuqInput;
import org.jeecg.modules.cable.entity.userCommon.EcbuStore;
import org.jeecg.modules.cable.model.efficiency.EcdCollectModel;
import org.jeecg.modules.cable.service.price.EcuQuotedService;
import org.jeecg.modules.cable.service.price.EcuqInputService;
import org.jeecg.modules.cable.service.userCommon.EcbuStoreService;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private EcuqInputService ecuqInputService;
    @Resource
    private EcuQuotedService ecuQuotedService;


    public StoreVo getListAndCount(StoreBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcbuStore record = new EcbuStore();
        record.setStartType(bo.getStartType());
        record.setEcCompanyId(sysUser.getEcCompanyId());
        List<EcbuStore> list = ecbuStoreService.getList(record);
        Long count = ecbuStoreService.getCount(record);
        return new StoreVo(list, count);
    }


    public EcbuStore getObject(EcbuStoreBaseBo bo) {
        EcbuStore record = new EcbuStore();
        Integer ecbusId = bo.getEcbusId();
        record.setEcbusId(ecbusId);
        return ecbuStoreService.getObject(record);
    }

    // deal
    @Transactional(rollbackFor = Exception.class)
    public String deal(EcbuStoreDealBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        Integer ecbusId = bo.getEcbusId();
        String storeName = bo.getStoreName();
        BigDecimal percentCopper = bo.getPercentCopper();
        BigDecimal percentAluminium = bo.getPercentAluminium();
        BigDecimal dunitMoney = bo.getDunitMoney();
        String description = bo.getDescription();

        EcbuStore record = new EcbuStore();
        record.setEcbusId(ecbusId);
        record.setEcCompanyId(sysUser.getEcCompanyId());
        record.setStoreName(storeName);
        String msg = "";
        EcbuStore ecbuStore = ecbuStoreService.getObjectPassStoreName(record);
        if (ecbuStore != null) {
            throw new RuntimeException("仓库名称已占用");
        }
        if (ObjectUtil.isNull(ecbusId)) {// 插入
            int sortId = 1;
            ecbuStore = ecbuStoreService.getLatestObject(record);
            if (ecbuStore != null) {
                sortId = ecbuStore.getSortId() + 1;
            }
            record = new EcbuStore();
            record.setEcCompanyId(sysUser.getEcCompanyId());
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
        } else {// 更新
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
        loadData(sysUser.getEcCompanyId());// 加载load为集成数据
        return msg;
    }


    public void sort(List<EcbuStoreSortBo> bos) {
        for (EcbuStoreSortBo bo : bos) {
            Integer ecbusId = bo.getEcbusId();
            Integer sortId = bo.getSortId();
            EcbuStore record = new EcbuStore();
            record.setEcbusId(ecbusId);
            record.setSortId(sortId);
            ecbuStoreService.update(record);
        }
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        loadData(sysUser.getEcCompanyId());// 加载load为集成数据
    }


    @Transactional(rollbackFor = Exception.class)
    public void delete(EcbuStoreBaseBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        Integer ecCompanyId = sysUser.getEcCompanyId();
        Integer ecbusId = bo.getEcbusId();
        //查询对应的报价单和报价单明细中是否使用了仓库
        EcuqInput input = new EcuqInput();
        input.setEcbusId(ecbusId);
        Long count = ecuqInputService.getCount(input);
        EcuQuoted ecuQuoted = new EcuQuoted();
        ecuQuoted.setDeliveryStoreId(ecbusId);
        Long count1 = ecuQuotedService.selectCount(ecuQuoted);
        if (count > 0 || count1 > 0) {
            throw new RuntimeException("当前仓库数据被使用，无法删除");
        }

        EcbuStore record = new EcbuStore();
        record.setEcbusId(ecbusId);
        EcbuStore ecbuStore = ecbuStoreService.getObject(record);
        if (ObjUtil.isNull(ecbuStore)) {
            throw new RuntimeException("仓库数据不存在");
        }
        if (ecbuStore.getDefaultType()) {
            throw new RuntimeException("请先将仓库设置为非默认状态");
        }
        Integer sortId = ecbuStore.getSortId();
        ecbuStoreService.reduceSort(ecCompanyId, sortId);
        record = new EcbuStore();
        record.setEcbusId(ecbusId);
        ecbuStoreService.delete(record);
        loadData(ecCompanyId);// 加载load为集成数据
    }

    /**
     * 设置默认项
     *
     * @param bo
     */
    @Transactional(rollbackFor = Exception.class)
    public void dealDefault(EcbuStoreBaseBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        Integer ecbusId = bo.getEcbusId();
        EcbuStore record = new EcbuStore();
        record.setEcCompanyId(sysUser.getEcCompanyId());
        ecbuStoreService.updateNotDefaultPassEcCompanyId(record);
        // 设置为默认项
        record.setEcbusId(ecbusId);
        record.setDefaultType(true);
        ecbuStoreService.update(record);
        loadData(sysUser.getEcCompanyId());// 加载load为集成数据
    }


    public String start(EcbuStoreBaseBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        Integer ecbusId = bo.getEcbusId();
        EcbuStore record = new EcbuStore();
        record.setEcbusId(ecbusId);
        EcbuStore ecbuStore = ecbuStoreService.getObject(record);
        Boolean startType = ecbuStore.getStartType();
        String msg = "";
        if (!startType) {
            startType = true;
            msg = "数据启用成功";
        } else {
            startType = false;
            msg = "数据禁用成功";
        }
        if (!startType && ecbuStore.getDefaultType()) {
            throw new RuntimeException("当前仓库是默认仓库，无法停用");
        }
        record = new EcbuStore();
        record.setEcbusId(ecbuStore.getEcbusId());
        record.setStartType(startType);
        // System.out.println(CommonFunction.getGson().toJson(record));
        ecbuStoreService.update(record);
        loadData(sysUser.getEcCompanyId());// 加载load为集成数据
        return msg;
    }

    // getDefaultStore
    public EcbuStore getDefaultStore() {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcbuStore record = new EcbuStore();
        record.setEcCompanyId(sysUser.getEcCompanyId());
        record.setDefaultType(true);
        return ecbuStoreService.getObject(record);
    }

    /***===数据模型===***/
    // 加截集成数据
    public void loadData(Integer ecCompanyId) {
        EcbuStore record = new EcbuStore();
        record.setEcCompanyId(ecCompanyId);
        record.setStartType(true);
        List<EcbuStore> list = ecbuStoreService.getList(record);
        List<String> txtList = new ArrayList<>();
        txtList.add(CommonFunction.getGson().toJson(list));
        ecdCollectModel.deal(ecCompanyId, 1, txtList);
    }

    /***===数据模型===***/
    // dealDefault 加载默认仓库
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

    // getObjectDefaultPassEcCompanyId 获取公司下的默认仓库
    public EcbuStore getObjectDefaultPassEcCompanyId(EcbuStore record) {
        return ecbuStoreService.getObject(record);
    }

    // deletePassEcCompanyId
    public void deletePassEcCompanyId(Integer ecCompanyId) {
        EcbuStore record = new EcbuStore();
        record.setEcCompanyId(ecCompanyId);
        ecbuStoreService.delete(record);
    }

    // getObjectPassEcCompanyIdAndStoreName
    public EcbuStore getObjectPassEcCompanyIdAndStoreName(Integer ecCompanyId, String storeName) {
        EcbuStore record = new EcbuStore();
        record.setEcCompanyId(ecCompanyId);
        record.setStoreName(storeName);
        return ecbuStoreService.getObject(record);
    }

}
