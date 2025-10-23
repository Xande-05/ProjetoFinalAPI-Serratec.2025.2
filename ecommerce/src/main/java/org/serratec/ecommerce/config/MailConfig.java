package org.serratec.ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MailConfig {

	@Bean
	public JavaMailSander javaMailSander() {
		return new JavaMailSander();
	}
}
