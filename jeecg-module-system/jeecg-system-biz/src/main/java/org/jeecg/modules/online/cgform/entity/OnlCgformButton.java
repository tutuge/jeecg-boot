 package org.jeecg.modules.online.cgform.entity;
 
 import com.baomidou.mybatisplus.annotation.IdType;
 import com.baomidou.mybatisplus.annotation.TableId;
 import com.baomidou.mybatisplus.annotation.TableName;
 import java.io.Serializable;
 
 @TableName("onl_cgform_button")
 public class OnlCgformButton implements Serializable {
   private static final long serialVersionUID = 1L;
   
   @TableId(type = IdType.ASSIGN_UUID)
   private String id;
   
   private String cgformHeadId;
   
   private String buttonCode;
   
   private String buttonName;
   
   private String buttonStyle;
   
   private String optType;
   
   private String exp;
   
   private String buttonStatus;
   
   private Integer orderNum;
   
   private String buttonIcon;
   
   private String optPosition;
   
   public void setId(String id) {
     this.id = id;
   }
   
   public void setCgformHeadId(String cgformHeadId) {
     this.cgformHeadId = cgformHeadId;
   }
   
   public void setButtonCode(String buttonCode) {
     this.buttonCode = buttonCode;
   }
   
   public void setButtonName(String buttonName) {
     this.buttonName = buttonName;
   }
   
   public void setButtonStyle(String buttonStyle) {
     this.buttonStyle = buttonStyle;
   }
   
   public void setOptType(String optType) {
     this.optType = optType;
   }
   
   public void setExp(String exp) {
     this.exp = exp;
   }
   
   public void setButtonStatus(String buttonStatus) {
     this.buttonStatus = buttonStatus;
   }
   
   public void setOrderNum(Integer orderNum) {
     this.orderNum = orderNum;
   }
   
   public void setButtonIcon(String buttonIcon) {
     this.buttonIcon = buttonIcon;
   }
   
   public void setOptPosition(String optPosition) {
     this.optPosition = optPosition;
   }
   
   public boolean equals(Object o) {
     if (o == this)
       return true; 
     if (!(o instanceof OnlCgformButton))
       return false; 
     OnlCgformButton onlCgformButton = (OnlCgformButton)o;
     if (!onlCgformButton.canEqual(this))
       return false; 
     Integer integer1 = getOrderNum(), integer2 = onlCgformButton.getOrderNum();
     if ((integer1 == null) ? (integer2 != null) : !integer1.equals(integer2))
       return false; 
     String str1 = getId(), str2 = onlCgformButton.getId();
     if ((str1 == null) ? (str2 != null) : !str1.equals(str2))
       return false; 
     String str3 = getCgformHeadId(), str4 = onlCgformButton.getCgformHeadId();
     if ((str3 == null) ? (str4 != null) : !str3.equals(str4))
       return false; 
     String str5 = getButtonCode(), str6 = onlCgformButton.getButtonCode();
     if ((str5 == null) ? (str6 != null) : !str5.equals(str6))
       return false; 
     String str7 = getButtonName(), str8 = onlCgformButton.getButtonName();
     if ((str7 == null) ? (str8 != null) : !str7.equals(str8))
       return false; 
     String str9 = getButtonStyle(), str10 = onlCgformButton.getButtonStyle();
     if ((str9 == null) ? (str10 != null) : !str9.equals(str10))
       return false; 
     String str11 = getOptType(), str12 = onlCgformButton.getOptType();
     if ((str11 == null) ? (str12 != null) : !str11.equals(str12))
       return false; 
     String str13 = getExp(), str14 = onlCgformButton.getExp();
     if ((str13 == null) ? (str14 != null) : !str13.equals(str14))
       return false; 
     String str15 = getButtonStatus(), str16 = onlCgformButton.getButtonStatus();
     if ((str15 == null) ? (str16 != null) : !str15.equals(str16))
       return false; 
     String str17 = getButtonIcon(), str18 = onlCgformButton.getButtonIcon();
     if ((str17 == null) ? (str18 != null) : !str17.equals(str18))
       return false; 
     String str19 = getOptPosition(), str20 = onlCgformButton.getOptPosition();
     return !((str19 == null) ? (str20 != null) : !str19.equals(str20));
   }
   
   protected boolean canEqual(Object other) {
     return other instanceof OnlCgformButton;
   }
   
   public int hashCode() {
     byte b = 59;
     null = 1;
     Integer integer = getOrderNum();
     null = null * 59 + ((integer == null) ? 43 : integer.hashCode());
     String str1 = getId();
     null = null * 59 + ((str1 == null) ? 43 : str1.hashCode());
     String str2 = getCgformHeadId();
     null = null * 59 + ((str2 == null) ? 43 : str2.hashCode());
     String str3 = getButtonCode();
     null = null * 59 + ((str3 == null) ? 43 : str3.hashCode());
     String str4 = getButtonName();
     null = null * 59 + ((str4 == null) ? 43 : str4.hashCode());
     String str5 = getButtonStyle();
     null = null * 59 + ((str5 == null) ? 43 : str5.hashCode());
     String str6 = getOptType();
     null = null * 59 + ((str6 == null) ? 43 : str6.hashCode());
     String str7 = getExp();
     null = null * 59 + ((str7 == null) ? 43 : str7.hashCode());
     String str8 = getButtonStatus();
     null = null * 59 + ((str8 == null) ? 43 : str8.hashCode());
     String str9 = getButtonIcon();
     null = null * 59 + ((str9 == null) ? 43 : str9.hashCode());
     String str10 = getOptPosition();
     return null * 59 + ((str10 == null) ? 43 : str10.hashCode());
   }
   
   public String toString() {
     return "OnlCgformButton(id=" + getId() + ", cgformHeadId=" + getCgformHeadId() + ", buttonCode=" + getButtonCode() + ", buttonName=" + getButtonName() + ", buttonStyle=" + getButtonStyle() + ", optType=" + getOptType() + ", exp=" + getExp() + ", buttonStatus=" + getButtonStatus() + ", orderNum=" + getOrderNum() + ", buttonIcon=" + getButtonIcon() + ", optPosition=" + getOptPosition() + ")";
   }
   
   public String getId() {
     return this.id;
   }
   
   public String getCgformHeadId() {
     return this.cgformHeadId;
   }
   
   public String getButtonCode() {
     return this.buttonCode;
   }
   
   public String getButtonName() {
     return this.buttonName;
   }
   
   public String getButtonStyle() {
     return this.buttonStyle;
   }
   
   public String getOptType() {
     return this.optType;
   }
   
   public String getExp() {
     return this.exp;
   }
   
   public String getButtonStatus() {
     return this.buttonStatus;
   }
   
   public Integer getOrderNum() {
     return this.orderNum;
   }
   
   public String getButtonIcon() {
     return this.buttonIcon;
   }
   
   public String getOptPosition() {
     return this.optPosition;
   }
 }


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\cgform\entity\OnlCgformButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
