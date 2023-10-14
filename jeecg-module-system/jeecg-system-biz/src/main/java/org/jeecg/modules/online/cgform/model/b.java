 package org.jeecg.modules.online.cgform.model;
 
 import java.io.Serializable;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import org.jeecg.common.system.vo.DictModel;
 import org.jeecg.modules.online.cgform.entity.OnlCgformButton;
 
 public class b implements Serializable {
   private static final long b = 1L;
   
   private String c;
   
   private String d;
   
   private String e;
   
   private String f;
   
   private Integer g;
   
   private String h;
   
   private String i;
   
   private Integer j;
   
   private List<OnlColumn> k;
   
   private List<String> l;
   
   public void setCode(String code) {
     this.c = code;
   }
   
   public void setFormTemplate(String formTemplate) {
     this.d = formTemplate;
   }
   
   public void setDescription(String description) {
     this.e = description;
   }
   
   public void setCurrentTableName(String currentTableName) {
     this.f = currentTableName;
   }
   
   public void setTableType(Integer tableType) {
     this.g = tableType;
   }
   
   public void setPaginationFlag(String paginationFlag) {
     this.h = paginationFlag;
   }
   
   public void setCheckboxFlag(String checkboxFlag) {
     this.i = checkboxFlag;
   }
   
   public void setScrollFlag(Integer scrollFlag) {
     this.j = scrollFlag;
   }
   
   public void setColumns(List<OnlColumn> columns) {
     this.k = columns;
   }
   
   public void setHideColumns(List<String> hideColumns) {
     this.l = hideColumns;
   }
   
   public void setDictOptions(Map<String, List<DictModel>> dictOptions) {
     this.m = dictOptions;
   }
   
   public void setCgButtonList(List<OnlCgformButton> cgButtonList) {
     this.n = cgButtonList;
   }
   
   public void setFieldHrefSlots(List<HrefSlots> fieldHrefSlots) {
     this.a = fieldHrefSlots;
   }
   
   public void setEnhanceJs(String enhanceJs) {
     this.o = enhanceJs;
   }
   
   public void setForeignKeys(List<c> foreignKeys) {
     this.p = foreignKeys;
   }
   
   public void setPidField(String pidField) {
     this.q = pidField;
   }
   
   public void setHasChildrenField(String hasChildrenField) {
     this.r = hasChildrenField;
   }
   
   public void setTextField(String textField) {
     this.s = textField;
   }
   
   public void setIsDesForm(String isDesForm) {
     this.t = isDesForm;
   }
   
   public void setDesFormCode(String desFormCode) {
     this.u = desFormCode;
   }
   
   public void setRelationType(Integer relationType) {
     this.v = relationType;
   }
   
   public boolean equals(Object o) {
     if (o == this)
       return true; 
     if (!(o instanceof b))
       return false; 
     b b1 = (b)o;
     if (!b1.a(this))
       return false; 
     Integer integer1 = getTableType(), integer2 = b1.getTableType();
     if ((integer1 == null) ? (integer2 != null) : !integer1.equals(integer2))
       return false; 
     Integer integer3 = getScrollFlag(), integer4 = b1.getScrollFlag();
     if ((integer3 == null) ? (integer4 != null) : !integer3.equals(integer4))
       return false; 
     Integer integer5 = getRelationType(), integer6 = b1.getRelationType();
     if ((integer5 == null) ? (integer6 != null) : !integer5.equals(integer6))
       return false; 
     String str1 = getCode(), str2 = b1.getCode();
     if ((str1 == null) ? (str2 != null) : !str1.equals(str2))
       return false; 
     String str3 = getFormTemplate(), str4 = b1.getFormTemplate();
     if ((str3 == null) ? (str4 != null) : !str3.equals(str4))
       return false; 
     String str5 = getDescription(), str6 = b1.getDescription();
     if ((str5 == null) ? (str6 != null) : !str5.equals(str6))
       return false; 
     String str7 = getCurrentTableName(), str8 = b1.getCurrentTableName();
     if ((str7 == null) ? (str8 != null) : !str7.equals(str8))
       return false; 
     String str9 = getPaginationFlag(), str10 = b1.getPaginationFlag();
     if ((str9 == null) ? (str10 != null) : !str9.equals(str10))
       return false; 
     String str11 = getCheckboxFlag(), str12 = b1.getCheckboxFlag();
     if ((str11 == null) ? (str12 != null) : !str11.equals(str12))
       return false; 
     List<OnlColumn> list1 = getColumns(), list2 = b1.getColumns();
     if ((list1 == null) ? (list2 != null) : !list1.equals(list2))
       return false; 
     List<String> list3 = getHideColumns(), list4 = b1.getHideColumns();
     if ((list3 == null) ? (list4 != null) : !list3.equals(list4))
       return false; 
     Map<String, List<DictModel>> map1 = getDictOptions(), map2 = b1.getDictOptions();
     if ((map1 == null) ? (map2 != null) : !map1.equals(map2))
       return false; 
     List<OnlCgformButton> list5 = getCgButtonList(), list6 = b1.getCgButtonList();
     if ((list5 == null) ? (list6 != null) : !list5.equals(list6))
       return false; 
     List<HrefSlots> list7 = getFieldHrefSlots(), list8 = b1.getFieldHrefSlots();
     if ((list7 == null) ? (list8 != null) : !list7.equals(list8))
       return false; 
     String str13 = getEnhanceJs(), str14 = b1.getEnhanceJs();
     if ((str13 == null) ? (str14 != null) : !str13.equals(str14))
       return false; 
     List<c> list9 = getForeignKeys(), list10 = b1.getForeignKeys();
     if ((list9 == null) ? (list10 != null) : !list9.equals(list10))
       return false; 
     String str15 = getPidField(), str16 = b1.getPidField();
     if ((str15 == null) ? (str16 != null) : !str15.equals(str16))
       return false; 
     String str17 = getHasChildrenField(), str18 = b1.getHasChildrenField();
     if ((str17 == null) ? (str18 != null) : !str17.equals(str18))
       return false; 
     String str19 = getTextField(), str20 = b1.getTextField();
     if ((str19 == null) ? (str20 != null) : !str19.equals(str20))
       return false; 
     String str21 = getIsDesForm(), str22 = b1.getIsDesForm();
     if ((str21 == null) ? (str22 != null) : !str21.equals(str22))
       return false; 
     String str23 = getDesFormCode(), str24 = b1.getDesFormCode();
     return !((str23 == null) ? (str24 != null) : !str23.equals(str24));
   }
   
   protected boolean a(Object paramObject) {
     return paramObject instanceof b;
   }
   
   public int hashCode() {
     byte b1 = 59;
     null = 1;
     Integer integer1 = getTableType();
     null = null * 59 + ((integer1 == null) ? 43 : integer1.hashCode());
     Integer integer2 = getScrollFlag();
     null = null * 59 + ((integer2 == null) ? 43 : integer2.hashCode());
     Integer integer3 = getRelationType();
     null = null * 59 + ((integer3 == null) ? 43 : integer3.hashCode());
     String str1 = getCode();
     null = null * 59 + ((str1 == null) ? 43 : str1.hashCode());
     String str2 = getFormTemplate();
     null = null * 59 + ((str2 == null) ? 43 : str2.hashCode());
     String str3 = getDescription();
     null = null * 59 + ((str3 == null) ? 43 : str3.hashCode());
     String str4 = getCurrentTableName();
     null = null * 59 + ((str4 == null) ? 43 : str4.hashCode());
     String str5 = getPaginationFlag();
     null = null * 59 + ((str5 == null) ? 43 : str5.hashCode());
     String str6 = getCheckboxFlag();
     null = null * 59 + ((str6 == null) ? 43 : str6.hashCode());
     List<OnlColumn> list = getColumns();
     null = null * 59 + ((list == null) ? 43 : list.hashCode());
     List<String> list1 = getHideColumns();
     null = null * 59 + ((list1 == null) ? 43 : list1.hashCode());
     Map<String, List<DictModel>> map = getDictOptions();
     null = null * 59 + ((map == null) ? 43 : map.hashCode());
     List<OnlCgformButton> list2 = getCgButtonList();
     null = null * 59 + ((list2 == null) ? 43 : list2.hashCode());
     List<HrefSlots> list3 = getFieldHrefSlots();
     null = null * 59 + ((list3 == null) ? 43 : list3.hashCode());
     String str7 = getEnhanceJs();
     null = null * 59 + ((str7 == null) ? 43 : str7.hashCode());
     List<c> list4 = getForeignKeys();
     null = null * 59 + ((list4 == null) ? 43 : list4.hashCode());
     String str8 = getPidField();
     null = null * 59 + ((str8 == null) ? 43 : str8.hashCode());
     String str9 = getHasChildrenField();
     null = null * 59 + ((str9 == null) ? 43 : str9.hashCode());
     String str10 = getTextField();
     null = null * 59 + ((str10 == null) ? 43 : str10.hashCode());
     String str11 = getIsDesForm();
     null = null * 59 + ((str11 == null) ? 43 : str11.hashCode());
     String str12 = getDesFormCode();
     return null * 59 + ((str12 == null) ? 43 : str12.hashCode());
   }
   
   public String toString() {
     return "OnlComplexModel(code=" + getCode() + ", formTemplate=" + getFormTemplate() + ", description=" + getDescription() + ", currentTableName=" + getCurrentTableName() + ", tableType=" + getTableType() + ", paginationFlag=" + getPaginationFlag() + ", checkboxFlag=" + getCheckboxFlag() + ", scrollFlag=" + getScrollFlag() + ", columns=" + getColumns() + ", hideColumns=" + getHideColumns() + ", dictOptions=" + getDictOptions() + ", cgButtonList=" + getCgButtonList() + ", fieldHrefSlots=" + getFieldHrefSlots() + ", enhanceJs=" + getEnhanceJs() + ", foreignKeys=" + getForeignKeys() + ", pidField=" + getPidField() + ", hasChildrenField=" + getHasChildrenField() + ", textField=" + getTextField() + ", isDesForm=" + getIsDesForm() + ", desFormCode=" + getDesFormCode() + ", relationType=" + getRelationType() + ")";
   }
   
   public String getCode() {
     return this.c;
   }
   
   public String getFormTemplate() {
     return this.d;
   }
   
   public String getDescription() {
     return this.e;
   }
   
   public String getCurrentTableName() {
     return this.f;
   }
   
   public Integer getTableType() {
     return this.g;
   }
   
   public String getPaginationFlag() {
     return this.h;
   }
   
   public String getCheckboxFlag() {
     return this.i;
   }
   
   public Integer getScrollFlag() {
     return this.j;
   }
   
   public List<OnlColumn> getColumns() {
     return this.k;
   }
   
   public List<String> getHideColumns() {
     return this.l;
   }
   
   private Map<String, List<DictModel>> m = new HashMap<>();
   
   private List<OnlCgformButton> n;
   
   List<HrefSlots> a;
   
   private String o;
   
   private List<c> p;
   
   private String q;
   
   private String r;
   
   private String s;
   
   private String t;
   
   private String u;
   
   private Integer v;
   
   public Map<String, List<DictModel>> getDictOptions() {
     return this.m;
   }
   
   public List<OnlCgformButton> getCgButtonList() {
     return this.n;
   }
   
   public List<HrefSlots> getFieldHrefSlots() {
     return this.a;
   }
   
   public String getEnhanceJs() {
     return this.o;
   }
   
   public List<c> getForeignKeys() {
     return this.p;
   }
   
   public String getPidField() {
     return this.q;
   }
   
   public String getHasChildrenField() {
     return this.r;
   }
   
   public String getTextField() {
     return this.s;
   }
   
   public String getIsDesForm() {
     return this.t;
   }
   
   public String getDesFormCode() {
     return this.u;
   }
   
   public Integer getRelationType() {
     return this.v;
   }
 }


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\cgform\model\b.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
