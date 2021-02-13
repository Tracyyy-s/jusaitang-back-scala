//package com.tracy.competition.config
//
//import org.springframework.context.annotation.{Bean, Configuration}
//import springfox.documentation.builders.{ApiInfoBuilder, PathSelectors, RequestHandlerSelectors}
//import springfox.documentation.service.ApiInfo
//import springfox.documentation.spi.DocumentationType
//import springfox.documentation.spring.web.plugins.Docket
//import springfox.documentation.swagger2.annotations.EnableSwagger2
//
///**
// * @author Tracy
// * @date 2021/2/9 12:03
// */
//@Configuration
//@EnableSwagger2
//class SwaggerConfig {
//
//  @Bean def createRestApi: Docket = {
//    new Docket(DocumentationType.SWAGGER_2)
//      .apiInfo(apiInfo)
//      .select
//      .apis(RequestHandlerSelectors.basePackage("com.tracy.competition.controller"))
//      .paths(PathSelectors.any)
//      .build
//  }
//
//  private def apiInfo = {
//    new ApiInfoBuixlder()
//      .title("聚赛堂 Restful APIs For Scala")
//      .description("聚赛堂后端相关接口，使用Scala重构")
//      .termsOfServiceUrl("https://github.com/Tracyyy-s/jusaitang-back")
//      .contact("tracy")
//      .version("1.0")
//      .build
//  }
//}
