package kr.co.kpcard.billingservice.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.spi.JobFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import kr.co.kpcard.billingservice.app.job.settlement.GsPointDownloadSettlementJob;
import kr.co.kpcard.billingservice.app.job.settlement.GsPointUploadSettlementJob;
import kr.co.kpcard.billingservice.app.job.settlement.GsPopDownloadSettlementJob;
import kr.co.kpcard.billingservice.app.job.settlement.GsPopUploadSettlementJob;
import kr.co.kpcard.billingservice.app.job.settlement.GshbPopDownloadSettlement;
import kr.co.kpcard.billingservice.app.job.settlement.GshbPopUploadSettlement;
import kr.co.kpcard.billingservice.app.job.settlement.GssmDownloadSettlementJob;
import kr.co.kpcard.billingservice.app.job.settlement.GssmUploadSettlementJob;
import kr.co.kpcard.billingservice.app.job.settlement.HappyCashUploadSettlementJob;
import kr.co.kpcard.billingservice.app.job.settlement.HappyMoneyDonwloadSettlementJob;
import kr.co.kpcard.billingservice.app.job.settlement.PopcardSettlementJob;
import kr.co.kpcard.billingservice.app.job.settlement.SettlementFailReCompare;
import kr.co.kpcard.billingservice.app.job.settlement.SettlementJob;

@Configuration
@ConditionalOnProperty(name = "quartz.enabled")
public class SchedulerConfiguration {

	private static final Logger logger 
		= LoggerFactory.getLogger(SchedulerConfiguration.class);

	@Bean
	public JobFactory jobFactory(ApplicationContext applicationContext) 
	{
		AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
		jobFactory.setApplicationContext(applicationContext);
		return jobFactory;
	}
	
	@Bean
	public SchedulerFactoryBean schedulerFactoryBean(DataSource dataSource, JobFactory jobFactory
			, @Qualifier("popcardUploadJobTrigger") Trigger popcardUploadJobTrigger
			, @Qualifier("gspopUploadJobTrigger") Trigger gspopUploadJobTrigger
			, @Qualifier("gspopDownloadJobTrigger") Trigger gspopDownloadJobTrigger
			, @Qualifier("gshbUploadJobTrigger") Trigger gshbUploadJobTrigger
			, @Qualifier("gshbDownloadJobTrigger") Trigger gshbDownloadJobTrigger
			, @Qualifier("gssmUploadJobTrigger") Trigger gssmUploadJobTrigger
			, @Qualifier("gssmDownloadJobTrigger") Trigger gssmDownloadJobTrigger
			, @Qualifier("gsPointUploadJobTrigger") Trigger gsPointUploadJobTrigger
			, @Qualifier("gsPointDownloadJobTrigger") Trigger gsPointDownloadJobTrigger
			, @Qualifier("settlementJobTrigger") Trigger settlementJobTrigger
			, @Qualifier("settlementFailReCompareJobTrigger") Trigger settlementFailReCompareJobTrigger
			, @Qualifier("happyCashUploadJobTrigger") Trigger happyCashUploadJobTrigger
			, @Qualifier("happyMoneyDownloadJobTrigger") Trigger happyMoneyDownloadJobTrigger
			
			) throws IOException {
		
		logger.debug("========= schedulerFactoryBean start ==========");
		logger.info("database :" +dataSource);
		
		SchedulerFactoryBean factory = new SchedulerFactoryBean();
		factory.setOverwriteExistingJobs(true);
		factory.setAutoStartup(true);
		//factory.setDataSource(dataSource);
		factory.setJobFactory(jobFactory);
		factory.setQuartzProperties(quartzProperties());
		
		List<Trigger> triggers = new ArrayList<>();
        
		triggers.add(popcardUploadJobTrigger);
		// GSPOP JOB 
		triggers.add(gspopUploadJobTrigger);
		triggers.add(gspopDownloadJobTrigger);
		// GSHB JOB 
		triggers.add(gshbUploadJobTrigger);
		triggers.add(gshbDownloadJobTrigger);
		// GSSM JOB 
		triggers.add(gssmUploadJobTrigger);
		triggers.add(gssmDownloadJobTrigger);
		// GSNPOINT JOB
		// HAPPYCASH JOB
		triggers.add(happyCashUploadJobTrigger);
		// HAPPYCASH JOB
		triggers.add(gsPointUploadJobTrigger);
		triggers.add(gsPointDownloadJobTrigger);
		// 통합 대사 시작 
		// HAPPYCASH JOB
		triggers.add(happyMoneyDownloadJobTrigger);
		// 통합 대사 시작 
		triggers.add(settlementJobTrigger);
		
		//대사 실패데이터 재배치 JOB
		triggers.add(settlementFailReCompareJobTrigger);
		
		factory.setTriggers(triggers.toArray(new Trigger[triggers.size()]));
		
		logger.debug("========= schedulerFactoryBean End ==========");
		
		return factory;
	}
	
	
	@Bean(name = "popcardUploadJobTrigger")
	public CronTriggerFactoryBean popcardUploadJobTrigger(@Qualifier("popcardUploadJobDetail") JobDetail jobDetail
			, @Value("${cronJob.popcardUpload}") String cronExpression)	
	{
		return createCronTrigger(jobDetail, cronExpression); 
	}
	@Bean(name = "gspopUploadJobTrigger")
	public CronTriggerFactoryBean gspopUploadJobTrigger(@Qualifier("gspopUploadJobDetail") JobDetail jobDetail
			, @Value("${cronJob.gspopUpload}") String cronExpression)	
	{
		return createCronTrigger(jobDetail, cronExpression); 
	}	
	@Bean(name = "gspopDownloadJobTrigger")
	public CronTriggerFactoryBean gspopDownloadJobTrigger(@Qualifier("gspopDownloadJobDetail") JobDetail jobDetail
			, @Value("${cronJob.gspopDownload}") String cronExpression)	
	{
		return createCronTrigger(jobDetail, cronExpression); 
	}	
	@Bean(name = "gshbUploadJobTrigger")
	public CronTriggerFactoryBean gshbUploadJobTrigger(@Qualifier("gshbUploadJobDetail") JobDetail jobDetail
			, @Value("${cronJob.gshbUpload}") String cronExpression)	
	{
		return createCronTrigger(jobDetail, cronExpression); 
	}	
	@Bean(name = "gshbDownloadJobTrigger")
	public CronTriggerFactoryBean gshbDownloadJobTrigger(@Qualifier("gshbDownloadJobDetail") JobDetail jobDetail
			, @Value("${cronJob.gshbDownload}") String cronExpression)	
	{
		return createCronTrigger(jobDetail, cronExpression); 
	}
	@Bean(name = "gssmUploadJobTrigger")
	public CronTriggerFactoryBean gssmUploadJobTrigger(@Qualifier("gssmUploadJobDetail") JobDetail jobDetail
			, @Value("${cronJob.gssmUpload}") String cronExpression)	
	{
		return createCronTrigger(jobDetail, cronExpression); 
	}	
	@Bean(name = "gssmDownloadJobTrigger")
	public CronTriggerFactoryBean gssmDownloadJobTrigger(@Qualifier("gssmDownloadJobDetail") JobDetail jobDetail
			, @Value("${cronJob.gssmDownload}") String cronExpression)	
	{
		return createCronTrigger(jobDetail, cronExpression); 
	}	
	@Bean(name = "gsPointUploadJobTrigger")
	public CronTriggerFactoryBean gsPointUploadJobTrigger(@Qualifier("gsPointUploadJobDetail") JobDetail jobDetail
			, @Value("${cronJob.gsnPointUpload}") String cronExpression)	
	{
		return createCronTrigger(jobDetail, cronExpression); 
	}	
	@Bean(name = "gsPointDownloadJobTrigger")
	public CronTriggerFactoryBean gsPointDownloadJobTrigger(@Qualifier("gsPointDownloadJobDetail") JobDetail jobDetail
			, @Value("${cronJob.gsnPointDownload}") String cronExpression)	
	{
		return createCronTrigger(jobDetail, cronExpression); 
	}	
	@Bean(name = "settlementJobTrigger")
	public CronTriggerFactoryBean settlementJobTrigger(@Qualifier("settlementJobDetail") JobDetail jobDetail
			, @Value("${cronJob.settlement}") String cronExpression)	
	{
		return createCronTrigger(jobDetail, cronExpression); 
	}	
	@Bean(name = "settlementFailReCompareJobTrigger")
	public CronTriggerFactoryBean settlementFailReCompareJobTrigger(@Qualifier("settlementFailReCompareJobDetail") JobDetail jobDetail
			, @Value("${cronJob.settlementFailReCompare}") String cronExpression)	
	{
		return createCronTrigger(jobDetail, cronExpression); 
	}	
	@Bean(name = "happyCashUploadJobTrigger")
	public CronTriggerFactoryBean happyCashUploadJobTrigger(@Qualifier("happyCashUploadJobDetail") JobDetail jobDetail
			, @Value("${cronJob.happyCashUpload}") String cronExpression)	
	{
		return createCronTrigger(jobDetail, cronExpression); 
	}
	
	@Bean(name = "happyMoneyDownloadJobTrigger")
	public CronTriggerFactoryBean happyMoneyDownloadJobTrigger(@Qualifier("happyMoneyDownloadJobDetail") JobDetail jobDetail
			, @Value("${cronJob.happyMoneyDownload}") String cronExpression)	
	{
		return createCronTrigger(jobDetail, cronExpression); 
	}	
	
	@Bean
	public JobDetailFactoryBean popcardUploadJobDetail()
	{
		return createJobDetail(PopcardSettlementJob.class);
	}
	
	@Bean
	public JobDetailFactoryBean gspopUploadJobDetail()
	{
		return createJobDetail(GsPopUploadSettlementJob.class);
	}
	
	@Bean
	public JobDetailFactoryBean gspopDownloadJobDetail()
	{
		return createJobDetail(GsPopDownloadSettlementJob.class);
	}
	
	@Bean
	public JobDetailFactoryBean gshbUploadJobDetail()
	{
		return createJobDetail(GshbPopUploadSettlement.class);
	}
	
	@Bean
	public JobDetailFactoryBean gshbDownloadJobDetail()
	{
		return createJobDetail(GshbPopDownloadSettlement.class);
	}
	
	@Bean
	public JobDetailFactoryBean gssmUploadJobDetail()
	{
		return createJobDetail(GssmUploadSettlementJob.class);
	}
	
	@Bean
	public JobDetailFactoryBean gssmDownloadJobDetail()
	{
		return createJobDetail(GssmDownloadSettlementJob.class);
	}
	
	@Bean
	public JobDetailFactoryBean gsPointUploadJobDetail()
	{
		return createJobDetail(GsPointUploadSettlementJob.class);
	}
	
	
	@Bean
	public JobDetailFactoryBean gsPointDownloadJobDetail()
	{
		return createJobDetail(GsPointDownloadSettlementJob.class);
	}	
	
	@Bean
	public JobDetailFactoryBean settlementJobDetail()
	{
		return createJobDetail(SettlementJob.class);
	}
	
	@Bean
	public JobDetailFactoryBean settlementFailReCompareJobDetail()
	{
		return createJobDetail(SettlementFailReCompare.class);
	}
	
	@Bean
	public JobDetailFactoryBean happyCashUploadJobDetail()
	{
		return createJobDetail(HappyCashUploadSettlementJob.class);
	}
	
	@Bean
	public JobDetailFactoryBean happyMoneyDownloadJobDetail()
	{
		return createJobDetail(HappyMoneyDonwloadSettlementJob.class);
	}
	
	@Bean
	public Properties quartzProperties() throws IOException 
	{
		PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
		propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
		propertiesFactoryBean.afterPropertiesSet();
		return propertiesFactoryBean.getObject();
	}
	
	private static JobDetailFactoryBean createJobDetail(Class jobClass) {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(jobClass);
        // job has to be durable to be stored in DB:
        factoryBean.setDurability(true);
        return factoryBean;
    }

    // Use this method for creating cron triggers instead of simple triggers:
    private static CronTriggerFactoryBean createCronTrigger(JobDetail jobDetail, String cronExpression) {
        CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
        factoryBean.setJobDetail(jobDetail);
        factoryBean.setCronExpression(cronExpression);
        factoryBean.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
        return factoryBean;
    }

}
