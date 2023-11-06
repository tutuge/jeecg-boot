package org.jeecg.modules.cable.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.cable.api.AddressApi;
import org.jeecg.modules.cable.controller.api.bo.AddressBo;
import org.jeecg.modules.cable.entity.hand.Address;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Tag(name = "请求百度获取地址")
@RestController
@Slf4j
public class AddressApiController {

    @Operation(summary = "请求百度识别地址")
    @PostMapping({"/ecableErpPc/api/getAddress"})
    public Address getAddress(@RequestBody AddressBo bo) {
        String text = bo.getText();
        Map<String, Object> map = new HashMap<>();
        map.put("text", text);
        return AddressApi.getAddress(CommonFunction.getGson().toJson(map));
    }
}
