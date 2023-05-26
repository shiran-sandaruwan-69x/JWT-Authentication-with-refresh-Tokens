package lk.shiran.JsonWebTokenJWT.dto;

import lk.shiran.JsonWebTokenJWT.entity.AppRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AppUserDTO {
    private Long id;
    private String name;
    private String username;
    private String password;
    private Collection<AppRole> roles = new ArrayList<>();
}
