package org.jeecg.modules.cable.entity.systemOffer;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.jeecg.modules.cable.domain.computeBo.*;
import org.jeecg.modules.cable.entity.systemEcable.*;
import org.jeecg.modules.cable.entity.systemQuality.EcqLevel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Schema(description = "系统成本库表对应数据")
@Data
public class EcOffer {

    @Schema(description = "主键ID")
    @TableId(type = IdType.AUTO)
    private Integer ecoId;

    @Schema(description = "系统质量等级ID")
    private Integer ecqlId;

    @Schema(description = "型号类型ID")
    private Integer ecsId;

    @Schema(description = "是否启用")
    private Boolean startType;

    @Schema(description = "序号")
    private Integer sortId;

    @Schema(description = "截面")
    private String areaStr;

    @Schema(description = "成本加点")
    private BigDecimal addPercent;

    @Schema(description = "材料的json")
    private String material;

    @Schema(description = "重量")
    private BigDecimal defaultWeight;

    @Schema(description = "金额")
    private BigDecimal defaultMoney;


    @Schema(description = "添加时间")
    private Date createTime;

    @Schema(description = "修改时间")
    private Date updateTime;



    @Schema(description = "质量等级")
    @TableField(exist = false)
    private EcqLevel ecqLevel;

    public void setMaterial(String material) {
        this.material = material;
        if (StrUtil.isNotBlank(material)) {
            this.structure = JSONObject.parseObject(material, Structure.class);
            this.conductor = this.structure.getConductor();
            this.internals = this.structure.getInternals();
            this.infilling = this.structure.getInfilling();
            this.externals = this.structure.getExternals();
        }
    }

    @Schema(description = "电缆结构")
    @TableField(exist = false)
    private Structure structure;

    @Schema(description = "导体")
    @TableField(exist = false)
    private Conductor conductor;

    @Schema(description = "内部材料")
    @TableField(exist = false)
    private List<Internal> internals;

    @Schema(description = "填充物")
    @TableField(exist = false)
    private Infilling infilling;

    @Schema(description = "外部材料")
    @TableField(exist = false)
    private List<External> externals;

    public void convert() {
        if (ObjUtil.isNotNull(structure)) {
            this.material = JSONObject.toJSONString(structure);
        }
    }
}
