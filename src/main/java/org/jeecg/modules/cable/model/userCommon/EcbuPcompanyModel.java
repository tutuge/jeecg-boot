package org.jeecg.modules.cable.model.userCommon;

import cn.hutool.core.util.ObjectUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.userCommon.pcompany.bo.CompanyBaseBo;
import org.jeecg.modules.cable.controller.userCommon.pcompany.bo.CompanyDealBo;
import org.jeecg.modules.cable.controller.userCommon.pcompany.bo.CompanyListBo;
import org.jeecg.modules.cable.controller.userCommon.pcompany.bo.CompanySortBo;
import org.jeecg.modules.cable.controller.userCommon.pcompany.vo.CompanyListVo;
import org.jeecg.modules.cable.controller.userCommon.pcompany.vo.EcbuPCompanyVo;
import org.jeecg.modules.cable.entity.userCommon.EcbuPcompany;
import org.jeecg.modules.cable.model.efficiency.EcdCollectModel;
import org.jeecg.modules.cable.service.userCommon.EcbuPcompanyService;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class EcbuPcompanyModel {
    @Resource
    EcbuPcompanyService ecbuPcompanyService;
    @Resource
    EcdCollectModel ecdCollectModel;

    // getListAndCount
    public CompanyListVo getListAndCount(CompanyListBo bo) {
        // 获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();

        EcbuPcompany record = new EcbuPcompany();
        record.setStartType(bo.getStartType());
        record.setEcCompanyId(ecUser.getEcCompanyId());
        List<EcbuPCompanyVo> list = ecbuPcompanyService.getList(record);
        long count = ecbuPcompanyService.getCount(record);
        return new CompanyListVo(list, count);
    }


    public EcbuPcompany getObject(CompanyBaseBo bo) {
        EcbuPcompany record = new EcbuPcompany();
        Integer ecbupId = bo.getEcbupId();
        record.setEcbupId(ecbupId);
        return ecbuPcompanyService.getObject(record);
    }


    @Transactional(rollbackFor = Exception.class)
    public String deal(CompanyDealBo bo) {
        // 获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        Integer ecbupId = bo.getEcbupId();
        Integer platformId = bo.getPlatformId();
        String pcName = bo.getPcName();
        BigDecimal pcPercent = bo.getPcPercent();
        String description = bo.getDescription();

        EcbuPcompany record = new EcbuPcompany();
        record.setEcbupId(ecbupId);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        record.setPcName(pcName);
        EcbuPcompany ecbuPcompany = ecbuPcompanyService.getObjectPassPcName(record);

        String msg = "";
        if (ecbuPcompany != null) {
            throw new RuntimeException("名称已占用");
        }
        if (ObjectUtil.isNull(ecbupId)) {// 插入
            Integer sortId = 1;
            ecbuPcompany = ecbuPcompanyService.getLatestObject(record);
            if (ecbuPcompany != null) {
                sortId = ecbuPcompany.getSortId() + 1;
            }
            record = new EcbuPcompany();
            record.setEcCompanyId(ecUser.getEcCompanyId());
            record.setStartType(true);
            record.setSortId(sortId);
            record.setPlatformId(platformId);
            record.setPcName(pcName);
            record.setPcPercent(pcPercent);
            record.setDescription(description);
            System.out.println(CommonFunction.getGson().toJson(record));
            ecbuPcompanyService.insert(record);
            msg = "正常插入数据";
        } else {// 更新
            record.setEcbupId(ecbupId);
            record.setPlatformId(platformId);
            record.setPcName(pcName);
            record.setPcPercent(pcPercent);
            record.setDescription(description);
            ecbuPcompanyService.update(record);
            msg = "正常更新数据";
        }
        loadData();
        return msg;
    }

    @Transactional(rollbackFor = Exception.class)
    public void sort(List<CompanySortBo> bos) {
        for (CompanySortBo bo : bos) {
            Integer ecbupId = bo.getEcbupId();
            Integer sortId = bo.getSortId();
            EcbuPcompany record = new EcbuPcompany();
            record.setEcbupId(ecbupId);
            record.setSortId(sortId);
            ecbuPcompanyService.update(record);
        }
        loadData();
    }


    @Transactional(rollbackFor = Exception.class)
    public void delete(CompanyBaseBo bo) {
        Integer ecbupId = bo.getEcbupId();
        EcbuPcompany record = new EcbuPcompany();
        record.setEcbupId(ecbupId);
        EcbuPcompany ecbuPcompany = ecbuPcompanyService.getObject(record);
        Integer sortId = ecbuPcompany.getSortId();
        record = new EcbuPcompany();
        record.setSortId(sortId);
        record.setEcCompanyId(ecbuPcompany.getEcCompanyId());
        List<EcbuPcompany> list = ecbuPcompanyService.getListGreaterThanSortId(record);
        Integer ecbup_id;
        for (EcbuPcompany ecbud_money : list) {
            ecbup_id = ecbud_money.getEcbupId();
            sortId = ecbud_money.getSortId() - 1;
            record.setEcbupId(ecbup_id);
            record.setSortId(sortId);
            ecbuPcompanyService.update(record);
        }
        record = new EcbuPcompany();
        record.setEcbupId(ecbupId);
        ecbuPcompanyService.delete(record);
        loadData();
    }


    public String start(CompanyBaseBo bo) {

        Integer ecbupId = bo.getEcbupId();
        EcbuPcompany record = new EcbuPcompany();
        record.setEcbupId(ecbupId);
        EcbuPcompany ecbuPcompany = ecbuPcompanyService.getObject(record);
        Boolean startType = ecbuPcompany.getStartType();
        String msg = "";
        if (!startType) {
            startType = true;
            msg = "数据启用成功";
        } else {
            startType = false;
            msg = "数据禁用成功";
        }
        record = new EcbuPcompany();
        record.setEcbupId(ecbuPcompany.getEcbupId());
        record.setStartType(startType);
        ecbuPcompanyService.update(record);

        loadData();
        return msg;
    }

    // load 加载用户包带数据为txt文档
    public void loadData() {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        Integer ecCompanyId = ecUser.getEcCompanyId();
        EcbuPcompany record = new EcbuPcompany();
        record.setStartType(true);
        record.setEcCompanyId(ecCompanyId);
        log.info(CommonFunction.getGson().toJson(record));
        List<EcbuPCompanyVo> list = ecbuPcompanyService.getList(record);
        List<String> txtList = new ArrayList<>();
        txtList.add(CommonFunction.getGson().toJson(list));
        ecdCollectModel.deal(ecCompanyId, 11, txtList);
    }

    /***===数据模型===***/

    @Transactional(rollbackFor = Exception.class)
    public void deal(EcbuPcompany record) {
        EcbuPcompany recordEcbuPcompany = new EcbuPcompany();
        recordEcbuPcompany.setEcCompanyId(record.getEcCompanyId());
        recordEcbuPcompany.setPlatformId(record.getPlatformId());
        recordEcbuPcompany.setPcName(record.getPcName());
        EcbuPcompany ecbuPcompany = ecbuPcompanyService.getObject(record);
        if (ecbuPcompany != null) {
            record.setEcbupId(ecbuPcompany.getEcbupId());
            ecbuPcompanyService.update(record);
        } else {
            ecbuPcompanyService.insert(record);
        }
    }

    // deletePassEcCompanyId
    public void deletePassEcCompanyId(Integer ecCompanyId) {
        EcbuPcompany record = new EcbuPcompany();
        record.setEcCompanyId(ecCompanyId);
        ecbuPcompanyService.delete(record);
    }

    // getObjectPassEcbupId
    public EcbuPcompany getObjectPassEcbupId(Integer ecbupId) {
        EcbuPcompany record = new EcbuPcompany();
        record.setEcbupId(ecbupId);
        return ecbuPcompanyService.getObject(record);
    }
}
