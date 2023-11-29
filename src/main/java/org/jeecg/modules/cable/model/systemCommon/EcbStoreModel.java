package org.jeecg.modules.cable.model.systemCommon;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ObjectUtil;
import jakarta.annotation.Resource;
import org.jeecg.modules.cable.controller.systemCommon.store.bo.EcbStoreBaseBo;
import org.jeecg.modules.cable.controller.systemCommon.store.bo.EcbStoreDealBo;
import org.jeecg.modules.cable.controller.systemCommon.store.bo.EcbStoreSortBo;
import org.jeecg.modules.cable.controller.systemCommon.store.bo.StoreBo;
import org.jeecg.modules.cable.controller.systemCommon.store.vo.StoreVo;
import org.jeecg.modules.cable.entity.systemCommon.EcbStore;
import org.jeecg.modules.cable.service.systemCommon.EcbStoreService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class EcbStoreModel {
    @Resource
    EcbStoreService ecbStoreService;


    public StoreVo getListAndCount(StoreBo bo) {
        EcbStore record = new EcbStore();
        record.setStartType(bo.getStartType());
        List<EcbStore> list = ecbStoreService.getList(record);
        Long count = ecbStoreService.getCount(record);
        return new StoreVo(list, count);
    }

    public List<EcbStore> getList(EcbStore store) {
        return ecbStoreService.getList(store);
    }


    public EcbStore getObject(EcbStoreBaseBo bo) {
        EcbStore record = new EcbStore();
        Integer ecbsId = bo.getEcbsId();
        record.setEcbsId(ecbsId);
        return ecbStoreService.getObject(record);
    }

    // deal
    @Transactional(rollbackFor = Exception.class)
    public String deal(EcbStoreDealBo bo) {
        Integer ecbsId = bo.getEcbsId();
        String storeName = bo.getStoreName();
        BigDecimal percentCopper = bo.getPercentCopper();
        BigDecimal percentAluminium = bo.getPercentAluminium();
        BigDecimal dunitMoney = bo.getDunitMoney();
        String description = bo.getDescription();
        EcbStore record = new EcbStore();
        record.setEcbsId(ecbsId);

        record.setStoreName(storeName);
        String msg = "";
        EcbStore ecbuStore = ecbStoreService.getObjectPassStoreName(record);
        if (ecbuStore != null) {
            throw new RuntimeException("仓库名称已占用");
        }
        if (ObjectUtil.isNull(ecbsId)) {// 插入
            int sortId = 1;
            ecbuStore = ecbStoreService.getLatestObject(record);
            if (ecbuStore != null) {
                sortId = ecbuStore.getSortId() + 1;
            }
            record = new EcbStore();
            record.setStoreName(storeName);
            record.setSortId(sortId);
            record.setStartType(false);
            record.setDefaultType(false);
            record.setPercentCopper(percentCopper);
            record.setPercentAluminium(percentAluminium);
            record.setDunitMoney(dunitMoney);
            record.setDescription(description);
            ecbStoreService.insert(record);
            msg = "正常插入数据";
        } else {// 更新
            record = new EcbStore();
            record.setEcbsId(ecbsId);
            record.setStoreName(storeName);
            record.setPercentCopper(percentCopper);
            record.setPercentAluminium(percentAluminium);
            record.setDunitMoney(dunitMoney);
            record.setDescription(description);
            ecbStoreService.update(record);
            msg = "正常更新数据";
        }
        return msg;
    }


    @Transactional(rollbackFor = Exception.class)
    public void sort(List<EcbStoreSortBo> bos) {
        for (EcbStoreSortBo bo : bos) {
            Integer ecbsId = bo.getEcbsId();
            Integer sortId = bo.getSortId();
            EcbStore record = new EcbStore();
            record.setEcbsId(ecbsId);
            record.setSortId(sortId);
            ecbStoreService.update(record);
        }
    }


    @Transactional(rollbackFor = Exception.class)
    public void delete(EcbStoreBaseBo bo) {
        Integer ecbsId = bo.getEcbsId();
        EcbStore record = new EcbStore();
        record.setEcbsId(ecbsId);
        EcbStore ecbuStore = ecbStoreService.getObject(record);
        if (ObjUtil.isNull(ecbuStore)) {
            throw new RuntimeException("仓库数据不存在");
        }
        if (ecbuStore.getDefaultType()) {
            throw new RuntimeException("请先将仓库设置为非默认状态");
        }
        Integer sortId = ecbuStore.getSortId();
        ecbStoreService.reduceSort(sortId);
        record = new EcbStore();
        record.setEcbsId(ecbsId);
        ecbStoreService.delete(record);
    }

    /**
     * 设置默认项
     *
     * @param bo
     */
    @Transactional(rollbackFor = Exception.class)
    public void dealDefault(EcbStoreBaseBo bo) {
        Integer ecbsId = bo.getEcbsId();
        ecbStoreService.updateNotDefault();
        // 设置为默认项
        EcbStore record = new EcbStore();
        record.setEcbsId(ecbsId);
        record.setDefaultType(true);
        ecbStoreService.update(record);

    }


    public String start(EcbStoreBaseBo bo) {
        Integer ecbsId = bo.getEcbsId();
        EcbStore record = new EcbStore();
        record.setEcbsId(ecbsId);
        EcbStore ecbuStore = ecbStoreService.getObject(record);
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
        record = new EcbStore();
        record.setEcbsId(ecbuStore.getEcbsId());
        record.setStartType(startType);
        ecbStoreService.update(record);
        return msg;
    }

    // getDefaultStore
    public EcbStore getDefaultStore() {
        EcbStore record = new EcbStore();
        record.setDefaultType(true);
        return ecbStoreService.getObject(record);
    }
}
