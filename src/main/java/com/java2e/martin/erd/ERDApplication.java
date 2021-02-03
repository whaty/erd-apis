package com.java2e.martin.erd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 狮少
 * @version 1.0
 * @date 2020/10/16
 * @describtion PDManApplication
 * @since 1.0
 */
@SpringBootApplication
@MapperScan("com.java2e.martin.erd.mapper")
public class ERDApplication {
    public static void main(String[] args) {
        SpringApplication.run(ERDApplication.class, args);
    }


}
