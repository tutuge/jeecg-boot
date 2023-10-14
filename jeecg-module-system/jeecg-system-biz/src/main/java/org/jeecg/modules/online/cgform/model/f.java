 package org.jeecg.modules.online.cgform.model;
 
 import java.util.List;
 import org.jeecg.common.system.vo.SysPermissionDataRuleModel;
 import org.jeecg.modules.online.cgform.entity.OnlCgformField;
 
 public class f {
   private String a;
   
   private String b;
   
   private List<OnlCgformField> c;
   
   private List<OnlCgformField> d;
   
   private List<SysPermissionDataRuleModel> e;
   
   private String f;
   
   private String g;
   
   private String h;
   
   private boolean i;
   
   public void setTableName(String tableName) {
     this.a = tableName;
   }
   
   public void setTableId(String tableId) {
     this.b = tableId;
   }
   
   public void setAllFieldList(List<OnlCgformField> allFieldList) {
     this.c = allFieldList;
   }
   
   public void setSelectFieldList(List<OnlCgformField> selectFieldList) {
     this.d = selectFieldList;
   }
   
   public void setAuthList(List<SysPermissionDataRuleModel> authList) {
     this.e = authList;
   }
   
   public void setMainField(String mainField) {
     this.f = mainField;
   }
   
   public void setJoinField(String joinField) {
     this.g = joinField;
   }
   
   public void setAlias(String alias) {
     this.h = alias;
   }
   
   public void setMain(boolean isMain) {
     this.i = isMain;
   }
   
   public boolean equals(Object o) {
     if (o == this)
       return true; 
     if (!(o instanceof f))
       return false; 
     f f1 = (f)o;
     if (!f1.a(this))
       return false; 
     if (a() != f1.a())
       return false; 
     String str1 = getTableName(), str2 = f1.getTableName();
     if ((str1 == null) ? (str2 != null) : !str1.equals(str2))
       return false; 
     String str3 = getTableId(), str4 = f1.getTableId();
     if ((str3 == null) ? (str4 != null) : !str3.equals(str4))
       return false; 
     List<OnlCgformField> list1 = getAllFieldList(), list2 = f1.getAllFieldList();
     if ((list1 == null) ? (list2 != null) : !list1.equals(list2))
       return false; 
     List<OnlCgformField> list3 = getSelectFieldList(), list4 = f1.getSelectFieldList();
     if ((list3 == null) ? (list4 != null) : !list3.equals(list4))
       return false; 
     List<SysPermissionDataRuleModel> list5 = getAuthList(), list6 = f1.getAuthList();
     if ((list5 == null) ? (list6 != null) : !list5.equals(list6))
       return false; 
     String str5 = getMainField(), str6 = f1.getMainField();
     if ((str5 == null) ? (str6 != null) : !str5.equals(str6))
       return false; 
     String str7 = getJoinField(), str8 = f1.getJoinField();
     if ((str7 == null) ? (str8 != null) : !str7.equals(str8))
       return false; 
     String str9 = getAlias(), str10 = f1.getAlias();
     return !((str9 == null) ? (str10 != null) : !str9.equals(str10));
   }
   
   protected boolean a(Object paramObject) {
     return paramObject instanceof f;
   }
   
   public int hashCode() {
     byte b = 59;
     null = 1;
     null = null * 59 + (a() ? 79 : 97);
     String str1 = getTableName();
     null = null * 59 + ((str1 == null) ? 43 : str1.hashCode());
     String str2 = getTableId();
     null = null * 59 + ((str2 == null) ? 43 : str2.hashCode());
     List<OnlCgformField> list1 = getAllFieldList();
     null = null * 59 + ((list1 == null) ? 43 : list1.hashCode());
     List<OnlCgformField> list2 = getSelectFieldList();
     null = null * 59 + ((list2 == null) ? 43 : list2.hashCode());
     List<SysPermissionDataRuleModel> list = getAuthList();
     null = null * 59 + ((list == null) ? 43 : list.hashCode());
     String str3 = getMainField();
     null = null * 59 + ((str3 == null) ? 43 : str3.hashCode());
     String str4 = getJoinField();
     null = null * 59 + ((str4 == null) ? 43 : str4.hashCode());
     String str5 = getAlias();
     return null * 59 + ((str5 == null) ? 43 : str5.hashCode());
   }
   
   public String toString() {
     return "OnlTable(tableName=" + getTableName() + ", tableId=" + getTableId() + ", allFieldList=" + getAllFieldList() + ", selectFieldList=" + getSelectFieldList() + ", authList=" + getAuthList() + ", mainField=" + getMainField() + ", joinField=" + getJoinField() + ", alias=" + getAlias() + ", isMain=" + a() + ")";
   }
   
   public String getTableName() {
     return this.a;
   }
   
   public String getTableId() {
     return this.b;
   }
   
   public List<OnlCgformField> getAllFieldList() {
     return this.c;
   }
   
   public List<OnlCgformField> getSelectFieldList() {
     return this.d;
   }
   
   public List<SysPermissionDataRuleModel> getAuthList() {
     return this.e;
   }
   
   public String getMainField() {
     return this.f;
   }
   
   public String getJoinField() {
     return this.g;
   }
   
   public String getAlias() {
     return this.h;
   }
   
   public boolean a() {
     return this.i;
   }
   
   public void setAliasByIntValue(int index) {
     char c = (char)index;
     this.h = String.valueOf(c);
   }
   
   public String getTableAlias() {
     return this.h + ".";
   }
   
   public f() {}
   
   public f(String paramString1, String paramString2, boolean paramBoolean) {
     this.a = paramString1;
     this.b = paramString2;
     this.i = paramBoolean;
   }
 }


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\cgform\model\f.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
