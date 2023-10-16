package org.jeecg.modules.cable.model.pcc;

import org.jeecg.modules.cable.entity.pcc.EcCity;
import org.jeecg.modules.cable.entity.pcc.EcCounty;
import org.jeecg.modules.cable.entity.pcc.EcProvince;
import org.jeecg.modules.cable.service.pcc.EcCityService;
import org.jeecg.modules.cable.service.pcc.EcCountyService;
import org.jeecg.modules.cable.service.pcc.EcProvinceService;
import jakarta.annotation.Resource;
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

    //获取省市县三级联动 为快递提供数据
    public List<EcProvince> getListContact() {
        List<EcProvince> list;
        List<EcProvince> list_new = new ArrayList<>();
        List<EcCity> listCity;
        List<EcCounty> listCounty;
        EcProvince recordProvince = new EcProvince();
        recordProvince.setStartType(true);
        list = ecProvinceService.getList(recordProvince);
        EcProvince record;
        int i = 0;
        for (EcProvince ecProvince : list) {
            System.out.println("i + " + i);
            i++;
            int ecpId = ecProvince.getEcpId();
            EcCity recordCity = new EcCity();
            recordCity.setStartType(true);
            recordCity.setEcpId(ecpId);
            listCity = ecCityService.getList(recordCity);
            if (!listCity.isEmpty()) {
                for (EcCity ecCity : listCity) {
                    int eccId = ecCity.getEccId();
                    EcCounty recordCounty = new EcCounty();
                    recordCounty.setStartType(true);
                    recordCounty.setEccId(eccId);
                    listCounty = ecCountyService.getList(recordCounty);
                    if (listCounty != null) {
                        for (EcCounty ecCounty : listCounty) {
                            record = new EcProvince();
                            record.setEcpId(ecProvince.getEcpId());
                            record.setProvinceName(ecProvince.getProvinceName() + "-" + ecCity.getCityName() + "-" + ecCounty.getCountyName());
                            list_new.add(record);
                        }
                    } else {
                        record = new EcProvince();
                        record.setEcpId(ecProvince.getEcpId());
                        record.setProvinceName(ecProvince.getProvinceName() + "-" + ecCity.getCityName() + "-无");
                        list_new.add(record);
                    }
                }
            } else {
                record = new EcProvince();
                record.setEcpId(ecProvince.getEcpId());
                record.setProvinceName(ecProvince.getProvinceName() + "-无-无");
                list_new.add(record);
            }
        }
        return list_new;
    }

    //getListStart
    public List<EcProvince> getListStart() {
        EcProvince record = new EcProvince();
        record.setStartType(true);
        return ecProvinceService.getList(record);
    }
}
