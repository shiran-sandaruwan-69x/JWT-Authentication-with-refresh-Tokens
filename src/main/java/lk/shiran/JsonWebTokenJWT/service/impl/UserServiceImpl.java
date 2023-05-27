package lk.shiran.JsonWebTokenJWT.service.impl;

import lk.shiran.JsonWebTokenJWT.dto.AppRoleDTO;
import lk.shiran.JsonWebTokenJWT.dto.AppUserDTO;
import lk.shiran.JsonWebTokenJWT.entity.AppRole;
import lk.shiran.JsonWebTokenJWT.entity.AppUser;
import lk.shiran.JsonWebTokenJWT.repo.RoleRepo;
import lk.shiran.JsonWebTokenJWT.repo.UserRepo;
import lk.shiran.JsonWebTokenJWT.service.UserService;
import lombok.Lombok;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepo userRepo;

    private final RoleRepo roleRepo;

    @Autowired
    private ModelMapper mapper;

    private final PasswordEncoder passwordEncoder;


    // meken kare spring security walata role tika add kara and access karanna ena user wa add kara
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AppUser user = userRepo.findByUsername(username);
        if (user == null){
            log.error("user not found database");
            throw new UsernameNotFoundException("user not found database");
        }else{
            log.info("user found database {}",username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(appRole -> {
            authorities.add(new SimpleGrantedAuthority(appRole.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),authorities);
    }

    @Override
    public AppUserDTO saveUser(AppUserDTO appUserDTO) {
        if (appUserDTO != null){
         log.info("saving new user {} to database",appUserDTO.getName());
         // meken eken passwordEncoder karana eka karanne
         appUserDTO.setPassword(passwordEncoder.encode(appUserDTO.getPassword()));
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
