 package org.jeecg.modules.online.cgform.model;
 
 import java.util.List;
 import java.util.Map;
 import org.jeecg.modules.online.cgform.entity.OnlCgformField;
 
 public class e {
   private String a;
   
   private Map<String, Object> b;
   
   private Map<String, String> c;
   
   private List<OnlCgformField> d;
   
   public void setSql(String sql) {
     this.a = sql;
   }
   
   public void setParams(Map<String, Object> params) {
     this.b = params;
   }
   
   public void setTableAliasMap(Map<String, String> tableAliasMap) {
     this.c = tableAliasMap;
   }
   
   public void setFieldList(List<OnlCgformField> fieldList) {
     this.d = fieldList;
   }
   
   public boolean equals(Object o) {
     if (o == this)
       return true; 
     if (!(o instanceof e))
       return false; 
     e e1 = (e)o;
     if (!e1.a(this))
       return false; 
     String str1 = getSql(), str2 = e1.getSql();
     if ((str1 == null) ? (str2 != null) : !str1.equals(str2))
       return false; 
     Map<String, Object> map1 = getParams(), map2 = e1.getParams();
     if ((map1 == null) ? (map2 != null) : !map1.equals(map2))
       return false; 
     Map<String, String> map3 = getTableAliasMap(), map4 = e1.getTableAliasMap();
     if ((map3 == null) ? (map4 != null) : !map3.equals(map4))
       return false; 
     List<OnlCgformField> list1 = getFieldList(), list2 = e1.getFieldList();
     return !((list1 == null) ? (list2 != null) : !list1.equals(list2));
   }
   
   protected boolean a(Object paramObject) {
     return paramObject instanceof e;
   }
   
   public int hashCode() {
     byte b = 59;
     null = 1;
     String str = getSql();
     null = null * 59 + ((str == null) ? 43 : str.hashCode());
     Map<String, Object> map = getParams();
     null = null * 59 + ((map == null) ? 43 : map.hashCode());
     Map<String, String> map1 = getTableAliasMap();
     null = null * 59 + ((map1 == null) ? 43 : map1.hashCode());
     List<OnlCgformField> list = getFieldList();
     return null * 59 + ((list == null) ? 43 : list.hashCode());
   }
   
   public String toString() {
     return "OnlQueryModel(sql=" + getSql() + ", params=" + getParams() + ", tableAliasMap=" + getTableAliasMap() + ", fieldList=" + getFieldList() + ")";
   }
   
   public String getSql() {
     return this.a;
   }
   
   public Map<String, Object> getParams() {
     return this.b;
   }
   
   public Map<String, String> getTableAliasMap() {
     return this.c;
   }
   
   public List<OnlCgformField> getFieldList() {
     return this.d;
   }
   
   public e() {}
   
   public e(String paramString, Map<String, Object> paramMap) {
     this.a = paramString;
     this.b = paramMap;
   }
 }


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\cgform\model\e.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
