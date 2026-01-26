package ohh.net.common.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Knife4j/Swagger 配置类
 * 用于生成在线 API 文档
 */
@Configuration
public class Knife4jConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Memory 博客系统 - 在线API文档")
                        .description("基于 Spring Boot 3 + Vue 3 的全栈博客系统接口文档")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("OHH")
                                .email("2720751424@qq.com")
                                .url("https://github.com/Drop0827/Memory-Server"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .externalDocs(new ExternalDocumentation()
                        .description("Memory 项目文档")
                        .url("https://github.com/Drop0827/Memory-Server"));
    }
}