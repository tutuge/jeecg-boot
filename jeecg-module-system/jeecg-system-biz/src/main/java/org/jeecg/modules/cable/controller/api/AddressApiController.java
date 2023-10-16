package org.jeecg.modules.cable.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.cable.api.AddressApi;
import org.jeecg.modules.cable.entity.hand.Address;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
@Tag(name = "请求百度获取地址")
@RestController
@Slf4j
public class AddressApiController {

    @Operation(summary = "请求百度识别地址")
    @PostMapping({"/ecableErpPc/api/getAddress"})
    public Address getAddress(HttpServletRequest request) {
        String text = request.getParameter("text");
        Map<String, Object> map = new HashMap<>();
        map.put("text", text);
        return AddressApi.getAddress(CommonFunction.getGson().toJson(map));
    }
}
