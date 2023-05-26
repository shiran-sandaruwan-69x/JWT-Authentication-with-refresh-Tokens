package lk.shiran.JsonWebTokenJWT.controller;

import lk.shiran.JsonWebTokenJWT.dto.AppRoleDTO;
import lk.shiran.JsonWebTokenJWT.dto.AppUserDTO;
import lk.shiran.JsonWebTokenJWT.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<AppUserDTO>> getAllUsers(){
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @PostMapping("/user/save")
    public ResponseEntity<AppUserDTO> saveUser(@RequestBody AppUserDTO userDTO){
        AppUserDTO appUserDTO = userService.saveUser(userDTO);
        return ResponseEntity.ok().body(appUserDTO);
    }

    @PostMapping("/role/save")
    public ResponseEntity<AppRoleDTO> saveRole(@RequestBody AppRoleDTO roleDTO){
        AppRoleDTO appRoleDTO = userService.saveRole(roleDTO);
        return ResponseEntity.ok().body(appRoleDTO);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<AppUserDTO> getUser(@PathVariable String username){
        AppUserDTO user = userService.getUser(username);
        return ResponseEntity.ok().body(user);
    }


}
