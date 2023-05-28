package lk.shiran.JsonWebTokenJWT.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lk.shiran.JsonWebTokenJWT.dto.AppRoleDTO;
import lk.shiran.JsonWebTokenJWT.dto.AppUserDTO;
import lk.shiran.JsonWebTokenJWT.dto.RoleUserDTO;
import lk.shiran.JsonWebTokenJWT.entity.AppRole;
import lk.shiran.JsonWebTokenJWT.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@CrossOrigin
@RequestMapping("/api/user")
public class UserController {

    // http://localhost:8080/JsonWebTokenJWT/login me login method eka enne filter wala UsernamePasswordAuthenticationFilter
    // default enne post http method type eken

    //http://localhost:8080/JsonWebTokenJWT/login me default url ek change karnn puluwan apita
    // eka SecurityConfig eka krl thiyanwa /api/userlogin walata == http://localhost:8080/JsonWebTokenJWT/api/userlogin

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<AppUserDTO>> getAllUsers(){
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @PostMapping("/user/save")
    public ResponseEntity<AppUserDTO> saveUser(@RequestBody AppUserDTO userDTO){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequestUri().path("/api/user/user/save").toUriString());
        AppUserDTO appUserDTO = userService.saveUser(userDTO);
        return ResponseEntity.created(uri).body(appUserDTO);
    }

    @PostMapping("/role/save")
    public ResponseEntity<AppRoleDTO> saveRole(@RequestBody AppRoleDTO roleDTO){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequestUri().path("/api/user/role/save").toUriString());
        AppRoleDTO appRoleDTO = userService.saveRole(roleDTO);
        return ResponseEntity.created(uri).body(appRoleDTO);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<AppUserDTO> getUser(@PathVariable String username){
        AppUserDTO user = userService.getUser(username);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/role/addtouser")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleUserDTO roleUserDTO){
        userService.addRoleToUser(roleUserDTO.getUsername(),roleUserDTO.getRoleName());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String authorizationHeader = request.getHeader(AUTHORIZATION);

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){

            try {

                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);

                String username = decodedJWT.getSubject();
                AppUserDTO user = userService.getUser(username);

                String access_token = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getRoles().stream().map(AppRole::getName).collect(Collectors.toList()))
                        .sign(algorithm);


                // convert to json format
                Map<String,String> tokens = new HashMap<>();
                tokens.put("access_token",access_token);
                tokens.put("refresh_token",refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(),tokens);

            }catch (Exception e){
                System.out.println(e.getMessage());
                response.setHeader("error",e.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String,String> error = new HashMap<>();
                error.put("error_message",e.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(),error);
            }

        }else{
            throw new RuntimeException("Refresh token missing");
        }
    }

}
