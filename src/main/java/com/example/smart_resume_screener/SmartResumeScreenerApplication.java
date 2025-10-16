package com.example.smart_resume_screener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class SmartResumeScreenerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartResumeScreenerApplication.class, args);
        System.out.println("Smart Resume Screener Backend is Running!");
	}
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:63342") // your frontend URL or port
                        .allowedMethods("GET", "POST", "PUT", "DELETE");
            }
        };
    }
}


