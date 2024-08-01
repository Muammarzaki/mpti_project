package uin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Document(collection = "account")
@NoArgsConstructor
public class AccountEntity implements UserDetails {
    @Id
    private String userId;
    @Indexed
    private String username;
    private String credential;
    private String email;
    private List<String> roles = new LinkedList<>();

    public AccountEntity(String userId, String username, String credential, String email) {
        this.userId = userId;
        this.username = username;
        this.credential = credential;
        this.email = email;
    }

    public AccountEntity(String userId, String username, String credential, String email, List<String> roles) {
        this.userId = userId;
        this.username = username;
        this.credential = credential;
        this.email = email;
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles
                .stream().map("ROLE_%s"::formatted)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public String getPassword() {
        return this.credential;
    }
}
