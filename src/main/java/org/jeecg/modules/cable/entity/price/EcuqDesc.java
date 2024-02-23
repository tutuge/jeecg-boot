package org.jeecg.modules.cable.entity.price;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.jeecg.modules.cable.domain.computeBo.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Schema(description = "订单的金额详细信息等")
public class EcuqDesc {

    @Schema(description = "主键ID")
    @TableId(type = IdType.AUTO)
    private Integer ecuqdId;

    @Schema(description = "报价单ID")
    private Integer ecuqId;

    @Schema(description = "成本库表ID")
    private Integer ecuoId;

    @Schema(description = "报价明细的Id")
    private Integer ecuqiId;

    @Schema(description = "质量等级ID")
    private Integer ecqulId;


    @Schema(description = "仓库ID")
    private Integer storeId;

    @Schema(description = "是否启用")
    private Boolean startType;

    @Schema(description = "序号")
    private Integer sortId;

    @Schema(description = "导体单价")
    private BigDecimal cunitPrice;

    @Schema(description = "1米导体重量")
    private BigDecimal cweight;

    @Schema(description = "仓库利润")
    private BigDecimal storePercent;

    @Schema(description = "仓库运费加点")
    private BigDecimal sdunitMoney;

    @Schema(description = "截面")
    private String areaStr;

    @Schema(description = "成本加点")
    private BigDecimal addPercent;


    @Schema(description = "是否启动手输 true 开启手输 false 不开启")
    private Boolean inputStart = false;

    @Schema(description = "不开票的单价")
    private BigDecimal nbupsMoney;// no bill unit price single money 不开票的单价

    @Schema(description = "开票单价")
    private BigDecimal bupsMoney;// bill unit price single money 开票单价

    @Schema(description = "不开票小计")
    private BigDecimal nbupcMoney;// no bill unit price compute money 不开票小计

    @Schema(description = "开票小计")
    private BigDecimal bupcMoney;// bill unit price compute money 开票小计


    @Schema(description = "本明细总计重量")
    private BigDecimal weight;

    @Schema(description = "税前单价是否手输")
    private Boolean unitPriceInput = false;

    @Schema(description = "税前单价")
    private BigDecimal unitPrice;

    @Schema(description = "1米的重量")
    private BigDecimal unitWeight;

    @Schema(description = "木轴ID")
    private Integer ecbuaId;

    @Schema(description = "木轴的数量")
    private Integer axleNumber;

    @Schema(description = "添加时间")
    private Date addTime;

    @Schema(description = "修改时间")
    private Date updateTime;


    @Schema(description = "材料的json")
    private String material;

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
