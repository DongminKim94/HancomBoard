package com.hancom.board;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class HancomBoardApplication {

	@RequestMapping("/")
	public String index() {
		return "한글과컴퓨터 백엔드 개발 직무능력테스트 Spring Boot를 활용한 게시판 개발";
	}
	public static void main(String[] args) {
		SpringApplication.run(HancomBoardApplication.class, args);
	}

}
