 package org.jeecg.modules.online.cgreport.entity;
 
 import com.baomidou.mybatisplus.annotation.IdType;
 import com.baomidou.mybatisplus.annotation.TableId;
 import com.baomidou.mybatisplus.annotation.TableName;
 import com.fasterxml.jackson.annotation.JsonFormat;
 import java.io.Serializable;
 import java.util.Date;
 import org.jeecg.common.aspect.annotation.Dict;
 import org.springframework.format.annotation.DateTimeFormat;
 
 @TableName("onl_cgreport_head")
 public class OnlCgreportHead implements Serializable {
   private static final long serialVersionUID = 1L;
   
   @TableId(type = IdType.ASSIGN_ID)
   private String id;
   
   private String code;
   
   private String name;
   
   private String cgrSql;
   
   private String returnValField;
   
   private String returnTxtField;
   
   private String returnType;
   
   @Dict(dicCode = "code", dicText = "name", dictTable = "sys_data_source")
   private String dbSource;
   
   private String content;
   
   private String lowAppId;
   
   @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
   @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   private Date updateTime;
   
   private String updateBy;
   
   @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
   @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   private Date createTime;
   
   private String createBy;
   
   public void setId(String id) {
     this.id = id;
   }
   
   public void setCode(String code) {
     this.code = code;
   }
   
   public void setName(String name) {
     this.name = name;
   }
   
   public void setCgrSql(String cgrSql) {
     this.cgrSql = cgrSql;
   }
   
   public void setReturnValField(String returnValField) {
     this.returnValField = returnValField;
   }
   
   public void setReturnTxtField(String returnTxtField) {
     this.returnTxtField = returnTxtField;
   }
   
   public void setReturnType(String returnType) {
     this.returnType = returnType;
   }
   
   public void setDbSource(String dbSource) {
     this.dbSource = dbSource;
   }
   
   public void setContent(String content) {
     this.content = content;
   }
   
   public void setLowAppId(String lowAppId) {
     this.lowAppId = lowAppId;
   }
   
   @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
   public void setUpdateTime(Date updateTime) {
     this.updateTime = updateTime;
   }
   
   public void setUpdateBy(String updateBy) {
     this.updateBy = updateBy;
   }
   
   @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
   public void setCreateTime(Date createTime) {
     this.createTime = createTime;
   }
   
   public void setCreateBy(String createBy) {
     this.createBy = createBy;
   }
   
   public boolean equals(Object o) {
     if (o == this)
       return true; 
     if (!(o instanceof OnlCgreportHead))
       return false; 
     OnlCgreportHead onlCgreportHead = (OnlCgreportHead)o;
     if (!onlCgreportHead.canEqual(this))
       return false; 
     String str1 = getId(), str2 = onlCgreportHead.getId();
     if ((str1 == null) ? (str2 != null) : !str1.equals(str2))
       return false; 
     String str3 = getCode(), str4 = onlCgreportHead.getCode();
     if ((str3 == null) ? (str4 != null) : !str3.equals(str4))
       return false; 
     String str5 = getName(), str6 = onlCgreportHead.getName();
     if ((str5 == null) ? (str6 != null) : !str5.equals(str6))
       return false; 
     String str7 = getCgrSql(), str8 = onlCgreportHead.getCgrSql();
     if ((str7 == null) ? (str8 != null) : !str7.equals(str8))
       return false; 
     String str9 = getReturnValField(), str10 = onlCgreportHead.getReturnValField();
     if ((str9 == null) ? (str10 != null) : !str9.equals(str10))
       return false; 
     String str11 = getReturnTxtField(), str12 = onlCgreportHead.getReturnTxtField();
     if ((str11 == null) ? (str12 != null) : !str11.equals(str12))
       return false; 
     String str13 = getReturnType(), str14 = onlCgreportHead.getReturnType();
     if ((str13 == null) ? (str14 != null) : !str13.equals(str14))
       return false; 
     String str15 = getDbSource(), str16 = onlCgreportHead.getDbSource();
     if ((str15 == null) ? (str16 != null) : !str15.equals(str16))
       return false; 
     String str17 = getContent(), str18 = onlCgreportHead.getContent();
     if ((str17 == null) ? (str18 != null) : !str17.equals(str18))
       return false; 
     String str19 = getLowAppId(), str20 = onlCgreportHead.getLowAppId();
     if ((str19 == null) ? (str20 != null) : !str19.equals(str20))
       return false; 
     Date date1 = getUpdateTime(), date2 = onlCgreportHead.getUpdateTime();
     if ((date1 == null) ? (date2 != null) : !date1.equals(date2))
       return false; 
     String str21 = getUpdateBy(), str22 = onlCgreportHead.getUpdateBy();
     if ((str21 == null) ? (str22 != null) : !str21.equals(str22))
       return false; 
     Date date3 = getCreateTime(), date4 = onlCgreportHead.getCreateTime();
     if ((date3 == null) ? (date4 != null) : !date3.equals(date4))
       return false; 
     String str23 = getCreateBy(), str24 = onlCgreportHead.getCreateBy();
     return !((str23 == null) ? (str24 != null) : !str23.equals(str24));
   }
   
   protected boolean canEqual(Object other) {
     return other instanceof OnlCgreportHead;
   }
   
   public int hashCode() {
     byte b = 59;
     null = 1;
     String str1 = getId();
     null = null * 59 + ((str1 == null) ? 43 : str1.hashCode());
     String str2 = getCode();
     null = null * 59 + ((str2 == null) ? 43 : str2.hashCode());
     String str3 = getName();
     null = null * 59 + ((str3 == null) ? 43 : str3.hashCode());
     String str4 = getCgrSql();
     null = null * 59 + ((str4 == null) ? 43 : str4.hashCode());
     String str5 = getReturnValField();
     null = null * 59 + ((str5 == null) ? 43 : str5.hashCode());
     String str6 = getReturnTxtField();
     null = null * 59 + ((str6 == null) ? 43 : str6.hashCode());
     String str7 = getReturnType();
     null = null * 59 + ((str7 == null) ? 43 : str7.hashCode());
     String str8 = getDbSource();
     null = null * 59 + ((str8 == null) ? 43 : str8.hashCode());
     String str9 = getContent();
     null = null * 59 + ((str9 == null) ? 43 : str9.hashCode());
     String str10 = getLowAppId();
     null = null * 59 + ((str10 == null) ? 43 : str10.hashCode());
     Date date1 = getUpdateTime();
     null = null * 59 + ((date1 == null) ? 43 : date1.hashCode());
     String str11 = getUpdateBy();
     null = null * 59 + ((str11 == null) ? 43 : str11.hashCode());
     Date date2 = getCreateTime();
     null = null * 59 + ((date2 == null) ? 43 : date2.hashCode());
     String str12 = getCreateBy();
     return null * 59 + ((str12 == null) ? 43 : str12.hashCode());
   }
   
   public String toString() {
     return "OnlCgreportHead(id=" + getId() + ", code=" + getCode() + ", name=" + getName() + ", cgrSql=" + getCgrSql() + ", returnValField=" + getReturnValField() + ", returnTxtField=" + getReturnTxtField() + ", returnType=" + getReturnType() + ", dbSource=" + getDbSource() + ", content=" + getContent() + ", lowAppId=" + getLowAppId() + ", updateTime=" + getUpdateTime() + ", updateBy=" + getUpdateBy() + ", createTime=" + getCreateTime() + ", createBy=" + getCreateBy() + ")";
   }
   
   public String getId() {
     return this.id;
   }
   
   public String getCode() {
     return this.code;
   }
   
   public String getName() {
     return this.name;
   }
   
   public String getCgrSql() {
     return this.cgrSql;
   }
   
   public String getReturnValField() {
     return this.returnValField;
   }
   
   public String getReturnTxtField() {
     return this.returnTxtField;
   }
   
   public String getReturnType() {
     return this.returnType;
   }
   
   public String getDbSource() {
     return this.dbSource;
   }
   
   public String getContent() {
     return this.content;
   }
   
   public String getLowAppId() {
     return this.lowAppId;
   }
   
   public Date getUpdateTime() {
     return this.updateTime;
   }
   
   public String getUpdateBy() {
     return this.updateBy;
   }
   
   public Date getCreateTime() {
     return this.createTime;
   }
   
   public String getCreateBy() {
     return this.createBy;
   }
 }


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\cgreport\entity\OnlCgreportHead.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
