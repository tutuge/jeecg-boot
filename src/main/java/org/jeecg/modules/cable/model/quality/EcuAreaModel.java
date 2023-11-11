package org.jeecg.modules.cable.model.quality;

import cn.hutool.core.util.ObjectUtil;
import jakarta.annotation.Resource;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.quality.uarea.bo.AreaBo;
import org.jeecg.modules.cable.controller.quality.uarea.bo.AreaSortBo;
import org.jeecg.modules.cable.controller.quality.uarea.bo.EcuAreaBo;
import org.jeecg.modules.cable.controller.quality.uarea.bo.UAreaBo;
import org.jeecg.modules.cable.entity.quality.EcuArea;
import org.jeecg.modules.cable.service.quality.EcuAreaService;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
            record.setEffectTime(System.currentTimeMillis());
            System.out.println(CommonFunction.getGson().toJson(record));
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
}
