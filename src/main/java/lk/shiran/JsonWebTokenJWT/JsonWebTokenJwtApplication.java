package lk.shiran.JsonWebTokenJWT;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JsonWebTokenJwtApplication {

	public static void main(String[] args) {

		SpringApplication.run(JsonWebTokenJwtApplication.class);
		System.out.println("spring start ...!");
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

}
