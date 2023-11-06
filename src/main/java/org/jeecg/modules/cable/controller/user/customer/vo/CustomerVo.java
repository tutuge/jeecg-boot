package org.jeecg.modules.cable.controller.user.customer.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.cable.entity.user.EcCustomer;

import java.util.List;

@Schema(description = "收货人")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerVo {


    @Schema(description = "收货人")
    private List<EcCustomer> list;


    @Schema(description = "数量")
    private Long count;
}
