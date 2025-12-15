package sunshine.config;

import java.util.concurrent.TimeUnit;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Request;

@Configuration
@EnableFeignClients(basePackages = "sunshine.feign.client")
public class FeignConfig {

	@Bean
	public Request.Options feignRequestOptions() {
		return new Request.Options(
			5, TimeUnit.SECONDS, 5, TimeUnit.SECONDS, true
		);
	}

}
