package kr.co.kpcard.billingservice.app;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@MapperScan(basePackages={"kr.co.kpcard.erp.repository"
						 }, sqlSessionFactoryRef="erpSqlFactory")
@EnableTransactionManagement
public class ErpDatabaseConfig {
	
	private Logger logger = LoggerFactory.getLogger(ErpDatabaseConfig.class);

	@Bean(name = "erpTransactionManager")
	public DataSourceTransactionManager erpTransactionManager() {
		return new DataSourceTransactionManager(erpDataSource());
	}
	
    @Bean(name = "erpDataSource")  
    @ConfigurationProperties(prefix = "spring.db4.datasource")
    public DataSource erpDataSource() {
    	HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig());
    	logger.debug("backOfficeDataSource getMaximumPoolSize : {} " , hikariDataSource.getMaximumPoolSize());
    	logger.debug("backOfficeDataSource getConnectionTimeout : {} " , hikariDataSource.getConnectionTimeout());
    	/*
    	logger.debug("getJdbcUrl : {} " , hikariDataSource.getJdbcUrl());
    	logger.debug("getUsername() : {} " , hikariDataSource.getUsername());
    	logger.debug("getPassword() : {} " , hikariDataSource.getPassword());
    	*/
        return hikariDataSource;
    }

    @Bean(name = "erpSqlFactory")
    public SqlSessionFactory erpSqlSessionFactory(@Qualifier("erpDataSource") DataSource erpDataSource, ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(erpDataSource);
        return sessionFactory.getObject();
    }
    
    @Bean(name = "erpSqlTemplate")
    public SqlSessionTemplate erpSqlSessionTemplate(SqlSessionFactory erpSqlSessionFactory) throws Exception {

        return new SqlSessionTemplate(erpSqlSessionFactory);
    }
    
    @Bean(name = "erpHikariConfig")  
    @ConfigurationProperties(prefix = "spring.db4.datasource.hikari")
    public HikariConfig hikariConfig() {
    	return new HikariConfig();
    }    
}
