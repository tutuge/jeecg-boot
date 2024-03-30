package org.jeecg.modules.cable.controller.userCommon.contract;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.plugin.table.LoopRowTableRenderPolicy;
import com.deepoove.poi.util.PoitlIOUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.Constants;
import org.jeecg.common.util.StringUtils;
import org.jeecg.common.util.file.FileUploadUtils;
import org.jeecg.config.UploadConfig;
import org.jeecg.modules.cable.controller.userCommon.contract.vo.DetailVo;
import org.jeecg.modules.cable.entity.price.EcuQuoted;
import org.jeecg.modules.cable.entity.price.EcuqDesc;
import org.jeecg.modules.cable.entity.price.EcuqInput;
import org.jeecg.modules.cable.entity.user.EcCustomer;
import org.jeecg.modules.cable.entity.userCommon.EcbulUnit;
import org.jeecg.modules.cable.entity.userCommon.EcuContract;
import org.jeecg.modules.cable.entity.userEcable.EcuSilkModel;
import org.jeecg.modules.cable.service.price.EcuQuotedService;
import org.jeecg.modules.cable.service.price.EcuqDescService;
import org.jeecg.modules.cable.service.price.EcuqInputService;
import org.jeecg.modules.cable.service.user.EcCustomerService;
import org.jeecg.modules.cable.service.userCommon.EcbulUnitService;
import org.jeecg.modules.cable.service.userCommon.EcuContractService;
import org.jeecg.modules.cable.service.userEcable.EcuSilkModelService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static org.jeecg.common.util.ConvertUpMoney.toChinese;

@Slf4j
@Tag(name = "合同模板--用户接口", description = "合同模板用户接口",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "3", parseValue = true)})})
@RestController
@RequestMapping("/ecableErpPc/contract")
public class EcuContractController {

    @Resource
    private EcuContractService ecuContractService;
    @Resource
    private EcuqInputService ecuqInputService;
    @Resource
    private EcuQuotedService quotedService;
    @Resource
    private EcCustomerService ecCustomerService;
    @Resource
    private EcuSilkModelService ecuSilkModelService;
    @Resource
    private EcbulUnitService ecbulUnitService;
    @Resource
    private EcuqDescService ecuqDescService;


    @Operation(summary = "合同-分页列表查询", description = "合同-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<EcuContract>> queryPageList(EcuContract ecuContract,
                                                    @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                    @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                    HttpServletRequest req) {
        Result<IPage<EcuContract>> result = new Result<>();
        //------------------------------------------------------------------------------------------------
        Page<EcuContract> page = new Page<>(pageNo, pageSize);
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        ecuContract.setEcCompanyId(sysUser.getEcCompanyId());
        IPage<EcuContract> pageList = ecuContractService.selectPage(page, ecuContract);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }


    @Operation(summary = "合同-添加", description = "合同-添加")
    @PostMapping(value = "/add")
    public Result<EcuContract> add(@RequestBody EcuContract ecuContract) {
        Result<EcuContract> result = new Result<>();
        try {
            LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
            ecuContract.setEcuId(sysUser.getUserId());
            ecuContract.setEcCompanyId(sysUser.getEcCompanyId());
            ecuContractService.save(ecuContract);
            result.success("添加成功！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500("操作失败");
        }
        return result;
    }


    @Operation(summary = "合同-编辑", description = "合同-编辑")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<EcuContract> edit(@RequestBody EcuContract ecuContract) {
        Result<EcuContract> result = new Result<>();
        EcuContract silkModel = ecuContractService.getById(ecuContract.getEcucId());
        if (silkModel == null) {
            result.error500("未找到对应实体");
        } else {
            boolean ok = ecuContractService.updateById(ecuContract);
            // TODO 返回false说明什么？
            if (ok) {
                result.success("修改成功!");
            }
        }
        return result;
    }


    @Operation(summary = "合同-通过id删除", description = "合同-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id") String id) {
        try {
            ecuContractService.removeById(id);
        } catch (Exception e) {
            log.error("删除失败", e);
            return Result.error("删除失败!");
        }
        return Result.ok("删除成功!");
    }


    @Operation(summary = "合同-批量删除", description = "合同-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<EcuContract> deleteBatch(@RequestParam(name = "ids") String ids) {
        Result<EcuContract> result = new Result<>();
        if (ids == null || ids.trim().isEmpty()) {
            result.error500("参数不识别！");
        } else {
            this.ecuContractService.removeByIds(Arrays.asList(ids.split(",")));
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
    @Operation(summary = "合同-通过id查询", description = "合同-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<EcuContract> queryById(@RequestParam(name = "id") Integer id) {
        Result<EcuContract> result = new Result<>();
        EcuContract byId = ecuContractService.getVoById(id);
        if (byId == null) {
            result.error500("未找到对应实体");
        } else {
            result.setResult(byId);
            result.setSuccess(true);
        }
        return result;
    }


    @Operation(summary = "合同模板-导入", description = "合同-导入")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(@RequestParam(name = "file") MultipartFile file,
                                 @RequestParam(name = "fileName") String fileName) throws IOException {
        try {
            // 上传文件路径
            String filePath = UploadConfig.getUploadPath();
            // 上传并返回新文件名称
            String excelFileName = FileUploadUtils.upload(filePath, file);
            EcuContract ecuContract = new EcuContract();
            ecuContract.setPath(excelFileName);
            ecuContract.setFileName(fileName);
            LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
            ecuContract.setEcCompanyId(sysUser.getEcCompanyId());
            ecuContract.setEcuId(sysUser.getUserId());
            ecuContract.setAddTime(new Date());
            ecuContractService.save(ecuContract);
            return Result.ok();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @SneakyThrows
    @Operation(summary = "填充合同模板")
    @PostMapping({"/ht"})
    public void hgz(HttpServletResponse response,
                    @RequestParam(name = "ecuqId") Integer ecuqId,
                    @RequestParam(name = "id") Integer id) {
        EcuContract byId = ecuContractService.getById(id);
        if (ObjectUtil.isNull(byId)) {
            throw new RuntimeException("合同模板不存在！");
        }
        String path = byId.getPath();
        if (StrUtil.isBlank(path)) {
            throw new RuntimeException("合同模板文件路径不存在！");
        }
        EcuQuoted objectById = quotedService.getObjectById(ecuqId);
        Integer eccuId = objectById.getEccuId();
        EcCustomer customer = ecCustomerService.getObjectById(eccuId);
        //查询报价单的每行数据
        EcuqInput input = new EcuqInput();
        input.setEcuqId(ecuqId);
        List<EcuqInput> list = ecuqInputService.getList(input);
        if (CollUtil.isEmpty(list)) {
            throw new RuntimeException("报价单明细不存在！");
        }
        int size = list.size();
        String profile = UploadConfig.getProfile();
        String downloadPath = profile + StringUtils.substringAfter(path, Constants.RESOURCE_PREFIX);
        FileInputStream inputStream = new FileInputStream(downloadPath);
        //组装详情
        List<DetailVo> list1 = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;//总金额
        for (int i = 0; i < size; i++) {
            EcuqInput ecuqInput = list.get(i);
            DetailVo vo = new DetailVo();
            vo.setNo(String.valueOf(i + 1));
            Integer ecusmId = ecuqInput.getEcusmId();//型号id
            if (ObjectUtil.isNotNull(ecusmId)) {
                EcuSilkModel ecuSilkModel = ecuSilkModelService.getObjectById(ecusmId);
                if (ObjectUtil.isNotNull(ecuSilkModel)) {
                    String modelFullName = ecuSilkModel.getFullName();
                    vo.setModelName(modelFullName);
                }
            }
            vo.setSpecifications(ecuqInput.getAreaStr());
            Integer ecbuluId = ecuqInput.getEcbuluId();
            if (ObjectUtil.isNotNull(ecbuluId)) {
                EcbulUnit ecbulUnit = ecbulUnitService.getObjectById(ecbuluId);
                vo.setQuantity(ecuqInput.getSaleNumber() + "*" + ecbulUnit.getLengthName());
            } else {
                vo.setQuantity("");
            }
            Integer ecuqiId = ecuqInput.getEcuqiId();
            EcuqDesc desc = new EcuqDesc();
            desc.setEcuqiId(ecuqiId);
            EcuqDesc ecuqDesc = ecuqDescService.getObject(desc);
            BigDecimal billComputeMoney;
            if (ObjectUtil.isNotNull(ecuqDesc)) {
                BigDecimal billSingleMoney = ecuqDesc.getBupsMoney();
                if (ObjectUtil.isNotNull(billSingleMoney)) {
                    vo.setUnitPrice(billSingleMoney.setScale(2, RoundingMode.HALF_UP).toString());
                }
                billComputeMoney = ecuqDesc.getBupcMoney();
                if (ObjectUtil.isNotNull(billComputeMoney)) {
                    vo.setSubtotal(billComputeMoney.setScale(2, RoundingMode.HALF_UP).toString());
                }
            } else {
                billComputeMoney = BigDecimal.ZERO;
            }

            vo.setRemarks(ecuqInput.getItemDesc());
            list1.add(vo);
            total = total.add(billComputeMoney);
        }
        String totalStr = total.setScale(2, RoundingMode.HALF_UP).toString();
        String totalChinese = toChinese(totalStr);
        LoopRowTableRenderPolicy policy = new LoopRowTableRenderPolicy();
        Configure config = Configure.builder()
                .bind("detail", policy).build();
        String companyName = ""; //单位名称
        String tax = "";//税    号
        String address = "";//单位地址
        String phone = "";//电    话
        String bank = "";//开 户 行
        String account = "";//帐   号
        if (ObjectUtil.isNotNull(customer)) {
            companyName = customer.getCompanyName();
            tax = customer.getTaxAccount();
            address = customer.getAddress();
            phone = customer.getCompanyPhone();
            bank = customer.getBankName();
            account = customer.getBankAccount();
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("detail", list1);//产品详细信息
        map.put("totalChinese", totalChinese);//汉字金额
        map.put("totalAmount", totalStr);//汉字金额
        //公司信息
        map.put("companyName", companyName);
        map.put("tax", tax);
        map.put("address", address);
        map.put("phone", phone);
        map.put("bank", bank);
        map.put("account", account);
        XWPFTemplate template = XWPFTemplate.compile(inputStream, config).render(map);
        response.setContentType("application/octet-stream");
        // 设置URLEncoder.encode 防止中文乱码
        String fileName = URLEncoder.encode("合同", StandardCharsets.UTF_8).replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename=\"" + fileName + ".docx" + "\"");
        OutputStream out = response.getOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(out);
        template.write(bos);
        bos.flush();
        out.flush();
        PoitlIOUtils.closeQuietlyMulti(template, bos, out);
    }


    @SneakyThrows
    @Operation(summary = "下载合同模板")
    @PostMapping({"/download"})
    public void downloadTemplate(HttpServletResponse response) {
        // 设置响应的内容类型为文件类型
        response.setContentType("application/octet-stream");
        // 获取要导出的文件路径
        String profile = UploadConfig.getProfile();
        String downloadPath = profile + StringUtils.substringAfter("/profile/合同模板.docx", Constants.RESOURCE_PREFIX);
        File file = new File(downloadPath);
        String fileName = URLEncoder.encode("合同模板", StandardCharsets.UTF_8).replaceAll("\\+", "%20");
        // 设置响应头，指定文件名
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + ".docx" + "\"");

        // 读取文件并输出到响应流
        try (FileInputStream fis = new FileInputStream(file);
             OutputStream os = response.getOutputStream()) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServletException("Unable to export file.", e);
        }

    }
}
