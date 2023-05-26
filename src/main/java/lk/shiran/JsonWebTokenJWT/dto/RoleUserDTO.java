package lk.shiran.JsonWebTokenJWT.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoleUserDTO {
    private String username;
    private String roleName;
}
