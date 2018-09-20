package kr.co.kpcard.billingservice.app;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@MapperScan(basePackages="kr.co.kpcard.backoffice.repository", sqlSessionFactoryRef="backOfficeSqlFactory")
@EnableTransactionManagement
public class BackOfficeDatabaseConfig {

	private Logger logger = LoggerFactory.getLogger(BackOfficeDatabaseConfig.class);

	@Autowired
	private Environment environment; 

	@Bean(name = "backOfficeTransactionManager")
	public DataSourceTransactionManager backOfficeTransactionManager() {
		return new DataSourceTransactionManager(backOfficeDataSource());
	}
	
	@Primary
    @Bean(name = "backOfficeDataSource")  
    public DataSource backOfficeDataSource() {

		HikariDataSource dataSource = new HikariDataSource();
		
		dataSource.setMaximumPoolSize(Integer.parseInt(environment.getRequiredProperty("spring.db1.datasource.hikari.maximum-pool-size")));
		dataSource.setConnectionTimeout(Integer.parseInt(environment.getRequiredProperty("spring.db1.datasource.hikari.connection-timeout")));
		dataSource.setJdbcUrl(environment.getRequiredProperty("spring.db1.datasource.hikari.jdbcUrl"));
		dataSource.setUsername(environment.getRequiredProperty("spring.db1.datasource.hikari.username"));
		dataSource.setPassword(environment.getRequiredProperty("spring.db1.datasource.hikari.password"));

		
		logger.debug("------------------------------ backOfficeDataSource --------------------------------------------");
    	logger.debug("backOfficeDataSource getMaximumPoolSize : {} " , environment.getRequiredProperty("spring.db1.datasource.hikari.maximum-pool-size"));
    	logger.debug("backOfficeDataSource getConnectionTimeout : {} " , environment.getRequiredProperty("spring.db1.datasource.hikari.connection-timeout"));
    	/*
    	logger.debug("getJdbcUrl : {} " , environment.getRequiredProperty("spring.db1.datasource.hikari.jdbcUrl"));
    	logger.debug("getUsername() : {} " , environment.getRequiredProperty("spring.db1.datasource.hikari.username"));
    	logger.debug("getPassword() : {} " , environment.getRequiredProperty("spring.db1.datasource.hikari.password"));
    	*/
    	logger.debug("------------------------------ backOfficeDataSource --------------------------------------------");
        return dataSource;
    }

	@Primary
    @Bean(name = "backOfficeSqlFactory")
    public SqlSessionFactory backOfficeSqlSessionFactory(@Qualifier("backOfficeDataSource") DataSource backOfficeDataSource, ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(backOfficeDataSource);
        return sessionFactory.getObject();
    }
    
    @Bean(name = "backOfficeSqlTemplate")
    public SqlSessionTemplate backOfficeSqlSessionTemplate(SqlSessionFactory backOfficeSqlSessionFactory) throws Exception {

        return new SqlSessionTemplate(backOfficeSqlSessionFactory);
    }
    
    @Bean(name = "backOfficeHikariConfig")  
    @ConfigurationProperties(prefix = "spring.db1.datasource.hikari")
    public HikariConfig hikariConfig() {
    	return new HikariConfig();
    }    

}
