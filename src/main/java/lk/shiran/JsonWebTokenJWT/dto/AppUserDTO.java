package lk.shiran.JsonWebTokenJWT.dto;

import lk.shiran.JsonWebTokenJWT.entity.AppRole;

import java.util.ArrayList;
import java.util.Collection;

public class AppUserDTO {
    private Long id;
    private String name;
    private String username;
    private String password;
    private Collection<AppRole> roles = new ArrayList<>();
}
