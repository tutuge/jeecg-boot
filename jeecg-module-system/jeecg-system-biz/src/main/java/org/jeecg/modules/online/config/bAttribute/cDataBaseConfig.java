 package org.jeecg.modules.online.config.bAttribute;

 import org.springframework.boot.context.properties.ConfigurationProperties;
 import org.springframework.stereotype.Component;

 @Component("dmDataBaseConfig")
 @ConfigurationProperties(prefix = "spring.datasource.druid")
 public class cDataBaseConfig {
   private String a;

   private String b;

   private String c;

   private String d;

   private publicKey e;

   public publicKey getDruid() {
     return this.e;
   }

   public void setDruid(publicKey druid) {
     this.e = druid;
   }

   public String getUrl() {
     return this.a;
   }

   public void setUrl(String url) {
     this.a = url;
   }

   public String getUsername() {
     return this.b;
   }

   public void setUsername(String username) {
     this.b = username;
   }

   public String getPassword() {
     return this.c;
   }

   public void setPassword(String password) {
     this.c = password;
   }

   public String getDriverClassName() {
     return this.d;
   }

   public void setDriverClassName(String driverClassName) {
     this.d = driverClassName;
   }
 }


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\config\b\c.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
