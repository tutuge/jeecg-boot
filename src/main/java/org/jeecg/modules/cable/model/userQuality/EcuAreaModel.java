package org.jeecg.modules.cable.model.userQuality;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ObjectUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.userQuality.uarea.bo.AreaBo;
import org.jeecg.modules.cable.controller.userQuality.uarea.bo.AreaSortBo;
import org.jeecg.modules.cable.controller.userQuality.uarea.bo.EcuAreaBo;
import org.jeecg.modules.cable.controller.userQuality.uarea.bo.UAreaBo;
import org.jeecg.modules.cable.entity.userQuality.EcuArea;
import org.jeecg.modules.cable.service.userQuality.EcuAreaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EcuAreaModel {
    @Resource
    EcuAreaService ecuAreaService;

    public List<EcuArea> getList(UAreaBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        Integer ecqulId = bo.getEcqulId();
        EcuArea record = new EcuArea();
        record.setStartType(bo.getStartType());
        record.setEcCompanyId(sysUser.getEcCompanyId());
        record.setEcqulId(ecqulId);
        List<EcuArea> list = ecuAreaService.getList(record);
        List<EcuArea> collect = list.stream().distinct().collect(Collectors.toList());
        return collect;
    }


    public EcuArea getObject(AreaBo bo) {
        EcuArea recordEcuArea = new EcuArea();
        if (bo.getEcuaId() != null) {
            Integer ecuaId = bo.getEcuaId();
            recordEcuArea.setEcuaId(ecuaId);
        }
        return ecuAreaService.getObject(recordEcuArea);
    }


    @Transactional(rollbackFor = Exception.class)
    public String deal(EcuAreaBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        Integer ecuaId = bo.getEcuaId();
        Integer ecqulId = bo.getEcqulId();
        String areaStr = bo.getAreaStr();
        EcuArea record = new EcuArea();
        record.setEcqulId(ecqulId);
        record.setEcCompanyId(sysUser.getEcCompanyId());
        record.setAreaStr(areaStr);
        EcuArea ecuArea = ecuAreaService.getObjectPassAreaStr(record);
        String msg;
        if (ecuArea != null) {
            throw new RuntimeException("截面积已占用");
        }
        if (ObjectUtil.isNull(ecuaId)) {// 插入
            int sortId = 1;
            ecuArea = ecuAreaService.getLatestObject(record);
            if (ecuArea != null) {
                sortId = ecuArea.getSortId() + 1;
            }
            record = new EcuArea();
            record.setEcCompanyId(sysUser.getEcCompanyId());
            record.setEcqulId(ecqulId);
            record.setStartType(true);
            record.setSortId(sortId);
            record.setAreaStr(areaStr);
            record.setEffectTime(new Date());
            ecuAreaService.insert(record);
            msg = "正常插入数据";
        } else {// 更新
            record.setEcuaId(ecuaId);
            record.setAreaStr(areaStr);
            ecuAreaService.updateByPrimaryKeySelective(record);
            msg = "正常更新数据";
        }
        return msg;
    }

    @Transactional(rollbackFor = Exception.class)
    public void sort(List<AreaSortBo> bos) {
        for (AreaSortBo bo : bos) {
            Integer ecuaId = bo.getEcuaId();
            Integer sortId = bo.getSortId();
            EcuArea record = new EcuArea();
            record.setEcuaId(ecuaId);
            record.setSortId(sortId);
            ecuAreaService.updateByPrimaryKeySelective(record);
        }
    }


    public String start(AreaBo bo) {
        Integer ecuaId = bo.getEcuaId();
        EcuArea recordEcuArea = new EcuArea();
        recordEcuArea.setEcuaId(ecuaId);
        EcuArea ecuArea = ecuAreaService.getObject(recordEcuArea);
        Boolean startType = ecuArea.getStartType();
        String msg;
        if (!startType) {
            startType = true;
            msg = "数据启用成功";
        } else {
            startType = false;
            msg = "数据禁用成功";
        }
        EcuArea record = new EcuArea();
        record.setEcuaId(ecuaId);
        record.setStartType(startType);
        ecuAreaService.updateByPrimaryKeySelective(record);
        return msg;
    }

    public Boolean isExistsPassEcqulId(Integer ecCompanyId, Integer ecqulId, String areaStr) {
        //boolean isExists = false;
        EcuArea record = new EcuArea();
        record.setEcCompanyId(ecCompanyId);
        record.setEcqulId(ecqulId);
        record.setAreaStr(areaStr);
        EcuArea ecdArea = ecuAreaService.getObject(record);
        return ObjUtil.isNotNull(ecdArea);
        //log.info("ecdArea + " + CommonFunction.getGson().toJson(ecdArea));
        ////String txtContent = TxtUtils.readTxtFile(txtPath + ecdArea.getTxtUrl()).get(1);
        //List<EcuArea> listArea = CommonFunction.getGson().fromJson(txtContent, new TypeToken<List<EcuArea>>() {
        //}.getType());
        //for (EcuArea ecuArea : listArea) {
        //    if (ecuArea.getAreaStr().equals(areaStr)) {
        //        isExists = true;
        //        break;
        //    }
        //}
        //return isExists;
    }
}
