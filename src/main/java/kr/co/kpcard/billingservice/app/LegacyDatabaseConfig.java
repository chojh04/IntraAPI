package kr.co.kpcard.billingservice.app;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@MapperScan(basePackages="kr.co.kpcard.billingservice.app.repository.legacy", sqlSessionFactoryRef="legacySqlFactory")
@EnableTransactionManagement
public class LegacyDatabaseConfig {

    @Bean(name = "legacyDataSource")  
    @ConfigurationProperties(prefix = "spring.db2.datasource")
    public DataSource legacyDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "legacySqlFactory")
    public SqlSessionFactory legacySqlSessionFactory(@Qualifier("legacyDataSource") DataSource legacyDataSource, ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(legacyDataSource);
        return sessionFactory.getObject();
    }

    @Bean(name = "legacySqlTemplate")
    public SqlSessionTemplate legacySqlSessionTemplate(SqlSessionFactory legacySqlSessionFactory) throws Exception {

        return new SqlSessionTemplate(legacySqlSessionFactory);
    }
}
