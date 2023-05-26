package lk.shiran.JsonWebTokenJWT.service;

import lk.shiran.JsonWebTokenJWT.dto.AppRoleDTO;
import lk.shiran.JsonWebTokenJWT.dto.AppUserDTO;
import lk.shiran.JsonWebTokenJWT.entity.AppRole;

import java.util.List;

public interface UserService {
    AppUserDTO saveUser(AppUserDTO appUserDTO);
    AppRoleDTO saveRole(AppRoleDTO appRoleDTO);
    void addRoleToUser(String username,String roleName);
    AppUserDTO getUser(String username);
    List<AppUserDTO> getUsers();

}
