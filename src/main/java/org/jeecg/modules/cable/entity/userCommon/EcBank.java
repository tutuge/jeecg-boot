package org.jeecg.modules.cable.entity.userCommon;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcBank {
    private Integer ecbId;//主键ID

    private Boolean startType;//是否启用

    private Integer sortId;//序号

    private String wechatCode;//微信支付代号

    private String alipayCode;//支付宝支付代号

    private String bankName;//银行名称

    private String description;//备注

    private Long addTime;//添加时间

    private Long updateTime;//修改时间
}
