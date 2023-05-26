package lk.shiran.JsonWebTokenJWT.repo;

import lk.shiran.JsonWebTokenJWT.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<AppUser,Long> {
    AppUser findByUsername(String username);
}
