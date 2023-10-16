package org.jeecg.modules.cable.controller.userDelivery;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.jeecg.modules.cable.model.user.EcuLoginModel;
import org.jeecg.modules.cable.model.userDelivery.EcbudPriceModel;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "物流")
@RestController
public class EcbudPriceController {
    @Resource
    EcbudPriceModel ecbudPriceModel;
    @Resource
    EcuLoginModel ecuLoginModel;


    @Operation(summary = "物流信息加载")
    @PostMapping({"/ecableErpPc/ecbudPrice/load"})
    public Map<String, Object> load(HttpServletRequest request) {
        Map<String, Object> map;
        int status;
        String code;
        String msg;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            ecbudPriceModel.load(request);
            status = 3;//正常获取数据
            code = "200";
            msg = "正常加载数据";
            CommonFunction.getCommonMap(map, status, code, msg);
        }
        return map;
    }


    @Operation(summary = "物流信息列表")
    //getList
    @PostMapping({"/ecableErpPc/ecbudPrice/getList"})
    public Map<String, Object> getList(HttpServletRequest request) {

            map = ecbudPriceModel.getListAndCount(request);
        }
        return map;
    }


    @Operation(summary = "物流信息详情")
    //getObject
    @PostMapping({"/ecableErpPc/ecbudPrice/getObject"})
    public Map<String, Object> getObject(HttpServletRequest request) {

            map = ecbudPriceModel.getObject(request);
        }
        return map;
    }


    @Operation(summary = "物流信息编辑")
    //deal
    @PostMapping({"/ecableErpPc/ecbudPrice/deal"})
    public Map<String, Object> deal(HttpServletRequest request) {

            map = ecbudPriceModel.deal(request);
        }
        return map;
    }


    @Operation(summary = "物流信息排序")
    //sort
    @PostMapping({"/ecableErpPc/ecbudPrice/sort"})
    public Map<String, Object> sort(HttpServletRequest request) {

            map = ecbudPriceModel.sort(request);
        }
        return map;
    }


    @Operation(summary = "物流信息删除")
    //delete
    @PostMapping({"/ecableErpPc/ecbudPrice/delete"})
    public Map<String, Object> delete(HttpServletRequest request) {

            map = ecbudPriceModel.delete(request);
        }
        return map;
    }


    @Operation(summary = "物流信息开启禁用")
    //start
    @PostMapping({"/ecableErpPc/ecbudPrice/start"})
    public Map<String, Object> start(HttpServletRequest request) {

            map = ecbudPriceModel.start(request);
        }
        return map;
    }
}
