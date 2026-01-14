package com.yggdrasil.labs.start;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot Starter
 *
 * @author YoungerYang-Y
 */
@EnableDubbo(scanBasePackages = "com.yggdrasil.labs.adapter.rpc")
@SpringBootApplication(scanBasePackages = {"com.yggdrasil.labs", "com.alibaba.cola"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
