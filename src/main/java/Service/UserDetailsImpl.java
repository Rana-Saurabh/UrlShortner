package Service;

import Models.Users;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.graph.Graph;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
public class UserDetailsImpl implements UserDetails {
    public UserDetailsImpl(Long id, String username, String password, String email, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.authorities = authorities;
    }

    private static final long serialVersionUID=1L;
    private Long id;
    private String username;
    private String password;
    private String email;
    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
    public static UserDetailsImpl build(Users users){
         GrantedAuthority authority = new SimpleGrantedAuthority(users.getRoles());
          return new UserDetailsImpl(
                  users.getId(),
                  users.getPassword(),
                  users.getEmail(),
                  users.getPassword(),
                  Collections.singletonList(authority)
          );

    }
}
