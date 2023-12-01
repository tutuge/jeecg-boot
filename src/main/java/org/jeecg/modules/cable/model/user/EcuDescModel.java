package org.jeecg.modules.cable.model.user;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.user.udesc.bo.EcuDescBo;
import org.jeecg.modules.cable.controller.user.udesc.bo.EcuDescDealBo;
import org.jeecg.modules.cable.controller.user.udesc.bo.EcuDescPageBo;
import org.jeecg.modules.cable.controller.user.udesc.bo.EcuDescSortBo;
import org.jeecg.modules.cable.controller.user.udesc.vo.EcuDescVo;
import org.jeecg.modules.cable.controller.user.udesc.vo.UDescListVo;
import org.jeecg.modules.cable.entity.user.EcuDesc;
import org.jeecg.modules.cable.entity.userEcable.EcuSilkModel;
import org.jeecg.modules.cable.service.user.EcuDescService;
import org.jeecg.modules.cable.service.userEcable.EcuSilkModelService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EcuDescModel {
    @Resource
    private EcuDescService ecuDescService;

    @Resource
    private EcuSilkModelService ecuSilkModelService;


    public UDescListVo getList(EcuDescPageBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        Integer ecuId = sysUser.getUserId();
        EcuDesc record = new EcuDesc();
        record.setEcuId(ecuId);
        BeanUtils.copyProperties(bo, record);
        record.setStartType(bo.getStartType());
        if (bo.getPageNum() != null) {
            Integer pageNumber = bo.getPageSize();
            Integer startNumber = (bo.getPageNum() - 1) * pageNumber;
            record.setStartNumber(startNumber);
            record.setPageNumber(pageNumber);
        }
        List<EcuDesc> list = ecuDescService.getList(record);
        List<EcuDescVo> res = convert(list);
        long count = ecuDescService.getCount(record);
        return new UDescListVo(res, count);
    }

    /**
     * 转换vo
     *
     * @param list 原始数据
     * @return 增加型号list
     */
    private List<EcuDescVo> convert(List<EcuDesc> list) {
        // 将型号的id转换成数字的集合再进行数据库查询
        Set<Integer> ids = new HashSet<>();
        List<List<Integer>> sids = new ArrayList<>();
        // 最终结果
        List<EcuDescVo> res = new ArrayList<>();
        for (EcuDesc desc : list) {
            EcuDescVo vo = new EcuDescVo();
            BeanUtils.copyProperties(desc, vo);
            res.add(vo);
            String s = desc.getEcusmId();
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
            List<EcuSilkModel> ecSilks = ecuSilkModelService.listByIds(ids);
            Map<Integer, EcuSilkModel> map = ecSilks.stream().collect(Collectors.toMap(EcuSilkModel::getEcusmId, v -> v));
            for (int i = 0; i < res.size(); i++) {
                EcuDescVo vo = res.get(i);
                List<Integer> integers = sids.get(i);
                List<EcuSilkModel> silkModels = new ArrayList<>();
                if (!integers.isEmpty()) {
                    for (Integer i1 : integers) {
                        EcuSilkModel ecuSilkModel = map.get(i1);
                        if (ObjUtil.isNotNull(ecuSilkModel)) {
                            silkModels.add(ecuSilkModel);
                        }
                    }
                }
                vo.setSilkModels(silkModels);
            }
        }
        return res;
    }


    public EcuDescVo getObject(EcuDescBo bo) {
        EcuDesc record = new EcuDesc();
        record.setEcudId(bo.getEcudId());
        record.setDefaultType(bo.getDefaultType());
        EcuDesc object = ecuDescService.getObject(record);
        if (ObjUtil.isNull(object)) {
            return null;
        }
        List<EcuDescVo> convert = convert(List.of(object));
        return convert.get(0);
    }


    @Transactional(rollbackFor = Exception.class)
    public String deal(EcuDescDealBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        Integer ecuId = sysUser.getUserId();
        Integer ecudId = bo.getEcudId();
        String content = bo.getContent();
        EcuDesc record = new EcuDesc();
        BeanUtils.copyProperties(bo, record);

        String msg;
        if (ObjectUtil.isNull(ecudId)) {// 插入
            Integer sortId = 1;
            record.setEcCompanyId(sysUser.getEcCompanyId());
            record.setEcuId(ecuId);
            EcuDesc ecuNotice = ecuDescService.getObject(record);
            if (ecuNotice != null) {
                sortId = ecuNotice.getSortId() + 1;
            }
            record.setDefaultType(false);
            record.setStartType(true);
            record.setSortId(sortId);
            record.setContent(content);
            record.setAddTime(new Date());
            record.setUpdateTime(new Date());
            ecuDescService.insert(record);
            msg = "正常新增数据";
        } else {// 修改
            record.setEcudId(ecudId);
            record.setContent(content);
            record.setUpdateTime(new Date());
            ecuDescService.update(record);
            msg = "正常更新数据";
        }
        return msg;
    }


    public String start(EcuDescBo bo) {
        Integer ecudId = bo.getEcudId();
        EcuDesc ecuDesc = getObjectPassEcudId(ecudId);
        String msg;
        Boolean startType = ecuDesc.getStartType();
        if (!startType) {
            startType = true;
            msg = "启用成功";
        } else {
            startType = false;
            msg = "禁用成功";
        }
        EcuDesc record = new EcuDesc();
        record.setEcudId(ecudId);
        record.setStartType(startType);
        ecuDescService.update(record);
        return msg;
    }


    @Transactional(rollbackFor = Exception.class)
    public void sort(List<EcuDescSortBo> bos) {
        for (EcuDescSortBo bo : bos) {
            Integer ecudId = bo.getEcudId();
            Integer sortId = bo.getSortId();
            EcuDesc record = new EcuDesc();
            record.setEcudId(ecudId);
            record.setSortId(sortId);
            ecuDescService.update(record);
        }
    }


    @Transactional(rollbackFor = Exception.class)
    public void delete(EcuDescBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        Integer ecuId = sysUser.getUserId();
        Integer ecudId = bo.getEcudId();
        EcuDesc record = new EcuDesc();
        record.setEcudId(ecudId);
        EcuDesc ecuDesc = ecuDescService.getObject(record);
        Integer sortId = ecuDesc.getSortId();
        record = new EcuDesc();
        record.setEcuId(ecuId);
        record.setSortId(sortId);
        List<EcuDesc> list = ecuDescService.getList(record);
        Integer ecud_id;
        for (EcuDesc desc : list) {
            ecud_id = desc.getEcudId();
            sortId = desc.getSortId() - 1;
            record.setEcudId(ecud_id);
            record.setSortId(sortId);
            ecuDescService.update(record);
        }
        record = new EcuDesc();
        record.setEcudId(ecudId);
        ecuDescService.delete(record);
    }


    public void defaultType(EcuDescBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        Integer ecuId = sysUser.getUserId();
        Integer ecudId = bo.getEcudId();
        EcuDesc record = new EcuDesc();
        record.setEcuId(ecuId);
        record.setDefaultType(false);
        ecuDescService.update(record);

        record = new EcuDesc();
        record.setEcudId(ecudId);
        record.setDefaultType(true);
        ecuDescService.update(record);
    }

    

// getObjectPassEcunId
    public EcuDesc getObjectPassEcudId(Integer ecudId) {
        EcuDesc record = new EcuDesc();
        record.setEcudId(ecudId);
        return ecuDescService.getObject(record);
    }
}
