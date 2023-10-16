package org.jeecg.modules.online.cgform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;
import org.jeecg.modules.online.cgform.entity.OnlCgformHead;
import org.jeecg.modules.online.cgform.mapper.OnlCgformFieldMapper;
import org.jeecg.modules.online.cgform.mapper.OnlCgformHeadMapper;
import org.jeecg.modules.online.cgform.service.IOnlineBaseAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("onlineBaseAPI")
public class OnlineBaseAPI implements IOnlineBaseAPI {
    @Autowired
    private OnlCgformHeadMapper onlCgformHeadMapper;

    @Autowired
    private OnlCgformFieldMapper onlCgformFieldMapper;

    public String getOnlineErpCode(String code, String tableType) {
        if ("3".equals(tableType)) {
            String str = code.substring(1);
            OnlCgformHead onlCgformHead = this.onlCgformHeadMapper.selectById(str);
            if (onlCgformHead != null && onlCgformHead.getTableType() == 3) {
                LambdaQueryWrapper<OnlCgformField> lambdaQueryWrapper = Wrappers.lambdaQuery(OnlCgformField.class)
                        .eq(OnlCgformField::getCgformHeadId, str);
                List<OnlCgformField> list = this.onlCgformFieldMapper.selectList(lambdaQueryWrapper);
                if (list != null && list.size() > 0) {
                    String str1 = null;
                    for (OnlCgformField onlCgformField : list) {
                        if (oConvertUtils.isNotEmpty(onlCgformField.getMainTable())) {
                            str1 = onlCgformField.getMainTable();
                            break;
                        }
                    }
                    LambdaQueryWrapper<OnlCgformHead> lambdaQueryWrapper1 = Wrappers.lambdaQuery(OnlCgformHead.class).eq(OnlCgformHead::getTableName, str1);
                    OnlCgformHead onlCgformHead1 = this.onlCgformHeadMapper.selectOne(lambdaQueryWrapper1);
                    if (onlCgformHead1 != null)
                        code = "/" + onlCgformHead1.getId();
                }
            }
        }
        return code;
    }
}
