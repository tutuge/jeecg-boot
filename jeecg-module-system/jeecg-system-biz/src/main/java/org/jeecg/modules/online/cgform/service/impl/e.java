 package org.jeecg.modules.online.cgform.service.impl;

 import com.baomidou.mybatisplus.core.conditions.Wrapper;
 import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
 import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

 import java.util.List;
 import org.jeecg.common.constant.CommonConstant;
 import org.jeecg.modules.online.cgform.entity.OnlCgformIndex;
 import org.jeecg.modules.online.cgform.mapper.OnlCgformHeadMapper;
 import org.jeecg.modules.online.cgform.mapper.OnlCgformIndexMapper;
 import org.jeecg.modules.online.cgform.service.IOnlCgformIndexService;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;

 @Service("onlCgformIndexServiceImpl")
 public class e extends ServiceImpl<OnlCgformIndexMapper, OnlCgformIndex> implements IOnlCgformIndexService {
   private static final Logger a = LoggerFactory.getLogger(e.class);

   @Autowired
   private OnlCgformHeadMapper onlCgformHeadMapper;

   public void createIndex(String code, String databaseType, String tbname) {
     LambdaQueryWrapper lambdaQueryWrapper = new LambdaQueryWrapper();
     lambdaQueryWrapper.eq(OnlCgformIndex::getCgformHeadId, code);
     List list = list((Wrapper)lambdaQueryWrapper);
     if (list != null && list.size() > 0)
       for (OnlCgformIndex onlCgformIndex : list) {
         if (!CommonConstant.DEL_FLAG_1.equals(onlCgformIndex.getDelFlag()) && "N".equals(onlCgformIndex.getIsDbSynch())) {
           String str1 = "";
           String str2 = onlCgformIndex.getIndexName();
           String str3 = onlCgformIndex.getIndexField();
           String str4 = "normal".equals(onlCgformIndex.getIndexType()) ? " index " : (onlCgformIndex.getIndexType() + " index ");
           switch (databaseType) {
             case "MYSQL":
               str1 = "create " + str4 + str2 + " on " + tbname + "(" + str3 + ")";
               break;
             case "ORACLE":
               str1 = "create " + str4 + str2 + " on " + tbname + "(" + str3 + ")";
               break;
             case "SQLSERVER":
               str1 = "create " + str4 + str2 + " on " + tbname + "(" + str3 + ")";
               break;
             case "POSTGRESQL":
               str1 = "create " + str4 + str2 + " on " + tbname + "(" + str3 + ")";
               break;
             default:
               str1 = "create " + str4 + str2 + " on " + tbname + "(" + str3 + ")";
               break;
           }
           this.onlCgformHeadMapper.executeDDL(str1);
           onlCgformIndex.setIsDbSynch("Y");
           updateById(onlCgformIndex);
         }
       }
   }

   public boolean isExistIndex(String countSql) {
     if (countSql == null)
       return true;
     Integer integer = Integer.valueOf(((OnlCgformIndexMapper)this.baseMapper).queryIndexCount(countSql));
     if (integer != null && integer.intValue() > 0)
       return true;
     return false;
   }

   public List<OnlCgformIndex> getCgformIndexsByCgformId(String cgformId) {
     return ((OnlCgformIndexMapper)this.baseMapper).selectList((Wrapper)(new LambdaQueryWrapper()).in(OnlCgformIndex::getCgformHeadId, new Object[] { cgformId }));
   }
 }


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\cgform\service\a\e.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
