package com.example.qmex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.qmex.Repository") // 配置 Repository 包的扫描
@EntityScan(basePackages = "com.example.qmex.Entity") // 配置 Entity 包的扫描

public class QMexApplication {
    public static void main(String[] args) {
        SpringApplication.run(QMexApplication.class, args);
    }
}
