package org.jeecg.modules.cable.entity.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.common.system.vo.EcUser;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcuNotice {
    private Integer ecunId;//主键ID

    private Integer ecCompanyId;//公司ID

    private Integer ecuId;//用户ID

    private Boolean defaultType;//是否默认

    private Boolean startType;//是否启用

    private Integer sortId;//序号

    private String noticeName;//注意事项名称

    private String title;//标题

    private String content;//内容

    private Long addTime;//添加时间

    private Long updateTime;//修改时间

    private Integer startNumber;

    private Integer pageNumber;

    private EcUser ecUser;//用户
}
