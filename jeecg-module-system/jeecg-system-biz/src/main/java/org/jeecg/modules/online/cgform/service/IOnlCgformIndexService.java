package org.jeecg.modules.online.cgform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;
import org.jeecg.modules.online.cgform.entity.OnlCgformIndex;

public interface IOnlCgformIndexService extends IService<OnlCgformIndex> {
  void createIndex(String paramString1, String paramString2, String paramString3);

  boolean isExistIndex(String paramString);

  List<OnlCgformIndex> getCgformIndexsByCgformId(String paramString);
}
