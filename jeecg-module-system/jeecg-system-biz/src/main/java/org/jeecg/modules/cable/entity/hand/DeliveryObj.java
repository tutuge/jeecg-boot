package org.jeecg.modules.cable.entity.hand;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryObj{
	private Integer ecbudId;//快递ID

	private String deliveryName;//快递名称

	private String description;//备注

	private Boolean dSelect;//是否默认选择

	private BigDecimal unitPrice;//单价

	private BigDecimal price;//价格


}
