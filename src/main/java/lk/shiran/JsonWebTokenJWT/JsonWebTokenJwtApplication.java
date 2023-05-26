package lk.shiran.JsonWebTokenJWT;

import lk.shiran.JsonWebTokenJWT.dto.AppRoleDTO;
import lk.shiran.JsonWebTokenJWT.dto.AppUserDTO;
import lk.shiran.JsonWebTokenJWT.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;

@SpringBootApplication
public class JsonWebTokenJwtApplication {

	public String PORT=System.getenv("PORT");

	public static void main(String[] args) {

		SpringApplication.run(JsonWebTokenJwtApplication.class);
		System.out.println("spring start ...!");
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	@Bean
	CommandLineRunner run(UserService userService){
		return args -> {

			userService.saveRole(new AppRoleDTO(null,"ROLE_USER"));
			userService.saveRole(new AppRoleDTO(null,"ROLE_MANAGER"));
			userService.saveRole(new AppRoleDTO(null,"ROLE_ADMIN"));
			userService.saveRole(new AppRoleDTO(null,"ROLE_SUPER_ADMIN"));

			userService.saveUser(new AppUserDTO(null,"shiran sandaruwan","shiran","1234",new ArrayList<>()));
			userService.saveUser(new AppUserDTO(null,"kamal perera","kamal","1234",new ArrayList<>()));
			userService.saveUser(new AppUserDTO(null,"gayan perera","gayan","1234",new ArrayList<>()));
			userService.saveUser(new AppUserDTO(null,"tashi perera","tashi","1234",new ArrayList<>()));


			userService.addRoleToUser("shiran","ROLE_ADMIN");
			userService.addRoleToUser("shiran","ROLE_USER");
			userService.addRoleToUser("shiran","ROLE_MANAGER");
			userService.addRoleToUser("shiran","ROLE_SUPER_ADMIN");
			userService.addRoleToUser("kamal","ROLE_USER");
			userService.addRoleToUser("gayan","ROLE_MANAGER");
			userService.addRoleToUser("tashi","ROLE_ADMIN");

		};
	}

}
