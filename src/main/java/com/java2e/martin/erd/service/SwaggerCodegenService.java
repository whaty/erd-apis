package com.java2e.martin.erd.service;

import com.java2e.martin.erd.util.Result;

import java.util.Map;

/**
 * @author 狮少
 * @version 1.0
 * @date 2020/12/11
 * @describtion SwaggerCodegenService
 * @since 1.0
 */
public interface SwaggerCodegenService {
    /**
     * 生成 spring boot 风格的代码
     *
     * @param map
     * @return
     */
    Result springBoot(Map map);
}
