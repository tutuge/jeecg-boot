//package org.jeecg.modules.cable.config;
//
//import org.apache.shardingsphere.driver.api.yaml.YamlShardingSphereDataSourceFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//
//import javax.sql.DataSource;
//import java.io.File;
//import java.io.IOException;
//import java.sql.SQLException;
//
//import static org.springframework.util.ResourceUtils.getFile;
//
//@Configuration
//public class MysqlConfig {
//    @Primary
//    @Bean("dataSource")
//    public DataSource getDataSource() throws SQLException, IOException {
//        File file = getFile("/home/sql/mysql/mysql.yml");//正式环境
//        if (!file.exists()) {
//            file = getFile("classpath:mysql.yml");
//        }
//        return YamlShardingSphereDataSourceFactory.createDataSource(file);
//    }
//}
//
