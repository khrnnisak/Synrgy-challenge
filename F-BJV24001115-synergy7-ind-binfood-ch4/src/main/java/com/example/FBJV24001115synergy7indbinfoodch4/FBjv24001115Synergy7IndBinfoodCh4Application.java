package com.example.FBJV24001115synergy7indbinfoodch4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.example.FBJV24001115synergy7indbinfoodch4.controllers.HomeController;

@EnableJpaAuditing
@SpringBootApplication
public class FBjv24001115Synergy7IndBinfoodCh4Application {

	public static void main(String[] args) throws InterruptedException{
		HomeController homeController = SpringApplication.run(FBjv24001115Synergy7IndBinfoodCh4Application.class, args).getBean(HomeController.class);
		homeController.home();
	}

}
