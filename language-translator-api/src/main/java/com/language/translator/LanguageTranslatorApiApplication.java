package com.language.translator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.ibm.cloud.sdk.core.security.IamAuthenticator;


@SpringBootApplication
public class LanguageTranslatorApiApplication {
	
	@Value("${watson.api.key}")
	private String apiKey;

	public static void main(String[] args) {
		SpringApplication.run(LanguageTranslatorApiApplication.class, args);
	}

	@Bean 
	protected IamAuthenticator iamAuthenticator() {
		
		IamAuthenticator iamAuthenticator = new IamAuthenticator(apiKey);
		return iamAuthenticator;
	}
	
}
