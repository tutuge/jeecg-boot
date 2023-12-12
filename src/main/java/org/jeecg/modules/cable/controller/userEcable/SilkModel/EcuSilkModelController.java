package org.jeecg.modules.cable.controller.userEcable.SilkModel;


import cn.hutool.core.util.ObjUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Validator;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.ConvertUtils;
import org.jeecg.config.BeanValidators;
import org.jeecg.modules.cable.controller.userEcable.SilkModel.bo.UserSilkModelBo;
import org.jeecg.modules.cable.controller.userEcable.SilkModel.vo.SilkModelVo;
import org.jeecg.modules.cable.entity.userEcable.EcuSilk;
import org.jeecg.modules.cable.entity.userEcable.EcuSilkModel;
import org.jeecg.modules.cable.service.userEcable.EcuSilkModelService;
import org.jeecg.modules.cable.service.userEcable.EcuSilkService;
import org.jeecg.poi.excel.ExcelImportUtil;
import org.jeecg.poi.excel.def.NormalExcelConstants;
import org.jeecg.poi.excel.entity.ExportParams;
import org.jeecg.poi.excel.entity.ImportParams;
import org.jeecg.poi.excel.entity.result.ExcelImportResult;
import org.jeecg.poi.excel.view.JeecgEntityExcelView;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
@Tag(name = "型号--用户接口", description = "型号--用户接口",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "303", parseValue = true)})})
@RestController
@RequestMapping("/ecableErpPc/silk/model")
@Validated
public class EcuSilkModelController {

    @Resource
    private EcuSilkModelService ecuSilkModelService;

    @Resource
    private EcuSilkService ecuSilkService;
    @Resource
    private Validator validator;


    @Operation(summary = "型号-分页列表查询", description = "型号-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<SilkModelVo>> queryPageList(EcuSilkModel ecuSilkModel,
                                                    @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                    @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                    HttpServletRequest req) {
        Result<IPage<SilkModelVo>> result = new Result<>();
        //------------------------------------------------------------------------------------------------
        Page<EcuSilkModel> page = new Page<>(pageNo, pageSize);
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        ecuSilkModel.setCompanyId(sysUser.getEcCompanyId());
        IPage<SilkModelVo> pageList = ecuSilkModelService.selectPage(page, ecuSilkModel);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }


    @Operation(summary = "型号-添加", description = "型号-添加")
    @PostMapping(value = "/add")
    public Result<EcuSilkModel> add(@RequestBody EcuSilkModel ecuSilkModel) {
        Result<EcuSilkModel> result = new Result<>();
        try {
            LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
            ecuSilkModel.setEcuId(sysUser.getUserId());
            ecuSilkModel.setCompanyId(sysUser.getEcCompanyId());
            ecuSilkModelService.insert(ecuSilkModel);
            result.success("添加成功！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500("操作失败");
        }
        return result;
    }


    @Operation(summary = "型号-编辑", description = "型号-编辑")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<EcuSilkModel> edit(@RequestBody EcuSilkModel ecuSilkModel) {
        Result<EcuSilkModel> result = new Result<>();
        EcuSilkModel silkModel = ecuSilkModelService.getObjectById(ecuSilkModel.getEcusmId());
        if (silkModel == null) {
            result.error500("未找到对应实体");
        } else {
            boolean ok = ecuSilkModelService.updateById(ecuSilkModel);
            // TODO 返回false说明什么？
            if (ok) {
                result.success("修改成功!");
            }
        }
        return result;
    }


    @Operation(summary = "型号-通过id删除", description = "型号-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "ecusmId", required = true) String id) {
        try {
            ecuSilkModelService.removeById(id);
        } catch (Exception e) {
            log.error("删除失败", e.getMessage());
            return Result.error("删除失败!");
        }
        return Result.ok("删除成功!");
    }


    @Operation(summary = "型号-批量删除", description = "型号-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<EcuSilkModel> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        Result<EcuSilkModel> result = new Result<>();
        if (ids == null || "".equals(ids.trim())) {
            result.error500("参数不识别！");
        } else {
            this.ecuSilkModelService.removeByIds(Arrays.asList(ids.split(",")));
            result.success("删除成功!");
        }
        return result;
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @Operation(summary = "型号-通过id查询", description = "型号-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<SilkModelVo> queryById(@RequestParam(name = "id") Integer id) {
        Result<SilkModelVo> result = new Result<>();
        SilkModelVo ecuSilkModel = ecuSilkModelService.getVoById(id);
        if (ecuSilkModel == null) {
            result.error500("未找到对应实体");
        } else {
            result.setResult(ecuSilkModel);
            result.setSuccess(true);
        }
        return result;
    }

    /**
     * 通过型号名称查询
     *
     * @param name
     * @return
     */
    @Operation(summary = "型号-通过型号名称查询", description = "型号-通过型号名称查询")
    @GetMapping(value = "/queryByName")
    public Result<List<EcuSilkModel>> queryByName(@NotBlank(message = "型号名称不得为空") @RequestParam(name = "name") String name) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        Integer ecCompanyId = sysUser.getEcCompanyId();
        List<EcuSilkModel> ecuSilkModels = ecuSilkModelService.queryByName(name, ecCompanyId);
        return Result.ok(ecuSilkModels);
    }


    @Operation(summary = "型号-导出", description = "型号-导出")
    @PostMapping(value = "/exportSpecificationsXls")
    public ModelAndView exportSpecificationsXls(HttpServletRequest request, HttpServletResponse response) {
        // Step.1 组装查询条件
        QueryWrapper<EcuSilkModel> queryWrapper = null;
        String paramsStr = request.getParameter("paramsStr");
        if (ConvertUtils.isNotEmpty(paramsStr)) {
            String deString = URLDecoder.decode(paramsStr, StandardCharsets.UTF_8);
            EcuSilkModel ecuSilkModel = JSON.parseObject(deString, EcuSilkModel.class);
            //------------------------------------------------------------------------------------------------
            queryWrapper = QueryGenerator.initQueryWrapper(ecuSilkModel, request.getParameterMap());
        }

        // Step.2 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        List<EcuSilkModel> pageList = ecuSilkModelService.list(queryWrapper);
        LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        // 导出文件名称
        mv.addObject(NormalExcelConstants.FILE_NAME, "规格对照列表");
        mv.addObject(NormalExcelConstants.CLASS, EcuSilkModel.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("规格对照列表数据", "导出人:" + user.getRealname(), "导出信息"));
        mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
        return mv;
    }


    @Operation(summary = "用户型号系列+型号-导入", description = "用户型号系列+型号-导入")
    @PostMapping(value = "/import/silk")
    public Result<?> importSilk(@RequestPart("file") MultipartFile file) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        Integer userId = sysUser.getUserId();
        //公司Id
        Integer ecCompanyId = sysUser.getEcCompanyId();
        // 错误信息
        int successNum = 0, failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        // 获取上传文件对象
        ImportParams params = new ImportParams();
        try {
            ExcelImportResult<UserSilkModelBo> importResult = ExcelImportUtil.importExcelVerify(file.getInputStream(), UserSilkModelBo.class, params);
            // 1 查询型号系列
            //查找到对应的型号系列名称
            Map<String, Integer> silkMap = ecuSilkService.silkModelMap(ecCompanyId);
            //型号系列的名字
            List<String> sheetNames = importResult.getSheetNames();
            //型号
            List<List<UserSilkModelBo>> listArray = importResult.getListArray();
            for (int i = 0; i < sheetNames.size(); i++) {
                String sheetName = sheetNames.get(i);
                Integer silkId = silkMap.get(sheetName);
                //如果存在这个id，就不插入型号系列名称了，如果不存在，就创建一条记录
                if (ObjUtil.isNull(silkId)) {
                    EcuSilk silk = new EcuSilk();
                    silk.setAbbreviation(sheetName);
                    silk.setEcuId(userId);
                    silk.setCompanyId(ecCompanyId);
                    silk.setStartType(true);
                    ecuSilkService.save(silk);
                    silkId = silk.getEcusId();
                }
                //开始插入
                List<UserSilkModelBo> silkModelExcelBos = listArray.get(i);
                //对应型号的id等
                Map<String, Integer> silkModelMap = ecuSilkModelService.silkModelMap(silkId, ecCompanyId);
                for (int j = 0; j < silkModelExcelBos.size(); j++) {
                    UserSilkModelBo bo = silkModelExcelBos.get(j);
                    try {
                        BeanValidators.validateWithException(validator, bo);
                        Integer silkModelId = silkModelMap.get(bo.getFullName());
                        if (ObjUtil.isNull(silkModelId)) {
                            //不存在的话就插入
                            EcuSilkModel silkModel = new EcuSilkModel();
                            BeanUtils.copyProperties(bo, silkModel);
                            silkModel.setEcuSilkId(silkId);
                            silkModel.setEcuId(userId);
                            silkModel.setCompanyId(ecCompanyId);
                            silkModel.setStartType(true);
                            ecuSilkModelService.insert(silkModel);
                            successMsg.append("<br/>型号系列 " + sheetName + "第" + j + "行" + silkModel.getAbbreviation() + "新增成功");
                        } else {
                            EcuSilkModel silkModel = new EcuSilkModel();
                            BeanUtils.copyProperties(bo, silkModel);
                            silkModel.setEcusmId(silkModelId);
                            silkModel.setEcuSilkId(silkId);
                            silkModel.setEcuId(userId);
                            silkModel.setCompanyId(ecCompanyId);
                            silkModel.setStartType(true);
                            ecuSilkModelService.updateById(silkModel);
                            successMsg.append("<br/>型号系列 " + sheetName + "第" + j + "行" + silkModel.getAbbreviation() + "更新成功");
                        }
                        successNum++;
                    } catch (Exception e) {
                        failureNum++;
                        String msg = "<br/>" + failureNum + "、型号系列 " + sheetName + "第" + j + "行导入失败：";
                        failureMsg.append(msg + e.getMessage());
                        log.error(msg, e);
                    }
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Result.error("文件解析失败:" + e.getMessage());
        } finally {
            try {
                file.getInputStream().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new RuntimeException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return Result.ok(successMsg.toString());
    }
}
