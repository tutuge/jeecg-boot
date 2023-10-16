package org.jeecg.modules.cable.entity.user;

import org.jeecg.common.system.vo.EcUser;
import org.jeecg.modules.cable.entity.userCommon.EcbuStore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcuData {
    private Integer ecudId;//主键ID

    private Integer ecCompanyId;//公司ID

    private Integer ecuId;//用户ID

    private Boolean startType;//是否启用

    private Integer ecbusId;//仓库ID

    private String silkName;//丝名称

    private Long addTime;//添加时间

    private Long updateTime;//修改时间

    private Integer startNumber;

    private Integer pageNumber;

    private EcbuStore ecbuStore;

    private EcUser ecUser;
}
