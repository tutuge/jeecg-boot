package org.jeecg.modules.cable.controller.userCommon;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.jeecg.modules.cable.model.user.EcuLoginModel;
import org.jeecg.modules.cable.model.userCommon.EcbuStoreModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@Tag(name = "仓库")
@RestController
public class EcbuStoreController {
    @Resource
    EcuLoginModel ecuLoginModel;
    @Resource
    EcbuStoreModel ecbuStoreModel;

    @Operation(summary = "获取仓库列表")
    //getList
    @PostMapping({"/ecableErpPc/ecbuStore/getList"})
    public Map<String, Object> getList(HttpServletRequest request) {

            map = ecbuStoreModel.getListAndCount(request);
        }
        return map;
    }

    @Operation(summary = "获取仓库")
    //getObject
    @PostMapping({"/ecableErpPc/ecbuStore/getObject"})
    public Map<String, Object> getObject(HttpServletRequest request) {

            map = ecbuStoreModel.getObject(request);
        }
        return map;
    }


    @Operation(summary = "编辑仓库")
    //deal
    @PostMapping({"/ecableErpPc/ecbuStore/deal"})
    public Map<String, Object> deal(HttpServletRequest request) throws IOException {

            map = ecbuStoreModel.deal(request);
        }
        return map;
    }

    @Operation(summary = "仓库排序")
    //sort
    @PostMapping({"/ecableErpPc/ecbuStore/sort"})
    public Map<String, Object> sort(HttpServletRequest request) throws IOException {

            map = ecbuStoreModel.sort(request);
        }
        return map;
    }

    @Operation(summary = "删除仓库")
    //delete
    @PostMapping({"/ecableErpPc/ecbuStore/delete"})
    public Map<String, Object> delete(HttpServletRequest request) throws IOException {

            map = ecbuStoreModel.delete(request);
        }
        return map;
    }


    @Operation(summary = "设置默认仓库")
    //dealDefault 设置默认项
    @PostMapping({"/ecableErpPc/ecbuStore/dealDefault"})
    public Map<String, Object> defaultType(HttpServletRequest request) {

            map = ecbuStoreModel.dealDefault(request);
        }
        return map;
    }


    @Operation(summary = "开启禁用")
    //start
    @PostMapping({"/ecableErpPc/ecbuStore/start"})
    public Map<String, Object> start(HttpServletRequest request) throws IOException {

            map = ecbuStoreModel.start(request);
        }
        return map;
    }
}
