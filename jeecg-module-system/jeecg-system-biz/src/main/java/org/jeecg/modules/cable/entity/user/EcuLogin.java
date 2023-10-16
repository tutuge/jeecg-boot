package org.jeecg.modules.cable.entity.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcuLogin {
    private Integer eculId;//主键ID

    private Integer ecuId;//用户ID

    private Integer clientType;//客户端类型 Pc网页端

    private String token;

    private String phoneStr;//手机信息

    private Long effectTime;//影响时间
}
