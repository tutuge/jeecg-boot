 package org.jeecg.modules.online.config.bAttribute;

 import org.jeecg.common.util.oConvertUtils;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.boot.context.properties.ConfigurationProperties;
 import org.springframework.stereotype.Component;

 @Component("dataBaseConfig")
 @ConfigurationProperties(prefix = "spring.datasource.dynamic.datasource.master")
 public class bDataBaseConfig {
   @Autowired
   private cDataBaseConfig dmDataBaseConfig;

   private String a;

   private String b;

   private String c;

   private String d;

   private publicKey e;

   public publicKey getDruid() {
     if (this.e == null)
       return this.dmDataBaseConfig.getDruid();
     return this.e;
   }

   public void setDruid(publicKey druid) {
     this.e = druid;
   }

   public String getUrl() {
     return oConvertUtils.getString(this.a, this.dmDataBaseConfig.getUrl());
   }

   public void setUrl(String url) {
     this.a = url;
   }

   public String getUsername() {
     return oConvertUtils.getString(this.b, this.dmDataBaseConfig.getUsername());
   }

   public void setUsername(String username) {
     this.b = username;
   }

   public String getPassword() {
     return oConvertUtils.getString(this.c, this.dmDataBaseConfig.getPassword());
   }

   public void setPassword(String password) {
     this.c = password;
   }

   public String getDriverClassName() {
     return oConvertUtils.getString(this.d, this.dmDataBaseConfig.getDriverClassName());
   }

   public void setDriverClassName(String driverClassName) {
     this.d = driverClassName;
   }

   public void setDmDataBaseConfig(cDataBaseConfig dmDataBaseConfig) {
     this.dmDataBaseConfig = dmDataBaseConfig;
   }
 }


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\config\b\b.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
