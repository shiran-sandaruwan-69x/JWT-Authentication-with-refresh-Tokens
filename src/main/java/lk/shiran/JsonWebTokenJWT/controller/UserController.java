package lk.shiran.JsonWebTokenJWT.controller;

import lk.shiran.JsonWebTokenJWT.dto.AppRoleDTO;
import lk.shiran.JsonWebTokenJWT.dto.AppUserDTO;
import lk.shiran.JsonWebTokenJWT.dto.RoleUserDTO;
import lk.shiran.JsonWebTokenJWT.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/user")
public class UserController {

    // http://localhost:8080/JsonWebTokenJWT/login me login method eka enne filter wala UsernamePasswordAuthenticationFilter
    // default enne post http method type eken

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

}
