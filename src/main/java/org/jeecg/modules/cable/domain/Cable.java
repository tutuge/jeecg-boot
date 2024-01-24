package org.jeecg.modules.cable.domain;

import cn.hutool.core.util.StrUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(description = "电缆对象，数据与计算")
@Data
public class Cable {
    /**
     * 电缆规格
     */
    private String areaStr;
    /**
     * 粗芯导体数量
     */
    private Integer fireNumber;

    /**
     * 细芯导体数量
     */
    private Integer zeroNumber;


    public Cable(String areaStr) {
        this.setAreaStr(areaStr);
    }

    //计算粗芯细芯导体数量
    public void setAreaStr(String areaStr) {
        this.areaStr = areaStr;
        if (StrUtil.isNotBlank(areaStr)) {
            String[] areaArr = areaStr.split("\\+");
            String[] fireArr = areaArr[0].split("\\*");
            this.fireNumber = Integer.valueOf(fireArr[0]);
            if (areaArr.length == 2) {
                String[] split = areaArr[1].split("\\*");
                if (split.length == 2) {
                    this.zeroNumber = Integer.valueOf(split[0]);
                }
            }
        } else {
            throw new RuntimeException("规格不得为空");
        }
    }

    //--------------------导体相关-------------

    /**
     * 导体
     */
    private ConductorMaterial conductorMaterial;

    //--------------------导体到填充物------------------------


    /**
     * 内部材料
     */
    private List<InternalMaterial> internalMaterials;

    //-------------填充物-----------------

    private InfillingMaterial infillingMaterial;


    //----------------填充物之外材料----------------


    /**
     * 外部材料
     */
    private List<ExternalMaterial> externalMaterials;
}
