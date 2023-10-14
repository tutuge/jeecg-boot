 package org.jeecg.modules.online.cgform.service.impl;

 import com.alibaba.fastjson.JSON;
 import com.alibaba.fastjson.JSONObject;
 import com.baomidou.mybatisplus.core.conditions.Wrapper;
 import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

 import java.util.ArrayList;
 import java.util.Collections;
 import java.util.Comparator;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.List;
 import java.util.Map;
 import java.util.stream.Collectors;
 import org.apache.commons.lang.StringUtils;
 import org.apache.shiro.SecurityUtils;
 import org.jeecg.common.system.api.ISysBaseAPI;
 import org.jeecg.common.system.vo.DictModel;
 import org.jeecg.common.system.vo.LoginUser;
 import org.jeecg.common.util.oConvertUtils;
 import org.jeecg.modules.online.auth.service.IOnlAuthPageService;
 import org.jeecg.modules.online.cgform.a1.aEntity;
 import org.jeecg.modules.online.cgform.b1.bConstant;

 import org.jeecg.modules.online.cgform.dConstants.c;
 import org.jeecg.modules.online.cgform.entity.OnlCgformButton;
 import org.jeecg.modules.online.cgform.entity.OnlCgformEnhanceJs;
 import org.jeecg.modules.online.cgform.entity.OnlCgformField;
 import org.jeecg.modules.online.cgform.entity.OnlCgformHead;
 import org.jeecg.modules.online.cgform.model.HrefSlots;
 import org.jeecg.modules.online.cgform.model.OnlColumn;


 import org.jeecg.modules.online.cgform.model.g;
 import org.jeecg.modules.online.cgform.service.IOnlCgformFieldService;
 import org.jeecg.modules.online.cgform.service.IOnlCgformHeadService;
 import org.jeecg.modules.online.cgform.service.IOnlineService;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.cache.annotation.Cacheable;
 import org.springframework.context.annotation.Lazy;
 import org.springframework.stereotype.Service;

 @Service("onlineService")
 public class i implements IOnlineService {
   private static final Logger a = LoggerFactory.getLogger(i.class);

   @Autowired
   private IOnlCgformFieldService onlCgformFieldService;

   @Autowired
   private IOnlCgformHeadService onlCgformHeadService;

   @Lazy
   @Autowired
   private ISysBaseAPI sysBaseAPI;

   @Autowired
   private IOnlAuthPageService onlAuthPageService;

   public bConstant queryOnlineConfig(OnlCgformHead head, String username) {
     String str = head.getId();
     boolean bool = bConstant.a(head);
     List<OnlCgformField> list = b(str);
     List list1 = this.onlAuthPageService.queryHideCode(str, true);
     ArrayList<OnlColumn> arrayList = new ArrayList();
     HashMap<Object, Object> hashMap1 = new HashMap<>(5);
     ArrayList<HrefSlots> arrayList1 = new ArrayList();
     ArrayList<c> arrayList2 = new ArrayList();
     ArrayList<String> arrayList3 = new ArrayList();
     HashMap<Object, Object> hashMap2 = new HashMap<>(5);
     List list2 = head.getSelectFieldList();
     for (OnlCgformField onlCgformField : list) {
       String str1 = onlCgformField.getDbFieldName();
       String str2 = onlCgformField.getMainTable();
       String str3 = onlCgformField.getMainField();
       if (oConvertUtils.isNotEmpty(str3) && oConvertUtils.isNotEmpty(str2)) {
         c c = new c(str1, str3);
         arrayList2.add(c);
       }
       if (onlCgformField.getIsShowList() == null || 1 != onlCgformField.getIsShowList().intValue())
         continue;
       if ("id".equals(str1))
         continue;
       if (list1.contains(str1))
         continue;
       if (arrayList3.contains(str1))
         continue;
       if (list2 != null && list2.size() > 0 && list2.indexOf(str1) < 0)
         continue;
       OnlColumn onlColumn = a(onlCgformField, (Map)hashMap1, arrayList1);
       hashMap2.put(onlCgformField.getDbFieldName(), Integer.valueOf(1));
       arrayList.add(onlColumn);
       String str4 = onlColumn.getLinkField();
       if (str4 != null && !"".equals(str4))
         a(list, arrayList3, arrayList, str1, str4);
     }
     a(arrayList, arrayList3);
     if (bool) {
       List<OnlColumn> list4 = a(head, (Map)hashMap1, arrayList1, (Map)hashMap2);
       if (list4.size() > 0) {
         ArrayList<String> arrayList5 = new ArrayList();
         for (String str1 : hashMap2.keySet()) {
           if (((Integer)hashMap2.get(str1)).intValue() > 1)
             arrayList5.add(str1);
         }
         for (OnlColumn onlColumn : list4) {
           String str1 = onlColumn.getDataIndex();
           if (arrayList5.contains(str1))
             onlColumn.setDataIndex(onlColumn.getTableName() + "_" + str1);
           arrayList.add(onlColumn);
         }
       }
     }
     bConstant b = new bConstant();
     b.setCode(str);
     b.setTableType(head.getTableType());
     b.setFormTemplate(head.getFormTemplate());
     b.setDescription(head.getTableTxt());
     b.setCurrentTableName(head.getTableName());
     b.setPaginationFlag(head.getIsPage());
     b.setCheckboxFlag(head.getIsCheckbox());
     b.setScrollFlag(head.getScroll());
     b.setRelationType(head.getRelationType());
     b.setColumns(arrayList);
     b.setDictOptions(hashMap1);
     b.setFieldHrefSlots(arrayList1);
     b.setForeignKeys(arrayList2);
     b.setHideColumns(list1);
     List list3 = this.onlCgformHeadService.queryButtonList(str, true);
     ArrayList<OnlCgformButton> arrayList4 = new ArrayList();
     for (OnlCgformButton onlCgformButton : list3) {
       if (list1.contains(onlCgformButton.getButtonCode()))
         continue;
       arrayList4.add(onlCgformButton);
     }
     b.setCgButtonList(arrayList4);
     OnlCgformEnhanceJs onlCgformEnhanceJs = this.onlCgformHeadService.queryEnhanceJs(str, "list");
     if (onlCgformEnhanceJs != null && oConvertUtils.isNotEmpty(onlCgformEnhanceJs.getCgJs())) {
       String str1 = c.b(onlCgformEnhanceJs.getCgJs(), list3);
       b.setEnhanceJs(str1);
     }
     if ("Y".equals(head.getIsTree())) {
       b.setPidField(head.getTreeParentIdField());
       b.setHasChildrenField(head.getTreeIdField());
       b.setTextField(head.getTreeFieldname());
     }
     return b;
   }

   private void a(List<OnlColumn> paramList, List<String> paramList1) {
     Iterator<OnlColumn> iterator = paramList.iterator();
     while (iterator.hasNext()) {
       OnlColumn onlColumn = iterator.next();
       String str = onlColumn.getDataIndex();
       if (paramList1 != null && paramList1.indexOf(str) >= 0 && oConvertUtils.isEmpty(onlColumn.getCustomRender()))
         iterator.remove();
     }
   }

   private String[] a(String paramString) {
     String[] arrayOfString = { "", "" };
     if (paramString != null && !"".equals(paramString)) {
       JSONObject jSONObject = JSON.parseObject(paramString);
       if (jSONObject.containsKey("store"))
         arrayOfString[0] = oConvertUtils.camelToUnderline(jSONObject.getString("store"));
       if (jSONObject.containsKey("text"))
         arrayOfString[1] = oConvertUtils.camelToUnderline(jSONObject.getString("text"));
     }
     return arrayOfString;
   }

   private void a(List<OnlCgformField> paramList, List<String> paramList1, List<OnlColumn> paramList2, String paramString1, String paramString2) {
     if (oConvertUtils.isNotEmpty(paramString2)) {
       String[] arrayOfString = paramString2.split(",");
       for (String str : arrayOfString) {
         for (OnlCgformField onlCgformField : paramList) {
           String str1 = onlCgformField.getDbFieldName();
           if (1 != onlCgformField.getIsShowList().intValue())
             continue;
           if (str.equals(str1)) {
             paramList1.add(str);
             OnlColumn onlColumn = new OnlColumn(onlCgformField.getDbFieldTxt(), str1);
             onlColumn.setCustomRender(paramString1);
             paramList2.add(onlColumn);
             break;
           }
         }
       }
     }
   }

   public JSONObject queryOnlineFormObj(OnlCgformHead head, OnlCgformEnhanceJs onlCgformEnhanceJs) {
     JSONObject jSONObject1 = new JSONObject();
     String str1 = head.getId();
     String str2 = head.getTaskId();
     List list = this.onlCgformFieldService.queryAvailableFields(str1, head.getTableName(), str2, false);
     ArrayList arrayList = new ArrayList();
     if (oConvertUtils.isEmpty(str2)) {
       List list2 = this.onlAuthPageService.queryFormDisabledCode(head.getId());
       if (list2 != null && list2.size() > 0 && list2.get(0) != null)
         arrayList.addAll(list2);
     } else {
       List list2 = this.onlCgformFieldService.queryDisabledFields(head.getTableName(), str2);
       if (list2 != null && list2.size() > 0 && list2.get(0) != null)
         arrayList.addAll(list2);
     }
     c.a(onlCgformEnhanceJs, head.getTableName(), list);
     org.jeecg.modules.online.cgform.model.i i1 = null;
     if ("Y".equals(head.getIsTree())) {
       i1 = new org.jeecg.modules.online.cgform.model.i();
       i1.setCodeField("id");
       i1.setFieldName(head.getTreeParentIdField());
       i1.setPidField(head.getTreeParentIdField());
       i1.setPidValue("0");
       i1.setHsaChildField(head.getTreeIdField());
       i1.setTableName(bConstant.f(head.getTableName()));
       i1.setTextField(head.getTreeFieldname());
     }
     JSONObject jSONObject2 = bConstant.a(list, arrayList, i1);
     jSONObject2.put("table", head.getTableName());
     jSONObject2.put("describe", head.getTableTxt());
     jSONObject1.put("schema", jSONObject2);
     jSONObject1.put("head", head);
     List<OnlCgformButton> list1 = queryFormValidButton(str1);
     if (list1 != null && list1.size() > 0)
       jSONObject1.put("cgButtonList", list1);
     if (onlCgformEnhanceJs != null && oConvertUtils.isNotEmpty(onlCgformEnhanceJs.getCgJs())) {
       String str = c.c(onlCgformEnhanceJs.getCgJs(), list1);
       onlCgformEnhanceJs.setCgJs(str);
       jSONObject1.put("enhanceJs", c.a(onlCgformEnhanceJs.getCgJs()));
     }
     return jSONObject1;
   }

   @Cacheable(value = {"sys:cache:online:form"}, key = "'erp'+ #head.id+'-'+#username")
   public JSONObject queryOnlineFormObj(OnlCgformHead head, String username) {
     OnlCgformEnhanceJs onlCgformEnhanceJs = this.onlCgformHeadService.queryEnhanceJs(head.getId(), "form");
     return queryOnlineFormObj(head, onlCgformEnhanceJs);
   }

   public List<OnlCgformButton> queryFormValidButton(String headId) {
     List list = this.onlCgformHeadService.queryButtonList(headId, false);
     List<OnlCgformButton> list1 = null;
     if (list != null && list.size() > 0) {
       LoginUser loginUser = (LoginUser)SecurityUtils.getSubject().getPrincipal();
       String str = loginUser.getId();
       List list2 = this.onlAuthPageService.queryFormHideButton(str, headId);
       list1 = (List)list.stream().filter(paramOnlCgformButton -> (paramList == null || paramList.indexOf(paramOnlCgformButton.getButtonCode()) < 0)).collect(Collectors.toList());
     }
     return list1;
   }

   @Cacheable(value = {"sys:cache:online:form"}, key = "#head.id+'-'+#username")
   public JSONObject queryOnlineFormItem(OnlCgformHead head, String username) {
     head.setTaskId(null);
     return a(head);
   }

   public JSONObject queryFlowOnlineFormItem(OnlCgformHead head, String username, String taskId) {
     head.setTaskId(taskId);
     return a(head);
   }

   @Cacheable(value = {"sys:cache:online:form"}, key = "'enhancejs' + #code + '-' + #type")
   public String queryEnahcneJsString(String code, String type) {
     String str = "";
     OnlCgformEnhanceJs onlCgformEnhanceJs = this.onlCgformHeadService.queryEnhanceJs(code, type);
     if (onlCgformEnhanceJs != null && oConvertUtils.isNotEmpty(onlCgformEnhanceJs.getCgJs()))
       str = c.b(onlCgformEnhanceJs.getCgJs(), null);
     return str;
   }

   public JSONObject getOnlineVue3QueryInfo(String headId) {
     OnlCgformHead onlCgformHead = (OnlCgformHead)this.onlCgformHeadService.getById(headId);
     boolean bool = bConstant.a(onlCgformHead);
     ArrayList<String> arrayList = new ArrayList();
     JSONObject jSONObject1 = a(headId, arrayList, true, (String)null);
     JSONObject jSONObject2 = jSONObject1.getJSONObject("properties");
     jSONObject1.put("title", onlCgformHead.getTableTxt());
     jSONObject1.put("table", onlCgformHead.getTableName());
     jSONObject1.put("joinQuery", Boolean.valueOf(bool));
     jSONObject1.put("searchFieldList", arrayList);
     if (bConstant.aE.equals(onlCgformHead.getTableType())) {
       String str = onlCgformHead.getSubTableStr();
       if (str != null && !"".equals(str)) {
         String[] arrayOfString = str.split(",");
         for (String str1 : arrayOfString) {
           OnlCgformHead onlCgformHead1 = (OnlCgformHead)this.onlCgformHeadService.getOne((Wrapper)(new LambdaQueryWrapper()).eq(OnlCgformHead::getTableName, str1));
           if (onlCgformHead1 != null) {
             JSONObject jSONObject = a(onlCgformHead1.getId(), arrayList, false, str1);
             jSONObject.put("title", onlCgformHead1.getTableTxt());
             jSONObject.put("view", "table");
             jSONObject2.put(str1, jSONObject);
           }
         }
       }
     }
     return jSONObject1;
   }

   public List<DictModel> getOnlineTableDictData(String table, String text, String code) {
     List list = null;
     try {
       OnlCgformHead onlCgformHead = this.onlCgformHeadService.getTable(table);
       LambdaQueryWrapper lambdaQueryWrapper = (LambdaQueryWrapper)((LambdaQueryWrapper)(new LambdaQueryWrapper()).eq(OnlCgformField::getCgformHeadId, onlCgformHead.getId())).eq(OnlCgformField::getDbFieldName, text);
       List<OnlCgformField> list2 = this.onlCgformFieldService.list((Wrapper)lambdaQueryWrapper);
       if (list2 != null && list2.size() > 0) {
         OnlCgformField onlCgformField = list2.get(0);
         String str1 = onlCgformField.getDictTable();
         String str2 = onlCgformField.getDictField();
         String str3 = onlCgformField.getDictText();
         if (oConvertUtils.isNotEmpty(str1) && oConvertUtils.isNotEmpty(str2) && oConvertUtils.isNotEmpty(str3)) {
           list = this.sysBaseAPI.queryTableDictItemsByCode(str1, str3, str2);
         } else if (oConvertUtils.isNotEmpty(str2)) {
           list = this.sysBaseAPI.queryDictItemsByCode(str2);
         }
       }
     } catch (Exception exception) {
       a.error("他表字段获取字典数据失败", exception.getMessage());
     }
     List<DictModel> list1 = this.sysBaseAPI.queryTableDictItemsByCode(table, text, code);
     if (list != null && list.size() > 0)
       for (DictModel dictModel : list1) {
         String str = dictModel.getText();
         for (DictModel dictModel1 : list) {
           if (dictModel1.getValue().equals(str))
             dictModel.setText(dictModel1.getText());
         }
       }
     return list1;
   }

   private JSONObject a(String paramString1, List<String> paramList, boolean paramBoolean, String paramString2) {
     LambdaQueryWrapper lambdaQueryWrapper = new LambdaQueryWrapper();
     lambdaQueryWrapper.eq(OnlCgformField::getCgformHeadId, paramString1);
     lambdaQueryWrapper.and(paramLambdaQueryWrapper -> (LambdaQueryWrapper)((LambdaQueryWrapper)((LambdaQueryWrapper)paramLambdaQueryWrapper.eq(OnlCgformField::getIsShowList, Integer.valueOf(1))).or()).eq(OnlCgformField::getIsQuery, Integer.valueOf(1)));
     lambdaQueryWrapper.eq(OnlCgformField::getDbIsPersist, bConstant.b);
     lambdaQueryWrapper.orderByAsc(OnlCgformField::getOrderNum);
     List list = this.onlCgformFieldService.list((Wrapper)lambdaQueryWrapper);
     for (OnlCgformField onlCgformField : list) {
       onlCgformField.setFieldDefaultValue(null);
       if ("1".equals(onlCgformField.getQueryConfigFlag())) {
         onlCgformField.setFieldDefaultValue(onlCgformField.getQueryDefVal());
         onlCgformField.setDictField(onlCgformField.getQueryDictField());
         onlCgformField.setDictTable(onlCgformField.getQueryDictTable());
         onlCgformField.setDictText(onlCgformField.getQueryDictText());
         onlCgformField.setFieldShowType(onlCgformField.getQueryShowType());
       }
       if (1 == onlCgformField.getIsQuery().intValue()) {
         if (paramBoolean) {
           paramList.add(onlCgformField.getDbFieldName());
           continue;
         }
         paramList.add(paramString2 + "@" + onlCgformField.getDbFieldName());
       }
     }
     JSONObject jSONObject = bConstant.a(list, null, null);
     bConstant.b(jSONObject);
     return jSONObject;
   }

   private List<OnlCgformField> b(String paramString) {
     LambdaQueryWrapper lambdaQueryWrapper = new LambdaQueryWrapper();
     lambdaQueryWrapper.eq(OnlCgformField::getCgformHeadId, paramString);
     lambdaQueryWrapper.orderByAsc(OnlCgformField::getOrderNum);
     return this.onlCgformFieldService.list((Wrapper)lambdaQueryWrapper);
   }

   private JSONObject a(OnlCgformHead paramOnlCgformHead) {
     OnlCgformEnhanceJs onlCgformEnhanceJs = this.onlCgformHeadService.queryEnhanceJs(paramOnlCgformHead.getId(), "form");
     JSONObject jSONObject = queryOnlineFormObj(paramOnlCgformHead, onlCgformEnhanceJs);
     jSONObject.put("formTemplate", paramOnlCgformHead.getFormTemplate());
     List list = this.onlAuthPageService.queryHideCode(paramOnlCgformHead.getId(), true);
     if (list != null && list.indexOf("update") >= 0)
       jSONObject.put("form_disable_update", Boolean.valueOf(true));
     if (paramOnlCgformHead.getTableType().intValue() == 2) {
       JSONObject jSONObject1 = jSONObject.getJSONObject("schema");
       String str = paramOnlCgformHead.getSubTableStr();
       if (oConvertUtils.isNotEmpty(str)) {
         ArrayList<OnlCgformHead> arrayList = new ArrayList();
         for (String str1 : str.split(",")) {
           OnlCgformHead onlCgformHead = (OnlCgformHead)this.onlCgformHeadService.getOne((Wrapper)(new LambdaQueryWrapper()).eq(OnlCgformHead::getTableName, str1));
           if (onlCgformHead != null)
             arrayList.add(onlCgformHead);
         }
         if (arrayList.size() > 0) {
           Collections.sort(arrayList, new Comparator<OnlCgformHead>(this) {
                 public int a(OnlCgformHead param1OnlCgformHead1, OnlCgformHead param1OnlCgformHead2) {
                   Integer integer1 = param1OnlCgformHead1.getTabOrderNum();
                   if (integer1 == null)
                     integer1 = Integer.valueOf(0);
                   Integer integer2 = param1OnlCgformHead2.getTabOrderNum();
                   if (integer2 == null)
                     integer2 = Integer.valueOf(0);
                   return integer1.compareTo(integer2);
                 }
               });
           for (OnlCgformHead onlCgformHead : arrayList) {
             List list1 = this.onlCgformFieldService.queryAvailableFields(onlCgformHead.getId(), onlCgformHead.getTableName(), paramOnlCgformHead.getTaskId(), false);
             c.b(onlCgformEnhanceJs, onlCgformHead.getTableName(), list1);
             JSONObject jSONObject2 = new JSONObject();
             List list2 = new ArrayList();
             if (oConvertUtils.isNotEmpty(paramOnlCgformHead.getTaskId())) {
               List list3 = this.onlCgformFieldService.queryDisabledFields(onlCgformHead.getTableName(), paramOnlCgformHead.getTaskId());
             } else {
               list2 = this.onlAuthPageService.queryFormDisabledCode(onlCgformHead.getId());
             }
             if (1 == onlCgformHead.getRelationType().intValue()) {
               jSONObject2 = bConstant.a(list1, list2, null);
             } else {
               jSONObject2.put("columns", bConstant.a(list1, list2));
               List list3 = this.onlAuthPageService.queryListHideButton(null, onlCgformHead.getId());
               jSONObject2.put("hideButtons", list3);
             }
             String str1 = this.onlCgformFieldService.queryForeignKey(onlCgformHead.getId(), paramOnlCgformHead.getTableName());
             jSONObject2.put("foreignKey", str1);
             jSONObject2.put("id", onlCgformHead.getId());
             jSONObject2.put("describe", onlCgformHead.getTableTxt());
             jSONObject2.put("key", onlCgformHead.getTableName());
             jSONObject2.put("view", "tab");
             jSONObject2.put("order", onlCgformHead.getTabOrderNum());
             jSONObject2.put("relationType", onlCgformHead.getRelationType());
             jSONObject2.put("formTemplate", onlCgformHead.getFormTemplate());
             jSONObject1.getJSONObject("properties").put(onlCgformHead.getTableName(), jSONObject2);
           }
         }
       }
       if (onlCgformEnhanceJs != null && oConvertUtils.isNotEmpty(onlCgformEnhanceJs.getCgJs()))
         jSONObject.put("enhanceJs", c.a(onlCgformEnhanceJs.getCgJs()));
     }
     return jSONObject;
   }

   private OnlColumn a(OnlCgformField paramOnlCgformField, Map<String, List<DictModel>> paramMap, List<HrefSlots> paramList) {
     String str1 = paramOnlCgformField.getDbFieldName();
     OnlColumn onlColumn = new OnlColumn(paramOnlCgformField.getDbFieldTxt(), str1);
     onlColumn.setDbType(paramOnlCgformField.getDbType());
     String str2 = paramOnlCgformField.getDictField();
     String str3 = paramOnlCgformField.getFieldShowType();
     if (str3 == null)
       return onlColumn;
     if (oConvertUtils.isNotEmpty(str2) && !"popup".equals(str3) && !"link_table".equals(str3)) {
       List<DictModel> list = new ArrayList();
       if (oConvertUtils.isNotEmpty(paramOnlCgformField.getDictTable())) {
         List list1 = this.sysBaseAPI.queryTableDictItemsByCode(paramOnlCgformField.getDictTable(), paramOnlCgformField.getDictText(), str2);
       } else if (oConvertUtils.isNotEmpty(paramOnlCgformField.getDictField())) {
         list = this.sysBaseAPI.queryDictItemsByCode(str2);
       }
       paramMap.put(str1, list);
       onlColumn.setCustomRender(str1);
     }
     if ("switch".equals(str3)) {
       List<DictModel> list = bConstant.b(paramOnlCgformField);
       paramMap.put(str1, list);
       onlColumn.setCustomRender(str1);
     }
     if ("link_table_field".equals(str3))
       onlColumn.setFieldType(str3);
     if ("link_table".equals(str3)) {
       onlColumn.setFieldType(str3);
       onlColumn.setHrefSlotName(paramOnlCgformField.getDictTable());
     }
     if ("link_down".equals(str3)) {
       String str = paramOnlCgformField.getDictTable();
       aEntity a = (aEntity)JSONObject.parseObject(str, aEntity.class);
       try {
         List<DictModel> list = this.sysBaseAPI.queryTableDictItemsByCode(a.getTable(), a.getTxt(), a.getKey());
         paramMap.put(str1, list);
         onlColumn.setCustomRender(str1);
         onlColumn.setLinkField(a.getLinkField());
       } catch (Exception exception) {
         a.warn("联动组件配置错误!", exception.getMessage());
       }
     }
     if ("sel_tree".equals(str3)) {
       String[] arrayOfString = paramOnlCgformField.getDictText().split(",");
       List<DictModel> list = this.sysBaseAPI.queryTableDictItemsByCode(paramOnlCgformField.getDictTable(), arrayOfString[2], arrayOfString[0]);
       paramMap.put(str1, list);
       onlColumn.setCustomRender(str1);
     }
     if ("cat_tree".equals(str3)) {
       String str = paramOnlCgformField.getDictText();
       if (oConvertUtils.isEmpty(str)) {
         String str5 = bConstant.e(paramOnlCgformField.getDictField());
         List<DictModel> list = this.sysBaseAPI.queryFilterTableDictInfo("SYS_CATEGORY", "NAME", "ID", str5);
         paramMap.put(str1, list);
         onlColumn.setCustomRender(str1);
       } else {
         onlColumn.setCustomRender("_replace_text_" + str);
       }
     }
     if ("sel_depart".equals(str3)) {
       String[] arrayOfString = a(paramOnlCgformField.getFieldExtendJson());
       String str5 = (arrayOfString[0].length() > 0) ? arrayOfString[0] : "ID";
       String str6 = (arrayOfString[1].length() > 0) ? arrayOfString[1] : "DEPART_NAME";
       List<DictModel> list = this.sysBaseAPI.queryTableDictItemsByCode("SYS_DEPART", str6, str5);
       paramMap.put(str1, list);
       onlColumn.setCustomRender(str1);
     }
     if ("sel_user".equals(paramOnlCgformField.getFieldShowType())) {
       String[] arrayOfString = a(paramOnlCgformField.getFieldExtendJson());
       String str5 = (arrayOfString[0].length() > 0) ? arrayOfString[0] : "USERNAME";
       String str6 = (arrayOfString[1].length() > 0) ? arrayOfString[1] : "REALNAME";
       List<DictModel> list = this.sysBaseAPI.queryTableDictItemsByCode("SYS_USER", str6, str5);
       paramMap.put(str1, list);
       onlColumn.setCustomRender(str1);
     }
     if (str3.indexOf("file") >= 0) {
       onlColumn.setScopedSlots(new g("fileSlot"));
     } else if (str3.indexOf("image") >= 0) {
       onlColumn.setScopedSlots(new g("imgSlot"));
     } else if (str3.indexOf("editor") >= 0) {
       onlColumn.setScopedSlots(new g("htmlSlot"));
     } else if (str3.equals("date")) {
       onlColumn.setScopedSlots(new g("dateSlot"));
     } else if (str3.equals("pca")) {
       onlColumn.setScopedSlots(new g("pcaSlot"));
     }
     if (StringUtils.isNotBlank(paramOnlCgformField.getFieldHref())) {
       String str = "fieldHref_" + str1;
       onlColumn.setHrefSlotName(str);
       paramList.add(new HrefSlots(str, paramOnlCgformField.getFieldHref()));
     }
     if ("1".equals(paramOnlCgformField.getSortFlag()))
       onlColumn.setSorter(true);
     String str4 = paramOnlCgformField.getFieldExtendJson();
     if (oConvertUtils.isNotEmpty(str4) && str4.indexOf("showLength") > 0) {
       JSONObject jSONObject = JSON.parseObject(str4);
       if (jSONObject != null && jSONObject.get("showLength") != null)
         onlColumn.setShowLength(oConvertUtils.getInt(jSONObject.get("showLength")).intValue());
     }
     return onlColumn;
   }

   private List<OnlColumn> a(OnlCgformHead paramOnlCgformHead, Map<String, List<DictModel>> paramMap, List<HrefSlots> paramList, Map<String, Integer> paramMap1) {
     int j = paramOnlCgformHead.getTableType().intValue();
     ArrayList<OnlColumn> arrayList = new ArrayList();
     if (j == 2) {
       String str = paramOnlCgformHead.getSubTableStr();
       if (str != null && !"".equals(str)) {
         String[] arrayOfString = str.split(",");
         for (String str1 : arrayOfString) {
           LambdaQueryWrapper lambdaQueryWrapper = (LambdaQueryWrapper)(new LambdaQueryWrapper()).eq(OnlCgformHead::getTableName, str1);
           OnlCgformHead onlCgformHead = (OnlCgformHead)this.onlCgformHeadService.getOne((Wrapper)lambdaQueryWrapper);
           if (onlCgformHead != null) {
             List list = this.onlAuthPageService.queryHideCode(onlCgformHead.getId(), true);
             List<OnlCgformField> list1 = b(onlCgformHead.getId());
             for (OnlCgformField onlCgformField : list1) {
               if (1 != onlCgformField.getIsShowList().intValue() && 1 != onlCgformField.getIsQuery().intValue())
                 continue;
               String str2 = onlCgformField.getDbFieldName();
               if (list.contains(str2))
                 continue;
               if ("id".equals(str2))
                 continue;
               Integer integer = paramMap1.get(str2);
               if (integer == null) {
                 paramMap1.put(str2, Integer.valueOf(1));
               } else {
                 paramMap1.put(str2, Integer.valueOf(integer.intValue() + 1));
               }
               OnlColumn onlColumn = a(onlCgformField, paramMap, paramList);
               if (1 == onlCgformField.getIsShowList().intValue()) {
                 onlColumn.setTableName(onlCgformHead.getTableName());
                 arrayList.add(onlColumn);
               }
             }
           }
         }
       }
     }
     return arrayList;
   }
 }


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\cgform\service\a\i.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
