package org.jeecg.modules.cable.service.systemCommon;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.cable.entity.systemCommon.EcSpecifications;

import java.util.List;

public interface EcSpecificationsService extends IService<EcSpecifications> {


    List<EcSpecifications> selectListByName(Boolean special,String s0);

    void insert(Boolean special,String s0, String s1);

    void updateByName(boolean b, String s0, String s1);
}
