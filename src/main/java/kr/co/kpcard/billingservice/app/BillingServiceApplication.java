package kr.co.kpcard.billingservice.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.system.ApplicationPidFileWriter;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@ComponentScan("kr.co.kpcard")
@PropertySource("classpath:application.properties")
public class BillingServiceApplication {

	public static void main(String[] args) {
//		SpringApplication.run(BillingServiceApplication.class, args);
	    SpringApplication app = new SpringApplication(BillingServiceApplication.class);
	    initPidWriter(app);
	    app.run(args);
	}
	
	/** PID작성기 초기화 */
	private static void initPidWriter(SpringApplication app) {
		//ApplicationPreparedEvent시 PID기록
		app.addListeners(new ApplicationPidFileWriter());   // pid 를 작성하는 역할을 하는 클래스 선언
	}
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		builder.setConnectTimeout(10*1000);
		builder.setReadTimeout(10*1000);
		return builder.build();
	}
}
