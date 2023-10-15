package org.jeecg.modules.online.cgform.converter.b1;

import org.jeecg.common.system.vo.DictModel;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.modules.online.cgform.converter.a1.bConfig;
import org.jeecg.modules.online.cgform.dConstants.bConstant;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;
import org.jeecg.modules.online.cgform.service.IOnlCgformFieldService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class f extends bConfig {
    private static final Logger d = LoggerFactory.getLogger(f.class);

    protected IOnlCgformFieldService c;

    public f(OnlCgformField paramOnlCgformField) {
        String str1 = paramOnlCgformField.getDictTable();
        String str2 = paramOnlCgformField.getDictText();
        String str3 = paramOnlCgformField.getDictField();
        ArrayList<DictModel> arrayList = new ArrayList<>();
        try {
            String str = str2.split(",")[0];
            this.c = SpringContextUtils.getBean(IOnlCgformFieldService.class);
            List<Map<String, Object>> list = this.c.queryLinkTableDictList(str1, str2, str3);
            if (list != null && list.size() > 0)
                for (Map<String, Object> map : list) {
                    String str4 = bConstant.getId(map, str);
                    String str5 = bConstant.getId(map, str3);
                    arrayList.add(new DictModel(str5, str4));
                }
        } catch (Exception exception) {
            d.error("关联记录组件 导入导出数据翻译失败", exception.getMessage());
        }
        this.b = arrayList;
    }
}
