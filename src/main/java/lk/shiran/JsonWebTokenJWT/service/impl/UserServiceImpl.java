package lk.shiran.JsonWebTokenJWT.service.impl;

import lk.shiran.JsonWebTokenJWT.dto.AppRoleDTO;
import lk.shiran.JsonWebTokenJWT.dto.AppUserDTO;
import lk.shiran.JsonWebTokenJWT.service.UserService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Override
    public AppUserDTO saveUser(AppUserDTO appUserDTO) {
        return null;
    }

    @Override
    public AppRoleDTO saveRole(AppRoleDTO appRoleDTO) {
        return null;
    }

    @Override
    public void addRoleToUser(String username, String roleName) {

    }

    @Override
    public AppUserDTO getUser(String username) {
        return null;
    }

    @Override
    public List<AppUserDTO> getUsers() {
        return null;
    }
}
