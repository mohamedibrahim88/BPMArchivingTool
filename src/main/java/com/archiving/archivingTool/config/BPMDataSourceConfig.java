package com.archiving.archivingTool.config;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
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
        basePackages = "com.archiving.archivingTool.repository.bpm",
        entityManagerFactoryRef= "bpmEntityManagerFactory",
        transactionManagerRef = "bpmTransactionManager"
)
public class BPMDataSourceConfig {
    @Bean(name="bpmDataSource")
//    @ConfigurationProperties(prefix="spring.bpm")
    public DataSource bpmDataSource (){
           return   DataSourceBuilder.create()
            .url("jdbc:oracle:thin:@//192.168.1.31:1521/XEPDB1")
                .username("BPMDBA")
                .password("dbadmin")
                .driverClassName("oracle.jdbc.OracleDriver").build();
    }

//    @Bean(name="bpmEntityManagerFactory")
//    public LocalContainerEntityManagerFactoryBean bpmEntityManagerFactory(
//            EntityManagerFactoryBuilder builder) {
//        return builder
//                .dataSource(bpmDataSource())
//                .packages("com.archiving.archivingTool.entity.bpm") // Adjust this path
//                .persistenceUnit("bpm")
//                .properties(hibernateProperties())
//                .build();
//    }

    @Bean(name="bpmEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean bpmEntityManagerFactory(
            DataSource bpmDataSource) throws SQLException {
        System.out.println("DataSource: " + bpmDataSource());
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(bpmDataSource);
        em.setPackagesToScan("com.archiving.archivingTool.entity.bpm");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setEntityManagerFactoryInterface(EntityManagerFactory.class);
        em.setPersistenceUnitName("bpm");
        em.setJpaPropertyMap(hibernateProperties());
        //    System.out.println("em : " + em.getDataSource().getConnection().createStatement().execute("create table Mariam (name varchar(50) NOT NULL)"));
        return em;
    }
    @Bean(name = "bpmTransactionManager")
    public PlatformTransactionManager bpmTransactionManager(
            @Qualifier("bpmEntityManagerFactory") EntityManagerFactory bpmEntityManagerFactory) {
        return new JpaTransactionManager(bpmEntityManagerFactory);
    }
    private Map<String, Object> hibernateProperties() {
        return Map.of(
                "hibernate.hbm2ddl.auto", "none", // No auto-update or create
                "hibernate.dialect", "org.hibernate.dialect.OracleDialect"
        );
    }
}
