package org.jeecg.modules.cable.controller.systemEcable.model;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.cable.entity.systemEcable.EcbModel;
import org.jeecg.modules.cable.service.systemEcable.IEcbModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;

/**
 * @Description: 系统型号管理
 * @Author: jeecg-boot
 * @Date: 2023-10-25
 * @Version: V1.0
 */
@Slf4j
@Tag(name = "型号管理--系统接口", description = "型号管理--系统接口",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "401", parseValue = true)})})
@RestController
@RequestMapping("/ecableAdminPc/ecbModel")
public class EcbModelController extends JeecgController<EcbModel, IEcbModelService> {
    @Autowired
    private IEcbModelService ecbModelService;

    /**
     * 分页列表查询
     *
     * @param ecbModel
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @Operation(summary = "系统型号管理-分页列表查询", description = "系统型号管理-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(EcbModel ecbModel,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        QueryWrapper<EcbModel> queryWrapper = QueryGenerator.initQueryWrapper(ecbModel, req.getParameterMap());
        Page<EcbModel> page = new Page<>(pageNo, pageSize);
        IPage<EcbModel> pageList = ecbModelService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param ecbModel
     * @return
     */
    @Operation(summary = "系统型号管理-添加", description = "系统型号管理-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody EcbModel ecbModel) {
        ecbModelService.save(ecbModel);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param ecbModel
     * @return
     */
    @Operation(summary = "系统型号管理-编辑", description = "系统型号管理-编辑")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<?> edit(@RequestBody EcbModel ecbModel) {
        ecbModelService.updateById(ecbModel);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @Operation(summary = "系统型号管理-通过id删除", description = "系统型号管理-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        ecbModelService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @Operation(summary = "系统型号管理-批量删除", description = "系统型号管理-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.ecbModelService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功！");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @Operation(summary = "系统型号管理-通过id查询", description = "系统型号管理-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        EcbModel ecbModel = ecbModelService.getById(id);
        return Result.OK(ecbModel);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param ecbModel
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, EcbModel ecbModel) {
        return super.exportXls(request, ecbModel, EcbModel.class, "系统型号管理");
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, EcbModel.class);
    }

}
