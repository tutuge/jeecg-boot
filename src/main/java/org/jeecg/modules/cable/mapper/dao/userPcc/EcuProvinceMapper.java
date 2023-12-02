package org.jeecg.modules.cable.mapper.dao.userPcc;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.jeecg.modules.cable.entity.userPcc.EcuProvince;

import java.util.List;

@Mapper
public interface EcuProvinceMapper extends BaseMapper<EcuProvince> {

    List<EcuProvince> selectPccByCompanyId(Integer ecCompanyId);

    /**
     * 根据省份ID排序
     * @param ecCompanyId
     * @return
     */
    @Select("select * from ecu_province where ec_company_id = #{ecCompanyId} order by ecp_id desc limit 1")
    EcuProvince getEcpIdLastOne(@Param("ecCompanyId") Integer ecCompanyId);
}
