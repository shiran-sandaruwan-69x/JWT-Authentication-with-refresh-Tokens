package lk.shiran.JsonWebTokenJWT.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

import static javax.persistence.FetchType.*;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String username;
    private String password;

    // FetchType.LAZY wala okoma data tika load kara gannawa collection eke thiyana eka application ek slow wenna puluwan
    // a nisa api FetchType.EAGER use karanawa collection walata
    @ManyToMany(fetch = EAGER)
    private Collection<AppRole> roles = new ArrayList<>();
}
