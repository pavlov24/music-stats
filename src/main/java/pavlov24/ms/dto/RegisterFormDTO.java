package pavlov24.ms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;
import pavlov24.ms.entity.User;

@Data
@ToString
public class RegisterFormDTO {

    @NotBlank
    private String username;
    @NotBlank
    @Size(min = 8, max = 24)
    private CharSequence password;


    public User toEntity() {
        var user = new User();
        user.setUsername(username);
//        user.setPassword(password);
        user.setEnabled(true);
        user.setAuthority("USER");
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setAccountNonExpired(true);
        return user;
    }

}
