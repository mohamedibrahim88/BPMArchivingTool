//package com.archiving.archivingTool.config;
//
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//
//import javax.sql.DataSource;
//import java.sql.SQLException;
//
//@Configuration
//public class DataSourceConfig {
//
//    @Bean(name="bpmDataSource")
////    @ConfigurationProperties(prefix="spring.bpm")
//    public DataSource bpmDataSource() {
//         DataSource dataSource= DataSourceBuilder.create()
//                .url("jdbc:oracle:thin:@//oradb:1521/XE")
//                .username("process_db")
//                .password("dbadmin")
//                .driverClassName("oracle.jdbc.OracleDriver").build();
//        System.out.println("BPM Config " + dataSource);
//         return dataSource;
//    }
//
//    @Bean(name="archivingDataSource")
////    @ConfigurationProperties(prefix="spring.archiving")
//    public DataSource archivingDataSource() {
//
//        return  DataSourceBuilder.create()
//                .url("jdbc:oracle:thin:@//oradb:1521/XE")
//                .username("Archiving_Solution")
//                .password("dbadmin")
//                .driverClassName("oracle.jdbc.OracleDriver").build();
//
////        System.out.println(dataSource);
////        return dataSource;
//    }
//
//    @Bean(name="bpmEntityManagerFactory")
//    public LocalContainerEntityManagerFactoryBean bpmEntityManagerFactory(
//            DataSource bpmDataSource) {
//        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
//        em.setDataSource(bpmDataSource);
//        em.setPackagesToScan("com.archiving.archivingTool.entity.bpm");
//        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
//        return em;
//    }
//
//
//    @Bean(name="archivingEntityManagerFactory")
//    public LocalContainerEntityManagerFactoryBean archiveEntityManagerFactory(
//            DataSource archivingDataSource) throws SQLException {
//        System.out.println("DataSource: " + archivingDataSource);
//        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
//        em.setDataSource(archivingDataSource);
//        em.setPackagesToScan("com.archiving.archivingTool.entity.archiving");
//        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
//    //    System.out.println("em : " + em.getDataSource().getConnection().createStatement().execute("create table Mariam (name varchar(50) NOT NULL)"));
//        return em;
//    }
//
//}
