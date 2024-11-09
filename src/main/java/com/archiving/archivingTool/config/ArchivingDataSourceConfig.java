package com.archiving.archivingTool.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Map;
@Configuration
@EnableJpaRepositories(
        basePackages = "com.archiving.archivingTool.repository.archiving",
        entityManagerFactoryRef= "archivingEntityManagerFactory",
        transactionManagerRef = "archivingTransactionManager"
)
public class ArchivingDataSourceConfig {

    @Bean(name="archivingDataSource")
//    @ConfigurationProperties(prefix="spring.archiving")
    public DataSource archivingDataSource (){
        return   DataSourceBuilder.create()
            .url("jdbc:oracle:thin:@//oradb:1521/XE")
                .username("Archiving_Solution")
                .password("dbadmin")
                .driverClassName("oracle.jdbc.OracleDriver").build();
    }

//    @Bean(name="archivingEntityManagerFactory")
//    public LocalContainerEntityManagerFactoryBean archivingEntityManagerFactory(
//            EntityManagerFactoryBuilder builder) {
//        return builder
//                .dataSource(archivingDataSource())
//                .packages("com.archiving.archivingTool.entity.archiving") // Adjust this path
//                .persistenceUnit("archiving")
//                .properties(hibernateProperties())
//                .build();
//    }
    @Bean(name="archivingEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean archiveEntityManagerFactory(
            DataSource archivingDataSource) throws SQLException {
        System.out.println("DataSource: " + archivingDataSource);
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(archivingDataSource);
        em.setPackagesToScan("com.archiving.archivingTool.entity.archiving");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setEntityManagerFactoryInterface(EntityManagerFactory.class);
        em.setPersistenceUnitName("archiving");
        em.setJpaPropertyMap(hibernateProperties());
    //    System.out.println("em : " + em.getDataSource().getConnection().createStatement().execute("create table Mariam (name varchar(50) NOT NULL)"));
        return em;
    }
    @Bean(name ="archivingTransactionManager")
    public PlatformTransactionManager archivingTransactionManager(
            @Qualifier("archivingEntityManagerFactory") EntityManagerFactory archivingEntityManagerFactory) {
        return new JpaTransactionManager(archivingEntityManagerFactory);
    }
    private Map<String, Object> hibernateProperties() {
        return Map.of(
                "hibernate.hbm2ddl.auto", "update", // No auto-update or create
                "hibernate.dialect", "org.hibernate.dialect.OracleDialect"
        );
    }
}
