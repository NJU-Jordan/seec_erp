package com.nju.edu.erp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.nju.edu.erp.dao")
public class ErpApplication {


    public static void main(String[] args) {
        SpringApplication.run(ErpApplication.class, args);
    }

}
