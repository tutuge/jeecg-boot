package org.jeecg.modules.cable.controller.user.notice.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.cable.entity.user.EcuNotice;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "注意事项列表")
public class NoticeVo {

    @Schema(description = "注意事项列表")
    private List<EcuNotice> list;

    @Schema(description = "数量")
    private Long count;
}
