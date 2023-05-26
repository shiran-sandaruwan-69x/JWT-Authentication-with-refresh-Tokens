package lk.shiran.JsonWebTokenJWT.service.impl;

import lk.shiran.JsonWebTokenJWT.dto.AppRoleDTO;
import lk.shiran.JsonWebTokenJWT.dto.AppUserDTO;
import lk.shiran.JsonWebTokenJWT.entity.AppRole;
import lk.shiran.JsonWebTokenJWT.entity.AppUser;
import lk.shiran.JsonWebTokenJWT.repo.RoleRepo;
import lk.shiran.JsonWebTokenJWT.repo.UserRepo;
import lk.shiran.JsonWebTokenJWT.service.UserService;
import lombok.Lombok;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private ModelMapper mapper;

    @Override
    public AppUserDTO saveUser(AppUserDTO appUserDTO) {
        if (appUserDTO != null){
         log.info("saving new user {} to database",appUserDTO.getName());
         userRepo.save(mapper.map(appUserDTO,AppUser.class));
         return appUserDTO;
        }
        return null;
    }

    @Override
    public AppRoleDTO saveRole(AppRoleDTO appRoleDTO) {
        if (appRoleDTO != null){
            log.info("saving new role {} to database",appRoleDTO.getName());
            roleRepo.save(mapper.map(appRoleDTO, AppRole.class));
            return appRoleDTO;
        }
        return null;
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("Adding role {} to user {}",roleName,username);
        AppUser user = userRepo.findByUsername(username);
        AppRole role = roleRepo.findByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public AppUserDTO getUser(String username) {
        log.info("fetching user {}",username);
        return mapper.map(userRepo.findByUsername(username),AppUserDTO.class);
    }

    @Override
    public List<AppUserDTO> getUsers() {
        log.info("fetching all users");
        return mapper.map(userRepo.findAll(), new TypeToken<ArrayList<AppUserDTO>>(){}.getType());
    }
}
