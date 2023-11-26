package org.jeecg.modules.cable.entity.efficiency;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcdCollect {

    @TableId(type = IdType.AUTO)
    private Integer ecdtId;//主键ID

    private Integer ecCompanyId;//公司ID

    private Integer typeId;//数据类型

    private String txtUrl;//txt文件地址

    private Date effectTime;//影响时间
}
