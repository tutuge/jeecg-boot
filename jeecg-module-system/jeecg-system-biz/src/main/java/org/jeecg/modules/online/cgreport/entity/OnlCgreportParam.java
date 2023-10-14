 package org.jeecg.modules.online.cgreport.entity;
 
 import com.baomidou.mybatisplus.annotation.IdType;
 import com.baomidou.mybatisplus.annotation.TableId;
 import com.baomidou.mybatisplus.annotation.TableName;
 import com.fasterxml.jackson.annotation.JsonFormat;
 import java.io.Serializable;
 import java.util.Date;
 import org.springframework.format.annotation.DateTimeFormat;
 
 @TableName("onl_cgreport_param")
 public class OnlCgreportParam implements Serializable {
   private static final long serialVersionUID = 1L;
   
   @TableId(type = IdType.ASSIGN_ID)
   private String id;
   
   private String cgrheadId;
   
   private String paramName;
   
   private String paramTxt;
   
   private String paramValue;
   
   private Integer orderNum;
   
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
   
   public void setCgrheadId(String cgrheadId) {
     this.cgrheadId = cgrheadId;
   }
   
   public void setParamName(String paramName) {
     this.paramName = paramName;
   }
   
   public void setParamTxt(String paramTxt) {
     this.paramTxt = paramTxt;
   }
   
   public void setParamValue(String paramValue) {
     this.paramValue = paramValue;
   }
   
   public void setOrderNum(Integer orderNum) {
     this.orderNum = orderNum;
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
     if (!(o instanceof OnlCgreportParam))
       return false; 
     OnlCgreportParam onlCgreportParam = (OnlCgreportParam)o;
     if (!onlCgreportParam.canEqual(this))
       return false; 
     Integer integer1 = getOrderNum(), integer2 = onlCgreportParam.getOrderNum();
     if ((integer1 == null) ? (integer2 != null) : !integer1.equals(integer2))
       return false; 
     String str1 = getId(), str2 = onlCgreportParam.getId();
     if ((str1 == null) ? (str2 != null) : !str1.equals(str2))
       return false; 
     String str3 = getCgrheadId(), str4 = onlCgreportParam.getCgrheadId();
     if ((str3 == null) ? (str4 != null) : !str3.equals(str4))
       return false; 
     String str5 = getParamName(), str6 = onlCgreportParam.getParamName();
     if ((str5 == null) ? (str6 != null) : !str5.equals(str6))
       return false; 
     String str7 = getParamTxt(), str8 = onlCgreportParam.getParamTxt();
     if ((str7 == null) ? (str8 != null) : !str7.equals(str8))
       return false; 
     String str9 = getParamValue(), str10 = onlCgreportParam.getParamValue();
     if ((str9 == null) ? (str10 != null) : !str9.equals(str10))
       return false; 
     String str11 = getCreateBy(), str12 = onlCgreportParam.getCreateBy();
     if ((str11 == null) ? (str12 != null) : !str11.equals(str12))
       return false; 
     Date date1 = getCreateTime(), date2 = onlCgreportParam.getCreateTime();
     if ((date1 == null) ? (date2 != null) : !date1.equals(date2))
       return false; 
     String str13 = getUpdateBy(), str14 = onlCgreportParam.getUpdateBy();
     if ((str13 == null) ? (str14 != null) : !str13.equals(str14))
       return false; 
     Date date3 = getUpdateTime(), date4 = onlCgreportParam.getUpdateTime();
     return !((date3 == null) ? (date4 != null) : !date3.equals(date4));
   }
   
   protected boolean canEqual(Object other) {
     return other instanceof OnlCgreportParam;
   }
   
   public int hashCode() {
     byte b = 59;
     null = 1;
     Integer integer = getOrderNum();
     null = null * 59 + ((integer == null) ? 43 : integer.hashCode());
     String str1 = getId();
     null = null * 59 + ((str1 == null) ? 43 : str1.hashCode());
     String str2 = getCgrheadId();
     null = null * 59 + ((str2 == null) ? 43 : str2.hashCode());
     String str3 = getParamName();
     null = null * 59 + ((str3 == null) ? 43 : str3.hashCode());
     String str4 = getParamTxt();
     null = null * 59 + ((str4 == null) ? 43 : str4.hashCode());
     String str5 = getParamValue();
     null = null * 59 + ((str5 == null) ? 43 : str5.hashCode());
     String str6 = getCreateBy();
     null = null * 59 + ((str6 == null) ? 43 : str6.hashCode());
     Date date1 = getCreateTime();
     null = null * 59 + ((date1 == null) ? 43 : date1.hashCode());
     String str7 = getUpdateBy();
     null = null * 59 + ((str7 == null) ? 43 : str7.hashCode());
     Date date2 = getUpdateTime();
     return null * 59 + ((date2 == null) ? 43 : date2.hashCode());
   }
   
   public String toString() {
     return "OnlCgreportParam(id=" + getId() + ", cgrheadId=" + getCgrheadId() + ", paramName=" + getParamName() + ", paramTxt=" + getParamTxt() + ", paramValue=" + getParamValue() + ", orderNum=" + getOrderNum() + ", createBy=" + getCreateBy() + ", createTime=" + getCreateTime() + ", updateBy=" + getUpdateBy() + ", updateTime=" + getUpdateTime() + ")";
   }
   
   public String getId() {
     return this.id;
   }
   
   public String getCgrheadId() {
     return this.cgrheadId;
   }
   
   public String getParamName() {
     return this.paramName;
   }
   
   public String getParamTxt() {
     return this.paramTxt;
   }
   
   public String getParamValue() {
     return this.paramValue;
   }
   
   public Integer getOrderNum() {
     return this.orderNum;
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


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\cgreport\entity\OnlCgreportParam.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
