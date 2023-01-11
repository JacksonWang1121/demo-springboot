package com.example.demo.master;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan(basePackages = {"com.example.demo.master.mapper"})  //扫描指定包下的Bean
@EnableScheduling  //开启任务调度
@EnableAdminServer  //开启Admin后台监控
public class DemoMasterApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoMasterApplication.class, args);
    }

}
