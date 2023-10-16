package org.jeecg.modules.cable.mapper.dao.userOffer;

import org.jeecg.modules.cable.entity.userOffer.EcuoProgramme;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcuoProgrammeDao {
    List<EcuoProgramme> getList(EcuoProgramme record);

    EcuoProgramme getObject(EcuoProgramme record);

    int insert(EcuoProgramme record);

    int update(EcuoProgramme record);

    int delete(EcuoProgramme record);
}
