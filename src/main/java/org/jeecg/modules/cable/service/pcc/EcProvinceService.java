package org.jeecg.modules.cable.service.pcc;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.cable.entity.pcc.EcProvince;

import java.util.List;

public interface EcProvinceService extends IService<EcProvince> {

    List<EcProvince> getList(EcProvince record);

    EcProvince getObject(EcProvince record);
}
