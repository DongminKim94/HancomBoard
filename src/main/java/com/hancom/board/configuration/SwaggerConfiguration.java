package com.hancom.board.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
	
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
        		.apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any()) //현재 RequestMapping으로 할당된 모든 URL 추출
                .paths(PathSelectors.ant("/hancom/board/**")) //그중 RequestMapping이 "/hancom/board/**"인 URL들만 필터링하여 UI에 보여줌
                .build();
    }
    
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("한글과컴퓨터 게시판 API Docs").version("1.0.0").description("한글과컴퓨터 Back End 개발 부문 직무 능력 테스트인 게시판 만들기 RESTful API 문서입니다.").build();
	}
	
}
