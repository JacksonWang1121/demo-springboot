package com.example.demo.master.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * <p>
 * <h2>简述</h2>
 * 	   <ol>无</ol>
 *   <h2>功能描述</h2>
 * 	   <ol>无</ol>
 *   <h2>修改历史</h2>
 *     <ol>无</ol>
 * </p>
 *
 * @author wangjisen
 * @version 1.0
 * @date 2022/12/22 16:05
 */

@Configuration
@EnableSwagger2  //表示开启Swagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        //构建文档
        return new Docket(DocumentationType.SWAGGER_2)
                //文档信息
                .apiInfo(apiInfo())
                //查询
                .select()
                //注解包的路径
                .apis(RequestHandlerSelectors.basePackage("com.example.demo.master.controller"))
                //任何路径
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 文档信息
     * @return  文档信息对象
     */
    private ApiInfo apiInfo() {
        //文档对象构建器
        ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder();
        //文档标题
        return apiInfoBuilder.title("demo-springboot-master API DOC")
                //描述信息
                .description("demo-springboot-master OpenAPI")
                //版本号
                .version("V1.0.0")
                //联系人
                .contact(new Contact("demo-springboot-master", "http://localhost:8088/demo-springboot-master", "demo-springboot-master@gmail.com"))
                //声明许可
                .license("demo-springboot-master许可")
                //许可路径
                .licenseUrl("https://github.com/JacksonWang1121/demo-springboot-master.git")
                .build();
    }

}
