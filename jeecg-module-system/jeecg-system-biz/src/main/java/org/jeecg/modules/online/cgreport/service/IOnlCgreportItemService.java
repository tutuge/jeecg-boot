package org.jeecg.modules.online.cgreport.service;

import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;
import java.util.Map;
import org.jeecg.modules.online.cgreport.entity.OnlCgreportItem;

public interface IOnlCgreportItemService extends IService<OnlCgreportItem> {
  List<Map<String, String>> getAutoListQueryInfo(String paramString);
}


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\cgreport\service\IOnlCgreportItemService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */