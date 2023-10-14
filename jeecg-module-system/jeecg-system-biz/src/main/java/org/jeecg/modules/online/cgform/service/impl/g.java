 package org.jeecg.modules.online.cgform.service.impl;

 import com.baomidou.mybatisplus.core.conditions.Wrapper;
 import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
 import java.lang.invoke.SerializedLambda;
 import java.util.List;
 import org.jeecg.common.util.oConvertUtils;
 import org.jeecg.modules.online.cgform.entity.OnlCgformField;
 import org.jeecg.modules.online.cgform.entity.OnlCgformHead;
 import org.jeecg.modules.online.cgform.mapper.OnlCgformFieldMapper;
 import org.jeecg.modules.online.cgform.mapper.OnlCgformHeadMapper;
 import org.jeecg.modules.online.cgform.service.IOnlineBaseAPI;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;

 @Service("onlineBaseAPI")
 public class g implements IOnlineBaseAPI {
   @Autowired
   private OnlCgformHeadMapper onlCgformHeadMapper;

   @Autowired
   private OnlCgformFieldMapper onlCgformFieldMapper;

   public String getOnlineErpCode(String code, String tableType) {
     if ("3".equals(tableType)) {
       String str = code.substring(1);
       OnlCgformHead onlCgformHead = (OnlCgformHead)this.onlCgformHeadMapper.selectById(str);
       if (onlCgformHead != null && onlCgformHead.getTableType().intValue() == 3) {
         LambdaQueryWrapper lambdaQueryWrapper = (LambdaQueryWrapper)(new LambdaQueryWrapper()).eq(OnlCgformField::getCgformHeadId, str);
         List list = this.onlCgformFieldMapper.selectList((Wrapper)lambdaQueryWrapper);
         if (list != null && list.size() > 0) {
           String str1 = null;
           for (OnlCgformField onlCgformField : list) {
             if (oConvertUtils.isNotEmpty(onlCgformField.getMainTable())) {
               str1 = onlCgformField.getMainTable();
               break;
             }
           }
           LambdaQueryWrapper lambdaQueryWrapper1 = (LambdaQueryWrapper)(new LambdaQueryWrapper()).eq(OnlCgformHead::getTableName, str1);
           OnlCgformHead onlCgformHead1 = (OnlCgformHead)this.onlCgformHeadMapper.selectOne((Wrapper)lambdaQueryWrapper1);
           if (onlCgformHead1 != null)
             code = "/" + onlCgformHead1.getId();
         }
       }
     }
     return code;
   }
 }


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\cgform\service\a\g.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
