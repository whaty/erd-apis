package com.java2e.martin.erd.config;


import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 狮少
 * @version 1.0
 * @date 2019/11/1
 * @describtion MybatisPlusConfiguration
 * @since 1.0
 */
@Configuration
@Slf4j
public class MybatisPlusConfiguration {
    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }


}
