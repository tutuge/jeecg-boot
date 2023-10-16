package org.jeecg.modules.cable.controller.userCommon;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.jeecg.modules.cable.model.user.EcuLoginModel;
import org.jeecg.modules.cable.model.userCommon.EcbuAxleModel;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "木轴")
@RestController
public class EcbuAxleController {
    @Resource
    EcbuAxleModel ecbuAxleModel;
    @Resource
    EcuLoginModel ecuLoginModel;

    @Operation(summary = "获取木轴列表")
    //getList
    @PostMapping({"/ecableErpPc/ecbuAxle/getList"})
    public Map<String, Object> getList(HttpServletRequest request) {

            map = ecbuAxleModel.getListAndCount(request);
        }
        return map;
    }


    @Operation(summary = "获取木轴")
    //getObject
    @PostMapping({"/ecableErpPc/ecbuAxle/getObject"})
    public Map<String, Object> getObjectPassId(HttpServletRequest request) {

            map = ecbuAxleModel.getObject(request);
        }
        return map;
    }

    @Operation(summary = "提交")
    //deal
    @PostMapping({"/ecableErpPc/ecbuAxle/deal"})
    public Map<String, Object> deal(HttpServletRequest request) {

            map = ecbuAxleModel.deal(request);
        }
        return map;
    }

    @Operation(summary = "排序")
    //sort
    @PostMapping({"/ecableErpPc/ecbuAxle/sort"})
    public Map<String, Object> sort(HttpServletRequest request) {

            map = ecbuAxleModel.sort(request);
        }
        return map;
    }

    @Operation(summary = "删除")
    //delete
    @PostMapping({"/ecableErpPc/ecbuAxle/delete"})
    public Map<String, Object> delete(HttpServletRequest request) {

            map = ecbuAxleModel.delete(request);
        }
        return map;
    }


    @Operation(summary = "开启禁用")
    //start
    @PostMapping({"/ecableErpPc/ecbuAxle/start"})
    public Map<String, Object> start(HttpServletRequest request) {

            ecbuAxleModel.start(request);
        }
        return map;
    }
}
