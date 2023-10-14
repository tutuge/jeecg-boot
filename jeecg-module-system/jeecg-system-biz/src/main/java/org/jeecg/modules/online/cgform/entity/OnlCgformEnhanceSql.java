 package org.jeecg.modules.online.cgform.entity;
 
 import com.baomidou.mybatisplus.annotation.IdType;
 import com.baomidou.mybatisplus.annotation.TableId;
 import com.baomidou.mybatisplus.annotation.TableName;
 import java.io.Serializable;
 
 @TableName("onl_cgform_enhance_sql")
 public class OnlCgformEnhanceSql implements Serializable {
   private static final long serialVersionUID = 1L;
   
   @TableId(type = IdType.ASSIGN_UUID)
   private String id;
   
   private String cgformHeadId;
   
   private String buttonCode;
   
   private String cgbSql;
   
   private String cgbSqlName;
   
   private String content;
   
   public void setId(String id) {
     this.id = id;
   }
   
   public void setCgformHeadId(String cgformHeadId) {
     this.cgformHeadId = cgformHeadId;
   }
   
   public void setButtonCode(String buttonCode) {
     this.buttonCode = buttonCode;
   }
   
   public void setCgbSql(String cgbSql) {
     this.cgbSql = cgbSql;
   }
   
   public void setCgbSqlName(String cgbSqlName) {
     this.cgbSqlName = cgbSqlName;
   }
   
   public void setContent(String content) {
     this.content = content;
   }
   
   public boolean equals(Object o) {
     if (o == this)
       return true; 
     if (!(o instanceof OnlCgformEnhanceSql))
       return false; 
     OnlCgformEnhanceSql onlCgformEnhanceSql = (OnlCgformEnhanceSql)o;
     if (!onlCgformEnhanceSql.canEqual(this))
       return false; 
     String str1 = getId(), str2 = onlCgformEnhanceSql.getId();
     if ((str1 == null) ? (str2 != null) : !str1.equals(str2))
       return false; 
     String str3 = getCgformHeadId(), str4 = onlCgformEnhanceSql.getCgformHeadId();
     if ((str3 == null) ? (str4 != null) : !str3.equals(str4))
       return false; 
     String str5 = getButtonCode(), str6 = onlCgformEnhanceSql.getButtonCode();
     if ((str5 == null) ? (str6 != null) : !str5.equals(str6))
       return false; 
     String str7 = getCgbSql(), str8 = onlCgformEnhanceSql.getCgbSql();
     if ((str7 == null) ? (str8 != null) : !str7.equals(str8))
       return false; 
     String str9 = getCgbSqlName(), str10 = onlCgformEnhanceSql.getCgbSqlName();
     if ((str9 == null) ? (str10 != null) : !str9.equals(str10))
       return false; 
     String str11 = getContent(), str12 = onlCgformEnhanceSql.getContent();
     return !((str11 == null) ? (str12 != null) : !str11.equals(str12));
   }
   
   protected boolean canEqual(Object other) {
     return other instanceof OnlCgformEnhanceSql;
   }
   
   public int hashCode() {
     byte b = 59;
     null = 1;
     String str1 = getId();
     null = null * 59 + ((str1 == null) ? 43 : str1.hashCode());
     String str2 = getCgformHeadId();
     null = null * 59 + ((str2 == null) ? 43 : str2.hashCode());
     String str3 = getButtonCode();
     null = null * 59 + ((str3 == null) ? 43 : str3.hashCode());
     String str4 = getCgbSql();
     null = null * 59 + ((str4 == null) ? 43 : str4.hashCode());
     String str5 = getCgbSqlName();
     null = null * 59 + ((str5 == null) ? 43 : str5.hashCode());
     String str6 = getContent();
     return null * 59 + ((str6 == null) ? 43 : str6.hashCode());
   }
   
   public String toString() {
     return "OnlCgformEnhanceSql(id=" + getId() + ", cgformHeadId=" + getCgformHeadId() + ", buttonCode=" + getButtonCode() + ", cgbSql=" + getCgbSql() + ", cgbSqlName=" + getCgbSqlName() + ", content=" + getContent() + ")";
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
   
   public String getCgbSql() {
     return this.cgbSql;
   }
   
   public String getCgbSqlName() {
     return this.cgbSqlName;
   }
   
   public String getContent() {
     return this.content;
   }
 }


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\cgform\entity\OnlCgformEnhanceSql.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
