package org.jeecg.modules.cable.model.userCommon;

import cn.hutool.core.util.ObjectUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.userCommon.pcompany.bo.CompanyBaseBo;
import org.jeecg.modules.cable.controller.userCommon.pcompany.bo.CompanyDealBo;
import org.jeecg.modules.cable.controller.userCommon.pcompany.bo.CompanyListBo;
import org.jeecg.modules.cable.controller.userCommon.pcompany.bo.CompanySortBo;
import org.jeecg.modules.cable.controller.userCommon.pcompany.vo.CompanyListVo;
import org.jeecg.modules.cable.controller.userCommon.pcompany.vo.EcbuPCompanyVo;
import org.jeecg.modules.cable.entity.price.EcuQuoted;
import org.jeecg.modules.cable.entity.userCommon.EcbuPlatformCompany;
import org.jeecg.modules.cable.service.price.EcuQuotedService;
import org.jeecg.modules.cable.service.userCommon.EcbuPlatformcompanyService;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
public class EcbuPlatformCompanyModel {
    @Resource
    EcbuPlatformcompanyService ecbuPlatformcompanyService;
    @Resource
    private EcuQuotedService quotedService;


    public CompanyListVo getListAndCount(CompanyListBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcbuPlatformCompany record = new EcbuPlatformCompany();
        record.setStartType(bo.getStartType());
        record.setEcCompanyId(sysUser.getEcCompanyId());
        List<EcbuPCompanyVo> list = ecbuPlatformcompanyService.getList(record);
        long count = ecbuPlatformcompanyService.getCount(record);
        return new CompanyListVo(list, count);
    }


    public EcbuPlatformCompany getObject(CompanyBaseBo bo) {
        EcbuPlatformCompany record = new EcbuPlatformCompany();
        Integer ecbupId = bo.getEcbupId();
        record.setEcbupId(ecbupId);
        return ecbuPlatformcompanyService.getObject(record);
    }


    @Transactional(rollbackFor = Exception.class)
    public String saveOrUpdate(CompanyDealBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        Integer ecbupId = bo.getEcbupId();
        Integer platformId = bo.getPlatformId();
        String pcName = bo.getPcName();
        BigDecimal pcPercent = bo.getPcPercent();
        String description = bo.getDescription();

        EcbuPlatformCompany record = new EcbuPlatformCompany();
        record.setEcbupId(ecbupId);
        record.setEcCompanyId(sysUser.getEcCompanyId());
        record.setPcName(pcName);
        EcbuPlatformCompany ecbuPlatformCompany = ecbuPlatformcompanyService.getObjectPassPcName(record);

        String msg = "";
        if (ecbuPlatformCompany != null) {
            throw new RuntimeException("名称已占用");
        }
        if (ObjectUtil.isNull(ecbupId)) {// 插入
            Integer sortId = 1;
            ecbuPlatformCompany = ecbuPlatformcompanyService.getLatestObject(record);
            if (ecbuPlatformCompany != null) {
                sortId = ecbuPlatformCompany.getSortId() + 1;
            }
            record = new EcbuPlatformCompany();
            record.setEcCompanyId(sysUser.getEcCompanyId());
            record.setStartType(true);
            record.setSortId(sortId);
            record.setPlatformId(platformId);
            record.setPcName(pcName);
            record.setPcPercent(pcPercent);
            record.setDescription(description);
            System.out.println(CommonFunction.getGson().toJson(record));
            ecbuPlatformcompanyService.insert(record);
            msg = "正常插入数据";
        } else {// 更新
            record.setEcbupId(ecbupId);
            record.setPlatformId(platformId);
            record.setPcName(pcName);
            record.setPcPercent(pcPercent);
            record.setDescription(description);
            ecbuPlatformcompanyService.update(record);
            msg = "正常更新数据";
        }

        //loadData(sysUser.getEcCompanyId());
        return msg;
    }

    @Transactional(rollbackFor = Exception.class)
    public void sort(List<CompanySortBo> bos) {
        for (CompanySortBo bo : bos) {
            Integer ecbupId = bo.getEcbupId();
            Integer sortId = bo.getSortId();
            EcbuPlatformCompany record = new EcbuPlatformCompany();
            record.setEcbupId(ecbupId);
            record.setSortId(sortId);
            ecbuPlatformcompanyService.update(record);
        }
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        Integer ecCompanyId = sysUser.getEcCompanyId();
        //loadData(ecCompanyId);
    }


    @Transactional(rollbackFor = Exception.class)
    public void delete(CompanyBaseBo bo) {
        Integer ecbupId = bo.getEcbupId();
        //验证下报价单上是否在使用这个平台费率
        EcuQuoted ecuQuoted = new EcuQuoted();
        ecuQuoted.setEcbupId(ecbupId);
        List<EcuQuoted> list1 = quotedService.getList(ecuQuoted);
        if (!list1.isEmpty()) {
            throw new RuntimeException("当前数据已被报价单使用，无法删除");
        }
        EcbuPlatformCompany record = new EcbuPlatformCompany();
        record.setEcbupId(ecbupId);
        EcbuPlatformCompany ecbuPlatformCompany = ecbuPlatformcompanyService.getObject(record);
        Integer sortId = ecbuPlatformCompany.getSortId();
        record = new EcbuPlatformCompany();
        record.setSortId(sortId);
        record.setEcCompanyId(ecbuPlatformCompany.getEcCompanyId());
        List<EcbuPlatformCompany> list = ecbuPlatformcompanyService.getListGreaterThanSortId(record);
        Integer ecbup_id;
        for (EcbuPlatformCompany ecbud_money : list) {
            ecbup_id = ecbud_money.getEcbupId();
            sortId = ecbud_money.getSortId() - 1;
            record.setEcbupId(ecbup_id);
            record.setSortId(sortId);
            ecbuPlatformcompanyService.update(record);
        }
        record = new EcbuPlatformCompany();
        record.setEcbupId(ecbupId);
        ecbuPlatformcompanyService.delete(record);
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        Integer ecCompanyId = sysUser.getEcCompanyId();
        //loadData(ecCompanyId);
    }


    public String start(CompanyBaseBo bo) {
        Integer ecbupId = bo.getEcbupId();
        EcbuPlatformCompany record = new EcbuPlatformCompany();
        record.setEcbupId(ecbupId);
        EcbuPlatformCompany ecbuPlatformCompany = ecbuPlatformcompanyService.getObject(record);
        Boolean startType = ecbuPlatformCompany.getStartType();
        String msg = "";
        if (!startType) {
            startType = true;
            msg = "数据启用成功";
        } else {
            startType = false;
            msg = "数据禁用成功";
        }
        record = new EcbuPlatformCompany();
        record.setEcbupId(ecbuPlatformCompany.getEcbupId());
        record.setStartType(startType);
        ecbuPlatformcompanyService.update(record);
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        Integer ecCompanyId = sysUser.getEcCompanyId();
        //loadData(ecCompanyId);
        return msg;
    }

    // load 加载用户包带数据为txt文档
    //public void loadData(Integer ecCompanyId) {
    //    EcbuPlatformCompany record = new EcbuPlatformCompany();
    //    record.setStartType(true);
    //    record.setEcCompanyId(ecCompanyId);
    //    List<EcbuPCompanyVo> list = ecbuPlatformcompanyService.getList(record);
    //    List<String> txtList = new ArrayList<>();
    //    txtList.add(CommonFunction.getGson().toJson(list));
    //ecdCollectModel.deal(ecCompanyId, 11, txtList);
    //}


    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(EcbuPlatformCompany record) {
        EcbuPlatformCompany recordEcbuPlatformCompany = new EcbuPlatformCompany();
        recordEcbuPlatformCompany.setEcCompanyId(record.getEcCompanyId());
        recordEcbuPlatformCompany.setPlatformId(record.getPlatformId());
        recordEcbuPlatformCompany.setPcName(record.getPcName());
        EcbuPlatformCompany ecbuPlatformCompany = ecbuPlatformcompanyService.getObject(record);
        if (ecbuPlatformCompany != null) {
            record.setEcbupId(ecbuPlatformCompany.getEcbupId());
            ecbuPlatformcompanyService.update(record);
        } else {
            ecbuPlatformcompanyService.insert(record);
        }
    }

    // deletePassEcCompanyId
    public void deletePassEcCompanyId(Integer ecCompanyId) {
        EcbuPlatformCompany record = new EcbuPlatformCompany();
        record.setEcCompanyId(ecCompanyId);
        ecbuPlatformcompanyService.delete(record);
    }

    // getObjectPassEcbupId
    public EcbuPlatformCompany getObjectPassEcbupId(Integer ecbupId) {
        EcbuPlatformCompany record = new EcbuPlatformCompany();
        record.setEcbupId(ecbupId);
        return ecbuPlatformcompanyService.getObject(record);
    }
}
