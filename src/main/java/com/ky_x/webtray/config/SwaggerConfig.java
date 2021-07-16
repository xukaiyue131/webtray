package com.ky_x.webtray.config;

import com.google.common.base.Predicates;


import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import java.util.*;
import javax.validation.constraints.Max;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket webApiConfig(){
        List<ApiKey> list = new ArrayList<>();
        list.add(new ApiKey("token","token","header"));
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("webApi")
                .apiInfo(webApiInfo())
                .select()
                .paths(Predicates.not(PathSelectors.regex("/admin/.*")))
                .paths(Predicates.not(PathSelectors.regex("/error/.*")))
                .build()
                .securitySchemes(list);

    }

    private ApiInfo webApiInfo(){
        return new ApiInfoBuilder()
                .title("在线办公系统")
                .description("本文档描述了办公系统微服务接口定义")
                .version("1.0")
                .contact(new Contact("java","http://xukaiyue.com","2878786711@qq.com"))
                .build()

                ;
    }

}
