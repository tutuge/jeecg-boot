package org.jeecg.modules.cable.service.user;


import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.cable.entity.user.EcCompany;

public interface EcCompanyService {

    EcCompany getObject(EcCompany record);
    EcCompany getObjectById(Integer ecCompanyId);

    Integer insert(EcCompany record);

    /**
     * 根据公司名称处理客户的公司信息，对应名称找到了公司的话，就返回，没有的话就创建
     *
     * @param companyName 公司名称
     * @return 公司信息
     */
    EcCompany detailCompany(String companyName);
}
