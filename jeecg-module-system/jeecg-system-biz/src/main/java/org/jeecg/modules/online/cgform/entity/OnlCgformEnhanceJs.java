 package org.jeecg.modules.online.cgform.entity;
 
 import com.baomidou.mybatisplus.annotation.IdType;
 import com.baomidou.mybatisplus.annotation.TableId;
 import com.baomidou.mybatisplus.annotation.TableName;
 import java.io.Serializable;
 
 @TableName("onl_cgform_enhance_js")
 public class OnlCgformEnhanceJs implements Serializable {
   private static final long serialVersionUID = 1L;
   
   @TableId(type = IdType.ASSIGN_UUID)
   private String id;
   
   private String cgformHeadId;
   
   private String cgJsType;
   
   private String cgJs;
   
   private String content;
   
   public void setId(String id) {
     this.id = id;
   }
   
   public void setCgformHeadId(String cgformHeadId) {
     this.cgformHeadId = cgformHeadId;
   }
   
   public void setCgJsType(String cgJsType) {
     this.cgJsType = cgJsType;
   }
   
   public void setCgJs(String cgJs) {
     this.cgJs = cgJs;
   }
   
   public void setContent(String content) {
     this.content = content;
   }
   
   public boolean equals(Object o) {
     if (o == this)
       return true; 
     if (!(o instanceof OnlCgformEnhanceJs))
       return false; 
     OnlCgformEnhanceJs onlCgformEnhanceJs = (OnlCgformEnhanceJs)o;
     if (!onlCgformEnhanceJs.canEqual(this))
       return false; 
     String str1 = getId(), str2 = onlCgformEnhanceJs.getId();
     if ((str1 == null) ? (str2 != null) : !str1.equals(str2))
       return false; 
     String str3 = getCgformHeadId(), str4 = onlCgformEnhanceJs.getCgformHeadId();
     if ((str3 == null) ? (str4 != null) : !str3.equals(str4))
       return false; 
     String str5 = getCgJsType(), str6 = onlCgformEnhanceJs.getCgJsType();
     if ((str5 == null) ? (str6 != null) : !str5.equals(str6))
       return false; 
     String str7 = getCgJs(), str8 = onlCgformEnhanceJs.getCgJs();
     if ((str7 == null) ? (str8 != null) : !str7.equals(str8))
       return false; 
     String str9 = getContent(), str10 = onlCgformEnhanceJs.getContent();
     return !((str9 == null) ? (str10 != null) : !str9.equals(str10));
   }
   
   protected boolean canEqual(Object other) {
     return other instanceof OnlCgformEnhanceJs;
   }
   
   public int hashCode() {
     byte b = 59;
     null = 1;
     String str1 = getId();
     null = null * 59 + ((str1 == null) ? 43 : str1.hashCode());
     String str2 = getCgformHeadId();
     null = null * 59 + ((str2 == null) ? 43 : str2.hashCode());
     String str3 = getCgJsType();
     null = null * 59 + ((str3 == null) ? 43 : str3.hashCode());
     String str4 = getCgJs();
     null = null * 59 + ((str4 == null) ? 43 : str4.hashCode());
     String str5 = getContent();
     return null * 59 + ((str5 == null) ? 43 : str5.hashCode());
   }
   
   public String toString() {
     return "OnlCgformEnhanceJs(id=" + getId() + ", cgformHeadId=" + getCgformHeadId() + ", cgJsType=" + getCgJsType() + ", cgJs=" + getCgJs() + ", content=" + getContent() + ")";
   }
   
   public String getId() {
     return this.id;
   }
   
   public String getCgformHeadId() {
     return this.cgformHeadId;
   }
   
   public String getCgJsType() {
     return this.cgJsType;
   }
   
   public String getCgJs() {
     return this.cgJs;
   }
   
   public String getContent() {
     return this.content;
   }
 }


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\cgform\entity\OnlCgformEnhanceJs.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
