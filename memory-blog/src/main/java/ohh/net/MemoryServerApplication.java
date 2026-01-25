package ohh.net;

import org.dromara.x.file.storage.spring.EnableFileStorage;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableFileStorage // 【新增】开启文件存储功能
@SpringBootApplication
@MapperScan("ohh.net.web.mapper") // 必须明确指向你的 mapper 所在包
public class MemoryServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(MemoryServerApplication.class, args);
    }
}
