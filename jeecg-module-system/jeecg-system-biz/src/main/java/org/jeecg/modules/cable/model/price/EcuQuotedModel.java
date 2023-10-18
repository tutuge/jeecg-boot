package org.jeecg.modules.cable.model.price;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.price.quoted.vo.QuotedVo;
import org.jeecg.modules.cable.entity.pcc.EcProvince;
import org.jeecg.modules.cable.entity.price.EcuQuoted;
import org.jeecg.modules.cable.entity.price.EcuqDesc;
import org.jeecg.modules.cable.entity.user.EcuNotice;
import org.jeecg.modules.cable.entity.userCommon.EcbuPcompany;
import org.jeecg.modules.cable.entity.userCommon.EcbuStore;
import org.jeecg.modules.cable.model.user.EcuNoticeModel;
import org.jeecg.modules.cable.model.userCommon.EcbuStoreModel;
import org.jeecg.modules.cable.service.pcc.EcProvinceService;
import org.jeecg.modules.cable.service.price.EcuQuotedService;
import org.jeecg.modules.cable.service.price.EcuqDescService;
import org.jeecg.modules.cable.service.user.EcUserService;
import org.jeecg.modules.cable.service.userCommon.EcbuPcompanyService;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.jeecg.modules.cable.tools.SerialNumber;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class EcuQuotedModel {
    @Resource
    EcUserService ecUserService;
    @Resource
    EcuQuotedService ecuQuotedService;
    @Resource
    EcbuPcompanyService ecbuPcompanyService;
    @Resource
    EcProvinceService ecProvinceService;
    @Resource
    EcbuStoreModel ecbuStoreModel;
    @Resource
    EcuqDescModel ecuqDescModel;
    @Resource
    EcuqDescService ecuqDescService;
    @Resource
    EcuNoticeModel ecuNoticeModel;

    //getList
    public QuotedVo getListAndCount(HttpServletRequest request) {
        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        EcuQuoted record = new EcuQuoted();
        record.setEcCompanyId(ecUser.getEcCompanyId());
        Integer ecuId = ecUser.getEcuId();
        if (ecUser.getTypeId() == 2) {
            record.setEcuId(ecuId);
        }
        if (request.getParameter("ecUsername") != null
                && (ecUser.getTypeId() == 0
                || ecUser.getTypeId() == 1)) {//创建者名称
            String ecUsername = request.getParameter("ecUsername");
            record.setEcUsername("%" + ecUsername + "%");
        }
        if (request.getParameter("name") != null) {//报价单名称
            String name = request.getParameter("name");
            record.setName("%" + name + "%");
        }
        if (request.getParameter("tradeType") != null) {//成交类型
            int tradeType = Integer.parseInt(request.getParameter("tradeType"));
            if (tradeType != 0) {
                record.setTradeType(tradeType);
            }
        }
        if (request.getParameter("customerName") != null) {
            String customerName = request.getParameter("customerName");
            record.setCustomerName("%" + customerName + "%");
        }
        if (request.getParameter("customerPhone") != null) {//客户手机
            String customerPhone = request.getParameter("customerPhone");
            record.setCustomerPhone("%" + customerPhone + "%");
        }
        if (request.getParameter("accountNumber") != null) {//客户账号
            String accountNumber = request.getParameter("accountNumber");
            record.setAccountNumber("%" + accountNumber + "%");
        }
        if (request.getParameter("companyName") != null) {
            String companyName = request.getParameter("companyName");
            record.setCompanyName("%" + companyName + "%");
        }
        if (request.getParameter("provinceName") != null) {
            String provinceName = request.getParameter("provinceName");
            record.setProvinceName("%" + provinceName + "%");
        }
        if (request.getParameter("addStartTime") != null && request.getParameter("addEndTime") != null) {
            long addStartTime = Long.parseLong(request.getParameter("addStartTime"));
            long addEndTime = Long.parseLong(request.getParameter("addEndTime"));
            record.setAddStartTime(addStartTime);
            record.setAddEndTime(addEndTime);
        }
        if (request.getParameter("completeStartTime") != null && request.getParameter("completeEndTime") != null) {
            long completeStartTime = Long.parseLong(request.getParameter("completeStartTime"));
            long completeEndTime = Long.parseLong(request.getParameter("completeEndTime"));
            record.setCompleteStartTime(completeStartTime);
            record.setCompleteEndTime(completeEndTime);
        }
        if (request.getParameter("pageNumber") != null) {
            int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
            int startNumber = (Integer.parseInt(request.getParameter("page")) - 1) * pageNumber;
            record.setStartNumber(startNumber);
            record.setPageNumber(pageNumber);
        }
        log.info("record + " + CommonFunction.getGson().toJson(record));
        List<EcuQuoted> list = ecuQuotedService.getList(record);
        long count = ecuQuotedService.getCount(record);
        return new QuotedVo(list, count);
    }

    //getObject
    public EcuQuoted getObject(HttpServletRequest request) {

        EcuQuoted record = new EcuQuoted();
        if (request.getParameter("ecuqId") != null) {
            int ecuqId = Integer.parseInt(request.getParameter("ecuqId"));
            record.setEcuqId(ecuqId);
        }
        return ecuQuotedService.getObject(record);

    }

    //deal
    public String deal(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        EcUser recordEcUser = new EcUser();
        recordEcUser.setEcuId(ecuId);
        EcUser ecUser = ecUserService.getObject(recordEcUser);
        int status;
        String code;
        String msg;
        int ecuqId = Integer.parseInt(request.getParameter("ecuqId"));
        String serialNumber = SerialNumber.getTradeNumber();//流水号
        String companyName = "";
        BigDecimal deliveryDivide = new BigDecimal("1");//运费除以
        BigDecimal deliveryAdd = new BigDecimal("0");//运费加减
        int tradeType = 1;
        if (request.getParameter("tradeType") != null) {
            tradeType = Integer.parseInt(request.getParameter("tradeType"));
        }
        int billPercentType = 1;
        if (request.getParameter("billPercentType") != null) {
            billPercentType = Integer.parseInt(request.getParameter("billPercentType"));
        }
        String name = "";
        if (request.getParameter("name") != null) {//报价单名称
            name = request.getParameter("name");
        }
        int deliveryStoreId = 0;
        if (request.getParameter("deliveryStoreId") == null) {
            EcbuStore ecbuStore = ecbuStoreModel.getDefaultStore(request);
            if (ecbuStore != null) {
                deliveryStoreId = ecbuStore.getEcbusId();
            }
        } else {
            deliveryStoreId = Integer.parseInt(request.getParameter("deliveryStoreId"));
        }
        int ecpId = 0;//默认省份不填写
        String provinceName = "";
        if (request.getParameter("provinceName") != null) {
            provinceName = request.getParameter("provinceName");
            EcProvince recordProvince = new EcProvince();
            recordProvince.setProvinceName(provinceName);
            EcProvince province = ecProvinceService.getObject(recordProvince);
            if (province != null) {
                ecpId = province.getEcpId();
                provinceName = province.getProvinceName();
            }
        }
        BigDecimal totalWeight = new BigDecimal("0");//总重量
        BigDecimal totalMoney = new BigDecimal("0");//总金额
        BigDecimal deliveryMoney = new BigDecimal("0");//快递金额
        EcuQuoted record = new EcuQuoted();
        if (ecuqId == 0) {//插入
            String billName = "";//开票公司
            BigDecimal nbuptMoney = new BigDecimal("0");//不开发票总计
            BigDecimal buptMoney = new BigDecimal("0");//开发票总计
            int ecbupId = 0;
            EcbuPcompany recordEcbuPcompany = new EcbuPcompany();
            recordEcbuPcompany.setSortId(1);
            EcbuPcompany ecbuPcompany = ecbuPcompanyService.getObject(recordEcbuPcompany);
            if (ecbuPcompany != null) {
                ecbupId = ecbuPcompany.getEcbupId();//平台公司ID
            }
            record.setEcCompanyId(ecUser.getEcCompanyId());
            record.setEcbudId(0);//默认快递是0
            record.setEcuId(ecuId);
            record.setEccuId(0);//客户默认是没有的
            record.setCompanyName(companyName);
            record.setDeliveryStoreId(deliveryStoreId);
            record.setDeliveryDivide(deliveryDivide);//运费除以
            record.setDeliveryAdd(deliveryAdd);//运费加减
            record.setSerialNumber(serialNumber);//流水号
            record.setTradeType(tradeType);//交易类型
            record.setName(name);//报价单名称
            record.setEcpId(ecpId);//省ID
            record.setProvinceName(provinceName);//省名称
            record.setTotalWeight(totalWeight);//总重
            record.setTotalMoney(totalMoney);//总金额
            record.setDeliveryMoney(deliveryMoney);//快递费
            record.setBillPercentType(billPercentType);//发票类型
            record.setEcbupId(ecbupId);//销售平台ID
            record.setBillName(billName);//开票公司
            record.setAddTime(System.currentTimeMillis());
            record.setCompleteTime(System.currentTimeMillis());
            record.setNbuptMoney(nbuptMoney);//不开发票总计
            record.setBuptMoney(buptMoney);//开发票总计
            record.setUnitPriceAdd(new BigDecimal("0"));//单位加价
            record.setAddPricePercent(new BigDecimal("0"));//加价百分比
            String totalTitle = "";
            String totalDesc = "";
            EcuNotice ecuNotice = ecuNoticeModel.getObjectDefaultPassEcuId(ecuId);
            if (ecuNotice != null) {
                totalTitle = ecuNotice.getTitle();
                totalDesc = ecuNotice.getContent();
            }
            record.setTotalTitle(totalTitle);
            record.setTotalDesc(totalDesc);
            ecuQuotedService.insert(record);
            msg = "正常插入数据";
        } else {//更新
            record.setEcuqId(ecuqId);
            if (request.getParameter("ecbupId") != null) {//销售平台
                int ecbupId = Integer.parseInt(request.getParameter("ecbupId"));//销售平台
                record.setEcbupId(ecbupId);
            }
            if (request.getParameter("tradeType") != null) {//交易类型
                record.setTradeType(tradeType);
            }
            if (request.getParameter("name") != null) {//报价单名称
                record.setName(name);
            }
            if (ecpId != 0) {//省ID
                record.setEcpId(ecpId);
            }
            if (request.getParameter("provinceName") != null) {//省名称
                record.setProvinceName(provinceName);
            }
            if (request.getParameter("deliveryStoreId") != null) {//发货地
                record.setDeliveryStoreId(deliveryStoreId);
            }
            if (request.getParameter("billPercentType") != null) {//发票类型
                record.setBillPercentType(billPercentType);
            }
            if (request.getParameter("ecbudId") != null) {//快递类型
                int ecbudId = Integer.parseInt(request.getParameter("ecbudId"));
                record.setEcbudId(ecbudId);
            }
            if (request.getParameter("deliveryDivide") != null) {//运费加点
                deliveryDivide = new BigDecimal(request.getParameter("deliveryDivide"));
                record.setDeliveryDivide(deliveryDivide);
            }
            if (request.getParameter("deliveryAdd") != null) {//运费加减
                deliveryAdd = new BigDecimal(request.getParameter("deliveryAdd"));
                record.setDeliveryAdd(deliveryAdd);
            }
            if (request.getParameter("unitPriceAdd") != null) {//单位加价
                BigDecimal unitPriceAdd = new BigDecimal(request.getParameter("unitPriceAdd"));
                record.setUnitPriceAdd(unitPriceAdd);
            }
            if (request.getParameter("addPricePercent") != null) {//加价百分比
                BigDecimal addPricePercent = new BigDecimal(request.getParameter("addPricePercent"));
                record.setAddPricePercent(addPricePercent);
            }
            if (request.getParameter("companyName") != null) {
                companyName = request.getParameter("companyName");
                record.setCompanyName(companyName);
            }
            //log.info(CommonFunction.getGson().toJson(record));
            ecuQuotedService.update(record);
            //更新客户及公司信息
            msg = "正常更新数据";
        }

        return msg;
    }

    //getLatestObject
    public EcuQuoted getLatestObject(HttpServletRequest request) {
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        EcuQuoted record = new EcuQuoted();
        record.setEcuId(ecuId);
        return ecuQuotedService.getLatestObject(record);
    }

    //dealMoneyPassInput 通过手输的方式改变总额
    public void dealMoneyPassInput(HttpServletRequest request) {

        int ecuqId = Integer.parseInt(request.getParameter("ecuqId"));
        BigDecimal nbuptMoney = new BigDecimal("0");
        BigDecimal buptMoney = new BigDecimal("0");
        EcuQuoted record = new EcuQuoted();
        record.setEcuqId(ecuqId);
        if (request.getParameter("nbuptMoney") != null) {
            nbuptMoney = new BigDecimal(request.getParameter("nbuptMoney"));
            record.setNbuptMoney(nbuptMoney);
        }
        if (request.getParameter("buptMoney") != null) {
            buptMoney = new BigDecimal(request.getParameter("buptMoney"));
            record.setBuptMoney(buptMoney);
        }
        //System.out.println(CommonFunction.getGson().toJson(record));
        //更新desc
        ecuqDescModel.dealMoneyPassQuoted(ecuqId, buptMoney, nbuptMoney);
        //更新本表
        ecuQuotedService.update(record);
    }

    //complete 提交报价单，成交
    public void dealComplete(HttpServletRequest request) {

        int ecuqId = Integer.parseInt(request.getParameter("ecuqId"));
        BigDecimal totalMoney = new BigDecimal(request.getParameter("totalMoney"));
        //总重量和总金额
        EcuqDesc recordEcuqDesc = new EcuqDesc();
        recordEcuqDesc.setEcuqdId(ecuqId);
        List<EcuqDesc> listDesc = ecuqDescService.getList(recordEcuqDesc);
        BigDecimal totalWeight = new BigDecimal("0");
        for (EcuqDesc ecuqDesc : listDesc) {
            totalWeight = totalWeight.add(ecuqDesc.getWeight());
        }
        EcuQuoted record = new EcuQuoted();
        record.setEcuqId(ecuqId);
        record.setTotalWeight(totalWeight);
        record.setTotalMoney(totalMoney);
        record.setTradeType(2);//已成交
        ecuQuotedService.update(record);
    }

    //dealTotalDesc
    public void dealTotalDesc(HttpServletRequest request) {

        int ecuqId = Integer.parseInt(request.getParameter("ecuqId"));
        String totalTitle = request.getParameter("totalTitle");
        String totalDesc = request.getParameter("totalDesc");
        EcuQuoted record = new EcuQuoted();
        record.setEcuqId(ecuqId);
        record.setTotalTitle(totalTitle);
        record.setTotalDesc(totalDesc);
        ecuQuotedService.update(record);

    }

    /***===数据模型===***/
//dealTotalMoney 修改总额
    public void dealMoney(int ecuqId, BigDecimal nbuptMoney, BigDecimal buptMoney) {
        EcuQuoted record = new EcuQuoted();
        record.setEcuqId(ecuqId);
        record.setNbuptMoney(nbuptMoney);
        record.setBuptMoney(buptMoney);
        //log.info(CommonFunction.getGson().toJson(record));
        ecuQuotedService.update(record);
    }

    //dealDeliveryMoney 修改运费
    public void dealDeliveryMoney(int ecuqId, BigDecimal deliveryMoney) {
        EcuQuoted record = new EcuQuoted();
        record.setEcuqId(ecuqId);
        record.setDeliveryMoney(deliveryMoney);
        ecuQuotedService.update(record);
    }

    //cleanMoney 清除金额
    public void cleanMoney(int ecuqId) {
        BigDecimal nbuptMoney = new BigDecimal("0");
        BigDecimal buptMoney = new BigDecimal("0");
        BigDecimal deliveryMoney = new BigDecimal("0");
        EcuQuoted record = new EcuQuoted();
        record.setEcuqId(ecuqId);
        record.setNbuptMoney(nbuptMoney);
        record.setBuptMoney(buptMoney);
        record.setDeliveryMoney(deliveryMoney);
        //System.out.println("cleanMoney + " + CommonFunction.getGson().toJson(record));
        ecuQuotedService.update(record);
    }

    //dealTotalWeight
    public void dealTotalWeight(int ecuqId, BigDecimal totalWeight) {
        EcuQuoted record = new EcuQuoted();
        record.setEcuqId(ecuqId);
        record.setTotalWeight(totalWeight);
        ecuQuotedService.update(record);
    }

    //dealEccuId 更新关联客户信息
    public void dealEccuId(int ecuqId, int eccuId) {
        EcuQuoted record = new EcuQuoted();
        record.setEcuqId(ecuqId);
        record.setEccuId(eccuId);
        log.info("record + " + CommonFunction.getGson().toJson(record));
        ecuQuotedService.update(record);
    }

}
