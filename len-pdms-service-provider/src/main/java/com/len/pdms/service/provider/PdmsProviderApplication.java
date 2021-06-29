package com.len.pdms.service.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author zhuxiaomeng
 * @date 2018/1/1.
 * @email 154040976@qq.com
 */

@EnableWebMvc
@SpringBootApplication
@EnableTransactionManagement
@ComponentScan({"com.len.pdms.service.provider"})
@MapperScan(basePackages = {"com.len.mapper"})
@EnableAutoConfiguration( exclude = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
})
public class PdmsProviderApplication {

    public static void main(String[] args) {
//        ApplicationContext applicationContext = SpringApplication.run(PdmsProviderApplication.class, args);
        SpringApplicationBuilder builder = new SpringApplicationBuilder(PdmsProviderApplication.class);
        //监听
        builder.listeners((ApplicationListener<ApplicationEnvironmentPreparedEvent>) event-> {
            Environment environment = event.getEnvironment();
            int port = environment.getProperty("embedded.zookeeper.port", int.class);
            new EmbeddedZooKeeper(port,true).start();

        }).run(args);
//        new SpringApplicationBuilder(PdmsProviderApplication.class)
//                .listeners((ApplicationListener<ApplicationEnvironmentPreparedEvent>) event -> {
//                    Environment environment = event.getEnvironment();
//                    int port = environment.getProperty("embedded.zookeeper.port", int.class);
//                    new EmbeddedZooKeeper(port, false).start();
//                }).run(args);

        System.out.println("PdmsProviderApplication Server start succ");
    }


}
