package com.insistbrave

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.web.support.SpringBootServletInitializer

/**
 * 描述
 *
 * @author 张忍
 * @since 0.0.1
 * @date 2018/11/6
 */

@SpringBootApplication
class Application extends SpringBootServletInitializer{
    static void main(String[] args){
        SpringApplication.run(Application.class,args)
    }
}
