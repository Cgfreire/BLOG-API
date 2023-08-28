package com.unisales.blogapi;

import com.unisales.blogapi.filters.AuthFilter;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BlogApi {

	public static void main(String[] args) {
		SpringApplication.run(BlogApi.class, args);
	}
        
        @Autowired
        private AuthFilter authFilter;


        /**
         * adicionamos o filtro de login na navegação
         *
         * @return
         */
        @Bean
        public FilterRegistrationBean<AuthFilter> filterRegistrationBean() {
         FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean();
         registrationBean.setFilter(authFilter);
         // aplica-se apenas ao endpoint tarefa
         registrationBean.addUrlPatterns("/posts/*");
         // define a ordem de precedencia do filtro
         registrationBean.setOrder(1);
         return registrationBean;
        }

        
	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
                    .info(new Info()
                            .title("Blog API")
                            .version("0.1")
                            .description("API desenvolvida para o Blog ConnectSales")
                    .addSecurityItem(new SecurityRequirement().addList("Auth JWT"))
                    .components(new Components()
                    .addSecuritySchemes("Auth JWT",
           new SecurityScheme()
                        .name("Auth JWT")
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("Bearer")
                        .bearerFormat("JWT")))
                    .termsOfService("http://swagger.io/terms/")
                    .license(new License().name("Apache 2.0").url("http://springdoc.org")));
	}
}