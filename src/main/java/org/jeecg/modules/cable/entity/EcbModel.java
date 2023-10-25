package .org.jeecg.modules.cable.entity;

import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Description: 系统型号管理
 * @Author: jeecg-boot
 * @Date:   2023-10-25
 * @Version: V1.0
 */
@Data
@TableName("ecb_model")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="ecb_model对象", description="系统型号管理")
public class EcbModel {
    
	/**主键ID*/
	@Excel(name = "主键ID", width = 15)
    @ApiModelProperty(value = "主键ID")
	private java.lang.Integer ecbmId;
	/**型号名称*/
	@Excel(name = "型号名称", width = 15)
    @ApiModelProperty(value = "型号名称")
	private java.lang.String modelName;
	/**导体ID*/
	@Excel(name = "导体ID", width = 15)
    @ApiModelProperty(value = "导体ID")
	private java.lang.Integer conductorId;
	/**云母带ID*/
	@Excel(name = "云母带ID", width = 15)
    @ApiModelProperty(value = "云母带ID")
	private java.lang.Integer micatapeId;
	/**绝缘ID*/
	@Excel(name = "绝缘ID", width = 15)
    @ApiModelProperty(value = "绝缘ID")
	private java.lang.Integer insulationId;
	/**填充物ID*/
	@Excel(name = "填充物ID", width = 15)
    @ApiModelProperty(value = "填充物ID")
	private java.lang.Integer infillingId;
	/**包带ID*/
	@Excel(name = "包带ID", width = 15)
    @ApiModelProperty(value = "包带ID")
	private java.lang.Integer bagId;
	/**屏蔽ID*/
	@Excel(name = "屏蔽ID", width = 15)
    @ApiModelProperty(value = "屏蔽ID")
	private java.lang.Integer shieldId;
	/**钢带ID*/
	@Excel(name = "钢带ID", width = 15)
    @ApiModelProperty(value = "钢带ID")
	private java.lang.Integer steelBandId;
	/**护套ID*/
	@Excel(name = "护套ID", width = 15)
    @ApiModelProperty(value = "护套ID")
	private java.lang.Integer sheathId;
	/**是否启用*/
	@Excel(name = "是否启用", width = 15)
    @ApiModelProperty(value = "是否启用")
	private java.lang.Integer startType;
	/**添加时间*/
	@Excel(name = "添加时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "添加时间")
	private java.util.Date addTime;
	/**修改时间*/
	@Excel(name = "修改时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "修改时间")
	private java.util.Date updateTime;
}
