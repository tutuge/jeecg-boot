package org.jeecg.modules.cable.model.price;

import org.jeecg.modules.cable.entity.price.EcuQuoted;
import org.jeecg.modules.cable.entity.price.EcuqDesc;
import org.jeecg.modules.cable.entity.price.EcuqInput;
import org.jeecg.modules.cable.entity.quality.EcquLevel;
import org.jeecg.modules.cable.entity.systemEcable.EcSilk;
import org.jeecg.modules.cable.entity.systemEcable.EcbInsulation;
import org.jeecg.modules.cable.entity.systemEcable.EcbSheath;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.modules.cable.entity.userCommon.EcbuStore;
import org.jeecg.modules.cable.entity.userCommon.EcduCompany;
import org.jeecg.modules.cable.entity.userEcable.EcbuConductor;
import org.jeecg.modules.cable.entity.userEcable.EcbuInsulation;
import org.jeecg.modules.cable.entity.userEcable.EcbuSheath;
import org.jeecg.modules.cable.entity.userOffer.EcuOffer;
import org.jeecg.modules.cable.model.systemEcable.EcSilkModel;
import org.jeecg.modules.cable.model.systemEcable.EcbInsulationModel;
import org.jeecg.modules.cable.model.user.EcuLoginModel;
import org.jeecg.modules.cable.model.userEcable.EcbuInsulationModel;
import org.jeecg.modules.cable.model.userEcable.EcbuSheathModel;
import org.jeecg.modules.cable.model.userOffer.EcuOfferModel;
import org.jeecg.modules.cable.service.price.EcSilkService;
import org.jeecg.modules.cable.service.price.EcuQuotedService;
import org.jeecg.modules.cable.service.price.EcuqDescService;
import org.jeecg.modules.cable.service.price.EcuqInputService;
import org.jeecg.modules.cable.service.quality.EcquLevelService;
import org.jeecg.modules.cable.service.systemEcable.EcbSheathService;
import org.jeecg.modules.cable.service.user.EcUserService;
import org.jeecg.modules.cable.service.userCommon.EcbuStoreService;
import org.jeecg.modules.cable.service.userCommon.EcduCompanyService;
import org.jeecg.modules.cable.service.userEcable.EcbuConductorService;
import org.jeecg.modules.cable.service.userEcable.EcbuSheathService;
import org.jeecg.modules.cable.tools.CommonFunction;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class EcuqDescModel {
    @Resource
    EcuqDescService ecuqDescService;
    @Resource
    EcuOfferModel ecuOfferModel;//库数据
    @Resource
    EcbuStoreService ecbuStoreService;//用户仓库
    @Resource
    EcbuConductorService ecbuConductorService;//用户导体
    @Resource
    EcquLevelService ecquLevelService;//质量等级
    @Resource
    EcSilkService ecSilkService;//丝型号
    @Resource
    EcbSheathService ecbSheathService;//系统护套
    @Resource
    @Lazy
    EcuqInputModel ecuqInputModel;
    @Resource
    EcUserService ecUserService;
    @Resource
    EcbuSheathService ecbuSheathService;
    @Resource
    EcuqInputService ecuqInputService;
    @Resource
    EcduCompanyService ecduCompanyService;
    @Resource
    EcuQuotedService ecuQuotedService;
    @Resource
    EcuLoginModel ecuLoginModel;
    @Resource
    EcbuSheathModel ecbuSheathModel;
    @Resource
    EcbInsulationModel ecbInsulationModel;
    @Resource
    EcbuInsulationModel ecbuInsulationModel;
    @Resource
    EcSilkModel ecSilkModel;//丝型号

    //dealStructure
    public Map<String, Object> dealStructure(HttpServletRequest request) {
        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        int ecuqiId = Integer.parseInt(request.getParameter("ecuqiId"));
        EcuqDesc recordEcuqDesc = new EcuqDesc();
        recordEcuqDesc.setEcuqiId(ecuqiId);
        EcuqDesc ecuqDesc = ecuqDescService.getObject(recordEcuqDesc);
        if (ecuqDesc == null) {
            status = 3;//数据错误
            code = "103";
            msg = "数据错误";
        } else {
            EcuqDesc record = new EcuqDesc();
            record.setEcuqdId(ecuqDesc.getEcuqdId());
            if (request.getParameter("storePercent") != null) {//仓库加点
                BigDecimal storePercent = new BigDecimal(request.getParameter("storePercent"));
                record.setStorePercent(storePercent);
            }
            if (request.getParameter("sdunitMoney") != null) {//运费加点
                BigDecimal sdunitMoney = new BigDecimal(request.getParameter("sdunitMoney"));
                record.setSdunitMoney(sdunitMoney);
            }
            if (request.getParameter("ecbucId") != null) { //导体类型
                int ecbucId = Integer.parseInt(request.getParameter("ecbucId"));
                record.setEcbucId(ecbucId);
            }
            if (request.getParameter("fireSilkNumber") != null) {//粗芯丝号
                BigDecimal fireSilkNumber = new BigDecimal(request.getParameter("fireSilkNumber"));
                record.setFireSilkNumber(fireSilkNumber);
            }
            if (request.getParameter("fireStrand") != null) {//粗芯绞合系数
                BigDecimal fireStrand = new BigDecimal(request.getParameter("fireStrand"));
                record.setFireStrand(fireStrand);
            }
            if (request.getParameter("zeroSilkNubmer") != null) {//细芯丝号
                BigDecimal zeroSilkNumber = new BigDecimal(request.getParameter("zeroSilkNubmer"));
                record.setZeroSilkNumber(zeroSilkNumber);
            }
            if (request.getParameter("zeroStrand") != null) {//细芯绞合系数
                BigDecimal zeroStrand = new BigDecimal(request.getParameter("zeroStrand"));
                record.setZeroStrand(zeroStrand);
            }
            if (request.getParameter("ecbumId") != null) {//云母带类型
                int ecbumId = Integer.parseInt(request.getParameter("ecbumId"));
                record.setEcbumId(ecbumId);
            }
            if (request.getParameter("micatapeThickness") != null) {//云母带厚度
                BigDecimal micatapeThickness = new BigDecimal(request.getParameter("micatapeThickness"));
                record.setMicatapeThickness(micatapeThickness);
            }
            if (request.getParameter("ecbuiId") != null) {//绝缘类型
                int ecbuiId = Integer.parseInt(request.getParameter("ecbuiId"));
                record.setEcbuiId(ecbuiId);
            }
            if (request.getParameter("insulationFireThickness") != null) {//绝缘粗芯厚度
                BigDecimal insulationFireThickness = new BigDecimal(request.getParameter("insulationFireThickness"));
                record.setInsulationFireThickness(insulationFireThickness);
            }
            if (request.getParameter("insulationZeroThickness") != null) { //绝缘细芯厚度
                BigDecimal insulationZeroThickness = new BigDecimal(request.getParameter("insulationZeroThickness"));
                record.setInsulationZeroThickness(insulationZeroThickness);
            }
            if (request.getParameter("ecbuinId") != null) {//填充物类型类型
                int ecbuinId = Integer.parseInt(request.getParameter("ecbuinId"));
                record.setEcbuinId(ecbuinId);
            }
            if (request.getParameter("ecbubId") != null) {//包带类型
                int ecbubId = Integer.parseInt(request.getParameter("ecbubId"));
                record.setEcbubId(ecbubId);
            }
            if (request.getParameter("bagThickness") != null) {//包带厚度
                BigDecimal bagThickness = new BigDecimal(request.getParameter("bagThickness"));
                record.setBagThickness(bagThickness);
            }
            if (request.getParameter("ecbusId") != null) {//屏蔽类型
                int ecbusId = Integer.parseInt(request.getParameter("ecbusId"));
                record.setEcbusId(ecbusId);
            }
            if (request.getParameter("shieldThickness") != null) {//屏蔽厚度
                BigDecimal shieldThickness = new BigDecimal(request.getParameter("shieldThickness"));
                record.setShieldThickness(shieldThickness);
            }
            if (request.getParameter("shieldPercent") != null) { //屏蔽编织密度
                BigDecimal shieldPercent = new BigDecimal(request.getParameter("shieldPercent"));
                record.setShieldPercent(shieldPercent);
            }
            if (request.getParameter("ecbusbId") != null) {//钢带类型
                int ecbusbId = Integer.parseInt(request.getParameter("ecbusbId"));
                record.setEcbusbId(ecbusbId);
            }

            if (request.getParameter("steelbandThickness") != null) { //钢带厚度
                BigDecimal steelbandThickness = new BigDecimal(request.getParameter("steelbandThickness"));
                record.setSteelbandThickness(steelbandThickness);
            }
            if (request.getParameter("steelbandStorey") != null) {//钢带层数
                int steelbandStorey = Integer.parseInt(request.getParameter("steelbandStorey"));
                record.setSteelbandStorey(steelbandStorey);
            }
            if (request.getParameter("ecbsId") != null) {//护套类型
                int ecbsId = Integer.parseInt(request.getParameter("ecbsId"));
                EcbuSheath recordEcbuSheath = new EcbuSheath();
                recordEcbuSheath.setEcbsId(ecbsId);
                recordEcbuSheath.setEcCompanyId(ecUser.getEcCompanyId());
                EcbuSheath ecbuSheath = ecbuSheathService.getObject(recordEcbuSheath);
                record.setEcbusid(ecbuSheath.getEcbusId());
            }
            if (request.getParameter("sheathThickness") != null) { //护套厚度
                BigDecimal sheathThickness = new BigDecimal(request.getParameter("sheathThickness"));
                record.setSheathThickness(sheathThickness);
            }
            log.info("record + " + CommonFunction.getGson().toJson(record));
            ecuqDescService.update(record);
            status = 4;//正常更新数据
            code = "200";
            msg = "正常更新数据";
        }

    }

    //cleanMoney 清除金额
    public void cleanMoney(int ecuqdId) {
        BigDecimal nbupsMoney = new BigDecimal("0");//不开票单价
        BigDecimal bupsMoney = new BigDecimal("0");//开发票单价
        BigDecimal nbupcMoney = new BigDecimal("0");//不开票小计
        BigDecimal bupcMoney = new BigDecimal("0");//开票小计
        BigDecimal weight = new BigDecimal("0");
        EcuqDesc record = new EcuqDesc();
        record.setEcuqdId(ecuqdId);
        record.setBupsMoney(bupsMoney);
        record.setBupcMoney(bupcMoney);
        record.setNbupsMoney(nbupsMoney);
        record.setNbupcMoney(nbupcMoney);
        record.setWeight(weight);
        //System.out.println(CommonFunction.getGson().toJson(record));
        ecuqDescService.update(record);
    }

    //updateCweight 修改导体重量
    public void updateCweight(EcuqInput ecuqInput, BigDecimal cweight) {
        EcuqDesc record = new EcuqDesc();
        record.setEcuqiId(ecuqInput.getEcuqiId());
        EcuqDesc ecuqDesc = ecuqDescService.getObject(record);
        record.setEcuqdId(ecuqDesc.getEcuqdId());
        record.setCweight(cweight);//导体重量
        ecuqDescService.update(record);
    }

    //dealInputStart 更改为手输或是自动计算价格 false 是自动 true 是手输
    public Map<String, Object> dealInputStart(HttpServletRequest request) {

        int ecuqdId = Integer.parseInt(request.getParameter("ecuqdId"));
        boolean inputStart = Boolean.parseBoolean(request.getParameter("inputStart"));
        EcuqDesc record = new EcuqDesc();
        record.setEcuqdId(ecuqdId);
        record.setInputStart(inputStart);
        ecuqDescService.update(record);
        status = 3;//操作操作成功
        code = "200";
        msg = "操作数据成功";

    }

    //dealUnitPrice
    public Map<String, Object> dealUnitPrice(HttpServletRequest request) {

        int ecuqiId = Integer.parseInt(request.getParameter("ecuqiId"));
        EcuqDesc record = new EcuqDesc();
        record.setEcuqiId(ecuqiId);
        EcuqDesc ecuqDesc = ecuqDescService.getObject(record);
        boolean unitPriceInput = Boolean.parseBoolean(request.getParameter("unitPriceInput"));
        BigDecimal unitPrice = new BigDecimal(request.getParameter("unitPrice"));
        record.setEcuqdId(ecuqDesc.getEcuqdId());
        record.setUnitPriceInput(unitPriceInput);
        record.setUnitPrice(unitPrice);
        ecuqDescService.update(record);
        status = 3;//操作操作成功
        code = "200";
        msg = "操作数据成功";

    }

    //cleanUnitPriceInput 清空税前单价 即将税前单价的手输模式改为false
    public void cleanUnitPriceInput(int ecuqiId, boolean unitPriceInput) {
        EcuqDesc record = new EcuqDesc();
        record.setEcuqiId(ecuqiId);
        EcuqDesc ecuqDesc = ecuqDescService.getObject(record);
        if (ecuqDesc != null) {
            record.setEcuqdId(ecuqDesc.getEcuqdId());
            record.setUnitPriceInput(unitPriceInput);
            ecuqDescService.update(record);
        }
    }

    //dealAxle 木轴类型和木轴数量提交
    public Map<String, Object> dealAxle(HttpServletRequest request) {

        int ecuqiId = Integer.parseInt(request.getParameter("ecuqiId"));
        EcuqDesc record = new EcuqDesc();
        record.setEcuqiId(ecuqiId);
        EcuqDesc ecuqDesc = ecuqDescService.getObject(record);
        if (ecuqDesc == null) {
            status = 3;//数据不完整
            code = "103";
            msg = "数据不完整";
        } else {
            record.setEcuqdId(ecuqDesc.getEcuqdId());
            if (request.getParameter("ecbuaId") != null) {
                int ecbuaId = 0;
                if (!"".equals(request.getParameter("ecbuaId"))) {
                    ecbuaId = Integer.parseInt(request.getParameter("ecbuaId"));
                }
                record.setEcbuaId(ecbuaId);
                if (ecuqDesc.getEcbuaId() == 0 && ecbuaId != 0) {
                    record.setAxleNumber(1);
                } else if (ecbuaId == 0) {
                    record.setAxleNumber(0);
                }
            }
            if (request.getParameter("axleNumber") != null) {
                int axleNumber = Integer.parseInt(request.getParameter("axleNumber"));
                record.setAxleNumber(axleNumber);
            }
            log.info(CommonFunction.getGson().toJson(record));
            ecuqDescService.update(record);
            status = 3;//操作数据成功
            code = "200";
            msg = "操作数据成功";
        }

    }

    //dealMoney 提交金额
    public Map<String, Object> dealMoney(HttpServletRequest request) {
        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        int ecuqiId = Integer.parseInt(request.getParameter("ecuqiId"));
        //System.out.println(ecuqiId);
        EcuqInput recordEcuqInput = new EcuqInput();
        recordEcuqInput.setEcuqiId(ecuqiId);
        EcuqInput ecuqInput = ecuqInputService.getObject(recordEcuqInput);
        BigDecimal nbupsMoney = new BigDecimal("0");
        BigDecimal bupsMoney = new BigDecimal("0");
        BigDecimal nbupcMoney = new BigDecimal("0");
        BigDecimal bupcMoney = new BigDecimal("0");
        if (request.getParameter("nbupsMoney") != null) {//不开票单价
            nbupsMoney = new BigDecimal(request.getParameter("nbupsMoney"));
            nbupcMoney = nbupsMoney.multiply(new BigDecimal(ecuqInput.getSaleNumber()));
        }
        if (request.getParameter("nbupcMoney") != null) {//不开票小计
            nbupcMoney = new BigDecimal(request.getParameter("nbupcMoney"));
            nbupsMoney = nbupcMoney.divide(new BigDecimal(ecuqInput.getSaleNumber()), 6, RoundingMode.HALF_UP);
        }
        if (request.getParameter("bupsMoney") != null) {//开票单价
            bupsMoney = new BigDecimal(request.getParameter("bupsMoney"));
            bupcMoney = bupsMoney.multiply(new BigDecimal(ecuqInput.getSaleNumber()));
        }
        if (request.getParameter("bupcMoney") != null) {//开票小计
            bupcMoney = new BigDecimal(request.getParameter("bupcMoney"));
            bupsMoney = bupcMoney.divide(new BigDecimal(ecuqInput.getSaleNumber()), 6, RoundingMode.HALF_UP);
        }
        EcduCompany recordEcduCompany = new EcduCompany();
        recordEcduCompany.setEcCompanyId(ecUser.getEcCompanyId());
        recordEcduCompany.setDefaultType(true);
        EcduCompany company = ecduCompanyService.getObject(recordEcduCompany);
        if (request.getParameter("nbupsMoney") != null || request.getParameter("nbupcMoney") != null) {
            if (company.getBillPercentType() == 1) {//算法1
                bupsMoney = nbupsMoney.divide(new BigDecimal("1").subtract(ecuqInput.getBillPercent()), 6, RoundingMode.HALF_UP);
                bupcMoney = nbupcMoney.divide(new BigDecimal("1").subtract(ecuqInput.getBillPercent()), 6, RoundingMode.HALF_UP);
            } else if (company.getBillPercentType() == 2) {//算法2
                bupsMoney = nbupsMoney.multiply(new BigDecimal("1").add(ecuqInput.getBillPercent()));
                bupcMoney = nbupcMoney.multiply(new BigDecimal("1").add(ecuqInput.getBillPercent()));
            }
        }
        if (request.getParameter("bupsMoney") != null || request.getParameter("bupcMoney") != null) {
            if (company.getBillPercentType() == 1) {//算法1
                nbupsMoney = bupsMoney.multiply(new BigDecimal("1").subtract(ecuqInput.getBillPercent()));
                nbupcMoney = bupcMoney.multiply(new BigDecimal("1").subtract(ecuqInput.getBillPercent()));
            } else if (company.getBillPercentType() == 2) {//算法2
                nbupsMoney = bupsMoney
                        .divide(
                                new BigDecimal("1")
                                        .add(ecuqInput.getBillPercent()), 6, RoundingMode.HALF_UP);
                nbupcMoney = bupcMoney.divide(
                        new BigDecimal("1")
                                .add(ecuqInput.getBillPercent()), 6, RoundingMode.HALF_UP
                );
            }
        }
        //System.out.println("h1");
        EcuqDesc record = new EcuqDesc();
        record.setEcuqiId(ecuqiId);
        EcuqDesc ecuqDesc = ecuqDescService.getObject(record);
        record.setEcuqdId(ecuqDesc.getEcuqdId());
        record.setNbupsMoney(nbupsMoney);
        record.setNbupcMoney(nbupcMoney);
        record.setBupsMoney(bupsMoney);
        record.setBupcMoney(bupcMoney);
        record.setInputStart(true);
        log.info(CommonFunction.getGson().toJson(record));
        ecuqDescService.update(record);
        status = 3;//操作数据成功
        code = "200";
        msg = "操作数据成功";

    }

    //dealMoneyPassQuoted
    public void dealMoneyPassQuoted(int ecuqId, BigDecimal buptMoney, BigDecimal nbuptMoney) {
        log.info("nbuptMoney + " + nbuptMoney);
        EcuqDesc record = new EcuqDesc();
        EcuQuoted recordEcuQuoted = new EcuQuoted();
        recordEcuQuoted.setEcuqId(ecuqId);
        EcuQuoted ecuQuoted = ecuQuotedService.getObject(recordEcuQuoted);
        record.setEcuqId(ecuqId);
        List<EcuqDesc> list = ecuqDescService.getList(record);
        //System.out.println(CommonFunction.getGson().toJson(list));
        BigDecimal buptM = ecuQuoted.getBuptMoney();
        BigDecimal nbuptM = ecuQuoted.getNbuptMoney();
        for (EcuqDesc ecuqDesc : list) {
            int ecuqiId = ecuqDesc.getEcuqiId();
            EcuqInput recordEcuqInput = new EcuqInput();
            recordEcuqInput.setEcuqiId(ecuqiId);
            EcuqInput ecuqInput = ecuqInputService.getObject(recordEcuqInput);
            if (buptMoney.compareTo(new BigDecimal("0")) != 0) {//开发票总计
                BigDecimal bupcMoney = ecuqDesc.getBupcMoney();
                BigDecimal percent = bupcMoney.divide(buptM, 6, RoundingMode.HALF_UP);//百分比
                bupcMoney = buptMoney.multiply(percent);
                BigDecimal bupsMoney = bupcMoney.divide(new BigDecimal(ecuqInput.getSaleNumber()), 6, RoundingMode.HALF_UP);
                record.setEcuqdId(ecuqDesc.getEcuqdId());
                record.setBupcMoney(bupcMoney);
                record.setBupsMoney(bupsMoney);
                ecuqDescService.update(record);
            } else {//不开发票总计
                BigDecimal nbupcMoney = ecuqDesc.getNbupcMoney();
                log.info("nbuptM + " + nbuptM);
                BigDecimal percent = nbupcMoney.divide(nbuptM, 6, RoundingMode.HALF_UP);//百分比
                nbupcMoney = nbuptMoney.multiply(percent);
                BigDecimal nbupsMoney = nbupcMoney.divide(new BigDecimal(ecuqInput.getSaleNumber()), 6, RoundingMode.HALF_UP);
                record.setEcuqdId(ecuqDesc.getEcuqdId());
                record.setNbupcMoney(nbupcMoney);
                record.setNbupsMoney(nbupsMoney);
                //System.out.println(CommonFunction.getGson().toJson(record));
                ecuqDescService.update(record);
            }
        }
    }

    //dealUnitPriceInput 计算税前单价改为自动
    public Map<String, Object> dealUnitPriceInput(HttpServletRequest request) {

            int ecuqiId = Integer.parseInt(request.getParameter("ecuqiId"));
            EcuqDesc record = new EcuqDesc();
            record.setEcuqiId(ecuqiId);
            //log.info(CommonFunction.getGson().toJson(record));
            EcuqDesc ecuqDesc = ecuqDescService.getObject(record);
            if (ecuqDesc != null) {
                record.setEcuqdId(ecuqDesc.getEcuqdId());
                record.setUnitPriceInput(false);
                //log.info("record + " + CommonFunction.getGson().toJson(record));
                ecuqDescService.update(record);
            }
            status = 3;//操作数据成功
            code = "200";
            msg = "操作数据成功";
            CommonFunction.getCommonMap(map, status, code, msg);}
        return map;
    }

    /***===数据模型===***/
    //deal
    public void deal(EcuqInput ecuqInput, int ecCompanyId, int ecuId) {
        EcuOffer object = ecuOfferModel.getOfferPassEcuqInput(ecuqInput);
        List<EcSilk> listSilk = ecSilkModel.getAllList(ecuId);
        if (object != null) {
            int ecqulId = object.getEcqulId();//质量等级ID
            int storeId = 0;//仓库ID
            EcbuStore recordStore = new EcbuStore();
            recordStore.setEcCompanyId(ecCompanyId);
            recordStore.setDefaultType(true);
            EcbuStore ecbuStore = ecbuStoreService.getObject(recordStore);
            if (ecbuStore != null) {
                storeId = ecbuStore.getEcbusId();
            }
            int ecbucId = object.getEcbucId();//导体ID
            int sortId = ecuqInput.getSortId();
            BigDecimal cunitPrice = new BigDecimal("0");//导体单价
            EcbuConductor recordEcbuConductor = new EcbuConductor();
            recordEcbuConductor.setEcbucId(object.getEcbucId());
            EcbuConductor ecbuConductor = ecbuConductorService.getObject(recordEcbuConductor);
            if (ecbuConductor != null) {
                cunitPrice = ecbuConductor.getUnitPrice();
            }
            BigDecimal cweight = new BigDecimal("0");//导体重量
            BigDecimal storePercent = new BigDecimal("0");//仓库利润
            BigDecimal sdunitMoney = new BigDecimal("0");//仓库运费加点
            if (ecbuStore != null) {
                EcquLevel recordEcquLevel = new EcquLevel();
                recordEcquLevel.setEcqulId(ecqulId);
                EcquLevel ecquLevel = ecquLevelService.getObject(recordEcquLevel);
                if (ecquLevel != null) {
                    EcSilk recordEcSilk = new EcSilk();
                    recordEcSilk.setEcsId(ecquLevel.getEcsId());
                    EcSilk ecSilk = ecSilkService.getObject(recordEcSilk);
                    if (ecSilk != null) {
                        if ("YJV".equals(ecSilk.getAbbreviation())) {
                            storePercent = ecbuStore.getPercentCopper();
                        }
                    }
                }
                sdunitMoney = ecbuStore.getDunitMoney();//仓库运费加点
            }
            String areaStr = object.getAreaStr();//截面str
            BigDecimal fireSilkNumber = object.getFireSilkNumber();//粗芯丝号
            int fireRootNumber = object.getFireRootNumber();//粗芯根数
            int fireMembrance = object.getFireMembrance();//粗芯过膜
            BigDecimal firePress = object.getFirePress();//粗芯压型
            BigDecimal fireStrand = object.getFireStrand();//粗芯绞合系数
            BigDecimal zeroSilkNumber = object.getZeroSilkNumber();//细芯丝号
            int zeroRootNumber = object.getZeroRootNumber();//细芯根数
            int zeroMembrance = object.getZeroMembrance();//细芯过膜
            BigDecimal zeroPress = object.getZeroPress();//细芯压型
            BigDecimal zeroStrand = object.getZeroStrand();//细芯绞合系数
            String silkName = ecuqInput.getSilkName();
            int ecbuiId = object.getEcbuiId();//绝缘类型
            if (silkName.contains("BYJ")) {
                String[] silkNameArr = silkName.split("-");
                String firstName = silkNameArr[0];
                if ("WDZ".equals(firstName)) {
                    firstName = "WDZC";
                }
                EcbInsulation ecbInsulation = ecbInsulationModel.getObjectPassAbbreviation(firstName);
                if (ecbInsulation != null) {
                    EcbuInsulation ecbuInsulation = ecbuInsulationModel
                            .getObjectPassEcCompanyIdAndEcbiId(ecCompanyId, ecbInsulation.getEcbiId());
                    ecbuiId = ecbuInsulation.getEcbuiId();
                }

            }
            BigDecimal insulationFireThickness = object.getInsulationFireThickness();//绝缘粗芯厚度
            BigDecimal insulationZeroThickness = object.getInsulationZeroThickness();//绝缘细芯厚度
            int ecbubId = object.getEcbubId();//包带类型
            BigDecimal bagThickness = object.getBagThickness();//包带厚度
            int ecbub22Id = object.getEcbubId();//凯装包带类型
            BigDecimal bag22Thickness = new BigDecimal("0");
            log.info("object + " + CommonFunction.getGson().toJson(object));
            if (object.getBag22Thickness() != null) {
                bag22Thickness = object.getBag22Thickness();//凯装包带厚度
            }
            //屏蔽 1 带 "-P" 2 带 "-P2"
            int ecbusId = 0;//屏蔽类型
            BigDecimal shieldThickness = new BigDecimal("0");//屏蔽厚度
            BigDecimal shieldPercent = new BigDecimal("0");//屏蔽厚度
			/*if(ecuqInput.getSilkName().contains("-P")) {
				shieldThickness = object.getShieldThickness();//屏蔽厚度
				shieldPercent = object.getShieldPercent();//屏蔽厚度
				if(!ecuqInput.getSilkName().contains("-P2")) {//不是P2
					ecbusId = object.getEcbusId();//屏蔽类型
				}else {//P2调用第一项
					Ecb_shield ecbShield = ecbShieldService.getShieldPassP2(request);
					if(ecbShield.getEcbuShield() != null) {
						ecbusId = ecbShield.getEcbuShield().getEcbusId();
					}
				}
			}*/
            //判断是否有钢带 丝类型中带"22"
            silkName = ecuqInput.getSilkName();
            silkName = silkName.replace("23", "22");
            silkName = silkName.replace("YJY", "YJV");
            silkName = silkName.replace("YJLY", "YJLV");
            silkName = silkName.replace("BYJ", "BV");
            int ecbusbId = 0;//钢带类型
            BigDecimal steelbandThickness = new BigDecimal("0");//钢带厚度
            int steelbandStorey = 0;//钢带层数
            if (silkName.contains("22")) {
                ecbusbId = object.getEcbusbId();//钢带类型
                steelbandThickness = object.getSteelbandThickness();//钢带厚度
                steelbandStorey = object.getSteelbandStorey();//钢带层数
            }
            int ecbusid = 0;//护套类型
            BigDecimal sheathThickness = object.getSheathThickness();//护套厚度
            BigDecimal sheath22Thickness = object.getSheath22Thickness();//凯装护套厚度
            String[] silkNameArr = silkName.split("-");
            String firstName = silkNameArr[0];
            log.info("firstName + " + firstName);
            firstName = firstName.replace("NH", "");
            log.info("firstName + " + firstName);
            firstName = firstName.replace("N", "");
            log.info("firstName + " + firstName);
            if ("WDZ".equals(firstName)) {
                firstName = "WDZC";
            }
            if ("BV".equals(firstName)) {
                ecbusid = object.getEcbusid();//护套类型
            } else if (!firstName.isEmpty() && !"YJV".equals(firstName) && !"YJV22".equals(firstName)) {//也不是YJV、
                EcbSheath recordEcbSheath = new EcbSheath();
                recordEcbSheath.setAbbreviation(firstName);
                log.info("recordEcbSheath + " + recordEcbSheath);
                EcbSheath ecbSheath = ecbSheathService.getObject(recordEcbSheath);
                log.info("ecbSheath + " + CommonFunction.getGson().toJson(ecbSheath));
                if (ecbSheath != null) {
                    EcbuSheath ecbuSheath = ecbuSheathModel
                            .getObjectPassEcCompanyIdAndEcbsId(ecCompanyId, ecbSheath.getEcbsId());
                    if (ecbuSheath != null) {
                        ecbusid = ecbuSheath.getEcbusId();
                    }
                }
            } else {//默认护套
                ecbusid = object.getEcbusid();//护套类型
            }
            //判断是否耐火 丝类型中带"N" 或者 "NH"
            int ecbumId = 0;//云母带类型
            BigDecimal micatapeThickness = new BigDecimal("0");//云母带厚度
            if (silkName.contains("N") || silkName.contains("NH")) {
                ecbumId = object.getEcbumId();//云母带类型
                micatapeThickness = object.getMicatapeThickness();//云母带厚度
            }
            int ecbuinId = object.getEcbuinId();//填充物类型
            int ecbuswId = object.getEcbuswId();//钢丝类型
            BigDecimal steelwireMembrance = object.getSteelwireMembrance();//钢丝过膜
            BigDecimal steelwirePress = object.getSteelwirePress();//钢丝压型
            BigDecimal nbupsMoney = new BigDecimal("0");//不开发票单价
            BigDecimal bupsMoney = new BigDecimal("0");//开发票单价
            BigDecimal nbupcMoney = new BigDecimal("0");//不开发票小计
            BigDecimal bupcMoney = new BigDecimal("0");//开发票小计
            BigDecimal weight = new BigDecimal("0");//重量
            EcuqDesc record = new EcuqDesc();
            record.setEcuqiId(ecuqInput.getEcuqiId());
            EcuqDesc ecuqDesc = ecuqDescService.getObject(record);
            if (ecuqDesc == null) {//插入
                record.setEcuoId(object.getEcuoId());
                record.setEcuqId(ecuqInput.getEcuqId());//报价单ID
                record.setEcuqiId(ecuqInput.getEcuqiId());
                record.setEcqulId(ecqulId);
                record.setStoreId(storeId);
                record.setEcbucId(ecbucId);
                record.setStartType(true);//默认开启，暂时没用，做备用
                record.setSortId(sortId);
                record.setCunitPrice(cunitPrice);//导体单价
                record.setCweight(cweight);//导体重量
                record.setStorePercent(storePercent);//仓库利润
                record.setSdunitMoney(sdunitMoney);//仓库运费加点
                record.setAddPercent(object.getAddPercent());
                record.setAreaStr(areaStr);//截面str
                record.setFireSilkNumber(fireSilkNumber);//粗芯丝号
                record.setFireRootNumber(fireRootNumber);//粗芯根数
                record.setFireMembrance(fireMembrance);//粗芯过膜
                record.setFirePress(firePress);//粗芯压型
                record.setZeroSilkNumber(zeroSilkNumber);//细芯丝号
                record.setZeroRootNumber(zeroRootNumber);//细芯根数
                record.setZeroMembrance(zeroMembrance);//细芯过膜
                record.setZeroPress(zeroPress);//细芯过型
                record.setEcbuiId(ecbuiId);//绝缘类型
                record.setInsulationFireThickness(insulationFireThickness);//粗芯绝缘厚度
                record.setInsulationZeroThickness(insulationZeroThickness);//细芯绝缘厚度
                record.setEcbubId(ecbubId);//包带类型
                record.setBagThickness(bagThickness);//包带厚度
                record.setEcbub22Id(ecbub22Id);//凯装包带类型
                record.setBag22Thickness(bag22Thickness);//凯装包带厚度
                record.setEcbusId(ecbusId);//屏蔽类型
                record.setShieldThickness(shieldThickness);//屏蔽厚度
                record.setShieldPercent(shieldPercent);//屏蔽编织系数
                record.setEcbusbId(ecbusbId);//钢带类型
                record.setSteelbandThickness(steelbandThickness);//钢带厚度
                record.setSteelbandStorey(steelbandStorey);//钢带层数
                record.setEcbusid(ecbusid);//护套类型
                record.setSheathThickness(sheathThickness);//护套厚度
                record.setSheath22Thickness(sheath22Thickness);
                record.setEcbumId(ecbumId);//云母带类型
                record.setMicatapeThickness(micatapeThickness);//云母带厚度
                record.setFireStrand(fireStrand);//火线绞合系数
                record.setZeroStrand(zeroStrand);//零线绞合系数
                record.setEcbuinId(ecbuinId);//填充物类型
                record.setEcbuswId(ecbuswId);//钢丝类型
                record.setSteelwireMembrance(steelwireMembrance);//钢丝过膜
                record.setSteelwirePress(steelwirePress);//钢丝压型
                record.setInputStart(false);//默认不开启手输状态
                record.setNbupsMoney(nbupsMoney);//不开发票单价
                record.setBupsMoney(bupsMoney);//开发票单价
                record.setNbupcMoney(nbupcMoney);//不开发票小计
                record.setBupcMoney(bupcMoney);//开发票小计
                record.setWeight(weight);
                record.setUnitPriceInput(false);
                record.setUnitPrice(new BigDecimal("0"));
                record.setUnitWeight(new BigDecimal("0"));
                record.setEcbuaId(0);//木轴默认没有
                record.setAxleNumber(0);//木轴默认数量为0
                record.setAddTime(System.currentTimeMillis());
                log.info(CommonFunction.getGson().toJson(record));
                boolean silkNameIsExists = false;
                for (EcSilk ecSilk : listSilk) {
                    if (ecSilk.getAbbreviation().equals(ecuqInput.getSilkName())) {
                        silkNameIsExists = true;
                        break;
                    }
                }
                //log.info("silkNameIsExists + " + silkNameIsExists);
                if (silkNameIsExists) {
                    ecuqDescService.insert(record);
                    ecuqInputModel.dealBillPercent(ecuqInput.getEcuqiId());
                }
            } else {//修改
                //System.out.println("list + " + CommonFunction.getGson().toJson(list));
                record.setEcuqdId(ecuqDesc.getEcuqdId());
                record.setEcqulId(ecqulId);
                record.setStoreId(storeId);
                record.setEcbucId(ecbucId);
                record.setCunitPrice(cunitPrice);//导体单价
                record.setCweight(cweight);//导体重量
                record.setStorePercent(storePercent);//仓库利润
                record.setSdunitMoney(sdunitMoney);//仓库运费加点
                record.setAreaStr(areaStr);//截面str
                record.setFireSilkNumber(fireSilkNumber);//粗芯丝号
                record.setFireRootNumber(fireRootNumber);
                record.setFireMembrance(fireMembrance);//粗芯过膜
                record.setFirePress(firePress);//粗芯压型
                record.setZeroSilkNumber(zeroSilkNumber);//细芯丝号
                record.setZeroRootNumber(zeroRootNumber);
                record.setZeroMembrance(zeroMembrance);//细芯过膜
                record.setZeroPress(zeroPress);//细芯过型
                record.setEcbuiId(ecbuiId);//绝缘类型
                record.setInsulationFireThickness(insulationFireThickness);//粗芯绝缘厚度
                record.setInsulationZeroThickness(insulationZeroThickness);//细芯绝缘厚度
                record.setEcbubId(ecbubId);//包带类型
                record.setBagThickness(bagThickness);//包带厚度
                record.setEcbub22Id(ecbub22Id);//包带类型
                record.setBag22Thickness(bag22Thickness);//包带厚度
                record.setEcbusId(ecbusId);//屏蔽类型
                record.setShieldThickness(shieldThickness);//屏蔽厚度
                record.setShieldPercent(shieldPercent);//屏蔽编织系数
                record.setEcbusbId(ecbusbId);//钢带类型
                record.setSteelbandThickness(steelbandThickness);//钢带厚度
                record.setSteelbandStorey(steelbandStorey);//钢带层数
                record.setEcbusid(ecbusid);//护套类型
                record.setSheathThickness(sheathThickness);//护套厚度
                record.setEcbumId(ecbumId);//云母带类型
                record.setMicatapeThickness(micatapeThickness);//云母带厚度
                record.setFireStrand(fireStrand);//火线绞合系数
                record.setZeroStrand(zeroStrand);//零线绞合系数
                record.setEcbuinId(ecbuinId);//填充物类型
                record.setEcbuswId(ecbuswId);//钢丝类型
                record.setSteelwireMembrance(steelwireMembrance);//钢丝过膜
                record.setSteelwirePress(steelwirePress);//钢丝压型
                record.setAddTime(System.currentTimeMillis());
                //System.out.println("h13");
                boolean silkNameIsExists = false;
                for (EcSilk ecSilk : listSilk) {
                    if (ecSilk.getAbbreviation().equals(ecuqInput.getSilkName())) {
                        silkNameIsExists = true;
                        break;
                    }
                }
                //log.info("silkNameIsExists + " + silkNameIsExists);
                if (!silkNameIsExists) {
                    log.info("ecuqiId +" + ecuqInput.getEcuqiId());
                    ecuqDescService.deletePassEcuqiId(ecuqInput.getEcuqiId());
                } else {
                    ecuqDescService.update(record);
                }
            }
        } else {
            ecuqDescService.deletePassEcuqiId(ecuqInput.getEcuqiId());
            EcuqInput record = new EcuqInput();
            record.setEcuqiId(ecuqInput.getEcuqiId());
            ecuqInputService.delete(record);
        }
    }

    //dealUnitPrice 单价提交
    public void dealUnitPrice(int ecuqiId, boolean unitPriceInput, BigDecimal unitPrice) {
        EcuqDesc record = new EcuqDesc();
        record.setEcuqiId(ecuqiId);
        EcuqDesc ecuqDesc = ecuqDescService.getObject(record);
        record.setEcuqdId(ecuqDesc.getEcuqdId());
        record.setUnitPriceInput(unitPriceInput);
        record.setUnitPrice(unitPrice);
        ecuqDescService.update(record);
    }

    //dealMoney 提交金额
    public void dealMoney(int ecuqiId, BigDecimal nbupsMoney, BigDecimal bupsMoney, BigDecimal nbupcMoney, BigDecimal bupcMoney) {
        EcuqDesc record = new EcuqDesc();
        record.setEcuqiId(ecuqiId);
        EcuqDesc ecuqDesc = ecuqDescService.getObject(record);
        record.setEcuqdId(ecuqDesc.getEcuqdId());
        record.setNbupsMoney(nbupsMoney);
        record.setBupsMoney(bupsMoney);
        record.setNbupcMoney(nbupcMoney);
        record.setBupcMoney(bupcMoney);
        ecuqDescService.update(record);
    }

    //dealWeight 提交重量
    public void dealWeight(int ecuqdId, BigDecimal weight) {
        EcuqDesc record = new EcuqDesc();
        record.setEcuqdId(ecuqdId);
        record.setWeight(weight);
        ecuqDescService.update(record);
    }

    //getObjectPassEcuqiId
    public EcuqDesc getObjectPassEcuqiId(int ecuqiId) {
        EcuqDesc record = new EcuqDesc();
        record.setEcuqiId(ecuqiId);
        return ecuqDescService.getObject(record);
    }

    //delete
    public void delete(int ecuqiId) {
        ecuqDescService.deletePassEcuqiId(ecuqiId);
    }
}
