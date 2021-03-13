package com.hancom.board.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class HancomBoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(HancomBoardApplication.class, args);
	}

}
