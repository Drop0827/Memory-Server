package ohh.net.common.config;

import ohh.net.common.interceptor.JwtTokenAdminInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private JwtTokenAdminInterceptor jwtTokenAdminInterceptor;
    @Value("${file.dir}") // 从配置文件中读取上传目录
    private String uploadDir;

    private static final Set<String> EXCLUDED_PATHS = new HashSet<>(Arrays.asList(
            "/",
            "/doc.html",
            "/swagger-resources",
            "/webjars",
            "/v2/api-docs",
            "/v3/api-docs",
            "/swagger-ui.html",
            "/swagger-ui"));

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        // 指定统一API访问前缀
        // 只为 ohh.net.web.controller 包下的 Controller 添加 /api 前缀
        configurer.addPathPrefix("/api", c -> {
            // 检查 Controller 类是否在业务包中
            String packageName = c.getPackageName();
            boolean isBusinessController = packageName.startsWith("ohh.net.web.controller");

            // 如果是业务 Controller，还需要检查是否在排除列表中
            if (isBusinessController) {
                RequestMapping requestMapping = c.getAnnotation(RequestMapping.class);
                if (requestMapping != null && requestMapping.value().length > 0) {
                    String path = requestMapping.value()[0];
                    return !EXCLUDED_PATHS.contains(path);
                }
                return true;
            }

            // 非业务 Controller（如 SpringDoc 的内部 Controller）不添加前缀
            return false;
        });
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 拦截指定请求
        registry.addInterceptor(jwtTokenAdminInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns(
                        "/api/user/login",
                        // Swagger 和 Knife4j 相关路径
                        "/doc.html",
                        "/doc.html/**",
                        "/swagger-resources/**",
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/webjars/**",
                        "/favicon.ico")
                .excludePathPatterns("/doc.html")
                .excludePathPatterns("/webjars/**")
                .excludePathPatterns("/swagger-resources/**")
                .excludePathPatterns("/v2/api-docs/**")
                .excludePathPatterns("/v3/api-docs/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/upload/**")
                .addResourceLocations("file:" + uploadDir);
    }
}
