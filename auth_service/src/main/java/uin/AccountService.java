package uin;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uin.dto.AccessTokenDTO;
import uin.dto.LoginDTO;
import uin.dto.RegisterDTO;
import uin.helpers.AccountExitsException;
import uin.token.JWTToken;

import java.util.UUID;

@Service
@Transactional
public class AccountService {
    private final AccountRepository repository;
    private final PasswordEncoder pwEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTToken jwtToken;

    public AccountService(AccountRepository repository, PasswordEncoder pwEncoder, AuthenticationManager authenticationManager, JWTToken jwtToken) {
        this.repository = repository;
        this.pwEncoder = pwEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtToken = jwtToken;
    }

    public void addNewAccount(RegisterDTO dto) {
        AccountEntity newAccount = new AccountEntity(UUID.randomUUID().toString(), dto.username(), pwEncoder.encode(dto.password()), dto.email());
        if (repository.findByUsername(newAccount.getUsername()).isPresent()) {
            throw new AccountExitsException();
        }
        repository.save(newAccount);
    }

    public AccessTokenDTO authenticated(AbstractAuthenticationToken authenticationToken) {
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        return new AccessTokenDTO(jwtToken.generate(authenticate));
    }
}
