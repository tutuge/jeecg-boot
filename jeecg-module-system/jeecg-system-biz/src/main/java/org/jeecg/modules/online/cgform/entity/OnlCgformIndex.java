 package org.jeecg.modules.online.cgform.entity;
 
 import com.baomidou.mybatisplus.annotation.IdType;
 import com.baomidou.mybatisplus.annotation.TableId;
 import com.baomidou.mybatisplus.annotation.TableName;
 import com.fasterxml.jackson.annotation.JsonFormat;
 import java.io.Serializable;
 import java.util.Date;
 import org.springframework.format.annotation.DateTimeFormat;
 
 @TableName("onl_cgform_index")
 public class OnlCgformIndex implements Serializable {
   private static final long serialVersionUID = 1L;
   
   @TableId(type = IdType.ASSIGN_UUID)
   private String id;
   
   private String cgformHeadId;
   
   private String indexName;
   
   private String indexField;
   
   private String isDbSynch;
   
   private Integer delFlag;
   
   private String indexType;
   
   private String createBy;
   
   @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
   @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   private Date createTime;
   
   private String updateBy;
   
   @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
   @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   private Date updateTime;
   
   public void setId(String id) {
     this.id = id;
   }
   
   public void setCgformHeadId(String cgformHeadId) {
     this.cgformHeadId = cgformHeadId;
   }
   
   public void setIndexName(String indexName) {
     this.indexName = indexName;
   }
   
   public void setIndexField(String indexField) {
     this.indexField = indexField;
   }
   
   public void setIsDbSynch(String isDbSynch) {
     this.isDbSynch = isDbSynch;
   }
   
   public void setDelFlag(Integer delFlag) {
     this.delFlag = delFlag;
   }
   
   public void setIndexType(String indexType) {
     this.indexType = indexType;
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
   
   public boolean equals(Object o) {
     if (o == this)
       return true; 
     if (!(o instanceof OnlCgformIndex))
       return false; 
     OnlCgformIndex onlCgformIndex = (OnlCgformIndex)o;
     if (!onlCgformIndex.canEqual(this))
       return false; 
     Integer integer1 = getDelFlag(), integer2 = onlCgformIndex.getDelFlag();
     if ((integer1 == null) ? (integer2 != null) : !integer1.equals(integer2))
       return false; 
     String str1 = getId(), str2 = onlCgformIndex.getId();
     if ((str1 == null) ? (str2 != null) : !str1.equals(str2))
       return false; 
     String str3 = getCgformHeadId(), str4 = onlCgformIndex.getCgformHeadId();
     if ((str3 == null) ? (str4 != null) : !str3.equals(str4))
       return false; 
     String str5 = getIndexName(), str6 = onlCgformIndex.getIndexName();
     if ((str5 == null) ? (str6 != null) : !str5.equals(str6))
       return false; 
     String str7 = getIndexField(), str8 = onlCgformIndex.getIndexField();
     if ((str7 == null) ? (str8 != null) : !str7.equals(str8))
       return false; 
     String str9 = getIsDbSynch(), str10 = onlCgformIndex.getIsDbSynch();
     if ((str9 == null) ? (str10 != null) : !str9.equals(str10))
       return false; 
     String str11 = getIndexType(), str12 = onlCgformIndex.getIndexType();
     if ((str11 == null) ? (str12 != null) : !str11.equals(str12))
       return false; 
     String str13 = getCreateBy(), str14 = onlCgformIndex.getCreateBy();
     if ((str13 == null) ? (str14 != null) : !str13.equals(str14))
       return false; 
     Date date1 = getCreateTime(), date2 = onlCgformIndex.getCreateTime();
     if ((date1 == null) ? (date2 != null) : !date1.equals(date2))
       return false; 
     String str15 = getUpdateBy(), str16 = onlCgformIndex.getUpdateBy();
     if ((str15 == null) ? (str16 != null) : !str15.equals(str16))
       return false; 
     Date date3 = getUpdateTime(), date4 = onlCgformIndex.getUpdateTime();
     return !((date3 == null) ? (date4 != null) : !date3.equals(date4));
   }
   
   protected boolean canEqual(Object other) {
     return other instanceof OnlCgformIndex;
   }
   
   public int hashCode() {
     byte b = 59;
     null = 1;
     Integer integer = getDelFlag();
     null = null * 59 + ((integer == null) ? 43 : integer.hashCode());
     String str1 = getId();
     null = null * 59 + ((str1 == null) ? 43 : str1.hashCode());
     String str2 = getCgformHeadId();
     null = null * 59 + ((str2 == null) ? 43 : str2.hashCode());
     String str3 = getIndexName();
     null = null * 59 + ((str3 == null) ? 43 : str3.hashCode());
     String str4 = getIndexField();
     null = null * 59 + ((str4 == null) ? 43 : str4.hashCode());
     String str5 = getIsDbSynch();
     null = null * 59 + ((str5 == null) ? 43 : str5.hashCode());
     String str6 = getIndexType();
     null = null * 59 + ((str6 == null) ? 43 : str6.hashCode());
     String str7 = getCreateBy();
     null = null * 59 + ((str7 == null) ? 43 : str7.hashCode());
     Date date1 = getCreateTime();
     null = null * 59 + ((date1 == null) ? 43 : date1.hashCode());
     String str8 = getUpdateBy();
     null = null * 59 + ((str8 == null) ? 43 : str8.hashCode());
     Date date2 = getUpdateTime();
     return null * 59 + ((date2 == null) ? 43 : date2.hashCode());
   }
   
   public String toString() {
     return "OnlCgformIndex(id=" + getId() + ", cgformHeadId=" + getCgformHeadId() + ", indexName=" + getIndexName() + ", indexField=" + getIndexField() + ", isDbSynch=" + getIsDbSynch() + ", delFlag=" + getDelFlag() + ", indexType=" + getIndexType() + ", createBy=" + getCreateBy() + ", createTime=" + getCreateTime() + ", updateBy=" + getUpdateBy() + ", updateTime=" + getUpdateTime() + ")";
   }
   
   public String getId() {
     return this.id;
   }
   
   public String getCgformHeadId() {
     return this.cgformHeadId;
   }
   
   public String getIndexName() {
     return this.indexName;
   }
   
   public String getIndexField() {
     return this.indexField;
   }
   
   public String getIsDbSynch() {
     return this.isDbSynch;
   }
   
   public Integer getDelFlag() {
     return this.delFlag;
   }
   
   public String getIndexType() {
     return this.indexType;
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
 }


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\cgform\entity\OnlCgformIndex.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
