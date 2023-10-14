 package org.jeecg.modules.online.cgform.model;
 
 public class OnlColumn {
   private String title;
   
   private String dataIndex;
   
   private String align;
   
   private String customRender;
   
   private g scopedSlots;
   
   private String hrefSlotName;
   
   private int showLength;
   
   public void setTitle(String title) {
     this.title = title;
   }
   
   public void setDataIndex(String dataIndex) {
     this.dataIndex = dataIndex;
   }
   
   public void setAlign(String align) {
     this.align = align;
   }
   
   public void setCustomRender(String customRender) {
     this.customRender = customRender;
   }
   
   public void setScopedSlots(g scopedSlots) {
     this.scopedSlots = scopedSlots;
   }
   
   public void setHrefSlotName(String hrefSlotName) {
     this.hrefSlotName = hrefSlotName;
   }
   
   public void setShowLength(int showLength) {
     this.showLength = showLength;
   }
   
   public void setSorter(boolean sorter) {
     this.sorter = sorter;
   }
   
   public void setLinkField(String linkField) {
     this.linkField = linkField;
   }
   
   public void setTableName(String tableName) {
     this.tableName = tableName;
   }
   
   public void setDbType(String dbType) {
     this.dbType = dbType;
   }
   
   public void setFieldType(String fieldType) {
     this.fieldType = fieldType;
   }
   
   public boolean equals(Object o) {
     if (o == this)
       return true; 
     if (!(o instanceof OnlColumn))
       return false; 
     OnlColumn onlColumn = (OnlColumn)o;
     if (!onlColumn.canEqual(this))
       return false; 
     if (getShowLength() != onlColumn.getShowLength())
       return false; 
     if (isSorter() != onlColumn.isSorter())
       return false; 
     String str1 = getTitle(), str2 = onlColumn.getTitle();
     if ((str1 == null) ? (str2 != null) : !str1.equals(str2))
       return false; 
     String str3 = getDataIndex(), str4 = onlColumn.getDataIndex();
     if ((str3 == null) ? (str4 != null) : !str3.equals(str4))
       return false; 
     String str5 = getAlign(), str6 = onlColumn.getAlign();
     if ((str5 == null) ? (str6 != null) : !str5.equals(str6))
       return false; 
     String str7 = getCustomRender(), str8 = onlColumn.getCustomRender();
     if ((str7 == null) ? (str8 != null) : !str7.equals(str8))
       return false; 
     g g1 = getScopedSlots(), g2 = onlColumn.getScopedSlots();
     if ((g1 == null) ? (g2 != null) : !g1.equals(g2))
       return false; 
     String str9 = getHrefSlotName(), str10 = onlColumn.getHrefSlotName();
     if ((str9 == null) ? (str10 != null) : !str9.equals(str10))
       return false; 
     String str11 = getLinkField(), str12 = onlColumn.getLinkField();
     if ((str11 == null) ? (str12 != null) : !str11.equals(str12))
       return false; 
     String str13 = getTableName(), str14 = onlColumn.getTableName();
     if ((str13 == null) ? (str14 != null) : !str13.equals(str14))
       return false; 
     String str15 = getDbType(), str16 = onlColumn.getDbType();
     if ((str15 == null) ? (str16 != null) : !str15.equals(str16))
       return false; 
     String str17 = getFieldType(), str18 = onlColumn.getFieldType();
     return !((str17 == null) ? (str18 != null) : !str17.equals(str18));
   }
   
   protected boolean canEqual(Object other) {
     return other instanceof OnlColumn;
   }
   
   public int hashCode() {
     byte b = 59;
     null = 1;
     null = null * 59 + getShowLength();
     null = null * 59 + (isSorter() ? 79 : 97);
     String str1 = getTitle();
     null = null * 59 + ((str1 == null) ? 43 : str1.hashCode());
     String str2 = getDataIndex();
     null = null * 59 + ((str2 == null) ? 43 : str2.hashCode());
     String str3 = getAlign();
     null = null * 59 + ((str3 == null) ? 43 : str3.hashCode());
     String str4 = getCustomRender();
     null = null * 59 + ((str4 == null) ? 43 : str4.hashCode());
     g g1 = getScopedSlots();
     null = null * 59 + ((g1 == null) ? 43 : g1.hashCode());
     String str5 = getHrefSlotName();
     null = null * 59 + ((str5 == null) ? 43 : str5.hashCode());
     String str6 = getLinkField();
     null = null * 59 + ((str6 == null) ? 43 : str6.hashCode());
     String str7 = getTableName();
     null = null * 59 + ((str7 == null) ? 43 : str7.hashCode());
     String str8 = getDbType();
     null = null * 59 + ((str8 == null) ? 43 : str8.hashCode());
     String str9 = getFieldType();
     return null * 59 + ((str9 == null) ? 43 : str9.hashCode());
   }
   
   public String toString() {
     return "OnlColumn(title=" + getTitle() + ", dataIndex=" + getDataIndex() + ", align=" + getAlign() + ", customRender=" + getCustomRender() + ", scopedSlots=" + getScopedSlots() + ", hrefSlotName=" + getHrefSlotName() + ", showLength=" + getShowLength() + ", sorter=" + isSorter() + ", linkField=" + getLinkField() + ", tableName=" + getTableName() + ", dbType=" + getDbType() + ", fieldType=" + getFieldType() + ")";
   }
   
   public String getTitle() {
     return this.title;
   }
   
   public String getDataIndex() {
     return this.dataIndex;
   }
   
   public String getAlign() {
     return this.align;
   }
   
   public String getCustomRender() {
     return this.customRender;
   }
   
   public g getScopedSlots() {
     return this.scopedSlots;
   }
   
   public String getHrefSlotName() {
     return this.hrefSlotName;
   }
   
   public int getShowLength() {
     return this.showLength;
   }
   
   private boolean sorter = false;
   
   private String linkField;
   
   private String tableName;
   
   private String dbType;
   
   private String fieldType;
   
   public boolean isSorter() {
     return this.sorter;
   }
   
   public String getLinkField() {
     return this.linkField;
   }
   
   public String getTableName() {
     return this.tableName;
   }
   
   public String getDbType() {
     return this.dbType;
   }
   
   public String getFieldType() {
     return this.fieldType;
   }
   
   public OnlColumn(String title, String dataIndex) {
     this.align = "center";
     this.title = title;
     this.dataIndex = dataIndex;
   }
   
   public OnlColumn() {}
 }


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\cgform\model\OnlColumn.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
