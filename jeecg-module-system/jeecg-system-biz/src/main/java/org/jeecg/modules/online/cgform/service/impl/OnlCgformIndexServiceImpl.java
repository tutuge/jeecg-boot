package org.jeecg.modules.online.cgform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.modules.online.cgform.entity.OnlCgformIndex;
import org.jeecg.modules.online.cgform.mapper.OnlCgformHeadMapper;
import org.jeecg.modules.online.cgform.mapper.OnlCgformIndexMapper;
import org.jeecg.modules.online.cgform.service.IOnlCgformIndexService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("onlCgformIndexServiceImpl")
public class OnlCgformIndexServiceImpl extends ServiceImpl<OnlCgformIndexMapper, OnlCgformIndex> implements IOnlCgformIndexService {
    private static final Logger a = LoggerFactory.getLogger(OnlCgformIndexServiceImpl.class);

    @Autowired
    private OnlCgformHeadMapper onlCgformHeadMapper;

    public void createIndex(String code, String databaseType, String tbname) {
        LambdaQueryWrapper<OnlCgformIndex> lambdaQueryWrapper = Wrappers.lambdaQuery(OnlCgformIndex.class)
                .eq(OnlCgformIndex::getCgformHeadId, code);
        List<OnlCgformIndex> list = list(lambdaQueryWrapper);
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
        Integer integer = this.baseMapper.queryIndexCount(countSql);
        if (integer != null && integer > 0)
            return true;
        return false;
    }

    public List<OnlCgformIndex> getCgformIndexsByCgformId(String cgformId) {
        return this.baseMapper.selectList(Wrappers.lambdaQuery(OnlCgformIndex.class)
                .in(OnlCgformIndex::getCgformHeadId, cgformId));
    }
}
