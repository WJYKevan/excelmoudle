package com.wjy.excelmoudle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class ExcelmoudleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExcelmoudleApplication.class, args);
	}

	@RequestMapping
    public String hello(){
	    return "nininin";
    }


}
