package org.jeecg.modules.cable.service.systemPcc;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.cable.entity.systemPcc.EcProvince;

import java.util.List;

public interface EcProvinceService {

    List<EcProvince> getList(EcProvince record);

    EcProvince getObjectByName(EcProvince record);


    EcProvince getObjectById(Integer ecpId);

    boolean updateById(EcProvince ecProvince);

    void removeById(Integer id);

    void save(EcProvince ecProvince);

    IPage<EcProvince> selectPageData(Page<EcProvince> page, EcProvince ecProvince);
}
