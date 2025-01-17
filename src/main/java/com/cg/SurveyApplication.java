package com.cg;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Hello world!
 *
 */

@EnableTransactionManagement
@SpringBootApplication
@MapperScan("com.cg.mapper")
public class SurveyApplication {
    public static void main( String[] args ) {
        SpringApplication.run(SurveyApplication.class, args);
    }
}
