package ohh.net.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Knife4jConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Memory 博客系统 - 在线API文档")
                        .description("基于 Spring Boot 3 + Vue 3 的全栈博客系统接口文档")
                        .version("v1.0")
                        .contact(new Contact()
                                .name("OHH")
                                .email("2720751424@qq.com")));
    }
}