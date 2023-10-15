package org.jeecg.modules.online.cgreport.service.a;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.online.cgreport.c.c;
import org.jeecg.modules.online.cgreport.entity.OnlCgreportItem;
import org.jeecg.modules.online.cgreport.mapper.OnlCgreportItemMapper;
import org.jeecg.modules.online.cgreport.service.IOnlCgreportItemService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("onlCgreportItemServiceImpl")
public class OnlCgreportItemServiceImpl extends ServiceImpl<OnlCgreportItemMapper, OnlCgreportItem> implements IOnlCgreportItemService {
    @Cacheable(value = {"sys:cache:online:rp"}, key = "'search-'+#cgrheadId")
    public List<Map<String, String>> getAutoListQueryInfo(String cgrheadId) {
        LambdaQueryWrapper<OnlCgreportItem> lambdaQueryWrapper = Wrappers.lambdaQuery(OnlCgreportItem.class)
                .eq(OnlCgreportItem::getCgrheadId, cgrheadId)
                .eq(OnlCgreportItem::getIsSearch, 1);
        List<OnlCgreportItem> list = list(lambdaQueryWrapper);
        ArrayList<HashMap<Object, Object>> arrayList = new ArrayList<>();
        byte b = 0;
        for (OnlCgreportItem onlCgreportItem : list) {
            HashMap<Object, Object> hashMap = new HashMap<>(5);
            hashMap.put("label", onlCgreportItem.getFieldTxt());
            String str = onlCgreportItem.getDictCode();
            if (oConvertUtils.isNotEmpty(str)) {
                if (c.b(str)) {
                    hashMap.put("view", "search");
                    hashMap.put("sql", str);
                } else {
                    hashMap.put("view", "list");
                }
            } else {
                hashMap.put("view", onlCgreportItem.getFieldType().toLowerCase());
            }
            hashMap.put("mode", oConvertUtils.isEmpty(onlCgreportItem.getSearchMode()) ? "single" : onlCgreportItem.getSearchMode());
            hashMap.put("field", onlCgreportItem.getFieldName());
            if (++b > 2)
                hashMap.put("hidden", "1");
            arrayList.add(hashMap);
        }
        return (List) arrayList;
    }
}
