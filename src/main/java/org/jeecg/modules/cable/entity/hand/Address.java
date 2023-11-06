package org.jeecg.modules.cable.entity.hand;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private String province;//省

    private String city;//市

    private String county;//县

    private String town;//镇

    private String detail;//详情

    private String person;//名称

    private String phonenum;//手机号
}
