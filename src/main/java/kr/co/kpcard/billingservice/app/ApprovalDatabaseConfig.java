package kr.co.kpcard.billingservice.app;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@MapperScan(basePackages={"kr.co.kpcard.billingservice.app.repository.settlement"
						, "kr.co.kpcard.billingservice.app.repository.approval"
						 }, sqlSessionFactoryRef="approvalSqlFactory")
@EnableTransactionManagement
public class ApprovalDatabaseConfig {
	
	private Logger logger = LoggerFactory.getLogger(ApprovalDatabaseConfig.class);

    @Bean(name = "approvalDataSource")  
    @ConfigurationProperties(prefix = "spring.db3.datasource")
    public DataSource approvalDataSource(@Qualifier("approvalHikariConfig") HikariConfig hikariConfig) {
    	HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
    	logger.debug("backOfficeDataSource getMaximumPoolSize : {} " , hikariDataSource.getMaximumPoolSize());
    	logger.debug("backOfficeDataSource getConnectionTimeout : {} " , hikariDataSource.getConnectionTimeout());
    	/*
    	logger.debug("getJdbcUrl : {} " , hikariDataSource.getJdbcUrl());
    	logger.debug("getUsername() : {} " , hikariDataSource.getUsername());
    	logger.debug("getPassword() : {} " , hikariDataSource.getPassword());
    	*/
        return hikariDataSource;
    }

    @Bean(name = "approvalSqlFactory")
    public SqlSessionFactory approvalSqlSessionFactory(@Qualifier("approvalDataSource") DataSource approvalDataSource, ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(approvalDataSource);
        return sessionFactory.getObject();
    }
    
    @Bean(name = "approvalSqlTemplate")
    public SqlSessionTemplate approvalSqlSessionTemplate(SqlSessionFactory approvalSqlSessionFactory) throws Exception {

        return new SqlSessionTemplate(approvalSqlSessionFactory);
    }
    
    @Bean(name = "approvalHikariConfig")  
    @ConfigurationProperties(prefix = "spring.db3.datasource.hikari")
    public HikariConfig hikariConfig() {
    	return new HikariConfig();
    }    
}
