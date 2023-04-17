package com.tweetapp;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class TweetAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(TweetAppApplication.class, args);
    }

	@Bean
	public OpenAPI springShopOpenAPI() {
		return new OpenAPI()
				.info(new Info().title("Tweet App API")
						.description("This api allows user to register, login, reset password and also create, update, delete, read tweets")
						.version("v1.0"))
				.externalDocs(new ExternalDocumentation()
						.description("Github")
						.url("https://github.com/AbhishekSingh612/tweetapp"));
	}


}
