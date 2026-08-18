package org.example.djiankang;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@ServletComponentScan
@MapperScan("org.example.djiankang.db.mapper")
@EnableAsync
public class DjiankangApplication {
    public static void main(String[] args) {
        SpringApplication.run(DjiankangApplication.class, args);
    }

}
