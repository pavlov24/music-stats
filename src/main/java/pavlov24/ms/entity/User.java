package pavlov24.ms.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;


@Data
@Entity(name = "user_profile")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
    private String authority;

    @ManyToMany
    private List<Album> albums;


    public List<GrantedAuthority> getAuthorities() {
        return List.of(() ->authority);
    }

}
