package com.example.FBJV24001115synergy7indbinfoodch6;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@EnableJpaAuditing
@SpringBootApplication
public class FBjv24001115Synergy7IndBinfoodCh6Application {

	public static void main(String[] args) throws InterruptedException{
		SpringApplication.run(FBjv24001115Synergy7IndBinfoodCh6Application.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	

}
