package org.jeecg.modules.cable.controller.api;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.cable.api.AddressApi;
import org.jeecg.modules.cable.entity.hand.Address;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class AddressApiController {
    @PostMapping({"/ecableErpPc/api/getAddress"})
    public Address getAddress(HttpServletRequest request) {
        String text = request.getParameter("text");
        Map<String, Object> map = new HashMap<>();
        map.put("text", text);
        return AddressApi.getAddress(CommonFunction.getGson().toJson(map));
    }
}
