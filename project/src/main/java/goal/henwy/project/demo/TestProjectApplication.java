package goal.henwy.project.demo;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import javax.annotation.PostConstruct;

/**
 * @author HenwyGoal
 */
@EnableDiscoveryClient
@EnableApolloConfig
@SpringBootApplication
public class TestProjectApplication {

	@Value("${test.integer}")
	private String value;

	@PostConstruct
	public void init() {
		System.err.println("test.integer = " + value);
	}

	public static void main(String[] args) {
		SpringApplication.run(TestProjectApplication.class, args);
	}
}
