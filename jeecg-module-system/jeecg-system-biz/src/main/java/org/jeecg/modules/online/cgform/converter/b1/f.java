 package org.jeecg.modules.online.cgform.converter.b1;

 import java.util.ArrayList;
 import java.util.List;
 import java.util.Map;
 import org.jeecg.common.system.vo.DictModel;
 import org.jeecg.common.util.SpringContextUtils;
 import org.jeecg.modules.online.cgform.converter.a2.b;
 import org.jeecg.modules.online.cgform.entity.OnlCgformField;
 import org.jeecg.modules.online.cgform.service.IOnlCgformFieldService;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;

 public class f extends b {
   private static final Logger d = LoggerFactory.getLogger(f.class);

   protected IOnlCgformFieldService c;

   public f(OnlCgformField paramOnlCgformField) {
     String str1 = paramOnlCgformField.getDictTable();
     String str2 = paramOnlCgformField.getDictText();
     String str3 = paramOnlCgformField.getDictField();
     ArrayList<DictModel> arrayList = new ArrayList();
     try {
       String str = str2.split(",")[0];
       this.c = (IOnlCgformFieldService)SpringContextUtils.getBean(IOnlCgformFieldService.class);
       List list = this.c.queryLinkTableDictList(str1, str2, str3);
       if (list != null && list.size() > 0)
         for (Map map : list) {
           String str4 = b.a(map, str);
           String str5 = b.a(map, str3);
           arrayList.add(new DictModel(str5, str4));
         }
     } catch (Exception exception) {
       d.error("关联记录组件 导入导出数据翻译失败", exception.getMessage());
     }
     this.b = arrayList;
   }
 }


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\cgform\converter\b\f.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
