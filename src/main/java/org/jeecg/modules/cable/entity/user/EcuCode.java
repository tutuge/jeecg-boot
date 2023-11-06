package org.jeecg.modules.cable.entity.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcuCode {
    private Integer ecucId;//主键ID

    private String sendPhone;//发送手机号

    private String code;//验证码 md5

    private Long ip;//ip

    private String ipAddress;//ip物理地址

    private Long addTime;//添加时间

}
