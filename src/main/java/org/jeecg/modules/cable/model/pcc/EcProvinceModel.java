package org.jeecg.modules.cable.model.pcc;

import jakarta.annotation.Resource;
import org.jeecg.modules.cable.entity.systemPcc.EcCity;
import org.jeecg.modules.cable.entity.systemPcc.EcCounty;
import org.jeecg.modules.cable.entity.systemPcc.EcProvince;
import org.jeecg.modules.cable.service.systemPcc.EcCityService;
import org.jeecg.modules.cable.service.systemPcc.EcCountyService;
import org.jeecg.modules.cable.service.systemPcc.EcProvinceService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EcProvinceModel {
    @Resource
    EcProvinceService ecProvinceService;
    @Resource
    EcCityService ecCityService;
    @Resource
    EcCountyService ecCountyService;

    // 获取省市县三级联动 为快递提供数据
    public List<EcProvince> getListContact() {
        List<EcProvince> list;
        List<EcProvince> ecProvinces = new ArrayList<>();
        List<EcCity> listCity;
        List<EcCounty> listCounty;
        EcProvince recordProvince = new EcProvince();
        recordProvince.setStartType(true);
        list = ecProvinceService.getList(recordProvince);
        EcProvince record;
        for (EcProvince ecProvince : list) {
            Integer ecpId = ecProvince.getEcpId();
            EcCity recordCity = new EcCity();
            recordCity.setStartType(true);
            recordCity.setEcpId(ecpId);
            listCity = ecCityService.getList(recordCity);
            if (!listCity.isEmpty()) {
                for (EcCity ecCity : listCity) {
                    Integer eccId = ecCity.getEccId();
                    EcCounty recordCounty = new EcCounty();
                    recordCounty.setStartType(true);
                    recordCounty.setEccId(eccId);
                    listCounty = ecCountyService.getList(recordCounty);
                    if (listCounty != null) {
                        for (EcCounty ecCounty : listCounty) {
                            record = new EcProvince();
                            record.setEcpId(ecProvince.getEcpId());
                            record.setProvinceName(ecProvince.getProvinceName() + "-" + ecCity.getCityName() + "-" + ecCounty.getCountyName());
                            ecProvinces.add(record);
                        }
                    } else {
                        record = new EcProvince();
                        record.setEcpId(ecProvince.getEcpId());
                        record.setProvinceName(ecProvince.getProvinceName() + "-" + ecCity.getCityName() + "-无");
                        ecProvinces.add(record);
                    }
                }
            } else {
                record = new EcProvince();
                record.setEcpId(ecProvince.getEcpId());
                record.setProvinceName(ecProvince.getProvinceName() + "-无-无");
                ecProvinces.add(record);
            }
        }
        return ecProvinces;
    }


    public List<EcProvince> getListStart() {
        EcProvince record = new EcProvince();
        record.setStartType(true);
        return ecProvinceService.getList(record);
    }
}
