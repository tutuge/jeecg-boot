package org.jeecg.modules.online.cgreport.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.online.cgreport.entity.OnlCgreportItem;

import java.util.List;
import java.util.Map;

public interface IOnlCgreportItemService extends IService<OnlCgreportItem> {
    List<Map<String, String>> getAutoListQueryInfo(String paramString);
}
