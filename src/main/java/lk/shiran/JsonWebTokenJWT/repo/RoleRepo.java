package lk.shiran.JsonWebTokenJWT.repo;

import lk.shiran.JsonWebTokenJWT.entity.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<AppRole,Long> {
    AppRole findByName(String name);
}
