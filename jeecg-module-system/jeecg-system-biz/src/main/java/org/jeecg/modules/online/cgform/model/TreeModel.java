 package org.jeecg.modules.online.cgform.model;
 
 public class TreeModel {
   private String label;
   
   private String store;
   
   private String id;
   
   private String pid;
   
   public void setLabel(String label) {
     this.label = label;
   }
   
   public void setStore(String store) {
     this.store = store;
   }
   
   public void setId(String id) {
     this.id = id;
   }
   
   public void setPid(String pid) {
     this.pid = pid;
   }
   
   public boolean equals(Object o) {
     if (o == this)
       return true; 
     if (!(o instanceof TreeModel))
       return false; 
     TreeModel treeModel = (TreeModel)o;
     if (!treeModel.canEqual(this))
       return false; 
     String str1 = getLabel(), str2 = treeModel.getLabel();
     if ((str1 == null) ? (str2 != null) : !str1.equals(str2))
       return false; 
     String str3 = getStore(), str4 = treeModel.getStore();
     if ((str3 == null) ? (str4 != null) : !str3.equals(str4))
       return false; 
     String str5 = getId(), str6 = treeModel.getId();
     if ((str5 == null) ? (str6 != null) : !str5.equals(str6))
       return false; 
     String str7 = getPid(), str8 = treeModel.getPid();
     return !((str7 == null) ? (str8 != null) : !str7.equals(str8));
   }
   
   protected boolean canEqual(Object other) {
     return other instanceof TreeModel;
   }
   
   public int hashCode() {
     byte b = 59;
     null = 1;
     String str1 = getLabel();
     null = null * 59 + ((str1 == null) ? 43 : str1.hashCode());
     String str2 = getStore();
     null = null * 59 + ((str2 == null) ? 43 : str2.hashCode());
     String str3 = getId();
     null = null * 59 + ((str3 == null) ? 43 : str3.hashCode());
     String str4 = getPid();
     return null * 59 + ((str4 == null) ? 43 : str4.hashCode());
   }
   
   public String toString() {
     return "TreeModel(label=" + getLabel() + ", store=" + getStore() + ", id=" + getId() + ", pid=" + getPid() + ")";
   }
   
   public String getLabel() {
     return this.label;
   }
   
   public String getStore() {
     return this.store;
   }
   
   public String getId() {
     return this.id;
   }
   
   public String getPid() {
     return this.pid;
   }
 }


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\cgform\model\TreeModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
