 package org.jeecg.modules.online.cgreport.entity;
 
 import com.baomidou.mybatisplus.annotation.IdType;
 import com.baomidou.mybatisplus.annotation.TableId;
 import com.baomidou.mybatisplus.annotation.TableName;
 import com.fasterxml.jackson.annotation.JsonFormat;
 import java.io.Serializable;
 import java.util.Date;
 import org.springframework.format.annotation.DateTimeFormat;
 
 @TableName("onl_cgreport_item")
 public class OnlCgreportItem implements Serializable {
   private static final long serialVersionUID = 1L;
   
   @TableId(type = IdType.ASSIGN_ID)
   private String id;
   
   private String cgrheadId;
   
   private String fieldName;
   
   private String fieldTxt;
   
   private Integer fieldWidth;
   
   private String fieldType;
   
   private String searchMode;
   
   private Integer isOrder;
   
   private Integer isSearch;
   
   private String dictCode;
   
   private String fieldHref;
   
   private Integer isShow;
   
   private Integer orderNum;
   
   private String replaceVal;
   
   private String isTotal;
   
   private String createBy;
   
   @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
   @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   private Date createTime;
   
   private String updateBy;
   
   @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
   @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   private Date updateTime;
   
   private String groupTitle;
   
   public void setId(String id) {
     this.id = id;
   }
   
   public void setCgrheadId(String cgrheadId) {
     this.cgrheadId = cgrheadId;
   }
   
   public void setFieldName(String fieldName) {
     this.fieldName = fieldName;
   }
   
   public void setFieldTxt(String fieldTxt) {
     this.fieldTxt = fieldTxt;
   }
   
   public void setFieldWidth(Integer fieldWidth) {
     this.fieldWidth = fieldWidth;
   }
   
   public void setFieldType(String fieldType) {
     this.fieldType = fieldType;
   }
   
   public void setSearchMode(String searchMode) {
     this.searchMode = searchMode;
   }
   
   public void setIsOrder(Integer isOrder) {
     this.isOrder = isOrder;
   }
   
   public void setIsSearch(Integer isSearch) {
     this.isSearch = isSearch;
   }
   
   public void setDictCode(String dictCode) {
     this.dictCode = dictCode;
   }
   
   public void setFieldHref(String fieldHref) {
     this.fieldHref = fieldHref;
   }
   
   public void setIsShow(Integer isShow) {
     this.isShow = isShow;
   }
   
   public void setOrderNum(Integer orderNum) {
     this.orderNum = orderNum;
   }
   
   public void setReplaceVal(String replaceVal) {
     this.replaceVal = replaceVal;
   }
   
   public void setIsTotal(String isTotal) {
     this.isTotal = isTotal;
   }
   
   public void setCreateBy(String createBy) {
     this.createBy = createBy;
   }
   
   @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
   public void setCreateTime(Date createTime) {
     this.createTime = createTime;
   }
   
   public void setUpdateBy(String updateBy) {
     this.updateBy = updateBy;
   }
   
   @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
   public void setUpdateTime(Date updateTime) {
     this.updateTime = updateTime;
   }
   
   public void setGroupTitle(String groupTitle) {
     this.groupTitle = groupTitle;
   }
   
   public boolean equals(Object o) {
     if (o == this)
       return true; 
     if (!(o instanceof OnlCgreportItem))
       return false; 
     OnlCgreportItem onlCgreportItem = (OnlCgreportItem)o;
     if (!onlCgreportItem.canEqual(this))
       return false; 
     Integer integer1 = getFieldWidth(), integer2 = onlCgreportItem.getFieldWidth();
     if ((integer1 == null) ? (integer2 != null) : !integer1.equals(integer2))
       return false; 
     Integer integer3 = getIsOrder(), integer4 = onlCgreportItem.getIsOrder();
     if ((integer3 == null) ? (integer4 != null) : !integer3.equals(integer4))
       return false; 
     Integer integer5 = getIsSearch(), integer6 = onlCgreportItem.getIsSearch();
     if ((integer5 == null) ? (integer6 != null) : !integer5.equals(integer6))
       return false; 
     Integer integer7 = getIsShow(), integer8 = onlCgreportItem.getIsShow();
     if ((integer7 == null) ? (integer8 != null) : !integer7.equals(integer8))
       return false; 
     Integer integer9 = getOrderNum(), integer10 = onlCgreportItem.getOrderNum();
     if ((integer9 == null) ? (integer10 != null) : !integer9.equals(integer10))
       return false; 
     String str1 = getId(), str2 = onlCgreportItem.getId();
     if ((str1 == null) ? (str2 != null) : !str1.equals(str2))
       return false; 
     String str3 = getCgrheadId(), str4 = onlCgreportItem.getCgrheadId();
     if ((str3 == null) ? (str4 != null) : !str3.equals(str4))
       return false; 
     String str5 = getFieldName(), str6 = onlCgreportItem.getFieldName();
     if ((str5 == null) ? (str6 != null) : !str5.equals(str6))
       return false; 
     String str7 = getFieldTxt(), str8 = onlCgreportItem.getFieldTxt();
     if ((str7 == null) ? (str8 != null) : !str7.equals(str8))
       return false; 
     String str9 = getFieldType(), str10 = onlCgreportItem.getFieldType();
     if ((str9 == null) ? (str10 != null) : !str9.equals(str10))
       return false; 
     String str11 = getSearchMode(), str12 = onlCgreportItem.getSearchMode();
     if ((str11 == null) ? (str12 != null) : !str11.equals(str12))
       return false; 
     String str13 = getDictCode(), str14 = onlCgreportItem.getDictCode();
     if ((str13 == null) ? (str14 != null) : !str13.equals(str14))
       return false; 
     String str15 = getFieldHref(), str16 = onlCgreportItem.getFieldHref();
     if ((str15 == null) ? (str16 != null) : !str15.equals(str16))
       return false; 
     String str17 = getReplaceVal(), str18 = onlCgreportItem.getReplaceVal();
     if ((str17 == null) ? (str18 != null) : !str17.equals(str18))
       return false; 
     String str19 = getIsTotal(), str20 = onlCgreportItem.getIsTotal();
     if ((str19 == null) ? (str20 != null) : !str19.equals(str20))
       return false; 
     String str21 = getCreateBy(), str22 = onlCgreportItem.getCreateBy();
     if ((str21 == null) ? (str22 != null) : !str21.equals(str22))
       return false; 
     Date date1 = getCreateTime(), date2 = onlCgreportItem.getCreateTime();
     if ((date1 == null) ? (date2 != null) : !date1.equals(date2))
       return false; 
     String str23 = getUpdateBy(), str24 = onlCgreportItem.getUpdateBy();
     if ((str23 == null) ? (str24 != null) : !str23.equals(str24))
       return false; 
     Date date3 = getUpdateTime(), date4 = onlCgreportItem.getUpdateTime();
     if ((date3 == null) ? (date4 != null) : !date3.equals(date4))
       return false; 
     String str25 = getGroupTitle(), str26 = onlCgreportItem.getGroupTitle();
     return !((str25 == null) ? (str26 != null) : !str25.equals(str26));
   }
   
   protected boolean canEqual(Object other) {
     return other instanceof OnlCgreportItem;
   }
   
   public int hashCode() {
     byte b = 59;
     null = 1;
     Integer integer1 = getFieldWidth();
     null = null * 59 + ((integer1 == null) ? 43 : integer1.hashCode());
     Integer integer2 = getIsOrder();
     null = null * 59 + ((integer2 == null) ? 43 : integer2.hashCode());
     Integer integer3 = getIsSearch();
     null = null * 59 + ((integer3 == null) ? 43 : integer3.hashCode());
     Integer integer4 = getIsShow();
     null = null * 59 + ((integer4 == null) ? 43 : integer4.hashCode());
     Integer integer5 = getOrderNum();
     null = null * 59 + ((integer5 == null) ? 43 : integer5.hashCode());
     String str1 = getId();
     null = null * 59 + ((str1 == null) ? 43 : str1.hashCode());
     String str2 = getCgrheadId();
     null = null * 59 + ((str2 == null) ? 43 : str2.hashCode());
     String str3 = getFieldName();
     null = null * 59 + ((str3 == null) ? 43 : str3.hashCode());
     String str4 = getFieldTxt();
     null = null * 59 + ((str4 == null) ? 43 : str4.hashCode());
     String str5 = getFieldType();
     null = null * 59 + ((str5 == null) ? 43 : str5.hashCode());
     String str6 = getSearchMode();
     null = null * 59 + ((str6 == null) ? 43 : str6.hashCode());
     String str7 = getDictCode();
     null = null * 59 + ((str7 == null) ? 43 : str7.hashCode());
     String str8 = getFieldHref();
     null = null * 59 + ((str8 == null) ? 43 : str8.hashCode());
     String str9 = getReplaceVal();
     null = null * 59 + ((str9 == null) ? 43 : str9.hashCode());
     String str10 = getIsTotal();
     null = null * 59 + ((str10 == null) ? 43 : str10.hashCode());
     String str11 = getCreateBy();
     null = null * 59 + ((str11 == null) ? 43 : str11.hashCode());
     Date date1 = getCreateTime();
     null = null * 59 + ((date1 == null) ? 43 : date1.hashCode());
     String str12 = getUpdateBy();
     null = null * 59 + ((str12 == null) ? 43 : str12.hashCode());
     Date date2 = getUpdateTime();
     null = null * 59 + ((date2 == null) ? 43 : date2.hashCode());
     String str13 = getGroupTitle();
     return null * 59 + ((str13 == null) ? 43 : str13.hashCode());
   }
   
   public String toString() {
     return "OnlCgreportItem(id=" + getId() + ", cgrheadId=" + getCgrheadId() + ", fieldName=" + getFieldName() + ", fieldTxt=" + getFieldTxt() + ", fieldWidth=" + getFieldWidth() + ", fieldType=" + getFieldType() + ", searchMode=" + getSearchMode() + ", isOrder=" + getIsOrder() + ", isSearch=" + getIsSearch() + ", dictCode=" + getDictCode() + ", fieldHref=" + getFieldHref() + ", isShow=" + getIsShow() + ", orderNum=" + getOrderNum() + ", replaceVal=" + getReplaceVal() + ", isTotal=" + getIsTotal() + ", createBy=" + getCreateBy() + ", createTime=" + getCreateTime() + ", updateBy=" + getUpdateBy() + ", updateTime=" + getUpdateTime() + ", groupTitle=" + getGroupTitle() + ")";
   }
   
   public String getId() {
     return this.id;
   }
   
   public String getCgrheadId() {
     return this.cgrheadId;
   }
   
   public String getFieldName() {
     return this.fieldName;
   }
   
   public String getFieldTxt() {
     return this.fieldTxt;
   }
   
   public Integer getFieldWidth() {
     return this.fieldWidth;
   }
   
   public String getFieldType() {
     return this.fieldType;
   }
   
   public String getSearchMode() {
     return this.searchMode;
   }
   
   public Integer getIsOrder() {
     return this.isOrder;
   }
   
   public Integer getIsSearch() {
     return this.isSearch;
   }
   
   public String getDictCode() {
     return this.dictCode;
   }
   
   public String getFieldHref() {
     return this.fieldHref;
   }
   
   public Integer getIsShow() {
     return this.isShow;
   }
   
   public Integer getOrderNum() {
     return this.orderNum;
   }
   
   public String getReplaceVal() {
     return this.replaceVal;
   }
   
   public String getIsTotal() {
     return this.isTotal;
   }
   
   public String getCreateBy() {
     return this.createBy;
   }
   
   public Date getCreateTime() {
     return this.createTime;
   }
   
   public String getUpdateBy() {
     return this.updateBy;
   }
   
   public Date getUpdateTime() {
     return this.updateTime;
   }
   
   public String getGroupTitle() {
     return this.groupTitle;
   }
 }


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\cgreport\entity\OnlCgreportItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
